package agenda;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Si occupa delle operazioni all'interno dell'agenda
 * @author Nasima Barki
 *
 */

public class Agenda {
	String nome;
	private static ArrayList<Appuntamento> appuntamenti;
	/**
	 * Massimo numero di appuntamenti per agenda
	 */
	public static final int MAX_DIM = 6;
	/**
	 * Il numero di campi all'interno di un appuntamento
	 */
	public static final int MAX_CAMPI_APP = 5;
	
	/**
	 * Inizializza l'agenda 
	 */
	public static void crea() {
		appuntamenti = new ArrayList<Appuntamento>();
	}

	/**
	 * Ritorna il numero di appuntamenti all'interno dell'agenda
	 * @return Il numero delle agende
	 */
	public static int numEls() {
		return appuntamenti.size();
	}

	/**
	 * Svuota l'agenda
	 */
	public static void svuota() {
		appuntamenti.clear();
	}

	/**
	 * Costruttore dell'agenda
	 * @param nome nome dell'agenda
	 */
	public Agenda(String nome) {
		super();
		this.nome = nome;
		appuntamenti = new ArrayList<>();
	}

	/**
	 * Restituisce il nome dell'agenda
	 * @return il nome dell'agenda
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Inserisce un'appuntamento all'agenda controllando i dati e la sovrapposizione degli orari
	 * @param data data dell'appuntamento
	 * @param orario orario dell'appuntamento
	 * @param durata durata dell'appuntamento
	 * @param nome nome della persona con cui si ha l'appuntamento
	 * @param luogo luogo dell'appuntamento
	 * @throws AgendaException se il formato di un qualsiasi dato non è corretto o se avviene una sovrapposizione
	 */
	public static void inserisciAppuntamento(String data, String orario, String durata, String nome, String luogo) throws AgendaException{
		if(data == null || orario == null || durata == null || nome == null || luogo == null) {
			throw new AgendaException("Uno dei campi è vuoto.");
		}
		if(!controlloData(data)) {
			throw new AgendaException("Il formato della data non e' corretto.");
		}
		if(!controlloOrario(orario)) {
			throw new AgendaException("Il formato dell'orario non e' corretto.");
		}
		if(!controlloDurata(durata)) {
			throw new AgendaException("Il formato della durata non e' corretto.");
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataFormat = LocalDate.parse(data, formatter);
        
        formatter = DateTimeFormatter.ofPattern("HH-mm");
        LocalTime orarioFormat = LocalTime.parse(orario, formatter);
        
        int durataAppuntamento = Integer.parseInt(durata);
        
        Appuntamento nuovoAppuntamento = new Appuntamento(dataFormat, orarioFormat, durataAppuntamento, nome, luogo);
        
        if(esisteSovrapposizione(nuovoAppuntamento)) {
        	throw new AgendaException("L'appuntamento si sovrappone con uno già esistente.");
        }
        
        appuntamenti.add(nuovoAppuntamento);
	}

	/**
	 * Verifica se esiste una sovrapposizione con gli appuntamenti esistenti
	 * @param nuovoAppuntamento
	 * @return vero se c'è una sovrapposizione, false altrimenti
	 */
	private static boolean esisteSovrapposizione(Appuntamento nuovoAppuntamento) {
	    for (Appuntamento appuntamentoEsistente : appuntamenti) {
	        if (appuntamentoEsistente.getData().equals(nuovoAppuntamento.getData()) &&
	            orariSovrapposti(appuntamentoEsistente.getOrario(), nuovoAppuntamento.getOrario(),
	                             appuntamentoEsistente.getDurata(), nuovoAppuntamento.getDurata())) {
	            return true;
	        }
	    }
	    return false;
	}

	/**
	 * Verifica se c'è sovrapposizione tra gli orari
	 * @param orario1
	 * @param orario2
	 * @param durata1
	 * @param durata2
	 * @return
	 */
	private static boolean orariSovrapposti(LocalTime orario1, LocalTime orario2, int durata1, int durata2) {
	    LocalTime fine1 = orario1.plusMinutes(durata1);
	    LocalTime fine2 = orario2.plusMinutes(durata2);

	    return (orario1.isBefore(fine2) && fine1.isAfter(orario2));
	}

	/**
	 * Controlla il formato della durata
	 * @param durata
	 * @return
	 */
	private static boolean controlloDurata(String durata) {
		try {
	        int durataInt = Integer.parseInt(durata);
	        return durataInt > 0;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

	/**
	 * Controlla il formato dell'orario
	 * @param orario
	 * @return
	 */
	private static boolean controlloOrario(String orario) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm");
            @SuppressWarnings("unused")
			LocalTime localTime = LocalTime.parse(orario, formatter);
            return true;
		} catch(DateTimeParseException e) {
			return false;
		}
	}

	/**
	 * Controlla il formato della data
	 * @param data
	 * @return
	 */
	private static boolean controlloData(String data) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            @SuppressWarnings("unused")
			LocalDate localDate = LocalDate.parse(data, formatter);
            return true;
		} catch(DateTimeParseException e) {
			return false;
		}
	}

	/**
	 * Modifica uno dei campi dell'appuntamento
	 * @param indiceCampo
	 * @param appuntamento
	 * @param modifica
	 * @throws AgendaException
	 */
	public static void modificaAppuntamento(int indiceCampo, Appuntamento appuntamento, String modifica) throws AgendaException {
		if(indiceCampo > MAX_CAMPI_APP || indiceCampo <= 0) {
			throw new AgendaException("Il campo non esiste.");
		}
		if(modifica == null) {
			throw new AgendaException("Il campo modifica non può essere vuoto.");
		}
		
		 boolean appuntamentoFound = esisteAppuntamento(
	        appuntamento.getData(),
	        appuntamento.getOrario(),
	        appuntamento.getDurata(),
	        appuntamento.getNome(),
	        appuntamento.getLuogo()
	    );

	    if (!appuntamentoFound) {
	        throw new AgendaException("L'appuntamento da modificare non esiste.");
	    }
			    
		if(indiceCampo == 1) {
			if(!controlloData(modifica)) {
				throw new AgendaException("Il formato della data non e' corretto.");
			}
			else {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		        LocalDate dataFormat = LocalDate.parse(modifica, formatter);
		        appuntamento.setData(dataFormat);
			}
		}
		else if(indiceCampo == 2) {
			if(!controlloOrario(modifica)) {
				throw new AgendaException("Il formato dell'orario non e' corretto.");
			}
			else {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm");
		        LocalTime orarioFormat = LocalTime.parse(modifica, formatter);
		        appuntamento.setOrario(orarioFormat);
			}
		}
		else if(indiceCampo == 3) {
			if(!controlloDurata(modifica)) {
				throw new AgendaException("Il formato della durata non e' corretto.");
			}
			else {
				int durataAppuntamento = Integer.parseInt(modifica);
				appuntamento.setDurata(durataAppuntamento);
			}
		}
		else if(indiceCampo == 4) {
			appuntamento.setNome(modifica);
		}
		else {
			appuntamento.setLuogo(modifica);
		}
	}

	/**
	 * Controlla se l'appuntamento esiste
	 * @param data
	 * @param orario
	 * @param durata
	 * @param nome
	 * @param luogo
	 * @return true se l'appuntamento esiste, false altrimenti
	 */
	public static boolean esisteAppuntamento(LocalDate data, LocalTime orario, int durata, String nome, String luogo) {
	    for (Appuntamento appuntamento : appuntamenti) {
	        if (appuntamento.getData().equals(data) &&
	            appuntamento.getOrario().equals(orario) &&
	            appuntamento.getDurata() == durata &&
	            appuntamento.getNome().equals(nome) &&
	            appuntamento.getLuogo().equals(luogo)) {
	            return true; 
	        }
	    }
	    return false; 
	}

	/**
	 * Ritorna la lista di appuntamenti
	 * @return la lista di appuntamenti
	 */
	public static ArrayList<Appuntamento> getAppuntamenti() {
		return appuntamenti;
	}

	/**
	 * Ricerca degli appuntamenti per data
	 * @param data
	 * @throws AgendaException se il formato non è corretto
	 */
	public static void ricercaData(String data) throws AgendaException {
		if(!controlloData(data)) {
			throw new AgendaException("Il formato della data non e' corretto.");
		}
		
		ArrayList<Appuntamento> ricerca = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataFormat = LocalDate.parse(data, formatter);
        
        for (Appuntamento appuntamento : appuntamenti) {
            if (appuntamento.getData().isEqual(dataFormat)) {
                ricerca.add(appuntamento);
            }
        }

        stampaInformazioni(ricerca);
	}

	/**
	 * Ricerca degli appuntamenti per nome
	 * @param nome
	 */
	public static void ricercaNome(String nome) {
		ArrayList<Appuntamento> ricerca = new ArrayList<>();
        
		for (Appuntamento appuntamento : appuntamenti) {
            if (appuntamento.getNome().equalsIgnoreCase(nome)) {
                ricerca.add(appuntamento);
            }
        }

		stampaInformazioni(ricerca);
	}
	
	/**
	 * Stampa informazioni degli appuntamenti
	 * @param ricerca la stampa avviene dopo la funzione di ricerca
	 */
	private static void stampaInformazioni(ArrayList<Appuntamento> ricerca) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm");
	    
		for (Appuntamento appuntamento : ricerca) {
			String formattedData = appuntamento.getData().format(dateFormatter);
	        String formattedOrario = appuntamento.getOrario().format(timeFormatter);

	        System.out.println("Data: " + formattedData);
	        System.out.println("Orario: " + formattedOrario);
	        System.out.println("Durata: " + appuntamento.getDurata());
	        System.out.println("Nome: " + appuntamento.getNome());
	        System.out.println("Luogo: " + appuntamento.getLuogo());
	        System.out.println(); // Linea vuota tra gli appuntamenti
	    }
	}

	/**
	 * Stampa gli appuntamenti indicando l'indice all'inizio
	 */
	public static void stampaAppuntamentiIndex() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm");
	    
	    int i = 0;
		for (Appuntamento appuntamento : appuntamenti) {
			String formattedData = appuntamento.getData().format(dateFormatter);
	        String formattedOrario = appuntamento.getOrario().format(timeFormatter);

	        System.out.println("Indice: " + i);
	        System.out.println("Data: " + formattedData);
	        System.out.println("Orario: " + formattedOrario);
	        System.out.println("Durata: " + appuntamento.getDurata());
	        System.out.println("Nome: " + appuntamento.getNome());
	        System.out.println("Luogo: " + appuntamento.getLuogo());
	        System.out.println(); // Linea vuota tra gli appuntamenti
	        i++;
	    }
	}
	
	/**
	 * Viene fatta una copia degli appuntamenti e vengono ordinati per data
	 */
	public static void ordinaPerData() {
		 ArrayList<Appuntamento> appuntamentiOrdinati = new ArrayList<>(appuntamenti);

	    Collections.sort(appuntamentiOrdinati, new Comparator<Appuntamento>() {
	        @Override
	        /**
	         * Paragona gli appuntamenti per ordinarli per data e poi per orario
	         */
	        public int compare(Appuntamento app1, Appuntamento app2) {
	            int result = app1.getData().compareTo(app2.getData());
	            if (result == 0) {
	                result = app1.getOrario().compareTo(app2.getOrario());
	            }
	            return result;
	        }
	    });
	    
	    stampaInformazioni(appuntamentiOrdinati);
	}
}
