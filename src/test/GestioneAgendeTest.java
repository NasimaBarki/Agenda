package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import agenda.Agenda;
import agenda.AgendaException;
import agenda.GestioneAgende;
import agenda.GestioneAgendeException;

class GestioneAgendeTest {

	private GestioneAgende agende;
	
	@BeforeEach
	void setup() {
		agende = new GestioneAgende();
		GestioneAgende.crea();
	}
	
	@AfterEach
	void reset() {
		GestioneAgende.svuota();
	}
	
	@Test
	void testCrea() {
		assertEquals(0, GestioneAgende.numEls());
	}
	
	@Test
	void testSvuota() {
		GestioneAgende.svuota();
		assertEquals(0, GestioneAgende.numEls());
	}
	
	@Test
	void testCreaAgendaVuota() throws GestioneAgendeException {
		//Controllo agenda senza nome
		String nome = null;
        assertThrows(GestioneAgendeException.class, () -> {GestioneAgende.creaAgendaVuota(nome);});
        assertEquals(0, GestioneAgende.numEls());
		
		//Controllo agende con nome corretto
        GestioneAgende.creaAgendaVuota("Agenda 1");
		assertEquals(1, GestioneAgende.numEls());
		
		//Controllo agenda con stesso nome di una esistente
		assertThrows(GestioneAgendeException.class, () -> {GestioneAgende.creaAgendaVuota("Agenda 1");});
		
		//Controllo agende con nome corretto
		GestioneAgende.creaAgendaVuota("Agenda 2");
		assertEquals(2, GestioneAgende.numEls());
		GestioneAgende.creaAgendaVuota("Agenda 3");
		assertEquals(3, GestioneAgende.numEls());
		GestioneAgende.creaAgendaVuota("Agenda 4");
		assertEquals(4, GestioneAgende.numEls());
		GestioneAgende.creaAgendaVuota("Agenda 5");
		assertEquals(5, GestioneAgende.numEls());
		GestioneAgende.creaAgendaVuota("Agenda 6");
		assertEquals(6, GestioneAgende.numEls());
		
		//Controllo agenda che supera il limite
		assertThrows(GestioneAgendeException.class, () -> {GestioneAgende.creaAgendaVuota("Agenda 7");});
		assertEquals(6, GestioneAgende.numEls());
		
		//Controllo agenda non esistente
		assertEquals(null, GestioneAgende.getAgendaByName("NonEsiste"));
	}
	
	@Test
	void testEliminaAgenda() throws GestioneAgendeException {
		GestioneAgende.creaAgendaVuota("Agenda 1");
		assertEquals(1, GestioneAgende.numEls());
		
		GestioneAgende.eliminaAgenda("Agenda 1");
		assertEquals(0, GestioneAgende.numEls());
		
		assertThrows(GestioneAgendeException.class, () -> {GestioneAgende.eliminaAgenda("Agenda 2");});
	}
	
	@SuppressWarnings("static-access")
	@Test
	void testCreaAgendaDaFile() throws IOException, AgendaException, GestioneAgendeException, ParseException {
		GestioneAgende.creaAgendaVuota("Agenda 1");
		Agenda agenda1 = GestioneAgende.getAgendaByName("Agenda 1");
		assertNotNull(agenda1);
		agenda1.inserisciAppuntamento("09-10-2010", "10-10", "10", "Andrea", "Vercelli");
		agenda1.inserisciAppuntamento("11-11-2010", "11-11", "11", "Paola", "Milano");
		agenda1.inserisciAppuntamento("12-12-2012", "12-12", "12", "Piero", "Novara");
		
		GestioneAgende.scriviAgendaSuFile(agenda1.getNome(), "file.txt");
		
		try (BufferedReader in = new BufferedReader(new FileReader("file.txt"))) {
	        String linea;
	        while ((linea = in.readLine()) != null) {
	            System.out.println(linea);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		GestioneAgende.creaAgendaDaFile("Agenda 2", "file.txt");
		assertThrows(AgendaException.class, () -> {GestioneAgende.creaAgendaDaFile("Agenda 2", "file1.txt");});
		assertThrows(AgendaException.class, () -> {GestioneAgende.creaAgendaDaFile(null, "file.txt");});
		assertThrows(AgendaException.class, () -> {GestioneAgende.creaAgendaDaFile("Agenda 3", null);});
		assertThrows(AgendaException.class, () -> {GestioneAgende.scriviAgendaSuFile("", "file.txt");});
		assertThrows(AgendaException.class, () -> {GestioneAgende.scriviAgendaSuFile(null, "file.txt");});
	}
	
	@SuppressWarnings("static-access")
	@Test
    void testIteratore() throws GestioneAgendeException {
	 	agende.creaAgendaVuota("Agenda 1");
	 	agende.creaAgendaVuota("Agenda 2");
	 	
        int count = 0;
        for (Agenda agenda : agende) {
            assertNotNull(agenda);
            count++;
        }
        assertEquals(2, count);
    }
	
	@Test
    void testEccezioneNoSuchElement() throws GestioneAgendeException {
		agende.creaAgendaVuota("Agenda 1");
	 	agende.creaAgendaVuota("Agenda 2");
	 	
        Iterator<Agenda> iterator = agende.iterator();
        
        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        assertThrows(NoSuchElementException.class, () -> {
            iterator.next();
        });
    }
	 
}
