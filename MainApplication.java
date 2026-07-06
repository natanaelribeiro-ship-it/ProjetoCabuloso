import java.sql.*;
import java.util.Scanner;

public class MainApplication {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\nGESTÃO ACADÊMICA ");
            System.out.println("1. Alunos");
            System.out.println("2. Cursos");
            System.out.println("3. Matrículas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1 -> menuAlunos();
                    case 2 -> menuCursos();
                    case 3 -> menuMatriculas();
                    case 0 -> System.out.println("Encerrando o sistema...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida ou falha no sistema. " + e.getMessage());
            }
        }
    }


    // 1. CRUD ALUNOS
    
    private static void menuAlunos() throws SQLException {
        System.out.println("\n--- Menu Alunos ---");
        System.out.println("1. Cadastrar Aluno\n2. Listar Alunos\n3. Atualizar Aluno\n4. Excluir Aluno\n5. Voltar");
        System.out.print("Opção: ");
        int op = Integer.parseInt(scanner.nextLine());

        switch (op) {
            case 1 -> {
                System.out.print("Nome: "); String nome = scanner.nextLine();
                System.out.print("Email: "); String email = scanner.nextLine();
                System.out.print("Data de Nascimento (AAAA-MM-DD): "); String dataNasc = scanner.nextLine();
                
                String sql = "INSERT INTO alunos (nome, email, data_nascimento) VALUES (?, ?, ?)";
                try (Connection conn = DatabaseConnection.getConnection(); 
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nome);
                    stmt.setString(2, email);
                    stmt.setDate(3, Date.valueOf(dataNasc));
                    stmt.executeUpdate();
                    System.out.println("✔ Aluno cadastrado com sucesso!");
                }
            }
            case 2 -> {
                String sql = "SELECT * FROM alunos";
                try (Connection conn = DatabaseConnection.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    System.out.println("\nID | Nome | Email | Data Nasc.");
                    while (rs.next()) {
                        System.out.printf("%d | %s | %s | %s\n", rs.getInt("id_aluno"), rs.getString("nome"), rs.getString("email"), rs.getDate("data_nascimento"));
                    }
                }
            }
            case 3 -> {
                System.out.print("ID do Aluno para atualizar: "); int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Novo Nome: "); String nome = scanner.nextLine();
                System.out.print("Novo Email: "); String email = scanner.nextLine();

                String sql = "UPDATE alunos SET nome = ?, email = ? WHERE id_aluno = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nome);
                    stmt.setString(2, email);
                    stmt.setInt(3, id);
                    int rows = stmt.executeUpdate();
                    System.out.println(rows > 0 ? "✔ Aluno atualizado!" : "❌ Aluno não encontrado.");
                }
            }
            case 4 -> {
                System.out.print("ID do Aluno para excluir: "); int id = Integer.parseInt(scanner.nextLine());
                String sql = "DELETE FROM alunos WHERE id_aluno = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    int rows = stmt.executeUpdate();
                    System.out.println(rows > 0 ? "✔ Aluno removido!" : "❌ Aluno não encontrado.");
                }
            }
        }
    }

    // 2. CRUD CURSOS

    private static void menuCursos() throws SQLException {
        System.out.println("\n--- Menu Cursos ---");
        System.out.println("1. Cadastrar Curso\n2. Listar Cursos\n3. Atualizar Curso\n4. Excluir Curso\n5. Voltar");
        System.out.print("Opção: ");
        int op = Integer.parseInt(scanner.nextLine());

        switch (op) {
            case 1 -> {
                System.out.print("Nome do Curso: "); String nome = scanner.nextLine();
                System.out.print("Descrição: "); String desc = scanner.nextLine();
                System.out.print("Carga Horária: "); int ch = Integer.parseInt(scanner.nextLine());

                String sql = "INSERT INTO cursos (nome, descricao, carga_horaria) VALUES (?, ?, ?)";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nome);
                    stmt.setString(2, desc);
                    stmt.setInt(3, ch);
                    stmt.executeUpdate();
                    System.out.println("✔ Curso cadastrado com sucesso!");
                }
            }
            case 2 -> {
                String sql = "SELECT * FROM cursos";
                try (Connection conn = DatabaseConnection.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    System.out.println("\nID | Nome | Carga Horária");
                    while (rs.next()) {
                        System.out.printf("%d | %s | %d horas\n", rs.getInt("id_curso"), rs.getString("nome"), rs.getInt("carga_horaria"));
                    }
                }
            }
            case 3 -> {
                System.out.print("ID do Curso para atualizar: "); int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Novo Nome: "); String nome = scanner.nextLine();
                System.out.print("Nova Carga Horária: "); int ch = Integer.parseInt(scanner.nextLine());

                String sql = "UPDATE cursos SET nome = ?, carga_horaria = ? WHERE id_curso = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nome);
                    stmt.setInt(2, ch);
                    stmt.setInt(3, id);
                    int rows = stmt.executeUpdate();
                    System.out.println(rows > 0 ? "✔ Curso atualizado!" : "❌ Curso não encontrado.");
                }
            }
            case 4 -> {
                System.out.print("ID do Curso para excluir: "); int id = Integer.parseInt(scanner.nextLine());
                String sql = "DELETE FROM cursos WHERE id_curso = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    int rows = stmt.executeUpdate();
                    System.out.println(rows > 0 ? "✔ Curso removido!" : "❌ Curso não encontrado.");
                }
            }
        }
    }


    // 3. CRUD MATRÍCULAS 
  
    private static void menuMatriculas() throws SQLException {
        System.out.println("\n--- Menu Matrículas ---");
        System.out.println("1. Matricular Aluno em Curso\n2. Listar Histórico de Matrículas\n3. Alterar Status de Matrícula\n4. Cancelar Matrícula (Excluir)\n5. Voltar");
        System.out.print("Opção: ");
        int op = Integer.parseInt(scanner.nextLine());

        switch (op) {
            case 1 -> {
                System.out.print("ID do Aluno: "); int idAluno = Integer.parseInt(scanner.nextLine());
                System.out.print("ID do Curso: "); int idCurso = Integer.parseInt(scanner.nextLine());
                
                String sql = "INSERT INTO matriculas (id_aluno, id_curso, data_matricula, status_matricula) VALUES (?, ?, CURDATE(), 'Ativo')";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, idAluno);
                    stmt.setInt(2, idCurso);
                    stmt.executeUpdate();
                    System.out.println("✔ Aluno matriculado com sucesso!");
                }
            }
            case 2 -> {
                String sql = "SELECT m.id_matricula, a.nome AS aluno, c.nome AS curso, m.data_matricula, m.status_matricula " +
                             "FROM matriculas m " +
                             "JOIN alunos a ON m.id_aluno = a.id_aluno " +
                             "JOIN cursos c ON m.id_curso = c.id_curso";
                try (Connection conn = DatabaseConnection.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    System.out.println("\nID Mat. | Aluno | Curso | Data Mat. | Status");
                    while (rs.next()) {
                        System.out.printf("%d | %s | %s | %s | %s\n", 
                            rs.getInt("id_matricula"), rs.getString("aluno"), rs.getString("curso"), rs.getDate("data_matricula"), rs.getString("status_matricula"));
                    }
                }
            }
            case 3 -> {
                System.out.print("ID da Matrícula: "); int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Novo Status (Ativo, Concluído, Trancado, Cancelado): "); String status = scanner.nextLine();

                String sql = "UPDATE matriculas SET status_matricula = ? WHERE id_matricula = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, status);
                    stmt.setInt(2, id);
                    int rows = stmt.executeUpdate();
                    System.out.println(rows > 0 ? "✔ Status alterado!" : "❌ Matrícula não encontrada.");
                }
            }
            case 4 -> {
                System.out.print("ID da Matrícula a ser excluída: "); int id = Integer.parseInt(scanner.nextLine());
                String sql = "DELETE FROM matriculas WHERE id_matricula = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    int rows = stmt.executeUpdate();
                    System.out.println(rows > 0 ? "✔ Matrícula excluída com sucesso!" : "❌ Matrícula não encontrada.");
                }
            }
        }
    }
}
