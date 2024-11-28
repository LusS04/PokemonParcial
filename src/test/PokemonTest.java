package test;

import Model.tipos.Fuego;
import Model.tipos.Planta;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import Model.*;

class PokemonTest {

    private Pokemon charmander;
    private Pokemon bulbasaur;
    private Entrenador entrenador;

    @BeforeEach
    void setUp() {
        // Crear el entrenador con el constructor actualizado
        entrenador = new Entrenador(1, "Ash", "1990-01-01", "Kanto", 'M', 30, 3);
        
        // Crear los Pokémon
        charmander = new Pokemon(1, new Fuego(), "Charmander", 50, 20, 100);
        bulbasaur = new Pokemon(2, new Planta(), "Bulbasaur", 60, 15, 120);
    }

    @Test
    void testSerCapturado() {
        assertTrue(charmander.serCapturado(entrenador));
        assertEquals(entrenador.getId(), charmander.getEntrenadorId());

        // Intentar capturarlo de nuevo
        assertFalse(charmander.serCapturado(entrenador));
    }

    @Test
    void testAtacar() {
        charmander.atacar(bulbasaur);

        // Verificar que la vida de Bulbasaur se reduce
        assertTrue(bulbasaur.getVida() < 120);

        // Verificar que la energía de Charmander se reduce
        assertTrue(charmander.getEnergia() < 50);
    }

    @Test
    void testRestarVida() {
        bulbasaur.restarVida(30);
        assertEquals(90, bulbasaur.getVida());

        bulbasaur.restarVida(100); // Daño mayor a la vida restante
        assertEquals(0, bulbasaur.getVida());
    }

    @Test
    void testAumentarVida() {
        bulbasaur.restarVida(50);
        bulbasaur.aumentarVida(30);
        assertEquals(100, bulbasaur.getVida());

        // No debería superar el límite de 100
        bulbasaur.aumentarVida(50);
        assertEquals(100, bulbasaur.getVida());
    }

    @Test
    void testRecibirDanioDeFuego() {
        double danio = bulbasaur.recibirDanioDeFuego(charmander);
        assertEquals(charmander.getPoder()*1.2, danio);
    }

    @Test
    void testRecibirDanioDePlanta() {
        double danio = charmander.recibirDanioDePlanta(bulbasaur);
        assertEquals(bulbasaur.getPoder(), danio);
    }

    @Test
    void testPokemonTipo() {
        Tipo tipoFuego = Pokemon.pokemonTipo("fuego");
        assertEquals("fuego", tipoFuego.getTipo().toLowerCase());

        Tipo tipoAgua = Pokemon.pokemonTipo("agua");
        assertEquals("agua", tipoAgua.getTipo().toLowerCase());

    }
}
