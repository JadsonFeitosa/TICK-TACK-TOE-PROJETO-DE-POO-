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
import Exceptions.ValidarEscritaEmailException;
import Ouvintes.OuvinteBotaoFecharJanela;
import Principais.Jogador;

public class JanelaEnviarEmailNaoCadastrado extends JanelaPadrao{
	
	private JTextField assunto;
	private JTextField email;
	private JTextArea descricao;
	
	private JButton cancelar;
	private JButton enviar;
	
	private Jogador jogadorLogado;
	
	public  JanelaEnviarEmailNaoCadastrado(Jogador jogadorLogado, JanelaPadrao JanelaPadrao) {
		super("Enviar e-mail", 435, 361);
		
		this.jogadorLogado = jogadorLogado;
		
		acoes();
		repaint();
		
		setLocationRelativeTo(JanelaPadrao);
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
		private JanelaPadrao JanelaPadraoOuvinte;
		
		public OuvinteBotaoEnviarEmail(JanelaPadrao JanelaPadraoOuvinte) {
			this.JanelaPadraoOuvinte = JanelaPadraoOuvinte;
		}
		public void actionPerformed(ActionEvent e) {
			if(email.getText() == null) {
				JOptionPane.showMessageDialog(JanelaPadraoOuvinte, "Insira o campo de e-mail.");
			}
			else{
				try {
					getValidacoes().validarEscritaEmail(email.getText());
					String msg = central.enviarEmail(email.getText(), assunto.getText(), descricao.getText() + "\n\nMensagem enviada por: " + jogadorLogado.getUsuario());
					JOptionPane.showConfirmDialog(JanelaPadraoOuvinte, msg, "Aviso!", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
					dispose();
				}catch (EnviarEmailException | ValidarEscritaEmailException e1) {
					JOptionPane.showConfirmDialog(JanelaPadraoOuvinte, e1.getMessage(), "Aviso!", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}//else
		}//metodo
		
	}
	
	private void adcJBotao() {
		enviar = JBPadrao("Enviar", 180, 288, 95, 32, "Clique aqui para enviar.");
		enviar.addActionListener(new OuvinteBotaoEnviarEmail(this));
		
		cancelar = JBPadrao("Cancelar", 290, 288, 95, 32, "Clique aqui para cancelar e voltar.");
		cancelar.addActionListener(new OuvinteBotaoFecharJanela(this));
		
	}
	private void adcLabel() {
		JLabelPadrao("Enviar e-mail","OCR A Extended",Font.BOLD,20, 139, 25, 176, 30);
		JLabelPadrao("E-mail","Arial", Font.NORMAL, 14, 15, 75, 55, 24);
		JLabelPadrao("Assunto","Arial", Font.NORMAL, 14, 15, 120, 55, 24);
		JLabelPadrao("Mensagem","Arial", Font.NORMAL, 14, 15, 170, 80, 24);
		
	}
	
	private void adcTextField() {
		email = JTextFielPadrao(75, 75, 310, 24, (JPanel) getContentPane());
		assunto = JTextFielPadrao(75, 120, 310, 24, (JPanel) getContentPane());
	}
	
	private void adcTextArea() {
		descricao = JTextAreaPadrao(95, 170, 290, 100);
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
}
