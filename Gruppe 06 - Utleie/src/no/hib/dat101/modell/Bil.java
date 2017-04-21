package no.hib.dat101.modell;

import javax.persistence.Id;

/**
 * 
 * @author Kristoffer-Andre Kalliainen
 *
 */
public class Bil implements Comparable<Bil> {
	@Id
	private Integer reg_nummer;
	private String merke;
	private String modell;
	private String farge;
	private Kategori kategori;
	private Boolean er_ferdig;
	private Integer km_stand;
	private Utleiekontor kontornummer;

	/**
	 * 
	 * Tom Konstrukt�r
	 *
	 */
	public Bil() {
		this(0, "", "", "", null, null, 0, null);
	}

	/**
	 * Konstrukt�r
	 * 
	 * @param reg_nummer
	 * @param merke
	 * @param modell
	 * @param farge
	 * @param kategori
	 * @param er_ferdig
	 * @param km_stand
	 * @param kontornummer
	 */
	public Bil(Integer reg_nummer, String merke, String modell, String farge, Kategori kategori, Boolean er_ferdig,
			Integer km_stand, Utleiekontor kontornummer) {
		this.reg_nummer = reg_nummer;
		this.merke = merke;
		this.modell = modell;
		this.farge = farge;
		this.kategori = kategori;
		this.er_ferdig = er_ferdig;
		this.km_stand = km_stand;
		this.kontornummer = kontornummer;
	}

	/**
	 * Sammenligner bilen med denAndreBilen. Sammenligner f�rst reg_nummer,
	 * deretter merke, deretter modell, og tilslutt farge.
	 * 
	 * @param denAndreBilen
	 *            Den andre bilen som skal sammenlignes med
	 * @return 0 hvis de er like.
	 */
	@Override
	public int compareTo(Bil denAndreBilen) {
		Bil b2 = (Bil) denAndreBilen;
		int resultat = -1;
		if (this.reg_nummer == b2.reg_nummer) {
			resultat = this.merke.compareTo(b2.merke);
			if (resultat == 0) {
				resultat = this.modell.compareTo(b2.modell);
				if (resultat == 0) {
					resultat = this.farge.compareTo(b2.farge);
				}
			}
		} else if (this.reg_nummer > b2.reg_nummer) {
			resultat = 1;
		}
		return resultat;
	}

	/**
	 * @return String representasjon av Bil
	 */
	@Override
	public String toString() {
		return "reg_nummer: " + reg_nummer + ", merke: " + merke + ", modell: " + modell + ", farge: " + farge
				+ ", kategori: " + kategori + ", er_ferdig: " + er_ferdig + ", km_stand: " + km_stand
				+ ", kontornummer: " + kontornummer;
	}

	/**
	 * @return henter reg_nummer
	 */
	public Integer getReg_nummer() {
		return reg_nummer;
	}

	/**
	 * @param reg_nummer
	 *            setter reg_nummer
	 */
	public void setReg_nummer(Integer reg_nummer) {
		this.reg_nummer = reg_nummer;
	}

	/**
	 * @return henter merke
	 */
	public String getMerke() {
		return merke;
	}

	/**
	 * @param merke
	 *            setter merke
	 */
	public void setMerke(String merke) {
		this.merke = merke;
	}

	/**
	 * @return henter modell
	 */
	public String getModell() {
		return modell;
	}

	/**
	 * @param modell
	 *            setter modell
	 */
	public void setModell(String modell) {
		this.modell = modell;
	}

	/**
	 * @return henter farge
	 */
	public String getFarge() {
		return farge;
	}

	/**
	 * @param farge
	 *            setter farge
	 */
	public void setFarge(String farge) {
		this.farge = farge;
	}

	/**
	 * @return henter kategori
	 */
	public Kategori getKategori() {
		return kategori;
	}

	/**
	 * @param kategori
	 *            setter kategori
	 */
	public void setKategori(Kategori kategori) {
		this.kategori = kategori;
	}

	/**
	 * @return henter er_ferdig
	 */
	public Boolean getEr_ferdig() {
		return er_ferdig;
	}

	/**
	 * @param er_ferdig
	 *            setter er_ferdig
	 */
	public void setEr_ferdig(Boolean er_ferdig) {
		this.er_ferdig = er_ferdig;
	}

	/**
	 * @return henter km_stand
	 */
	public Integer getKm_stand() {
		return km_stand;
	}

	/**
	 * @param km_stand
	 *            setter km_stand
	 */
	public void setKm_stand(Integer km_stand) {
		this.km_stand = km_stand;
	}

	/**
	 * @return henter kontornummer
	 */
	public Utleiekontor getKontornummer() {
		return kontornummer;
	}

	/**
	 * @param kontornummer
	 *            setter kontornummer
	 */
	public void setKontornummer(Utleiekontor kontornummer) {
		this.kontornummer = kontornummer;
	}

}