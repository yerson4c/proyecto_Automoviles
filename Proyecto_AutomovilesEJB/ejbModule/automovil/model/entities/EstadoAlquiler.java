package automovil.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the "EstadoAlquiler" database table.
 * 
 */
@Entity
@Table(name="\"EstadoAlquiler\"")
@NamedQuery(name="EstadoAlquiler.findAll", query="SELECT e FROM EstadoAlquiler e")
public class EstadoAlquiler implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_estado")
	private Integer idEstado;

	@Column(name="fecha_recepcion")
	private Time fechaRecepcion;

	//bi-directional many-to-one association to Autmovile
	@ManyToOne
	@JoinColumn(name="placa")
	private Autmovile autmovile;

	//bi-directional many-to-one association to Alquiler
	@ManyToOne
	@JoinColumn(name="id_alquiler")
	private Alquiler alquiler;

	//bi-directional many-to-one association to Gerente
	@ManyToOne
	@JoinColumn(name="id_gerente")
	private Gerente gerente;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public EstadoAlquiler() {
	}

	public Integer getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Time getFechaRecepcion() {
		return this.fechaRecepcion;
	}

	public void setFechaRecepcion(Time fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Autmovile getAutmovile() {
		return this.autmovile;
	}

	public void setAutmovile(Autmovile autmovile) {
		this.autmovile = autmovile;
	}

	public Alquiler getAlquiler() {
		return this.alquiler;
	}

	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}

	public Gerente getGerente() {
		return this.gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}