package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import agenda.Appuntamento;

class AppuntamentoTest {

	@Test
    public void testCostruttoreEGetter() {
        LocalDate data = LocalDate.of(2023, 9, 15);
        LocalTime orario = LocalTime.of(14, 30);
        int durata = 60;
        String nome = "Appuntamento di prova";
        String luogo = "Luogo di prova";

        Appuntamento appuntamento = new Appuntamento(data, orario, durata, nome, luogo);

        assertEquals(data, appuntamento.getData());
        assertEquals(orario, appuntamento.getOrario());
        assertEquals(durata, appuntamento.getDurata());
        assertEquals(nome, appuntamento.getNome());
        assertEquals(luogo, appuntamento.getLuogo());
    }

    @Test
    public void testSetter() {
        Appuntamento appuntamento = new Appuntamento(LocalDate.now(), LocalTime.now(), 30, "Appuntamento 1", "Luogo 1");

        LocalDate newData = LocalDate.of(2023, 10, 1);
        LocalTime newOrario = LocalTime.of(15, 0);
        int newDurata = 45;
        String newNome = "Nuovo Appuntamento";
        String newLuogo = "Nuovo Luogo";

        appuntamento.setData(newData);
        appuntamento.setOrario(newOrario);
        appuntamento.setDurata(newDurata);
        appuntamento.setNome(newNome);
        appuntamento.setLuogo(newLuogo);

        assertEquals(newData, appuntamento.getData());
        assertEquals(newOrario, appuntamento.getOrario());
        assertEquals(newDurata, appuntamento.getDurata());
        assertEquals(newNome, appuntamento.getNome());
        assertEquals(newLuogo, appuntamento.getLuogo());
    }

}
