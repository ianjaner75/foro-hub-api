-- ======= USUARIOS =======
CREATE TABLE IF NOT EXISTS usuarios (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre      VARCHAR(100)      NOT NULL,
  email       VARCHAR(100)      NOT NULL UNIQUE,
  contrasena  VARCHAR(255)      NOT NULL
);

-- ======= CURSOS =======
CREATE TABLE IF NOT EXISTS cursos (
  id        BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre    VARCHAR(100) NOT NULL UNIQUE,
  categoria VARCHAR(100) NOT NULL
);

-- ======= TOPICOS =======
CREATE TABLE IF NOT EXISTS topicos (
  id              BIGINT PRIMARY KEY AUTO_INCREMENT,
  titulo          VARCHAR(200) NOT NULL,
  mensaje         TEXT         NOT NULL,
  fecha_creacion  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status`        VARCHAR(20)  NOT NULL,
  autor_id        BIGINT       NOT NULL,
  curso_id        BIGINT       NOT NULL,
  CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
  CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES cursos(id),
  CONSTRAINT chk_topicos_status CHECK (`status` IN ('ABIERTO','CERRADO','ARCHIVADO'))
);

-- Índices (¡sin IF NOT EXISTS!)
CREATE INDEX idx_topicos_fecha  ON topicos(fecha_creacion);
CREATE INDEX idx_topicos_status ON topicos(`status`);
CREATE INDEX idx_topicos_autor  ON topicos(autor_id);
CREATE INDEX idx_topicos_curso  ON topicos(curso_id);
