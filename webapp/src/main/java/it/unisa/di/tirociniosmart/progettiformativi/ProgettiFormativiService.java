package it.unisa.di.tirociniosmart.progettiformativi;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.AziendaRepository;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaNonValidoException;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe che definisce la logica di business per le operazioni possibili nell'ambito delle
 * della gestione dei progetti formativi di un'azienda.
 *
 * @see ProgettoFormativo
 * @see ProgettoFormativoRepository
 */
@Service
public class ProgettiFormativiService {
  
  @Autowired
  private ProgettoFormativoRepository progettoFormativoRepository;
  
  @Autowired
  private AziendaRepository aziendaRepository;
  
  @Autowired
  private UtenzaService utenzaService;

  
  /**
   * Permette di richiedere al sistema il salvataggio di un progetto formativo. La procedura 
   * registra un progetto assegnandogli una {@link Azienda}.
   * 
   * @param progetto {@link ProgettoFormativo} che si vuole aggiungere tra i progetti dell'azienda.
   *        Non è necessario impostare l'azienda che eroga il progetto dato che questa viene
   *        inserita in base al delegato autenticato nel sistema.
   * 
   * @return Il progetto formativo preso come parametro a cui è stato aggiunto anche l'id
   * 
   * @pre progetto != null
   * 
   * @throws RichiestaNonAutorizzataException se l'utente che richiede l'esecuzione del metodo non
   *         è un delegato aziendale
   * 
   * @throws NomeProgettoNonValidoException se il nome del progetto formativo non rientra
   *         nell'intervallo che va da {@link ProgettoFormativo#MIN_LUNGHEZZA_NOME} a 
   *         {@link ProgettoFormativo#MAX_LUNGHEZZA_NOME}
   *         
   * @throws DescrizioneProgettoNonValidaException se la descrizione del progetto formativo non
   *         rispetta il limite minimo di {@link ProgettoFormativo#MIN_LUNGHEZZA_DESCRIZIONE}
   *         caratteri
   */
  @Transactional(rollbackFor = Exception.class)
  public ProgettoFormativo aggiungiProgettoFormativo(ProgettoFormativo progetto)
         throws RichiestaNonAutorizzataException, NomeProgettoNonValidoException,
                DescrizioneProgettoNonValidaException {
    if (!(utenzaService.getUtenteAutenticato() instanceof DelegatoAziendale)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    progetto.setNome(validaNome(progetto.getNome()));
    progetto.setDescrizione(validaDescrizione(progetto.getDescrizione()));
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    DelegatoAziendale delegato = (DelegatoAziendale) utenzaService.getUtenteAutenticato();
    Azienda azienda = delegato.getAzienda();
    progetto.setAzienda(azienda);
   
    progetto = progettoFormativoRepository.save(progetto);
    return progetto;
  }
  
  /**
   * Permette di ottenere la lista dei progetti formativi di un'azienda a partire
   * dall'identificatore di quest'ultima.
   * 
   * @param idAzienda Stringa che rappresenta l'identificatore dell'azienda
   * 
   * @return Lista di oggetti {@link ProgettoFormativo} associati all'azienda che ha idAzienda come
   *         parametro
   *         
   * @throws IdAziendaNonValidoException se l'identificatore passato come parametro non si riferisce
   *         ad alcun azienda
   */
  @Transactional(rollbackFor = Exception.class)
  public List<ProgettoFormativo> elencaProgettiFormativi(String idAzienda) 
      throws IdAziendaNonValidoException {
    if (!aziendaRepository.existsById(idAzienda)) {
      throw new IdAziendaNonValidoException();
    }
    
    List<ProgettoFormativo> progettiFormativi = 
                   progettoFormativoRepository.findAllByStatusAndAziendaId(ProgettoFormativo.ATTIVO,
                                                                           idAzienda);
    
    return progettiFormativi;
  }
  
  /**
   * Permette di archiviare un progetto formativo con un determinato id.
   * 
   * @param idProgetto long che rappresenta l'identificatore del progetto
   *         
   * @return il progetto formativo che è stato archiviato        
   *         
   * @throws IdProgettoFormativoInesistenteException se l'identificatore passato come parametro 
   *         non si riferisce ad alcun progetto
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che richiede l'esecuzione del metodo non
   *         è un delegato aziendale oppure se il progetto identificato da idProgetto non è
   *         associato all'azienda rappresentata dal delegato
   */
  @Transactional(rollbackFor = Exception.class)
  public ProgettoFormativo archiviaProgettoFormativo(long idProgetto) 
      throws IdProgettoFormativoInesistenteException, RichiestaNonAutorizzataException {
    UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
    
    // Solo un delegato aziendale può archiviare un progetto formativo..
    if (!(utente instanceof DelegatoAziendale)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // ..se esiste..
    if (!progettoFormativoRepository.existsById(idProgetto)) {
      throw new IdProgettoFormativoInesistenteException();
    }
    
    DelegatoAziendale delegato = (DelegatoAziendale) utente;
    ProgettoFormativo progetto = progettoFormativoRepository.findById(idProgetto);
    
    // ..e se esso è erogato dall'azienda che egli rappresenta
    if (!delegato.getAzienda().equals(progetto.getAzienda())) {
      throw new RichiestaNonAutorizzataException();
    }
    
    progetto.setStatus(ProgettoFormativo.ARCHIVIATO);
    return progetto;
  }
  
  /**
   * Permette di ottenere un progetto formativo tramite id.
   * 
   * @param id long che rappresenta l'identificatore del progetto
   * 
   * @return l'oggetto ProgettoFormativo
   * 
   * @throws IdProgettoFormativoInesistenteException se l'identificatore passato come parametro
   *         non si riferisce ad alcun progetto
   */
  
  public ProgettoFormativo ottieniProgettoFormativo(Long id) 
         throws IdProgettoFormativoInesistenteException {
    
    // ..se esiste..
    if (!progettoFormativoRepository.existsById(id)) {
      throw new IdProgettoFormativoInesistenteException();
    }
    
    ProgettoFormativo progettoFormativo = progettoFormativoRepository.findById(id);
    
    return progettoFormativo;
  }
  
  
  
  
  
  
  /**
   * Controlla che il nome di un progetto sia specificato e che la sua lunghezza rispetti 
   * i parametri prestabiliti.
   * 
   * @param nome Stringa che rappresenta il nome da controllare
   * 
   * @return La stringa che rappresenta il nome da controllare bonificata
   * 
   * @throws NomeProgettoNonValidoException se il nome è nullo oppure se la sua lunghezza non 
   *         rientra nell'intervallo che va da {@link ProgettoFormativo#MIN_LUNGHEZZA_NOME} a
   *         {@link ProgettoFormativo#MAX_LUNGHEZZA_NOME}
   */
  @Transactional(rollbackFor = Exception.class)
  public String validaNome(String nome) throws NomeProgettoNonValidoException {
    if (nome == null) {
      throw new NomeProgettoNonValidoException();
    } else {
      nome = nome.trim();
      
      if (nome.length() < ProgettoFormativo.MIN_LUNGHEZZA_NOME
          || nome.length() > ProgettoFormativo.MAX_LUNGHEZZA_NOME) {
        throw new NomeProgettoNonValidoException();
      } else {
        return nome;
      }
    }
  }
  
  /**
   * Controlla che la descrizione di un progetto formativo sia specificato.
   * 
   * @param descrizione Stringa che rappresenta la descrizione da controllare
   * 
   * @return La stringa che rappresenta la descrizione da controllare bonificata
   * 
   * @throws DescrizioneProgettoNonValidaException se la descrizione è nulla oppure se la sua
   *         lunghezza è minore di {@link ProgettoFormativo#MAX_LUNGHEZZA_DESCRIZIONE}
   */
  public String validaDescrizione(String descrizione) 
        throws DescrizioneProgettoNonValidaException {
    if (descrizione == null) {
      throw new DescrizioneProgettoNonValidaException();
    } else {
      descrizione = descrizione.trim();
      
      if (descrizione.length() < ProgettoFormativo.MIN_LUNGHEZZA_DESCRIZIONE) {
        throw new DescrizioneProgettoNonValidaException();
      } else {
        return descrizione;
      }
    }
  }

}
