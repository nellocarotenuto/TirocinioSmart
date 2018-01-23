package it.unisa.di.tirociniosmart.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller che espone via web i servizi generici relativi alle registrazioni.
 */
@Controller
public class RegistrazioneController {

  /**
   * Mostra i form di registrazione per studenti e aziende.
   * 
   * @param model
   * 
   * @return
   */
  @RequestMapping(value = "/registrazione", method = RequestMethod.GET)
  public String mostraForm(Model model) {
    if (!model.containsAttribute("convenzionamentoForm")) {
      model.addAttribute(new ConvenzionamentoForm());
    }
   
    if (!model.containsAttribute("richiestaIscrizioneForm")) {
      model.addAttribute(new RichiestaIscrizioneForm());
    }
    
    return "registrazione";
  }
}
