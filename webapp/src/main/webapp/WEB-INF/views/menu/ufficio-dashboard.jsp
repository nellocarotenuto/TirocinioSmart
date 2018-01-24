<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- Voci del menù --%>
<spring:message var="voceMenuDomande" code="vmenu.dashboard.domandeTirocinioRicevute" />
<spring:message var="voceMenuIscrizioni" code="vmenu.dashboard.richiesteIscrizione" />
<spring:message var="voceMenuConvenzioni" code="vmenu.dashboard.richiesteConvenzionamento" />
<spring:message var="voceMenuTirocini" code="vmenu.dashboard.tirociniInCorso" />


<%-- Menù laterale --%>
<div class="card vertical-nav hide-on-med-and-down">
  <div class="card-content">
    <ul>
    
    
      <%-- Voce menù tirocini --%>
      <li <c:if test="${voceMenu == voceMenuTirocini}">class="active"</c:if> >
        <a href="/dashboard/tirocini"
           class="waves-effect">
          <i class="material-icons">content_paste</i>
          <c:out value="${voceMenuTirocini}" />
        </a>
      </li>
    
    
      <%-- Voce menù tirocini --%>
      <li <c:if test="${voceMenu == voceMenuDomande}">class="active"</c:if> >
        <a href="/dashboard/domande/ricevute"
           class="waves-effect">
          <i class="material-icons">work</i>
          <c:out value="${voceMenuDomande}" />
        </a>
      </li>
      
      
      <%-- Voce menù richieste d'iscrizione --%>
      <li <c:if test="${voceMenu == voceMenuIscrizioni}">class="active"</c:if> >
        <a href="/dashboard/richieste/iscrizione"
           class="waves-effect">
          <i class="material-icons">face</i>
          <c:out value="${voceMenuIscrizioni}" />
        </a>
      </li>
      
      
      <%-- Voce menù richieste di convenzionamento --%>
      <li <c:if test="${voceMenu == voceMenuConvenzioni}">class="active"</c:if> >
        <a href="/dashboard/richieste/convenzionamento"
           class="waves-effect">
          <i class="material-icons">business</i>
          <c:out value="${voceMenuConvenzioni}" />
        </a>
      </li>
      
      
    </ul>
  </div>
</div>