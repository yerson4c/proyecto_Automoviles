package automovil.controller.gerente;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Calendar;

import java.util.List;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import automovil.controller.cliente.BeanAlquiler;
import automovil.controller.login.BeanLogin;
import automovil.model.entities.Alquiler;
import automovil.model.entities.Automovil;
import automovil.model.entities.EstadoAlquiler;
import automovil.model.gerente.ManagerAlquiler;
import automovil.model.gerente.ManagerAutomovil;
import automovil.model.gerente.ManagerEstadoAlquiler;
import automovil.model.gerente.ManagerUsuarioRol;
import sun.misc.BASE64Encoder;



@Named
@SessionScoped
public class BeanEntregas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<EstadoAlquiler> listaEntregas;
	private EstadoAlquiler entregas;
	private Integer idEntregas;
	
	
	@EJB
	private ManagerAutomovil managerAutomovil;
	
	@EJB
	private ManagerUsuarioRol managerUsuarioRol;

	@EJB
	private ManagerAlquiler managerAlquiler;
	@EJB
	private ManagerEstadoAlquiler  managerEntregas;
	@Inject
	BeanLogin login;

	
	
//	@Inject
//	BeanLogin login;

	
	 
	
	@PostConstruct
	public void inicializar() {
		try {
			entregas=new EstadoAlquiler();
			listaEntregas=managerEntregas.findAllEstadoAlquiler(); 
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error: ");
			
		}
		
	}

	
	public void actionListenerCargarEntregas(EstadoAlquiler u) {
		try {
			entregas=new EstadoAlquiler();
			entregas.setAlquiler(managerAlquiler.findByIdAlquileres(u.getAlquiler().getIdAlquiler()));
			entregas.setEntregado(u.getEntregado());
			entregas.setFechaRecepcion(u.getFechaRecepcion());
			entregas.setIdEstado(u.getIdEstado());
			entregas.setUsuarioRol(managerUsuarioRol.findByIdUsuarioRol(u.getUsuarioRol().getIdUsuarioRol()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionListenerInsertarEntregas() {
		try {
//			if (alquiler.getEstado().toString().length() > 0) {
				EstadoAlquiler e=new EstadoAlquiler();
				e.setAlquiler(managerAlquiler.findByIdAlquileres(entregas.getIdEstado()));
				e.setEntregado(entregas.getEntregado());
				e.setFechaRecepcion(entregas.getFechaRecepcion());
				e.setUsuarioRol(managerUsuarioRol.findByIdUsuarioRol(entregas.getUsuarioRol().getIdUsuarioRol()));
				
				managerEntregas.insertarEstadoAlquiler(e);
				listaEntregas=managerEntregas.findAllEstadoAlquiler();	

				 
				JSFUtil.crearMensajeInfo("Alquilado exitosamente");
//			} else {
//				JSFUtil.crearMensajeError("Debe ingresar el estado"); 
//			}		
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al insertar");
		}

	}

	public void actionListenerActualizarEntregas() {
		try {
			
//			if (automovil.getPlaca().toString().length() > 0) {
			
			EstadoAlquiler e=managerEntregas.findByIdEstadoAlquileres(entregas.getIdEstado());
			
			
			e.setAlquiler(managerAlquiler.findByIdAlquileres(entregas.getIdEstado()));
			e.setEntregado(entregas.getEntregado());
			e.setFechaRecepcion(entregas.getFechaRecepcion());
			e.setUsuarioRol(managerUsuarioRol.findByIdUsuarioRol(entregas.getUsuarioRol().getIdUsuarioRol()));
			managerEntregas.actualizarEstadoAlquiler(e);
			listaEntregas=managerEntregas.findAllEstadoAlquiler();
		
			

				//listaMedida=managerMedida.findAllMedidas();
				//managerbit.crearEvento("actionListenerActualizarMedida()", "actualiza una medida ");
				JSFUtil.crearMensajeInfo("Actualizado  exitosamente");
//			} else {
//				JSFUtil.crearMensajeError("Debe ingresar la placa"); 
//			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarEntregas() {
		entregas=new EstadoAlquiler();
		entregas.setAlquiler(null);
		entregas.setEntregado("");
		entregas.setFechaRecepcion(null);
		entregas.setIdEstado(null);
		entregas.setUsuarioRol(null);
		
	}

	 

	
	
//	public void actionListenerActualizarClave() throws Exception {
//		if (claveAnterior.equals(login.getPassword())) {
//			login.setPassword(claveNueva);
//			Usuario us=managerUser.findByIdUsuario(login.getIdUsuario());
//			us.setClaveUsuario(claveNueva);
//			managerUser.actualizarUsuario(us);
//			System.out.println("paso algo");
//			
//			JSFUtil.crearMensajeInfo("Su contrase�a se ha actualizado correctamente");
//		}else {
//			JSFUtil.crearMensajeError("Contrase�as no coinciden, ingrese de nuevo");
//		}
//		
//	}
	
	
	public int CalcularEdad(Calendar fechaNac) {

		Calendar fechaActual = Calendar.getInstance();
		int years = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
		int months = fechaActual.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
		int days = fechaActual.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
		// Hay que comprobar si el d�a de su cumplea�os es posterior
		// a la fecha actual, para restar 1 a la diferencia de a�os,
		// pues a�n no ha sido su cumplea�os.
		if (months < 0 // A�n no es el mes de su cumplea�os
				|| (months == 0 && days < 0)) { // o es el mes pero no ha llegado el d�a.
			years--;
		}
		System.out.println("los anios xD xD " + years);
		return years;
	}

	public boolean validarCedula(String x) {
		try {
			int suma = 0;
			if (x.length() == 9) {
				return false;
			} else {
				int a[] = new int[x.length() / 2];
				int b[] = new int[(x.length() / 2)];
				int c = 0;
				int d = 1;
				for (int i = 0; i < x.length() / 2; i++) {
					a[i] = Integer.parseInt(String.valueOf(x.charAt(c)));
					c = c + 2;
					if (i < (x.length() / 2) - 1) {
						b[i] = Integer.parseInt(String.valueOf(x.charAt(d)));
						d = d + 2;
					}
				}

				for (int i = 0; i < a.length; i++) {
					a[i] = a[i] * 2;
					if (a[i] > 9) {
						a[i] = a[i] - 9;
					}
					suma = suma + a[i] + b[i];
				}
				int aux = suma / 10;
				int dec = (aux + 1) * 10;
				if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length() - 1)))) {
					return true;
				} else if (suma % 10 == 0 && x.charAt(x.length() - 1) == '0') {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
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


	public Integer getIdEntregas() {
		return idEntregas;
	}


	public void setIdEntregas(Integer idEntregas) {
		this.idEntregas = idEntregas;
	}


	public ManagerAutomovil getManagerAutomovil() {
		return managerAutomovil;
	}


	public void setManagerAutomovil(ManagerAutomovil managerAutomovil) {
		this.managerAutomovil = managerAutomovil;
	}


	

	public BeanLogin getLogin() {
		return login;
	}


	public void setLogin(BeanLogin login) {
		this.login = login;
	}


	
	

}
