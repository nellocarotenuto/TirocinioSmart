package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.ConvenzioniService;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.IdRichiestaConvenzionamentoNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoGestitaException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.utenza.AutenticazioneHolder;

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
 * Controller che espone via web i servizi relativi alle convenzioni.
 * 
 * @see ConvenzioniService
 */
@Controller
public class ConvenzioniController {

  private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
  
  @Autowired
  private ConvenzioniService convenzioniService;
  
  @Autowired
  private ConvenzionamentoFormValidator formValidator;
  
  
  /**
   * Elabora le richieste di convenzionamento effettuandone la validazione e richiedendone
   * eventualmente la registrazione.
   * 
   * @param convenzionamentoForm {@link ConvenzionamentoForm} che incapsula gli input utente.
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
  @RequestMapping(value = "/registrazione/azienda", method = RequestMethod.POST)
  public String elaboraRichiestaConvenzionamento(
                  @ModelAttribute("convenzionamentoForm") ConvenzionamentoForm convenzionamentoForm,
                  BindingResult result,
                  RedirectAttributes redirectAttributes) {    
    // Controlla la validità del form ricevuto come parametro e salva il risultato in result
    formValidator.validate(convenzionamentoForm, result);
    
    // Redirigi l'utente alla pagina del form se sono stati rilevati degli errori, altrimenti
    // istanzia un oggetto azienda e richiedine il salvataggio
    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.convenzionamentoForm",
                             result);
      redirectAttributes.addFlashAttribute("convenzionamentoForm", convenzionamentoForm);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.convenzioni.richiestaNonValida");
      return "redirect:/registrazione#azienda";
    }
    
    // Istanzia un nuovo oggetto azienda e richiedine la registrazione. Redirigi verso /errore
    // nel caso qualcosa vada storto.
    Azienda azienda = new Azienda();
    azienda.setId(convenzionamentoForm.getIdAzienda());
    azienda.setNome(convenzionamentoForm.getNomeAzienda());
    azienda.setPartitaIva(convenzionamentoForm.getPartitaIvaAzienda());
    azienda.setIndirizzo(convenzionamentoForm.getIndirizzoAzienda());
    azienda.setSenzaBarriere(convenzionamentoForm.isSenzaBarriere());
    
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername(convenzionamentoForm.getUsername());
    delegato.setPassword(convenzionamentoForm.getPassword());
    delegato.setEmail(convenzionamentoForm.getEmail());
    delegato.setNome(convenzionamentoForm.getNome());
    delegato.setCognome(convenzionamentoForm.getCognome());
    delegato.setSesso(convenzionamentoForm.getSesso());
    delegato.setTelefono(convenzionamentoForm.getTelefono());
    
    try {
      convenzioniService.registraRichiestaConvenzionamento(azienda);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.convenzioni.richiestaInviata");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }
    
    return "redirect:/";
  }
  
  /**
   * Fornisce l'elenco delle richieste di convenzionamento non ancora gestite dall'ufficio tirocini.
   *
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   *
   * @param model Incapsula gli attributi da passare alla pagina delegata alla presentazione
   * 
   * @return Stringa indicante la vista delegata alla presentazione della lista se l'utente è
   *         autorizzato, stringa indicante l'URL della home page (tramite redirect) altrimenti
   */
  @RequestMapping(value = "/dashboard/richieste/convenzionamento", method = RequestMethod.GET)
  public String visualizzaRichiesteConvenzionamento(RedirectAttributes redirectAttributes, 
                                                    Model model) {
    // La lista delle richieste può essere visualizzata solo dall'impiegato dell'ufficio tirocini
    if (!(AutenticazioneHolder.getUtente() instanceof ImpiegatoUfficioTirocini)) {
      redirectAttributes.addFlashAttribute("TestoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    List<RichiestaConvenzionamento> listaRichiesteConvenzionamento = 
                                    convenzioniService.elencaRichiesteConvenzionamentoInAttesa(); 
    model.addAttribute("listaRichiesteConvenzionamento", listaRichiesteConvenzionamento);
    
    return "pages/richiesteConvenzionamento";
  }
  
  
  /**
   * Fornisce l'elenco delle aziende convenzionate con l'università.
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   * 
   * @param model Incapsula gli attributi da passare alla pagina delegata alla presentazione
   * 
   * @return stringa indicante la vista delegata alla presentazione della lista di aziende 
   *         convenzionate
   */
  
  @RequestMapping(value = "/aziende", method = RequestMethod.GET)
  public String viualizzaAziendeConvenzionate(RedirectAttributes redirectAttributes,
                                               Model model) {

    List<Azienda> listaAziendeConvenzionate = convenzioniService.elencaAziendeConvenzionate();
    model.addAttribute("listaAziendeConvenzionate", listaAziendeConvenzionate);
    
    return "pages/aziendeConvenzionate";
  }
  
  /**
   * Elabora le richieste di convenzionamento effettuandone l'approvazione.
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
  @RequestMapping(value = "/dashboard/richieste/convenzionamento/approva",
                  method = RequestMethod.POST)
  public String elaboraApprovazioneRichiestaConvenzionamento(RedirectAttributes redirectAttributes,
                                                             @RequestParam Long idRichiesta) {
    // La richiesta può essere approvata solo dall'impiegato dell'ufficio tirocini
    if (!(AutenticazioneHolder.getUtente() instanceof ImpiegatoUfficioTirocini)) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    try {
      convenzioniService.approvaRichiestaConvenzionamento(idRichiesta);
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.convenzioni.richiestaApprovata");
    } catch (IdRichiestaConvenzionamentoNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                          "toast.convenzioni.richiestaConvenzionamentoInesistente");
    } catch (RichiestaConvenzionamentoGestitaException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
          "toast.convenzioni.richiestaGestita");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }

    return "redirect:/dashboard/richieste/convenzionamento";
  }
  
  /**
   * Elabora le richieste di convenzionamento effettuandone il rifiuto.
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
  @RequestMapping(value = "/dashboard/richieste/convenzionamento/rifiuta",
                  method = RequestMethod.POST)
  public String elaboraRifiutoRichiesta(RedirectAttributes redirectAttributes,
                                        @RequestParam Long idRichiesta,
                                        @RequestParam String commentoRichiesta) {
    // La richiesta può essere rifiutata solo dall'impiegato dell'ufficio tirocini
    if (!(AutenticazioneHolder.getUtente() instanceof ImpiegatoUfficioTirocini)) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    try {
      convenzioniService.rifiutaRichiestaConvenzionamento(idRichiesta, commentoRichiesta);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.convenzioni.richiestaRifiutata");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }

    return "redirect:/dashboard/richieste/convenzionamento";
  }
}
