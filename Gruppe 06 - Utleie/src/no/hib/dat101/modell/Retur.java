package no.hib.dat101.modell;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.RollbackException;
import javax.persistence.Table;
import javax.persistence.Transient;

import no.hib.dat101.ui.SelskapUI;

/**
 * 
 * @author Kristoffer-Andre Kalliainen
 *
 */
@Entity
@Table(name = "retur", schema = "bilutleie")
public class Retur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer retur_id;
	@Column(name = "klokke_retur")
	private Time klokke_retur;
	@Column(name = "dato_retur")
	private Date dato_retur;
	@Column(name = "km_stand_retur")
	private Integer km_stand_retur;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "utleie_id", referencedColumnName = "utleie_id")
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

	public void info() {
		setKlokke_retur(utleie_id.getReservasjon().getUi().lesInnKlokkeslett());
		setDato_retur(utleie_id.getReservasjon().getUi().lesInnDato());
		oppdaterKmBil();
		oppdaterUtleiekontor();
		utleie_id.getReservasjon().getUi().skrivFaktura(lagFaktura());
	}
	
	public void infoFerdig() {
		setKlokke_retur(Time.valueOf("13:00:00"));
		setDato_retur(Date.valueOf("2017-04-24"));
		km_stand_retur = 45000;
		utleie_id.getReservasjon().getBil().setKm_stand(km_stand_retur);
		oppdaterUtleiekontor();
		utleie_id.getReservasjon().getUi().skrivFaktura(lagFaktura());
	}

	public void lastOppReturDB(EntityManager em) {
		try {
			em.getTransaction().begin();
			em.persist(this);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			em.getTransaction().rollback();
		}
	}

	/**
	 * Oppdaterer kilometer stand p� bilen n�r bilen blir returnert
	 */
	private void oppdaterKmBil() {
		km_stand_retur = utleie_id.getReservasjon().getUi().lesInnKm_stand();
		utleie_id.getReservasjon().getBil().setKm_stand(km_stand_retur);
	}

	/**
	 * Faktura
	 * 
	 * @return Representasjon av en faktura for Retur
	 */
	private ArrayList<String> lagFaktura() {
		ArrayList<String> faktura = new ArrayList<String>();
		faktura.add("Reservasjon: " + utleie_id.getReservasjon().getReservasjon_id().toString());
		faktura.add("Kundenummer: " + utleie_id.getReservasjon().getKundenummer().getKundenummer().toString());
		faktura.add("Dato for utl�n: " + utleie_id.getReservasjon().getDato_resv().toString());
		faktura.add("Utl�nskontor: " + utleie_id.getReservasjon().getUtleiested().getAdresse().getGateadresse());
		faktura.add("Dato for retur: " + this.getDato_retur().toString());
		faktura.add("Innleveringssted: " + utleie_id.getReservasjon().getRetursted().getAdresse().getGateadresse());
		faktura.add("Pris: " + (utleie_id.getReservasjon().getBil().getKategori().getDagspris()
				* (this.getDato_retur().getTime() / (1000 * 60 * 60 * 24)
						- utleie_id.getReservasjon().getDato_resv().getTime() / (1000 * 60 * 60 * 24))));
		return faktura;
	}

	/**
	 * Hvis utleiekontoret er forskjellig fra returkontoret, oppdatert bilen med
	 * retur uteleiekontoret
	 */
	private void oppdaterUtleiekontor() {
		if (utleie_id.getReservasjon().getUtleiested().compareTo(utleie_id.getReservasjon().getRetursted()) != 0) {
			utleie_id.getReservasjon().getBil().setKontornummer(utleie_id.getReservasjon().getRetursted());
		}
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
