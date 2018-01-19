<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloPagina" scope="request" code="pagina.progettiFormativi.titolo" />

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main class="container"> 
	<div class="row">
	 	<div class="col s12">
	 		<div class="card single-row-header">
	 			<div class="card-content">
	 				<span class="card-title">
	 					<spring:message code="${listaProgettiFormativi.nome}"/>
	 				</span>
	 			</div>
	 		</div>
	 		<jsp:include page="/WEB-INF/views/lists/progettiFormativi.jsp"/>
	 	</div>
	 </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />