package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.studenti.CommentoRichiestaIscrizioneNonValidoException;
import it.unisa.di.tirociniosmart.studenti.IdRichiestaIscrizioneNonValidoException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizione;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneGestitaException;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.studenti.StudentiService;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller che espone via web i servizi relativi agli studenti ed alle relative richieste
 * d'iscrizione.
 * 
 * @see StudentiService
 * @see Studente
 * @see RichiestaIscrizione
 */
@Controller
public class RichiestaIscrizioneController {

  private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
  
  @Autowired
  private StudentiService studentiService;
  
  @Autowired
  private RichiestaIscrizioneFormValidator formValidator;
  
  @Autowired
  private UtenzaService utenzaService;
  
  /**
   * Elabora le richieste di iscrizione effettuandone la validazione e richiedendone
   * eventualmente la registrazione.
   * 
   * @param richiestaIscrizioneForm {@link RichiestaIscrizioneForm} che incapsula gli input utente.
   *        <b>Iniettato dalla dispatcher servlet</b>.
   * 
   * @param result Incapsula gli errori di validazione per poi passarli alla vista delegata per la 
   *        presentazione all'utente
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   * 
   * @return Stringa indicante la vista delegata alla presentazione del form in caso di insuccesso,
   *         stringa indicante l'URL della home page (tramite redirect) in caso di successo
   */
  @RequestMapping(value = "/registrazione/studente", method = RequestMethod.POST)
  public String elaboraRichiestaIscrizione(
         @ModelAttribute("richiestaIscrizioneForm") RichiestaIscrizioneForm richiestaIscrizioneForm,
         BindingResult result,
         RedirectAttributes redirectAttributes) {
    // Controlla la validità del form ricevuto come parametro e salva il risultato in result
    formValidator.validate(richiestaIscrizioneForm, result);
    
    // Redirigi l'utente alla pagina del form se sono stati rilevati degli errori, altrimenti
    // istanzia un oggetto studente e richiedine il salvataggio
    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.richiestaIscrizioneForm",
                             result);
      redirectAttributes.addFlashAttribute("richiestaIscrizioneForm", richiestaIscrizioneForm);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.iscrizioni.richiestaNonValida");
      return "redirect:/registrazione#studente";
    }
    
    // Genera un oggetto LocalDate a partire dagli interi presenti nel form
    LocalDate date = LocalDate.of(richiestaIscrizioneForm.getAnnoDiNascita(),
                                  richiestaIscrizioneForm.getMeseDiNascita(),
                                  richiestaIscrizioneForm.getGiornoDiNascita());
    
    // Istanzia un nuovo oggetto studente e richiedine la registrazione. Redirigi verso /errore
    // nel caso qualcosa vada storto.
    Studente studente = new Studente();
    studente.setNome(richiestaIscrizioneForm.getNome());
    studente.setCognome(richiestaIscrizioneForm.getCognome());
    studente.setEmail(richiestaIscrizioneForm.getEmail());
    studente.setIndirizzo(richiestaIscrizioneForm.getIndirizzoStudente());
    studente.setDataDiNascita(date);
    studente.setMatricola(richiestaIscrizioneForm.getMatricola());
    studente.setUsername(richiestaIscrizioneForm.getUsername());
    studente.setPassword(richiestaIscrizioneForm.getPassword());
    studente.setSesso(richiestaIscrizioneForm.getSesso());
    studente.setTelefono(richiestaIscrizioneForm.getTelefono());
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.iscrizioni.richiestaInviata");
    } catch (RichiestaNonAutorizzataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }
    
    return "redirect:/";
  }
  
  /**
   * Permette di prelevare dal database l'elenco delle richieste d'iscrizione.
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   * 
   * @return Stringa indicante l'homepage in caso l'utente che tenta di eseguire l'operazione non 
   *         è un impiegato dell'ufficio tirocini,
   *         Stringa indicante l'URL della pagina da mostrare (tramite redirect) in caso di successo
   */
  @RequestMapping(value = "/dashboard/richieste/iscrizione", method = RequestMethod.GET)
  public String visualizzaRichiesteIscrizione(Model model, RedirectAttributes redirectAttributes) {
    // La lista delle richieste può essere visualizzata solo dall'impiegato dell'ufficio tirocini
    if (!(utenzaService.getUtenteAutenticato() instanceof ImpiegatoUfficioTirocini)) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    try {
      List<RichiestaIscrizione> richieste = studentiService.elencaListaRichiesteIscrizione();
      model.addAttribute("listaRischiesteIscrizione", richieste);
    } catch (RichiestaNonAutorizzataException e) {
      // Redirigi l'utente in home page se non ha le autorizzazioni necessarie per visualizzare
      // la lista delle richieste
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
   
    return "richieste-iscrizione";
  }

  /**
   * Elabora le richieste di iscrizione effettuandone l'approvazione.
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   *        
   * @param idRichiesta Long che indica la richiesta da approvare
   * 
   * @return Stringa indicante la vista in caso di insuccesso nell'approvazione,
   *         stringa indicante l'URL della pagina da mostrare (tramite redirect) 
   *                 in caso di successo,
   *         Stringa indicante l'URL dell'homePage nel caso l'utente che tenta di fare l'operazione
   *                 non è un impiegato dell'Ufficio Tirocini
   */
  @RequestMapping(value = "/dashboard/richieste/iscrizione/approva", method = RequestMethod.POST)
  public String elaboraApprovazioneRichiesta(RedirectAttributes redirectAttributes,
                                             @RequestParam Long idRichiesta) {
    // La richiesta può essere approvata solo dall'impiegato dell'ufficio tirocini
    if (!(utenzaService.getUtenteAutenticato() instanceof ImpiegatoUfficioTirocini)) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    try {
      studentiService.approvaRichiestaIscrizione(idRichiesta);
      redirectAttributes.addFlashAttribute("testoNotifica",
                                             "toast.iscrizioni.richiestaApprovata");
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.iscrizioni.richiestaIscrizioneInesistente");
    } catch (RichiestaIscrizioneGestitaException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
          "toast.iscrizioni.richiestaGestita");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }

    return "redirect:/dashboard/richieste/iscrizione";
  }
  
  /**
   * Elabora le richieste di iscrizione effettuandone il rifiuto.
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   *        
   * @param idRichiesta Long che indica la richiesta da rifiutare
   * 
   * @param commentoRichiesta Stringa che indica il commento da associare alla richiesta
   * 
   * @return Stringa indicante la vista in caso di insuccesso nel rifiuto,
   *         stringa indicante l'URL della pagina da mostrare (tramite redirect) in caso di successo
   *         Stringa indicante l'URL dell'homePage nel caso l'utente che tenta di fare l'operazione
   *                 non è un impiegato dell'Ufficio Tirocini
   */
  @RequestMapping(value = "/dashboard/richieste/iscrizione/rifiuta", method = RequestMethod.POST)
  public String elaboraRifiutoRichiesta(RedirectAttributes redirectAttributes,
                                        @RequestParam Long idRichiesta,
                                        @RequestParam String commentoRichiesta) {
    // La richiesta può essere rifiutata solo dall'impiegato dell'ufficio tirocini
    if (!(utenzaService.getUtenteAutenticato() instanceof ImpiegatoUfficioTirocini)) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    try {
      studentiService.rifiutaRichiestaIscrizione(idRichiesta, commentoRichiesta);
      redirectAttributes.addFlashAttribute("testoNotifica",
                                             "toast.iscrizioni.richiestaRifiutata");
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.iscrizioni.richiestaIscrizioneInesistente");
    } catch (RichiestaIscrizioneGestitaException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.iscrizioni.richiestaGestita");
    } catch (CommentoRichiestaIscrizioneNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.iscrizioni.commentoRichiestaNonValido");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }

    return "redirect:/dashboard/richieste/iscrizione";
  }
  
}
