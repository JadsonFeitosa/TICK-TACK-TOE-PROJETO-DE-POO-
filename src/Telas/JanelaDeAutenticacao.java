package Telas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.itextpdf.text.Font;

import Exceptions.AutenticacaoException;
import Exceptions.RecuperarJogadorException;
import Exceptions.SenhaInvalidaException;
import Exceptions.ValidarEscritaEmailException;
import Imagens.IconesPadrao;
import Principais.Jogador;

public class JanelaDeAutenticacao extends JanelaPadrao {
	
	private JButton jbEntrar;
	private JButton jbCadastrar;
	
	private JTextField jtEmailLogin;
	private JTextField jtEmailCadastro;
	
	private JPasswordField senhaEntrar;
	private JPasswordField senhaCadastrar;
	
	private JLabel[] icones = new JLabel[4];
	
	private static int jaEntrouAqui;
	
	private JPanel painelLogin = new JPanel();
	
	public JanelaDeAutenticacao(JanelaPadrao telaPadrao) {
		super("Tick-Tack-Toe",630,310);
		
		baixarERecuperarCentral();
		subirCentralAddListener();
		
		acoes();
		
		painelLogin.setSize(300, 310);
		painelLogin.setLayout(null);
		painelLogin.setBackground(getContentPane().getBackground());
		add(painelLogin);
		
		repaint();
		
		setLocationRelativeTo(telaPadrao);
		setVisible(true);
	}
	
	private void acoes() {
		adcIcones();
		adcLabels();
		adcTextField();
		adcBotoes();
	}
	
	private void adcLabels() {
		JLabelPadrao("Acessar minha Conta","OCR A Extended",Font.BOLD,21, 18, 35, 290, 33);
		JLabelPadrao("Criar uma Conta","OCR A Extended",Font.BOLD,21, 353, 35, 261, 33);
		
		JLabel recuperarConta = JLabelPadrao("Recuperar conta","OCR A Extended",Font.BOLD, 15, 115, 228, 261, 33);
		recuperarConta.addMouseListener(new OuvinteLabelRecuperarConta(this));
		
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBounds(311, 31, 2, 230);
		label.setBackground(Color.WHITE);
		this.add(label);
	}
	
	private void adcBotoes() {
		jbEntrar = JBPadrao("Entrar", 14, 228, 87, 32, "Entre no seu perfil");
		jbEntrar.addActionListener(new OuvinteBotaoEntrar(this));
		
		jbCadastrar = JBPadrao("Cadastrar",356,227,135,32, "Clique aqui para criar uma nova conta.");
		jbCadastrar.addActionListener(new OuvinteBotaoCadastrar(this));
	}
	
	private void adcIcones() {
		icones[0] = adcIcones(IconesPadrao.iconeUsuario, 14, 90, 35, 39);
		icones[1] = adcIcones(IconesPadrao.iconeEmail, 356, 90, 38, 39);
		icones[2] = adcIcones(IconesPadrao.iconeSenha, 14, 166, 35, 39);
		icones[3] = adcIcones(IconesPadrao.iconeSenha, 356, 166, 35, 39);
	}
	
	private void adcTextField() {
		jtEmailLogin = JTextFielPadrao(49, 90, 211, 39, painelLogin);
		senhaEntrar = JPassowordFielPadrao(49, 166, 211, 39, painelLogin);
		
		jtEmailCadastro = JTextFielPadrao(394, 90, 211, 39, (JPanel) getContentPane());
		jtEmailCadastro.addFocusListener(new OuvinteTextFieldCadastrar());
		
		senhaCadastrar = JPassowordFielPadrao(391, 166, 214, 39, (JPanel) getContentPane());
		senhaCadastrar.addFocusListener(new OuvinteTextFieldCadastrar());
	}
	
	private class OuvinteLabelRecuperarConta implements MouseListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteLabelRecuperarConta(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}

