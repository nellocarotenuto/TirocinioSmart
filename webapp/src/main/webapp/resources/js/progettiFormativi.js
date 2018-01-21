$(document).ready(function() {
  var collassabili = $('.collapsible');
  
  for (var i = 0; i < collassabili.length; i++) {
	  M.Collapsible.init(collassabili[i]);
  }
});

$(document).ready(function() {
  var tooltips = $('.tooltipped');
  for(var i = 0; i < tooltips.length; i++) {
    M.Tooltip.init(tooltips[i]);
  }
});

$(document).ready(function() {
  var modals = $('.modal');
  for(var i = 0; i < modals.length; i++) {
    M.Modal.init(modals[i]);
  }
});


