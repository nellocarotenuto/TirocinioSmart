<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipCommonNominativo" code="tooltip.common.nominativo" />
<spring:message var="tooltipProgettoFormativoNome" code="tooltip.progettoFormativo.nome"/>
<spring:message var="tooltipDomandaTirocinioPeriodoTirocinio"
                code="tooltip.domandaTirocinio.periodoTirocinio" />


<%-- Definizione lista --%>
<c:forEach items="${tirociniInCorso}" var="current" varStatus="loop">


  <%-- Collassabile --%>
  <ul id="idCollapsible" class="collapsible">
    <li>
      
      
      <%-- Testata del collassabile --%>
      <div class="collapsible-header">
        <div class="col s12 valign-wrapper">
          <i class="material-icons">content_paste</i>
          <c:out value="${current.studente.nome}"/> 
          <c:out value="${current.studente.cognome}"/> - 
          <c:out value="${current.progettoFormativo.nome}" />
          (<c:out value="${current.progettoFormativo.azienda.nome}" />)
        </div>
      </div>
    
    
      <%-- Corpo del collassabile --%>
      <div class="collapsible-body">
        <div class="row">
          
          
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
                (<c:out value="${current.progettoFormativo.azienda.nome}" />)
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