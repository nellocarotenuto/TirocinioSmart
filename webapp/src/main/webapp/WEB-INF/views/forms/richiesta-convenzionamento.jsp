<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Etichette pagina --%>
<spring:message var="formConvenzionamentoDescrizione" code="form.convenzionamento.descrizione" />
<spring:message var="formRegistrazioneCredenziali" code="form.registrazione.credenziali" />
<spring:message var="formConvenzionamentoInfoAzienda" code="form.convenzionamento.infoAzienda" />
<spring:message var="formConvenzionamentoInfoDelegato" code="form.convenzionamento.infoDelegato" />


<%-- Etichette campi --%>
<spring:message var="formLabelUsername" code="form.label.username" />
<spring:message var="formLabelEmail" code="form.label.email" />
<spring:message var="formLabelPassword" code="form.label.password" />
<spring:message var="formLabelConfermaPassword" code="form.label.confermaPassword" />
<spring:message var="formLabelNomeAzienda" code="form.label.nomeAzienda" />
<spring:message var="formLabelIdAzienda" code="form.label.idAzienda" />
<spring:message var="formLabelPartitaIvaAzienda" code="form.label.partitaIvaAzienda" />
<spring:message var="formLabelIndirizzoAzienda" code="form.label.indirizzoAzienda" />
<spring:message var="formLabelAccessibilità" code="form.label.accessibilità" />

<spring:message var="formLabelNome" code="form.label.nome" />
<spring:message var="formLabelCognome" code="form.label.cognome" />
<spring:message var="formLabelSesso" code="form.label.sesso" />
<spring:message var="formLabelTelefono" code="form.label.telefono" />

<spring:message var="formPlaceholderGiorno" code="form.placeholder.giorno" />
<spring:message var="formPlaceholderMese" code="form.placeholder.mese" />
<spring:message var="formPlaceholderAnno" code="form.placeholder.anno" />
<spring:message var="formPlaceholderSesso" code="form.placeholder.sesso" />


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonInvia" code="button.common.invia" />


<%-- Preambolo --%>
<div class="row">


  <%-- Descrizione del form --%>
  <div class="col s12">
    <c:out value="${formConvenzionamentoDescrizione}" />
  </div>
  
  
