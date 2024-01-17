package agenda;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Una classe che contiene i dati di un appuntamento
 * @author Nasima Barki
 *
 */

public class Appuntamento {
	LocalDate data;
	LocalTime orario;
	int durata;
	String nome;
	String luogo;
	
	public Appuntamento(LocalDate data, LocalTime orario, int durata, String nome, String luogo) {
		super();
		this.data = data;
		this.orario = orario;
		this.durata = durata;
		this.nome = nome;
		this.luogo = luogo;
	}

	public LocalDate getData() {
		return data;
	}

	public LocalTime getOrario() {
		return orario;
	}

	public int getDurata() {
		return durata;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public void setOrario(LocalTime orario) {
		this.orario = orario;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}
}