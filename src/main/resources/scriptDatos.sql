-- ========================================
-- BASE DE DATOS: listaCompra
-- ========================================

-- ========================================
-- TABLA: usuarios
-- Tabla que almacena los usuarios del sistema
-- ========================================
CREATE TABLE IF NOT EXISTS usuarios(
id SERIAL PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
apellido VARCHAR(50) NOT NULL,
dni VARCHAR(20) UNIQUE,
email VARCHAR(100) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL
);

-- ========================================
-- TABLA: listasCompra
-- Tabla que almacena las listas de compra generadas
-- ========================================
CREATE TABLE IF NOT EXISTS listasCompra (
idLista SERIAL PRIMARY KEY,
idUsuario INT NOT NULL,
nombreCompra VARCHAR(50) NOT NULL,
fechaCreacion DATE,
FOREIGN KEY (idUsuario) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- ========================================
-- TABLA: productos
-- Tabla que almacena los productos que se añaden en itemsLista
-- ========================================
CREATE TABLE IF NOT EXISTS productos (
idProducto SERIAL PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
precio DECIMAL(10,2) NOT NULL,
categoria VARCHAR(50)
);

-- ========================================
-- TABLA: itemsLista
-- Tabla que almacena todos los productos de una lista
-- ========================================
CREATE TABLE IF NOT EXISTS itemsLista (
idItem SERIAL PRIMARY KEY,
idProducto INT NOT NULL,
idLista INT NOT NULL,
cantidad INT NOT NULL,
precioUnitario DECIMAL(10,2) NOT NULL,
comprado BOOLEAN DEFAULT FALSE,
FOREIGN KEY(idProducto) REFERENCES productos(idProducto),
FOREIGN KEY (idLista) REFERENCES listasCompra(idLista) ON DELETE CASCADE,
UNIQUE(idLista,idProducto)
);

-- ========================================
-- DATOS DE PRUEBA
-- ========================================

-- Limpiar tablas (opcional pero recomendable para pruebas)
TRUNCATE itemsLista, listasCompra, productos, usuarios RESTART IDENTITY CASCADE;

-- ========================================
-- USUARIOS
-- ========================================
INSERT INTO usuarios (nombre, apellido, dni, email, password) VALUES
('Cristian', 'Paños', '09074828V', 'cristian@gmail.com', 'clave123'),
('Laura', 'García', '12345678A', 'laura@gmail.com', 'laura123'),
('Niall', 'Martín', '87654321B', 'niall@gmail.com', 'niall123');

-- ========================================
-- LISTAS DE COMPRA
-- ========================================
INSERT INTO listasCompra (idUsuario, nombreCompra, fechaCreacion) VALUES
(1, 'Compra semanal', '2026-04-20'),
(1, 'Limpieza hogar', '2026-04-21'),
(2, 'Compra mensual', '2026-04-18'),
(3, 'Cena especial', '2026-04-19');

-- ========================================
-- PRODUCTOS
-- ========================================
INSERT INTO productos (nombre, precio, categoria) VALUES
('Leche', 1.20, 'Lácteos'),
('Pan', 0.90, 'Panadería'),
('Huevos', 2.50, 'Alimentación'),
('Arroz', 1.75, 'Alimentación'),
('Pasta', 1.40, 'Alimentación'),
('Tomate frito', 1.95, 'Conservas'),
('Detergente', 5.80, 'Limpieza'),
('Lavavajillas', 2.95, 'Limpieza'),
('Papel higiénico', 4.50, 'Hogar'),
('Soja', 2.50, 'Salsas'),
('Avena', 2.80, 'Cereales'),
('Café', 4.20, 'Desayuno');

-- ========================================
-- ITEMS DE LAS LISTAS
-- ========================================
INSERT INTO itemsLista (idProducto, idLista, cantidad, precioUnitario, comprado) VALUES
(1, 1, 6, 1.20, false),
(2, 1, 3, 0.90, true),
(3, 1, 2, 2.50, false),
(4, 1, 1, 1.75, false),

(7, 2, 1, 5.80, false),
(8, 2, 2, 2.95, true),
(9, 2, 1, 4.50, false),

(5, 3, 4, 1.40, false),
(6, 3, 3, 1.95, false),
(12, 3, 1, 4.20, true),

(10, 4, 2, 2.50, false),
(11, 4, 1, 2.80, false),
(12, 4, 1, 4.20, false);