package automovil.controller.cliente;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

import automovil.model.gerente.*;
import automovil.controller.gerente.JSFUtil;
import automovil.model.entities.Usuario;
import automovil.model.entities.UsuarioRol;




@Named
@SessionScoped
public class BeanUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Usuario> listaUsuario;
	private Usuario  usuario;
	private Integer idUsuario;
	

	@EJB
	private ManagerUsuario managerUsuario;
	
	@EJB
	private ManagerUsuarioRol managerUsuarioRol;
	
	@EJB
	private ManagerRol managerRol;
	
//	@Inject
//	BeanLogin login;

	
	
	//contraseÃ±a
	private String claveAnterior;
	private String claveNueva;
	
	@PostConstruct
	public void inicializar() {
		try {
			listaUsuario=managerUsuario.findAllUsuarios();
			usuario=new Usuario();
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error: ");
			
		}
		
	}

	
	public void actionListenerCargarUsuario(Usuario u) {
		try {
			usuario=new Usuario();
			
			usuario.setApellidoUsuario(u.getApellidoUsuario());
			usuario.setCedulaUsuario(u.getCedulaUsuario());
			usuario.setClaveUsuario(u.getClaveUsuario());
			usuario.setCorreoUsuario(u.getCorreoUsuario());
			usuario.setDireccionUsuario(u.getDireccionUsuario());
			usuario.setFechaNacUsuario(u.getFechaNacUsuario());
			usuario.setGenero(u.getGenero());
			usuario.setIdUsuario(u.getIdUsuario());
			usuario.setNombreUsuario(u.getNombreUsuario());
			usuario.setTelefonoUsuario(u.getTelefonoUsuario());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerActualizarUsuario() {
		try {
			if (usuario.getCedulaUsuario().toString().length() > 0) {
				Usuario u=managerUsuario.findByIdUsuario(usuario.getIdUsuario());
				
				u.setApellidoUsuario(usuario.getApellidoUsuario());
				u.setCedulaUsuario(usuario.getCedulaUsuario());
				u.setClaveUsuario(usuario.getClaveUsuario());
				u.setCorreoUsuario(usuario.getCorreoUsuario());
				u.setDireccionUsuario(usuario.getDireccionUsuario());
				u.setFechaNacUsuario(usuario.getFechaNacUsuario());
				u.setGenero(usuario.getGenero());
				u.setNombreUsuario(usuario.getNombreUsuario());
				u.setTelefonoUsuario(usuario.getTelefonoUsuario());
				managerUsuario.actualizarUsuario(u);
				listaUsuario=managerUsuario.findAllUsuarios();
				

				//listaMedida=managerMedida.findAllMedidas();
				//managerbit.crearEvento("actionListenerActualizarMedida()", "actualiza una medida ");
				JSFUtil.crearMensajeInfo("Actualizado  exitosamente");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar la placa"); 
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarUsuario() {
		
		usuario=new Usuario();
		usuario.setApellidoUsuario("");
		usuario.setCedulaUsuario("");
		usuario.setClaveUsuario("");
		usuario.setCorreoUsuario("");
		usuario.setDireccionUsuario("");
		usuario.setFechaNacUsuario(null);
		usuario.setGenero("");
		usuario.setIdUsuario(null);
		usuario.setNombreUsuario("");
		usuario.setTelefonoUsuario(null);
		
		
	}

	public String actionInsertarUsuario() {
		try {
			if (usuario.getCedulaUsuario().toString().length() > 0) {
				if (usuario.getNombreUsuario().length() > 0 && !usuario.getCedulaUsuario().isEmpty()
						&& !usuario.getCorreoUsuario().isEmpty()) {
					if (usuario.getCorreoUsuario().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
							+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
						if (validarCedula(usuario.getCedulaUsuario())) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
							sdf.format(usuario.getFechaNacUsuario());

							if (CalcularEdad(new GregorianCalendar(sdf.getCalendar().get(Calendar.YEAR),
									sdf.getCalendar().get(Calendar.MONTH),
									sdf.getCalendar().get(Calendar.DAY_OF_MONTH))) >= 17) {

				Usuario u=new Usuario();
				
				u.setApellidoUsuario(usuario.getApellidoUsuario());
				u.setCedulaUsuario(usuario.getCedulaUsuario());
				u.setClaveUsuario(usuario.getClaveUsuario());
				u.setCorreoUsuario(usuario.getCorreoUsuario());
				u.setDireccionUsuario(usuario.getDireccionUsuario());
				u.setFechaNacUsuario(usuario.getFechaNacUsuario());
				u.setGenero(usuario.getGenero());
				u.setNombreUsuario(usuario.getNombreUsuario());
				u.setTelefonoUsuario(usuario.getTelefonoUsuario());
				
				List<Usuario>lstUsuario=managerUsuario.findWhereCorreoUsuario(usuario.getCorreoUsuario());
				if (lstUsuario.size()==0) {
					managerUsuario.insertarUsuario(u);
					listaUsuario=managerUsuario.findAllUsuarios();
					
					List<Usuario> lstUsu=managerUsuario.findWhereCorreoUsuario(usuario.getCorreoUsuario());
					UsuarioRol urol=new UsuarioRol();
					urol.setUsuario(managerUsuario.findByIdUsuario(lstUsu.get(0).getIdUsuario()));
					urol.setRol(managerRol.findByIdRol(2));
					managerUsuarioRol.insertarUsuarioRol(urol);
					
					listaUsuario=managerUsuario.findAllUsuarios();
					
					
					JSFUtil.crearMensajeInfo("Usuario creado correctamente, por favor iniciar sesión");
					return "login";
				} else {
					JSFUtil.crearMensajeError("Error al crear: Ya existe registro con ese correo");
					return "";
				}
				
							} else {
								JSFUtil.crearMensajeError("El usuario debe ser mayor de edad");
								return "";
							}
						} else {
							JSFUtil.crearMensajeError("Debe ingresar una cédula válida");
							return "";
						}
					} else {
						JSFUtil.crearMensajeError("Debe ingresar un correo válido");
						return "";
					}
				} else {
					JSFUtil.crearMensajeError("Debe ingresar todos los campos");
					return "";
				}
			} else {
				JSFUtil.crearMensajeError("Debe ingresar la cédula");
				return "";
				 
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al insertar: "+e.getMessage() );
			return "";
			
		}
		
	}
	

	public void actionListenerEliminarUsuario(Integer id) {
		try {
			managerUsuario.eliminarUsuario(id);
			listaUsuario=managerUsuario.findAllUsuarios();
			
			JSFUtil.crearMensajeInfo("Usuario ha sido eliminado");
		} catch (Exception e) {
			
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
//			JSFUtil.crearMensajeInfo("Su contraseï¿½a se ha actualizado correctamente");
//		}else {
//			JSFUtil.crearMensajeError("Contraseï¿½as no coinciden, ingrese de nuevo");
//		}
//		
//	}
	
	
	public int CalcularEdad(Calendar fechaNac) {

		Calendar fechaActual = Calendar.getInstance();
		int years = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
		int months = fechaActual.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
		int days = fechaActual.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
		// Hay que comprobar si el dï¿½a de su cumpleaï¿½os es posterior
		// a la fecha actual, para restar 1 a la diferencia de aï¿½os,
		// pues aï¿½n no ha sido su cumpleaï¿½os.
		if (months < 0 // Aï¿½n no es el mes de su cumpleaï¿½os
				|| (months == 0 && days < 0)) { // o es el mes pero no ha llegado el dï¿½a.
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

	

	

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Integer getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
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
	

}
