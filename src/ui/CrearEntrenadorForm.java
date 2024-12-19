package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.*;
import DAO.EntrenadorDAO;

public class CrearEntrenadorForm extends JFrame {
    private JTextField nombreField;
    private JTextField fechaNacimientoField;
    private JTextField nacionalidadField;
    private JTextField generoField;
    private JTextField edadField;

    private Usuario usuarioActual; // Atributo para mantener el usuario asociado

    public CrearEntrenadorForm(Usuario usuario) {
        this.usuarioActual = usuario;

        setTitle("Crear Entrenador");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Inicializar campos como atributos
        nombreField = new JTextField(20);
        fechaNacimientoField = new JTextField(10);
        nacionalidadField = new JTextField(20);
        generoField = new JTextField(1);
        edadField = new JTextField(3);

        // Campos del formulario
        JLabel nombreLabel = new JLabel("Nombre:");
        JLabel fechaNacimientoLabel = new JLabel("Fecha de Nacimiento (YYYY-MM-DD):");
        JLabel nacionalidadLabel = new JLabel("Nacionalidad:");
        JLabel generoLabel = new JLabel("Género (M/F):");
        JLabel edadLabel = new JLabel("Edad:");

        JButton crearButton = new JButton("Crear");
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearEntrenador();
            }
        });

        // Agregar componentes al panel
        panel.add(nombreLabel);
        panel.add(nombreField);
        panel.add(fechaNacimientoLabel);
        panel.add(fechaNacimientoField);
        panel.add(nacionalidadLabel);
        panel.add(nacionalidadField);
        panel.add(generoLabel);
        panel.add(generoField);
        panel.add(edadLabel);
        panel.add(edadField);
        panel.add(crearButton);

        add(panel);
        setLocationRelativeTo(null);
    }

    private void crearEntrenador() {
        try {
            String nombre = nombreField.getText().trim();
            String fechaNacimiento = fechaNacimientoField.getText().trim();
            String nacionalidad = nacionalidadField.getText().trim();
            char genero = generoField.getText().trim().toUpperCase().charAt(0);
            int edad = Integer.parseInt(edadField.getText().trim());

            if (nombre.isEmpty() || fechaNacimiento.isEmpty() || nacionalidad.isEmpty() || (genero != 'M' && genero != 'F') || edad <= 0) {
                throw new IllegalArgumentException("Todos los campos son obligatorios y deben ser válidos.");
            }

            // Crear el entrenador con el ID usuarioActual.getId()
            Entrenador nuevoEntrenador = new Entrenador(0, nombre, fechaNacimiento, nacionalidad, genero, edad, usuarioActual.getId());

            if (EntrenadorDAO.guardarEntrenador(nuevoEntrenador)) {
                JOptionPane.showMessageDialog(this, "Entrenador creado con éxito.");
                dispose(); // Cerrar la ventana al finalizar
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el entrenador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
