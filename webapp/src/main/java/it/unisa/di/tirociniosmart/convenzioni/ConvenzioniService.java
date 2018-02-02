package it.unisa.di.tirociniosmart.convenzioni;

import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.utenza.CognomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.EmailEsistenteException;
import it.unisa.di.tirociniosmart.utenza.EmailNonValidaException;
import it.unisa.di.tirociniosmart.utenza.NomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.PasswordNonValidaException;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.SessoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.TelefonoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UsernameEsistenteException;
import it.unisa.di.tirociniosmart.utenza.UsernameNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe che definisce la logica di business per le operazioni possibili nell'ambito delle
 * convenzioni.
 *
 * @see Azienda
 * @see AziendaRepository
 * @see RichiestaConvenzionamentoRepository
 */
@Service
public class ConvenzioniService {

  @Autowired
  private AziendaRepository aziendaRepository;
  
  @Autowired
  private RichiestaConvenzionamentoRepository richiestaConvenzionamentoRepository;
  
  @Autowired
  private UtenzaService utenzaService;
  
  /**
   * Permette di richiedere al sistema il salvataggio di un'azienda. La procedura registra l'azienda
   * assegnandole una {@link RichiestaConvenzionamento} inizialmente in attesa.
   * 
   * @param azienda {@link Azienda} per cui si vuole registrare una richiesta di convenzionamento.
   *                Non è necessario specificare la data della richiesta di convenzionamento ad essa
   *                associata né la lista dei progetti formativi offerti poiché vengono impostati da
   *                questo metodo.
   *                
   * @return l'azienda presa come parametro a cui è stata associato l'id alla richiesta di 
   *         convenzionamento corrispondete               
   *                
   * @throws RichiestaNonAutorizzataException se l'utente non è autorizzato ad eseguire la seguente 
   *         operazione
   *         
   * @throws UsernameEsistenteException se l'username specificato è già presente nel sistema
   * 
   * @throws UsernameNonValidoException se l'username non è specificato oppure se non rispetta il
   *         formato {@link UtenteRegistrato#USERNAME_PATTERN}
   * 
   * @throws PasswordNonValidaException se la password non è specificata oppure se non rispetta il
   *         formato {@link UtenteRegistrato#PASSWORD_PATTERN} 
   * 
   * @throws EmailEsistenteException se l'e-mail specificata è già presente nel sistema
   * 
   * @throws EmailNonValidaException se l'e-mail non è specificata oppure se non rispetta il formato
   *         {@link UtenteRegistrato#EMAIL_PATTERN}
   * 
   * @throws NomeNonValidoException se il nome è nullo oppure se la sua lunghezza non rientra
   *         nell'intervallo che va da {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *         {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   * 
   * @throws CognomeNonValidoException se il cognome è nullo oppure se la sua lunghezza non rientra
   *         nell'intervallo che va da {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *         {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   * 
   * @throws TelefonoNonValidoException se il numero di telefono del delegato non è specificato o
   *         non rispetta il formato definito in {@link UtenteRegistrato#TELEFONO_PATTERN}
   * 
   * @throws SessoNonValidoException se il sesso del delegato non è una delle costanti
   *         {@link UtenteRegistrato#SESSO_MASCHILE} e {@link UtenteRegistrato#SESSO_FEMMINILE}
   * 
   * @throws PartitaIvaAziendaNonValidaException se la partita IVA passata come parametro è nulla
   *         oppure se non rispetta il formato {@link Azienda#PARTITA_IVA_PATTERN}
   * 
   * @throws PartitaIvaAziendaEsistenteException se la partita IVA passata come parametro è già
   *         presente nel sistema
   * 
   * @throws NomeAziendaNonValidoException se il nome passato come parametro è nullo o se la sua
   *         lunghezza non rientra nell'intervallo che va da
   *         {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *         {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   * 
   * @throws IdAziendaNonValidoException se l'identificatore passato come parametro è nullo oppure
   *         se non rispetta il formato {@link Azienda#ID_PATTERN}
   * 
   * @throws IdAziendaEsistenteException se l'id specificato è già presente nel sistema
   * 
   * @throws CommentoRichiestaConvenzionamentoNonValidoException se il commento passato come 
   *         parametro è nullo oppure è rappresentato da una stringa di lunghezza minore di 2
   * 
   * @throws IndirizzoAziendaNonValidoException se l'indirizzo dell'azienda è nullo o se la sua
   *         lunghezza non rientra nell'intervallo che va da {@link Azienda#MIN_LUNGHEZZA_INDIRIZZO}
   *         a {@link Azienda#MAX_LUNGHEZZA_INDIRIZZO}
   * 
   * @pre azienda != null
   */
  @Transactional(rollbackFor = Exception.class)
  public Azienda registraRichiestaConvenzionamento(Azienda azienda) 
      throws IndirizzoAziendaNonValidoException,
      PartitaIvaAziendaNonValidaException,
      PartitaIvaAziendaEsistenteException,
      NomeAziendaNonValidoException, 
      IdAziendaNonValidoException,
      IdAziendaEsistenteException, 
      CommentoRichiestaConvenzionamentoNonValidoException,
      RichiestaNonAutorizzataException, 
      UsernameNonValidoException, 
      UsernameEsistenteException, 
      PasswordNonValidaException, 
      EmailNonValidaException, 
      EmailEsistenteException, 
      NomeNonValidoException, 
      CognomeNonValidoException, 
      TelefonoNonValidoException, 
      SessoNonValidoException {
    // Un utente già autenticato nel sistema non può inviare un nuova richiesta di convenzionamento
    if (utenzaService.getUtenteAutenticato() != null) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Valida i campi dell'azienda
    azienda.setId(validaIdAzienda(azienda.getId()));
    azienda.setPartitaIva(validaPartitaIvaAzienda(azienda.getPartitaIva()));
    azienda.setNome(validaNomeAzienda(azienda.getNome()));
    azienda.setIndirizzo(validaIndirizzoAzienda(azienda.getIndirizzo()));
    
    // Valida i campi del delegato aziendale
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername(utenzaService.validaUsername(delegato.getUsername()));
    delegato.setPassword(utenzaService.validaPassword(delegato.getPassword()));
    delegato.setEmail(utenzaService.validaEmail(delegato.getEmail()));
    delegato.setNome(utenzaService.validaNome(delegato.getNome()));
    delegato.setCognome(utenzaService.validaCognome(delegato.getCognome()));
    delegato.setTelefono(utenzaService.validaTelefono(delegato.getTelefono()));
    delegato.setSesso(utenzaService.validaSesso(delegato.getSesso()));
    
    // Imposta stato e data della richiesta
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.now());
    
