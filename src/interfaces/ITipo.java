package interfaces;
import Model.Pokemon;

public interface ITipo {

    // Métodos básicos
    String getTipo();
    void setTipo(String tipo);

    // Método principal para calcular el daño de un ataque
    double atacar(Pokemon atacante, Pokemon enemigo);

    // Métodos específicos para calcular el daño según el tipo
    double recibirDanioDePlanta(Pokemon atacante);
    double recibirDanioDeFuego(Pokemon atacante);
    double recibirDanioDeAgua(Pokemon atacante);
    double recibirDanioDeElectrico(Pokemon atacante);
    double recibirDanioDePiedra(Pokemon atacante);
}

