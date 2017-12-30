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
   *          essere null</b> se nel database non è presente un progetto formativo 
   *          con l'id passato come parametro
   * 
   * @pre id > 0
   * 
   */
  ProgettoFormativo findById(long id);
  
  /**
   * Permette di ottenere l'elenco dei progetti formativi di una determina azienda.
   * 
   * @param idAzienda Stringa che rappresenta l'id dell'azienda 
   *           
   * @return Lista di {@link ProgettoFormativo} che rappresenta la lista dei progetti formativi
   *                                         
   * @pre idAzienda != null
   * 
   */
  List<ProgettoFormativo> findAllByAziendaId(String idAzienda);
}
