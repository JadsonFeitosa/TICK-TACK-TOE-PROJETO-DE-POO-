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
import Principais.Partida;

public class JanelaListarPartidas extends JanelaPadrao{
	
	private DefaultTableModel modelo;
	private JTable tabela;
	
	private JButton jbDetalharJogo;
	private JButton jbVoltar;
	private JButton jbGerarPDF;
	
	private Jogador jogadorLogado;
	
	public JanelaListarPartidas(Jogador jogadorLogado, JanelaPadrao telaPadrao) {
		super("Listar Partidas", 590, 500);
		
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
		jbGerarPDF = JBPadrao("Detalhar Jogadas PDF",  14, 415, 230, 32, "Clique aqui para gerar um PDF.");
		jbGerarPDF.addActionListener(new OuvinteBotaoGerarPDF(this));
		
		jbDetalharJogo = JBPadrao("Ver jogadas", 250, 415, 125, 32, "Selecione um Jogador, e clique aqui para ver mais informações.");
		jbDetalharJogo.addActionListener(new OuvinteBotaoDetalharJogo(this));
		
		jbVoltar = JBPadrao("Voltar", 388, 415, 110, 32, "Clique aqui para voltar para o perfil.");
		jbVoltar.addActionListener(new OuvinteBotaoVoltarPerfil(jogadorLogado, this));

		
	}
	
	private class OuvinteBotaoGerarPDF implements ActionListener{
		private JanelaPadrao telaPadrao;
		
		public OuvinteBotaoGerarPDF(JanelaPadrao telaPadrao) {
			this.telaPadrao = telaPadrao;
		}
		public void actionPerformed(ActionEvent arg0) {
			int linha = tabela.getSelectedRow();
			if(linha == -1) {
				JOptionPane.showMessageDialog(telaPadrao, "Antes, selecione uma linha", "Sistema", JOptionPane.WARNING_MESSAGE);
			}else {
				central.gerarRelatorioPDF(jogadorLogado.getHistoricoPartidas().get(linha));
				JOptionPane.showMessageDialog(telaPadrao, "PDF gerado", "Sistema", JOptionPane.WARNING_MESSAGE);
			}
			
		}

	}
	private class OuvinteBotaoDetalharJogo implements ActionListener{
		private JanelaPadrao telaPadrao;
		
		
		public OuvinteBotaoDetalharJogo(JanelaPadrao telaPadrao) {
			super();
			this.telaPadrao = telaPadrao;
		}

		public void actionPerformed(ActionEvent e) {
			int linha = tabela.getSelectedRow();
			if(linha == -1) {
				JOptionPane.showMessageDialog(telaPadrao, "Antes, selecione uma linha.", "Sistema", JOptionPane.WARNING_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(telaPadrao, central.montarRelatorioConsole(jogadorLogado.getHistoricoPartidas().get(linha)), "Sistema", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	
	private void adcLabels() {
		JLabelPadrao("Histórico de Partidas","OCR A Extended",Font.BOLD,20, 200, 30, 250, 30);		

	}
	
	private void adicionarTabela() {
		modelo = new DefaultTableModel() {				
			public boolean isCellEditable(int row, int column) { 
				return false;
			}
		};
		
		modelo.addColumn("Partida N°");
		modelo.addColumn("Desafiante");
		modelo.addColumn("Desafiado");
		
		tabela = new JTable(modelo);
		tabela.setBackground(Color.white);			
		JScrollPane painel = new JScrollPane(tabela);
		painel.setBounds(15, 100, 560, 250);
		add(painel);
	}
	
	private void preencherModelo() {
		Integer posicao = 1;
		for(Partida jogos: jogadorLogado.getHistoricoPartidas()) {
			Object [] linhas = new Object[3];
			linhas[0] = posicao++;
			linhas[1] = jogos.getJogador1();
			linhas[2] = jogos.getJogador2();
			modelo.addRow(linhas);
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

	public JButton getJbDetalharJogo() {
		return jbDetalharJogo;
	}

	public void setJbDetalharJogo(JButton jbDetalharJogo) {
		this.jbDetalharJogo = jbDetalharJogo;
	}

	public JButton getJbVoltar() {
		return jbVoltar;
	}

	public void setJbVoltar(JButton jbVoltar) {
		this.jbVoltar = jbVoltar;
	}

	public JButton getJbGerarPDF() {
		return jbGerarPDF;
	}

	public void setJbGerarPDF(JButton jbGerarPDF) {
		this.jbGerarPDF = jbGerarPDF;
	}

	public Jogador getJogadorLogado() {
		return jogadorLogado;
	}

	public void setJogadorLogado(Jogador jogadorLogado) {
		this.jogadorLogado = jogadorLogado;
	}
	
}
