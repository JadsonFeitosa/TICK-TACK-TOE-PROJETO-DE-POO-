package Telas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Font;

import Ouvintes.OuvinteBotaoVoltarPerfil;
import Principais.Jogador;

public class JanelaListarMensagens extends JanelaPadrao{
	private DefaultTableModel modelo;
	private JTable tabela;
	
	private JButton jbVoltar; 
	private JButton jbDeletar;
	private JButton jbVerMensagem;
	
	private Jogador jogadorLogado;
	
	public JanelaListarMensagens(Jogador jogadorLogado , JanelaPadrao telaPadrao) {
		super("Mensagens recebidas", 335, 410);
		
		this.jogadorLogado = jogadorLogado;
		
		acoes();
		repaint();
		
		subirCentralAddListener();
		setLocationRelativeTo(telaPadrao);
		setVisible(true);
	}
	
	private void acoes() {
		adcLabels();
		adcBotoes();
		adicionarTabela();
		preencherModelo();
	}
	
	private void adcBotoes() {
		jbVerMensagem = JBPadrao("Ler", 37, 330, 66, 32, "Selecione uma Mensagen, � clique aqui para ver a Mensagem.");
		jbVerMensagem.addActionListener(new OuvinteBotaoVer(this));
		
		jbDeletar = JBPadrao("Deletar", 115, 330, 95, 32, "Selecione uma Mensagen, � clique aqui para deletar.");
		jbDeletar.addActionListener(new OuvinteBotaoDeLetar(this));
		
		jbVoltar = JBPadrao("Voltar",  225, 330, 90, 32, "Clique aqui para voltar para o perfil.");
		jbVoltar.addActionListener(new OuvinteBotaoVoltarPerfil(jogadorLogado, this));
		
	}
	private void adcLabels() {
		JLabelPadrao("Mensagens Recebidas","OCR A Extended",Font.BOLD,20, 40, 20, 247, 30);
	}
	
	private void adicionarTabela() {
		modelo = new DefaultTableModel() {				
			public boolean isCellEditable(int row, int column) { 
				return false;
			}
		};
		
		modelo.addColumn("Usuário");
		modelo.addColumn("Assunto");
		
		tabela = new JTable(modelo);
		tabela.setBackground(Color.white);	
		
		JScrollPane painel = new JScrollPane(tabela);
		painel.setBounds(15, 80, 300, 230);
			
		add(painel);
		
	}
	
	private void preencherModelo() {
		for (String[] mensagens: jogadorLogado.getMensagensRecebidas() ) {
			Object[] linha = new Object[2];
			linha[0] = mensagens[0];
			linha[1] = mensagens[1];
			modelo.addRow(linha);
			
		}
	}
	
	private void limparModelo() {
		modelo.setNumRows(0);
	}
	
	private class OuvinteBotaoVer implements ActionListener {
		private JanelaPadrao telaPadrao;
		
		public OuvinteBotaoVer(JanelaPadrao telaPadrao) {
			this.telaPadrao = telaPadrao;
		}
		
		public void actionPerformed(ActionEvent e) {
			int linha = tabela.getSelectedRow();
			if(linha == -1) {
				JOptionPane.showMessageDialog(telaPadrao, "Antes, selecione uma linha.", "Sistema", JOptionPane.WARNING_MESSAGE);
			}else {
				new JanelaDetalharMensagem(jogadorLogado, telaPadrao, linha);
			}
		}
	}//class
	
	private class OuvinteBotaoDeLetar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoDeLetar(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			int linha = tabela.getSelectedRow();
			if(linha == -1){
				JOptionPane.showMessageDialog(telaPadraoOuvinte, "Antes, selecione uma linha.", "Sistema", JOptionPane.WARNING_MESSAGE);
			} else{
				central.excluirMensagem(jogadorLogado, linha); 
				limparModelo();
				preencherModelo();
				salvarCentral();
			}
		}
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

	public JButton getJbVoltar() {
		return jbVoltar;
	}

	public void setJbVoltar(JButton jbVoltar) {
		this.jbVoltar = jbVoltar;
	}

	public JButton getJbDeletar() {
		return jbDeletar;
	}

	public void setJbDeletar(JButton jbDeletar) {
		this.jbDeletar = jbDeletar;
	}

	public JButton getJbVerMensagem() {
		return jbVerMensagem;
	}

	public void setJbVerMensagem(JButton jbVerMensagem) {
		this.jbVerMensagem = jbVerMensagem;
	}

	public Jogador getJogadorLogado() {
		return jogadorLogado;
	}

	public void setJogadorLogado(Jogador jogadorLogado) {
		this.jogadorLogado = jogadorLogado;
	}
	
}
