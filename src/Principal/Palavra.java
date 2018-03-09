package Principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Palavra {
	final String[] vogais = { "a", "e", "i", "o", "u", "ã", "õ", "â", "ê", "ô", "á", "é", "í", "ó", "ú" };
	final String[] consoantes = { "b", "c", "d", "f", "g", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w",
			"x", "z" };
	String palavra, palavraSeparada, palavraMinuscula, hiato;
	Integer pontosDeClassificacao, quantiDadeSilabas, quantidaDeLetras;

	// Construtor padrão que recebe a palavra
	public Palavra(String palavra) throws IOException {
		
		this.palavra = palavra;
		this.palavraMinuscula = palavra.toLowerCase();
		this.palavraSeparada = SeparaSilabas(this.palavraMinuscula);
		this.quantiDadeSilabas = ContaSilabas(this.palavraSeparada);
		this.quantidaDeLetras = QuantidadeLetras(palavra);
		
	}

	// Metodo que faz uma analise geral da palavra
	public void AnalisarPalavra() {
		System.out.println("Separação Silabica da palavra " + palavra + ": " + palavraSeparada);
		System.out.println("Total de letras: " + quantidaDeLetras);
		System.out.println("Total de Silabas: " + quantiDadeSilabas);
		if (isVogaisIdenticas()) {
			System.out.println("Possui vogais identicas");
		}
		if (isEncontroLR()) {
			System.out.println("Possui encontro do L ou do R com consoante");
		}
		if (isTritongo()) {
			System.out.println("É uma palavra Tritongo");
		} else {
			if (isDitongo() && !palavraSeparada.contains(this.hiato)) {
				System.out.println("É uma palavra Hiato");
			}else if(isDitongo() ) {
				System.out.println("É uma palavra Ditongo");
			}
		}
		if (isDigrafo()) {
			System.out.println("É uma palavra Digrafo onde não há separação silábica");
		}
		if (isOutrosDigrafo()) {
			System.out.println("É uma palavra Dirgrafo onde há separação silábica");
		}
	}

	// Metodo que faz a separação silábica da palavra
	public String SeparaSilabas(String palavra) throws IOException {
		URL url = new URL("https://www.dicio.com.br/" + Normalizer.normalize(palavra, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			if (inputLine.contains("Separação silábica: <b>")) {
				return inputLine.substring(
						inputLine.indexOf("Separação silábica: <b>") + "Separação silábica: <b>".length(),
						inputLine.lastIndexOf("</b><br />"));
			}

		}

		in.close();
		return "Palavra não encontrada";
	}
	
	// Metodo que conta as silabas da palavra
	public Integer ContaSilabas(String palavraSeparada) {
		Integer quantidade = 0;
		for (int i = 0; i < palavraSeparada.length(); i++) {
			String temp = String.valueOf(palavraSeparada.charAt(i));
			if (temp.equalsIgnoreCase("-")) {
				quantidade++;
			}
		}
		return quantidade+1;
	}

	//Metodo que verifica a existencia de vogais identicas na palavra e cc e cç
	public Boolean isVogaisIdenticas() {
		if (palavraMinuscula.contains("cc") || palavraMinuscula.contains("cç")) {
			return true;
		}

		for (int i = 0; i < 5; i++) {
			if (palavraMinuscula.contains(vogais[i] + vogais[i])) {
				return true;
			}
		}
		return false;
	}

	// Metodo que verifica se possui o encontro do L e R com outra consoante
	public Boolean isEncontroLR() {
		for (int i = 0; i < consoantes.length; i++) {
			if (palavraMinuscula.contains(consoantes[i] + "l") || palavraMinuscula.contains(consoantes[i] + "r")) {
				return true;
			}
		}
		return false;
	}

	// Metodo que verifica se a palavra é ditongo
	public Boolean isDitongo() {
		for (int i = 0; i < vogais.length; i++) {
			for (int j = 0; j < vogais.length; j++) {
				if (palavraMinuscula.contains(vogais[i] + vogais[j])) {
					this.hiato = vogais[i] + vogais[j];
					return true;
				}
			}
		}
		return false;
	}

	// Metodo que verifica se a palavra é dígrafo onde não há separação silábica
	public Boolean isDigrafo() {
		if (palavraMinuscula.contains("ch") || palavraMinuscula.contains("lh") || palavraMinuscula.contains("gu")
				|| palavraMinuscula.contains("qu")) {
			return true;
		} else {
			return false;
		}
	}

	// Metodo que verifica outros digrafos que quando encontrados na palavra há
	// separação silábica
	public Boolean isOutrosDigrafo() {
		if (palavraMinuscula.contains("rr") || palavraMinuscula.contains("ss") || palavraMinuscula.contains("sc")
				|| palavraMinuscula.contains("sç") || palavraMinuscula.contains("xs")
				|| palavraMinuscula.contains("xc")) {
			return true;
		} else {
			return false;
		}
	}

	// Metodo que verifica se a palavra é tritongo
	public Boolean isTritongo() {
		for (int i = 0; i < vogais.length; i++) {
			for (int j = 0; j < vogais.length; j++) {
				for (int k = 0; k < vogais.length; k++) {
					if (palavraMinuscula.contains(vogais[i] + vogais[j] + vogais[k])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Metodo onde retorna a quantidade de letras da palavra
	public Integer QuantidadeLetras(String palavra) {
		return palavraMinuscula.length();
	}

	// Metodo onde faz a pontuação avaliando os acentos da palavra
	public static Integer PontuaAcentos(String word) { // Classe para analisar os acentos das palavras
		Integer counter = 0;
		Pattern padrao = Pattern.compile("[a-z A-Z]*"); // A-Z a-z separados permitem "" (espaço)
		Matcher pesquisa = padrao.matcher(word);
		if (pesquisa.matches()) {
			counter += 1;
		}
		return counter;
	}

	// METODOS GETTERS E SETTERS ABAIXO
	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public String getPalavraSeparada() {
		return palavraSeparada;
	}

	public void setPalavraSeparada(String palavraSeparada) {
		this.palavraSeparada = palavraSeparada;
	}

	public Integer getPontosDeClassificacao() {
		return pontosDeClassificacao;
	}

	public void setPontosDeClassificacao(Integer pontosDeClassificacao) {
		this.pontosDeClassificacao = pontosDeClassificacao;
	}

	public Integer getQuantidadeSilabas() {
		return quantiDadeSilabas;
	}

	public void setQuantidadeSilabas(Integer quantiDadeSilabas) {
		this.quantiDadeSilabas = quantiDadeSilabas;
	}

	public Integer getQuantidadeLetras() {
		return quantidaDeLetras;
	}

	public void setQuantidadeLetras(Integer quantidaDeLetras) {
		this.quantidaDeLetras = quantidaDeLetras;
	}

	public String getPalavraL() {
		return palavraMinuscula;
	}

	public void setPalavraL(String palavraMinuscula) {
		this.palavraMinuscula = palavraMinuscula;
	}

}