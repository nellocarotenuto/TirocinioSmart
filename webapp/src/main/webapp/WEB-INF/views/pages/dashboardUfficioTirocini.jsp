<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloPagina" scope="request" code="pagina.dashboard.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.dashboard.titolo" />

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main>
  <div class="row">
    <div class="col l4 xl3">
      <jsp:include page="/WEB-INF/views/menu/ufficioTirociniNav.jsp" />
    </div>
  </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />