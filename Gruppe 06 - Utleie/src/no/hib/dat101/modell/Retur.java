package no.hib.dat101.modell;

import java.sql.Date;
import java.sql.Time;

/**
 * 
 * @author Kristoffer-Andre Kalliainen
 *
 */
public class Retur extends Reservasjon {
	private Integer retur_id;
	private Time klokke_retur;
	private Date dato_retur;
	private Integer km_stand_retur;
	private Utleie utleie_id;

	/**
	 * Konstrukt�r
	 * 
	 */
	public Retur() {
		this(0, null, null, 0, null);
	}

	/**
	 * Konstrukt�r
	 * 
	 * @param reservasjon_id
	 * @param klokke_retur
	 * @param dato_retur
	 * @param km_stand_retur
	 * @param utleie_id
	 */
	public Retur(Integer retur_id, Time klokke_retur, Date dato_retur, Integer km_stand_retur, Utleie utleie_id) {
		super();
		this.retur_id = retur_id;
		this.klokke_retur = klokke_retur;
		this.dato_retur = dato_retur;
		this.km_stand_retur = km_stand_retur;
		this.utleie_id = utleie_id;
	}

	/**
	 * Oppdaterer kilometer stand p� bilen n�r bilen blir returnert
	 */
	public void oppdaterKmBil() {
		super.getBil().setKm_stand(km_stand_retur);
	}

	/**
	 * Faktura
	 * 
	 * @return Representasjon av en faktura for Retur
	 */
	public String skrivFaktura() {
		// TODO Hva data skal v�re med i en faktura? Har tilgang til Reservasjon ved � bruke superklassen
		return null;
	}

	/**
	 * @return String representasjon av Retur
	 */
	@Override
	public String toString() {
		return "retur_id: " + retur_id + ", klokke_retur: " + klokke_retur + ", dato_retur: " + dato_retur
				+ ", km_stand_retur: " + km_stand_retur + ", utleie_id: " + utleie_id;
	}

	/**
	 * @return henter reservasjon_id
	 */
	public Integer getRetur_id() {
		return retur_id;
	}

	/**
	 * @param reservasjon_id
	 *            setter reservasjon_id
	 */
	public void setRetur_id(Integer retur_id) {
		this.retur_id = retur_id;
	}

	/**
	 * @return henter klokke_retur
	 */
	public Time getKlokke_retur() {
		return klokke_retur;
	}

	/**
	 * @param klokke_retur
	 *            setter klokke_retur
	 */
	public void setKlokke_retur(Time klokke_retur) {
		this.klokke_retur = klokke_retur;
	}

	/**
	 * @return henter dato_retur
	 */
	public Date getDato_retur() {
		return dato_retur;
	}

	/**
	 * @param dato_retur
	 *            setter dato_retur
	 */
	public void setDato_retur(Date dato_retur) {
		this.dato_retur = dato_retur;
	}

	/**
	 * @return henter km_stand_retur
	 */
	public Integer getKm_stand_retur() {
		return km_stand_retur;
	}

	/**
	 * @param km_stand_retur
	 *            setter km_stand_retur
	 */
	public void setKm_stand_retur(Integer km_stand_retur) {
		this.km_stand_retur = km_stand_retur;
	}

	/**
	 * @return henter utleie_id
	 */
	public Utleie getUtleie_id() {
		return utleie_id;
	}

	/**
	 * @param utleie_id
	 *            setter utleie_id
	 */
	public void setUtleie_id(Utleie utleie_id) {
		this.utleie_id = utleie_id;
	}

}