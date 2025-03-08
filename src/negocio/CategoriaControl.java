
package negocio;

import datosDAO.CategoriaDAO;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

public class CategoriaControl {
    private final CategoriaDAO DATOS;
    private Categoria obj;
    private DefaultTableModel tModel;
    public int registrosMostrados;
    public CategoriaControl() {
        this.DATOS = new CategoriaDAO();
        this.obj = new Categoria();
    }

    public DefaultTableModel listar(String texto) {
        List<Categoria> lista = new ArrayList();
        lista.addAll(DATOS.getAll(texto));
        String[] titulos
                = {"id", "nombre", "Descripcion", "Estado"};
        this.tModel = new DefaultTableModel(null, titulos);

        String estado;
        String[] registro = new String[4];
        this.registrosMostrados = 0;
        for (Categoria item : lista) {
            if (item.isActivo()) {
                estado = "activo";
            } else {
                estado = "Inactivo";
            }
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNombre();
            registro[2] = item.getDescripcion();
            registro[3] = estado;
            this.registrosMostrados = this.registrosMostrados +1;
            this.tModel.addRow(registro);
        }
        return this.tModel;
    }

    public String Insertar(String nombre, String descripcion, Boolean estado) {
        if (DATOS.exist(nombre)) {
            return "Registro ya existe";
        } else {
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            obj.setActivo(estado);
            if (DATOS.insert(obj)) {
                return "OK";
            } else {
                return "Error en el registro ";
            }
        }
    }

    public String actualizar(int id, String nombre, String nombreAnterior, String descripcion) {
        if (nombre.equals(nombreAnterior)) {
            obj.setId(id);
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            if (DATOS.update(obj)) {
                return "OK";
            } else {
                return "Error al actualizar";
            }
        } else {
            if (DATOS.exist(nombre)) {
                return "El objeto ya existe";
            } else {
                obj.setId(id);
                obj.setNombre(nombre);
                obj.setDescripcion(descripcion);
                if (DATOS.exist(nombre)) {
                    return "El registro ya existe";
                } else {
                    return "Error en la actualizacion";
                }
            }
        }
    }

    public String desactivar(int id) {
        if (DATOS.offVariable(id)) {
            return "OK";
        } else {
            return "No se puede desactivar el registro";
        }
    }

    public String activar(int id) {
        if (DATOS.onVariable(id)){
            return "OK";
        } else {
            return "No se puede activar el registro";
        }
    }

    public int total() {
        return  DATOS.total();
    }
    
    public int  totalMostrados(){
    return this.registrosMostrados;
    }
}
