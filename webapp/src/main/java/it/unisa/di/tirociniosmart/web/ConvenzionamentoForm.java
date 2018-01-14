package it.unisa.di.tirociniosmart.web;

/**
 * Oggetto utilizzato per la mappatura dei campi del form di richiesta convenzionamento HTML. Questo
 * oggetto viene passato come parametro ai controller dalla dispatcher servlet quando un utente
 * sottomette il modulo di richiesta di convenzionamento.
 */
public class ConvenzionamentoForm extends RegistrazioneForm {
  
  ConvenzionamentoForm() {
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
  
  public String getIdAzienda() {
    return idAzienda;
  }
  
  public void setIdAzienda(String idAzienda) {
    this.idAzienda = idAzienda;
  }
  
  public String getNomeAzienda() {
    return nomeAzienda;
  }
  
  public void setNomeAzienda(String nomeAzienda) {
    this.nomeAzienda = nomeAzienda;
  }
  
  public String getPartitaIvaAzienda() {
    return partitaIvaAzienda;
  }
  
  public void setPartitaIvaAzienda(String partitaIvaAzienda) {
    this.partitaIvaAzienda = partitaIvaAzienda;
  }
  
  public String getIndirizzoAzienda() {
    return indirizzoAzienda;
  }
  
  public void setIndirizzoAzienda(String indirizzoAzienda) {
    this.indirizzoAzienda = indirizzoAzienda;
  }
  
  public boolean isSenzaBarriere() {
    return senzaBarriere;
  }
  
  public void setSenzaBarriere(boolean senzaBarriere) {
    this.senzaBarriere = senzaBarriere;
  }

  private String telefono;
  private String sesso;
  private String idAzienda;
  private String nomeAzienda;
  private String partitaIvaAzienda;
  private String indirizzoAzienda;
  private boolean senzaBarriere;
  
}
