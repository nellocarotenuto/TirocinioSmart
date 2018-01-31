package it.unisa.di.tirociniosmart.convenzioni;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

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
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Classe che definisce i casi di test per i servizi offerti da ConvenzioniService. 
 *
 * @see ConvenzioniService
 */
@RunWith(MockitoJUnitRunner.class)
public class ConvenzioneServiceTest {

  @InjectMocks
  private ConvenzioniService convenzioniService;
  
  @Mock
  private AziendaRepository aziendaRepository;
  
  @Mock
  private RichiestaConvenzionamentoRepository richiestaConvenzionamentoRepository;
  
  @Mock
  private UtenzaService utenzaService;
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se la richiesta viene registrata con successo
   */
  @Test
  public void registraRichiestaConvenzionamento() {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se la viene lanciata l'eccezione aspettata
   */
  @Test(expected =  RichiestaNonAutorizzataException.class)
  public void registraRichiestaConvenzionamentoRichiestaNonAutorizzata() 
      throws RichiestaNonAutorizzataException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo ci sia un delegato autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException 
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws IndirizzoAziendaNonValidoException se la sua lunghezza è minore di
   *         {@link Azienda#MIN_LUNGHEZZA_INDIRIZZO} 
   */
  @Test(expected = IndirizzoAziendaNonValidoException.class)
  public void registraRichiestaConvenzionamentoConIndirizzoNonValido()
      throws IndirizzoAziendaNonValidoException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws IndirizzoAziendaNonValidoException se la sua lunghezza è maggiore di 
   *         {@link Azienda#MAX_LUNGHEZZA_INDIRIZZO}
   */
  @Test(expected = IndirizzoAziendaNonValidoException.class)
  public void registraRichiestaConvenzionamentoConIndirizzoNonValidoCasoDue()
      throws IndirizzoAziendaNonValidoException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("abcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzab"
        + "abcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilm"
        + "nopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzab"
        + "abcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdcdabcdefghi"
        + "lmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvz"
        + "abcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilm"
        + "nopqrstuvzabcdabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrst");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws IndirizzoAziendaNonValidoException se l'indirizzo è null
   */
  @Test(expected = IndirizzoAziendaNonValidoException.class)
  public void registraRichiestaConvenzionamentoConIndirizzoNullo()
      throws IndirizzoAziendaNonValidoException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws PartitaIvaAziendaNonValidaException se la partita IVA passata come parametro non 
   *         rispetta il formato {@link Azienda#PARTITA_IVA_PATTERN}
   */
  @Test(expected = PartitaIvaAziendaNonValidaException.class)
  public void registraRichiestaConvenzionamentoConPartitaIvaNonValida() 
      throws PartitaIvaAziendaNonValidaException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("012367890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException 
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws PartitaIvaAziendaNonValidaException se la partita IVA passata come parametro è null
   */
  @Test(expected = PartitaIvaAziendaNonValidaException.class)
  public void registraRichiestaConvenzionamentoConPartitaIvaNulla() 
      throws PartitaIvaAziendaNonValidaException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException 
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws PartitaIvaAziendaEsistenteException se la partita IVA è già presente sul database
   */
  @Test(expected = PartitaIvaAziendaEsistenteException.class)
  public void registraRichiestaConvenzionamentoConPartitaIvaEsistente() 
      throws PartitaIvaAziendaEsistenteException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    //Stabilisce che la partita Iva sia già presente nel database
    when(aziendaRepository.existsByPartitaIva("01234567890")).thenReturn(true);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws NomeAziendaNonValidoException se il nome passato come parametro ha una lunghezza  
   *         minore di {@link UtenteRegistrato#MIN_LUNGHEZZA_NOME} 
   */
  @Test(expected = NomeAziendaNonValidoException.class)
  public void registraRichiestaConvenzionamentoConNomeNonValidoCasoUno() 
      throws NomeAziendaNonValidoException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException 
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws NomeAziendaNonValidoException se il nome passato come parametro ha una lunghezza  
   *         maggiore di {@link UtenteRegistrato#MAX_LUNGHEZZA_NOME} 
   */
  @Test(expected = NomeAziendaNonValidoException.class)
  public void registraRichiestaConvenzionamentoConNomeNonValidoCasoDue() 
      throws NomeAziendaNonValidoException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("abcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcd "
        + "abcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilm"
        + "nopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzab"
        + "abcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdcdabcdefghi"
        + "lmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvz"
        + "abcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilm"
        + "nopqrstuvzabcdabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrstuvzabcdabcdefghilmnopqrst");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException 
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws NomeAziendaNonValidoException se il nome passato come parametro è null
   */
  @Test(expected = NomeAziendaNonValidoException.class)
  public void registraRichiestaConvenzionamentoConNomeNullo() 
      throws NomeAziendaNonValidoException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException 
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws IdAziendaNonValidoException se l'identificatore passato come parametro non rispetta
   *         il formato {@link Azienda#ID_PATTERN}
   */
  @Test(expected =  IdAziendaNonValidoException.class)
  public void registraRichiestaConvenzionamentoConIdNonValido() throws IdAziendaNonValidoException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws IdAziendaNonValidoException se l'identificatore passato come parametro è nullo
   */
  @Test(expected =  IdAziendaNonValidoException.class)
  public void registraRichiestaConvenzionamentoConIdNullo() throws IdAziendaNonValidoException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette di memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se viene lanciata l'eccezione aspettata
   */
  @Test(expected = IdAziendaEsistenteException.class)
  public void registraRichiestaConvenzionamentoConIdEsistente() throws IdAziendaEsistenteException {
    
    //Crea Azienda
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Grand Canyon");
    
    //Associa delegato aziendale ad azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Associa richiesta ad azienda
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setDataRichiesta(LocalDateTime.now());
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    
    //Stabilisce che l'id sia già presente nel database
    when(aziendaRepository.existsById("acmeltd")).thenReturn(true);
    
    try {
      //Permette di memorizzare su database la richiesta di convenzionamento
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException 
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo per ottenere un'azienda a partire dal proprio id.
   * 
   * @test {@link ConvenzioniService#ottieniAzienda(String)}
   * 
   * @result Il test è superato se l'azienda ricercata è caricata correttamente
   */
  @Test
  public void testOttieniAzienda() {
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("indirizzo");
    azienda.setNome("azienda");
    azienda.setPartitaIva("12345678912");
    azienda.setSenzaBarriere(true);
    
    when(aziendaRepository.findById("azienda")).thenReturn(azienda);
    
    try {
      assertThat(azienda, is(equalTo(convenzioniService.ottieniAzienda("azienda"))));
    } catch (IdAziendaNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Testa il metodo per ottenere un'azienda a partire dal proprio id.
   * 
   * @test {@link ConvenzioniService#ottieniAzienda(String)}
   * 
   * @result Il test è superato se il metodo lancia l'eccezione aspettata
   * 
   * @throws IdAziendaNonValidoException se se non esiste alcuna azienda nel sistema
   *         con un determinato identificatore
   */
  @Test(expected = IdAziendaNonValidoException.class)
  public void testOttieniAziendaIdNonValido() throws IdAziendaNonValidoException {
    
    when(aziendaRepository.findById("ciao")).thenReturn(null);
    assertThat(null, is(equalTo(convenzioniService.ottieniAzienda("ciao"))));

  }
  
  /**
   * Testa il metodo per approvare una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#approvaRichiestaConvenzionamento(long)}
   * 
   * @result Il test è superato se il metodo lancia l'eccezione aspettata
   * 
   * @throws IdRichiestaConvenzionamentoNonValidoException se non esiste alcuna richiesta di 
   *         convenzionamento nel sistema con un determinato identificatore
   */
  @Test(expected = IdRichiestaConvenzionamentoNonValidoException.class)
  public void testApprovaIdRichiestaConvenzionamentoNonValido() 
      throws IdRichiestaConvenzionamentoNonValidoException {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Rossi");
    impiegato.setEmail("antonio@rossi.com");
    impiegato.setUsername("antonio");
    impiegato.setPassword("antonio");
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(null);
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      convenzioniService.approvaRichiestaConvenzionamento(1111111111111111L);
    } catch (RichiestaConvenzionamentoGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo per approvare una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#approvaRichiestaConvenzionamento(long)}
   * 
   * @result Il test è superato se il metodo lancia l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         la seguente operazione
   */
  @Test(expected =  RichiestaNonAutorizzataException.class)
  public void testApprovaRichiestaNonAutorizzataException()
      throws RichiestaNonAutorizzataException {
    
    DelegatoAziendale delegato = new DelegatoAziendale();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    RichiestaConvenzionamento r = new RichiestaConvenzionamento();
    r.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    r.setDataRichiesta(LocalDateTime.now());
    r.setCommentoUfficioTirocini("ciaooo");
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(r);
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    try {
      convenzioniService.approvaRichiestaConvenzionamento(1111111111111111L);
    } catch (IdRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaConvenzionamentoGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo per approvare una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#approvaRichiestaConvenzionamento(long)}
   * 
   * @result Il test è superato se la richiesta viene approvata con successo 
   * 
   */
  @Test
  public void approvaRichiesta() {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Rossi");
    impiegato.setEmail("antonio@rossi.com");
    impiegato.setUsername("antonio");
    impiegato.setPassword("antonio");
    
    RichiestaConvenzionamento r = new RichiestaConvenzionamento();
    r.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    r.setDataRichiesta(LocalDateTime.now());
    r.setCommentoUfficioTirocini("ciaooo");
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(r);
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      convenzioniService.approvaRichiestaConvenzionamento(1111111111111111L);
    } catch (IdRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaConvenzionamentoGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Testa il metodo per approvare una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#approvaRichiestaConvenzionamento(long)}
   * 
   * @result Il test è superato se il metodo lancia l'eccezione aspettata
   * 
   * @throws RichiestaConvenzionamentoGestitaException se la richiesta di convenzionamento è stata 
   *         già approvata o rifiutata
   */
  @Test(expected =  RichiestaConvenzionamentoGestitaException.class)
  public void approvaRichiestaGestita() throws RichiestaConvenzionamentoGestitaException {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Rossi");
    impiegato.setEmail("antonio@rossi.com");
    impiegato.setUsername("antonio");
    impiegato.setPassword("antonio");
    
    RichiestaConvenzionamento r = new RichiestaConvenzionamento();
    r.setStatus(RichiestaConvenzionamento.APPROVATA);
    r.setDataRichiesta(LocalDateTime.now());
    r.setCommentoUfficioTirocini("ciaooo");
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(r);
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      convenzioniService.approvaRichiestaConvenzionamento(1111111111111111L);
    } catch (IdRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo per ottenere una lista di richiesta di convenzionamento il cui status è 
   * in attesa.
   * 
   * @test {@link ConvenzioniService#elencaRichiesteConvenzionamentoInAttesa()}
   * 
   * @result Il test è superato se l'elenco delle richieste è caricato correttamente
   */
  @Test
  public void elencaRichiesteConvenzionamentoInAttesa() {
    
    RichiestaConvenzionamento richiesta1 = new RichiestaConvenzionamento();
    richiesta1.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta1.setDataRichiesta(LocalDateTime.now());
    richiesta1.setCommentoUfficioTirocini("commento richiesta uno");
    
    List<RichiestaConvenzionamento> listaRichieste = new ArrayList<RichiestaConvenzionamento>();
    listaRichieste.add(richiesta1);
    
    RichiestaConvenzionamento richiesta2 = new RichiestaConvenzionamento();
    richiesta2.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta2.setDataRichiesta(LocalDateTime.now());
    richiesta2.setCommentoUfficioTirocini("commento richiesta due");
    
    listaRichieste.add(richiesta2);
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Rossi");
    impiegato.setEmail("antonio@rossi.com");
    impiegato.setUsername("antonio");
    impiegato.setPassword("antonio");
    
    when(richiestaConvenzionamentoRepository.findAllByStatus(RichiestaConvenzionamento.IN_ATTESA))
        .thenReturn(listaRichieste);
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      assertThat(listaRichieste, is(equalTo(convenzioniService
                                                      .elencaRichiesteConvenzionamentoInAttesa())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Testa il metodo per ottenere una lista di richiesta di convenzionamento il cui status è 
   * in attesa.
   * 
   * @test {@link ConvenzioniService#elencaRichiesteConvenzionamentoInAttesa()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         la seguente operazione
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void elencaRichiesteNonImp() throws RichiestaNonAutorizzataException {
 
    
    RichiestaConvenzionamento richiesta1 = new RichiestaConvenzionamento();
    richiesta1.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta1.setDataRichiesta(LocalDateTime.now());
    richiesta1.setCommentoUfficioTirocini("commento richiesta uno");
    
    List<RichiestaConvenzionamento> listaRichieste = new ArrayList<RichiestaConvenzionamento>();
    
    listaRichieste.add(richiesta1);
    
    RichiestaConvenzionamento richiesta2 = new RichiestaConvenzionamento();
    richiesta2.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta2.setDataRichiesta(LocalDateTime.now());
    richiesta2.setCommentoUfficioTirocini("commento richiesta due");
    
    listaRichieste.add(richiesta2);
    
    DelegatoAziendale delegato = new DelegatoAziendale();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    when(richiestaConvenzionamentoRepository.findAllByStatus(RichiestaConvenzionamento.IN_ATTESA))
        .thenReturn(listaRichieste);
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    assertThat(listaRichieste, is(equalTo(convenzioniService
                                                      .elencaRichiesteConvenzionamentoInAttesa())));
  }
  
  /**
   * Testa il metodo per ottenere l'elenco delle aziende convenzionate con l'Università.
   * 
   * @test {@link ConvenzioniService#elencaAziendeConvenzionate()}
   * 
   * @result Il test è superato se l'elenco delle aziende convenzionate è caricato correttamente
   * 
   */
  @Test
  public void elencaAziendeConvenzionate() {
    
    // Crea l'azienda #1 ed inseriscila in lista
    Azienda azienda1 = new Azienda();
    azienda1.setId("acmeltd");
    azienda1.setNome("ACME Ltd.");
    azienda1.setPartitaIva("01234567890");
    azienda1.setSenzaBarriere(true);
    azienda1.setIndirizzo("Grand Canyon");
   
    RichiestaConvenzionamento richiesta1 = azienda1.getRichiesta();
    richiesta1.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
   
    List<Azienda> listaAziende = new ArrayList<Azienda>();
    listaAziende.add(azienda1);
    
    
    // Crea l'azienda #2 ed inseriscila in lista
    Azienda azienda2 = new Azienda();
    azienda2.setId("starkind");
    azienda2.setNome("Stark Industries");
    azienda2.setPartitaIva("74598763241");
    azienda2.setSenzaBarriere(true);
    azienda2.setIndirizzo("Marvel Valley, 45");
    
    RichiestaConvenzionamento richiesta2 = azienda2.getRichiesta();
    richiesta2.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta2.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    listaAziende.add(azienda2);
    
    
    // Crea l'azienda #3 ed inseriscila in lista
    Azienda azienda3 = new Azienda();
    azienda3.setId("cyberdynecorp");
    azienda3.setNome("Cyberdyne System Corporation");
    azienda3.setPartitaIva("54569814752");
    azienda3.setSenzaBarriere(false);
    azienda3.setIndirizzo("Steel Mountain, 57");
    
    RichiestaConvenzionamento richiesta3 = azienda3.getRichiesta();
    richiesta3.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta3.setDataRichiesta(LocalDateTime.of(2017, 12, 31, 23, 59));
   
    listaAziende.add(azienda3);
    
    when(aziendaRepository.findAllByRichiestaConvenzionamentoStatus(
                                     RichiestaConvenzionamento.APPROVATA)).thenReturn(listaAziende);
    assertThat(listaAziende, is(equalTo(convenzioniService.elencaAziendeConvenzionate())));    
  }
  
  /**
   * Testa il metodo per rifiutare una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#rifiutaRichiestaConvenzionamento(long, String)}
   * 
   * @result Il test è superato se la richiesta viene rifiuta con successo 
   * 
   */
  @Test
  public void rifiutaRichiesta() {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Rossi");
    impiegato.setEmail("antonio@rossi.com");
    impiegato.setUsername("antonio");
    impiegato.setPassword("antonio");
    
    RichiestaConvenzionamento r = new RichiestaConvenzionamento();
    r.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    r.setDataRichiesta(LocalDateTime.now());
    r.setCommentoUfficioTirocini("ciaooo");
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(r);
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      convenzioniService.rifiutaRichiestaConvenzionamento(1111111111111111L, "ciaooo");
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException
        | CommentoRichiestaConvenzionamentoNonValidoException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo per rifiutare una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#rifiutaRichiestaConvenzionamento(long, String)}
   * 
   * @result Il test è superato se il metodo lancia l'eccezione aspettata
   * 
   * @throws IdRichiestaConvenzionamentoNonValidoException se non esiste alcuna richiesta di 
   *         convenzionamento nel sistema con un determinato identificatore
   */
  @Test(expected = IdRichiestaConvenzionamentoNonValidoException.class)
  public void testRifiutaIdRichiestaConvenzionamentoNonValido() 
      throws IdRichiestaConvenzionamentoNonValidoException {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Rossi");
    impiegato.setEmail("antonio@rossi.com");
    impiegato.setUsername("antonio");
    impiegato.setPassword("antonio");
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(null);
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      convenzioniService.rifiutaRichiestaConvenzionamento(1111111111111111L, "ciao");
    } catch (RichiestaConvenzionamentoGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo per rifiutare una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#rifiutaRichiestaConvenzionamento(long, String)}
   * 
   * @result Il test è superato se il metodo lancia l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         la seguente operazione
   */
  @Test(expected =  RichiestaNonAutorizzataException.class)
  public void testRifiutaRichiestaNonAutorizzataException()
      throws RichiestaNonAutorizzataException {
    
    DelegatoAziendale delegato = new DelegatoAziendale();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    RichiestaConvenzionamento r = new RichiestaConvenzionamento();
    r.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    r.setDataRichiesta(LocalDateTime.now());
    r.setCommentoUfficioTirocini("ciaooo");
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(r);
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    try {
      convenzioniService.rifiutaRichiestaConvenzionamento(1111111111111111L, "ciaooo");
    } catch (IdRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaConvenzionamentoGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo per rifiuta una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#rifiutaRichiestaConvenzionamento(long, String)}
   * 
   * @result Il test è superato se il metodo lancia l'eccezione aspettata
   * 
   * @throws RichiestaConvenzionamentoGestitaException se la richiesta di convenzionamento è stata 
   *         già approvata o rifiutata
   */
  @Test(expected =  RichiestaConvenzionamentoGestitaException.class)
  public void rifiutaRichiestaGestita() throws RichiestaConvenzionamentoGestitaException {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Rossi");
    impiegato.setEmail("antonio@rossi.com");
    impiegato.setUsername("antonio");
    impiegato.setPassword("antonio");
    
    RichiestaConvenzionamento r = new RichiestaConvenzionamento();
    r.setStatus(RichiestaConvenzionamento.APPROVATA);
    r.setDataRichiesta(LocalDateTime.now());
    r.setCommentoUfficioTirocini("ciaooo");
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(r);
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      convenzioniService.rifiutaRichiestaConvenzionamento(1111111111111111L, "ciaooo");
    } catch (IdRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo per rifiuta una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#rifiutaRichiestaConvenzionamento(long, String)}
   * 
   * @result Il test è superato se il metodo lancia l'eccezione aspettata
   * 
   * @throws CommentoRichiestaConvenzionamentoNonValidoException se il commento non rispetta il 
   *         pattern stabilito
   *        
   */
  @Test(expected =  CommentoRichiestaConvenzionamentoNonValidoException.class)
  public void rifiutaRichiestaConCommentoNonValido() 
      throws CommentoRichiestaConvenzionamentoNonValidoException {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Rossi");
    impiegato.setEmail("antonio@rossi.com");
    impiegato.setUsername("antonio");
    impiegato.setPassword("antonio");
    
    RichiestaConvenzionamento r = new RichiestaConvenzionamento();
    r.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    r.setDataRichiesta(LocalDateTime.now());
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(r);
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      convenzioniService.rifiutaRichiestaConvenzionamento(1111111111111111L, "");
    } catch (IdRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaConvenzionamentoGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo per rifiuta una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#rifiutaRichiestaConvenzionamento(long, String)}
   * 
   * @result Il test è superato se il metodo lancia l'eccezione aspettata
   * 
   * @throws CommentoRichiestaConvenzionamentoNonValidoException se il commento è null 
   *        
   */
  @Test(expected =  CommentoRichiestaConvenzionamentoNonValidoException.class)
  public void rifiutaRichiestaConCommentoNullo() 
      throws CommentoRichiestaConvenzionamentoNonValidoException {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Rossi");
    impiegato.setEmail("antonio@rossi.com");
    impiegato.setUsername("antonio");
    impiegato.setPassword("antonio");
    
    RichiestaConvenzionamento r = new RichiestaConvenzionamento();
    r.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    r.setDataRichiesta(LocalDateTime.now());
    
    when(richiestaConvenzionamentoRepository.findById(1111111111111111L)).thenReturn(r);
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      convenzioniService.rifiutaRichiestaConvenzionamento(1111111111111111L, null);
    } catch (IdRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaConvenzionamentoGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
}
