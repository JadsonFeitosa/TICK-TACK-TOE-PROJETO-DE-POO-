package Principais;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Jogador implements Comparable<Jogador>{	
	private String nome;
	private String usuario;
	private String senha;
	private String email;
	private String cpf;
	private String profissao;
	private String sexo;
	private int idade;
	
	private String dataCadastro;
	private long tempo;
	private int totalVitorias;
	private int totalDerrotas;
	private float totalPontos;
	
	private ArrayList<Partida> historicoPartidas = new ArrayList<Partida>();
	private ArrayList<String[]> mensagensRecebidas = new ArrayList<String[]>();
	
	public Jogador(String nome, String usuario, String senha, String email, String cpf, String profissao, String sexo, int idade){
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.email = email;
		this.cpf = cpf;
		this.profissao = profissao;
		this.sexo = sexo;
		this.idade = idade;
		
		this.tempo = System.currentTimeMillis();
		this.dataCadastro = new SimpleDateFormat("dd/MM/yyyy").format(new Date(tempo));
	}
	
	public int compareTo(Jogador jogador) {
		if(tempo > jogador.getTempo()) {
			return -1;
		}else if(tempo < jogador.getTempo()) {
			return 1;
		}
		return 0;
	}
	
	public void adicionarVitoria() {
		totalVitorias++;
	}
	
	public void adicionarDerrotas() {
		totalDerrotas++;
	}
	
	public void adicionarPontos(float quantidade) {
		totalPontos += quantidade;
	}

	public void removerPontos(float quantidade) {
		if(totalPontos >= quantidade) {
			totalPontos -= quantidade;
		}else {
			totalPontos = 0;
		}
	}
	
	public void adicionarPartidaJogador(Partida partida) {
		historicoPartidas.add(partida);
	}
	
	public void adicionarMensagem(Jogador origem, String assunto, String mensagem) {
		String [] dados = {origem.getUsuario(), assunto, mensagem};
		mensagensRecebidas.add(dados);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	public int getTotalVitorias() {
		return totalVitorias;
	}

	public void setTotalVitorias(int totalVitorias) {
		this.totalVitorias = totalVitorias;
	}

	public int getTotalDerrotas() {
		return totalDerrotas;
	}

	public void setTotalDerrotas(int totalDerrotas) {
		this.totalDerrotas = totalDerrotas;
	}

	public float getTotalPontos() {
		return totalPontos;
	}

	public void setTotalPontos(float totalPontos) {
		this.totalPontos = totalPontos;
	}

	public ArrayList<Partida> getHistoricoPartidas() {
		return historicoPartidas;
	}

	public void setHistoricoPartidas(ArrayList<Partida> historicoPartidas) {
		this.historicoPartidas = historicoPartidas;
	}

	public ArrayList<String[]> getMensagensRecebidas() {
		return mensagensRecebidas;
	}

	public void setMensagensRecebidas(ArrayList<String[]> mensagensRecebidas) {
		this.mensagensRecebidas = mensagensRecebidas;
	}
	
	public String toString() {
		return "Login: "+ getUsuario()+"\n"+
			"Senha: "+ getSenha()+"\n"+
			"Email: "+ getEmail()+"\n"+
			"Nome: "+ getNome()+"\n"+
			"Idade: "+ getIdade()+"\n"+
			"CPF: "+ getCpf()+"\n"+
			"Sexo: "+ getSexo()+"\n";
	}

}
