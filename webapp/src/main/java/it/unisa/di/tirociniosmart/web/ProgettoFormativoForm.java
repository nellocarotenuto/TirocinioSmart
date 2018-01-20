package it.unisa.di.tirociniosmart.web;

/**
 * Oggetto utilizzato per la mappatura dei campi del form di aggiunta di un progetto formativo  
 * HTML. Questo oggetto viene passato come parametro ai controller dalla dispatcher servlet quando 
 * un delegato aziendale sottomette il modulo per aggiungere un nuovo progetto.
 */
public abstract class ProgettoFormativoForm {

  ProgettoFormativoForm() {
  }
  
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



  private String nome;
  private String descrizione;

}
