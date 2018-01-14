package it.unisa.di.tirociniosmart;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Classe che definisce il caricamento delle configurazioni nel caso di build war non eseguibile.
 * Garantisce il corretto funzionamento dell'applicazione anche per il tradizionale deploy
 * su applications server esterni.
 * 
 * @author Nello Carotenuto
 */
public class ServletInitializer extends SpringBootServletInitializer {

  /**
   * Metodo per il caricamento delle configurazioni.
   * Visto l'utilizzo di Spring Boot e di {@link Webapp EnableAutoConfiguration}, non è necessario
   * che il programmatore vi apporti modifiche.
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Webapp.class);
  }

}
