package ui;

import DAO.UsuarioDAO;
import Model.Usuario;
import ui.Emparejamiento;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JTextField nicknameField;

    public Login() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameField = new JTextField();

        panel.add(nicknameLabel);
        panel.add(nicknameField);

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        panel.add(loginButton);

        JButton createUserButton = new JButton("Crear Usuario");
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioRegistro();
            }
        });
        panel.add(createUserButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void iniciarSesion() {
        String nickname = nicknameField.getText();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obtenerUsuarioPorNickname(nickname);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + usuario.getNombreCompleto());
            MenuPrincipal menuPrincipal  = new MenuPrincipal (usuario);
            menuPrincipal .setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado. Verifica tu nickname.");
        }
    }



    private void abrirFormularioRegistro() {
        SwingForm swingForm = new SwingForm();
        swingForm.setVisible(true);
        dispose(); // Cierra la ventana de login
    }

    public static void main(String[] args) {
        Login loginForm = new Login();
        loginForm.setVisible(true);
    }
}
