<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Etichette campi form --%>
<spring:message var="formLabelNomeProgettoFormativo" code="form.label.nomeProgettoFormativo" />
<spring:message var="formLabelNomeAzienda" code="from.label.nomeAzienda" />
<spring:message var="formLabelDataInizio" code="form.label.dataInizio" />
<spring:message var="formLabelDataFine" code="form.label.dataFine" />
<spring:message var="formLabelCfu" code="form.label.cfu" />
<spring:message var="formLabelCommento" code="form.label.commento" />

<spring:message var="formPlaceholderGiorno" code="form.placeholder.giorno" />
<spring:message var="formPlaceholderMese" code="form.placeholder.mese" />
<spring:message var="formPlaceholderAnno" code="form.placeholder.anno" />


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />
<spring:message var="buttonDomandaTirocinioProponiti" code="button.domandaTirocinio.proponiti" />


<%-- Definizione variabile header modal --%>
<spring:message var="modalTitoloInviaDomandaTirocinio"
                code="modal.titolo.inviaDomandaTirocinio" />
<spring:message var="modalDescrizioneInviaDomandaTirocinio"
                code="modal.descrizione.inviaDomandaTirocinio" />


<%-- Definizione modal --%>
<div id="${param.idModal}" class="modal">
  <div class="modal-content">

		<div class="row">
		  <div class="col s12">
		    <h4>
		      <c:out value="${modalTitoloInviaDomandaTirocinio}" />
		    </h4>
		    <p>
		      <c:out value="${modalDescrizioneInviaDomandaTirocinio}" />
		    </p>
		    
		    
		    <%-- Definizione form --%>
		    <form:form action="/dashboard/domande/invia"
		               method="POST"
		               modelAttribute="domandaTirocinioForm-${param.numeroModal}"
		               novalidate="novalidate" >
		              
		      <div class="row">
		
		        <div class="input-field col s6">
		          <i class="material-icons prefix">business_center</i>
		          <input id="progetto-formativo-nome-${param.numeroModal}"
		                 type="text"
		                 disabled="disabled"
		                 value="${param.nomeProgetto}" />
		          <label for="progetto-formativo-nome-${param.numeroModal}">
		            <c:out value="${formLabelNomeProgettoFormativo}" />
		          </label>
		        </div>
		        
		        <div class="input-field col s6">
		          <i class="material-icons prefix">business</i>
		          <input id="progetto-formativo-azienda-${param.numeroModal}"
		                 type="text"
		                 disabled="disabled"
		                 value="${param.nomeAzienda}" />
		          <label for="progetto-formativo-azienda-${param.numeroModal}">
		            <c:out value="${formLabelNomeAzienda}" />
		          </label>
		        </div>
		      
		      </div>
		      
		      <div class="row date-input">
		        <div class="col s12">
		          <label class="row-label">
		            <c:out value="${formLabelDataInizio}" />
		          </label>
		        </div>
		        <div class="input-field col s4">
		          <i class="material-icons prefix">event</i>
		          <form:input
		            placeholder="${formPlaceholderGiorno}" 
		            path="giornoInizio" id="domanda-tirocinio-${param.numeroModal}-giorno-inizio" />  
		        </div>
		        <div class="input-field col s5">
		          <form:select path="meseInizio" id="domanda-tirocinio-${param.numeroModal}-mese-inizio">
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
		                      path="annoInizio"
		                      id="domanda-tirocinio-${param.numeroModal}-anno-inizio" /> 
		        </div>
		        <div class="input-field col s12">
		          <form:errors path="giornoInizio" cssClass="helper-text" />
		        </div>
		      </div>
		      
		      <div class="row date-input">
		        <div class="col s12">
		          <label class="row-label">
		            <c:out value="${formLabelDataFine}" />
		          </label>
		        </div>
		        <div class="input-field col s4">
		          <i class="material-icons prefix">event</i>
		          <form:input
		            placeholder="${formPlaceholderGiorno}" 
		            path="giornoFine" id="domanda-tirocinio-${param.numeroModal}-giorno-fine" />  
		        </div>
		        <div class="input-field col s5">
		          <form:select path="meseFine" id="domanda-tirocinio-${param.numeroModal}-mese-fine">
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
		                      path="annoFine"
		                      id="domanda-tirocinio-${param.numeroModal}-anno-fine" /> 
		        </div>
		        <div class="input-field col s12">
		          <form:errors path="giornoFine" cssClass="helper-text" />
		        </div>
		      </div>
		      
		      <div class="row">
		        <div class="input-field col s4 m6">
			        <i class="material-icons prefix">school</i>
			        <form:input path="cfu"
			                    id="progettoFormativo-cfu-${param.numeroModal}" />
			        <label for="progettoFormativo-cfu-${param.numeroModal}">
			          <c:out value="${formLabelCfu}" />
			        </label>
			        <form:errors path="cfu" cssClass="helper-text" />
			      </div>
		      </div>
		      
		      <div class="row">
		        <div class="input-field col s12">
		          <i class="material-icons prefix">sms</i>
		          <form:textarea path="commentoStudente"
		                         id="domanda-tirocinio-${param.numeroModal}-commento-studente"
		                         class="materialize-textarea" />
		          <label for="domanda-tirocinio-${param.numeroModal}-commento-studente">
		            <c:out value="${formLabelCommento}" />
		          </label>
		          <form:errors path="commentoStudente" cssClass="helper-text" />
		        </div>
		      </div>
		      
		      <div class="row">
		        <div class="col s12 right-align">
		          <a class="btn-flat waves-effect modal-close">
		            <c:out value="${buttonCommonAnnulla}" />
		          </a>
		          <form:input type="hidden" path="idProgettoFormativo" value="${param.idProgetto}" />
		          <form:input type="hidden" path="posizione" value="${param.numeroModal}" />
		          <button class="btn waves-effect waves-light" type="submit" name="action">
		            <i class="material-icons right">send</i>
		            <c:out value="${buttonDomandaTirocinioProponiti}" />
		          </button>
		        </div>
		      </div>
		    </form:form>
		  </div>
		</div>

  </div>
</div>


<script src="/resources/js/invioDomandaTirocinio.js"></script>