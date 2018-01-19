<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="titoloPagina" scope="request" code="pagina.aziende.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.aziende.titolo" />

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main class="container"> 
	<div class="row">
	 	<div class="col s12">
	 		<div class="card single-row-header">
	 			<div class="card-content">
	 				<span class="card-title">
	 					<spring:message code="pagina.aziende.titolo"/>
	 				</span>
	 			</div>
	 		</div>
	 	</div>
	 </div> 
	 <div class="row">
	 	<div class="col s12">
	 		<div class="card list">
	 			<div class="card-content" style="padding: 0px">
	 				<jsp:include page="/WEB-INF/views/lists/aziendeConvenzionate.jsp"/>
	 			</div>
			</div>
	 	</div>
	 </div>	
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />