package it.unisa.di.tirociniosmart.convenzioni;

import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;

import java.util.List;

/**
 * Classe che modella un'azienda convenzionata con il dipartimento.
 */
public class Azienda {

  /**
   * Costruisce un oggetto Azienda vuoto che deve essere popolata con i metodi setters.
   */
  public Azienda() {
    this.richiesta = new RichiestaConvenzionamento(this);
    this.delegato = new DelegatoAziendale(this);
  }
  
  /**
   * Determina se due oggetti rappresentano la stessa azienda guardando agli id dei suddetti.
   */
  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    
    if (object.getClass() != getClass()) {
      return false;
    }
    
    Azienda azienda = (Azienda) object;
    
    return id.equals(azienda.getId());
  }

  /**
   * Permette di ottenere l'id dell'azienda.
   * 
   * @return Stringa che rappresenta l'id dell'azienda
   */
  public String getId() {
    return id;
  }
  
  /**
   * Permette di specificare l'id dell'azienda.
   * 
   * @param id Stringa che rappresenta l'id che si vuole assegnare all'azienda.
   * 
   * @pre id != null
   * @pre id.length() >= 6 and id.length() <= 24
   * @pre id matches {@link Azienda#ID_PATTERN}
   * 
   * @post getId() = id
   */
  public void setId(String id) {
    this.id = id;
  }
  
  /**
   * Permette di ottenere la partita IVA dell'azienda.
   * 
   * @return Stringa che rappresenta la partita IVA dell'azienda
   */
  public String getPartitaIva() {
    return partitaIva;
  }
  
  /**
   * Permette di specificare la partita IVA dell'azienda.
   * 
   * @param partitaIva Stringa numerica che rappresenta la partita IVA che si vuole assegnare
   *                   all'azienda.
   * 
   * @pre partitaIva != null
   * @pre partitaIva.length() = 11
   * @pre partitaIva matches {@link Azienda#PARTITA_IVA_PATTERN}
   * 
   * @post getPartitaIva() = partitaIva
   */
  public void setPartitaIva(String partitaIva) {
    this.partitaIva = partitaIva;
  }
  
  /**
   * Permette di ottenere il nome (o ragione sociale) dell'azienda.
   * 
   * @return Stringa che rappresenta il nome dell'azienda
   */
  public String getNome() {
    return nome;
  }
  
  /**
   * Permette di specificare il nome (o ragione sociale) dell'azienda.
   * 
   * @param nome Stringa che rappresenta il nome dell'azienda
   * 
   * @pre nome != null
   * @pre nome.length() >= 2 and nome.length() <= 255
   * 
   * @post getNome() = nome
   */
  public void setNome(String nome) {
    this.nome = nome;
  }
  
  /**
   * Permette di ottenere l'indirizzo dell'azienda.
   * 
   * @return Stringa che rappresenta l'indirizzo dell'azienda
   */
  public String getIndirizzo() {
    return indirizzo;
  }
  
  /**
   * Permette di specificare l'indirizzo dell'azienda.
   * 
   * @param indirizzo Stringa che rappresenta l'indirizzo dell'azienda
   * 
   * @pre indirizzo != null
   * @pre indirizzo.length() > 0
   * 
   * @post getIndirizzo() = indirizzo
   */
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }
  
  /**
   * Permette di determinare se l'azienda sia in grado di accogliere studenti con disabilità
   * motorie o meno.
   * 
   * @return true se l'azienda può accogliere studenti con disabilità; false altrimenti
   */
  public boolean isSenzaBarriere() {
    return senzaBarriere;
  }
  
  /**
   * Permette di specificare se l'azienda sia in grado di accogliere studenti con disabilità
   * o meno.
   * 
   * @param senzaBarriere true se l'azienda può accogliere studenti con disabilità motorie;
   *                      false altrimenti
   *                      
   * @post isSenzaBarriere() = senzaBarriere
   */
  public void setSenzaBarriere(boolean senzaBarriere) {
    this.senzaBarriere = senzaBarriere;
  }
  
  /**
   * Permette di ottenere il {@link DelegatoAziendale} che rappresenta l'azienda.
   * 
   * @return L'oggetto che rappresenta il delegato aziendale che rappresenta l'azienda
   */
  public DelegatoAziendale getDelegato() {
    return delegato;
  }
  
  // TODO (1) Definire l'associazione uno a molti con i progetti formativi
  
  /**
   * Permette di ottenere la lista dei progetti formativi offerti dall'azienda.
   * 
   * @return Lista di oggetti {@link ProgettoFormativo} offerti dall'azienda
   */
  public List<ProgettoFormativo> getProgettiFormativi() {
    return progettiFormativi;
  }
  
  /**
   * Permette di ottenere la richiesta di convenzionamento associata all'azienda.
   * 
   * @return La {@link RichiestaConvenzionamento} associata all'azienda
   */
  public RichiestaConvenzionamento getRichiesta() {
    return richiesta;
  }

  private String id;
  private String partitaIva;
  private String nome;
  private String indirizzo;
  private boolean senzaBarriere;
  private DelegatoAziendale delegato;
  private List<ProgettoFormativo> progettiFormativi;
  private RichiestaConvenzionamento richiesta;
  
  
  /** Espressione regolare che definisce il formato del campo id. */
  public static final String ID_PATTERN = "^[a-zA-Z0-9]{6,24}$";
  
  /** Espressione regolare che definisce il formato del campo partita IVA. */
  public static final String PARTITA_IVA_PATTERN = "^[0-9]{11}$";
  
}
