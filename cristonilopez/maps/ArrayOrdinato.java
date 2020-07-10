package movida.cristonilopez.maps;

import java.lang.reflect.Array;

public class ArrayOrdinato<T> implements Dizionario<T> {

    Coppia[] S = new Coppia[0];

    Class<T> typeClass;

    /**
     * Costrutore dell'array ordinato
     * 
     * @param typeClass Classe del tipo non primitivo che userà la struttura
     */
    public ArrayOrdinato(Class<T> typeClass){
        this.typeClass = typeClass;
    }

   @Override
    public void insert(T element, String key) { 
        Coppia[] temp = new Coppia[S.length + 1];
        if(S.length == 0){ //inserimento del primo elemento
            temp[0] = new Coppia(element, key);
        } 
        else
        {
            int i = cerca(key, 0, S.length - 1);
            if(i < S.length && key.compareToIgnoreCase(S[i].getKey()) == 0)
            {
                S[i] = new Coppia(element, key);
                return;
            }
            else
            {
                int t = 0, j = 0;
                for(; j < S.length; t++, j++) //potrei renderlo un while a questo punto ..
                {
                    if(i == j){ //abbiamo raggiunto la casella dove va inserito il nuovo elemento
                        temp[t] = new Coppia(element, key);
                        t++;
                    }
                    temp[t] = S[j];
                }
                if(i == S.length){ //nel caso la posizione da inserire la coppia sia la casella extra rispeto a S
                    temp[i] = new Coppia(element, key);
                }
            }
        }
        S = temp;
    }

    @Override
    public boolean delete(String key) {
        if(S.length == 0){
            return false;
        }
        else
        {
            Coppia[] temp = new Coppia[S.length - 1];
            int i = 0, j = 0;
            for(; j < S.length; i++, j++) //e se facessi una ricerca dicotomica?
            {
                if(key.compareToIgnoreCase(S[j].getKey()) == 0){ //trova l'elemento
                    j++;
                }
                try{
                temp[i] = S[j]; //se l'elemento non viene trovato, all'ultimo ciclo si genererà un eccezione
                } catch(ArrayIndexOutOfBoundsException e){
                    return false;
                }
            }
            S = temp;
            return true;
        }
    }

    @Override
    public T search(String key) {
        if(S.length == 0){
            return null;
        }
        else
        {
            for(int i = 0; i < S.length; i++)
            {
                if(key.compareToIgnoreCase(S[i].getKey()) == 0){
                    return (T) S[i].getData();
                }
            }
            return null;
        }
    }

    @Override
    public int count() {
        return S.length;
    }

    @Override
    public T[] toArray() {
            T[] ris = (T[]) Array.newInstance(typeClass, S.length);
            for(int i = 0; i < S.length; i++){
                ris[i] = (T) S[i].getData();
            }
            return ris;

    }
    
    /** Cerca un elemento nell'array e ne restituisce l'indice dov'è contenuto
     *  o l'indice dove andrebbe inserito
     * 
     * @param key la stringa che verrà cercata
     * @param i l'indice minimo dell'array
     * @param j l'indice massimo dell'array
     * @return l'indice della casella contenente la key o l'indice dove la key andrebbe inserita
     */
    private int cerca(String key, int i, int j){ 
        if(S.length == 0 || i > j){
            return i;
        }
        else{
            int temp = (i + j) / 2; // j è a lunghezza dell'array, noi vogliamo il l'indice massimo
            int compare = key.compareToIgnoreCase(S[temp].getKey());
            if(compare == 0){
                return temp;
            } else if(compare < 0){
                return cerca(key, i, temp - 1);
            } else {
                return cerca(key, temp + 1, j);
            }
        }
    }

}