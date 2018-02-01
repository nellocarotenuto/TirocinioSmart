package it.unisa.di.tirociniosmart.progettiformativi;

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
 * Testa i servizi offerti da {@link ProgettiFormativoService} con tutte le dipedenze.
 * 
 * @see ProgettiFormativoService  
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class ProgettoFormativoServiceIT {
  
  @Autowired
  private ProgettiFormativiService progettoFormativoService;
  
  @Autowired
  private ImpiegatoUfficioTirociniRepository impiegatoRepository;
  
  @Autowired
  private ConvenzioniService convenzioniService;
    
  @Autowired
  private UtenzaService utenzaService;
  
  /**
   * Testa il metodo per aggiungere al database un progetto formativo.
   * 
   * @test {@link ProgettiFormativiService#aggiungiProgettoFormativo(ProgettoFormativo)}
   * 
   * @result Il test è superato se il progetto viene aggiunto correttamente
   */
  @Test
  public void aggiungiProgettoFormativo() {
          
    // Crea l'azienda #1 possedente il progetto formativo #1
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
    
    Azienda azienda1Registrata = null;
    // Salviamo l'azienda nel database
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
    
    // Crea l'impiegato ufficio tirocini che approverà la richiesta convenzionamento
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
    
    // Approviamo la richiesta di convenzionamento
    try {
      convenzioniService.approvaRichiestaConvenzionamento(
                                              azienda1Registrata.getRichiesta().getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    // Effettuiamo il logout dell'impiegato
    utenzaService.logout();
    
    // Crea progetto formativo #1
    ProgettoFormativo progetto1 = new ProgettoFormativo();
    progetto1.setAzienda(azienda1);
    progetto1.setNome("ProjectX");
    progetto1.setDescrizione("descrizioneeeeee");
    progetto1.setStatus(ProgettoFormativo.ATTIVO);
        
    // Viene effettuato il login al sistema come delegato1
    try {
      utenzaService.login(delegato1.getUsername(), delegato1.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    ProgettoFormativo progetto1Registrato = null;
    // Salva il progetto formativo sul database
    try {
      progetto1Registrato = progettoFormativoService.aggiungiProgettoFormativo(progetto1);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua il logout del delegato1
    utenzaService.logout();
    
    // Verifica che il progetto formativo ottenuto sia quello salvato
    assertThat(progetto1, is(equalTo(progetto1Registrato)));
    
  }
  
  /**
   * Testa il metodo che elenca la lista di progetti formativi di un'azienda.
   * 
   * @test {@link ProgettiFormativiService#elencaProgettiFormativi(String)}
   * 
   * @result Il test è superato se la lista viene visualizzata correttamente
   */
  @Test
  public void elencaProgettiFormativi() {
            
    // Crea l'azienda #1 possedente il progetto formativo #1
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
    richiesta1.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
    
    Azienda azienda1Registrata = null;
    // Salviamo l'azienda nel database
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
    
    // Crea l'impiegato ufficio tirocini che approverà la richiesta convenzionamento
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
    
    // Approviamo la richiesta di convenzionamento#1
    try {
      convenzioniService.approvaRichiestaConvenzionamento(
                                              azienda1Registrata.getRichiesta().getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
        
    // Effettua il logout l'impiegato
    utenzaService.logout();
    
    
    // Crea progetto formativo #1
    ProgettoFormativo progetto1 = new ProgettoFormativo();
    progetto1.setAzienda(azienda1);
    progetto1.setNome("ProjectX");
    progetto1.setDescrizione("descrizioneeeeee");
    progetto1.setStatus(ProgettoFormativo.ATTIVO);
    
    // Crea progetto formativo #2
    ProgettoFormativo progetto2 = new ProgettoFormativo();
    progetto2.setAzienda(azienda1);
    progetto2.setNome("Assiri");
    progetto2.setDescrizione("descrizioneeeeee");
    progetto2.setStatus(ProgettoFormativo.ATTIVO);
    
    // Viene effettuato il login come delegato#1
    try {
      utenzaService.login(delegato1.getUsername(), delegato1.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    ProgettoFormativo progetto1Registrato = null;
    // Salva il progetto formativo sul database
    try {
      progetto1Registrato = progettoFormativoService.aggiungiProgettoFormativo(progetto1);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    ProgettoFormativo progetto2Registrato = null;
    // Salva il progetto formativo sul database
    try {
      progetto2Registrato = progettoFormativoService.aggiungiProgettoFormativo(progetto2);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    // Effettua il logout il delegato#1
    utenzaService.logout();
        
    List<ProgettoFormativo> listaProgetti = new ArrayList<ProgettoFormativo>();
    listaProgetti.add(progetto1Registrato);
    listaProgetti.add(progetto2Registrato);
    
    try {
      assertThat(listaProgetti, everyItem(isIn(
                              progettoFormativoService.elencaProgettiFormativi(azienda1.getId()))));
    } catch (IdAziendaNonValidoException e) {
      fail(e.getMessage());
    }  
  }
  
  /**
   * Testa il metodo che archivia un progetto formativo.
   * 
   * @test {@link ProgettiFormativiService#archiviaProgettoFormativo(long)}
   * 
   * @result Il test è superato se il progetto viene archiviato correttamente
   */
  @Test
  public void archiviaProgettoFormativo() {
    
    // Crea l'azienda #1 possedente il progetto formativo #1
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
    richiesta1.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
    
    Azienda azienda1Registrata = null;
    // Salviamo l'azienda nel database
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
    
    // Crea l'impiegato ufficio tirocini che approverà la richiesta convenzionamento
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
    
    // Approviamo la richiesta di convenzionamento#1
    try {
      convenzioniService.approvaRichiestaConvenzionamento(
                                              azienda1Registrata.getRichiesta().getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
        
    // Effettua il logout l'impiegato
    utenzaService.logout();
    
    
    // Crea progetto formativo #1
    ProgettoFormativo progetto1 = new ProgettoFormativo();
    progetto1.setAzienda(azienda1);
    progetto1.setNome("ProjectX");
    progetto1.setDescrizione("descrizioneeeeee");
    progetto1.setStatus(ProgettoFormativo.ATTIVO);
    
    // Viene effettuato il login come delegato#1
    try {
      utenzaService.login(delegato1.getUsername(), delegato1.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    ProgettoFormativo progetto1Registrato = null;
    // Salva il progetto formativo sul database
    try {
      progetto1Registrato = progettoFormativoService.aggiungiProgettoFormativo(progetto1);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    try {
      progetto1Registrato = progettoFormativoService.archiviaProgettoFormativo(
                                                         progetto1Registrato.getId());
    } catch (IdProgettoFormativoInesistenteException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
    
    assertThat(progetto1Registrato.getStatus(), is(equalTo(ProgettoFormativo.ARCHIVIATO)));
    
    // Viene effettuato il logout di delegato#1
    utenzaService.logout(); 
    
  }
  
  
  @Test
  public void ottieniProgettoFormativo() {
    
    // Crea l'azienda #1 possedente il progetto formativo #1
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
    richiesta1.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
    
    Azienda azienda1Registrata = null;
    // Salviamo l'azienda nel database
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
    
    // Crea l'impiegato ufficio tirocini che approverà la richiesta convenzionamento
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
    
    // Approviamo la richiesta di convenzionamento#1
    try {
      convenzioniService.approvaRichiestaConvenzionamento(
                                              azienda1Registrata.getRichiesta().getId());
    } catch (IdRichiestaConvenzionamentoNonValidoException
        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
    }
        
    // Effettua il logout l'impiegato
    utenzaService.logout();
    
    
    // Crea progetto formativo #1
    ProgettoFormativo progetto1 = new ProgettoFormativo();
    progetto1.setAzienda(azienda1);
    progetto1.setNome("ProjectX");
    progetto1.setDescrizione("descrizioneeeeee");
    progetto1.setStatus(ProgettoFormativo.ATTIVO);
    
    // Viene effettuato il login come delegato#1
    try {
      utenzaService.login(delegato1.getUsername(), delegato1.getPassword());
    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
        | RichiestaIscrizioneInAttesaException e) {
      fail(e.getMessage());
    }
    
    ProgettoFormativo progetto1Registrato = null;
    // Salva il progetto formativo sul database
    try {
      progetto1Registrato = progettoFormativoService.aggiungiProgettoFormativo(progetto1);
    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
        | DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
    }
    
    try {
      assertThat(progetto1Registrato, is(equalTo(progettoFormativoService
                                          .ottieniProgettoFormativo(progetto1Registrato.getId()))));
    } catch (IdProgettoFormativoInesistenteException e) {
      fail(e.getMessage());
    }
    
    // Effettuo il logout del delegato#1
    utenzaService.logout();
  }
}
