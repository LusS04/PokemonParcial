package Model;
import interfaces.ITipo;

public class Tipo implements ITipo{
    private String tipo;

    public Tipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double atacar(Pokemon atacante, Pokemon enemigo) {
        return calcularDanio(atacante, enemigo);
    }

    public double calcularDanio(Pokemon atacante, Pokemon enemigo) {
        return atacante.getPoder(); 
    }
    
    public double recibirDanioDePlanta(Pokemon atacante) {
        return atacante.getPoder(); 
    }

    public double recibirDanioDeFuego(Pokemon atacante) {
        return atacante.getPoder();
    }

    public double recibirDanioDeAgua(Pokemon atacante) {
        return atacante.getPoder();
    }

    public double recibirDanioDeElectrico(Pokemon atacante) {
       
        return atacante.getPoder();
    }

    public double recibirDanioDePiedra(Pokemon atacante) {
        return atacante.getPoder();
    }
}
