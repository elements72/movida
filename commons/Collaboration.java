package movida.commons;

import java.util.ArrayList;

public class Collaboration {

	Person actorA;
	Person actorB;
	ArrayList<Movie> movies;
	
	public Collaboration(Person actorA, Person actorB) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = new ArrayList<Movie>();
	}

	public Person getActorA() {
		return actorA;
	}

	public Person getActorB() {
		return actorB;
	}

	public Double getScore(){
		
		Double score = 0.0;
		
		for (Movie m : movies)
			score += m.getVotes();
		
		return score / movies.size();
	}

	public void addMovie(Movie movie){
		movies.add(movie);
	}

	public void deleteMovie(Movie movie){
		movies.remove(movie);
	}

	public boolean isEmpty(){
		return movies.isEmpty();
	}

	public boolean searchMovie(Movie movie){
		return movies.indexOf(movie) == -1 ? false : true;
	}

	@Override
	public String toString() {
		return actorA.getName() + "+" + actorB.getName() + "  score:"+ getScore()+"\n";
	}
	
}
