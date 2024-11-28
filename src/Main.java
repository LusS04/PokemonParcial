import DAO.UsuarioDAO;
import DAO.EntrenadorDAO;
import DAO.PokemonDAO;
import Model.Usuario;
import Model.Entrenador;
import Model.Pokemon;
import Model.Tipo;
import Model.tipos.*;
import ui.*;

public class Main {
    public static void main(String[] args) {
        // Inicia mostrando la ventana de Login
    	PokemonDAO.curarTodos();
        Login loginForm = new Login();
        loginForm.setVisible(true);
    }
}
