package movida.cristonilopez.maps.asdlab.Grafi;
import movida.cristonilopez.maps.asdlab.Rif;


/* ============================================================================
 *  $RCSfile: Nodo.java,v $
 * ============================================================================
 * Copyright (C) 2007 Camil Demetrescu, Umberto Ferraro Petrillo,
 *                    Irene Finocchi, Giuseppe F. Italiano
 *  License:          See the end of this file for license information
 *  Created:          
 *  Last changed:   $Date: 2007/03/26 10:43:23 $  
 *  Changed by:     $Author: umbfer $
 *  Revision:       $Revision: 1.12 $
 */

/**
 * Classe astratta per la rappresentazione del generico nodo di strutture
 * dati quali alberi e grafi. <br> 
 * Ogni istanza di <code>Nodo</code> &egrave; caratterizzata da un campo <code>info</code> di tipo generico
 * <code>Object</code>, che consente di associare al nodo stesso informazioni
 * dipendenti dalla specifica applicazione.<p>
 * A seconda della particolare
 * rappresentazione dell'albero utilizzata, la classe <code>Nodo</code> viene
 * estesa con ulteriori informazioni specifiche come collegamenti
 * ad altri nodi o altre informazioni strutturali. Le classi derivate
 * dovranno inoltre fornire un'opportuna realizzazione del metodo
 * <code>contenitore</code>, utilizzato per individuare l'istanza di struttura dati
 * che contiene il nodo su cui il metodo &egrave; invocato. 
 *
 */
public abstract class Nodo implements Rif{
	/**
	 * Il contenuto informativo associato a ciascun nodo
	 */
	public Object info;

	/**
	 * Costruttore per l'istanziazione di nuovi nodi.
	 * 
	 * @param info il contenuto informativo da associare al nuovo nodo
	 */
	public Nodo(Object info) {this.info = info;}
	
	/**
	 * Restituisce il riferimento alla struttura dati contenente il nodo.
	 * 
	 * @return il riferimento alla struttura dati contenente il nodo
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