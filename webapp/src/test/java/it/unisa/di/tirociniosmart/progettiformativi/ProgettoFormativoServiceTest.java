package it.unisa.di.tirociniosmart.progettiformativi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.AziendaRepository;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaNonValidoException;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Classe che offre i casi di test di ProgettoFormativoService.
 * 
 * @see ProgettoFormativoRepository
 * @see ProgettoFormativoService
 * @see AziendaRepository
 * @see UtenzaService
 */

@RunWith(MockitoJUnitRunner.class)
public class ProgettoFormativoServiceTest {

  @InjectMocks
  private ProgettiFormativiService progettoFormativoService;
  
  @Mock
  private ProgettoFormativoRepository progettoFormativoRepository;
  
  @Mock
  private AziendaRepository aziendaRepository;
  
  @Mock
  private UtenzaService utenzaService;

  /**
   * Metodo che testa l'ottenimento di un progetto formativo.
   * 
   * @test {@link ProgettoFormativoServiceTest#testOttieniProgettoFormativo()}
   * 
   * @result Il test è superato se l'elenco dei progetti formativi è ottenuto correttamente
   */
  @Test
  public void testOttieniProgettoFormativo()  {
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("progetto");
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    when(progettoFormativoRepository.existsById(123456L)).thenReturn(true);
    when(progettoFormativoRepository.findById(123456L)).thenReturn(progetto);
    try {
      assertThat(progetto, is(equalTo(progettoFormativoService.ottieniProgettoFormativo(123456L))));
    } catch (IdProgettoFormativoInesistenteException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Metodo che testa l'ottenimento di un progetto formativo.
   * 
   * @test {@link ProgettoFormativoServiceTest#testOttieniProgettoFormativoIdInesistente()}
   * 
   * @throws IdProgettoFormativoInesistenteException
   * 
   * @result Il test è superato se viene lanciata l'eccezione.
   */
  @Test (expected = IdProgettoFormativoInesistenteException.class)
  public void testOttieniProgettoFormativoIdInesistente()
         throws IdProgettoFormativoInesistenteException {
   
    when(progettoFormativoRepository.findById(12345L)).thenReturn(null);
    progettoFormativoService.ottieniProgettoFormativo(12345L);
    
  }  
  
  /**
   * Metodo che testa l'archiviazione di un progetto formativo.
   * 
   * @test {@link ProgettoFormativoServiceTest#testArchiviaProgettoFormativo()}
   * 
   * @result Il test è superato se l'archiviazione del progetto avviene con successo
   */
  @Test
  public void testArchiviaProgettoFormativo() {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("Andrea1");
    delegato.setPassword("andrea1");
    delegato.setEmail("azndrea@carozza.com");
    delegato.setNome("Andrea");
    delegato.setCognome("Carozza");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setAzienda(azienda);
    progetto.setNome("progetto");
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    when(progettoFormativoRepository.existsById(1234L)).thenReturn(true);
    when(progettoFormativoRepository.findById(1234L)).thenReturn(progetto);
    try {
      progettoFormativoService.archiviaProgettoFormativo(1234L);
    } catch (IdProgettoFormativoInesistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    assertThat(progetto.getStatus(), is(equalTo(ProgettoFormativo.ARCHIVIATO)));
  }
  
  /**
   * Metodo che testa l'archiviazione di un progetto formativo.
   * 
   * @test {@link ProgettoFormativoServiceTest#testArchiviaProgettoFormativoIdInesistente()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = IdProgettoFormativoInesistenteException.class)
  public void testArchiviaProgettoFormativoIdInesistente()
         throws IdProgettoFormativoInesistenteException {
    
    Azienda azienda = new Azienda();
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("Andrea1");
    delegato.setPassword("andrea1");
    delegato.setEmail("azndrea@carozza.com");
    delegato.setNome("Andrea");
    delegato.setCognome("Carozza");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    ProgettoFormativo progetto = new ProgettoFormativo(); 
    progetto.setNome("progetto");
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    when(progettoFormativoRepository.existsById(progetto.getId())).thenReturn(false);
    try {
      progettoFormativoService.archiviaProgettoFormativo(progetto.getId());
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } 
  }
  
  /**
   * Metodo che testa l'archiviazione di un progetto formativo.
   * 
   * @test {@link ProgettoFormativoServiceTest#testArchiviaProgettoFormativoRichiestaAutorizzata()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaNonAutorizzataException.class)
  public void testArchiviaProgettoFormativoRichiestaNonAutorizzata()
         throws RichiestaNonAutorizzataException {
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("progetto");
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    try {
      progettoFormativoService.archiviaProgettoFormativo(progetto.getId());
    } catch (IdProgettoFormativoInesistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa l'archiviazione di un progetto formativo.
   * 
   * @test {@link ProgettoFormativoServiceTest
   *            #testArchiviaProgettoFormativoRichiestaAutorizzataAziendaDelegatoProgetto()}
   * 
   * @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaNonAutorizzataException.class)
  public void testArchiviaProgettoFormativoRichiestaNonAutorizzataAziendaDelegatoProgetto()
         throws RichiestaNonAutorizzataException {
       
    Azienda azienda2 = new Azienda();
    azienda2.setId("azienda2");
    DelegatoAziendale delegato2 = azienda2.getDelegato();
    delegato2.setUsername("Andrea1");
    delegato2.setPassword("andrea1");
    delegato2.setEmail("azndrea@carozza.com");
    delegato2.setNome("Andrea");
    delegato2.setCognome("Carozza");
    delegato2.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato2.setTelefono("9876543210");
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setAzienda(azienda2);
    progetto.setNome("progetto");
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    Azienda azienda1 = new Azienda();
    azienda1.setId("azienda1");
    DelegatoAziendale delegato = azienda1.getDelegato();
    delegato.setUsername("Andrea1");
    delegato.setPassword("andrea1");
    delegato.setEmail("azndrea@carozza.com");
    delegato.setNome("Andrea");
    delegato.setCognome("Carozza");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210"); 
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    when(progettoFormativoRepository.existsById(progetto.getId())).thenReturn(true);
    when(progettoFormativoRepository.findById(progetto.getId())).thenReturn(progetto);
    try {
      progettoFormativoService.archiviaProgettoFormativo(progetto.getId());
    } catch (IdProgettoFormativoInesistenteException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  
  /**
   * Metodo che testa l'ottenimento di una lista di progetti formativi.
   * 
   * @test {@link ProgettoFormativoServiceTest#testaElencaProgettiFormativi()}
   * 
   * @result Il testa è superato se la lista di progetti formativi viene visualizzata correttamente
   */
  @Test
  public void testaElencaProgettiFormativi()  {
           
    ProgettoFormativo progetto1 = new ProgettoFormativo();
    progetto1.setNome("progetto1");
    progetto1.setDescrizione("descrizione progetto1");
    progetto1.setStatus(ProgettoFormativo.ATTIVO);
    
    ProgettoFormativo progetto2 = new ProgettoFormativo();
    progetto2.setNome("progetto2");
    progetto2.setDescrizione("descrizione progetto2");
    progetto2.setStatus(ProgettoFormativo.ATTIVO);
    
    ProgettoFormativo progetto3 = new ProgettoFormativo();
    progetto3.setNome("progetto3");
    progetto3.setDescrizione("descrizione progetto3");
    progetto3.setStatus(ProgettoFormativo.ATTIVO);
    
    List<ProgettoFormativo> listaProgetti = new ArrayList<ProgettoFormativo>();
    
    listaProgetti.add(progetto1);  
    listaProgetti.add(progetto2);   
    listaProgetti.add(progetto3);
        
    when(aziendaRepository.existsById("12345")).thenReturn(true);
    when(progettoFormativoRepository.findAllByStatusAndAziendaId(
                        ProgettoFormativo.ATTIVO, "12345")).thenReturn(listaProgetti);
    try {
      assertThat(listaProgetti, is(equalTo(
                                progettoFormativoService.elencaProgettiFormativi("12345"))));
    } catch (IdAziendaNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
  /**
   * Metodo che testa l'ottenimento di una lista di progetti formativi.
   * 
   * @test {@link ProgettoFormativoServiceTest#testaElencaProgettiFormativiIdAziendaNonValido()}
   * 
   * @result Il testa è superato se viene lanciata l'eccezione
   */
  @Test (expected = IdAziendaNonValidoException.class)
  public void testaElencaProgettiFormativiIdAziendaNonValido()
         throws IdAziendaNonValidoException {
    
    when(aziendaRepository.existsById("12345")).thenReturn(false);
    progettoFormativoService.elencaProgettiFormativi("12345");
  }
  
  /**
   * Metodo che testa l'aggiunta di un progetto formativo.
   * 
   *  @test {@link ProgettoFormativoServiceTest#testaAggiungiProgettoFormativo()}
   *  
   *  @result Il test è superato se il progetto viene aggiunto correttamente
   */
  @Test
  public void testaAggiungiProgettoFormativo()  {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("Andrea1");
    delegato.setPassword("andrea1");
    delegato.setEmail("azndrea@carozza.com");
    delegato.setNome("Andrea");
    delegato.setCognome("Carozza");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setAzienda(azienda);
    progetto.setNome("progetto");
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    when(progettoFormativoRepository.save(progetto)).thenReturn(null);
    try {
      progettoFormativoService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeProgettoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa l'aggiunta di un progetto formativo.
   * 
   *  @test {@link ProgettoFormativoServiceTest
   *               #testaAggiungiProgettoFormativoRichiestaNonAutorizzata()}
   *  
   *  @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = RichiestaNonAutorizzataException.class)
  public void testaAggiunngiProgettoFormativoRichiestaNonAutorizzata()
          throws RichiestaNonAutorizzataException {
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("progetto");
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO); 
   
    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
    try {
      progettoFormativoService.aggiungiProgettoFormativo(progetto);
    } catch (NomeProgettoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
  }
  
  /**
   * Metodo che testa l'aggiunta di un progetto formativo.
   * 
   *  @test {@link ProgettoFormativoServiceTest
   *               #testaAggiungiProgettoFormativoNomeNonValidoMin()}
   *  
   *  @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = NomeProgettoNonValidoException.class)
  public void testaAggiunngiProgettoFormativoNomeNonValidoMin()
         throws NomeProgettoNonValidoException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("Andrea1");
    delegato.setPassword("andrea1");
    delegato.setEmail("azndrea@carozza.com");
    delegato.setNome("Andrea");
    delegato.setCognome("Carozza");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setAzienda(azienda);
    progetto.setNome("P");
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    when(progettoFormativoRepository.save(progetto)).thenReturn(null);
    try {
      progettoFormativoService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
   
  }
  
  /**
   * Metodo che testa l'aggiunta di un progetto formativo.
   * 
   *  @test {@link ProgettoFormativoServiceTest
   *               #testaAggiungiProgettoFormativoNomeNonValidoMax()}
   *  
   *  @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = NomeProgettoNonValidoException.class)
  public void testaAggiunngiProgettoFormativoNomeNonValidoMax()
         throws NomeProgettoNonValidoException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("Andrea1");
    delegato.setPassword("andrea1");
    delegato.setEmail("azndrea@carozza.com");
    delegato.setNome("Andrea");
    delegato.setCognome("Carozza");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setAzienda(azienda);
    progetto.setNome("NomeProgetto NomeProgetto NomeProgetto NomeProgetto NomeProgetto "
        + "           NomeProgetto NomeProgetto NomeProgetto NomeProgetto NomeProgetto"
        + "           NomeProgetto NomeProgetto NomeProgetto NomeProgetto NomeProgetto"
        + "           NomeProgetto NomeProgetto NomeProgetto NomeProgetto NomeProgetto");
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    when(progettoFormativoRepository.save(progetto)).thenReturn(null);
    try {
      progettoFormativoService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
   
  }
  
  /**
   * Metodo che testa l'aggiunta di un progetto formativo.
   * 
   *  @test {@link ProgettoFormativoServiceTest
   *               #testaAggiungiProgettoFormativoNomeNullo()}
   *  
   *  @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = NomeProgettoNonValidoException.class)
  public void testaAggiunngiProgettoFormativoNomeNullo()
         throws NomeProgettoNonValidoException {
    
    Azienda azienda = new Azienda();
    azienda.setId("idAzienda");
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("Andrea1");
    delegato.setPassword("andrea1");
    delegato.setEmail("azndrea@carozza.com");
    delegato.setNome("Andrea");
    delegato.setCognome("Carozza");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setAzienda(azienda);
    progetto.setDescrizione("descrizione progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    when(progettoFormativoRepository.save(progetto)).thenReturn(null);
    try {
      progettoFormativoService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (DescrizioneProgettoNonValidaException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
   
  }
  
  /**
   * Metodo che testa l'aggiunta di un progetto formativo.
   * 
   *  @test {@link ProgettoFormativoServiceTest
   *               #testaAggiungiProgettoFormativoDescrizioneNonValida()}
   *  
   *  @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = DescrizioneProgettoNonValidaException.class)
  public void testaAggiunngiProgettoFormativoDescrizioneNonValida()
          throws DescrizioneProgettoNonValidaException {
    
    Azienda azienda = new Azienda();
    DelegatoAziendale delegato = azienda.getDelegato();    
    delegato.setUsername("Andrea1");
    delegato.setPassword("andrea1");
    delegato.setEmail("azndrea@carozza.com");
    delegato.setNome("Andrea");
    delegato.setCognome("Carozza");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("Progetto");
    progetto.setDescrizione("D");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    try {
      progettoFormativoService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeProgettoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
   
  }
  
  /**
   * Metodo che testa l'aggiunta di un progetto formativo.
   * 
   *  @test {@link ProgettoFormativoServiceTest
   *               #testaAggiungiProgettoFormativoDescrizioneNulla()}
   *  
   *  @result Il test è superato se viene lanciata l'eccezione
   */
  @Test (expected = DescrizioneProgettoNonValidaException.class)
  public void testaAggiunngiProgettoFormativoDescrizioneNulla()
          throws DescrizioneProgettoNonValidaException {
    
    Azienda azienda = new Azienda();
    DelegatoAziendale delegato = azienda.getDelegato();    
    delegato.setUsername("Andrea1");
    delegato.setPassword("andrea1");
    delegato.setEmail("azndrea@carozza.com");
    delegato.setNome("Andrea");
    delegato.setCognome("Carozza");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("Progetto");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    
    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
    try {
      progettoFormativoService.aggiungiProgettoFormativo(progetto);
    } catch (RichiestaNonAutorizzataException e) {
      fail(e.getMessage());
      e.printStackTrace();
    } catch (NomeProgettoNonValidoException e) {
      fail(e.getMessage());
      e.printStackTrace();
    }
   
  }
  
}
