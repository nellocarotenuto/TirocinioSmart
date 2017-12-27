package it.unisa.di.tirociniosmart.convenzioni;

import java.time.LocalDateTime;

/**
 * Classe che modella una richiesta di convenzionamento con il dipartimento.
 * <b>Questa classe non può essere istanziata dall'esterno ma sue istanze possono essere ottenute
 * solo tramite {@link Azienda#getRichiesta()}.</b>
 * 
 * @see Azienda
 */
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
  

  private long id;
  private int status;
  private LocalDateTime dataRichiesta;
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

}
