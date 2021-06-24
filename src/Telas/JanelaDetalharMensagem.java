package Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.itextpdf.text.Font;

import Exceptions.RecuperarJogadorException;
import Principais.Jogador;

public class JanelaDetalharMensagem extends JanelaPadrao{
	
	private JButton jbResponder;
	private JTextArea mensagem;
	private JTextField assunto;

	private Jogador jogadorLogado;
	
	private String[] mensangemCompleta;
	
	public JanelaDetalharMensagem(Jogador jogadorLogado, JanelaPadrao telaPadrao, int indiceClicado) {
		super("Mensagem", 435, 361);
		
		this.jogadorLogado = jogadorLogado;
		this.mensangemCompleta = central.lerMensagemLocal(jogadorLogado, indiceClicado);
		
		acoes();
		repaint();
		
		setLocationRelativeTo(telaPadrao);
		setDefaultCloseOperation(1);
		setVisible(true);
	}
	
	private void acoes() {
		adcLabel();
		adcBotao();
	}
		
	private void adcLabel() {
		JLabelPadrao("Mensagem","OCR A Extended", Font.BOLD,20, 150, 20, 117, 30);
		JLabelPadrao("Assunto","Arial", Font.BOLD,14, 14, 50, 122, 30);
		JLabelPadrao("Mensagem","Arial", Font.BOLD,14, 14, 115, 117, 30);
		
		assunto = JTextFielPadrao(14, 85, 395, 30, (JPanel) getContentPane());
		assunto.setText(mensangemCompleta[1]);
		assunto.setEditable(false);
		add(assunto);

		mensagem = JTextAreaPadrao(14, 150, 395, 120);
		mensagem.setText(mensangemCompleta[2]);
		mensagem.setEditable(false);
		add(mensagem);
	}
	
	private void adcBotao() {
		jbResponder = JBPadrao("Responder",  295, 290, 90, 32, "Clique aqui para responder a mensagem.");
		jbResponder.addActionListener(new OuvinteBotaoVoltar(this));
	}
	
	private class OuvinteBotaoVoltar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoVoltar(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				Jogador jogadorSelecionado = central.recuperarJogador(mensangemCompleta[0]);
				new JanelaEnviarMensagemLocal(jogadorLogado, jogadorSelecionado, telaPadraoOuvinte);
				dispose();
			} catch (RecuperarJogadorException e1) {
				JOptionPane.showMessageDialog(telaPadraoOuvinte, e1.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public JButton getJbResponder() {
		return jbResponder;
	}

	public void setJbResponder(JButton jbResponder) {
		this.jbResponder = jbResponder;
	}

	public JTextArea getMensagem() {
		return mensagem;
	}

	public void setMensagem(JTextArea mensagem) {
		this.mensagem = mensagem;
	}

	public JTextField getAssunto() {
		return assunto;
	}

	public void setAssunto(JTextField assunto) {
		this.assunto = assunto;
	}

	public Jogador getJogadorLogado() {
		return jogadorLogado;
	}

	public void setJogadorLogado(Jogador jogadorLogado) {
		this.jogadorLogado = jogadorLogado;
	}

	public String[] getMensangemCompleta() {
		return mensangemCompleta;
	}

	public void setMensangemCompleta(String[] mensangemCompleta) {
		this.mensangemCompleta = mensangemCompleta;
	}
	
}
