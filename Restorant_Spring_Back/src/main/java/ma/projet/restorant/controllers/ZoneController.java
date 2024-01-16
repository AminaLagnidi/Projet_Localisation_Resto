package ma.projet.restorant.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import ma.projet.restorant.entities.Resto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.projet.restorant.entities.Ville;
import ma.projet.restorant.entities.Zone;
import ma.projet.restorant.reposit.VilleRepository;
import ma.projet.restorant.reposit.ZoneRepository;

@RestController
@RequestMapping("zones")
public class ZoneController {
	@Autowired
	private ZoneRepository zoneRepository;
	@Autowired
	private VilleRepository  villeRepository;

	@PostMapping("/save")
	public void save(@RequestBody Zone Zone) {
		zoneRepository.save(Zone);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable(required = true) String id) {
		Zone s = zoneRepository.findById(Integer.parseInt(id));
		zoneRepository.delete(s);
	}

	@GetMapping("/page")
	public String showZonePage() {
		return "zone"; // Assurez-vous que "zone" correspond au nom de votre fichier HTML.
	}
	@GetMapping("/all")
	public List<Zone> findAll() {
		return zoneRepository.findAll();
	}

	@GetMapping(value = "/count")
	public long countResto() {
		return zoneRepository.count();
	}

	@GetMapping("/byName")
	public List<Zone> findByNom(@RequestParam(required = false) String nom) {
		return zoneRepository.findByNom(nom);
	}

	@GetMapping("/findById/{id}")
	public Zone findById(@PathVariable(required = true) String id) {
		return zoneRepository.findById(Integer.parseInt(id));
	}

	@GetMapping("/findByIdVille/{id}")
	public List<Zone> findAllById(@PathVariable(required = false) String id){
		Ville v = villeRepository.findById(Integer.parseInt(id));
        return v.getZones();
    }

	@GetMapping("/restaurantsByZone/{id}")
	public ResponseEntity<Map<String, Integer>> getRestaurantsByZone(@PathVariable(required = false) String id) {
		try {
			Zone zone = zoneRepository.findById(Integer.parseInt(id));

			if (zone == null) {
				return ResponseEntity.notFound().build();
			}

			List<Resto> restaurants = zone.getRestos();
			Map<String, Integer> result = new HashMap<>();

			for (Resto restaurant : restaurants) {
				// Assuming each restaurant is associated with a unique zone name
				String zoneName = restaurant.getZone().getNom();

				// Increment the count for the respective zone
				result.put(zoneName, result.getOrDefault(zoneName, 0) + 1);
			}

			return ResponseEntity.ok(result);
		} catch (NumberFormatException e) {
			// Handle the case where id is not a valid integer
			return ResponseEntity.badRequest().build();
		}
	}
}
