package it.unisa.di.tirociniosmart.progettiformativi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;

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
 * @see ProgettoFormativo
 * @see ProgettoFormativoRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProgettoFormativoRepositoryIT {

  @Autowired
  private ProgettoFormativoRepository progettoRepository;
  
  private static List<ProgettoFormativo> listaProgettiFormativi;
  
  
  /**
   * Popola la lista {@link #listaProgettiFormativi} con oggetti fittizi che faranno da sorgente di 
   * dati per le operazioni di lettura e scrittura su database.
   */
  @BeforeClass
  public static void inizializzaProgettiFormativi() {
    
    listaProgettiFormativi = new ArrayList<ProgettoFormativo>();
    
    //Crea l'azienda possedente il progetto formativo #1
    Azienda azienda1 = new Azienda();
    azienda1.setId("acmeltd");
    azienda1.setNome("ACME Ltd.");
    azienda1.setPartitaIva("01234567890");
    azienda1.setSenzaBarriere(true);
    azienda1.setIndirizzo("Grand Canyon");
    
    //Crea progetto formativo #1
    ProgettoFormativo progetto1 = new ProgettoFormativo();
    progetto1.setNome("ProjectX");
    progetto1.setDescrizione("descrizioneeeeee");
    progetto1.setStatus(ProgettoFormativo.ATTIVO);

    listaProgettiFormativi.add(progetto1);
    
    // Crea l'azienda #2 possedente il progetto formativo #2
    Azienda azienda2 = new Azienda();
    azienda2.setId("starkind");
    azienda2.setNome("Stark Industries");
    azienda2.setPartitaIva("74598763241");
    azienda2.setSenzaBarriere(true);
    azienda2.setIndirizzo("Marvel Valley, 45");
    
    //Crea progetto formativo #2
    ProgettoFormativo progetto2 = new ProgettoFormativo();
    progetto2.setNome("Assiri");
    progetto2.setDescrizione("descrizioneeeeee");
    progetto2.setStatus(ProgettoFormativo.ARCHIVIATO);

    listaProgettiFormativi.add(progetto2);
   
    //Crea l'azienda possedente il progetto formativo #3
    Azienda azienda3 = new Azienda();
    azienda3.setId("cyberdynecorp");
    azienda3.setNome("Cyberdyne System Corporation");
    azienda3.setPartitaIva("54569814752");
    azienda3.setSenzaBarriere(false);
    azienda3.setIndirizzo("Steel Mountain, 57");
    
    //Crea progetto formativo #3
    ProgettoFormativo progetto3 = new ProgettoFormativo();
    progetto3.setNome("America");
    progetto3.setDescrizione("descrizioneeeeee");
    progetto3.setStatus(ProgettoFormativo.ATTIVO);

    listaProgettiFormativi.add(progetto3);
    
  }
  
  
  /**
   * Salva la lista dei progetti formativi su database prima di ogni singolo test.
   */
  @Before
  public void salvaProgettiFormativi() {
    for (ProgettoFormativo progetto: listaProgettiFormativi) {
      progettoRepository.save(progetto);
    }
  }
  
  /**
   * Testa l'interazione con il database per il singolo caricamento dei progetti formativi della 
   * lista tramite identificatore.
   * 
   * @test {@link ProgettoFormativoRepository#findById(String)}
   * 
   * @result Il test è superato se ogni entità viene correttamente caricata dal database
   */
  @Test
  public void findById() {
    for (ProgettoFormativo progetto: listaProgettiFormativi) {
      ProgettoFormativo progettoSalvato = progettoRepository.findById(progetto.getId());
      assertThat(progetto, is(equalTo(progettoSalvato)));
    }
  }
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di progetti formativi di una
   * data azienda che si trovano in uno stato specificato.
   * 
   * @test {@link ProgettoFormativoRepository#findAllByStatusAndAziendaId(int)}
   * 
   * @result Il test è superato se sono caricate solo i progetti formativi dell' azienda il cui 
   *         stato si trova nello stato specificato
   */
  @Test
  public void findAllByStatusAndAziendaId() {
    List<ProgettoFormativo> progettiAttiviAzienda = new ArrayList<ProgettoFormativo>();
    for (ProgettoFormativo progetto: listaProgettiFormativi) {
      if (progetto.getStatus() == ProgettoFormativo.ATTIVO 
          && progetto.getAzienda().getNome().equals("acmeltd")) { 
        progettiAttiviAzienda.add(progetto);
      }
    }
    
    // Controlla che ogni elemento della lista restituita dalla repository sia nella lista
    // utilizzata per il test
    List<ProgettoFormativo> progettiSalvati = progettoRepository.findAllByStatusAndAziendaId(
         ProgettoFormativo.ATTIVO, listaProgettiFormativi.get(0).getAzienda().getId());
    assertThat(progettiAttiviAzienda, everyItem(isIn(progettiSalvati)));
      
  }
  
  /**
   * Testa l'interazione con il database per determinare se la ricerca di un progetto formativo
   * tramite id avvenga correttamente.
   * 
   * @test {@link ProgettoFormativoRepository#existsById(String)}
   * 
   * @result Il test è superato se la ricerca degli id dei progetti formativi presenti nella lista
   *         utilizzata per il test ha successo
   */
  @Test
  public void existById() {
    for (ProgettoFormativo progetto: listaProgettiFormativi) {
      boolean progettoEsistente = progettoRepository.existsById(progetto.getId());
      assertThat(progettoEsistente, is(true));
    }
  }
}
