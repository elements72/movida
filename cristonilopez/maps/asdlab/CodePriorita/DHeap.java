package movida.cristonilopez.maps.asdlab.CodePriorita;

import movida.cristonilopez.maps.asdlab.Rif;
import movida.cristonilopez.maps.asdlab.Eccezioni.*;

/* ============================================================================
 *  $RCSfile: DHeap.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/26 10:43:42 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.16 $
 */


/**
 *  La classe <code>DHeap</code> implementa il tipo di dato coda con priorit&agrave; mediante
 *  un <em>d</em>-heap. Il <em>d</em>-heap viene realizzato come un albero <em>d</em>-ario di n nodi,
 *  ciascuno contenente una coppia (elem, chiave) che rispetta la propriet&agrave; di ordinamento
 *  a heap (i.e., ciascun nodo v diverso dalla radice ha una chiave non inferiore a quella del suo genitore). 
 *  Il <em>d</em>-albero viene mantenuto mediante una rappresentazione indicizzata basata su un array
 *  <code>nodi</code> contenente i nodi dell'albero ed il cui dimensionamento avviene secondo
 *  la tecnica del raddoppiamento-dimezzamento (doubling-halving).
 *  
 */
public class DHeap implements CodaPriorita {

	/** 
	 * Classe definita localmente alla classe <code>DHeap</code>
	 * per la rappresentazione dei nodi del <em>d</em>-albero.
	 * Memorizza la coppia (elem,chiave) associata a ciascun nodo 
	 * e l'indice della posizione occupata dal nodo stesso nell'array di <code>DHeap</code>
	 * utilizzato per la rappresentazione del <em>d</em>-albero.
	 *
	 */
	public class InfoDHeap implements Rif {
		/**
		 * Elemento da conservare nel <em>d</em>-albero
		 */
		public Object elem;
		
		/**
		 * Chiave associata ad <code>elem</code>
		 */
		public Comparable chiave;
		
		/**
		 * Indice della posizione occupata dal nodo nell'array
		 * usato da <code>DHeap</code> per la rappresentazione del <em>d</em>-albero
		 */
		protected int indice;
		
		/**
		 * Costruttore per l'allocazione di una nuova istanza di <code>InfoDHeap</code>
		 * 
		 * @param e elemento da conservare nella coda
		 * @param k chiave associata ad <code>e</code>
		 * @param i posizione del nodo nell'array utilizzato da <code>DHeap</code> per la rappresentazione del <em>d</em>-albero
		 */
		public InfoDHeap(Object e, Comparable k, int i) {
			elem = e; chiave = k; indice = i;
		}
	}
	
	/**
	 * Array utilizzato per la rappresentazione mediante vettore posizionale del <em>d</em>-albero
	 */
	protected InfoDHeap[] nodi;
	
	/**
	 * Numero di elementi attualmente presenti nella coda
	 */
	protected int n;
	
	/**
	 * Grado del <em>d</em>-albero
	 */
	protected int d;

	/**
	 * Costruttore per l'istanziazione di un nuovo <em>d</em>-heap.
	 * Il grado dell'albero <em>d</em>-ario utilizzato per la rappresentazione
	 * del <em>d</em>-heap sar&agrave; 4.
	 *
	 */
	public DHeap(){
		this(4);
	}

	/**
	 * Costruttore per l'istanziazione di un nuovo <em>d</em>-heap il cui grado &egrave;
	 * indicato da input.
	 * 
	 * @param d il grado dell'albero <em>d</em>-ario utilizzato per la rappresentazione del <em>d</em>-heap
	 */
	public DHeap(int d){
		n = 0;
		nodi = new InfoDHeap[1];
		this.d = d;
	}
	
	/**
	 * Restituisce l'elemento di chiave minima presente nel <em>d</em>-heap (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return l'elemento di chiave minima
	 */	
	public Object findMin() {
		if (isEmpty()) return null;
		return nodi[1].elem;
	}

