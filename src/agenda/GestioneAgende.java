package agenda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Si occupa della gestione delle agende
 * @author Nasima Barki
 *
 */

public class GestioneAgende implements Iterable<Agenda> {
	private static ArrayList<Agenda> agende;
	/**
	 * Massimo numero di appuntamenti per agenda
	 */
	public static final int MAX_DIM = 6;
	
	/**
	 * Restituisce un iteratore per le agende
	 * @return iteratore per le agende
	 */
	public Iterator<Agenda> iterator() {
        return new GestioneAgendeIterator();
    }
	
	/**
	 * 
	 * Le agende sono iterabili, ma non è possibile rimuoverne una durante l'iterazione
	 *
	 */
	private static class GestioneAgendeIterator implements Iterator<Agenda> {
        private int currentIndex = 0;

        /**
         * Verifica se esistono altre agende da iterare
         * @return true se ci sono altre agende da iterare, false altrimenti
         */
        @Override
        public boolean hasNext() {
            return currentIndex < agende.size();
        }

        /**
         * Restituisce la prossima agenda da iterare
         * @return la prossima agenda nell'iterazione
         * @throws NoSuchElementException se non ci sono altre agende da iterare
         */
        @Override
        public Agenda next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return agende.get(currentIndex++);
        }
    }

	/**
	 * Inizializza il gestore delle agende
	 */
	public static void crea() {
		agende = new ArrayList<Agenda>();
	}
	
	/**
	 * Ritorna il numero di appuntamenti all'interno dell'agenda
	 * @return Il numero delle agende
	 */
	public static Integer numEls() {
		return agende.size();
	}
	
	/**
	 * Svuota il gestore delle agende
	 */
	public static void svuota() {
		agende.clear();
	}
	
	/**
	 * Crea un'agenda vuota partendo dal nome
	 * @param nomeAgenda Nome dell'agenda da creare
	 */
	public static void creaAgendaVuota(String nomeAgenda) throws GestioneAgendeException {
		if(nomeAgenda == null || nomeAgenda.isEmpty()) {
			throw new GestioneAgendeException("Il nome dell'agenda non puo' essere vuoto.");
		}
		else if(agende.size() >= MAX_DIM) {
			throw new GestioneAgendeException("E' stato raggiunto il numero massimo di agende.");
		}
		else if(esisteAgenda(nomeAgenda)) {
			throw new GestioneAgendeException("Esiste già un'agenda con quel nome.");
		}
		else {
			Agenda agenda = new Agenda(nomeAgenda);
			agende.add(agenda);
		}
	}
	
	/**
	 * Controlla se esiste un'agenda con lo stesso nome
	 * @param nomeAgenda nome dell'agenda da controllare
	 * @return vero se esiste un'agenda con quel nome, falso altrimenti
	 */
	private static boolean esisteAgenda(String nomeAgenda) {
		for(Agenda agenda : agende) {
			if(agenda.getNome().equals(nomeAgenda)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Elimina l'agenda anche se vuota
	 * @param nomeAgenda nome dell'agenda da eliminare
	 */
	public static void eliminaAgenda(String nomeAgenda) throws GestioneAgendeException {
		ArrayList<Agenda> copiaAgende = new ArrayList<>(agende);
		
		for(Agenda agenda : copiaAgende) {
			if(agenda.getNome().equals(nomeAgenda)) {
				agende.remove(agenda);
				return;
			}
		}
		throw new GestioneAgendeException("Impossibile eliminare l'agenda.");
	}

	/**
	 * Crea un'agenda partendo da file
	 * @param nomeAgenda
	 * @param nomeFile
	 * @throws IOException
	 * @throws AgendaException
	 * @throws ParseException 
	 */
	@SuppressWarnings("static-access")
	public static void creaAgendaDaFile(String nomeAgenda, String nomeFile) throws IOException, AgendaException, ParseException {
		if (nomeAgenda == null || nomeFile == null) {
	        throw new AgendaException("Il nome dell'agenda o il nome del file non possono essere vuoti.");
	    }
	    if (esisteAgenda(nomeAgenda)) {
	        throw new AgendaException("Esiste già un'agenda con quel nome.");
	    }
	    File file = new File(nomeFile);
	    if (!file.exists()) {
	        throw new IOException("Il file specificato non esiste: " + nomeFile);
	    }

	    Agenda nuovaAgenda = new Agenda(nomeAgenda);

	    BufferedReader in = new BufferedReader (new FileReader(nomeFile));
	    String linea = in.readLine();

	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
	    while(linea != null) {
	    	String[] dati = linea.split(", ");
	    	if(dati.length >= 5) {
	    		String data = dati[0].trim();
	    		String orario = dati[1].trim();
	    		String durata = dati[2].trim();
	    		String nome = dati[3].trim();
	    		String luogo = dati[4].trim();
	    	
		    	Date parsedDate = dateFormat.parse(data);
		    	String formattedDate = dateFormat.format(parsedDate);
		    	
		    	orario = orario.replace(":", "-");
		    	LocalTime localTime = LocalTime.parse(orario, timeFormatter);
		    	String formattedOra = localTime.format(timeFormatter);
		    	
	    		nuovaAgenda.inserisciAppuntamento(formattedDate, formattedOra, durata, nome, luogo);
	    	}
	    	else {
	    		System.out.println("Una linea e' stata scartata perche' non ha tutti i parametri.");
	    	}
	    	linea = in.readLine();
	    }
	    in.close();
	    agende.add(nuovaAgenda);
	}

	/**
	 * Crea un'agenda partendo da un file;
	 * @param nomeAgenda
	 * @param nomeFile
	 * @throws IOException
	 * @throws AgendaException
	 */
	@SuppressWarnings("static-access")
	public static void scriviAgendaSuFile(String nomeAgenda, String nomeFile) throws IOException, AgendaException {
		if (nomeAgenda == null || nomeFile == null) {
	        throw new AgendaException("Il nome dell'agenda o il nome del file non possono essere vuoti.");
	    }

	    if (!esisteAgenda(nomeAgenda)) {
	        throw new AgendaException("Non esiste un'agenda con lo stesso nome.");
	    }
	    
	    PrintWriter out = new PrintWriter(new File(nomeFile));
	    Agenda agenda = getAgendaByName(nomeAgenda);
	    
	    for (Appuntamento appuntamento : agenda.getAppuntamenti()) {
	        LocalDate data = appuntamento.getData();
	        LocalTime orario = appuntamento.getOrario();
	        int durata = appuntamento.getDurata();
	        String nome = appuntamento.getNome();
	        String luogo = appuntamento.getLuogo();

	        String formattedAppointment = String.format("%s, %s, %d, %s, %s%n", data, orario, durata, nome, luogo);
	        out.print(formattedAppointment);
	    }

	    out.printf("%n");
	    out.close();
	}

	/**
	 * Restituisce l'agenda con il nome corrispondente
	 * @param nomeAgenda
	 * @return
	 */
	public static Agenda getAgendaByName(String nomeAgenda) {
		for(Agenda agenda : agende) {
			if(agenda.getNome().equals(nomeAgenda)) {
				return agenda;
			}
		}
		return null;
	}
}
