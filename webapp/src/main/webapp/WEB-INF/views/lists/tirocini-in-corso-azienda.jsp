<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipCommonNominativo" code="tooltip.common.nominativo" />
<spring:message var="tooltipCommonEmail" code="tooltip.common.email" />
<spring:message var="tooltipCommonTelefono" code="tooltip.common.telefono" />
<spring:message var="tooltipProgettoFormativoNome" code="tooltip.progettoFormativo.nome"/>
<spring:message var="tooltipDomandaTirocinioPeriodoTirocinio"
                code="tooltip.domandaTirocinio.periodoTirocinio" />

<%-- Definizione lista --%>
<c:forEach items="${tirociniInCorso}" var="current" varStatus="loop">


	<%-- Collassabile --%>
	<ul class="collapsible">
	  <li>
	    
	    
	    <%-- Testata del collassabile --%>
	    <div class="collapsible-header">
	      <div class="col s8 valign-wrapper">
	        <i class="material-icons">content_paste</i>
	        <c:out value="${current.studente.nome}"/> 
	        <c:out value="${current.studente.cognome}"/> - 
	        <c:out value="${current.progettoFormativo.nome}" />
	      </div>
	      <div class="col s4 right-align">
	        <span class="right-align"><tags:localDateTime date="${current.data}"/></span>
	      </div>
	    </div>
	  
	  
	    <%-- Corpo del collassabile --%>
	    <div class="collapsible-body grey lighten-4">
	      <div class="row">
	      
	      
	        <%-- Nome e cognome dello studente --%>
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

            
          <%-- E-mail dello studente --%>
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
                <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="${tooltipCommonEmail}" >
                   <i class="small material-icons">email</i>
                </a>
              </div>
              <div class="col s11">
                <c:out value="${current.studente.email}" />
              </div>
            </div>
          </div>
          
          
          <%-- Contatto telefonico dello studente --%>
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
                <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="${tooltipCommonTelefono}" >
                   <i class="small material-icons">phone</i>
                </a>
              </div>
              <div class="col s11">
                <c:out value="${current.studente.telefono}" />
              </div>
            </div>
          </div>
          
          
          <%-- Progetto formativo --%>
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
            
	      </div>
	    </div>
	    
	    
	  </li>
	</ul>
	
	
</c:forEach>