<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili etichette tab --%>
<spring:message var="tabNavbarHome" code="tab.navbar.home" />
<spring:message var="tabNavbarAziende" code="tab.navbar.aziende" />
<spring:message var="tabNavbarDashboard" code="tab.navbar.dashboard" />


<%-- Titolo da mostrare accanto al logo --%>
<spring:message var="sitoTitolo" code="sito.titolo" />


<%-- Voci del menù --%>
<spring:message var="voceMenuDomande" code="vmenu.dashboard.domandeTirocinioRicevute" />
<spring:message var="voceMenuIscrizioni" code="vmenu.dashboard.richiesteIscrizione" />
<spring:message var="voceMenuConvenzioni" code="vmenu.dashboard.richiesteConvenzionamento" />
<spring:message var="voceMenuTirocini" code="vmenu.dashboard.tirociniInCorso" />
<spring:message var="voceMenuDomandeInviate" code="vmenu.dashboard.domandeTirocinioInviate" />
<spring:message var="voceMenuProgetti" code="vmenu.dashboard.progettiFormativi" />


<%-- Etichette pulsanti --%>
<spring:message var="buttonLogoutLabel" code="button.common.logout" />
<spring:message var="buttonLoginLabel" code="button.common.login" />
<spring:message var="buttonRegistrazioneLabel" code="button.common.registrazione" />


<%-- Definizione variabile testo notifica [NON PUÒ CONTENERE SINGOLI O DOPPI APICI] --%>
<spring:message var="testoNotifica" code="${testoNotifica}" />


