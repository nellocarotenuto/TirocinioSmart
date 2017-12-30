package it.unisa.di.tirociniosmart.studenti;

import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Classe che modella uno studente che ha richiesto l'iscrizione alla piattaforma.
 * 
 * @see UtenteRegistrato
 */
@Entity
public class Studente extends UtenteRegistrato {
  
  /**
   * Costruisce un oggetto Studente vuoto che deve essere popolata con i metodi setters.
   */
  public Studente() {
    this.richiestaIscrizione = new RichiestaIscrizione(this);
    this.domandeTirocinio = new ArrayList<DomandaTirocinio>();
  }
  
  /**
   * Determina se due oggetti rappresentano lo stesso delegato guardando agli username dei suddetti.
   */
  @Override
  public boolean equals(Object object) {
    return super.equals(object);
  }
  
  /**
   * Permette di definire una stringa che può essere considerata come la 
   * "rappresentazione testuale" dell'oggetto Studente.
   * 
   * @return Stringa che rappresenta una descrizione più accurata e consona dell'oggetto
   */
  @Override
  public String toString() {
    return "Studente [dataRegistrazione=" + dataRegistrazione + ", matricola=" + matricola
        + ", indirizzo=" + indirizzo + ", dataDiNascita=" + dataDiNascita + ", sesso=" + sesso
        + ", telefono=" + telefono + ", richiestaIscrizione=" + richiestaIscrizione
        + ", domandeTirocinio=" + domandeTirocinio + ", username=" + username + ", password="
        + password + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + "]";
  }

  /**
   * Permette di ottenere la data e l'ora di registrazione dello studente alla piattaforma.
   * 
   * @return Oggetto LocalDateTime che rappresenta la data e l'ora in cui è stata
   *         inviata la richiesta. <b>Può essere null</b> se la {@link RichiestaIscrizione}
   *         associata allo studente non è stata approvata.
   */
  public LocalDateTime getDataRegistrazione() {
    return dataRegistrazione;
  }
  
  /**
   * Permette di specificare la data e l'ora di registrazione dello studente alla piattaforma.
   * 
   * @param dataRegistrazione Oggetto LocalDateTime che rappresenta la data e l'ora in cui è stata
   *                          inviata la richiesta.
   * 
   * @pre dataRegistrazione != null
   * 
   * @post getDataRegistrazione().isEqual(dataRegistrazione)
   */
  public void setDataRegistrazione(LocalDateTime dataRegistrazione) {
    this.dataRegistrazione = dataRegistrazione;
  }
  
  /**
   * Permette di ottenere la matricola dello studente.
   * 
   * @return Stringa numerica che rappresenta la matricola dello studente.
   */
  public String getMatricola() {
    return matricola;
  }
  
  /**
   * Permette di specificare la matricola dello studente.
   * 
   * @param matricola Stringa numerica che rappresenta la matricola dello studente.
   * 
   * @pre matricola != null
   * @pre matricola matches {@link Studente#MATRICOLA_PATTERN}
   * 
   * @post getMatricola().equals(matricola)
   */
  public void setMatricola(String matricola) {
    this.matricola = matricola;
  }
  
  /**
   * Permette di ottenere l'indirizzo dello studente.
   * 
   * @return Stringa che rappresenta l'indirizzo dello studente.
   */
  public String getIndirizzo() {
    return indirizzo;
  }
  
  /**
   * Permette di specificare l'indirizzo dello studente.
   * 
   * @param indirizzo Stringa che rappresenta l'indirizzo dello studente
   * 
   * @pre indirizzo.length() > 0
   * 
   * @post getIndirizzo().equals(indirizzo)
   */
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }
  
  /**
   * Permette di ottenere la data di nascita dello studente.
   * 
   * @return Oggetto LocalDate che rappresenta la data di nascita dello studente.
   */
  public LocalDate getDataDiNascita() {
    return dataDiNascita;
  }
  
  /**
   * Permette di specificare la data di nascita dello studente.
   * 
   * @param dataDiNascita Oggetto LocalDate che rappresenta la data di nascita dello studente.
   * 
   * @pre dataDiNascita != null
   * 
   * @post getDataDiNascita().isEqual(dataDiNascita)
   */
  public void setDataDiNascita(LocalDate dataDiNascita) {
    this.dataDiNascita = dataDiNascita;
  }
  
  /**
   * Permette di ottenere il sesso dello studente.
   * 
   * @return {@link UtenteRegistrato#SESSO_MASCHILE} se lo studente è uomo,
   *         {@link UtenteRegistrato#SESSO_FEMMINILE} se invece è donna
   */
  public char getSesso() {
    return sesso;
  }
  
  /**
   * Permette di specificare il sesso dello studente.
   * 
   * @param sesso Carattere che rappresenta il sesso dello studente.
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
   * Permette di ottenere il numero di telefono dello studente.
   * 
   * @return Stringa numerica che rappresenta il numero di telefono dello studente.
   */
  public String getTelefono() {
    return telefono;
  }
  
  /**
   * Permette di specificare il numero di telefono dello studente.
   * 
   * @param telefono Stringa numerica che rappresenta il numero di telefono dello studente.
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
   * Permette di ottenere la richiesta d'iscrizione associata allo studente.
   * 
   * @return Oggetto {@link RichiestaIscrizione} che rappresenta la richiesta d'iscrizione avanzata
   *         dallo studente.
   */
  public RichiestaIscrizione getRichiestaIscrizione() {
    return richiestaIscrizione;
  }
  
  /**
   * Permette di ottenere la lista delle domande di tirocinio inviate dallo studente.
   * 
   * @return Lista delle domande di tirocinio inviate dallo studente
   */
  public List<DomandaTirocinio> getDomandeTirocinio() {
    return domandeTirocinio;
  }
  
  /**
   * Permette di aggiungere una domanda di tirocinio alla lista di quelle inviate dallo studente.
   * 
   * @param domandaTirocinio Oggetto {@link DomandaTirocinio} che rappresenta la domanda che si
   *                         si vuole aggiungere alla lista di quelle inviate dallo studente
   */
  public void addDomandaTirocinio(DomandaTirocinio domandaTirocinio) {
    if (!domandeTirocinio.contains(domandaTirocinio)) {
      domandeTirocinio.add(domandaTirocinio);
      domandaTirocinio.setStudente(this);
    }
  }
  
  private LocalDateTime dataRegistrazione;
  private String matricola;
  private String indirizzo;
  private LocalDate dataDiNascita;
  private char sesso;
  private String telefono;
  
  @OneToOne(cascade = CascadeType.ALL)
  private RichiestaIscrizione richiestaIscrizione;
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "studente")
  private List<DomandaTirocinio> domandeTirocinio;
  
  /** Espressione regolare che definisce il formato del campo matricola. */
  public static final String MATRICOLA_PATTERN = "^[0-9]{10}$";
  
}
