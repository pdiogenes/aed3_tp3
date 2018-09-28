/*
 * Autores: Ana Flávia Dias Rodrigues, Bernardo Victor de Souza e Silva, Bertone Cândido de Sousa Lima, Joao Victor da Silva,Temístocles Altivo Schwartz
 * Data: 21/08/2018
 * */


import java.io.*;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

//import aed3.ArquivoIndexado;
//import aed3.CrudGeneros;

public class MainProgram {
	
	private static Scanner console;
	private static CrudFilmes crudF;
	private static CrudGeneros crudG;

	public static void main(String[] args) {
		console = new Scanner(System.in);	
		int opcao = -1;

		RandomAccessFile arq;

		try{
			crudG = new CrudGeneros();
			crudF = new CrudFilmes();
		}catch(Exception e){e.printStackTrace();}

		System.out.println("Bem-vindo ao CRUD de filmes!");

			int id;

			try{
				while(opcao != 0) {
					System.out.print("\n\n\n-----GESTOR DE FILMES-----\n"+
					"0 - Finalizar programa\n"+
					"1 - Gerenciar filmes\n"+
					"2 - Gerenciar Generos\n"+
					"Inserir : ");
					opcao = Integer.valueOf(console.nextLine());
					if( opcao == 1 ){
						ViewFilme viewFilme = new ViewFilme();
						viewFilme.menu();
					}
					else if(opcao == 2){
						ViewGenero viewGen = new ViewGenero();
						viewGen.menu();
					}
						
					//TRATAR EXCEÇÕES
				}
			}catch(Exception e){e.printStackTrace();}
					
				
	}//end main()

}
