package Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.itextpdf.text.Font;

import Exceptions.EnviarEmailException;
import Ouvintes.OuvinteBotaoFecharJanela;
import Principais.Jogador;

public class JanelaEnviarEmail extends JanelaPadrao{
	
	private JTextField assunto;
	private JTextArea descricao;
	
	private JLabel label;
	
	private JButton cancelar;
	private JButton enviar;
	
	private Jogador jogadorLogado;
	private Jogador jogadorSelecionado;
	
	public JanelaEnviarEmail(Jogador jogadorLogado, Jogador jogadorSelecionado, JanelaPadrao telaPadrao) {
		super("Enviar e-mail", 435, 361);
		
		this.jogadorLogado = jogadorLogado;
		this.jogadorSelecionado = jogadorSelecionado;
		
		acoes();
		repaint();
		
		setLocationRelativeTo(telaPadrao);
		setDefaultCloseOperation(1);
		setVisible(true);
	}
	
	private void acoes() {
		adcLabel();
		adcTextField();
		adcTextArea();
		adcJBotao();
	}
	
	private class OuvinteBotaoEnviarEmail implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoEnviarEmail(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		public void actionPerformed(ActionEvent e) {
			try {
				String msg = central.enviarEmail(jogadorSelecionado.getEmail(), assunto.getText(), descricao.getText() + "\n\nMensagem enviada por: " + jogadorLogado.getUsuario());
				JOptionPane.showMessageDialog(telaPadraoOuvinte, msg, "Sistema", JOptionPane.WARNING_MESSAGE);
				dispose();
			} catch (EnviarEmailException e1) {
				JOptionPane.showMessageDialog(telaPadraoOuvinte, e1.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	
	private void adcJBotao() {
		enviar = JBPadrao("Enviar", 180, 288, 95, 32, "Clique aqui para enviar.");
		enviar.addActionListener(new OuvinteBotaoEnviarEmail(this));
		
		cancelar = JBPadrao("Cancelar", 290, 288, 95, 32, "Clique aqui para cancelar e voltar.");
		cancelar.addActionListener(new OuvinteBotaoFecharJanela(this));
		
	}
	private void adcLabel() {
		label = JLabelPadrao("Enviar e-mail","OCR A Extended",Font.BOLD,20, 139, 25, 156, 30);
		label = JLabelPadrao("Assunto","Arial", Font.NORMAL, 14, 15, 80, 55, 24);
		label = JLabelPadrao("Mensagem","Arial", Font.NORMAL, 14, 15, 120, 69, 24);
		
	}
	
	private void adcTextField() {
		assunto = JTextFielPadrao(75, 80, 310, 24, (JPanel) getContentPane());
		
	}
	
	private void adcTextArea() {
		descricao = JTextAreaPadrao(40, 150, 348, 120);
		
	}
	
	public JTextField getAssunto() {
		return assunto;
	}
	public void setAssunto(JTextField assunto) {
		this.assunto = assunto;
	}
	public JTextArea getDescricao() {
		return descricao;
	}
	public void setDescricao(JTextArea descricao) {
		this.descricao = descricao;
	}
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public JButton getCancelar() {
		return cancelar;
	}
	public void setCancelar(JButton cancelar) {
		this.cancelar = cancelar;
	}
	public JButton getEnviar() {
		return enviar;
	}
	public void setEnviar(JButton enviar) {
		this.enviar = enviar;
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
	
}
