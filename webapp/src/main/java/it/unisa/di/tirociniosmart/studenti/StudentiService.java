package it.unisa.di.tirociniosmart.studenti;

import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe che definisce la logica di business per le operazioni possibili nell'ambito delle
 * richieste di iscrizione da parte di uno studente.
 *
 * @see Studente
 * @see StudenteRepository
 */
@Service
public class StudentiService {
  
  @Autowired
  private StudenteRepository studenteRepository;
  
  @Autowired
  private RichiestaIscrizioneRepository richiestaIscrizioneRepository;
  
  @Autowired
  private UtenzaService utenzaService;

  /**
   * Permette di richiedere al sistema il salvataggio di uno studente. La procedura registra
   * uno studente assegnandogli una {@link RichiestaIscrizione} inizialmente in attesa.
   * 
   * @param studente {@link Studente} per cui si vuole registrare una richiesta di iscrizione.
   *                 Non è necessario specificare la data della richiesta di iscrizione ad essa
   *                 associata poiché è il metodo stesso ad impostarla.
   * 
   * @pre studente != null
   */
  @Transactional
  public void registraRichiestaIscrizione(Studente studente) throws Exception {
    //valida i campi dello studente
    studente.setUsername(utenzaService.validaUsername(studente.getUsername()));
    studente.setPassword(utenzaService.validaPassword(studente.getPassword()));
    studente.setEmail(utenzaService.validaEmail(studente.getEmail()));
    studente.setNome(utenzaService.validaNome(studente.getNome()));
    studente.setCognome(utenzaService.validaCognome(studente.getCognome()));
    studente.setTelefono(utenzaService.validaTelefono(studente.getTelefono()));
    studente.setSesso(utenzaService.validaSesso(studente.getSesso()));
    studente.setIndirizzo(validaIndirizzoStudente(studente.getIndirizzo()));
    studente.setMatricola(validaMatricolaStudente(studente.getMatricola()));
    studente.setDataDiNascita(validaDataDiNascitaStudente(studente.getDataDiNascita()));
    
    // Imposta stato e data della richiesta
    RichiestaIscrizione richiesta = studente.getRichiestaIscrizione();
    richiesta.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.now());
    
