package DAO;

import Model.Usuario;
import interfaces.IUsuarioDAO;
import Model.Entrenador;
import Model.Pokemon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuarioDAO{

	public void crearTablaUsuario() {
	    String sql = "CREATE TABLE IF NOT EXISTS usuario (" +
	                 "id INT PRIMARY KEY," +
	                 "email VARCHAR(255) NOT NULL," +
	                 "nombre_completo VARCHAR(255) NOT NULL," +
	                 "nickname VARCHAR(255) NOT NULL UNIQUE," +
	                 "numero_celular VARCHAR(15)" +
	                 ");";

	    try (Connection conn = Conexion.getConexion();
	         Statement stmt = conn.createStatement()) {

	        stmt.executeUpdate(sql);
	        System.out.println("Tabla 'usuario' creada con éxito.");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
    public boolean agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (id, email, nombre_completo, nickname, numero_celular) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, usuario.getId());  // Asignamos el id manualmente
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getNombreCompleto());
            stmt.setString(4, usuario.getNickname());
            stmt.setString(5, usuario.getNumeroCelular());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                agregarEntrenadoresDeUsuario(usuario);
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void agregarEntrenadoresDeUsuario(Usuario usuario) {
        // Asumimos que los entrenadores ya están creados y se agregan al usuario
        EntrenadorDAO entrenadorDAO = new EntrenadorDAO();
        for (Entrenador entrenador : usuario.getEntrenadores()) {
            entrenador.setUsuarioId(usuario.getId()); // Asignar el usuarioId al entrenador
            entrenadorDAO.agregarEntrenador(entrenador); // Guardar el entrenador asociado
        }
    }

    public Usuario obtenerUsuarioPorNickname(String nickname) {
        String sql = "SELECT * FROM usuario WHERE nickname = ?";  
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, nickname);  // Pasamos el nickname como parámetro
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("nombre_completo"),
                    rs.getString("nickname"),
                    rs.getString("numero_celular")
                );
                
                // Obtener los entrenadores asociados al usuario
                List<Entrenador> entrenadores = obtenerEntrenadoresPorUsuario(usuario.getId());
                usuario.setEntrenadores(entrenadores);
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> obtenerTodosUsuarios() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("nombre_completo"),
                    rs.getString("nickname"),
                    rs.getString("numero_celular")
                );
                
                // Obtener los entrenadores asociados al usuario
                List<Entrenador> entrenadores = obtenerEntrenadoresPorUsuario(usuario.getId());
                usuario.getEntrenadores().addAll(entrenadores);
                
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    public List<String> obtenerTodosNicknames() {
        String sql = "SELECT nickname FROM usuario";
        List<String> nicknames = new ArrayList<>();
        
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                nicknames.add(rs.getString("nickname"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nicknames;
    }


    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET email = ?, nombre_completo = ?, nickname = ?, numero_celular = ? WHERE id = ?";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getNombreCompleto());
            stmt.setString(3, usuario.getNickname());
            stmt.setString(4, usuario.getNumeroCelular());
            stmt.setInt(5, usuario.getId());  // Usamos el id manualmente
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Actualizar entrenadores asociados al usuario
                actualizarEntrenadoresDeUsuario(usuario);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void actualizarEntrenadoresDeUsuario(Usuario usuario) {
        EntrenadorDAO entrenadorDAO = new EntrenadorDAO();
        for (Entrenador entrenador : usuario.getEntrenadores()) {
            entrenador.setUsuarioId(usuario.getId()); // Usamos el id manualmente
            entrenadorDAO.actualizarEntrenador(entrenador); // Actualizamos el entrenador
        }
    }

    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, id);  // Usamos el id manualmente
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Eliminar los entrenadores asociados al usuario
                eliminarEntrenadoresDeUsuario(id);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void eliminarEntrenadoresDeUsuario(int usuarioId) {
        String sql = "DELETE FROM entrenador WHERE usuario_id = ?";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, usuarioId);  // Usamos el id manualmente
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Entrenador> obtenerEntrenadoresPorUsuario(int usuarioId) {
    	EntrenadorDAO entrenadorDAO = new EntrenadorDAO();
        String sql = "SELECT * FROM entrenador WHERE usuario_id = ?";
        List<Entrenador> entrenadores = new ArrayList<>();
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, usuarioId);  // Usamos el id manualmente
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Obtén el id del entrenador desde el ResultSet
                int entrenadorId = rs.getInt("id");
                
                // Llama al método obtenerEntrenador(id) de EntrenadorDAO para obtener el objeto Entrenador
                Entrenador entrenador = entrenadorDAO.obtenerEntrenador(entrenadorId);
                
                // Agregar el entrenador a la lista local
                entrenadores.add(entrenador);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Retornar la lista de entrenadores
        return entrenadores;
    }



    public static void guardar() {
        UsuarioDAO u = new UsuarioDAO();
        for (Usuario usuario : Usuario.getTodosUsuarios()) {
            if (u.obtenerUsuarioPorNickname(usuario.getNickname()) != null) {
            	
                u.actualizarUsuario(usuario);
            } else {
                u.agregarUsuario(usuario);
            }
        }

        EntrenadorDAO.guardar();
        PokemonDAO.guardar();
    }
}

