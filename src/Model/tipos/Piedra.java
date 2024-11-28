package Model.tipos;

import Model.Pokemon;
import Model.Tipo;

public class Piedra extends Tipo {
    public Piedra() {
        super("piedra");
    }

    @Override
    public double calcularDanio(Pokemon atacante, Pokemon enemigo) {
        return atacante.getPoder(); // Sin interacciones especiales
    }

    @Override
    public double recibirDanioDePlanta(Pokemon atacante) {
    	System.out.println("Los tipos planta no le hacen danio a los tipo piedra");
        return 0; // Daño nulo contra ataques tipo Planta
    }
}
