<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="row">
  <div class="col s12">
    <spring:message code="richiestaIscrizioneForm.introduzione.label" />
  </div>
</div>
<div class="row">
	<form:form action="/registrazione/studente"
	           method="POST"
	           modelAttribute="richiestaIscrizioneForm"
	           novalidate="novalidate"
	           cssClass="col s12" >
    <div class="row">
      <div class="col s12 l6">
        <div class="row">
          <div class="col s12">
	          <h5>
	            <spring:message code="registrazioneForm.credenziali.label"/>
	          </h5>
	          <div class="divider">
	          </div>
          </div>
        </div>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">account_circle</i>
						<form:input path="username" id="iscrizione-username" />
						<label for="iscrizione-username">
						  <spring:message code="registrazioneForm.username.label" />
						</label>
						<form:errors path="username" cssClass="helper-text" />
		      </div>
		    </div>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">email</i>
            <form:input path="email" id="iscrizione-email" type="email"/>
            <label for="iscrizione-email">
              <spring:message code="registrazioneForm.email.label" />
            </label>
            <form:errors path="email" cssClass="helper-text" />
		      </div>
		    </div>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">vpn_key</i>
            <form:password path="password" id="iscrizione-password"/>
            <label for="iscrizione-password">
              <spring:message code="registrazioneForm.password.label" />
            </label>
            <form:errors path="password" cssClass="helper-text" />
		      </div>
		    </div>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:password path="confermaPassword" id="iscrizione-confermaPassword" />
            <label for="iscrizione-confermaPassword">
              <spring:message code="registrazioneForm.confermaPassword.label" />
            </label>
            <form:errors path="confermaPassword" cssClass="helper-text" />
		      </div>
		    </div>
      </div>
      <div class="col s12 l6">
        <div class="row">
          <div class="col s12">
            <h5>
              <spring:message code="richiestaIscrizioneForm.infoStudente.label"/>
            </h5>
            <div class="divider"></div>
          </div>
        </div>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">face</i>
            <form:input path="nome" id="iscrizione-nome" />
            <label for="iscrizione-nome">
              <spring:message code="registrazioneForm.nome.label" />
            </label>
            <form:errors path="nome" cssClass="helper-text" />
		      </div>
        </div>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:input path="cognome" id="iscrizione-cognome" />
            <label for="iscrizione-cognome">
              <spring:message code="registrazioneForm.cognome.label" />
            </label>
            <form:errors path="cognome" cssClass="helper-text" />
		      </div>
        </div>
        <div class="row date-input">
          <div class="col s12">
            <label class="row-label">
              <spring:message code="richiestaIscrizioneForm.dataDiNascita.label" />
            </label>
          </div>
          <div class="input-field col s4">
            <i class="material-icons prefix">cake</i>
            <spring:message var="giornoLabel" code="richiestaIscrizioneForm.giornoDiNascita.label" />
            <form:input
              placeholder="${giornoLabel}" 
              path="giornoDiNascita" id="iscrizione-giornoDiNascita" />  
          </div>
          <div class="input-field col s5">
            <form:select path="meseDiNascita" id="iscrizione-meseDiNascita">
              <form:option value="" disabled="true" selected="selected">
                <spring:message code="form.select.segnaposto"/>
              </form:option>
              <form:option value="1">
                <spring:message code="form.mese.gennaio"/>
              </form:option>
              <form:option value="2">
                <spring:message code="form.mese.febbraio"/>
              </form:option>
              <form:option value="3">
                <spring:message code="form.mese.marzo"/>
              </form:option>
              <form:option value="4">
                <spring:message code="form.mese.aprile"/>
              </form:option>
              <form:option value="5">
                <spring:message code="form.mese.maggio"/>
              </form:option>
              <form:option value="6">
                <spring:message code="form.mese.giugno"/>
              </form:option>
              <form:option value="7">
                <spring:message code="form.mese.luglio"/>
              </form:option>
              <form:option value="8">
                <spring:message code="form.mese.agosto"/>
              </form:option>
              <form:option value="9">
                <spring:message code="form.mese.settembre"/>
              </form:option>
              <form:option value="10">
               <spring:message code="form.mese.ottobre"/>
              </form:option>
              <form:option value="11">
               <spring:message code="form.mese.novembre"/>
              </form:option>
              <form:option value="12">
               <spring:message code="form.mese.dicembre"/>
              </form:option>
            </form:select>
          </div>
          <div class="input-field col s3">
            <spring:message var="annoLabel" code="richiestaIscrizioneForm.annoDiNascita.label" />
            <form:input placeholder="${annoLabel}"
                        path="annoDiNascita"
                        id="iscrizione-annoDiNascita" /> 
          </div>
          <div class="input-field col s12">
            <form:errors path="giornoDiNascita" cssClass="helper-text" />
          </div>
        </div>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">wc</i>
            <form:select path="sesso" id="iscrizione-sesso">
              <form:option value="" disabled="true" selected="selected">
                <spring:message code="form.select.segnaposto"/>
              </form:option>
				      <form:option value="M">
				        <spring:message code="registrazioneForm.sesso.maschile"/>
				      </form:option>
				      <form:option value="F">
				        <spring:message code="registrazioneForm.sesso.femminile"/>
				      </form:option>
				    </form:select>
				    <label>
				      <spring:message code="registrazioneForm.sesso.label"/>
				    </label>
				    <form:errors path="sesso" cssClass="helper-text" />
		      </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">location_city</i>
            <form:input path="indirizzoStudente" id="iscrizione-indirizzo" />
            <label for=iscrizione-indirizzo>
              <spring:message code="richiestaIscrizioneForm.indirizzoStudente.label" />
            </label>
            <form:errors path="indirizzoStudente" cssClass="helper-text" />
				  </div>
				</div>
				<div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">phone</i>
            <form:input path="telefono" id="registrazioneForm-telefono" />
            <label for=registrazioneForm-telefono>
              <spring:message code="registrazioneForm.telefono.label" />
            </label>
            <form:errors path="telefono" cssClass="helper-text" />
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix"></i>
	          <form:input path="matricola" id="iscrizione-matricola" />
	          <label for="iscrizione-matricola">
	            <spring:message code="richiestaIscrizioneForm.matricola.label" />
	          </label>
	          <form:errors path="matricola" cssClass="helper-text" />
          </div>
        </div>  
      </div>
    </div>
    <div class="row">
      <div class="col s12 right-align">
        <button class="btn waves-effect waves-light" type="submit" name="action">
          <spring:message code="form.invia.label" />
          <i class="material-icons right">send</i>
        </button>
      </div>
    </div>
	</form:form>
</div>