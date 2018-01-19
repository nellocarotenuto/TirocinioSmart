<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloTabHome" code="tab.home.titolo" />
<spring:message var="titoloTabAziende" code="tab.aziende.titolo" />

<spring:message var="testoNotifica" code="${testoNotifica}" />

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    
    <!-- Definisci il titolo della pagina -->
    <title>
      <c:out value="${titoloPagina}"/>
    </title>
    
    <!-- Importa le icone -->
    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons" />"
          rel="stylesheet" >
    
    <!-- Importa i fogli di stile -->
    <link type="text/css"
          rel="stylesheet"
          href="<c:url value="/resources/css/materialize.min.css" />"
          media="screen,projection" />
          
    <link type="text/css"
          rel="stylesheet"
          href="<c:url value="/resources/css/style.css" />"
          media="screen,projection" />

    <!-- Imposta la viewport per abilitare la versione mobile -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    
    <!-- JavaScript -->
    <script type="text/javascript"
            src="<c:url value="/resources/js/jquery-3.2.1.min.js" />" ></script>
            
    <script type="text/javascript"
            src="<c:url value="/resources/js/materialize.min.js" />" ></script>
            
    <script type="text/javascript"
            src="<c:url value="/resources/js/toast.js" />" ></script>
  </head>
  <body class="grey lighten-3" onload="mostraToast('<c:out value="${testoNotifica}" />')" >
  
    <nav id="navbar" class="nav-extended">
	    <div class="row">
		    <div class="nav-wrapper">
			    <div class="col s12">
			      <a href="/" class="brand-logo">
			        <img src="<c:url value="/resources/images/logos/logo.png" />" alt="Logo" />
			        <spring:message code="navbar.titolo.tirocinioSmart" />
			      </a>
			      <a href="#" data-target="mobile-demo" class="sidenav-trigger">
			        <i class="material-icons">menu</i>
			      </a>
			      <ul id="nav-mobile" class="right hide-on-med-and-down">
			        <c:choose>
			          <c:when test="${not empty utente}">
                  <li>
                    <c:out value="${utente.nome}"/> <c:out value="${utente.cognome}"/>
                  </li>
                  <li>
                    <a class="white-text waves-effect waves-light btn-flat modal-trigger" href="/logout">
                      <spring:message code="pagina.logout.titolo" />
                    </a>
                  </li>
                </c:when>
                <c:otherwise>
		              <li>
		                <a class="white-text waves-effect waves-light btn-flat modal-trigger" href="#login">
		                  <spring:message code="pagina.login.titolo" />
		                </a>
		              </li>
		              <li>
		                <a class="white-text waves-effect waves-light btn-flat"
		                   href="/registrazione"
		                   style="margin: 0;" >
		                  <spring:message code="pagina.registrazione.titolo" />
		                </a>
		              </li>
                </c:otherwise>
			        </c:choose>
			      </ul>
			      <ul class="sidenav" id="mobile-demo">
			        <li>
			          <a href="#login modal-trigger">
			            <spring:message code="pagina.login.titolo" />
			          </a>
			        </li>
			        <li>
			          <a href="/registrazione">
			            <spring:message code="pagina.registrazione.titolo" />
			          </a>
			        </li>
			      </ul>
			    </div>
		    </div>
	    </div>
	    <div class="nav-content">
	      <ul class="tabs tabs-transparent">
	        <li class="tab">
	          <a <c:if test="${titoloTab == titoloTabHome}">class="active"</c:if>
	             href="/">
	            <spring:message code="tab.home.titolo" />
	          </a>
	        </li>
	        <li class="tab">
	          <a <c:if test="${titoloTab == titoloTabAziende}">class="active"</c:if>
	             href="/aziende" >
	            <spring:message code="tab.aziende.titolo" />
	          </a>
	        </li>
	      </ul>
	    </div>
	  </nav>
	  
	  <div id="login" class="modal">
	    <div class="modal-content">
	      <h4>
          <spring:message code="pagina.login.titolo" />
        </h4>
	      <jsp:include page="/WEB-INF/views/forms/login.jsp" />
	    </div>
	  </div>
	  
	  <!-- Script per l'inizializzazione e la validazione del form di login -->
		<script type="text/javascript"
		        src="<c:url value="/resources/js/header.js" />" ></script>