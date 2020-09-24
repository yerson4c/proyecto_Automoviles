package automovil.model.manager;

import java.math.BigDecimal;
import java.util.Date;

public class DatosDTO {
	private String modelo;
	private String placa;
	private String marca;
	private String color;
	private BigDecimal precio;
	
	private Date fechaAlquilerInicio;  
	private Date fechaAlquilerFin;
	private String usuario;
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public Date getFechaAlquilerInicio() {
		return fechaAlquilerInicio;
	}
	public void setFechaAlquilerInicio(Date fechaAlquilerInicio) {
		this.fechaAlquilerInicio = fechaAlquilerInicio;
	}
	public Date getFechaAlquilerFin() {
		return fechaAlquilerFin;
	}
	public void setFechaAlquilerFin(Date fechaAlquilerFin) {
		this.fechaAlquilerFin = fechaAlquilerFin;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	
}
