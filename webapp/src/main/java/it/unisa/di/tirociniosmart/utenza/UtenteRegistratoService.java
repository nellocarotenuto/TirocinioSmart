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
public class UtenteRegistratoService {

  @Autowired
  private UtenteRegistratoRepository utenteRepository;
  
  /**
   * Permette di determinare se un oggetto {@link UtenteRegistrato} è ben formato e non esiste nel
   * sistema al fine di aggiungerlo.
   * 
   * @param utente Rappresenta l'utente che si vuole validare e quindi aggiungere nel sistema
   * 
   * @return L'oggetto utente preso in input i cui campi sono stati ripuliti in modo da renderlo
   *         pronto ad essere inserito nel sistema.
   * 
   * @pre utente != null
   * 
   * @throws UsernameNonValidoException se l'username non è specificato oppure se non rispetta il
   *                                    formato {@link UtenteRegistrato#USERNAME_PATTERN}
   * 
   * @throws UsernameEsistenteException se l'username specificato è già presente nel sistema
   * 
   * @throws PasswordNonValidaException se la password non è specificata oppure se non rispetta il
   *                                    formato {@link UtenteRegistrato#PASSWORD_PATTERN}
   * 
   * @throws EmailNonValidaException se l'e-mail non è specificata oppure se non rispetta il formato
   *                                 {@link UtenteRegistrato#EMAIL_PATTERN}
   * 
   * @throws EmailEsistenteException se l'e-mail specificata è gia presente nel sistema
   * 
   * @throws NomeNonValidoException se il nome è nullo oppure se la sua lunghezza non rientra
   *                                nell'intervallo che va da
   *                                {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *                                {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   * 
   * @throws CognomeNonValidoException se il cognome è nullo oppure se la sua lunghezza non rientra
   *                                   nell'intervallo che va da
   *                                   {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *                                   {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   */
  public UtenteRegistrato validaUtente(UtenteRegistrato utente)
         throws UsernameNonValidoException, UsernameEsistenteException, PasswordNonValidaException,
                EmailNonValidaException, EmailEsistenteException, NomeNonValidoException,
                CognomeNonValidoException {
    // Controlla che l'username sia specificato, che rispetti il pattern, non sia già presente nel
    // sistema quindi aggiornalo nell'utente
    String username = utente.getUsername();
    if (username == null) {
      throw new UsernameNonValidoException();
    } else {
      username = username.trim();
      
      if (!username.matches(UtenteRegistrato.USERNAME_PATTERN)) {
        throw new UsernameNonValidoException();
      } else if (utenteRepository.existsByUsername(username)) {
        throw new UsernameEsistenteException();
      } else {
        utente.setUsername(username);
      }
    }
    
    // Controlla che la password sia specificata e che rispetti il pattern
    String password = utente.getPassword();
    if (password == null) {
      throw new PasswordNonValidaException();
    } else if (!password.matches(UtenteRegistrato.PASSWORD_PATTERN)) {
      throw new PasswordNonValidaException();
    }
    
    // Controlla che l'email sia specificata, che rispetti il pattern, non sia già presente nel
    // sistema quindi aggiornala nell'utente
    String email = utente.getEmail();
    if (email == null) {
      throw new EmailNonValidaException();
    } else {
      email = email.trim();
      
      if (!email.matches(UtenteRegistrato.EMAIL_PATTERN)) {
        throw new EmailNonValidaException();
      } else if (utenteRepository.existsByEmail(email)) {
        throw new EmailEsistenteException();
      } else {
        utente.setEmail(email);
      }
    }
    
    // Controlla che il nome sia specificato e che la sua lunghezza rispetti i parametri prefissati
    // quindi assegnalo all'utente (poiché ne è stato fatto il trim)
    String nome = utente.getNome();
    if (nome == null) {
      throw new NomeNonValidoException();
    } else {
      nome = nome.trim();
      
      if (nome.length() < UtenteRegistrato.MIN_LUNGHEZZA_NOME
          || nome.length() > UtenteRegistrato.MAX_LUNGHEZZA_NOME) {
        throw new NomeNonValidoException();
      } else {
        utente.setNome(nome);
      }
    }
    
    // Controlla che il cognome sia specificato e che la sua lunghezza rispetti i parametri
    // prefissati quindi assegnalo all'utente (poiché ne è stato fatto il trim)
    String cognome = utente.getCognome();
    if (cognome == null) {
      throw new CognomeNonValidoException();
    } else {
      cognome = cognome.trim();
      
      if (nome.length() < UtenteRegistrato.MIN_LUNGHEZZA_NOME
          || nome.length() > UtenteRegistrato.MAX_LUNGHEZZA_NOME) {
        throw new CognomeNonValidoException();
      } else {
        utente.setCognome(cognome);
      }
    }
    
    return utente;
  }
  
}
