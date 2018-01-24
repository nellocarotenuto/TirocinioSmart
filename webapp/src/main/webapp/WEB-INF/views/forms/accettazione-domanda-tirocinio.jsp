<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabile header modal --%>
<spring:message var="modalTitoloAccettaDomandaTirocinio"
                code="modal.titolo.accettaDomandaTirocinio" />
<spring:message var="modalDescrizioneAccettaDomandaTirocinio"
                code="modal.descrizione.accettaDomandaTirocinio" />


<%-- Etichette campi form --%>
<spring:message var="formLabelCommento" code="form.label.commento" />


<%-- Etichette dei pulsanti --%>
<spring:message var="buttonCommonAccetta" code="button.common.accetta" />
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />


<%-- Definizione del modal --%>
<div id="${param.idModal}" class="modal">
  
  
  <%-- Corpo del modal --%>
  <div class="modal-content">
    <div class="row">
      <div class="col s12">
      
        <%-- Titolo del modal --%>
        <h4>
          <c:out value="${modalTitoloAccettaDomandaTirocinio}" />
        </h4>
        
        <%-- Descrizione del modal --%>
        <p>
          <c:out value="${modalDescrizioneAccettaDomandaTirocinio}" />
        </p>
        
        
        <%-- Form di rifiuto --%>
        <form action="/dashboard/domande/accetta"
              method="POST">
          
          
          <%-- Motivazione --%>
          <div class="row">
            <div class="input-field col s12">
              <i class="material-icons prefix">create</i>
              <textarea id="${param.idModal}-commento"
                        name="commentoAzienda" 
                        class="materialize-textarea"></textarea>
              <label for="${param.idModal}-commento">
                <c:out value="${formLabelCommento}" />
              </label>
            </div>
          </div>
          
          
          <%-- Pulsanti di rifiuto e uscita dal modal --%>
          <div class="row">
            <div class="col s12 right-align">
              <a class="btn-flat waves-effect modal-close">
                <c:out value="${buttonCommonAnnulla}" />
              </a>
              <input type="hidden" name="idDomanda" value="${param.idDomanda}">
              <button class="btn waves-effect waves-light green" type="submit" name="action">
                <c:out value="${buttonCommonAccetta}" />
              </button>
            </div>
          </div>
          
          
        </form>
        
        
      </div>
    </div>
  </div>
  
  
</div>