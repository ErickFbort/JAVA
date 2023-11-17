package AulaAvalia;

import java.util.List;

public class Aluno extends Pessoa {
	
	private List <Double> notas;
	
	public Aluno(String nome, int idade, List <Double> notas) {
	    super(nome, idade);
	  }
	
	public List<Double> getNotas() {
		return notas;
	}

	public void setNotas(List<Double> notas) {
		this.notas = notas;
	}

	
}
