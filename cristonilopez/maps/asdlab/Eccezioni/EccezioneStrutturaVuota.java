package movida.cristonilopez.maps.asdlab.Eccezioni;

public class EccezioneStrutturaVuota extends RuntimeException {

	public EccezioneStrutturaVuota(String messaggioErrore) {
		super(messaggioErrore);
	}

	public EccezioneStrutturaVuota() {
	}
}
