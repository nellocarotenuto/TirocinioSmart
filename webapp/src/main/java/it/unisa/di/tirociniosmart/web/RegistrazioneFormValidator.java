package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.utenza.CognomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.EmailEsistenteException;
import it.unisa.di.tirociniosmart.utenza.EmailNonValidaException;
import it.unisa.di.tirociniosmart.utenza.NomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.PasswordNonValidaException;
import it.unisa.di.tirociniosmart.utenza.UsernameEsistenteException;
import it.unisa.di.tirociniosmart.utenza.UsernameNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Classe che definisce un validatore per {@link RegistrazioneForm} tramite i servizi offerti da
 * {@link UtenzaService}. I controlli effettuati riguardano la validità di nome, cognome, password, 
 * email ed username: per gli ultimi due è anche controllata l'unicità all'interno del sistema.
 * La validazione della password prevede anche il controllo di congruità con il campo
 * {@link RegistrazioneForm#confermaPassword}.
 *
 * @see RegistrazioneForm
 * @see UtenzaService
 */
@Component
public class RegistrazioneFormValidator implements Validator {

  @Autowired
  private UtenzaService utenzaService;
  
  /**
   * Permette di definire le classi cui il validatore è applicabile.
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return RegistrazioneForm.class.isAssignableFrom(clazz);
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
    RegistrazioneForm form = (RegistrazioneForm) target;
    
    try {
      utenzaService.validaUsername(form.getUsername());
    } catch (UsernameNonValidoException e) {
      errors.rejectValue("username", "registrazioneForm.username.nonValido");
    } catch (UsernameEsistenteException e) {
      errors.rejectValue("username", "registrazioneForm.username.esistente");
    }
    
    try {
      utenzaService.validaPassword(form.getPassword());
      
      if (!form.getPassword().equals(form.getConfermaPassword())) {
        errors.rejectValue("confermaPassword", "registrazioneForm.confermaPassword.nonCoincidenti");
      }
    } catch (PasswordNonValidaException e) {
      errors.rejectValue("password", "registrazioneForm.password.nonValida");
    }
    
    try {
      utenzaService.validaEmail(form.getEmail());
    } catch (EmailNonValidaException e) {
      errors.rejectValue("email", "registrazioneForm.email.nonValida");
    } catch (EmailEsistenteException e) {
      errors.rejectValue("email", "registrazioneForm.email.esistente");
    }
    
    try {
      utenzaService.validaNome(form.getNome());
    } catch (NomeNonValidoException e) {
      errors.rejectValue("nome", "registrazioneForm.nome.nonValido");
    }
    
    try {
      utenzaService.validaCognome(form.getCognome());
    } catch (CognomeNonValidoException e) {
      errors.rejectValue("cognome", "registrazioneForm.cognome.nonValido");
    }
    
  }

}
