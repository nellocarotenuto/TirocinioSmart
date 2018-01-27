package it.unisa.di.tirociniosmart.utenza;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.studenti.StudenteRepository;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

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

/**
 * Classe che definisce i casi di test per le operazioni sul database inerenti agli utenti
 * registrati definite dalla relativa repository.
 * 
 * @see UtenteRegistrato
 * @see UtenteRegistratoRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UtenteRegistratoRepositoryIT {

  @Autowired
  private UtenteRegistratoRepository utenteRegistratoRepository;
  
  private List<UtenteRegistrato> listaUtenti;
  
  
  /**
   * Salva la lista delle aziende su database prima dell'esecuzione di ogni singolo test.
   */
  @Before
  public void salvaUtenti() {
    listaUtenti = new ArrayList<UtenteRegistrato>();
    
    
    // Crea lo studente #1 ed inseriscilo nella lista
    Studente studente1 = new Studente();
    studente1.setUsername("daffyduck");
    studente1.setPassword("daffyduck");
    studente1.setEmail("duffy@duck.com");
    studente1.setNome("Daffy");
    studente1.setCognome("Duck");
    studente1.setDataDiNascita(LocalDate.of(1993, 4, 13));
    studente1.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    studente1.setDataRegistrazione(LocalDateTime.now());
    studente1.setTelefono("7856985478");
    studente1.setIndirizzo("via delle Anatre, 14 - Paperopoli");
    studente1.setMatricola("0512104785");
    
    studente1 = utenteRegistratoRepository.save(studente1);
    listaUtenti.add(studente1);
    
    
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
    
    delegato1 = utenteRegistratoRepository.save(delegato1);
    listaUtenti.add(delegato1);
    
    
    //Crea oggetto impiegato #1
    ImpiegatoUfficioTirocini impiegato1 = new ImpiegatoUfficioTirocini();
    impiegato1.setNome("Giovanni");
    impiegato1.setCognome("Verdi");
    impiegato1.setUsername("Giovanni55");
    impiegato1.setPassword("GiovanniUnisa");
    impiegato1.setEmail("giovanniverdi@gmail.com");
    
    impiegato1 = utenteRegistratoRepository.save(impiegato1);
    listaUtenti.add(impiegato1);
    
    utenteRegistratoRepository.flush();
  }
  
  
  /**
   * Testa l'interazione con il database per determinare se la ricerca di un utente tramite username
   * avvenga correttamente.
   * 
   * @test {@link UtenteRegistratoRepository#existsByUsername(String)}
   * 
   * @result Il test è superato se la ricerca tramite username degli utente presenti nella lista
   *         utilizzata per il test ha successo
   */
  @Test
  public void existsByUsername() {
    // Controlla che utente della lista utilizzata per il test sia presente su database 
    // ricercandolo tramite username
    for (UtenteRegistrato utente : listaUtenti) {
      boolean utenteEsistente = utenteRegistratoRepository.existsByUsername(utente.getUsername());
      
      assertThat(utenteEsistente, is(true));
    }
  }
  
  
  /**
   * Testa l'interazione con il database per determinare se la ricerca di un utente tramite e-mail
   * avvenga correttamente.
   * 
   * @test {@link UtenteRegistratoRepository#existsByUsername(String)}
   * 
   * @result Il test è superato se la ricerca tramite e-mail degli utente presenti nella lista
   *         utilizzata per il test ha successo
   */
  @Test
  public void existsByEmail() {
    // Controlla che utente della lista utilizzata per il test sia presente su database 
    // ricercandolo tramite e-mail
    for (UtenteRegistrato utente : listaUtenti) {
      boolean utenteEsistente = utenteRegistratoRepository.existsByEmail(utente.getEmail());
      
      assertThat(utenteEsistente, is(true));
    }
  }
  
}