	/**
	 * Aggiunge al <em>d</em>-heap  un nuovo elemento <code>e</code> con chiave <code>k</code>
	 * (<font color=red>Tempo O(log<sub>d</sub>(n))</font>). L'elemento viene 
	 * inizialmente incapsulato in una nuova istanza di <code>InfoDHeap</code>
	 * e collocato in una foglia nell'ultimo livello dell'albero <em>d</em>-ario.
	 * Dopodich&eacute; viene fatto risalire mediante ripetute chiamate di <code>muoviAlto</code>
	 * sino a che la propriet&agrave; di ordinamento a heap &egrave; rispettata.
	 * 
	 * @param e l'elemento da inserire nella coda
	 * @param k la chiave da associare ad <code>e</code>
	 * @return il riferimento all'oggetto del <em>d</em>-heap che incapsula l'elemento inserito
	 */	
	public InfoDHeap insert(Object e, Comparable k) {
		if (n + 1 == nodi.length) ridimensiona(nodi.length * 2);
		n = n + 1;
		InfoDHeap i = new InfoDHeap(e, k, n);
		nodi[n] = i;
		muoviAlto(i);
		return i;
	}
	
	/**
	 * Cancella dal <em>d</em>-heap l'oggetto <code>v</code> (<font color=red>Tempo O(d &middot;log<sub>d</sub>(n))</font>). L'elemento
	 * viene cancellato dapprima scambiando il suo contenuto informativo
	 * con quello della foglia <code>u</code> pi&ugrave; a destra nell'ultimo livello
	 * del <em>d</em>-albero, dopodich&eacute; rimuovendo dal <em>d</em>-albero
	 * la foglia <code>u</code>. Infine, la propriet&agrave; di ordinamento a heap
	 * viene ripristinata facendo scendere o risalire il nodo <code>v</code> nell'albero
	 * mediante i metodi <code>muoviAlto</code> e <code>muoviBasso</code>.
	 * 
	 * @param v il riferimento all'oggetto del <em>d</em>-heap da cancellare
	 * @return l'elemento incapsulato nell'oggetto del <em>d</em>-heap cancellato
	 */
	public Object delete(Rif v) {
		InfoDHeap u = nodi[n];
		n = n - 1;
		if (isEmpty()) return u.elem;
		scambia((InfoDHeap) v, u);
		if (n == nodi.length/4) 
			ridimensiona(nodi.length/2);
		muoviAlto(u);
		muoviBasso(u);
		return ((InfoDHeap) v).elem;
	}

	/**
	 * Cancella dal <em>d</em>-heap l'elemento con chiave minima e lo restituisce (<font color=red>Tempo O(d &middot;log<sub>d</sub>(n))</font>). 
	 * La cancellazione avviene invocando internamente il metodo <code>delete</code> sul nodo
	 * radice dell'albero. 
	 * 
	 * @return l'elemento di chiave minima
	 */	
	public Object deleteMin() {
		if (isEmpty()) return null;
		InfoDHeap min = nodi[1];
		delete(min);
		return min.elem;
	}
	
	/**
	 * Sostituisce la chiave dell'elemento incapsulato nell'oggetto <code>u</code>
	 * con <code>newKey</code> (<font color=red>Tempo O(d &middot; log<sub>d</sub>(n))</font>). Assume che la nuova chiave abbia un valore non inferiore
	 * a quello della precedente chiave di <code>u</code>. La sostituzione avviene
	 * attribuendo ad <code>u</code> la nuova chiave ed utilizzando il metodo <code>muoviAlto</code>
	 * per ripristinare la propriet&agrave; di ordinamento a heap.
	 * 
	 * @param u il riferimento all'oggetto dell'heap coda cui aggiornare la chiave
	 * @param newKey la nuova chiave da associare ad <code>u</code>
	 * @throws EccezioneChiaveNonValida se <code>newKey</code> ha un valore inferiore a quello della precedente chiave di <code>u</code>
	 */	
	public void increaseKey(Rif u, Comparable newKey) {
		InfoDHeap i = ((InfoDHeap)u);
		if (newKey.compareTo(i.chiave) < 0)
			throw new EccezioneChiaveNonValida();
		i.chiave = newKey;
		muoviBasso(i);
	}

