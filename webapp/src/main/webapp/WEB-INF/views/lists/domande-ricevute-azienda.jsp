<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipProgettoFormativoNome" code="tooltip.progettoFormativo.nome"/>
<spring:message var="tooltipCommonNominativo" code="tooltip.common.nominativo" />
<spring:message var="tooltipCommonDataDiNascita" code="tooltip.common.dataDiNascita" />
<spring:message var="tooltipCommonSesso" code="tooltip.common.sesso" />
<spring:message var="tooltipCommonEmail" code="tooltip.common.email" />
<spring:message var="tooltipCommonTelefono" code="tooltip.common.telefono" />

<spring:message var="tooltipProgettoFormativoNome" code="tooltip.progettoFormativo.nome" />
<spring:message var="tooltipDomandaTirocinioCommentoStudente"
                code="tooltip.domandaTirocinio.commentoStudente" />
<spring:message var="tooltipDomandaTirocinioPeriodoTirocinio"
                code="tooltip.domandaTirocinio.periodoTirocinio" />


<%-- Definizione etichette sesso --%>
<spring:message var="labelSessoMaschile" code="label.sesso.maschile" />
<spring:message var="labelSessoFemminile" code="label.sesso.femminile" />


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonRifiuta" code="button.common.rifiuta" />
<spring:message var="buttonCommonAccetta" code="button.common.accetta" />


<%-- Lista delle domande --%>
<c:forEach items="${elencoDomandeTirocinio}" var="current" varStatus="loop">
	
	
	<%-- Imposta variabili per gli id dei modal --%>
	<c:set var="idModalRifiuta" value="domanda-tirocinio-modal-rifiuta-${loop.index}" />
  <c:set var="idModalAccetta" value="domandaa-tirocinio-modal-accetta-${loop.index}" />
		
		
		<ul class="collapsible">
			<li>
			
			  <%-- Testata del collassabile --%>
				<div class="collapsible-header">
				
				  
				  <%-- Nominativo di studente e progetto formativo --%>
					<div class="col s8 valign-wrapper">
      		  <i class="material-icons">work</i>
       		  <c:out value="${current.studente.nome}"/> 
       		  <c:out value="${current.studente.cognome}"/> - 
       		  <c:out value="${current.progettoFormativo.nome}" />
        	</div>
        	
        	
        	<%-- Data dell'invio della domanda --%>
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
                  <c:out value="${current.studente.nome}" />  
                  <c:out value="${current.studente.cognome}" />
                </div>
              </div>
            </div>
	        
	        
	          <%-- Data di nascita dello studente --%>
	          <div class="col s12">
              <div class="row valign-wrapper" >
                <div class="col s1">
                  <a class="tooltipped tooltipped-icon" 
                     data-position="right"
                     data-delay="50"
                     data-tooltip="${tooltipCommonDataDiNascita}">
                    <i class ="small material-icons">cake</i>
                  </a>      
                </div>
                <div class="col s11">
                  <tags:localDate date="${current.studente.dataDiNascita}"/>
                </div>
              </div>
            </div>
            
            
            <%-- Sesso dello studente --%>
            <div class="col s12">
              <div class="row valign-wrapper" >
                <div class="col s1">
                  <a class="tooltipped tooltipped-icon" 
                     data-position="right"
                     data-delay="50"
                     data-tooltip="${tooltipCommonSesso}">
                    <i class ="small material-icons">wc</i>
                  </a>      
                </div>
                <div class="col s11">
	                <c:choose>
	                  <c:when test="${current.studente.sesso == 'M'}">
	                    <c:out value="${labelSessoMaschile}" />
	                  </c:when>
	                  <c:when test="${current.studente.sesso == 'F'}">
	                    <c:out value="${labelSessoFemminile}" />
	                  </c:when>
	                </c:choose>
                </div>
              </div>
            </div>
            
            
            <%-- E-mail dello studente --%>
            <div class="col s12">
	            <div class="row valign-wrapper">
	              <div class="col s1">
	                <a class="tooltipped tooltipped-icon"
	                   data-position="right"
	                   data-delay="50"
	                   data-tooltip="<c:out value="${tooltipCommonEmail}"/>">
	                  <i class="small material-icons">email</i>
	                </a>
	              </div>
	              <div class="col s11">
	                <c:out value="${current.studente.email}" />
	              </div>
	            </div>
            </div>
          
          
	          <%-- Contatto telefonico dello studente --%>
	          <div class="col s12">
	            <div class="row valign-wrapper">
	              <div class="col s1">
	                <a class="tooltipped tooltipped-icon"
	                   data-position="right"
	                   data-delay="50"
	                   data-tooltip="<c:out value="${tooltipCommonTelefono}"/>">
	                  <i class="small material-icons">phone</i>
	                </a>
	              </div>
	              <div class="col s11">
	                <c:out value="${current.studente.telefono}" />
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
			            <c:out value="${current.progettoFormativo.nome}" />
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
	          
	          
	          <%-- Commento studente --%>
	          <div class="col s12">
	            <div class="row valign-wrapper">
		            <div class="col s1">
		            <a class="tooltipped tooltipped-icon" 
	              		 data-position="right"
	              		 data-delay="50"
	              		 data-tooltip="${tooltipDomandaTirocinioCommentoStudente}">
	              		 <i class="small material-icons">sms</i>
	              	</a>
			          </div>
			          <div class="col s11">
			            <c:out value="${current.commentoStudente}"/>
			          </div>
	            </div>
	          </div>
	         
	         
	        </div>
	         
	         
          <%-- Pulsanti per approvazione e rifiuto --%>
          <div class="row">
            <div class="col s12 right-align">
	            <a class="btn red white-text waves-effect waves-light modal-trigger"
	               href="#<c:out value="${idModalRifiuta}"/>">
	              <c:out value="${buttonCommonRifiuta}" />
	              <i class="material-icons right">clear</i>
	            </a>
	            <a class="btn green white-text waves-effect waves-light modal-trigger"
	               href="#<c:out value="${idModalAccetta}"/>">
	              <c:out value="${buttonCommonAccetta}" />
	              <i class="material-icons right">check</i>
	            </a>
            </div>
          </div>
	      </div>
	      
	      
			</li>
		</ul>
		
		
    <%-- Includi modal per rifiuto domanda --%>
    <jsp:include page="/WEB-INF/views/forms/rifiuto-domanda-tirocinio.jsp">
      <jsp:param value="${current.id}" name="idDomanda" />
      <jsp:param value="${idModalRifiuta}" name="idModal" />
    </jsp:include>
    
    
    <%-- Includi modal per accettazione domanda --%>
    <jsp:include page="/WEB-INF/views/forms/accettazione-domanda-tirocinio.jsp">
      <jsp:param value="${current.id}" name="idDomanda" />
      <jsp:param value="${idModalAccetta}" name="idModal" />
    </jsp:include>
    

</c:forEach>