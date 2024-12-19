package ui;

import Model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
    private Usuario usuarioActual;

    public MenuPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;

        setTitle("Menú Principal");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton crearEntrenadorButton = new JButton("Crear Entrenador");
        JButton combatirButton = new JButton("Combatir");

        crearEntrenadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearEntrenadorForm crearEntrenadorForm = new CrearEntrenadorForm(usuarioActual);
                crearEntrenadorForm.setVisible(true);
                dispose();
            }
        });

        combatirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Emparejamiento emparejamiento = new Emparejamiento(usuarioActual);
                emparejamiento.setVisible(true);
                dispose(); 
            }
        });

        panel.add(crearEntrenadorButton);
        panel.add(combatirButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Ejemplo de un usuario ficticio
        Usuario usuario = new Usuario(1, "email@dominio.com", "Nombre Completo", "nickname", "123456789");
        MenuPrincipal menuPrincipal = new MenuPrincipal(usuario);
        menuPrincipal.setVisible(true);
    }
}
