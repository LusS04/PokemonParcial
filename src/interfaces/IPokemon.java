package interfaces;

import Model.tipos.*;
import Model.*;
import java.util.List;

public interface IPokemon {

    // Métodos getters y setters
    int getId();
    void setId(int id);

    String getEspecie();
    void setEspecie(String especie);

    String getTipo();
    void setTipo(Tipo tipo);

    float getEnergia();
    void setEnergia(float energia);

    float getPoder();
    void setPoder(float poder);

    float getVida();
    void setVida(float vida);

    int getEntrenadorId();
    void setEntrenadorId(int entrenadorId);

    // Métodos para el manejo de los Pokémon
    boolean serCapturado(Entrenador entrenador);

    // Métodos para atacar y recibir daño
    void atacar(Pokemon enemigo);
    double recibirDanioDePlanta(Pokemon atacante);
    double recibirDanioDeFuego(Pokemon atacante);
    double recibirDanioDeAgua(Pokemon atacante);
    double recibirDanioDeElectrico(Pokemon atacante);
    double recibirDanioDePiedra(Pokemon atacante);

    // Métodos para la vida del Pokémon
    void restarVida(float dano);
    void aumentarVida(float cantidad);
}
