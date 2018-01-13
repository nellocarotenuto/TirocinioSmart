package it.unisa.di.tirociniosmart.utenza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe che definisce operazioni che permettono di realizzare la logica di business comune a
 * tutti gli utenti del sistema.
 *
 * @see UtenteRegistrato
 * @see UtenteRegistratoRepository
 */
@Service
public class UtenzaService {

  @Autowired
  private UtenteRegistratoRepository utenteRepository;
  
  /**
   * Controlla che l'username di un utente sia specificato e che rispetti il formato prestabilito.
   * Controlla inolte che tale username non sia già presente nel sistema.
   * 
   * @param username Stringa che rappresenta l'username da controllare
   * 
   * @return La stringa che rappresenta l'username da controllare bonificata
   * 
   * @throws UsernameNonValidoException se l'username non è specificato oppure se non rispetta il
   *         formato {@link UtenteRegistrato#USERNAME_PATTERN}
   * 
   * @throws UsernameEsistenteException se l'username specificato è già presente nel sistema
   */
  public String validaUsername(String username)
         throws UsernameNonValidoException, UsernameEsistenteException {
    if (username == null) {
      throw new UsernameNonValidoException();
    } else {
      username = username.trim();
      
      if (!username.matches(UtenteRegistrato.USERNAME_PATTERN)) {
        throw new UsernameNonValidoException();
      } else if (utenteRepository.existsByUsername(username)) {
        throw new UsernameEsistenteException();
      } else {
        return username;
      }
    }
  }
  
  /**
   * Controlla che la password di un utente sia specificata e che rispetti il formato prestabilito.
   * 
   * @param password Stringa che rappresenta la password da controllare
   * 
   * @return La stessa stringa passata come parametro
   * 
   * @throws PasswordNonValidaException se la password non è specificata oppure se non rispetta il
   *         formato {@link UtenteRegistrato#PASSWORD_PATTERN}
   */
  public String validaPassword(String password) throws PasswordNonValidaException {
    if (password == null) {
      throw new PasswordNonValidaException();
    } else if (!password.matches(UtenteRegistrato.PASSWORD_PATTERN)) {
      throw new PasswordNonValidaException();
    } else {
      return password;
    }
  }
  
  /**
   * Controlla che l'e-mail di un utente sia specificata e che rispetti il formato prestabilito.
   * Controlla inoltre che tale email non sia già presente nel sistema.
   * 
   * @param email Stringa che rappresenta l'e-mail da controllare
   * 
   * @return La stringa che rappresenta l'e-mail da controllare bonificata
   * 
   * @throws EmailNonValidaException se l'e-mail non è specificata oppure se non rispetta il formato
   *                                 {@link UtenteRegistrato#EMAIL_PATTERN}
   * 
   * @throws EmailEsistenteException se l'e-mail specificata è gia presente nel sistema
   */
  public String validaEmail(String email) throws EmailNonValidaException, EmailEsistenteException {
    if (email == null) {
      throw new EmailNonValidaException();
    } else {
      email = email.trim();
      
      if (!email.matches(UtenteRegistrato.EMAIL_PATTERN)) {
        throw new EmailNonValidaException();
      } else if (utenteRepository.existsByEmail(email)) {
        throw new EmailEsistenteException();
      } else {
        return email;
      }
    }
  }
  
  /**
   * Controlla che il nome di un utente sia specificato e che la sua lunghezza rispetti i parametri
   * prestabiliti.
   * 
   * @param nome Stringa che rappresenta il nome da controllare
   * 
   * @return La stringa che rappresenta il nome da controllare bonificata
   * 
   * @throws NomeNonValidoException se il nome è nullo oppure se la sua lunghezza non rientra
   *         nell'intervallo che va da {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *         {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   */
  public String validaNome(String nome) throws NomeNonValidoException {
    if (nome == null) {
      throw new NomeNonValidoException();
    } else {
      nome = nome.trim();
      
      if (nome.length() < UtenteRegistrato.MIN_LUNGHEZZA_NOME
          || nome.length() > UtenteRegistrato.MAX_LUNGHEZZA_NOME) {
        throw new NomeNonValidoException();
      } else {
        return nome;
      }
    }
  }
  
  /**
   * Controlla che il cognome di un utente sia specificato e che la sua lunghezza rispetti i
   * parametri prestabiliti.
   * 
   * @param cognome Stringa che rappresenta il cognome da controllare
   * 
   * @return La stringa che rappresenta il cognome da controllare bonificata
   * 
   * @throws CognomeNonValidoException se il cognome è nullo oppure se la sua lunghezza non rientra
   *         nell'intervallo che va da {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *         {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   */
  public String validaCognome(String cognome) throws CognomeNonValidoException {
    if (cognome == null) {
      throw new CognomeNonValidoException();
    } else {
      cognome = cognome.trim();
      
      if (cognome.length() < UtenteRegistrato.MIN_LUNGHEZZA_NOME
          || cognome.length() > UtenteRegistrato.MAX_LUNGHEZZA_NOME) {
        throw new CognomeNonValidoException();
      } else {
        return cognome;
      }
    }
  }
  
  /**
   * Controlla che il sesso di un utente sia sia specificato e che rispetti una delle costanti
   * prestabilite.
   * 
   * @param sesso Carattere che rappresenta il sesso da controllare
   * 
   * @return Lo stesso carattere ricevuto come parametro
   * 
   * @throws SessoNonValidoException se il sesso del delegato non è una delle costanti
   *         {@link UtenteRegistrato#SESSO_MASCHILE} e {@link UtenteRegistrato#SESSO_FEMMINILE}
   */
  public char validaSesso(char sesso) throws SessoNonValidoException {
    if (sesso != UtenteRegistrato.SESSO_MASCHILE && sesso != UtenteRegistrato.SESSO_FEMMINILE) {
      throw new SessoNonValidoException();
    } else {
      return sesso;
    }
  }
  
  /**
   * Controlla che il numero di telefono di un utente sia specificato e che rispetti il formato
   * prestabilito.
   * 
   * @param telefono Stringa che rappresenta il numero di telefono da controllare
   * 
   * @return La stringa che rappresenta il numero di telefono da controllare bonificata
   * 
   * @throws TelefonoNonValidoException se il numero di telefono del delegato non è specificato o
   *         non rispetta il formato definito in {@link UtenteRegistrato#TELEFONO_PATTERN}
   */
  public String validaTelefono(String telefono) throws TelefonoNonValidoException {
    if (telefono == null) {
      throw new TelefonoNonValidoException();
    } else {
      telefono = telefono.trim();
      
      if (!telefono.matches(UtenteRegistrato.TELEFONO_PATTERN)) {
        throw new TelefonoNonValidoException();
      } else {
        return telefono;
      }
    }
  }
}
