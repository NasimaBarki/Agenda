package agenda;

import java.io.IOException;
import java.text.ParseException;

import jbook.util.Input;

/**
 * Classe per l'interazione dell'utente con le agende
 * @author Nasima Barki
 *
 */

public class AgendaMain {
	/**
	 * Main per l'interfaccia con l'utente
	 * @param args
	 * @throws AgendaException
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws AgendaException {
		GestioneAgende.crea();
		
		boolean esecuzione = true;
		while(esecuzione) {
			stampaOperazioniAgende();
			String input = Input.readString();
			
			switch(input) {
			case "1":
				System.out.println("Inserire il nome dell'agenda da creare: ");
				input = Input.readString();
				try {
					GestioneAgende.creaAgendaVuota(input);
					System.out.println("L'agenda e' stata creata.");
				} catch (GestioneAgendeException e) {
					e.printStackTrace();
				}
				break;
			case "2":
				System.out.println("Inserire il nome dell'agenda da eliminare: ");
				input = Input.readString();
				try {
					GestioneAgende.eliminaAgenda(input);
					System.out.println("L'agenda e' stata eliminata.");
				} catch (GestioneAgendeException e) {
					e.printStackTrace();
				}
				break;
			case "3":
				System.out.println("Inserire il nome dell'agenda da creare: ");
				input = Input.readString();
				String nomeAgenda = input;
				System.out.println("Inserire il nome del file: ");
				input = Input.readString();
				String nomeFile = input;
				try {
					GestioneAgende.creaAgendaDaFile(nomeAgenda, nomeFile);
					System.out.println("L'agenda e' stata creata");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AgendaException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			case "4":
				System.out.println("Inserire il nome dell'agenda: ");
				input = Input.readString();
				String nomeAgendaEsistente = input;
				System.out.println("Inserire il nome del file su cui esportare l'agenda: ");
				input = Input.readString();
				String fileEsporta = input;
				try {
					GestioneAgende.scriviAgendaSuFile(nomeAgendaEsistente, fileEsporta);
					System.out.println("L'agenda e' stata esportata su file.");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AgendaException e) {
					e.printStackTrace();
				}
				break;
			case "5":
				System.out.println("Inserire il nome dell'agenda: ");
				input = Input.readString();
				if(GestioneAgende.getAgendaByName(input) == null) {
					System.out.println("Non esiste un'agenda con quel nome.");
					break;
				} else {
					Agenda agenda = GestioneAgende.getAgendaByName(input);
					System.out.println("Inserire la data dell'appuntamento: ");
					input = Input.readString();
					String data = input;
					System.out.println("Inserire l'orario dell'appuntamento: ");
					input = Input.readString();
					String orario = input;
					System.out.println("Inserire la durata dell'appuntamento in minuti: ");
					input = Input.readString();
					String durata = input;
					System.out.println("Inserire il nome della persona con cui si ha l'appuntamento: ");
					input = Input.readString();
					String nome = input;
					System.out.println("Inserire il luogo dell'appuntamento: ");
					input = Input.readString();
					String luogo = input;
					
					try {
						agenda.inserisciAppuntamento(data, orario, durata, nome, luogo);
						System.out.println("L'appuntamento e' stato creato.");
					} catch (AgendaException e) {
						e.printStackTrace();
					}
				}
				break;
			case "6":
				System.out.println("Inserire il nome dell'agenda: ");
				input = Input.readString();
				if(GestioneAgende.getAgendaByName(input) == null) {
					System.out.println("Non esiste un'agenda con quel nome.");
					break;
				} else {
					Agenda agenda = GestioneAgende.getAgendaByName(input);
					agenda.stampaAppuntamentiIndex();
					System.out.println("Inserire l'indice di uno degli appuntamenti indicati sopra per modificarlo: ");
					input = Input.readString();
					int index = Integer.parseInt(input);
					Appuntamento appuntamentoDaModificare = Agenda.getAppuntamenti().get(index);
					
					stampaCampi();
					input = Input.readString();
					String opzione = input;
					System.out.println("Inserire la modifica: ");
					input = Input.readString();
					try {
						agenda.modificaAppuntamento(Integer.parseInt(opzione), appuntamentoDaModificare, input);
						System.out.println("L'appuntamento è stato modificato.");
					} catch(AgendaException e) {
						e.printStackTrace();
					}
					break;
				}
			case "7":
				System.out.println("Inserire il nome dell'agenda: ");
				input = Input.readString();
				if(GestioneAgende.getAgendaByName(input) == null) {
					System.out.println("Non esiste un'agenda con quel nome.");
					break;
				} else {
					Agenda agenda = GestioneAgende.getAgendaByName(input);
					System.out.println("Inserire 1 per eseguire la ricerca per data; inserire 2 per la ricerca per nome.");
					input = Input.readString();
					String opzione = input;
					switch(opzione) {
					case "1":
						System.out.println("Inserire la data da cercare: ");
						input = Input.readString();
						try {
							agenda.ricercaData(input);
						} catch (AgendaException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "2":
						System.out.println("Inserire il nome della persona da cercare: ");
						input = Input.readString();
						agenda.ricercaNome(input);
						break;
					default:
						System.out.println("Non e' stata selezionata un'opzione valida.");
						break;
					}
				break;
			}
			case "8":
				System.out.println("Inserire il nome dell'agenda: ");
				input = Input.readString();
				if(GestioneAgende.getAgendaByName(input) == null) {
					System.out.println("Non esiste un'agenda con quel nome.");
					break;
				} else {
					Agenda agenda = GestioneAgende.getAgendaByName(input);
					agenda.ordinaPerData();
					break;
				}
			case "9":
				esecuzione = false;
				break;
			default:
				System.out.println("Errore, inserire un'opzione valida.");
			}
		}
	}

	private static void stampaOperazioniAgende() {
		System.out.println();
		System.out.println("1. Crea agenda vuota");
		System.out.println("2. Cancella agenda");
		System.out.println("3. Crea agenda da file");
		System.out.println("4. Esporta agenda su file");
		System.out.println("5. Inserisci un nuovo appuntamento su un'agenda");
		System.out.println("6. Modifica l'appuntamento di un'agenda");
		System.out.println("7. Cerca un'appuntamento in un'agenda");
		System.out.println("8. Elenca gli appuntamenti di un'agenda ordinandoli per data");
		System.out.println("9. Esci dall'applicazione");
		System.out.println();
	}
	
	private static void stampaCampi() {
		System.out.println();
		System.out.println("1. Modifica la data");
		System.out.println("2. Modifica l'orario");
		System.out.println("3. Modifica la durata");
		System.out.println("4. Modifica il nome della persona");
		System.out.println("5. Modifica il luogo");
		System.out.println();
	}
}
