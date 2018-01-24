<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili header modal --%>
<spring:message var="modalTitoloRifiutaRichiestaConvenzionamento"
                code="modal.titolo.rifiutaRichiestaConvenzionamento" />
<spring:message var="modalDescrizioneRifiutaRichiestaConvenzionamento"
                code="modal.descrizione.rifiutaRichiestaConvenzionamento" />


<%-- Definizione etichette campi del form --%>
<spring:message var="formLabelMotivazione" code="form.label.motivazione" />


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />
<spring:message var="buttonCommonRifiuta" code="button.common.rifiuta" />


<%-- Definizione modal --%>
<div id="${param.idModal}" class="modal">
  <div class="modal-content">
  
  
    <%-- Testata del modal --%>
		<div class="row">
		  <div class="col s12">
		    
		    
		    <%-- Titolo del modal --%>
		    <h4>
			    <c:out value="${modalTitoloRifiutaRichiestaConvenzionamento}" />
			  </h4>
			  
			  
			  <%-- Descrizione del modal --%>
			  <p>
			    <c:out value="${modalDescrizioneRifiutaRichiestaConvenzionamento}" />
			  </p>
			  
			  
			  <%-- Definizione form --%>
			  <form action="/dashboard/richieste/convenzionamento/rifiuta"
			        method="POST">
			    
			    <div class="row">
		        <div class="input-field col s12">
		          <i class="material-icons prefix">feedback</i>
		          <textarea id="commento-${param.idRichiesta}"
		                    name="commentoRichiesta" 
		                    class="materialize-textarea"></textarea>
		          <label for="commento-${param.idRichiesta}">
		            <c:out value="${formLabelMotivazione}" />
		          </label>
		        </div>
		      </div>
			    
			    <div class="row">
			      <div class="col s12 right-align">
			        <a class="btn-flat waves-effect modal-close">
		            <c:out value="${buttonCommonAnnulla}" />
		          </a>
			        <input type="hidden" name="idRichiesta" value="${param.idRichiesta}">
				      <button class="btn waves-effect waves-light red" type="submit" name="action">
				        <c:out value="${buttonCommonRifiuta}" />
				        <i class="material-icons right">clear</i>
				      </button>
			      </div>
			    </div>
			  </form>
			  
			  
		  </div>
		</div>
		
		
  </div>
</div>