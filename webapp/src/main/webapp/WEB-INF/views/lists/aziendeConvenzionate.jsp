<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:forEach items="${listaAziendeConvenzionate}" var="current" varStatus="loop"> 
	<div class="row valign-wrapper" style="margin-bottoom:0px" style="padding: 10 0px">
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
	    <a href="/azienda/${current.id}"
         class="waves-effect btn-flat right">Progetti Formativi</a>
	  </div>
	</div>
	<div class="divider"></div>
</c:forEach>