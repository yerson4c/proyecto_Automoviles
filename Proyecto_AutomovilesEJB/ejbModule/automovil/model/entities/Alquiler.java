package automovil.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.List;


/**
 * The persistent class for the alquiler database table.
 * 
 */
@Entity
@NamedQuery(name="Alquiler.findAll", query="SELECT a FROM Alquiler a")
public class Alquiler implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_alquiler")
	private Integer idAlquiler;

	private String estado;

	private Time fechafin;

	private Time fechainicio;

	private String gasolina;

	private String observaciones;

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

	public Time getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(Time fechafin) {
		this.fechafin = fechafin;
	}

	public Time getFechainicio() {
		return this.fechainicio;
	}

	public void setFechainicio(Time fechainicio) {
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