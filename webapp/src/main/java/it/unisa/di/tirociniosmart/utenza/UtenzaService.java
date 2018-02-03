package it.unisa.di.tirociniosmart.utenza;

import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendaleRepository;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoInAttesaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoRifiutataException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirociniRepository;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizione;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneInAttesaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneRifiutataException;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.studenti.StudenteRepository;

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
  
  @Autowired
  private StudenteRepository studenteRepository;
  
  @Autowired
  private DelegatoAziendaleRepository delegatoRepository;
  
  @Autowired
  private ImpiegatoUfficioTirociniRepository impiegatoRepository;
  
  /**
   * Controlla che l'username di un utente sia specificato e che rispetti il formato prestabilito.
   * Controlla inolte che tale username non sia già presente nel sistema.
   * 
   * @param username Stringa che rappresenta l'username da controllare
   * 
   * @return username La stringa che rappresenta l'username da controllare bonificata
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
   * @return passsword Stringa che rappresenta la password da controllare bonifica
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
   *         {@link UtenteRegistrato#EMAIL_PATTERN}
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
   * Controlla che il sesso di un utente sia sia specificato e che sia rappresentato da una delle 
   * costanti prestabilite.
   * 
   * @param sesso Carattere che rappresenta il sesso da controllare
   * 
   * @return sesso Carattere che rappresenta il sesso da controllare bonificato
   * 
   * @throws SessoNonValidoException se il sesso del delegato non è una delle costanti
   *         {@link UtenteRegistrato#SESSO_MASCHILE} e {@link UtenteRegistrato#SESSO_FEMMINILE}
   */
  public String validaSesso(String sesso) throws SessoNonValidoException {
    if (sesso == null) {
      throw new SessoNonValidoException();
    } else {
      sesso = sesso.trim();
      
      if (!sesso.equals(UtenteRegistrato.SESSO_MASCHILE)
          && !sesso.equals(UtenteRegistrato.SESSO_FEMMINILE)) {
        throw new SessoNonValidoException();
      } else {
        return sesso;
      }
    }
  }
  
  /**
   * Controlla che il numero di telefono di un utente sia specificato e che rispetti il formato
   * prestabilito.
   * 
   * @param telefono Stringa che rappresenta il numero di telefono da controllare
   * 
   * @return telefono La stringa che rappresenta il numero di telefono da controllare bonificata
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
  
  /**
   * Permette di ottenere l'utente autenticato nel sistema.
   * 
   * @return L'utente autenticato nel sistema, <b>null</b> se non vi è alcun utente autenticato
   */
  public UtenteRegistrato getUtenteAutenticato() {  
    // Ottieni l'username dell'utente autenticato e restituisci null se non è presente alcun utente
    // in sessione
    String username = (AutenticazioneHolder.getUtente());
    if (username == null) {
      return null;
    }
    
    UtenteRegistrato utente;
    
    // Controlla se l'username è associato ad un impiegato dell'ufficio tirocini
    utente = impiegatoRepository.findByUsername(username);
    if (utente != null) {
      return utente;
    }
    
    // Controlla se l'username è associato ad un delegato aziendale
    utente = delegatoRepository.findByUsername(username);
    if (utente != null) {
      return utente;
    }
    
    // Controlla se l'username è associato ad uno studente
    utente = studenteRepository.findByUsername(username);
    if (utente != null) {
      return utente;
    }
    
    // Dead code
    return null;
  }
  
  /**
   * Permette di specificare l'utente autenticato nel sistema, tramite username, in una variabile
   * visibile a livello di thread così da condividere l'informazione con tutti gli altri livelli.
   * Questo metodo può essere utilizzato per iniettare automaticamente l'utente nel thread associato
   * alla richiesta a partire dall'attributo di sessione del server.
   * 
   * @param username Username dell'utente che si vuole autenticare nel sistema
   */
  public void setUtenteAutenticato(String username) {
    // Se username è null, rimuovi la variabile di thread per prevenire memory leak
    if (username == null) {
      AutenticazioneHolder.setUtente(null);
      return;
    }
    
    if (utenteRepository.existsByUsername(username)) {
      AutenticazioneHolder.setUtente(username);
    }
    
  }
  
  /**
   * Permette l'autenticazione di un utente nel sistema.
   * 
   * @param username Stringa che rappresenta l'username dell'utente
   * 
   * @param password Stringa che rappresenta la password dell'utente
   * 
   * @throws CredenzialiNonValideException se la coppia (username, password) non è presente nel
   *         sistema
   *         
   * @throws RichiestaConvenzionamentoInAttesaException se l'utente che tenta di accedere è un
   *         delegato aziendale che rappresenta un'azienda la cui richiesta di convenzionamento non
   *         è ancora stata gestita
   *         
   * @throws RichiestaConvenzionamentoRifiutataException se l'utente che tenta di accedere è un
   *         delegato aziendale che rappresenta un'azienda la cui richiesta di convenzionamento non
   *         è stata rifiutata
   *         
   * @throws RichiestaIscrizioneInAttesaException se l'utente che tenta di accedere è uno studente
   *         la cui richiesta d'iscrizione non è ancora stata gestita
   *         
   * @throws RichiestaIscrizioneRifiutataException se l'utente che tenta di accedere è uno studente
   *         la cui richiesta d'iscrizione è stata rifiutata
   */
  public void login(String username, String password)
         throws CredenzialiNonValideException, RichiestaConvenzionamentoInAttesaException,
                RichiestaConvenzionamentoRifiutataException, RichiestaIscrizioneInAttesaException,
                RichiestaIscrizioneRifiutataException {
    UtenteRegistrato utente;
    
    utente = impiegatoRepository.findByUsernameAndPassword(username, password);
    if (utente != null) {
      AutenticazioneHolder.setUtente(username);
      return;
    }
    
    // Controlla se le credenziali corrispondono a quelle di un delegato aziendale e, nel caso,
    // controlla che la richiesta di convenzionamento associata alla sua azienda sia accettata
    utente = delegatoRepository.findByUsernameAndPassword(username, password);
    if (utente != null) {
      DelegatoAziendale delegato = (DelegatoAziendale) utente;
      RichiestaConvenzionamento richiesta = delegato.getAzienda().getRichiesta();
     
      if (richiesta.getStatus() == RichiestaConvenzionamento.IN_ATTESA) {
        throw new RichiestaConvenzionamentoInAttesaException();
      } else if (richiesta.getStatus() == RichiestaConvenzionamento.RIFIUTATA) {
        throw new RichiestaConvenzionamentoRifiutataException();
      } else {
        AutenticazioneHolder.setUtente(username);
        return;
      }
    }
    
    // Controlla se le credenziali corrispondono a quelle di uno studente e, nel caso, controlla
    // che la richiesta d'iscrizione associatagli sia stata accettata
    utente = studenteRepository.findByUsernameAndPassword(username, password);
    if (utente != null) {
      Studente studente = (Studente) utente;
      RichiestaIscrizione richiesta = studente.getRichiestaIscrizione();
      
      if (richiesta.getStatus() == RichiestaIscrizione.IN_ATTESA) {
        throw new RichiestaIscrizioneInAttesaException();
      } else if (richiesta.getStatus() == RichiestaIscrizione.RIFIUTATA) {
        throw new RichiestaIscrizioneRifiutataException();
      } else {
        AutenticazioneHolder.setUtente(username);
        return;
      }
    }
    
    throw new CredenzialiNonValideException();
  }
  
  /**
   * Permette la rimozione dell'utente dalla sessione.
   */
  public void logout() { 
    AutenticazioneHolder.setUtente(null);
  }
  
}
