<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
         import="it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipProgettoFormativoDescrizione"
                code="tooltip.progettoFormativo.descrizione" />


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonProgettoFormativoArchivia" code="button.progettoFormativo.archivia" />
<spring:message var="buttonDomandaTirocinioProponiti" code="button.domandaTirocinio.proponiti" />


<spring:message code="progettoFormativo.nome.label" var="nomeProgettoFormativoLabel"/>
<spring:message code="progettoFormativo.descrizione.label" var="descrizioneProgettoFormativoLabel"/>
<spring:message code="progettoFormativo.status.label" var="statusProgettoFormativoLabel"/>

<c:set var="statusProgettoAttivo" scope="page" value="<%= ProgettoFormativo.ATTIVO %>" />


<%-- Definizione lista dei progetti --%>
<c:forEach items="${azienda.progettiFormativi}" var="current" varStatus="loop">
	<c:if test="${current.status == statusProgettoAttivo}">
	  
	  
	  <%-- Definizione variabili per l'identificazione dei modal --%>
	  <c:set var="idModalArchivia" value="progetto-formativo-modal-archivia-${loop.index}" />
	  <c:set var="idModalProponiti" value="progetto-formativo-modal-proponiti-${loop.index}" />  
	  
	  
	  <ul id="idCollapsible" class="collapsible">
	    <li>
	    
	    
	      <%-- Testata del collassabile --%>
	      <div class="collapsible-header">
	          <div class="col s12 valign-wrapper">
	            <i class="material-icons">business_center</i>
	            <c:out value="${current.nome}"/> 
	          </div>
	       </div>
	       
	       
	       <%-- Corpo del collassabile --%>
	       <div class="collapsible-body">
	         
	         
	         <%-- Descrizione del progetto formativo --%>
	         <div class="row row-group" style="margin-bottom: 0px">
	           <div class="col s12">
	             <div class="row" >
	               <div class="col s1">
	                 <a class="tooltipped tooltipped-icon" 
	                    data-position="right"
	                    data-delay="50"
	                    data-tooltip="${tooltipProgettoFormativoDescrizione}">
	                   <i class ="small material-icons">chat</i>
	                 </a>      
	               </div>
	               <div class="col s11">
	                <c:out value="${current.descrizione}" />
	               </div>
	             </div>
	           </div>
	         </div>
	         
	         
	         <%-- Azioni sui progetti --%>
	         <c:choose>
		         
		         
		         <%-- Definizione tasto di archiviazione progetto --%>
		         <c:when test="${utente.getClass().getSimpleName() == 'DelegatoAziendale'}">
	             <c:if test="${utente.azienda.nome == azienda.nome}" >
	               <div class="row">
	                 <div class="col s12">
	                   <a href="#<c:out value="${idModalArchivia}"/>"
	                      data-position="right"
	                      class="modal-trigger btn waves-effect right">
	                     <i class="material-icons right">archive</i>
	                     <c:out value="${buttonProgettoFormativoArchivia}" />
	                   </a>
	                 </div>
	               </div>
	               
                 <jsp:include page="/WEB-INF/views/forms/archivia-progetto-formativo.jsp">
                   <jsp:param value="${idModalArchivia}" name="idModal"/>
                   <jsp:param value="${current.id}" name="idProgetto" />
                 </jsp:include>

	             </c:if>
	           </c:when>
		         
		         
		         <%-- Definizione tasto invio domanda tirocinio --%>
		         <c:when test="${utente.getClass().getSimpleName() == 'Studente'}">
	             <div class="row">
	               <div class="col s12">
	                 <a href="#<c:out value="${idModalProponiti}" />"
	                    data-position="right"
	                    class="modal-trigger btn waves-effect right">
	                   <i class="material-icons right">send</i>
	                   <c:out value="${buttonDomandaTirocinioProponiti}" />
	                 </a>
	               </div>
	             </div>
	             
	             
	             <%-- Inclusione del modal di invio domanda tirocinio --%>
							 <jsp:include page="/WEB-INF/views/forms/invio-domanda-tirocinio.jsp">
								 <jsp:param value="${idModalProponiti}" name="idModal" />
								 <jsp:param value="${current.id}" name="idProgetto" />
								 <jsp:param value="${loop.index}" name="numeroModal" />
								 <jsp:param value="${current.azienda.nome}" name="nomeAzienda" />
								 <jsp:param value="${current.nome}" name="nomeProgetto" />
							 </jsp:include>
					        
		         </c:when>
		         
		         
		         <%-- Aggiunta tasto proponiti disabilitato per utenti non autenticati --%>
		         <c:when test="${empty utente}">
		         	 <div class="row">
	               <div class="col s12">
	                 <a class="btn waves-effect right disabled">
	                   <i class="material-icons right">send</i>
	                   <c:out value="${buttonDomandaTirocinioProponiti}" />
	                 </a>
	               </div>
	             </div>
		         </c:when>
		         
		         
	   		 	</c:choose>
	   		 	
	   		 	
	       </div>
	       
	       
	    </li>
	  </ul>
	  
	  
	</c:if>
</c:forEach>