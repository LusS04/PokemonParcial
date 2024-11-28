package interfaces;

import Model.Pokemon;
import java.util.List;

public interface IPokemonDAO {

    boolean agregarPokemon(Pokemon pokemon);

    Pokemon obtenerPokemon(int id);

    List<Pokemon> obtenerTodosPokemons();

    boolean actualizarPokemon(Pokemon pokemon);

    boolean eliminarPokemon(int id);

}
