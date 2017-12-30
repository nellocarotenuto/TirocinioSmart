package it.unisa.di.tirociniosmart.utenza;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative ad un utente registrato alla piattaforma.
 * 
 * @see UtenteRegistrato
 */
@Repository
public interface UtenteRegistratoRepository extends JpaRepository<UtenteRegistrato, String> {

  /**
   * Permette di verificare se un'utente esiste nel database attraverso la 
   * propria username.
   * 
   * @param username Stringa che rappresenta il nome utente di un utente 
   *               
   * @return true se l'utente esiste,
   *         false se l'utente non esiste
   *         
   * @pre username != null 
   */
  boolean existsByUsername(String username);
  
  /**
   * Permette di verificare se un'utente esiste nel database attraverso la 
   * propria email.
   * 
   * @param username Stringa che rappresenta l'email di un utente 
   *               
   * @return true se l'utente esiste,
   *         false se l'utente non esiste
   *         
   * @pre email != null 
   */
  boolean existsByEmail(String email);
   
}
