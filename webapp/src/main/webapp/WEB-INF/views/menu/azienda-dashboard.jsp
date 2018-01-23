<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="voceMenuDomandeRicevute" code="vmenu.domandeTirocinio.ricevute" />
<spring:message var="voceMenuDomandeInviate" code="vmenu.domandeTirocinio.inviate" />
<spring:message var="voceMenuProgetti" code="vmenu.progettiFormativi.titolo" />
<spring:message var="voceMenuTirocini" code="vmenu.tirocini.titolo" />

<div class="card vertical-nav hide-on-med-and-down">
  <div class="card-content">
    <ul>
      <li <c:if test="${voceMenu == voceMenuTirocini}">class="active"</c:if> >
        <a href="/dashboard/tirocini"
           class="waves-effect">
          <i class="material-icons">content_paste</i>
          <spring:message code="vmenu.tirocini.titolo" />
        </a>
      </li>
      <li <c:if test="${voceMenu == voceMenuDomandeRicevute}">class="active"</c:if> >
        <a href="/dashboard/domande/ricevute"
           class="waves-effect">
          <i class="material-icons">call_received</i>
          <spring:message code="vmenu.domandeTirocinio.ricevute" />
        </a>
      </li>
      <li <c:if test="${voceMenu == voceMenuDomandeInviate}">class="active"</c:if> >
        <a href="/dashboard/domande/inviate"
           class="waves-effect">
          <i class="material-icons">call_made</i>
          <spring:message code="vmenu.domandeTirocinio.ricevute" />
        </a>
      </li>
      <li <c:if test="${voceMenu == voceMenuProgetti}">class="active"</c:if> >
        <a href="/dashboard/progetti"
           class="waves-effect">
          <i class="material-icons">business_center</i>
          <spring:message code="vmenu.progettiFormativi.titolo" />
        </a>
      </li>
    </ul>
  </div>
</div>