<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- Definizione variabili utilizzate per titolo e menù --%>
<spring:message var="titoloPagina" scope="request" code="pagina.home.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.navbar.home" />


<%-- Inclusione dell'header con passaggio del titolo della pagina come parametro --%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
  <jsp:param name="titoloPagina" value="${titoloPagina}"/>
</jsp:include>


<%-- Corpo della pagina --%>
<main class="container">

</main>


<%-- Inclusione del footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />