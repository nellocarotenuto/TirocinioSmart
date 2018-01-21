package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Oggetto utilizzato per la mappatura dei campi del form di invio di una domanda di tirocinio  
 * HTML. Questo oggetto viene passato come parametro ai controller dalla dispatcher servlet quando 
 * uno studente sottomette il modulo per inviare una domanda.
 */
public class DomandaTirocinioForm {

  DomandaTirocinioForm() {
  }
  
  public int getCfu() {
    return cfu;
  }
  
  public void setCfu(int cfu) {
    this.cfu = cfu;
  }
  
  public int getStatus() {
    return status;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
  
  public LocalDateTime getData() {
    return data;
  }
  
  public void setData(LocalDateTime data) {
    this.data = data;
  }
  
  public LocalDate getInizioTirocinio() {
    return inizioTirocinio;
  }
  
  public void setInizioTirocinio(LocalDate inizioTirocinio) {
    this.inizioTirocinio = inizioTirocinio;
  }
  
  public LocalDate getFineTirocinio() {
    return fineTirocinio;
  }
  
  public void setFineTirocinio(LocalDate fineTirocinio) {
    this.fineTirocinio = fineTirocinio;
  }
  
  public ProgettoFormativo getProgetto() {
    return progetto;
  }

  public void setProgetto(ProgettoFormativo progetto) {
    this.progetto = progetto;
  }

  private int cfu;
  private int status;
  private LocalDateTime data;
  private LocalDate inizioTirocinio;
  private LocalDate fineTirocinio;
  private ProgettoFormativo progetto;

}
