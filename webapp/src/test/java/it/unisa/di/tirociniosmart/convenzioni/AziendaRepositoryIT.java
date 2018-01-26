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

/**
 * Classe che definisce i casi di test per le operazioni sul database inerenti all'azienda e
 * definite dalla relativa repository.
 *
 * @see Azienda
 * @see AziendaRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AziendaRepositoryIT {

  @Autowired
  private AziendaRepository aziendaRepository;
  
  private static List<Azienda> listaAziende;
  
  /**
   * Popola la lista {@link #listaAziende} con oggetti fittizi che faranno da sorgente di dati per
   * le operazioni di lettura e scrittura su database.
   */
  @BeforeClass
  public static void inizializzaAziende() {
    listaAziende = new ArrayList<Azienda>();
    
    
    // Crea l'azienda #1 ed inseriscila in lista
    Azienda azienda1 = new Azienda();
    azienda1.setId("acmeltd");
    azienda1.setNome("ACME Ltd.");
    azienda1.setPartitaIva("01234567890");
    azienda1.setSenzaBarriere(true);
    azienda1.setIndirizzo("Grand Canyon");
    
    DelegatoAziendale delegato1 = azienda1.getDelegato();
    delegato1.setUsername("wilee");
    delegato1.setPassword("beepbeep");
    delegato1.setEmail("wilee@coyote.com");
    delegato1.setNome("Wile E.");
    delegato1.setCognome("Coyote");
    delegato1.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato1.setTelefono("9876543210");
    
    RichiestaConvenzionamento richiesta1 = azienda1.getRichiesta();
    richiesta1.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
    
    listaAziende.add(azienda1);
    
    
    // Crea l'azienda #2 ed inseriscila in lista
    Azienda azienda2 = new Azienda();
    azienda2.setId("starkind");
    azienda2.setNome("Stark Industries");
    azienda2.setPartitaIva("74598763241");
    azienda2.setSenzaBarriere(true);
    azienda2.setIndirizzo("Marvel Valley, 45");
    
    DelegatoAziendale delegato2 = azienda2.getDelegato();
    delegato2.setUsername("tonystark");
    delegato2.setPassword("ironman");
    delegato2.setEmail("tony@starkind.com");
    delegato2.setNome("Anthony Edward");
    delegato2.setCognome("Stark");
    delegato2.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato2.setTelefono("7485214786");
    
    RichiestaConvenzionamento richiesta2 = azienda2.getRichiesta();
    richiesta2.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta2.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    listaAziende.add(azienda2);
    
    
    // Crea l'azienda #3 ed inseriscila in lista
    Azienda azienda3 = new Azienda();
    azienda3.setId("cyberdynecorp");
    azienda3.setNome("Cyberdyne System Corporation");
    azienda3.setPartitaIva("54569814752");
    azienda3.setSenzaBarriere(false);
    azienda3.setIndirizzo("Steel Mountain, 57");
    
    DelegatoAziendale delegato3 = azienda3.getDelegato();
    delegato3.setUsername("milesdyson");
    delegato3.setPassword("terminator");
    delegato3.setEmail("miles@cyberdyne.net");
    delegato3.setNome("Miles");
    delegato3.setCognome("Dyson");
    delegato3.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato3.setTelefono("7451453658");
    
    RichiestaConvenzionamento richiesta3 = azienda3.getRichiesta();
    richiesta3.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta3.setDataRichiesta(LocalDateTime.of(2017, 12, 31, 23, 59));
    
    listaAziende.add(azienda3);
  }
  
  
  /**
   * Salva la lista delle aziende su database prima dell'esecuzione di ogni singolo test.
   */
  @Before
  public void salvaAziende() {
    for (Azienda azienda : listaAziende) {
      aziendaRepository.save(azienda);
    }
  }
  
  
  /**
   * Testa l'interazione con il database per l'inserimento ed il successivo caricamento di una lista
   * di aziende.
   * 
   * @test {@link AziendaRepository#findAll()}
   * 
   * @result Il test è superato se tutte le aziende salvate sono correttamente caricate
   */
  @Test
  public void findAll() {
    // Controlla che ogni elemento della lista restituita dalla repository sia presente nella lista
    // utilizzata per il test e viceversa
    List<Azienda> listaAziendeSalvate = aziendaRepository.findAll();
    assertThat(listaAziende, everyItem(isIn(listaAziendeSalvate)));
  }
  
  
  /**
   * Testa l'interazione con il database per il singolo caricamento delle aziende della lista
   * tramite identificatore.
   * 
   * @test {@link AziendaRepository#findById(String)}
   * 
   * @result Il test è superato se ogni entità viene correttamente caricata dal database
   */
  @Test
  public void findById() {
    // Controlla che ogni azienda della lista per il test sia presente su database ricercandola per
    // l'identificatore
    for (Azienda azienda : listaAziende) {
      Azienda aziendaSalvata = aziendaRepository.findById(azienda.getId());
      assertThat(azienda, is(equalTo(aziendaSalvata)));
    }
  }
  
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di aziende che non hanno
   * barriere architettoniche.
   * 
   * @test {@link AziendaRepository#findAllBySenzaBarriere(boolean)}
   * 
   * @result Il test è superato se sono caricate solo le aziende della lista che non hanno barriere
   *         architettoniche
   */
  @Test
  public void findAllBySenzaBarriere() {
    // Definisci la lista delle aziende senza barriere a partire da quelle usate per il test
    List<Azienda> listaAziendeSenzaBarriere = new ArrayList<Azienda>();
    for (Azienda azienda : listaAziende) {
      if (azienda.isSenzaBarriere()) {
        listaAziendeSenzaBarriere.add(azienda);
      }
    }
    
    // Controlla che ogni elemento della lista restituita dalla repository sia nella lista
    // utilizzata per il test
    List<Azienda> listaAziendeSenzaBarriereSalvate = aziendaRepository.findAllBySenzaBarriere(true);
    assertThat(listaAziendeSenzaBarriere, everyItem(isIn(listaAziendeSenzaBarriereSalvate)));
  }
  
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di aziende la cui richiesta
   * di convenzionamento si trovano in uno stato specificato.
   * 
   * @test {@link AziendaRepository#findAllByRichiestaConvenzionamentoStatus(int)}
   * 
   * @result Il test è superato se sono caricate solo le aziende della lista la cui richiesta di
   *         convenzionamento si trova nello stato specificato
   */
  @Test
  public void findAllByRichiestaConvenzionamentoStatus() {
    List<Azienda> listaAziendeConvenzionate = new ArrayList<Azienda>();
    for (Azienda azienda : listaAziende) {
      if (azienda.getRichiesta().getStatus() == RichiestaConvenzionamento.APPROVATA) {
        listaAziendeConvenzionate.add(azienda);
      }
    }
    
    // Controlla che la lista delle aziende convenzionate ottenuta dalla repository contenga
    // le aziende convenzionate definite per il test
    List<Azienda> listaAziendeConvenzionateSalvate = aziendaRepository
                     .findAllByRichiestaConvenzionamentoStatus(RichiestaConvenzionamento.APPROVATA);
    assertThat(listaAziendeConvenzionate, everyItem(isIn(listaAziendeConvenzionateSalvate)));
  }

  
  /**
   * Testa l'interazione con il database per determinare se la ricerca di un'azienda tramite
   * identificatore avvenga correttamente.
   * 
   * @test {@link AziendaRepository#existsById(String)}
   * 
   * @result Il test è superato se la ricerca degli identificatori delle aziende presenti nella
   *         lista utilizzata per il test ha successo e se la ricerca di una partita IVA vuota o
   *         inesistente non ha successo
   */
  @Test
  public void existsById() {
    // Controlla che ogni azienda della lista utilizzata per il test sia presente su database 
    // ricercandola per id
    for (Azienda azienda : listaAziende) {
      boolean aziendaEsistente = aziendaRepository.existsById(azienda.getId());
      assertThat(aziendaEsistente, is(true));
    }
  }
  
  
  /**
   * Testa l'interazione con il database per determinare se la ricerca di un'azienda tramite partita
   * IVA avvenga correttamente.
   * 
   * @test {@link AziendaRepository#existsByPartitaIva(String)}
   * 
   * @result Il test è superato se la ricerca delle partite IVA delle aziende presenti nella lista
   *         utilizzata per il test ha successo e se la ricerca di una partita IVA vuota o
   *         inesistente non ha successo
   */
  @Test
  public void existsByPartitaIva() {
    // Controlla che ogni azienda della lista utilizzata per il test sia presente su database 
    // ricercandola per partita IVA
    for (Azienda azienda : listaAziende) {
      boolean aziendaEsistente = aziendaRepository.existsByPartitaIva(azienda.getPartitaIva());
      assertThat(aziendaEsistente, is(true));
    }
  }
  

  
}
