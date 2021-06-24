package Ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Principais.Jogador;
import Telas.JanelaDePerfil;
import Telas.JanelaPadrao;

public class OuvinteBotaoVoltarPerfil implements ActionListener {
	private Jogador jogador;
	private JanelaPadrao telaPadrao;
	
	public OuvinteBotaoVoltarPerfil(Jogador jogador, JanelaPadrao telaPadrao) {
		this.jogador = jogador;
		this.telaPadrao = telaPadrao;
	}

	public void actionPerformed(ActionEvent e) {
		new JanelaDePerfil(jogador, telaPadrao);
		telaPadrao.dispose();
	}
}
