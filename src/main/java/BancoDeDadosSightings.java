
import java.util.ArrayList;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class BancoDeDadosSightings {
	
	ObjectContainer sightings = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/sightings.db4o");
		
	public void cadastrarSightingsNoBD(ArrayList<String> sightingList) {
		
		Query query = sightings.query();
		query.constrain(Sighting.class);
		ObjectSet<Sighting> listaSightingsDoBD = query.execute();
		
		for (Sighting s : listaSightingsDoBD) {
			sightings.delete(s);
		}

		for (int i = 0; i < sightingList.size(); i++) {
			if (sightingList.get(i).equals("Date: ")) {
				
				Sighting s = new Sighting();
				
				s.setDate(sightingList.get(i + 1));
				s.setTimeVisible(sightingList.get(i + 3));
				s.setMaxHeight(sightingList.get(i + 5));
				s.setAppears(sightingList.get(i + 7));
				s.setDisappears(sightingList.get(i + 9));
				
				sightings.store(s);
				sightings.commit();
			}
		}
	}

	public ObjectSet<Sighting> listarSightingsDoBD() {
		
		Query query = sightings.query();
		query.constrain(Sighting.class);
		ObjectSet<Sighting> listaSightingsDoBD = query.execute();
		
		return listaSightingsDoBD;
	}
	
	public void fecharBD() {
		sightings.close();
	}
}
