package Principal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		PalavrasDAO palavras = new PalavrasDAO();
		BufferedWriter  buffWriter = new BufferedWriter(new FileWriter("palavras2.txt"));
		for (String palavraString : palavras.retornaPalavras()) {
			Palavra palavra = new Palavra(palavraString);
			buffWriter.append(palavra.imprimirArray());
		}
		buffWriter.close ();
	}

}