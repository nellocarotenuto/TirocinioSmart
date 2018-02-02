package it.unisa.di.tirociniosmart.utenza;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.CommentoRichiestaConvenzionamentoNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.ConvenzioniService;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaEsistenteException;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.IdRichiestaConvenzionamentoNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.IndirizzoAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.NomeAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.PartitaIvaAziendaEsistenteException;
import it.unisa.di.tirociniosmart.convenzioni.PartitaIvaAziendaNonValidaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoGestitaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoInAttesaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoRifiutataException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirociniRepository;
import it.unisa.di.tirociniosmart.studenti.DataDiNascitaStudenteNonValidaException;
import it.unisa.di.tirociniosmart.studenti.IdRichiestaIscrizioneNonValidoException;
import it.unisa.di.tirociniosmart.studenti.IndirizzoStudenteNonValidoException;
import it.unisa.di.tirociniosmart.studenti.MatricolaStudenteEsistenteException;
import it.unisa.di.tirociniosmart.studenti.MatricolaStudenteNonValidaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneGestitaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneInAttesaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneRifiutataException;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.studenti.StudentiService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * Testa i servizi offerti da {@link UtenzaService} con tutte le dipedenze.
 * 
 * @see UtenzaService  
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class UtenzaServiceIT {

  @Autowired
  private UtenzaService utenzaService;
  
  @Autowired
  private ImpiegatoUfficioTirociniRepository impiegatoRepository;
  
  @Autowired
  private StudentiService studentiService;
  
  @Autowired
  private ConvenzioniService convenzioniService;
  
  /**
   * Testa il metodo per effettuare il login all'interno del sistema.
   * 
   * @test {@link UtenzaService#login(String, String)}
   * 
   * @result Il test è superato se il login va a buon fine
   */
  @Test 
  public void login() {
    
    // Crea l'impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Impiegato");
    impiegato.setCognome("Impiegato");
    impiegato.setEmail("impiegato@impiegato.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Salviamo l'impiegato ufficio tirocini
    impiegatoRepository.save(impiegato);
    
    // Effettuamo il login come impiegato
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    assertThat(impiegato.getUsername(), is(equalTo(
                                          utenzaService.getUtenteAutenticato().getUsername())));
    
    // Effettuo il logout dell'impiegato
    utenzaService.logout();
    
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
    
    // Effettuamo il login come impiegato
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // L'impiegato approva la richiesta di iscrizione
    try {
      studentiService.approvaRichiestaIscrizione(
                                          studente1Registrato.getRichiestaIscrizione().getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettuo il logout dell'impiegato
    utenzaService.logout();
    
    // Effettuo il login di uno studente
    try {
      utenzaService.login(studente1Registrato.getUsername(), studente1Registrato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    assertThat(studente1Registrato.getUsername(), is(equalTo(
                                              utenzaService.getUtenteAutenticato().getUsername())));
   
    // Effettuo il logout dello studente
    utenzaService.logout();
    
    // Crea l'azienda #1 
    Azienda azienda1 = new Azienda();
    azienda1.setId("acmeltd");
    azienda1.setNome("ACME Ltd.");
    azienda1.setPartitaIva("01234567890");
    azienda1.setSenzaBarriere(true);
    azienda1.setIndirizzo("Grand Canyon");
    
    // Crea il delegato aziendale #1
    DelegatoAziendale delegato1 = azienda1.getDelegato();
    delegato1.setUsername("willeeee");
    delegato1.setPassword("beepbeep");
    delegato1.setEmail("wilee@coyote.com");
    delegato1.setNome("Wile E.");
    delegato1.setCognome("Coyote");
    delegato1.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato1.setTelefono("9876543210");
    
    // Crea la richiesta convenzionamento #1
    RichiestaConvenzionamento richiesta1 = azienda1.getRichiesta();
    richiesta1.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
    
    Azienda azienda1Registrata = new Azienda();
    // Effettuo la registrazione dell'azienda
    try {
      azienda1Registrata = convenzioniService.registraRichiestaConvenzionamento(azienda1);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
    }
    
    
    // Effettuamo il login come impiegato
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // L'impiegato approva la richiesta di convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(
                                                        azienda1Registrata.getRichiesta().getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettuo il logout dell'impiegato
    utenzaService.logout();
    
    // Effettuo il login con il delegato aziendale
    try {
      utenzaService.login(delegato1.getUsername(), delegato1.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    assertThat(azienda1Registrata.getDelegato().getUsername(), is(equalTo(
                                              utenzaService.getUtenteAutenticato().getUsername())));
  
    // Effettuo il logout del delegato
    utenzaService.logout();
    
  }
  
  /**
   * Testa il metodo per effettuare il logout dal sistema.
   * 
   * @test {@link UtenzaService#logout()}
   * 
   * @result Il test è superato se il logout va a buon fine
   */
  @Test
  public void logout() {
    
    // Crea l'impiegato ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Impiegato");
    impiegato.setCognome("Impiegato");
    impiegato.setEmail("impiegato@impiegato.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Salviamo l'impiegato ufficio tirocini
    impiegatoRepository.save(impiegato);
    
    // Effettuamo il login come impiegato
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
        
    // Effettuo il logout dell'impiegato
    utenzaService.logout();
    
    // Controllo se il logout è stato effettuato correttamente, controllando che 
    // l'username settato in sessione sia nullo
    assertThat(utenzaService.getUtenteAutenticato(), is(nullValue()));
    
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
    
    // Effettuamo il login come impiegato
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // L'impiegato approva la richiesta di iscrizione
    try {
      studentiService.approvaRichiestaIscrizione(
                                          studente1Registrato.getRichiestaIscrizione().getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettuo il logout dell'impiegato
    utenzaService.logout();
    
    // Effettuo il login di uno studente
    try {
      utenzaService.login(studente1Registrato.getUsername(), studente1Registrato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Effettuo il logout dello studente
    utenzaService.logout();
    
    // Controllo se il logout è stato effettuato correttamente, controllando che 
    // l'username settato in sessione sia nullo
    assertThat(utenzaService.getUtenteAutenticato(), is(nullValue()));
    
    // Crea l'azienda #1 
    Azienda azienda1 = new Azienda();
    azienda1.setId("acmeltd");
    azienda1.setNome("ACME Ltd.");
    azienda1.setPartitaIva("01234567890");
    azienda1.setSenzaBarriere(true);
    azienda1.setIndirizzo("Grand Canyon");
    
    // Crea il delegato aziendale #1
    DelegatoAziendale delegato1 = azienda1.getDelegato();
    delegato1.setUsername("willeeee");
    delegato1.setPassword("beepbeep");
    delegato1.setEmail("wilee@coyote.com");
    delegato1.setNome("Wile E.");
    delegato1.setCognome("Coyote");
    delegato1.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato1.setTelefono("9876543210");
    
    // Crea la richiesta convenzionamento #1
    RichiestaConvenzionamento richiesta1 = azienda1.getRichiesta();
    richiesta1.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
    
    Azienda azienda1Registrata = new Azienda();
    // Effettuo la registrazione dell'azienda
    try {
      azienda1Registrata = convenzioniService.registraRichiestaConvenzionamento(azienda1);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
    }
    
    
    // Effettuamo il login come impiegato
    try {
      utenzaService.login(impiegato.getUsername(), impiegato.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // L'impiegato approva la richiesta di convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(
                                                        azienda1Registrata.getRichiesta().getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettuo il logout dell'impiegato
    utenzaService.logout();
    
    // Effettuo il login con il delegato aziendale
    try {
      utenzaService.login(delegato1.getUsername(), delegato1.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Effettuo il logout del delegato
    utenzaService.logout(); 
   
    // Controllo se il logout è stato effettuato correttamente, controllando che 
    // l'username settato in sessione sia nullo
    assertThat(utenzaService.getUtenteAutenticato(), is(nullValue()));
    
  }
}
