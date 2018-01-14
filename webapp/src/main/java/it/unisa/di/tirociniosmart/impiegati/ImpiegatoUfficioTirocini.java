package it.unisa.di.tirociniosmart.impiegati;

import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import javax.persistence.Entity;

/**
 * Classe che modella un impiegato dell'ufficio tirocini.
 * 
 * @see UtenteRegistrato
 */
@Entity
public class ImpiegatoUfficioTirocini extends UtenteRegistrato {

  /**
   * Costruisce un oggetto ImpiegatoUfficioTirocini dotato solo delle proprietà definite
   * nella superclasse {@link UtenteRegistrato}.
   */
  public ImpiegatoUfficioTirocini() {
  }
  
  /**
   * Determina se due oggetti rappresentano lo stesso impiegato guardando agli username dei
   * suddetti.
   */
  public boolean equals(Object object) {
    return super.equals(object);
  }

  /**
   * Permette di definire una stringa che può essere considerata come la 
   * "rappresentazione testuale" dell'oggetto ImpiegatoUfficioTirocini.
   * 
   * @return Stringa che rappresenta una descrizione più accurata e consona dell'oggetto
   */
  @Override
  public String toString() {
    return "ImpiegatoUfficioTirocini [username=" + username + ", password=" + password + ", nome="
        + nome + ", cognome=" + cognome + ", email=" + email + "]";
  }
  
  
}
