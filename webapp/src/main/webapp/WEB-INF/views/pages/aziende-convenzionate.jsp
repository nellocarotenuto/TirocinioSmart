<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili utilizzate per titolo e menù --%>
<spring:message var="titoloPagina" scope="request" code="pagina.aziende.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.navbar.aziende" />


<%-- Inclusione header --%>
<jsp:include page="/WEB-INF/views/common/header.jsp" >
  <jsp:param name="titoloPagina" value="${titoloPagina}"/>
</jsp:include>


<%-- Corpo della pagina --%>
<main class="container"> 
	<div class="row">
	 	<div class="col s12">
	 		<div class="card single-row-header">
	 			<div class="card-content">
	 			
	 			  <%-- Titolo della pagina --%>
	 				<span class="card-title">
	 					<c:out value="${titoloPagina}" />
	 				</span>
	 				
	 			</div>
	 		</div>
	 	</div>
	 </div> 
	 <div class="row">
	 	<div class="col s12">
	 		<div class="card list">
	 			<div class="card-content" style="padding: 0px">
	 			
	 			  <%-- Inclusione lista aziende convenzionate --%>
	 				<jsp:include page="/WEB-INF/views/lists/aziende-convenzionate.jsp"/>
	 				
	 			</div>
			</div>
	 	</div>
	 </div>	
</main>


<%-- Inclusione del footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />