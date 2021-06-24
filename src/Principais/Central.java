package Principais;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Exceptions.*;

public class Central {
	private ArrayList<Jogador> jogadoresCadastrados = new ArrayList<Jogador>();
	private ArrayList<Partida> historicoPartidas = new ArrayList<Partida>();
	
    public void criarTabelaEPreencher(Partida partida, Document pdf) throws DocumentException {
    	
        PdfPTable jogadores = new PdfPTable(1);
        
        jogadores.addCell(partida.getJogador1() + " x " + partida.getJogador2());
        pdf.add(jogadores);
  
        PdfPTable table = new PdfPTable(3);
        
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				table.addCell(partida.getTabuleiroFinal()[i][j]);
			}
		}
        pdf.add(table);
        
        PdfPTable jogada = new PdfPTable(1);
        for (int i = 0; i < partida.getHistoricoJogadas().size(); i++) {
        	jogada.addCell(i+1 + "° jogada: " + partida.getHistoricoJogador().get(i) + " - Linha: " + (partida.getHistoricoJogadas().get(i)[0]+1)
        			 + " - Coluna: " + (partida.getHistoricoJogadas().get(i)[1]+1));
		}
        pdf.add(jogada);
    }
    
	public void gerarRelatorioPDF(Partida partida) {
        Document pdf = new Document();
        try {
            PdfWriter.getInstance(pdf, new FileOutputStream(partida.getJogador1() + "_X_" + partida.getJogador2() + ".pdf"));
            pdf.open();
            
            criarTabelaEPreencher(partida, pdf);
            
            pdf.add(new Paragraph("\n"));
            
            Paragraph vencedor = new Paragraph("Vencedor: " + partida.getVencedor());
            vencedor.setAlignment(Paragraph.ALIGN_CENTER);
            pdf.add(vencedor);
      
        } catch(DocumentException de) {
            System.err.println(de.getMessage());
        } catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        pdf.close();
	}
	
	public String montarRelatorioConsole(Partida partida) {
		String relatorio = new String();
		for (int i = 0; i < partida.getHistoricoJogadas().size(); i++) {
			relatorio += partida.getHistoricoJogador().get(i) + " jogou na linha " + (partida.getHistoricoJogadas().get(i)[0]+1) + " coluna " + (partida.getHistoricoJogadas().get(i)[1]+1) + "\n";
		}
		return relatorio + "\nVencedor: " + partida.getVencedor();
	}
	
	public void excluirMensagem(Jogador jogadorLogado, int indice) {
		jogadorLogado.getMensagensRecebidas().remove(indice);
	}
	
	public void ranquePorPontos() {
		Collections.sort(jogadoresCadastrados);
		Collections.sort(jogadoresCadastrados, new Comparator<Jogador>() {
			public int compare(Jogador o1, Jogador o2) {
				return (int)(Math.floor(o2.getTotalPontos()) - Math.ceil(o1.getTotalPontos()));
			}
		});
	}
	
	public void ranquePorVitorias() {
		Collections.sort(jogadoresCadastrados);
		Collections.sort(jogadoresCadastrados, new Comparator<Jogador>() {
			public int compare(Jogador o1, Jogador o2) {
				return o2.getTotalVitorias() - o1.getTotalVitorias();
			}
		});
	}
	
	public void ordemCadastroOriginal() {
		Collections.sort(jogadoresCadastrados); 
		Collections.reverse(jogadoresCadastrados);
	}
	
	public void autenticacao(Jogador jogador, String senha) throws AutenticacaoException {
		if(!jogador.getSenha().equals(senha)) {
			throw new AutenticacaoException("Senha incorreta.");
		}
	}
	
	public void adicionarJogador(Jogador jogador) {
		jogadoresCadastrados.add(jogador);
	}
	
	public void adicionarPartida(Partida partida) {
		historicoPartidas.add(partida);
	}
	
	public void excluirJogador(Jogador jogador) {
		jogadoresCadastrados.remove(jogador);
	}
	
	public void enviarMensagemLocal(Jogador origem, Jogador destino, String assunto, String mensagem) throws EnviarMensagemLocalException {
		if(assunto.length() == 0) {
			throw new EnviarMensagemLocalException("Você não pode enviar uma mensagem sem assunto.");
		}
		destino.adicionarMensagem(origem, assunto, mensagem);
	}
	
	public String[] lerMensagemLocal(Jogador jogadorLogado, int indice) {
		return jogadorLogado.getMensagensRecebidas().get(indice);
	}
	
	public int recuperarIndice(String usuario) throws RecuperarJogadorException {
		for (int i = 0; i < jogadoresCadastrados.size(); i++) {
			if(jogadoresCadastrados.get(i).getUsuario().equals(usuario)){
				return i;
			}
		}
		throw new RecuperarJogadorException("Jogador não cadastrado.");
	}	
	
	public Jogador recuperarJogador(String usuario) throws RecuperarJogadorException {
		for (Jogador jogador : jogadoresCadastrados) {
			if(jogador.getUsuario().equals(usuario)){
				return jogador;
			}
		}
		
		throw new RecuperarJogadorException("Jogador não cadastrado.");
	}	
	
	public String recuperarSenha(String usuario) {
		try {
			Jogador jogador = recuperarJogador(usuario);
			return enviarEmail(jogador.getEmail(), "Recuperação de senha", "Olá " + jogador.getNome() + ", \n\nRecebemos uma solicitação de recuperação de senha.\n\nUsuário: " + jogador.getUsuario() + "\nSenha: "+ jogador.getSenha());
		}catch(RecuperarJogadorException | EnviarEmailException e) {
			return e.getMessage();
		}
	}
	
	public String enviarEmail(String email, String assunto, String mensagem) throws EnviarEmailException {
		if(assunto.length() == 0) {
			throw new EnviarEmailException("Você não pode enviar um e-mail sem assunto.");
		}
		
		final String usuario = "projeto.ads.p2@gmail.com";
		final String senha = "projetoP2";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(usuario, senha);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("projeto.ads.p2@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(assunto);
			message.setText(mensagem);
			Transport.send(message);

		} catch (MessagingException e) {
			throw new EnviarEmailException("Erro ao enviar e-mail.");
		}
		
		return "E-mail enviado.";
	}
	
	public String calcularVD(Jogador jogador) {
		DecimalFormat df = new DecimalFormat("###,##0.00");
		if(jogador.getTotalDerrotas() == 0) {
			float resultado = (float)jogador.getTotalVitorias()/1;
			return df.format(resultado);
		}
		float resultado = (float)jogador.getTotalVitorias()/(float)jogador.getTotalDerrotas();

		return df.format(resultado);
	}
	
	public ArrayList<Jogador> getJogadoresCadastrados() {
		return jogadoresCadastrados;
	}
	
	public void setJogadoresCadastrados(ArrayList<Jogador> jogadoresCadastrados) {
		this.jogadoresCadastrados = jogadoresCadastrados;
	}
	
	public ArrayList<Partida> getHistoricoPartidas() {
		return historicoPartidas;
	}
	
	public void setHistoricoPartidas(ArrayList<Partida> historicoPartidas) {
		this.historicoPartidas = historicoPartidas;
	}
	
}
