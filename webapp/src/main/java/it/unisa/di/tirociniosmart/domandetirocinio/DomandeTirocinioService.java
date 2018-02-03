package it.unisa.di.tirociniosmart.domandetirocinio;

import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
  
  @Autowired
  private UtenzaService utenzaService;
  
  /**
   * Permette di richiedere al sistema il salvataggio di una domanda di tirocinio. 
   * 
   * @param domanda {@link DomandaTirocinio} che si vuole registrare sul database.
   *                 Non è necessario specificare la data della domanda di tirocinio ad essa
   *                 associata poiché è il metodo stesso ad impostarla.
   *  
   * @return la domanda tirocinio passata come parametro a cui è stato aggiunto anche l'id        
   *                 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         la seguente operazione
   *         
   * @throws DataDiInizioTirocinioNonValidaException se la data è nulla o se non rispetta i 
   *         parametri stabiliti
   *         
   * @throws DataDiFineTirocinioNonValidaException se la data è nulla o se non rispetta i 
   *         parametri stabiliti
   *         
   * @throws NumeroCfuNonValidoException se il numero di cfu non rispetta i parametri stabiliti
   * 
   * @throws CommentoDomandaTirocinioNonValidoException se il commento passato come parametro
   *         è nullo oppure è rappresentato da una stringa con lunghezza minore di 2
   * 
   * @throws ProgettoFormativoArchiviatoException se il progetto formativo è archiviato
   * 
   * @pre domanda != null
   */
  @Transactional(rollbackFor = Exception.class)
  public DomandaTirocinio registraDomandaTirocinio(DomandaTirocinio domanda) 
      throws RichiestaNonAutorizzataException, 
      DataDiInizioTirocinioNonValidaException, 
      DataDiFineTirocinioNonValidaException,
      NumeroCfuNonValidoException,
      CommentoDomandaTirocinioNonValidoException,
      ProgettoFormativoArchiviatoException {
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
    // Solo uno studente può registrare una nuova domanda di tirocinio
    if (!(utente instanceof Studente)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Valida i campi della domanda
    domanda.setInizioTirocinio(validaDataDiInizioTirocinio(domanda.getInizioTirocinio(),
                                                           domanda.getFineTirocinio()));
    domanda.setFineTirocinio(validaDataDiFineTirocinio(domanda.getInizioTirocinio(),
                                                       domanda.getFineTirocinio()));
    domanda.setCfu(validaCfu(domanda.getCfu()));
    domanda.setCommentoStudente(validaCommento(domanda.getCommentoStudente()));
    domanda.setProgettoFormativo(verificaStatoProgettoFormativo(domanda.getProgettoFormativo()));
    
    Studente studente = (Studente) utente;
    domanda.setStudente(studente);
    
    // Imposta stato e data della domanda
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setData(LocalDateTime.now());
    
    // Registra le informazioni
    domanda = domandaRepository.save(domanda);
    return domanda;
    
  }
  
  /**
   * Permette ad un delegato aziendale di accettare una domanda di tirocinio già presente nel 
   * sistema.
   * 
   * @param idDomanda Long che rappresenta l'identificatore della domanda di tirocinio
   *                  da accettare
   * 
   * @return domanda oggetto {@link DomandaTirocinio} che rappresenta la domanda tirocinio che è 
   *                 stata accettata
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
  public DomandaTirocinio accettaDomandaTirocinio(long idDomanda, String commentoAzienda)
         throws IdDomandaTirocinioNonValidoException,
                DomandaTirocinioGestitaException, RichiestaNonAutorizzataException {
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
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
      domanda.setCommentoAzienda(commentoAzienda);
      return domanda;
    }
  }
  
  /**
   * Permette ad un delegato aziendale di rifiutare una domanda di tirocinio già presente nel 
   * sistema.
   * 
   * @param idDomanda Long che rappresenta l'identificatore della domanda di tirocinio
   *                  da rifiutare
   * 
   * @return domanda oggetto {@link DomandaTirocinio} che rappresenta la domanda che è stata 
   *                 rifiutata
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
   * @throws RichiestaNonAutorizzataException se l'utente che richiede il rifiuto di una domanda
   *         non è un delegato aziendale oppure se la domanda non è associata all'azienda
   *         rappresentata dal delegato
   */
  @Transactional(rollbackFor = Exception.class)
  public DomandaTirocinio rifiutaDomandaTirocinio(long idDomanda, String commento)
         throws IdDomandaTirocinioNonValidoException,
                DomandaTirocinioGestitaException,
                CommentoDomandaTirocinioNonValidoException, RichiestaNonAutorizzataException {
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
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
    return domanda;
  }
  
  /**
   * Permette ad un impiegato dell'ufficio tirocini di approvare una domanda di tirocinio già 
   * presente nel sistema.
   * 
   * @param idDomanda Long che rappresenta l'identificatore della domanda di tirocinio
   *                  da respingere
   * 
   * @return domanda ogetto {@link DomandaTirocinio} che rappresenta la domanda di tirocinio che è 
   *                 stata approvata
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
  public DomandaTirocinio approvaDomandaTirocinio(long idDomanda)
         throws IdDomandaTirocinioNonValidoException,
                StatoDomandaNonIdoneoException, RichiestaNonAutorizzataException {
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
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
      return domanda;
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
  public List<DomandaTirocinio> elencaDomandeRicevute()
         throws RichiestaNonAutorizzataException {
    // Ottieni l'utente autenticato nel sistema
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
    if (utente instanceof ImpiegatoUfficioTirocini) {
      // Un impiegato dell'ufficio tirocini può vedere solo le domande accettate
      return domandaRepository.findAllByStatus(DomandaTirocinio.ACCETTATA);
    } else if (utente instanceof DelegatoAziendale) {
      // Un delegato aziendale può vedere solo le domande in attesa
      DelegatoAziendale delegato = (DelegatoAziendale) utente;
      return domandaRepository.findAllByStatusAndProgettoFormativoAziendaId(
                                                                     DomandaTirocinio.IN_ATTESA,
                                                                     delegato.getAzienda().getId());
    } else if (utente instanceof Studente) {
      // Uno studente può vedere le domande di tirocinio in attesa
      Studente studente = (Studente) utente;
      return domandaRepository.findAllByStatusAndStudenteUsername(DomandaTirocinio.IN_ATTESA,
                                                                  studente.getUsername());
    } else {
      throw new RichiestaNonAutorizzataException();
    }
  }
  
  /**
   * Permette di ottenere la lista delle domande inviate, se l'utente autenticato è uno studente;
   * la lista delle domande gestite dall'azienda se l'utente autenticato è un delegato aziendale.
   * 
   * @return La lista delle domande inviate da uno studente oppure la lista delle domande gestite da
   *         un'azienda
   * 
   * @throws RichiestaNonAutorizzataException se l'utente non dispone dei permessi necessari per
   *         l'elaborazione della richiesta
   */
  public List<DomandaTirocinio> elencaDomandeInviate()
         throws RichiestaNonAutorizzataException {
    // Ottieni l'utente autenticato nel sistema
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
    if (utente instanceof DelegatoAziendale) {
      // Un delegato aziendale può vedere solo le domande in attesa
      DelegatoAziendale delegato = (DelegatoAziendale) utente;
      List<DomandaTirocinio> domandeAccettate = domandaRepository
                                                      .findAllByStatusAndProgettoFormativoAziendaId(
                                                                     DomandaTirocinio.ACCETTATA,
                                                                     delegato.getAzienda().getId());
      
      List<DomandaTirocinio> domandeApprovate = domandaRepository
                                                      .findAllByStatusAndProgettoFormativoAziendaId(
                                                                     DomandaTirocinio.APPROVATA,
                                                                     delegato.getAzienda().getId());
      
      List<DomandaTirocinio> domandeRespinte = domandaRepository
                                                      .findAllByStatusAndProgettoFormativoAziendaId(
                                                                     DomandaTirocinio.RESPINTA,
                                                                     delegato.getAzienda().getId());
      
      List<DomandaTirocinio> domandeAzienda = new ArrayList<DomandaTirocinio>();
      domandeAzienda.addAll(domandeAccettate);
      domandeAzienda.addAll(domandeApprovate);
      domandeAzienda.addAll(domandeRespinte);
      
      Collections.sort(domandeAzienda);
      return domandeAzienda;
    } else if (utente instanceof Studente) {
      // Uno studente può vedere le domande di tirocinio in attesa
      Studente studente = (Studente) utente;
      List<DomandaTirocinio> domandeInviate = domandaRepository.findAllByStudenteUsername(
                                                                            studente.getUsername());
      Collections.sort(domandeInviate);
      return domandeInviate;
    } else {
      throw new RichiestaNonAutorizzataException();
    }
    
  }
  
  /**
   * Permette ad un impiegato dell'ufficio tirocinio di respingere una domanda di tirocinio già 
   * presente nel sistema.
   * 
   * @param idDomanda Long che rappresenta l'identificatore della domanda di tirocinio
   *                  da respingere
   * 
   * @return domanda oggetto {@link DomandaTirocinio} che rappresenta la domanda tirocinio che è 
   *         stata respinta
   * 
   * @throws RichiestaNonAutorizzataException se l'utente che richiede di respingere la domanda
   *         non è un impiegato dell'ufficio tirocini
   *         
   * @throws IdDomandaTirocinioNonValidoException se non esiste alcuna domanda di tirocinio
   *         nel sistema con identificatore uguale ad idDomanda
   * 
   * @throws StatoDomandaNonIdoneoException se la domanda identificata da idDomanda
   *         si trova in uno stato diverso da quello di "accettata"
   *         
   * @throws CommentoDomandaTirocinioNonValidoException se il commento da associare alla
   *         domanda è nullo o vuoto
   */
  @Transactional(rollbackFor = Exception.class)
  public DomandaTirocinio respingiDomandaTirocinio(long idDomanda, String commento)
         throws IdDomandaTirocinioNonValidoException, StatoDomandaNonIdoneoException,
                CommentoDomandaTirocinioNonValidoException, RichiestaNonAutorizzataException {
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
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
    return domanda;
  }
  
  /**
   * Controlla che il commento sul rifiuto o respinta di una richiesta sia specificato.
   * 
   * @param commento Stringa che rappresenta il commento da controllare
   * 
   * @return commento Stringa che rappresenta il commento da controllare bonificata
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
   * @throws DataDiInizioTirocinioNonValidaException se la data è nulla o se non rispetta i 
   *         parametri stabiliti
   */
  public LocalDate validaDataDiInizioTirocinio(LocalDate dataInizio, LocalDate dataFine) 
      throws DataDiInizioTirocinioNonValidaException {
    if (dataInizio == null) {
      throw new DataDiInizioTirocinioNonValidaException();
    } else {
      LocalDate oggi = LocalDate.now();

      if (dataInizio.isBefore(oggi) || dataInizio.equals(oggi)) {
        throw new DataDiInizioTirocinioNonValidaException();
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
      if (dataFine.isBefore(dataInizio)) {
        throw new DataDiFineTirocinioNonValidaException();
      } else {
        return dataFine;
      } 
    }
  }
  
  /**
   * Controlla che il numero di cfu specificato  rientri nell'intervallo prestabilito.
   * 
   * @param cfu int che rappresenta il numero di cfu da controllare
   * 
   * @return int che rappresenta l'intero da controllare bonificato
   * 
   * @throws NumeroCfuNonValidoException se il numero di cfu non rientra nel range che va da 
   *         {@link DomandaTirocinio#MIN_CFU} a {@link DomandaTirocinio#MAX_CFU}
   */
  public int validaCfu(Integer cfu) throws NumeroCfuNonValidoException {
    if (cfu == null) {
      throw new NumeroCfuNonValidoException();
    }
    
    if (cfu < DomandaTirocinio.MIN_CFU || cfu > DomandaTirocinio.MAX_CFU) {
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
   * @return progetto Oggetto {@link ProgettoFormativo} che rappresenta il progetto da controllare 
   *         bonificato
   * 
   * @throws ProgettoFormativoArchiviatoException se il progetto formativo si trova nello stato
   *         {@link ProgettoFormativo#ARCHIVIATO}
   */
  public ProgettoFormativo verificaStatoProgettoFormativo(ProgettoFormativo progetto) 
      throws ProgettoFormativoArchiviatoException {
    if (progetto.getStatus() == ProgettoFormativo.ARCHIVIATO) {
      throw new ProgettoFormativoArchiviatoException();
    } else {
      return progetto;
    }
  }
  
  
  /**
   * Permette di ottenere la lista dei tirocini in corso nella data corrente in base al periodo
   * di tirocinio specificato nelle varie domande.
   * 
   * @return Lista {@link DomandaTirocinio} il cui periodo inizia prima della data odierna e
   *         termina dopo di essa
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che richiede di visualizzare l'elenco dei
   *         tirocini in corso non è dotato dei permessi necessari per l'operazione
   */
  public List<DomandaTirocinio> elencaTirociniInCorso()
         throws RichiestaNonAutorizzataException {
    LocalDate giorno = LocalDate.now();
    return elencaTirociniInCorso(giorno);
  }
  
  /**
   * Permette di ottenere la lista dei tirocini in corso in una determinata data in base al periodo
   * di tirocinio specificato nelle varie domande.
   * 
   * @param giorno LocalDate che indica il giorno di cui si vogliono ottenere i tirocini in corso.
   * 
   * @return Lista delle domande di tirocinio il cui periodo inizia prima della data odierna e
   *         termina dopo di essa
   *         
   * @pre giorno != null
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che richiede di visualizzare l'elenco dei
   *         tirocini in corso non è dotato dei permessi necessari per l'operazione
   */
  public List<DomandaTirocinio> elencaTirociniInCorso(LocalDate giorno)
         throws RichiestaNonAutorizzataException {    
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
    List<DomandaTirocinio> domandeApprovate;
    
    if (utente instanceof DelegatoAziendale) {
      DelegatoAziendale delegato = (DelegatoAziendale) utente;
      domandeApprovate = domandaRepository
                       .findAllByStatusAndProgettoFormativoAziendaId(DomandaTirocinio.APPROVATA,
                                                                     delegato.getAzienda().getId());
    } else if (utente instanceof Studente) {
      Studente studente = (Studente) utente;
      domandeApprovate = domandaRepository
                                     .findAllByStatusAndStudenteUsername(
                                                                         DomandaTirocinio.APPROVATA,
                                                                         studente.getUsername());
    } else if (utente instanceof ImpiegatoUfficioTirocini) {
      domandeApprovate = domandaRepository.findAllByStatus(DomandaTirocinio.APPROVATA);
    } else {
      throw new RichiestaNonAutorizzataException();
    }
    
    List<DomandaTirocinio> tirociniInCorso = new ArrayList<DomandaTirocinio>();
    for (DomandaTirocinio domanda : domandeApprovate) {
      if ((domanda.getInizioTirocinio().isBefore(giorno)
          || domanda.getInizioTirocinio().isEqual(giorno))
          && domanda.getFineTirocinio().isAfter(giorno)) {
        tirociniInCorso.add(domanda);
      }
    }
    
    return tirociniInCorso;
  }
}
