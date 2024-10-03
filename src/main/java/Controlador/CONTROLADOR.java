/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
/**
 *
 * @author User17
 */
import Vista.JFRAMEREGISTRO;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Modelo.ALUMNO; 
import java.util.Calendar;
import java.util.List;

public class CONTROLADOR {
    private JFRAMEREGISTRO vista;
    private List<ALUMNO> listaAlumnos;

    public CONTROLADOR(JFRAMEREGISTRO vista) {
        this.vista = vista;
        this.listaAlumnos = new ArrayList<>();
        
        // Agregar listener para el botón de agregar
        this.vista.getBtnAgregar().addActionListener(e -> agregarAlumno());
        
        // Agregar listener para el botón de borrar
        this.vista.getBtnBorrar().addActionListener(e -> {
            int selectedRow = vista.getjTabla().getSelectedRow();
            borrarAlumno(selectedRow);
        });
        
        // Agregar listener para el botón de actualizar
        this.vista.getBtnActualizar().addActionListener(e -> {
            int selectedRow = vista.getjTabla().getSelectedRow();
            actualizarAlumno(selectedRow);
        });
        
        // Agregar listener para el botón de buscar
        this.vista.getBtnBuscar().addActionListener(e -> buscarAlumno(vista.getTxtBuscar().getText()));
    }

    public void agregarAlumno() {
        // Obtener los valores de los campos de texto y combo box
        String nombre = vista.getTxtNombre().getText();
        String codigoEstudiante = vista.getTxtCodigo().getText();
        String telefono = vista.getTxtTelefono().getText();
        Date fechaSeleccionada = vista.getjEdad().getDate();
        String carrera = (String) vista.getjComboBoxCarrera().getSelectedItem();
        String sede = (String) vista.getjComboBoxSede().getSelectedItem(); // Cambiado a sede

        boolean hayError = false;

        // Verificar si hay campos vacíos
        if (nombre.isEmpty() || codigoEstudiante.isEmpty() || telefono.isEmpty() || fechaSeleccionada == null || carrera == null || sede == null) {
            if (!hayError) {
                System.out.println("Por favor, completa todos los campos obligatorios.");
                hayError = true;
            }
        } else {
            // Crear nuevo ALUMNO
            ALUMNO nuevoRegistro = new ALUMNO(nombre, fechaSeleccionada, codigoEstudiante, telefono, carrera, sede);
            listaAlumnos.add(nuevoRegistro);
            
            // Agregar a la tabla
            DefaultTableModel model = (DefaultTableModel) vista.getjTabla().getModel();
            model.addRow(new Object[]{nombre, codigoEstudiante, nuevoRegistro.getEdad(), telefono, carrera, sede}); // Usar getEdad()

            System.out.println("Registro agregado con éxito.");
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        vista.getTxtNombre().setText("");
        vista.getTxtCodigo().setText("");
        vista.getTxtTelefono().setText("");
        vista.getjEdad().setDate(null);
        vista.getjComboBoxCarrera().setSelectedIndex(0);
        vista.getjComboBoxSede().setSelectedIndex(0); // Cambiado a sede
    }

    private int calcularEdad(Date fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        Calendar fechaNac = Calendar.getInstance();
        fechaNac.setTime(fechaNacimiento);
        Calendar hoy = Calendar.getInstance();
        
        int edad = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
        if (hoy.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH) ||
            (hoy.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) && hoy.get(Calendar.DAY_OF_MONTH) < fechaNac.get(Calendar.DAY_OF_MONTH))) {
            edad--;
        }
        return edad;
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getjTabla().getModel();
        modelo.setRowCount(0); // Limpiar la tabla antes de actualizar
        for (ALUMNO alumno : listaAlumnos) {
            modelo.addRow(new Object[]{
                alumno.getNombre(), 
                alumno.getCodigo(), 
                alumno.getEdad(), 
                alumno.getTelefono(), 
                alumno.getCarrera(), 
                alumno.getSede() // Cambiado a sede
            });
        }
    }

    public void borrarAlumno(int selectedRow) {
        if (selectedRow != -1) {
            listaAlumnos.remove(selectedRow);
            DefaultTableModel model = (DefaultTableModel) vista.getjTabla().getModel();
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(vista, "Registro borrado con éxito");
        } else {
            JOptionPane.showMessageDialog(vista, "Por favor selecciona un registro para borrar.");
        }
    }

    public void actualizarAlumno(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < listaAlumnos.size()) {
            String nombre = vista.getTxtNombre().getText();
            String codigo = vista.getTxtCodigo().getText();
            String telefono = vista.getTxtTelefono().getText();
            Date fechaNacimiento = vista.getjEdad().getDate();
            String carrera = (String) vista.getjComboBoxCarrera().getSelectedItem();
            String sede = (String) vista.getjComboBoxSede().getSelectedItem(); // Cambiado a sede

            ALUMNO alumnoActualizado = listaAlumnos.get(selectedRow);
            alumnoActualizado.setNombre(nombre);
            alumnoActualizado.setCodigo(codigo);
            alumnoActualizado.setTelefono(telefono);
            alumnoActualizado.setEdad(calcularEdad(fechaNacimiento));
            alumnoActualizado.setCarrera(carrera);
            alumnoActualizado.setSede(sede); // Cambiado a sede

            actualizarTabla();
            JOptionPane.showMessageDialog(vista, "Registro actualizado con éxito");
        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un registro para actualizar");
        }
    }

    public void buscarAlumno(String text) {
        DefaultTableModel modelo = (DefaultTableModel) vista.getjTabla().getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de buscar
        for (ALUMNO alumno : listaAlumnos) {
            if (alumno.getNombre().contains(text) || alumno.getCodigo().contains(text)) {
                modelo.addRow(new Object[]{
                    alumno.getNombre(), 
                    alumno.getCodigo(), 
                    alumno.getEdad(), 
                    alumno.getTelefono(), 
                    alumno.getCarrera(), 
                    alumno.getSede() // Cambiado a sede
                });
            }
        }
    }
}
