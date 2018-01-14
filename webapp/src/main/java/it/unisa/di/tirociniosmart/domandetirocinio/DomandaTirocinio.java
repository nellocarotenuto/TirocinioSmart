package it.unisa.di.tirociniosmart.domandetirocinio;

import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.studenti.Studente;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Classe che modella una domanda di tirocinio.
 */
@Entity
public class DomandaTirocinio {

  /**
   * Costruisce un oggetto DomandaTirocinio vuoto che deve essere popolata con i metodi setters.
   */
  public DomandaTirocinio() {
  }
  
  /**
   * Permette di determinare se due oggetti rappresentano la stessa domanda di tirocinio sulla base
   * dell'identificatore.
   */
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    
    if (getClass() != object.getClass()) {
      return false;
    }
    
    DomandaTirocinio domandaTirocinio = (DomandaTirocinio) object;
    
    return id == domandaTirocinio.getId();
  }
  
  /**
   * Permette di definire una stringa che può essere considerata come la 
   * "rappresentazione testuale" dell'oggetto DomandaTirocinio.
   * 
   * @return Stringa che rappresenta una descrizione più accurata e consona dell'oggetto
   */
  @Override
  public String toString() {
    return "DomandaTirocinio [id=" + id + ", status=" + status + ", data=" + data
        + ", inizioTirocinio=" + inizioTirocinio + ", fineTirocinio=" + fineTirocinio
        + ", commentoAzienda=" + commentoAzienda + ", commentoStudente=" + commentoStudente
        + ", cfu=" + cfu + ", studente=" + studente + ", progettoFormativo=" + progettoFormativo
        + "]";
  }

  /**
   * Permette di ottenere l'identificatore della domanda di tirocinio.
   * 
   * @return Long che rappresenta l'identificatore della domanda di tirocinio
   */
  public long getId() {
    return id;
  }

  /**
   * Permette di ottenere lo stato della domanda di tirocinio.
   * 
   * @return {@link DomandaTirocinio#IN_ATTESA} se la domanda è in attesa,
   *         {@link DomandaTirocinio#ACCETTATA} se la domanda è accettata,
   *         {@link DomandaTirocinio#RIFIUTATA} se la domanda è rifiutata,
   *         {@link DomandaTirocinio#APPROVATA} se la domanda è approvata,
   *         {@link DomandaTirocinio#RESPINTA} se la domanda è respinta
   */
  public int getStatus() {
    return status;
  }

  /**
   * Permette di specificare lo stato della domanda di tirocinio.
   * 
   * @param status Intero che rappresenta lo stato della domanda di tirocinio
   * 
   * @pre stauts = {@link DomandaTirocinio#IN_ATTESA} or
   *      stauts = {@link DomandaTirocinio#ACCETTATA} or
   *      stauts = {@link DomandaTirocinio#RIFIUTATA} or
   *      stauts = {@link DomandaTirocinio#APPROVATA} or
   *      stauts = {@link DomandaTirocinio#RESPINTA}
   *      
   * @post getStatus() = status
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * Permette di ottenere la data e l'ora in cui la domanda è stata inviata.
   * 
   * @return Oggetto LocalDateTime che rappresenta data ed ora in cui la domanda è stata inviata
   */
  public LocalDateTime getData() {
    return data;
  }

  /**
   * Permette di specificare la data e l'ora in cui la domanda è stata inviata.
   * 
   * @param data Oggetto LocalDateTime che rappresenta data ed ora in cui la domanda è stata inviata
   * 
   * @pre data != null
   * 
   * @post getData().isEqual(data)
   */
  public void setData(LocalDateTime data) {
    this.data = data;
  }

  /**
   * Permette di ottenere la data d'inizio del tirocinio.
   * 
   * @return Oggetto LocalDate che rappresenta la data d'inizio del tirocinio
   */
  public LocalDate getInizioTirocinio() {
    return inizioTirocinio;
  }

  /**
   * Permette di specificare la data d'inizio del tirocinio.
   * 
   * @param inizioTirocinio Oggetto LocalDate che rappresenta la data d'inizio del tirocinio
   * 
   * @pre inizioTirocinio != null
   * 
   * @post getDataInizioTirocinio().isEqual(inizioTirocinio)
   */
  public void setInizioTirocinio(LocalDate inizioTirocinio) {
    this.inizioTirocinio = inizioTirocinio;
  }

  /**
   * Permette di ottenere la data di fine del tirocinio.
   * 
   * @return Oggetto LocalDate che rappresenta la data di fine del tirocinio
   */
  public LocalDate getFineTirocinio() {
    return fineTirocinio;
  }

  /**
   * Permette di specificare la data di fine del tirocinio.
   * 
   * @param fineTirocinio Oggetto LocalDate che rappresenta la data di fine del tirocinio
   * 
   * @pre fineTirocinio != null
   * 
   * @post getDataFineTirocinio().isEqual(fineTirocinio)
   */
  public void setFineTirocinio(LocalDate fineTirocinio) {
    this.fineTirocinio = fineTirocinio;
  }

  /**
   * Permette di ottenere il commento dell'azienda alla domanda di tirocinio.
   * 
   * @return Stringa che rappresenta il commento dell'azienda alla domanda di tirocinio.
   *         <b>Tale stringa può essere null</b> se l'azienda non  ha commentato la richiesta
   */
  public String getCommentoAzienda() {
    return commentoAzienda;
  }

  /**
   * Permette di specificare il commento dell'azienda alla domanda di tirocinio.
   * 
   * @param commentoAzienda Stringa che rappresenta il commento dell'azienda alla domanda di
   *                        tirocinio
   *                        
   * @pre commentoAzienda != null
   * @pre commentoAzienda.length() > 0
   * 
   * @post getCommentoAzienda().equals(commentoAzienda)
   */
  public void setCommentoAzienda(String commentoAzienda) {
    this.commentoAzienda = commentoAzienda;
  }

  /**
   * Permette di ottenere il commento dello studente alla domanda di tirocinio.
   * 
   * @return Stringa che rappresenta il commento dello studente alla domanda di tirocinio
   */
  public String getCommentoStudente() {
    return commentoStudente;
  }

  /**
   * Permette di specificare il commento dello studente alla domanda di tirocinio.
   * 
   * @param commentoStudente Stringa che rappresenta il commento dello studente alla domanda di
   *                         tirocinio
   *                        
   * @pre commentoStudente != null
   * @pre commentoStudente.length() > 0
   * 
   * @post getCommentoStudente().equals(commentoStudente)
   */
  public void setCommentoStudente(String commentoStudente) {
    this.commentoStudente = commentoStudente;
  }

  /**
   * Permette di ottenere il numero di CFU cui il tirocinio è associato.
   * 
   * @return Intero che rappresta il numero di CFU cui il tirocinio è associato
   */
  public int getCfu() {
    return cfu;
  }

  /**
   * Permette di specificare il numero di CFU cui il tirocinio è associato.
   * 
   * @param cfu Intero che rappresenta il numero di CFU cui il tirocinio è associato
   * 
   * @pre cfu > 0
   * 
   * @post getCfu() = cfu
   */
  public void setCfu(int cfu) {
    this.cfu = cfu;
  }

  /**
   * Permette di ottenere lo studente associato alla domanda di tirocinio.
   * 
   * @return L'oggetto {@link Studente} associato alla domanda di tirocinio
   */
  public Studente getStudente() {
    return studente;
  }

  /**
   * Permette di specificare lo studente associato alla domanda di tirocinio.
   * 
   * @param studente L'oggetto {@link Studente} da associare alla domanda di tirocinio
   * 
   * @pre studente != null
   * 
   * @post getStudente() = studente
   */
  public void setStudente(Studente studente) {
    if (this.studente != studente) {
      this.studente = studente;
      studente.addDomandaTirocinio(this);
    }
  }

  /**
   * Permette di ottenere il progetto formativo associato alla domanda di tirocinio.
   * 
   * @return L'oggetto {@link ProgettoFormativo} associato alla domanda di tirocinio
   */
  public ProgettoFormativo getProgettoFormativo() {
    return progettoFormativo;
  }

  /**
   * Permette di specificare il progetto formativo associato alla domanda di tirocinio.
   * 
   * @param progettoFormativo L'oggetto {@link ProgettoFormativo} da associare alla domanda di
   *                          tirocinio
   *                          
   * @pre progettoFormativo != null
   * 
   * @post getProgettoFormativo().equals(progettoFormativo)
   */
  public void setProgettoFormativo(ProgettoFormativo progettoFormativo) {
    if (this.progettoFormativo != progettoFormativo) {
      this.progettoFormativo = progettoFormativo;
      progettoFormativo.addDomandaTirocinio(this);
    }
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private int status;
  private LocalDateTime data;
  private LocalDate inizioTirocinio;
  private LocalDate fineTirocinio;
  private String commentoAzienda;
  private String commentoStudente;
  private int cfu;
  
  @ManyToOne
  private Studente studente;
  
  @ManyToOne
  private ProgettoFormativo progettoFormativo;
  
  
  /**
   * Costante che rappresenta lo stato "in attesa" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando è stata inviata dallo studente ma
   * non è ancora stata esaminata e gestita dall'azienda che offre il progetto formativo ad essa
   * associato.
   */
  public static final int IN_ATTESA = 0;
  
  /**
   * Costante che rappresenta lo stato "accettato" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando è stata gestita ed accettata 
   * dall'azienda che offre il progetto formativo ad essa associato.
   */
  public static final int ACCETTATA = 1;
  
  /**
   * Costante che rappresenta lo stato "rifiutato" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando è stata gestita e rifiutata 
   * dall'azienda che offre il progetto formativo ad essa associato.
   */
  public static final int RIFIUTATA = 2;
  
  /**
   * Costante che rappresenta lo stato "approvato" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando è stata gestita ed approvata
   * dall'ufficio tirocini. <b>La domanda dev'essere stata precedentemente accettata</b> per potervi
   * assegnare questo stato.
   */
  public static final int APPROVATA = 3;
  
  /**
   * Costante che rappresenta lo stato "respinto" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando, sebbene accettata dall'azienda, la
   * domanda è stata respinta dall'ufficio tirocini.
   */
  public static final int RESPINTA = 4;

}
