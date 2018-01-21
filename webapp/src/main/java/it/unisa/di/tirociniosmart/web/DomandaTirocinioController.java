package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.convenzioni.ConvenzioniService;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandeTirocinioService;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  
  @Autowired
  private DomandeTirocinioService domandeService;
 
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
  @RequestMapping(value = "/dashboard/domande/", method = RequestMethod.GET)
  public String visualizzaElencoDomande(RedirectAttributes redirectAttributes, Model model) 
      throws RichiestaNonAutorizzataException {
    
    List<DomandaTirocinio> domande = domandeService.elencaDomandeTirocinio();
    model.addAttribute("elencoDomandeTirocinio", domande);
    
    return "pages/domandeTirocinio";
  }
}
