CREATE TABLE IF NOT EXISTS usuario (
    id INT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    nombre_completo VARCHAR(100),
    nickname VARCHAR(50) NOT NULL UNIQUE,
    numero_celular VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS entrenador (
    id INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pokemon (
    id INT PRIMARY KEY,
    tipo VARCHAR(50),
    especie VARCHAR(50),
    energia FLOAT,
    poder FLOAT,
    entrenador_id INT,
    vida FLOAT,
    FOREIGN KEY (entrenador_id) REFERENCES entrenador(id) ON DELETE SET NULL
);

-- Usuarios
INSERT INTO usuario (id, email, nombre_completo, nickname, numero_celular)
VALUES
(1, 'user1@example.com', 'Usuario Uno', 'nickname1', '1234567890'),
(2, 'user2@example.com', 'Usuario Dos', 'nickname2', '0987654321'),
(3, 'user3@example.com', 'Usuario Tres', 'nickname3', NULL); -- Usuario sin entrenadores

-- Entrenadores
INSERT INTO entrenador (id, nombre, usuario_id)
VALUES
(1, 'Entrenador Uno A', 1),
(2, 'Entrenador Uno B', 1),
(3, 'Entrenador Uno C', 1),
(4, 'Entrenador Dos A', 2),
(5, 'Entrenador Dos B', 2),
(6, 'Entrenador Dos C', 2);

-- Pokémon
INSERT INTO pokemon (id, tipo, especie, energia, poder, entrenador_id, vida)
VALUES
-- Pokémon para Entrenador Uno A
(1, 'Fuego', 'Charmander', 100, 50, 1, 100),
(2, 'Agua', 'Squirtle', 100, 45, 1, 100),
(3, 'Planta', 'Bulbasaur', 100, 48, 1, 100),

-- Pokémon para Entrenador Uno B
(4, 'Fuego', 'Vulpix', 100, 42, 2, 100),
(5, 'Agua', 'Poliwag', 100, 40, 2, 100),
(6, 'Planta', 'Oddish', 100, 38, 2, 100),

-- Pokémon para Entrenador Uno C
(7, 'Piedra', 'Geodude', 100, 55, 3, 100),
(8, 'Electricidad', 'Pikachu', 100, 60, 3, 100),
(9, 'Fuego', 'Growlithe', 100, 50, 3, 100),

-- Pokémon para Entrenador Dos A
(10, 'Agua', 'Psyduck', 100, 40, 4, 100),
(11, 'Planta', 'Chikorita', 100, 42, 4, 100),
(12, 'Electricidad', 'Magnemite', 100, 47, 4, 100),

-- Pokémon para Entrenador Dos B
(13, 'Fuego', 'Slugma', 100, 44, 5, 100),
(14, 'Agua', 'Horsea', 100, 45, 5, 100),
(15, 'Planta', 'Bellsprout', 100, 46, 5, 100),

-- Pokémon para Entrenador Dos C
(16, 'Piedra', 'Onix', 100, 60, 6, 100),
(17, 'Electricidad', 'Electabuzz', 100, 65, 6, 100),
(18, 'Fuego', 'Flareon', 100, 55, 6, 100);

