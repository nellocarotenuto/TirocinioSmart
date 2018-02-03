package it.unisa.di.tirociniosmart.convenzioni;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 * Classe che modella una richiesta di convenzionamento con il dipartimento.
 * <b>Questa classe non può essere istanziata dall'esterno ma sue istanze possono essere ottenute
 * solo tramite {@link Azienda#getRichiesta()}.</b>
 * 
 * @see Azienda
 */
@Entity
public class RichiestaConvenzionamento {

  /**
   * Costruisce un oggetto RichiestaConvenzionamento vuoto.
   * <b>Questo costruttore non dev'essere mai utilizzato</b>, è presente solo per esigenze del
   * container.
   */
  RichiestaConvenzionamento() {
  }
  
  /**
   * Costruisce un oggetto RichiestaConvenzionamento vuoto che dev'essere popolato tramite i metodi
   * setters.
   * Questo costruttore ha visibilità di pacchetto e non può essere utilizzato all'esterno. Istanze
   * di questa classe si possono ottenere solo tramite {@link Azienda#getRichiesta()}.
   */
  RichiestaConvenzionamento(Azienda azienda) {
    this.azienda = azienda;
  }
  
  /**
   * Determina se due oggetti rappresentano la stessa richiesta di convenzionamento in base
   * all'identificatore.
   */
  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    
    if (object.getClass() != getClass()) {
      return false;
    }
    
    RichiestaConvenzionamento richiestaConvenzionamento = (RichiestaConvenzionamento) object;
    return id == richiestaConvenzionamento.getId();
  }
  
  /**
   * Permette di definire una stringa che può essere considerata come la 
   * "rappresentazione testuale" dell'oggetto RichiestaConvenzionamento.
   * 
   * @return Stringa che rappresenta una descrizione più accurata e consona dell'oggetto
   */
  @Override
  public String toString() {
    return "RichiestaConvenzionamento [id=" + id + ", status=" + status + ", dataRichiesta="
        + dataRichiesta + ", azienda=" + azienda.getId() + "]";
  }

  /**
   * Permette di ottenere l'identificatore della richiesta di convenzionamento.
   * 
   * @return Long che rappresenta l'identificatore della richiesta di convenzionamento
   */
  public long getId() {
    return id;
  }
  
  /**
   * Permette di ottenere lo stato della richiesta di convenzionamento.
   * 
   * @return {@link RichiestaConvenzionamento#IN_ATTESA} se la richiesta è in attesa,
   *         {@link RichiestaConvenzionamento#APPROVATA} se la richiesta è approvata,
   *         {@link RichiestaConvenzionamento#RIFIUTATA} se la richiesta è rifiutata
   */
  public int getStatus() {
    return status;
  }
  
  /**
   * Permette di specificare lo stato della richiesta di convenzionamento.
   * 
   * @param status Intero che rappresenta lo stato che si vuole assegnare alla richiesta di
   *               convenzionamento
   *               
   * @pre status = {@link RichiestaConvenzionamento#IN_ATTESA} or
   *      status = {@link RichiestaConvenzionamento#APPROVATA} or
   *      status = {@link RichiestaConvenzionamento#RIFIUTATA}
   *      
   * @post getStatus() = status
   */
  public void setStatus(int status) {
    this.status = status;
  }
  
  /**
   * Permette di ottenere la data e l'ora in cui è stata inviata la richiesta.
   * 
   * @return La data e l'ora in cui è stata inviata la richiesta
   */
  public LocalDateTime getDataRichiesta() {
    return dataRichiesta;
  }
  
  /**
   * Permette di ottenere il commento alla richiesta fatto dall'ufficio tirocini.
   *   
   * @return La stringa che rappresenta il commento alla richiesta da parte dell'ufficio tirocini
   */
  public String getCommentoUfficioTirocini() {
    return commentoUfficioTirocini;
  }

  /**
   * Permette di specificare il commento alla richiesta da parte dell'ufficio tirocini.
   * 
   * @param commentoUfficioTirocini Stringa che rappresenta il commento che si vuole assegnare
   *                                alla richiesta
   *                                
   * @pre commentoUfficioTirocini != null
   * 
   * @post getCommentoUfficioTirocini().equals(commentoUfficioTirocini)
   */
  public void setCommentoUfficioTirocini(String commentoUfficioTirocini) {
    this.commentoUfficioTirocini = commentoUfficioTirocini;
  }

  /**
   * Permette di specificare la data e l'ora in cui è stata inviata la richiesta di
   * convenzionamento.
   *  
   * @param dataRichiesta Oggetto LocalDateTime che rappresenta la data e l'ora in cui è stata
   *                      inviata la richiesta di convenzionamento.
   * 
   * @pre dataRichiesta != null
   * 
   * @post getDataRichiesta().equals(dataRichiesta)
   */
  public void setDataRichiesta(LocalDateTime dataRichiesta) {
    this.dataRichiesta = dataRichiesta;
  }
  
  /**
   * Permette di ottenere l'azienda associata alla richiesta di convenzionamento.
   * 
   * @return L'oggetto {@link Azienda} che rappresenta l'azienda associata alla richiesta di
   *         convenzionamento
   */
  public Azienda getAzienda() {
    return azienda;
  }
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  private int status;
  private LocalDateTime dataRichiesta;
  
  @Lob
  private String commentoUfficioTirocini;
  
  @OneToOne(mappedBy = "richiestaConvenzionamento")
  private Azienda azienda;
  
  
  /**
   * Costante che rappresenta lo stato "in attesa" di una richiesta di convenzionamento.
   * Una richiesta si trova in questo stato quando non è ancora stata gestita dall'ufficio tirocini.
   */
  public static final int IN_ATTESA = 0;
  
  /**
   * Costante che rappresenta lo stato "approvato" di una richiesta di convenzionamento.
   * Una richiesta si trova in questo stato quando è stata esaminata ed approvata dall'ufficio
   * tirocini.
   */
  public static final int APPROVATA = 1;
  
  /**
   * Costante che rappresenta lo stato "rifiutato" di una richiesta di convenzionamento.
   * Una richiesta si trova in questo stato quando è stata esaminata e rifiutata dall'ufficio
   * tirocini.
   */
  public static final int RIFIUTATA = 2;
  

  /** Costante che definisce la minima lunghezza del campo commentoUfficioTirocini. */
  public static final int MIN_LUNGHEZZA_COMMENTO = 2;

}
