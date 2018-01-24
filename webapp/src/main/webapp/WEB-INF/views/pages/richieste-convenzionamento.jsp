<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili utilizzate per titolo e menù --%>
<spring:message var="titoloPagina" scope="request" code="pagina.richiesteConvenzionamento.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.navbar.dashboard" />
<spring:message var="voceMenu" scope="request" code="vmenu.dashboard.richiesteConvenzionamento" />


<%-- Inclusione header --%>
<jsp:include page="/WEB-INF/views/common/header.jsp" >
  <jsp:param name="titoloPagina" value="${titoloPagina}"/>
</jsp:include>


<%-- Corpo della pagina --%>
<main>
  <div class="row">
    <div class="col l4 xl3">
      <jsp:include page="/WEB-INF/views/menu/ufficio-dashboard.jsp" />
    </div>
    <div class="col s12 m12 l8 xl9">
      <div class="card single-row-header">
        <div class="card-content">
          <span class="card-title">
            
            
            <%-- Titolo della pagina --%>
            <c:out value="${titoloPagina}" />
            
            
          </span>
        </div>
      </div>
      
      
      <%-- Inclusione della lista di richieste di convenzionamento --%>
      <jsp:include page="/WEB-INF/views/lists/richieste-convenzionamento.jsp" />
      
      
    </div>
  </div>
</main>


<%-- Inclusione del footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />