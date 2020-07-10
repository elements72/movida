package movida.cristonilopez.maps.asdlab.Grafi;

import java.util.*;

/* ============================================================================
 *  $RCSfile: GrafoLA.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/04/07 20:53:17 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.12 $
 */
/**
 * La classe <code>GrafoLA</code> implementa l'interfaccia <code>Grafo</code>
 * usando una rappresentazione basata su liste di adiacenza. A tale scopo,
 * essa mantiene un array, <code>nodi</code>, nel quale viene memorizzato
 * l'elenco dei nodi attualmente presenti nel grafo. Ciascun nodo <code>v</code>, 
 * implementato mediante la classe <code>NodiLA</code>, memorizza
 * il puntatore alla lista degli archi ad esso adiacenti, il grafo di appartenenza,
 * l'indice intero corrispondente e il numero di archi entranti. L'array <code>nodi</code>
 * viene dimensionato secondo la tecnica del raddoppiamento-dimezzamento (doubling-halving).<br>
 *  Durante la vita della struttura dati, viene mantenuta la propriet&agrave;
 *  invariante che il riferimento al nodo <code>v</code> di indice <code>i</code>
 *  &egrave; memorizzato in <code>nodi[i]</code>. E' quindi 
 *  possibile ottenere l'indice di un nodo <code>v</code> accedendo
 *  alla variabile di istanza <code>v.indice</code>, ed &egrave; possibile conoscere
 *  il nodo di indice <code>i</code> accedendo al valore <code>nodi[i]</code>.<br>
 * 
 */

public class GrafoLA implements Grafo {

	/**
	 * Il numero di nodi attualmente presenti nel grafo
	 */
	protected int n;
	
	/**
	 *  Il numero di archi attualmente presenti nel grafo
	 */
	protected int m;
	
	/**
	 * Array utilizzato per memorizzare l'elenco dei nodi
	 * attualmente presenti nel grafo. La sua dimensione
	 * iniziale &egrave; pari ad 1.
	 */
	protected NodoLA[] nodi = new NodoLA[1];

	/**
	 * Restituisce il numero di nodi presenti nel grafo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return il numero di nodi del grafo
	 */
	public int numNodi() { return n; }

	/**
	 * Restituisce il numero di archi presenti nel grafo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return il numero di archi del grafo
	 */
	public int numArchi() { return m; }

	/**
	 * Restituisce l'elenco dei nodi presenti nel grafo (<font color=red>Tempo O(n)</font>).
	 * L'elenco viene costruito come copia dell'array <code>nodi</code>.
	 * 
	 * @return l'elenco dei nodi del grafo
	 */
	public Nodo[] nodi() { 
		NodoLA[] v = new NodoLA[n];
		System.arraycopy(nodi, 0, v, 0, n);
		return v; 
	}

	/**
	 * Restituisce l'elenco degli archi presenti nel grafo (<font color=red>Tempo O(m)</font>).
	 * L'elenco viene costruito scorrendo l'elenco dei nodi 
	 * del grafo ed includendo tutti gli archi originanti
	 * da ciascuno di essi.
	 * 
	 * @return l'elenco degli archi nel grafo
	 */
	public Arco[] archi(){
		List l = new LinkedList();
		for (int i = 0; i < n; i++)
			l.addAll(archiUscenti(nodi[i]));
		return (Arco[])l.toArray(new ArcoLA[m]);
	}

	/**
	 * Restituisce il numero di archi (grado) uscenti dal nodo di input <code>v</code> (<font color=red>Tempo O(grado uscente di v)</font>).
	 * Il risultato viene determinato ottenendo dal metodo <code>archiUscenti</code>
	 * l'elenco degli archi uscenti da <code>v</code>, e misurandone la taglia.
	 * 
	 * @param v il nodo di cui si vuol conoscere il grado uscente
	 * @return il grado uscente di <code>v</code>
	 */
	public int gradoUscente(Nodo v){
		return archiUscenti(v).size();
	}

	/**
	 * Restituisce il contenuto informativo di un nodo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v il nodo di cui si vuol conoscere il contenuto informativo
	 * @return il contenuto informativo di <code>v</code>
	 */
	public Object infoNodo(Nodo v) { return v.info; }

	/**
	 * Restituisce il contenuto informativo di un arco (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param a l'arco di cui si vuol conoscere il contenuto informativo
	 * @return il contenuto informativo di <code>a</code>
	 */
	public Object infoArco(Arco a) { return a.info; }

	/**
	 * Restituisce l'elenco degli archi uscenti dal nodo <code>v</code> (<font color=red>Tempo O(grado uscente di v)</font>).
	 * L'elenco viene determinato scorrendo la lista degli archi uscenti
	 * da <code>v</code> e verificando, per ciascuno di essi, se il nodo di
	 * destinazione &egrave; stato marcato come cancellato dal metodo {@link GrafoLA#rimuoviNodo(Nodo)} .
	 * In caso affermativo, l'arco viene rimosso dal grafo mediante il metodo <code>rimuoviArco</code>
	 * altrimenti, l'arco viene aggiunto all'elenco degli archi uscenti da <code>v</code>.
	 * 
	 * @param v il nodo di cui si vuol conoscere l'elenco degli archi uscenti
	 * @return l'elenco degli archi uscenti da <code>v</code>
	 */	
	public List archiUscenti(Nodo v){
		List l = new LinkedList();
		ArcoLA a = ((NodoLA)v).adiac; 
		while (a != null) {
			ArcoLA succ = a.succ;
			if (((NodoLA)a.dest).grafo != null) l.add(a);
			else rimuoviArco(a);
			a = succ;
		}
		return l;
	}
	
	/**
	 * Verifica se due nodi <code>x</code> ed <code>y</code> sono adiacenti e, in caso affermativo,
	 * restituisce l'arco <code>(x,y)</code> (<font color=red>Tempo O(grado uscente di x)</font>). L'operazione viene
	 * realizzata ottenendo l'elenco degli archi uscenti da <code>x</code>
	 * e verificando se, tra questi, ne esiste uno entrante in <code>y</code>.
	 * 
	 * @param x il primo nodo di cui verificare l'adiacenza
	 * @param y il secondo nodo di cui verificare l'adiacenza
	 * @return l'arco <code>(x,y)</code>, se <code>x</code> ed <code>y</code> sono adiacenti. <code>null</code>, altrimenti.
	 */
	public Arco sonoAdiacenti(Nodo x, Nodo y){
		Iterator i = archiUscenti(x).iterator();
		while (i.hasNext()){
			Arco a = (Arco)i.next();
			if (a.dest == y) return a;
		}
		return null;
	}
	
	/**
	 * Modifica il contenuto informativo di un nodo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v il nodo di cui modificare il contenuto informativo
 	 * @param info il nuovo contenuto informativo di <code>v</code>
	 */
	public void cambiaInfoNodo(Nodo v, Object info){
		v.info = info;
	}

	/**
	 * Modifica il contenuto informativo di un arco (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param a l'arco di cui modificare il contenuto informativo
	 * @param info il nuovo contenuto informativo di <code>a</code>
	 */
	public void cambiaInfoArco(Arco a, Object info){
		a.info = info;
	}

	/**
	 * Restituisce il nodo con indice <code>i</code> (<font color=red>Tempo O(1)</font>). L'operazione
	 * viene realizzata restituendo il nodo esistente
	 * alla posizione <code>i</code> di <code>nodi</code>.
	 * 
	 * @param i l'indice di cui si vuol conoscere il nodo
	 * @return il nodo di indice <code>i</code>
	 */
	public Nodo nodo(int i){
		if (i < 0 || i >= n) return null;
		return nodi[i];
	}

	/**
	 * Restituisce l'indice di un nodo <code>v</code> (<font color=red>Tempo O(1)</font>).
	 * 
	 * @param v il nodo di cui si vuol conoscere l'indice
	 * @return l'indice di <code>v</code>
	 */
	public int indice(Nodo v){
		return ((NodoLA)v).indice;
	}

	/**
	 * Inserisce un nuovo nodo con contenuto informativo <code>info</code> (<font color=red>Tempo O(1)</font>).
	 * L'inserimento avviene creando una nuova istanza di <code>NodoLA</code>
	 * e collocandola nella prima posizione libera di <code>nodi</code>.
	 * Nel caso in cui l'array <code>nodi</code> sia pieno, si esegue il metodo
	 * <code>ridimensiona</code> per raddoppiarne la taglia. 
	 * 
	 * @param info il contenuto informativo del nuovo nodo
	 * @return il riferimento al nodo inserito
	 */
	public Nodo aggiungiNodo(Object info){
		NodoLA v = new NodoLA(info, null, this, n);
		if (n == nodi.length) ridimensiona(n);
		nodi[n] = v;
		n = n + 1;
		return v;
	}

