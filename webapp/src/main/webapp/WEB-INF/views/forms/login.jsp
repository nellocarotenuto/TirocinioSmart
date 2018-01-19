<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="row">
  <form:form action="/login"
             method="POST"
             modelAttribute="loginForm"
             novalidate="novalidate"
             cssClass="col s12" >
    <div class="row">
      <div class="row">
	      <div class="input-field col s12">
	        <i class="material-icons prefix">account_circle</i>
	        <form:input path="username" id="login-username" />
	        <label for="login-username">
	          <spring:message code="registrazioneForm.username.label" />
	        </label>
	        <form:errors path="username" cssClass="helper-text" />
	      </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <i class="material-icons prefix">vpn_key</i>
          <form:password path="password" id="login-password" />
          <label for="login-password">
            <spring:message code="registrazioneForm.password.label" />
          </label>
          <form:errors path="password" cssClass="helper-text" />
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col s12 right-align">
        <a class="btn-flat waves-effect modal-close">
          <spring:message code="button.annulla.label" />
        </a>
        <button class="btn waves-effect waves-light" type="submit" name="action">
          <spring:message code="pagina.login.titolo" />
          <i class="material-icons right">send</i>
        </button>
      </div>
    </div>
  </form:form>
</div>