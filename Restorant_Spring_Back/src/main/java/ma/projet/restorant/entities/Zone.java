package ma.projet.restorant.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;



import javax.persistence.*;

@Entity
public class Zone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;
	@OneToMany(fetch = FetchType.LAZY , mappedBy = "zone")
	@JsonIgnore
	private List<Resto> restos;
	@ManyToOne
	@JoinColumn(name = "ville")
	private Ville ville;

	public Zone() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Resto> getRestos() {
		return restos;
	}

	public void setRestos(List<Resto> restos) {
		this.restos = restos;
	}

	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

}
