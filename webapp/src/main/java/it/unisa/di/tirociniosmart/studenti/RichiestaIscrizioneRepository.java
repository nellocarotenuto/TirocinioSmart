package it.unisa.di.tirociniosmart.studenti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative alle richieste d'iscrizione alla piattaforma.
 * 
 * @see RichiestaIscrizione
 */
@Repository
public interface RichiestaIscrizioneRepository extends JpaRepository<RichiestaIscrizione, Long> {

  /**
   * Permette di ottenere l'elenco delle richieste d'iscrizione aventi un determinato 
   * status assegnato.
   * 
   * @param status Intero che rappresenta lo stato assegnato alla richiesta d'iscrizione
   *           
   * @return Lista di {@link RichiestaIscrizione} che rappresenta la lista delle richieste
   *         d'iscrizione
   *                                         
   * @pre status = {@link RichiestaIscrizione#IN_ATTESA} or
   *      status = {@link RichiestaIscrizione#APPROVATA} or
   *      status = {@link RichiestaIscrizione#RIFIUTATA} 
   *      
   */
  List<RichiestaIscrizione> findAllByStatus(int status);
  
  /**
   * Permette di ottenere una richiesta d'iscrizione a partire dal suo identificatore.
   * 
   * @param id Numero di tipo long che rappresenta l'identificatore della richiesta di
   *           iscrizione
   *           
   * @return Oggetto {@link RichiestaIscrizione} che rappresenta la richiesta d'iscrizione. <b>Può
   *         essere null</b> se nel database non è presente una richiesta d'iscrizione 
   *         con l'id passato come parametro
   * 
   * @pre id > 0
   * 
   */
  RichiestaIscrizione findById(long id);
}
