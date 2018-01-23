<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabile header modal --%>
<spring:message var="modalTitoloLogin" code="modal.titolo.login" />
<spring:message var="modalDescrizioneLogin" code="modal.descrizione.login" />


<%-- Etichette campi form --%>
<spring:message var="formLabelUsername" code="form.label.username" />
<spring:message var="formLabelPassword" code="form.label.password" />


<%-- Etichette dei pulsanti --%>
<spring:message var="buttonCommonLogin" code="button.common.login" />
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />


<%-- ID dei componenti HTML --%>
<c:set var="idModalLogin" value="${param.idModalLogin}" />


<%-- Definizione modal di login --%>
<div id="${idModalLogin}" class="modal">
  <div class="modal-content">
    
    
    <%-- Titolo del modal --%>
    <h4>
      <c:out value="${modalTitoloLogin}" />
    </h4>
    
    <p>
      <c:out value="${modalDescrizioneLogin}" />
    </p>


    <%-- Corpo del modal --%>
		<div class="row">
		
		
		  <%-- Form di login --%>
		  <form:form action="/login"
		             method="POST"
		             modelAttribute="loginForm"
		             novalidate="novalidate"
		             cssClass="col s12" >
		    <div class="row">
		      
		      
		      <%-- Campo username --%>
		      <div class="row">
			      <div class="input-field col s12">
			        <i class="material-icons prefix">account_circle</i>
			        <form:input path="username" id="${idModalLogin}-username" />
			        <label for="${idModalLogin}-username">
			          <c:out value="${formLabelUsername}" />
			        </label>
			        <form:errors path="username" cssClass="helper-text" />
			      </div>
		      </div>
		      
		      
		      <%-- Campo password --%>
		      <div class="row">
		        <div class="input-field col s12">
		          <i class="material-icons prefix">vpn_key</i>
		          <form:password path="password" id="login-password" />
		          <label for="login-password">
		            <c:out value="${formLabelPassword}" />
		          </label>
		          <form:errors path="password" cssClass="helper-text" />
		        </div>
		      </div>
		    </div>
		    
		    
		    <%-- Tasti di invio del form e uscita dal modal --%>
		    <div class="row">
		      <div class="col s12 right-align">
		        <a class="btn-flat waves-effect modal-close">
		          <c:out value="${buttonCommonAnnulla}" />
		        </a>
		        <button class="btn waves-effect waves-light" type="submit" name="action">
		          <c:out value="${buttonCommonLogin}" />
		          <i class="material-icons right">send</i>
		        </button>
		      </div>
		    </div>
		    
		    
		  </form:form>
		  
		  
		</div>


  </div>
</div>