CREATE DATABASE IF NOT EXISTS db_funcionarios;
USE db_funcionarios;

CREATE TABLE IF NOT EXISTS tipo_documento (
    id_tipo_documento INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS estado_civil (
    id_estado_civil INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS funcionario (
    id_funcionario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    numero_documento VARCHAR(20) NOT NULL UNIQUE,
    fecha_nacimiento DATE,
    direccion VARCHAR(150),
    telefono VARCHAR(15),
    email VARCHAR(100),
    id_tipo_documento INT NOT NULL,
    id_estado_civil INT NOT NULL,
    
    FOREIGN KEY (id_tipo_documento) REFERENCES tipo_documento(id_tipo_documento),
    FOREIGN KEY (id_estado_civil) REFERENCES estado_civil(id_estado_civil)
);

CREATE TABLE IF NOT EXISTS formacion_academica (
    id_formacion INT PRIMARY KEY AUTO_INCREMENT,
    id_funcionario INT NOT NULL,
    nivel_academico VARCHAR(50),
    institucion VARCHAR(100),
    fecha_graduacion DATE,
    
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS grupo_familiar (
    id_familiar INT PRIMARY KEY AUTO_INCREMENT,
    id_funcionario INT NOT NULL,
    nombre_familiar VARCHAR(100) NOT NULL,
    parentesco VARCHAR(50),
    
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario) ON DELETE CASCADE
);