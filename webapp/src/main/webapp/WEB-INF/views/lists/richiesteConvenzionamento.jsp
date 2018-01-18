<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<spring:message code="convenzionamentoForm.partitaIvaAzienda.label" var="partitaIvaLabel"/>
<spring:message code="convenzionamentoForm.indirizzoAzienda.label" var="indirizzoLabel"/>
<spring:message code="convenzionamentoForm.senzaBarriere.accessibile" var="accessibileLabel"/>
<spring:message code="convenzionamentoForm.senzaBarriere.inaccessibile" var="inaccessibileLabel"/>
<spring:message code="convenzionamentoForm.delegato.label" var="delegatoLabel"/>
<spring:message code="registrazioneForm.email.label" var="emailDelegatoLabel"/>
<spring:message code="registrazioneForm.telefono.label" var="telefonoDelegatoLabel"/>
<spring:message code="registrazioneForm.sesso.maschile" var="maschileDelegatoLabel"/>
<spring:message code="registrazioneForm.sesso.femminile" var="femminileDelegatoLabel"/>
<spring:message code="registrazioneForm.sessoDelegato.label" var="sessoDelegatoLabel"/>


<c:forEach items="${listaRichiesteConvenzionamento}" var="current">
		<ul id="idCollapsible" class="collapsible">
			<li>
				<div class="collapsible-header">
					<div class="col s8 valign-wrapper">
      		  <i class="material-icons">business</i>
       		  <c:out value="${current.azienda.nome}"/> 
        	</div>
        	<div class="col s4 right-align">
       	   <span class="right-align"><tags:localDateTime date="${current.dataRichiesta}"/></span>
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
              		 data-tooltip="${partitaIvaLabel}">
              		 <i class ="small material-icons">business_center</i>
              	</a>
		            
		          </div>
		          <div class="col s11">
		            <c:out value="${current.azienda.partitaIva}" />
		          </div>
            </div>
          </div>
          
          <div class="col s12">
            <div class="row valign-wrapper">
	            <div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
              		 data-position="right"
              		 data-delay="50"
              		 data-tooltip="${indirizzoLabel}">
              		 <i class="small material-icons">location_city</i>
              	</a>
		          </div>
		          <div class="col s11">
		            <c:out value="${current.azienda.indirizzo}"/>
		          </div>
            </div>
          </div>
          
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
              	<a class="tooltipped tooltipped-icon" 
              		 data-position="right"
              		 data-delay="50"
              		 data-tooltip="${partitaIvaLabel}">
              		 <i class ="small material-icons">accessible</i>
              	</a>
		          </div>
		          
		          <div class="col s11">
		            <c:choose>
		              <c:when test="${current.azienda.senzaBarriere == 'true'}">
		                <c:out value="${accessibileLabel}"/>
		              </c:when>
		              <c:when test="${current.azienda.senzaBarriere == 'false'}">
		               <c:out value="${inaccessibileLabel}"/>
		              </c:when>
		            </c:choose>
		          </div>
            </div>
          </div>
                  
          	<div class="col s12">
          		<div class="row valign-wrapper">
          			<div class="col s1">
          				<a class="tooltipped tooltipped-icon" 
              			 data-position="right"
	              		 data-delay="50"
	              		 data-tooltip="${delegatoLabel}">
              		  <i class ="small material-icons">face</i>
              		</a>
          			</div>
          			<div class="col s11">
          				<c:out value="${current.azienda.delegato.nome}"/>
          				<c:out value="${current.azienda.delegato.cognome}"/>
          			</div>	
          		</div>
          	</div>
          	
          	<div class="col s12">
          		<div class="row valign-wrapper">
          			<div class="col s1">
          				<a class="tooltipped tooltipped-icon" 
              			 data-position="right"
	              		 data-delay="50"
	              		 data-tooltip="${sessoDelegatoLabel}">
              		  <i class ="small material-icons">wc</i>
              		</a>
          			</div>
          			<div class="col s11">
          				<c:choose>
		             	 <c:when test="${current.azienda.delegato.sesso == 'M'}">
		              	  <c:out value="${maschileDelegatoLabel}"/>
		             	 </c:when>
		            	  <c:when test="${current.azienda.delegato.sesso == 'F'}">
		             	  <c:out value="${femminileDelegatoLabel}"/>
		             	 </c:when>
		            </c:choose> 
          			</div>	
          		</div>
          	</div>
          	
          	<div class="col s12">
          		<div class="row valign-wrapper">
          			<div class="col s1">
          				<a class="tooltipped tooltipped-icon" 
              			 data-position="right"
	              		 data-delay="50"
	              		 data-tooltip="${emailDelegatoLabel}">
              		  <i class ="small material-icons">email</i>
              		</a>
          			</div>
          			<div class="col s11">
          				<c:out value="${current.azienda.delegato.email}"/> 
          			</div>	
          		</div>
          	</div>
          	
          	<div class="col s12">
          		<div class="row valign-wrapper">
          			<div class="col s1">
          				<a class="tooltipped tooltipped-icon" 
              			 data-position="right"
	              		 data-delay="50"
	              		 data-tooltip="${telefonoDelegatoLabel}">
              		  <i class ="small material-icons">phone</i>
              		</a>
          			</div>
          			<div class="col s11">
          				<c:out value="${current.azienda.delegato.telefono}"/> 
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
        src="<c:url value="/resources/js/richiesteConvenzionamento.js" />" ></script>