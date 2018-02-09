<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili utilizzate per titolo e menù --%>
<spring:message var="titoloPagina" scope="request" code="pagina.progettiFormativi.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.navbar.dashboard" />
<spring:message var="voceMenu" scope="request" code="vmenu.dashboard.progettiFormativi" />


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipProgettoFormativoAggiungi" code="tooltip.progettoFormativo.aggiungi" />
<spring:message var="tooltipAziendaIndirizzo" code="tooltip.azienda.indirizzo" />
<spring:message var="tooltipCommonEmail" code="tooltip.common.email" />
<spring:message var="tooltipAziendaTelefono" code="tooltip.azienda.telefono" />
<spring:message var="tooltipAziendaAccessibilità" code="tooltip.azienda.accessibilità" />
<spring:message var="tooltipAziendaSenzaBarriere" code="tooltip.azienda.senzaBarriere" />


<%-- Inclusione header --%>
<jsp:include page="/WEB-INF/views/common/header.jsp" >
  <jsp:param name="titoloPagina" value="${titoloPagina}"/>
</jsp:include>


<%-- Corpo della pagina --%>
<main> 
  <div class="row">
    <div class="col l4 xl3">
    
      <%-- Inclusione menù laterale --%>
      <jsp:include page="/WEB-INF/views/menu/azienda-dashboard.jsp" />
      
    </div>
    <div class="col s12 m12 l8 xl9">
      <div class="card single-row-header">
        
        
        <%-- Intestazione --%>
        <div class="card-image">
          <img src="<c:url value="/resources/images/backgrounds/azienda.png"/>">
          
          <span class="card-title">
            <c:out value="${azienda.nome}" />
          </span>
          
          <c:if test="${utente.getClass().getSimpleName() == 'DelegatoAziendale'}">
            <c:if test="${utente.azienda.nome == azienda.nome}">
              <a class="modal-trigger halfway-fab btn-floating btn-large waves-effect waves-light red tooltipped"
                 data-delay="50"
                 data-position="left"
                 href="#aggiungi-progetto-formativo"
                 data-tooltip="${tooltipProgettoFormativoAggiungi}">
                <i class="large material-icons">add</i>
              </a>
            </c:if>
          </c:if>
        
        </div>
        
        <div class="card-content">
          
          
          <%-- Sede dell'azienda --%>
          <div class="row valign-wrapper">
            <div class="col s1">
               <a class="tooltipped tooltipped-icon"
                  data-position="right"
                  data-delay="50"
                  data-tooltip="${tooltipAziendaIndirizzo}">
                  <i class="small material-icons">location_city</i>
               </a>
             </div>
             <div class="col s11">
               <c:out value="${azienda.indirizzo}" />
             </div>
          </div>
          
          
          <%-- E-mail del delegato --%>
          <div class="row valign-wrapper">
            <div class="col s1">
               <a class="tooltipped tooltipped-icon"
                  data-position="right"
                  data-delay="50"
                  data-tooltip="${tooltipCommonEmail}">
                 <i class="small material-icons">email</i>
               </a>
             </div>
             <div class="col s11">
               <c:out value="${azienda.delegato.email}" />
             </div>
          </div>
          
          
          <%-- Contatto telefonico --%>
          <div class="row valign-wrapper">
            <div class="col s1">
               <a class="tooltipped tooltipped-icon"
                  data-position="right"
                  data-delay="50"
                  data-tooltip="${tooltipAziendaTelefono}">
                  <i class="small material-icons">phone</i>
               </a>
             </div>
             <div class="col s11">
               <c:out value="${azienda.delegato.telefono}" />
             </div>
          </div>
          
          
          <%-- Grado accessibilità --%>
          <c:if test="${azienda.senzaBarriere}">
            <div class="row valign-wrapper">
              <div class="col s1">
                 <a class="tooltipped tooltipped-icon"
                    data-position="right"
                    data-delay="50"
                    data-tooltip="<c:out value="${tooltipAziendaAccessibilità}"/>">
                    <i class="small material-icons">accessible</i>
                 </a>
               </div>
               <div class="col s11">
                 <c:out value="${tooltipAziendaSenzaBarriere}" />
               </div>
            </div>
          </c:if>
          
          
        </div>
      </div>
      
      
      <%-- Inclusione lista progetti formativi --%>
      <jsp:include page="/WEB-INF/views/lists/progetti-formativi.jsp"/>
      
      
    </div>
  </div>


  <%-- Inclusione form per l'aggiunta di un progetto formativo --%>
  <jsp:include page="/WEB-INF/views/forms/aggiunta-progetto-formativo.jsp" >
    <jsp:param name="idModal" value="aggiungi-progetto-formativo" />
  </jsp:include>


</main>

    
 <%-- Inclusione footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />