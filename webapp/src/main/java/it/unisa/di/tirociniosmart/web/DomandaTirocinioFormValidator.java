package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.domandetirocinio.CommentoDomandaTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.domandetirocinio.DataDiFineTirocinioNonValidaException;
import it.unisa.di.tirociniosmart.domandetirocinio.DataDiInizioTirocinioNonValidaException;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandeTirocinioService;
import it.unisa.di.tirociniosmart.domandetirocinio.NumeroCfuNonValidoException;
import it.unisa.di.tirociniosmart.domandetirocinio.ProgettoFormativoArchiviatoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


/**
 * Classe che definisce un validatore per {@link ConvenzionamentoForm} tramite i servizi offerti da
 * {@link UtenzaService} e {@link ConvenzioniService}. Il controllo viene effettuato su tutti i
 * campi del form.
 *
 * @see ConvenzionamentoForm
 * @see ConvenzioniService
 * @see UtenzaService
 */
@Component
public class DomandaTirocinioFormValidator {

  @Autowired
  private DomandeTirocinioService domandeService;
  
  /**
   * Permette di definire le classi cui il validatore è applicabile.
   */
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
  public void validate(Object target, Errors errors) {
    DomandaTirocinioForm form = (DomandaTirocinioForm) target;
    
    try {
      domandeService.validaCfu(form.getCfu());
    } catch (NumeroCfuNonValidoException e) {
      errors.rejectValue("cfu", "domandaTirocinioForm.cfu.nonValido");
    }
   
    try {
      domandeService.validaDataDiInizioTirocinio(form.getInizioTirocinio(),
                                                 form.getFineTirocinio());
    } catch (DataDiInizioTirocinioNonValidaException e) {
      errors.rejectValue("dataInizio", "domandaTirocinioForm.dataInizio.nonValida");
    }
  
    try {
      domandeService.validaDataDiFineTirocinio(form.getInizioTirocinio(), form.getFineTirocinio());
    } catch (DataDiFineTirocinioNonValidaException e) {
      errors.rejectValue("dataFine", "domandaTirocinioForm.dataFine.nonValida");
    }
    
    try {
      domandeService.validaCommento(form.getCommentoStudente());
    } catch (CommentoDomandaTirocinioNonValidoException e) {
      errors.rejectValue("commento", "domandaTirocinioForm.commento.nonValido");
    }
    
    try {
      domandeService.verificaStatoProgettoFormativo(form.getProgetto());
    } catch (ProgettoFormativoArchiviatoException e) {
      errors.rejectValue("progetto", "domandaTirocinioForm.progettoFormattivo.archiviato");
    }

  }

}
