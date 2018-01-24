<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%-- Etichette pulsanti --%>
<spring:message var="buttonProgettiFormativiVisualizza"
                code="button.progettiFormativi.visualizza" />


<%-- Lista delle aziende --%>
<c:forEach items="${listaAziendeConvenzionate}" var="current" varStatus="loop"> 
	<div class="row valign-wrapper">
		
		
		<%-- Colonna con l'icona --%>
		<div class="col s1 center-align">
			<i class="small material-icons circle">business</i>
		</div>
		
		
		<%-- Colonna con il nome e l'indirizzo dell'azienda --%>
		<div class="col s6">
		  <h5>
		    <c:out value="${current.nome}"/>
		  </h5>
		  <p class="grey-text">
	      <c:out value="${current.indirizzo}"/>
	    </p>
	  </div>
	  
	  
	  <%-- Colonna con il pulsante per la visualizzazione del profilo aziendale --%>
	  <div class="col s5">
	    <a href="/aziende/${current.id}"
         class="btn waves-effect right">
        <i class="material-icons right">business_center</i>
        <c:out value="${buttonProgettiFormativiVisualizza}" />
      </a>
	  </div>
	  
	  
	</div>
	
	
</c:forEach>