package DAO;

import Model.Pokemon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import interfaces.IPokemonDAO;

public class PokemonDAO implements IPokemonDAO{
	
    public static void crearTablaPokemon() {
        String sql = "CREATE TABLE IF NOT EXISTS pokemon (" +
                "id INT PRIMARY KEY, " +
                "tipo VARCHAR(255), " +
                "especie VARCHAR(255), " +
                "energia FLOAT, " +
                "poder FLOAT, " +
                "entrenador_id INT, " +
                "vida FLOAT, " +
                "FOREIGN KEY (entrenador_id) REFERENCES entrenador(id))";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Tabla 'pokemon' creada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean agregarPokemon(Pokemon pokemon) {
        String sql = "INSERT INTO pokemon (id, tipo, especie, energia, poder, entrenador_id, vida) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pokemon.getId());
            stmt.setString(2, pokemon.getTipo());
            stmt.setString(3, pokemon.getEspecie());
            stmt.setFloat(4, pokemon.getEnergia());
            stmt.setFloat(5, pokemon.getPoder());

            if (pokemon.getEntrenadorId() == 0) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, pokemon.getEntrenadorId());
            }

            stmt.setFloat(7, pokemon.getVida());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Pokemon obtenerPokemon(int id) {
        String sql = "SELECT * FROM pokemon WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pokemon(
                        rs.getInt("id"),
                        Pokemon.pokemonTipo(rs.getString("tipo")),
                        rs.getString("especie"),
                        rs.getFloat("energia"),
                        rs.getFloat("poder"),
                        rs.getInt("entrenador_id"),
                        rs.getFloat("vida")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pokemon> obtenerTodosPokemons() {
        String sql = "SELECT * FROM pokemon";
        List<Pokemon> pokemons = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pokemons.add(new Pokemon(
                    rs.getInt("id"),
                    Pokemon.pokemonTipo(rs.getString("tipo")),
                    rs.getString("especie"),
                    rs.getFloat("energia"),
                    rs.getFloat("poder"),
                    rs.getInt("entrenador_id"),
                    rs.getFloat("vida")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    public boolean actualizarPokemon(Pokemon pokemon) {
        String sql = "UPDATE pokemon SET tipo = ?, especie = ?, energia = ?, poder = ?, entrenador_id = ?, vida = ? WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pokemon.getTipo());
            stmt.setString(2, pokemon.getEspecie());
            stmt.setFloat(3, pokemon.getEnergia());
            stmt.setFloat(4, pokemon.getPoder());

            if (pokemon.getEntrenadorId() == 0) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, pokemon.getEntrenadorId());
            }

            stmt.setFloat(6, pokemon.getVida());
            stmt.setInt(7, pokemon.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPokemon(int id) {
        String sql = "DELETE FROM pokemon WHERE id = ?";

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
    
    public static void curarTodos() {
        String sql = "UPDATE pokemon SET vida = ?, energia = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, 100); // Establecer la vida a 100
            stmt.setFloat(2, 100); // Establecer la energía a 100

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Todos los Pokémon han sido curados y su energía restaurada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void guardar() {
        List<Pokemon> pokemons = new ArrayList<>(Pokemon.getTodosPokemones());
        PokemonDAO pokemonDAO = new PokemonDAO();

        for (Pokemon pokemon : pokemons) {
            if (pokemonDAO.obtenerPokemon(pokemon.getId()) != null) {
            	System.out.println();
                pokemonDAO.actualizarPokemon(pokemon);
            } else {
                pokemonDAO.agregarPokemon(pokemon);
            }
        }
    }
}
