package movida.cristonilopez.ordinamento;

import java.util.Comparator;

public class HeapSort {
    
    public static<T> void sort(T[] A, int N, Comparator<T> c){
        int maxIndex = A.length - 1;
        heapify(A, maxIndex, 0, c);
        int i = 0, d = maxIndex;
        while(i < N )
        {
            T temp = findMax(A);
            deleteMax(A, d, c);
            A[d] = temp;
            d--;
            i++;
        }
        System.out.println(i+"<-i N->"+N);
    }

    /** restituisce il valore massimo dell'array
     * 
     * @param <T> Il tipo dell'array da passare come parametro
     * @param A array di tipo T
     * @return il valore massimo dell'array
     */
    private static<T> T findMax(T[] A){
        if(A.length < 1)
            return null;
        else
            return A[0];

    }

    /** Rende un array un maxheap
     * 
     * @param <T> Il tipo dell'array da passare come parametro
     * @param A array di tipo T
     * @param n l'ultimo indice valido dell'array
     * @param i l'indice dell'elemento che diventerà la radice
     * @param c la classe comparator
     */
    private static<T> void fixHeap(T[] A, int n, int i, Comparator<T> c){
        if(i * 2 + 1> n) //se non ha figli, chiudi
            return;
        int figlio = i * 2 + 1;
        if((figlio + 1) <= n && c.compare(A[figlio], A[figlio + 1]) < 0 ){
            figlio++;
        }
        if(c.compare(A[i], A[figlio]) < 0)
        {
            T temp = A[i];
            A[i] = A[figlio];
            A[figlio] = temp;
            fixHeap(A, n, figlio, c);
        }
    }

    /** Rende un array un max-heap
     * 
     * @param <T> Il tipo dell'array da passare come parametro
     * @param A array di tipo T
     * @param n l'ultimo indice valido dell'array
     * @param i l'indice dell'elemento che diventerà la radice
     * @param c la classe comparator
     */
    private static<T> void heapify(T[] A, int n, int i, Comparator<T> c){
        if(i > n)
            return;
        heapify(A, n, i * 2 + 1, c);
        heapify(A, n, i * 2 + 2, c);
        fixHeap(A, n, i, c);
    }

    /** Cancella l'elemento massimo dall'array
     * 
     * @param <T> Il tipo dell'array da passare come parametro
     * @param A array di tipo T
     * @param n l'ultimo indice valido dell'array
     * @param cla classe comparator
     */
    private static<T> void deleteMax(T[] A, int n, Comparator<T> c){
        A[0] = A[n];
        fixHeap(A, n, 0, c);
    }

}