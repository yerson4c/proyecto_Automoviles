package automovil.controller.cliente;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

import automovil.model.gerente.*;
import automovil.controller.gerente.JSFUtil;
import automovil.model.entities.Alquiler;
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
	private ManagerAlquiler managerAlquiler;
	

//	@Inject
//	BeanLogin login;

	
	
	@PostConstruct
	public void inicializar() {
		try {
			listaAlquiler=managerAlquiler.findAllAlquiler();
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
				listaAlquiler=managerAlquiler.findAllAlquiler();

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
			listaAlquiler=managerAlquiler.findAllAlquiler();
			
			JSFUtil.crearMensajeInfo("Alquiler ha sido eliminado");
		} catch (Exception e) {
			
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar : "+e.getMessage());
		}

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
	

	
}
