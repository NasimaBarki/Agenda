package agenda;

/**
 * Gestisce gli errori della classe Agenda
 * @author Nasima Barki
 *
 */

public class AgendaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruisce un'AgendaException con il relativo messaggio d'errore
	 * @param messaggio Contiene la stringa del messaggio d'errore
	 */
    public AgendaException(String messaggio) {
        super(messaggio);
    }
}
