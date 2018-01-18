<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:forEach items="${listaAziendeConvenzionate}" var="current" varStatus="loop"> 
	<div class="row valign-wrapper" style="margin-bottoom:0px" style="padding: 10 0px">
		<div class="col s1 center align">
			<i class="small material-icons circle">business</i>
		</div>
		<div class="col s7">
			<h5><span class="title"><c:out value="${current.nome}"/></span></h5>
      <p> 
			<c:out value="${current.indirizzo}"/>
      </p>
		</div>
		<div class="col s4 center align">
			<a href="/azienda/${current.id}"
				 class="waves-effect waves-teal btn-flat">Progetti Formativi</a>
		</div>
	</div>
	<div class="divider"></div>
</c:forEach>