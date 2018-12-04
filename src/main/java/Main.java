import com.pengrad.telegrambot.request.SendMessage;

public class Main {

	private static Model model;
	
	public static void main(String[] args) {
				
		
		model = Model.getInstance();
		initializeModel(model);
		View view = new View(model);
		model.registerObserver(view); //connection Model -> View
		view.receiveUsersMessages();

	}
	
	public static void initializeModel(Model model){
		//model.addSighting(new Sighting("date", "timeVisible","maxHeight","appears","disappears"));
		//model.addStudent(new Sighting("thomas", "222"));
		
		//model.addInstruction(new Instruction("date", "timeVisible","maxHeight","appears","disappears"));
	}

}

