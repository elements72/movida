package movida.cristonilopez.maps.albero23;

public class test {
    public static void main(String[] args) {
        Albero23 albero = new Albero23();
        String[] alfabeto = { "antonio", "b", "c", "d", "a", "f", "g", "x", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "h", "y", "z" };
        Integer data = new Integer(1); 
        for (int i = 0; i < 24; i++) {
            albero.insert(data, alfabeto[i]);
        }   
        //albero.printTree();
        System.out.println(albero.search("antonio"));

        //System.out.println(Arrays.toString(albero.toArray()));
    }
    
}