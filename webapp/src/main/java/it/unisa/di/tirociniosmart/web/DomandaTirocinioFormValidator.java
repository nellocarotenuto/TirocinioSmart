package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.domandetirocinio.CommentoDomandaTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.domandetirocinio.DataDiFineTirocinioNonValidaException;
import it.unisa.di.tirociniosmart.domandetirocinio.DataDiInizioTirocinioNonValidaException;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandeTirocinioService;
import it.unisa.di.tirociniosmart.domandetirocinio.NumeroCfuNonValidoException;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * Classe che definisce un validatore per {@link DomandaTirocinioForm} tramite i servizi offerti da
 * {@link DomandeTirocinioService}. Il controllo viene effettuato su tutti i campi del form.
 *
 * @see DomandaTirocinioForm
 * @see DomandeTirocinioService
 */
@Component
public class DomandaTirocinioFormValidator implements Validator {

  @Autowired
  private DomandeTirocinioService domandeService;
  
  /**
   * Permette di definire le classi cui il validatore è applicabile.
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return DomandaTirocinioForm.class.isAssignableFrom(clazz);
  }
  
  /**
   * Effettua la validazione dell'oggetto target riportando gli errori nell'oggetto errors.
   * 
   * @param target Oggetto da validare
   * 
   * @param errors Oggetto in cui salvare l'esito della validazione
   */
  @Override
  public void validate(Object target, Errors errors) {
    DomandaTirocinioForm form = (DomandaTirocinioForm) target;
    
    try {
      domandeService.validaCfu(form.getCfu());
    } catch (NumeroCfuNonValidoException e) {
      errors.rejectValue("cfu", "domandaTirocinioForm.cfu.nonValido");
    }
   
    try {
      if (form.getGiornoInizio() == null
          || form.getMeseInizio() == null
          || form.getAnnoInizio() == null) {
        throw new DataDiInizioTirocinioNonValidaException();
      }
      
      if (form.getGiornoFine() == null 
          || form.getMeseFine() == null 
          || form.getAnnoFine() == null) {
        throw new DataDiFineTirocinioNonValidaException();
      }
      
      // Crea oggetti LocalDate per la validazione
      LocalDate dataInizio;
      LocalDate dateFine;
      
      // Cerca di popolare la data d'inizio a partire dagli interi presenti nel form
      try {
        dataInizio = LocalDate.of(form.getAnnoInizio(),
                                  form.getMeseInizio(), 
                                  form.getGiornoInizio());
      } catch (DateTimeException e) {
        throw new DataDiInizioTirocinioNonValidaException();
      }
      
      // Cerca di popolare la data di fine a partire dagli interi presenti nel form
      try { 
        dateFine = LocalDate.of(form.getAnnoFine(), 
                                form.getMeseFine(),
                                form.getGiornoFine());
      } catch (DateTimeException e) {
        throw new DataDiFineTirocinioNonValidaException();
      }
      
      // Controlla che rispettino i vincoli (quella di fine segua quella d'inizio)
      domandeService.validaDataDiInizioTirocinio(dataInizio, dateFine);
      domandeService.validaDataDiFineTirocinio(dataInizio, dateFine);
      
    } catch (DataDiInizioTirocinioNonValidaException e) {
      errors.rejectValue("giornoInizio", "domandaTirocinioForm.dataInizio.nonValida");
      errors.rejectValue("meseInizio", "domandaTirocinioForm.dataInizio.nonValida");
      errors.rejectValue("annoInizio", "domandaTirocinioForm.dataInizio.nonValida");
    } catch (DataDiFineTirocinioNonValidaException e) {
      errors.rejectValue("giornoFine", "domandaTirocinioForm.dataFine.nonValida");
      errors.rejectValue("meseFine", "domandaTirocinioForm.dataFine.nonValida");
      errors.rejectValue("annoFine", "domandaTirocinioForm.dataFine.nonValida");
    }
    
    try {
      domandeService.validaCommento(form.getCommentoStudente());
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      errors.rejectValue("commentoStudente", "domandaTirocinioForm.commento.nonValido");
    }

  }

}
