package it.unisa.di.tirociniosmart.convenzioni;

import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Classe che modella un delegato rappresentante un'azienda convenzionata con il dipartimento.
 * <b>Questa classe non può essere istanziata dall'esterno ma sue istanze possono essere ottenute
 * solo tramite {@link Azienda#getDelegato()}.</b>
 * 
 * @see Azienda
 */
@Entity
public class DelegatoAziendale extends UtenteRegistrato {

  /**
   * Costruisce un oggetto DelegatoAziendale vuoto.
   * <b>Questo costruttore non dev'essere mai utilizzato</b>, è presente solo per esigenze del
   * container.
   */
  DelegatoAziendale() {
  }
  
  /**
   * Costruisce un oggetto DelegatoAziendale vuoto che dev'essere popolato tramite i metodi setters.
   * Questo costruttore ha visibilità di pacchetto e non può essere utilizzato all'esterno. Istanze
   * di questa classe si possono ottenere solo tramite {@link Azienda#getDelegato()}.
   */
  DelegatoAziendale(Azienda azienda) {
    this.azienda = azienda;
  }

  /**
   * Determina se due oggetti rappresentano lo stesso delegato guardando agli username dei suddetti.
   */
  @Override
  public boolean equals(Object object) {
    return super.equals(object);
  }
  
  /**
   * Permette di ottenere il sesso del delegato aziendale.
   * 
   * @return {@link UtenteRegistrato#SESSO_MASCHILE} se il delegato è uomo,
   *         {@link UtenteRegistrato#SESSO_FEMMINILE} se invece è donna
   */
  public char getSesso() {
    return sesso;
  }
  
  /**
   * Permette di specificare il sesso del delegato aziendale.
   * 
   * @param sesso Carattere che rappresenta il sesso del delegato aziendale.
   * 
   * @pre sesso = {@link UtenteRegistrato#SESSO_MASCHILE} or
   *      sesso = {@link UtenteRegistrato#SESSO_FEMMINILE}
   *      
   * @post getSesso() = sesso
   */
  public void setSesso(char sesso) {
    this.sesso = sesso;
  }
  
  /**
   * Permette di ottenere il numero di telefono del delegato aziendale.
   * 
   * @return Stringa numerica che rappresenta il numero di telefono del delegato aziendale.
   */
  public String getTelefono() {
    return telefono;
  }
  
  /**
   * Permette di specificare il numero di telefono del delegato aziendale.
   * 
   * @param telefono Stringa numerica che rappresenta il numero di telefono del delegato aziendale.
   * 
   * @pre telefono.length() >= 10 and telefono.length() <= 11
   * @pre telefono matches {@link UtenteRegistrato#TELEFONO_PATTERN}
   * 
   * @post getTelefono().equals(telefono)
   */
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  /**
   * Permette di ottenere l'azienda che è rappresentata dal delegato aziendale.
   * 
   * @return {@link Azienda} rappresentata dal delegato aziendale
   */
  public Azienda getAzienda() {
    return azienda;
  }


  private char sesso;
  private String telefono;
  
  @OneToOne(mappedBy = "delegato")
  private Azienda azienda;
  
}
