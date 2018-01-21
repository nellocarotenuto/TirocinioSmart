package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.ConvenzioniService;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaNonValidoException;
import it.unisa.di.tirociniosmart.progettiformativi.DescrizioneProgettoNonValidaException;
import it.unisa.di.tirociniosmart.progettiformativi.IdProgettoFormativoInesistenteException;
import it.unisa.di.tirociniosmart.progettiformativi.NomeProgettoNonValidoException;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettiFormativiService;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.utenza.AutenticazioneHolder;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.web.ProgettoFormativoForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller che espone via web i servizi relativi ai progetti formativi.
 * 
 * @see progettiFormativiService
 * @see ProgettoFormativo
 */
@Controller
public class ProgettiFormativiController {
  
  @Autowired
  private ProgettiFormativiService progettoFormativoService;
  
  @Autowired
  private ConvenzioniService convenzioniService;
  
  @Autowired
  private ProgettoFormativoFormValidator formValidator;
  
  /**
   * Fornise l'elenco dei progetti formativi messi a disposizione da un'azienda.
   * 
   * @param idAzienda stringa che indica l'id dell'azienda della quale visualizzare i progetti 
   *        formativi
   * 
   * @param model Incapsula gli attributi da passare alla pagina delegata alla presentazione
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli 
   *        disponibili anche dopo un redirect
   *        
   * @return Stringa indicante la vista delegata alla presentazione della lista di progetti
   *         formativi dell'azienda specificata in caso di successo, la stringa indicante la pagina
   *         che elenca le aziende (tramite redirect) in caso di insuccesso
   */
  @RequestMapping(value = "/azienda/{idAzienda}", method = RequestMethod.GET)
  public String elencaProgettiFormativi(@PathVariable("idAzienda") String idAzienda, Model model, 
                                        RedirectAttributes redirectAttributes) {
   
      try {
        Azienda azienda = convenzioniService.ottieniAzienda(idAzienda);
        model.addAttribute("azienda", azienda);
        
      } catch (IdAziendaNonValidoException e) {
        redirectAttributes.addFlashAttribute("testoNotifica",
                                             "toast.convenzioni.idAziendaNonValido");
        return "redirect:/aziende";
      }
    
   // model.addAttribute("progettoFormativoForm", new ProgettoFormativoForm());
    return "pages/progettiFormativi";
  }
  
  /**
   * Elabora l'aggiunta di un progetto formativo effettuandone la validazione.
   * 
   * @param progettoFormativoForm {@link ProgettoFormativoForm} che incapsula gli input utente.
   *        <b>Iniettato dalla dispatcher servlet</b>.
   * 
   * @param result Incapsula gli errori di validazione per poi passarli alla vista delegata per la 
   *        presentazione all'utente
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   * 
   * @return Stringa indicante la vista delegata alla presentazione del form in caso di insuccesso,
   *         stringa indicante l'URL dei progetti formativi dell'azienda (tramite redirect) 
   *         in caso di successo
   */
  @RequestMapping(value = "/dashboard/progetti/aggiungi", method = RequestMethod.POST)
  public String aggiungiProgettoFormativo(@ModelAttribute("progettoFormativoForm")
                                          ProgettoFormativoForm progettoFormativoForm, 
                                          BindingResult result,
                                          RedirectAttributes redirectAttributes) {
    
    // Controlla la validità del form ricevuto come parametro e salva il risultato in result
    formValidator.validate(progettoFormativoForm, result);
    
    // Redirigi l'utente alla pagina del form se sono stati rilevati degli errori
    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.progettoFormativoForm",
                             result);
      redirectAttributes.addFlashAttribute("progettoFormativoForm", progettoFormativoForm);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.convenzioni.richiestaNonValida");

      return "redirect:/dashboard/progetti";
    }
    
    // Instanzia un nuovo oggetto ProgettoFormativo
    ProgettoFormativo progettoFormativo = new ProgettoFormativo();
    progettoFormativo.setNome(progettoFormativoForm.getNome());
    progettoFormativo.setDescrizione(progettoFormativoForm.getDescrizione());
    
    try {
      // Richiedi il salvataggio del progetto e redirigi alla dashboard
      progettoFormativoService.aggiungiProgettoFormativo(progettoFormativo);
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.progettiFormativi.progettoAggiunto");
      return "redirect:/dashboard/progetti";
    } catch (RichiestaNonAutorizzataException e) {
      // Redirigi alla home page se l'utente non dispone delle autorizzazioni necessarie
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    } catch (NomeProgettoNonValidoException e) {
      // Redirigi alla pagina di aggiunta progetti nel caso in cui il nome non sia valido
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.progettiFormativi.nomeProgettoNonValido");
      return "redirect:/dashboard/progetti";
    } catch (DescrizioneProgettoNonValidaException e) {
      // Redirigi alla pagina di aggiunta progetti nel caso in la descrizione non sia valida
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.progettiFormativi.descrizioneProgettoNonValida");
      return "redirect:/dashboard/progetti";
    }
    
  }
  
  /**
   * Elabora l'archiviazione di un progetto formativo.
   * 
   * @param idProgetto Long indicante l'id del progetto da archiviare
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   * 
   * @return Stringa indicante la vista delegata alla presentazione del form in caso di insuccesso,
   *         stringa indicante l'URL dei progetti formativi dell'azienda (tramite redirect) 
   *         in caso di successo
   */
  @RequestMapping(value = "/dashboard/progetti/archivia", method = RequestMethod.POST)
  public String archiviaProgettoFormativo(RedirectAttributes redirectAttributes,
                                          @RequestParam Long idProgetto) {
    
    try {
      // Richiedi l'archiviazione del progetto e redirigi alla dashboard
      progettoFormativoService.archiviaProgettoFormativo(idProgetto);
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.progettiFormativi.progettoArchiviato");
      return "redirect:/dashboard/progetti";
    } catch (RichiestaNonAutorizzataException e) {
      // Redirigi alla home page se l'utente non dispone delle autorizzazioni necessarie
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    } catch (IdProgettoFormativoInesistenteException e) {
      // Redirigi alla pagina che elenca i progetti dell'azienda se l'id del progetto non è valido
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.progettiFormativi.idProgettoNonValido");
      return "redirect:/dashboard/progetti";
    }
    
  }
  
}
