package ui;

import Model.Entrenador;
import Model.Usuario;
import DAO.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Enfrentamiento extends JFrame {
    private JComboBox<Entrenador> entrenador1ComboBox;
    private JComboBox<Entrenador> entrenador2ComboBox;
    private Usuario usuarioActual;
    private Usuario usuarioOponente;

    public Enfrentamiento(Usuario usuarioActual, Usuario usuarioOponente) {
        this.usuarioActual = usuarioActual;
        this.usuarioOponente = usuarioOponente;

        setTitle("Seleccionar Entrenadores para el Enfrentamiento");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        List<Entrenador> entrenadoresUsuario = usuarioActual.getEntrenadores();
        List<Entrenador> entrenadoresOponente = usuarioOponente.getEntrenadores();

        if (entrenadoresUsuario == null || entrenadoresUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes entrenadores disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        if (entrenadoresOponente == null || entrenadoresOponente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El usuario oponente no tiene entrenadores disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel entrenador1Label = new JLabel("Seleccionar tu Entrenador:");
        entrenador1ComboBox = new JComboBox<>(entrenadoresUsuario.toArray(new Entrenador[0]));

        JLabel entrenador2Label = new JLabel("Seleccionar Entrenador del Oponente:");
        entrenador2ComboBox = new JComboBox<>(entrenadoresOponente.toArray(new Entrenador[0]));

        JButton battleButton = new JButton("Iniciar Enfrentamiento");
        battleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarEnfrentamiento();
            }
        });

        panel.add(entrenador1Label);
        panel.add(entrenador1ComboBox);
        panel.add(entrenador2Label);
        panel.add(entrenador2ComboBox);
        panel.add(battleButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void iniciarEnfrentamiento() {
        Entrenador entrenador1 = (Entrenador) entrenador1ComboBox.getSelectedItem();
        Entrenador entrenador2 = (Entrenador) entrenador2ComboBox.getSelectedItem();

        if (entrenador1 == null || entrenador2 == null) {
            JOptionPane.showMessageDialog(this, "Selecciona entrenadores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lógica del enfrentamiento
        Entrenador ganador = entrenador1.enfrentarseA(entrenador2);
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        JLabel resultLabel = new JLabel("El ganador es: " + ganador.getNombre());
        resultPanel.add(resultLabel);

        JButton guardarButton = new JButton("Guardar");
        JButton cancelarButton = new JButton("Cancelar");

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamamos al método guardar() del UsuarioDAO para guardar el usuario
                UsuarioDAO.guardar();
                dispose(); // Cerrar la ventana
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Solo cerramos la ventana si se cancela
            }
        });

        resultPanel.add(guardarButton);
        resultPanel.add(cancelarButton);

        // Mostrar el panel sin el botón de aceptar
        JDialog dialog = new JDialog(this, "Resultado del Enfrentamiento", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setContentPane(resultPanel);
        dialog.setVisible(true);
    }
}

