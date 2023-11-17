package AulaAvalia;

public class Main {

	public static void main(String[] args) {
		
		// Criação de objetos
		Professor professor1 = new Professor("João", 35, 0);
	    Aluno aluno1 = new Aluno("Erick", 20, null);
	    Aluno aluno2 = new Aluno("Pedro", 22, null);

	    // Criação da turma e adição de alunos
	    Turma turma1 = new Turma(professor1);
	    turma1.adicionarAluno(aluno1);
	    turma1.adicionarAluno(aluno2);

	    //Obtenção do professor 
	    Professor professorTurma1 = turma1.getProfessor();
	    System.out.println("Professor da Turma: " + professorTurma1.getNome());
	    
	    // Listagem dos alunos da turma
	    System.out.println("Alunos:");
	    turma1.listarAlunos();

	    
	  }

	}


