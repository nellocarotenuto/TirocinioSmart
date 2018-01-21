<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloPagina" scope="request" code="pagina.progettiFormativi.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.aziende.titolo" />
<spring:message code="progettoFormativo.aggiungi.label" var ="aggiungiProgettoLabel" />

<spring:message var="emailLabel" code="registrazioneForm.email.label" />
<spring:message var="indirizzoLabel" code="convenzionamentoForm.indirizzoAzienda.label" />
<spring:message var="telefonoLabel" code="registrazioneForm.telefono.label" />

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main class="container"> 
	<div class="row">
	 	<div class="col s12">
	 		<div class="card single-row-header">
	 			<div class="card-image">
	 			  <img src="<c:url value="/resources/images/backgrounds/azienda.png"/>">
	 				<span class="card-title">
	 					<spring:message code="${azienda.nome}"/>
	 				</span>
	 				<c:if test="${utente.getClass().getSimpleName() == 'DelegatoAziendale'}">
				    <c:if test="${utente.azienda.nome == azienda.nome}">
				      <a class="halfway-fab btn-floating btn-large waves-effect waves-light red tooltipped"
				         data-delay="50"
				         data-position="left"
				         href="#aggiungi"
				         data-tooltip="${aggiungiProgettoLabel}">
				        <i class="large material-icons">add</i>
				      </a>
				    </c:if>
				  </c:if>
	 			</div>
	 			<div class="card-content">
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
	             <c:out value="${azienda.indirizzo}" />
	           </div>
          </div>
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
               <c:out value="${azienda.delegato.email}" />
             </div>
          </div>
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
               <c:out value="${azienda.delegato.telefono}" />
             </div>
          </div>
        </div>
	 		</div>
	 		<jsp:include page="/WEB-INF/views/lists/progettiFormativi.jsp"/>
		</div>
	</div>
	<div id="aggiungi" class="modal">
		<div class="modal-content">
	  	<h4>
     		<spring:message code="progettoFormativo.aggiungi.label" />
      </h4>
	    <jsp:include page="/WEB-INF/views/forms/aggiuntaProgettoFormativo.jsp"/>
	  </div>
	</div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />