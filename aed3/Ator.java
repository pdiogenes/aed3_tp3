package aed3;
import java.io.*;

public class Ator implements Entidade {
    protected int    id;
    protected String nome;
    protected char sexo;

    public Ator(int c, String t, char s) {
        this.id = c;
        this.nome = t;
        this.sexo = s;
    }
    public Ator() {
        this.id = 0;
        this.nome = "";
        this.sexo = 0;
    }

    public void setId(int c) {
        this.id = c;
    }

    public int getId() {
        return this.id;
    }

    public String getString() {
        return this.nome;
    }

    public String toString() {
        return "\nCódigo: " + this.id +
               "\nNome: " + this.nome +
               "\nSexo: " + this.sexo;
    }

    public byte[] getByteArray() throws IOException {
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream( dados );
        saida.writeInt(this.id);
        saida.writeUTF(this.nome);
        saida.writeChar(this.sexo);
        return dados.toByteArray();
    }

    public void setByteArray(byte[] b) throws IOException {
        ByteArrayInputStream dados = new ByteArrayInputStream(b);
        DataInputStream entrada = new DataInputStream(dados);
        this.id = entrada.readInt();
        this.nome = entrada.readUTF();
        this.sexo = entrada.readChar();
    }


}
