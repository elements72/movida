package movida.cristonilopez.maps.asdlab.Grafi;

/* ============================================================================
 *  $RCSfile: ArcoLA.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/30 18:52:28 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.4 $
 */

/**
 * Classe utilizzata per l'implementazione di grafi rappresentati mediante liste di adiacenza
 * ed ottenuta derivando la classe {@link Arco}. Ciascuna istanza di <code>ArcoLA</code>
 * mantiene, oltre al contenuto informativo associato all'arco, il riferimento
 * al grafo contenente l'arco, i nodi di origine e destinazione dell'arco stesso 
 * ed il riferimento all'arco predecessore ed all'arco successore nella lista
 * degli archi incidenti al nodo di destinazione.  
 */
public class ArcoLA extends Arco {
	/**
	 * Riferimento all'arco predecessore nella lista degli archi incidenti
	 * al nodo di destinazione
	 */
	public ArcoLA pred;
	
	/**
	 * Riferimento all'arco successore nella lista degli archi incidenti
	 * al nodo di destinazione
	 */
	public ArcoLA succ;
	
	/**
	 * Riferimento al grafo contenente l'arco
	 */
	public Grafo grafo;
	
	/**
	 * Costruttore per l'allocazione di una nuova istanza di ArcoLA.
	 * 
	 * @param x il nodo di origine del nuovo arco
	 * @param y il nodo di destinazione del nuovo arco
	 * @param i il contenuto informativo da associare al nuovo arco
	 */
	public ArcoLA(Nodo x, Nodo y, Object i) { super(x,y,i); }
	
	/**
	 * Restituisce il riferimento al grafo contenente l'arco (<font color=red>Tempo O(1)</font>).
	 * 
	 * @return il grafo contenente l'arco
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