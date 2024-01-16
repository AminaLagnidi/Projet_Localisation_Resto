package ma.projet.restorant.entities;





import javax.persistence.*;

@Entity
public class LigneSpecialite {

	@EmbeddedId
	private LigneSpecialitePK specialitePK;
	@ManyToOne
	@JoinColumn(name = "resto_id", insertable = false, updatable = false)
	private Resto restoo;
	@ManyToOne
	@JoinColumn(name = "specialite_id", insertable = false, updatable = false)
	private Specialite specialite;
	
	public LigneSpecialite() {
		super();
	}

	public LigneSpecialitePK getSpecialitePK() {
		return specialitePK;
	}

	public void setSpecialitePK(LigneSpecialitePK specialitePK) {
		this.specialitePK = specialitePK;
	}

	public Resto getResto() {
		return restoo;
	}

	public void setResto(Resto resto) {
		this.restoo = resto;
	}

	public Specialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}
	
}
