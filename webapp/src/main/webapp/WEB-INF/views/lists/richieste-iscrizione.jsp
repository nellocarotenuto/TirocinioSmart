<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipStudenteMatricola" code="tooltip.studente.matricola"/>
<spring:message var="tooltipCommonDataDiNascita" code="tooltip.common.dataDiNascita" />
<spring:message var="tooltipCommonSesso" code="tooltip.common.sesso" />
<spring:message var="tooltipCommonEmail" code="tooltip.common.email" />
<spring:message var="tooltipCommonTelefono" code="tooltip.common.telefono" />
<spring:message var="tooltipCommonIndirizzo" code="tooltip.common.indirizzo" />


<%-- Definizione etichette sesso --%>
<spring:message var="labelSessoMaschile" code="label.sesso.maschile" />
<spring:message var="labelSessoFemminile" code="label.sesso.femminile" />


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonApprova" code="button.common.approva" />
<spring:message var="buttonCommonRifiuta" code="button.common.rifiuta" />


<%-- Definizione della lista --%>
<c:forEach items="${listaRischiesteIscrizione}" var="current" varStatus="loop">
  
  
  <%-- Definizione variabili modal --%>
  <c:set var="idModalRifiuto" value="iscrizione-modal-rifiuto-${loop.index}" />
  <c:set var="idModalApprovazione" value="iscrizione-modal-approvazione-${loop.index}" />
  
  
  <%-- Definizione collassabile --%>
  <ul class="collapsible">
    <li>
      
      
      <%-- Testata del collassabile --%>
      <div class="collapsible-header">
        <div class="col s8 valign-wrapper">
	        <i class="small material-icons">account_circle</i>
	        <c:out value="${current.studente.nome}"/> <c:out value="${current.studente.cognome}"/>
        </div>
        <div class="col s4 right-align">
          <span class="right-align">
            <tags:localDateTime date="${current.dataRichiesta}"/>
          </span>
        </div>
      </div>
      
      
      <%-- Corpo del collassabile --%>
      <div class="collapsible-body grey lighten-4">
        <div class="row">
          
          
          <%-- Email dello studente --%>
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
          
          
          <%-- Data di nascita dello studente --%>
          <div class="col s12">
            <div class="row valign-wrapper">
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="<c:out value="${tooltipCommonDataDiNascita}"/>">
                   <i class="small material-icons">cake</i>
                </a>
              </div>
		          <div class="col s11">
		            <tags:localDate date="${current.studente.dataDiNascita}"/>
		          </div>
            </div>
          </div>
          
          
          <%-- Sesso dello studente --%>
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
                <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="<c:out value="${tooltipCommonSesso}"/>">
                   <i class="small material-icons">wc</i>
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
           
           
          <%-- Indirizzo dello studente --%>
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
                <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="<c:out value="${tooltipCommonIndirizzo}"/>">
                   <i class="small material-icons">location_city</i>
                </a>
              </div>
              <div class="col s11">
                <c:out value="${current.studente.indirizzo}" />
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
	                 data-tooltip="<c:out value="${tooltipCommonTelefono}"/>">
	                 <i class="small material-icons">phone</i>
	              </a>
	            </div>
	            <div class="col s11">
	              <c:out value="${current.studente.telefono}" />
	            </div>
            </div>
          </div>


          <%-- Matricola dello studente --%>
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
                <a class="tooltipped tooltipped-icon"
                   data-position="right"
                   data-delay="50"
                   data-tooltip="<c:out value="${tooltipStudenteMatricola}"/>">
                   <i class="small material-icons">assignment_ind</i>
                </a>
              </div>
              <div class="col s11">
                <c:out value="${current.studente.matricola}" />
              </div>
            </div>
          </div>
        </div>
        
        
        <%-- Pulsanti per approvazione e rifiuto --%>
        <div class="row">
          <div class="col s12 right-align">
            <a class="btn red white-text waves-effect waves-light modal-trigger"
               href="#<c:out value="${idModalRifiuto}"/>">
              <c:out value="${buttonCommonRifiuta}" />
              <i class="material-icons right">clear</i>
            </a>
            <a class="btn green white-text waves-effect waves-light modal-trigger"
               href="#<c:out value="${idModalApprovazione}"/>">
              <c:out value="${buttonCommonApprova}" />
              <i class="material-icons right">check</i>
            </a>
          </div>
        </div>
        
        
      </div>
    </li>
  </ul>

  
  <%-- Inclusione modal rifiuto richiesta --%>
  <jsp:include page="/WEB-INF/views/forms/rifiuto-richiesta-iscrizione.jsp">
    <jsp:param value="${current.id}" name="idRichiesta" />
    <jsp:param value="${idModalRifiuto}" name="idModal" />
  </jsp:include>

  <jsp:include page="/WEB-INF/views/forms/approva-richiesta-iscrizione.jsp">
    <jsp:param value="${current.id}" name="idRichiesta" />
    <jsp:param value="${idModalApprovazione}" name="idModal" />
  </jsp:include>

</c:forEach>