package it.unisa.di.tirociniosmart.domandetirocinio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
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
import it.unisa.di.tirociniosmart.progettiformativi.DescrizioneProgettoNonValidaException;
import it.unisa.di.tirociniosmart.progettiformativi.NomeProgettoNonValidoException;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettiFormativiService;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.studenti.DataDiNascitaStudenteNonValidaException;
import it.unisa.di.tirociniosmart.studenti.IdRichiestaIscrizioneNonValidoException;
import it.unisa.di.tirociniosmart.studenti.IndirizzoStudenteNonValidoException;
import it.unisa.di.tirociniosmart.studenti.MatricolaStudenteEsistenteException;
import it.unisa.di.tirociniosmart.studenti.MatricolaStudenteNonValidaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizione;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneGestitaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneInAttesaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneRifiutataException;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.studenti.StudentiService;
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
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
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
 * Testa i servizi offerti da {@link DomandeTirocinioService} con tutte le dipedenze.
 * 
 * @see DomandeTirocinioService  
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class DomandeTirocinioServiceIT {

  @Autowired
  private DomandeTirocinioService domandeService;
  
  @Autowired
  private UtenzaService utenzaService;
  
  @Autowired 
  private ImpiegatoUfficioTirociniRepository impiegatoRepository;
  
  @Autowired
  private ConvenzioniService convenzioniService;
  
  @Autowired
  private ProgettiFormativiService progettiService;
  
  @Autowired
  private StudentiService studentiService;
  
  /**
   * Testa il metodo che permette di salvare una domanda di tirocinio sul database.
   * 
   * @test {@link DomandeTirocinioService#registraDomandaTirocinio(DomandaTirocinio)}
   * 
   * @result il test è superato se la domanda di tirocinio viene salvata correttamente sul database
   * 
   */
  @Test
  public void registraDomandaTirocinio() {
    // Crea un azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea un delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea una richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    //Registra richiesta di convenzionamente
    try {
      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
      richiesta = azienda.getRichiesta();
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getLocalizedMessage());
    }
    
    // Crea un impiegato
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Inserisci l'impiegato nel sistema
    impiegatoRepository.save(impiegato);
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Esegue logout dell'impiegato
    utenzaService.logout();
    
    // Crea un nuovo progetto formativo
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("Sviluppo applicazioni web");
    progetto.setAzienda(azienda);
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Aggiunge un nuovo progetto formativo
    try {
      progettiService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Crea lo studente 
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescoF");
    
    // Crea la richiesta iscrizione
    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiestaIscrizione.setCommentoUfficioTirocini("commento");
    
    // Registra richiesta d'iscrizione sul database
    try {
      studente = studentiService.registraRichiestaIscrizione(studente);
      richiestaIscrizione = studente.getRichiestaIscrizione();   
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta d'iscrizione dello studente
    try {
      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    // Crea una domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setProgettoFormativo(progetto);
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setCfu(8);
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 03, 11));
    domanda.setFineTirocinio(LocalDate.of(2018, 04, 01));
    domanda.setCommentoStudente("commento studente");
    
    //Effettua login dello studente
    try {
      utenzaService.login("FrancescoF", "FrancescoF");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    DomandaTirocinio domandaSalvata = new DomandaTirocinio();
    try {
      // Registra domanda di tirocinio sul database
      domandaSalvata = domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    }
    
    assertThat(domanda, is(equalTo(domandaSalvata)));
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di accettare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#accettaDomandaTirocinio(long, String)}
   * 
   * @result il test è superato se la domanda di tirocinio viene accettata correttamente sul 
   *         database
   * 
   */
  @Test
  public void accettaDomandaTirocinio() {
    // Crea un azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea un delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea una richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    //Registra richiesta di convenzionamente
    try {
      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
      richiesta = azienda.getRichiesta();
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getLocalizedMessage());
    }
    
    // Crea un impiegato
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Inserisci l'impiegato nel sistema
    impiegatoRepository.save(impiegato);
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Esegue logout dell'impiegato
    utenzaService.logout();
    
    // Crea un nuovo progetto formativo
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("Sviluppo applicazioni web");
    progetto.setAzienda(azienda);
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Aggiunge un nuovo progetto formativo
    try {
      progettiService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Crea lo studente 
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescoF");
    
    // Crea la richiesta iscrizione
    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiestaIscrizione.setCommentoUfficioTirocini("commento");
    
    // Registra richiesta d'iscrizione sul database
    try {
      studente = studentiService.registraRichiestaIscrizione(studente);
      richiestaIscrizione = studente.getRichiestaIscrizione();   
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta d'iscrizione dello studente
    try {
      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    // Crea una domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setProgettoFormativo(progetto);
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setCfu(8);
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 03, 11));
    domanda.setFineTirocinio(LocalDate.of(2018, 04, 01));
    domanda.setCommentoStudente("commento studente");
    
    //Effettua login dello studente
    try {
      utenzaService.login("FrancescoF", "FrancescoF");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
   
    try {
      // Registra domanda di tirocinio sul database
      domanda = domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    }
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    //
    try {
      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    assertThat(domanda.getStatus(), is(equalTo(DomandaTirocinio.ACCETTATA)));
  }
  
  /**
   * Testa il metodo che permette ad un delegato aziendale di rifiutare una domanda di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#rifiutaDomandaTirocinio(long, String)}
   * 
   * @result il test è superato se la domanda di tirocinio viene rifiutata sul database
   * 
   */
  @Test
  public void rifiutaDomandaTirocinio() {
    // Crea un azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea un delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea una richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    //Registra richiesta di convenzionamente
    try {
      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
      richiesta = azienda.getRichiesta();
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getLocalizedMessage());
    }
    
    // Crea un impiegato
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Inserisci l'impiegato nel sistema
    impiegatoRepository.save(impiegato);
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Esegue logout dell'impiegato
    utenzaService.logout();
    
    // Crea un nuovo progetto formativo
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("Sviluppo applicazioni web");
    progetto.setAzienda(azienda);
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Aggiunge un nuovo progetto formativo
    try {
      progettiService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Crea lo studente 
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescoF");
    
    // Crea la richiesta iscrizione
    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiestaIscrizione.setCommentoUfficioTirocini("commento");
    
    // Registra richiesta d'iscrizione sul database
    try {
      studente = studentiService.registraRichiestaIscrizione(studente);
      richiestaIscrizione = studente.getRichiestaIscrizione();   
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta d'iscrizione dello studente
    try {
      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    // Crea una domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setProgettoFormativo(progetto);
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setCfu(8);
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 03, 11));
    domanda.setFineTirocinio(LocalDate.of(2018, 04, 01));
    domanda.setCommentoStudente("commento studente");
    
    //Effettua login dello studente
    try {
      utenzaService.login("FrancescoF", "FrancescoF");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
   
    try {
      // Registra domanda di tirocinio sul database
      domanda = domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    }
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    //
    try {
      domanda = domandeService.rifiutaDomandaTirocinio(domanda.getId(), "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
        | RichiestaNonAutorizzataException | CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    assertThat(domanda.getStatus(), is(equalTo(DomandaTirocinio.RIFIUTATA)));
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocini di approvare una domanda 
   * di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#approvaDomandaTirocinio(long)}
   * 
   * @result il test è superato se la domanda di tirocinio viene approvata correttamente 
   * 
   */
  @Test
  public void approvaDomandaTirocinio() {
    // Crea un azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea un delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea una richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    //Registra richiesta di convenzionamente
    try {
      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
      richiesta = azienda.getRichiesta();
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getLocalizedMessage());
    }
    
    // Crea un impiegato
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Inserisci l'impiegato nel sistema
    impiegatoRepository.save(impiegato);
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Esegue logout dell'impiegato
    utenzaService.logout();
    
    // Crea un nuovo progetto formativo
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("Sviluppo applicazioni web");
    progetto.setAzienda(azienda);
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Aggiunge un nuovo progetto formativo
    try {
      progettiService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Crea lo studente 
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescoF");
    
    // Crea la richiesta iscrizione
    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiestaIscrizione.setCommentoUfficioTirocini("commento");
    
    // Registra richiesta d'iscrizione sul database
    try {
      studente = studentiService.registraRichiestaIscrizione(studente);
      richiestaIscrizione = studente.getRichiestaIscrizione();   
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta d'iscrizione dello studente
    try {
      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    // Crea una domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setProgettoFormativo(progetto);
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setCfu(8);
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 03, 11));
    domanda.setFineTirocinio(LocalDate.of(2018, 04, 01));
    domanda.setCommentoStudente("commento studente");
    
    //Effettua login dello studente
    try {
      utenzaService.login("FrancescoF", "FrancescoF");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
   
    try {
      // Registra domanda di tirocinio sul database
      domanda = domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    }
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    //
    try {
      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      domanda = domandeService.approvaDomandaTirocinio(domanda.getId());
    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    assertThat(domanda.getStatus(), is(equalTo(DomandaTirocinio.APPROVATA)));
  }
  
  /**
   * Testa il metodo che permette ad un impiegato dell'ufficio tirocini di respingere una domanda 
   * di tirocinio.
   * 
   * @test {@link DomandeTirocinioService#respingiDomandaTirocinio(long, String)}
   * 
   * @result il test è superato se la domanda di tirocinio viene respinta correttamente 
   * 
   */
  @Test
  public void respingiDomandaTirocinio() {
    // Crea un azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea un delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea una richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    //Registra richiesta di convenzionamente
    try {
      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
      richiesta = azienda.getRichiesta();
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getLocalizedMessage());
    }
    
    // Crea un impiegato
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Inserisci l'impiegato nel sistema
    impiegatoRepository.save(impiegato);
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Esegue logout dell'impiegato
    utenzaService.logout();
    
    // Crea un nuovo progetto formativo
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("Sviluppo applicazioni web");
    progetto.setAzienda(azienda);
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Aggiunge un nuovo progetto formativo
    try {
      progettiService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Crea lo studente 
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescoF");
    
    // Crea la richiesta iscrizione
    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiestaIscrizione.setCommentoUfficioTirocini("commento");
    
    // Registra richiesta d'iscrizione sul database
    try {
      studente = studentiService.registraRichiestaIscrizione(studente);
      richiestaIscrizione = studente.getRichiestaIscrizione();   
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta d'iscrizione dello studente
    try {
      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    // Crea una domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setProgettoFormativo(progetto);
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setCfu(8);
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 03, 11));
    domanda.setFineTirocinio(LocalDate.of(2018, 04, 01));
    domanda.setCommentoStudente("commento studente");
    
    //Effettua login dello studente
    try {
      utenzaService.login("FrancescoF", "FrancescoF");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
   
    try {
      // Registra domanda di tirocinio sul database
      domanda = domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    }
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    //
    try {
      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      domanda = domandeService.respingiDomandaTirocinio(domanda.getId(), "commentoImpiegato");
    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
        | RichiestaNonAutorizzataException | CommentoDomandaTirocinioNonValidoException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    assertThat(domanda.getStatus(), is(equalTo(DomandaTirocinio.RESPINTA)));
  }
  
  /**
   * Testa il metodo che permette di ottenere l'elenco delle domande di tirocinio il cui stato è 
   * {@link DomandaTirocinio#IN_ATTESA} per uno studente ed un delegato, 
   * {@link DomandaTirocinio#ACCETTATA} per un impiegato dell'ufficio tirocini.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeRicevute()}
   * 
   * @result il test è superato se l'elenco delle domande viene caricato correttamente dal database
   * 
   */
  @Test
  public void elencaDomandeRicevute() {
    // Crea un azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea un delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea una richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    //Registra richiesta di convenzionamente
    try {
      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
      richiesta = azienda.getRichiesta();
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getLocalizedMessage());
    }
    
    // Crea un impiegato
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Inserisci l'impiegato nel sistema
    impiegatoRepository.save(impiegato);
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Esegue logout dell'impiegato
    utenzaService.logout();
    
    // Crea un nuovo progetto formativo
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("Sviluppo applicazioni web");
    progetto.setAzienda(azienda);
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Aggiunge un nuovo progetto formativo
    try {
      progettiService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Crea lo studente 
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescoF");
    
    // Crea la richiesta iscrizione
    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiestaIscrizione.setCommentoUfficioTirocini("commento");
    
    // Registra richiesta d'iscrizione sul database
    try {
      studente = studentiService.registraRichiestaIscrizione(studente);
      richiestaIscrizione = studente.getRichiestaIscrizione();   
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta d'iscrizione dello studente
    try {
      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    // Crea una domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setProgettoFormativo(progetto);
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setCfu(8);
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 03, 11));
    domanda.setFineTirocinio(LocalDate.of(2018, 04, 01));
    domanda.setCommentoStudente("commento studente");
    
    // Crea una domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setProgettoFormativo(progetto);
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda2.setCfu(8);
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 02, 11));
    domanda2.setFineTirocinio(LocalDate.of(2018, 03, 01));
    domanda2.setCommentoStudente("commento studente");
    
    //Effettua login dello studente
    try {
      utenzaService.login("FrancescoF", "FrancescoF");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
   
    try {
      // Registra domanda di tirocinio sul database
      domanda = domandeService.registraDomandaTirocinio(domanda);
      domanda2 = domandeService.registraDomandaTirocinio(domanda2);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    }
    
    // Crea lista ed aggiungi domande
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda2);
    
    try {
      List<DomandaTirocinio> domandeOttenuteStudente = domandeService.elencaDomandeRicevute();
      assertThat(listaDomande, everyItem(isIn(domandeOttenuteStudente)));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
    // Effettua logout dello studente
    utenzaService.logout();
    
    //Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      List<DomandaTirocinio> domandeOttenuteDelegato = domandeService.elencaDomandeRicevute();
      assertThat(listaDomande, everyItem(isIn(domandeOttenuteDelegato)));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Approva domande di tirocinio
    try {
      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
      domanda2 = domandeService.accettaDomandaTirocinio(domanda2.getId(), "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      List<DomandaTirocinio> domandeOttenuteImpiegato = domandeService.elencaDomandeRicevute();
      assertThat(listaDomande, is(equalTo(domandeOttenuteImpiegato)));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
    //Effettua logout dell'impiegato
    utenzaService.logout();
  }
  
  /**
   * Testa il metodo che permette di ottenere l'elenco delle domande di tirocinio inviate.
   * 
   * @test {@link DomandeTirocinioService#elencaDomandeInviate()}
   * 
   * @result il test è superato se l'elenco delle domande viene caricato correttamente dal database
   * 
   */
  @Test
  public void elencaDomandeInviate() {
    // Crea un azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea un delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea una richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    //Registra richiesta di convenzionamente
    try {
      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
      richiesta = azienda.getRichiesta();
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getLocalizedMessage());
    }
    
    // Crea un impiegato
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Inserisci l'impiegato nel sistema
    impiegatoRepository.save(impiegato);
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Esegue logout dell'impiegato
    utenzaService.logout();
    
    // Crea un nuovo progetto formativo
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("WEB");
    progetto.setDescrizione("Sviluppo applicazioni web");
    progetto.setAzienda(azienda);
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Aggiunge un nuovo progetto formativo
    try {
      progettiService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Crea lo studente 
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescoF");
    
    // Crea la richiesta iscrizione
    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiestaIscrizione.setCommentoUfficioTirocini("commento");
    
    // Registra richiesta d'iscrizione sul database
    try {
      studente = studentiService.registraRichiestaIscrizione(studente);
      richiestaIscrizione = studente.getRichiestaIscrizione();   
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta d'iscrizione dello studente
    try {
      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    // Crea una domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setProgettoFormativo(progetto);
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setCfu(8);
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(LocalDate.of(2018, 03, 11));
    domanda.setFineTirocinio(LocalDate.of(2018, 04, 01));
    domanda.setCommentoStudente("commento studente");
    
    // Crea una domanda di tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setProgettoFormativo(progetto);
    domanda2.setStudente(studente);
    domanda2.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda2.setCfu(8);
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 02, 11));
    domanda2.setFineTirocinio(LocalDate.of(2018, 03, 01));
    domanda2.setCommentoStudente("commento studente");
    
    //Effettua login dello studente
    try {
      utenzaService.login("FrancescoF", "FrancescoF");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
   
    try {
      // Registra domanda di tirocinio sul database
      domanda = domandeService.registraDomandaTirocinio(domanda);
      domanda2 = domandeService.registraDomandaTirocinio(domanda2);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dello studente
    utenzaService.logout();
   
    //Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException | RichiestaNonAutorizzataException 
        | DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
    }
    
    // Effettua il logout del delegato
    utenzaService.logout();
    
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
    listaDomande.add(domanda);
    
    //Effettua login dello studente
    try {
      utenzaService.login("FrancescoF", "FrancescoF");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      List<DomandaTirocinio> domandeStudente = domandeService.elencaDomandeInviate();
      assertThat(listaDomande, everyItem(isIn(domandeStudente)));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dello studente
    utenzaService.logout();
    
    // Effettua login come delegato
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      domanda2 = domandeService.accettaDomandaTirocinio(domanda2.getId(), "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException | RichiestaNonAutorizzataException 
        | DomandaTirocinioGestitaException e) {
      fail(e.getMessage());
    }
    
    // Effettua il logout del delegato
    utenzaService.logout();
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      domanda2 = domandeService.respingiDomandaTirocinio(domanda2.getId(), "commentoImpiegato");
    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
        | CommentoDomandaTirocinioNonValidoException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout di impiegato
    utenzaService.logout();
    
    // Effettua login come delegato
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      List<DomandaTirocinio> domandeDelegato = domandeService.elencaDomandeInviate();
      assertThat(listaDomande, everyItem(isIn(domandeDelegato)));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua il logout del delegato
    utenzaService.logout();
    
  }
  
  /**
   * Testa il metodo che permette di ottenere l'elenco dei tirocini in corso.
   * 
   * @test {@link DomandeTirocinioService#elencaTirociniInCorso()}
   * 
   * @result il test è superato se l'elenco delle domande viene caricato correttamente dal database
   * 
   */
  @Test
  public void elencaTirociniInCorso() {
    // Crea un azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea un delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea una richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    //Registra richiesta di convenzionamente
    try {
      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
      richiesta = azienda.getRichiesta();
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getLocalizedMessage());
    }
    
    // Crea un impiegato
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Inserisci l'impiegato nel sistema
    impiegatoRepository.save(impiegato);
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Esegue logout dell'impiegato
    utenzaService.logout();
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Inizializza un progetto formativo 
    ProgettoFormativo progetto = new ProgettoFormativo();
    try {

      progetto.setNome("WEB");
      progetto.setDescrizione("Sviluppo applicazioni web");
      
      // Aggiungi un nuovo progetto formativo
      progetto = progettiService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout del delegato
    utenzaService.logout();
    
    // Crea lo studente 
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso(Studente.SESSO_MASCHILE);
    studente.setUsername("FrancescoF");
    studente.setPassword("francescoF");
    
    // Crea la richiesta iscrizione
    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiestaIscrizione.setCommentoUfficioTirocini("commento");
    
    // Registra richiesta d'iscrizione sul database
    try {
      studente = studentiService.registraRichiestaIscrizione(studente);
      richiestaIscrizione = studente.getRichiestaIscrizione();   
    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Approva richiesta d'iscrizione dello studente
    try {
      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
    // Ottieni la data di oggi
    LocalDate oggi = LocalDate.now();
    
    // Definisci la data di inizio tirocinio pari a domani
    LocalDate domani = oggi.plusDays(1);
    
    // Definisci la data di fine tirocinio fra due mesi
    LocalDate traDueMesi = oggi.plusDays(60);
    
    // Crea una domanda di tirocinio
    DomandaTirocinio domanda = new DomandaTirocinio();
    domanda.setProgettoFormativo(progetto);
    domanda.setStudente(studente);
    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
    domanda.setCfu(8);
    domanda.setData(LocalDateTime.now());
    domanda.setInizioTirocinio(domani);
    domanda.setFineTirocinio(traDueMesi);
    domanda.setCommentoStudente("commento studente");

    //Effettua login dello studente
    try {
      utenzaService.login("FrancescoF", "FrancescoF");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
   
    try {
      // Registra la domanda di tirocinio sul database
      domanda = domandeService.registraDomandaTirocinio(domanda);
    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout di studente
    utenzaService.logout();
    
    // Effettua login del delegato aziendale
    try {
      utenzaService.login("tonystark", "ironman");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    try {
      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout di delegato
    utenzaService.logout();
    
    // Effettua il login come impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }   
    
    try {
      domanda = domandeService.approvaDomandaTirocinio(domanda.getId());
    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
        | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    List<DomandaTirocinio> listaDomande = new ArrayList<DomandaTirocinio>();
    listaDomande.add(domanda);
     
    try {
      List<DomandaTirocinio> domandeOttenute = domandeService.elencaTirociniInCorso(domani);
      assertThat(listaDomande, everyItem(isIn(domandeOttenute)));
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettua logout dell'impiegato
    utenzaService.logout();
    
  }

}
