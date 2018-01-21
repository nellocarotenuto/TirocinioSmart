package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.progettiformativi.DescrizioneProgettoNonValidaException;
import it.unisa.di.tirociniosmart.progettiformativi.NomeProgettoNonValidoException;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettiFormativiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Classe che definisce un validatore per {@link ProgettoFormativoForm} tramite i servizi offerti da
 * {@link ProgettiFormativiService}. Il controllo viene effettuato su tutti i
 * campi del form.
 *
 * @see ProgettoFormativoForm
 * @see ProgettiFormativiService
 * 
 */
@Component
public class ProgettoFormativoFormValidator implements Validator {
  
  @Autowired
  private ProgettiFormativiService progettiFormativiService;
  
  
  /**
   * Permette di definire le classi cui il validatore è applicabile.
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return ProgettoFormativoForm.class.isAssignableFrom(clazz);
  }
  
  /**
   * Effettua la validazione dell'oggetto target riportando gli errori
   * nell'oggetto errors.
   * 
   * @param target Oggetto da validare
   * 
   * @param errors Oggetto in cui salvare l'esito della validazione
   */
  @Override
  public void validate(Object target, Errors errors) {
    ProgettoFormativoForm form = (ProgettoFormativoForm) target;
    
    try {
      progettiFormativiService.validaNome(form.getNome());
    } catch (NomeProgettoNonValidoException e) {
      errors.rejectValue("nome", "toast.progettiFormativi.nomeNonValido");
    } 
    
    try {
      progettiFormativiService.validaDescrizione(form.getDescrizione());
    } catch (DescrizioneProgettoNonValidaException e) {
      errors.rejectValue("descrizione", 
                         "toast.progettiFormativi.descrizioneNonValida");
    } 
    
  }
  
}