	/**
	 * Sostituisce la chiave dell'elemento incapsulato nell'oggetto <code>u</code>
	 * con <code>newKey</code> (<font color=red>Tempo O(log<sub>d</sub>(n))</font>). Assume che la nuova chiave abbia un valore non superiore
	 * a quello della precedente chiave di <code>u</code>. La sostituzione avviene
	 * attribuendo ad <code>u</code> la nuova chiave ed utilizzando il metodo <code>muoviAlto</code>
	 * per ripristinare la propriet&agrave; di ordinamento a heap.
	 * 
	 * @param u il riferimento all'oggetto dell'heap cui aggiornare la chiave
	 * @param newKey la nuova chiave da associare ad <code>u</code>
	 * @throws EccezioneChiaveNonValida se <code>newKey</code> ha un valore superiore a quello della precedente chiave di <code>u</code>
	 */	
	public void decreaseKey(Rif u, Comparable newKey) {
		InfoDHeap i = ((InfoDHeap)u);
		if (newKey.compareTo(i.chiave) > 0) 
			throw new EccezioneChiaveNonValida();
		i.chiave = newKey;
		muoviAlto(i);
	}

	/**
	 * Operazione non supportata.
	 * 
	 * @throws EccezioneOperazioneNonSupportata se il metodo viene invocato
	 */
	public void merge(CodaPriorita c1) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Verifica se il <em>d</em>-heap &egrave; vuoto (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return <code>true</code>, se la <em>d</em>-heap &egrave; vuoto. <code>false</code>, altrimenti
	 */
	public boolean isEmpty() {
		if (n == 0) return true;
		return false;
	}
	

	/**
	 * Fa risalire un nodo <code>v</code> verso l'alto nel <em>d</em>-heap sino a che
	 * <code>v</code> rispetta la propriet&agrave; di ordinamento a heap (verso il proprio padre).
	 * La risalita avviene mediante il metodo <code>scambia</code>.
	 * 
	 * @param v il nodo da far risalire
	 */
	private void muoviAlto(InfoDHeap v) {
		InfoDHeap p = padre(v);
		while (v.indice > 1 && v.chiave.compareTo(p.chiave) < 0) {
			scambia(v, p);
			p = padre(v);
		}
	}

	/**
	 * Fa scendere un nodo <code>v</code> verso il basso nel <em>d</em>-heap sino a che
	 * <code>v</code> rispetta la propriet&agrave; di ordinamento a heap (verso i proprii figli).
	 * La discesa avviene mediante il metodo <code>scambia</code>.
	 * 
	 * @param v il nodo da far risalire
	 */
	private void muoviBasso(InfoDHeap v) {
		InfoDHeap min = minFiglio(v);
		while((min != null) && (v.chiave.compareTo(min.chiave) > 0)){
			scambia(v, min);
			min = minFiglio(v);			
		}
	}

	/**
	 * Restituisce il nodo con chiave minima tra i figli di <code>v</code>.
	 * 
	 * @param v il nodo di cui si vuole conoscere il figlio con chiave minima
	 * @return il nodo figlio di chiave minima. <code>null</code>, se <code>v</code> non ha figli 
	 */
	private InfoDHeap minFiglio(InfoDHeap v){
		int f = (d * (v.indice - 1)) + 2;
		if (f > n) return null;
		InfoDHeap min = nodi[f];
		for (int i=f+1; (i < f+d) && (i<=n); i++) {
			if (nodi[i].chiave.compareTo(min.chiave) < 0)
				min = nodi[i];
		}
		return min;
	}

	/**
	 * Scambia una coppia di nodi nel <em>d</em>-heap 
	 *  
	 * @param n1 Il primo nodo da scambiare 
	 * @param n2 Il secondo nodo da scambiare
	 */
	private void scambia(InfoDHeap n1, InfoDHeap n2) {
		nodi[n1.indice] = n2;
		nodi[n2.indice] = n1;

		int temp = n1.indice;
		n1.indice = n2.indice;
		n2.indice = temp;
	}
	
	/**
	 * Restituisce il padre di un nodo.
	 * 
	 * @param v il nodo di cui si vuol conoscere il padre
	 * @return il padre di <code>v</code>
	 */
	private InfoDHeap padre(InfoDHeap v) {
		return nodi[(v.indice-2)/d + 1];
	}

	/**
	 * Ridimensiona l'array <code>nodi</code> ad una taglia indicata da input. 
	 * 
	 * @param dim la nuova taglia dell'array <code>nodi</code>
	 */
	private void ridimensiona(int dim) {
		InfoDHeap[] temp = new InfoDHeap[dim];
		System.arraycopy(nodi, 1, temp, 1, n);
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
