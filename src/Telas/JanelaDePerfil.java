package Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.itextpdf.text.Font;

import Exceptions.CodigoEmailInvalidoException;
import Exceptions.EnviarEmailException;
import Exceptions.NumeroInvalidoException;
import Exceptions.RecuperarJogadorException;
import Imagens.IconesPadrao;
import Principais.Jogador;

public class JanelaDePerfil extends JanelaPadrao{
	
	private JButton jbSair;//
	private JButton jbListarJogadores;
	private JButton jbMensagens;
	private JButton jbHistoricoDeJogos;
	private JButton jbNovoJogoComputador;//
	private JButton jbEditarDados;//
	private JButton jbExcluirConta;//
	
	private Jogador jogadorLogado;
	
	
	public JanelaDePerfil(Jogador jogadorLogado, JanelaPadrao telaPadrao) {
		super("Perfil", 510, 381);
		
		this.jogadorLogado = jogadorLogado;
		central.ranquePorPontos();
		
		acoes();
		repaint();
		
		subirCentralAddListener();
		setLocationRelativeTo(telaPadrao);
		setVisible(true);
	}
	
	private void acoes() {
		adcMenuBar();
		adcLabel();
		adcIcones();
		adcBotoes();
	}
	
	private void adcMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu("Mais");
		menuBar.add(menu);
		JMenuItem item = new JMenuItem("Enviar e-mail");
		item.addActionListener(new OuvinteMenuItem(this));
		menu.add(item);
	}
	
	private void adcLabel() {
		JLabelPadrao("Perfil","OCR A Extended",Font.BOLD,30, 218, 25, 120, 40);
		JLabelPadrao(jogadorLogado.getUsuario(),"Arial",Font.NORMAL,14, 65, 80, 400, 20);
		JLabelPadrao(jogadorLogado.getEmail(),"Arial",Font.NORMAL,14, 65, 125, 400, 20);
		
		try {
			JLabelPadrao("Posição no ranque: " + (central.recuperarIndice(jogadorLogado.getUsuario())+1) + "°","Arial",Font.NORMAL,15, 15, 160, 400, 20);
		} catch (RecuperarJogadorException e) {}
		
		JLabelPadrao("Pontuação: " + jogadorLogado.getTotalPontos(),"Arial",Font.NORMAL,14, 15, 180, 200, 20);
		JLabelPadrao("Total de vitórias: " + jogadorLogado.getTotalVitorias() ,"Arial",Font.NORMAL,14, 15, 200, 200, 20);
		JLabelPadrao("Total de derrotas: " + jogadorLogado.getTotalDerrotas(),"Arial",Font.NORMAL,14, 15, 220, 200, 20);
		
		JLabelPadrao("V/D: " + central.calcularVD(jogadorLogado) ,"Arial",Font.BOLD,20, 345, 215, 200, 25);
		
	}
	
	private void adcIcones() {
		adcIcones(IconesPadrao.iconeUsuario, 15, 70, 36, 38);
		adcIcones(IconesPadrao.iconeEmail, 15, 115, 37, 38);
	}

	private void adcBotoes() {
		jbSair = JBPadrao("Sair", 445, 10, 50, 32, "Clique aqui para sair e voltar pra tela de login.");
		jbSair.addActionListener(new OuvinteBotaoSair(this));
		
		jbListarJogadores = JBPadrao("Listar Jogadores", 15, 245, 150, 28, "Ver lista de jogadores.");
		jbListarJogadores.addActionListener(new OuvinteBotaoListarJogador(this));
		
		jbHistoricoDeJogos = JBPadrao("Histórico", 180, 245, 150, 28, "Listar histórico de partidas.");
		jbHistoricoDeJogos.addActionListener(new OuvintePartida(this));

		jbEditarDados = JBPadrao("Editar", 345, 245, 150, 28,"Editar Dados Cadastrais." );
		jbEditarDados.addActionListener(new OuvinteBotaoEditar(this));
		
		jbNovoJogoComputador = JBPadrao("Novo Jogo", 15, 288, 150, 28, "Jogar com a Maquina.");
		jbNovoJogoComputador.addActionListener(new OuvinteBotaoNovoJogo(this));

		jbMensagens = JBPadrao("Mensagens", 180, 288, 150, 28, "Mensagens Recebidas.");
		jbMensagens.addActionListener(new OuvinteMensagen(this));
		
		jbExcluirConta = JBPadrao("Excluir", 345, 288, 150, 28, "Excluir sua Conta!");
		jbExcluirConta.addActionListener(new OuvinteExcluirConta(this));
	}
	
	private class OuvinteExcluirConta implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteExcluirConta(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}

		public void actionPerformed(ActionEvent arg0) {
			int codigoGerado = getValidacoes().gerarCodigo();
			String codigoFormatoTexto = new String();
			try {
				central.enviarEmail(jogadorLogado.getEmail(), "Código de exclusão de conta", Integer.toString(codigoGerado));
				
				codigoFormatoTexto = (String)JOptionPane.showInputDialog(telaPadraoOuvinte, "Informe o código", "Sistema", JOptionPane.INFORMATION_MESSAGE);
				int codigoTentado = getValidacoes().numeroInvalido(codigoFormatoTexto);
				getValidacoes().codigoEmailInvalido(codigoGerado, codigoTentado);
				
				central.excluirJogador(jogadorLogado);
				
				salvarCentral();
				new JanelaDeAutenticacao(telaPadraoOuvinte);
				dispose();
			} catch (EnviarEmailException | NumeroInvalidoException | CodigoEmailInvalidoException e) {
				if(codigoFormatoTexto != null) {
					JOptionPane.showMessageDialog(telaPadraoOuvinte, e.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	private class OuvinteBotaoNovoJogo implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoNovoJogo(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			Jogador computador = new Jogador(null, "Computador", null, null, null, null, null, 0);
			String[] opcoes = {"Fácil", "Médio", "Difícil"};
			
			String selecionado = (String)JOptionPane.showInputDialog(telaPadraoOuvinte, "Dificuldade", "Opções", JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);
			
			if(selecionado != null && selecionado.equals("Fácil")) {
				new JanelaDeDesafioComputador(jogadorLogado, computador, telaPadraoOuvinte, (float)1.0, (float)0.5, (float)0.5, 1);
				dispose();
			}else if(selecionado != null && selecionado.equals("Médio")) {
				new JanelaDeDesafioComputador(jogadorLogado, computador, telaPadraoOuvinte, (float)1.0, (float)1.0, (float)1.0, 2);
				dispose();
			}
			else if(selecionado != null && selecionado.equals("Difícil")) {
				new JanelaDeDesafioComputador(jogadorLogado, computador, telaPadraoOuvinte, (float)3.0, (float)1.0, (float)1.0, 3);
				dispose();
			}
			
		}	
	}
	
	private class OuvinteMenuItem implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteMenuItem(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaEnviarEmailNaoCadastrado(jogadorLogado, telaPadraoOuvinte);
		}
	}
	
	private class OuvinteBotaoEditar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoEditar(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaDeEdicao(jogadorLogado, telaPadraoOuvinte);
			dispose();
		}
	}
	
	
	private class OuvinteBotaoListarJogador implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoListarJogador(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaListarJogador(jogadorLogado, telaPadraoOuvinte);
			dispose();	
		}
	}
	private class OuvinteBotaoSair implements ActionListener {
		
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoSair(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaDeAutenticacao(telaPadraoOuvinte);
			dispose();
		}
	}
	
	private class OuvinteMensagen implements ActionListener{
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteMensagen(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaListarMensagens(jogadorLogado,telaPadraoOuvinte);
			dispose();
		}
	}
	
	private class OuvintePartida implements ActionListener{
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvintePartida(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaListarPartidas(jogadorLogado, telaPadraoOuvinte);
			dispose();
		}
	}
	
	public JButton getJbSair() {
		return jbSair;
	}

	public void setJbSair(JButton jbSair) {
		this.jbSair = jbSair;
	}

	public JButton getJbListarJogadores() {
		return jbListarJogadores;
	}

	public void setJbListarJogadores(JButton jbListarJogadores) {
		this.jbListarJogadores = jbListarJogadores;
	}

	public JButton getJbMensagens() {
		return jbMensagens;
	}

	public void setJbMensagens(JButton jbMensagens) {
		this.jbMensagens = jbMensagens;
	}

	public JButton getJbHistoricoDeJogos() {
		return jbHistoricoDeJogos;
	}

	public void setJbHistoricoDeJogos(JButton jbHistoricoDeJogos) {
		this.jbHistoricoDeJogos = jbHistoricoDeJogos;
	}

	public JButton getJbNovoJogoComputador() {
		return jbNovoJogoComputador;
	}

	public void setJbNovoJogoMaquina(JButton jbNovoJogoComputador) {
		this.jbNovoJogoComputador = jbNovoJogoComputador;
	}

	public JButton getJbEditarDados() {
		return jbEditarDados;
	}

	public void setJbEditarDados(JButton jbEditarDados) {
		this.jbEditarDados = jbEditarDados;
	}

	public JButton getJbExcluirConta() {
		return jbExcluirConta;
	}

	public void setJbExcluirConta(JButton jbExcluirConta) {
		this.jbExcluirConta = jbExcluirConta;
	}

	public Jogador getJogadorLogado() {
		return jogadorLogado;
	}

	public void setJogadorLogado(Jogador jogadorLogado) {
		this.jogadorLogado = jogadorLogado;
	}

	public void setJbNovoJogoComputador(JButton jbNovoJogoComputador) {
		this.jbNovoJogoComputador = jbNovoJogoComputador;
	}

}
