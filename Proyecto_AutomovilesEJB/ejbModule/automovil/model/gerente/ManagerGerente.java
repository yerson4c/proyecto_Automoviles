package automovil.model.gerente;


import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import automovil.model.manager.ManagerDAO;




/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerGerente {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerGerente() {
        // TODO Auto-generated constructor stub
    }
//    
//    @SuppressWarnings("unchecked")
//	public List<Gerente>findAllGerente() throws Exception{
//    	try {        	
//        	return managerDAO.findAll(Gerente.class);
//		} catch (Exception e) {
//			throw new Exception("No existe registro gerentes");
//		}
//    	
//    }
// 
//    public Gerente findByIdGerente(Integer id) {
//    	
//    	try {
//			return (Gerente) managerDAO.findById(Gerente.class, id);
//			//managerDAO.findWhere(clase, pClausulaWhere, pOrderBy)
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;	
//    }
 /*public Usuario findWhereCorreoUsuario(String correo) {
    	
    	try {
			return (Usuario) managerDAO.findById(Usuario.class, id);
			managerDAO.findWhere(clase, pClausulaWhere, pOrderBy)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }*/
//    public void insertarGerente(Gerente us) throws Exception {
//    	//managerDAO.insertar(cat);
//    	em.merge(us);
//    }
//    
//    public void actualizarGerente(Gerente gerente)throws Exception{
//    	try {
//    		managerDAO.actualizar(gerente);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//						
//    }
//    
//    public void eliminarGerente(Integer id) throws Exception {
//    	
//			managerDAO.eliminar(Gerente.class, id);
//    }
//    
} 
