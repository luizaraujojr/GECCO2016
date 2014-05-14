package br.unirio.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import br.unirio.overwork.builders.WorkPackageProjectBuilder;
import br.unirio.overwork.model.Project;
import br.unirio.overwork.model.Activity;

public class InstanceReader {
	
	//
	
	public Project execute(String path)
	{
//		File file = new File(path);
//		Project project = new Project();
//
//        try 
//        {
//            Scanner scanner = new Scanner(file);
//
//            while (scanner.hasNextLine()) 
//            {
//                String line = scanner.nextLine();
//    			String[] tokens = line.split(";");
//            
//    			Activity activity = new Activity (tokens.length - 2);
//    			
//    			int duration = Integer.parseInt(tokens[1].trim());
//    			activity.setDuration(duration);
//
//    			for (int i = 2; i < tokens.length; i++)
//    			{
//					int coverage = Integer.parseInt(tokens[i].trim());
//					activity.setCoverage(i-2, coverage);
//    			}
//    			
//    			Project.add(activity);
//            }
//                      
//            scanner.close();
//        } 
//        catch (FileNotFoundException e) 
//        {
//            e.printStackTrace();
//        }           

		return createProject();
	}
	
	
	private static Project createProject()
	{
		WorkPackageProjectBuilder builder = new WorkPackageProjectBuilder();
		
		builder.addWorkPackage("Usu�rios")
			.addDataFunction("usuario", 7)
			.addTransactionalFunction("Cadastro de usu�rio", 3)
			.addTransactionalFunction("Lista de usu�rios ", 4)
			.addTransactionalFunction("Consulta de usu�rio", 4);

		builder.addWorkPackage("Professores")
			.addDataFunction("professor", 7)
			.addTransactionalFunction("Cadastro de professor", 4)
			.addTransactionalFunction("Lista de professores", 4)
			.addTransactionalFunction("Consulta de professor", 5);
		
		builder.addWorkPackage("�reas")
			.addDataFunction("area", 7)
			.addTransactionalFunction("Cadastro de �rea", 4)
			.addTransactionalFunction("Lista de �reas", 4)
			.addTransactionalFunction("Lista de sub-�reas", 4)
			.addTransactionalFunction("Consulta de �rea", 4);
		
		builder.addWorkPackage("Aluno")
			.addDataFunction("aluno", 10)
			.addTransactionalFunction("Cadastro de aluno", 6)
			.addTransactionalFunction("Consulta de aluno", 7);
		
		builder.addWorkPackage("Disciplinas")
			.addDataFunction("disciplina", 7)
			.addTransactionalFunction("Cadastro de disciplina", 4)
			.addTransactionalFunction("Lista de disciplinas", 4)
			.addTransactionalFunction("Consulta de disciplina", 5);
		
		builder.addWorkPackage("Turmas")
			.addDataFunction("turma", 7)
			.addDataFunction("turmasolicitada", 7)
			.addTransactionalFunction("Cadastro de turma", 6)
			.addTransactionalFunction("Cadastro de turma solicitada", 6)
			.addTransactionalFunction("Lista de turmas", 4)
			.addTransactionalFunction("Consulta de turma", 7)
			.addTransactionalFunction("Lista de turmas solicitadas", 4);
		
		builder.addWorkPackage("Inscri��es")
			.addDataFunction("inscricao", 7)
			.addTransactionalFunction("Cadastro de inscri��o", 6)
			.addTransactionalFunction("Consulta de inscri��o", 7)
			.addTransactionalFunction("Gera��o de comprovante", 7);
		
		return builder.execute();
	}
	
}