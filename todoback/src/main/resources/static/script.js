let tareaActualId = null;

// funcion para cargar modal ver (muestra en detalle la tarea)
const verTarea = async (id) => {
    try {
        const response = await fetch(`/api/tareas/${id}`);
        if (!response.ok) {
            throw new Error("Error al obtener la tarea");
        }
	        const tarea = await response.json();
			document.getElementById("modalTitulo").textContent = tarea.titulo;
			document.getElementById("modal-descripcion").textContent = tarea.descripcion;
			document.getElementById("modal-status").textContent = tarea.status;
			document.getElementById("modal-fechaCreacion").textContent = tarea.fechaCreacion;	

		}catch (error) {
	        console.error("Error");
	        alert("No se pudo cargar la tarea", error.message);
	    }
	};

// creo una variable con el boton eliminar del modal para eliminar tarea
eliminarTareaButton = document.getElementById("eliminarTareaButton");

// funcion para eliminar tarea
eliminarTareaButton.addEventListener("click", async ()  => {				
	try {
	       const responseDelete = await fetch(`/api/tareas/${tareaActualId}`, {
	           method: 'DELETE',
	           headers: {
	               'Content-Type': 'application/json'
	           }
	       });

	       if (responseDelete.ok) {
//	           const resultado = await responseDelete.text();
//	           alert(resultado);
	           window.location.reload();
	       } else {
	           throw new Error(await responseDelete.text());
	       }
	   } catch (error) {
	       console.error("Error al eliminar:", error);
	       alert("Error al eliminar la tarea: " + error.message);
	   }
	
});	

//funcion para preguntar si se desea eliminar la tarea
const eliminarTarea = async (id) => {
	try {
	        const response = await fetch(`/api/tareas/${id}`);
	        if (!response.ok) {
	            throw new Error("Error al obtener la tarea");
	        }else{
			const tarea = await response.json();
			tareaActualId = tarea.id;
			document.getElementById("modal-tareaPoreliminar").textContent = `${tarea.id} - ${tarea.titulo}?`;
			}
			}catch (error) {
		        console.error("Error");
		        alert("No se pudo cargar la tarea", error.message);
		    }
}

//funcion para actualizar el estado de una tarea
const actualizarEstado = async (id) => {
const responseUpdate = await fetch(`/api/tareas/${id}`)
if (responseUpdate.ok) {
	           //const resultado = await responseUpdate.text();
			   const tareaPorActualizar = await responseUpdate.json();
			   switch(tareaPorActualizar.status){
			   		case "PENDIENTE":		
					tareaPorActualizar.status = "EN_PROGRESO";
					break;
					
					case "EN_PROGRESO":
					tareaPorActualizar.status = "COMPLETADA";			
					break;			
			
					case "COMPLETADA":
					tareaPorActualizar.status = "PENDIENTE";		
					break;		
			   }
			   
			   	const actualizarEstadoRequest = 
			   	await fetch(`/api/tareas/${id}`, {
			   		method: 'PUT',
			   		headers: {
			   		    'Content-Type': 'application/json'
			   	},
				body: JSON.stringify(tareaPorActualizar)
			   })
			   if(actualizarEstadoRequest.ok){
			   	alert("Estado de la tarea modificado exitosamente.");
			   	window.location.reload();
			   }else{
					console.error("No se pudo modificar el estado de la tarea.");
			   }
	       } else {
	           throw new Error(await responseUpdate.text());
	       }
}

//funcion para filtrar por estado:
const buscarPorEstado = () => {
    const filtro = document.getElementById("estadoFiltro").value;
    const tareas = document.querySelectorAll(".tarea");
    tareas.forEach(tarea => {
        const estado = tarea.getAttribute("data-estado");
        if (filtro === "TODAS" || estado === filtro) {
            tarea.style.display = ""; 
        } else {
            tarea.style.display = "none"; 
        }
    });
}

//funcion para filtrar por titulo:
function filtrarPorTitulo() {
    const inputTitulo = document.getElementById('filtroTitulo').value.toLowerCase().trim();
    const tareas = document.querySelectorAll('tr.tarea');
	let algunaCoincide = false;
    tareas.forEach((tarea) => {
        const tituloTarea = tarea.querySelector('.titulo').textContent.toLowerCase().trim();
        if (tituloTarea.includes(inputTitulo)) {
            tarea.style.display = '';
			document.getElementById('filtroTitulo').value = "";
        } else {
            tarea.style.display = 'none';
        }
    });


}



