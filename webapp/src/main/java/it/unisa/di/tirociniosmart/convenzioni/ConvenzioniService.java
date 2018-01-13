package it.unisa.di.tirociniosmart.convenzioni;

import it.unisa.di.tirociniosmart.utenza.CognomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.EmailEsistenteException;
import it.unisa.di.tirociniosmart.utenza.EmailNonValidaException;
import it.unisa.di.tirociniosmart.utenza.NomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.PasswordNonValidaException;
import it.unisa.di.tirociniosmart.utenza.SessoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.TelefonoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UsernameEsistenteException;
import it.unisa.di.tirociniosmart.utenza.UsernameNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistratoService;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  private UtenteRegistratoService utenteService;
  
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
   * 
   * @throws IdAziendaNonValidoException se l'identificatore dell'azienda è null o non rispetta il
   *         formato definito in {@link Azienda#ID_PATTERN}
   * 
   * @throws IdAziendaEsistenteException se l'identificatore dell'azienda è già presente nel sistema
   * 
   * @throws PartitaIvaAziendaNonValidaException se la partita IVA dell'azienda è nulla o non
   *         rispetta il formato definito in {@link Azienda#PARTITA_IVA_PATTERN}
   * 
   * @throws PartitaIvaAziendaEsistenteException se la partita IVA dell'azienda è già presente nel
   *         sistema
   * 
   * @throws NomeAziendaNonValidoException se il nome dell'azienda è nullo o se la sua lunghezza
   *         non rientra nell'intervallo che va dal valore di {@link Azienda#MIN_LUNGHEZZA_NOME} a
   *         quello di {@link Azienda#MAX_LUNGHEZZA_NOME}
   * 
   * @throws IndirizzoAziendaNonValidoException se l'indirizzo dell'azienda è nullo o se la sua
   *         lunghezza non rientra nell'intervallo che va da {@link Azienda#MIN_LUNGHEZZA_INDIRIZZO}
   *         a {@link Azienda#MAX_LUNGHEZZA_INDIRIZZO}
   * 
   * @throws DelegatoNonValidoException se il delegato dell'azienda è nullo
   * 
   * @throws UsernameNonValidoException se l'username del delegato non è specificato oppure se non
   *         rispetta il formato {@link UtenteRegistrato#USERNAME_PATTERN}
   * 
   * @throws UsernameEsistenteException se l'username del delegato è già presente nel sistema
   * 
   * @throws PasswordNonValidaException se la password del delegato non è specificata oppure se non
   *         rispetta il formato {@link UtenteRegistrato#PASSWORD_PATTERN}
   * 
   * @throws EmailNonValidaException se l'e-mail del delegato non è specificata oppure se non
   *         rispetta il formato {@link UtenteRegistrato#EMAIL_PATTERN}
   * 
   * @throws EmailEsistenteException se l'e-mail del delegato è gia presente nel sistema
   * 
   * @throws NomeNonValidoException se il nome del delegato è nullo oppure se la sua lunghezza non
   *         rientra nell'intervallo che va da {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *         {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   * 
   * @throws CognomeNonValidoException se il cognome del delegato è nullo oppure se la sua lunghezza
   *         on rientra nell'intervallo che va da {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} a
   *         {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME}
   *         
   * @throws SessoNonValidoException se il sesso del delegato non è una delle costanti
   *         {@link UtenteRegistrato#SESSO_MASCHILE} e {@link UtenteRegistrato#SESSO_FEMMINILE}
   *         
   * @throws TelefonoNonValidoException se il numero di telefono del delegato non è specificato o
   *         non rispetta il formato definito in {@link UtenteRegistrato#TELEFONO_PATTERN}
   */
  @Transactional
  public void registraRichiestaConvenzionamento(Azienda azienda)
         throws PartitaIvaAziendaNonValidaException, IdAziendaNonValidoException,
                IdAziendaEsistenteException, PartitaIvaAziendaEsistenteException,
                NomeAziendaNonValidoException, IndirizzoAziendaNonValidoException,
                DelegatoNonValidoException, UsernameNonValidoException, UsernameEsistenteException,
                PasswordNonValidaException, EmailNonValidaException, EmailEsistenteException,
                NomeNonValidoException, CognomeNonValidoException, SessoNonValidoException,
                TelefonoNonValidoException {
    // Controlla che l'id sia specificato, rispetti il formato, non sia già presente nel sistema
    // ed assegnalo all'azienda (poiché ne è stato effettuato il trim)
    String idAzienda = azienda.getId();
    if (idAzienda == null) {
      throw new IdAziendaNonValidoException();
    } else {
      idAzienda = idAzienda.trim();
      
      if (!idAzienda.matches(Azienda.ID_PATTERN)) {
        throw new IdAziendaNonValidoException();
      } else if (aziendaRepository.existsById(idAzienda)) {
        throw new IdAziendaEsistenteException();
      } else {
        azienda.setId(idAzienda);
      }
    }
    
    // Controlla che la partita IVA sia specificata, rispetti il formato, non sia già presente nel
    // sistema ed assegnala all'azienda (poiché ne è stato effettuato il trim)
    String partitaIvaAzienda = azienda.getPartitaIva();
    if (partitaIvaAzienda == null) {
      throw new PartitaIvaAziendaNonValidaException();
    } else {
      partitaIvaAzienda = partitaIvaAzienda.trim();
      
      if (!partitaIvaAzienda.matches(Azienda.PARTITA_IVA_PATTERN)) {
        throw new PartitaIvaAziendaNonValidaException();
      } else if (aziendaRepository.existsByPartitaIva(partitaIvaAzienda)) {
        throw new PartitaIvaAziendaEsistenteException();
      } else {
        azienda.setPartitaIva(partitaIvaAzienda);
      }
    }
    
    // Controlla che il nome sia specificato, che la sua lunghezza rispetti i parametri prefissati
    // ed assegnalo all'azienda (poiché ne è stato effettuato il trim)
    String nomeAzienda = azienda.getNome();
    if (nomeAzienda == null) {
      throw new NomeAziendaNonValidoException();
    } else {
      nomeAzienda = nomeAzienda.trim();
      
      if (nomeAzienda.length() < Azienda.MIN_LUNGHEZZA_NOME
          || nomeAzienda.length() > Azienda.MAX_LUNGHEZZA_NOME) {
        throw new NomeAziendaNonValidoException();
      } else {
        azienda.setIndirizzo(nomeAzienda);
      }
    }
    
    // Controlla che l'indirizzo sia specificato, che la sua lunghezza rispetti i parametri
    // prefissati ed assegnalo all'azienda (poiché ne è stato effettuato il trim)
    String indirizzoAzienda = azienda.getIndirizzo();
    if (indirizzoAzienda == null) {
      throw new IndirizzoAziendaNonValidoException();
    } else {
      indirizzoAzienda = indirizzoAzienda.trim();
      
      if (indirizzoAzienda.length() < Azienda.MIN_LUNGHEZZA_INDIRIZZO
          || indirizzoAzienda.length() > Azienda.MAX_LUNGHEZZA_INDIRIZZO) {
        throw new IndirizzoAziendaNonValidoException();
      } else {
        azienda.setIndirizzo(indirizzoAzienda);
      } 
    }
    
    // Controlla che il delegato aziendale sia ben formato
    DelegatoAziendale delegato = azienda.getDelegato();
    if (delegato == null) {
      throw new DelegatoNonValidoException();
    } else {
      delegato = (DelegatoAziendale) utenteService.validaUtente(delegato);
      
      char sesso = delegato.getSesso();
      if (sesso != UtenteRegistrato.SESSO_MASCHILE && sesso != UtenteRegistrato.SESSO_FEMMINILE) {
        throw new SessoNonValidoException();
      }
      
      String telefono = delegato.getTelefono();
      if (telefono == null) {
        throw new TelefonoNonValidoException();
      } else {
        telefono = telefono.trim();
        
        if (!telefono.matches(UtenteRegistrato.TELEFONO_PATTERN)) {
          throw new TelefonoNonValidoException();
        } else {
          delegato.setTelefono(telefono);
        }
      }
    }
    
    // Imposta stato e data della richiesta
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.now());
    
    // Registra la richiesta
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
  @Transactional
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
  @Transactional
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
    
    // Controlla che il commento sia stato specificato ed associalo alla richiesta
    if (commento == null) {
      throw new CommentoRichiestaConvenzionamentoNonValidoException();
    } else {
      commento = commento.trim();
      
      if (commento.equals("")) {
        throw new CommentoRichiestaConvenzionamentoNonValidoException();
      } else {
        richiesta.setCommentoUfficioTirocini(commento);
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
}
