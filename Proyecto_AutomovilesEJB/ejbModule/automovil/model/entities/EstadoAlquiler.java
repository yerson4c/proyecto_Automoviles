package automovil.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the estado_alquiler database table.
 * 
 */
@Entity
@Table(name="estado_alquiler")
@NamedQuery(name="EstadoAlquiler.findAll", query="SELECT e FROM EstadoAlquiler e")
public class EstadoAlquiler implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ESTADO_ALQUILER_IDESTADO_GENERATOR", sequenceName="ESTADO_ALQUILER_ID_ESTADO_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ESTADO_ALQUILER_IDESTADO_GENERATOR")
	@Column(name="id_estado", unique=true, nullable=false)
	private Integer idEstado;

	@Column(length=1)
	private String entregado;

	@Column(name="fecha_recepcion", nullable=false)
	private Timestamp fechaRecepcion;

	//bi-directional many-to-one association to Alquiler
	@ManyToOne
	@JoinColumn(name="id_alquiler", nullable=false)
	private Alquiler alquiler;

	//bi-directional many-to-one association to UsuarioRol
	@ManyToOne
	@JoinColumn(name="id_usuario_rol", nullable=false)
	private UsuarioRol usuarioRol;

	public EstadoAlquiler() {
	}

	public Integer getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getEntregado() {
		return this.entregado;
	}

	public void setEntregado(String entregado) {
		this.entregado = entregado;
	}

	public Timestamp getFechaRecepcion() {
		return this.fechaRecepcion;
	}

	public void setFechaRecepcion(Timestamp fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Alquiler getAlquiler() {
		return this.alquiler;
	}

	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}

	public UsuarioRol getUsuarioRol() {
		return this.usuarioRol;
	}

	public void setUsuarioRol(UsuarioRol usuarioRol) {
		this.usuarioRol = usuarioRol;
	}

}