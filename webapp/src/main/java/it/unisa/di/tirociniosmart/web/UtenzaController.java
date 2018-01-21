package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoInAttesaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoRifiutataException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneInAttesaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneRifiutataException;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.utenza.AutenticazioneHolder;
import it.unisa.di.tirociniosmart.utenza.CredenzialiNonValideException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UtenzaController {

  @Autowired
  private UtenzaService utenzaService;

  /**
   * Elabora le richieste di autenticazione degli utenti.
   * 
   * @param session Sessione HTTP <b>iniettata dalla dispatcher servlet</b>
   * 
   * @param loginForm {@link LoginForm} che incapsula le informazioni di login
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
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(HttpSession session,
                      @ModelAttribute("loginForm") LoginForm loginForm,
                      BindingResult result,
                      RedirectAttributes redirectAttributes) {
    // Controlla che non ci sia un utente già registrato, altrimenti riportalo in home page
    // mostrandogli una notifica dell'errore
    if (AutenticazioneHolder.getUtente() != null) {
      redirectAttributes.addFlashAttribute("testoNotifica",
          "toast.login.utenteLoggato");
      return "redirect:/";
    }
    
    // Estrai username e password e prova ad effettuare il login (salvando l'utente in sessione),
    // mostra una notifica di errore sulle credenziali nel caso il login fallisca
    String username = loginForm.getUsername();
    String password = loginForm.getPassword();
    
    try {
      UtenteRegistrato utente = utenzaService.login(username, password);
      session.setAttribute("utente", utente);
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.login.loginEffettuato");
    } catch (RichiestaConvenzionamentoRifiutataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.login.richiestaConvenzionamentoRifiutata");
    } catch (RichiestaIscrizioneRifiutataException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.login.richiestaIscrizioneRifiutata");
    } catch (RichiestaConvenzionamentoInAttesaException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.login.richiestaConvenzionamentoInAttesa");
    } catch (RichiestaIscrizioneInAttesaException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.login.richiestaIscrizioneInAttesa");
    } catch (CredenzialiNonValideException e) {
      redirectAttributes.addFlashAttribute("testoNotifica",
                                           "toast.login.credenzialiNonValide");
    }
    
    return "redirect:/";
  }
  
  /**
   * Elabora le richieste di logout degli utenti.
   * 
   * @param session Sessione HTTP <b>iniettata dalla dispatcher servlet</b>
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   *        
   * @return Stringa indicante la vista delegata alla presentazione del form in caso di insuccesso,
   *         stringa indicante l'URL della home page (tramite redirect) in caso di successo
   */
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
    if (AutenticazioneHolder.getUtente() != null) {
      session.setAttribute("utente", null);
      utenzaService.logout();
      redirectAttributes.addFlashAttribute("testoNotifica", "toast.logout.logoutEffettuato");
    }
    
    return "redirect:/";
  }
  
  /**
   * Consente di visualizzare la dashboard all'utente loggato.
   * 
   * @param redirectAttributes Incapsula gli attributi da salvare in sessione per renderli
   *        disponibili anche dopo un redirect
   *        
   * @return Stringa indicante la vista delegata alla presentazione del form in caso di insuccesso,
   *         stringa indicante l'URL della home page (tramite redirect) in caso di successo
   */
  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  public String mostraDashboard(RedirectAttributes redirectAttributes) {
    if (AutenticazioneHolder.getUtente() == null) {
      redirectAttributes.addFlashAttribute("testoNotifica", 
          "toast.autorizzazioni.richiestaNonAutorizzata");
      return "redirect:/";
    }
    
    UtenteRegistrato utente = AutenticazioneHolder.getUtente();
    
    if (utente instanceof ImpiegatoUfficioTirocini) {
      return "redirect:/dashboard/domande";
    } else if (utente instanceof DelegatoAziendale) {
      return "redirect:/dashboard/tirocini";
    } else if (utente instanceof Studente) {
      return "pages/dashboard/tirocini";
    }
    
    return "redirect:/errore";
  }
  
}
