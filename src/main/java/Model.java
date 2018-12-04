import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.db4o.ObjectSet;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;

public class Model implements Subject{
	
	private List<Observer> observers = new LinkedList<Observer>();
	private List<Sighting> sightings = new LinkedList<Sighting>();
	private List<Instruction> instructions = new LinkedList<Instruction>();
	
	private static Model uniqueInstance;
	
	private Model(){}
	
	public static Model getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}
	
	public void registerObserver(Observer observer){
		observers.add(observer);
	}
	
	public void notifyObservers(long chatId, String sightingsData){
		for(Observer observer:observers){
			observer.update(chatId, sightingsData);
		}
	}
	
	public void addSighting(Sighting sighting) {
		this.sightings.add(sighting);
				
	}
	
	
	public void addInstruction(Instruction instruction){
		this.instructions.add(instruction);
	}
	
	
	public void searchSighting(Update update){
		
		/*
		File f = new File("bd/sightings.db4o");
		if (f.exists()) {
			f.delete();
		}
		*/
		
		String sightingsParaMostrarNoBot = "\nISS Sightings for Jacareí: ";
		
		ScrapingSighting lista = new ScrapingSighting();
		ArrayList<String> listaDaRaspagem = lista.ScrapingSighting();

		if(listaDaRaspagem != null){
			
			BancoDeDadosSightings bd = new BancoDeDadosSightings();
			bd.cadastrarSightingsNoBD(listaDaRaspagem);
			
			ObjectSet<Sighting> listaDoBD = bd.listarSightingsDoBD();
			
			for (int i =0; i<listaDoBD.size(); i++) {
				
				sightingsParaMostrarNoBot += " \n";
				sightingsParaMostrarNoBot += "\nDate: "+listaDoBD.get(i).getDate();
				sightingsParaMostrarNoBot += "\nTime Visible: "+listaDoBD.get(i).getTimeVisible();
				sightingsParaMostrarNoBot += "\nMax Height: "+listaDoBD.get(i).getMaxHeight();
				sightingsParaMostrarNoBot += "\nAppears: "+listaDoBD.get(i).getAppears();
				sightingsParaMostrarNoBot += "\nDisappears: "+listaDoBD.get(i).getDisappears();
			}
			
			bd.fecharBD();
			
			this.notifyObservers(update.message().chat().id(), sightingsParaMostrarNoBot); //sightingsParaMostrarNoBot
		} else {
			this.notifyObservers(update.message().chat().id(), "Não há sighting(s) no momento");
		}
	}

		
	
	public void searchInstruction(Update update){
		
		String instructionsParaMostrarNoBot = "\nInstructions";
		
		ScrapingInstructions lista = new ScrapingInstructions();
		ArrayList<String> listaDaRaspagem = lista.ScrapingInstructions();
		
		BancoDeDadosInstructions bd = new BancoDeDadosInstructions();
		bd.cadastrarInstructionsNoBD(listaDaRaspagem);
		
		ObjectSet<Instruction> listaDoBD = bd.listarInstructionsDoBD();
		
		if(listaDaRaspagem != null){
						
			for (int i =0; i<listaDoBD.size(); i++) {
				
				instructionsParaMostrarNoBot += "\n\nDate: "+listaDoBD.get(i).getDate();
				instructionsParaMostrarNoBot += "\n\nTime Visible: "+listaDoBD.get(i).getTimeVisible();
				instructionsParaMostrarNoBot += "\n\nMax Height: "+listaDoBD.get(i).getMaxHeight();
				instructionsParaMostrarNoBot += "\n\nAppears: "+listaDoBD.get(i).getAppears();
				instructionsParaMostrarNoBot += "\n\nDisappears: "+listaDoBD.get(i).getDisappears();
			}
			
			bd.fecharBD();

			this.notifyObservers(update.message().chat().id(), instructionsParaMostrarNoBot);
		} else {
			this.notifyObservers(update.message().chat().id(), "Não há instruction(s) no momento");
		}
				
	}
			
}
