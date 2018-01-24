<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Etichette pagina --%>
<spring:message var="formIscrizioneDescrizione" code="form.iscrizione.descrizione" />
<spring:message var="formRegistrazioneCredenziali" code="form.registrazione.credenziali" />
<spring:message var="formIscrizioneAnagrafica" code="form.iscrizione.anagrafica" />


<%-- Etichette campi --%>
<spring:message var="formLabelUsername" code="form.label.username" />
<spring:message var="formLabelEmail" code="form.label.email" />
<spring:message var="formLabelPassword" code="form.label.password" />
<spring:message var="formLabelConfermaPassword" code="form.label.confermaPassword" />
<spring:message var="formLabelNome" code="form.label.nome" />
<spring:message var="formLabelCognome" code="form.label.cognome" />
<spring:message var="formLabelDataDiNascita" code="form.label.dataDiNascita" />
<spring:message var="formLabelSesso" code="form.label.sesso" />
<spring:message var="formLabelIndirizzo" code="form.label.indirizzo" />
<spring:message var="formLabelTelefono" code="form.label.telefono" />
<spring:message var="formLabelMatricola" code="form.label.matricola" />

<spring:message var="formPlaceholderGiorno" code="form.placeholder.giorno" />
<spring:message var="formPlaceholderMese" code="form.placeholder.mese" />
<spring:message var="formPlaceholderAnno" code="form.placeholder.anno" />
<spring:message var="formPlaceholderSesso" code="form.placeholder.sesso" />


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonInvia" code="button.common.invia" />


<%-- Preambolo --%>
<div class="row">
  
  
  <%-- Descrizione del form --%>
  <div class="col s12">
    <c:out value="${formIscrizioneDescrizione}" />
  </div>
  
  
</div>


<%-- Corpo della pagina --%>
<div class="row">

  
  <%-- Form d'iscrizione --%>
	<form:form action="/registrazione/studente"
	           method="POST"
	           modelAttribute="richiestaIscrizioneForm"
	           novalidate="novalidate"
	           cssClass="col s12" >
    <div class="row">
      <div class="col s12 l6">
      
      
        <%-- Intestazione sezione credenziali --%>
        <div class="row heading">
          <div class="col s12">
	          <h5>
	            <c:out value="${formRegistrazioneCredenziali}" />
	          </h5>
	          <div class="divider">
	          </div>
          </div>
        </div>
        
        
        <%-- Username --%>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">account_circle</i>
						<form:input path="username" id="iscrizione-username" />
						<label for="iscrizione-username">
						  <c:out value="${formLabelUsername}" />
						</label>
						<form:errors path="username" cssClass="helper-text" />
		      </div>
		    </div>
		    
		    
		    <%-- E-mail --%>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">email</i>
            <form:input path="email" id="iscrizione-email" type="email"/>
            <label for="iscrizione-email">
              <c:out value="${formLabelEmail}" />
            </label>
            <form:errors path="email" cssClass="helper-text" />
		      </div>
		    </div>
		    
		    
		    <%-- Password --%>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">vpn_key</i>
            <form:password path="password" id="iscrizione-password"/>
            <label for="iscrizione-password">
              <c:out value="${formLabelPassword}" />
            </label>
            <form:errors path="password" cssClass="helper-text" />
		      </div>
		    </div>
		    
		    
		    <%-- Conferma password --%>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:password path="confermaPassword" id="iscrizione-conferma-password" />
            <label for="iscrizione-conferma-password">
              <c:out value="${formLabelConfermaPassword}" />
            </label>
            <form:errors path="confermaPassword" cssClass="helper-text" />
		      </div>
		    </div>
		    
		    
      </div>
      <div class="col s12 l6">
      
      
        <%-- Intestazione sezioni informazioni anagrafiche --%>
        <div class="row heading">
          <div class="col s12">
            <h5>
              <c:out value="${formIscrizioneAnagrafica}" />
            </h5>
            <div class="divider"></div>
          </div>
        </div>
        
        
        <%-- Nome --%>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">face</i>
            <form:input path="nome" id="iscrizione-nome" />
            <label for="iscrizione-nome">
              <c:out value="${formLabelNome}" />
            </label>
            <form:errors path="nome" cssClass="helper-text" />
		      </div>
        </div>
        
        
        <%-- Cognome --%>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:input path="cognome" id="iscrizione-cognome" />
            <label for="iscrizione-cognome">
              <c:out value="${formLabelCognome}" />
            </label>
            <form:errors path="cognome" cssClass="helper-text" />
		      </div>
        </div>
        
        
        <%-- Data di nascita --%>
        <div class="row date-input">
          <div class="col s12">
            <label class="row-label">
              <c:out value="${formLabelDataDiNascita}" />
            </label>
          </div>
          <div class="input-field col s4">
            <i class="material-icons prefix">cake</i>
            <form:input
              placeholder="${formPlaceholderGiorno}" 
              path="giornoDiNascita" id="iscrizione-giornoDiNascita" />  
          </div>
          <div class="input-field col s5">
            <form:select path="meseDiNascita" id="iscrizione-meseDiNascita">
              <form:option value="" disabled="true" selected="selected">
                <c:out value="${formPlaceholderMese}" />
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
            <form:input placeholder="${formPlaceholderAnno}"
                        path="annoDiNascita"
                        id="iscrizione-annoDiNascita" /> 
          </div>
          <div class="input-field col s12">
            <form:errors path="giornoDiNascita" cssClass="helper-text" />
          </div>
        </div>
        
        
        <%-- Sesso --%>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">wc</i>
            <form:select path="sesso" id="iscrizione-sesso">
              <form:option value="" disabled="true" selected="selected">
                <c:out value="${formPlaceholderSesso}" />
              </form:option>
				      <form:option value="M">
				        <spring:message code="form.sesso.maschile"/>
				      </form:option>
				      <form:option value="F">
				        <spring:message code="form.sesso.femminile"/>
				      </form:option>
				    </form:select>
				    <label>
				      <c:out value="${formLabelSesso}" />
				    </label>
				    <form:errors path="sesso" cssClass="helper-text" />
		      </div>
        </div>
        
        
        <%-- Indirizzo --%>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">location_city</i>
            <form:input path="indirizzoStudente" id="iscrizione-indirizzo" />
            <label for=iscrizione-indirizzo>
              <c:out value="${formLabelIndirizzo}" />
            </label>
            <form:errors path="indirizzoStudente" cssClass="helper-text" />
				  </div>
				</div>
				
				
				<%-- Telefono --%>
				<div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">phone</i>
            <form:input path="telefono" id="registrazioneForm-telefono" />
            <label for=registrazioneForm-telefono>
              <c:out value="${formLabelTelefono}" />
            </label>
            <form:errors path="telefono" cssClass="helper-text" />
          </div>
        </div>
        
        
        <%-- Matricola --%>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">assignment_ind</i>
	          <form:input path="matricola" id="iscrizione-matricola" />
	          <label for="iscrizione-matricola">
	            <c:out value="${formLabelMatricola}" />
	          </label>
	          <form:errors path="matricola" cssClass="helper-text" />
          </div>
        </div>  
        
        
      </div>
    </div>
    
    
    <%-- Pulsante d'invio --%>
    <div class="row">
      <div class="col s12 right-align">
        <button class="btn waves-effect waves-light" type="submit" name="action">
          <c:out value="${buttonCommonInvia}" />
          <i class="material-icons right">send</i>
        </button>
      </div>
    </div>
    
    
	</form:form>
</div>