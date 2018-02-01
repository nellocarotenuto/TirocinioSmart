package it.unisa.di.tirociniosmart.studenti;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoInAttesaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoRifiutataException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirociniRepository;
import it.unisa.di.tirociniosmart.utenza.CognomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.CredenzialiNonValideException;
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

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;




/**
 * Testa i servizi offerti da {@link StudentiService} con tutte le dipedenze.
 * 
 * @see StudentiService  
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class StudentiServiceIT {

  @Autowired
  private StudentiService studentiService;
  
  @Autowired
  private ImpiegatoUfficioTirociniRepository impiegatoRepository;
  
  @Autowired
  private UtenzaService utenzaService;
  
  /**
   * Testa il metodo per aggiungere al database la richiesta di iscrizione.
   * 
   * @test {@link StudentiService#registraRichiestaIscrizione(Studente)}
   * 
   * @result il test è superato se la richiesta viene registrata correttamente
   */
  @Test
  public void registraRichiestaIscrizione() {
    
    // Crea lo studente #1 
    Studente studente1 = new Studente();
    studente1.setNome("Francesco");
    studente1.setCognome("Facchinetti");
    studente1.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente1.setDataRegistrazione(LocalDateTime.now());
    studente1.setEmail("francesco@facchinetti.com");
    studente1.setIndirizzo("Via francesco, 9");
    studente1.setMatricola("0512103434");
    studente1.setTelefono("3331234123");
    studente1.setSesso("M");
    studente1.setUsername("FrancescoF");
    studente1.setPassword("francescof");
       
    Studente studente1Registrato = new Studente();
    // Effettua la registrazione di una richiesta di iscrizione
    try {
      studente1Registrato = studentiService.registraRichiestaIscrizione(studente1);
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Controlla se la richiesta è stata registrata correttamente, controllando che
    // il suo stato sia impostato su IN_ATTESA
    assertThat(RichiestaIscrizione.IN_ATTESA, is(equalTo(
                                        studente1Registrato.getRichiestaIscrizione().getStatus())));
  }
  
  /**
   * Testa il metodo per l'approvazione di una richiesta di iscrizione.
   * 
   * @test {@link StudentiService#approvaRichiestaIscrizione(long)}
   * 
   * @result il test è superato se la richiesta viene approvata correttamente
   */
  @Test
  public void approvaRichiestaIscrizione() {
    
    // Crea lo studente #1 
    Studente studente1 = new Studente();
    studente1.setNome("Francesco");
    studente1.setCognome("Facchinetti");
    studente1.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente1.setDataRegistrazione(LocalDateTime.now());
    studente1.setEmail("francesco@facchinetti.com");
    studente1.setIndirizzo("Via francesco, 9");
    studente1.setMatricola("0512103434");
    studente1.setTelefono("3331234123");
    studente1.setSesso("M");
    studente1.setUsername("FrancescoF");
    studente1.setPassword("francescof");
       
    Studente studente1Registrato = new Studente();

    try {
      studente1Registrato = studentiService.registraRichiestaIscrizione(studente1);
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Crea l'impiegato ufficio tirocini che approverà la richiesta iscrizione
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Impiegato");
    impiegato.setCognome("Impiegato");
    impiegato.setEmail("impiegato@impiegato.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Salviamo l'impiegato ufficio tirocini
    impiegatoRepository.save(impiegato);
    
    // Effettua il login l'impiegato
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Effettua l'approvazione della richiesta di iscrizione
    try {
      studentiService.approvaRichiestaIscrizione(
                          studente1Registrato.getRichiestaIscrizione().getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Controlla se lo status della richiesta approvata sia effettivamente cambiato
    assertThat(studente1Registrato.getRichiestaIscrizione().getStatus(), 
               is(equalTo(RichiestaIscrizione.APPROVATA)));
    
    // Effettua il logout dell'impiegato ufficio tirocini
    utenzaService.logout();
  }
  
  /**
   * Testa il metodo per il rifiuto di una richiesta di iscrizione.
   * 
   * @test {@link StudentiService#rifiutaRichiestaIscrizione(long)}
   * 
   * @result il test è superato se la richiesta viene approvata correttamente
   */
  @Test 
  public void rifiutaRichiestaIscrizione() {
    
    // Crea lo studente #1 
    Studente studente1 = new Studente();
    studente1.setNome("Francesco");
    studente1.setCognome("Facchinetti");
    studente1.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente1.setDataRegistrazione(LocalDateTime.now());
    studente1.setEmail("francesco@facchinetti.com");
    studente1.setIndirizzo("Via francesco, 9");
    studente1.setMatricola("0512103434");
    studente1.setTelefono("3331234123");
    studente1.setSesso("M");
    studente1.setUsername("FrancescoF");
    studente1.setPassword("francescof");
       
    Studente studente1Registrato = new Studente();
    // Effettua la registrazione dello studente#1
    try {
      studente1Registrato = studentiService.registraRichiestaIscrizione(studente1);
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Crea l'impiegato ufficio tirocini che approverà la richiesta iscrizione
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Impiegato");
    impiegato.setCognome("Impiegato");
    impiegato.setEmail("impiegato@impiegato.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Salviamo l'impiegato ufficio tirocini
    impiegatoRepository.save(impiegato);
    
    // Effettua il login l'impiegato
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Effettua il rifiuto della richiesta di iscrizione
    try {
      studentiService.rifiutaRichiestaIscrizione(
                          studente1Registrato.getRichiestaIscrizione().getId(), "commento");
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException 
        | CommentoRichiestaIscrizioneNonValidoException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    
    // Controlla se lo status della richiesta approvata sia effettivamente cambiato
    assertThat(studente1Registrato.getRichiestaIscrizione().getStatus(), 
               is(equalTo(RichiestaIscrizione.RIFIUTATA)));
    
    // Effettua il logout dell'impiegato ufficio tirocini
    utenzaService.logout();
  }
  
  /**
   * Testa il metodo per il elencare una lista di richieste di iscrizione.
   * 
   * @test {@link StudentiService#elencaListaRichiesteIscrizione()}
   * 
   * @result il test è superato se la lista di richieste viene visualizzata correttamente
   */
  @Test
  public void elencaListaRichiesteIscrizione() {
    
    // Crea lo studente #1 
    Studente studente1 = new Studente();
    studente1.setNome("Francesco");
    studente1.setCognome("Facchinetti");
    studente1.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente1.setDataRegistrazione(LocalDateTime.now());
    studente1.setEmail("francesco@facchinetti.com");
    studente1.setIndirizzo("Via francesco, 9");
    studente1.setMatricola("0512103434");
    studente1.setTelefono("3331234123");
    studente1.setSesso("M");
    studente1.setUsername("FrancescoF");
    studente1.setPassword("francescof");
       
    Studente studente1Registrato = new Studente();
    // Effettua la registrazione dello studente#1
    try {
      studente1Registrato = studentiService.registraRichiestaIscrizione(studente1);
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    //Crea lo studente #2
    Studente studente2 = new Studente();
    studente2.setNome("Maria");
    studente2.setCognome("Adinolfi");
    studente2.setDataDiNascita(LocalDate.of(1997, 11, 10));
    studente2.setDataRegistrazione(LocalDateTime.now());
    studente2.setEmail("maria@adinolfi.com");
    studente2.setIndirizzo("Via maria, 9");
    studente2.setMatricola("0512103774");
    studente2.setTelefono("3331644123");
    studente2.setSesso("F");
    studente2.setUsername("MariaA");
    studente2.setPassword("MariaA");
    
    Studente studente2Registrato = new Studente();
    // Effettua la registrazione dello studente#1
    try {
      studente2Registrato = studentiService.registraRichiestaIscrizione(studente2);
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    //Crea lo studente #3
    Studente studente3 = new Studente();
    studente3.setNome("Valerio");
    studente3.setCognome("Stanco");
    studente3.setDataDiNascita(LocalDate.of(1995, 3, 10));
    studente3.setDataRegistrazione(LocalDateTime.now());
    studente3.setEmail("valerio@stanco.com");
    studente3.setIndirizzo("Via valerio, 10");
    studente3.setMatricola("0512103574");
    studente3.setTelefono("3291644123");
    studente3.setSesso("M");
    studente3.setUsername("ValerioS");
    studente3.setPassword("ValerioS");
    
    Studente studente3Registrato = new Studente();
    // Effettua la registrazione dello studente#1
    try {
      studente3Registrato = studentiService.registraRichiestaIscrizione(studente3);
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    List<RichiestaIscrizione> listaRichieste = new ArrayList<RichiestaIscrizione>();
    listaRichieste.add(studente1Registrato.getRichiestaIscrizione());
    listaRichieste.add(studente2Registrato.getRichiestaIscrizione());
    listaRichieste.add(studente3Registrato.getRichiestaIscrizione());
    
    // Crea l'impiegato ufficio tirocini che approverà la richiesta iscrizione
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Impiegato");
    impiegato.setCognome("Impiegato");
    impiegato.setEmail("impiegato@impiegato.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Salviamo l'impiegato ufficio tirocini
    impiegatoRepository.save(impiegato);
    
    // Effettua il login l'impiegato
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Controlla se le richieste registrate sono presenti effettivamente nel database
    try {
      assertThat(listaRichieste, everyItem(isIn(studentiService.elencaListaRichiesteIscrizione())));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettuo il logout dell'impiegato
    utenzaService.logout();
  }
}
