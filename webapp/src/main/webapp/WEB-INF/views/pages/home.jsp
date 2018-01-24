<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili utilizzate per titolo e menù --%>
<spring:message var="titoloPagina" scope="request" code="pagina.home.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.navbar.home" />


<%-- Inclusione dell'header con passaggio del titolo della pagina come parametro --%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
  <jsp:param name="titoloPagina" value="${titoloPagina}"/>
</jsp:include>


<%-- Corpo della pagina --%>
<main>

  <div id="home-slider" class="slider" style="margin-top: -10px;">
    <ul class="slides">
      
      <%-- Slide 1 --%>
      <li>
        <img src="<c:url value="/resources/images/slider/1.jpg" />" >
        <div class="caption right-align">
          <h3>
            <spring:message code="sito.titolo" />
          </h3>
          <h5 class="light grey-text text-lighten-3">
            <spring:message code="home.slide1.sottotitolo" />
          </h5>
        </div>
      </li>
      
      
      <%-- Slide 2 --%>
      <li>
        <img src="<c:url value="/resources/images/slider/2.jpg" />" >
        <div class="caption left-align">
          <h3>
            <spring:message code="home.slide2.titolo" />
          </h3>
          <h5 class="light grey-text text-lighten-3">
            <spring:message code="home.slide2.sottotitolo" />
          </h5>
        </div>
      </li>
      
      
      <%-- Slide 3 --%>
      <li>
        <img src="<c:url value="/resources/images/slider/3.jpg" />" >
        <div class="caption right-align">
          <h3>
            <spring:message code="home.slide3.titolo" />
          </h3>
          <h5 class="light grey-text text-lighten-3">
            <spring:message code="home.slide3.sottotitolo" />
          </h5>
        </div>
      </li>
      
      
      <%-- Slide 4 --%>
      <li>
        <img src="<c:url value="/resources/images/slider/4.jpg" />" >
        <div class="caption left-align">
          <h3>
            <spring:message code="home.slide4.titolo" />
          </h3>
          <h5 class="light grey-text text-lighten-3">
            <spring:message code="home.slide4.sottotitolo" />
          </h5>
        </div>
      </li>
      
      
    </ul>
  </div>
  
  <div class="section white">
    <div class="row container">
      <h5 class="header">
        <em><q><spring:message code="home.sezione1.head" /></q></em>
      </h5>
      <p class="grey-text text-darken-3 lighten-3">
        <spring:message code="home.sezione1.body" />
      </p>
    </div>
    <div class="row container">
      <spring:message code="home.sezione1.visualizzaListaAziende" />
      <a class="btn waves-effect right" href="/aziende">
        <spring:message code="pagina.aziende.titolo" />
      </a>
    </div>
    <div class="row container">
      <spring:message code="home.sezione1.richiestaConvenzionamento" />
      <a class="btn waves-effect right" href="/registrazione#azienda">
        <spring:message code="pagina.registrazione.titolo" />
      </a>
    </div>
  </div>
  <div class="parallax-container">
    <div class="parallax">
      <img src="<c:url value="/resources/images/parallax/campus.jpg" />">
    </div>
  </div>
  
  
  <%-- Inclusione script --%>
  <script type="text/javascript"
          src="<c:url value="/resources/js/home.js" />"></script>
</main>


<%-- Inclusione del footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />