CREATE DATABASE IF NOT EXISTS gestion_funcionarios;
USE gestion_funcionarios;

DROP TABLE IF EXISTS academico;
DROP TABLE IF EXISTS grupo_familiar;
DROP TABLE IF EXISTS funcionario;

CREATE TABLE funcionario (
  id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
  tipo_identificacion VARCHAR(5) NOT NULL,
  numero_identificacion VARCHAR(20) NOT NULL UNIQUE,
  nombres VARCHAR(100) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  estado_civil VARCHAR(20),
  sexo CHAR(1),
  direccion VARCHAR(150),
  telefono VARCHAR(20),
  fecha_nacimiento DATE
);

CREATE TABLE grupo_familiar (
  id_familiar INT AUTO_INCREMENT PRIMARY KEY,
  id_funcionario INT,
  nombre VARCHAR(100),
  parentesco VARCHAR(50),
  fecha_nacimiento DATE,
  CONSTRAINT fk_fam FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario) ON DELETE CASCADE
);

CREATE TABLE academico (
  id_academico INT AUTO_INCREMENT PRIMARY KEY,
  id_funcionario INT,
  universidad VARCHAR(100),
  nivel_estudio VARCHAR(50),
  titulo VARCHAR(100),
  CONSTRAINT fk_acad FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario) ON DELETE CASCADE
);
