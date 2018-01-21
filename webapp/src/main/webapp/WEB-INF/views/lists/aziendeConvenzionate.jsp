<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:forEach items="${listaAziendeConvenzionate}" var="current" varStatus="loop"> 
	<div class="row valign-wrapper">
		<div class="col s1 center-align">
			<i class="small material-icons circle">business</i>
		</div>
		<div class="col s6">
		  <h5>
		    <c:out value="${current.nome}"/>
		  </h5>
		  <p class="grey-text">
	      <c:out value="${current.indirizzo}"/>
	    </p>
	  </div>
	  <div class="col s5">
	    <a href="/aziende/${current.id}"
         class="btn waves-effect right">
        <i class="material-icons right">business_center</i>
        <spring:message code="button.progettiFormativi.label" />
      </a>
	  </div>
	</div>
</c:forEach>