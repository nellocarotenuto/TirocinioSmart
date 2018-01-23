package it.unisa.di.tirociniosmart.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller che espone via web la home page del sito.
 */
@Controller
public class HomeController {

  /**
   * Delega la vista dedicata alla presentazione della home page del sito.
   * 
   * @param model Contenitore degli attributi <b>iniettato dalla dispatcher servlet</b>
   * 
   * @return La stringa che rappresenta il nome della vista dedicata alla realizzazione della home
   *         page
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String mostraHomePage(Model model) {
    return "home";
  }
  
}
