# Movida
MOVIDA (MOVIes Data and Algorithms)

https://github.com/elements72/movida

Progetto svillupato da:

**Antonio Lopez: 0000915711**

**Davide Cristoni: 0000880126**

## Introduzione:

Nella nostra implementazione del progetto movida abbiamo implementato:

Due algoritmi di ordinamento:
* **Insertion Sort**
* **Heap Sort** 

Due strutture dati classiche:
* **Alberi 2-3**
* **Array Ordinato**


## Movida parte principale:  
I dati all'interno del progetto sono mantenuti in due strutture distinte:
* **Movies** che mantiene i dati relativi ai film
* **Actors** che mantiene le informazioni relative agli attori

Si è deciso di mantenere per ogni attore i riferimenti ai film nei quali ha recitato/diretto,
per mantenere tali riferimeti sono state adottate le medesime strutture di Movies e Actors. 

A discapito quindi di un maggior utilizzo di memoria e di una maggiore attenzione per quanto riguarda l'aggiornamento o l'aggiunta di nuovi dati abbiamo avuto 
la possibilità di eseguire operazioni come **"searchMoviesStarred", "searchMoviesDirected"**... in modo immediato.

Le operazioni che invece richiedevano un ordinamento dei dati sono state soddisfate utilizzando dei **"Comparators"** che permettono di avere un unico codice per 
l'ordinamento dei dati il quale cambia la sua politica di ordinamento in base al comparator che viene fornito. 

La funzione ```setMap(MapImplementation m)``` modifica la struttura dati da usare, inoltre cambia la struttura dati delle informazioni già caricate se presenti alcune.

La funzione ```loadFromFile(FIle file )``` 


## Movida grafi:

Nella parte riguardante l'estensione grafi del progetto movida ogni attore è visto come un nodo di un grafo ed ogni arco è una collaborazione.

Sono state usate le seguenti strutture per mantenere le informazioni necessarie:
* Hash-Map(java generic) per mantenere i riferimenti ai nostri nodi.
* GrafoLA (modulo della libreria asdlab) per avere una classe che gestisse le operazioni tipiche di un grafo

Per rispondere alle due query "getTeamOf" e "maximizeCollaborationsInTheTeamOf" abbiamo usato:
* Una BFS per ottenere il team di un attore
* L'algoritmo di Prim per i MinimumSpanningTree modificando i pesi dei nostri archi e redendoli negativi,
così facendo abbiamo infatti ottenuto un MaximumSpanningTree

La classe collaboration è stata modificata in modo tale da permettere di aggiungere, rimuovere, ricercare film.
Ogni qualvolta vi è una collaborazione tra due attori A e B viene controllato se questi hanno già collaborato in altri film,
 se così fosse viene aggiunto il nuovo film tra quelli in cui hanno collaborato.

Un arco **(x, y)** contiene il riferimento alla collaborazione tra l'attore x e l'attore y.
Il suo "costo" è dato da "getScore" ovvero la media dei voti ricevuti dai film in cui hanno partecipato insieme. 
