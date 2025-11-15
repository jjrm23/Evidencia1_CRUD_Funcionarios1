USE db_funcionarios;

INSERT INTO tipo_documento (id_tipo_documento, nombre) VALUES 
(1, 'Cédula de Ciudadanía'),
(2, 'Tarjeta de Identidad'),
(3, 'Cédula de Extranjería'),
(4, 'Pasaporte');

INSERT INTO estado_civil (id_estado_civil, nombre) VALUES 
(1, 'Soltero(a)'),
(2, 'Casado(a)'),
(3, 'Unión Libre'),
(4, 'Divorciado(a)'),
(5, 'Viudo(a)');