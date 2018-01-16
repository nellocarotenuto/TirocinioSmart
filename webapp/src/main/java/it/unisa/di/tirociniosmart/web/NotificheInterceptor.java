package it.unisa.di.tirociniosmart.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Interceptor che si occupa di trasferire rendere i testi di notifica visibili direttamente alle
 * pagine di presentazione, evitando il problema del consumo del controller.
 */
public class NotificheInterceptor implements HandlerInterceptor {
  
  private String testoNotifica;
  
  /**
   * Eseguito prima dell'esecuzione di un controller, determina se è presente una notifica e la
   * salva nell'istanza per renderla disponibile in postHandle.
   */
  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler)
         throws Exception {

    RequestContextUtils.getInputFlashMap(request);
    if (RequestContextUtils.getInputFlashMap(request) != null) {
      testoNotifica = (String) RequestContextUtils.getInputFlashMap(request).get("testoNotifica");
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
    modelAndView.addObject("testoNotifica", testoNotifica);
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
    testoNotifica = null;
  }

}