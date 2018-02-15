package it.unisa.di.tirociniosmart.domandetirocinio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Classe che definisce i casi di test per i servizi offerti da {@link DomandeTirocinioService}. 
 *
 * @see DomandeTirocinioService 
 */
@RunWith(MockitoJUnitRunner.class)
public class DomandeTirocinioServiceTest {

  @InjectMocks
  private DomandeTirocinioService domandeService;
  
  @Mock
  private DomandaTirocinioRepository domandaRepository;
  
  @Mock
  private UtenzaService utenzaService;

  /**
   * Testa il metodo che permette ad un delegato aziendale di accettare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#accettaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se la domanda viene accettata con successo
   * 
   */
  @Test
  public void testAccettaDomandaTirocinio() {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.accettaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di accettare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#accettaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws IdDomandaTirocinioNonValidoException se non esiste alcuna domanda di tirocinio con 
   *         l'identificatore specificato
   * 
   */
  @Test(expected = IdDomandaTirocinioNonValidoException.class)
  public void testAccettaDomandaConIdNonValido() throws IdDomandaTirocinioNonValidoException {
 
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    //permette di cercare la domanda con l'id specificato restituendo null
    when(domandaRepository.findById(11111L)).thenReturn(null);
    try {
      domandeService.accettaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di accettare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#accettaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws DomandaTirocinioGestitaException se la domanda di tirocinio si trova in uno stato 
   *         diverso dallo stato {@link DomandaTirocinio#IN_ATTESA}
   * 
   */
  @Test(expected = DomandaTirocinioGestitaException.class)
  public void testAccettaDomandaGestita() throws DomandaTirocinioGestitaException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.ACCETTATA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.accettaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di accettare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#accettaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         tale operazione
   * 
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void testAccettaDomandaRichiestaNonAutorizzataCasoDue() 
      throws RichiestaNonAutorizzataException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Crea azienda #2
    Azienda azienda2 = new Azienda();
    azienda2.setId("azienda2");
    azienda2.setIndirizzo("indirizzo");
    azienda2.setNome("Azienda2");
    azienda2.setPartitaIva("56478295687");
    azienda2.setSenzaBarriere(true);
    
    //crea delegato #2
    DelegatoAziendale delegato2 = azienda2.getDelegato();
    delegato2.setNome("Anna");
    delegato2.setCognome("Vicidomini");
    delegato2.setEmail("anna@vicidomini.com");
    delegato2.setSesso(DelegatoAziendale.SESSO_FEMMINILE);
    delegato2.setTelefono("3333333333");
    delegato2.setUsername("delegato2");
    delegato2.setPassword("delegato2");
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato2);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.accettaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di accettare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#accettaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         tale operazione
   * 
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void testAccettaDomandaRichiestaNonAutorizzata() throws RichiestaNonAutorizzataException {
    
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un impiegato dell'ufficio tirocini
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.accettaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di rifiutare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#rifiutaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se la domanda viene rifiutata con successo
   * 
   */
  @Test
  public void testRifiutaDomandaTirocinio() {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.rifiutaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage()); 
      e.printStackTrace();
    } catch (DomandaTirocinioGestitaException e) {
      fail(e.getMessage()); 
      e.printStackTrace();
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());   
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());  
      e.printStackTrace();
    }   
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di rifiutare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#rifiutaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws IdDomandaTirocinioNonValidoException se non esiste alcuna domanda di tirocinio con 
   *         l'identificatore specificato
   * 
   */
  @Test(expected = IdDomandaTirocinioNonValidoException.class)
  public void testRfiutaDomandaConIdNonValido() throws IdDomandaTirocinioNonValidoException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    //permette di cercare la domanda con l'id specificato restituendo null
    when(domandaRepository.findById(11111L)).thenReturn(null);
    try {
      domandeService.rifiutaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }  
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di rifiutare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#rifiutaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws DomandaTirocinioGestitaException se la domanda di tirocinio si trova in uno stato 
   *         diverso dallo stato {@link DomandaTirocinio#IN_ATTESA}
   * 
   */
  @Test(expected = DomandaTirocinioGestitaException.class)
  public void testRifiutaDomandaGestita() throws DomandaTirocinioGestitaException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.ACCETTATA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.rifiutaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di rifiutare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#rifiutaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         tale operazione
   * 
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void testRifiutaDomandaRichiestaNonAutorizzata() throws RichiestaNonAutorizzataException {
    
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un impiegato dell'ufficio tirocini
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.rifiutaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di rifiutare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#rifiutaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         tale operazione
   * 
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void testRifiutaDomandaRichiestaNonAutorizzataCasoDue()
      throws RichiestaNonAutorizzataException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Crea azienda #2
    Azienda azienda2 = new Azienda();
    azienda2.setId("azienda2");
    azienda2.setIndirizzo("indirizzo");
    azienda2.setNome("Azienda2");
    azienda2.setPartitaIva("56478295687");
    azienda2.setSenzaBarriere(true);
    
    //crea delegato #2
    DelegatoAziendale delegato2 = azienda2.getDelegato();
    delegato2.setNome("Anna");
    delegato2.setCognome("Vicidomini");
    delegato2.setEmail("anna@vicidomini.com");
    delegato2.setSesso(DelegatoAziendale.SESSO_FEMMINILE);
    delegato2.setTelefono("3333333333");
    delegato2.setUsername("delegato2");
    delegato2.setPassword("delegato2");
    
    //Permette di stabilire che l'operazione è eseguita da un delegato aziendale
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato2);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.rifiutaDomandaTirocinio(11111L, "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di rifiutare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#rifiutaDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws CommentoDomandaTirocinioNonValidoException se il commento dell'azienda associato alla 
   *         domanda di tirocinio non è valido
   * 
   */
  @Test(expected = CommentoDomandaTirocinioNonValidoException.class)
  public void testRifiutaDomandaConCommentoNonValido() 
      throws CommentoDomandaTirocinioNonValidoException {
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.rifiutaDomandaTirocinio(11111L, "");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'Ufficio Tirocini di accettare una 
   * domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#approvaDomandaTirocinio(long)}
   * 
   * @result Il test è superato se la domanda viene approvata con successo
   * 
   */
  @Test
  public void testApprovaDomandaTirocinio() {
    
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.ACCETTATA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.approvaDomandaTirocinio(11111L);
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (StatoDomandaNonIdoneoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocini di approvare 
   * una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#approvaDomandaTirocinio(long)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws IdDomandaTirocinioNonValidoException se non esiste alcuna domanda di tirocinio con 
   *         l'identificatore specificato
   * 
   */
  @Test(expected = IdDomandaTirocinioNonValidoException.class)
  public void testApprovaDomandaConIdNonValido() throws IdDomandaTirocinioNonValidoException {
 
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.ACCETTATA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un impiegato dell'ufficio tirocini
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    //permette di cercare la domanda con l'id specificato restituendo null
    when(domandaRepository.findById(11111L)).thenReturn(null);
    try {
      domandeService.approvaDomandaTirocinio(11111L);
    } catch (StatoDomandaNonIdoneoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocini di rifiutare 
   * una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#approvaDomandaTirocinio(long)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         tale operazione
   * 
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void testApprovaDomandaRichiestaNonAutorizzata() throws RichiestaNonAutorizzataException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato aziendale
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.approvaDomandaTirocinio(11111L);
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (StatoDomandaNonIdoneoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocinio di approvare 
   * una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#approvaDomandaTirocinio(long)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws StatoDomandaNonIdoneoException se la domanda di tirocinio si trova in uno stato 
   *         diverso dallo stato {@link DomandaTirocinio#ACCETTATA}
   * 
   */
  @Test(expected = StatoDomandaNonIdoneoException.class)
  public void testApprovaDomandaConStatoNonIdoneo() throws StatoDomandaNonIdoneoException {
    
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.RIFIUTATA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.approvaDomandaTirocinio(11111L);
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocini di respingere 
   * una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#respingiDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se la domanda viene respinta con successo
   * 
   */
  @Test
  public void testRespingiDomandaTirocinio() {
    
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.ACCETTATA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un impiegato dell'Ufficio tirocini
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.respingiDomandaTirocinio(11111L, "commentoImpiegato");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (StatoDomandaNonIdoneoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocini di respingere 
   * una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#respingiDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws IdDomandaTirocinioNonValidoException se non esiste alcuna domanda di tirocinio con 
   *         l'identificatore specificato
   * 
   */
  @Test(expected = IdDomandaTirocinioNonValidoException.class)
  public void testRespingiDomandaConIdNonValido() throws IdDomandaTirocinioNonValidoException {
    
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.ACCETTATA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un impiegato dell'ufficio tirocini
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    //permette di cercare la domanda con l'id specificato restituendo null
    when(domandaRepository.findById(11111L)).thenReturn(null);
    try {
      domandeService.respingiDomandaTirocinio(11111L, "commentoImpiegato");
    } catch (StatoDomandaNonIdoneoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocinio di respingere 
   * una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#respingiDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws StatoDomandaNonIdoneoException se la domanda di tirocinio si trova in uno stato 
   *         diverso dallo stato {@link DomandaTirocinio#ACCETTATA}
   * 
   */
  @Test(expected = StatoDomandaNonIdoneoException.class)
  public void testRespingiDomandaConStatoNonIdoneo() throws StatoDomandaNonIdoneoException {
    
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.RIFIUTATA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.respingiDomandaTirocinio(11111L, "commentoImpiegato");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocini di respingere 
   * una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#respingiDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         tale operazione
   * 
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void testRespingiDomandaRichiestaNonAutorizzata() throws RichiestaNonAutorizzataException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un delegato aziendale
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.respingiDomandaTirocinio(11111L, "commentoImpiegato");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (StatoDomandaNonIdoneoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }  
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocini di respingere 
   * una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#respingiDomandaTirocinio(long, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws CommentoDomandaTirocinioNonValidoException se il commento dell'impiegato associato  
   *         alla domanda di tirocinio non è valido
   * 
   */
  @Test(expected = CommentoDomandaTirocinioNonValidoException.class)
  public void testRespingiDomandaConCommentoNonValido() 
      throws CommentoDomandaTirocinioNonValidoException {
    
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.ACCETTATA);
    domanda.setProgettoFormativo(progetto);
    
    //Permette di stabilire che l'operazione è eseguita da un impiegato dell'Ufficio tirocini
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    //permette di cercare la domanda con l'id specificato
    when(domandaRepository.findById(11111L)).thenReturn(domanda);
    try {
      domandeService.respingiDomandaTirocinio(11111L, "");
    } catch (IdDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (StatoDomandaNonIdoneoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }   
  }
  
  /** 
   * Testa il metodo per ottenere l'elenco delle domande {@link DomandaTirocinio#IN_ATTESA}
   * per uno studente.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeRicevute()}
   * 
   * @result Il test è superato se l'elenco delle domande viene caricato correttamente
   * 
   */
  @Test
  public void elencaDomandeRicevuteStudente() {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //Crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda2.setProgettoFormativo(progetto);
    
    //Crea lista delle domande IN_ATTESA
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
    
    //Permette di stabilire che il metodo findAllByStatusAndStudenteUsername deve restituire
    //lista listaDomande
    when(domandaRepository.findAllByStatusAndStudenteUsername(DomandaTirocinio.IN_ATTESA,
        "FrancescoF")).thenReturn(listaDomande);
    //Permette di stabilire che l'utente autenticato che svolge l'operazione è uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    
    try {
      //Verifica che gli oggetti presenti nelle liste siano uguali
      assertThat(listaDomande, is(equalTo(domandeService.elencaDomandeRicevute())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   *  Testa il metodo per ottenere l'elenco delle domande {@link DomandaTirocinio#IN_ATTESA}
   *  pervenute ad un delegato.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeRicevute()}
   * 
   * @result Il test è superato se l'elenco delle domande viene caricato correttamente
   * 
   */
  @Test
  public void elencaDomandeRicevuteDelegato() {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda2.setProgettoFormativo(progetto);
    
    //Crea la lista contenente le domande IN_ATTESA di essere accettate o rifiutate
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
    
    //Stabilisce che il metodo findAllByStatusAndProgettoFormativoAziendaId restituisca 
    //la lista listaDomande
    when(domandaRepository.findAllByStatusAndProgettoFormativoAziendaId(DomandaTirocinio.IN_ATTESA,
        "azienda")).thenReturn(listaDomande);
    //Permette di stabile che l'utente autenticato che svolge l'operazione è un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    try {
      //Controlla che gli oggetti nelle liste siano uguali
      assertThat(listaDomande, is(equalTo(domandeService.elencaDomandeRicevute())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /** 
   * Testa il metodo per ottenere l'elenco delle domande {@link DomandaTirocinio#ACCETTATA}
   * per un impiegato dell'ufficio tirocini.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeRicevute()}
   * 
   * @result Il test è superato se l'elenco delle domande viene caricato correttamente
   * 
   */
  @Test
  public void elencaDomandeRicevuteImpiegato() {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea studente #2 
    Studente studente2 = new Studente();
    studente2.setNome("Antonia");
    studente2.setCognome("Facchinetti");
    studente2.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente2.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente2.setEmail("antonia@facchinetti.com");
    studente2.setIndirizzo("Via francesco, 9");
    studente2.setMatricola("0512103834");
    studente2.setTelefono("3331234183");
    studente2.setSesso(Studente.SESSO_FEMMINILE);
    studente2.setUsername("AntoniaF");
    studente2.setPassword("AntoniaF");
    
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
     
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.ACCETTATA);
    domanda.setProgettoFormativo(progetto);
    
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente2);
    domanda2.setStatus(DomandaTirocinio.ACCETTATA);
    domanda2.setProgettoFormativo(progetto);
    
    //Crea lista delle domande accettate
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
    
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    //Stabilisce che il metodo findAllByStatus restituisca la lista listaDomande
    when(domandaRepository.findAllByStatus(DomandaTirocinio.ACCETTATA)).thenReturn(listaDomande);
    //Stabilisce che l'utente autenticato sia un impiegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      //Verifica che gli oggetti contenuti nelle due liste siano uguali
      assertThat(listaDomande, is(equalTo(domandeService.elencaDomandeRicevute())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /** 
   * Testa il metodo per ottenere l'elenco delle domande {@link DomandaTirocinio#ACCETATA}
   * per un impiegato dell'ufficio tirocini.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeRicevute()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
   *         l'operazione
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void elencaDomandeRicevuteRichiestaNonAutorizzata() 
      throws RichiestaNonAutorizzataException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea studente #2 
    Studente studente2 = new Studente();
    studente2.setNome("Antonia");
    studente2.setCognome("Facchinetti");
    studente2.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente2.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente2.setEmail("antonia@facchinetti.com");
    studente2.setIndirizzo("Via francesco, 9");
    studente2.setMatricola("0512103834");
    studente2.setTelefono("3331234183");
    studente2.setSesso(Studente.SESSO_FEMMINILE);
    studente2.setUsername("AntoniaF");
    studente2.setPassword("AntoniaF");
    
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.ACCETTATA);
    domanda.setProgettoFormativo(progetto);
    
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente2);
    domanda2.setStatus(DomandaTirocinio.ACCETTATA);
    domanda2.setProgettoFormativo(progetto);
    
    //Crea lista contenente le domande accettate
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
    
    //Stabilisce che il metodo findAllByStatus restituisca la lista listaDomande
    when(domandaRepository.findAllByStatus(DomandaTirocinio.ACCETTATA)).thenReturn(listaDomande);
    //Stabilisce che non ci sia alcun utente autenticato al momento dell'invocazione del metodo
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    //verifica che la lista restituita dal metodo elencaDomandeRicevute sia uguale a listaDomande
    assertThat(listaDomande, is(equalTo(domandeService.elencaDomandeRicevute())));   
  } 
  
  /** 
   * Testa il metodo per ottenere l'elenco delle domande inviate presso un'azienda da uno specifico
   * studente.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeInviate()}
   * 
   * @result Il test è superato se l'elenco delle domande viene caricato correttamente
   * 
   */
  @Test
  public void testElencaDomandeInviateStudente() {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.ACCETTATA);
    domanda2.setProgettoFormativo(progetto);
    
    //Crea elenco domande inviate dallo studente
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
    
    //Stabilisce che il metodo findAllByStudenteUsername restituisca listaDomande
    when(domandaRepository.findAllByStudenteUsername("FrancescoF")).thenReturn(listaDomande);
    //Stabilisce che al momento dell'invocazione del metodo studente sia l'utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    
    try {
      //Verifica che gli oggetti delle due liste siano uguali
      assertThat(listaDomande, is(equalTo(domandeService.elencaDomandeInviate())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /** 
   * Testa il metodo per ottenere l'elenco delle domande inoltrate all'ufficio tirocini da parte di
   * un delegato aziendale.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeInviate()}
   * 
   * @result Il test è superato se l'elenco delle domande viene caricato correttamente
   * 
   */
  @Test
  public void testElencaDomandeInviateDelegato() {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.APPROVATA);
    domanda.setProgettoFormativo(progetto);
    
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.ACCETTATA);
    domanda2.setProgettoFormativo(progetto);
    
    //crea domanda di tirocinio #3
    DomandaTirocinio domanda3 = new DomandaTirocinio();
    domanda3.setCfu(6);
    domanda3.setCommentoStudente("Mi piace il progetto!");
    domanda3.setData(LocalDateTime.now());
    domanda3.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda3.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda3.setStudente(studente);
    domanda3.setStatus(DomandaTirocinio.RESPINTA);
    domanda3.setProgettoFormativo(progetto);
    
    //Crea lista contenente domande approvate
    List<DomandaTirocinio> listaDomandeApprovate = new ArrayList<DomandaTirocinio>();
    listaDomandeApprovate.add(domanda);
    
    //Crea lista contenente domande accettate
    List<DomandaTirocinio> listaDomandeAccettate = new ArrayList<DomandaTirocinio>();
    listaDomandeAccettate.add(domanda2);
    
    //Crea lista contenente domande respinte
    List<DomandaTirocinio> listaDomandeRespinte = new ArrayList<DomandaTirocinio>();
    listaDomandeRespinte.add(domanda3);
    
    //Crea lista contenente tutte le domande inoltrate all'ufficio tirocini
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
    listaDomande.add(domanda3);
    
    //Stabilisce che il metodo findAllByStatusAndProgettoFormativoAziendaId restituisca 
    //listaDOmandeAccettate
    when(domandaRepository.findAllByStatusAndProgettoFormativoAziendaId(DomandaTirocinio.ACCETTATA,
        "azienda")).thenReturn(listaDomandeAccettate);
    //Stabilisce che il metodo findAllByStatusAndProgettoFormativoAziendaId restituisca
    //listaDomandeApprovate
    when(domandaRepository.findAllByStatusAndProgettoFormativoAziendaId(DomandaTirocinio.APPROVATA,
        "azienda")).thenReturn(listaDomandeApprovate);
    //Stabilisce che il metodo findAllByStatusAndProgettoFormativoAziendaId restituisca
    //listaDomandeRespinte
    when(domandaRepository.findAllByStatusAndProgettoFormativoAziendaId(DomandaTirocinio.RESPINTA,
        "azienda")).thenReturn(listaDomandeRespinte);
    //Stabilisce che l'utente autenticato al momento dell'invocazione del metodo sia un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    try {
      //Verifica che gli oggetti appartenenti alle due liste siano uguali
      assertThat(listaDomande, is(equalTo(domandeService.elencaDomandeInviate())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /** 
   * Testa il metodo per ottenere l'elenco delle domande inviate ad un'azienda da parte di uno
   * studente.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeInviate()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione aspettata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non ha il permesso per 
   *         eseguire tale operazione
   * 
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void testElencaDomandeInviateRichiestaNonAutorizzata() 
      throws RichiestaNonAutorizzataException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setProgettoFormativo(progetto);
    
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 3, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.ACCETTATA);
    domanda2.setProgettoFormativo(progetto);
    
    //Crea lista domande inviate
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
    
    //Stabilisce che il metodo findALLByStudenteUsername restituisca listaDomande
    when(domandaRepository.findAllByStudenteUsername("FrancescoF")).thenReturn(listaDomande);
    //Stabilisce che al momento dell'invocazione del metodo non ci sia alcun utente autenticato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    //Verifica che gli oggetti contenuti nelle due liste siano uguali
    assertThat(listaDomande, is(equalTo(domandeService.elencaDomandeInviate())));
  }
  
  /** 
   * Testa il metodo per ottenere l'elenco dei tirocini in corso di uno studente.
   * 
   * @test {@link DomandeTirocinioService#elencaTirociniInCorso()}
   * 
   * @result Il test è superato se l'elenco delle domande viene caricato correttamente
   * 
   */
  @Test
  public void testElencaTirociniInCorsoStudente() {
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 1, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.APPROVATA);
    domanda.setProgettoFormativo(progetto);
    
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 1, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.APPROVATA);
    domanda2.setProgettoFormativo(progetto);
    
    //Crea l'elenco dei tirocini in corso
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
    
    //Stabilisce che il metodo findAllByStatusAndStudenteUsername restituisca listaDomande
    when(domandaRepository.findAllByStatusAndStudenteUsername(DomandaTirocinio.APPROVATA,
        "FrancescoF")).thenReturn(listaDomande);
    //Stabilisce che al momento dell'invocazione del metodo l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //verifica che le liste contengano gli stessi oggetti
      assertThat(listaDomande, is(equalTo(domandeService.elencaTirociniInCorso())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
    
  /** 
   * Testa il metodo per ottenere l'elenco dei tirocini in corso presso un'azienda.
   * 
   * @test {@link DomandeTirocinioService#elencaTirociniInCorso()}
   * 
   * @result Il test è superato se l'elenco delle domande viene caricato correttamente
   * 
   */
  @Test
    public void testElencaTirociniInCorsoDelegato() {
    
    //Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
      
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
      
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
        
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
        
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 1, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.APPROVATA);
    domanda.setProgettoFormativo(progetto);
      
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 1, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.APPROVATA);
    domanda2.setProgettoFormativo(progetto);
     
    //Crea elenco tirocini in corso
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
      
    //Stabilisce che il metodo findAllByStatusAndProgettoFormativoAziendaId restituisca listaDomande
    when(domandaRepository.findAllByStatusAndProgettoFormativoAziendaId(DomandaTirocinio.APPROVATA,
          "azienda")).thenReturn(listaDomande);
    //Stabilisce che l'utente autentica sia un delegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    try {
      //Verifica che li liste contengano gli stessi oggetti
      assertThat(listaDomande, is(equalTo(domandeService.elencaTirociniInCorso())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
    
  /** 
   * Testa il metodo per ottenere l'elenco delle domande inoltrate all'ufficio tirocini da parte di
   * un delegato aziendale.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeInviate()}
   * 
   * @result Il test è superato se l'elenco delle domande viene caricato correttamente
   * 
   */
  @Test
  public void testElencaTirociniInCorsoImpiegato() {
      
    //Crea impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Masola");
    impiegato.setEmail("antonio@masola.com");
    impiegato.setUsername("username");
    impiegato.setPassword("password");
      
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
      
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
      
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
      
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
      
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 1, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.APPROVATA);
    domanda.setProgettoFormativo(progetto);
      
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 1, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.APPROVATA);
    domanda2.setProgettoFormativo(progetto);
      
    //Crea elenco tirocini in corso
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
      
    //Stabilisce che il metodo findAllByStatus restituisca listaDomande
    when(domandaRepository.findAllByStatus(DomandaTirocinio.APPROVATA)).thenReturn(listaDomande);
    //Stabilisce che al momento dell'invocazione del metodo l'utente autenticato sia un impiegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    try {
      //Verifica che gli elementi delle due liste siano uguali
      assertThat(listaDomande, is(equalTo(domandeService.elencaTirociniInCorso())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
    
  /** 
   * Testa il metodo per ottenere l'elenco dei tirocini in corso da parte dell'impiegato 
   * dell'ufficio tirocini.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeInviate()}
   * 
   * @result Il test è superato se l'elenco delle domande viene caricato correttamente
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non ha il permesso per 
   *         eseguire la seguente operazione
   * 
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void testElencaTirociniInCorsoRichiestaNonAutorizzata()
      throws RichiestaNonAutorizzataException {
      
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
      
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
      
    //Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setNome("Giuseppe");
    delegato.setCognome("Errore");
    delegato.setEmail("giuseppe@errore.com");
    delegato.setSesso("Maschile");
    delegato.setTelefono("3333333333");
    delegato.setUsername("user");
    delegato.setPassword("user");
      
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
      
    //crea domanda di tirocinio #1
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 1, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.APPROVATA);
    domanda.setProgettoFormativo(progetto);
      
    //crea domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setCfu(6);
    domanda2.setCommentoStudente("Mi piace il progetto!");
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 1, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.APPROVATA);
    domanda2.setProgettoFormativo(progetto);
      
    //Crea elenco tirocini in corso
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
      
    //Stabilisce che il metodo findAllByStatus restituisca listaDomande
    when(domandaRepository.findAllByStatus(DomandaTirocinio.APPROVATA)).thenReturn(listaDomande);
    //Stabilisce che al momento dell'invocazione del metodo l'utente autenticato sia un impiegato
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    //Verifica che gli elementi delle due liste siano uguali
    assertThat(listaDomande, is(equalTo(domandeService.elencaTirociniInCorso())));
  }   
   
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se la domanda viene memorizzata sul database con successo
   * 
   */
  @Test
    public void testRegistraDomandaTirocinio() {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
      
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
      
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
      
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setProgettoFormativo(progetto);
    
    // Definisci dinamicamente la data di inizio tirocinio
    LocalDate dataInizioTirocinio = LocalDate.now();
    dataInizioTirocinio = dataInizioTirocinio.plusDays(1);
    domanda.setInizioTirocinio(dataInizioTirocinio);
    
    // Definisci dinamicamente la data di fine tirocinio
    LocalDate dataFineTirocinio = dataInizioTirocinio;
    dataFineTirocinio = dataFineTirocinio.plusDays(40);
    domanda.setFineTirocinio(dataFineTirocinio);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
            | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
            | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    } 
  }
    
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws RichiestaNonAutorizzataException se l'utente autenticato non ha il permesso per 
   *         eseguire la seguente operazione
   * 
   */
  @Test(expected = RichiestaNonAutorizzataException.class)
  public void testRegistraDomandaTirocinioRichiestaNonAutorizzata()
      throws RichiestaNonAutorizzataException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 2, 11));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setProgettoFormativo(progetto);
    
    //Stabilisce che non ci sia alcun utente autenticato al momento dell'invocazione del metodo
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    try {
      //Permette di memorizzare la domanda sul database
      domandeService.registraDomandaTirocinio(domanda);
    } catch (DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } 
  }
    
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws DataDiInizioTirocinioNonValidaException se se la data è uguale alla data odierna 
   *         
   * 
   */
  @Test(expected = DataDiInizioTirocinioNonValidaException.class)
  public void testRegistraDomandaTirocinioConDataInizioNonValida() 
      throws  DataDiInizioTirocinioNonValidaException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.now());
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setProgettoFormativo(progetto);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare la domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiFineTirocinioNonValidaException
        | NumeroCfuNonValidoException | CommentoDomandaTirocinioNonValidoException 
        | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws DataDiInizioTirocinioNonValidaException se se la data è nulla
   *          
   */ 
  @Test(expected = DataDiInizioTirocinioNonValidaException.class)
  public void testRegistraDomandaTirocinioConDataInizioNonValidaNulla() 
      throws  DataDiInizioTirocinioNonValidaException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setProgettoFormativo(progetto);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiFineTirocinioNonValidaException
        | NumeroCfuNonValidoException | CommentoDomandaTirocinioNonValidoException 
        | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws DataDiInizioTirocinioNonValidaException se la data viene dopo la data odierna
   *          
   */
  @Test(expected = DataDiInizioTirocinioNonValidaException.class)
    public void testRegistraDomandaTirocinioConDataInizioNonValidaBeforeOggi() 
        throws  DataDiInizioTirocinioNonValidaException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 1, 1));
    domanda.setFineTirocinio(LocalDate.of(2018, 3, 31));
    domanda.setProgettoFormativo(progetto);
    
    //Syabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiFineTirocinioNonValidaException
        | NumeroCfuNonValidoException | CommentoDomandaTirocinioNonValidoException 
        | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }   
  }
  
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws DataDiFineTirocinioNonValidaException se se la data di fine precede la data di inizio
   *          
   */
  @Test(expected =  DataDiFineTirocinioNonValidaException.class)
  public void testRegistraDomandaTirocinioConDataFineNonValida() 
      throws DataDiFineTirocinioNonValidaException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setProgettoFormativo(progetto);
    
    // Definisci dinamicamente la data di inizio tirocinio
    LocalDate dataInizioTirocinio = LocalDate.now();
    dataInizioTirocinio = dataInizioTirocinio.plusDays(1);
    domanda.setInizioTirocinio(dataInizioTirocinio);
    
    // Definisci dinamicamente la data di fine tirocinio
    LocalDate dataFineTirocinio = dataInizioTirocinio;
    dataFineTirocinio = dataFineTirocinio.minusDays(40);
    domanda.setFineTirocinio(dataFineTirocinio);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | NumeroCfuNonValidoException | CommentoDomandaTirocinioNonValidoException 
        | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws DataDiFineTirocinioNonValidaException se la data di fine è nulla 
   *          
   */
  @Test(expected =  DataDiFineTirocinioNonValidaException.class)
  public void testRegistraDomandaTirocinioConDataFineNonValidaNulla() 
      throws DataDiFineTirocinioNonValidaException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setProgettoFormativo(progetto);
    
    // Definisci dinamicamente la data di inizio tirocinio
    LocalDate dataInizioTirocinio = LocalDate.now();
    dataInizioTirocinio = dataInizioTirocinio.plusDays(1);
    domanda.setInizioTirocinio(dataInizioTirocinio);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | NumeroCfuNonValidoException | CommentoDomandaTirocinioNonValidoException 
        | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws NumeroCfuNonValidoException se il numero di cfu è minore del minimo stabilito
   *          
   */
  @Test(expected = NumeroCfuNonValidoException.class)
  public void testRegistraDomandaTirocinioConNumeroCfuNonValido() 
      throws  NumeroCfuNonValidoException {
 
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(0);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setProgettoFormativo(progetto);
    
    // Definisci dinamicamente la data di inizio tirocinio
    LocalDate dataInizioTirocinio = LocalDate.now();
    dataInizioTirocinio = dataInizioTirocinio.plusDays(1);
    domanda.setInizioTirocinio(dataInizioTirocinio);
    
    // Definisci dinamicamente la data di fine tirocinio
    LocalDate dataFineTirocinio = dataInizioTirocinio;
    dataFineTirocinio = dataFineTirocinio.plusDays(40);
    domanda.setFineTirocinio(dataFineTirocinio);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | CommentoDomandaTirocinioNonValidoException
        | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws NumeroCfuNonValidoException se il numero di cfu è null
   *          
   */
  @Test(expected = NumeroCfuNonValidoException.class)
  public void testRegistraDomandaTirocinioConNumeroCfuNonValidoNullo() 
      throws  NumeroCfuNonValidoException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setProgettoFormativo(progetto);
    
    // Definisci dinamicamente la data di inizio tirocinio
    LocalDate dataInizioTirocinio = LocalDate.now();
    dataInizioTirocinio = dataInizioTirocinio.plusDays(1);
    domanda.setInizioTirocinio(dataInizioTirocinio);
    
    // Definisci dinamicamente la data di fine tirocinio
    LocalDate dataFineTirocinio = dataInizioTirocinio;
    dataFineTirocinio = dataFineTirocinio.plusDays(40);
    domanda.setFineTirocinio(dataFineTirocinio);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | CommentoDomandaTirocinioNonValidoException
        | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws NumeroCfuNonValidoException se il numero di cfu è maggiore del massimo previsto
   *          
   */
  @Test(expected = NumeroCfuNonValidoException.class)
  public void testRegistraDomandaTirocinioConNumeroCfuMaggiore() 
      throws  NumeroCfuNonValidoException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(30);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setProgettoFormativo(progetto);
    
    // Definisci dinamicamente la data di inizio tirocinio
    LocalDate dataInizioTirocinio = LocalDate.now();
    dataInizioTirocinio = dataInizioTirocinio.plusDays(1);
    domanda.setInizioTirocinio(dataInizioTirocinio);
    
    // Definisci dinamicamente la data di fine tirocinio
    LocalDate dataFineTirocinio = dataInizioTirocinio;
    dataFineTirocinio = dataFineTirocinio.plusDays(40);
    domanda.setFineTirocinio(dataFineTirocinio);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | CommentoDomandaTirocinioNonValidoException
        | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws CommentoDomandaTirocinioNonValidoException se il commento inserito non è valido
   */
  @Test(expected = CommentoDomandaTirocinioNonValidoException.class)
  public void testRegistraDomandaTirocinioConCommentoNonValido() 
      throws CommentoDomandaTirocinioNonValidoException {
 
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente(null);
    domanda.setData(LocalDateTime.now());
    domanda.setProgettoFormativo(progetto);
    
    // Definisci dinamicamente la data di inizio tirocinio
    LocalDate dataInizioTirocinio = LocalDate.now();
    dataInizioTirocinio = dataInizioTirocinio.plusDays(1);
    domanda.setInizioTirocinio(dataInizioTirocinio);
    
    // Definisci dinamicamente la data di fine tirocinio
    LocalDate dataFineTirocinio = dataInizioTirocinio;
    dataFineTirocinio = dataFineTirocinio.plusDays(40);
    domanda.setFineTirocinio(dataFineTirocinio);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Testa il metodo per il salvataggio di una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione desiderata
   * 
   * @throws ProgettoFormativoArchiviatoException se il progetto formativo si trova in uno stato
   *         {@link ProgettoFormativo#ARCHIVIATO}
   */
  @Test(expected = ProgettoFormativoArchiviatoException.class)
  public void testRegistraDomandaTirocinioConProgettoArchiviato() 
      throws ProgettoFormativoArchiviatoException {
    
    // Crea studente
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("azienda");
    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
    azienda.setPartitaIva("12345678901");
    azienda.setSenzaBarriere(true);
    
    //Crea progetto formativo dell'azienda
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("descrizione");
    progetto.setStatus(ProgettoFormativo.ARCHIVIATO);
    progetto.setAzienda(azienda);
    
    //crea domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setCfu(6);
    domanda.setCommentoStudente("Mi piace il progetto!");
    domanda.setData(LocalDateTime.now());
    domanda.setProgettoFormativo(progetto);
    
    // Definisci dinamicamente la data di inizio tirocinio
    LocalDate dataInizioTirocinio = LocalDate.now();
    dataInizioTirocinio = dataInizioTirocinio.plusDays(1);
    domanda.setInizioTirocinio(dataInizioTirocinio);
    
    // Definisci dinamicamente la data di fine tirocinio
    LocalDate dataFineTirocinio = dataInizioTirocinio;
    dataFineTirocinio = dataFineTirocinio.plusDays(40);
    domanda.setFineTirocinio(dataFineTirocinio);
    
    //Stabilisce che l'utente autenticato sia uno studente
    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
    try {
      //Permette di registrare una domanda di tirocinio
      domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } 
  }  
    
}

