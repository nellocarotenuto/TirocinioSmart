package it.unisa.di.tirociniosmart.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe che espone il metodo main necessario per avviare l'applicazione con l'embedded server,
 * senza quindi la necessità di costruire un war e farne il deploy su un server esterno.
 * 
 * @author Nello Carotenuto
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Webapp {

  /**
   * Metodo necessario a Spring Boot per l'avvio dell'applicazione nel caso in cui si costruisca
   * un jar/war eseguibile con server embedded.
   * 
   * @param args classici parametri di inizializzazione
   */
  public static void main(String[] args) {
    SpringApplication.run(Webapp.class, args);
  }
  
}
