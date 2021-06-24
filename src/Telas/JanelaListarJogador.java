package Telas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Font;

import Exceptions.JogadorIgualLogadoException;
import Exceptions.RecuperarJogadorException;
import Ouvintes.OuvinteBotaoVoltarPerfil;
import Principais.Jogador;

public class JanelaListarJogador extends JanelaPadrao{
	
	private DefaultTableModel modelo;
	private JTable tabela;
	
	private JTextField usuarioPesquisa;
	
	private JButton pesquisar;
	private JButton jbDetalharPerfil;
	private JButton jbVoltar;
	
	private ButtonGroup grupo;
	
	private JLabel qtdVitorias;
	private JLabel qtdDerrotas;
	
	private Jogador jogadorLogado;
	
	public JanelaListarJogador(Jogador jogadorLogado, JanelaPadrao telaPadrao) {
		super("Listar Jogadores", 590, 500);
		
		this.jogadorLogado = jogadorLogado;
		central.ranquePorPontos();
		
		acoes();
		repaint();
		
		subirCentralAddListener();
		setLocationRelativeTo(telaPadrao);
		setVisible(true);
	}
	
	private void acoes() {
		adcTextField();
		adcLabels();
		adcBotoes();
		adcRadioButton();
		adicionarTabela();
		preencherModelo(pesquisarJogadores());
	}
	
	private void adcTextField() {
		usuarioPesquisa = JTextFielPadrao(130, 65, 211, 25, (JPanel) getContentPane());
		usuarioPesquisa.addKeyListener(new OuvinteTextFieldPesquisar());
	}
	
	private void adcRadioButton() {
		JRadioButton ordemOriginal = JRadioButtonPadrao("Ordem original", 40, 30, 150, 30);
		ordemOriginal.addActionListener(new OuvinteRadioButtonRanques());
		
		JRadioButton ranquePontos = JRadioButtonPadrao("Ranque por ponto", 190, 30, 150, 30);
		ranquePontos.addActionListener(new OuvinteRadioButtonRanques());
		ranquePontos.setSelected(true);
		
		JRadioButton ranqueVitorias = JRadioButtonPadrao("Ranque por vitória", 350, 30, 150, 30);
		ranqueVitorias.addActionListener(new OuvinteRadioButtonRanques());
		
		grupo = new ButtonGroup();
		grupo.add(ordemOriginal);
		grupo.add(ranquePontos);
		grupo.add(ranqueVitorias);
		
		
	}
	private void adcBotoes() {
		pesquisar = JBPadrao("Pesquisar",  350, 65, 110, 25, "Clique aqui para pesquisar.");
		pesquisar.addActionListener(new OuvinteBotaoPesquisar());
		
		jbDetalharPerfil = JBPadrao("Detalhar", 170, 415, 105, 32, "Selecione um Jogador, e clique aqui para ver mais informações.");
		desativarBotaoDetalhar();
		jbDetalharPerfil.addActionListener(new OuvinteBotaoDetalhar(this));
		
		jbVoltar = JBPadrao("Voltar",  300, 415, 110, 32, "Clique aqui para voltar para o perfil.");
		
		jbVoltar.addActionListener(new OuvinteBotaoVoltarPerfil(jogadorLogado, this));
		
	}
		
	
	private void adcLabels() {
		JLabelPadrao("Jogadores","OCR A Extended",Font.BOLD,20, 245, 2, 117, 30);
		
		qtdVitorias = JLabelPadrao("Nº de Vitorias: N/D","OCR A Extended",Font.BOLD,14, 120, 370, 170, 33);
		qtdDerrotas = JLabelPadrao("Nº de Derrotas: N/D","OCR A Extended",Font.BOLD,14, 320, 370, 170, 33);

	}
	
	private void adicionarTabela() {
		modelo = new DefaultTableModel() {				
			public boolean isCellEditable(int row, int column) { 
				return false;
			}
		};
		
		modelo.addColumn("Posição");
		modelo.addColumn("Usuário");
		modelo.addColumn("Pontuação");
		modelo.addColumn("Vitórias");
		modelo.addColumn("Data de Cadastro");
		
		tabela = new JTable(modelo);
		tabela.setBackground(Color.white);	
		tabela.addMouseListener(new OuvinteMouseTabela());
		tabela.addKeyListener(new OuvinteTecladoTabela());
		
		JScrollPane painel = new JScrollPane(tabela);
		painel.setBounds(15, 100, 560, 250);
			
		add(painel);
		
	}
	
	private void preencherModelo(ArrayList<Jogador> jogadores) {
		Integer posicao = 1;
		for(Jogador jogador: jogadores) {
			Object [] linhas = new Object[5];
			linhas[0] = posicao++;
			linhas[1] = jogador.getUsuario();
			linhas[2] = jogador.getTotalPontos();
			linhas[3] = jogador.getTotalVitorias();
			linhas[4] = jogador.getDataCadastro();
			modelo.addRow(linhas);
			
		}
	}
	
	private void limparModelo() {
		modelo.setNumRows(0);
	}
	
	private void ativarBotaoDetalhar() {
		jbDetalharPerfil.setEnabled(true);
	}
	
