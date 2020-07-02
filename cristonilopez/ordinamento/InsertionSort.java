package movida.cristonilopez.ordinamento;

import java.util.Comparator;

public class InsertionSort {

    /**
     * Ordina l'array secondo l'algoritmo insertion sort,
     * Il comparator è colui che decide secondo quali parametri i nostri oggetti vanno ordinati
     * @param A
     * @param c
     */
    public static<T> void sort(T[] A, Comparator<T> c){
        for (int i = 1; i < A.length; i++) {
            int j = i-1;
            T tmp;
            while(j >= 0 && c.compare(A[j+1], A[j]) < 0){
                tmp = A[j+1];
                A[j+1] = A[j];
                A[j] = tmp;
                j--;
            }
        }
    } 
}