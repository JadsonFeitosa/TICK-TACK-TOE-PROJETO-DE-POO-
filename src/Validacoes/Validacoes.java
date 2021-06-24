package Validacoes;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.*;
import Principais.Jogador;

public class Validacoes {
	
	public void cpfInvalido(String cpf) throws CpfInvalidException {
		if(!cpf.matches("^[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$")) {
			throw new CpfInvalidException("CPF inválido");
		}
	}
	
	public void idadeInvalida(int idade) throws IdadeInvalidException{
		if(idade < 0 || idade > 150) {
			throw new IdadeInvalidException("Idade inválida.");
		}
	}
	
	public void idadeContemLetraException(String string) throws IdadeContemLetraException {
		try {
			Integer.parseInt(string);
		}catch(NumberFormatException e) {
			throw new IdadeContemLetraException("Idade não pode conter letras.");
		}
	}
	
	public int numeroInvalido(String string) throws NumeroInvalidoException {
		try {
			return Integer.parseInt(string);
		}catch(NumberFormatException e) {
			throw new NumeroInvalidoException("Formato do número inválido.");
		}
	}
	
	public void senhaInvalida(String senha) throws SenhaInvalidaException {
		int qtdNumeros = 0;
		int qtdLetras = 0;
		
		for (int i = 0; i < senha.length(); i++) {
			if(Character.toString(senha.charAt(i)).matches("^[0-9]$")) {
				qtdNumeros++;
			}else if(Character.toString(senha.charAt(i)).matches("^[a-zA-Z]$")) {
				qtdLetras++;
			}
		}
		if(qtdNumeros < 3 || qtdLetras < 3) {
			throw new SenhaInvalidaException("A senha precisa ter pelo menos 6 caracteres, sendo: três letras e três números.");
		}
	}

	public void jogadorDuplicado(String usuario, ArrayList<Jogador> jogadoresCadastrados) throws JogadorDuplicadoException {
		for (Jogador jogador : jogadoresCadastrados) {
			if(jogador.getUsuario().equals(usuario)){
				throw new JogadorDuplicadoException("Jogador já cadastrado.");
			}
		}
	}
	
	public int gerarCodigo() {
		return (int)(10000000 + Math.random() * (200000000 - 100000000 + 1));
	}
	
	public void codigoEmailInvalido(int codigoGerado, int codigoTentado) throws CodigoEmailInvalidoException {
		if(codigoGerado != codigoTentado) {
			throw new CodigoEmailInvalidoException("Código de confirmação incorreto.");
		}
	}
	
	public void validarEscritaEmail(String email) throws ValidarEscritaEmailException {
		String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
	        
		if(!matcher.matches()) {
			throw new ValidarEscritaEmailException("E-mail inválido.");
		}
	}

	public void campoVazioLogin(String string) throws CampoVazioLoginException {
		if(string.length() == 0) {
			throw new CampoVazioLoginException("Campo do login está vazio.");
		}
	}
	
	public void campoVazioNome(String string) throws CampoVazioNomeException {
		if(string.length() == 0) {
			throw new CampoVazioNomeException("Campo do nome está vazio.");
		}
	}
	
	public void campoVazioIdade(String string) throws CampoVazioIdadeException {
		if(string.length() == 0) {
			throw new CampoVazioIdadeException("Campo da idade está vazia.");
		}
	}
	
	public void campoVazioProfissao(String string) throws CampoVazioProfissaoException {
		if(string.length() == 0) {
			throw new CampoVazioProfissaoException("Campo da profissão está vazia.");
		}
	}
	
}
