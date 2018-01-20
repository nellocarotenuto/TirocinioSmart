package it.unisa.di.tirociniosmart.progettiformativi;



/**
 * Oggetto utilizzato per la mappatura dei campi di un form di aggiunta di un progetto formativo
 * HTML. Questo oggetto viene passato come parametro ai controller dalla dispatcher servlet quando
 * un utente sottomette il modulo di aggiunta.
 *
 */
public class ProgettoFormativoForm {


  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
  
  private String nome, descrizione;
}
