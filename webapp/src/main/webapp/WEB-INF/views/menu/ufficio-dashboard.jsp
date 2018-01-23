<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="voceMenuDomande" code="vmenu.domandeTirocinio.titolo" />
<spring:message var="voceMenuIscrizioni" code="vmenu.richiesteIscrizione.titolo" />
<spring:message var="voceMenuConvenzioni" code="vmenu.richiesteConvenzionamento.titolo" />

<div class="card vertical-nav hide-on-med-and-down">
  <div class="card-content">
    <ul>
      <li <c:if test="${voceMenu == voceMenuDomande}">class="active"</c:if> >
        <a href="/dashboard/domande"
           class="waves-effect">
          <i class="material-icons">work</i>
          <spring:message code="vmenu.domandeTirocinio.titolo" />
        </a>
      </li>
      <li <c:if test="${voceMenu == voceMenuIscrizioni}">class="active"</c:if> >
        <a href="/dashboard/richieste/iscrizione"
           class="waves-effect">
          <i class="material-icons">face</i>
          <spring:message code="vmenu.richiesteIscrizione.titolo" />
        </a>
      </li>
      <li <c:if test="${voceMenu == voceMenuConvenzioni}">class="active"</c:if> >
        <a href="/dashboard/richieste/convenzionamento"
           class="waves-effect">
          <i class="material-icons">business</i>
          <spring:message code="vmenu.richiesteConvenzionamento.titolo" />
        </a>
      </li>
    </ul>
  </div>
</div>