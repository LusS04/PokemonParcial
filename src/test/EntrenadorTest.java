package test;

import Model.*;
import Model.tipos.Fuego;
import Model.tipos.Planta;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class EntrenadorTest {

    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Pokemon charmander;
    private Pokemon bulbasaur;

    @BeforeEach
    void setUp() {
        charmander = new Pokemon(1, new Fuego(), "Charmander", 50, 20, 100);
        bulbasaur = new Pokemon(2, new Planta(), "Bulbasaur", 60, 15, 120);
        entrenador1 = new Entrenador(1, "Ash", "1990-01-01", "Kanto", 'M', 30, 1);
        entrenador2 = new Entrenador(2, "Misty", "1995-01-01", "Kanto", 'F', 25, 2);
    }

    @Test
    void testCapturaPokemon() {
        entrenador1.capturarPokemon(charmander);
        assertEquals(1, entrenador1.getEquipo().size());
        assertTrue(entrenador1.getEquipo().contains(charmander));

        for (int i = 0; i < 4; i++) {
            entrenador1.capturarPokemon(new Pokemon(i + 3, new Fuego(), "Pokemon" + (i + 3), 50, 20, 100));
        }

        entrenador1.capturarPokemon(new Pokemon(6, new Planta(), "Bulbasaur", 60, 15, 120));
        assertEquals(5, entrenador1.getEquipo().size());
    }

    @Test
    void testEnfrentamiento() {
        entrenador1.capturarPokemon(charmander);
        entrenador2.capturarPokemon(bulbasaur);

        Entrenador ganador = entrenador1.enfrentarseA(entrenador2);
        assertEquals(entrenador1, ganador);
    }

    @Test
    void testCapturaPokemonYaCapturado() {
        entrenador1.capturarPokemon(charmander);
        entrenador1.capturarPokemon(charmander);
        assertEquals(1, entrenador1.getEquipo().size());
    }
}
