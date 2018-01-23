<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<c:set var="numeroModal" value="${param.numeroModal}" />
<c:set var="nomeProgetto" value="${param.nomeProgetto}" />
<c:set var="nomeAzienda" value="${param.nomeAzienda}" />
<c:set var="idProgetto" value="${param.idProgetto}" />


<%-- Definizione modal --%>
<div id="${param.idModal}" class="modal">
  <div class="modal-content">

		<div class="row">
		  <div class="col s12">
		    <h4>
		      <spring:message code="pagina.inviaDomandaTirocinio.titolo" />
		    </h4>
		    <p><spring:message code="domandeTirocinio.proponiti.messaggio" /></p>
		    
		    
		    <%-- Definizione form --%>
		    <form:form action="/dashboard/domande/invia"
		               method="POST"
		               modelAttribute="domandaTirocinioForm-${numeroModal}"
		               novalidate="novalidate" >
		              
		      <div class="row">
		
		        <div class="input-field col s6">
		          <i class="material-icons prefix">business_center</i>
		          <input id="progettoFormativo-nome-${numeroModal}"
		                 type="text"
		                 disabled="disabled"
		                 value="${nomeProgetto}" />
		          <label for="progettoFormativo-nome-${numeroModal}">
		            <spring:message code="progettiFormativi.nomeProgetto.label" />
		          </label>
		        </div>
		        
		        <div class="input-field col s6">
		          <i class="material-icons prefix">business</i>
		          <input id="progettoFormativo-azienda-${numeroModal}"
		                 type="text"
		                 disabled="disabled"
		                 value="${nomeAzienda}" />
		          <label for="progettoFormativo-azienda-${numeroModal}">
		            <spring:message code="progettiFormativi.aziendaProgetto.label" />
		          </label>
		        </div>
		      
		      </div>
		      
		      <div class="row date-input">
		        <div class="col s12">
		          <label class="row-label">
		            <spring:message code="domandeTirocinio.dataInizio.label" />
		          </label>
		        </div>
		        <div class="input-field col s4">
		          <i class="material-icons prefix">event</i>
		          <spring:message var="giornoLabel" code="richiestaIscrizioneForm.giornoDiNascita.label" />
		          <form:input
		            placeholder="${giornoLabel}" 
		            path="giornoInizio" id="domanda-tirocinio-${numeroModal}-giorno-inizio" />  
		        </div>
		        <div class="input-field col s5">
		          <form:select path="meseInizio" id="domanda-tirocinio-${numeroModal}-mese-inizio">
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
		                      path="annoInizio"
		                      id="domanda-tirocinio-${numeroModal}-anno-inizio" /> 
		        </div>
		        <div class="input-field col s12">
		          <form:errors path="giornoInizio" cssClass="helper-text" />
		        </div>
		      </div>
		      
		      <div class="row date-input">
		        <div class="col s12">
		          <label class="row-label">
		            <spring:message code="domandeTirocinio.dataFine.label" />
		          </label>
		        </div>
		        <div class="input-field col s4">
		          <i class="material-icons prefix">event</i>
		          <spring:message var="giornoLabel" code="richiestaIscrizioneForm.giornoDiNascita.label" />
		          <form:input
		            placeholder="${giornoLabel}" 
		            path="giornoFine" id="domanda-tirocinio-${numeroModal}-giorno-fine" />  
		        </div>
		        <div class="input-field col s5">
		          <form:select path="meseFine" id="domanda-tirocinio-${numeroModal}-mese-fine">
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
		                      path="annoFine"
		                      id="domanda-tirocinio-${numeroModal}-anno-fine" /> 
		        </div>
		        <div class="input-field col s12">
		          <form:errors path="giornoFine" cssClass="helper-text" />
		        </div>
		      </div>
		      
		      <div class="row">
		        <div class="input-field col s6">
			        <i class="material-icons prefix">school</i>
			        <form:input path="cfu"
			                    id="progettoFormativo-cfu-${numeroModal}" />
			        <label for="progettoFormativo-cfu-${numeroModal}">
			          <spring:message code="progettiFormativi.cfu.label" />
			        </label>
			        <form:errors path="cfu" cssClass="helper-text" />
			      </div>
		      </div>
		      
		      <div class="row">
		        <div class="input-field col s12">
		          <i class="material-icons prefix">sms</i>
		          <form:textarea path="commentoStudente"
		                         id="domanda-tirocinio-${numeroModal}-commento-studente"
		                         class="materialize-textarea" />
		          <label for="domanda-tirocinio-${numeroModal}-commento-studente">
		            <spring:message code="domandeTirocinio.commentoStudente.label" />
		          </label>
		          <form:errors path="commentoStudente" cssClass="helper-text" />
		        </div>
		      </div>
		      
		      <div class="row">
		        <div class="col s12 right-align">
		          <a class="btn-flat waves-effect modal-close">
		            <spring:message code="button.annulla.label" />
		          </a>
		          <form:input type="hidden" path="idProgettoFormativo" value="${idProgetto}" />
		          <form:input type="hidden" path="posizione" value="${numeroModal}" />
		          <button class="btn waves-effect waves-light" type="submit" name="action">
		            <i class="material-icons right">send</i>
		            <spring:message code="progettoFormativo.proponiti.label" />
		          </button>
		        </div>
		      </div>
		    </form:form>
		  </div>
		</div>

  </div>
</div>

<script src="/resources/js/invioDomandaTirocinio.js"></script>