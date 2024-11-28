package test;

import Model.*;
import Model.tipos.Fuego;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import java.util.List;

class UsuarioTest {

    private Usuario usuario1;
    private Usuario usuario2;
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Pokemon charmander;

    @BeforeEach
    void setUp() {
        charmander = new Pokemon(1, new Fuego(), "Charmander", 50, 20, 100);
        entrenador1 = new Entrenador(1, "Ash", "1990-01-01", "Kanto", 'M', 30, 1);
        entrenador2 = new Entrenador(2, "Misty", "1995-01-01", "Kanto", 'F', 25, 2);
        usuario1 = new Usuario(1, "ash@example.com", "Ash Ketchum", "ashk", "1234567890");
        usuario2 = new Usuario(2, "misty@example.com", "Misty Waterflower", "mistyw", "0987654321");
    }

    @Test
    void testAgregarEntrenador() {
        boolean resultado = usuario1.agregarEntrenador(entrenador1);
        assertTrue(resultado);
        assertEquals(1, usuario1.getEntrenadores().size());

        resultado = usuario1.agregarEntrenador(entrenador2);
        assertTrue(resultado);
        assertEquals(2, usuario1.getEntrenadores().size());

        Usuario usuarioConMaxEntrenadores = new Usuario(3, "brock@example.com", "Brock", "brock", "111223344");
        Entrenador entrenador3 = new Entrenador(3, "Brock", "1980-05-05", "Kanto", 'M', 40, 3);
        Entrenador entrenador4 = new Entrenador(4, "Blaine", "1985-06-06", "Kanto", 'M', 45, 4);
        usuarioConMaxEntrenadores.agregarEntrenador(entrenador3);
        usuarioConMaxEntrenadores.agregarEntrenador(entrenador4);

        assertEquals(3, usuarioConMaxEntrenadores.getEntrenadores().size());

        boolean resultadoExceso = usuarioConMaxEntrenadores.agregarEntrenador(new Entrenador(5, "Giovanni", "1970-07-07", "Kanto", 'M', 50, 5));
        assertFalse(resultadoExceso);
    }

    @Test
    void testListarUsuarios() {
        new Usuario(3, "brock@example.com", "Brock", "brock", "111223344");

        List<Usuario> usuarios = Usuario.getTodosUsuarios();
        assertEquals(3, usuarios.size());
        assertTrue(usuarios.contains(usuario1));
        assertTrue(usuarios.contains(usuario2));
    }

    @Test
    void testToString() {
        usuario1.agregarEntrenador(entrenador1);
        usuario1.agregarEntrenador(entrenador2);

        String expected = "Usuario{id=1, email='ash@example.com', nombreCompleto='Ash Ketchum', nickname='ashk', numeroCelular='1234567890'\n" +
                "entrenadores= \n[Entrenador{id=1, nombre='Ash', fechaNacimiento='1990-01-01', nacionalidad='Kanto', genero=M, edad=30, usuarioId=1}"+
                "Entrenador{id=2, nombre='Misty', fechaNacimiento='1995-01-01', nacionalidad='Kanto', genero=F, edad=25, usuarioId=1}]";
        assertEquals(expected, usuario1.toString());
    }
}
