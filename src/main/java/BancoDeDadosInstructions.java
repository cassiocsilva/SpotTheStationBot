
import java.util.ArrayList;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class BancoDeDadosInstructions {
	
	ObjectContainer instructions = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/instructions.db4o");
	
	public void cadastrarInstructionsNoBD(ArrayList<String> instructionsList) {
		
		Query query = instructions.query();
		query.constrain(Instruction.class);
		ObjectSet<Instruction> listaInstructionsDoBD = query.execute();
		
		for (Instruction i : listaInstructionsDoBD) {
			instructions.delete(i);
		}

		for (int c = 0; c < instructionsList.size(); c++) {
			
			if (instructionsList.get(c).equals("Time")) {
				
				Instruction i = new Instruction();
				
				i.setDate(instructionsList.get(c + 1));
				i.setTimeVisible(instructionsList.get(c + 3));
				i.setMaxHeight(instructionsList.get(c + 5));
				i.setAppears(instructionsList.get(c + 7));
				i.setDisappears(instructionsList.get(c + 9));
				
				instructions.store(i);
				instructions.commit();
			}
		}
	}

	public ObjectSet<Instruction> listarInstructionsDoBD() {

		Query query = instructions.query();
		query.constrain(Instruction.class);
		ObjectSet<Instruction> listaInstructionsDoBD = query.execute();

		return listaInstructionsDoBD;
	}
	
	public void fecharBD() {
		instructions.close();
	}
	
}