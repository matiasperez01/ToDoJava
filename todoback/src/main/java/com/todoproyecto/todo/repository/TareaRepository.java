package com.todoproyecto.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoproyecto.todo.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

	public List<Tarea> findByBorradoLogicoFalse();

	public Tarea findByTituloIgnoreCaseAndBorradoLogicoFalse(String titulo);

	public List<Tarea> findByStatusAndBorradoLogicoFalse(Tarea.Status status);

}
