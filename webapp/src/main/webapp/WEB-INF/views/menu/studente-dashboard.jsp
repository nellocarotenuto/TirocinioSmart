<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- Voci del menù --%>
<spring:message var="voceMenuDomandeInviate" code="vmenu.dashboard.domandeTirocinioInviate" />
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
      
      
      <%-- Voce menù domande inviate --%>
      <li <c:if test="${voceMenu == voceMenuDomandeInviate}">class="active"</c:if> >
        <a href="/dashboard/domande/inviate"
           class="waves-effect">
          <i class="material-icons">call_made</i>
          <c:out value="${voceMenuDomandeInviate}" />
        </a>
      </li>
      
      
    </ul>
  </div>
</div>