</div>
<div class="row">


  <%-- Form di convenzionamento --%>
	<form:form action="/registrazione/azienda"
	           method="POST"
	           modelAttribute="convenzionamentoForm"
	           novalidate="novalidate"
	           cssClass="col s12" >
    <div class="row">
      <div class="col s12 l6">
        
        
        <%-- Intestazione sezione credenziali --%>
        <div class="row heading">
          <div class="col s12">
	          <h5>
	            <c:out value="${formRegistrazioneCredenziali}" />
	          </h5>
	          <div class="divider">
	          </div>
          </div>
        </div>
        
        
        <%-- Username --%>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">account_circle</i>
						<form:input path="username" id="convenzionamento-username" />
						<label for="convenzionamento-username">
						  <c:out value="${formLabelUsername}" />
						</label>
						<form:errors path="username" cssClass="helper-text" />
		      </div>
		    </div>
		    
		    
		    <%-- E-mail --%>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">email</i>
            <form:input path="email" id="convenzionamento-email" type="email"/>
            <label for="convenzionamento-email">
              <c:out value="${formLabelEmail}" />
            </label>
            <form:errors path="email" cssClass="helper-text" />
		      </div>
		    </div>
		    
		    
		    <%-- Password --%>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">vpn_key</i>
            <form:password path="password" id="convenzionamento-password"/>
            <label for="convenzionamento-password">
              <c:out value="${formLabelPassword}" />
            </label>
            <form:errors path="password" cssClass="helper-text" />
		      </div>
		    </div>
		    
		    
		    <%-- Conferma password --%>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:password path="confermaPassword" id="convenzionamento-conferma-password" />
            <label for="convenzionamento-conferma-password">
              <c:out value="${formLabelConfermaPassword}" />
            </label>
            <form:errors path="confermaPassword" cssClass="helper-text" />
		      </div>
		    </div>
		    
		    
      </div>
      <div class="col s12 l6">
      
      
        <%-- Intestazione sezione informazioni azienda --%>
        <div class="row heading">
          <div class="col s12">
            <h5>
              <c:out value="${formConvenzionamentoInfoAzienda}" />
            </h5>
            <div class="divider"></div>
          </div>
        </div>
        
        
        <%-- Nome dell'azienda --%>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">business</i>
            <form:input path="nomeAzienda" id="convenzionamento-nome-azienda" />
            <label for="convenzionamento-nome-azienda">
              <c:out value="${formLabelNomeAzienda}" />
            </label>
            <form:errors path="nomeAzienda" cssClass="helper-text" />
		      </div>
        </div>
        
        
        <%-- ID dell'azienda --%>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:input path="idAzienda" id="convenzionamento-idAzienda" />
            <label for="convenzionamento-idAzienda">
              <c:out value="${formLabelIdAzienda}" />
            </label>
            <form:errors path="idAzienda" cssClass="helper-text" />
		      </div>
        </div>
        
        
        <%-- Partita IVA dell'azienda --%>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:input path="partitaIvaAzienda" id="convenzionamento-partitaIvaAzienda" />
            <label for="convenzionamento-partitaIvaAzienda">
              <c:out value="${formLabelPartitaIvaAzienda}" />
            </label>
            <form:errors path="partitaIvaAzienda" cssClass="helper-text" />
		      </div>
        </div>
        
        
        <%-- Indirizzo dell'azienda --%>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">location_city</i>
            <form:input path="indirizzoAzienda" id="convenzionamento-indirizzoAzienda" />
            <label for="convenzionamento-indirizzoAzienda">
              <c:out value="${formLabelIndirizzoAzienda}" />
            </label>
            <form:errors path="indirizzoAzienda" cssClass="helper-text" />
		      </div>
        </div>
        
        
        <%-- Accessibilità --%>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">accessible</i>
            <form:select path="senzaBarriere" id="convenzionamento-senzaBarriere">
              <form:option value="true">
                <spring:message code="label.accessibilità.senzaBarriere"/>
              </form:option>
              <form:option value="false">
                <spring:message code="label.accessibilità.conBarriere"/>
              </form:option>
            </form:select>
            <label>
              <c:out value="${formLabelAccessibilità}" />
            </label>
				  </div>
				</div>
				
				
				
				<%-- Intestazione informazioni delegato --%>
        <div class="row heading">
          <div class="col s12">
            <h5>
              <c:out value="${formConvenzionamentoInfoDelegato}" />
            </h5>
            <div class="divider">
            </div>
          </div>
        </div>
        
        
        <%-- Nome del delegato --%>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">face</i>
	          <form:input path="nome" id="convenzionamento-nome" />
	          <label for="convenzionamento-nome">
	            <c:out value="${formLabelNome}" />
	          </label>
	          <form:errors path="nome" cssClass="helper-text" />
          </div>
        </div>
        
        
        <%-- Cognome del delegato --%>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix"></i>
            <form:input path="cognome" id="convenzionamento-cognome" />
            <label for="convenzionamento-cognome">
              <c:out value="${formLabelCognome}" />
            </label>
            <form:errors path="cognome" cssClass="helper-text" />
          </div>
        </div>
        
        
        <%-- Sesso del delegato --%>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">wc</i>
				    <form:select path="sesso" id="convenzionamento-sesso">
              <form:option value="" disabled="true" selected="selected">
                <c:out value="${formPlaceholderSesso}" />
              </form:option>
				      <form:option value="M">
				        <spring:message code="label.sesso.maschile"/>
				      </form:option>
				      <form:option value="F">
				        <spring:message code="label.sesso.femminile"/>
				      </form:option>
				    </form:select>
				    <label>
				      <c:out value="${formLabelSesso}" />
				    </label>
				    <form:errors path="sesso" cssClass="helper-text" />
				  </div>
        </div>
        
        
        <%-- Telefono del delegato --%>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">phone</i>
            <form:input path="telefono" id="convenzionamento-telefono" />
            <label for="convenzionamento-telefono">
              <c:out value="${formLabelTelefono}" />
            </label>
            <form:errors path="telefono" cssClass="helper-text" />
          </div>
        </div>
        
        
      </div>
    </div>
    
    
    <%-- Pulsante d'invio --%>
    <div class="row">
      <div class="col s12 right-align">
        <button class="btn waves-effect waves-light" type="submit" name="action">
          <c:out value="${buttonCommonInvia}" />
          <i class="material-icons right">send</i>
        </button>
      </div>
    </div>
    
    
	</form:form>
</div>