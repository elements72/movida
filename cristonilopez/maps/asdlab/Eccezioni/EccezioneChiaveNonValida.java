package movida.cristonilopez.maps.asdlab.Eccezioni;

public class EccezioneChiaveNonValida extends RuntimeException {

	public EccezioneChiaveNonValida(String messaggioErrore){
		super(messaggioErrore);
	}

	public EccezioneChiaveNonValida() {
	}
}
