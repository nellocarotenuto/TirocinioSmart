<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
		<footer class="page-footer blue-grey">
		  <div class="container">
		    <div class="row">
		      <div class="col l6 s12">
		        <h5 class="white-text">TirocinioSmart</h5>
		        <p class="grey-text text-lighten-4">
		          <spring:message code="footer.tirocinioSmart.descrizione" />
            </p>
		      </div>
		      <div class="col l4 offset-l2 s12">
		        <h5 class="white-text">
              <spring:message code="footer.tirocinioSmart.autori" />
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
		  <div class="footer-copyright">
		    <div class="container">
		    Prodotto sviluppato con licenza MIT
		    <a class="grey-text text-lighten-4 right"
		       href="https://www.github.com/nellocarotenuto/tirociniosmart">Repository</a>
		    </div>
		  </div>
		</footer>
    
  </body>
</html>