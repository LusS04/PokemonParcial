package interfaces;

import java.util.List;
import Model.Entrenador;

public interface IUsuario {

    // Métodos básicos
    int getId();
    void setId(int id);

    String getEmail();
    void setEmail(String email);

    String getNombreCompleto();
    void setNombreCompleto(String nombreCompleto);

    String getNickname();
    void setNickname(String nickname);

    String getNumeroCelular();
    void setNumeroCelular(String numeroCelular);

    // Métodos relacionados con los entrenadores
    List<Entrenador> getEntrenadores();
    void setEntrenadores(List<Entrenador> entrenadores);
    boolean agregarEntrenador(Entrenador entrenador);


}
