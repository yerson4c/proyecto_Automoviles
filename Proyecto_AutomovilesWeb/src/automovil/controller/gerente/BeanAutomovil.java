package automovil.controller.gerente;

import java.io.Serializable;

import java.util.Calendar;

import java.util.List;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;


import automovil.model.entities.Automovil;
import automovil.model.gerente.ManagerAutomovil;



@Named
@SessionScoped
public class BeanAutomovil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Automovil> listaAutomovil;
	private Automovil automovil;
	private Integer idAutomovil;
	

	@EJB
	private ManagerAutomovil managerAutomovil;
	

//	@Inject
//	BeanLogin login;

	
	
	//contraseña
	private String claveAnterior;
	private String claveNueva;
	
	@PostConstruct
	public void inicializar() {
		try {
			listaAutomovil=managerAutomovil.findAllAutomovil();
			automovil=new Automovil();
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error: ");
			
		}
		
	}

	
	public void actionListenerCargarAutomovil(Automovil u) {
		try {
			automovil=new Automovil();
			automovil.setIdAutomovil(u.getIdAutomovil());
			automovil.setDescripcion(u.getDescripcion());
			automovil.setAnio(u.getAnio());
			automovil.setColor(u.getColor());
			automovil.setEstadoAutomovil(u.getEstadoAutomovil());
			automovil.setMarca(u.getMarca());
			automovil.setModelo(u.getModelo());
			automovil.setPlaca(u.getPlaca());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerActualizarAutomovil() {
		try {
			if (automovil.getPlaca().toString().length() > 0) {
				Automovil a=managerAutomovil.findByIdAutomovil(automovil.getIdAutomovil());
				a.setAnio(automovil.getAnio());
				a.setColor(automovil.getColor());
				a.setEstadoAutomovil(automovil.getEstadoAutomovil());
				a.setMarca(automovil.getMarca());
				a.setModelo(automovil.getModelo());
				a.setPlaca(automovil.getPlaca());
				managerAutomovil.actualizarAutomovil(a);
				listaAutomovil=managerAutomovil.findAllAutomovil();
				

				//listaMedida=managerMedida.findAllMedidas();
				//managerbit.crearEvento("actionListenerActualizarMedida()", "actualiza una medida ");
				JSFUtil.crearMensajeInfo("Actualizado con éxito");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar la placa"); 
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarAutomovil() {
		automovil=new Automovil();
		automovil.setAnio(null);
		automovil.setColor("");
		automovil.setEstadoAutomovil("");
		automovil.setMarca("");
		automovil.setModelo("");
		automovil.setPlaca(null);
		
	}

	public void actionListenerInsertarAutomovil() {
		try {
			if (automovil.getPlaca().toString().length() > 0) {
				Automovil a=new Automovil();
				a.setAnio(automovil.getAnio());
				a.setDescripcion(automovil.getDescripcion());
				a.setColor(automovil.getColor());
				a.setEstadoAutomovil(automovil.getEstadoAutomovil());
				a.setMarca(automovil.getMarca());
				a.setModelo(automovil.getModelo());
				a.setPlaca(automovil.getPlaca());
				managerAutomovil.insertarAutomovil(a);
				
				listaAutomovil=managerAutomovil.findAllAutomovil();
				JSFUtil.crearMensajeInfo("Actualizado con éxito");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar la placa"); 
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al insertar: "+e.getMessage());
		}

	}
	

	public void actionListenerEliminarAutomovil(Integer id) {
		try {
			managerAutomovil.eliminarAutomovil(id);
			listaAutomovil=managerAutomovil.findAllAutomovil();
			JSFUtil.crearMensajeInfo("Automóvil ha sido eliminado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}

	}
	
	
	
//	public void actionListenerActualizarClave() throws Exception {
//		if (claveAnterior.equals(login.getPassword())) {
//			login.setPassword(claveNueva);
//			Usuario us=managerUser.findByIdUsuario(login.getIdUsuario());
//			us.setClaveUsuario(claveNueva);
//			managerUser.actualizarUsuario(us);
//			System.out.println("paso algo");
//			
//			JSFUtil.crearMensajeInfo("Su contraseña se ha actualizado correctamente");
//		}else {
//			JSFUtil.crearMensajeError("Contraseñas no coinciden, ingrese de nuevo");
//		}
//		
//	}
	
	
	public int CalcularEdad(Calendar fechaNac) {

		Calendar fechaActual = Calendar.getInstance();
		int years = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
		int months = fechaActual.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
		int days = fechaActual.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
		// Hay que comprobar si el día de su cumpleaños es posterior
		// a la fecha actual, para restar 1 a la diferencia de años,
		// pues aún no ha sido su cumpleaños.
		if (months < 0 // Aún no es el mes de su cumpleaños
				|| (months == 0 && days < 0)) { // o es el mes pero no ha llegado el día.
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



	public String irHome() {
		return "home";
	}

	public String irBitacora() {
		
			return "bitacora";
		
	}

	public String irUsuario() {
		return "usuario";
	}
	public String irCambiarContrasenia() {
		return "cambiar_contrasenia";
	}
	public String irRol() {
		return "rol";
	}

	public String irMovimientos() {
		return "movimientos";
	}

	public String irPuntoVenta() {
		return "punto_venta";
	}

	public String irProducto() {
		return "producto";
	}

	public String irMedida() {
		return "medida";
	}

	public String irGenero() {
		return "genero";
	}

	public String irCategoria() {
		return "categoria";
	}

	public String irBodega() {
		return "bodega";
	}

	public String irUsuarioRol() {
		return "usuario_rol";
	}

	

	

	public Integer getIdAutomovil() {
		return idAutomovil;
	}


	public void setIdAutomovil(Integer idAutomovil) {
		this.idAutomovil = idAutomovil;
	}


	public ManagerAutomovil getManagerAutomovil() {
		return managerAutomovil;
	}


	public void setManagerAutomovil(ManagerAutomovil managerAutomovil) {
		this.managerAutomovil = managerAutomovil;
	}


	public String getClaveAnterior() {
		return claveAnterior;
	}

	public void setClaveAnterior(String claveAnterior) {
		this.claveAnterior = claveAnterior;
	}

	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}


	public List<Automovil> getListaAutomovil() {
		return listaAutomovil;
	}


	public void setListaAutomovil(List<Automovil> listaAutomovil) {
		this.listaAutomovil = listaAutomovil;
	}


	public Automovil getAutomovil() {
		return automovil;
	}


	public void setAutomovil(Automovil automovil) {
		this.automovil = automovil;
	} 
	

}
