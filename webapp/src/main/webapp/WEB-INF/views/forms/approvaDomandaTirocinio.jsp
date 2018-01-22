<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="row">
  <div class="col s12">
    <h4>
      <spring:message code="pagina.approvaDomandaTirocinio.titolo" />
    </h4>
    <p><spring:message code="domandaTirocinio.approva.messaggio" /></p>
    <form action="/dashboard/domande/approva"
          method="POST">
          
      <div class="row">
        <div class="col s12 right-align">
          <a class="btn-flat waves-effect modal-close">
            <spring:message code="button.annulla.label" />
          </a>
          <input type="hidden" name="idDomanda" value="${param.idDomanda}">
          <button class="btn waves-effect waves-light green" type="submit" name="action">
            <spring:message code="form.approva.label" />
          </button>
        </div>
      </div>
    </form>
  </div>
</div>