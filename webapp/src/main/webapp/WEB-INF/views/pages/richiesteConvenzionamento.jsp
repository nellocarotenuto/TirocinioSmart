<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloPagina" scope="request" code="pagina.richiesteConvenzionamento.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.dashboard.titolo" />
<spring:message var="voceMenu" scope="request" code="vmenu.richiesteConvenzionamento.titolo" />

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main>
  <div class="row">
    <div class="col l4 xl3">
      <jsp:include page="/WEB-INF/views/menu/ufficioTirociniNav.jsp" />
    </div>
    <div class="col s12 m12 l8 xl9">
      <div class="card single-row-header">
        <div class="card-content">
          <span class="card-title">
            <spring:message code="pagina.richiesteConvenzionamento.titolo" />
          </span>
        </div>
      </div>
      <jsp:include page="/WEB-INF/views/lists/richiesteConvenzionamento.jsp" />
    </div>
  </div>
</main>
    
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
