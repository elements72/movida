package movida.cristonilopez.maps.Grafi;

/* ============================================================================
 *  $RCSfile: Arco.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/30 18:52:29 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.9 $
 */

/**
 * Classe astratta per la rappresentazione del generico arco
 * di un grafo orientato. Ogni istanza di <code>Arco</code> &egrave;
 * caratterizzato da un campo informativo <code>info</code>, di tipo generico
 * <code>Object</code>, che consente di associare all'arco stesso informazioni
 * dipendenti dalla specifica applicazione, e da una coppia di campi <code>orig</code> e <code>dest</code>,
 * di tipo <code>Nodo</code>, che indicano i nodi cui l'arco &egrave; incidente.<br>
 * A seconda della particolare
 * rappresentazione dell'albero utilizzata, la classe <code>Arco</code> viene
 * estesa con ulteriori informazioni specifiche come collegamenti
 * ad altri archi o altre informazioni strutturali. Le classi derivate
 * dovranno inoltre fornire un'opportuna realizzazione del metodo
 * <code>contenitore</code>, utilizzato per individuare l'istanza di struttura dati
 * che contiene l'arco su cui il metodo &egrave; invocato. 
 *
 */
public abstract class Arco implements Rif {
	/**
	 * Il contenuto informativo associato a ciascun arco
	 */
	public Object info;
	
	/**
	 * Il nodo di origine dell'arco
	 */
	public Nodo orig;
	
	/**
	 * Il nodo di destinazione dell'arco
	 */
	public Nodo dest;
	
	/**
	 * Costruttore per l'istanziazione di nuovi archi.
	 * 
	 * @param x il nodo di origine del nuovo arco
	 * @param y il nodo di destinazione del nuovo arco
	 * @param i il contenuto informativo da associare al nuovo arco
	 */
	public Arco(Nodo x, Nodo y, Object i) {
		info = i; orig = x; dest = y;
	}
	
	/**
	 * Restituisce il riferimento alla struttura dati contenente l'arco.
	 * 
	 * @return il riferimento alla struttura dati contenente l'arco
	 */
	public abstract Object contenitore();
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