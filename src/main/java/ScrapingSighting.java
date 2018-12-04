
import java.util.ArrayList;
import java.util.List;

import com.jaunt.JauntException;
import com.jaunt.UserAgent;

public class ScrapingSighting {
	
	public ArrayList<String> ScrapingSighting() {
		
		ArrayList<String> listaRaspagem = new ArrayList();

		try {
			UserAgent userAgent = new UserAgent(); 
			userAgent.settings.autoSaveAsHTML = true; 

			userAgent.visit("https://spotthestation.nasa.gov/sightings/view.cfm?country=Brazil&region=None&city=Jacarei"); 
						
			com.jaunt.Elements titulos = userAgent.doc.findEvery("<th>");
			com.jaunt.Elements dados = userAgent.doc.findEvery("<td>");

			int ds = 0;
			int cont = 0;

			while (ds < (dados.size() - 5)) {

				if ((ds == 5) || (ds == 11) || (ds == 17) || (ds == 23) || (ds == 29) || (ds == 35) || (ds == 41)
						|| (ds == 47) || (ds == 53) || (ds == 59)) {
					ds++;
					continue;
				}

				for (int contador = 0; contador < (5); contador++) {
					listaRaspagem.add(titulos.getElement(contador).getChildText()+": ");
					listaRaspagem.add(dados.getElement(ds).getChildText().trim().replace("&nbsp;", "").replace("&deg;", "°"));
					ds++;
				}

			}
			

		} catch (JauntException e) { 
			System.err.println(e);
		}
		return listaRaspagem;

	}

}