    // Registra le informazioni
    azienda = aziendaRepository.save(azienda);
    return azienda;
  }
  
  /**
   * Permette di approvare una richiesta di convenzionamento già presente nel sistema.
   * 
   * @param idRichiesta Long che rappresenta l'identificatore della richiesta di convenzionamento
   *                    da approvare
   *                    
   * @return richiesta oggetto {@link RichiestaConvenzionamento} che rappresenta la richiesta di 
   *         convenzionamento che è stata approvata
   * 
   * @throws IdRichiestaConvenzionamentoNonValidoException se non esiste alcuna richiesta di
   *         convenzionamento nel sistema con identificatore uguale ad idRichiesta
   * 
   * @throws RichiestaConvenzionamentoGestitaException se la richiesta identificata da idRichiesta
   *         si trova in uno stato diverso da quello in attesa
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che richiede l'approvazione non è un
   *         impiegato dell'ufficio tirocini
   */
  @Transactional(rollbackFor = Exception.class)
  public RichiestaConvenzionamento approvaRichiestaConvenzionamento(long idRichiesta)
         throws IdRichiestaConvenzionamentoNonValidoException,
                RichiestaConvenzionamentoGestitaException, RichiestaNonAutorizzataException {
    // Le richieste di convenzionamento possono essere approvate solo dagli impiegati dell'ufficio
    // tirocini
    if (!(utenzaService.getUtenteAutenticato() instanceof ImpiegatoUfficioTirocini)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la richiesta esista
    RichiestaConvenzionamento richiesta = richiestaConvenzionamentoRepository.findById(idRichiesta);
    if (richiesta == null) {
      throw new IdRichiestaConvenzionamentoNonValidoException();
    }
    
    // Controlla che la richiesta non sia già stata gestita in precedenza
    if (richiesta.getStatus() != RichiestaConvenzionamento.IN_ATTESA) {
      throw new RichiestaConvenzionamentoGestitaException();
    } else {
      richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
      return richiesta;
    }
    
  }
  
  /**
   * Permette di rifiutare una richiesta di convenzionamento già presente nel sistema.
   * 
   * @param idRichiesta Long che rappresenta l'identificatore della richiesta di convenzionamento
   *                    da rifiutare
   * 
   * @return  richiesta oggetto {@link RichiestaConvenzionamento} che rappresenta la richiesta 
   *          di convenzionamento che è stata rifiutata
   * 
   * @throws IdRichiestaConvenzionamentoNonValidoException se non esiste alcuna richiesta di
   *         convenzionamento nel sistema con identificatore uguale ad idRichiesta
   * 
   * @throws RichiestaConvenzionamentoGestitaException se la richiesta identificata da idRichiesta
   *         si trova in uno stato diverso da quello in attesa
   *         
   * @throws CommentoRichiestaConvenzionamentoNonValidoException se il commento da associare alla
   *         richiesta è nullo o vuoto
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che richiede l'approvazione non è un
   *         impiegato dell'ufficio tirocini 
   */
  @Transactional(rollbackFor = Exception.class)
  public RichiestaConvenzionamento rifiutaRichiestaConvenzionamento(long idRichiesta,
                                                                    String commento)
         throws IdRichiestaConvenzionamentoNonValidoException,
                RichiestaConvenzionamentoGestitaException,
                CommentoRichiestaConvenzionamentoNonValidoException,
                RichiestaNonAutorizzataException {
    // Le richieste di convenzionamento possono essere rifiutate solo dagli impiegati dell'ufficio
    // tirocini
    if (!(utenzaService.getUtenteAutenticato() instanceof ImpiegatoUfficioTirocini)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la richiesta esista
    RichiestaConvenzionamento richiesta = richiestaConvenzionamentoRepository.findById(idRichiesta);
    if (richiesta == null) {
      throw new IdRichiestaConvenzionamentoNonValidoException();
    }
    
    // Controlla che la richiesta non sia già stata gestita in precedenza ed impostane lo stato
    if (richiesta.getStatus() != RichiestaConvenzionamento.IN_ATTESA) {
      throw new RichiestaConvenzionamentoGestitaException();
    } else {
      richiesta.setStatus(RichiestaConvenzionamento.RIFIUTATA);
    }
    
    validaCommento(commento, richiesta);
    return richiesta;
  }
  
  /**
   * Controlla che il commento sul rifiuto di una richiesta sia specificato.
   * 
   * @param commento Stringa che rappresenta il commento da controllare
   * 
   * @param richiesta {@link RichiestaConvenzionamento} che rapprensenta la richiesta a cui 
   *        associare il commento
   * 
   * @return La stringa che rappresenta il commento da controllare bonificata
   * 
   * @throws CommentoRichiestaConvenzionamentoNonValidoException se il commento passato come 
   *         parametro è nullo oppure è rappresentato da una stringa di lunghezza minore di 2
   */
  public String validaCommento(String commento, RichiestaConvenzionamento richiesta) 
      throws CommentoRichiestaConvenzionamentoNonValidoException {
    if (commento == null) {
      throw new CommentoRichiestaConvenzionamentoNonValidoException();
    } else {
      commento = commento.trim();
      
      if (commento.length() < RichiestaConvenzionamento.MIN_LUNGHEZZA_COMMENTO) {
        throw new CommentoRichiestaConvenzionamentoNonValidoException();
      } else {
        richiesta.setCommentoUfficioTirocini(commento);
        return commento;
      }
    }
  
  }
  
  /**
   * Permette di ottenere la lista delle richieste di convenzionamento non ancora approvate o 
   * rifiutate.
   * 
   * @return Lista di {@link RichiestaConvenzionamento} il cui status è "in attesa"
   * 
   * @throws RichiestaNonAutorizzataException se l'utente che tenta di visualizzare le richieste
   *         di convenzionamento in attesa non è un impiegato dell'ufficio tirocini
   */
  @Transactional
  public List<RichiestaConvenzionamento> elencaRichiesteConvenzionamentoInAttesa()
         throws RichiestaNonAutorizzataException {
    // Le richieste di convenzionamento possono essere visualizzate solo dagli impiegati
    // dell'ufficio tirocini
    if (!(utenzaService.getUtenteAutenticato() instanceof ImpiegatoUfficioTirocini)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    return richiestaConvenzionamentoRepository.findAllByStatus(RichiestaConvenzionamento.IN_ATTESA);
  }
  
  /**
   * Permette di ottenere la lista delle aziende convenzionate.
   * 
   * @return Lista di {@link Azienda} le cui richieste di convenzionamento sono state approvate
   */
  @Transactional
  public List<Azienda> elencaAziendeConvenzionate() {
    return aziendaRepository.findAllByRichiestaConvenzionamentoStatus(
                                                               RichiestaConvenzionamento.APPROVATA);
  }
  
  /**
   * Permette di ottenere un'azienda presente nel sistema a partire dal suo identificatore.
   * 
   * @param idAzienda Stringa che rappresenta l'identificatore dell'azienda che si vuole ottenere
   * 
   * @return {@link Azienda} che rappresenta l'azienda cercata
   */
  @Transactional(rollbackFor = Exception.class)
  public Azienda ottieniAzienda(String idAzienda) throws IdAziendaNonValidoException {
    Azienda azienda = aziendaRepository.findById(idAzienda);
    
    if (azienda == null) {
      throw new IdAziendaNonValidoException();
    }
    
    return azienda;
  }
  
  /**
   * Controlla che l'identificatore di un'azienda sia specificato e che rispetti il formato
   * prestabilito. Controlla inoltre che tale identificatore non sia già presente nel sistema.
   * 
   * @param idAzienda Stringa che rappresenta l'identificatore da controllare
   * 
   * @return La stringa che rappresenta l'identificatore da controllare bonificata
   * 
   * @throws IdAziendaNonValidoException se l'identificatore passato come parametro è nullo oppure
   *         se non rispetta il formato {@link Azienda#ID_PATTERN}
   *         
   * @throws IdAziendaEsistenteException se l'identificatore passato come parametro è già presente
   *         nel sistema
   */
  public String validaIdAzienda(String idAzienda)
         throws IdAziendaNonValidoException, IdAziendaEsistenteException {
    // Controlla che il parametro non sia nullo, quindi effettua il trim e verifica che rispetti il
    // formato prestabilito e che non sia presente nel database
    if (idAzienda == null) {
      throw new IdAziendaNonValidoException();
    } else {
      idAzienda = idAzienda.trim();
      
      if (!idAzienda.matches(Azienda.ID_PATTERN)) {
        throw new IdAziendaNonValidoException();
      } else if (aziendaRepository.existsById(idAzienda)) {
        throw new IdAziendaEsistenteException();
      } else {
        return idAzienda;
      }
    }
  }
  
  /**
   * Controlla che il nome di un'azienda sia specificato e che la sua lunghezza rispetti i parametri
   * prestabiliti.
   * 
   * @param nomeAzienda Stringa che rappresenta il nome da controllare
   * 
   * @return La stringa che rappresenta il nome da controllare bonificata
   * 
   * @throws NomeAziendaNonValidoException se il nome passato come parametro è nullo o se la sua
   *         lunghezza non rientra nell'intervallo che va da
   *         {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *         {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   */
  public String validaNomeAzienda(String nomeAzienda) throws NomeAziendaNonValidoException {
    if (nomeAzienda == null) {
      throw new NomeAziendaNonValidoException();
    } else {
      nomeAzienda = nomeAzienda.trim();
      
      if (nomeAzienda.length() < Azienda.MIN_LUNGHEZZA_NOME
          || nomeAzienda.length() > Azienda.MAX_LUNGHEZZA_NOME) {
        throw new NomeAziendaNonValidoException();
      } else {
        return nomeAzienda;
      }
    }
  }
  
  /**
   * Controlla che la partita IVA di un'azienda sia specificata e che rispetti il formato
   * prestabilito. Controlla inoltre che tale partita IVA non sia già presente nel sistema.
   * 
   * @param partitaIvaAzienda Stringa che rappresenta la partita iva da controllare
   * 
   * @return La stringa che rappresenta la partita IVA da controllare bonificata
   * 
   * @throws PartitaIvaAziendaNonValidaException se la partita IVA passata come parametro è nulla
   *         oppure se non rispetta il formato {@link Azienda#PARTITA_IVA_PATTERN}
   * 
   * @throws PartitaIvaAziendaEsistenteException se la partita IVA passata come parametro è già
   *         presente nel sistema
   */
  public String validaPartitaIvaAzienda(String partitaIvaAzienda)
         throws PartitaIvaAziendaNonValidaException, PartitaIvaAziendaEsistenteException {
    if (partitaIvaAzienda == null) {
      throw new PartitaIvaAziendaNonValidaException();
    } else {
      partitaIvaAzienda = partitaIvaAzienda.trim();
      
      if (!partitaIvaAzienda.matches(Azienda.PARTITA_IVA_PATTERN)) {
        throw new PartitaIvaAziendaNonValidaException();
      } else if (aziendaRepository.existsByPartitaIva(partitaIvaAzienda)) {
        throw new PartitaIvaAziendaEsistenteException();
      } else {
        return partitaIvaAzienda;
      }
    }
  }
  
  /**
   * Controlla che l'indirizzo di un'azienda sia specificato e che la sua lunghezza rispetti i
   * parametri prestabiliti.
   * 
   * @param indirizzoAzienda Stringa che rappresenta l'indirizzo da controllare
   * 
   * @return La stringa che rappresenta l'indirizzo da controllare bonificata
   * 
   * @throws IndirizzoAziendaNonValidoException se l'indirizzo dell'azienda è nullo o se la sua
   *         lunghezza non rientra nell'intervallo che va da {@link Azienda#MIN_LUNGHEZZA_INDIRIZZO}
   *         a {@link Azienda#MAX_LUNGHEZZA_INDIRIZZO}
   */
  public String validaIndirizzoAzienda(String indirizzoAzienda)
         throws IndirizzoAziendaNonValidoException {
    if (indirizzoAzienda == null) {
      throw new IndirizzoAziendaNonValidoException();
    } else {
      indirizzoAzienda = indirizzoAzienda.trim();
      
      if (indirizzoAzienda.length() < Azienda.MIN_LUNGHEZZA_INDIRIZZO
          || indirizzoAzienda.length() > Azienda.MAX_LUNGHEZZA_INDIRIZZO) {
        throw new IndirizzoAziendaNonValidoException();
      } else {
        return indirizzoAzienda;
      } 
    }
  }
  
}
