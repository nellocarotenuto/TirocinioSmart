<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloPagina" scope="request" code="pagina.home.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.home.titolo" />

<spring:message var="testoNotifica" code="${testoNotifica}" />

<jsp:include page="/WEB-INF/views/common/header.jsp">
  <jsp:param name="titoloPagina" value="${titoloPagina}"/>
</jsp:include>

<main class="container">

</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />