package Model;
import interfaces.IPokemon;
import DAO.PokemonDAO;
import Model.tipos.Agua;
import Model.tipos.Electrico;
import Model.tipos.Fuego;
import Model.tipos.Piedra;
import Model.tipos.Planta;

import java.util.ArrayList;
import java.util.List;

public class Pokemon implements IPokemon{

    private int id;
    private Tipo tipo;
    private String especie;
    private float energia;
    private float poder;
    private int entrenadorId;
    private float vida;

    // Lista estática para almacenar todos los Pokémon generados
    private static List<Pokemon> todosPokemones = new ArrayList<>();

    public Pokemon(int id, Tipo tipo, String especie, float energia, float poder, int entrenadorId, float vida) {
        this.id = id;
        this.tipo = tipo;
        this.especie = especie;
        this.energia = energia;
        this.poder = poder;
        this.entrenadorId = entrenadorId;
        this.vida = vida;

        boolean existe = false;
        for (Pokemon pokemon : todosPokemones) {
            if (pokemon.getId() == this.id) {
                existe = true;
                break;
            }
        }

        // Si no existe, agregar el Pokemon a la lista
        if (!existe) {
            todosPokemones.add(this);
        }
    }
    
    public Pokemon(int id, Tipo tipo, String especie, float energia, float poder, float vida) {
        this.id = id;
        this.tipo = tipo;
        this.especie = especie;
        this.energia = energia;
        this.poder = poder;
        this.entrenadorId = 0;
        this.vida = vida;

        boolean existe = false;
        for (Pokemon pokemon : todosPokemones) {
            if (pokemon.getId() == this.id) {
                existe = true;
                break;
            }
        }

        // Si no existe, agregar el Pokemon a la lista
        if (!existe) {
            todosPokemones.add(this);
        }
    }


    // Getters y setters
    public static List<Pokemon> getTodosPokemones() {
        return todosPokemones;
    }

    public static void setTodosPokemones(List<Pokemon> todosPokemones) {
        Pokemon.todosPokemones = todosPokemones;
    }

    public int getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(int entrenadorId) {
        this.entrenadorId = entrenadorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo.getTipo();
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public float getEnergia() {
        return energia;
    }

    public void setEnergia(float energia) {
        this.energia = energia;
    }

    public float getPoder() {
        return poder;
    }

    public void setPoder(float poder) {
        this.poder = poder;
    }

    public float getVida() {
        return vida;
    }

    public void setVida(float vida) {
        this.vida = vida;
    }

    // Método para ser capturado por un entrenador
    public boolean serCapturado(Entrenador entrenador) {
        try {
            if (this.entrenadorId != 0) {
                throw new Exception("La captura ha fallado. El Pokémon ya tiene un entrenador.");
            }

            this.entrenadorId = entrenador.getId();

            for (int i = 0; i < entrenador.getEquipo().size(); i++) {
                Pokemon pk = entrenador.getEquipo().get(i);
                if (pk.getId() == this.getId()) {
                    entrenador.getEquipo().remove(i); // Reemplazar el Pokémon en el equipo
                    break;
                }
            }

            todosPokemones.add(this);
            System.out.println("El Pokémon ha sido capturado y añadido al equipo del entrenador " + entrenador.getNombre());
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Método para restar vida al Pokémon
    public void restarVida(float dano) {
        if (dano >= vida) {
            vida = 0;
        } else {
            vida -= dano;
        }
    }

    // Método para aumentar vida al Pokémon
    public void aumentarVida(float cantidad) {
        vida += cantidad;
        if (vida > 100) {
            vida = 100;
        }
    }


    public void atacar(Pokemon enemigo) {
        float danio = (float) tipo.atacar(this, enemigo); 
        this.setEnergia(danio/2);
        enemigo.restarVida(danio);
    }

    // Métodos para recibir daño según el tipo del atacante
    public double recibirDanioDePlanta(Pokemon atacante) {
        return this.tipo.recibirDanioDePlanta(atacante); 
    }

    public double recibirDanioDeFuego(Pokemon atacante) {
        return this.tipo.recibirDanioDeFuego(atacante);
    }

    public double recibirDanioDeAgua(Pokemon atacante) {
        return this.tipo.recibirDanioDeAgua(atacante);
    }

    public double recibirDanioDeElectrico(Pokemon atacante) {
       
        return this.tipo.recibirDanioDeElectrico(atacante);
    }

    public double recibirDanioDePiedra(Pokemon atacante) {
        return this.tipo.recibirDanioDePiedra(atacante);
    }

    public static Tipo pokemonTipo(String tipoStr) {
        switch (tipoStr.toLowerCase()) {
            case "electrico":
                return new Electrico();
            case "agua":
                return new Agua();
            case "fuego":
                return new Fuego();
            case "planta":
                return new Planta();
            case "piedra":
                return new Piedra();
            default:
                throw new IllegalArgumentException("Tipo desconocido: " + tipoStr);
        }
    }

    @Override
    public String toString() {
        return "		Pokemon{id=" + id +
                ", tipo=" + tipo.getTipo() +
                ", especie='" + especie + '\'' +
                ", energia=" + energia +
                ", poder=" + poder +
                ", entrenadorId=" + entrenadorId +
                ", vida=" + vida + "} \n";
    }
}
