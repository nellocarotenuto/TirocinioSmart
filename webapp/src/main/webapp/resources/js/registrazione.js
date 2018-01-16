// Inizializza le tabs per la selezione del form d'iscrizione da visualizzare
var selettoreFormIscrizioneTabs = document.querySelector('#selettore-form-iscrizione');
var selettoreFormIscrizioneTabsInstance = M.Tabs.init(selettoreFormIscrizioneTabs);

//Inizializza il menù dropdown per la selezione dell'accessibilità nel form di convenzionamento
var convenzionamentoSenzaBarriereSelect = document.querySelector('#convenzionamento-senzaBarriere');
var convenzionamentoSenzaBarriereSelectInstance = M.Select.init(convenzionamentoSenzaBarriereSelect);

// Inizializza il menù dropdown per la selezione del sesso nel form di convenzionamento
var convenzionamentoSessoSelect = document.querySelector('#convenzionamento-sesso');
var convenzionamentoSessoSelectInstance = M.Select.init(convenzionamentoSessoSelect);

//inizializza il menù dropdown per la selezione del sesso nel form di iscrizione studente
var iscrizioneSessoSelect = document.querySelector('#iscrizione-sesso');
var iscrizioneSessoSelectInstance = M.select.init(iscrizioneSessoSelect);

//Inizializza il menù dropdown per la selezione del mese nella data di nascita
var iscrizioneMeseSelect = document.querySelector('#iscrizione-meseDiNascita');
var iscrizioneMeseSelectInstance = M.select.init(iscrizioneMeseSelect);