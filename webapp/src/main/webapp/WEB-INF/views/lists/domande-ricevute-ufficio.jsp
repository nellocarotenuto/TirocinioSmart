<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipProgettoFormativoNome" code="tooltip.progettoFormativo.nome"/>
<spring:message var="tooltipCommonNominativo" code="tooltip.common.nominativo" />
<spring:message var="tooltipDomandaTirocinioCfu" code="tooltip.domandaTirocinio.cfu" />
<spring:message var="tooltipDomandaTirocinioPeriodoTirocinio"
                code="tooltip.domandaTirocinio.periodoTirocinio" />
<spring:message var="tooltipDomandaTirocinioCommentoStudente"
                code="tooltip.domandaTirocinio.commentoStudente" />
<spring:message var="tooltipDomandaTirocinioCommentoAzienda"
                code="tooltip.domandaTirocinio.commentoAzienda" />
<spring:message var="tooltipDomandaTirocinioPeriodoTirocinio"
                code="tooltip.domandaTirocinio.periodoTirocinio" />


<%-- Definizione etichette sesso --%>
<spring:message var="labelSessoMaschile" code="label.sesso.maschile" />
<spring:message var="labelSessoFemminile" code="label.sesso.femminile" />

<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonApprova" code="button.common.approva" />
<spring:message var="buttonCommonRespingi" code="button.common.respingi" />


<%-- Definizione elenco --%>
<c:forEach items="${elencoDomandeTirocinio}" var="current" varStatus="loop">
  
  
  <%-- Definizione identificatori modal approvazione e respinta --%>
  <c:set var="idModalRespinta" value="domanda-tirocinio-modal-respinta-${loop.index}" />
  <c:set var="idModalApprovazione" value="domanda-tirocinio-modal-approvazione-${loop.index}" />
  
  
  <%-- Collassabile --%>
  <ul class="collapsible">
	  <li>
	  
	  
	    <%-- Testata del collassabile --%>
	    <div class="collapsible-header">
	      <div class="col s8 valign-wrapper">
	        <i class="material-icons">work</i>
	        <c:out value="${current.studente.nome}"/> <c:out value="${current.studente.cognome}"/> - 
	        <c:out value="${current.progettoFormativo.nome}" />
	        (<c:out value="${current.progettoFormativo.azienda.nome}" />)
	      </div>
	      <div class="col s4 right-align">
	        <span class="right-align"><tags:localDateTime date="${current.data}"/></span>
	      </div>
	    </div>
	    
	    
	    <%-- Corpo del collassabile --%>
	    <div class="collapsible-body grey lighten-4">
	      <div class="row row-group">
	      
	      
	        <%-- Nominativo dello studente --%>
	        <div class="col s12">
	          <div class="row valign-wrapper" >
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipCommonNominativo}">
	                <i class ="small material-icons">face</i>
	              </a>      
	            </div>
	            <div class="col s11">
	              <c:out value="${current.studente.nome}"/> 
	              <c:out value="${current.studente.cognome}"/>
	              (<c:out value="${current.studente.matricola}"/>)
	            </div>
	          </div>
	        </div>
	        
	        
	        <%-- Nome del progetto formativo --%>
	        <div class="col s12">
	          <div class="row valign-wrapper" >
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipProgettoFormativoNome}">
	                <i class ="small material-icons">business_center</i>
	              </a>      
	            </div>
	            <div class="col s11">
	              <c:out value="${current.progettoFormativo.nome}"/> 
	              (<c:out value="${current.progettoFormativo.azienda.nome}"/>)
	            </div>
	          </div>
	        </div>
	      
	        
	        <%-- Periodo di tirocinio --%>
	        <div class="col s12">
	          <div class="row valign-wrapper" >
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioPeriodoTirocinio}">
	                <i class ="small material-icons">event</i>
	              </a>      
	            </div>
	            <div class="col s11">
	              <tags:localDate date="${current.inizioTirocinio}"/> - 
	              <tags:localDate date="${current.fineTirocinio}"/>
	            </div>
	          </div>
	        </div>
	        
	      
	        <%-- Crediti formativi --%>
	        <div class="col s12">
	          <div class="row valign-wrapper" >
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioCfu}">
	                <i class ="small material-icons">school</i>
	              </a>      
	            </div>
	            <div class="col s11">
	              <c:out value="${current.cfu}"/> CFU
	            </div>
	          </div>
	        </div>
	        
	        
	        <%-- Commento dello studente --%>
	        <div class="col s12">
	          <div class="row valign-wrapper" >
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioCommentoStudente}">
	                <i class ="small material-icons">sms</i>
	              </a>      
	            </div>
	            <div class="col s11">
	              <c:out value="${current.commentoStudente}"/>
	            </div>
	          </div>
	        </div>
	        
	        
	        <%-- Commento dell'azienda --%>
	        <c:if test="${not empty current.commentoAzienda}">
	         <div class="col s12">
	           <div class="row valign-wrapper" >
	             <div class="col s1">
	               <a class="tooltipped tooltipped-icon" 
	                  data-position="right"
	                  data-delay="50"
	                  data-tooltip="${tooltipDomandaTirocinioCommentoAzienda}">
	                 <i class ="small material-icons">feedback</i>
	               </a>      
	             </div>
	             <div class="col s11">
	               <c:out value="${current.commentoAzienda}"/>
	             </div>
	           </div>
	         </div>
	        </c:if>
	        
	      </div>
	      
	      
        <%-- Pulsanti per approvazione e respinta --%>
        <div class="row">
          <div class="col s12 right-align">
            <a class="btn red white-text waves-effect waves-light modal-trigger"
               href="#<c:out value="${idModalRespinta}"/>">
              <c:out value="${buttonCommonRespingi}" />
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


  <%-- Inclusione modal per respingere la richiesta --%>
  <jsp:include page="/WEB-INF/views/forms/respinta-domanda-tirocinio.jsp">
    <jsp:param value="${current.id}" name="idDomanda" />
    <jsp:param value="${idModalRespinta}" name="idModal" />
  </jsp:include>


  <%-- Inclusione modal per l'approvazione della richiesta --%>
  <jsp:include page="/WEB-INF/views/forms/approva-domanda-tirocinio.jsp">
    <jsp:param value="${current.id}" name="idDomanda" />
    <jsp:param value="${idModalApprovazione}" name="idModal" />
  </jsp:include>
  
  
</c:forEach>