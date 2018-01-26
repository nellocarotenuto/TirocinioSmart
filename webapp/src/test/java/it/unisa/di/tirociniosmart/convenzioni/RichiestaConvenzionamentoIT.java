package it.unisa.di.tirociniosmart.convenzioni;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RichiestaConvenzionamentoIT {

  @Autowired
  private RichiestaConvenzionamentoRepository richiestaConvenzionamentoRepository;
  
  private static List<RichiestaConvenzionamento> listaRichiesteConvenzionamento;
  
  /**
   * Popola la lista {@link #listaRichiesteConvenzionamento} con oggetti fittizi che faranno 
   * da sorgente di dati per le operazioni di lettura e scrittura su database.
   */
  @BeforeClass
  public static void inizializzaRichiesteConvenzionamento() {
    
    listaRichiesteConvenzionamento = new ArrayList<RichiestaConvenzionamento>();
    
    // Crea l'azienda #1 
    Azienda azienda1 = new Azienda();
    azienda1.setId("acmeltd");
    azienda1.setNome("ACME Ltd.");
    azienda1.setPartitaIva("01234567890");
    azienda1.setSenzaBarriere(true);
    azienda1.setIndirizzo("Grand Canyon");
    
    // Crea il delegato aziendale dell'azienda #1   
    DelegatoAziendale delegato1 = azienda1.getDelegato();
    delegato1.setUsername("wilee");
    delegato1.setPassword("beepbeep");
    delegato1.setEmail("wilee@coyote.com");
    delegato1.setNome("Wile E.");
    delegato1.setCognome("Coyote");
    delegato1.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato1.setTelefono("9876543210");
    
    // Crea la richiesta convenzionamento #1 e la inserisce in lista
    RichiestaConvenzionamento richiesta1 = azienda1.getRichiesta();
    richiesta1.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
    
    listaRichiesteConvenzionamento.add(richiesta1);
    
    
    // Crea l'azienda #2 
    Azienda azienda2 = new Azienda();
    azienda2.setId("starkind");
    azienda2.setNome("Stark Industries");
    azienda2.setPartitaIva("74598763241");
    azienda2.setSenzaBarriere(true);
    azienda2.setIndirizzo("Marvel Valley, 45");
    
    // Crea il delegato per l'azienda #2   
    DelegatoAziendale delegato2 = azienda2.getDelegato();
    delegato2.setUsername("tonystark");
    delegato2.setPassword("ironman");
    delegato2.setEmail("tony@starkind.com");
    delegato2.setNome("Anthony Edward");
    delegato2.setCognome("Stark");
    delegato2.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato2.setTelefono("7485214786");
    
    // Crea la richiesta per l'azienda #2 e la inserisce in lista   
    RichiestaConvenzionamento richiesta2 = azienda2.getRichiesta();
    richiesta2.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta2.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    listaRichiesteConvenzionamento.add(richiesta2);
    
    
    // Crea l'azienda #3
    Azienda azienda3 = new Azienda();
    azienda3.setId("cyberdynecorp");
    azienda3.setNome("Cyberdyne System Corporation");
    azienda3.setPartitaIva("54569814752");
    azienda3.setSenzaBarriere(false);
    azienda3.setIndirizzo("Steel Mountain, 57");
    
    // Crea il delegato dell'azienda #3   
    DelegatoAziendale delegato3 = azienda3.getDelegato();
    delegato3.setUsername("milesdyson");
    delegato3.setPassword("terminator");
    delegato3.setEmail("miles@cyberdyne.net");
    delegato3.setNome("Miles");
    delegato3.setCognome("Dyson");
    delegato3.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato3.setTelefono("7451453658");
    
    // Crea la richiesta dell'azienda #3 e la aggiunge in lista   
    RichiestaConvenzionamento richiesta3 = azienda3.getRichiesta();
    richiesta3.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta3.setDataRichiesta(LocalDateTime.of(2017, 12, 31, 23, 59));
    
    listaRichiesteConvenzionamento.add(richiesta3);
  }
  
  /**
   * Salva la lista delle richieste convenzionamento su database prima 
   * dell'esecuzione di ogni singolo test.
   */
  
  @Before
  public void salvaRichieste() {
    for (RichiestaConvenzionamento richiesta: listaRichiesteConvenzionamento) {
      richiestaConvenzionamentoRepository.save(richiesta);
    }
  }
  
   
  /**
   * Testa l'interazione con il database per l'inserimento ed il successivo caricamento di una lista
   * di richieste convenzionamento.
   * 
   * @test {@link RichiestaConvenzionamentoRepository#findAll()}
   * 
   * @result Il test è superato se tutte le richieste salvate sono correttamente caricate
   */
  @Test
  public void findAll() {
    List<RichiestaConvenzionamento> listaRichiesteSalvate = 
        richiestaConvenzionamentoRepository.findAll();
    assertThat(listaRichiesteConvenzionamento, everyItem(isIn(listaRichiesteSalvate)));
  }
  
  
  /**
   * Testa l'interazione con il database per il singolo caricamento delle richieste della lista
   * tramite identificatore.
   * 
   * @test {@link RichiestaConvenzionamentoRepository#findById(String)}
   * 
   * @result Il test è superato se ogni entità viene correttamente caricata dal database
   */
  @Test
  public void findById() {
    for (RichiestaConvenzionamento richiesta: listaRichiesteConvenzionamento) {
      RichiestaConvenzionamento richiestaSalvata = 
          richiestaConvenzionamentoRepository.findById(richiesta.getId());
      assertThat(richiesta, is(equalTo(richiestaSalvata)));
    }
  }
  
  /**
   * Testa l'interazione con il database per il caricamento delle richieste
   * di convenzionamento che si trovano in uno stato specificato.
   * 
   * @test {@link RichiestaConvenzionamentoRepository#findAllByStatus(int)}
   * 
   * @result Il test è superato se sono caricate solo le richieste che si trovano nello stato 
   *         specificato
   */
  @Test
  public void findAllByStatus() {
    List<RichiestaConvenzionamento> richiesteApprovate = 
        richiestaConvenzionamentoRepository.findAllByStatus(RichiestaConvenzionamento.APPROVATA);
    for (RichiestaConvenzionamento richiesta: listaRichiesteConvenzionamento) {
      if (richiesta.getStatus() == RichiestaConvenzionamento.APPROVATA) {
        richiesteApprovate.add(richiesta);
      }
    }
      
    List<RichiestaConvenzionamento> richiesteSalvate = 
        richiestaConvenzionamentoRepository.findAllByStatus(RichiestaConvenzionamento.APPROVATA);
    assertThat(richiesteApprovate, everyItem(isIn(richiesteSalvate)));
  }
  
  
  /**
   * Testa l'interazione con il database per determinare se la ricerca di una richiesta tramite
   * identificatore avvenga correttamente.
   * 
   * @test {@link RichiestaConvenzionamentoRepository#existsById(String)}
   * 
   * @result Il test è superato se la ricerca degli identificatori delle richieste presenti nella
   *         lista utilizzata per il test ha successo.
   */
  @Test
  public void existsById() {
    // Controlla che ogni richiesta della lista utilizzata per il test sia presente su database 
    // ricercandola per id
    for (RichiestaConvenzionamento richiesta : listaRichiesteConvenzionamento) {
      boolean richiestaEsistente = 
          richiestaConvenzionamentoRepository.existsById(richiesta.getId());
      assertThat(richiestaEsistente, is(true));
    }
  }
  
  
}