	/**
	 * Inserisce un nuovo arco <code>(x,y)</code> con contenuto informativo <code>info</code> (<font color=red>Tempo O(1)</font>).
	 * L'operazione avviene creando una nuova istanza di <code>ArcoLA</code>,
	 * aggiungendo il nuovo arco alla lista di archi uscenti da <code>x</code>
	 * ed incrementando il conteggio degli archi entranti in <code>y</code>.
	 * 
	 * @param x il nodo origine dell'arco da inserire
	 * @param y il nodo destinazione dell'arco da inserire 
	 * @param info il contenuto informativo del nuovo arco
	 * @return il riferimento all'arco inserito
	 */	
	public Arco aggiungiArco(Nodo x, Nodo y, Object info){
		NodoLA u = (NodoLA)x;
		ArcoLA a = new ArcoLA(x, y, info);
		if (u.adiac != null) u.adiac.pred = a;
		a.succ = u.adiac;
		u.adiac = a;
		((NodoLA)y).entranti++;
		m = m + 1;
		return a;
	}

	/**
	 * Cancella dal grafo il nodo <code>v</code> e tutti gli archi ad 
	 * esso incidenti (<font color=red>Tempo O(grado di v)</font>). L'eliminazione di <code>v</code> dalle liste di adiacenza
	 * dei nodi cui &egrave; connesso da un arco in uscita avviene solo virtualmente
	 * ed &egrave; effettivamente portata a termine solo durante 
	 * l'invocazione del metodo {@link GrafoLA#archiUscenti(Nodo)} su ciascuno di questi nodi. 
	 * Tuttavia, il conteggio <code>m</code> del numero di archi presenti nel grafo
	 * viene aggiornato gi&agrave; in questa fase decrementandolo del numero di
	 * archi entranti nel nodo <code>v</code>. La rimozione avviene:
	 * <ul>
	 * <li> scorrendo la lista degli archi incidenti a <code>v</code>
	 * ed eliminandoli tramite il metodo <code>rimuoviArco</code></li>
	 * <li> marcando come cancellato <code>v</code> attraverso l'assegnamento a <code>null</code> del suo campo <code>grafo</code> </li>
	 * <li> sostituendo nell'array <code>nodi</code> l'elemento <code>v</code>
	 * con l'ultimo elemento ad occupare lo stesso array <code>nodi</code> e
	 * riducendo la taglia di <code>nodi</code> per escluderne l'ultimo elemento </li>
	 * <li> aggiornando di conseguenza il conteggio del numero di nodi e di archi presenti nel grafo</li>
	 * <ul>
	 * 
	 * @param v il nodo da cancellare
	 */
	public void rimuoviNodo(Nodo v){
		Iterator i = archiUscenti(v).iterator();
		while (i.hasNext()) 
			rimuoviArco((Arco)i.next());
		m = m - ((NodoLA)v).entranti;
		((NodoLA)v).grafo = null;
		nodi[indice(v)] = nodi[n-1];
		nodi[indice(v)].indice = indice(v);
		n = n - 1;
		if (n > 1 && n == nodi.length / 4) 
			ridimensiona(n);
	}

	/**
	 * Cancella dal grafo l'arco <code>a=(x,y)</code> indicato da input (<font color=red>Tempo O(grado uscente di x)</font>). L'operazione
	 * viene realizzata rimuovendo <code>a</code> dalla lista degli archi
	 * uscenti da <code>x</code> e decrementando di 1 il conteggio
	 * degli archi entranti in <code>y</code>. Il conteggio
	 * del numero complessivo di archi presenti nel grafo viene decrementato
	 * di 1 nel solo caso in cui il nodo  <code>y</code> non sia stato in precedenza marcato come cancellato. 
	 * 
	 * @param a l'arco da cancellare
	 */
	public void rimuoviArco(Arco a){
		NodoLA x = (NodoLA)a.orig;
		NodoLA y = (NodoLA)a.dest;
		ArcoLA r = (ArcoLA)a;
		if (x.adiac == a) x.adiac = r.succ;
		if (r.succ != null) r.succ.pred = r.pred;
		if (r.pred != null) r.pred.succ = r.succ;
		y.entranti--;
		if (y.grafo != null) m = m - 1;
	}

	/**
	 * Ridimensiona l'array <code>nodi</code> ad una taglia indicata da input. 
	 * 
	 * @param n la nuova taglia dell'array <code>nodi</code>
	 */
	private void ridimensiona(int n){
		NodoLA[] temp = new NodoLA[2*n];
		System.arraycopy(nodi, 0, temp, 0, n);
		nodi = temp;
	}
}

/*
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo, Irene
 * Finocchi, Giuseppe F. Italiano
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */