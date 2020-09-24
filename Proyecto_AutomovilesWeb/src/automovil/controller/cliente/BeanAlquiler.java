package automovil.controller.cliente;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import automovil.model.gerente.*;
import automovil.controller.gerente.BeanAutomovil;
import automovil.controller.gerente.JSFUtil;
import automovil.controller.login.BeanLogin;
import automovil.model.entities.Alquiler;
import automovil.model.entities.Automovil;
import automovil.model.entities.EstadoAlquiler;
import automovil.model.entities.Usuario;




@Named
@SessionScoped
public class BeanAlquiler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Alquiler> listaAlquiler;
	private Alquiler  alquiler;
	private Integer idAlquier;

	@EJB
	private ManagerEstadoAlquiler  managerEntregas;
	
	@EJB
	private ManagerAlquiler managerAlquiler;
	@EJB
	private ManagerUsuarioRol managerUsuarioRol;
	@EJB
	private ManagerAutomovil managerAuto;

	@Inject
	BeanLogin login;

	@Inject
	BeanAutomovil beanAuto;
	
	
	@PostConstruct
	public void inicializar() {
		try {
			listaEntregas=managerEntregas.findAllEstadoAlquiler();
			entregas=new EstadoAlquiler();
			listaAlquiler=managerAlquiler.findAllAlquiler();
			alquiler=new Alquiler();
			
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al cargar: ");
			
		}
		
	}

	public void inicializarCliente() {
		try {
			listaAlquiler=managerAlquiler.findAllAlquilerByIdUsuario(login.getLoginDTO().getIdRolUsuario());
			alquiler=new Alquiler();
			
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al cargar: ");
			
		}
		
	}
	public void actionListenerCargarAlquiler(Alquiler u) {
		try {
			alquiler=new Alquiler();
			alquiler.setFechainicio(u.getFechainicio());
			alquiler.setFechafin(u.getFechafin());
			alquiler.setEstado(u.getEstado());
			alquiler.setGasolina(u.getGasolina());
			alquiler.setRecepcion(u.getRecepcion());
			alquiler.setObservaciones(u.getObservaciones());
			alquiler.setIdAlquiler(u.getIdAlquiler());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerAprobarAlquiler(Alquiler alquile) {
		try {
			
				Alquiler u=managerAlquiler.findByIdAlquileres(alquile.getIdAlquiler());
				u.setAprobado("SI");
				managerAlquiler.actualizarAlquiler(u);
				listaAlquiler=managerAlquiler.findAllAlquiler();

				//listaMedida=managerMedida.findAllMedidas();
				//managerbit.crearEvento("actionListenerActualizarMedida()", "actualiza una medida ");
				JSFUtil.crearMensajeInfo("Aprobado exitosamente");
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al aprobar");
		}

	}
	
	private List<EstadoAlquiler> listaEntregas;
	private EstadoAlquiler entregas;
	///////////////////////////////////////////////////////////////////
	public void actionListenerInsertarEntregas() {
		try {
//			if (alquiler.getEstado().toString().length() > 0) {
				EstadoAlquiler e=new EstadoAlquiler();
				alquiler.setEntregado("SI");
				e.setAlquiler(managerAlquiler.findByIdAlquileres(alquiler.getIdAlquiler()));
				e.setEntregado("S");
				e.setFechaRecepcion(entregas.getFechaRecepcion());
				e.setUsuarioRol(managerUsuarioRol.findByIdUsuarioRol(alquiler.getUsuarioRol().getIdUsuarioRol()));
				managerAlquiler.actualizarAlquiler(alquiler);
				
				managerEntregas.insertarEstadoAlquiler(e);
				
				listaEntregas=managerEntregas.findAllEstadoAlquiler();	
				listaAlquiler=managerAlquiler.findAllAlquiler();
				 
				JSFUtil.crearMensajeInfo("Alquilado exitosamente");
//			} else {
//				JSFUtil.crearMensajeError("Debe ingresar el estado"); 
//			}		
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al insertar");
		}

	}
	
	public boolean validarBotonEntrega(Alquiler entregado) {
		if (entregado.getAprobado().equals("SI") && entregado.getEntregado().equals("NO")) {
			return true;
		} else {
			return false;
		}
	}
	public boolean validarBotonAlquiler(String v) {
		if (v.equals("SI")) {
			return false;
		} else {
			return true;
		}
	}
	public void actionListenerActualizarAlquiler() {
		try {
			if (alquiler.getEstado().toString().length() > 0) {
				
				Alquiler u=managerAlquiler.findByIdAlquileres(alquiler.getIdAlquiler());
				u.setFechainicio(new Timestamp(alquiler.getFechainicio().getTime()));
				u.setFechafin(new Timestamp(alquiler.getFechafin().getTime()));
				u.setEstado(alquiler.getEstado());
				u.setGasolina(alquiler.getGasolina());
				u.setRecepcion(alquiler.getRecepcion());
				u.setObservaciones(alquiler.getObservaciones());
				managerAlquiler.actualizarAlquiler(u);
				listaAlquiler=managerAlquiler.findAllAlquilerByIdUsuario(login.getLoginDTO().getIdRolUsuario());

				//listaMedida=managerMedida.findAllMedidas();
				//managerbit.crearEvento("actionListenerActualizarMedida()", "actualiza una medida ");
				JSFUtil.crearMensajeInfo("Actualizado exitosamente");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar el estado"); 
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarAlquiler() {
		alquiler=new Alquiler();
		alquiler.setFechainicio(null);
		alquiler.setFechafin(null);
		alquiler.setEstado("");
		alquiler.setGasolina("");
		alquiler.setRecepcion("");
		alquiler.setObservaciones("");
		alquiler.setIdAlquiler(null);
		
	}

	public void actionListenerInsertarAlquiler() {
		try {
			if (alquiler.getEstado().toString().length() > 0) {
				
				Alquiler u=new Alquiler();
				
				u.setFechainicio(new Timestamp(alquiler.getFechainicio().getTime()));
				u.setFechafin(new Timestamp(alquiler.getFechafin().getTime()));
				u.setEstado(alquiler.getEstado());
				u.setGasolina(alquiler.getGasolina());
				u.setRecepcion(alquiler.getRecepcion());
				u.setObservaciones(alquiler.getObservaciones());
				u.setUsuarioRol(managerUsuarioRol.findByIdUsuarioRol(login.getLoginDTO().getIdRolUsuario()));
				u.setAutomovil(managerAuto.findByIdAutomovil(beanAuto.getIdAutomovil()));
				managerAlquiler.insertarAlquiler(u);
				listaAlquiler=managerAlquiler.findAllAlquiler();

				//listaMedida=managerMedida.findAllMedidas();
				//managerbit.crearEvento("actionListenerActualizarMedida()", "actualiza una medida ");
				JSFUtil.crearMensajeInfo("Insertado exitosamente");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar el estado"); 
			}		
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al insertar");
		}

	}
	

	public void actionListenerEliminarAlquiler(Integer id) {
		try {
			managerAlquiler.eliminarAlquiler(id);
			listaAlquiler=managerAlquiler.findAllAlquilerByIdUsuario(login.getLoginDTO().getIdRolUsuario());
			
			JSFUtil.crearMensajeInfo("Alquiler ha sido eliminado");
		} catch (Exception e) {
			
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar : "+e.getMessage());
		}

	}

	
	public String irAlquiler() {
		return "alquiler_cliente";
	}
	public String irAutomovilC() {
		return "automovil_cliente";
	}
	 

	public String irHome() {
		return "home_cliente";
	}

	

	public List<Alquiler> getListaAlquiler() {
		return listaAlquiler;
	}


	public void setListaAlquiler(List<Alquiler> listaAlquiler) {
		this.listaAlquiler = listaAlquiler;
	}


	public Alquiler getAlquiler() {
		return alquiler;
	}


	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}


	public Integer getIdAlquier() {
		return idAlquier;
	}


	public void setIdAlquier(Integer idAlquier) {
		this.idAlquier = idAlquier;
	}


	public BeanLogin getLogin() {
		return login;
	}


	public void setLogin(BeanLogin login) {
		this.login = login;
	}


	public BeanAutomovil getBeanAuto() {
		return beanAuto;
	}


	public void setBeanAuto(BeanAutomovil beanAuto) {
		this.beanAuto = beanAuto;
	}

	public List<EstadoAlquiler> getListaEntregas() {
		return listaEntregas;
	}

	public void setListaEntregas(List<EstadoAlquiler> listaEntregas) {
		this.listaEntregas = listaEntregas;
	}

	public EstadoAlquiler getEntregas() {
		return entregas;
	}

	public void setEntregas(EstadoAlquiler entregas) {
		this.entregas = entregas;
	}
	

	
}
