package automovil.model.gerente;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import automovil.model.entities.Alquiler;

import automovil.model.manager.ManagerDAO;




/**
 * Session Bean implementation class 
 */
@Stateless
@LocalBean
public class ManagerAlquiler {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerAlquiler() {
        
    }
    
    @SuppressWarnings("unchecked")
	public List<Alquiler>findAllAlquilerByIdUsuario(Integer id) throws Exception{
    	List<Alquiler>lstAlquiler=new ArrayList<>();
    	try {   
    		lstAlquiler=managerDAO.findWhere(Alquiler.class, "o.usuarioRol="+id, null);
        	return lstAlquiler;
        	
		} catch (Exception e) {
			throw new Exception("No existe registro de alquileres");
		}
    	
    }
    @SuppressWarnings("unchecked")
  	public List<Alquiler>findAllAlquiler() throws Exception{
      	List<Alquiler>lstAlquiler=new ArrayList<>();
      	try {   
      		lstAlquiler=managerDAO.findAll(Alquiler.class);
      		return lstAlquiler;
          	
  		} catch (Exception e) {
  			throw new Exception("No existe registro de alquileres");
  		}
      	
      }
    public Alquiler findByIdAlquileres(Integer id) {
    	
    	try {
			return (Alquiler) managerDAO.findById(Alquiler.class, id);
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
    public void insertarAlquiler(Alquiler us) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(us);
    }
    
    public void actualizarAlquiler(Alquiler alquiler)throws Exception{
    	try {
    		managerDAO.actualizar(alquiler);
		} catch (Exception e) {
			e.printStackTrace();
		}
						
    }
    
    public void eliminarAlquiler(Integer id) throws Exception {
    	
			managerDAO.eliminar(Alquiler.class, id);
    }
    
} 
