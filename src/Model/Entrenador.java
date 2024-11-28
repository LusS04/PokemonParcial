package Model;

import DAO.EntrenadorDAO;
import Model.Pokemon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import interfaces.IEntrenador;

public class Entrenador implements IEntrenador{

    private int id;
    private String nombre;
    private String fechaNacimiento;
    private String nacionalidad;
    private char genero;
    private int edad;
    private int usuarioId; // Nuevo atributo
    private List<Pokemon> equipo = new ArrayList<>();

    // Lista estática para almacenar todos los entrenadores generados
    private static List<Entrenador> todosEntrenadores = new ArrayList<>();

    public Entrenador(int id, String nombre, String fechaNacimiento, String nacionalidad, char genero, int edad, int usuarioId) {
        EntrenadorDAO entrenadorDAO = new EntrenadorDAO();
        if (entrenadorDAO.obtenerEntrenador(id) != null) {
            System.out.println("Ya existe un entrenador con esta id. El programa se detendrá.");
            System.exit(1); 
        }

        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.genero = genero;
        this.edad = edad;
        this.usuarioId = usuarioId;

        boolean existe = false;
        for (Entrenador entrenador : todosEntrenadores) {
            if (entrenador.getId() == id) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            todosEntrenadores.add(this);
        } 
    }

    // Constructor auxiliar
    public Entrenador(int id, String nombre, String fechaNacimiento, String nacionalidad, char genero, int edad, int usuarioId, int auxiliar) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.genero = genero;
        this.edad = edad;
        this.usuarioId = usuarioId;
        boolean existe = false;
        for (Entrenador entrenador : todosEntrenadores) {
            if (entrenador.getId() == id) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            todosEntrenadores.add(this);
        } 
    }

    // Getters y setters
    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }




	// Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Pokemon> getEquipo() {
        return equipo;
    }

    public void setEquipo(List<Pokemon> equipo) {
        this.equipo = equipo;
    }

    // Método para capturar un Pokémon y agregarlo al equipo
    public void capturarPokemon(Pokemon pk) {
        try {
            if (equipo.size() < 5) {
                if (pk.serCapturado(this)) {
                    equipo.add(pk);
                } else {
                    System.out.println("El Pokémon ya tiene dueño");
                }
            } else {
                throw new Exception("Error: tu equipo ya está lleno");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Método estático para obtener todos los entrenadores generados
    public static List<Entrenador> getTodosEntrenadores() {
        return todosEntrenadores;
    }
    
    public Entrenador enfrentarseA(Entrenador enemigo) {
        int i = 0;
        int j = 0;
        Random random = new Random(); // Generador de números aleatorios

        while (i < this.equipo.size() && j < enemigo.getEquipo().size()) {
            Pokemon miPokemon = this.equipo.get(i);
            Pokemon pokemonEnemigo = enemigo.getEquipo().get(j);

            // Mientras ambos Pokémon tengan vida
            while (miPokemon.getVida() > 0 && pokemonEnemigo.getVida() > 0) {
                // Determinar aleatoriamente quién ataca primero
                if (random.nextBoolean()) {
                    // Si es verdadero, mi Pokémon ataca primero
                    miPokemon.atacar(pokemonEnemigo);
                    if (pokemonEnemigo.getVida() > 0) {
                        pokemonEnemigo.atacar(miPokemon);
                    }
                } else {
                    // Si es falso, el Pokémon enemigo ataca primero
                    pokemonEnemigo.atacar(miPokemon);
                    if (miPokemon.getVida() > 0) {
                        miPokemon.atacar(pokemonEnemigo);
                    }
                }
            }

            if (pokemonEnemigo.getVida() <= 0) {
                System.out.println("El Pokémon enemigo " + pokemonEnemigo.getEspecie() + " ha sido derrotado.");
                j++;
            }

            if (miPokemon.getVida() <= 0) {
                System.out.println("Tu Pokémon " + miPokemon.getEspecie() + " ha sido derrotado.");
                i++;
            }
        }

        if (i < this.equipo.size()) {
            return this; // El jugador actual gana
        } else {
            return enemigo; // El enemigo gana
        }
    }

    
    @Override
    public String toString() {
        // Crear una cadena base con la información del entrenador
        String result = "	Entrenador{id=" + id + 
                        ", nombre='" + nombre + '\'' + 
                        ", fechaNacimiento='" + fechaNacimiento + '\'' + 
                        ", nacionalidad='" + nacionalidad + '\'' + 
                        ", genero=" + genero + 
                        ", edad=" + edad + 
                        ", usuarioId-"+ usuarioId+
                        "\n	equipo:\n";

        // Usamos un for para recorrer el equipo de Pokémon y llamar a toString() para cada uno
        for (int i = 0; i < equipo.size(); i++) {
            result += equipo.get(i).toString();
        }


        return result;
    }



}
