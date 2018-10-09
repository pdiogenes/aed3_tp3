package aed3;
import java.io.*;

public class Elenco implements Entidade {
    protected int    id;
    protected int    idFilme;
    protected int    idAtor;
    protected String nomePersonagem;

    public Elenco(int c, int f, int a, String n) {
        this.id = c;
        this.idFilme = f;
        this.idAtor = a;
        this.nomePersonagem = n;
    }
    public Elenco() {
        this.id = 0;
        this.idFilme = 0;
        this.idAtor = 0;
        this.nomePersonagem = "";
    }

    public void setId(int c) {
        this.id = c;
    }

    public int getId() {
        return this.id;
    }

    public String getString() {
        return this.nomePersonagem;
    }

    public String toString() {
        return "\nCódigo: " + this.id +
               "\nNome do Personagem: " + this.nomePersonagem; 
    }

    public byte[] getByteArray() throws IOException {
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream( dados );
        saida.writeInt(this.id);
        saida.writeInt(this.idFilme);
        saida.writeInt(this.idAtor);
        saida.writeUTF(this.nomePersonagem);
        return dados.toByteArray();
    }

    public void setByteArray(byte[] b) throws IOException {
        ByteArrayInputStream dados = new ByteArrayInputStream(b);
        DataInputStream entrada = new DataInputStream(dados);
        this.id = entrada.readInt();
        this.idFilme = entrada.readInt();
        this.idAtor = entrada.readInt();
        this.nomePersonagem = entrada.readUTF();
    }


}
