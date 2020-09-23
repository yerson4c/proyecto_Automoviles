package automovil.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the automovil database table.
 * 
 */
@Entity
@Table(name="automovil")
@NamedQuery(name="Automovil.findAll", query="SELECT a FROM Automovil a")
public class Automovil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUTOMOVIL_IDAUTOMOVIL_GENERATOR", sequenceName="AUTOMOVIL_ID_AUTOMOVIL_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTOMOVIL_IDAUTOMOVIL_GENERATOR")
	@Column(name="id_automovil", unique=true, nullable=false)
	private Integer idAutomovil;

	@Column(nullable=false, precision=4)
	private BigDecimal anio;

	@Column(nullable=false, length=50)
	private String color;

	@Column(nullable=false, length=100)
	private String descripcion;

	@Column(name="estado_automovil", nullable=false, length=7)
	private String estadoAutomovil;

	private String foto;

	@Column(nullable=false, length=50)
	private String marca;

	@Column(nullable=false, length=50)
	private String modelo;

	@Column(nullable=false, length=10)
	private String placa;

	@Column(precision=7)
	private BigDecimal precio;

	//bi-directional many-to-one association to Alquiler
	@OneToMany(mappedBy="automovil")
	private List<Alquiler> alquilers;

	public Automovil() {
	}

	public Integer getIdAutomovil() {
		return this.idAutomovil;
	}

	public void setIdAutomovil(Integer idAutomovil) {
		this.idAutomovil = idAutomovil;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoAutomovil() {
		return this.estadoAutomovil;
	}

	public void setEstadoAutomovil(String estadoAutomovil) {
		this.estadoAutomovil = estadoAutomovil;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public List<Alquiler> getAlquilers() {
		return this.alquilers;
	}

	public void setAlquilers(List<Alquiler> alquilers) {
		this.alquilers = alquilers;
	}

	public Alquiler addAlquiler(Alquiler alquiler) {
		getAlquilers().add(alquiler);
		alquiler.setAutomovil(this);

		return alquiler;
	}

	public Alquiler removeAlquiler(Alquiler alquiler) {
		getAlquilers().remove(alquiler);
		alquiler.setAutomovil(null);

		return alquiler;
	}

}