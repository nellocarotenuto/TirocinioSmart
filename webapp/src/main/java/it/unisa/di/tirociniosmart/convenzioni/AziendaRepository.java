package it.unisa.di.tirociniosmart.convenzioni;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative alle Aziende.
 * 
 * @see Azienda
 */
@Repository
public interface AziendaRepository extends 
    JpaRepository<Azienda, String> {
  
  /**
  * Permette di ottenere l'elenco delle aziende salvate nel Database.
  * 
  * @return lista di {@link azienda} che rappresenta la lista delle aziende
  */
  List<Azienda> findAll();

  /**
  * Permette di ottenere un'azienda a partire dal suo identificatore.
  * 
  * @param id Stringa che rappresenta l'identificativo dell'azienda
  *           
  * @return {@link Azienda} che rappresenta l'azienda.
  * 
  * @pre id != null 
  */
  Azienda findById(String id);

  /**
  * Permette di ottenere l'elenco delle aziende con o senza barriere architettoniche.
  * 
  * @param senzaBarriere Boolean che permette di ricercare aziende con o senza barriere 
  *                      architettoniche
  *           
  * @return lista di {@link Azienda} che rappresenta la lista delle 
  *                                  aziende ricercate                                       
  */
  List<Azienda> findAllBySenzaBarriere(boolean senzaBarriere);

  /**
  * Permette di verificare se un'Azienda esiste nel database attraverso il 
  * proprio identificatore e la propria partita IVA.
  * 
  * @param id Stringa che rappresenta l'identificativo dell'Azienda
  *           
  * @param partitaIva Stringa che rappresenta la partita IVA dell'Azienda
  *               
  * @return true se l'azienda esiste nel database,
  *         false se l'azienda non esiste nel database
  *         
  * @pre id != null && partitaIva != null
  */
  boolean existsByIdOrPartitaIva(String id, String partitaIva);

}

