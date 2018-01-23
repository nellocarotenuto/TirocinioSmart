<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
         import="it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%-- Definizione variabili stato domande --%>
<c:set var="statoDomandaAccettata" value="<%= DomandaTirocinio.ACCETTATA %>" />
<c:set var="statoDomandaApprovata" value="<%= DomandaTirocinio.APPROVATA %>" />
<c:set var="statoDomandaRespinta" value="<%= DomandaTirocinio.RESPINTA %>" />


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipProgettoFormativoNome" code="tooltip.progettoFormativo.nome"/>
<spring:message var="tooltipCommonNominativo" code="tooltip.common.nominativo" />
<spring:message var="tooltipCommonDataDiNascita" code="tooltip.common.dataDiNascita" />
<spring:message var="tooltipCommonSesso" code="tooltip.common.sesso" />
<spring:message var="tooltipCommonEmail" code="tooltip.common.email" />
<spring:message var="tooltipCommonTelefono" code="tooltip.common.telefono" />

<spring:message var="tooltipDomandaTirocinioCommentoStudente"
                code="tooltip.domandaTirocinio.commentoStudente" />
<spring:message var="tooltipDomandaTirocinioPeriodoTirocinio"
                code="tooltip.domandaTirocinio.periodoTirocinio" />


<%-- Definizione etichette sesso --%>
<spring:message var="labelSessoMaschile" code="label.sesso.maschile" />
<spring:message var="labelSessoFemminile" code="label.sesso.femminile" />


<%-- Definizione etichette badges --%>
<spring:message var="badgeDomandaTirocinioAccettata" code="badge.domandaTirocinio.accettata" />
<spring:message var="badgeDomandaTirocinioApprovata" code="badge.domandaTirocinio.approvata" />
<spring:message var="badgeDomandaTirocinioRespinta" code="badge.domandaTirocinio.respinta" />


