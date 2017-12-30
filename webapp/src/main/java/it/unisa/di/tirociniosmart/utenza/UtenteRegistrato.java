package it.unisa.di.tirociniosmart.utenza;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.data.annotation.Id;

/**
 * Classe astratta che modella un utente generico registrato alla piattaforma.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UtenteRegistrato {

  /**
   * Determina se due oggetti rappresentano lo stesso utente agli username dei suddetti.
   */
  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    
    if (object.getClass() != getClass()) {
      return false;
    }
    
    UtenteRegistrato utenteRegistrato = (UtenteRegistrato) object;
    
    return username.equals(utenteRegistrato.getUsername());
  }
  
  /**
   * Permette di definire una stringa che può essere considerata come la 
   * "rappresentazione testuale" dell'oggetto UtenteRegistrato.
   * 
   * @return Stringa che rappresenta una descrizione più accurata e consona dell'oggetto
   */
  @Override
  public String toString() {
    return "UtenteRegistrato [username=" + username + ", password=" + password + ", nome=" + nome
        + ", cognome=" + cognome + ", email=" + email + "]";
  }



  /**
   * Permette di ottenere l'username dell'utente.
   * 
   * @return Stringa che rappresenta l'username dell'utente
   */
  public String getUsername() {
    return username;
  }

  /**
   * Permette di specificare l'username dell'utente.
   * 
   * @param username Stringa che rappresenta l'username che si vuole assegnare all'utente
   * 
   * @pre username != null
   * @pre username.length() >= 6 and username.length() <= 24
   * @pre username matches {@link UtenteRegistrato#USERNAME_PATTERN}
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Permette di ottenere la password dell'utente.
   * 
   * @return Stringa che rappresenta la password dell'utente
   */
  public String getPassword() {
    return password;
  }

  /**
   * Permette di specificare la password dell'utente.
   * 
   * @param password Stringa che rappresenta la password dell'utente
   * 
   * @pre password != null
   * @pre password.length() >= 6 and password.length <= 24
   * @pre password matches {@link UtenteRegistrato#PASSWORD_PATTERN}
   * 
   * @post getPassword() = password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Permette di ottenere il nome dell'utente.
   * 
   * @return Stringa che rappresenta il nome dell'utente
   */
  public String getNome() {
    return nome;
  }

  /**
   * Permette di specificare il nome dell'utente.
   * 
   * @param nome Stringa che rappresenta il nome dell'utente
   * 
   * @pre nome != null
   * @pre nome.length() >= 2 and nome.length() <= 255
   * 
   * @post getNome() = nome
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * Permette di ottenere il cognome dell'utente.
   * 
   * @return Stringa che rappresenta il cognome dell'utente
   */
  public String getCognome() {
    return cognome;
  }

  /**
   * Permette di specificare il nome dell'utente.
   * 
   * @param cognome Stringa che rappresenta il cognome dell'utente
   * 
   * @pre cognome != null
   * @pre cognome.length() >= 2 and nome.length() <= 255
   * 
   * @post getCognome() = cognome
   */
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   * Permette di ottenere l'email dell'utente.
   * 
   * @return Stringa che rappresenta l'email dell'utente
   */
  public String getEmail() {
    return email;
  }

  /**
   * Permette di specificare l'email dell'utente.
   * 
   * @param email Stringa che rappresenta l'email dell'utente
   * 
   * @pre email != null
   * @pre email.length() >= 6 and email.length() <= 255
   * @pre email matches {@link UtenteRegistrato#EMAIL_PATTERN}
   */
  public void setEmail(String email) {
    this.email = email;
  }

  @Id
  protected String username;
  protected String password;
  protected String nome;
  protected String cognome;
  protected String email;
  
  /** Espressione regolare che definisce il formato del campo username. */
  public static final String USERNAME_PATTERN = "^[a-zA-Z0-9] {6,24}$";
  
  /** Espressione regolare che definisce il formato del campo password. */
  public static final String PASSWORD_PATTERN = "^[\\S]{6,24}$";

  /** Espressione regolare che definisce il formato del campo telefono. */
  public static final String TELEFONO_PATTERN = "^[0-9]{10,11}";
  
  /** Espressione regolare che definisce il formato del campo email. */
  public static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/"
                                           + "=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f"
                                           + "\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x"
                                           + "0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-"
                                           + "9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25["
                                           + "0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2"
                                           + "[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\"
                                           + "x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]"
                                           + "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
  
  
  /** Costante che rappresenta il genere maschile per l'utente. */
  public static final char SESSO_MASCHILE = 'M';
  
  /** Costante che rappresenta il genere femminile per l'utente. */
  public static final char SESSO_FEMMINILE = 'F';
  
}
