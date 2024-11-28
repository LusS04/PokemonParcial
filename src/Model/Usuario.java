package Model;

import java.util.ArrayList;
import interfaces.IUsuario;
import java.util.List;

public class Usuario implements IUsuario{
    private int id; // Añadido el campo id
    private String email;
    private String nombreCompleto;
    private String nickname;
    private String numeroCelular;

    // Lista para almacenar los entrenadores creados por el usuario
    private List<Entrenador> entrenadores;

    // Lista estática para almacenar todos los usuarios
    private static List<Usuario> todosUsuarios = new ArrayList<>();

    public Usuario(int id, String email, String nombreCompleto, String nickname, String numeroCelular) {
        this.id = id;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.nickname = nickname;
        this.numeroCelular = numeroCelular;
        this.entrenadores = new ArrayList<>(); // Inicializado vacío

        // Comprobar si el usuario ya está en la lista
        boolean existe = false;
        for (Usuario usuario : todosUsuarios) {
            if (usuario.getNickname().equals(this.nickname)) {
                existe = true;
                break;
            }
        }

        // Si no existe, agregar el usuario a la lista
        if (!existe) {
            todosUsuarios.add(this);
        }
    }


    // Getter para la lista de todos los usuarios
    public static List<Usuario> getTodosUsuarios() {
        return new ArrayList<>(todosUsuarios); // Retorna una copia para evitar modificaciones externas
    }

	// Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public List<Entrenador> getEntrenadores() {
        return new ArrayList<>(entrenadores);
    }
    
    public void setEntrenadores(List<Entrenador> entrenadores) {
		this.entrenadores.addAll(entrenadores);
	}

	// Método para agregar un entrenador
    public boolean agregarEntrenador(Entrenador entrenador) {
        try {
            if (entrenadores.size() >= 3) {
                throw new Exception("No se pueden agregar más de 3 entrenadores.");
            }
            entrenadores.add(entrenador);
            System.out.println("Entrenador " + entrenador.getNombre() + " agregado exitosamente.");
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id + // Agregado el id al toString
                ", email='" + email + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", nickname='" + nickname + '\'' +
                ", numeroCelular='" + numeroCelular + '\'' +
                "\nentrenadores= \n" + entrenadores +
                '}';
    }
}
