import java.util.Scanner;

public class ViewFilme{
    private static CrudFilmes crudF;
    private static Scanner console;

    public ViewFilme(){}

    public void menu(){
        int opcao;
        

        try{
            crudF = new CrudFilmes();
            console = new Scanner(System.in);

            do{
                System.out.println("\n\nGESTÃO DE FILMES");
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
                    case 1:listarFilmes();break;
                    case 2:incluir();break;
                    case 3:alterar();break;
                    case 4:excluir();break;
                    case 5: busca();break;
                    default:System.out.println("\t\t---OPÇÃO INVALIDA--\n\n");break;
                }
            }while(opcao!=0);
        }catch(Exception e){e.printStackTrace();}
    }

	private Filme criarObjetoGenero(){
		console = new Scanner(System.in);
		String titulo,tituloOriginal,pais,diretor,sinopse;
        short ano;
        short min;
        int idGenero;
        
        Filme filme = null;

        System.out.print("Titulo: ");
        titulo = console.nextLine();

        System.out.print("Titulo Original: ");
        tituloOriginal = console.nextLine();

        System.out.print("Pais de origem: ");
        pais = console.nextLine();

        System.out.print("Diretor: ");
        diretor = console.nextLine();

        System.out.print("Sinopse: ");
        sinopse = console.nextLine();

        System.out.print("Ano: ");
        ano = Short.valueOf(console.nextLine());

        System.out.print("Minutos filme: ");
		min = Short.valueOf(console.nextLine());
		
		try{
			boolean isGenero = false;
			Genero obj;
            do{
                CrudGeneros crudG = new CrudGeneros();
                Genero[] listaG = crudG.listarGeneros();
                if(listaG.length == 0){
                    System.out.println("\n\n\t\t---Nenhum genero está registrado!----");
                    System.out.println("Registre um  genero!");
                    ViewGenero vwGen = new ViewGenero();
                    vwGen.incluir();
                }
                else
                    for (Genero var : listaG) {
                        System.out.println(var);
                    }
				
                System.out.print("Id do Gênero do filme: ");
            
                idGenero = Integer.valueOf(console.nextLine());

                obj = crudG.buscarGenero(idGenero);

                if(obj == null)
                    System.out.println("Genero inválido!");
                else{
                    System.out.println("Genero : " + obj.getNome());
                    isGenero = true;
                }
                    

            }while(!isGenero);
            
        System.out.print("Para confirma inclusão de filme, insira - sim:");
        char confirma;
        confirma = console.nextLine().charAt(0);
        if(confirma=='s' || confirma=='S')
            filme = new Filme(titulo,tituloOriginal,pais,ano,min,diretor,sinopse,obj.getId());

        }catch(Exception e){e.printStackTrace();}
        
        return filme;
    }
    
    public void incluir() throws Exception{
        Filme filme = criarObjetoGenero();
        if(filme!=null){
            crudF.incluirFilme(filme);
            System.out.println("\t\t---Filme incluido com sucesso!---\n\n");

        }

    }
    
    public void busca() throws Exception{
        int id;
        System.out.print("ID do filme a ser busca :");
        id = Integer.valueOf(console.nextLine());

        if(id>0){
            Filme filme = crudF.buscarFilme(id);
            if(filme!=null){
                System.out.println(filme);
            }
            else{
                System.out.println("\n\n\t\t---Filme não encontrado!---");
                System.out.println("\n\n\t\t---Filme com o ID ="+id+" não existe!---");
                System.out.print("Sim - para tentar novamente :");
                char confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S')
                    busca();
            }
        }
        else
            System.out.println("ID inválido!");

    }

    public void listarFilmes() throws Exception{
        Filme[] listaFilme = crudF.listarFilmes();
        System.out.println("\t\t---Lista de Filmes ---");
        for(int i = 0; i < listaFilme.length; i++)
            System.out.println(listaFilme[i]+"\n\n");
    }

    public void alterar() throws Exception{
        System.out.println("\nALTERAÇÃO DE FILME");

       int id;
       System.out.print("ID do filme: ");
       id = Integer.valueOf(console.nextLine());

       if(id > 0){
            Filme objAlterar = crudF.buscarFilme(id);
            if(objAlterar!=null){
                System.out.println("\t\tCaso não deseja alterar determinado campo apenas dê ENTER!");
                Filme objNovo = criarObjetoGenero();
                objAlterar = alterarCamposFilme(objAlterar, objNovo);
                crudF.alterar(objAlterar);

            }
            else
                System.out.println("Filme com id ="+id+" não encontrado!");
       }
       else
            System.out.println("ID inválido!");
    }

    public void excluir() throws Exception{
        
        int id;
        char confirma;
        System.out.println("\nEXCLUSÃO DE FILME");
        System.out.print("ID do Genero: ");

        id = Integer.valueOf(console.nextLine());
        if(id>0){
            Filme filme = crudF.buscarFilme(id);
            if(filme!=null){
                System.out.println(filme);
                System.out.print("Deseja excluir o filme? Sim - para confirma:");
                confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S'){
                    if(crudF.excluirFilme(id))
                        System.out.println("Filme excluido com sucesso!");
                }
            }
            else{
                System.out.println("\t\t---Filme não encontrado!---\n");
                System.out.print("Sim - para tentar novamente :");
                confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S')
                    excluir();

            }
        }
        else
            System.out.println("\n\n----ID inválido!----");
    }
    
    private Filme alterarCamposFilme(Filme objAlterar, Filme objNovo){
        
        objAlterar.titulo = (objNovo.titulo.length()>0 ? objNovo.titulo : objAlterar.titulo);
        objAlterar.tituloOriginal = (objNovo.tituloOriginal.length()>0 ? objNovo.tituloOriginal : objAlterar.tituloOriginal);
        objAlterar.pais = (objNovo.pais.length()>0 ? objNovo.pais : objAlterar.pais);
        objAlterar.diretor = (objNovo.diretor.length()>0 ? objNovo.diretor : objAlterar.diretor);
        objAlterar.sinopse = (objNovo.sinopse.length()>0 ? objNovo.sinopse : objAlterar.sinopse);
        objAlterar.duracao = (objNovo.duracao>=0?objNovo.duracao:objAlterar.duracao);
        objAlterar.ano = (objNovo.ano>=0?objNovo.ano:objAlterar.ano);
        objAlterar.idGenero = objNovo.idGenero;
        
        return objAlterar;
    }


}