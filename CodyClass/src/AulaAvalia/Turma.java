package AulaAvalia;

import java.util.ArrayList;

public class Turma {
	
	 private Professor professor;
	 private ArrayList<Aluno> alunos;
	
	  public Turma(Professor professor) {
	    this.professor = professor;
	    this.alunos = new ArrayList<>();
	  }
 
	public void adicionarAluno(Aluno aluno) {
	    alunos.add(aluno);
	  }

	  public void listarAlunos() {
	    for (Aluno aluno : alunos) {
	      System.out.println(aluno.getNome());
	    }
	  }

	  public Professor getProfessor() {
	    return professor;
	  }
	
}
