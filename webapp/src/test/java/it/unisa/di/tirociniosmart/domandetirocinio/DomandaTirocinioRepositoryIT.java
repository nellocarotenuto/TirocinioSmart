package it.unisa.di.tirociniosmart.domandetirocinio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.AziendaRepository;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativoRepository;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizione;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.studenti.StudenteRepository;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

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
 * Classe che definisce i casi di test per le operazioni sul database inerenti all'azienda e
 * definite dalla relativa repository.
 *
 * @see DomandaTirocinio
 * @see DomandaTirocinioRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DomandaTirocinioRepositoryIT {

  private List<DomandaTirocinio> listaDomande;
  
  @Autowired
  private DomandaTirocinioRepository domandaRepository;
  
  @Autowired
  private AziendaRepository aziendaRepository;
  
  @Autowired
  private StudenteRepository studenteRepository;
  
  @Autowired
  private ProgettoFormativoRepository progettoFormativoRepository;
  
  /**
   * Salva la lista delle domande tirocinio su database prima di ogni singolo test.
   */
  @Before
  public void salvaDomande() {
    listaDomande = new ArrayList<DomandaTirocinio>();
    
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
    studente1.setSesso(Studente.SESSO_MASCHILE);
    studente1.setUsername("FrancescoF");
    studente1.setPassword("francescof");
    
    // Crea la richiesta iscrizione #1
    RichiestaIscrizione richiestaIscrizione1 = studente1.getRichiestaIscrizione();
    richiestaIscrizione1.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
    richiestaIscrizione1.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiestaIscrizione1.setCommentoUfficioTirocini("commento");
    
    studente1 = studenteRepository.save(studente1);
    
    
    // Crea lo studente #2
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
    
    // Crea la richiesta d'iscrizione #2
    RichiestaIscrizione richiestaIscrizione2 = studente2.getRichiestaIscrizione();
    richiestaIscrizione2.setDataRichiesta(LocalDateTime.of(2017, 5, 27, 15, 12));
    richiestaIscrizione2.setStatus(RichiestaIscrizione.APPROVATA);
    richiestaIscrizione2.setCommentoUfficioTirocini("commento");
    
    studente2 = studenteRepository.save(studente2);
    
    
    // Crea l'azienda #1
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
    
    RichiestaConvenzionamento richiestaConvenzionamento1 = azienda1.getRichiesta();
    richiestaConvenzionamento1.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiestaConvenzionamento1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
    
    azienda1 = aziendaRepository.save(azienda1);
    
    
    // Crea l'azienda #2
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
    
    RichiestaConvenzionamento richiestaConvenzionamento2 = azienda2.getRichiesta();
    richiestaConvenzionamento2.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiestaConvenzionamento2.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    azienda2 = aziendaRepository.save(azienda2);
    
    
    // Crea progetto formativo #1
    ProgettoFormativo progetto1 = new ProgettoFormativo();
    progetto1.setAzienda(azienda1);
    progetto1.setNome("ProjectX");
    progetto1.setDescrizione("descrizioneeeeee");
    progetto1.setStatus(ProgettoFormativo.ATTIVO);
    progetto1.setAzienda(azienda1);
    
    progetto1 = progettoFormativoRepository.save(progetto1);

    
    // Crea il progetto formativo #2 
    ProgettoFormativo progetto2 = new ProgettoFormativo();
    progetto2.setAzienda(azienda2);
    progetto2.setNome("Assiri");
    progetto2.setDescrizione("descrizioneeeeee");
    progetto2.setStatus(ProgettoFormativo.ARCHIVIATO);
    azienda2.addProgettoFormativo(progetto2);

    progetto2 = progettoFormativoRepository.save(progetto2);
    
    
    //Crea domanda tirocinio #1
    DomandaTirocinio domanda1 = new DomandaTirocinio();
    domanda1.setProgettoFormativo(progetto1);
    domanda1.setStudente(studente1);
    domanda1.setStatus(DomandaTirocinio.ACCETTATA);
    domanda1.setCfu(8);
    domanda1.setData(LocalDateTime.now());
    domanda1.setInizioTirocinio(LocalDate.of(2018, 03, 11));
    domanda1.setFineTirocinio(LocalDate.of(2018, 04, 01));
    domanda1.setCommentoStudente("commento studente");
    domanda1.setCommentoAzienda("commento azienda");
    
    domanda1 = domandaRepository.save(domanda1);
    listaDomande.add(domanda1);
    
    
    //Crea domanda tirocinio #2
    DomandaTirocinio domanda2 = new DomandaTirocinio();
    domanda2.setProgettoFormativo(progetto2);
    domanda2.setStudente(studente2);
    domanda2.setStatus(DomandaTirocinio.APPROVATA);
    domanda2.setCfu(8);
    domanda2.setData(LocalDateTime.now());
    domanda2.setInizioTirocinio(LocalDate.of(2018, 02, 1));
    domanda2.setFineTirocinio(LocalDate.of(2018, 03, 15));
    domanda2.setCommentoStudente("commento studente");
    domanda2.setCommentoAzienda("commento azienda");
    domanda2.setCommentoImpiegato("commento impiegato");
    
    domanda2 = domandaRepository.save(domanda2);
    listaDomande.add(domanda2);
    

    aziendaRepository.flush();
    studenteRepository.flush();
    domandaRepository.flush();
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
    
    // Controlla che la lista delle domande ottenuta dalla repository sia uguale alla lista delle 
    // domande definite per il test
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
   * @test {@link DomandaTirocinioRepository#findAllByProgettoFormativo(long)}
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
                           domandaRepository.findAllByProgettoFormativoId(progetto.getId());
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
