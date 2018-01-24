// Inizializza tutte le select nella pagina
$(document).ready(function() {
  var selects = $('select');
  
  for (var i = 0; i < selects.length; i++) {
	  M.Select.init(selects[i]);
  }
});

// Inizializza tutti i collassabili presenti nella pagina
$(document).ready(function() {
  var collassabili = $('.collapsible');
  
  for (var i = 0; i < collassabili.length; i++) {
	  M.Collapsible.init(collassabili[i]);
  }
});

// Inizializza tutte le tooltip presenti nella pagina
$(document).ready(function() {
  var tooltips = $('.tooltipped');
  for(var i = 0; i < tooltips.length; i++) {
    M.Tooltip.init(tooltips[i]);
  }
});

// Inizializza tutti i modal presenti nella pagina
$(document).ready(function() {
  var modals = $('.modal');
  for(var i = 0; i < modals.length; i++) {
    M.Modal.init(modals[i]);
  }
});