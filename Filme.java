/* Autores: Tulio N. Polido Lopes, Joao Victor da Silva, Gustavo Lescowicz Kotarsky, Temistocles Altivo Schwartz 
 * Data: 21/08/2018
 * */
import java.io.*;

public class Filme implements Entidade{
	protected int id;
	protected String titulo;
	protected String tituloOriginal;
	protected String pais;
	protected short ano;
	protected short duracao;
	protected String diretor;
	protected String sinopse;
	protected int idGenero;

	public Filme() {
	}//end Filme()

	/*
	 * Construtor da classe
	 * @param titulo do filme
	 * @param titulo Original do filme
	 * @param pais em que foi produzido
	 * @param ano de lancamento
	 * @param duracao em min do filme
	 * @param diretor do filme
	 * @param sinopse oficial do filme
	 * @return Instancia de filme criada com parametros selecionados
	 * */
	public Filme(String titulo, String tituloOriginal, String pais, short ano, short duracao, String diretor, String sinopse,int idGenero) {
		this.titulo = titulo;
		this.tituloOriginal = tituloOriginal;
		this.pais = pais;
		this.ano = ano;
		this.duracao = duracao;
		this.diretor = diretor;
		this.sinopse = sinopse;
		this.idGenero = idGenero;
	}//end Filme()

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setTituloOriginal(String tituloOriginal) {
		this.tituloOriginal = tituloOriginal;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setAno(short ano) {
		this.ano = ano;
	}

	public void setDuracao(short duracao) {
		this.duracao = duracao;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public void setId(int id){
		this.id = id;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getTitulo () {
		return this.titulo;			
	}
	
	public String getTituloOriginal () {
		return this.tituloOriginal;
	}

	public String getPais () {
		return this.pais;
	}
	
	public int getAno () {
		return this.ano;
	}
	
	public int getDuracao () {
		return this.duracao;
	}
	
	public String getDiretor () {
		return this.diretor;
	}
	
	public String getSinopse () {
		return this.sinopse;
	}

	public int getId() {
		return this.id;
	}
	
	public int getIdGenero() {
		return this.idGenero;
	}

	/*
	 * Retorna um vetor de bytes(registro) do Filme corrente
	 * @return vetor de bytes do registro
	 * @throws IOException
	 * */	
	public byte[] getByteArray() throws IOException {
		ByteArrayOutputStream dados = new ByteArrayOutputStream();
		DataOutputStream saida = new DataOutputStream(dados);
		
		saida.writeInt(this.id);
		saida.writeUTF(this.titulo);
		saida.writeUTF(this.tituloOriginal);
		saida.writeUTF(this.pais);
		saida.writeShort(this.ano);
		saida.writeShort(this.duracao);
		saida.writeUTF(this.diretor);
		saida.writeUTF(this.sinopse);
		saida.writeInt(this.idGenero);
		

		return dados.toByteArray();
	}//end getByteArray()

	/*
	 * Recebe um vetor de bytes com informacoes de um filme e seta no Filme corrente
	 * @param vetor de bytes com informacoes de um filme do arquivo
	 * @throws IOException
	 * */
	public void setByteArray(byte[] bytes) throws IOException {
		ByteArrayInputStream dados = new ByteArrayInputStream(bytes);
		DataInputStream entrada = new DataInputStream(dados);

		this.id = entrada.readInt();
		this.titulo = entrada.readUTF();
		this.tituloOriginal = entrada.readUTF();
		this.pais = entrada.readUTF();
		this.ano = entrada.readShort();
		this.duracao = entrada.readShort();
		this.diretor = entrada.readUTF();
		this.sinopse = entrada.readUTF();
		this.idGenero = entrada.readInt();
		
	}//end setByteArray()

	/*
	 * Escreve o objeto filme no arquivo
	 * @param Instancia de RAF com o arquivo aberto
	 * @throws IOException
	 * */
	public void writeObject(RandomAccessFile raf) throws IOException {
		byte[] dados = this.getByteArray();
		raf.writeChar(' ');
		raf.writeShort(dados.length);
		raf.write(dados);
	}//end writeObject()

	/*
	 * Retorna uma String com as informacoes do filme
	 * @return Classe corrente em formato de string
	 * */
	public String toString(){

		String genero= "";
		try{
			CrudGeneros crudG = new CrudGeneros();
			Genero obj = crudG.buscarGenero(this.idGenero);
			genero = obj.getNome();
		}
		catch(Exception e){e.printStackTrace();}
		

		return "Titulo: "+this.titulo+
			"\nTitulo Original: "+this.tituloOriginal+
			"\nDiretor: "+this.diretor+
			"\nPais: "+this.pais+
			"\nDuracao: "+this.duracao+
			"\nAno: "+this.ano+
			"\nSinopse: "+this.sinopse+
			"\nID: "+this.id+
			"\nGenero :"+genero;

	}//end toString()
}//end Filme
