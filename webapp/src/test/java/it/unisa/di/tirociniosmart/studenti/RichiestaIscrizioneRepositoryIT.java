package it.unisa.di.tirociniosmart.studenti;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Classe che definisce i casi di test per le operazioni sul database inerenti alle richieste di 
 * iscrizione e definite dalla relativa repository.
 *
 * @see RichiestaIscrizione
 * @see RichiestaIscrizioneaRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RichiestaIscrizioneRepositoryIT {

  private static List<RichiestaIscrizione> listaIscrizioni;
  
  @Autowired
  private RichiestaIscrizioneRepository richiestaIscrizioneRepository;
  
  @Autowired
  private StudenteRepository studenteRepository;
  
  
  /**
   * Salva la lista delle richieste di iscrizione su database prima dell'esecuzione di ogni singolo
   * test.
   */
  @Before
  public void salvaRichiesteIscrizioni() {
    listaIscrizioni = new ArrayList<RichiestaIscrizione>();
    
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
    RichiestaIscrizione richiesta1 = studente1.getRichiestaIscrizione();
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiesta1.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiesta1.setCommentoUfficioTirocini("commento");
    
    studente1 = studenteRepository.save(studente1);
    listaIscrizioni.add(studente1.getRichiestaIscrizione());
    
    
    // Crea lo studente #2 
    Studente studente2 = new Studente();
    studente2.setNome("Nicola");
    studente2.setCognome("Scheggia");
    studente2.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente2.setDataRegistrazione(LocalDateTime.now());
    studente2.setEmail("nicola@scheggia.com");
    studente2.setIndirizzo("Via nicola, 9");
    studente2.setMatricola("0512103455");
    studente2.setTelefono("3331234654");
    studente2.setSesso("M");
    studente2.setUsername("NicolaS");
    studente2.setPassword("nicolas");
    
    // Crea la richiesta iscrizione #2
    RichiestaIscrizione richiesta2 = studente2.getRichiestaIscrizione();
    richiesta2.setDataRichiesta(LocalDateTime.of(2017, 5, 27, 15, 12));
    richiesta2.setStatus(RichiestaIscrizione.APPROVATA);
    richiesta2.setCommentoUfficioTirocini("commento");
    
    studente2 = studenteRepository.save(studente2);
    listaIscrizioni.add(studente2.getRichiestaIscrizione());
    
    
    // Crea lo studente #3
    Studente studente3 = new Studente();
    studente3.setNome("Alessandra");
    studente3.setCognome("Amoroso");
    studente3.setDataDiNascita(LocalDate.of(1980, 6, 12));
    studente3.setDataRegistrazione(LocalDateTime.now());
    studente3.setEmail("alessandra@amoroso.com");
    studente3.setIndirizzo("Via alessandra, 9");
    studente3.setMatricola("0512103488");
    studente3.setTelefono("3331236523");
    studente3.setSesso("F");
    studente3.setUsername("AlessandraA");
    studente3.setPassword("alessandraa");
    
    // Crea la richiesta iscrizione #3
    RichiestaIscrizione richiesta3 = studente3.getRichiestaIscrizione();
    richiesta3.setDataRichiesta(LocalDateTime.of(2017, 1, 14, 15, 12));
    richiesta3.setStatus(RichiestaIscrizione.APPROVATA);
    richiesta3.setCommentoUfficioTirocini("commento");
    
    studente3 = studenteRepository.save(studente3);
    listaIscrizioni.add(studente3.getRichiestaIscrizione());
    
    
    studenteRepository.flush();
  }
  
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di richieste di
   * iscrizione che si trovano in uno stato specificato.
   * 
   * @test {@link RichiestaIscrizioneRepository#findAllByStatus(int)}
   * 
   * @result Il test è superato se sono caricate solo le richieste di iscrizione della lista 
   *         che si trovano nello stato specificato
   */
  @Test
  public void findAllByStatus() {
    List<RichiestaIscrizione> richiesteApprovate = new ArrayList<RichiestaIscrizione>();
    for (RichiestaIscrizione richiesta: listaIscrizioni) {
      if (richiesta.getStatus() == RichiestaIscrizione.APPROVATA) {
        richiesteApprovate.add(richiesta);
      }
    }
    
    // Controlla che la lista delle richieste di iscrizione ottenuta dalla repository contenga
    // le richieste di iscrizione approvate definite per il test
    List<RichiestaIscrizione> richiesteApprovateSalvate = 
        richiestaIscrizioneRepository.findAllByStatus(RichiestaIscrizione.APPROVATA);
    assertThat(richiesteApprovate, everyItem(isIn(richiesteApprovateSalvate)));
  }
  
  /**
   * Testa l'interazione con il database per il singolo caricamento delle richieste di iscrizioni 
   * della lista tramite identificatore.
   * 
   * @test {@link RichiestaIscrizioneRepository#findById(String)}
   * 
   * @result Il test è superato se ogni entità viene correttamente caricata dal database
   */
  @Test
  public void findById() {
    for (RichiestaIscrizione richiesta: listaIscrizioni) {
      RichiestaIscrizione richiestaSalvata = 
          richiestaIscrizioneRepository.findById(richiesta.getId());
      assertThat(richiesta, is(equalTo(richiestaSalvata)));
    }
  }
}
