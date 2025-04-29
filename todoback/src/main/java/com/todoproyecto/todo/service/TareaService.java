package com.todoproyecto.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoproyecto.todo.exception.RecursoNoEncontradoException;
import com.todoproyecto.todo.model.Tarea;
import com.todoproyecto.todo.repository.TareaRepository;

@Service
public class TareaService {

	@Autowired
	private TareaRepository tareaRepository;

	public List<Tarea> listarTareas() {
		List<Tarea> tareas = tareaRepository.findByBorradoLogicoFalse();
		if (tareas.isEmpty())
			throw new RecursoNoEncontradoException("No se encontraron tareas registradas");
		else
			return tareas;
	}

	public Tarea guardarTarea(Tarea tareaNueva) {
		Tarea tareaPorCrear = tareaNueva;
		if (tareaPorCrear.getTitulo() == null || tareaPorCrear.getTitulo().isBlank()
				|| tareaPorCrear.getDescripcion() == null || tareaPorCrear.getDescripcion().isBlank()) {
			throw new RecursoNoEncontradoException("Debe completar los datos obligatorios");
		} else
			return tareaRepository.save(tareaPorCrear);
	}

	public void eliminarTarea(Long id) {
		Tarea tareaPorEliminar = tareaRepository.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la tarea con el ID " + id));
		tareaPorEliminar.setBorradoLogico(true);
		tareaRepository.save(tareaPorEliminar);
	}

	public Tarea listarTareaPorId(Long id) {
		return tareaRepository.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("No se encontro la tarea con el ID" + id));
	}

	public Tarea listarTareaPorTitulo(String titulo) {
		Tarea tarea = tareaRepository.findByTituloIgnoreCaseAndBorradoLogicoFalse(titulo.trim());
		if (tarea == null) {
			throw new RecursoNoEncontradoException("No se encontro ninguna tarea con el titulo " + titulo);
		} else {
			return tarea;
		}
	}

	public List<Tarea> listarTareasPorEstado(Tarea.Status status) {
		List<Tarea> tareas = tareaRepository.findByStatusAndBorradoLogicoFalse(status);
		return tareas;
	}

}
