
package datosDAO;

import database.Conexion;
import datos.interfaces.CRUDGeneralInterface;
import entidades.Categoria;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CategoriaDAO implements CRUDGeneralInterface<Categoria>{
    
    private final Conexion conectar;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;
    
    public CategoriaDAO(){        
        conectar = Conexion.getInstance();
    }

    @Override
    public List<Categoria> getAll(String list) {
        List<Categoria> registros = new ArrayList();
        try {
        ps = conectar.conectar().prepareStatement("SELECT * FROM categoria WHERE nombre LIKE ?", 
                                                 ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                 ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, "%" + list + "%");
        rs = ps.executeQuery();
        while(rs.next()) {
            registros.add(new Categoria(
                rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)
            ));
        }
        ps.close();
        rs.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    } finally {
        ps = null;
        rs = null;
        conectar.desconectar();
    }
    return registros;
    }

    @Override
    public boolean insert(Categoria object) {
        resp = false;
        try{
            ps = conectar.conectar().prepareStatement("INSERT INTO categoria(nombre, descripcion, estado) VALUES (?, ?, ?)");
            ps.setString(1, object.getNombre());
            ps.setString(2, object.getDescripcion());
            ps.setBoolean(3, object.isActivo());
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconectar();
        }
        return resp;
    }
    
    @Override
    public boolean update(Categoria object) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement
        ("Update categoria SET nombre=?, descripcion =? where id= ?");
            ps.setString(1, object.getNombre());
            ps.setString(2, object.getDescripcion());
            ps.setInt(3, object.getId());
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconectar();
        }
        return resp;
    }

    @Override
    public boolean onVariable(int id) {
       resp = false;
        try {
            ps = conectar.conectar().prepareStatement("Update categoria SET estado=1, where id= ?");
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconectar();
        }
        return resp;
    }

    @Override
    public boolean offVariable(int id) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement
        ("Update categoria SET estado=0, where id= ?");
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconectar();
        }
        return resp;
    }

    @Override
    public boolean exist(String text) {
         resp = false;
        try {
        ps = conectar.conectar().prepareStatement("select nombre from categoria where id = ?", 
                                                 ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                 ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, text);
        rs = ps.executeQuery();
        if(rs.next()) {
            resp = true;
        }
        ps.close();
        rs.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    } finally {
        ps = null;
        rs = null;
        conectar.desconectar();
    }
    return resp;
    }

    @Override
    public int total() {
        int totalRegistro = 0;
        try {
        ps = conectar.conectar().prepareStatement("select count(id) from categoria", 
                                                 ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                 ResultSet.CONCUR_READ_ONLY);
        rs = ps.executeQuery();
        if(rs.next()) {
            totalRegistro = rs.getInt(1);
        }
        ps.close();
        rs.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    } finally {
        ps = null;
        rs = null;
        conectar.desconectar();
    }
    return totalRegistro;
    }
}