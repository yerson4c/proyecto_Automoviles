package automovil.controller.gerente;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import automovil.controller.cliente.BeanAlquiler;
import automovil.controller.login.BeanLogin;
import automovil.model.entities.Alquiler;
import automovil.model.entities.Automovil;
import automovil.model.gerente.ManagerAlquiler;
import automovil.model.gerente.ManagerAutomovil;
import automovil.model.gerente.ManagerUsuarioRol;
import automovil.model.manager.DatosDTO;
import automovil.model.manager.ReporteDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sun.misc.BASE64Encoder;



@Named
@SessionScoped
public class BeanReportes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date fecha;
	
    
    
    private ReporteDTO reportesCharts;


	
	
	@EJB
	private ManagerAutomovil managerAutomovil;
	
	@EJB
	private ManagerUsuarioRol managerUsuarioRol;

	@EJB
	private ManagerAlquiler managerAlquiler;
	
	private List<Alquiler>lstAlquiler;

	@Inject
	BeanLogin login;

	@Inject
	BeanReportes beanAuto;
	
//	@Inject
//	BeanLogin login;


	
	@PostConstruct
	public void inicializar() {
		try {
			reportesCharts = new ReporteDTO();
			lstAlquiler=new ArrayList<>();
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error: ");
			
		}
		
	}

	public void obtenerN() throws Exception {
	
	}
	
	public void obtenerDatos() throws Exception{
		
		
		List<DatosDTO>lstDatos=new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		sdf.format(fecha);
		int mes=sdf.getCalendar().get(Calendar.MONTH);
		mes=mes+1;
		int dia=sdf.getCalendar().get(Calendar.DAY_OF_MONTH);
		int anio=sdf.getCalendar().get(Calendar.YEAR);
			System.out.println("dia:"+dia);
			System.out.println("mes:"+mes);
			System.out.println("anio:"+anio);

		
		lstAlquiler=managerAlquiler.findAllAlquilerByFechaInicio(dia, mes, anio);
		DatosDTO report;
		for (Alquiler a : lstAlquiler) {
			report=new DatosDTO();
			report.setColor(a.getAutomovil().getColor());
			report.setFechaAlquilerFin(a.getFechafin());
			report.setFechaAlquilerInicio(a.getFechainicio());
			report.setMarca(a.getAutomovil().getMarca());
			report.setModelo(a.getAutomovil().getModelo());
			report.setPlaca(a.getAutomovil().getPlaca());
			report.setPrecio(a.getAutomovil().getPrecio());
			report.setUsuario(a.getUsuarioRol().getUsuario().getNombreUsuario()+" "+a.getUsuarioRol().getUsuario().getApellidoUsuario());
		
			lstDatos.add(report);
		}
		reportesCharts.setLstReporte(lstDatos);
		
	}

	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ReporteDTO getReportesCharts() {
		return reportesCharts;
	}

	public void setReportesCharts(ReporteDTO reportesCharts) {
		this.reportesCharts = reportesCharts;
	}

	public List<Alquiler> getLstAlquiler() {
		return lstAlquiler;
	}

	public void setLstAlquiler(List<Alquiler> lstAlquiler) {
		this.lstAlquiler = lstAlquiler;
	}

	
	
	
}
