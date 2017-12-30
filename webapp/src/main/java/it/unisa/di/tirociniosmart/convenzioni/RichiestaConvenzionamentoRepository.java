package it.unisa.di.tirociniosmart.convenzioni;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe che definisce le operazioni relative alla persistenza delle richieste di convenzionamento.
 * 
 * @see RichiestaConvenzionamento
 */
@Repository
public interface RichiestaConvenzionamentoRepository
       extends JpaRepository<RichiestaConvenzionamento, Long> {
  
  /**
   * Permette di ottenere l'elenco delle richieste di convenzionamento salvate nel database.
   * 
   * @return Lista di {@link RichiestaConvenzionamento} che rappresenta la lista delle richieste 
   *         di convenzionamento
   */
  List<RichiestaConvenzionamento> findAll();
  
  /**
   * Permette di ottenere una richiesta di convenzionamento a partire dal suo identificatore.
   * 
   * @param id Numero di tipo long che rappresenta l'identificativo della richiesta di
   *           convenzionamento
   *           
   * @return Oggetto {@link RichiestaConvenzionamento} che rappresenta la richiesta 
   *         di convenzionamento
   *         <b>Può essere null</b> se nel database non è presente una richiesta di convenzionamento
   *         con l'id specificato come parametro
   * 
   * @pre id > 0
   */
  RichiestaConvenzionamento findById(long id);
  
  /**
   * Permette di ottenere l'elenco delle richieste di convenzionamento con un determinato status.
   * 
   * @param status Intero che rappresenta lo stato assegnato alla richiesta di
   *               convenzionamento
   *           
   * @return Lista di {@link RichiestaConvenzionamento} che rappresenta la lista delle richieste 
   *         di convenzionamento
   *                                         
   * @pre status = {@link RichiestaConvenzionamento#IN_ATTESA} or
   *      status = {@link RichiestaConvenzionamento#APPROVATA} or
   *      status = {@link RichiestaConvenzionamento#RIFIUTATA}
   */
  List<RichiestaConvenzionamento> findAllByStatus(int status);
  
  /**
   * Permette di verificare se una richiesta di convenzionamento esiste nel database attraverso il 
   * proprio identificatore.
   * 
   * @param id Numero di tipo long che rappresenta l'identificativo della richiesta di
   *           convenzionamento
   *               
   * @return true se la richiesta di convenzionamento esiste,
   *         false se la richiesta di convenzionamento non esiste
   *         
   * @pre id > 0
   */
  boolean existsById(long id);
  

}
