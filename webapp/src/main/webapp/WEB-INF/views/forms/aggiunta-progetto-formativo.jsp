<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione etichette campi --%>
<spring:message var="formLabelNomeProgettoFormativo" code="form.label.nomeProgettoFormativo" />
<spring:message var="formLabelDescrizione" code="form.label.descrizione" />


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />
<spring:message var="buttonProgettoFormativoAggiungi" code="button.progettoFormativo.aggiungi" />


<%-- Definizione variabili header modal --%>
<spring:message var="modalTitoloAggiungiProgettoFormativo"
                code="modal.titolo.aggiungiProgettoFormativo" />
<spring:message var="modalDescrizioneAggiungiProgettoFormativo"
                code="modal.descrizione.aggiungiProgettoFormativo" />


<%-- Definizione modal --%>
<div id="${param.idModal}" class="modal">
  <div class="modal-content">
    
    <%-- Titolo del modal --%>
    <h4>
      <c:out value="${modalTitoloAggiungiProgettoFormativo}" />
    </h4>
    
    
    <%-- Descrizione modal --%>
    <p>
      <c:out value="${modalDescrizioneAggiungiProgettoFormativo}" />
    </p>
    
    
			<div class="row">
			
			
			  <%-- Definizione form --%>
				<form:form action="/dashboard/progetti/aggiungi"
				           method="POST"
				           modelAttribute="progettoFormativoForm"
				           novalidate="novalidate"
				           cssClass="col s12" >
					<div class="row">
			      
			      
			      <%-- Nome del progetto --%>
			      <div class="row">
				      <div class="input-field col s12">
				        <i class="material-icons prefix">business_center</i>
				        <form:input path="nome" id="${idModal}-nome" />
				        <label for="${idModal}-nome">
				          <c:out value="${formLabelNomeProgettoFormativo}" />
				        </label>
				        <form:errors path="nome" cssClass="helper-text" />
				      </div>
			      </div>
			      
			      
			      <%-- Descrizione del progetto --%>
			      <div class="row">
			        <div class="input-field col s12">
			          <i class="material-icons prefix"></i>
			          <form:textarea path="descrizione"
			                         id="${idModal}-descrizione"
			                         class="materialize-textarea" />
			          <label for="${idModal}-descrizione">
			            <c:out value="${formLabelDescrizione}" />
			          </label>
			          <form:errors path="descrizione" cssClass="helper-text" />
			        </div>
			      </div>
			    </div>
			    <div class="row">
			      <div class="col s12 right-align">
			        <a class="btn-flat waves-effect modal-close">
			          <c:out value="${buttonCommonAnnulla}" />
			        </a>
			        <button class="btn waves-effect waves-light" type="submit" name="action">
			          <i class="material-icons right">add</i>
			          <c:out value="${buttonProgettoFormativoAggiungi}" />
			        </button>
			      </div>
			    </div>                   
				</form:form>
			</div>
			
	</div>
</div>