package Model.tipos;

import Model.Pokemon;
import Model.Tipo;

public class Planta extends Tipo{
	public Planta () {
		super ("planta");
	}

    @Override
    public double calcularDanio(Pokemon atacante, Pokemon enemigo) {
        return enemigo.recibirDanioDePlanta(atacante);
    }
    @Override
    public double recibirDanioDeFuego(Pokemon atacante) {
    	System.out.println("Los tipos Fuego le hacen mas danio a los tipo Planta");
        return atacante.getPoder()*1.2; 
    }
}
