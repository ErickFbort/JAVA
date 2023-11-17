package AulaAvalia;

public class Professor extends Pessoa {
	
	private double Salario;	
	
	 public Professor(String nome, int idade, double Salario) {
		    super(nome, idade);
		    this.setSalario (Salario);
	 }
	 
	public double getSalario() {
			return Salario;
	 }

	public void setSalario(double salario) {
			Salario = salario;
	 }
}
		

