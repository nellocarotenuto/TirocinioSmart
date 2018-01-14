package it.unisa.di.tirociniosmart.web;

import it.unisa.di.tirociniosmart.convenzioni.ConvenzioniService;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaEsistenteException;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.IndirizzoAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.NomeAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.PartitaIvaAziendaEsistenteException;
import it.unisa.di.tirociniosmart.convenzioni.PartitaIvaAziendaNonValidaException;
import it.unisa.di.tirociniosmart.utenza.SessoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.TelefonoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

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
public class ConvenzionamentoFormValidator extends RegistrazioneFormValidator {

  @Autowired
  private ConvenzioniService convenzioniService;
  
  @Autowired
  private UtenzaService utenzaService;
  
  /**
   * Permette di definire le classi cui il validatore è applicabile.
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return ConvenzionamentoForm.class.isAssignableFrom(clazz);
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
    ConvenzionamentoForm form = (ConvenzionamentoForm) target;
    
    super.validate(target, errors);
    
    try {
      convenzioniService.validaIdAzienda(form.getIdAzienda());
    } catch (IdAziendaNonValidoException e) {
      errors.rejectValue("idAzienda", "convenzionamentoForm.idAzienda.nonValido");
    } catch (IdAziendaEsistenteException e) {
      errors.rejectValue("idAzienda", "convenzionamentoForm.idAzienda.esistente");
    }
    
    try {
      convenzioniService.validaPartitaIvaAzienda(form.getPartitaIvaAzienda());
    } catch (PartitaIvaAziendaNonValidaException e) {
      errors.rejectValue("partitaIvaAzienda", "convenzionamentoForm.partitaIvaAzienda.nonValida");
    } catch (PartitaIvaAziendaEsistenteException e) {
      errors.rejectValue("partitaIvaAzienda", "convenzionamentoForm.partitaIvaAzienda.esistente");
    }
    
    try {
      convenzioniService.validaNomeAzienda(form.getNomeAzienda());
    } catch (NomeAziendaNonValidoException e) {
      errors.rejectValue("nomeAzienda", "convenzionamentoForm.nomeAzienda.nonValido");
    }
    
    try {
      convenzioniService.validaIndirizzoAzienda(form.getIndirizzoAzienda());
    } catch (IndirizzoAziendaNonValidoException e) {
      errors.rejectValue("indirizzoAzienda", "convenzionamentoForm.indirizzoAzienda.nonValido");
    }
    
    try {
      utenzaService.validaSesso(form.getSesso());
    } catch (SessoNonValidoException e) {
      errors.rejectValue("sesso", "registrazioneForm.sesso.nonValido");
    }
    
    try {
      utenzaService.validaTelefono(form.getTelefono());
    } catch (TelefonoNonValidoException e) {
      errors.rejectValue("telefono", "registrazioneForm.telefono.nonValido");
    }
  }

}
