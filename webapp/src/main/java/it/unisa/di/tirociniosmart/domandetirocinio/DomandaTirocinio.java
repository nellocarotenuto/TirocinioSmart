package DomandeTirocinio;

import java.time.LocalDateTime;

import ProgettiFormativi.ProgettoFormativo;
import Studenti.Studente;

public class DomandaTirocinio {

	public DomandaTirocinio() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public LocalDateTime getInizioTirocinio() {
		return inizioTirocinio;
	}
	public void setInizioTirocinio(LocalDateTime inizioTirocinio) {
		this.inizioTirocinio = inizioTirocinio;
	}
	public LocalDateTime getFineTirocinio() {
		return fineTirocinio;
	}
	public void setFineTirocinio(LocalDateTime fineTirocinio) {
		this.fineTirocinio = fineTirocinio;
	}
	public String getCommentoAzienda() {
		return commentoAzienda;
	}
	public void setCommentoAzienda(String commentoAzienda) {
		this.commentoAzienda = commentoAzienda;
	}
	public String getCommentoStudente() {
		return commentoStudente;
	}
	public void setCommentoStudente(String commentoStudente) {
		this.commentoStudente = commentoStudente;
	}
	public int getCfu() {
		return cfu;
	}
	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
	public Studente getStudente() {
		return studente;
	}
	public void setStudente(Studente studente) {
		this.studente = studente;
	}
	public ProgettoFormativo getProgettoFormativo() {
		return progettoFormativo;
	}
	public void setProgettoFormativo(ProgettoFormativo progettoFormativo) {
		this.progettoFormativo = progettoFormativo;
	}

	private int id;
	private int status;
	private LocalDateTime data;
	private LocalDateTime inizioTirocinio;
	private LocalDateTime fineTirocinio;
	private String commentoAzienda;
	private String commentoStudente;
	private int cfu;
	private Studente studente;
	private ProgettoFormativo progettoFormativo;

}
