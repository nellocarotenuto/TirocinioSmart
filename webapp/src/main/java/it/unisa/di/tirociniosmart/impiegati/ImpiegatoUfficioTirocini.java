package it.unisa.di.tirociniosmart.impiegati;

import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

/**
 * Classe che modella un impiegato dell'ufficio tirocini.
 * 
 * @see UtenteRegistrato
 */
public class ImpiegatoUfficioTirocini extends UtenteRegistrato {

  /**
   * Costruisce un oggetto ImpiegatoUfficioTirocini dotato solo delle proprietà definite
   * nella superclasse {@link UtenteRegistrato}.
   */
  public ImpiegatoUfficioTirocini() {
    super();
  }
  
  /**
   * Determina se due oggetti rappresentano lo stesso impiegato guardando agli username dei
   * suddetti.
   */
  public boolean equals(Object object) {
    return super.equals(object);
  }
  
}
