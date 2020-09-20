package automovil.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the gerente database table.
 * 
 */
@Entity
@NamedQuery(name="Gerente.findAll", query="SELECT g FROM Gerente g")
public class Gerente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_gerente")
	private Integer idGerente;

	@Column(name="apellido_g")
	private String apellidoG;

	private String contrasenia;

	@Column(name="correo_g")
	private String correoG;

	@Column(name="direccion_g")
	private String direccionG;

	@Column(name="nombre_g")
	private String nombreG;

	@Column(name="telefono_g")
	private BigDecimal telefonoG;

	//bi-directional many-to-one association to EstadoAlquiler
	@OneToMany(mappedBy="gerente")
	private List<EstadoAlquiler> estadoAlquilers;

	public Gerente() {
	}

	public Integer getIdGerente() {
		return this.idGerente;
	}

	public void setIdGerente(Integer idGerente) {
		this.idGerente = idGerente;
	}

	public String getApellidoG() {
		return this.apellidoG;
	}

	public void setApellidoG(String apellidoG) {
		this.apellidoG = apellidoG;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getCorreoG() {
		return this.correoG;
	}

	public void setCorreoG(String correoG) {
		this.correoG = correoG;
	}

	public String getDireccionG() {
		return this.direccionG;
	}

	public void setDireccionG(String direccionG) {
		this.direccionG = direccionG;
	}

	public String getNombreG() {
		return this.nombreG;
	}

	public void setNombreG(String nombreG) {
		this.nombreG = nombreG;
	}

	public BigDecimal getTelefonoG() {
		return this.telefonoG;
	}

	public void setTelefonoG(BigDecimal telefonoG) {
		this.telefonoG = telefonoG;
	}

	public List<EstadoAlquiler> getEstadoAlquilers() {
		return this.estadoAlquilers;
	}

	public void setEstadoAlquilers(List<EstadoAlquiler> estadoAlquilers) {
		this.estadoAlquilers = estadoAlquilers;
	}

	public EstadoAlquiler addEstadoAlquiler(EstadoAlquiler estadoAlquiler) {
		getEstadoAlquilers().add(estadoAlquiler);
		estadoAlquiler.setGerente(this);

		return estadoAlquiler;
	}

	public EstadoAlquiler removeEstadoAlquiler(EstadoAlquiler estadoAlquiler) {
		getEstadoAlquilers().remove(estadoAlquiler);
		estadoAlquiler.setGerente(null);

		return estadoAlquiler;
	}

}