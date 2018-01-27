package it.unisa.di.tirociniosmart.progettiformativi;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Classe che modella un progetto formativo offerto da un'azienda.
 * 
 * @see Azienda
 */
@Entity
public class ProgettoFormativo {

  /**
   * Costruisce un oggetto ProgettoFormativo vuoto che deve essere popolata con i metodi setters.
   */
  public ProgettoFormativo() {
    this.domandeTirocinio = new ArrayList<DomandaTirocinio>();
  }
  
  /**
   * Permette di determinare se due oggetti rappresentano lo stesso progetto formativo sulla base
   * dell'identificatore.
   */
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    
    if (getClass() != object.getClass()) {
      return false;
    }
    
    ProgettoFormativo progettoFormativo = (ProgettoFormativo) object;
    
    return id == progettoFormativo.getId();
  }
  
  /**
   * Permette di definire una stringa che può essere considerata come la 
   * "rappresentazione testuale" dell'oggetto ProgettoFormativo.
   * 
   * @return Stringa che rappresenta una descrizione più accurata e consona dell'oggetto
   */
  @Override
  public String toString() {
    return "ProgettoFormativo [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione
        + ", status=" + status + ", azienda=" + azienda.getId() + ", domandeTirocinio="
        + domandeTirocinio + "]";
  }

  /**
   * Permette di ottenere l'identificatore del progetto formativo.
   * 
   * @return Long che rappresenta l'identificatore del progetto formativo
   */
  public long getId() {
    return id;
  }

  /**
   * Permette di ottenere il nome del progetto formativo.
   * 
   * @return La stringa che rappresenta il nome del progetto formativo
   */
  public String getNome() {
    return nome;
  }

  /**
   * Permette di specificare il nome del progetto formativo.
   * 
   * @param nome Stringa che rappresenta il nome del progetto formativo
   * 
   * @pre nome != null
   * @pre nome.length() >= 2 and nome.length() =< 255
   * 
   * @post getNome().equals(nome)
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * Permette di ottenere la descrizione del progetto formativo.
   * 
   * @return La stringa che rappresenta la descrizione del progetto formativo
   */
  public String getDescrizione() {
    return descrizione;
  }

  /**
   * Permette di specificare la descrizione del progetto formativo.
   * 
   * @param descrizione Stringa che rappresenta la descrizione del progetto formativo
   * 
   * @pre descrizione != null
   * @pre descrizione.length() > 0
   * 
   * @post getDescrizione().equals(descrizione)
   */
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   * Permette di ottenere lo stato del progetto formativo.
   * 
   * @return {@link ProgettoFormativo#ATTIVO} se il progetto è disponibile per la selezione,
   *         {@link ProgettoFormativo#ARCHIVIATO} se invece è stato archiviato
   */
  public int getStatus() {
    return status;
  }

  /**
   * Permette di specificare lo stato del progetto formativo.
   * 
   * @param status Intero che rappresenta lo stato che si vuole assegnare al progetto formativo
   * 
   * @pre status = {@link ProgettoFormativo#ATTIVO} or
   *      status = {@link ProgettoFormativo#ARCHIVIATO}
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * Permette di ottenere l'{@link Azienda} che offre il progetto formativo.
   * 
   * @return L'oggetto {@link Azienda} che offre il progetto formativo
   */
  public Azienda getAzienda() {
    return azienda;
  }
  
  /**
   * Permette di specificare l'azienda che offre il progetto formativo. È possibile usare questo
   * metodo così come {@link Azienda#addProgettoFormativo(ProgettoFormativo)} per associare il
   * progetto all'azienda e viceversa.
   * 
   * @param azienda L'{@link Azienda} che offre il progetto formativo
   * 
   * @pre azienda != null
   */
  public void setAzienda(Azienda azienda) {
    if (!azienda.equals(this.azienda)) {
      this.azienda = azienda;
      azienda.addProgettoFormativo(this);
    }
  }
  
  /**
   * Permette di ottenere la lista delle domande di tirocinio associate al progetto formativo.
   * 
   * @return Lista delle domande di tirocinio associate al progetto formativo
   */
  public List<DomandaTirocinio> getDomandeTirocinio() {
    return domandeTirocinio;
  }
  
  /**
   * Permette di aggiungere una domanda di tirocinio alla lista di quelle associate al progetto
   * formativo.
   * 
   * @param domandaTirocinio Oggetto {@link DomandaTirocinio} che rappresenta la domanda di
   *                         tirocinio da associare al progetto formativo
   */
  public void addDomandaTirocinio(DomandaTirocinio domandaTirocinio) {
    if (!domandeTirocinio.contains(domandaTirocinio)) {
      domandeTirocinio.add(domandaTirocinio);
      domandaTirocinio.setProgettoFormativo(this);
    }
  }
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String nome;
  
  @Lob
  private String descrizione;
  private int status;
  
  @ManyToOne
  private Azienda azienda;
  
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "progettoFormativo")
  private List<DomandaTirocinio> domandeTirocinio;
  
  
  /**
   * Costante che rappresenta lo stato "attivo" di un progetto formativo.
   * Una progetto che si trova in questo stato può essere selezionato dagli studenti nelle proprie
   * domande di tirocinio.
   */
  public static final int ATTIVO = 1;
  
  /**
   * Costante che rappresenta lo stato "archiviato" di un progetto formativo.
   * Una progetto che si trova in questo stato <b>non</b> può essere selezionato dagli studenti
   * nelle proprie domande di tirocinio.
   */
  public static final int ARCHIVIATO = 2;
  
  /** Costante che definisce la minima lunghezza del campo nome. */
  public static final int MIN_LUNGHEZZA_NOME = 2;
  
  /** Costante che definisce la massima lunghezza del campo nome. */
  public static final int MAX_LUNGHEZZA_NOME = 255;
  
  /** Costante che definisce la minima lunghezza del campo descrizione. */
  public static final int MIN_LUNGHEZZA_DESCRIZIONE = 2;
  
}
