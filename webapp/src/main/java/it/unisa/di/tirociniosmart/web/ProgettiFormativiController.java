package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.ConvenzioniService;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaNonValidoException;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettiFormativiService;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  private ProgettiFormativiService progettiFormativiService;
  
  @Autowired
  private ConvenzioniService convenzioniService;
  
  
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
  
}
