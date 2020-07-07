package movida.cristonilopez.maps;

import java.lang.reflect.Array;

public class ArrayOrdinato<T> implements Dizionario<T> {

    Coppia[] S = new Coppia[0];

    Class typeClass;

    public ArrayOrdinato(Class<T> typeClass){
        this.typeClass = typeClass;
    }

    @Override
    public void insert(T element, String key) {
        Coppia[] temp = new Coppia[S.length];
        for(int i = 0, j = 0; j < S.length; i++, j++) 
        {
            if(key.compareToIgnoreCase(S[j].getKey()) <= 0 && i == j){ 
                temp[i] = new Coppia(element, key);
                i++;
            }
            temp[i] = S[j];
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
        if(S.length == 0){
            return null;
        }
        else
        {
            T[] ris = (T[]) Array.newInstance(typeClass, S.length);
            for(int i = 0; i < S.length; i++){
                ris[i] = (T) S[i].getData();
            }
            return ris;
        }
    }
    
}