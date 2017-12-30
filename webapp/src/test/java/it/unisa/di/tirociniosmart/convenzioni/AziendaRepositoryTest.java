package it.unisa.di.tirociniosmart.convenzioni;

import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import org.junit.Assert;
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
 * @see Azienda
 * @see AziendaRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AziendaRepositoryTest {

  @Autowired
  private AziendaRepository aziendaRepository;
  
  /**
   * Testa l'interazione con il database per l'inserimento ed il successivo caricamento
   * dell'azienda (tramite identificatore).
   * 
   * @test {@link AziendaRepository#findById(String)}
   * 
   * @result Il test è superato se l'entità viene correttamente salvata e caricata dal database
   */
  @Test
  public void findByIdTest() {
    Azienda azienda = new Azienda();
    azienda.setId("acmeltd");
    azienda.setNome("ACME Ltd.");
    azienda.setPartitaIva("01234567890");
    azienda.setSenzaBarriere(true);
    
    DelegatoAziendale delegato = azienda.getDelegato();
    delegato.setUsername("wilee");
    delegato.setEmail("wilee@coyote.com");
    delegato.setNome("Wile E.");
    delegato.setCognome("Coyote");
    delegato.setPassword("beepbeep");
    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato.setTelefono("9876543210");
    
    aziendaRepository.save(azienda);
    
    Assert.assertEquals(azienda, aziendaRepository.findById(azienda.getId()));
  }
  
}
