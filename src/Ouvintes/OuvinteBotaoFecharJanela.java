package Ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Telas.JanelaPadrao;

public class OuvinteBotaoFecharJanela implements ActionListener {
	private JanelaPadrao telaPadrao;

	public OuvinteBotaoFecharJanela(JanelaPadrao telaPadrao) {
		this.telaPadrao = telaPadrao;
	}

	public void actionPerformed(ActionEvent e) {
		telaPadrao.dispose();
	}
}
