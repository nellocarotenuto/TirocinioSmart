package Convenzioni;

public class Azienda {
	
	public Azienda(){
	}
	
	public String getPartitaIVA() {
		return partitaIVA;
	}
	public void setPartitaIVA(String partitaIVA) {
		this.partitaIVA = partitaIVA;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAccessibilità() {
		return accessibilità;
	}
	public void setAccessibilità(int accessibilità) {
		this.accessibilità = accessibilità;
	}

	private String partitaIVA;
	private String indirizzo;
	private String nome;
	private String id;
	private int accessibilità;
}
