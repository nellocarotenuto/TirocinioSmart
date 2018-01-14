<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloPaginaHome" code="pagina.home.titolo" />
<spring:message var="titoloPaginaAziende" code="pagina.aziende.titolo" />

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    
    <!-- Definisci il titolo della pagina -->
    <title>
      <c:out value="${param.titoloPagina}"/>
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
  </head>
  <body class="grey lighten-3">
  
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
			        <li>
			          <a class="white-text waves-effect waves-light btn-flat" href="/login">
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
			      </ul>
			      <ul class="sidenav" id="mobile-demo">
			        <li>
			          <a href="/login">
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
	          <a <c:if test="${param.titoloPagina == titoloPaginaHome}">class="active"</c:if>
	             href="/">
	            <spring:message code="pagina.home.titolo" />
	          </a>
	        </li>
	        <li class="tab">
	          <a <c:if test="${param.titoloPagina == titoloPaginaAziende}">class="active"</c:if>
	             href="/aziende" >
	            <spring:message code="pagina.aziende.titolo" />
	          </a>
	        </li>
	      </ul>
	    </div>
	  </nav>