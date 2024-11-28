package interfaces;

import java.util.List;

import Model.Entrenador;
import Model.Pokemon;

public interface IEntrenador {

    // Métodos básicos del entrenador
    int getId();
    void setId(int id);

    String getNombre();
    void setNombre(String nombre);

    String getFechaNacimiento();
    void setFechaNacimiento(String fechaNacimiento);

    String getNacionalidad();
    void setNacionalidad(String nacionalidad);

    char getGenero();
    void setGenero(char genero);

    int getEdad();
    void setEdad(int edad);

    int getUsuarioId();
    void setUsuarioId(int usuarioId);

    // Métodos relacionados con el equipo Pokémon
    List<Pokemon> getEquipo();
    void setEquipo(List<Pokemon> equipo);

    void capturarPokemon(Pokemon pk);

    // Métodos para enfrentamientos
    Entrenador enfrentarseA(Entrenador enemigo);
    


}
