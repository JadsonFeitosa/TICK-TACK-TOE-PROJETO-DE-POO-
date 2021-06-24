package Principais;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Persistencia {
	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private String diretorioTemporario = System.getProperty ("java.io.tmpdir") + "/central.xml" ;
	
	private String tokenDropbox = "7LmVt3Q3_HAAAAAAAAAAaHj6ggUjS1FUUXLF0tlrDePrnvHO9TGAOklV-9T8kRR7";
	private DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
    private DbxClientV2 client = new DbxClientV2(config, tokenDropbox);
	
	public void salvarCentral(Central obj, String nome) {
		File arquivo = new File(diretorioTemporario);
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n" + xstream.toXML(obj);
		
		try {
			PrintWriter gravar = new PrintWriter(arquivo, "ISO-8859-1");
			if(!arquivo.exists()) {
				arquivo.createNewFile();
			}
			gravar.print(xml);
			gravar.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Central recuperarCentral(String nome) {
		File arquivo = new File(diretorioTemporario);
		if(arquivo.exists()) {
			try {
				BufferedReader r = new BufferedReader(new InputStreamReader (new FileInputStream (arquivo), "ISO-8859-1"));
				return (Central) xstream.fromXML(r);
			} catch (Exception e) {
				Central novaCentral = new Central();
				salvarCentral(novaCentral, nome);
				return novaCentral;
			}
		}
		return new Central();
	}
	
	/**
	 * Faz o upload da central para o Dropbox
	 * @param nome Nome da central que será salvo no Dropbox
	 */
	public void subirCentral(String nome) {
        try (InputStream in = new FileInputStream(diretorioTemporario)) {
            client.files().uploadBuilder("/"+nome).withMode(WriteMode.OVERWRITE).uploadAndFinish(in);
        } catch (FileNotFoundException e) {
        	System.out.println("Central não encontrada.");
        } catch (DbxException e) {
        	System.out.println("Erro no token.");
		} catch (IOException e) {
			System.out.println("Erro.");
		}
	}
	
	/**
	 * Faz o download da central do Dropbox para a pasta temporária do Sistema Operacional
	 * @param nome Nome da central no Dropbox
	 * @throws Exception Caso haver alguma exceção é lançada
	 */
	public void baixarCentral(String nome) throws Exception {
		try {
            FileOutputStream saida = new FileOutputStream(diretorioTemporario);
            client.files().downloadBuilder("/"+nome).download(saida);
		} catch (Exception e) {
			throw new Exception("Não foi possível recuperar a central.");
		}
	}
}