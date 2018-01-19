package it.unisa.di.tirociniosmart.convenzioni;

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
   * @pre azienda != null
   */
  @Transactional(rollbackFor = Exception.class)
  public void registraRichiestaConvenzionamento(Azienda azienda) throws Exception {
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
    aziendaRepository.save(azienda);
  }
  
  /**
   * Permette di approvare una richiesta di convenzionamento già presente nel sistema.
   * 
   * @param idRichiesta Long che rappresenta l'identificatore della richiesta di convenzionamento
   *                    da approvare
   * 
   * @throws IdRichiestaConvenzionamentoNonValidoException se non esiste alcuna richiesta di
   *         convenzionamento nel sistema con identificatore uguale ad idRichiesta
   * 
   * @throws RichiestaConvenzionamentoGestitaException se la richiesta identificata da idRichiesta
   *         si trova in uno stato diverso da quello in attesa
   */
  @Transactional(rollbackFor = Exception.class)
  public void approvaRichiestaConvenzionamento(long idRichiesta)
         throws IdRichiestaConvenzionamentoNonValidoException,
                RichiestaConvenzionamentoGestitaException {
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
    }
  }
  
  /**
   * Permette di rifiutare una richiesta di convenzionamento già presente nel sistema.
   * 
   * @param idRichiesta Long che rappresenta l'identificatore della richiesta di convenzionamento
   *                    da rifiutare
   * 
   * @throws IdRichiestaConvenzionamentoNonValidoException se non esiste alcuna richiesta di
   *         convenzionamento nel sistema con identificatore uguale ad idRichiesta
   * 
   * @throws RichiestaConvenzionamentoGestitaException se la richiesta identificata da idRichiesta
   *         si trova in uno stato diverso da quello in attesa
   *         
   * @throws CommentoRichiestaConvenzionamentoNonValidoException se il commento da associare alla
   *         richiesta è nullo o vuoto
   */
  @Transactional(rollbackFor = Exception.class)
  public void rifiutaRichiestaConvenzionamento(long idRichiesta, String commento)
         throws IdRichiestaConvenzionamentoNonValidoException,
                RichiestaConvenzionamentoGestitaException,
                CommentoRichiestaConvenzionamentoNonValidoException {
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
   *         parametro è nullo oppure è rappresentato da stringa vuota
   */
  public String validaCommento(String commento, RichiestaConvenzionamento richiesta) 
      throws CommentoRichiestaConvenzionamentoNonValidoException {
    if (commento == null) {
      throw new CommentoRichiestaConvenzionamentoNonValidoException();
    } else {
      commento = commento.trim();
      
      if (commento.equals("")) {
        throw new CommentoRichiestaConvenzionamentoNonValidoException();
      } else {
        richiesta.setCommentoUfficioTirocini(commento);
        return commento;
      }
    }
  
  }
  
  /**
   * Permette di ottenere la lista delle richieste di convenzionamento non ancora gestite.
   * 
   * @return Lista di {@link RichiestaConvenzionamento} il cui status è "in attesa"
   */
  @Transactional
  public List<RichiestaConvenzionamento> elencaRichiesteConvenzionamentoInAttesa() {
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
  
  public Azienda ottieniAzienda(String idAzienda) throws IdAziendaNonValidoException {
    Azienda azienda = aziendaRepository.findById(idAzienda);
    
    if(azienda == null) {
      throw new IdAziendaNonValidoException();
    } 
    
    return azienda;
  }
  
}
