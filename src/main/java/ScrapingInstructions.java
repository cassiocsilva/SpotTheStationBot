import java.util.ArrayList;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

public class ScrapingInstructions {
	
	public ArrayList<String> ScrapingInstructions() {
		
		ArrayList<String> listaRaspagem2 = new ArrayList();

		try {
			UserAgent userAgent = new UserAgent(); 
			userAgent.settings.autoSaveAsHTML = true; 
			userAgent.visit("https://spotthestation.nasa.gov/sightings/view.cfm?country=Brazil&region=None&city=Jacarei");
			
			com.jaunt.Elements topicos = userAgent.doc.findEvery("<strong>");
			com.jaunt.Elements instructions = userAgent.doc.findEvery("<p class=\"smallTxt\">");

			for (int c = 0; c < instructions.size(); c++) {

				//System.out.println(topicos.getElement(c+1).getChildText());
				listaRaspagem2.add(topicos.getElement(c+1).getChildText());
				//System.out.println(instructions.getElement(c).getChildText());
				listaRaspagem2.add(instructions.getElement(c).getChildText());
			}
			
			

		} catch (JauntException e) { // if title element isn't found or
										// HTTP/connection error occurs, handle
										// JauntException.
			System.err.println(e);
		}
		
		return listaRaspagem2;

	}

}
