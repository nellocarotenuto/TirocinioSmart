<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<spring:message code="domandaTirocinio.nomeProgetto.label" var="nomeProgettoLabel"/>
<spring:message code="domandaTirocinio.descrizioneProgetto.label" var="descrizioneProgettoLabel"/>



<c:forEach items="${listaDomandeTirocinio}" var="current" varStatus="loop">
	<c:set var="idModalRifiuta" value="domandaTirocinio-modal-rifiuta-${loop.index}" />
  <c:set var="idModalAccetta" value="domandaTirocinio-modal-accetta-${loop.index}" />
		<ul id="idCollapsible" class="collapsible">
			<li>
				<div class="collapsible-header">
					<div class="col s8 valign-wrapper">
      		  <i class="material-icons">business</i>
       		  <c:out value="${current.studente.nome}"/> 
       		  <c:out value="${current.studente.cognome}"/>
        	</div>
        	<div class="col s4 right-align">
       	   <span class="right-align"><tags:localDateTime date="${current.data}"/></span>
      	  </div>
   	  	</div>
      
	      <div class="collapsible-body">
	        <div class="row row-group">
	        
	          <div class="col s12">
	            <div class="row valign-wrapper" >
	              <div class="col s1">
	              	<a class="tooltipped tooltipped-icon" 
	              		 data-position="right"
	              		 data-delay="50"
	              		 data-tooltip="${nomeProgettoLabel}">
	              		 <i class ="small material-icons">business_center</i>
	              	</a>      
			          </div>
			          <div class="col s11">
			            <c:out value="${current.progettoFormativo.nome}" />
			          </div>
	            </div>
	          </div>
	          
	          <div class="col s12">
	            <div class="row valign-wrapper">
		            <div class="col s1">
		            <a class="tooltipped tooltipped-icon" 
	              		 data-position="right"
	              		 data-delay="50"
	              		 data-tooltip="${descrizioneProgettoLabel}">
	              		 <i class="small material-icons">sms</i>
	              	</a>
			          </div>
			          <div class="col s11">
			            <c:out value="${current.progettoFormativo.descrizione}"/>
			          </div>
	            </div>
	          </div>
	                 
	         </div>
	         <div class="row">
	          <div class="col s12 right-align">
	            <a class="btn red white-text waves-effect waves-light modal-trigger"
	               href="#<c:out value="${idModalRifiuta}"/>">
	              <spring:message code="form.rifiuta.label" />
	            </a>
	            <a class="btn green white-text waves-effect waves-light modal-trigger"
	               href="#<c:out value="${idModalAccetta}"/>">
	              <spring:message code="form.accetta.label" />
	            </a>
	          </div>
	        </div>
	      </div>
			</li>
		</ul>
		
  <div id="${idModalRifiuta}" class="modal">
    <div class="modal-content">
      <jsp:include page="/WEB-INF/views/forms/rifiutaDomandaTirocinio.jsp">
        <jsp:param value="${current.id}" name="idDomanda" />
      </jsp:include>
    </div>
  </div>
  <div id="${idModalAccetta}" class="modal">
    <div class="modal-content">
      <jsp:include page="/WEB-INF/views/forms/accettaDomandaTirocinio.jsp">
        <jsp:param value="${current.id}" name="idDomanda" />
      </jsp:include>
    </div>
  </div>

</c:forEach>

<!-- Script per l'inizializzazione e la validazione dei form -->
<script type="text/javascript"
        src="<c:url value="/resources/js/domandeTirocinio.js" />" ></script>