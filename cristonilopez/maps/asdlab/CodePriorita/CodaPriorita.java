package movida.cristonilopez.maps.asdlab.CodePriorita;

import movida.cristonilopez.maps.asdlab.Rif;
/* ============================================================================
 *  $RCSfile: CodaPriorita.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/29 11:08:19 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.11 $
 */

/**
 * Una coda con priorit&agrave; &egrave; un tipo di dato che permette di mantenere
 * il minimo (o il massimo) in un insieme di chiavi su cui &egrave; definita
 * una relazione d'ordine totale.<br>
 *  Le operazioni fondamentali che una coda con priorit&agrave; deve realizzare
 *  sono l'inserimento di un nuovo elemento (<code>insert</code>), la ricerca
 *  del minimo (<code>findMin</code>) e l'estrazione del minimo (<code>deleteMin</code>).
 *  Ulteriori operazioni tipicamente supportate sono la cancellazione
 *  di un elemento (<code>delete</code>), l'incremento di una chiave (<code>increaseKey</code>) 
 *  e il decremento di una chiave (<code>decreaseKey</code>).
 *  Le implementazioni pi&ugrave; avanzate permettono anche la fusione (<code>merge</code>)
 *  di due code con priorit&agrave; in una unica coda con priorit&agrave;. <br>
 *  Le operazioni citate sono specificate dall'interfaccia <code>CodaPriorita</code>
 *  che offre una possibile definizione in Java del tipo coda con priorit&agrave;.
 *  Nell'interfaccia <code>CodaPriorita</code> le operazioni <code>delete</code>, <code>increaseKey</code>
 *  e <code>decreaseKey</code> ricevono un riferimento, codificato come oggetto di tipo
 *  <code>Rif</code>, all'elemento della coda su cui devono operare,
 *  mentre invece l'operazione <code>insert</code> restituisce un riferimento 
 *  all'oggetto della coda che incapsula l'elemento inserito. 

 */

public interface CodaPriorita {
	/**
	 * Restituisce l'elemento di chiave minima presente nella coda.
	 * 
	 * @return l'elemento di chiave minima
	 */
	public Object findMin();
	
	/**
	 * Aggiunge alla coda un nuovo elemento <code>e</code> con chiave <code>k</code>.
	 * 
	 * @param e l'elemento da inserire nella coda
	 * @param k la chiave da associare ad <code>e</code>
	 * @return il riferimento all'oggetto della coda che incapsula l'elemento inserito
	 */
	public Rif insert(Object e, Comparable k);
	
	/**
	 * Cancella dalla coda l'oggetto <code>u</code>.
	 * 
	 * @param u il riferimento all'oggetto della coda da cancellare
	 * @return l'elemento incapsulato nell'oggetto della coda cancellato
	 */
	public Object delete(Rif u);
	
	/**
	 * Cancella dalla coda l'elemento con chiave minima e lo restituisce. 
	 * 
	 * @return l'elemento di chiave minima
	 */
	public Object deleteMin();
	
	/**
	 * Sostituisce la chiave dell'elemento incapsulato nell'oggetto <code>u</code>
	 * con <code>newKey</code>. Assume che la nuova chiave abbia un valore non inferiore
	 * a quello della precedente chiave di <code>u</code>.
	 * 
	 * @param u il riferimento all'oggetto della coda cui aggiornare la chiave
	 * @param newKey la nuova chiave da associare ad <code>u</code>
	 */
	public void increaseKey(Rif u, Comparable newKey);
	
	/**
	 * Sostituisce la chiave dell'elemento incapsulato nell'oggetto <code>u</code>
	 * con <code>newKey</code>. Assume che la nuova chiave abbia un valore non superiore
	 * a quello della precedente chiave di <code>u</code>.
	 * 
	 * @param u il riferimento all'oggetto della coda cui aggiornare la chiave
	 * @param newKey la nuova chiave da associare ad <code>u</code>
	 */
	public void decreaseKey(Rif u, Comparable newKey);
	
	/**
	 * Fonde la coda con priorit&agrave; <code>c</code> indicata da input nella coda con priorit&agrave; attuale.
	 * @param c la coda con priorit&agrave; da fondere nella coda attuale
	 */
	public void merge(CodaPriorita c);
	
	/**
	 * Verifica se la coda &egrave; vuota.
	 * 
	 * @return <code>true</code>, se la coda &egrave; vuota. <code>false</code>, altrimenti
	 */
	public boolean isEmpty();

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