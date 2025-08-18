CREATE TABLE IF NOT EXISTS respuestas (
  id             BIGINT PRIMARY KEY AUTO_INCREMENT,
  mensaje        TEXT        NOT NULL,
  fecha_creacion DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  autor_id       BIGINT      NOT NULL,
  topico_id      BIGINT      NOT NULL,
  CONSTRAINT fk_resp_autor  FOREIGN KEY (autor_id) REFERENCES usuarios(id),
  CONSTRAINT fk_resp_topico FOREIGN KEY (topico_id) REFERENCES topicos(id)
);

CREATE INDEX idx_resp_topico ON respuestas(topico_id);
CREATE INDEX idx_resp_autor  ON respuestas(autor_id);
