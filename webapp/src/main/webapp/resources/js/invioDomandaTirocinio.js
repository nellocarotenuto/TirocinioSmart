// Funzione che si occupa di inizializzare i collassabili per ogni richiesta mostrata
$(document).ready(function() {
  var dropdowns = $('select');
  
  for (var i = 0; i < dropdowns.length; i++) {
    M.Select.init(dropdowns[i]);
  }
});