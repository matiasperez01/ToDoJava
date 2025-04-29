package com.todoproyecto.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todoproyecto.todo.exception.RecursoNoEncontradoException;
import com.todoproyecto.todo.model.Tarea;
import com.todoproyecto.todo.service.TareaService;

@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class TareaController {
	@Autowired
	private TareaService tareaService;

	@GetMapping("/tareas")
	public String listarTareas(Model model) {
		List<Tarea> tareas = tareaService.listarTareas();
		model.addAttribute("tareas", tareas);
		model.addAttribute("pendientes", tareaService.listarTareasPorEstado(Tarea.Status.PENDIENTE));
		model.addAttribute("enProgreso", tareaService.listarTareasPorEstado(Tarea.Status.EN_PROGRESO));
		model.addAttribute("completadas", tareaService.listarTareasPorEstado(Tarea.Status.COMPLETADA));
		if (tareas.isEmpty()) {
			throw new RecursoNoEncontradoException("No se encontraron tareas registradas.");
		} else {
			System.out.println(tareas);
			return "index";
		}
	}

	@PostMapping("/tareas")
	public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
		Tarea tareaPorCrear = tarea;
		tareaService.guardarTarea(tareaPorCrear);
		return ResponseEntity.ok(tareaPorCrear);
	}

	@PutMapping("/tareas/{id}")
	public ResponseEntity<Tarea> actualizarTarea(@RequestBody Tarea tarea, @PathVariable Long id) {
		Tarea tareaAmodificar = tareaService.listarTareaPorId(id);
		if (tareaAmodificar == null) {
			throw new RecursoNoEncontradoException("No fue posible encontrar la tarea con el ID " + id);
		} else {
			tareaAmodificar.setTitulo(tarea.getTitulo());
			tareaAmodificar.setDescripcion(tarea.getDescripcion());
			tareaAmodificar.setStatus(tarea.getStatus());
			tareaAmodificar.setBorradoLogico(tarea.getBorradoLogico());
			tareaService.guardarTarea(tareaAmodificar);
			return ResponseEntity.ok(tareaAmodificar);
		}
	}

	@DeleteMapping("/tareas/{id}")
	public ResponseEntity<String> eliminarTarea(@PathVariable Long id) {
		Tarea tareaPorEliminar = tareaService.listarTareaPorId(id);
		if (tareaPorEliminar == null) {
			throw new RecursoNoEncontradoException("No fue posible encontrar la tarea con el ID " + id);
		} else {
			tareaService.eliminarTarea(id);
			return ResponseEntity.ok("Tarea eliminada exitosamente. " + tareaPorEliminar);
		}
	}

	@GetMapping("/tareas/{id}")
	public ResponseEntity<Tarea> listarTareaPorId(Model model, @PathVariable Long id) {
		Tarea tareaEncontrada = tareaService.listarTareaPorId(id);
		if (tareaEncontrada == null) {
			throw new RecursoNoEncontradoException("No se encontro la tarea con el ID " + id);
		} else {
			model.addAttribute("tareaEncontrada", tareaEncontrada);
			return ResponseEntity.ok(tareaEncontrada);
		}
	}

	@GetMapping("/formulario")
	public String cargarFormulario(Model model) {
		model.addAttribute("tareaAcargar", new Tarea());
		return "formulario";
	}

	@GetMapping("/formulario/{id}")
	public String editarTareaFormulario(@PathVariable Long id, Model model) {
		Tarea tarea = tareaService.listarTareaPorId(id);
		if (tarea == null) {
			throw new RecursoNoEncontradoException("No se encontro la tarea con el ID " + id);
		} else {
			model.addAttribute("tareaAcargar", tarea);
			return "formulario";
		}
	}
}
