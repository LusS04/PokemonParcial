package interfaces;


import Model.Usuario;
import Model.Entrenador;
import java.util.List;

public interface IUsuarioDAO {

    boolean agregarUsuario(Usuario usuario);

    Usuario obtenerUsuarioPorNickname(String nickname);

    List<Usuario> obtenerTodosUsuarios();

    boolean actualizarUsuario(Usuario usuario);

    boolean eliminarUsuario(int id);

    List<Entrenador> obtenerEntrenadoresPorUsuario(int usuarioId);

}