	private void desativarBotaoDetalhar() {
		jbDetalharPerfil.setEnabled(false);
	}
	
	private void jogadorIgualLogado(Jogador jogadorSelecionado) throws JogadorIgualLogadoException {
		if(jogadorLogado == jogadorSelecionado) {
			desativarBotaoDetalhar();
			throw new JogadorIgualLogadoException("Você não pode selecionar você mesmo.");
		}
	}
	
	private void mudarEstadoItens() {
		try {
			String usuario = jogadorSelecionadoUsuario();
			Jogador jogadorSelecionado = central.recuperarJogador(usuario);
			
			qtdVitorias.setText("Nº de Vitorias: " + jogadorSelecionado.getTotalVitorias());
			qtdDerrotas.setText("Nº de Derrotas: " + jogadorSelecionado.getTotalDerrotas());
			
			ativarBotaoDetalhar();
			jogadorIgualLogado(jogadorSelecionado);
			
		} catch(JogadorIgualLogadoException e) {
			desativarBotaoDetalhar();
		} catch(RecuperarJogadorException | ArrayIndexOutOfBoundsException e) {}
			
	}
	
	private void instrucoesTabelaPesquisa() {
		desativarBotaoDetalhar();
		limparModelo();
		preencherModelo(pesquisarJogadores());
	}
	
	private ArrayList<Jogador> pesquisarJogadores(){
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
		for(Jogador jogador: central.getJogadoresCadastrados()) {
			if(jogador.getUsuario().contains(usuarioPesquisa.getText())) {
				jogadores.add(jogador);
			}
		}
		return jogadores;
	}
	
	private class OuvinteBotaoPesquisar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			instrucoesTabelaPesquisa();
		}
	}
	
	private class OuvinteRadioButtonRanques implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "Ordem original":
				central.ordemCadastroOriginal();
				break;
			case "Ranque por ponto":
				central.ranquePorPontos();
				break;
			case "Ranque por vitória":
				central.ranquePorVitorias();
				break;
			}
			instrucoesTabelaPesquisa();
		}
	}
	
	private class OuvinteBotaoDetalhar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoDetalhar(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				Jogador jogadorSelecionado = central.recuperarJogador(jogadorSelecionadoUsuario());
				jogadorIgualLogado(jogadorSelecionado);
				new JanelaDetalharPerfil(jogadorLogado, jogadorSelecionado, telaPadraoOuvinte);
				dispose();
			} catch (RecuperarJogadorException | JogadorIgualLogadoException e1) {
				JOptionPane.showMessageDialog(telaPadraoOuvinte, e1.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class OuvinteTecladoTabela implements KeyListener {
		public void keyReleased(KeyEvent arg0) {
			mudarEstadoItens();
		}
		
		public void keyPressed(KeyEvent arg0) {}
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	private class OuvinteMouseTabela implements MouseListener {
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() == 1) {
				mudarEstadoItens();
			}
		}
		
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}

	}
	
	private class OuvinteTextFieldPesquisar implements KeyListener {
		public void keyReleased(KeyEvent e) {
			if(usuarioPesquisa.getText().length() == 0) {
				instrucoesTabelaPesquisa();
			}
		}
		
		public void keyPressed(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
		
	}
	
	private String jogadorSelecionadoUsuario() {
		return tabela.getValueAt(tabela.getSelectedRow(), 1).toString();
	}
	
	public JButton getJbDetalharPerfil() {
		return jbDetalharPerfil;
	}
	public void setJbDetalharPerfil(JButton jbDetalharPerfil) {
		this.jbDetalharPerfil = jbDetalharPerfil;
	}
	public JButton getJbVoltarTelaInicial() {
		return jbVoltar;
	}
	public void setJbVoltarTelaInicial(JButton jbVoltarTelaInicial) {
		this.jbVoltar = jbVoltarTelaInicial;
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

	public JTextField getUsuarioPesquisa() {
		return usuarioPesquisa;
	}

	public void setUsuarioPesquisa(JTextField usuarioPesquisa) {
		this.usuarioPesquisa = usuarioPesquisa;
	}

	public JButton getPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(JButton pesquisar) {
		this.pesquisar = pesquisar;
	}

	public JButton getJbVoltar() {
		return jbVoltar;
	}

	public void setJbVoltar(JButton jbVoltar) {
		this.jbVoltar = jbVoltar;
	}

	public ButtonGroup getGrupo() {
		return grupo;
	}

	public void setGrupo(ButtonGroup grupo) {
		this.grupo = grupo;
	}

	public JLabel getQtdVitorias() {
		return qtdVitorias;
	}

	public void setQtdVitorias(JLabel qtdVitorias) {
		this.qtdVitorias = qtdVitorias;
	}

	public JLabel getQtdDerrotas() {
		return qtdDerrotas;
	}

	public void setQtdDerrotas(JLabel qtdDerrotas) {
		this.qtdDerrotas = qtdDerrotas;
	}

	public Jogador getJogadorLogado() {
		return jogadorLogado;
	}

	public void setJogadorLogado(Jogador jogadorLogado) {
		this.jogadorLogado = jogadorLogado;
	}
	
}