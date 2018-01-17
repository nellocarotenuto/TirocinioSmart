<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloPagina" scope="request" code="pagina.registrazione.titolo" />

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main class="container">
	<div class="row">
	  <div class="col s12">
	    <div class="card">
	      <div class="card-content">
	        <span class="card-title">
	          <spring:message code="pagina.registrazione.titolo" />
	        </span>
	      </div>
	      <div class="card-tabs">
	        <ul id="selettore-form-iscrizione" class="tabs tabs-fixed-width">
	          <li class="tab">
	            <a href="#studente">
	              <spring:message code="tab.registrazione.studente" />
	            </a>
	          </li>
	          <li class="tab">
	            <a href="#azienda">
	              <spring:message code="tab.registrazione.azienda" />
	            </a>
	          </li>
	        </ul>
	      </div>
	      <div class="card-content grey lighten-5">
	        <div id="studente"><jsp:include page ="/WEB-INF/views/forms/iscrizione.jsp" /></div>
	        <div id="azienda"><jsp:include page="/WEB-INF/views/forms/convenzione.jsp" /></div>
	      </div>
	    </div>
	  </div>
	</div>
</main>

<!-- Script per l'inizializzazione e la validazione dei form -->
<script type="text/javascript"
        src="<c:url value="/resources/js/registrazione.js" />" ></script>
    
<jsp:include page="/WEB-INF/views/common/footer.jsp" />