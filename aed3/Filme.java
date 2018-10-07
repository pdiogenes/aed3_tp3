package aed3;
import java.io.*;

public class Filme implements Entidade {
    protected int id;
    protected String titulo;
    protected String titulo_original;
    protected String pais;
    protected short ano;
    protected short duracao;
    protected String diretor;
    protected String sinopse;
    protected int idGenero;
    
    public Filme(int id, String titulo,String titulo_original,String pais,short ano, short duracao, String diretor, String sinopse, int id_genero) {
        this.id = id;
        this.titulo = titulo;
        this.titulo_original = titulo_original;
        this.pais = pais;
        this.ano = ano;
        this.duracao = duracao;
        this.diretor = diretor;
        this.sinopse = sinopse;
        this.idGenero = id_genero;
    }
    public Filme(){
        this(-1, "","","",(short)0,(short)0,"","", -1);
    }
    
    public void setId(int c) {
        this.id = c;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getString() {
        return this.titulo;
    }
    
    public String toString() {
        return "\nID: " + id
        + "\nTitulo: " + titulo
        + "\nTitulo Original: " + titulo_original
        + "\nPais: " + pais
        + "\nAno: " + ano
        + "\nDuração: " + duracao
        + "\nDiretor: " + diretor
        + "\nSinopse: " + sinopse 
        + "\nId do Genero: " + idGenero;
    }
    
    public byte[] getByteArray() throws IOException {
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream( dados );
        saida.writeInt(this.id);
        saida.writeUTF(this.titulo);
        saida.writeUTF(this.titulo_original);
        saida.writeUTF(this.pais);
        saida.writeShort(this.ano);
        saida.writeShort(this.duracao);
        saida.writeUTF(this.diretor);
        saida.writeUTF(this.sinopse);
        saida.writeInt(this.idGenero);
        return dados.toByteArray();        
    }
    
    public void setByteArray(byte[] b) throws IOException {
        ByteArrayInputStream dados = new ByteArrayInputStream(b);
        DataInputStream entrada = new DataInputStream(dados);
        this.id = entrada.readInt();
        this.titulo = entrada.readUTF();
        this.titulo_original = entrada.readUTF();
        this.pais = entrada.readUTF();
        this.ano = entrada.readShort();
        this.duracao = entrada.readShort();
        this.diretor = entrada.readUTF();
        this.sinopse = entrada.readUTF();
        this.idGenero = entrada.readInt();
    }


}
