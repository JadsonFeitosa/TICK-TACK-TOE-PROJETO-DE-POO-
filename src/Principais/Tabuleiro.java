package Principais;

public class Tabuleiro {
	private String[][] matriz = new String[3][3];
	private Integer[] ultimaJogada = new Integer[2];
	private int qtdJogadas;
	
	private int[][] botaoX = {{0,0}, {0,1}, {1,0}, {2,1}, {0,0}, {1,0}, {1,2}, {0,0}, {1,1}, {0,2}, {1,1}, {2,1}, {0,1}, {1,0}, {1,2}, {0,2}, {0,0}, {0,2}, {0,0}, {2,0}, {0,0}, {0,2}, {0,1}, {0,1}, {0,1}, {2,0}};
	private int[][] botaoY = {{0,1}, {0,2}, {1,2}, {2,2}, {1,0}, {2,0}, {2,2}, {1,1}, {2,2}, {1,1}, {2,0}, {1,1}, {1,1}, {1,1}, {1,1}, {2,0}, {2,2}, {2,2}, {2,0}, {2,2}, {0,2}, {1,2}, {2,1}, {1,0}, {1,2}, {2,1}};
	private int[][] botaoF = {{0,2}, {0,0}, {1,1}, {2,0}, {2,0}, {0,0}, {0,2}, {2,2}, {0,0}, {2,0}, {0,2}, {0,1}, {2,1}, {1,2}, {1,0}, {1,1}, {1,1}, {1,2}, {1,0}, {2,1}, {0,1}, {2,2}, {1,1}, {1,1}, {1,1}, {2,2}};

	public Tabuleiro() {
		for(int i = 0; i < 3; i++) 
			for(int j = 0; j < 3; j++) {
				matriz[i][j] = "-";
		}
	}

	public void jogada(int linha, int coluna, String letra) {
		ultimaJogada[0] = linha;
		ultimaJogada[1] = coluna;
		matriz[linha][coluna] = letra;
	}
	
	public boolean empate() {
		qtdJogadas++;
		if(qtdJogadas == 9) {
			return true;
		}
		return false;
	}
	
	public boolean eVago(int linha, int coluna) {
		return matriz[linha][coluna].equals("-");
	}
	
	private boolean defendeAtaca(int linha1, int coluna1, int linha2, int coluna2, int linhaFoco, int colunaFoco, String alvo) {
		if(matriz[linha1][coluna1].equals(alvo) && matriz[linha2][coluna2].equals(alvo) && eVago(linhaFoco, colunaFoco)) {
			jogada(linhaFoco, colunaFoco, "O");
			return true;
		}
		return false;
	}
	
	private int[] sorteio() {
		int sortLinha = (int)(Math.random()*3);
		int sortColuna = (int)(Math.random()*3);
		
		while(!eVago(sortLinha, sortColuna)) {
			sortLinha = (int)(Math.random()*3);
			sortColuna = (int)(Math.random()*3);
		}
		int[] indice = {sortLinha, sortColuna};
		return indice;
	}
	
	public int[] facil() {
		int[] sorteado = sorteio();
		int sortLinha = sorteado[0];
		int sortColuna = sorteado[1];
		
		int[] indice = {sortLinha, sortColuna};
		jogada(sortLinha, sortColuna, "O");
		
		return indice;
	}
	
	
	public int[] medio() {
		for (int i = 0; i < botaoX.length; i++) {
			if(defendeAtaca(botaoX[i][0], botaoX[i][1], botaoY[i][0], botaoY[i][1], botaoF[i][0], botaoF[i][1], "X")) {
				return botaoF[i];
			}
		}
		
		int[][] lados = {{0,0}, {0,2}, {2,0}, {2,2}};
		int i = 0;
		int linha = lados[i][0];
		int coluna = lados[i][1];
		while(!eVago(linha, coluna) && i <= 3) {
			linha = lados[i][0];
			coluna = lados[i][1];
			i++;
		}
		
		if(!eVago(linha, coluna)) {
			int[] sorteado = sorteio();
			linha = sorteado[0];
			coluna = sorteado[1];
		}
		
		int[] indice = {linha, coluna};
		jogada(linha, coluna, "O");

		return indice;
	}
	
	public int[] dificil() {
		if(qtdJogadas == 1) {
			int[][] lados = {{0,0}, {0,2}, {2,0}, {2,2}};
			int i = (int)(Math.random()*4);
			int linha = lados[i][0];
			int coluna = lados[i][1];
			while(!eVago(linha, coluna)) {
				i = (int)(Math.random()*4);
				linha = lados[i][0];
				coluna = lados[i][1];
			}
			
			int[] indice = {linha, coluna};
			jogada(linha, coluna, "O");
			return indice;
		}
		
		for (int i = 0; i < botaoX.length; i++) {
			if(defendeAtaca(botaoX[i][0], botaoX[i][1], botaoY[i][0], botaoY[i][1], botaoF[i][0], botaoF[i][1], "O")) {
				return botaoF[i];
			}
		}
		return medio();
	}
	
	public boolean fimDeJogada() {
		String[] jogadores = {"X", "O"};
		for(String x: jogadores) {
			for(int i = 0; i < 3; i++) {
				if((x.equals(matriz[i][0]) && x.equals(matriz[i][1]) && x.equals(matriz[i][2])) ||
				   (x.equals(matriz[0][i]) && x.equals(matriz[1][i]) && x.equals(matriz[2][i]))	) {
					return true;
				}
			}
			if((x.equals(matriz[0][0]) && x.equals(matriz[1][1]) && x.equals(matriz[2][2])) ||
			  (x.equals(matriz[0][2]) && x.equals(matriz[1][1]) && x.equals(matriz[2][0])) ) {
				return true;
			}
		}
		return false;
	}
	
	public String[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(String[][] matriz) {
		this.matriz = matriz;
	}

	public Integer[] getUltimaJogada() {
		return ultimaJogada;
	}

	public void setUltimaJogada(Integer[] ultimaJogada) {
		this.ultimaJogada = ultimaJogada;
	}

	public int getQtdJogadas() {
		return qtdJogadas;
	}

	public void setQtdJogadas(int qtdJogadas) {
		this.qtdJogadas = qtdJogadas;
	}

	public int[][] getBotaoX() {
		return botaoX;
	}

	public void setBotaoX(int[][] botaoX) {
		this.botaoX = botaoX;
	}

	public int[][] getBotaoY() {
		return botaoY;
	}

	public void setBotaoY(int[][] botaoY) {
		this.botaoY = botaoY;
	}

	public int[][] getBotaoF() {
		return botaoF;
	}

	public void setBotaoF(int[][] botaoF) {
		this.botaoF = botaoF;
	}

}
