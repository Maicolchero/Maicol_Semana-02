/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User17
 */
import java.util.ArrayList;
import java.util.List;

public class ARRAYLIST {
    private List<ALUMNO> listaAlumnos;

    public ARRAYLIST() {
        this.listaAlumnos = new ArrayList<>();
    }

    public void agregarAlumno(ALUMNO alumno) {
        listaAlumnos.add(alumno);
    }

    public void borrarAlumno(int index) {
        if (index >= 0 && index < listaAlumnos.size()) {
            listaAlumnos.remove(index);
        }
    }

    public void actualizarAlumno(int index, ALUMNO alumno) {
        if (index >= 0 && index < listaAlumnos.size()) {
            listaAlumnos.set(index, alumno);
        }
    }

    public List<ALUMNO> getListaAlumnos() {
        return listaAlumnos;
    }
    
    public ALUMNO buscarAlumnoPorCodigo(String codigo) {
        for (ALUMNO alumno : listaAlumnos) {
            if (alumno.getCodigo().equals(codigo)) {
                return alumno;
            }
        }
        return null; // Si no encuentra un alumno con ese código
    }

    public List<ALUMNO> buscarAlumnosPorSede(String sede) {
        List<ALUMNO> alumnosEnSede = new ArrayList<>();
        for (ALUMNO alumno : listaAlumnos) {
            if (alumno.getSede().equalsIgnoreCase(sede)) {
                alumnosEnSede.add(alumno);
            }
        }
        return alumnosEnSede;
    }
}
