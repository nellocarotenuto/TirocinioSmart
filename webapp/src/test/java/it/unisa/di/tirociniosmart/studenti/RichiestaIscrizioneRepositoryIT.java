package it.unisa.di.tirociniosmart.studenti;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.BeforeClass;
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
public class RichiestaIscrizioneRepositoryIT {

  @Autowired
  private RichiestaIscrizioneRepository richiestaIscrizioneRepository;
  
  private static List<RichiestaIscrizione> listaIscrizioni;
  
  
  
  @BeforeClass
  public static void inizializzaStudenti() {
    
    listaIscrizioni = new ArrayList<RichiestaIscrizione>();
    
    //Crea lo studente #1 
    Studente studente = new Studente();
    studente.setNome("Francesco");
    studente.setCognome("Facchinetti");
    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
    studente.setDataRegistrazione(LocalDateTime.now());
    studente.setEmail("francesco@facchinetti.com");
    studente.setIndirizzo("Via francesco, 9");
    studente.setMatricola("0512103434");
    studente.setTelefono("3331234123");
    studente.setSesso("M");
    studente.setUsername("FrancescoF");
    studente.setPassword("francescof");
    
    //Crea la richiesta iscrizione #1
    RichiestaIscrizione richiesta1 = new RichiestaIscrizione();
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiesta1.setStatus(RichiestaIscrizione.APPROVATA);
    richiesta1.setCommentoUfficioTirocini("commento");
    
    listaIscrizioni.add(richiesta1);
    
    
  }
}
