## info
This is going to be the new CSW Web application!

## file structure
backend:
	database/
		models.py 	| Klassen für Datenbanktabellen
		db.py		| access functions for db
	routers/  		| REST-API-Endpunkte
	auth/	  		| Login, Tokenüberprüfung, Rollen

	main.py	  		| main entrypoint.
	
frontend:
	routes/	  | the different pages
	lib/ 	  | reusable components

## dev
for developing I recomend doing npm install inside /frontend and downloading every module via pip in the requirements.txt
