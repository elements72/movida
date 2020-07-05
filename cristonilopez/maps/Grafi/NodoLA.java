package movida.cristonilopez.maps.Grafi;

/* ============================================================================
 *  $RCSfile: NodoLA.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/30 18:52:29 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.7 $
 */

/**
 * Classe utilizzata per l'implementazione di grafi rappresentati mediante liste di adiacenza
 * ed ottenuta derivando la classe {@link Nodo}. Ciascuna istanza di <code>NodoLA</code>
 * mantiene, oltre che il contenuto informativo associato al nodo, il riferimento alla lista
 * degli archi uscenti dal nodo, il riferimento al grafo contenente il nodo, l'indice del nodo
 * stesso ed il numero di archi incidenti.
 * 
 */
public class NodoLA extends Nodo {
	
	/**
	 * Riferimento al primo elemento dell'elenco di archi uscenti dal nodo
	 */
	public ArcoLA adiac;
	
	/**
	 * Riferimento al grafo contenente il nodo
	 */
	public Grafo grafo;
	
	/**
	 * Indice del nodo nel grafo
	 */
	public int indice;
	
	/**
	 * Numero di archi incidenti al nodo
	 */
	public int entranti;

	/**
	 * Costruttore per l'allocazione di una nuova istanza di <code>NodoLA</code>. 
	 * 
	 * @param e contenuto informativo associato al nuovo nodo
	 * @param a riferimento al primo degli archi incidenti al nuovo nodo
	 * @param g riferimento al grafo contenente il nuovo nodo
	 * @param i indice del nuovo nodo nel grafo <code>g</code>
	 */
	public NodoLA(Object e, ArcoLA a, Grafo g, int i) { 
		super(e);
		adiac = a; grafo = g; indice = i;
	}
	
	/**
	 * Restituisce il riferimento al grafo contenente il nodo (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return il riferimento al grafo contenente il nodo
	 */
	public Object contenitore() { return grafo; }
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