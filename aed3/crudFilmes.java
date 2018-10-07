package aed3;

import java.util.Scanner;

public class crudFilmes {

    private static Scanner console = new Scanner(System.in);
    private static ArquivoIndexado<Filme> arqFilmes;
    private static ArquivoIndexado<Genero> arqGeneros;
    private static ArvoreBMais_ChaveComposta idxGeneroFilme;

    /**
     * Método principal, cujo objetivo é criar uma interface para o usuário
     */
    public static void main(String[] args) {

        try {

            arqFilmes = new ArquivoIndexado<>(Filme.class.getConstructor(), "filmes.db", "filmes1.idx", "filmes2.idx");
            arqGeneros = new ArquivoIndexado<>(Genero.class.getConstructor(), "generos.db", "generos1.idx", "generos2.idx");
            idxGeneroFilme = new ArvoreBMais_ChaveComposta(10, "generos_filme.idx");
            
            
            // menu
            int opcao;
            do {
                System.out.println("\n\nGESTÃO DE FILMES");
                System.out.println("-----------------------------\n");
                System.out.println("FILMES                   GENEROS");
                System.out.println("11 - Listar              21 - Listar");
                System.out.println("12 - Incluir             22 - Incluir");
                System.out.println("13 - Alterar             23 - Alterar");
                System.out.println("14 - Excluir             24 - Excluir");
                System.out.println("15 - Buscar por ID       25 - Buscar por ID");
                System.out.println("16 - Buscar por título   26 - Buscar por nome");
                System.out.println("                         27 - Listar Filmes");
                System.out.println("\n0 - Sair");
                System.out.print("\nOpcao: ");
                opcao = Integer.valueOf(console.nextLine());

                switch(opcao) {
                    case 11: listarFilmes(); break;
                    case 12: incluirFilme(); break;
                    case 13: alterarFilme(); break;
                    case 14: excluirFilme(); break;
                    case 15: buscarFilme(); break;
                    case 16: buscarFilmePorTitulo(); break;

                    case 21: listarGeneros(); break;
                    case 22: incluirGenero(); break;
                    case 23: alterarGenero(); break;
                    case 24: excluirGenero(); break;
                    case 25: buscarGenero(); break;
                    case 26: buscarGeneroPorNome(); break;
                    case 27: listarFilmesGenero(); break;

                    case 0: break;
                    default: System.out.println("Opção inválida");
                }

            } while(opcao!=0);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    

    public static void listarFilmes() throws Exception {

        Object[] obj = arqFilmes.listar();
        Filme l;

        System.out.println("\nLISTA DE FILMES");
        for(int i=0; i<obj.length; i++) {
            l = (Filme)obj[i];
            System.out.println(   "\nID.............: " + l.id
                                + "\nTitulo.........: " + l.titulo
                                + "\nTitulo Original: " + l.titulo_original
                                + "\nPais...........: " + l.pais
                                + "\nAno............: " + l.ano
                                + "\nDuração........: " + l.duracao
                                + "\nDiretor........: " + l.diretor
                                + "\nSinopse........: " + l.sinopse 
                                + "\nGenero.........: " + ((Genero)arqGeneros.buscar(l.idGenero)).getString() + " ("+l.idGenero+")" );
        }
        pausa();

    }
   
    public static void incluirFilme() throws Exception {

        System.out.print("Digite os dados do filme a ser inserido.  \nTítulo: ");
        String titulo = console.nextLine();

        System.out.print("Título_Original: ");
        String titulo_original = console.nextLine();

        System.out.print("País: ");
        String pais = console.nextLine();

        System.out.print("Ano: ");
        Short ano = console.nextShort();

        System.out.print("Duração: ");
        Short duracao = console.nextShort();

        console.nextLine();

        System.out.print("Diretor: ");
        String diretor = console.nextLine();

        System.out.print("Sinopse: ");
        String sinopse = console.nextLine();

        int idGenero;
        Genero gen;
        
        System.out.print("Genero: ");
        idGenero = Integer.parseInt(console.nextLine());
        if((gen = (Genero)arqGeneros.buscar(idGenero))!=null )
            System.out.println("           "+gen.getString());
        else {
            System.out.println("Genero não encontrado");
            pausa();
            return;
        }

        System.out.print("\nConfirma inclusão? ");
        char confirma = console.nextLine().charAt(0);
        if(confirma=='s' || confirma=='S') {
            Filme obj = new Filme(-1, titulo, titulo_original, pais, ano, duracao, diretor, sinopse, idGenero);
            int id = arqFilmes.incluir(obj);
            idxGeneroFilme.inserir(idGenero, id);
            System.out.println("Filme incluído com ID: "+id);
        }

        pausa();
    }

   
   public static void alterarFilme() throws Exception {
       
        System.out.println("\nALTERAÇÃO DE Filme");
        
        int idGenero;
        Genero gen;
        int id;
        System.out.print("ID do Filme: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;

        Filme obj;
        if( (obj = (Filme)arqFilmes.buscar(id))!=null ) {
            System.out.println(  "\nID.............: " + obj.id
                                + "\nTitulo.........: " + obj.titulo
                                + "\nTitulo Original: " + obj.titulo_original
                                + "\nPais...........: " + obj.pais
                                + "\nAno............: " + obj.ano
                                + "\nDuração........: " + obj.duracao
                                + "\nDiretor........: " + obj.diretor
                                + "\nSinopse........: " + obj.sinopse 
                                + "\nGenero.........: " + ((Genero)arqGeneros.buscar(obj.idGenero)).getString() + " ("+obj.idGenero+")" );
            int idGeneroAnterior = obj.idGenero;
            
            System.out.print("Digite os dados do filme a ser inserido. \nTítulo: ");
            String titulo = console.nextLine();

            System.out.print("Título_Original: ");
            String titulo_original = console.nextLine();

            System.out.print("País: ");
            String pais = console.nextLine();

            System.out.print("Ano: ");
            Short ano = console.nextShort();

            System.out.print("Duração: ");
            Short duracao = console.nextShort();

            console.nextLine();

            System.out.print("Diretor: ");
            String diretor = console.nextLine();

            System.out.print("Sinopse: ");
            String sinopse = console.nextLine();

            System.out.print("Novo genero: ");
            String auxGen = console.nextLine();
            if(auxGen.compareTo("")==0)
                idGenero=-1;
            else {
                idGenero = Integer.parseInt(auxGen);
                if((gen = (Genero)arqGeneros.buscar(idGenero))!=null )
                    System.out.println("                "+gen.getString());
                else {
                    System.out.println("Categoria não encontrada");
                    pausa();
                    return;
                }
            }

            if(titulo.length()>0 || duracao>=0 || ano >=0) {
                System.out.print("\nConfirma alteração? ");
                char confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S') {

                obj.titulo = (titulo.length()>0 ? titulo : obj.titulo);
                obj.titulo_original = (titulo_original.length()>0 ? titulo_original : obj.titulo_original);
                obj.pais = (pais.length()>0 ? pais : obj.pais);
                obj.diretor = (diretor.length()>0 ? diretor : obj.diretor);
                obj.sinopse = (sinopse.length()>0 ? sinopse : obj.sinopse);
                obj.duracao = (duracao>=0?duracao:obj.duracao);
                obj.ano = (ano>=0?ano:obj.ano);

                if( arqFilmes.alterar(obj) )
                        System.out.println("Filme alterado.");
                    else
                        System.out.println("Filme não pode ser alterado.");
                }
            }
       }
       else
           System.out.println("Filme não encontrado");
       pausa();

    }
  
   
    public static void excluirFilme() throws Exception {

        System.out.println("\nEXCLUSÃO DE FILME");

        int id;
        System.out.print("ID do Filme: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;

        Filme obj;
        if( (obj = (Filme)arqFilmes.buscar(id))!=null ) {
             System.out.println(  "\nID.............: " + obj.id
                                + "\nTitulo.........: " + obj.titulo
                                + "\nTitulo Original: " + obj.titulo_original
                                + "\nPais...........: " + obj.pais
                                + "\nAno............: " + obj.ano
                                + "\nDuração........: " + obj.duracao
                                + "\nDiretor........: " + obj.diretor
                                + "\nSinopse........: " + obj.sinopse 
                                + "\nGenero.........: " + ((Genero)arqGeneros.buscar(obj.idGenero)).getString() + " ("+obj.idGenero+")" );

             System.out.print("\nConfirma exclusão? ");
             char confirma = console.nextLine().charAt(0);
             if(confirma=='s' || confirma=='S') {
                 if( arqFilmes.excluir(id) ) {
                     idxGeneroFilme.excluir(obj.idGenero, obj.id);
                     System.out.println("Filme excluído.");
                 }
             }
        }
        else
            System.out.println("Filme não encontrado");
        pausa();

    }
   
   
    public static void buscarFilme() throws Exception {

        System.out.println("\nBUSCA DE FILME POR CÓDIGO");

        int codigo;
        System.out.print("Código: ");
        codigo = Integer.valueOf(console.nextLine());
        if(codigo <=0) 
            return; 

        Filme obj;
        if( (obj = (Filme)arqFilmes.buscar(codigo))!=null ) {
             System.out.println(  "\nID.............: " + obj.id
                                + "\nTitulo.........: " + obj.titulo
                                + "\nTitulo Original: " + obj.titulo_original
                                + "\nPais...........: " + obj.pais
                                + "\nAno............: " + obj.ano
                                + "\nDuração........: " + obj.duracao
                                + "\nDiretor........: " + obj.diretor
                                + "\nSinopse........: " + obj.sinopse 
                                + "\nGenero.........: " + ((Genero)arqGeneros.buscar(obj.idGenero)).getString() + " ("+obj.idGenero+")" );
        }
        else
            System.out.println("Filme não encontrado");
        pausa();
    }
    
    public static void buscarFilmePorTitulo() throws Exception {

        System.out.println("\nBUSCA DE FILME POR TÍTULO");

        String titulo;
        System.out.print("Título: ");
        titulo = console.nextLine();

        Filme obj;
        if( (obj = (Filme)arqFilmes.buscarString(titulo))!=null ) {
             System.out.println(  "\nID.............: " + obj.id
                                + "\nTitulo.........: " + obj.titulo
                                + "\nTitulo Original: " + obj.titulo_original
                                + "\nPais...........: " + obj.pais
                                + "\nAno............: " + obj.ano
                                + "\nDuração........: " + obj.duracao
                                + "\nDiretor........: " + obj.diretor
                                + "\nSinopse........: " + obj.sinopse 
                                + "\nGenero.........: " + ((Genero)arqGeneros.buscar(obj.idGenero)).getString() + " ("+obj.idGenero+")" );
        }
        else
            System.out.println("Filme não encontrado");
        pausa();
    }

    public static void listarGeneros() throws Exception {

        Object[] obj = arqGeneros.listar();

        System.out.println("\nLISTA DE GENEROS");
        for(int i=0; i<obj.length; i++) {
            System.out.println((Genero)obj[i]);
        }
        pausa();

    }

    public static void incluirGenero() throws Exception {

        String nome;

        System.out.println("\nINCLUSÃO DE GENERRO");
        System.out.print("Nome: ");
        nome = console.nextLine();

        System.out.print("\nConfirma inclusão? ");
        char confirma = console.nextLine().charAt(0);
        if(confirma=='s' || confirma=='S') {
            Genero obj = new Genero(-1, nome);
            int id = arqGeneros.incluir(obj);
            System.out.println("Genero incluído com ID: "+id);
        }

        pausa();
    }


    public static void alterarGenero() throws Exception {

        System.out.println("\nALTERAÇÃO DE Genero");

        int id;
        System.out.print("ID do genero: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;

        Genero obj;
        if( (obj = (Genero)arqGeneros.buscar(id))!=null ) {
             System.out.println(obj);

             String nome;

             System.out.print("\nNovo nome: ");
             nome = console.nextLine();

             if(nome.length()>0) {
                 System.out.print("\nConfirma alteração? ");
                 char confirma = console.nextLine().charAt(0);
                 if(confirma=='s' || confirma=='S') {

                 obj.nome = (nome.length()>0 ? nome : obj.nome);

                 if( arqGeneros.alterar(obj) ) 
                         System.out.println("Genero alterado.");
                     else
                         System.out.println("Genero não pode ser alterado.");
                 }
             }
        }
        else
            System.out.println("Genero não encontrado");
        pausa();

    }


    public static void excluirGenero() throws Exception {

        System.out.println("\nEXCLUSÃO DE GENERO");

        int id;
        System.out.print("ID do Genero: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;

        int[] idsFilmes = idxGeneroFilme.lista(id);
        if(idsFilmes.length>0) {
            System.out.println("Genero não pode ser excluído por possuir filmes");
            pausa();
            return;
        }       
        
        Genero obj;
        if( (obj = (Genero)arqGeneros.buscar(id))!=null ) {
             System.out.println(obj);

             System.out.print("\nConfirma exclusão? ");
             char confirma = console.nextLine().charAt(0);
             if(confirma=='s' || confirma=='S') {
                 if( arqGeneros.excluir(id) ) {
                     System.out.println("Genero excluído.");
                 }
             }
        }
        else
            System.out.println("Genero não encontrado");
        pausa();

    }


    public static void buscarGenero() throws Exception {

        System.out.println("\nBUSCA DE GENERO POR ID");

        int id;
        System.out.print("ID do Genero: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return; 

        Genero obj;
        if( (obj = (Genero)arqGeneros.buscar(id))!=null )
            System.out.println(obj);
        else
            System.out.println("Genero não encontrado");
        pausa();

    }

    public static void buscarGeneroPorNome() throws Exception {

        System.out.println("\nBUSCA DE GENERO POR NOME");

        String nome;
        System.out.print("Nome: ");
        nome = console.nextLine();

        Genero obj;
        if( (obj = (Genero)arqGeneros.buscarString(nome))!=null ) 
            System.out.println(obj);
        else
            System.out.println("Genero não encontrado");
        pausa();
    }

    public static void listarFilmesGenero() throws Exception {

        Filme l;

        System.out.println("\nLISTA DE LIVROS POR GENERO");
        int id;
        System.out.print("ID do Genero: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return; 

        int[] idsFilmes = idxGeneroFilme.lista(id);
        for(int i=0; i<idsFilmes.length; i++) {
            l = (Filme)arqFilmes.buscar(idsFilmes[i]);
            System.out.println(   "\nID.............: " + l.id
                                + "\nTitulo.........: " + l.titulo
                                + "\nTitulo Original: " + l.titulo_original
                                + "\nPais...........: " + l.pais
                                + "\nAno............: " + l.ano
                                + "\nDuração........: " + l.duracao
                                + "\nDiretor........: " + l.diretor
                                + "\nSinopse........: " + l.sinopse 
                                + "\nGenero.........: " + ((Genero)arqGeneros.buscar(l.idGenero)).getString() + " ("+l.idGenero+")" );
        }
        pausa();

    }
   
    public static void pausa() throws Exception {
        System.out.print("\nPressione ENTER para continuar ...");
        console.nextLine();
    }
    

}
