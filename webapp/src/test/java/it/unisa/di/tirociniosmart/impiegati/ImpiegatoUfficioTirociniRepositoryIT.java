package it.unisa.di.tirociniosmart.impiegati;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
 * Classe che definisce i casi di test per le operazioni sul database inerenti all'impiegato
 * dell'ufficio tirocini e definite dalla relativa repository.
 *
 * @see ImpiegatoUfficioTirocini
 * @see ImpiegatoUfficioTirociniRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ImpiegatoUfficioTirociniRepositoryIT {

  @Autowired
  private ImpiegatoUfficioTirociniRepository impiegatoRepository;
  
  private static List<ImpiegatoUfficioTirocini> listaImpiegati;
  
  /**
   * Popola la lista {@link #listaImpiegati} con oggetti fittizi che faranno da sorgente di dati 
   * per le operazioni di lettura e scrittura su database.
   */ 
  @BeforeClass
  public static void inizializzaImpiegati() {
    listaImpiegati = new ArrayList<ImpiegatoUfficioTirocini>();
    
    //Crea oggetto impiegato #1
    ImpiegatoUfficioTirocini impiegato1 = new ImpiegatoUfficioTirocini();
    impiegato1.setNome("Giovanni");
    impiegato1.setCognome("Verdi");
    impiegato1.setUsername("Giovanni55");
    impiegato1.setPassword("GiovanniUnisa");
    impiegato1.setEmail("giovanniverdi@gmail.com");
    
    listaImpiegati.add(impiegato1);
    
    //Crea oggetto impiegato #2
    ImpiegatoUfficioTirocini impiegato2 = new ImpiegatoUfficioTirocini();
    impiegato2.setNome("Lucia");
    impiegato2.setCognome("Casaburi");
    impiegato2.setUsername("ufficiounisa");
    impiegato2.setPassword("ufficioUnisa");
    impiegato2.setEmail("luciacasaburi@gmail.com");
    
    listaImpiegati.add(impiegato2);
    
    //Crea oggetto impiegato #3
    ImpiegatoUfficioTirocini impiegato3 = new ImpiegatoUfficioTirocini();
    impiegato3.setNome("Andrea");
    impiegato3.setCognome("Lorenzin");
    impiegato3.setUsername("andreaunisa");
    impiegato3.setPassword("andreaunisa");
    impiegato3.setEmail("andrealorenzin@gmail.com");
    
    listaImpiegati.add(impiegato3);
  }
  
  /**
   * Salva la lista di impiegati su database prima dell'esecuzione di ogni singolo test.
   */
  @Before
  public void salvaDelegati() {
    for (ImpiegatoUfficioTirocini impiegato : listaImpiegati) {
      impiegatoRepository.save(impiegato);
    }
  }
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di impiegati
   * tramite username e password.
   * 
   * @test {@link ImpiegatoUfficioTirociniRepository#findByUsernameAndPassword(String, String)}
   * 
   * @result Il test è superato se l'entità coivolta viene correttamente caricata dal database
   */
  @Test
  public void findByUsernameAndPassword() {
    // Controlla che ogni impiegato della lista per il test sia presente su database ricercandolo 
    // per username e password
    for (ImpiegatoUfficioTirocini impiegato : listaImpiegati) {
      ImpiegatoUfficioTirocini impiegatoSalvato = impiegatoRepository
                       .findByUsernameAndPassword(impiegato.getUsername(), impiegato.getPassword());
      assertThat(impiegato, is(equalTo(impiegatoSalvato)));
    }
  }
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di impiegati
   * tramite username.
   * 
   * @test {@link ImpiegatoUfficioTirociniRepository#findByUsername(String)}
   * 
   * @result Il test è superato l'entità coinvolta viene correttamente caricata dal database
   */
  @Test
  public void findByUsername() {
    //Controlla che ogni impiegato inserito per il test sia presente su database ricercandolo
    //per username
    for (ImpiegatoUfficioTirocini impiegato : listaImpiegati) {
      ImpiegatoUfficioTirocini impiegatoSalvato = impiegatoRepository
                                                           .findByUsername(impiegato.getUsername());
      assertThat(impiegato, is(equalTo(impiegatoSalvato)));
    }
    
  }
  
}
