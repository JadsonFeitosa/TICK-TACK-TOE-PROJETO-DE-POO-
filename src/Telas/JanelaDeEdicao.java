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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.itextpdf.text.Font;

import Exceptions.CampoVazioIdadeException;
import Exceptions.CampoVazioLoginException;
import Exceptions.CampoVazioNomeException;
import Exceptions.CampoVazioProfissaoException;
import Exceptions.CpfInvalidException;
import Exceptions.IdadeContemLetraException;
import Exceptions.IdadeInvalidException;
import Exceptions.JogadorDuplicadoException;
import Exceptions.SenhaInvalidaException;
import Exceptions.ValidarEscritaEmailException;
import Ouvintes.OuvinteBotaoVoltarPerfil;
import Principais.Jogador;

public class JanelaDeEdicao extends JanelaPadrao{
	
	private JTextField jtLogin;
	private JTextField jtNome;
	private JTextField jtProfissao;
	private JTextField jtIdade;
	private JTextField jtEmail;
	
	private JFormattedTextField jtCPF;
	
	private JPasswordField jSenha;

	private JButton jbCadastrar;
	private JButton jbCancelar;
	
	private JComboBox<String> cargo;
	private String[] cargos = {"Outro","Masculino", "Feminino"};
	
	private Jogador jogadorLogado;
	
	public JanelaDeEdicao(Jogador jogadorLogado, JanelaPadrao telaPadrao) {
		super("Edição", 435, 361);
		
		this.jogadorLogado = jogadorLogado;
		
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
		JLabelPadrao("Editar dados Cadastrais","OCR A Extended",Font.BOLD,20, 70, 25, 300, 30);
		JLabelPadrao("Login:","Arial",Font.NORMAL,14, 33, 80, 40, 24);
		JLabelPadrao("Sexo:","Arial",Font.NORMAL,14, 256, 80, 40, 24);
		JLabelPadrao("Nome:","Arial",Font.NORMAL,14, 33, 113, 40, 24);
		JLabelPadrao("CPF:","Arial",Font.NORMAL,14, 33, 146, 40, 24);
		JLabelPadrao("Idade:","Arial",Font.NORMAL,14, 261, 146, 40, 24);
		JLabelPadrao("Profissão:","Arial",Font.NORMAL,14, 33, 179, 63, 24);
		JLabelPadrao("Email:","Arial",Font.NORMAL,14, 33, 212, 40, 24);
		JLabelPadrao("Nova Senha:","Arial",Font.NORMAL,14, 33, 242, 81, 24);
	}
	
	private void adcTextField() {
		jtLogin = JTextFielPadrao(76, 80, 170, 24, (JPanel) getContentPane());
		jtLogin.setText(jogadorLogado.getUsuario());
		jtLogin.addFocusListener(new OuvinteTextField());
		
		jtNome = JTextFielPadrao(77, 113, 316, 24, (JPanel) getContentPane());
		jtNome.setText(jogadorLogado.getNome());
		jtNome.addFocusListener(new OuvinteTextField());
		
		jtCPF = JFormattedTextFieldPadrao(75, 146, 175, 24, "###.###.###-##");
		jtCPF.setText(jogadorLogado.getCpf());
		jtCPF.addFocusListener(new OuvinteTextField());
		
		jtIdade = JTextFielPadrao(310, 145, 82, 24, (JPanel) getContentPane());
		jtIdade.setText(Integer.toString(jogadorLogado.getIdade()));
		jtIdade.addFocusListener(new OuvinteTextField());
		
		jtProfissao = JTextFielPadrao(109, 179, 284, 24, (JPanel) getContentPane());
		jtProfissao.setText(jogadorLogado.getProfissao());
		jtProfissao.addFocusListener(new OuvinteTextField());
		
		jtEmail = JTextFielPadrao(77, 212, 316, 24, (JPanel) getContentPane());
		jtEmail.setText(jogadorLogado.getEmail());
		jtEmail.addFocusListener(new OuvinteTextField());
		
		jSenha = JPassowordFielPadrao(118, 242, 276, 24, (JPanel) getContentPane());
		jSenha.setText(jogadorLogado.getSenha());
		jSenha.addFocusListener(new OuvinteTextField());
	}
	
