package automovil.model.gerente;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import automovil.model.entities.Alquiler;
import automovil.model.entities.Automovil;
import automovil.model.entities.EstadoAlquiler;
import automovil.model.entities.Usuario;

import automovil.model.manager.ManagerDAO;




/**
 * Session Bean implementation class 
 */
@Stateless
@LocalBean
public class ManagerEstadoAlquiler {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerEstadoAlquiler() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<EstadoAlquiler>findAllEstadoAlquiler() throws Exception{
    	try {        	
        	return managerDAO.findAll(EstadoAlquiler.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de estado de alquileres");
		}
    	
    }
 
    public EstadoAlquiler findByIdEstadoAlquileres(Integer id) {
    	
    	try {
			return (EstadoAlquiler) managerDAO.findById(EstadoAlquiler.class, id);
			//managerDAO.findWhere(clase, pClausulaWhere, pOrderBy)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }
 /*public Usuario findWhereCorreoUsuario(String correo) {
    	
    	try {
			return (Usuario) managerDAO.findById(Usuario.class, id);
			managerDAO.findWhere(clase, pClausulaWhere, pOrderBy)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }*/
    public void insertarEstadoAlquiler(EstadoAlquiler us) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(us);
    }
    
    public void actualizarEstadoAlquiler(EstadoAlquiler Estadoalquiler)throws Exception{
    	try {
    		managerDAO.actualizar(Estadoalquiler);
		} catch (Exception e) {
			e.printStackTrace();
		}
						
    }
    
    public void eliminarEstadoAlquiler(EstadoAlquiler id) throws Exception {
    	
			managerDAO.eliminar(EstadoAlquiler.class, id);
    }
    
} 
