package Principais;
import java.util.ArrayList;

public class Partida {
	
	private ArrayList<String> historicoJogador = new ArrayList<String>();
	private ArrayList<Integer[]> historicoJogadas = new ArrayList<Integer[]>();
	
	private String[][] tabuleiroFinal;
	
	private String jogador1;
	private String jogador2;
	
	private String vencedor;
	
	public Partida(String jogador1, String jogador2) {
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
	}

	public void adicionarHistoricoJogador(String jogador) {
		historicoJogador.add(jogador);
	}
	
	public void adicionarJogadaAPartida(Tabuleiro tabuleiro) {
		Integer[] ultimaJogada = tabuleiro.getUltimaJogada();
		Integer[] clone = {ultimaJogada[0], ultimaJogada[1]};
		historicoJogadas.add(clone);
	}

	public String getJogador1() {
		return jogador1;
	}

	public void setJogador1(String jogador1) {
		this.jogador1 = jogador1;
	}

	public String getJogador2() {
		return jogador2;
	}

	public void setJogador2(String jogador2) {
		this.jogador2 = jogador2;
	}

	public ArrayList<String> getHistoricoJogador() {
		return historicoJogador;
	}

	public void setHistoricoJogador(ArrayList<String> historicoJogador) {
		this.historicoJogador = historicoJogador;
	}

	public String[][] getTabuleiroFinal() {
		return tabuleiroFinal;
	}

	public void setTabuleiroFinal(String[][] tabuleiroFinal) {
		this.tabuleiroFinal = tabuleiroFinal;
	}

	public ArrayList<Integer[]> getHistoricoJogadas() {
		return historicoJogadas;
	}

	public void setHistoricoJogadas(ArrayList<Integer[]> historicoJogadas) {
		this.historicoJogadas = historicoJogadas;
	}

	public String getVencedor() {
		return vencedor;
	}

	public void setVencedor(String vencedor) {
		this.vencedor = vencedor;
	}
}
