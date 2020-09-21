package automovil.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the alquiler database table.
 * 
 */
@Entity
@Table(name="alquiler")
@NamedQuery(name="Alquiler.findAll", query="SELECT a FROM Alquiler a")
public class Alquiler implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ALQUILER_IDALQUILER_GENERATOR", sequenceName="ALQUILER_ID_ALQUILER_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ALQUILER_IDALQUILER_GENERATOR")
	@Column(name="id_alquiler", unique=true, nullable=false)
	private Integer idAlquiler;

	@Column(nullable=false, length=1)
	private String estado;

	@Column(nullable=false)
	private Timestamp fechafin;

	@Column(nullable=false)
	private Timestamp fechainicio;

	@Column(nullable=false, length=50)
	private String gasolina;

	@Column(nullable=false, length=100)
	private String observaciones;

	@Column(nullable=false, length=50)
	private String recepcion;

	//bi-directional many-to-one association to EstadoAlquiler
	@OneToMany(mappedBy="alquiler")
	private List<EstadoAlquiler> estadoAlquilers;

	public Alquiler() {
	}

	public Integer getIdAlquiler() {
		return this.idAlquiler;
	}

	public void setIdAlquiler(Integer idAlquiler) {
		this.idAlquiler = idAlquiler;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(Timestamp fechafin) {
		this.fechafin = fechafin;
	}

	public Timestamp getFechainicio() {
		return this.fechainicio;
	}

	public void setFechainicio(Timestamp fechainicio) {
		this.fechainicio = fechainicio;
	}

	public String getGasolina() {
		return this.gasolina;
	}

	public void setGasolina(String gasolina) {
		this.gasolina = gasolina;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRecepcion() {
		return this.recepcion;
	}

	public void setRecepcion(String recepcion) {
		this.recepcion = recepcion;
	}

	public List<EstadoAlquiler> getEstadoAlquilers() {
		return this.estadoAlquilers;
	}

	public void setEstadoAlquilers(List<EstadoAlquiler> estadoAlquilers) {
		this.estadoAlquilers = estadoAlquilers;
	}

	public EstadoAlquiler addEstadoAlquiler(EstadoAlquiler estadoAlquiler) {
		getEstadoAlquilers().add(estadoAlquiler);
		estadoAlquiler.setAlquiler(this);

		return estadoAlquiler;
	}

	public EstadoAlquiler removeEstadoAlquiler(EstadoAlquiler estadoAlquiler) {
		getEstadoAlquilers().remove(estadoAlquiler);
		estadoAlquiler.setAlquiler(null);

		return estadoAlquiler;
	}

}