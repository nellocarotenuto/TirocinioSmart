// Funzione che si occupa di inizializzare i collassabili per ogni richiesta mostrata
$(document).ready(function() {
  var collassabili = $('.collapsible');
  
  for (var i = 0; i < collassabili.length; i++) {
	  M.Collapsible.init(collassabili[i]);
  }
});

$(document).ready(function() {
  var tooltips = $('.tooltipped');
  for(var j = 0; j < tooltips.length; j++) {
    M.Tooltip.init(tooltips[j]);
  }
});

$(document).ready(function() {
  var tooltips = $('.modal');
  for(var j = 0; j < tooltips.length; j++) {
    M.Modal.init(tooltips[j]);
  }
});