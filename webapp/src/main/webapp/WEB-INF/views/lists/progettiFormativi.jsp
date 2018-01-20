<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message code="progettoFormativo.nome.label" var="nomeProgettoFormativoLabel"/>
<spring:message code="progettoFormativo.descrizione.label" var="descrizioneProgettoFormativoLabel"/>
<spring:message code="progettoFormativo.status.label" var="statusProgettoFormativoLabel"/>


<c:forEach items="${azienda.progettiFormativi}" var="current" varStatus="loop">
	<c:set var="idModalArchivia" value="progettoFormativo-modal-archivia-${loop.index}" />
  <c:set var="idModalProponiti" value="progettoFormativo-modal-proponiti-${loop.index}" />	
	<ul id="idCollapsible" class="collapsible">
		<li>
			<div class="collapsible-header">
					<div class="col s12 valign-wrapper">
      		  <i class="material-icons">business_center</i>
       		  <c:out value="${current.nome}"/> 
        	</div>
       </div>
       
       <div class="collapsible-body">
	        <div class="row row-group">
	        
	        <div class="col s12">
	            <div class="row" >
	              <div class="col s1">
	              	<a class="tooltipped tooltipped-icon" 
	              		 data-position="right"
	              		 data-delay="50"
	              		 data-tooltip="${descrizioneProgettoFormativoLabel}">
	              		 <i class ="small material-icons">chat</i>
	              	</a>      
			          </div>
			          <div class="col s11">
			            <c:out value="${current.descrizione}" />
			          </div>
	            </div>
	        </div>
	        
	        </div>
	     </div>
		</li>
	</ul>
</c:forEach>

<!-- Script per l'inizializzazione e la validazione dei form -->
<script type="text/javascript"
        src="<c:url value="/resources/js/progettiFormativi.js" />" ></script>