	private void adcBoxSexo(){
		cargo = new JComboBox<String>(cargos);
		cargo.setBounds(302, 80, 90, 24);
		cargo.setBackground(new Color(143,188,143));  
		cargo.setForeground( Color.WHITE ); 
		cargo.setSelectedItem(jogadorLogado.getSexo());
		
		add(cargo);

	}
	
	private void adcBotoes() {
		jbCadastrar = JBPadrao("Editar",170, 288, 130, 32, "Clique aqui para cadastrar.");
		jbCadastrar.addActionListener(new OuvinteBotaoEditar(this));
		
		jbCancelar = JBPadrao("Voltar", 322, 288, 90, 32, "Clique aqui para, cancelar e volta para tela inicial!");
		jbCancelar.addActionListener(new OuvinteBotaoVoltarPerfil(jogadorLogado, this));
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
					if(!objeto.getText().equals(jogadorLogado.getUsuario())) {
						getValidacoes().jogadorDuplicado(jtLogin.getText(), central.getJogadoresCadastrados());
					}
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
				}else if(objeto == jtEmail) {
					getValidacoes().validarEscritaEmail(jtEmail.getText());
				}else if(objeto == jSenha) {
					getValidacoes().senhaInvalida(jSenha.getText());
				}
				objeto.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			}catch(Exception e) {
				
				objeto.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
		}
	}
	
	private class OuvinteBotaoEditar implements ActionListener {
		private JanelaPadrao telaPadraoOuvinte;
		
		public OuvinteBotaoEditar(JanelaPadrao telaPadraoOuvinte) {
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
				
				if(!jtLogin.getText().equals(jogadorLogado.getUsuario())) {
					getValidacoes().jogadorDuplicado(jtLogin.getText(), central.getJogadoresCadastrados());
				}
				
				getValidacoes().validarEscritaEmail(jtEmail.getText());
				getValidacoes().senhaInvalida(jSenha.getText());
				
				jogadorLogado.setUsuario(jtLogin.getText());
				jogadorLogado.setNome(jtNome.getText());
				jogadorLogado.setCpf(jtCPF.getText());
				jogadorLogado.setIdade(Integer.parseInt(jtIdade.getText()));
				jogadorLogado.setProfissao(jtProfissao.getText());
				jogadorLogado.setEmail(jtEmail.getText());
				jogadorLogado.setSenha(jSenha.getText());
				jogadorLogado.setSexo((String)cargo.getSelectedItem());
				
				
				salvarCentral();
				JOptionPane.showMessageDialog(telaPadraoOuvinte, "Dados alterados", "Sistema", JOptionPane.WARNING_MESSAGE);
				new JanelaDePerfil(jogadorLogado, telaPadraoOuvinte);
				dispose();
				
			}catch(CampoVazioLoginException | CampoVazioNomeException | CpfInvalidException | IdadeContemLetraException | CampoVazioIdadeException
					| IdadeInvalidException | CampoVazioProfissaoException | JogadorDuplicadoException | ValidarEscritaEmailException | SenhaInvalidaException e) {
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
	
	public JTextField getJtEmail() {
		return jtEmail;
	}

	public void setJtEmail(JTextField jtEmail) {
		this.jtEmail = jtEmail;
	}

	public JPasswordField getjSenha() {
		return jSenha;
	}

	public void setjSenha(JPasswordField jSenha) {
		this.jSenha = jSenha;
	}

	public JFormattedTextField getJtCPF() {
		return jtCPF;
	}

	public void setJtCPF(JFormattedTextField jtCPF) {
		this.jtCPF = jtCPF;
	}

	public JComboBox<String> getCargo() {
		return cargo;
	}

	public void setCargo(JComboBox<String> cargo) {
		this.cargo = cargo;
	}

	public String[] getCargos() {
		return cargos;
	}

	public void setCargos(String[] cargos) {
		this.cargos = cargos;
	}

	public Jogador getJogadorLogado() {
		return jogadorLogado;
	}

	public void setJogadorLogado(Jogador jogadorLogado) {
		this.jogadorLogado = jogadorLogado;
	}
	
}
