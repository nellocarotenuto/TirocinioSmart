<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipAziendaPartitaIVA" code="tooltip.azienda.partitaIVA"/>
<spring:message var="tooltipAziendaIndirizzo" code="tooltip.azienda.indirizzo" />
<spring:message var="tooltipAziendaAccessibilità" code="tooltip.azienda.accessibilità" />
<spring:message var="tooltipAziendaDelegato" code="tooltip.azienda.delegato" />
<spring:message var="tooltipCommonSesso" code="tooltip.common.sesso" />
<spring:message var="tooltipCommonEmail" code="tooltip.common.email" />
<spring:message var="tooltipCommonTelefono" code="tooltip.common.telefono" />


<%-- Definizione etichette accessibilità --%>
<spring:message var="labelAccessibilitàConBarriere" code="label.accessibilità.conBarriere" />
<spring:message var="labelAccessibilitàSenzaBarriere" code="label.accessibilità.senzaBarriere" />


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonApprova" code="button.common.approva" />
<spring:message var="buttonCommonRifiuta" code="button.common.rifiuta" />


<%-- Definizione lista --%>
<c:forEach items="${listaRichiesteConvenzionamento}" var="current" varStatus="loop">
	
	
	<%-- Definizione identificatori modal di approvazione e rifiuto --%>
	<c:set var="idModalRifiuto" value="convenzionamento-modal-rifiuto-${loop.index}" />
  <c:set var="idModalApprovazione" value="convenzionamento-modal-approvazione-${loop.index}" />
	
	
	<%-- Collassabile --%>
	<ul class="collapsible">
		<li>
			
			
			<%-- Testata del collassabile --%>
			<div class="collapsible-header">
				<div class="col s8 valign-wrapper">
     		  <i class="material-icons">business</i>
      		  <c:out value="${current.azienda.nome}"/> 
       	</div>
       	<div class="col s4 right-align">
      	   <span class="right-align">
      	     <tags:localDateTime date="${current.dataRichiesta}"/>
      	   </span>
     	  </div>
  	  </div>
     
     
      <%-- Corpo del collassabile --%>
      <div class="collapsible-body grey lighten-4">
        <div class="row row-group">
        
        
          <%-- Partita IVA dell'azienda --%>
          <div class="col s12">
            <div class="row valign-wrapper" >
              <div class="col s1">
              	<a class="tooltipped tooltipped-icon" 
              		 data-position="right"
              		 data-delay="50"
              		 data-tooltip="${tooltipAziendaPartitaIVA}">
              		 <i class ="small material-icons">business_center</i>
              	</a>      
		          </div>
		          <div class="col s11">
		            <c:out value="${current.azienda.partitaIva}" />
		          </div>
            </div>
          </div>
          
          
          <%-- Sede dell'azienda --%>
          <div class="col s12">
            <div class="row valign-wrapper">
	            <div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
              		 data-position="right"
              		 data-delay="50"
              		 data-tooltip="${tooltipAziendaIndirizzo}">
              		 <i class="small material-icons">location_city</i>
              	</a>
		          </div>
		          <div class="col s11">
		            <c:out value="${current.azienda.indirizzo}"/>
		          </div>
            </div>
          </div>
          
          
          <%-- Accessibilità dell'azienda --%>
          <div class="col s12">
            <div class="row valign-wrapper">
              <div class="col s1">
              	<a class="tooltipped tooltipped-icon" 
              		 data-position="right"
              		 data-delay="50"
              		 data-tooltip="${tooltipAziendaAccessibilità}">
              		 <i class ="small material-icons">accessible</i>
              	</a>
		          </div>
		          
		          <div class="col s11">
		            <c:choose>
		              <c:when test="${current.azienda.senzaBarriere == 'true'}">
		                <c:out value="${labelAccessibilitàSenzaBarriere}"/>
		              </c:when>
		              <c:when test="${current.azienda.senzaBarriere == 'false'}">
		               <c:out value="${labelAccessibilitàConBarriere}"/>
		              </c:when>
		            </c:choose>
		          </div>
            </div>
          </div>
                  
                  
          <%-- Nominativo del delegato --%>     
         	<div class="col s12">
         		<div class="row valign-wrapper">
         			<div class="col s1">
         				<a class="tooltipped tooltipped-icon" 
             			 data-position="right"
              		 data-delay="50"
              		 data-tooltip="${tooltipAziendaDelegato}">
             		  <i class ="small material-icons">face</i>
             		</a>
         			</div>
         			<div class="col s11">
         				<c:out value="${current.azienda.delegato.nome}"/>
         				<c:out value="${current.azienda.delegato.cognome}"/>
         			</div>	
         		</div>
         	</div>
          	
          
          <%-- E-mail del delegato --%>
         	<div class="col s12">
         		<div class="row valign-wrapper">
         			<div class="col s1">
         				<a class="tooltipped tooltipped-icon" 
             			 data-position="right"
              		 data-delay="50"
              		 data-tooltip="${tooltipCommonEmail}">
             		  <i class ="small material-icons">email</i>
             		</a>
         			</div>
         			<div class="col s11">
         				<c:out value="${current.azienda.delegato.email}"/> 
         			</div>	
         		</div>
         	</div>
          	
        
          <%-- Contatto telefonico --%>
         	<div class="col s12">
         		<div class="row valign-wrapper">
         			<div class="col s1">
         				<a class="tooltipped tooltipped-icon" 
             			 data-position="right"
              		 data-delay="50"
              		 data-tooltip="${tooltipCommonTelefono}">
             		  <i class ="small material-icons">phone</i>
             		</a>
         			</div>
         			<div class="col s11">
         				<c:out value="${current.azienda.delegato.telefono}"/> 
         			</div>	
         		</div>
         	</div>
                 
                 
        </div>
        
        
        <%-- Pulsanti per approvazione e rifiuto --%>
        <div class="row">
          <div class="col s12 right-align">
            <a class="btn red white-text waves-effect waves-light modal-trigger"
               href="#<c:out value="${idModalRifiuto}"/>">
              <c:out value="${buttonCommonRifiuta}" />
              <i class="material-icons right">clear</i>
            </a>
            <a class="btn green white-text waves-effect waves-light modal-trigger"
               href="#<c:out value="${idModalApprovazione}"/>">
              <c:out value="${buttonCommonApprova}" />
              <i class="material-icons right">check</i>
            </a>
          </div>
        </div>
        
        
      </div>
		</li>
	</ul>
		

  <%-- Inclusione modal per il rifiuto della richiesta --%>
  <jsp:include page="/WEB-INF/views/forms/rifiuto-richiesta-convenzionamento.jsp">
    <jsp:param value="${current.id}" name="idRichiesta" />
    <jsp:param value="${idModalRifiuto}" name="idModal" />
  </jsp:include>


  <%-- Inclusione modal per l'approvazione della richiesta --%>
  <jsp:include page="/WEB-INF/views/forms/approva-richiesta-convenzionamento.jsp">
    <jsp:param value="${current.id}" name="idRichiesta" />
    <jsp:param value="${idModalApprovazione}" name="idModal" />
  </jsp:include>


</c:forEach>