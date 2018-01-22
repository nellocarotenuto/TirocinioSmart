package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandeTirocinioService;
import it.unisa.di.tirociniosmart.progettiformativi.IdProgettoFormativoInesistenteException;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettiFormativiService;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller che espone via web i servizi relativi alle domande di tirocinio.
 * 
 * @see DomandeTirocinioService
 */
@Controller
public class DomandaTirocinioController {
  
  private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
  
  @Autowired
  private DomandeTirocinioService domandeService;
  
  @Autowired
  private ProgettiFormativiService progettoFormativoService;
  
  @Autowired
  private DomandaTirocinioFormValidator formValidator;
 
  /**
   * Fornisce l'elenco delle domande di tirocinio in ATTESA per uno studente, o non ancora gestite 
   * dall'ufficio tirocini, o non ancora gestite da un delegato aziendale.
   *
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   *
   * @param model Incapsula gli attributi da passare alla pagina delegata alla presentazione
   * 
   * @return Stringa indicante la vista delegata alla presentazione della lista se l'utente è
   *         autorizzato
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che tenta di visualizzare l'elenco delle 
   *         domande di tirocinio non è autorizzato a svolgere l'operazione
   */
  @RequestMapping(value = "/dashboard/domande", method = RequestMethod.GET)
  public String visualizzaElencoDomande(RedirectAttributes redirectAttributes, Model model) 
      throws RichiestaNonAutorizzataException {
    
    List<DomandaTirocinio> domande = domandeService.elencaDomandeTirocinio();
    model.addAttribute("elencoDomandeTirocinio", domande);
    
    return "pages/domandeTirocinio";
  }
  
  /**
   * Elabora le domande di tirocinio richidendone la validazione ed effettuandone
   * eventualmente la registrazione.
   * 
   * 
   * @param domandaTirocinioForm {@link domandaTirocinioForm} che incapsula gli input utente.
   *        <b>Iniettato dalla dispatcher servlet </b>
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
  
   
  @RequestMapping(value = "/dasboard/domande/invia", method = RequestMethod.POST)
  public String inviaDomandaTirocinio(@ModelAttribute("domandaTirocinioForm")
                                      DomandaTirocinioForm domandaTirocinioForm,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {
    
    // Controlla la validità del form ricevuto come parametro e salva il risultato in result
    formValidator.validate(domandaTirocinioForm, result);
    
    // Redirigi l'utente alla pagina del form se sono stati rilevati degli errori
    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.domandaTirocinioForm",
                             result);
      redirectAttributes.addFlashAttribute("domandaTirocinioForm", domandaTirocinioForm);
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.domandaNonValida");

      return "redirect:/dashboard/domande";
    }
    
    LocalDate dataInizio = LocalDate.of(domandaTirocinioForm.getAnnoInizio(),
                                        domandaTirocinioForm.getMeseInizio(),
                                        domandaTirocinioForm.getGiornoInizio());
    
    LocalDate dataFine = LocalDate.of(domandaTirocinioForm.getAnnoFine(),
                                      domandaTirocinioForm.getMeseFine(),
                                      domandaTirocinioForm.getGiornoFine());
    
    
    //Instanzia un nuovo oggetto DomandaTirocinio
    DomandaTirocinio domandaTirocinio = new DomandaTirocinio();
    domandaTirocinio.setInizioTirocinio(dataInizio);
    domandaTirocinio.setFineTirocinio(dataFine);
    domandaTirocinio.setCommentoStudente(domandaTirocinio.getCommentoStudente());
    domandaTirocinio.setCfu(domandaTirocinio.getCfu());
   
    try {
      domandaTirocinio.setProgettoFormativo(progettoFormativoService.ottieniProgettoFormativo(
                                            domandaTirocinioForm.getIdProgettoFormativo()));
    } catch (IdProgettoFormativoInesistenteException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.progettiFormativi.idNonValido");
      return "redirect:/";
    }
    
    try {
      domandeService.registraDomandaTirocinio(domandaTirocinio);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.domandaTirocinio.inviata");
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
  
}
