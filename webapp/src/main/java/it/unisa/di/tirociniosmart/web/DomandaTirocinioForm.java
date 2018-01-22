package it.unisa.di.tirociniosmart.web;

/**
 * Oggetto utilizzato per la mappatura dei campi del form di invio di una domanda di tirocinio  
 * HTML. Questo oggetto viene passato come parametro ai controller dalla dispatcher servlet quando 
 * uno studente sottomette il modulo per inviare una domanda.
 */
public class DomandaTirocinioForm {

  DomandaTirocinioForm() {
  }
  
  public Integer getCfu() {
    return cfu;
  }
  
  public void setCfu(Integer cfu) {
    this.cfu = cfu;
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
  
  public Integer getPosizione() {
    return posizione;
  }
  
  public void setPosizione(Integer posizione) {
    this.posizione = posizione;
  }

  private Integer cfu;
  private String commentoStudente;
  private Integer giornoInizio;
  private Integer meseInizio;
  private Integer annoInizio;
  private Integer giornoFine;
  private Integer meseFine;
  private Integer annoFine;
  private Long idProgettoFormativo;
  
  private Integer posizione;
  
}
