package it.unisa.di.tirociniosmart.studenti;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.when;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
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
 * Classe che offre di casi di test di StudentiService.
 * 
 * @see StudentiService
 * @see StudenteRepository
 * @see RichiestaIscrizioneRepository
 * @see UtenzaService
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentiServiceTest {

  @InjectMocks
  private StudentiService studentiService;
  
  @Mock
  private StudenteRepository studenteRepository;
  
  @Mock
  private RichiestaIscrizioneRepository richiestaIscrizioneRepository;
  
  @Mock
  private UtenzaService utenzaService;

  /**
   * Metodo che testa l'ottenimenti di un elenco di richieste di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaElencaListaRichiesteIscrizioni()}
   * 
   * @result Il test è superato se la lista richieste iscrizioni è ottenuta correttamente
   */
  @Test
  public void testaElencaListaRichiesteIscrizioni() {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    Studente studente1 = new Studente();
    studente1.setUsername("studente1");
    
    RichiestaIscrizione iscrizione1 = studente1.getRichiestaIscrizione();
    iscrizione1.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione1.setDataRichiesta(LocalDateTime.now());
    
    Studente studente2 = new Studente();
    studente2.setUsername("studente2");
    
    RichiestaIscrizione iscrizione2 = studente2.getRichiestaIscrizione();
    iscrizione2.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione2.setDataRichiesta(LocalDateTime.now());
    
    Studente studente3 = new Studente();
    studente3.setUsername("studente3");
    
    RichiestaIscrizione iscrizione3 = studente3.getRichiestaIscrizione();
    iscrizione3.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione3.setDataRichiesta(LocalDateTime.now());
    
    List<RichiestaIscrizione> listaRichieste = new ArrayList<RichiestaIscrizione>();
    
    listaRichieste.add(iscrizione1);
    listaRichieste.add(iscrizione2);
    listaRichieste.add(iscrizione3);
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    when(richiestaIscrizioneRepository.findAllByStatus(RichiestaIscrizione.IN_ATTESA))
                                                      .thenReturn(listaRichieste);
    
    try {
      assertThat(listaRichieste, is(equalTo(studentiService.elencaListaRichiesteIscrizione())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Metodo che testa l'ottenimento di un elenco di richieste di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaElencaListaRichiesteIscrizioniRichiestaNonAutorizzata()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaNonAutorizzataException.class)
  public void testaElencaListaRichiesteIscrizioniRichiestaNonAutorizzata() 
         throws RichiestaNonAutorizzataException {
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    studentiService.elencaListaRichiesteIscrizione();
  }
  
  /**
   * Metodo che testa l'approvazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaApprovaRichiestaIscrizione()}
   * 
   * @result Il test è superato se la richiesta viene approvata correttamente
   */
  @Test
  public void testaApprovaRichiestaIscrizione() {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    Studente studente = new Studente();
    studente.setUsername("studente");
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    when(richiestaIscrizioneRepository.findById(1234L)).thenReturn(iscrizione);
    try {
      studentiService.approvaRichiestaIscrizione(1234L);
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaIscrizioneGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    assertThat(iscrizione.getStatus(), is(equalTo(RichiestaIscrizione.APPROVATA)));
  }
  
  /**
   * Metodo che testa l'approvazione di una richiesta iscrizione.
   * 
   * @throws IdRichiestaIscrizioneNonValidoException
   * 
   * @test {@link StudentiServiceTest#testaApprovaRichiestaIscrizioneIdNonValido()}
   * 
   * @result Il testa è superato se viene lanciata l'eccezione.
   */
  @Test (expected = IdRichiestaIscrizioneNonValidoException.class)
  public void testaApprovaRichiestaIscrizioneIdNonValido()
         throws IdRichiestaIscrizioneNonValidoException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    when(richiestaIscrizioneRepository.findById(1234L)).thenReturn(null);
    try {
      studentiService.approvaRichiestaIscrizione(1234L);
    } catch (RichiestaIscrizioneGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa l'approvazione di una richiesta di iscrizione.
   * 
   * @throws RichiestaIscrizioneGestitaException
   * 
   * @test {@link StudentiServiceTest#testaApprovaRichiestaIscrizioneRichiestaGestita()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaIscrizioneGestitaException.class)
  public void testaApprovaRichiestaIscrizioneRichiestaGestita() 
         throws RichiestaIscrizioneGestitaException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    Studente studente = new Studente();
    studente.setUsername("studente");
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.APPROVATA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    when(richiestaIscrizioneRepository.findById(1234L)).thenReturn(iscrizione);
    try {
      studentiService.approvaRichiestaIscrizione(1234L);
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa l'approvazione di una richiesta di iscrizione.
   * 
   * @throws RichiestaNonAutorizzataException
   * 
   * @test {@link StudentiServiceTest#testaApprovaRichiestaIscrizioneRichiestaNonAutorizzata()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaNonAutorizzataException.class)
  public void testaApprovaRichiestaIscrizioneRichiestaNonAutorizzata()
         throws RichiestaNonAutorizzataException {
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    try {
      studentiService.approvaRichiestaIscrizione(1234L);
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaIscrizioneGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa il rifiuto di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRifiutaRichiestaIscrizione()}
   * 
   * @result Il test è superato se la richiesta viene rifiutata correttamente
   */
  @Test
  public void testaRifiutaRichiestaIscrizione() {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    Studente studente = new Studente();
    studente.setUsername("studente");
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    when(richiestaIscrizioneRepository.findById(1234L)).thenReturn(iscrizione);
    try {
      studentiService.rifiutaRichiestaIscrizione(1234L, iscrizione.getCommentoUfficioTirocini());
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaIscrizioneGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa il rifiuto di una richiesta di iscrizione.
   * 
   * @throws IdRichiestaIscrizioneNonValidoException
   * 
   * @test {@link StudentiServiceTest#testaRifiutaRichiestaIscrizioneIdNonValido()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = IdRichiestaIscrizioneNonValidoException.class)
  public void testaRifiutaRichiestaIscrizioneIdNonValido() 
         throws IdRichiestaIscrizioneNonValidoException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    when(richiestaIscrizioneRepository.findById(1234L)).thenReturn(null);
    try {
      studentiService.rifiutaRichiestaIscrizione(1234L,"commento");
    } catch (RichiestaIscrizioneGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }   
  }
  
  /**
   * Metodo che testa il rifiuto di una richiesta di iscrizione.
   * 
   * @throws RichiestaIscrizioneGestitaException
   * 
   * @test {@link StudentiServiceTest#testaRifiutaRichiestaIscrizioneRichiestaGestita()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaIscrizioneGestitaException.class)
  public void testaRifiutaRichiestaIscrizioneRichiestaGestita() 
         throws RichiestaIscrizioneGestitaException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    Studente studente = new Studente();
    studente.setUsername("studente");
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.RIFIUTATA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    when(richiestaIscrizioneRepository.findById(1234L)).thenReturn(iscrizione);
    try {
      studentiService.rifiutaRichiestaIscrizione(1234L, iscrizione.getCommentoUfficioTirocini());
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa il rifiuto di una richiesta di iscrizione.
   * 
   * @throws CommentoRichiestaIscrizioneNonValidoException
   * 
   * @test {@link StudentiServiceTest#testaRifiutaRichiestaIscrizioneCommentoNonValido()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = CommentoRichiestaIscrizioneNonValidoException.class)
  public void testaRifiutaRichiestaIscrizioneCommentoNonValido()
         throws CommentoRichiestaIscrizioneNonValidoException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    Studente studente = new Studente();
    studente.setUsername("studente");
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("o");
    
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    when(richiestaIscrizioneRepository.findById(1234L)).thenReturn(iscrizione);
    try {
      studentiService.rifiutaRichiestaIscrizione(1234L, iscrizione.getCommentoUfficioTirocini());
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaIscrizioneGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa il rifiuto di una richiesta di iscrizione.
   * 
   * @throws CommentoRichiestaIscrizioneNonValidoException
   * 
   * @test {@link StudentiServiceTest#testaRifiutaRichiestaIscrizioneCommentoNulloNonValido()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = CommentoRichiestaIscrizioneNonValidoException.class)
  public void testaRifiutaRichiestaIscrizioneCommentoNulloNonValido()
         throws CommentoRichiestaIscrizioneNonValidoException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    Studente studente = new Studente();
    studente.setUsername("studente");
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());  
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    when(richiestaIscrizioneRepository.findById(1234L)).thenReturn(iscrizione);
    try {
      studentiService.rifiutaRichiestaIscrizione(1234L, iscrizione.getCommentoUfficioTirocini());
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaIscrizioneGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa il rifiuto di una richiesta di iscrizione.
   * 
   * @throws RichiestaNonAutorizzataException
   * 
   * @test {@link StudentiServiceTest#testaRifiutoRichiestaIscrizioneRichiestaNonAutorizzata()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaNonAutorizzataException.class)
  public void testaRifiutoRichiestaIscrizioneRichiestaNonAutorizzata()
         throws RichiestaNonAutorizzataException {
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    try {
      studentiService.rifiutaRichiestaIscrizione(1234L, "commento");
    } catch (IdRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaIscrizioneGestitaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CommentoRichiestaIscrizioneNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizione()}
   * 
   * @result Il test è superato se la richiesta viene registrata correttamente
   */
  @Test
  public void testaRegistraRichiestaIscrizione() {
        
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setDataDiNascita(LocalDate.of(1997, 02, 12));
    studente.setIndirizzo("indirizzo, 3");
    studente.setMatricola("0512112345");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.save(studente)).thenReturn(null);
  
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (IndirizzoStudenteNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DataDiNascitaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneRichiestaNonAutorizzata()}
   * 
   * @throws RichiestaNonAutorizzataException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaNonAutorizzataException.class)
  public void testaRegistrazioneRichiestaIscrizioneRichiestaNonAutorizzata() 
         throws RichiestaNonAutorizzataException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    Studente studente = new Studente();
    studente.setUsername("username");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (IndirizzoStudenteNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DataDiNascitaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneDataDiNascinaNonValidaMin()}
   * 
   * @throws DataDiNascitaStudenteNonValidaException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = DataDiNascitaStudenteNonValidaException.class)
  public void testaRegistraRichiestaIscrizioneDataDiNascinaNonValidaMin() 
         throws DataDiNascitaStudenteNonValidaException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setDataDiNascita(LocalDate.of(2020, 02, 12));
    studente.setIndirizzo("indirizzo, 3");
    studente.setMatricola("0512112345");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.save(studente)).thenReturn(null);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (IndirizzoStudenteNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneDataDiNascinaNonValidaMax()}
   * 
   * @throws DataDiNascitaStudenteNonValidaException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = DataDiNascitaStudenteNonValidaException.class)
  public void testaRegistraRichiestaIscrizioneDataDiNascinaNonValidaMax() 
         throws DataDiNascitaStudenteNonValidaException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setDataDiNascita(LocalDate.of(1880, 02, 12));
    studente.setIndirizzo("indirizzo, 3");
    studente.setMatricola("0512112345");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.save(studente)).thenReturn(null);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (IndirizzoStudenteNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneDataDiNascinaNulla()}
   * 
   * @throws DataDiNascitaStudenteNonValidaException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = DataDiNascitaStudenteNonValidaException.class)
  public void testaRegistraRichiestaIscrizioneDataDiNascinaNulla() 
         throws DataDiNascitaStudenteNonValidaException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setIndirizzo("indirizzo, 3");
    studente.setMatricola("0512112345");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.save(studente)).thenReturn(null);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (IndirizzoStudenteNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneIndirizzoNonValidoMin()}
   * 
   * @throws IndirizzoStudenteNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = IndirizzoStudenteNonValidoException.class)
  public void testaRegistraRichiestaIscrizioneIndirizzoNonValidoMin() 
         throws IndirizzoStudenteNonValidoException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setDataDiNascita(LocalDate.of(1997, 02, 12));
    studente.setIndirizzo("i");
    studente.setMatricola("0512112345");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.save(studente)).thenReturn(null);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DataDiNascitaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneIndirizzoNonValidoMax()}
   * 
   * @throws IndirizzoStudenteNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = IndirizzoStudenteNonValidoException.class)
  public void testaRegistraRichiestaIscrizioneIndirizzoNonValidoMax() 
         throws IndirizzoStudenteNonValidoException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setDataDiNascita(LocalDate.of(1997, 02, 12));
    studente.setIndirizzo("indirizzo indirizzo indirizzo indirizzo indirizzo indirizzo"
        + "                indirizzo indirizzo indirizzo indirizzo indirizzo indirizzo"
        + "                indirizzo indirizzo indirizzo, 1234567890 1234567890 1234567890"
        + "                1234567890 1234567890 1234567890 1234567890 1234567890 1234567890"
        + "                1234567890 1234567890 1234567890 1234567890 1234567890 1234567890");
    studente.setMatricola("0512112345");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.save(studente)).thenReturn(null);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DataDiNascitaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneIndirizzoNullo()}
   * 
   * @throws IndirizzoStudenteNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = IndirizzoStudenteNonValidoException.class)
  public void testaRegistraRichiestaIscrizioneIndirizzoNullo() 
         throws IndirizzoStudenteNonValidoException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setDataDiNascita(LocalDate.of(1997, 02, 12));
    studente.setMatricola("0512112345");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.save(studente)).thenReturn(null);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DataDiNascitaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneMatricolaEsistente()}
   * 
   * @throws MatricolaStudenteEsistenteException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = MatricolaStudenteEsistenteException.class)
  public void testaRegistraRichiestaIscrizioneMatricolaEsistente() 
         throws MatricolaStudenteEsistenteException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setDataDiNascita(LocalDate.of(1997, 02, 12));
    studente.setIndirizzo("indirizzo, 3");
    studente.setMatricola("0512112345");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.existsByMatricola(studente.getMatricola())).thenReturn(true);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IndirizzoStudenteNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DataDiNascitaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneMatricolaNulla()}
   * 
   * @throws MatricolaStudenteNonValidaException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = MatricolaStudenteNonValidaException.class)
  public void testaRegistraRichiestaIscrizioneMatricolaNulla() 
         throws MatricolaStudenteNonValidaException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setDataDiNascita(LocalDate.of(1997, 02, 12));
    studente.setIndirizzo("indirizzo, 3");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.save(studente)).thenReturn(null);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (IndirizzoStudenteNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DataDiNascitaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa la registrazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiServiceTest#testaRegistraRichiestaIscrizioneMatricolaNonValida()}
   * 
   * @throws MatricolaStudenteNonValidaException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = MatricolaStudenteNonValidaException.class)
  public void testaRegistraRichiestaIscrizioneMatricolaNonValida()
         throws MatricolaStudenteNonValidaException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    studente.setNome("Nome");
    studente.setCognome("cognome");
    studente.setEmail("nome@cognome.com");
    studente.setDataDiNascita(LocalDate.of(1997, 02, 12));
    studente.setIndirizzo("indirizzo, 3");
    studente.setMatricola("05121123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setTelefono("1234567890");
    studente.setDataRegistrazione(LocalDateTime.now());
    
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setCommentoUfficioTirocini("commento");
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    when(studenteRepository.save(studente)).thenReturn(null);
    
    try {
      studentiService.registraRichiestaIscrizione(studente);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (IndirizzoStudenteNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (MatricolaStudenteEsistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DataDiNascitaStudenteNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
}