<%-- Lista delle domande di tirocinio inviate --%>
<c:forEach items="${elencoDomandeInviate}" var="current" varStatus="loop">
  <ul class="collapsible">
    <li>
      
      
      <%-- Testata del collassabile --%>
      <div class="collapsible-header">
        
        
        <%-- Nominativo di studente e progetto formativo --%>
        <div class="col s8 valign-wrapper">
          <i class="material-icons">work</i>
          <c:out value="${current.studente.nome}"/> 
          <c:out value="${current.studente.cognome}"/> - 
          <c:out value="${current.progettoFormativo.nome}" />
        </div>
        
        
        <%-- Data ultima modifica e stato domanda --%>
        <div class="col s4 right-align">
	        <span class="right-align"><tags:localDateTime date="${current.data}"/></span>
	        <c:choose>
	          <c:when test="${current.status == statoDomandaAccettata}">
	            <span class="new badge yellow"
	                  data-badge-caption="<c:out value="${badgeDomandaTirocinioAccettata}" />" >
	            </span>
	          </c:when>
	          <c:when test="${current.status == statoDomandaApprovata}">
	            <span class="new badge green"
	                  data-badge-caption="<c:out value="${badgeDomandaTirocinioAccettata}" />" >
	            </span>
	          </c:when>
	          <c:when test="${current.status == statoDomandaRespinta}">
	            <span class="new badge red"
	                  data-badge-caption="<c:out value="${badgeDomandaTirocinioAccettata}" />" >
	            </span>
	          </c:when>
	        </c:choose>
	        
	        
        </div>
        
        
      </div>
    
    
      <%-- Corpo del collassabile --%>
      <div class="collapsible-body">
        <div class="row">
        
        
	        <%-- Nominativo dello studente --%>
	        <div class="col s12">
		        <div class="row valign-wrapper" >
		          <div class="col s1">
		            <a class="tooltipped tooltipped-icon" 
		               data-position="right"
		               data-delay="50"
		               data-tooltip="${tooltipCommonNominativo}">
		               <i class ="small material-icons">face</i>
		            </a>      
		          </div>
		          <div class="col s11">
		            <c:out value="${current.studente.nome}" />  
		            <c:out value="${current.studente.cognome}" />
		          </div>
		        </div>
	        </div>
	        
	        
	        <%-- Data di nascita dello studente --%>
	        <div class="col s12">
		        <div class="row valign-wrapper" >
		          <div class="col s1">
		            <a class="tooltipped tooltipped-icon" 
		               data-position="right"
		               data-delay="50"
		               data-tooltip="${tooltipCommonDataDiNascita}">
		               <i class ="small material-icons">cake</i>
		            </a>      
		          </div>
		          <div class="col s11">
		            <tags:localDate date="${current.studente.dataDiNascita}"/>
		          </div>
		        </div>
	        </div>
	          
	          
	        <%-- Sesso dello studente --%>
	        <div class="col s12">
		        <div class="row valign-wrapper" >
		          <div class="col s1">
		            <a class="tooltipped tooltipped-icon" 
		               data-position="right"
		               data-delay="50"
		               data-tooltip="${tooltipCommonSesso}">
		               <i class ="small material-icons">wc</i>
		            </a>      
		          </div>
		          <div class="col s11">
		            <c:choose>
		              <c:when test="${current.studente.sesso == 'M'}">
		                <c:out value="${labelSessoMaschile}" />
		              </c:when>
		              <c:when test="${current.studente.sesso == 'F'}">
		                <c:out value="${labelSessoFemminile}" />
		              </c:when>
		            </c:choose>
		          </div>
		        </div>
	        </div>
	          
	          
	        <%-- E-mail dello studente --%>
	        <div class="col s12">
	          <div class="row valign-wrapper">
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon"
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="<c:out value="${tooltipCommonEmail}"/>">
	                 <i class="small material-icons">email</i>
	              </a>
	            </div>
	            <div class="col s11">
	              <c:out value="${current.studente.email}" />
	            </div>
	          </div>
	        </div>
	        
	        
	        <%-- Telefono dello studente --%>
	        <div class="col s12">
	          <div class="row valign-wrapper">
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon"
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="<c:out value="${tooltipCommonTelefono}"/>">
	                 <i class="small material-icons">phone</i>
	              </a>
	            </div>
	            <div class="col s11">
	              <c:out value="${current.studente.telefono}" />
	            </div>
	          </div>
	        </div>
	        
	        
	        <%-- Nome del progetto formativo --%>
	        <div class="col s12">
	          <div class="row valign-wrapper" >
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipProgettoFormativoNome}">
	                 <i class ="small material-icons">business_center</i>
	              </a>      
	            </div>
	            <div class="col s11">
	              <c:out value="${current.progettoFormativo.nome}" />
	            </div>
	          </div>
	        </div>
	          
	        
	        <%-- Periodo di tirocinio --%>
	        <div class="col s12">
	          <div class="row valign-wrapper" >
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioPeriodoTirocinio}">
	                <i class ="small material-icons">event</i>
	              </a>      
	            </div>
	            <div class="col s11">
	              <tags:localDate date="${current.inizioTirocinio}"/> - 
	              <tags:localDate date="${current.fineTirocinio}"/>
	            </div>
	          </div>
	        </div>
	          
	        
	        <%-- Commento dello studente --%>
	        <div class="col s12">
	          <div class="row valign-wrapper">
	            <div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioCommentoStudente}">
	                 <i class="small material-icons">sms</i>
	              </a>
	            </div>
	            <div class="col s11">
	              <c:out value="${current.commentoStudente}"/>
	            </div>
	          </div>
	        </div>
          
          
        </div>
      </div>
      
      
    </li>
  </ul>
</c:forEach>

<!-- Script per l'inizializzazione e la validazione dei form -->
<script type="text/javascript"
        src="<c:url value="/resources/js/domandeTirocinio.js" />" ></script>