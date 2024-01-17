package test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import agenda.Agenda;
import agenda.AgendaException;
import agenda.Appuntamento;

class AgendaTest {

	@BeforeEach
	void setup() {
		Agenda.crea();
	}

	@AfterEach
	void reset() {
		Agenda.svuota();
	}
	
	@Test
	void testCreazione() {
		assertTrue(Agenda.numEls() == 0);
		Agenda agenda = new Agenda("Agenda");
		assertNotNull(agenda);
		assertEquals(agenda.getNome(), "Agenda");
	}
	
	@Test
	void testSvuota() {
		Agenda.svuota();
		assertEquals(0, Agenda.numEls());
	}
	
	@Test
	void testCreaAppuntamento() throws AgendaException {
		//Appuntamento corretto
		Agenda.inserisciAppuntamento("10-10-2010", "10-10", "10", "Andrea", "Vercelli");
		assertEquals(1, Agenda.numEls());
		
		//Appuntamento errato
		assertThrows(AgendaException.class, () -> {Agenda.inserisciAppuntamento("10/10/2010", "10-10", "10", "Andrea", "Vercelli");});
		assertThrows(AgendaException.class, () -> {Agenda.inserisciAppuntamento("10-10-2010", "10:10", "10", "Andrea", "Vercelli");});
		assertThrows(AgendaException.class, () -> {Agenda.inserisciAppuntamento("10-10-2010", "10-10", "a", "Andrea", "Vercelli");});
		
		//Appuntamento vuoto
		String nome = null;
		assertThrows(AgendaException.class, () -> {Agenda.inserisciAppuntamento("10-10-2010", "10-10", "10", nome, "Vercelli");});
		
		//Appuntamento che si sovrappone con la stessa ora
		assertThrows(AgendaException.class, () -> {Agenda.inserisciAppuntamento("10-10-2010", "10-10", "10", "Paolo", "Vercelli");});
		assertThrows(AgendaException.class, () -> {Agenda.inserisciAppuntamento("10-10-2010", "10-15", "10", "Maria", "Vercelli");});
		
	}

	@Test
	void testModificaAppuntamento() throws AgendaException {
		Agenda.inserisciAppuntamento("10-10-2010", "10-10", "10", "Andrea", "Vercelli");
		Appuntamento appuntamentoDaModificare = Agenda.getAppuntamenti().get(0);
		
		LocalDate data = LocalDate.of(2023, 9, 15);
		LocalTime orario = LocalTime.of(14, 30);
		int durata = 60;
		String nome = "Appuntamento di prova";
		String luogo = "Luogo di prova";
		
		Appuntamento appuntamentoNonEsistente = new Appuntamento(data, orario, durata, nome, luogo);
		
		String modifica = "05-02-2023";
		
		Agenda.modificaAppuntamento(1, appuntamentoDaModificare, modifica);
		Agenda.modificaAppuntamento(2, appuntamentoDaModificare, "15-00");
		Agenda.modificaAppuntamento(3, appuntamentoDaModificare, "50");
		Agenda.modificaAppuntamento(4, appuntamentoDaModificare, "Andrea");
		Agenda.modificaAppuntamento(5, appuntamentoDaModificare, "Vercelli");
		
		//Modifica errata
		assertThrows(AgendaException.class, () -> {Agenda.modificaAppuntamento(6, appuntamentoDaModificare, modifica);});
		assertThrows(AgendaException.class, () -> {Agenda.modificaAppuntamento(1, appuntamentoDaModificare, null);});
		assertThrows(AgendaException.class, () -> {Agenda.modificaAppuntamento(1, appuntamentoDaModificare, "05/02/2023");});
		assertThrows(AgendaException.class, () -> {Agenda.modificaAppuntamento(2, appuntamentoDaModificare, "a");});
		assertThrows(AgendaException.class, () -> {Agenda.modificaAppuntamento(3, appuntamentoDaModificare, "a");});
		assertThrows(AgendaException.class, () -> {Agenda.modificaAppuntamento(3, appuntamentoNonEsistente, "5");});
		
	}
	
	@Test
	void testRicerca() throws AgendaException {
		Agenda.inserisciAppuntamento("10-10-2010", "10-10", "10", "Andrea", "Vercelli");
		Agenda.inserisciAppuntamento("09-10-2010", "10-10", "10", "Andrea", "Vercelli");
		Agenda.inserisciAppuntamento("11-11-2010", "11-11", "11", "Paola", "Milano");
		Agenda.inserisciAppuntamento("12-12-2012", "12-12", "12", "Piero", "Novara");
		
		System.out.println("Appuntamenti con nome \"Andrea\":");
		Agenda.ricercaNome("Andrea");
		System.out.println("Appuntamenti con data \"10-10-2010\":");
		Agenda.ricercaData("10-10-2010");
		
		assertThrows(AgendaException.class, () -> {Agenda.ricercaData("10-10/2010");});
	}
	
	@Test
	void testElencoPerData() throws AgendaException {
		Agenda.inserisciAppuntamento("12-12-2012", "16-00", "12", "Luisa", "Novara");
		Agenda.inserisciAppuntamento("11-11-2010", "11-11", "11", "Paola", "Milano");
		Agenda.inserisciAppuntamento("10-10-2010", "10-10", "10", "Andrea", "Vercelli");
		Agenda.inserisciAppuntamento("12-12-2012", "12-12", "12", "Piero", "Novara");
		
		System.out.println("Appuntamenti ordinati per data:");
		Agenda.ordinaPerData();
	}
	
	@Test
	void testStampa() throws AgendaException {
		Agenda.inserisciAppuntamento("12-12-2012", "16-00", "12", "Luisa", "Novara");
		Agenda.inserisciAppuntamento("11-11-2010", "11-11", "11", "Paola", "Milano");
		Agenda.inserisciAppuntamento("10-10-2010", "10-10", "10", "Andrea", "Vercelli");
		Agenda.inserisciAppuntamento("12-12-2012", "12-12", "12", "Piero", "Novara");
		
		Agenda.stampaAppuntamentiIndex();
	}
}
