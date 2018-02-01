package it.unisa.di.tirociniosmart.convenzioni;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirociniRepository;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneInAttesaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneRifiutataException;
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
 * Testa i servizi offerti da {@link ConvenzioniService} con tutte le dipedenze.
 * 
 * @see ConvenzioniService  
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class ConvenzioniServiceIT {

  @Autowired
  private ConvenzioniService convenzioniService;
  
  @Autowired
  private UtenzaService utenzaService;
  
  @Autowired 
  private ImpiegatoUfficioTirociniRepository impiegatoRepository;
  
  
  /**
   * Testa il metodo per memorizzare su database una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#registraRichiestaConvenzionamento(Azienda)}
   * 
   * @result il test è superato se la richiesta viene salvata correttamente sul database
   * 
   */
  @Test
  public void richiestaConvenzionamento() {
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
    
    try {
      // Salva la richiesta sul database
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
    }    
    
    try {
      // Verifica che l'azienda ottenuta sia l'azienda salvata
      assertThat(convenzioniService.ottieniAzienda("starkind"), is(equalTo(azienda)));
    } catch (IdAziendaNonValidoException e) {
      fail(e.getMessage());
    }
    
  }
  
  /**
   * Testa il metodo che permette di approvare una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#approvaRichiestaConvenzionamento(long)}
   * 
   * @result il test è superato se la richiesta viene approvata correttamente
   */
  @Test
  public void approvaRichiestaConvenzionamento() {
    // Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    // Registra la richiesta di convenzionamento
    try {
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
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
    
    try {
      // Ottieni la richiesta salvata
      richiesta = convenzioniService.ottieniAzienda("starkind").getRichiesta();
      
      // Approva la richiesta ed aggiornane l'istanza
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
      richiesta = convenzioniService.ottieniAzienda("starkind").getRichiesta();
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException 
        | IdAziendaNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
   
    // Controlla che lo stato sia cambiato in "approvata"
    assertThat(richiesta.getStatus(), is(equalTo(RichiestaConvenzionamento.APPROVATA)));
  }
  
  /**
   * Testa il metodo che permette di rifiutare una richiesta di convenzionamento.
   * 
   * @test {@link ConvenzioniService#rifiutaRichiestaConvenzionamento(long, String)}
   * 
   * @result il test è superato se la richiesta viene rifiutata correttamente
   */
  @Test
  public void rifiutaRichiestaConvenzionamento() {
    // Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    // Registra la richiesta di convenzionamento
    try {
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
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
    
    try {
      // Ottieni la richiesta salvata
      richiesta = convenzioniService.ottieniAzienda("starkind").getRichiesta();
      
      // Approva la richiesta ed aggiornane l'istanza
      convenzioniService.rifiutaRichiestaConvenzionamento(richiesta.getId(), "commentoImpiegato");
      richiesta = convenzioniService.ottieniAzienda("starkind").getRichiesta();
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException 
        | IdAziendaNonValidoException | CommentoRichiestaConvenzionamentoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
   
    // Controlla che lo stato sia cambiato in "rifiutata"
    assertThat(richiesta.getStatus(), is(equalTo(RichiestaConvenzionamento.RIFIUTATA)));
  }
  
  /**
   * Testa il metodo che permette di ottenere una lista di richieste di convenzionamento non ancora
   * approvate o rifiutate.
   * 
   * @test {@link ConvenzioniService#elencaRichiesteConvenzionamentoInAttesa()}
   * 
   * @result il test è superato se l'elenco delle richieste viene caricato correttamente
   */
  @Test
  public void elencaRichiesteConvenzionamentoInAttesa() {
    
    // Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    // Registra la richiesta di convenzionamento dell'azienda 
    try {
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
    }
   
    // Crea l'azienda #2 
    Azienda azienda2 = new Azienda();
    azienda2.setId("cyberdynecorp");
    azienda2.setNome("Cyberdyne System Corporation");
    azienda2.setPartitaIva("54569814752");
    azienda2.setSenzaBarriere(false);
    azienda2.setIndirizzo("Steel Mountain, 57");
    
    // Crea il delegato #2 
    DelegatoAziendale delegato2 = azienda2.getDelegato();
    delegato2.setUsername("milesdyson");
    delegato2.setPassword("terminator");
    delegato2.setEmail("miles@cyberdyne.net");
    delegato2.setNome("Miles");
    delegato2.setCognome("Dyson");
    delegato2.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato2.setTelefono("7451453658");
    
    // Crea la richiesta #2
    RichiestaConvenzionamento richiesta2 = azienda2.getRichiesta();
    richiesta2.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta2.setDataRichiesta(LocalDateTime.of(2017, 12, 31, 23, 59));
   
    try {
      // Registra la richiesta di convenzionamento dell'azienda #2
      convenzioniService.registraRichiestaConvenzionamento(azienda2);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
    }
    
    // Ottieni le richieste salvate
    try {
      richiesta = convenzioniService.ottieniAzienda("starkind").getRichiesta();
    } catch (IdAziendaNonValidoException e2) {
      fail(e2.getMessage());
      e2.printStackTrace();
    }
    try {
      richiesta2 = convenzioniService.ottieniAzienda("cyberdynecorp").getRichiesta();
    } catch (IdAziendaNonValidoException e1) {
      fail(e1.getMessage());
      e1.printStackTrace();
    }
    
    // Crea lista in cui aggiungere le richieste in attesa
    List<RichiestaConvenzionamento> listaRichieste = new ArrayList<RichiestaConvenzionamento>();
    listaRichieste.add(richiesta);
    listaRichieste.add(richiesta2);
    
    List<RichiestaConvenzionamento> listaRichiesteOttenute = 
                                                         new ArrayList<RichiestaConvenzionamento>();
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
    
    try {
      // Permette di ottenere l'elenco delle richieste
      listaRichiesteOttenute = convenzioniService.elencaRichiesteConvenzionamentoInAttesa();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Verifica che l'elenco delle richieste salvate sia uguale all'elenco delle richieste ottenute
    // invocando il metodo del service
    assertThat(listaRichieste, everyItem(isIn(listaRichiesteOttenute)));
  }
  
  /**
   * Testa il metodo che permette di ottenere l'elenco delle aziende convenzionate. 
   * 
   * @test {@link ConvenzioniService#elencaAziendeConvenzionate()}
   * 
   * @result il test è superato se l'elenco delle aziende convenzionate è caricato correttamente
   */
  @Test
  public void elencaAziendeConvenzionate() {
    
    // Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    // Registra la richiesta di convenzionamento
    try {
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
    }
   
    // Crea l'azienda #2 
    Azienda azienda2 = new Azienda();
    azienda2.setId("cyberdynecorp");
    azienda2.setNome("Cyberdyne System Corporation");
    azienda2.setPartitaIva("54569814752");
    azienda2.setSenzaBarriere(false);
    azienda2.setIndirizzo("Steel Mountain, 57");
    
    // Crea il delegato #2
    DelegatoAziendale delegato2 = azienda2.getDelegato();
    delegato2.setUsername("milesdyson");
    delegato2.setPassword("terminator");
    delegato2.setEmail("miles@cyberdyne.net");
    delegato2.setNome("Miles");
    delegato2.setCognome("Dyson");
    delegato2.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato2.setTelefono("7451453658");
    
    // Crea la richiesta di convenzionamento #2
    RichiestaConvenzionamento richiesta2 = azienda2.getRichiesta();
    richiesta2.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta2.setDataRichiesta(LocalDateTime.of(2017, 12, 31, 23, 59));
   
    
    // Permette di registrare le richieste di convenzionamento
    try {
      convenzioniService.registraRichiestaConvenzionamento(azienda2);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
    }
    
    // Crea un impiegato dell'ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Salva il delegato nel database
    impiegatoRepository.save(impiegato);
    
    // Effettua il login dell'impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Permette di caricare le richieste di convenzionamento salvate
    try {
      richiesta = convenzioniService.ottieniAzienda("starkind").getRichiesta();
      richiesta2 = convenzioniService.ottieniAzienda("cyberdynecorp").getRichiesta();
    } catch (IdAziendaNonValidoException e) {
      fail(e.getMessage());
    }
   
    // Approva le richieste di convenzionamento salvate
    try {
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
      convenzioniService.approvaRichiestaConvenzionamento(richiesta2.getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Permette di caricare le aziende salvate
    try {
      azienda = convenzioniService.ottieniAzienda("starkind");
      azienda2 = convenzioniService.ottieniAzienda("cyberdynecorp");
    } catch (IdAziendaNonValidoException e) {
      fail(e.getMessage());
    }
    
    // Crea lista aziende convenzionate
    List<Azienda> listaAziende = new ArrayList<Azienda>();
    listaAziende.add(azienda);
    listaAziende.add(azienda2);
    
    List<Azienda> aziende = convenzioniService.elencaAziendeConvenzionate();
    
    // Verifica che la lista delle aziende convenzionate sia uguale alla lista delle aziende 
    // ottenuta invocando il metodo del service
    assertThat(listaAziende, everyItem(isIn(aziende)));
  }
  
  /**
   * Testa il metodo che permette di ottenere un'azienda a partire dal suo identificativo.
   * 
   * @test {@link ConvenzioniService#ottieniAzienda(String)}
   * 
   * @result il test è superato se l'azienda viene caricata correttamente
   */
  @Test
  public void ottieniAzienda() {
    // Crea azienda
    Azienda azienda = new Azienda();
    azienda.setId("starkind");
    azienda.setNome("Stark Industries");
    azienda.setPartitaIva("74598763241");
    azienda.setSenzaBarriere(true);
    azienda.setIndirizzo("Marvel Valley, 45");
    
    // Crea delegato da associare all'azienda
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("tonystark");
    delegato.setPassword("ironman");
    delegato.setEmail("tony@starkind.com");
    delegato.setNome("Anthony Edward");
    delegato.setCognome("Stark");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("7485214786");
    
    // Crea richiesta di convenzionamento
    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
    richiesta.setStatus(RichiestaConvenzionamento.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    // Registra la richiesta di convenzionamento
    try {
      convenzioniService.registraRichiestaConvenzionamento(azienda);
    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
        | IdAziendaNonValidoException | IdAziendaEsistenteException
        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
      fail(e.getMessage());
    }
    
    // Crea un impiegato dell'ufficio tirocini
    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
    impiegato.setNome("Antonio");
    impiegato.setCognome("Albanese");
    impiegato.setEmail("antonio@albanese.com");
    impiegato.setUsername("impiegato");
    impiegato.setPassword("impiegato");
    
    // Salva l'impiegato sul database
    impiegatoRepository.save(impiegato);
    
    // Effettua il login dell'impiegato
    try {
      utenzaService.login("impiegato", "impiegato");
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    // Permette di caricare richiesta dal database ed approvarla
    try {
      richiesta = convenzioniService.ottieniAzienda("starkind").getRichiesta();
      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
    } catch (IdAziendaNonValidoException | IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Verifica che l'azienda salvata sia uguale all'azienda caricata dal database
    try {
      assertThat(azienda, is(equalTo(convenzioniService.ottieniAzienda("starkind"))));
    } catch (IdAziendaNonValidoException e) {
      fail(e.getMessage());
    }
  }
 
  
}
