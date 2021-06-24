package Telas;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.itextpdf.text.Font;

import Exceptions.CampoVazioIdadeException;
import Exceptions.CampoVazioLoginException;
import Exceptions.CampoVazioNomeException;
import Exceptions.CampoVazioProfissaoException;
import Exceptions.CodigoEmailInvalidoException;
import Exceptions.CpfInvalidException;
import Exceptions.EnviarEmailException;
import Exceptions.IdadeContemLetraException;
import Exceptions.IdadeInvalidException;
import Exceptions.JogadorDuplicadoException;
import Exceptions.NumeroInvalidoException;
import Principais.Jogador;

public class JanelaDeCadastro extends JanelaPadrao{
	
	private JTextField jtLogin;
	private JTextField jtNome;
	private JTextField jtProfissao;
	private JTextField jtIdade;
	private JTextField jtConfirmarEmail;
	
	private JFormattedTextField jtCPF;
	
	private JButton jbCadastrar;
	private JButton jbCancelar;
	private JButton jbConfirmarEmail;
	
	private JComboBox<String> cargo;
	
	private String[] sexo = {"Outro","Masculino", "Feminino"};
	private String email;
	private String senha;
	
	private int codigoGerado = getValidacoes().gerarCodigo();
	
	public JanelaDeCadastro(String email, String senha, JanelaPadrao telaPadrao) {
		super("Cadastro Tick-Tack-Toe", 435, 361);
		
		this.email = email;
		this.senha = senha;

		acoes();
		repaint();
		
		subirCentralAddListener();
		setLocationRelativeTo(telaPadrao);
		setVisible(true);
	}
	
	public void acoes() {
		adcLabel();
		adcTextField();
		adcBoxSexo();
		adcBotoes();
		
	}
	private void adcLabel() {
		JLabelPadrao("Cadastro Tick-Tack-Toe","OCR A Extended",Font.BOLD,20, 74, 25, 286, 30);
		JLabelPadrao("Login:","Arial",Font.NORMAL,14, 33, 80, 40, 24);
		JLabelPadrao("Sexo:","Arial",Font.NORMAL,14, 256, 80, 40, 24);
		JLabelPadrao("Nome:","Arial",Font.NORMAL,14, 33, 113, 40, 24);
		JLabelPadrao("CPF:","Arial",Font.NORMAL,14, 33, 146, 40, 24);
		JLabelPadrao("Idade:","Arial",Font.NORMAL,14, 261, 146, 40, 24);
		JLabelPadrao("Profissão:","Arial",Font.NORMAL,14, 33, 179, 63, 24);
		JLabelPadrao("Confirmar:","Arial",Font.NORMAL,14, 33, 212, 63, 24);
	}
	
	private void adcTextField() {
		jtLogin = JTextFielPadrao(76, 80, 170, 24, (JPanel) getContentPane());
		jtLogin.addFocusListener(new OuvinteTextField());
		
		jtNome = JTextFielPadrao(77, 113, 316, 24, (JPanel) getContentPane());
		jtNome.addFocusListener(new OuvinteTextField());
		
		jtCPF = JFormattedTextFieldPadrao(75, 146, 175, 24, "###.###.###-##");
		jtCPF.addFocusListener(new OuvinteTextField());
		
		jtIdade = JTextFielPadrao(310, 146, 82, 24, (JPanel) getContentPane());
		jtIdade.addFocusListener(new OuvinteTextField());
		
		jtProfissao = JTextFielPadrao(109, 179, 284, 24, (JPanel) getContentPane());
		jtProfissao.addFocusListener(new OuvinteTextField());
		
		jtConfirmarEmail = JTextFielPadrao(109, 212, 150, 24, (JPanel) getContentPane());
		jtConfirmarEmail.addFocusListener(new OuvinteTextField());
		jtConfirmarEmail.setText("Coloque o código aqui");
		jtConfirmarEmail.setEditable(false);
	}
	
	
	private void adcBoxSexo(){
		cargo = new JComboBox<String>(sexo);
		cargo.setBounds(302, 80, 90, 24);
		cargo.setBackground(new Color(143,188,143));  
		cargo.setForeground( Color.WHITE ); 
		add(cargo);
		repaint();
	}
	
