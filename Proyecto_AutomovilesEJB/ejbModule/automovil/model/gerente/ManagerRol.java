package automovil.model.gerente;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import automovil.model.entities.Rol;
import automovil.model.manager.ManagerDAO;



/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerRol {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerRol() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Rol>findAllRol() throws Exception{
    	try {        	
        	return managerDAO.findAll(Rol.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de roles");
		}
    	
    }
 
    @SuppressWarnings("unchecked")
   	public List<Rol>findAllRolADM() throws Exception{
       	try {        	
           //	return managerDAO.findAll(Rol.class);
        	return managerDAO.findWhere(Rol.class, "o.idRol<=3", null);
   		} catch (Exception e) {
   			throw new Exception("No existe registro de roles");
   		}
       	
       }
    public Rol findByIdRol(Integer id) {
    	System.out.println("id en el manager "+id);
    	try {
			return (Rol) managerDAO.findById(Rol.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    	
    }
    
    public void insertarRol(Rol rol) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(rol);
    }
    
    public void actualizarRol(Rol rol)throws Exception{
    	Rol r=findByIdRol(rol.getIdRol());
    	if (r==null) {
			throw new Exception("No existe el rol especificado");
			
		} else {
			r.setTipoRol(rol.getTipoRol());
			managerDAO.actualizar(r);
		}
    	
    }
    
    public void eliminarRol(Integer id) throws Exception {
    	
			managerDAO.eliminar(Rol.class, id);
    }
    
}
