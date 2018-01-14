package it.unisa.di.tirociniosmart.studenti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative ad uno studente.
 * 
 * @see Studente
 */
@Repository
public interface StudenteRepository extends JpaRepository<Studente, String> {

  /**
   * Permette di ottenere uno studente a partire dalle proprie credenziali di accesso.
   * 
   * @param password Stringa che rappresenta la password dello studente
   * @param username Stringa che rappresenta il nome utente dello studente     
   * 
   * @return  Oggetto {@link ImpiegatoUfficioTirocinio} che rappresenta lo studente. <b>Può 
   *          essere null</b> se nel database non è presente uno studente con username 
   *          e password passati come parametro
   * 
   * @pre username != null && password != null
   */
  Studente findByUsernameAndPassword(String username, String password);
  
  /**
   * Permette di ottenere uno studente a partire dal proprio nome utente.
   * 
   * @param username Stringa che rappresenta il nome utente dello studente/tirocinante    
   * 
   * @return Oggetto {@link ImpiegatoUfficioTirocini} che rappresenta lo studente. <b>Può
   *         essere null</b> se nel database non è presente un impiegato con l'username 
   *         passata come parametro
   *         
   * @pre username != null
   */
  Studente findByUsername(String username);
}
