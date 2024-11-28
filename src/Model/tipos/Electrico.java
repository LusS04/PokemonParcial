package Model.tipos;

import Model.Pokemon;
import Model.Tipo;

public class Electrico extends Tipo {
    public Electrico() {
        super("electrico");
    }

    @Override
    public double calcularDanio(Pokemon atacante, Pokemon enemigo) {
        return enemigo.recibirDanioDeElectrico(atacante);
    }
}
