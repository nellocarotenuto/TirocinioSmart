-- Script di inizializzazione da utilizzare SOLO prima dell'installazione della web app.
-- L'avvio dell'applicazione web non richiede l'esecuzione di questo script.

-- Creazione dello schema. Nome NON MODIFICABILE a meno di modifiche in application.properties della webapp
CREATE SCHEMA tirocinosmart;

USE tirociniosmart;

-- Inserimento dell'impiegato dell'ufficio tirocini
-- Specificare i valori dei campi username, password, email, nome e login da utilizzare per accedere all'account
INSERT INTO utente_registrato (username, password, email, nome, cognome)
	   VALUES ('ufficiotirocini', 'ufficiotirocini', 'tirocini@di.unisa.it', 'Ufficio Tirocini', 'Unisa');
INSERT INTO impiegato_ufficio_tirocini (username)
       VALUES ('ufficiotirocini');
       
-- Ripetere le righe superiori tante volte quant'è il numero degli impiegati da voler inserire
-- IMPORTANTE: garantire l'unicità di username ed email