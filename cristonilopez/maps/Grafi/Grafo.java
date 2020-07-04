package movida.cristonilopez.maps.Grafi;

import java.util.*;

/* ============================================================================
 *  $RCSfile: Grafo.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/30 18:52:29 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.12 $
 */

/**
 * Il tipo di dato Grafo, descritto dall'interfaccia <code>Grafo</code>, 
 * rappresenta un grafo orientato contenente n nodi ed m archi. 
 * Per riferirci ai nodi e agli archi del grafo usiamo rispettivamente i tipi nodo e arco,
 * specificati dalle interfacce <code>Nodo</code> ed <code>Arco</code>:
 * i riferimenti vengono generati dalle operazioni che creano nodi o archi
 * (<code>aggiungiNodo</code> e <code>aggiungiArco</code>), e possono essere
 * in seguito utilizzati dalle altre operazioni. Osserviamo che l'operazione
 * <code>sonoAdiacenti</code> riceve i riferimenti a due nodi <code>x</code>
 * e <code>y</code>, e, nel caso in cui essi siano adacenti, &egrave;
 * in grado di recuperare e restituire il riferimento all'arco corrispondente.<br>
 * Se indichiamo con <code>n</code> il numero di nodi del grafo, &egrave;
 * naturale, e spesso conveniente, pensare che i nodi siano indicizzati
 * con numeri interi in [0, n - 1]. I due metodi <code>nodo</code> ed <code>indice</code>
 * realizzano questa corrispondenza. Ciascun nodo e ciascun arco del grafo possiede
 * inoltre un contenuto informativo cui &egrave; possibile accedere (<code>infoNodo</code>
 *  e <code>InfoArco</code>) e che pu&ograve; essere modificato (<code>cambiInfoNodo</code> e <code>cambiaInfoArco</code>).
  */

public interface Grafo {

	/**
	 * Restituisce il numero di nodi presenti nel grafo.
	 * 
	 * @return il numero di nodi del grafo
	 */
	public int numNodi();
	
	/**
	 * Restituisce il numero di archi presenti nel grafo.
	 * 
	 * @return il numero di archi del grafo
	 */
	public int numArchi();
	
	/**
	 * Restituisce l'elenco dei nodi presenti nel grafo.
	 * 
	 * @return l'elenco dei nodi del grafo
	 */
	public Nodo[] nodi();
	
	/**
	 * Restituisce l'elenco degli archi presenti nel grafo.
	 * 
	 * @return l'elenco degli archi nel grafo
	 */
	public Arco[] archi();
	
	/**
	 * Restituisce il numero di archi (grado) uscenti dal nodo di input.
	 * 
	 * @param v il nodo di cui si vuol conoscere il grado uscente
	 * @return il grado uscente di <code>v</code>
	 */
	public int gradoUscente(Nodo v);
	
	/**
	 * Restituisce il contenuto informativo di un nodo.
	 * 
	 * @param v il nodo di cui si vuol conoscere il contenuto informativo
	 * @return il contenuto informativo di <code>v</code>
	 */
	public Object infoNodo(Nodo v);
	
	/**
	 * Restituisce il contenuto informativo di un arco.
	 * 
	 * @param a l'arco di cui si vuol conoscere il contenuto informativo
	 * @return il contenuto informativo di <code>a</code>
	 */
	public Object infoArco(Arco a);
	
	/**
	 * Restituisce l'elenco degli archi uscente dal nodo di input.
	 * 
	 * @param v il nodo di cui si vuol conoscere l'elenco degli archi uscenti
	 * @return l'elenco degli archi uscenti da <code>v</code>
	 */
	public List archiUscenti(Nodo v);
	
	/**
	 * Verifica se due nodi <code>x</code> ed <code>y</code> sono adiacenti e, in caso affermativo,
	 * restituisce l'arco <code>(x,y)</code>. 
	 * 
	 * @param x il primo nodo di cui verificare l'adiacenza
	 * @param y il secondo nodo di cui verificare l'adiacenza
	 * @return l'arco <code>(x,y)</code>, se <code>x</code> ed <code>y</code> sono adiacenti. <code>null</code>, altrimenti.
	 */
	public Arco sonoAdiacenti(Nodo x, Nodo y);
	
	/**
	 * Modifica il contenuto informativo di un nodo.
	 * 
	 * @param v il nodo di cui modificare il contenuto informativo
 	 * @param info il nuovo contenuto informativo di <code>v</code>
	 */
	public void cambiaInfoNodo(Nodo v, Object info);
	
	/**
	 * Modifica il contenuto informativo di un arco.
	 * 
	 * @param a l'arco di cui modificare il contenuto informativo
	 * @param info il nuovo contenuto informativo di <code>a</code>
	 */
	public void cambiaInfoArco(Arco a, Object info);
	
	/**
	 * Restituisce il nodo con indice <code>i</code>.
	 * 
	 * @param i l'indice di cui si vuol conoscere il nodo
	 * @return il nodo di indice <code>i</code>
	 */
	public Nodo nodo(int i);
	
	/**
	 * Restituisce l'indice di un nodo <code>v</code>.
	 * 
	 * @param v il nodo di cui si vuol conoscere l'indice
	 * @return l'indice di <code>v</code>
	 */
	public int indice(Nodo v);
	
	/**
	 * Inserisce un nuovo nodo con contenuto informativo <code>info</code>.
	 * 
	 * @param info il contenuto informativo del nuovo nodo
	 * @return il riferimento al nodo inserito
	 */
	public Nodo aggiungiNodo(Object info);
	
	/**
	 * Inserisce un nuovo arco <code>(x,y)</code> con contenuto informativo info.
	 * 
	 * @param x il nodo origine dell'arco da inserire
	 * @param y il nodo destinazione dell'arco da inserire 
	 * @param info il contenuto informativo del nuovo arco
	 * @return il riferimento all'arco inserito
	 */
	public Arco aggiungiArco(Nodo x, Nodo y, Object info);
	
	/**
	 * Cancella dal grafo il nodo indicato da input e tutti gli archi ad 
	 * esso incidenti.
	 * 
	 * @param v il nodo da cancellare
	 */
	public void rimuoviNodo(Nodo v);
	
	/**
	 * Cancella dal grafo l'arco indicato da input.
	 * 
	 * @param e l'arco da cancellare
	 */
	public void rimuoviArco(Arco e);
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