package no.hib.dat101.modell;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "reservasjon", schema = "bilutleie")
public class Reservasjon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservasjon_id;
	@Column(name = "klokkeslett_resv")
	private Time klokkeslett_resv;
	@Column(name = "dato_resv")
	private Date dato_resv;
	@Column(name = "klokke_forventet")
	private Time klokke_forventet;
	@Column(name = "dato_forventet")
	private Date dato_forventet;
	@Column(name = "returgebyr")
	private Integer returgebyr;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "kundenummer", referencedColumnName = "kundenummer")
	private Kunde kundenummer;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "utleiested", referencedColumnName = "kontornummer")
	private Utleiekontor utleiested;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "retursted", referencedColumnName = "kontornummer")
	private Utleiekontor retursted;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bil", referencedColumnName = "reg_nummer")
	private Bil bil;

	@Transient
	private SelskapUI ui;

	/**
	 * KonstruktÝr
	 * 
	 */
	public Reservasjon() {
		this(0, null, null, null, null, null, null, null, null, 0);
	}

	/**
	 * KonstruktÝr
	 * 
	 * @param reservasjon_id
	 * @param klokkeslett_resv
	 * @param dato_resv
	 * @param kundenummer
	 * @param utleiested
	 * @param retursted
	 * @param klokke_forventet
	 * @param dato_forventet
	 * @param bil
	 * @param returgebyr
	 */
	public Reservasjon(Integer reservasjon_id, Time klokkeslett_resv, Date dato_resv, Kunde kundenummer,
			Utleiekontor utleiested, Utleiekontor retursted, Time klokke_forventet, Date dato_forventet, Bil bil,
			Integer returgebyr) {
		this.reservasjon_id = reservasjon_id;
		this.klokkeslett_resv = klokkeslett_resv;
		this.dato_resv = dato_resv;
		this.kundenummer = kundenummer;
		this.utleiested = utleiested;
		this.retursted = retursted;
		this.klokke_forventet = klokke_forventet;
		this.dato_forventet = dato_forventet;
		this.bil = bil;
		this.returgebyr = returgebyr;
	}

	/**
	 * Lager reservasjonen
	 */
	public void lagReservasjon(Selskap s) {
		setUtleiested(ui.velgUtleiekontor(s));
		setRetursted(ui.velgUtleiekontor(s));
		setKlokkeslett_resv(ui.lesInnKlokkeslett());
		setDato_resv(ui.lesInnDato());
		setKlokke_forventet(ui.lesInnKlokkeslett());
		setDato_forventet(ui.lesInnDato());
		getUtleiested().hentBilerFraDB();
		setBil(ui.velgBil(getUtleiested()));
		setKundenummer(hentKundeInformasjon());
	}

	/**
	 * Henter kundeinformasjon fra kunden
	 */
	public Kunde hentKundeInformasjon() {
		Kunde nyKunde = new Kunde();
		nyKunde.setAdresse(ui.lesInnAdresse());
		nyKunde.setFornavn(ui.lesInnFornavn());
		nyKunde.setEtternavn(ui.lesInnEtternavn());
		nyKunde.setTelefonnummer(ui.lesInnTelefonnummer());
		return nyKunde;
	}

	public Kunde hentKundeInformasjonFerdig() {
		Kunde nyKunde = new Kunde();
		nyKunde.setAdresse("breistolsveien 12");
		nyKunde.setFornavn("Peder");
		nyKunde.setEtternavn("Wiig");
		nyKunde.setTelefonnummer(40615713);
		return nyKunde;
	}

	/**
	 * Bekrefter reservasjonen og laster opp dataene til databasen.
	 */
	public Boolean bekreftReservasjon(EntityManager em) {
		Boolean tilstand = Boolean.FALSE;
		if (ui.bekreft()) {
			try {
				em.getTransaction().begin();
				em.persist(this);
				em.getTransaction().commit();
			} catch (RollbackException e) {
				em.getTransaction().rollback();
			}
			tilstand = Boolean.TRUE;
		}
		return tilstand;
	}

	public void lastOppReservasjonDB(EntityManager em) {
		try {
			em.getTransaction().begin();
			em.persist(this);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			em.getTransaction().rollback();
		}
	}

	/**
	 * Setter en bil ledig etter kunden har levert den til returkontor
	 * 
	 * @param flagg
	 */
	public void flaggBil(Boolean flagg) {
		getBil().setEr_ledig(flagg);
	}

	/**
	 * @return henter reservasjon_id
	 */
	public Integer getReservasjon_id() {
		return reservasjon_id;
	}

	/**
	 * @param reservasjon_id
	 *            setter reservasjon_id
	 */
	public void setReservasjon_id(Integer reservasjon_id) {
		this.reservasjon_id = reservasjon_id;
	}

	/**
	 * @return henter klokkeslett_resv
	 */
	public Time getKlokkeslett_resv() {
		return klokkeslett_resv;
	}

	/**
	 * @param klokkeslett_resv
	 *            setter klokkeslett_resv
	 */
	public void setKlokkeslett_resv(Time klokkeslett_resv) {
		this.klokkeslett_resv = klokkeslett_resv;
	}

	/**
	 * @return henter dato_resv
	 */
	public Date getDato_resv() {
		return dato_resv;
	}

	/**
	 * @param dato_resv
	 *            setter dato_resv
	 */
	public void setDato_resv(Date dato_resv) {
		this.dato_resv = dato_resv;
	}

	/**
	 * @return henter kundenummer
	 */
	public Kunde getKundenummer() {
		return kundenummer;
	}

	/**
	 * @param kundenummer
	 *            setter kundenummer
	 */
	public void setKundenummer(Kunde kundenummer) {
		this.kundenummer = kundenummer;
	}

	/**
	 * @return henter utleiested
	 */
	public Utleiekontor getUtleiested() {
		return utleiested;
	}

	/**
	 * @param utleiested
	 *            setter utleiested
	 */
	public void setUtleiested(Utleiekontor utleiested) {
		this.utleiested = utleiested;
	}

	/**
	 * @return henter retursted
	 */
	public Utleiekontor getRetursted() {
		return retursted;
	}

	/**
	 * @param retursted
	 *            setter retursted
	 */
	public void setRetursted(Utleiekontor retursted) {
		this.retursted = retursted;
	}

	/**
	 * @return henter klokke_forventet
	 */
	public Time getKlokke_forventet() {
		return klokke_forventet;
	}

	/**
	 * @param klokke_forventet
	 *            setter klokke_forventet
	 */
	public void setKlokke_forventet(Time klokke_forventet) {
		this.klokke_forventet = klokke_forventet;
	}

	/**
	 * @return henter dato_forventet
	 */
	public Date getDato_forventet() {
		return dato_forventet;
	}

	/**
	 * @param dato_forventet
	 *            setter dato_forventet
	 */
	public void setDato_forventet(Date dato_forventet) {
		this.dato_forventet = dato_forventet;
	}

	/**
	 * @return henter bil
	 */
	public Bil getBil() {
		return bil;
	}

	/**
	 * @param bil
	 *            setter bil
	 */
	public void setBil(Bil bil) {
		this.bil = bil;
	}

	/**
	 * @return henter returgebyr
	 */
	public Integer getReturgebyr() {
		return returgebyr;
	}

	/**
	 * @param returgebyr
	 *            setter returgebyr
	 */
	public void setReturgebyr(Integer returgebyr) {
		this.returgebyr = returgebyr;
	}

	/**
	 * @return henter ui
	 */
	public SelskapUI getUi() {
		return ui;
	}

	/**
	 * @param ui
	 *            setter ui
	 */
	public void setUi(SelskapUI ui) {
		this.ui = ui;
	}

}
