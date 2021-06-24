package Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.itextpdf.text.Font;

import Exceptions.AutenticacaoException;
import Imagens.IconesPadrao;
import Ouvintes.OuvinteBotaoFecharJanela;
import Principais.Jogador;

public class JanelaDeAutenticacaoComSenha extends JanelaPadrao {
	
	private JPasswordField senha;
	
	private JButton acessar;
	private JButton cancelar;
	
	private Jogador jogadorLogado;
	private Jogador jogadorSelecionado;
	
	private JanelaPadrao telaDetalharPerfil;

	public JanelaDeAutenticacaoComSenha(Jogador jogadorLogado, Jogador jogadorSelecionado, JanelaPadrao telaPadrao) {
		super("Perfil", 436, 230);
		
		this.jogadorLogado = jogadorLogado;
		this.jogadorSelecionado = jogadorSelecionado;
		this.telaDetalharPerfil = telaPadrao;
		
		acoes();
		repaint();
		
		setLocationRelativeTo(telaPadrao);
		setDefaultCloseOperation(1);
		setVisible(true);
	}
	
	private void acoes() {
		adcLabel();
		adcJPassowordField();
		adcBotoes();
		adcIcones();
	}
	
	private void adcIcones() {
		adcIcones(IconesPadrao.iconeSenha, 30, 90, 35, 39);
	}

	private void adcJPassowordField() {
		senha = JPassowordFielPadrao(65, 90, 310, 39, (JPanel) getContentPane());
	}

	private void adcBotoes() {
		acessar = JBPadrao("Acessar", 180, 150, 90, 30, "Confirmar Senha!");
		acessar.addActionListener(new OuvinteBotaoAcessar(this));
		
		cancelar = JBPadrao("Cancelar", 285, 150, 90, 30, "Cancelar!");
		cancelar.addActionListener(new OuvinteBotaoFecharJanela(this));
	}

	private void adcLabel() {
		JLabelPadrao(jogadorSelecionado.getUsuario() + ", entre com sua senha!","OCR A Extended",Font.BOLD,18, 30, 25, 400, 28);
	}
	
	
	private class OuvinteBotaoAcessar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoAcessar(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				central.autenticacao(jogadorSelecionado, senha.getText());
				new JanelaDeDesafio(jogadorLogado, jogadorSelecionado, telaPadraoOuvinte);
				telaDetalharPerfil.dispose();
				dispose();
			} catch (AutenticacaoException e1) {
				JOptionPane.showMessageDialog(telaPadraoOuvinte, e1.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	
	public JPasswordField getSenha() {
		return senha;
	}

	public void setSenha(JPasswordField senha) {
		this.senha = senha;
	}

	public JButton getAcessar() {
		return acessar;
	}

	public void setAcessar(JButton acessar) {
		this.acessar = acessar;
	}

	public JButton getCancelar() {
		return cancelar;
	}

	public void setCancelar(JButton cancelar) {
		this.cancelar = cancelar;
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

	public JanelaPadrao getTelaDetalharPerfil() {
		return telaDetalharPerfil;
	}

	public void setTelaDetalharPerfil(JanelaPadrao telaDetalharPerfil) {
		this.telaDetalharPerfil = telaDetalharPerfil;
	}
	
}
