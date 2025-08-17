-- V2__seed_minimos.sql
INSERT INTO usuarios (nombre, email, contrasena)
VALUES ('Bruno', 'bruno@alura.com', '{bcrypt}$2a$10$dNLTMgm9h2f5Afppcn.sUO1xcGMBBV5cMdm/jSoOLQ/5QIMxXQ7um');

INSERT INTO cursos (nombre, categoria)
VALUES ('Spring Boot', 'BACKEND');
