package it.unisa.di.tirociniosmart.convenzioni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative ad un Delegato Aziendale.
 * 
 * @see DelegatoAziendale
 */
@Repository
public interface DelegatoAziendaleRepository extends JpaRepository<DelegatoAziendale, String> {

  /**
   * Permette di ottenere un delegato aziendale a partire dalle proprie credenziali di accesso.
   * 
   * @param password Stringa che rappresenta la password del delegato aziendale
   * @param username Stringa che rappresenta il nome utente del delegato aziendale     
   * 
   * @return  Oggetto {@link DelegatoAziendale} che rappresenta il delegato aziendale
   *          <b>Può essere null</b> se nel database non è presente un delegato aziendale 
   *          con username e password passati come parametro
   * 
   * @pre username != null && password != null
   */
  DelegatoAziendale findByUsernameAndPassword(String username, String password);
  
  /**
   * Permette di ottenere un delegato aziendale a partire dal proprio nome utente.
   * 
   * @param username Stringa che rappresenta il nome utente del delegato aziendale     
   * 
   * @return Oggetto {@link DelegatoAziendale} che rappresenta il delegato aziendale
   * <b>     Può essere null</b> se nel database non è presente un delegato aziendale 
   *         con username e password passati come parametro
   * @pre username != null
   */
  DelegatoAziendale findByUsername(String username);
 
}
