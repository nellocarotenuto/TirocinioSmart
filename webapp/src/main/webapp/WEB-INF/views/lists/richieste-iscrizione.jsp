<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<spring:message var="emailLabel" code="registrazioneForm.email.label" />
<spring:message var="matricolaLabel" code="richiestaIscrizioneForm.matricola.label" />
<spring:message var="dataDiNascitaLabel" code="richiestaIscrizioneForm.dataDiNascita.label" />
<spring:message var="sessoLabel" code="registrazioneForm.sesso.label" />
<spring:message var="sessoMaschileLabel" code="registrazioneForm.sesso.maschile" />
<spring:message var="sessoFemminileLabel" code="registrazioneForm.sesso.femminile" />
<spring:message var="indirizzoLabel" code="richiestaIscrizioneForm.indirizzoStudente.label" />
<spring:message var="telefonoLabel" code="registrazioneForm.telefono.label" />

<c:forEach items="${listaRischiesteIscrizione}" var="current" varStatus="loop">
  <c:set var="idModalRifiuto" value="iscrizione-modal-rifiuto-${loop.index}" />
  <c:set var="idModalApprovazione" value="iscrizione-modal-approvazione-${loop.index}" />
  <ul id="${idCollapsible}" class="collapsible">
    <li>
      <div class="collapsible-header">
        <div class="col s8 valign-wrapper">
	        <i class="small material-icons">account_circle</i>
	        <c:out value="${current.studente.nome}"/> <c:out value="${current.studente.cognome}"/>
        </div>
        <div class="col s4 right-align">
          <span class="right-align"><tags:localDateTime date="${current.dataRichiesta}"/></span>
        </div>
      </div>
      <div class="collapsible-body">
        <div class="row">
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
                <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="<c:out value="${emailLabel}"/>">
                   <i class="small material-icons">email</i>
                </a>
		          </div>
		          <div class="col s11">
		            <c:out value="${current.studente.email}" />
		          </div>
            </div>
          </div>
          
          <div class="col s12">
            <div class="row valign-wrapper">
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="<c:out value="${dataDiNascitaLabel}"/>">
                   <i class="small material-icons">cake</i>
                </a>
              </div>
		          <div class="col s11">
		            <tags:localDate date="${current.studente.dataDiNascita}"/>
		          </div>
            </div>
          </div>
          
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
                <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="<c:out value="${sessoLabel}"/>">
                   <i class="small material-icons">wc</i>
                </a>
              </div>
              <div class="col s11">
                <c:choose>
                  <c:when test="${current.studente.sesso == 'M'}">
                    <c:out value="${sessoMaschileLabel}" />
                  </c:when>
                  <c:when test="${current.studente.sesso == 'F'}">
                    <c:out value="${sessoFemminileLabel}" />
                  </c:when>
                </c:choose>
              </div>
            </div>
          </div>
           
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
                <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="<c:out value="${indirizzoLabel}"/>">
                   <i class="small material-icons">location_city</i>
                </a>
              </div>
              <div class="col s11">
                <c:out value="${current.studente.indirizzo}" />
              </div>
            </div>
          </div>
          
          <div class="col s12">
	          <div class="row valign-wrapper">
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon"
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="<c:out value="${telefonoLabel}"/>">
	                 <i class="small material-icons">phone</i>
	              </a>
	            </div>
	            <div class="col s11">
	              <c:out value="${current.studente.telefono}" />
	            </div>
            </div>
          </div>

          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
                <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="<c:out value="${matricolaLabel}"/>">
                   <i class="small material-icons">assignment_ind</i>
                </a>
              </div>
              <div class="col s11">
                <c:out value="${current.studente.matricola}" />
              </div>
            </div>
          </div>
        </div>
        
        <div class="row">
          <div class="col s12 right-align">
            <a class="btn red white-text waves-effect waves-light modal-trigger"
               href="#<c:out value="${idModalRifiuto}"/>">
              <spring:message code="form.rifiuta.label" />
            </a>
            <a class="btn green white-text waves-effect waves-light modal-trigger"
               href="#<c:out value="${idModalApprovazione}"/>">
              <spring:message code="form.approva.label" />
            </a>
          </div>
        </div>
      </div>
    </li>
  </ul>

  <jsp:include page="/WEB-INF/views/forms/rifiuto-richiesta-iscrizione.jsp">
    <jsp:param value="${current.id}" name="idRichiesta" />
    <jsp:param value="${idModalRifiuto}" name="idModal" />
  </jsp:include>

  <jsp:include page="/WEB-INF/views/forms/approva-richiesta-iscrizione.jsp">
    <jsp:param value="${current.id}" name="idRichiesta" />
    <jsp:param value="${idModalApprovazione}" name="idModal" />
  </jsp:include>

</c:forEach>

<!-- Script per l'inizializzazione e la validazione dei form -->
<script type="text/javascript"
        src="<c:url value="/resources/js/richiesteIscrizione.js" />" ></script>