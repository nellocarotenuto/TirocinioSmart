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
    
  public ProgettoFormativo getProgetto() {
    return progetto;
  }

  public void setProgetto(ProgettoFormativo progetto) {
    this.progetto = progetto;
  }

  public String getCommentoStudente() {
    return commentoStudente;
  }

  public void setCommentoStudente(String commentoStudente) {
    this.commentoStudente = commentoStudente;
  }
 
  public Integer getGiornoInizio() {
    return giornoInizio;
  }

  public void setGiornoInizio(Integer giornoInizio) {
    this.giornoInizio = giornoInizio;
  }

  public Integer getMeseInizio() {
    return meseInizio;
  }

  public void setMeseInizio(Integer meseInizio) {
    this.meseInizio = meseInizio;
  }

  public Integer getAnnoInizio() {
    return annoInizio;
  }

  public void setAnnoInizio(Integer annoInizio) {
    this.annoInizio = annoInizio;
  }

  public Integer getGiornoFine() {
    return giornoFine;
  }

  public void setGiornoFine(Integer giornoFine) {
    this.giornoFine = giornoFine;
  }

  public Integer getMeseFine() {
    return meseFine;
  }

  public void setMeseFine(Integer meseFine) {
    this.meseFine = meseFine;
  }

  public Integer getAnnoFine() {
    return annoFine;
  }

  public void setAnnoFine(Integer annoFine) {
    this.annoFine = annoFine;
  }

  public Long getIdProgettoFormativo() {
    return idProgettoFormativo;
  }

  public void setIdProgettoFormativo(Long idProgettoFormativo) {
    this.idProgettoFormativo = idProgettoFormativo;
  }




  private int cfu;
  private int status;
  private LocalDateTime data;
  private ProgettoFormativo progetto;
  private String commentoStudente;
  private Integer giornoInizio;
  private Integer meseInizio;
  private Integer annoInizio;
  private Integer giornoFine;
  private Integer meseFine;
  private Integer annoFine;
  private Long idProgettoFormativo;
}
