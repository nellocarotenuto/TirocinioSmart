package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.CommentoRichiestaConvenzionamentoNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.ConvenzioniService;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.IdRichiestaConvenzionamentoNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoGestitaException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.progettiformativi.DescrizioneNonValidaException;
import it.unisa.di.tirociniosmart.progettiformativi.IdProgettoFormativoInesistenteException;
import it.unisa.di.tirociniosmart.progettiformativi.NomeProgettoNonValidoException;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettiFormativiService;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.utenza.AutenticazioneHolder;

import java.util.logging.Logger;
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

  private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
  
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
   * @return
   */
  @RequestMapping(value = "/azienda/{idAzienda}", method = RequestMethod.GET)
  public String elencaProgettiFormativi(@PathVariable("idAzienda") String idAzienda, Model model, 
                                        RedirectAttributes redirectAttributes) {
    try {
      Azienda azienda = convenzioniService.ottieniAzienda(idAzienda);
      model.addAttribute("azienda", azienda);
    } catch (IdAziendaNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.progettiFormativi.idNonValido");
      return "redirect:/aziende";
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }
      
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
  @RequestMapping(value = "/azienda/aggiungiProgettoFormativo", method = RequestMethod.POST)
  public String aggiungiProgettoFormativo(@ModelAttribute("progettoFormativoForm")
                                          ProgettoFormativoForm progettoFormativoForm, 
                                          Model model,
                                          BindingResult result,
                                          RedirectAttributes redirectAttributes) {
    
    //Validator
    // Controlla la validità del form ricevuto come parametro e salva il risultato in result
    formValidator.validate(progettoFormativoForm, result);
    
    // Redirigi l'utente alla pagina del form se sono stati rilevati degli errori, altrimenti
    // istanzia un oggetto azienda e richiedine il salvataggio
    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.progettoFormativoForm",
                             result);
      redirectAttributes.addFlashAttribute("progettoFormativoForm", progettoFormativoForm);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.convenzioni.richiestaNonValida");
      //non sapevo cosa mettere :)))
      return "redirect:/";
    }
    
    //Instanzia un nuovo oggetto ProgettoFormativo. Redirigi verso errore nel caso
    //qualcosa vada storto
    ProgettoFormativo progettoFormativo = new ProgettoFormativo();
    progettoFormativo.setNome(progettoFormativoForm.getNome());
    progettoFormativo.setDescrizione(progettoFormativoForm.getDescrizione());
    
    
    try {
      progettoFormativoService.aggiungiProgettoFormativo(progettoFormativo);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.progettoFormativo.aggiunto");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }
    
    return "redirect:/azienda/progettiFormativi";
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
  @RequestMapping( value = "/azienda/progetti/archivia", method = RequestMethod.POST)
  public String archiviaProgettoFormativo(RedirectAttributes redirectAttributes,
                                          @RequestParam Long idProgetto) {
    // Un progetto può essere archiviato solo dal delegato aziendale
    if (!(AutenticazioneHolder.getUtente() instanceof DelegatoAziendale)) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    try {
      progettoFormativoService.archiviaProgettoFormativo(idProgetto);
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.progettiFormativi.progettoAggiunto");
    } catch (IdProgettoFormativoInesistenteException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                          "toast.progettiFormativi.idNonValido");
    } catch (NomeProgettoNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.progettiFormativi.nomeNonValido");
    } catch (DescrizioneNonValidaException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.progettiFormativi.descrizioneNonValida");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }

    return "redirect:/azienda/progettiFormativi";
  }
}
