<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloPagina" scope="request" code="pagina.richiesteConvenzionamento.titolo" />

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main class="container"> 
	<div class="row">
	 	<div class="col s12">
	 		<div class="card">
	 			<div class="card-content">
	 				<jsp:include page="/WEB-INF/views/list/richiesteConvenzionamento.jsp"/>
	 			</div>
	 			<div class="card-title">
	 			<spring:message code="pagina.richiesteConvenzionamento.titolo"/>
	 			</div>
	 		</div>
	 	</div>
	 </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />