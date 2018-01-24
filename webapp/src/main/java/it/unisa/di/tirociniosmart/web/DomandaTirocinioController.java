package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.domandetirocinio.CommentoDomandaTirocinioNonValidoException;

import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinioGestitaException;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandeTirocinioService;
import it.unisa.di.tirociniosmart.domandetirocinio.IdDomandaTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.domandetirocinio.StatoDomandaNonIdoneoException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.progettiformativi.IdProgettoFormativoInesistenteException;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettiFormativiService;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
  
  @Autowired
  private UtenzaService utenzaService;
 
  /**
   * A seconda dell'utente autenticato nel sistema, restituisce la lista delle domande associategli:
   * per gli studenti, la lista contiene le domande non ancora approvate, per le aziende la lista
   * delle domande pervenute all'azienda e per gli impiegati la lista delle domande accettate dalle
   * aziende.
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
  @RequestMapping(value = "/dashboard/domande/ricevute", method = RequestMethod.GET)
  public String visualizzaElencoDomande(RedirectAttributes redirectAttributes, Model model) {
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
    try {
      List<DomandaTirocinio> domande = domandeService.elencaDomandeRicevute();
      model.addAttribute("elencoDomandeTirocinio", domande);
    } catch (RichiestaNonAutorizzataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    if (utente instanceof ImpiegatoUfficioTirocini) {
      return "domande-ricevute-ufficio";
    } else if (utente instanceof DelegatoAziendale) {
      return "domande-ricevute-azienda";
    }
    
    return "redirect:/errore";
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
  @RequestMapping(value = "/dashboard/domande/invia", method = RequestMethod.POST)
  public String inviaDomandaTirocinio(DomandaTirocinioForm domandaTirocinioForm,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {    
    // Controlla la validità del form ricevuto come parametro e salva il risultato in result
    formValidator.validate(domandaTirocinioForm, result);
    
    // Redirigi l'utente alla pagina del form se sono stati rilevati degli errori
    if (result.hasErrors()) {
      redirectAttributes
          .addFlashAttribute("org.springframework.validation.BindingResult.domandaTirocinioForm-"
                                                     + domandaTirocinioForm.getPosizione(), result);
      redirectAttributes.addFlashAttribute("domandaTirocinioForm-"
                                       + domandaTirocinioForm.getPosizione(), domandaTirocinioForm);
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.domandaNonValida");

      long idProgetto = domandaTirocinioForm.getIdProgettoFormativo();
      
      try {
        ProgettoFormativo progettoFormativo =
                                      progettoFormativoService.ottieniProgettoFormativo(idProgetto);
        
        return "redirect:/aziende/" + progettoFormativo.getAzienda().getId();
      } catch (IdProgettoFormativoInesistenteException e) {
        return "redirect:/aziende/";
      }
    
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
    domandaTirocinio.setCommentoStudente(domandaTirocinioForm.getCommentoStudente());
    domandaTirocinio.setCfu(domandaTirocinioForm.getCfu());
    
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
      return "redirect:/dashboard/domande/inviate"; 
    } catch (RichiestaNonAutorizzataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }
    
  }
  
  /**
   * Elabora le domande di tirocinio effettuandone l'accettazione.
   * 
   * @param idDomanda Long che indica la domanda da accettare
   *  
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   * 
   * @return Stringa indicante l'URL della pagina da mostrare (tramite redirect) 
   *                 in caso di successo,
   *         Stringa indicante l'URL della pagina da mostrare (tramite redirect)
   *                 in caso di insuccesso
   */
  @RequestMapping(value = "/dashboard/domande/accetta", method = RequestMethod.POST)
  public String accettaDomandaTirocinio(@RequestParam Long idDomanda,
                                        @RequestParam String commentoAzienda,
                                        RedirectAttributes redirectAttributes) {
    
    try {
      domandeService.accettaDomandaTirocinio(idDomanda, commentoAzienda);
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.domandaAccettata");
    } catch (IdDomandaTirocinioNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.idNonValido");
    } catch (DomandaTirocinioGestitaException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.gestita");
    } catch (RichiestaNonAutorizzataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }
    
    return "redirect:/dashboard/domande/ricevute";
  }
  
  /**
   * Elabora le domande di tirocinio effettuandone il rifiuto.
   * 
   * @param idDomanda long che indica la domanda da rifiutare
   * 
   * @param commentoAzienda Stringa che indica il commento da inserire per il rifiuto.
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *                           disponibili anche dopo un redirect
   * 
   * @return Stringa indicante l'URL della pagina da mostrare (tramite redirect) 
   *                 in caso di successo,
   *         Stringa indicante l'URL della pagina da mostrare (tramite redirect)
   *                 in caso di insuccesso
   */
  @RequestMapping(value = "/dashboard/domande/rifiuta", method = RequestMethod.POST)
  public String rifiutaDomandaTirocinio(@RequestParam Long idDomanda,
                                        @RequestParam String commentoAzienda,
                                        RedirectAttributes redirectAttributes) {
    
    try {
      domandeService.rifiutaDomandaTirocinio(idDomanda, commentoAzienda);
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.domandaTirocinio.domandaRifiutata");
    } catch (IdDomandaTirocinioNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.idNonValido");
    } catch (DomandaTirocinioGestitaException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                        "toast.domandaTirocinio.gestita");
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.commentoNonValido");
    } catch (RichiestaNonAutorizzataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }
    
    return "redirect:/dashboard/domande/ricevute";
  }
  
  /**
   * Elabora le domande di tirocinio effettuandone l'approvazione.
   * 
   * @param idDomanda long che indica la domanda da approvare
   * 
   * @param redirectAttributes incapsula gli attributi da salvare in sessione in modo
   *                           da renderli disponibili anche dopo il redirect
   *                           
   * @return Stringa indicante l'URL della pagina da mostrare (tramite redirect) 
   *                 in caso di successo,
   *         Stringa indicante l'URL della pagina da mostrare (tramite redirect)
   *                 in caso di insuccesso
   */
  @RequestMapping(value = "/dashboard/domande/approva", method = RequestMethod.POST)
  public String approvaDomandaTirocinio(@RequestParam Long idDomanda,
                                        RedirectAttributes redirectAttributes) {
    
    try {
      domandeService.approvaDomandaTirocinio(idDomanda);
      redirectAttributes.addFlashAttribute("testoNotifica",   
                                           "toast.domandaTirocinio.domandaApprovata");
    } catch (IdDomandaTirocinioNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.idNonValido");
    } catch (StatoDomandaNonIdoneoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",  
                                           "toast.domandaTirocinio.StatoDomandaNonIdoneo");
    } catch (RichiestaNonAutorizzataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }
    
    return "redirect:/dashboard/domande/ricevute";
  }
  
  /**
   * Elabora le domande di tirocinio effettuandone il rifiuto.
   * 
   * @param idDomanda Long che rappresenta l'identificatore della domanda
   * 
   * @param commentoImpiegato Stringa che rappresenta il commento dell'impiegato dell'ufficio
   *        tirocini (motivazione del rifiuto)
   *        
   * @param redirectAttributes incapsula gli attributi da salvare in sessione in modo
   *        da renderli disponibili anche dopo il redirect
   *                           
   * @return Stringa indicante l'URL della pagina da mostrare (tramite redirect) in caso di
   *         successo, Stringa indicante l'URL della pagina da mostrare (tramite redirect)
   *         in caso di insuccesso
   */
  @RequestMapping(value = "/dashboard/domande/respingi", method = RequestMethod.POST)
  public String respingiDomandaTirocinio(@RequestParam Long idDomanda,
                                         @RequestParam String commentoImpiegato,
                                         RedirectAttributes redirectAttributes) {
    try {
      domandeService.respingiDomandaTirocinio(idDomanda, commentoImpiegato);
      redirectAttributes.addFlashAttribute("testoNotifica",   
                                           "toast.domandaTirocinio.domandaRespinta");
    } catch (IdDomandaTirocinioNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.idNonValido");
    } catch (StatoDomandaNonIdoneoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",  
                                           "toast.domandaTirocinio.StatoDomandaNonIdoneo");
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.domandaTirocinio.commentoNonValido");
    } catch (RichiestaNonAutorizzataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return "redirect:/errore";
    }
    
    return "redirect:/dashboard/domande/ricevute";
  }
  
  /**
   * Permette di visualizzare l'elenco delle domande inviate dall'utente autenticato.
   * 
   * @param model Incapsula gli attributi di richiesta. <b>Iniettato dalla dispatcher servlet</b>
   * 
   * @param redirectAttributes incapsula gli attributi da salvare in sessione in modo
   *        da renderli disponibili anche dopo il redirect 
   *        
   * @return Stringa indicante l'URL della pagina da mostrare (tramite redirect) in caso di
   *         successo, Stringa indicante l'URL della pagina da mostrare (tramite redirect)
   *         in caso di insuccesso
   */
  @RequestMapping(value = "/dashboard/domande/inviate", method = RequestMethod.GET)
  public String visualizzaElencoDomandeInviate(Model model, RedirectAttributes redirectAttributes) {
    try {
      List<DomandaTirocinio> domandeInviate = domandeService.elencaDomandeInviate();
      model.addAttribute("elencoDomandeInviate", domandeInviate);
    } catch (RichiestaNonAutorizzataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    if (utenzaService.getUtenteAutenticato() instanceof DelegatoAziendale) {
      return "domande-inviate-azienda";
    } else if (utenzaService.getUtenteAutenticato() instanceof Studente) {
      return "domande-inviate-studente";
    } else {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
  }
  
  
  /**
   * Permette di visualizzare l'elenco dei tirocini in corso a seconda dell'utente autenticato.
   * 
   * @param model Incapsula gli attributi di richiesta. <b>Iniettato dalla dispatcher servlet</b>
   * 
   * @param redirectAttributes incapsula gli attributi da salvare in sessione in modo
   *        da renderli disponibili anche dopo il redirect 
   *        
   * @return Stringa indicante l'URL della pagina da mostrare (tramite redirect) in caso di
   *         successo, Stringa indicante l'URL della pagina da mostrare (tramite redirect)
   *         in caso di insuccesso
   */
  @RequestMapping(value = "/dashboard/tirocini", method = RequestMethod.GET)
  public String visualizzaElencoTirociniInCorso(Model model,
                                                RedirectAttributes redirectAttributes) {
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    List<DomandaTirocinio> tirociniInCorso;
    
    try {
      tirociniInCorso = domandeService.elencaTirociniInCorso();
      model.addAttribute("tirociniInCorso", tirociniInCorso);
    } catch (RichiestaNonAutorizzataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    if (utente instanceof ImpiegatoUfficioTirocini) {
      return "tirocini-in-corso-ufficio";
    } else if (utente instanceof DelegatoAziendale) {
      return "tirocini-in-corso-azienda";
    } else if (utente instanceof Studente) {
      return "tirocini-in-corso-studente";
    } else {
      redirectAttributes.addFlashAttribute("testoNotifica", 
                                           "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
  }
  
}
