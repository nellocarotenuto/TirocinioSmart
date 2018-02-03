package it.unisa.di.tirociniosmart.progettiformativi;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative ai Progetti Formativi offerti da un'azienda.
 * 
 * @see ProgettoFormativo
 */
public interface ProgettoFormativoRepository extends JpaRepository<ProgettoFormativo, Long> {

  /**
   * Permette di ottenere un progetto formativo a partire dal suo identificatore.
   * 
   * @param id Numero di tipo long che rappresenta l'identificativo della domanda di
   *           tirocinio
   *           
   * @return Oggetto {@link ProgettoFormativo} che rappresenta il progetto formativo. <b>Può
   *         essere null</b> se nel database non è presente un progetto formativo 
   *         con l'id passato come parametro
   * 
   * @pre id > 0
   * 
   */
  ProgettoFormativo findById(long id);
  
  /**
   * Permette di ottenere l'elenco dei progetti formativi di una determina azienda e con determinato
   * status.
   * 
   * @param idAzienda Stringa che rappresenta l'id dell'azienda 
   * 
   * @param status int che rapressenta lo status del progetto
   *           
   * @return Lista di {@link ProgettoFormativo} che rappresenta la lista dei progetti formativi
   *                                         
   * @pre idAzienda != null
   *
   * @pre status == {@link ProgettoFormativo#ATTIVO} or 
   *      status == {@link ProgettoFormativo#ARCHIVIATO}
   * 
   */
  List<ProgettoFormativo> findAllByStatusAndAziendaId(int status, String idAzienda);
  
  /**
   * Permette di verificare se un progetto formativo esiste nel database 
   * attraverso il proprio identificatore.
   * 
   * @param id long che rappresenta l'identificativo del progetto formativo
   *               
   * @return true se il progetto esiste nel database,
   *         false se il progetto non esiste nel database
   *         
   * @pre id > 0
   */
  boolean existsById(long id);
}
