import java.io.*;
import java.util.*;
import java.lang.reflect.Constructor;

public class ArquivoIndexado<T extends Entidade> {

    RandomAccessFile arquivo;
    String nomeArquivo;
    Constructor<T> construtor;
    ArvoreBMais_Int_Long indice;

    public ArquivoIndexado(Constructor<T> c, String n, String in) throws Exception {
        nomeArquivo = n;
        construtor = c;
        arquivo = new RandomAccessFile(nomeArquivo, "rw");
        if(arquivo.length()<4)
            arquivo.writeInt(0);
        indice = new ArvoreBMais_Int_Long(10, in);
    }

    public int incluir(T obj) throws Exception {
        arquivo.seek(0);
        int id = arquivo.readInt();
        id++;
        arquivo.seek(0);
        arquivo.writeInt(id);

        arquivo.seek(arquivo.length());
        long endereco = arquivo.getFilePointer();
        obj.setId(id);
        arquivo.writeByte(' ');
        byte[] b = obj.getByteArray();
        arquivo.writeInt(b.length);
        arquivo.write(b);
        indice.inserir(id, endereco);

        return id;
    }

    public Object[] listar() throws Exception {

        // Em um sistema real, o número de registros será muito superior ao que
        // um ArrayList poderia comportar em memória. Esta operação está aqui
        // apenas para facilitar a depuração do código
        ArrayList<T> lista = new ArrayList<>();
        arquivo.seek(4);
        byte lapide;
        byte[] b;
        int s;
        T l;
        while(arquivo.getFilePointer()<arquivo.length()) {
            l = construtor.newInstance();
            lapide = arquivo.readByte();
            s = arquivo.readInt();
            b = new byte[s];
            arquivo.read(b);
            l.setByteArray(b);
            if(lapide==' ')
                lista.add(l);
        }

        Object[] ls = lista.toArray();
        return ls;
    }

    public Entidade buscar(int id) throws Exception {

        T obj = construtor.newInstance();
        byte lapide;
        byte[] b;
        int s;
        long endereco;

        if( (endereco = indice.buscar(id))>=0 ) {
            arquivo.seek(endereco);
            lapide = arquivo.readByte();
            s = arquivo.readInt();
            b = new byte[s];
            arquivo.read(b);
            obj.setByteArray(b);
            arquivo.seek(endereco);
            return obj;
        }
        else
            return null;
    }


    public boolean excluir(int id) throws Exception {

        byte[] b;
        int s;
        T obj = construtor.newInstance();
        long endereco = indice.buscar(id);
        arquivo.seek(endereco);
        arquivo.write('*');
        s = arquivo.readInt();
        b = new byte[s];
        arquivo.read(b);
        obj.setByteArray(b);
        indice.excluir(id);

        return true;
    }

    public boolean alterar(T novoObj) throws Exception {
        byte[] b;
        int s;
        T obj = construtor.newInstance();
        long endereco = indice.buscar(novoObj.getId());
        arquivo.seek(endereco);
        arquivo.write('*');
        s = arquivo.readInt();
        b = new byte[s];
        arquivo.read(b);
        obj.setByteArray(b);

        arquivo.seek(arquivo.length());
        endereco = arquivo.getFilePointer();
        arquivo.writeByte(' ');
        b = novoObj.getByteArray();
        arquivo.writeInt(b.length);
        arquivo.write(b);
        indice.atualizar(novoObj.getId(), endereco);
        return true;
    }

}
