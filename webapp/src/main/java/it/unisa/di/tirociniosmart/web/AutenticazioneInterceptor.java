package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.utenza.AutenticazioneHolder;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor che si occupa di trasferire l'utente dalla sessione HTTP
 * all'{@link AutenticazioneHolder}.
 * 
 * @see UtenteRegistrato
 * @see AutenticazioneHolder
 */
public class AutenticazioneInterceptor implements HandlerInterceptor {
  
  private UtenteRegistrato utente;
  
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
    HttpSession session = request.getSession();
    
    utente = (UtenteRegistrato) session.getAttribute("utente");
   
    if (utente != null) {
      AutenticazioneHolder.setUtente(utente);
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
    request.setAttribute("loginForm", new LoginForm());
    request.setAttribute("utente", utente);
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
    // Previeni memory leak
    AutenticazioneHolder.setUtente(null);
  }

}
