// Inizializzazione slider
$(document).ready(function () {
	var homeSlider = $('#home-slider');
	M.Slider.init(homeSlider, {indicators : false});
});

// Inizializzazione parallax
$(document).ready(function () {
	var parallax = $('.parallax');
	M.Parallax.init(parallax);
});