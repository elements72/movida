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
* **movies** che mantiene i dati relativi ai film
* **actors** che mantiene le informazioni relative agli attori

Si è deciso di mantenere per ogni attore i riferimenti ai film nei quali ha recitato/diretto,
per mantenere tali riferimeti sono state adottate le medesime strutture  **movies** e **actors**. 

A discapito quindi di un maggior utilizzo di memoria e di una maggiore attenzione per quanto riguarda l'aggiornamento o l'aggiunta di nuovi dati abbiamo avuto la possibilità di eseguire operazioni come ```searchDirectedMovies(String title)``` e ```searchStarredMovies(String title)``` in modo immediato. Questo è stato possibile tramite la classe **Actor** che implementa la classe **Person** agiungendo strutture per salvare i film nei quali l'attore ha lavorato oltre che funzioni che gestiscono queste informazioni, come quelle citate sopra.

Le operazioni che invece richiedevano un ordinamento dei dati sono state soddisfate utilizzando dei **"Comparators"** che permettono di avere un unico codice per 
l'ordinamento dei dati il quale cambia la sua politica di ordinamento in base al comparator che viene fornito. 

La funzione ```setMap(MapImplementation m)``` modifica la struttura dati da usare, inoltre cambia la struttura dati delle informazioni già caricate se presenti alcune.

La funzione ```loadFromFile(FIle file )``` legge dati dal file passato come parametro e li aggiunge alle strutture dati. Questa funzione sfrutta una struttura di **Movie** d'appoggio per permettere di non caricare i dati nel caso di lettura di un file di formattazione sconosciuta. Una volta che la lettura è andata a buon fine, dalla struttura temporanea vengono estratte le modifiche da apportare sia a **movies** sia ad **actors** oltre che creare i nodi per le collaborazioni tra attori. Prima di aggiungere un film alla struttura dati principale viene controllato che non sia già presente; in quel caso le informazioni vengono aggiornate alla lettura più recente (il film viene sostituito nella struttura principale e il cast controllato in caso di nuovi partecipanti o persone rimosse). Le operazioni di vera e propria lettura da file sono gestite dalla classe **FileEngine** che, tramite i suoi metodi, estrae i dati dal documento txt, controllandone la formattazione. I dati estratti sono poi convertiti nei tipi o nelle strutture dati sfruttati dalla classe Movie.


## Movida grafi:

Nella parte riguardante l'estensione grafi del progetto movida ogni attore è visto come un nodo di un grafo ed ogni arco è una collaborazione.

Sono state usate le seguenti strutture per mantenere le informazioni necessarie:
* Hash-Map(java generic) per mantenere i riferimenti ai nostri nodi.
* GrafoLA (modulo della libreria asdlab) per avere una classe che gestisse le operazioni tipiche di un grafo

Ogni volta avviene il caricamento di un film dalla funzione ```loadFromFile(FIle file)``` vengono create le collaborazioni tra gli attori di tale film attraverso ```createMovieCollaboration(Movie movie)``` distinguiamo due casi:
*Esiste già una collaborazione: il nuovo film viene aggiunto ai film in cui hanno collaborato.
*Non esiste collaborazione: viene creata una nuova collaborazione.
Ad ogni attore corrisponde un **Nodo** e ad ogni collaborazione un **Arco** (x, y) che unisce due attori, la funzione
```createCollaboration(Person a, Person b, Movie movie)``` si occupa di creare ed aggiornare nodi ed archi.
La funzione di costo **w((x,y))** è definita dal metodo ```getScore()``` presenete in **collaboration**

Nel caso in cui un film viene sovrascritto o eliminato attaverso ```deleteMovieByTitle(String title)``` è necessario aggiornare le collaborazioni tra gli attori, la funzione ```deleteCollaborationsOfMovie(Movie movie)``` si occupa prorpio di questo ed appoggiandosi su ```deleteCollaboration(Person a, Person b, Movie movie)``` elimina **movie** tra i film in cui hanno partecipato entrambi gli attori. Se risulta che la **collaboration** tra due attori non presenta film viene eliminata (e con essa anche l'arco associato), inoltre se un attore non presenta più collaborazioni viene rimosso il suo "nodo" corrispondente. Le collaborazioni vengono solamente create tra attori i direttori sono esclusi

Per rispondere alle due query "getTeamOf" e "maximizeCollaborationsInTheTeamOf" abbiamo usato:
* Una BFS per ottenere il team di un attore
* L'algoritmo di Prim per i MinimumSpanningTree modificando i pesi dei nostri archi e redendoli negativi,
così facendo abbiamo infatti ottenuto un MaximumSpanningTree
