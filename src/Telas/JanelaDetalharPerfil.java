package Telas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Font;

import Principais.Jogador;

public class JanelaDetalharPerfil extends JanelaPadrao {
	
	private JButton jbDesafiar;
	private JButton jbMensagem;
	private JButton jbEmail;
	private JButton jbVoltar;
		
	private Jogador jogadorLogado;
	private Jogador jogadorSelecionado;
	
	DefaultTableModel modelo;
	JTable tabela = new JTable(modelo);
	
	public JanelaDetalharPerfil(Jogador jogadorLogado, Jogador jogadorSelecionado, JanelaPadrao telaPadrao) {
		super("Perfil de " + jogadorSelecionado.getNome(), 500, 300);
		
		this.jogadorLogado = jogadorLogado;
		this.jogadorSelecionado = jogadorSelecionado;
		
		acoes();
		
		subirCentralAddListener();
		setLocationRelativeTo(telaPadrao);
		setVisible(true);
		repaint();
	}
	
	private void acoes() {
		adicionarTabela();
		adcLabels();
		adcBotoes();
	}

	private void adcBotoes() {
		jbMensagem = JBPadrao("Mensagem", 187, 215, 110, 32, "Clique aqui para enviar mensagem local.");
		jbMensagem.addActionListener(new OuvinteBotaoLocal(this));
		
		jbEmail = JBPadrao("E-mail",  325, 215, 100, 32, "Clique aqui para enviar um e-mail.");
		jbEmail.addActionListener(new OuvinteBotaoEmail(this));
		
		jbDesafiar = JBPadrao("Desafiar",  64, 215, 100, 32, "Clique aqui para desafiar.");
		jbDesafiar.addActionListener(new OuvinteBotaoDesafio(this));
		
		jbVoltar = JBPadrao("Voltar",  385, 15, 85, 22, "Clique aqui para voltar a lista de jogadores.");
		jbVoltar.addActionListener(new OuvinteBotaoVoltar(this));
	}
	
	private void adcLabels() {
		JLabelPadrao(jogadorSelecionado.getUsuario(),"OCR A Extended",Font.BOLD,20, 50, 20, 150, 30);
	}

	public void adicionarTabela(){
		modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) { 
				return false;
			}
		};
		
		modelo.addColumn("Nome");
		modelo.addColumn("E-mail");
		modelo.addColumn("Profissão");
		modelo.addColumn("Idade");
		modelo.addColumn("Sexo");
				
		Object [] linhas = new Object[5];
		linhas[0] = jogadorSelecionado.getNome();
		linhas[1] = jogadorSelecionado.getEmail();
		linhas[2] = jogadorSelecionado.getProfissao();
		linhas[3] = jogadorSelecionado.getIdade();
		linhas[4] = jogadorSelecionado.getSexo();
		modelo.addRow(linhas);
		
		tabela = new JTable(modelo);
		tabela.setBackground(Color.white);		
		tabela.setEnabled(false);
		
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(14, 100, 472, 80);
		add(scroll);
	}
	
	private class OuvinteBotaoDesafio implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoDesafio(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaDeAutenticacaoComSenha(jogadorLogado, jogadorSelecionado, telaPadraoOuvinte);
		}
	}	
	
	private class OuvinteBotaoEmail implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoEmail(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaEnviarEmail(jogadorLogado, jogadorSelecionado, telaPadraoOuvinte);
		}
	}
	
	private class OuvinteBotaoLocal implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoLocal(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaEnviarMensagemLocal(jogadorLogado, jogadorSelecionado, telaPadraoOuvinte);
		}
	}
	
	private class OuvinteBotaoVoltar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoVoltar(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaListarJogador(jogadorLogado, telaPadraoOuvinte);
			dispose();
		}
	}

	public JButton getJbDesafiar() {
		return jbDesafiar;
	}

	public void setJbDesafiar(JButton jbDesafiar) {
		this.jbDesafiar = jbDesafiar;
	}

	public JButton getJbMensagem() {
		return jbMensagem;
	}

	public void setJbMensagem(JButton jbMensagem) {
		this.jbMensagem = jbMensagem;
	}

	public JButton getJbEmail() {
		return jbEmail;
	}

	public void setJbEmail(JButton jbEmail) {
		this.jbEmail = jbEmail;
	}

	public JButton getJbVoltar() {
		return jbVoltar;
	}

	public void setJbVoltar(JButton jbVoltar) {
		this.jbVoltar = jbVoltar;
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

	public DefaultTableModel getModelo() {
		return modelo;
	}

	public void setModelo(DefaultTableModel modelo) {
		this.modelo = modelo;
	}

	public JTable getTabela() {
		return tabela;
	}

	public void setTabela(JTable tabela) {
		this.tabela = tabela;
	}
	
}
