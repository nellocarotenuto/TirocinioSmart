<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:forEach items="${listaRischiesteIscrizione}" var="current"  varStatus="loop">
  <c:set var="idCollapsible" value="iscrizioni-richiesta-${loop.index}" />
  <c:set var="numeroUltimaRichiesta" value="${loop.index}" />
  <ul id="${idCollapsible}" class="collapsible">
    <li>
      <div class="collapsible-header">
        <i class="material-icons">account_circle</i>
        <c:out value="${current.studente.nome}"/> <c:out value="${current.studente.cognome}"/>
      </div>
      <div class="collapsible-body">
        <div class="row">
          <div class="col s4">
            <i class="tiny material-icons">email</i>
            <b><spring:message code="registrazioneForm.email.label" /></b>
          </div>
          <div class="col s8">
            <c:out value="${current.studente.email}" />
          </div>
          <div class="col s4">
            <i class="tiny material-icons">cake</i>
            <b><spring:message code="richiestaIscrizioneForm.dataDiNascita.label" /></b>
          </div>
          <div class="col s8">
            <tags:localDate date="${current.studente.dataDiNascita}"/>
          </div>
          <div class="col s4">
            <i class="tiny material-icons">wc</i>
            <b><spring:message code="registrazioneForm.sesso.label" /></b>
          </div>
          <div class="col s8">
            <c:choose>
              <c:when test="${current.studente.sesso == 'M'}">
                <spring:message code="registrazioneForm.sesso.maschile" />
              </c:when>
              <c:when test="${current.studente.sesso == 'F'}">
                <spring:message code="registrazioneForm.sesso.femminile" />
              </c:when>
            </c:choose>
          </div>
          <div class="col s4">
            <i class="tiny material-icons">location_city</i>
            <b><spring:message code="richiestaIscrizioneForm.indirizzoStudente.label" /></b>
          </div>
          <div class="col s8">
            <c:out value="${current.studente.indirizzo}" />
          </div>
          <div class="col s4">
            <i class="tiny material-icons">phone</i>
            <b><spring:message code="registrazioneForm.telefono.label" /></b>
          </div>
          <div class="col s8">
            <c:out value="${current.studente.telefono}" />
          </div>
        </div>
        <div class="row">
          <div class="col s4">
            <i class="tiny material-icons">assignment_ind</i>
            <b><spring:message code="richiestaIscrizioneForm.matricola.label" /></b>
          </div>
          <div class="col s8">
            <c:out value="${current.studente.matricola}" />
          </div>
        </div>
      </div>
    </li>
  </ul>
</c:forEach>

<input type="hidden" id="numero-richieste" value="${numeroUltimaRichiesta + 1}" />

<!-- Script per l'inizializzazione e la validazione dei form -->
<script type="text/javascript"
        src="<c:url value="/resources/js/richiesteIscrizione.js" />" ></script>