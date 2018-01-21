package it.unisa.di.tirociniosmart.domandetirocinio;

import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.utenza.AutenticazioneHolder;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe che definisce la logica di business per le operazioni possibili nell'ambito delle
 * domande di tirocinio.
 * 
 * @see DomandaTirocinio
 * @see DomandaTirocinioRepository
 */
@Service
public class DomandeTirocinioService {
  
  @Autowired
  private DomandaTirocinioRepository domandaRepository;
  
  /**
   * Permette di richiedere al sistema il salvataggio di una domanda di tirocinio. 
   * 
   * @param domanda {@link DomandaTirocinio} per cui si vuole registrare una domanda di tirocinio.
   *                 Non è necessario specificare la data della domanda di tirocinio ad essa
   *                 associata poiché è il metodo stesso ad impostarla.
   * 
   * @pre domanda != null
   */
  @Transactional(rollbackFor = Exception.class)
  public void registraDomandaTirocinio(DomandaTirocinio domanda) throws Exception {
    // Solo uno studente può registrare una nuova domanda di tirocinio
    if (!(AutenticazioneHolder.getUtente() instanceof Studente)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Valida i campi dello studente
    domanda.setInizioTirocinio(validaDataDiInizioTirocinio(domanda.getInizioTirocinio(),
                                                           domanda.getFineTirocinio()));
    domanda.setFineTirocinio(validaDataDiFineTirocinio(domanda.getInizioTirocinio(),
                                                       domanda.getFineTirocinio()));
    domanda.setCfu(validaCfu(domanda.getCfu()));
    domanda.setCommentoStudente(validaCommento(domanda.getCommentoStudente()));
    domanda.setProgettoFormativo(verificaStatoProgettoFormativo(domanda.getProgettoFormativo()));
    
    // Imposta stato e data della domanda
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setData(LocalDateTime.now());
    
    // Registra le informazioni
    domandaRepository.save(domanda);
    
  }
  
  /**
   * Permette ad un delegato aziendale di accettare una domanda di tirocinio già presente nel 
   * sistema.
   * 
   * @param idDomanda Long che rappresenta l'identificatore della domanda di tirocinio
   *                  da accettare
   * 
   * @throws IdDomandaTirocinioNonValidoException se non esiste alcuna domanda di
   *         tirocinio nel sistema con identificatore uguale ad idDomanda
   * 
   * @throws DomandaTirocinioGestitaException se la domanda identificata da idDomanda
   *         si trova in uno stato diverso da quello in attesa
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che richiede l'accettazione di una domanda
   *         non è un delegato aziendale oppure se la domanda non è associata all'azienda
   *         rappresentata dal delegato
   */
  @Transactional(rollbackFor = Exception.class)
  public void accettaDomandaTirocinio(long idDomanda)
         throws IdDomandaTirocinioNonValidoException,
                DomandaTirocinioGestitaException, RichiestaNonAutorizzataException {
    UtenteRegistrato utente = AutenticazioneHolder.getUtente();
    
    // Solo un delegato aziendale può accettare una domanda di tirocinio
    if (!(utente instanceof DelegatoAziendale)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la domanda esista
    DomandaTirocinio domanda = domandaRepository.findById(idDomanda);
    if (domanda == null) {
      throw new IdDomandaTirocinioNonValidoException();
    }
    
    // Controlla che la domanda sia associata ad un progetto formativo dell'azienda rappresentata
    // dal delegato che effettua la richiesta
    DelegatoAziendale delegato = (DelegatoAziendale) utente;
    if (!delegato.getAzienda().equals(domanda.getProgettoFormativo().getAzienda())) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la domanda non sia già stata gestita in precedenza
    if (domanda.getStatus() != DomandaTirocinio.IN_ATTESA) {
      throw new DomandaTirocinioGestitaException();
    } else {
      domanda.setStatus(DomandaTirocinio.ACCETTATA);
    }
  }
  
  /**
   * Permette ad un delegato aziendale di rifiutare una domanda di tirocinio già presente nel 
   * sistema.
   * 
   * @param idDomanda Long che rappresenta l'identificatore della domanda di tirocinio
   *                  da rifiutare
   * 
   * @throws IdDomandaTirocinioNonValidoException se non esiste alcuna domanda di tirocinio
   *         nel sistema con identificatore uguale ad idDomanda
   * 
   * @throws DomandaTirocinioGestitaException se la domanda identificata da idDomanda
   *         si trova in uno stato diverso da quello in attesa
   *         
   * @throws CommentoDomandaTirocinioNonValidoException se il commento da associare alla
   *         domanda è nullo o vuoto
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che richiede l'accettazione di una domanda
   *         non è un delegato aziendale oppure se la domanda non è associata all'azienda
   *         rappresentata dal delegato
   */
  @Transactional(rollbackFor = Exception.class)
  public void rifiutaDomandaTirocinio(long idDomanda, String commento)
         throws IdDomandaTirocinioNonValidoException,
                DomandaTirocinioGestitaException,
                CommentoDomandaTirocinioNonValidoException, RichiestaNonAutorizzataException {
    UtenteRegistrato utente = AutenticazioneHolder.getUtente();
    
    // Solo un delegato aziendale può rifiutare una domanda di tirocinio
    if (!(utente instanceof DelegatoAziendale)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la domanda esista
    DomandaTirocinio domanda = domandaRepository.findById(idDomanda);
    if (domanda == null) {
      throw new IdDomandaTirocinioNonValidoException();
    }
    
    // Controlla che la domanda sia associata ad un progetto formativo dell'azienda rappresentata
    // dal delegato che effettua la richiesta
    DelegatoAziendale delegato = (DelegatoAziendale) utente;
    if (!delegato.getAzienda().equals(domanda.getProgettoFormativo().getAzienda())) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la richiesta non sia già stata gestita in precedenza ed impostane lo stato
    if (domanda.getStatus() != DomandaTirocinio.IN_ATTESA) {
      throw new DomandaTirocinioGestitaException();
    } else {
      domanda.setStatus(DomandaTirocinio.RIFIUTATA);  
    }
    
    domanda.setCommentoAzienda(validaCommento(commento));
  }
  
  /**
   * Permette ad un impiegato dell'ufficio tirocini di approvare una domanda di tirocinio già 
   * presente nel sistema.
   * 
   * @param idDomanda Long che rappresenta l'identificatore della domanda di tirocinio
   *                  da respingere
   * 
   * @throws IdDomandaTirocinioNonValidoException se non esiste alcuna domanda di
   *         tirocinio nel sistema con identificatore uguale ad idDomanda
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che richiede l'approvazione della domanda
   *         non è un impiegato dell'ufficio tirocini
   * 
   * @throws DomandaTirocinioGestitaException se la domanda identificata da idDomanda
   *         si trova in uno stato diverso da accettata
   */
  @Transactional(rollbackFor = Exception.class)
  public void approvaDomandaTirocinio(long idDomanda)
         throws IdDomandaTirocinioNonValidoException,
                StatoDomandaNonIdoneoException, RichiestaNonAutorizzataException {
    UtenteRegistrato utente = AutenticazioneHolder.getUtente();
    
    // Solo un impiegato dell'ufficio tirocini può approvare una domanda di tirocinio
    if (!(utente instanceof ImpiegatoUfficioTirocini)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la domanda esista
    DomandaTirocinio domanda = domandaRepository.findById(idDomanda);
    if (domanda == null) {
      throw new IdDomandaTirocinioNonValidoException();
    }
    
    if (domanda.getStatus() == DomandaTirocinio.ACCETTATA) {
      domanda.setStatus(DomandaTirocinio.APPROVATA);
    } else {
      throw new StatoDomandaNonIdoneoException();
    }
    
  }
  
  /**
   * Permette di ottenere la lista delle domande di tirocinio.
   * 
   * @return Lista di {@link DomandaTirocinio} il cui status è "in attesa" o "accettata"
   * 
   * @throws RichiestaNonAutorizzataException se l'utente che tenta di visualizzare l'elenco delle 
   *         domande di tirocinio non è autorizzato a svolgere l'operazione
   */
  public List<DomandaTirocinio> elencaDomandeTirocinio() throws RichiestaNonAutorizzataException {
    
    //Un impiegato dell'ufficio tirocini può vedere solo le domande accettate 
    if (AutenticazioneHolder.getUtente() instanceof ImpiegatoUfficioTirocini) {
      return domandaRepository.findAllByStatus(DomandaTirocinio.ACCETTATA);
      
    //Un delegato aziendale può vedere solo le domande in attesa 
    } else if (AutenticazioneHolder.getUtente() instanceof DelegatoAziendale) {
      DelegatoAziendale delegato = (DelegatoAziendale) AutenticazioneHolder.getUtente();
      return domandaRepository.findAllByStatusAndProgettoFormativoAziendaId(DomandaTirocinio.IN_ATTESA,
                                                           delegato.getAzienda().getId());
      
      //Uno studente può vedere le domande di tirocinio in attesa
    } else if (AutenticazioneHolder.getUtente() instanceof Studente) {
      Studente studente = (Studente) AutenticazioneHolder.getUtente();
      return domandaRepository.findAllByStatusAndStudenteUsername(DomandaTirocinio.IN_ATTESA,
                                                                  studente.getUsername());
    } else {
      throw new RichiestaNonAutorizzataException();
    }  
  }
  
  /**
   * Permette ad un impiegato dell'ufficio tirocinio di respingere una domanda di tirocinio già 
   * presente nel sistema.
   * 
   * @param idDomanda Long che rappresenta l'identificatore della domanda di tirocinio
   *                  da rifiutare
   * 
   * @throws RichiestaNonAutorizzataException se l'utente che richiede l'approvazione della domanda
   *         non è un impiegato dell'ufficio tirocini
   *         
   * @throws IdDomandaTirocinioNonValidoException se non esiste alcuna domanda di tirocinio
   *         nel sistema con identificatore uguale ad idDomanda
   * 
   * @throws DomandaTirocinioGestitaException se la domanda identificata da idDomanda
   *         si trova in uno stato diverso da quello in attesa
   *         
   * @throws CommentoDomandaTirocinioNonValidoException se il commento da associare alla
   *         domanda è nullo o vuoto
   */
  @Transactional(rollbackFor = Exception.class)
  public void respingiDomandaTirocinio(long idDomanda, String commento)
         throws IdDomandaTirocinioNonValidoException,
                StatoDomandaNonIdoneoException,
                CommentoDomandaTirocinioNonValidoException, RichiestaNonAutorizzataException {
    UtenteRegistrato utente = AutenticazioneHolder.getUtente();
    
    // Solo un impiegato dell'ufficio tirocini può respingere una domanda di tirocinio
    if (!(utente instanceof ImpiegatoUfficioTirocini)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la domanda esista
    DomandaTirocinio domanda = domandaRepository.findById(idDomanda);
    if (domanda == null) {
      throw new IdDomandaTirocinioNonValidoException();
    }
    
    // Controlla che la richiesta non sia già stata gestita in precedenza ed impostane lo stato
    if (domanda.getStatus() == DomandaTirocinio.ACCETTATA) {
      domanda.setStatus(DomandaTirocinio.RESPINTA);
    } else {
      throw new StatoDomandaNonIdoneoException();
    }
    
    domanda.setCommentoImpiegato(validaCommento(commento));
  }
  
  /**
   * Controlla che il commento sul rifiuto o respinta di una richiesta sia specificato.
   * 
   * @param commento Stringa che rappresenta il commento da controllare
   * 
   * @return La stringa che rappresenta il commento da controllare bonificata
   * 
   * @throws CommentoDomandaTirocinioNonValidoException se il commento passato come parametro
   *         è nullo oppure è rappresentato da una stringa con lunghezza minore di 2
   */
  public String validaCommento(String commento) 
        throws CommentoDomandaTirocinioNonValidoException {
    if (commento == null) {
      throw new CommentoDomandaTirocinioNonValidoException();
    } else {
      commento = commento.trim();
      
      if (commento.length() < DomandaTirocinio.MIN_LUNGHEZZA_COMMENTO) {
        throw new CommentoDomandaTirocinioNonValidoException();
      } else {
        return commento;
      }
    }
  }
  
  /**
   * Controlla che la data di inizio tirocinio sia specificata e che rientra nell'intervallo
   * prestabilito.
   * 
   * @param dataInizio LocalDate che rappresenta la data da controllare
   * 
   * @param dataFine LocalDate che rappresenta la data di fine in base alla quale controllare
   *                 la data di inizio tirocinio
   * 
   * @return Oggetto LocalDate che rappresenta la data di inizio da controllare bonificata
   * 
   * @throws DataDiFineTirocinioNonValidaException se la data è nulla o se non rispetta i 
   *         parametri stabiliti
   */
  public LocalDate validaDataDiInizioTirocinio(LocalDate dataInizio, LocalDate dataFine) 
      throws DataDiFineTirocinioNonValidaException {
    if (dataInizio == null) {
      throw new DataDiFineTirocinioNonValidaException();
    } else {
      LocalDate oggi = LocalDate.now();
        
      if (dataInizio.isBefore(oggi) || dataInizio.equals(oggi) || dataInizio.isAfter(dataFine)) {
        throw new DataDiFineTirocinioNonValidaException();
      } else {
        return dataInizio;
      } 
    }
  }
  
  /**
   * Controlla che la data di fine tirocinio sia specificata e che rientra nell'intervallo
   * prestabilito.
   * 
   * @param dataInizio LocalDate che rappresenta la data da controllare
   * 
   * @param dataFine LocalDate che rappresenta la data di fine in base alla quale controllare
   *                 la data di inizio tirocinio
   * 
   * @return Oggetto LocalDate che rappresenta la data di fine da controllare bonificata
   * 
   * @throws DataDiFineTirocinioNonValidaException se la data è nulla o se non rispetta i 
   *         parametri stabiliti
   */
  public LocalDate validaDataDiFineTirocinio(LocalDate dataInizio, LocalDate dataFine) 
      throws DataDiFineTirocinioNonValidaException {
    if (dataFine == null) {
      throw new DataDiFineTirocinioNonValidaException();
    } else {
      LocalDate oggi = LocalDate.now();
        
      if (dataFine.isBefore(oggi) || dataFine.equals(oggi) || dataFine.isBefore(dataInizio)) {
        throw new DataDiFineTirocinioNonValidaException();
      } else {
        return dataFine;
      } 
    }
  }
  
  /**
   * Controlla che il numero di cfu specificato sia rientri nell'intervallo
   * prestabilito.
   * 
   * @param cfu int che rappresenta il numero di cfu da controllare
   * 
   * @return int che rappresenta l'intero da controllare bonificato
   * 
   * @throws NumeroCfuNonValidoException se il numero di cfu non rispetta i 
   *         parametri stabiliti
   */
  public int validaCfu(int cfu) throws NumeroCfuNonValidoException {
    if (cfu < 1 || cfu > 18) {
      throw new NumeroCfuNonValidoException();
    } else {
      return cfu;
    }
  }
  
  /**
   * Controlla che il progetto formativo da associare alla domanda di tirocinio non sia archiviato.
   * 
   * @param progetto {@link ProgettoFormativo} che rappresenta il progetto formativo da controllare
   * 
   * @return Oggetto {@link ProgettoFormativo} che rappresenta il progetto da controllare bonificato
   * 
   * @throws ProgettoFormativoArchiviatoException se il progetto formativo è archiviato
   */
  public ProgettoFormativo verificaStatoProgettoFormativo(ProgettoFormativo progetto) 
      throws ProgettoFormativoArchiviatoException {
    if (progetto.getStatus() == ProgettoFormativo.ARCHIVIATO) {
      throw new ProgettoFormativoArchiviatoException();
    } else {
      return progetto;
    }
  }
  
}
