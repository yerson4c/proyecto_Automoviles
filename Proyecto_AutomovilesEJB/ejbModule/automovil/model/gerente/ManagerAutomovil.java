package automovil.model.gerente;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import automovil.model.entities.Automovil;


import automovil.model.manager.ManagerDAO;




/**
 * Session Bean implementation class 
 */
@Stateless
@LocalBean
public class ManagerAutomovil {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerAutomovil() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Automovil>findAllAutomovil() throws Exception{
    	try {        	
        	return managerDAO.findAll(Automovil.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de automoviles");
		}
    	
    }
 
    public Automovil findByIdAutomovil(Integer id) {
    	
    	try {
			return (Automovil) managerDAO.findById(Automovil.class, id);
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
    public void insertarAutomovil(Automovil us) throws Exception {
    	managerDAO.insertar(us);
    	//em.merge(us);
    }
    
    public void actualizarAutomovil(Automovil auto)throws Exception{
    	try {
    		managerDAO.actualizar(auto);
		} catch (Exception e) {
			e.printStackTrace();
		}
						
    }
    
    public void eliminarAutomovil(Integer id) throws Exception {
    	
			managerDAO.eliminar(Automovil.class, id);
    }
    
} 
