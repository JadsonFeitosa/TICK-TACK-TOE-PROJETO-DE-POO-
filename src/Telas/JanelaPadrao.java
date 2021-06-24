package Telas;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Imagens.IconesPadrao;
import Principais.Central;
import Principais.Persistencia;
import Validacoes.Validacoes;

public class JanelaPadrao extends JFrame{

		private JButton botao;
		private JLabel label;
		private JTextField texto;
		private JPasswordField senha;
		private JTextArea area;
		
		public static Persistencia persistencia = new Persistencia();
		public static Central central;
		private Validacoes validacoes = new Validacoes();
		
		public JanelaPadrao (String titulo, int largura, int altura) {
			setTitle(titulo);
			setLayout(null);
			setResizable(false);
			getContentPane().setBackground(new Color(47,79,79));
			setSize(largura, altura);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setIconImage(IconesPadrao.icone.getImage());
		}
		
		
		public JTextArea JTextAreaPadrao(int posix, int posiy, int largura, int altura) {
			area = new JTextArea();
			area.setBounds(posix, posiy, largura, altura);
			area.setLineWrap(true);
			area.setWrapStyleWord(true);
			area.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			add(area);
			return area;
			
		}
		public JButton JBPadrao(String nome, int posiX, int posiY,int largura, int altura, String descricao){
			botao = new JButton(nome);
			botao.setBounds(posiX, posiY, largura, altura);
			botao.setBackground(new Color(143,188,143));  
			botao.setForeground( Color.WHITE );  
			botao.setFont(new Font("OCR A Extended",Font.BOLD,16));
			botao.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			botao.setToolTipText(descricao);
			add(botao);
			return botao;
		}//JBPadrao

		public JLabel JLabelPadrao(String nome,String font, int estilo, int fontTam, int posix, int posiy, int largura, int altura){
			label = new JLabel(nome);
			label.setBounds(posix, posiy, largura, altura);
			label.setForeground( Color.WHITE );  
//			label.setForeground(Color.DARK_GRAY);
			label.setFont(new Font(font,estilo,fontTam));
			label.setBackground(new Color(241, 101, 68));
			add(label);
			return label;
		}//JlabelPadrao

		public JTextField JTextFielPadrao(int posix, int posiy, int largura, int altura, JPanel painel){
			texto = new JTextField();
			texto.setBounds(posix, posiy, largura, altura);
			texto.setBackground(Color.WHITE);
			texto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//			texto.setForeground(Color.DARK_GRAY);

			painel.add(texto);
			return texto;
		}//JTextFielPadrao

		public JPasswordField JPassowordFielPadrao(int posix, int posiy, int largura, int altura, JPanel painel){
			senha = new JPasswordField();
			senha.setBounds(posix, posiy, largura, altura);
			senha.setBackground(Color.WHITE);
			senha.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			painel.add(senha);
			return senha;
		}//JTextFielPadrao
		
		public JRadioButton JRadioButtonPadrao(String nome, int posix, int posiy, int largura, int altura) {
			JRadioButton box = new JRadioButton(nome);
			box.setBounds(posix, posiy, largura, altura);
			box.setOpaque(true);
			box.setBackground(new Color(47,79,79));
			
			box.setForeground(Color.WHITE);
			box.setBorder(BorderFactory.createLineBorder(new Color(47,79,79)));
			box.setFont(new Font("OCR A Extended", Font.BOLD, 14));
			
			add(box);
			return box;
		}
		
		public JLabel adcIcones(ImageIcon icone,int posiX, int posiY,int largura, int altura) {
			JLabel icone1 = new JLabel(icone, JLabel.CENTER);
			icone1.setBounds(posiX, posiY, largura, altura);
			this.add(icone1);
			return icone1;
		}
		
		public JFormattedTextField JFormattedTextFieldPadrao(int posix, int posiy, int largura, int altura, String string) {
			JFormattedTextField cpf = null;
			try {
				MaskFormatter mascara = new MaskFormatter(string);
				cpf = new JFormattedTextField(mascara); 
				cpf.setBounds(posix, posiy, largura, altura);
				cpf.setBackground(Color.WHITE);
				cpf.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				add(cpf);
			}catch(Exception e) {
				
			}
			return cpf;
		}
		
		public void subirCentralAddListener() {
			addWindowListener(new OuvinteJanela());
		}
		
		private class OuvinteJanela extends WindowAdapter {
			public void windowClosing(java.awt.event.WindowEvent e) { 
				salvarCentral();
				setVisible(false);
				persistencia.subirCentral("central.xml");
			}
		}
		
		public void salvarCentral() {
			persistencia.salvarCentral(central, "central.xml");
		}

		public Validacoes getValidacoes() {
			return validacoes;
		}
		public void setValidacoes(Validacoes validacoes) {
			this.validacoes = validacoes;
		}

		public JButton getBotao() {
			return botao;
		}

		public void setBotao(JButton botao) {
			this.botao = botao;
		}

		public JLabel getLabel() {
			return label;
		}

		public void setLabel(JLabel label) {
			this.label = label;
		}

		public JTextField getTexto() {
			return texto;
		}

		public void setTexto(JTextField texto) {
			this.texto = texto;
		}

		public JPasswordField getSenha() {
			return senha;
		}

		public void setSenha(JPasswordField senha) {
			this.senha = senha;
		}

		public JTextArea getArea() {
			return area;
		}

		public void setArea(JTextArea area) {
			this.area = area;
		}

		public static Persistencia getPersistencia() {
			return persistencia;
		}

		public static void setPersistencia(Persistencia persistencia) {
			JanelaPadrao.persistencia = persistencia;
		}

		public static Central getCentral() {
			return central;
		}

		public static void setCentral(Central central) {
			JanelaPadrao.central = central;
		}

		
}//classe
