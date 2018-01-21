<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="row">
	<form:form action="/dashboard/progetti/aggiungi"
	           method="POST"
	           modelAttribute="progettoFormativoForm"
	           novalidate="novalidate"
	           cssClass="col s12" >
		<div class="row">
      <div class="row">
	      <div class="input-field col s12">
	        <i class="material-icons prefix">business_center</i>
	        <form:input path="nome" id="aggiungiProgettoFormativo-nome" />
	        <label for="aggiungiProgettoFormativo-nome">
	          <spring:message code="progettoFormativo.nome.label" />
	        </label>
	        <form:errors path="nome" cssClass="helper-text" />
	      </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <i class="material-icons prefix"></i>
          <textarea id="aggiungiProgettoFormativo-commento"
                    name="commento" 
                    class="materialize-textarea"></textarea>
          <label for="aggiungiProgettoFormativo-commento">
            <spring:message code="progettoFormativo.descrizione.label" />
          </label>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col s12 right-align">
        <a class="btn-flat waves-effect modal-close">
          <spring:message code="button.annulla.label" />
        </a>
        <button class="btn waves-effect waves-light" type="submit" name="action">
          <spring:message code="button.progettoFormativo.aggiungi.label" />
          <i class="material-icons right">add</i>
        </button>
      </div>
    </div>                   
	</form:form>
</div>