<%-- Inizio documento HTML --%>
<!DOCTYPE html>
<html>


  <%-- Inizio head HTML --%>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    
    
    <%-- Definizione titolo della pagina --%>
    <title>
      <c:out value="${titoloPagina}"/> - TirocinioSmart
    </title>
    
    
    <%-- Inclusione pacchetto di icone --%>
    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons" />"
          rel="stylesheet" >
    
    
    <%-- Inclusione fogli di stile --%>
    <link type="text/css"
          rel="stylesheet"
          href="<c:url value="/resources/css/materialize.min.css" />"
          media="screen,projection" />
    <link type="text/css"
          rel="stylesheet"
          href="<c:url value="/resources/css/style.css" />"
          media="screen,projection" />


    <%-- Definizione viewport per il mobile --%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    
    
    <%-- Inclusione script di MaterializeCSS e JQuery --%>
    <script type="text/javascript"
            src="<c:url value="/resources/js/jquery-3.2.1.min.js" />" ></script>
    <script type="text/javascript"
            src="<c:url value="/resources/js/materialize.min.js" />" ></script>
    
    
    <%-- Inclusione script per la visualizzazione della notifica toast --%>
    <script type="text/javascript"
            src="<c:url value="/resources/js/toast.js" />" ></script>
  </head>
  
  
  <%-- Inizio body HTML --%>
  <body class="grey lighten-3" onload="mostraToast('<c:out value="${testoNotifica}" />')" >
  
  
    <%-- Definizione header con navbar --%>
    <nav id="navbar" class="nav-extended">
	    <div class="row">
		    <div class="nav-wrapper">
			    <div class="col s12">
			      
			      
			      <%-- Link con logo e nome del sito --%>
			      <a href="/" class="brand-logo">
			        <img src="<c:url value="/resources/images/logos/logo.png" />" alt="Logo" />
			        <c:out value="${sitoTitolo}" />
			      </a>
			      
			      
			      <%-- Pulsante apertura sidebar --%>
			      <a href="#" data-target="mobile" class="sidenav-trigger">
			        <i class="material-icons">menu</i>
			      </a>
			      
			      
			      <%-- Sidebar --%>
			      <ul id="nav-mobile" class="right hide-on-med-and-down">
			        <c:choose>
			          
			          
			          <%-- Aggiunta del nominativo se l'utente è autenticato (con tasto di logout) --%>
			          <c:when test="${not empty utente}">
                  <li>
                    <c:out value="${utente.nome}"/>&nbsp;<c:out value="${utente.cognome}"/>
                  </li>
                  <li>
                    <a class="white-text waves-effect waves-light btn-flat modal-trigger"
                       href="/logout">
                      <c:out value="${buttonLogoutLabel}" />
                    </a>
                  </li>
                </c:when>
                
                
                <%-- Aggiunta dei tasti di login e registrazione per utenti non autenticati --%>
                <c:otherwise>
		              <li>
		                <a class="white-text waves-effect waves-light btn-flat modal-trigger"
		                   href="#login">
		                  <c:out value="${buttonLoginLabel}" />
		                </a>
		              </li>
		              <li>
		                <a class="white-text waves-effect waves-light btn-flat"
		                   href="/registrazione"
		                   style="margin: 0;" >
		                  <c:out value="${buttonRegistrazioneLabel}" />
		                </a>
		              </li>
                </c:otherwise>
			        </c:choose>
			      </ul>
			      
			      
			    </div>
		    </div>
	    </div>
	    
	    
	    <%-- Tab di navigazione --%>
	    <div class="nav-content hide-on-med-and-down">
	      <ul class="tabs tabs-transparent">
	        
	        
	        <%-- Tab home page --%>
	        <li class="tab">
	          <a <c:if test="${titoloTab == tabNavbarHome}">class="active"</c:if>
	             href="/">
	            <c:out value="${tabNavbarHome}" />
	          </a>
	        </li>
	        
	        
	        <%-- Tab aziende --%>
	        <li class="tab">
	          <a
	            <c:if test="${titoloTab == tabNavbarAziende}">class="active"</c:if>
	             href="/aziende" >
	            <c:out value="${tabNavbarAziende}" />
	          </a>
	        </li>
	        
	        
	        <%-- Tab dashboard [solo se l'utente è autenticato] --%>
	        <c:if test="${not empty utente}">
	          <li class="tab">
	            <a <c:if test="${titoloTab == tabNavbarDashboard}">class="active"</c:if>
	               href="/dashboard" >
	              <c:out value="${tabNavbarDashboard}" />
	            </a>
	          </li>
	        </c:if>
	        
	        
	      </ul>
	    </div>
	  </nav>
	  
	  
		<%-- Sidebar --%>
		 <ul class="sidenav" id="mobile">
		 
		 <li>
		   <div class="user-view">
		     
		     
		     <c:choose>
		       
		       
		       <%-- Se l'utente è autenticato, mostrane il nome e il tasto di logout --%>
		       <c:when test="${not empty utente}">
		         <li>
		           <a href="#" class="name">
		             <c:out value="${utente.nome}"/>&nbsp;<c:out value="${utente.cognome}"/>
		           </a>
		         </li>
		         <li>
		           <a class="waves-effect" href="/logout">
		             <c:out value="${buttonLogoutLabel}" />
		           </a>
		         </li>
		       </c:when>
		       
		       
		       <%-- Se l'utente non è autenticato, mostra i tasti di login e registrazione --%>
		       <c:when test="${empty utente}" >
		         <li>
			         <a href="#login" class="modal-trigger waves-effect">
			           <c:out value="${buttonLoginLabel}" />
			         </a>
			       </li>
			       <li>
			         <a href="/registrazione" class="waves-effect">
			           <c:out value="${buttonRegistrazioneLabel}" />
			         </a>
			       </li>
		       </c:when>
		       
		       
		     </c:choose>
		     

		   </div>
		 </li>
		 
     <li>
       <div class="divider"></div>
     </li>
     
     <%-- Voce home page --%>
     <li <c:if test="${titoloTab == tabNavbarHome}">class="active"</c:if> >
       <a href="/" class="waves-effect">
         <c:out value="${tabNavbarHome}" />
       </a>
     </li>
     
     
     <%-- Voce aziende --%>
     <li <c:if test="${titoloTab == tabNavbarAziende}">class="active"</c:if> >
       <a href="/aziende" class="waves-effect">
         <c:out value="${tabNavbarAziende}" />
       </a>
     </li>
		 
		 
		 <%-- Tab dashboard [solo se l'utente è autenticato] --%>
     <c:if test="${not empty utente}">
       <li class="no-padding <c:if test="${titoloTab == tabNavbarDashboard}">active</c:if>" >
         <ul class="collapsible collapsible-accordion">
           <li>
           
             <a class="collapsible-header">
	             <c:out value="${tabNavbarDashboard}" />
	           </a>
             <div class="collapsible-body">
               <ul>
                 
                 
                 <%-- Voce tirocini in corso --%>
                 <li <c:if test="${voceMenu == voceMenuTirocini}">class="active"</c:if> >
					         <a href="/dashboard/tirocini"
					            class="waves-effect">
					           <c:out value="${voceMenuTirocini}" />
					         </a>
					       </li>
					       
					       
					       <%-- Voce menù tirocini --%>
					       <c:if test="${utente.getClass().getSimpleName() == 'ImpiegatoUfficioTirocini'
					                     or utente.getClass().getSimpleName() == 'DelegatoAziendale'}" >
						       <li <c:if test="${voceMenu == voceMenuDomande}">class="active"</c:if> >
	                   <a href="/dashboard/domande/ricevute"
	                      class="waves-effect">
	                     <c:out value="${voceMenuDomande}" />
	                   </a>
	                 </li>
					       </c:if>
					       
					       
					      <%-- Voce menù domande inviate --%>
								<c:if test="${utente.getClass().getSimpleName() == 'DelegatoAziendale'
                              or utente.getClass().getSimpleName() == 'Studente'}" >
									<li <c:if test="${voceMenu == voceMenuDomandeInviate}">class="active"</c:if> >
									  <a href="/dashboard/domande/inviate"
									     class="waves-effect">
									    <c:out value="${voceMenuDomandeInviate}" />
									  </a>
									</li>
							  </c:if>
							  
							  
							  <%-- Voce menù domande inviate --%>
                <c:if test="${utente.getClass().getSimpleName() == 'DelegatoAziendale'}" >
                  <li <c:if test="${voceMenu == voceMenuProgetti}">class="active"</c:if> >
                    <a href="/dashboard/progetti"
                       class="waves-effect">
                      <c:out value="${voceMenuProgetti}" />
                    </a>
                  </li>
                </c:if>
      
      
					      <%-- Voce menù richieste d'iscrizione --%>
					      <c:if test="${utente.getClass().getSimpleName() == 'ImpiegatoUfficioTirocini'}" >
						      <li <c:if test="${voceMenu == voceMenuIscrizioni}">class="active"</c:if> >
						        <a href="/dashboard/richieste/iscrizione"
						           class="waves-effect">
						          <c:out value="${voceMenuIscrizioni}" />
						        </a>
						      </li>
					      </c:if>
					      
					      
					      <%-- Voce menù richieste di convenzionamento --%>
					      <c:if test="${utente.getClass().getSimpleName() == 'ImpiegatoUfficioTirocini'}" >
						      <li <c:if test="${voceMenu == voceMenuConvenzioni}">class="active"</c:if> >
						        <a href="/dashboard/richieste/convenzionamento"
						           class="waves-effect">
						          <c:out value="${voceMenuConvenzioni}" />
						        </a>
						      </li>
					      </c:if>
      
      
               </ul>
             </div>
             
             
           </li>
         </ul>
       </li>
     </c:if>
		 
		   
		 </ul>
	  
	  
	  <%-- Inclusione del modal di login --%>
    <jsp:include page="/WEB-INF/views/forms/login.jsp" >
      <jsp:param value="login" name="idModalLogin" />
    </jsp:include>

	  
	  <%-- Script per l'inizializzazione e la validazione del form di login --%>
		<script type="text/javascript"
		        src="<c:url value="/resources/js/header.js" />" ></script>