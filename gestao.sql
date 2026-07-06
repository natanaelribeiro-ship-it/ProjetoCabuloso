
CREATE DATABASE IF NOT EXISTS gestao_academica;
USE gestao_academica;

CREATE TABLE IF NOT EXISTS cursos (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    carga_horaria INT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS alunos (
    id_aluno INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS matriculas (
    id_matricula INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_curso INT NOT NULL,
    data_matricula DATE NOT NULL,
    status_matricula ENUM('Ativo', 'Concluído', 'Trancado', 'Cancelado') DEFAULT 'Ativo',
    FOREIGN KEY (id_aluno) REFERENCES alunos(id_aluno) ON DELETE CASCADE,
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso) ON DELETE CASCADE
);


INSERT INTO cursos (nome, descricao, carga_horaria) VALUES 
('Análise e Desenvolvimento de Sistemas', 'Curso focado em desenvolvimento de software.', 2000),
('Engenharia de Software', 'Foco em arquitetura e qualidade de software.', 3200);

INSERT INTO alunos (nome, email, data_nascimento) VALUES 
('Nelson Natanael', 'nelson@email.com', '2000-05-15'),
('Nayara Melissa', 'nayara@email.com', '2001-08-20'),
('Isaac Ruliver', 'isaac@email.com', '1999-11-02');

INSERT INTO matriculas (id_aluno, id_curso, data_matricula, status_matricula) VALUES 
(1, 1, CURDATE(), 'Ativo'),
(2, 1, CURDATE(), 'Ativo'),
(3, 2, CURDATE(), 'Ativo');
