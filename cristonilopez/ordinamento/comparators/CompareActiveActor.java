package movida.cristonilopez.ordinamento.comparators;

import java.util.Comparator;

import movida.cristonilopez.Actor;

public class CompareActiveActor implements Comparator<Actor> {
    @Override
    public int compare(Actor o1, Actor o2) {
        int o1Starred = o1.countMoviesStarred();
        int o2Starred = o2.countMoviesStarred();
        if(o1Starred < o2Starred){
            return -1;
        }
        else if(o1Starred == o2Starred)
            return 0;
        else
            return 1;
    }
    
}