package it.unisa.di.tirociniosmart.studenti;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Classe che definisce i casi di test per le operazioni sul database inerenti allo studente
 * e definite dalla relativa repository.
 *
 * @see Studente
 * @see StudenteRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StudenteRepositoryIT {

  @Autowired
  private StudenteRepository studenteRepository;
  
  private List<Studente> listaStudenti;
  
  
  /**
   * Salva la lista di studenti su database prima dell'esecuzione di ogni singolo test.
   */
  @Before
  public void salvaDelegati() {
    listaStudenti = new ArrayList<Studente>();
    
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
    
    // Crea la richiesta iscrizione #1
    RichiestaIscrizione richiesta1 = new RichiestaIscrizione();
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiesta1.setStatus(RichiestaIscrizione.APPROVATA);
    richiesta1.setCommentoUfficioTirocini("commento");
    
    studente1 = studenteRepository.save(studente1);
    listaStudenti.add(studente1);
    
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
    
    //Crea la richiesta iscrizione #2
    RichiestaIscrizione richiesta2 = new RichiestaIscrizione();
    richiesta2.setDataRichiesta(LocalDateTime.of(2017, 9, 20, 15, 30));
    richiesta2.setStatus(RichiestaIscrizione.APPROVATA);
    richiesta2.setCommentoUfficioTirocini("commento");
    
    studente2 = studenteRepository.save(studente2);
    listaStudenti.add(studente2);
    
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
    
    //Crea la richiesta iscrizione #3
    RichiestaIscrizione richiesta3 = new RichiestaIscrizione();
    richiesta3.setDataRichiesta(LocalDateTime.of(2018, 1, 5, 10, 33));
    richiesta3.setStatus(RichiestaIscrizione.APPROVATA);
    richiesta3.setCommentoUfficioTirocini("commento");
    
    studente3 = studenteRepository.save(studente3);
    listaStudenti.add(studente3);
    
    
    studenteRepository.flush();
  }
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di studenti
   * tramite username e password.
   * 
   * @test {@link StudenteRepository#findByUsernameAndPassword(String, String)}
   * 
   * @result Il test è superato se l'entità coivolta viene correttamente caricata dal database
   */
  @Test
  public void findByUsernameAndPassword() {
    // Controlla che ogni studente della lista per il test sia presente su database ricercandolo 
    // per username e password
    for (Studente studente : listaStudenti) {
      Studente studenteSalvato = studenteRepository
                         .findByUsernameAndPassword(studente.getUsername(), studente.getPassword());
      assertThat(studente, is(equalTo(studenteSalvato)));
    }
  }
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di studenti
   * tramite username.
   * 
   * @test {@link StudenteRepository#findByUsername(String)}
   * 
   * @result Il test è superato l'entità coinvolta viene correttamente caricata dal database
   */
  @Test
  public void findByUsername() {
    //Controlla che ogni impiegato inserito per il test sia presente su database ricercandolo
    //per username
    for (Studente studente : listaStudenti) {
      Studente studenteSalvato = studenteRepository.findByUsername(studente.getUsername());
      assertThat(studente, is(equalTo(studenteSalvato)));
    }
  }
    
  /**
   * Testa l'interazione con il database per determinare se la ricerca di uno studente tramite
   * matricola avvenga correttamente.
   * 
   * @test {@link StudenteRepository#existsByMatricola(String)}
   * 
   * @result Il test è superato se la ricerca delle matricole deglis studenti presenti nella
   *         lista utilizzata per il test ha successo
   */
  @Test
  public void existsByMatricola() {
    // Controlla che ogni studente della lista utilizzata per il test sia presente su database 
    // ricercandolo per matricola
    for (Studente studente : listaStudenti) {
      boolean studenteEsistente = studenteRepository.existsByMatricola(studente.getMatricola());
      assertThat(studenteEsistente, is(true));
    }
  }
    
}
