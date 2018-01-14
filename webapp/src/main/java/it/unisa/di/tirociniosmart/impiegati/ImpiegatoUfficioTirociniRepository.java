package it.unisa.di.tirociniosmart.impiegati;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative ad un Impiegato dell'Ufficio Tirocini.
 * 
 * @see ImpiegatoUfficioTirocini
 */
@Repository
public interface ImpiegatoUfficioTirociniRepository extends
    JpaRepository<ImpiegatoUfficioTirocini, String> {

  /**
   * Permette di ottenere un impiegato di segreteria a partire dalle proprie credenziali di accesso.
   * 
   * @param password Stringa che rappresenta la password dell'impiegato
   * @param username Stringa che rappresenta il nome utente dell'impiegato     
   * 
   * @return  Oggetto {@link ImpiegatoUfficioTirocinio} che rappresenta l'impiegato 
   *          dell'Ufficio Tirocini. <b>Può essere null</b> se nel database non è presente un 
   *          impiegato con username e password passati come parametro
   * 
   * @pre username != null && password != null
   */
  ImpiegatoUfficioTirocini findByUsernameAndPassword(String username, String password);
  
  /**
   * Permette di ottenere un impiegato dell'Ufficio Tirocini a partire dal proprio nome utente.
   * 
   * @param username Stringa che rappresenta il nome utente dell'impiegato di segreteria     
   * 
   * @return Oggetto {@link ImpiegatoUfficioTirocini} che rappresenta l'impiegato 
   *         dell'Ufficio Tirocini. <b>Può essere null</b> se nel database non è presente 
   *         un impiegato con l'username passata come parametro
   *         
   * @pre username != null
   */
  ImpiegatoUfficioTirocini findByUsername(String username);
}
