package agenda;

/**
 * Gestisce gli errori della classe GestioneAgende
 * @author Nasima Barki
 *
 */

public class GestioneAgendeException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruisce un'AgendaException con il relativo messaggio d'errore
	 * @param messaggio Contiene la stringa del messaggio d'errore
	 */
    public GestioneAgendeException(String messaggio) {
        super(messaggio);
    }
}
