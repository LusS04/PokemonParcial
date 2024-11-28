package ui;

import DAO.UsuarioDAO;
import Model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingForm extends JFrame {

    private JTextField emailField;
    private JTextField nombreCompletoField;
    private JTextField nicknameField;
    private JTextField numeroCelularField;

    public SwingForm() {
        setTitle("Formulario de Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel nombreCompletoLabel = new JLabel("Nombre Completo:");
        nombreCompletoField = new JTextField();
        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameField = new JTextField();
        JLabel numeroCelularLabel = new JLabel("Número Celular:");
        numeroCelularField = new JTextField();

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(nombreCompletoLabel);
        panel.add(nombreCompletoField);
        panel.add(nicknameLabel);
        panel.add(nicknameField);
        panel.add(numeroCelularLabel);
        panel.add(numeroCelularField);

        JButton sendButton = new JButton("Guardar Usuario");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarUsuario();
            }
        });
        panel.add(sendButton);

        // Botón que cambiará a "Volver"
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAlLogin();
            }
        });
        panel.add(volverButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void guardarUsuario() {
        try {
            String email = emailField.getText();
            String nombreCompleto = nombreCompletoField.getText();
            String nickname = nicknameField.getText();
            String numeroCelular = numeroCelularField.getText();

            Usuario usuario = new Usuario(0, email, nombreCompleto, nickname, numeroCelular);

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            if (usuarioDAO.obtenerUsuarioPorNickname(nickname) != null) {
                usuarioDAO.actualizarUsuario(usuario);
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
            } else {
                usuarioDAO.agregarUsuario(usuario);
                JOptionPane.showMessageDialog(this, "Usuario agregado correctamente.");
            }

            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void limpiarCampos() {
        emailField.setText("");
        nombreCompletoField.setText("");
        nicknameField.setText("");
        numeroCelularField.setText("");
    }

    private void volverAlLogin() {
        // Cerrar la ventana actual
        this.setVisible(false);

        // Crear y mostrar el LoginForm
        Login loginForm = new Login();
        loginForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingForm form = new SwingForm();
        form.setVisible(true);
    }
}
