<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="voceMenuDomande" code="vmenu.domandeTirocinio.titolo" />
<spring:message var="voceMenuProgetti" code="vmenu.progettiFormativi.titolo" />
<spring:message var="voceMenuTirocini" code="vmenu.tirocini.titolo" />

<div class="card vertical-nav hide-on-med-and-down">
  <div class="card-content">
    <ul>
      <li <c:if test="${voceMenu == voceMenuTirocini}">class="active"</c:if> >
        <a href="/dashboard/tirocini"
           class="waves-effect">
          <i class="material-icons">work</i>
          <spring:message code="vmenu.tirocini.titolo" />
        </a>
      </li>
      <li <c:if test="${voceMenu == voceMenuDomande}">class="active"</c:if> >
        <a href="/dashboard/domande"
           class="waves-effect">
          <i class="material-icons">face</i>
          <spring:message code="vmenu.domandeTirocinio.titolo" />
        </a>
      </li>
      <li <c:if test="${voceMenu == voceMenuProgetti}">class="active"</c:if> >
        <a href="/dashboard/progetti"
           class="waves-effect">
          <i class="material-icons">business</i>
          <spring:message code="vmenu.progettiFormativi.titolo" />
        </a>
      </li>
    </ul>
  </div>
</div>