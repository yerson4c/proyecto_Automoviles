package automovil.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the "Autmoviles" database table.
 * 
 */
@Entity
@Table(name="\"Autmoviles\"")
@NamedQuery(name="Autmovile.findAll", query="SELECT a FROM Autmovile a")
public class Autmovile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer placa;

	private BigDecimal anio;

	private String color;

	@Column(name="estado_automovil")
	private String estadoAutomovil;

	private String marca;

	private String modelo;

	//bi-directional many-to-one association to EstadoAlquiler
	@OneToMany(mappedBy="autmovile")
	private List<EstadoAlquiler> estadoAlquilers;

	public Autmovile() {
	}

	public Integer getPlaca() {
		return this.placa;
	}

	public void setPlaca(Integer placa) {
		this.placa = placa;
	}

	public BigDecimal getAnio() {
		return this.anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getEstadoAutomovil() {
		return this.estadoAutomovil;
	}

	public void setEstadoAutomovil(String estadoAutomovil) {
		this.estadoAutomovil = estadoAutomovil;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public List<EstadoAlquiler> getEstadoAlquilers() {
		return this.estadoAlquilers;
	}

	public void setEstadoAlquilers(List<EstadoAlquiler> estadoAlquilers) {
		this.estadoAlquilers = estadoAlquilers;
	}

	public EstadoAlquiler addEstadoAlquiler(EstadoAlquiler estadoAlquiler) {
		getEstadoAlquilers().add(estadoAlquiler);
		estadoAlquiler.setAutmovile(this);

		return estadoAlquiler;
	}

	public EstadoAlquiler removeEstadoAlquiler(EstadoAlquiler estadoAlquiler) {
		getEstadoAlquilers().remove(estadoAlquiler);
		estadoAlquiler.setAutmovile(null);

		return estadoAlquiler;
	}

}