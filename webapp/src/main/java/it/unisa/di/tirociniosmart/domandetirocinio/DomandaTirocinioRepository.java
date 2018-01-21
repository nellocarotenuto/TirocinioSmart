package it.unisa.di.tirociniosmart.domandetirocinio;

import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.studenti.Studente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative alle domande di tirocinio.
 * 
 * @see DomandaTirocinio
 */
@Repository
public interface DomandaTirocinioRepository
    extends JpaRepository<DomandaTirocinio, Long> {

  /**
   * Permette di ottenere l'elenco delle domande di tirocinio con un determinato status.
   * 
   * @param status Intero che rappresenta lo stato assegnato alla domanda di tirocinio
   *           
   * @return Lista di {@link DomandaTirocinio} che rappresenta la lista delle domande 
   *         di tirocinio
   *                                         
   * @pre status = {@link DomandaTirocinio#IN_ATTESA} or
   *      status = {@link DomandaTirocinio#ACCETTATA} or
   *      status = {@link DomandaTirocinio#RIFIUTATA} or
   *      status = {@link DomandaTirocinio#APPROVATA} or
   *      status = {@link DomandaTirocinio#RESPINTA}
   */
  List<DomandaTirocinio> findAllByStatus(int status);
  
  /**
   * Permette di ottenere una domanda di tirocinio a partire dal suo identificatore.
   * 
   * @param id Numero di tipo long che rappresenta l'identificativo della domanda di
   *           tirocinio
   *           
   * @return Oggetto {@link DomandaTirocinio} che rappresenta la domanda di tirocinio. <b>Può
   *          essere null</b> se nel database non è presente una domanda di tirocinio 
   *          con l'id passato come parametro
   * 
   * @pre id > 0
   * 
   */
  DomandaTirocinio findById(long id);
  
  /**
   * Permette di ottenere l'elenco delle domande di tirocinio inviate da un 
   * determinato studente.
   * 
   * @param studente Oggetto {@link Studente} che rappresenta lo studente assegnato alla domanda di 
   *                 tirocinio
   *           
   * @return Lista di {@link DomandaTirocinio} che rappresenta la lista delle domande 
   *         di tirocinio
   *                                         
   * @pre studente != null
   * 
   */
  List<DomandaTirocinio> findAllByStudente(Studente studente);
  
  /**
   * Permette di ottenere l'elenco delle domande di tirocinio inviate con un determinato stato
   * giunte ad una determinata azienda.
   * 
   * @param status int che rappresenta lo stato assegnato alla domanda di  tirocinio
   *     
   * @param idAzienda Striga che rappresenta l'id dell'azienda
   *           
   * @return Lista di {@link DomandaTirocinio} che rappresenta la lista delle domande 
   *         di tirocinio
   *                                         
   * @pre int > 0
   * @pre idAzienda != null
   * 
   */
  List<DomandaTirocinio> findAllByIdAziendaAndStatus(int status, String idAzienda);
  
  /**
   * Permette di ottenere l'elenco delle domande di tirocinio a partire dal progetto formativo 
   * selezionato.
   * 
   * @param progettoFormativo Oggetto {@link ProgettoFormativo} che rappresenta il progetto 
   *                          formativo assegnato alla domanda di tirocinio
   *           
   * @return Lista di {@link DomandaTirocinio} che rappresenta la lista delle domande 
   *         di tirocinio
   *                                         
   * @pre progettoFormativo != null
   * 
   */
  List<DomandaTirocinio> findAllByProgettoFormativo(ProgettoFormativo progettoFormativo);
}