		public void mouseClicked(MouseEvent arg0) {
			String usuario = JOptionPane.showInputDialog(telaPadraoOuvinte, "Qual seu nome de usuário ?", "Sistema", JOptionPane.QUESTION_MESSAGE);
			if(usuario != null) {
				JOptionPane.showMessageDialog(telaPadraoOuvinte, central.recuperarSenha(usuario), "Sistema", JOptionPane.WARNING_MESSAGE);
			}
		}

		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	private class OuvinteBotaoEntrar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoEntrar(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			try {
				Jogador jogador = central.recuperarJogador(jtEmailLogin.getText());
				central.autenticacao(jogador, senhaEntrar.getText());
				new JanelaDePerfil(jogador, telaPadraoOuvinte);
				dispose();
			} catch (RecuperarJogadorException | AutenticacaoException e) {
				JOptionPane.showMessageDialog(telaPadraoOuvinte, e.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
			} 
		}
	}
	
	private class OuvinteBotaoCadastrar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoCadastrar(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			try {
				getValidacoes().validarEscritaEmail(jtEmailCadastro.getText());
				getValidacoes().senhaInvalida(senhaCadastrar.getText());
				new JanelaDeCadastro(jtEmailCadastro.getText(), senhaCadastrar.getText(), telaPadraoOuvinte);
				dispose();
			}catch(ValidarEscritaEmailException | SenhaInvalidaException e) {
				JOptionPane.showMessageDialog(telaPadraoOuvinte, e.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class OuvinteTextFieldCadastrar implements FocusListener {
		public void focusGained(FocusEvent e) {
			JTextField objeto = (JTextField)e.getSource();
			objeto.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			
			if(objeto == jtEmailCadastro) {
				icones[1].setBorder(BorderFactory.createLineBorder(Color.GREEN));
			}else if(objeto == senhaCadastrar) {
				icones[3].setBorder(BorderFactory.createLineBorder(Color.GREEN));
			}
		}

		public void focusLost(FocusEvent e) {
			JTextField objeto = (JTextField)e.getSource();
			try {
				if(objeto == jtEmailCadastro) {
					getValidacoes().validarEscritaEmail(jtEmailCadastro.getText());
				}else if(objeto == senhaCadastrar) {
					getValidacoes().senhaInvalida(senhaCadastrar.getText());
				}
				objeto.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			} catch(ValidarEscritaEmailException | SenhaInvalidaException k) {
				objeto.setBorder(BorderFactory.createLineBorder(Color.RED));
				if(objeto == jtEmailCadastro) {
					icones[1].setBorder(BorderFactory.createLineBorder(Color.RED));
				}else if(objeto == senhaCadastrar) {
					icones[3].setBorder(BorderFactory.createLineBorder(Color.RED));
				}
			}
		}
	}
	
	private void baixarERecuperarCentral() {
		if(jaEntrouAqui == 0) {
			try {
				persistencia.baixarCentral("central.xml");
				central = persistencia.recuperarCentral("central.xml");
			}catch(Exception e) {
				JOptionPane.showConfirmDialog(this, e.getMessage(), "Aviso!", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}
			jaEntrouAqui++;
		}
	}
	
	public JButton getJbEntrar() {
		return jbEntrar;
	}
	public void setJbEntrar(JButton jbEntrar) {
		this.jbEntrar = jbEntrar;
	}
	public JButton getJbCadastrar() {
		return jbCadastrar;
	}
	public void setJbCadastrar(JButton jbCadastrar) {
		this.jbCadastrar = jbCadastrar;
	}
	public JTextField getJtEmailLogin() {
		return jtEmailLogin;
	}
	public void setJtEmailLogin(JTextField jtEmailLogin) {
		this.jtEmailLogin = jtEmailLogin;
	}
	public JTextField getJtEmailCadastro() {
		return jtEmailCadastro;
	}
	public void setJtEmailCadastro(JTextField jtEmailCadastro) {
		this.jtEmailCadastro = jtEmailCadastro;
	}
	public JPasswordField getSenhaEntrar() {
		return senhaEntrar;
	}
	public void setSenhaEntrar(JPasswordField senhaEntrar) {
		this.senhaEntrar = senhaEntrar;
	}
	public JPasswordField getSenhaCadastrar() {
		return senhaCadastrar;
	}
	public void setSenhaCadastrar(JPasswordField senhaCadastrar) {
		this.senhaCadastrar = senhaCadastrar;
	}
	
	public JLabel[] getIcones() {
		return icones;
	}

	public void setIcones(JLabel[] icones) {
		this.icones = icones;
	}

	public static void main(String[] args) {
		new JanelaDeAutenticacao(null);
	}
}
