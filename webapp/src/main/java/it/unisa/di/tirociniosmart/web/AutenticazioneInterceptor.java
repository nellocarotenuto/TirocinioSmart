package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.utenza.AutenticazioneHolder;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor che si occupa di trasferire l'username dell'utente autenticato dalla sessione HTTP
 * all'{@link AutenticazioneHolder}.
 * 
 * @see UtenteRegistrato
 * @see AutenticazioneHolder
 */
public class AutenticazioneInterceptor implements HandlerInterceptor {

  @Autowired
  private UtenzaService utenzaService;
  
  /**
   * Eseguito prima dell'esecuzione di un controller, determina se l'utente è in sessione, quindi
   * istanzia un oggetto {@link AutenticazioneHolder} contenente le informazioni circa l'utente
   * autenticato nel sistema (null altrimenti).
   */
  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler)
         throws Exception {
    // Ottieni la sessione del server
    HttpSession session = request.getSession();
    
    // Ottieni l'username dell'utente autenticato e propaga l'informazione anche a livello più basso
    String username = (String) session.getAttribute("username");
   
    if (username != null) {
      utenzaService.setUtenteAutenticato(username);
    }
    
    return true;
  }

  /**
   * Metodo eseguito dopo l'esecuzione di un controller ma prima del rendering della vista.
   */
  @Override
  public void postHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler,
                         ModelAndView modelAndView)
         throws Exception {
    // Fornisci ad ogni vista delegata un form per il login e l'oggetto utente che rappresenta
    // l'utente autenticato
    request.setAttribute("loginForm", new LoginForm());
    request.setAttribute("utente", utenzaService.getUtenteAutenticato());
  }

  /**
   * Metodo eseguito dopo il completamento della gestione della richiesta.
   */
  @Override
  public void afterCompletion(HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler,
                              Exception exception)
         throws Exception {
    // Previeni memory leak al termine dell'elaborazione della richiesta
    utenzaService.setUtenteAutenticato(null);
  }

}
