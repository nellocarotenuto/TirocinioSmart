package it.unisa.di.tirociniosmart.convenzioni;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Classe che definisce i casi di test per le operazioni sul database inerenti al delegato aziendale
 * e definite dalla relativa repository.
 *
 * @see DelegatoAziendale
 * @see DelegatoAziendaleRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DelegatoAziendaleRepositoryIT {
  
  @Autowired
  private DelegatoAziendaleRepository delegatoAziendaleRepository;
  
  @Autowired
  private AziendaRepository aziendaRepository;
  
  private static List<DelegatoAziendale> listaDelegati;
  
  /**
   * Popola la lista {@link #listaDelegati} con oggetti fittizi che faranno da sorgente di dati 
   * per le operazioni di lettura e scrittura su database.
   */ 
  @BeforeClass
  public static void inizializzaDelegati() {
    listaDelegati = new ArrayList<DelegatoAziendale>();
    
    
    // Crea il delegato #1 ed inseriscilo in lista
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
    
    listaDelegati.add(delegato1);
    
    
    // Crea il delegato #2 ed inseriscilo in lista
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
    
    listaDelegati.add(delegato2);
    
    
    // Crea il delegato #3 ed inseriscilo in lista
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
    
    listaDelegati.add(delegato3);
  }
  
  /**
   * Salva la lista di delegati su database prima dell'esecuzione di ogni singolo test.
   */
  @Before
  public void salvaDelegati() {
    for (int i = 0; i < listaDelegati.size(); i++) {
      DelegatoAziendale delegato = listaDelegati.get(i);
      
      Azienda azienda = aziendaRepository.save(delegato.getAzienda());
      listaDelegati.set(i, azienda.getDelegato());
    }
    
    aziendaRepository.flush();
  }
  
  /**
   * Testa l'interazione con il database per il caricamento dei singoli delegati tramite username e
   * password.
   * 
   * @test {@link DelegatoAziendaleRepository#findByUsernameAndPassword(String, String)}
   * 
   * @result Il test è superato se le entità coivolte sono correttamente caricate dal database
   */
  @Test
  public void findByUsernameAndPassword() {
    // Controlla che ogni delegato della lista per il test sia presente su database ricercandolo per
    // username e password
    for (DelegatoAziendale delegato : listaDelegati) {
      DelegatoAziendale delegatoSalvato = delegatoAziendaleRepository
                         .findByUsernameAndPassword(delegato.getUsername(), delegato.getPassword());
      
      assertThat(delegato, is(equalTo(delegatoSalvato)));
    }
  }
  
  /**
   * Testa l'interazione con il database per il caricamento dei singoli delegati tramite username.
   * 
   * @test {@link DelegatoAziendaleRepository#findByUsername(String)}
   * 
   * @result Il test è superato se le entità coivolte sono correttamente caricate dal database
   */
  @Test
  public void findByUsername() {
    //Controlla che ogni delegato della lista per il test sia presente su database ricercandolo
    //per username
    for (DelegatoAziendale delegato : listaDelegati) {
      DelegatoAziendale delegatoSalvato = delegatoAziendaleRepository
                                                            .findByUsername(delegato.getUsername());
      assertThat(delegato, is(equalTo(delegatoSalvato)));
    }
    
  }
  
  
}
