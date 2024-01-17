package agenda;

/**
 * Gestisce gli errori della classe Appuntamento
 * @author Nasima Barki
 *
 */

public class AppuntamentoException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruisce un'AgendaException con il relativo messaggio d'errore
	 * @param messaggio Contiene la stringa del messaggio d'errore
	 */
    public AppuntamentoException(String messaggio) {
        super(messaggio);
    }
}