    // Registra le informazioni
    studenteRepository.save(studente);
    
  }
  
  /**
   * Permette di approvare una richiesta di iscrizione già presente nel sistema.
   * 
   * @param idRichiesta Long che rappresenta l'identificatore della richiesta di iscrizione
   *                    da approvare
   * 
   * @throws IdRichiestaIscrizioneNonValidoException se non esiste alcuna richiesta di
   *         iscrizione nel sistema con identificatore uguale ad idRichiesta
   * 
   * @throws RichiestaIscrizioneGestitaException se la richiesta identificata da idRichiesta
   *         si trova in uno stato diverso da quello in attesa
   */
  @Transactional
  public void approvaRichiestaIscrizione(long idRichiesta)
         throws IdRichiestaIscrizioneNonValidoException,
                RichiestaIscrizioneGestitaException {
    // Controlla che la richiesta esista
    RichiestaIscrizione richiesta = richiestaIscrizioneRepository.findById(idRichiesta);
    if (richiesta == null) {
      throw new IdRichiestaIscrizioneNonValidoException();
    }
    
    // Controlla che la richiesta non sia già stata gestita in precedenza
    if (richiesta.getStatus() != RichiestaIscrizione.IN_ATTESA) {
      throw new RichiestaIscrizioneGestitaException();
    } else {
      richiesta.setStatus(RichiestaIscrizione.APPROVATA);
    }
  }
  
  /**
   * Permette di rifiutare una richiesta di iscrizione già presente nel sistema.
   * 
   * @param idRichiesta Long che rappresenta l'identificatore della richiesta di iscrizione
   *                    da rifiutare
   * 
   * @throws IdRichiestaIscrizioneNonValidoException se non esiste alcuna richiesta di
   *         iscrizione nel sistema con identificatore uguale ad idRichiesta
   * 
   * @throws RichiestaIscrizioneGestitaException se la richiesta identificata da idRichiesta
   *         si trova in uno stato diverso da quello in attesa
   *         
   * @throws CommentoRichiestaIscrizioneNonValidoException se il commento da associare alla
   *         richiesta è nullo o vuoto
   */
  @Transactional
  public void rifiutaRichiestaConvenzionamento(long idRichiesta, String commento)
         throws IdRichiestaIscrizioneNonValidoException,
                RichiestaIscrizioneGestitaException,
                CommentoRichiestaIscrizioneNonValidoException {
    // Controlla che la richiesta esista
    RichiestaIscrizione richiesta = richiestaIscrizioneRepository.findById(idRichiesta);
    if (richiesta == null) {
      throw new IdRichiestaIscrizioneNonValidoException();
    }
    
    // Controlla che la richiesta non sia già stata gestita in precedenza ed impostane lo stato
    if (richiesta.getStatus() != RichiestaIscrizione.IN_ATTESA) {
      throw new RichiestaIscrizioneGestitaException();
    } else {
      richiesta.setStatus(RichiestaIscrizione.RIFIUTATA);     
    }
    
    richiesta.setCommentoUfficioTirocini(validaCommento(commento));
  }
  
  /**
   * Controlla che il commento sul rifiuto di una richiesta sia specificato.
   * 
   * @param commento Stringa che rappresenta il commento da controllare
   * 
   * @return La stringa che rappresenta il commento da controllare bonificata
   * 
   * @throws CommentoRichiestaIscrizioneNonValidoException se il commento passato come parametro
   *         è nullo oppure è rappresentato da stringa vuota
   */
  public String validaCommento(String commento) 
        throws CommentoRichiestaIscrizioneNonValidoException {
    if (commento == null) {
      throw new CommentoRichiestaIscrizioneNonValidoException();
    } else {
      commento = commento.trim();
      
      if (commento.equals("")) {
        throw new CommentoRichiestaIscrizioneNonValidoException();
      } else {
        return commento;
      }
    }
  }
  
  /**
   * Controlla che la matricola di un'azienda sia specificata e che rispetti il formato
   * prestabilito. Controlla inoltre che tale matricola non sia già presente nel sistema.
   * 
   * @param matricolaStudente Stringa che rappresenta la matricola da controllare
   * 
   * @return La stringa che rappresenta la matricola da controllare bonificata
   * 
   * @throws MatricolaStudenteNonValidaException se la matricola passata come parametro è nulla
   *         oppure se non rispetta il formato {@link Studente#MATRICOLA_PATTERN}
   * 
   * @throws MatricolaStudenteEsistenteException se la matricola passata come parametro è già
   *         presente nel sistema
   */
  public String validaMatricolaStudente(String matricolaStudente)
         throws MatricolaStudenteNonValidaException, MatricolaStudenteEsistenteException {
    if (matricolaStudente == null) {
      throw new MatricolaStudenteNonValidaException();
    } else {
      matricolaStudente = matricolaStudente.trim();
      
      if (!matricolaStudente.matches(Studente.MATRICOLA_PATTERN)) {
        throw new MatricolaStudenteNonValidaException();
      } else if (studenteRepository.existsByMatricola(matricolaStudente)) {
        throw new MatricolaStudenteEsistenteException();
      } else {
        return matricolaStudente;
      }
    }
  }
  
  /**
   * Controlla che l'indirizzo di uno studente sia specificato e che la sua lunghezza rispetti i
   * parametri prestabiliti.
   * 
   * @param indirizzoStudente Stringa che rappresenta l'indirizzo da controllare
   * 
   * @return La stringa che rappresenta l'indirizzo da controllare bonificata
   * 
   * @throws IndirizzoStudenteNonValidoException se l'indirizzo dell'azienda è nullo o se la sua
   *         lunghezza non rientra nell'intervallo che va da 
   *         {@link Studente#MIN_LUNGHEZZA_INDIRIZZO} a {@link Studente#MAX_LUNGHEZZA_INDIRIZZO}
   */
  public String validaIndirizzoStudente(String indirizzoStudente)
         throws IndirizzoStudenteNonValidoException {
    if (indirizzoStudente == null) {
      throw new IndirizzoStudenteNonValidoException();
    } else {
      indirizzoStudente = indirizzoStudente.trim();
   
      if (indirizzoStudente.length() < Studente.MIN_LUNGHEZZA_INDIRIZZO
          || indirizzoStudente.length() > Studente.MAX_LUNGHEZZA_INDIRIZZO) {
        throw new IndirizzoStudenteNonValidoException();
      } else {
        return indirizzoStudente;
      } 
    }
  }
  
  /**
   * Controlla che la data di nascita di uno studente sia specificato e che la sua lunghezza 
   * rispetti i parametri prestabiliti.
   * 
   * @param dataDiNascita LocalDate che rappresenta la data da controllare
   * 
   * @return Oggetto LocalDate che rappresenta la data di nascita da controllare bonificata
   * 
   * @throws DataDiNascitaStudenteNonValidaException se la data è nulla o se la distanza
   *         dall'anno corrette è maggiore di {#MAX_DISTANZA_ANNO_NASCITA} o è minore di
   *         {#MIN_DISTANZA_ANNO_NASCITA}
   */
  public LocalDate validaDataDiNascitaStudente(LocalDate dataDiNascita) 
         throws DataDiNascitaStudenteNonValidaException {
         
    if (dataDiNascita == null) {
      throw new DataDiNascitaStudenteNonValidaException();
    } else {
      LocalDate oggi = LocalDate.now();
      long distanza = ChronoUnit.YEARS.between(dataDiNascita, oggi);
      
      if (distanza < MIN_DISTANZA_ANNO_NASCITA || distanza > MAX_DISTANZA_ANNO_NASCITA) {
        throw new DataDiNascitaStudenteNonValidaException();
      } else {
        return dataDiNascita;
      } 
    }
  }
  
  /** 
   * Costante che rappresenta la minima distanza in anni dalla data corrente 
   * per la data di nascita.
   */
  public static final int MIN_DISTANZA_ANNO_NASCITA = 17;
 
  /** 
   * Costante che rappresenta la massima distanza in anni dalla data corrente 
   * per la data di nascita.
   */
  public static final int MAX_DISTANZA_ANNO_NASCITA = 130;

}
