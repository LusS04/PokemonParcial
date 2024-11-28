package ui;

import Model.Usuario;
import DAO.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Emparejamiento extends JFrame {
    private JComboBox<String> usuariosComboBox;
    private Usuario usuarioActual;

    public Emparejamiento(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;

        setTitle("Seleccionar Usuario para Emparejamiento");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<String> nicknames = usuarioDAO.obtenerTodosNicknames();
        nicknames.remove(usuarioActual.getNickname()); // Excluir el nickname del usuario actual

        if (nicknames.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay otros usuarios disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel seleccionLabel = new JLabel("Selecciona el usuario contra el que pelearás:");
        usuariosComboBox = new JComboBox<>(nicknames.toArray(new String[0]));

        JButton continuarButton = new JButton("Continuar");
        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nicknameSeleccionado = (String) usuariosComboBox.getSelectedItem();
                if (nicknameSeleccionado != null) {
                    abrirEnfrentamiento(nicknameSeleccionado);
                } else {
                    JOptionPane.showMessageDialog(Emparejamiento.this, "Selecciona un usuario válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(seleccionLabel);
        panel.add(usuariosComboBox);
        panel.add(continuarButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void abrirEnfrentamiento(String nicknameSeleccionado) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioSeleccionado = usuarioDAO.obtenerUsuarioPorNickname(nicknameSeleccionado);

        if (usuarioSeleccionado != null) {
            Enfrentamiento enfrentamiento = new Enfrentamiento(usuarioActual, usuarioSeleccionado);
            enfrentamiento.setVisible(true);
            this.dispose(); // Cerramos esta ventana
        } else {
            JOptionPane.showMessageDialog(this, "Error al cargar el usuario seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Prueba con un usuario ficticio
        Usuario usuarioActual = new Usuario(1, "miemail@dominio.com", "Mi Nombre", "miNickname", "123456789");
        Emparejamiento emparejamiento = new Emparejamiento(usuarioActual);
        emparejamiento.setVisible(true);
    }
}
