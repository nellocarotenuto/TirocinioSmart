package it.unisa.di.tirociniosmart.utenza;

import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendaleRepository;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoInAttesaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoRifiutataException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirociniRepository;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizione;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneInAttesaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneRifiutataException;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.studenti.StudenteRepository;

/**
 * Classe che offre i casi di test di UtenzaService.
 * 
 * @see UtenzaService
 * @see UtenteRegistratoRepository
 * @see StudenteRepository
 * @see DelegatoAziendaleRepository
 * @see ImpiegatoUfficioTirociniRepository
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AutenticazioneHolder.class)
public class UtenzaServiceTest {

  @InjectMocks
  private UtenzaService utenzaService;
  
  @Mock
  private UtenteRegistratoRepository utenteRepository;
  
  @Mock
  private StudenteRepository studenteRepository;
  
  @Mock
  private DelegatoAziendaleRepository delegatoRepository;
  
  @Mock
  private ImpiegatoUfficioTirociniRepository impiegatoRepository;
  
  @Mock
  private AutenticazioneHolder autenticazioneHolder;
  
  @Before
  public void inizializzaMock() {
    mockStatic(AutenticazioneHolder.class);
  }
  
  /**
   * Testa il metodo per ottenere l'utente autenticato nel caso in cui esso sia null.
   * 
   * @test {@link UtenzaService#GetUtenteAutenticato()}
   * 
   * @result Il test è superato se carica correttamente l'utente autenticato
   */
  @Test
  public void testaGetUtenteAutenticatoOspite() {

    assertThat(null, is(equalTo(utenzaService.getUtenteAutenticato())));
  }
  
  /**
   * Testa il metodo per ottenere l'utente autenticaso nel caso in cui esso sia un impiegato.
   * 
   * @test {@link UtenzaService#getUtenteAutenticato()}
   * 
   * @result Il test è superato se carica correttamente l'utente autenticato
   */
  @Test 
  public void testaGetUtenteAutenticatoImpiegato() {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Nome");
    impiegato.setCognome("Cognome");
    impiegato.setEmail("nome@cognome.it");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    when(AutenticazioneHolder.getUtente()).thenReturn(impiegato.getUsername());
    when(impiegatoRepository.findByUsername(impiegato.getUsername())).thenReturn(impiegato);
    assertThat(impiegato, is(equalTo(utenzaService.getUtenteAutenticato())));
  }
  
  /**
   * Testa il metodo per ottenere l'utente autenticaso nel caso in cui esso sia un delegato.
   * 
   * @test {@link UtenzaService#getUtenteAutenticato()}
   * 
   * @result Il test è superato se carica correttamente l'utente autenticato
   */
  @Test 
  public void testaGetUtenteAutenticatoDelegato() {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("wilee");
    delegato.setPassword("beepbeep");
    delegato.setEmail("wilee@coyote.com");
    delegato.setNome("Wile E.");
    delegato.setCognome("Coyote");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    when(AutenticazioneHolder.getUtente()).thenReturn(delegato.getUsername());
    when(delegatoRepository.findByUsername(delegato.getUsername())).thenReturn(delegato);
    assertThat(delegato, is(equalTo(utenzaService.getUtenteAutenticato())));
  }
  
  /**
   * Testa il metodo per ottenere l'utente autenticato nel caso in cui esso sia uno studente.
   * 
   * @test {@link UtenzaService#getUtenteAutenticato()}
   * 
   * @result Il test è superato se carica correttamente l'utente autenticato
   */
  @Test 
  public void testaGetUtenteAutenticatoStudente() {
    
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso("M");
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    when(AutenticazioneHolder.getUtente()).thenReturn(studente.getUsername());
    when(studenteRepository.findByUsername(studente.getUsername())).thenReturn(studente);
    assertThat(studente, is(equalTo(utenzaService.getUtenteAutenticato())));
  }
  
  /**
   * Testa il metodo per settare l'utente autenticato nel caso in cui esso sia un impiegato.
   * 
   * @test {@link UtenzaService#setUtenteAutenticato()}
   * 
   * @result Il test è superato se setta correttamente l'utente autenticato
   */
  @Test
  public void testaSetUtenteAutenticatoImpiegato() {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setUsername("username");
    
    when(utenteRepository.existsByUsername(impiegato.getUsername())).thenReturn(true);
    utenzaService.setUtenteAutenticato(impiegato.getUsername());
  }
  
  /**
   * Testa il metodo per settare l'utente autenticato nel caso in cui esso sia un delegato.
   * 
   * @test {@link UtenzaService#setUtenteAutenticato()}
   * 
   * @result Il test è superato se setta correttamente l'utente autenticato
   */
  @Test
  public void testaSetUtenteAutenticatoDelegato() {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("username");
    
    when(utenteRepository.existsByUsername(delegato.getUsername())).thenReturn(true);
    utenzaService.setUtenteAutenticato(delegato.getUsername());
  }
  
  /**
   * Testa il metodo per settare l'utente autenticato nel caso in cui esso sia uno studente.
   * 
   * @test {@link UtenzaService#setUtenteAutenticato()}
   * 
   * @result Il test è superato se setta correttamente l'utente autenticato
   */
  @Test
  public void testaSetUtenteAutenticatoStudente() {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    
    when(utenteRepository.existsByUsername(studente.getUsername())).thenReturn(true);
    utenzaService.setUtenteAutenticato(studente.getUsername());
  }
  
  /**
   * Testa il metodo per settare l'utente autenticato nel caso in cui esso sia ospite.
   * 
   * @test {@link UtenzaService#setUtenteAutenticato()}
   * 
   * @result Il test è superato se setta correttamente l'utente autenticato
   */
  @Test
  public void testaSetUtenteAutenticato() {
    
    String username = null;
    utenzaService.setUtenteAutenticato(username);
  }
  
  /**
   * Testa il metodo per effettuare il logout dal sistema.
   * 
   * @test {@link UtenzaService#logout()}
   * 
   * @result Il test è superato se il logout viene effettuato
   */
  @Test
  public void testaLogout() {
    
    doNothing().when(AutenticazioneHolder.class); 
    utenzaService.logout();
  }
  
  /**
   * Metodo che testa il login di un impiegato.
   * 
   * @test {@link UtenzaService#login(String, String)}
   * 
   * @result Il test è superato se il login viene effettuato con successo
   */
  @Test
  public void testaLoginImpiegato() {
    
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setUsername("username");
    impiegato.setPassword("password");
    
    when(impiegatoRepository.findByUsernameAndPassword(
                           impiegato.getUsername(), impiegato.getPassword())).thenReturn(impiegato);
    
    
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Metodo che testa il login di un delegato.
   * 
   * @test {@link UtenzaService#login(String, String)}
   * 
   * @result Il test è superato se il login viene effettuato con successo
   */
  @Test
  public void testaLoginDelegato() {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("username");
    delegato.setPassword("password");
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta.setDataRichiesta(LocalDateTime.now());
    
    when(delegatoRepository.findByUsernameAndPassword(
                           delegato.getUsername(), delegato.getPassword())).thenReturn(delegato);
    
    
    try {
      utenzaService.login(delegato.getUsername(), delegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Metodo che testa il login di un delegato.
   * 
   * @test {@link UtenzaService#login(String, String)}
   * 
   * @throws RichiestaConvenzionamentoInAttesaException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaConvenzionamentoInAttesaException.class)
  public void testaLoginDelegatoRichiestaInAttesa()
         throws RichiestaConvenzionamentoInAttesaException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("username");
    delegato.setPassword("password");
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.now());
    
    when(delegatoRepository.findByUsernameAndPassword(
                           delegato.getUsername(), delegato.getPassword())).thenReturn(delegato);
    
    
    try {
      utenzaService.login(delegato.getUsername(), delegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Metodo che testa il login di un delegato.
   * 
   * @test {@link UtenzaService#login(String, String)}
   * 
   * @throws RichiestaConvenzionamentoRifiutataException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaConvenzionamentoRifiutataException.class)
  public void testaLoginDelegatoRichiestaRifiutata()
         throws RichiestaConvenzionamentoRifiutataException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("username");
    delegato.setPassword("password");
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.RIFIUTATA);
    richiesta.setDataRichiesta(LocalDateTime.now());
    
    when(delegatoRepository.findByUsernameAndPassword(
                           delegato.getUsername(), delegato.getPassword())).thenReturn(delegato);
    
    
    try {
      utenzaService.login(delegato.getUsername(), delegato.getPassword());
    } catch (RichiestaConvenzionamentoInAttesaException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Metodo che testa il login di un studente.
   * 
   * @test {@link UtenzaService#login(String, String)}
   * 
   * @result Il test è superato se il login viene effettuato con successo
   */
  @Test
  public void testaLoginStudente() {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setStatus(RichiestaIscrizione.APPROVATA);
    
    when(studenteRepository.findByUsernameAndPassword(
                           studente.getUsername(), studente.getPassword())).thenReturn(studente);
    
    
    try {
      utenzaService.login(studente.getUsername(), studente.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Metodo che testa il login di un studente.
   * 
   * @test {@link UtenzaService#login(String, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaIscrizioneInAttesaException.class)
  public void testaLoginStudenteRichiestaInAttesa()
         throws RichiestaIscrizioneInAttesaException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    
    when(studenteRepository.findByUsernameAndPassword(
                           studente.getUsername(), studente.getPassword())).thenReturn(studente);
    
    try {
      utenzaService.login(studente.getUsername(), studente.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Metodo che testa il login di un studente.
   * 
   * @test {@link UtenzaService#login(String, String)}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaIscrizioneRifiutataException.class)
  public void testaLoginStudenteRichiestaRifiutata()
         throws RichiestaIscrizioneRifiutataException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setStatus(RichiestaIscrizione.RIFIUTATA);
    
    when(studenteRepository.findByUsernameAndPassword(
                           studente.getUsername(), studente.getPassword())).thenReturn(studente);
    
    try {
      utenzaService.login(studente.getUsername(), studente.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneInAttesaException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException e) {
      fail(e.getMessage());
    } 
  }
  
  /**
   * Metodo che testa il login di un utente con credenziali incorrette.
   * 
   * @test {@link UtenzaService#login(String, String)}
   * 
   * @throws CredenzialiNonValideException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = CredenzialiNonValideException.class)
  public void testaLoginCredenzialiNonValide() 
         throws CredenzialiNonValideException {
    
    Studente studente = new Studente();
    studente.setUsername("username");
    studente.setPassword("password");
    RichiestaIscrizione iscrizione = studente.getRichiestaIscrizione();
    iscrizione.setDataRichiesta(LocalDateTime.now());
    iscrizione.setStatus(RichiestaIscrizione.APPROVATA);
    
    when(studenteRepository.findByUsernameAndPassword(
        studente.getUsername(), studente.getPassword())).thenReturn(null);

    try {
      utenzaService.login(studente.getUsername(), studente.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneInAttesaException
        | RichiestaIscrizioneRifiutataException | RichiestaConvenzionamentoInAttesaException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un username.
   * 
   * @test {@link UtenzaService#validaUsername(String)}
   * 
   * @result Il test è superato se la validazione va a buon fine
   */
  @Test
  public void testaValidaUsername() {
    
    String username = "username";
    try {
      utenzaService.validaUsername(username);
    } catch (UsernameNonValidoException | UsernameEsistenteException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un username.
   * 
   * @test {@link UtenzaService#validaUsername(String)}
   * 
   * @throws UsernameNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = UsernameNonValidoException.class)
  public void testaValidaUsernameNulla()
         throws UsernameNonValidoException {
    
    String username = null;
    try {
      utenzaService.validaUsername(username);
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un username.
   * 
   * @test {@link UtenzaService#validaUsername(String)}
   * 
   * @throws UsernameNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = UsernameNonValidoException.class)
  public void testaValidaUsernameNonValido()
         throws UsernameNonValidoException {
    
    String username = "user";
    try {
      utenzaService.validaUsername(username);
    } catch (UsernameEsistenteException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un username.
   * 
   * @test {@link UtenzaService#validaUsername(String)}
   * 
   * @throws UsernameEsistenteException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = UsernameEsistenteException.class)
  public void testaValidaUsernameEsistente()
         throws UsernameEsistenteException {
    
    String username = "username";
    
    when(utenteRepository.existsByUsername(username)).thenReturn(true);
    try {
      utenzaService.validaUsername(username);
    } catch (UsernameNonValidoException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di una password.
   * 
   * @test {@link UtenzaService#validaPassword(String)}
   * 
   * @result Il test è superato la validazione avviene correttamente
   */
  @Test
  public void testaValidaPassword() {
    
    String password = "password";
    try {
      utenzaService.validaPassword(password);
    } catch (PasswordNonValidaException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di una password.
   * 
   * @test {@link UtenzaService#validaPassword(String)}
   * 
   * @throws PasswordNonValidaException
   * 
   * @result Il test è superato la validazione avviene correttamente
   */
  @Test (expected = PasswordNonValidaException.class)
  public void testaValidaPasswordNulla() 
         throws PasswordNonValidaException {
    
    String password = null;
    utenzaService.validaPassword(password);
  }
  
  /**
   * Metodo che testa la validazione di una password.
   * 
   * @test {@link UtenzaService#validaPassword(String)}
   * 
   * @throws PasswordNonValidaException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = PasswordNonValidaException.class)
  public void testaValidaPasswordNonValida() 
         throws PasswordNonValidaException {
    
    String password = "pass";
    utenzaService.validaPassword(password);
  }
  
  /**
   * Metodo che testa la validazione di un email.
   * 
   * @test {@link UtenzaService#validaEmail(String)}
   * 
   * @result Il test è superato se la validazione avviene correttamente
   */
  @Test
  public void testaValidaEmail() {
    
    String email = "nome@cognome.com";
    
    try {
      utenzaService.validaEmail(email);
    } catch (EmailNonValidaException | EmailEsistenteException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un email.
   * 
   * @test {@link UtenzaService#validaEmail(String)}
   * 
   * @throws EmailNonValidaException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = EmailNonValidaException.class)
  public void testaValidaEmailNulla()
         throws EmailNonValidaException {
    
    String email = null;
    try {
      utenzaService.validaEmail(email);
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un email.
   * 
   * @test {@link UtenzaService#validaEmail(String)}
   * 
   * @throws EmailNonValidaException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = EmailNonValidaException.class)
  public void testaValidaEmailNonValida() 
         throws EmailNonValidaException {
    
    String email = "nome@.it";
    try {
      utenzaService.validaEmail(email);
    } catch (EmailEsistenteException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un email.
   * 
   * @test {@link UtenzaService#validaEmail(String)}
   * 
   * @throws EmailEsistenteException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = EmailEsistenteException.class)
  public void testaValidaEmailEsistente() 
         throws EmailEsistenteException {
    
    String email = "nome@cognome.it";
    
    when(utenteRepository.existsByEmail(email)).thenReturn(true);
    try {
      utenzaService.validaEmail(email);
    } catch (EmailNonValidaException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un nome.
   * 
   * @test {@link UtenzaService#validaNome(String)}
   * 
   * @result Il test è superato se la validazione avviene correttamente
   */
  @Test
  public void testaValidaNome() {
    
    String nome = "Nome"; 
    try {
      utenzaService.validaNome(nome);
    } catch (NomeNonValidoException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un nome.
   * 
   * @test {@link UtenzaService#validaNome(String)}
   * 
   * @throws NomeNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = NomeNonValidoException.class)
  public void testaValidaNomeNullo() 
         throws NomeNonValidoException {
    
    String nome = null;
    utenzaService.validaNome(nome);
  }
  
  /**
   * Metodo che testa la validazione di un nome.
   * 
   * @test {@link UtenzaService#validaNome(String)}
   * 
   * @throws NomeNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = NomeNonValidoException.class)
  public void testaValidaNomeNonValidoMin() 
         throws NomeNonValidoException {
    
    String nome = "a";
    utenzaService.validaNome(nome);
  }
  
  /**
   * Metodo che testa la validazione di un nome.
   * 
   * @test {@link UtenzaService#validaNome(String)}
   * 
   * @throws NomeNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = NomeNonValidoException.class)
  public void testaValidaNomeNonValidoMax() 
         throws NomeNonValidoException {
    
    String nome = "nomePersonanomePersonanomePersonanomePersonanomePersonanomePersonanomePersona"
        + "        nomePersonanomePersonanomePersonanomePersonanomePersonanomePersonanomePersona"
        + "        nomePersonanomePersonanomePersonanomePersonanomePersonanomePersonanomePersona"
        + "        nomePersonanomePersonanomePersonanomePersonanomePersonanomePersonanomePersona";
    utenzaService.validaNome(nome);
  }
  
  /**
   * Metodo che testa la validazione di un nome.
   * 
   * @test {@link UtenzaService#validaCognome(String)}
   * 
   * @result Il test è superato se la validazione avviene correttamente
   */
  @Test
  public void testaValidaCognome() {
    
    String cognome = "Cognome"; 
    try {
      utenzaService.validaCognome(cognome);
    } catch (CognomeNonValidoException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione di un nome.
   * 
   * @test {@link UtenzaService#validaCognome(String)}
   * 
   * @throws CognomeNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = CognomeNonValidoException.class)
  public void testaValidaCognomeNullo() 
         throws CognomeNonValidoException {
    
    String cognome = null;
    utenzaService.validaCognome(cognome);
  }
  
  /**
   * Metodo che testa la validazione di un nome.
   * 
   * @test {@link UtenzaService#validaCognome(String)}
   * 
   * @throws CognomeNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = CognomeNonValidoException.class)
  public void testaValidaCognomeNonValidoMin() 
         throws CognomeNonValidoException {
    
    String cognome = "a";
    utenzaService.validaCognome(cognome);
  }
  
  /**
   * Metodo che testa la validazione di un nome.
   * 
   * @test {@link UtenzaService#validaCognome(String)}
   * 
   * @throws CognomeNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = CognomeNonValidoException.class)
  public void testaValidaCognomeNonValidoMax() 
         throws CognomeNonValidoException {
    
    String cognome = "cognomePersonacognomePersonacognomePersonacognomePersonacognomePersona"
        + "           cognomePersonacognomePersonacognomePersonacognomePersonacognomePersona"
        + "           cognomePersonacognomePersonacognomePersonacognomePersonacognomePersona"
        + "           cognomePersonacognomePersonacognomePersonacognomePersonacognomePersona"
        + "           cognomePersonacognomePersonacognomePersonacognomePersonacognomePersona";
    utenzaService.validaCognome(cognome);
  }
  
  /**
   * Metodo che testa la validazione del sesso.
   * 
   * @test {@link UtenzaService#validaSesso(String)}
   * 
   * @result Il test è superato se la validazione avviene correttamente
   */
  @Test
  public void testaValidaSessoFemminile() {
    
    String sesso = UtenteRegistrato.SESSO_FEMMINILE;
    try {
      utenzaService.validaSesso(sesso);
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione del sesso.
   * 
   * @test {@link UtenzaService#validaSesso(String)}
   * 
   * @result Il test è superato se la validazione avviene correttamente
   */
  @Test
  public void testaValidaSessoMaschile() {
    
    String sesso = UtenteRegistrato.SESSO_MASCHILE;
    try {
      utenzaService.validaSesso(sesso);
    } catch (SessoNonValidoException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione del sesso.
   * 
   * @test {@link UtenzaService#validaSesso(String)}
   * 
   * @throws SessoNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = SessoNonValidoException.class)
  public void testaValisaSessoNullo()
         throws SessoNonValidoException {
    
    String sesso = null;
    utenzaService.validaSesso(sesso);
  }
  
  /**
   * Metodo che testa la validazione del sesso.
   * 
   * @test {@link UtenzaService#validaSesso(String)}
   * 
   * @throws SessoNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = SessoNonValidoException.class)
  public void testaValisaSessoNonValido()
         throws SessoNonValidoException {
    
    String sesso = "T";
    utenzaService.validaSesso(sesso);
  }
  
  /**
   * Metodo che testa la validazione del numero di telefono.
   * 
   * @test {@link UtenzaService#validaTelefono(String)}
   * 
   * @result Il test è superato se la validazione avviene correttamente
   */
  @Test
  public void testaValidaTelefono() {
    
    String telefono = "3331122345";
    try {
      utenzaService.validaTelefono(telefono);
    } catch (TelefonoNonValidoException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa la validazione del numero di telefono.
   * 
   * @test {@link UtenzaService#validaTelefono(String)}
   * 
   * @throws TelefonoNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = TelefonoNonValidoException.class)
  public void testaValidaTelefonoNullo()
         throws TelefonoNonValidoException {
    
    String telefono = null;
    utenzaService.validaTelefono(telefono);
  }
  
  /**
   * Metodo che testa la validazione del numero di telefono.
   * 
   * @test {@link UtenzaService#validaTelefono(String)}
   * 
   * @throws TelefonoNonValidoException
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = TelefonoNonValidoException.class)
  public void testaValidaTelefonoNonValido()
         throws TelefonoNonValidoException {
    
    String telefono = "12345";
    utenzaService.validaTelefono(telefono);
  }
  
}

