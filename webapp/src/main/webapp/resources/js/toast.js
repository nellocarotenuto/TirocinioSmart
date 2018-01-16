function mostraToast(testo) {
  if(testo == "") {
	return
  }
  
  $(document).ready(function() {
    M.toast({html: testo})
  });
}

