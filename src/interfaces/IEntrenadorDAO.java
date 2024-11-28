package interfaces;

import Model.Entrenador;
import Model.Pokemon;
import java.util.List;

public interface IEntrenadorDAO {

    boolean agregarEntrenador(Entrenador entrenador);

    Entrenador obtenerEntrenador(int id);

    List<Entrenador> obtenerTodosEntrenadores();

    boolean actualizarEntrenador(Entrenador entrenador);

    boolean eliminarEntrenador(int id);

    List<Pokemon> obtenerEquipo(int entrenadorId);

}
