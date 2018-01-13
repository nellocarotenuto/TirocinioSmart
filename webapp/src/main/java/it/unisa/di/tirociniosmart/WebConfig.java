package it.unisa.di.tirociniosmart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Classe che definisce la configurazione della webapp in termini di Bean disponibili a livello di
 * sistema.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  /**
   * Definisce il view resolver di sistema per la mappatura delle stringhe restituite nei controller
   * in pagine JSP.
   * 
   * @return L'oggetto ViewResolver che incapsula le informazioni su come risolvere le viste
   *         delegate dai controller
   */
  @Bean
  public ViewResolver internalResourceViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setViewClass(JstlView.class);
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
  
}
