package movida.cristonilopez.maps;

public class Coppia {
    public Object data;
    public String key;

    public Coppia(Object data, String key){
        this.data = data;
        this.key = key;
    }
    public Object getData() {
        return data;
    }
    public String getKey() {
        return key;
    }
    @Override
    public String toString() {
        return "Chiave:" + key.toString() + "  Valore" + data.toString() + "\n";
    }
}