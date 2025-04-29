




const guardarCambios = async (id) => {
	const tareaForm = {
		titulo: document.getElementById("titulo").value,
		descripcion: document.getElementById("descripcion").value,
		status: document.getElementById("estado").value,
	}
	
	if(id === undefined || id === null){
		if(tareaForm.titulo === "" || tareaForm.descripcion === ""){
			alert("Debe completar todos los campos.");
		}else{
			const responsePost = await fetch(`/api/tareas`, {
						method: 'POST',
						headers: {
				   		    'Content-Type': 'application/json'
					   	},
						body: JSON.stringify(tareaForm),
					})
					if(responsePost.ok){
						alert("Tarea creada exitosamente!");
						window.location.href = "/api/tareas";
					}else{
						alert("No se pudo crear la tarea.");
					}
		}
		
	}else{
		if(tareaForm.titulo === "" || tareaForm.descripcion === ""){
			alert("Debe completar todos los campos.");
		}else{
		const responsePut = await fetch(`/api/tareas/${id}`, {
					method: 'PUT',
					headers: {
			   		    'Content-Type': 'application/json'
				   	},
					body: JSON.stringify(tareaForm),
				})
				if(responsePut.ok){
					alert("Tarea editada exitosamente!");
					window.location.href = "/api/tareas";
				}else{
					alert("No se pudo editar la tarea.");
				}
			
		}
	}
}