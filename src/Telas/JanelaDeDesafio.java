package Telas;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.itextpdf.text.Font;

import Principais.Jogador;
import Principais.Partida;
import Principais.Tabuleiro;

public class JanelaDeDesafio extends JanelaPadrao{
	
	private JLabel labelJogador;
	
	private JButton[][] botoes = new JButton[3][3];
	
	private Jogador jogadorLogado;
	private Jogador jogadorSelecionado;
	
	private Partida partida;
	
	private Tabuleiro tabuleiro = new Tabuleiro();
	
	private int contador;
	
	public JanelaDeDesafio(Jogador jogadorLogado, Jogador jogadorSelecionado, JanelaPadrao telaPadrao) {
		super("Tabuleiro", 365, 376);
		
		this.jogadorLogado = jogadorLogado;
		this.jogadorSelecionado = jogadorSelecionado;
		
		partida = new Partida(jogadorLogado.getUsuario(), jogadorSelecionado.getUsuario());
		
		acoes();
		repaint();
		
		subirCentralAddListener();
		setLocationRelativeTo(telaPadrao);
		setVisible(true);
	}
	private void acoes() {
		adcLabel();
		adcBotao();
	}
	
	private void adcBotao() {
		botoes[0][0] = JBPadrao("", 60, 70, 70, 70, "");
		botoes[0][0].addActionListener(new OuvinteBotoesTabuleiro(0, 0));
		botoes[0][1] = JBPadrao("", 140, 70, 70, 70, "");
		botoes[0][1].addActionListener(new OuvinteBotoesTabuleiro(0, 1));
		botoes[0][2] = JBPadrao("", 220, 70, 70, 70, "");
		botoes[0][2].addActionListener(new OuvinteBotoesTabuleiro(0, 2));
		
		botoes[1][0] = JBPadrao("", 60, 151, 70, 70, "");
		botoes[1][0].addActionListener(new OuvinteBotoesTabuleiro(1, 0));
		botoes[1][1] = JBPadrao("", 140, 151, 70, 70, "");
		botoes[1][1].addActionListener(new OuvinteBotoesTabuleiro(1, 1));
		botoes[1][2] = JBPadrao("", 220, 151, 70, 70, "");
		botoes[1][2].addActionListener(new OuvinteBotoesTabuleiro(1, 2));

		botoes[2][0] = JBPadrao("", 60, 230, 70, 70, "");
		botoes[2][0].addActionListener(new OuvinteBotoesTabuleiro(2, 0));
		botoes[2][1] = JBPadrao("", 140, 230, 70, 70, "");
		botoes[2][1].addActionListener(new OuvinteBotoesTabuleiro(2, 1));
		botoes[2][2] = JBPadrao("", 220, 230, 70, 70, "");
		botoes[2][2].addActionListener(new OuvinteBotoesTabuleiro(2, 2));
	}
	
	private void adcLabel() {
		JLabelPadrao(jogadorLogado.getUsuario() + " VS  " + jogadorSelecionado.getUsuario(),"OCR A Extended",Font.BOLD,18, 60, 25, 370, 28);
		labelJogador = JLabelPadrao(jogadorLogado.getUsuario()  + ", agora é sua vez","OCR A Extended",Font.BOLD,14, 30, 310, 200, 28);
	}
	
	private String jogadorDaVez() {
		String[] letra = {"X", "O"};
		int vez = contador%2;
		contador++;
		return letra[vez];
	}
	
	private void voltarJanelaDetalharPerfil() {
		new JanelaDetalharPerfil(jogadorLogado, jogadorSelecionado, this);
		dispose();
	}
	
	private void juiz(String letra) {
		if(letra.equals("X")) {
			partida.adicionarHistoricoJogador(jogadorLogado.getUsuario());
			labelJogador.setText(jogadorSelecionado.getUsuario() + ", agora é sua vez");
		}else {
			partida.adicionarHistoricoJogador(jogadorSelecionado.getUsuario());
			labelJogador.setText(jogadorLogado.getUsuario() + ", agora é sua vez");
		}
		
		if(tabuleiro.fimDeJogada()) {
			Jogador jogadorVencedor = null;
			Jogador jogadorPerdedor = null;
			if(letra.equals("X")) {
				jogadorVencedor = jogadorLogado;
				jogadorPerdedor = jogadorSelecionado;
			}else {
				jogadorVencedor = jogadorSelecionado;
				jogadorPerdedor = jogadorLogado;
			}
			
			partida.setVencedor(jogadorVencedor.getUsuario());
			central.adicionarPartida(partida);
			
			jogadorVencedor.adicionarVitoria();
			jogadorVencedor.adicionarPontos(4);
			jogadorVencedor.adicionarPartidaJogador(partida);
			
			jogadorPerdedor.adicionarDerrotas();
			jogadorPerdedor.removerPontos(4);
			jogadorPerdedor.adicionarPartidaJogador(partida);
			
			partida.setTabuleiroFinal(tabuleiro.getMatriz());
			
			salvarCentral();
			JOptionPane.showMessageDialog(this, jogadorVencedor.getUsuario() + " venceu " + jogadorPerdedor.getUsuario(), "Sistema", JOptionPane.WARNING_MESSAGE);
			voltarJanelaDetalharPerfil();
		}else if(tabuleiro.empate()) {
			partida.setVencedor("Empate");
			partida.setTabuleiroFinal(tabuleiro.getMatriz());
			central.adicionarPartida(partida);
			jogadorLogado.adicionarPartidaJogador(partida);
			jogadorSelecionado.adicionarPartidaJogador(partida);
			
			salvarCentral();
			JOptionPane.showMessageDialog(this, "Vocês empataram", "Sistema", JOptionPane.WARNING_MESSAGE);
			voltarJanelaDetalharPerfil();
		}
	}
	
	private class OuvinteBotoesTabuleiro implements ActionListener {
		private int linha;
		private int coluna;
		
		public OuvinteBotoesTabuleiro(int linha, int coluna) {
			this.linha = linha;
			this.coluna = coluna;
		}
		
		public void actionPerformed(ActionEvent e) {
			JButton botao = (JButton)e.getSource();
			if(botao.getText().length() == 0) {
				String letra = jogadorDaVez();
				botao.setText(letra);
				
				tabuleiro.jogada(linha, coluna, letra);
				partida.adicionarJogadaAPartida(tabuleiro);
				
				juiz(letra);
			}
		}
	}

	public JLabel getLabelJogador() {
		return labelJogador;
	}
	public void setLabelJogador(JLabel labelJogador) {
		this.labelJogador = labelJogador;
	}
	public JButton[][] getBotoes() {
		return botoes;
	}
	public void setBotoes(JButton[][] botoes) {
		this.botoes = botoes;
	}
	public Jogador getJogadorLogado() {
		return jogadorLogado;
	}
	public void setJogadorLogado(Jogador jogadorLogado) {
		this.jogadorLogado = jogadorLogado;
	}
	public Jogador getJogadorSelecionado() {
		return jogadorSelecionado;
	}
	public void setJogadorSelecionado(Jogador jogadorSelecionado) {
		this.jogadorSelecionado = jogadorSelecionado;
	}
	public Partida getPartida() {
		return partida;
	}
	public void setPartida(Partida partida) {
		this.partida = partida;
	}
	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	public void setTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	
}
