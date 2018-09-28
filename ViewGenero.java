import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

public class ViewGenero{
    private static CrudGeneros crudG;
    private static Scanner console;

    public ViewGenero() throws Exception {
        console = new Scanner(System.in);
        crudG = new CrudGeneros();
    }

    public void menu(){
        int opcao;
        

        try{
        

            do{
                System.out.println("\n\nGESTÃO DE GENEROS");
                System.out.println("-----------------------------\n");
                System.out.println("1 - Listar");
                System.out.println("2 - Incluir");
                System.out.println("3 - Alterar");
                System.out.println("4 - Excluir");
                System.out.println("5 - Buscar");
                System.out.println("0 - Sair");
                System.out.print("\nOpcao: ");
                opcao = Integer.valueOf(console.nextLine());

                switch(opcao){
                    case 1:listarGeneros(crudG.listarGeneros());break;
                    case 2:incluir();break;
                    case 3:alterar();break;
                    case 4:excluir();break;
                    case 5: busca();break;
                    default:System.out.println("\t\t---OPÇÃO INVALIDA--\n\n");break;
                }
            }while(opcao!=0);
        }catch(Exception e){e.printStackTrace();}
    }

    private Genero criarObjetoGenero(){
        Genero obj = null;
		
		System.out.print("Digite o dado do Genero a ser inserido. \nNome: ");
		String nome = console.nextLine();

		System.out.print("\nNome: " + nome
		+ "\nEsse dado está correto? [s/n]: ");
		char confirma = console.nextLine().charAt(0);
		if(confirma=='s' || confirma=='S'){
            if(nome.length() > 0)
                obj = new Genero(nome);
            else{
                System.out.println("\n\n---Nome genero inválido!---");
                System.out.print("Sim - para tentar novamente :");
                if(confirma=='s' || confirma=='S')
                    obj = criarObjetoGenero();
            } 
        }	
		return obj;
    }

    public void incluir() throws Exception{
        Genero obj = criarObjetoGenero();
        if(obj!=null){
            crudG.incluirGenero(obj);
            System.out.println("\t\t---Filme incluido com sucesso!---\n\n");
        }
            
        
    }

    public void busca() throws Exception{
        int id;
        System.out.print("ID do genero a ser busca :");
        id = Integer.valueOf(console.nextLine());
            
        if(id > 0){
            Genero objGen = crudG.buscarGenero(id);
            if(objGen!=null)
                System.out.println(objGen);
            else{
                System.out.println("Genero não encontrado!");
                System.out.println("\n\n---ID inválido!---");
                System.out.print("Sim - para tentar novamente :");
                char confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S')
                    busca();

            }
                	
        }
        else
            System.out.println("ID inválido!");
    }
    

    public void listarGeneros(Genero[] listaGen){
        System.out.println("\t\t---Lista de Generos---");
        for(int i = 0; i < listaGen.length; i++)
            System.out.println(listaGen[i]+"\n\n");
    }

    public void alterar() throws Exception{
        
       System.out.println("\nALTERAÇÃO DE GENERO");

       int id;
       System.out.print("ID do Genero: ");
       id = Integer.valueOf(console.nextLine());
       if(id > 0){
            Genero objAlterar = crudG.buscarGenero(id);
            if(objAlterar!=null){
                Genero obj = criarObjetoGenero();
                if(obj !=null){
                    System.out.print("\nConfirma alteração? ");
                    char confirma = console.nextLine().charAt(0);
                    if(confirma=='s' || confirma=='S'){
                        objAlterar.nome = obj.nome;
                        obj = null;
                        crudG.alterarGenero(objAlterar);
                    }
                }
            }
            else
                System.out.println("Genero com id ="+id+" não encontrado!");
       }
       else{
           System.out.println("\n\n---ID inválido!---");
           System.out.print("Sim - para tentar novamente :");
           char confirma = console.nextLine().charAt(0);
           if(confirma=='s' || confirma=='S')
            alterar();

       }
    }    
    
    public void excluir() throws Exception {
        int id;
        char confirma;
        System.out.println("\nEXCLUSÃO DE GENERO");
        System.out.print("ID do Genero: ");

        id = Integer.valueOf(console.nextLine());
        if(id>0){
            Genero objGen = crudG.buscarGenero(id);
            if(objGen!=null){
                System.out.print("Deseja excluir o genero ("+objGen.getNome()+"). Sim - para confirma:");
                confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S'){
                    if(crudG.excluirGenero(id))
                        System.out.println("Genero excluido com sucesso!");
                    else
                    System.out.println("Error Genero("+objGen.getNome()+") possui relacionamento com algum filme!");

                }
            }
            else{
                System.out.println("\t\t---Genero não encontrado!---\n");
                System.out.print("Sim - para tentar novamente :");
                confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S')
                    excluir();

            }
        }
        else
            System.out.println("\n\n----ID inválido!----");
            
    }
}