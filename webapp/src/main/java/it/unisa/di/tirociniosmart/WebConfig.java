package it.unisa.di.tirociniosmart;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import it.unisa.di.tirociniosmart.web.AutenticazioneManager;

/**
 * Classe che definisce la configurazione della webapp in termini di Bean disponibili a livello di
 * sistema.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  /**
   * Definisce il message source di sistema per la risoluzione dei messaggi.
   * 
   * @return L'oggetto MessageSource che definisce classpath e codifica dei messaggi
   */
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
    source.setBasenames("classpath:messages");
    source.setUseCodeAsDefaultMessage(true);
    source.setDefaultEncoding("ISO-8859-1");
    source.setCacheSeconds(0);
    return source;
  }
  
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
  
  /**
   * Mappa le richieste per le risorse statiche alla locazione appropriata.
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/resources/**")
        .addResourceLocations("/resources/"); 
  }
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new AutenticazioneManager());
  }
  
}
