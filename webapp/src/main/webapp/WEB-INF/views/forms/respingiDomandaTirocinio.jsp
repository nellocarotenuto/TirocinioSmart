<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="row">
  <div class="col s12">
    <h4>
	    <spring:message code="pagina.respingiDomandaTirocinio.titolo" />
	  </h4>
	  <p><spring:message code="domandaTirocinio.respingi.messaggio" /></p>
	  <form action="/dashboard/domande/respingi"
	        method="POST">
	    
	    <div class="row">
        <div class="input-field col s12">
          <i class="material-icons prefix">create</i>
          <textarea id="commento-${param.idDomanda}"
                    name="commentoDomanda" 
                    class="materialize-textarea"></textarea>
          <label for="commento-${param.idDomanda}">
            <spring:message code="domandaTirocinio.motivazione.label" />
          </label>
        </div>
      </div>
	    
	    <div class="row">
	      <div class="col s12 right-align">
	        <a class="btn-flat waves-effect modal-close">
            <spring:message code="button.annulla.label" />
          </a>
	        <input type="hidden" name="idDomanda" value="${param.idDomanda}">
		      <button class="btn waves-effect waves-light red" type="submit" name="action">
		        <spring:message code="form.respingi.label" />
		      </button>
	      </div>
	    </div>
	  </form>
  </div>
</div>