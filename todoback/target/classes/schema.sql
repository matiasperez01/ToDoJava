CREATE TABLE tarea (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    status VARCHAR(20),
    fecha_creacion DATE,
    borrado_logico BOOLEAN
);
