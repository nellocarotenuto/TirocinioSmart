<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
 
 <%-- Etichette --%>
 <spring:message var="sitoTitolo" code="sito.titolo" />
 <spring:message var="sitoDescrizione" code="sito.descrizione" />
 <spring:message var="sitoAutori" code="sito.autori" />
 <spring:message var="sitoLicenza" code="sito.licenza" />
 <spring:message var="sitoRepository" code="sito.repository" />
 
 
    <%-- Definizione footer --%>
		<footer class="page-footer blue-grey">
		  <div class="container">
		    
		    
		    <%-- Riga principale --%>
		    <div class="row">
		     
		      
		      <%-- Descrizione del sito --%>
		      <div class="col l6 s12">
		        <h5 class="white-text">
              <c:out value="${sitoTitolo}" />
            </h5>
		        <p class="grey-text text-lighten-4">
		          <c:out value="${sitoDescrizione}" />
            </p>
		      </div>
		      
		      
		      <%-- Autori --%>
		      <div class="col l4 offset-l2 s12">
		        <h5 class="white-text">
              <c:out value="${sitoAutori}" />
            </h5>
		        <ul>
		          <li>
		            <a class="grey-text text-lighten-3"
		               href="https://www.github.com/nellocarotenuto">Aniello Carotenuto</a>
		          </li>
		          <li>
                <a class="grey-text text-lighten-3"
                   href="https://www.github.com/peppe3ds">Giuseppe Dello Stretto</a>
              </li>
		          <li>
                <a class="grey-text text-lighten-3"
                   href="https://www.github.com/rcoccaro3">Rosanna Coccaro</a>
              </li>
		        </ul>
		      </div>
		      
		      
		    </div>
		  </div>
		  
		  
		  <%-- Copyright e informazioni aggiuntive --%>
		  <div class="footer-copyright">
		    <div class="container">
			    <c:out value="${sitoLicenza}" />
			    <a class="grey-text text-lighten-4 right"
			       href="https://www.github.com/nellocarotenuto/tirociniosmart">
	          <c:out value="${sitoRepository}" />
	        </a>
		    </div>
		    
		    
		  </div>
		</footer>
    
    
  </body>
</html>