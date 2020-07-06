package movida.cristonilopez.ordinamento.comparators;

import java.util.Comparator;

import movida.commons.Collaboration;

public class CompareScore implements Comparator<Collaboration>{
    @Override
    public int compare(Collaboration o1, Collaboration o2) {
        return o1.getScore().compareTo(o2.getScore());
    }
}