

import java.util.Scanner;

//import com.sun.tools.internal.xjc.reader.gbind.OneOrMore;

public class CrudGeneros {

    private static Scanner console = new Scanner(System.in);

    private ArquivoIndexado<Genero> arqGeneros;

    public CrudGeneros() throws Exception{
        this.arqGeneros = new ArquivoIndexado<>(Genero.class.getConstructor(), "genero_dados.db", "genero_dados.idx");
    }

   public  Genero[] listarGeneros() throws Exception {

       Object[] objs = arqGeneros.listar();

       Genero[] listGen = new Genero[objs.length];
       
       for(int i=0; i<objs.length; i++) {
           listGen[i] = (Genero)objs[i];
       }
       
       return listGen;
   }
   

   public  void incluirGenero(Genero obj) throws Exception {
       
           int id = arqGeneros.incluir(obj);
           System.out.println("Genero incluído com ID: "+id);
   }

   public void alterarGenero(Genero objAlterar)throws Exception{
        if( arqGeneros.alterar(objAlterar) )
            System.out.println("Genero alterado.");
        else
            System.out.println("Genero não pode ser alterado.");
    
   }
   
   
   public  boolean excluirGenero(int id) throws Exception {
     
        boolean isExcluido = false; 

        CrudFilmes crudF = new CrudFilmes();

        int qtdFilmeRelacionados = crudF.listarGenero(id);

        if(qtdFilmeRelacionados == 0)
            isExcluido =  arqGeneros.excluir(id);

        return isExcluido; 
   }

   
   public  Genero buscarGenero(int codigo) throws Exception {

        Genero obj = null;
        if(codigo > 0)
            obj = (Genero)arqGeneros.buscar(codigo);
        return obj;


   }
   


    public static void pausa() throws Exception {
        System.out.println("\nPressione ENTER para continuar ...");
        console.nextLine();
    }
    


}
