# 📝 ToDoJava

Aplicación backend para gestionar tareas, desarrollada con Spring Boot y Maven. Este proyecto permite crear, listar, actualizar y eliminar tareas a través de una API REST.

## 🔧 Tecnologías utilizadas

- Java
- Spring Boot
- Thymeleaf
- Maven
- Docker
- Render (para despliegue)
- JavaScript
- HTML
- H2

## 🚀 Cómo usar

El backend está desplegado en Render y accesible desde:

➡️ [https://todojava-ptda.onrender.com/api/tareas]

### 📦 Endpoints disponibles

- `GET /api/tareas`: Obtener todas las tareas
- `POST /api/tareas`: Crear una nueva tarea
- `PUT /api/tareas/{id}`: Actualizar una tarea existente
- `DELETE /api/tareas/{id}`: Eliminar una tarea

## 🐳 Despliegue con Docker

```bash
docker build -t todojava .
docker run -p 8080:8080 todojava
