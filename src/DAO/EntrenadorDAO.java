package DAO;

import interfaces.IEntrenadorDAO;
import Model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO implements IEntrenadorDAO {

    // Método para crear la tabla 'entrenador'
    public static void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS entrenador (" +
                     "id INT PRIMARY KEY," +
                     "nombre VARCHAR(100)," +
                     "fecha_nacimiento DATE," +
                     "nacionalidad VARCHAR(50)," +
                     "genero CHAR(1)," +
                     "edad INT," +
                     "usuario_id INT," +
                     "FOREIGN KEY (usuario_id) REFERENCES usuario(id)" +
                     ")";
        
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabla 'entrenador' creada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para crear la tabla 'pokemon'
    public static void crearTablaPokemon() {
        String sql = "CREATE TABLE IF NOT EXISTS pokemon (" +
                     "id INT PRIMARY KEY," +
                     "tipo VARCHAR(50)," +
                     "especie VARCHAR(100)," +
                     "energia FLOAT," +
                     "poder FLOAT," +
                     "entrenador_id INT," +
                     "vida FLOAT," +
                     "FOREIGN KEY (entrenador_id) REFERENCES entrenador(id)" +
                     ")";
        
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabla 'pokemon' creada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean agregarEntrenador(Entrenador entrenador) {
        String sql = "INSERT INTO entrenador (id, nombre, fecha_nacimiento, nacionalidad, genero, edad, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, entrenador.getId());
            stmt.setString(2, entrenador.getNombre());
            stmt.setString(3, entrenador.getFechaNacimiento());
            stmt.setString(4, entrenador.getNacionalidad());
            stmt.setString(5, String.valueOf(entrenador.getGenero()));
            stmt.setInt(6, entrenador.getEdad());
            
            if (entrenador.getUsuarioId() == 0) {
                stmt.setNull(7, Types.INTEGER);
            } else {
                stmt.setInt(7, entrenador.getUsuarioId());
            }
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Entrenador obtenerEntrenador(int id) {
        String sql = "SELECT * FROM entrenador WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Entrenador entrenador = new Entrenador(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("fecha_nacimiento"),
                    rs.getString("nacionalidad"),
                    rs.getString("genero").charAt(0),
                    rs.getInt("edad"),
                    rs.getInt("usuario_id"),
                    1
                );
                
                List<Pokemon> equipo = obtenerEquipo(entrenador.getId());
                entrenador.setEquipo(equipo);
                
                return entrenador;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Entrenador> obtenerTodosEntrenadores() {
        String sql = "SELECT * FROM entrenador";
        List<Entrenador> entrenadores = new ArrayList<>();
        
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Entrenador entrenador = new Entrenador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("fecha_nacimiento"),
                        rs.getString("nacionalidad"),
                        rs.getString("genero").charAt(0),
                        rs.getInt("edad"),
                        rs.getInt("usuario_id"),
                        1
                );
                
                List<Pokemon> equipo = obtenerEquipo(entrenador.getId());
                entrenador.setEquipo(equipo);
                
                entrenadores.add(entrenador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entrenadores;
    }
    
    public static boolean guardarEntrenador(Entrenador entrenador) {
        int nuevoId = generarNuevoId();

        String sql = "INSERT INTO entrenador (id, nombre, usuario_id, nacionalidad, genero, edad, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevoId);
            stmt.setString(2, entrenador.getNombre());
            stmt.setInt(3, entrenador.getUsuarioId());
            stmt.setString(4, entrenador.getNacionalidad());
            stmt.setString(5, String.valueOf(entrenador.getGenero()));
            stmt.setInt(6, entrenador.getEdad());
            stmt.setString(7, entrenador.getFechaNacimiento());

            stmt.executeUpdate();

            entrenador.setId(nuevoId); // Actualizar el ID del entrenador en el objeto
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int generarNuevoId() {
        String sql = "SELECT MAX(id) AS max_id FROM entrenador";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1; // Si no hay entrenadores, comenzar desde 1
    }


    public boolean actualizarEntrenador(Entrenador entrenador) {
        String sql = "UPDATE entrenador SET nombre = ?, fecha_nacimiento = ?, nacionalidad = ?, genero = ?, edad = ?, usuario_id = ? WHERE id = ?";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, entrenador.getNombre());
            stmt.setString(2, entrenador.getFechaNacimiento());
            stmt.setString(3, entrenador.getNacionalidad());
            stmt.setString(4, String.valueOf(entrenador.getGenero()));
            stmt.setInt(5, entrenador.getEdad());
            
            if (entrenador.getUsuarioId() == 0) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, entrenador.getUsuarioId());
            }
            
            stmt.setInt(7, entrenador.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarEntrenador(int id) {
        String sql = "DELETE FROM entrenador WHERE id = ?";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Pokemon> obtenerEquipo(int entrenadorId) {
        String sql = "SELECT * FROM pokemon WHERE entrenador_id = ?";
        List<Pokemon> equipo = new ArrayList<>();
        
        try (Connection conn = Conexion.getConexion(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, entrenadorId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(
                        rs.getInt("id"),
                        Pokemon.pokemonTipo(rs.getString("tipo")),
                        rs.getString("especie"),
                        rs.getFloat("energia"),
                        rs.getFloat("poder"),
                        rs.getInt("entrenador_id"),
                        rs.getFloat("vida")
                );
                equipo.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipo;
    }

    public static void guardar() {
        List<Entrenador> entrenadores = new ArrayList<>(Entrenador.getTodosEntrenadores());
        EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

        for (Entrenador entrenador : entrenadores) {
            if (entrenadorDAO.obtenerEntrenador(entrenador.getId()) != null) {
                entrenadorDAO.actualizarEntrenador(entrenador);
            } else {
                entrenadorDAO.agregarEntrenador(entrenador);
            }
        }
    }
}
