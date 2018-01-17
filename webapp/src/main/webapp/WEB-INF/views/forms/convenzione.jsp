<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="row">
  <div class="col s12">
    <spring:message code="convenzionamentoForm.introduzione.label" />
  </div>
</div>
<div class="row">
	<form:form action="/registrazione/azienda"
	           method="POST"
	           modelAttribute="convenzionamentoForm"
	           novalidate="novalidate"
	           cssClass="col s12" >
    <div class="row">
      <div class="col s12 l6">
        <div class="row heading">
          <div class="col s12">
	          <h5>
	            <spring:message code="registrazioneForm.credenziali.label"/>
	          </h5>
	          <div class="divider">
	          </div>
          </div>
        </div>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">account_circle</i>
						<form:input path="username" id="convenzionamento-username" />
						<label for="convenzionamento-username">
						  <spring:message code="registrazioneForm.username.label" />
						</label>
						<form:errors path="username" cssClass="helper-text" />
		      </div>
		    </div>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">email</i>
            <form:input path="email" id="convenzionamento-email" type="email"/>
            <label for="convenzionamento-email">
              <spring:message code="registrazioneForm.email.label" />
            </label>
            <form:errors path="email" cssClass="helper-text" />
		      </div>
		    </div>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">vpn_key</i>
            <form:password path="password" id="convenzionamento-password"/>
            <label for="convenzionamento-password">
              <spring:message code="registrazioneForm.password.label" />
            </label>
            <form:errors path="password" cssClass="helper-text" />
		      </div>
		    </div>
		    <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:password path="confermaPassword" id="convenzionamento-confermaPassword" />
            <label for="convenzionamento-confermaPassword">
              <spring:message code="registrazioneForm.confermaPassword.label" />
            </label>
            <form:errors path="confermaPassword" cssClass="helper-text" />
		      </div>
		    </div>
      </div>
      <div class="col s12 l6">
        <div class="row heading">
          <div class="col s12">
            <h5>
              <spring:message code="convenzionamentoForm.infoAzienda.label"/>
            </h5>
            <div class="divider"></div>
          </div>
        </div>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">business</i>
            <form:input path="nomeAzienda" id="convenzionamento-nomeAzienda" />
            <label for="convenzionamento-nomeAzienda">
              <spring:message code="convenzionamentoForm.nomeAzienda.label" />
            </label>
            <form:errors path="nomeAzienda" cssClass="helper-text" />
		      </div>
        </div>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:input path="idAzienda" id="convenzionamento-idAzienda" />
            <label for="convenzionamento-idAzienda">
              <spring:message code="convenzionamentoForm.idAzienda.label" />
            </label>
            <form:errors path="idAzienda" cssClass="helper-text" />
		      </div>
        </div>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix"></i>
            <form:input path="partitaIvaAzienda" id="convenzionamento-partitaIvaAzienda" />
            <label for="convenzionamento-partitaIvaAzienda">
              <spring:message code="convenzionamentoForm.partitaIvaAzienda.label" />
            </label>
            <form:errors path="partitaIvaAzienda" cssClass="helper-text" />
		      </div>
        </div>
        <div class="row">
		      <div class="input-field col s12">
		        <i class="material-icons prefix">location_city</i>
            <form:input path="indirizzoAzienda" id="convenzionamento-indirizzoAzienda" />
            <label for="convenzionamento-indirizzoAzienda">
              <spring:message code="convenzionamentoForm.indirizzoAzienda.label" />
            </label>
            <form:errors path="indirizzoAzienda" cssClass="helper-text" />
		      </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">accessible</i>
            <form:select path="senzaBarriere" id="convenzionamento-senzaBarriere">
              <form:option value="true">
                <spring:message code="convenzionamentoForm.senzaBarriere.accessibile"/>
              </form:option>
              <form:option value="false">
                <spring:message code="convenzionamentoForm.senzaBarriere.inaccessibile"/>
              </form:option>
            </form:select>
            <label>
              <spring:message code="convenzionamentoForm.senzaBarriere.label"/>
            </label>
				  </div>
				</div>
        <div class="row heading">
          <div class="col s12">
            <h5>
              <spring:message code="convenzionamentoForm.infoDelegato.label"/>
            </h5>
            <div class="divider">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">face</i>
	          <form:input path="nome" id="convenzionamento-nome" />
	          <label for="convenzionamento-nome">
	            <spring:message code="registrazioneForm.nome.label" />
	          </label>
	          <form:errors path="nome" cssClass="helper-text" />
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix"></i>
            <form:input path="cognome" id="convenzionamento-cognome" />
            <label for="convenzionamento-cognome">
              <spring:message code="registrazioneForm.cognome.label" />
            </label>
            <form:errors path="cognome" cssClass="helper-text" />
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">wc</i>
				    <form:select path="sesso" id="convenzionamento-sesso">
              <form:option value="" disabled="true" selected="selected">
                <spring:message code="form.select.segnaposto"/>
              </form:option>
				      <form:option value="M">
				        <spring:message code="registrazioneForm.sesso.maschile"/>
				      </form:option>
				      <form:option value="F">
				        <spring:message code="registrazioneForm.sesso.femminile"/>
				      </form:option>
				    </form:select>
				    <label>
				      <spring:message code="registrazioneForm.sesso.label"/>
				    </label>
				    <form:errors path="sesso" cssClass="helper-text" />
				  </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <i class="material-icons prefix">phone</i>
            <form:input path="telefono" id="convenzionamento-telefono" />
            <label for="convenzionamento-telefono">
              <spring:message code="registrazioneForm.telefono.label" />
            </label>
            <form:errors path="telefono" cssClass="helper-text" />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col s12 right-align">
        <button class="btn waves-effect waves-light" type="submit" name="action">
          <spring:message code="form.invia.label" />
          <i class="material-icons right">send</i>
        </button>
      </div>
    </div>
	</form:form>
</div>
