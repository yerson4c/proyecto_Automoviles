package automovil.model.cliente;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import automovil.model.entities.Usuario;
import automovil.model.manager.ManagerDAO;




/**
 * Session Bean implementation class ManagerCategoria
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
			throw new Exception("No existe registro usuarios");
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
    	//managerDAO.insertar(cat);
    	em.merge(us);
    }
    
    public void actualizarUsuario(Usuario usuario)throws Exception{
    	Usuario u=findByIdUsuario(usuario.getIdUsuario());
    	if (u==null) {
			throw new Exception("No existe el punto de venta especificado");
			
		} else {
			u.setNombreUsuario(usuario.getNombreUsuario());
			u.setApellidoUsuario(usuario.getApellidoUsuario());
			u.setDireccionUsuario(usuario.getDireccionUsuario());
			u.setTelefonoUsuario(usuario.getTelefonoUsuario());
			u.setCorreoUsuario(usuario.getCorreoUsuario());
			//u.setContrasenia(usuario.getContrasenia());

			u.setTelefonoUsuario(usuario.getTelefonoUsuario());
						
			managerDAO.actualizar(u);
		}
    	
    }
    
    public void eliminarUsuario(Integer id) throws Exception {
    	
			managerDAO.eliminar(Usuario.class, id);
    }
    
}
