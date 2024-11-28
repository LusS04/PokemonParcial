package Model.tipos;

import Model.Pokemon;
import Model.Tipo;

public class Agua extends Tipo {
    public Agua() {
        super("agua");
    }

    @Override
    public double calcularDanio(Pokemon atacante, Pokemon enemigo) {
        return enemigo.recibirDanioDeAgua(atacante);
    }
    
    @Override
    public double recibirDanioDeElectrico(Pokemon atacante) {
    	System.out.println("Los tipos Electrico le hacen mas danio a los tipo Agua, pero se danian en el proceso");
    	atacante.restarVida(atacante.getPoder()*0.05f);
        return atacante.getPoder()*1.5; 
    }
}