	private void adcBotoes() {
		jbConfirmarEmail = JBPadrao("Enviar código", 272, 212, 120, 24, "Confirmação de e-mail.");
		jbConfirmarEmail.addActionListener(new OuvinteBotaoConfirmarEmail(this));
		
		jbCadastrar = JBPadrao("Cadastrar",170, 288, 130, 32, "Clique aqui para cadastrar.");
		jbCadastrar.addActionListener(new OuvinteBotaoCadastrar(this));
		jbCadastrar.setEnabled(false);
		
		jbCancelar = JBPadrao("Voltar", 322, 288, 90, 32, "Clique aqui para, cancelar e volta para tela inicial!");
		jbCancelar.addActionListener(new OuvinteBotaoVoltar(this));
	}
	
	private class OuvinteBotaoVoltar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoVoltar(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}
		
		public void actionPerformed(ActionEvent e) {
			new JanelaDeAutenticacao(telaPadraoOuvinte);
			dispose();
		}
	}
	
	private class OuvinteBotaoConfirmarEmail implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoConfirmarEmail(JanelaPadrao telaPadraoOuvinte) {
			this.telaPadraoOuvinte = telaPadraoOuvinte;
		}

		public void actionPerformed(ActionEvent e) {
			String opcao = e.getActionCommand();
			
			switch(opcao) {
			case "Enviar código":
				try {
					String msg = central.enviarEmail(email, "Código de confirmação de conta", Integer.toString(codigoGerado));
					jtConfirmarEmail.setText("");
					jtConfirmarEmail.setEditable(true);
					jbConfirmarEmail.setText("Confirmar");
					JOptionPane.showMessageDialog(telaPadraoOuvinte, msg + "\nCódigo enviado para o e-mail " + email, "Sistema", JOptionPane.WARNING_MESSAGE);
				} catch (EnviarEmailException e1) {
					JOptionPane.showMessageDialog(telaPadraoOuvinte, e1.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case "Confirmar":
				try {
					int codigoTentado = getValidacoes().numeroInvalido(jtConfirmarEmail.getText());
					getValidacoes().codigoEmailInvalido(codigoGerado, codigoTentado);
					jtConfirmarEmail.setEditable(false);
					jbConfirmarEmail.setEnabled(false);
					jbConfirmarEmail.setText("Código aceito!");
					jbCadastrar.setEnabled(true);
				} catch (NumeroInvalidoException | CodigoEmailInvalidoException e1) {
					JOptionPane.showMessageDialog(telaPadraoOuvinte, e1.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
				}
				break;
			}
			
		}
	}
	
	private class OuvinteTextField implements FocusListener {
		public void focusGained(FocusEvent arg0) {
			JTextField objeto = (JTextField)arg0.getSource();
			objeto.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		}

		public void focusLost(FocusEvent arg0) {
			JTextField objeto = (JTextField)arg0.getSource();
			
			try {
				if(objeto == jtLogin){
					getValidacoes().campoVazioLogin(jtLogin.getText());
					getValidacoes().jogadorDuplicado(jtLogin.getText(), central.getJogadoresCadastrados());
				}else if(objeto == jtNome) {
					getValidacoes().campoVazioNome(jtNome.getText());
				}else if(objeto == jtCPF) {
					getValidacoes().cpfInvalido(jtCPF.getText());
				}else if(objeto == jtIdade) {
					getValidacoes().idadeContemLetraException(jtIdade.getText());
					getValidacoes().campoVazioIdade(jtIdade.getText());
					getValidacoes().idadeInvalida(Integer.parseInt(jtIdade.getText()));
				}else if(objeto == jtProfissao) {
					getValidacoes().campoVazioProfissao(jtProfissao.getText());
				}else if(objeto == jtConfirmarEmail) {
					getValidacoes().numeroInvalido(jtConfirmarEmail.getText());
				}
				objeto.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			}catch(Exception e) {
				objeto.setBorder(BorderFactory.createLineBorder(Color.RED));
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
				
				getValidacoes().campoVazioLogin(jtLogin.getText());
				getValidacoes().campoVazioNome(jtNome.getText());
				getValidacoes().cpfInvalido(jtCPF.getText());
				getValidacoes().idadeContemLetraException(jtIdade.getText());
				getValidacoes().campoVazioIdade(jtIdade.getText());
				getValidacoes().idadeInvalida(Integer.parseInt(jtIdade.getText()));
				getValidacoes().campoVazioProfissao(jtProfissao.getText());
				
				getValidacoes().jogadorDuplicado(jtLogin.getText(), central.getJogadoresCadastrados());
				
				Jogador jogador = new Jogador(jtNome.getText(), jtLogin.getText(), senha, email, jtCPF.getText(),
						jtProfissao.getText(), (String)cargo.getSelectedItem(), Integer.parseInt(jtIdade.getText()));
				
				central.adicionarJogador(jogador);
				salvarCentral();
				JOptionPane.showMessageDialog(telaPadraoOuvinte, "Olá " + jogador.getNome() + ", você acabou de se cadastrar", "Sistema", JOptionPane.WARNING_MESSAGE);
				new JanelaDeAutenticacao(telaPadraoOuvinte);
				dispose();
				
			}catch(CampoVazioLoginException | CampoVazioNomeException | CpfInvalidException | IdadeContemLetraException | CampoVazioIdadeException
					| IdadeInvalidException | CampoVazioProfissaoException | JogadorDuplicadoException e) {
				JOptionPane.showMessageDialog(telaPadraoOuvinte, e.getMessage(), "Sistema", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public JTextField getJtLogin() {
		return jtLogin;
	}
	public void setJtLogin(JTextField jtLogin) {
		this.jtLogin = jtLogin;
	}
	public JTextField getJtNome() {
		return jtNome;
	}
	public void setJtNome(JTextField jtNome) {
		this.jtNome = jtNome;
	}

	public JTextField getJtProfissao() {
		return jtProfissao;
	}
	public void setJtProfissao(JTextField jtProfissao) {
		this.jtProfissao = jtProfissao;
	}
	public JTextField getJtIdade() {
		return jtIdade;
	}
	public void setJtIdade(JTextField jtIdade) {
		this.jtIdade = jtIdade;
	}
	public JButton getJbCadastrar() {
		return jbCadastrar;
	}
	public void setJbCadastrar(JButton jbCadastrar) {
		this.jbCadastrar = jbCadastrar;
	}
	public JButton getJbCancelar() {
		return jbCancelar;
	}
	public void setJbCancelar(JButton jbCancelar) {
		this.jbCancelar = jbCancelar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public JTextField getJtConfirmarEmail() {
		return jtConfirmarEmail;
	}

	public void setJtConfirmarEmail(JTextField jtConfirmarEmail) {
		this.jtConfirmarEmail = jtConfirmarEmail;
	}

	public JFormattedTextField getJtCPF() {
		return jtCPF;
	}

	public void setJtCPF(JFormattedTextField jtCPF) {
		this.jtCPF = jtCPF;
	}

	public JButton getJbConfirmarEmail() {
		return jbConfirmarEmail;
	}

	public void setJbConfirmarEmail(JButton jbConfirmarEmail) {
		this.jbConfirmarEmail = jbConfirmarEmail;
	}

	public JComboBox<String> getCargo() {
		return cargo;
	}

	public void setCargo(JComboBox<String> cargo) {
		this.cargo = cargo;
	}

	public String[] getSexo() {
		return sexo;
	}

	public void setSexo(String[] sexo) {
		this.sexo = sexo;
	}

	public int getCodigoGerado() {
		return codigoGerado;
	}

	public void setCodigoGerado(int codigoGerado) {
		this.codigoGerado = codigoGerado;
	}	
}
