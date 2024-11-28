package Model.tipos;

import Model.Pokemon;
import Model.Tipo;

public class Fuego extends Tipo {
    public Fuego() {
        super("fuego");
    }

    @Override
    public double calcularDanio(Pokemon atacante, Pokemon enemigo) {
        return enemigo.recibirDanioDeFuego(atacante);
    }
    
    @Override
    public double recibirDanioDeAgua(Pokemon atacante) {
    	System.out.println("Los tipos agua le hacen mas danio a los tipo fuego");
        return atacante.getPoder()*1.25; 
    }
}
