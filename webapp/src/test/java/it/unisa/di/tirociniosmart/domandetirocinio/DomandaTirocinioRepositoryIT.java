package it.unisa.di.tirociniosmart.domandetirocinio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
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

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.AziendaRepository;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativoRepository;
import it.unisa.di.tirociniosmart.studenti.Studente;



/**
 * Classe che definisce i casi di test per le operazioni sul database inerenti all'azienda e
 * definite dalla relativa repository.
 *
 * @see DomandaTirocinio
 * @see DomandaTirocinioRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DomandaTirocinioRepositoryIT {

  @Autowired
  private DomandaTirocinioRepository domandaRepository;
  
  private static List<DomandaTirocinio> listaDomande;

  /**
   * Popola la lista {@link #listaDomande} con oggetti fittizi che faranno da sorgente di 
   * dati per le operazioni di lettura e scrittura su database.
   */
  @BeforeClass
  public static void inizializzaDomandeTirocinio() {
    
    listaDomande = new ArrayList<DomandaTirocinio>();
    
    //Crea lo studente #1 
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
    
    //Crea progetto formativo #1
    ProgettoFormativo progetto1 = new ProgettoFormativo();
    progetto1.setNome("ProjectX");
    progetto1.setDescrizione("descrizioneeeeee");
    progetto1.setStatus(ProgettoFormativo.ATTIVO);
    
    //Crea domanda tirocinio #1
    DomandaTirocinio domanda1 = new DomandaTirocinio();
    domanda1.setStatus(DomandaTirocinio.ACCETTATA);
    domanda1.setCfu(8);
    domanda1.setData(LocalDateTime.now());
    domanda1.setInizioTirocinio(LocalDate.of(2018, 03, 11));
    domanda1.setFineTirocinio(LocalDate.of(2018, 04, 01));
    domanda1.setCommentoStudente("commento studente");
    domanda1.setCommentoAzienda("commento azienda");
    
    listaDomande.add(domanda1);
    
    
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
    
    //Crea il progetto formativo #2 
    ProgettoFormativo progetto2 = new ProgettoFormativo();
    progetto2.setNome("Assiri");
    progetto2.setDescrizione("descrizioneeeeee");
    progetto2.setStatus(ProgettoFormativo.ARCHIVIATO);
    
    //Crea domanda tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setStatus(DomandaTirocinio.APPROVATA);
    domanda2.setCfu(8);
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 02, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 03, 15));
    domanda2.setCommentoStudente("commento studente");
    domanda2.setCommentoAzienda("commento azienda");
    domanda2.setCommentoImpiegato("commento impiegato");
    
    listaDomande.add(domanda2);
  }
  
  /**
   * Salva la lista delle domande tirocinio su database prima di ogni singolo test.
   */
  @Before
  public void salvaDomande() {
    for (DomandaTirocinio domanda: listaDomande) {
      domandaRepository.save(domanda);
    }
  }
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di domande tirocinio 
   * che si trovano in uno stato specificato.
   * 
   * @test {@link DomandaTirocinioRepository#findAllByStatus(int)}
   * 
   * @result Il test è superato se sono caricate solo le domande tirocinio il cui 
   *         stato si trova nello stato specificato
   */
  @Test
  public void findAllByStatus() {
    List<DomandaTirocinio> domandeApprovate = new ArrayList<DomandaTirocinio>();
    for (DomandaTirocinio domanda: listaDomande) {
      if (domanda.getStatus() == DomandaTirocinio.APPROVATA) {
        domandeApprovate.add(domanda);
      }
    }
    //Controlla che la lista delle domande ottenuta dalla repository sia uguale alla lista delle 
    //domande definite per il test
    List<DomandaTirocinio> domandeApprovateSalvate = 
        domandaRepository.findAllByStatus(DomandaTirocinio.APPROVATA);
    assertThat(domandeApprovate, everyItem(isIn(domandeApprovateSalvate)));
  }
  
  /**
   * Testa l'interazione con il database per il singolo caricamento delle domande tirocinio della 
   * lista tramite identificatore.
   * 
   * @test {@link DomandaTirocinioRepository#findById(String)}
   * 
   * @result Il test è superato se ogni entità viene correttamente caricata dal database
   */
  @Test
  public void findById() {
    for (DomandaTirocinio domanda: listaDomande) {
      DomandaTirocinio domandaSalvata = domandaRepository.findById(domanda.getId());
      assertThat(domanda, is(equalTo(domandaSalvata)));
    }
  }
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di domande tirocinio di un
   * dato studente che si trovano in uno stato specificato.
   * 
   * @test {@link DomandaTirocinioRepository#findAllByStatusAndStudenteUsername(int)}
   * 
   * @result Il test è superato se sono caricate solo le domande tirocinio dello studente il cui 
   *         stato si trova nello stato specificato
   */
  @Test
  public void findAllByStatusAndStudenteUsername() {
    List<DomandaTirocinio> domandeApprovateStudente = new ArrayList<DomandaTirocinio>();
    for (DomandaTirocinio domanda: listaDomande) {
      if (domanda.getStatus() == DomandaTirocinio.APPROVATA 
          && domanda.getStudente().getUsername() == "FrancescoF") {
        domandeApprovateStudente.add(domanda);
      }
    }
    //Controlla che la lista delle domande ottenuta dalla repository sia uguale alla lista delle 
    //domande definite per il test
    List<DomandaTirocinio> domandeApprovateStudenteSalvate = 
        domandaRepository.findAllByStatusAndStudenteUsername(DomandaTirocinio.APPROVATA,
                                                             "FrancescoF");
    assertThat(domandeApprovateStudente, everyItem(isIn(domandeApprovateStudenteSalvate)));
  }
  
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di domande tirocinio di una
   * data azienda che si trovano in uno stato specificato.
   * 
   * @test {@link DomandaTirocinioRepository#findAllByStatusAndProgettoFormativoAziendaId
   *                                        (int,String)}
   * 
   * @result Il test è superato se sono caricate solo le domande tirocinio dell'azienda il cui 
   *         stato si trova nello stato specificato
   */
  @Test
  public void findAllByStatusAndProgettoFormativoAziendaId() {
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    List<DomandaTirocinio> domandeStatusAzienda = new ArrayList<DomandaTirocinio>();
    for (DomandaTirocinio domanda: listaDomande) {
      if (domanda.getStatus() == DomandaTirocinio.APPROVATA
          && domanda.getProgettoFormativo().getAzienda().getId().equals(azienda.getId())) {
        domandeStatusAzienda.add(domanda);
      }
    }
    
    //Controlla che la lista delle domande ottenuta dalla repository sia uguale alla lista delle 
    //domande definite per il test
    List<DomandaTirocinio> domandeStatusAziendaSalvate = 
                           domandaRepository.findAllByStatusAndProgettoFormativoAziendaId(
                           DomandaTirocinio.APPROVATA, azienda.getId());
    assertThat(domandeStatusAzienda, everyItem(isIn(domandeStatusAziendaSalvate)));
  }
  
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di domande di tirocinio dato
   * un progetto formativo.
   * 
   * @test {@link DomandaTirocinioRepository#findAllByProgettoFormativo(ProgettoFormativo)}
   * 
   * @result Il test è superato se sono caricate solo le domande tirocinio della lista di quel dato
   *         progetto formativo
   */
  @Test
  public void findAllByProgettoFormativo() {
    ProgettoFormativo progetto = new ProgettoFormativo();
    progetto.setNome("ProjectX");
    progetto.setDescrizione("descrizioneeeeee");
    progetto.setStatus(ProgettoFormativo.ATTIVO);
    List<DomandaTirocinio> domandeProgettoFormativo = new ArrayList<DomandaTirocinio>();
    for (DomandaTirocinio domanda: listaDomande) {
      if (domanda.getProgettoFormativo().equals(progetto)) {
        domandeProgettoFormativo.add(domanda);
      }
    }
    //Controlla che la lista delle domande ottenuta dalla repository sia uguale alla lista delle 
    //domande definite per il test
    List<DomandaTirocinio> domandeProgettoSalvate =
                           domandaRepository.findAllByProgettoFormativo(progetto);
    assertThat(domandeProgettoFormativo, everyItem(isIn(domandeProgettoSalvate)));
  }
  
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di domande di tirocinio dato
   * l'username di uno studente.
   * 
   * @test {@link DomandaTirocinioRepository#findAllByStudenteUsername(String)}
   * 
   * @result Il test è superato se sono caricate solo le domande tirocinio della lista di quel dato
   *         studente.
   */
  @Test
  public void findAllByStudenteUsername() {
    Studente studente = new Studente();
    studente.setUsername("FrancescoF");
    List<DomandaTirocinio> domandeStudente = new ArrayList<DomandaTirocinio>();
    for (DomandaTirocinio domanda: listaDomande) {
      if (domanda.getStudente().equals(studente.getUsername())) {
        domandeStudente.add(domanda);
      }
    }
    //Controlla che la lista delle domande ottenuta dalla repository sia uguale alla lista delle 
    //domande definite per il test
    List<DomandaTirocinio> domandeStudenteSalvate = 
                           domandaRepository.findAllByStudenteUsername(studente.getUsername());
    assertThat(domandeStudente, everyItem(isIn(domandeStudenteSalvate)));
  }
}
