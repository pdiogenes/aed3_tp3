package aed3;

import java.util.Scanner;

public class crudFilmes {

    private static Scanner console = new Scanner(System.in);
    private static ArquivoIndexado<Filme> arqFilmes;
    private static ArquivoIndexado<Genero> arqGeneros;
    private static ArquivoIndexado<Ator> arqAtores;
    private static ArquivoIndexado<Elenco> arqElenco;
    private static ArvoreBMais_ChaveComposta idxGeneroFilme;
    private static ArvoreBMais_ChaveComposta idxAtorElenco;
    private static ArvoreBMais_ChaveComposta idxFilmeElenco;

    /**
     * Método principal, cujo objetivo é criar uma interface para o usuário
     */
    public static void main(String[] args) {

        try {

            arqFilmes = new ArquivoIndexado<>(Filme.class.getConstructor(), "filmes.db", "filmes1.idx", "filmes2.idx");
            arqGeneros = new ArquivoIndexado<>(Genero.class.getConstructor(), "generos.db", "generos1.idx", "generos2.idx");
            arqAtores = new ArquivoIndexado<>(Ator.class.getConstructor(), "atores.db", "atores1.idx", "atores2.idx");
            arqElenco = new ArquivoIndexado<>(Elenco.class.getConstructor(), "elenco.db", "elenco1.idx", "elenco2.idx");
            idxGeneroFilme = new ArvoreBMais_ChaveComposta(10, "generos_filme.idx");
            idxAtorElenco = new ArvoreBMais_ChaveComposta(10, "atores_elenco.idx");
            idxFilmeElenco = new ArvoreBMais_ChaveComposta(10, "filmes_elenco.idx");
            
            
            // menu
            int opcao;
            do {
                System.out.println("\n\nGESTÃO DE FILMES");
                System.out.println("-----------------------------\n");
                System.out.println("FILMES                   GENEROS                   ATOR                                      ELENCO");
                System.out.println("11 - Listar              21 - Listar               31 - Listar                               41 - Listar");
                System.out.println("12 - Incluir             22 - Incluir              32 - Incluir                              42 - Incluir");
                System.out.println("13 - Alterar             23 - Alterar              33 - Alterar                              43 - Alterar");
                System.out.println("14 - Excluir             24 - Excluir              34 - Excluir                              44 - Excluir");
                System.out.println("15 - Buscar por ID       25 - Buscar por ID        35 - Buscar por ID                        45 - Buscar por ID");
                System.out.println("16 - Buscar por título   26 - Buscar por nome      36 - Buscar por Nome                      46 - Buscar Atores por Filme");
                System.out.println("                         27 - Listar Filmes                                                  47 - Buscar Filmes por Ator");
                System.out.println("----------------------------");
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

                    case 31: listarAtor(); break;
                    case 32: incluirAtor(); break;
                    case 33: alterarAtor(); break;
                    case 34: excluirAtor(); break;
                    case 35: buscarAtor(); break;
                    case 36: buscarAtorPorNome(); break;

                    case 41: listarElenco(); break;
                    case 42: incluirElenco(); break;
                    case 43: alterarElenco(); break;
                    case 44: excluirElenco(); break;
                    case 45: buscarElenco(); break;
                    case 46: buscarAtorPorFilme(); break;
                    case 47: buscarFilmePorAtor(); break;

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
       
        System.out.println("\nALTERAÇÃO DE FILME");
        
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
                    System.out.println("Genero não encontrado");
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
                obj.idGenero = idGenero;

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

        int[] idsFilmes = idxFilmeElenco.lista(id);
        if(idsFilmes.length>0) {
            System.out.println("Filme nao pode ser excluido por estar em algum elenco.");
            pausa();
            return;
        }    

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

    public static void listarAtor() throws Exception {

        Object[] obj = arqAtores.listar();

        System.out.println("\nLISTA DE ATORES");
        for(int i=0; i<obj.length; i++) {
            System.out.println((Ator)obj[i]);
        }
        pausa();

    }

    public static void incluirAtor() throws Exception {

        String nome;
        char sexo;

        System.out.println("\nINCLUSÃO DE ATOR");
        System.out.print("Nome: ");
        nome = console.nextLine();
        System.out.print("Sexo: [M/F] ");
        sexo = console.nextLine().charAt(0);

        System.out.print("\nConfirma inclusão? ");
        char confirma = console.nextLine().charAt(0);
        if(confirma=='s' || confirma=='S') {
            Ator obj = new Ator(-1, nome, sexo);
            int id = arqAtores.incluir(obj);
            System.out.println("Ator incluído com ID: "+id);
        }

        pausa();
    }

    public static void alterarAtor() throws Exception {

        System.out.println("\nALTERAÇÃO DE ATOR");

        int id;
        System.out.print("ID do ator: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;

        Ator obj;
        if( (obj = (Ator)arqAtores.buscar(id))!=null ) {
             System.out.println(obj);

             String nome;

             System.out.print("\nNovo nome: ");
             nome = console.nextLine();

             if(nome.length()>0) {
                 System.out.print("\nConfirma alteração? ");
                 char confirma = console.nextLine().charAt(0);
                 if(confirma=='s' || confirma=='S') {

                 obj.nome = (nome.length()>0 ? nome : obj.nome);

                 if( arqAtores.alterar(obj) ) 
                         System.out.println("Ator alterado.");
                     else
                         System.out.println("Ator não pode ser alterado.");
                 }
             }
        }
        else
            System.out.println("Ator não encontrado");
        pausa();

    }

    public static void excluirAtor() throws Exception {

        System.out.println("\nEXCLUSÃO DE ATOR");

        int id;
        System.out.print("ID do Ator: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;

        int[] idsElenco = idxAtorElenco.lista(id);
        if(idsElenco.length>0) {
            System.out.println("Ator não pode ser excluído por possuir filmes no elenco");
            pausa();
            return;
        }       
        
        Ator obj;
        if( (obj = (Ator)arqAtores.buscar(id))!=null ) {
             System.out.println(obj);

             System.out.print("\nConfirma exclusão? ");
             char confirma = console.nextLine().charAt(0);
             if(confirma=='s' || confirma=='S') {
                 if( arqAtores.excluir(id) ) {
                     System.out.println("Ator excluído.");
                 }
             }
        }
        else
            System.out.println("Ator não encontrado");
        pausa();

    }

    public static void buscarAtor() throws Exception {

        System.out.println("\nBUSCA DE ATOR POR ID");

        int id;
        System.out.print("ID do Ator: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return; 

        Ator obj;
        if( (obj = (Ator)arqAtores.buscar(id))!=null )
            System.out.println(obj);
        else
            System.out.println("Ator não encontrado");
        pausa();

    }

    public static void buscarAtorPorNome() throws Exception {

        System.out.println("\nBUSCA DE ATOR POR NOME");

        String nome;
        System.out.print("Nome: ");
        nome = console.nextLine();

        Ator obj;
        if( (obj = (Ator)arqAtores.buscarString(nome))!=null ) 
            System.out.println(obj);
        else
            System.out.println("Ator não encontrado");
        pausa();
    }

    public static void listarElenco() throws Exception {

        Object[] obj = arqElenco.listar();
        Elenco l;

        System.out.println("\nLISTA DE ELENCOS");
        for(int i=0; i<obj.length; i++) {
            l = (Elenco)obj[i];
            System.out.println(   "\nID.................: " + l.id
                                + "\nPersonagem.........: " + l.nomePersonagem
                                + "\nFilme..............: " + ((Filme)arqFilmes.buscar(l.idFilme)).getString() + " ("+l.idFilme+")" 
                                + "\nAtor...............: " + ((Ator)arqAtores.buscar(l.idAtor)).getString() + " ("+l.idAtor+")" );
        }
        pausa();

    }

    public static void incluirElenco() throws Exception {

        System.out.print("Digite os dados do elenco a ser inserido.  \nNome do personagem: ");
        String nome = console.nextLine();

        int idFilme;
        Filme fi;
        
        System.out.print("Filme: ");
        idFilme = Integer.parseInt(console.nextLine());
        if((fi = (Filme)arqFilmes.buscar(idFilme))!=null )
            System.out.println("           "+fi.getString());
        else {
            System.out.println("Filme não encontrado");
            pausa();
            return;
        }

        int idAtor;
        Ator at;
        
        System.out.print("Ator: ");
        idAtor = Integer.parseInt(console.nextLine());
        if((at = (Ator)arqAtores.buscar(idAtor))!=null )
            System.out.println("           "+at.getString());
        else {
            System.out.println("Ator não encontrado");
            pausa();
            return;
        }

        

        System.out.print("\nConfirma inclusão? ");
        char confirma = console.nextLine().charAt(0);
        if(confirma=='s' || confirma=='S') {
            Elenco obj = new Elenco(-1, idFilme, idAtor, nome);
            int id = arqElenco.incluir(obj);
            idxFilmeElenco.inserir(idFilme, id);
            idxAtorElenco.inserir(idAtor, id);
            System.out.println("Elenco incluído com ID: "+id);
        }

        pausa();
    }

    public static void alterarElenco() throws Exception {
       
        System.out.println("\nALTERAÇÃO DE ELENCO");
        
        int idFilme;
        Filme fi;
        int idAtor;
        Ator at;
        int id;
        System.out.print("ID do Elenco: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;

        Elenco obj;
        if( (obj = (Elenco)arqElenco.buscar(id))!=null ) {
            System.out.println(   "\nID.................: " + obj.id
                                + "\nPersonagem.........: " + obj.nomePersonagem
                                + "\nFilme..............: " + ((Filme)arqFilmes.buscar(obj.idFilme)).getString() + " ("+obj.idFilme+")" 
                                + "\nAtor...............: " + ((Ator)arqAtores.buscar(obj.idAtor)).getString() + " ("+obj.idAtor+")" );
            int idFilmeAnterior = obj.idFilme;
            int idAtorAnterior = obj.idAtor;
            
            System.out.print("Digite os dados do elenco a ser inserido.  \nNome do personagem: ");
            String nome = console.nextLine();

            System.out.print("Novo Filme: ");
            String auxFi = console.nextLine();
            if(auxFi.compareTo("")==0)
                idFilme=-1;
            else {
                idFilme = Integer.parseInt(auxFi);
                if((fi = (Filme)arqFilmes.buscar(idFilme))!=null )
                    System.out.println("                "+fi.getString());
                else {
                    System.out.print("Filme não encontrado");
                    pausa();
                    return;
                }
            }

            System.out.print("Novo Ator: ");
            String auxAt = console.nextLine();
            if(auxAt.compareTo("")==0)
                idAtor=-1;
            else {
                idAtor = Integer.parseInt(auxAt);
                if((at = (Ator)arqAtores.buscar(idAtor))!=null )
                    System.out.println("                "+at.getString());
                else {
                    System.out.print("Ator não encontrado");
                    pausa();
                    return;
                }
            }

            if(nome.length()>0 ) {
                System.out.print("\nConfirma alteração? ");
                char confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S') {

                obj.nomePersonagem = (nome.length()>0 ? nome : obj.nomePersonagem);
                obj.idFilme = idFilme;
                obj.idAtor = idAtor;

                if( arqElenco.alterar(obj) )
                        System.out.println("Elenco alterado.");
                    else
                        System.out.println("Elenco não pode ser alterado.");
                }
            }
       }
       else
           System.out.println("Elenco não encontrado");
       pausa();

    }
  
   
    public static void excluirElenco() throws Exception {

        System.out.println("\nEXCLUSÃO DE ELENCO");

        int id;
        System.out.print("ID do Elenco: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;

        Elenco obj;
        if( (obj = (Elenco)arqElenco.buscar(id))!=null ) {
            System.out.println(   "\nID.................: " + obj.id
                                + "\nPersonagem.........: " + obj.nomePersonagem
                                + "\nFilme..............: " + ((Filme)arqFilmes.buscar(obj.idFilme)).getString() + " ("+obj.idFilme+")" 
                                + "\nAtor...............: " + ((Ator)arqAtores.buscar(obj.idAtor)).getString() + " ("+obj.idAtor+")" );

             System.out.print("\nConfirma exclusão? ");
             char confirma = console.nextLine().charAt(0);
             if(confirma=='s' || confirma=='S') {
                 if( arqElenco.excluir(id) ) {
                     idxFilmeElenco.excluir(obj.idFilme, obj.id);
                     idxAtorElenco.excluir(obj.idAtor, obj.id);
                     System.out.println("Elenco excluído.");
                 }
             }
        }
        else
            System.out.println("Elenco não encontrado");
        pausa();

    }
   
   
    public static void buscarElenco() throws Exception {

        System.out.println("\nBUSCA DE ELENCO POR CÓDIGO");

        int codigo;
        System.out.print("Código: ");
        codigo = Integer.valueOf(console.nextLine());
        if(codigo <=0) 
            return; 

        Elenco obj;
        if( (obj = (Elenco)arqElenco.buscar(codigo))!=null ) {
            System.out.println(   "\nID.................: " + obj.id
                                + "\nPersonagem.........: " + obj.nomePersonagem
                                + "\nFilme..............: " + ((Filme)arqFilmes.buscar(obj.idFilme)).getString() + " ("+obj.idFilme+")" 
                                + "\nAtor...............: " + ((Ator)arqAtores.buscar(obj.idAtor)).getString() + " ("+obj.idAtor+")" );
        }
        else
            System.out.println("Elenco não encontrado");
        pausa();
    }

    public static void buscarAtorPorFilme() throws Exception {

        System.out.println("\nBUSCA DE ATOR POR FILME");
        Filme a;
        int op;
        System.out.print("\nDeseja buscar pelo ID do filme ou pelo Nome? (Digite 1 para ID, 2 para Nome) - ");
        op = Integer.valueOf(console.nextLine());

        int idFilme = -1;

        switch(op){
            default: pausa(); break;
            case 1: int id;
                    System.out.print("ID do Filme: ");
                    id = Integer.valueOf(console.nextLine());
                    if(id <=0) 
                        return;    

                    if( (a = (Filme)arqFilmes.buscar(id))!=null ){
                        idFilme = a.id;
                        break;
                    } else {
                        System.out.println("Filme não encontrado");
                        return;
                    }
                        
            case 2: String nome;
                    System.out.print("Nome do Filme: ");
                    nome = console.nextLine();
                    if( (a = (Filme)arqFilmes.buscarString(nome))!=null ){
                        idFilme = a.id;
                        break;
                    } else{
                        System.out.println("Filme não encontrado");
                        return;
                    }
                        
        } 

        int[] listaElenco = idxFilmeElenco.lista(idFilme);
        System.out.println("Atores presentes no filme: ");
        Elenco obj;
        for(int i = 0; i < listaElenco.length; i++){
            if( (obj = (Elenco)arqElenco.buscar(listaElenco[i]))!=null ) {
                System.out.println(((Ator)arqAtores.buscar(obj.idAtor)).getString() + " ("+obj.idAtor+") - Personagem: " + obj.nomePersonagem   );
            }
        }


        pausa();
    }

    public static void buscarFilmePorAtor() throws Exception {

        System.out.println("\nBUSCA DE FILME POR ATOR");
        Ator a;
        int op;
        System.out.print("\nDeseja buscar pelo ID do Ator ou pelo Nome? (Digite 1 para ID, 2 para Nome) - ");
        op = Integer.valueOf(console.nextLine());

        int idAtor = -1;

        switch(op){
            default: pausa(); break;
            case 1: int id;
                    System.out.print("ID do Ator: ");
                    id = Integer.valueOf(console.nextLine());
                    if(id <=0) 
                        return;    

                    if( (a = (Ator)arqAtores.buscar(id))!=null ){
                        idAtor = a.id;
                        break;
                    } else {
                        System.out.println("Ator não encontrado");
                        return;
                    }
                        
            case 2: String nome;
                    System.out.print("Nome do Ator: ");
                    nome = console.nextLine();
                    if( (a = (Ator)arqAtores.buscarString(nome))!=null ){
                        idAtor = a.id;
                        break;
                    } else{
                        System.out.println("Ator não encontrado");
                        return;
                    }
                        
        } 

        int[] listaElenco = idxAtorElenco.lista(idAtor);
        System.out.println("Filmes em que o ator aparece: ");
        Elenco obj;
        for(int i = 0; i < listaElenco.length; i++){
            if( (obj = (Elenco)arqElenco.buscar(listaElenco[i]))!=null ) {
                System.out.println(((Filme)arqFilmes.buscar(obj.idFilme)).getString() + " ("+obj.idFilme+") - Personagem: " + obj.nomePersonagem );
            }
        }


        pausa();
    }



   
    public static void pausa() throws Exception {
        System.out.print("\nPressione ENTER para continuar ...");
        console.nextLine();
    }


    

}
