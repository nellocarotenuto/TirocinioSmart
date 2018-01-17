// Funzione che si occupa di inizializzare i collassabili per ogni richiesta mostrata conoscendo
// il numero di richieste presenti nella pagina
$(document).ready(function() {
  var totaleRichieste = document.querySelector('#numero-richieste').value;
  var collassabili = [];
  for (var i = 0; i < totaleRichieste; i++) {
	  collassabili[i] = document.querySelector('#iscrizioni-richiesta-' + i);
	  M.Collapsible.init(collassabili[i]);
  }
});