<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="row">
  <div class="col s12">
    <h4>
	    <spring:message code="pagina.rifiutaRichiestaConvenzionamento.titolo" />
	  </h4>
	  <p><spring:message code="richiesteConvenzionamento.rifiuta.messaggio" /></p>
	  <form action="/dashboard/richieste/convenzionamento/rifiuta"
	        method="POST">
	    
	    <div class="row">
        <div class="input-field col s12">
          <i class="material-icons prefix">create</i>
          <textarea id="commento-${param.idRichiesta}"
                    name="commentoRichiesta" 
                    class="materialize-textarea"></textarea>
          <label for="commento-${param.idRichiesta}">
            <spring:message code="richiestaConvenzionamento.motivazione.label" />
          </label>
        </div>
      </div>
	    
	    <div class="row">
	      <div class="col s12 right-align">
	        <input type="hidden" name="idRichiesta" value="${param.idRichiesta}">
		      <button class="btn waves-effect waves-light red" type="submit" name="action">
		        <spring:message code="form.rifiuta.label" />
		      </button>
	      </div>
	    </div>
	  </form>
  </div>
</div>