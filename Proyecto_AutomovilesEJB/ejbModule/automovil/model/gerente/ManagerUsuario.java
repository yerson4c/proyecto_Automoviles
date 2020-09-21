package automovil.model.gerente;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import automovil.model.entities.Usuario;
import automovil.model.manager.ManagerDAO;




/**
 * Session Bean implementation class 
 */
@Stateless
@LocalBean
public class ManagerUsuario {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerUsuario() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Usuario>findAllUsuarios() throws Exception{
    	try {        	
        	return managerDAO.findAll(Usuario.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de usuarios");
		}
    	
    }
 
    public Usuario findByIdUsuario(Integer id) {
    	
    	try {
			return (Usuario) managerDAO.findById(Usuario.class, id);
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
    public void insertarUsuario(Usuario us) throws Exception {
    	managerDAO.insertar(us);
    	//em.merge(us);
    }
    
    public void actualizarUsuario(Usuario us)throws Exception{
    	try {
    		managerDAO.actualizar(us);
		} catch (Exception e) {
			e.printStackTrace();
		}
						
    }
    
    public void eliminarUsuario(Integer id) throws Exception {
			managerDAO.eliminar(Usuario.class, id);
    }
    
} 
