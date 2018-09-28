

import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

public class CrudFilmes {

    private static Scanner console = new Scanner(System.in);
    private static ArquivoIndexado<Filme> arqFilmes;


    public CrudFilmes() throws Exception{
        this.arqFilmes = new ArquivoIndexado<>(Filme.class.getConstructor(), "filme_dados.db", "filme_dados.idx");
    }


    public Filme[] listarFilmes() throws Exception {

        Object[] objs = arqFilmes.listar();

        Filme[] listF = new Filme[objs.length];

        for(int i=0; i<objs.length; i++) {
            listF[i] = (Filme)objs[i];
        }

        return listF;
    }

    public void  incluirFilme(Filme filme ) throws Exception {
        int id = arqFilmes.incluir(filme);
        System.out.println("Filme incluído com ID: "+id);

    }

    public void alterar(Filme filme) throws Exception{
        if( arqFilmes.alterar(filme) )
        System.out.println("Filme alterado.");
        else
            System.out.println("Filme não pode ser alterado.");
    }

   public  boolean excluirFilme(int id) throws Exception {

        return arqFilmes.excluir(id);

   }

   public  Filme buscarFilme(int codigo) throws Exception {    

       Filme obj =null;
       if(codigo > 0)
           obj = (Filme)arqFilmes.buscar(codigo);

       return obj;

   }
   
    public  void pausa() throws Exception {
        System.out.println("\nPressione ENTER para continuar ...");
        console.nextLine();
    }
    
     /**
    *   @param int idGenero id do genero a ser pesquisado
    *   Busca o numero de vezes em que o genero pesquisado aparece na lista de filmes
    *   @return resp numero de vezes em que o genero foi encontrado
    */
    public static int listarGenero(int idGenero) throws Exception {
        int resp = 0;

        Object[] obj = arqFilmes.listar();

        //Realiza a pesquisa enquanto houverem filmes
        //Adiciona ao somador cada iteração correspondente ao genero pesquisado
        for(int i=0; i<obj.length; i++) {
          if( (int)((Filme)obj[i]).getIdGenero() == idGenero )
            resp++;
        }

        return resp;
    }


}
