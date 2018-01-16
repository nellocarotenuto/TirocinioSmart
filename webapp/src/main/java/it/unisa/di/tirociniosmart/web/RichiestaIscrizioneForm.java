package it.unisa.di.tirociniosmart.web;

import java.time.LocalDate;

/**
 * Oggetto utilizzato per la mappatura dei campi del form di una richiesta di iscrizione HTML. 
 * Questo oggetto viene passato come parametro ai controller dalla dispatcher servlet quando un 
 * utente sottomette il modulo di richiesta di iscrizione.
 * 
 * @see RegistrazioneForm
 */
public class RichiestaIscrizioneForm extends RegistrazioneForm {

  public RichiestaIscrizioneForm() {
    super();
  }
  
  public String getTelefono() {
    return telefono;
  }
  
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
  
  public String getSesso() {
    return sesso;
  }
  
  public void setSesso(String sesso) {
    this.sesso = sesso;
  }

  public String getIndirizzoStudente() {
    return indirizzoStudente;
  }
  
  public void setIndirizzoStudente(String indirizzoStudente) {
    this.indirizzoStudente = indirizzoStudente;
  }
  
  public String getMatricola() {
    return matricola;
  }

  public void setMatricola(String matricola) {
    this.matricola = matricola;
  }

  public int getGiornoDiNascita() {
    return giornoDiNascita;
  }

  public void setGiornoDiNascita(int giornoDiNascita) {
    this.giornoDiNascita = giornoDiNascita;
  }

  public int getMeseDiNascita() {
    return meseDiNascita;
  }

  public void setMeseDiNascita(int meseDiNascita) {
    this.meseDiNascita = meseDiNascita;
  }

  public int getAnnoDiNascita() {
    return annoDiNascita;
  }

  public void setAnnoDiNascita(int annoDiNascita) {
    this.annoDiNascita = annoDiNascita;
  }



  private String telefono;
  private String sesso;
  private String indirizzoStudente;
  private String matricola;
  private int giornoDiNascita;
  private int meseDiNascita;
  private int annoDiNascita;
}
