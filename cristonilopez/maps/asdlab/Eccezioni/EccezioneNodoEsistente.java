package movida.cristonilopez.maps.asdlab.Eccezioni;

public class EccezioneNodoEsistente extends RuntimeException {

	public EccezioneNodoEsistente(String messaggioErrore) {
		super(messaggioErrore);
	}
	
	public EccezioneNodoEsistente() {
	}
}
