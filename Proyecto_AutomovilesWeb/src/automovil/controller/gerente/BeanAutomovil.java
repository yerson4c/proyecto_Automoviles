package automovil.controller.gerente;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
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
import automovil.model.gerente.ManagerAlquiler;
import automovil.model.gerente.ManagerAutomovil;
import automovil.model.gerente.ManagerUsuarioRol;
import sun.misc.BASE64Encoder;



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
	private UploadedFile uploadedFile; // +getter+setter
    private RenderedImage renderedImg;
	private InputStream inputstream;
	private FileInputStream inputFile;
    private Alquiler alquiler;
	
	@EJB
	private ManagerAutomovil managerAutomovil;
	
	@EJB
	private ManagerUsuarioRol managerUsuarioRol;

	@EJB
	private ManagerAlquiler managerAlquiler;

	@Inject
	BeanLogin login;

	@Inject
	BeanAutomovil beanAuto;
	
//	@Inject
//	BeanLogin login;

	
	
	//contrase�a
	private String claveAnterior;
	private String claveNueva;
	
	@PostConstruct
	public void inicializar() {
		try {
			
			alquiler=new Alquiler();
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
			automovil.setPrecio(u.getPrecio());
			automovil.setModelo(u.getModelo());
			automovil.setPlaca(u.getPlaca());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionListenerInsertarAlquiler() {
		try {
			if (alquiler.getFechainicio().toString().length() > 0 && 
					alquiler.getFechafin().toString().length() > 0 ) {
				
				Alquiler u=new Alquiler();
				
				BigDecimal valorInicio=BigDecimal.ZERO;
				BigDecimal valorFin=BigDecimal.ZERO;
				BigDecimal resp=BigDecimal.ZERO;
				valorInicio=new BigDecimal(alquiler.getFechainicio().getTime());
				valorFin=new BigDecimal(alquiler.getFechafin().getTime());
				System.out.println(valorFin.longValue());
				System.out.println("valor inicio "+valorInicio.longValue());
				resp=valorFin.subtract(valorInicio);
				System.out.println("resp "+resp.longValue());
				if (resp.longValue()<86400000 && resp.longValue()>0) {
					
				
				u.setFechainicio(new Timestamp(alquiler.getFechainicio().getTime()));
				u.setFechafin(new Timestamp(alquiler.getFechafin().getTime()));
				u.setEstado("A");
				u.setAprobado("NO");	
				u.setEntregado("NO");
				u.setGasolina(alquiler.getGasolina());
				u.setRecepcion("xxxxxxxxxx");
				u.setObservaciones(alquiler.getObservaciones());
				u.setUsuarioRol(managerUsuarioRol.findByIdUsuarioRol(login.getLoginDTO().getIdRolUsuario()));
				u.setAutomovil(managerAutomovil.findByIdAutomovil(automovil.getIdAutomovil()));
				boolean validar=false;
				List<Alquiler>lstAlq=managerAlquiler.FindlstAlquilerWhereUserAutoAlq(u.getUsuarioRol().getIdUsuarioRol(), u.getAutomovil().getIdAutomovil(), "NO");
				if (lstAlq.size()==0) {
					managerAlquiler.insertarAlquiler(u);
					
					inicializar();
					//listaMedida=managerMedida.findAllMedidas();
					//managerbit.crearEvento("actionListenerActualizarMedida()", "actualiza una medida ");
					JSFUtil.crearMensajeInfo("Alquilado exitosamente");
				}else {
					JSFUtil.crearMensajeError("Error al crear: Ya existe registro con ese alquiler");
				}
				
				} else {
					JSFUtil.crearMensajeError("Error: Solo se permite ingresar maximo 1 día completo");
				}
			} else {
				JSFUtil.crearMensajeError("Debe ingresar las fechas"); 
			}		
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al insertar");
		}

	}

	public void actionListenerActualizarAutomovil() {
		try {
			BufferedImage image = null;
			if (uploadedFile != null && uploadedFile.getSize() > 0) {
//				if (uploadedFile.getFileName().matches(".*\\.(png|jpeg|jpg|gif)$")) {
//					image = ImageIO.read(uploadedFile.getInputstream());
//				} else {
//					JSFUtil.crearMensajeError("Seleccione una imagen con los siguientes formatos: png, jpeg, jpg, gif");
//					return;
//				}
			} else {
				System.out.println(System.getProperty("user.dir"));

				InputStream inputstream = new FileInputStream("d:\\foto\\foto.jpg");

				image = ImageIO.read(inputstream);
			}
			
			if (automovil.getPlaca().toString().length() > 0) {
				Automovil a=managerAutomovil.findByIdAutomovil(automovil.getIdAutomovil());
				String cod64 = encodeToString(image, "jpg");
				
				a.setAnio(automovil.getAnio());
				a.setColor(automovil.getColor());
				System.out.println("codigo "+cod64);
				a.setFoto(cod64);
				a.setPrecio(automovil.getPrecio());
				a.setEstadoAutomovil(automovil.getEstadoAutomovil());
				a.setMarca(automovil.getMarca());
				a.setModelo(automovil.getModelo());
				a.setPlaca(automovil.getPlaca());
				managerAutomovil.actualizarAutomovil(a);
				listaAutomovil=managerAutomovil.findAllAutomovil();
				

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

	public void limpiarAutomovil() {
		automovil=new Automovil();
		automovil.setAnio(null);
		automovil.setColor("");
		automovil.setEstadoAutomovil("");
		automovil.setMarca("");
		automovil.setModelo("");
		automovil.setPlaca(null);
		automovil.setPrecio(null);
		
	}

	/***
	 * M�todo de ingreso de Imagen
	 * @param event 
	 * @throws IOException 
	 * */
	public void handleFileUploadImagen(FileUploadEvent event) throws IOException {
		//nombreArchivoImagen = "";
//		if (!ModelUtil.isEmpty(nombreArchivo)) {
//			nombreArchivoImagen = "";
//		}
		uploadedFile = event.getFile();
		uploadedFile.getFileName();
		System.out.println(uploadedFile.getFileName());
		System.out.println(uploadedFile.getInputstream());
//		FacesContext context = FacesContext.getCurrentInstance();
//		if (fileImagen != null) {
//			try {
//				long numero = managerPlanEstrat.findAllPlanEstrategico().size() + 2;
//				nombreArchivoImagen = managerPlanEstrat.subirAdjunto(fileImagen.getInputstream(), fileImagen.getFileName(), numero);
//
//				context.addMessage(null,
//						new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo Cargado", fileImagen.getFileName()));
//				System.out.println("**** nobre archivo img "+nombreArchivoImagen);
//			} catch (Exception e) {
//				JSFUtil.crearMensajeINFO(e.getMessage(), null);
//				e.printStackTrace();
//			}
//		}
		
	}

	
	public void actionListenerInsertarAutomovil() {
		try {
//			BufferedImage image = null;
//			
//			//if (uploadedFile != null) {
//				//if (uploadedFile.getFileName().matches(".*\\.(png|jpeg|jpg|gif)$")) {
//		//	InputStream inputstream = new FileInputStream(System.getProperty("user.dir"));
//			//String data = "data:image/jpeg;base64,/9...";
//			String base64Image = automovil.getFoto();
//			InputStream inputstream = new FileInputStream(inputFile.toString());
//			
//			//base64ImageString = imagease64String.replace('data:image/jpeg;base64,',''))
//			
//			 byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
//					image = ImageIO.read(inputstream);
//					System.out.println("inputstream "+inputstream.toString());
//					System.out.println("iamge strea, "+image.toString());
//				//} else {
//				//	JSFUtil.crearMensajeError("Seleccione una imagen con los siguientes formatos: png, jpeg, jpg, gif");
//				//	return;
//				//}
////			} else {
////				System.out.println(System.getProperty("user.dir"));
////
////				InputStream inputstream = new FileInputStream("d:\\foto\\foto.png");
////
////				image = ImageIO.read(inputstream);
////			}
			BufferedImage image = null;
			if (uploadedFile != null && uploadedFile.getSize() > 0) {
				if (uploadedFile.getFileName().matches(".*\\.(png|jpeg|jpg|gif)$")) {
					image = ImageIO.read(uploadedFile.getInputstream());
				} else {
					JSFUtil.crearMensajeError("Seleccione una imagen con los siguientes formatos: png, jpeg, jpg, gif");
					return;
				}
			} else {
				System.out.println(System.getProperty("user.dir"));

				InputStream inputstream = new FileInputStream("d:\\foto\\foto.jpg");

				image = ImageIO.read(inputstream);
			}
			
			if (automovil.getPlaca().toString().length() > 0) {
				Automovil a=new Automovil();
				a.setAnio(automovil.getAnio());
				String cod64 = encodeToString(image, "png");
				a.setFoto(cod64);
				a.setDescripcion(automovil.getDescripcion());
				a.setColor(automovil.getColor());
				a.setEstadoAutomovil(automovil.getEstadoAutomovil());
				a.setMarca(automovil.getMarca());
				a.setPrecio(automovil.getPrecio());
				a.setModelo(automovil.getModelo());
				a.setPlaca(automovil.getPlaca());
				managerAutomovil.insertarAutomovil(a);
				
				listaAutomovil=managerAutomovil.findAllAutomovil();
				JSFUtil.crearMensajeInfo("Insertado  exitosamente");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar la placa"); 
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al insertar: "+e.getMessage());
		}

	}
	
	public  BufferedImage decodeToImage(String imageString) {
	    BufferedImage image = null;
	    byte[] imageByte;
	    try {
	        Base64.Decoder decoder = Base64.getDecoder();
	        imageByte = decoder.decode(imageString);
	        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	        image = ImageIO.read(bis);
	        bis.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return image;
	}
	
	public String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();
			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageString;
	}
	public void actionListenerEliminarAutomovil(Integer id) {
		try {
			managerAutomovil.eliminarAutomovil(id);
			listaAutomovil=managerAutomovil.findAllAutomovil();
			JSFUtil.crearMensajeInfo("Ha sido eliminado exitosamente");
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


	/**
	 * @return the uploadedFile
	 */
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}


	/**
	 * @param uploadedFile the uploadedFile to set
	 */
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}


	/**
	 * @return the renderedImg
	 */
	public RenderedImage getRenderedImg() {
		return renderedImg;
	}


	/**
	 * @param renderedImg the renderedImg to set
	 */
	public void setRenderedImg(RenderedImage renderedImg) {
		this.renderedImg = renderedImg;
	}


	/**
	 * @return the inputstream
	 */
	public InputStream getInputstream() {
		return inputstream;
	}


	/**
	 * @param inputstream the inputstream to set
	 */
	public void setInputstream(InputStream inputstream) {
		this.inputstream = inputstream;
	}


	/**
	 * @return the inputFile
	 */
	public FileInputStream getInputFile() {
		return inputFile;
	}


	/**
	 * @param inputFile the inputFile to set
	 */
	public void setInputFile(FileInputStream inputFile) {
		this.inputFile = inputFile;
	}


	public Alquiler getAlquiler() {
		return alquiler;
	}


	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
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


 
	

}
