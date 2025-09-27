/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.persistence.impl;

import edu.eci.arsw.model.Blueprint;
import edu.eci.arsw.model.Point;
import edu.eci.arsw.persistence.BlueprintNotFoundException;
import edu.eci.arsw.persistence.BlueprintPersistenceException;
import edu.eci.arsw.persistence.BlueprintsPersistence;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */
@Component
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        
        Point[] pts1 = new Point[]{new Point(140, 140), new Point(115, 115)};
        Blueprint bp1 = new Blueprint("_authorname_", "_bpname_ ", pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        
        Point[] pts2 = new Point[]{
            new Point(0, 0),
            new Point(0, 0),
            new Point(50, 0),
            new Point(50, 50),
            new Point(50, 50),
            new Point(0, 50),
            new Point(0, 0)
        };
        Blueprint bp2 = new Blueprint("Juan", "Casa Moderna", pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        
        Point[] pts3 = new Point[]{
            new Point(10, 10),
            new Point(90, 10),
            new Point(90, 90),
            new Point(10, 90),
            new Point(10, 10),
            new Point(50, 50),
            new Point(50, 10),
            new Point(50, 90)
        };
        Blueprint bp3 = new Blueprint("Juan", "Edificio Comercial", pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
        
        Point[] pts4 = new Point[]{
            new Point(100, 100),
            new Point(200, 100),
            new Point(200, 200),
            new Point(150, 200),
            new Point(150, 250),
            new Point(100, 250),
            new Point(100, 100)
        };
        Blueprint bp4 = new Blueprint("Maria", "Villa Residencial", pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(), bp4.getName()), bp4);
        
        Point[] pts5 = new Point[]{
            new Point(0, 0),
            new Point(10, 5),
            new Point(20, 10),
            new Point(30, 15),
            new Point(40, 20),
            new Point(50, 25),
            new Point(60, 30),
            new Point(70, 35),
            new Point(80, 40),
            new Point(90, 45)
        };
        Blueprint bp5 = new Blueprint("Carlos", "Diseño Complejo", pts5);
        blueprints.put(new Tuple<>(bp5.getAuthor(), bp5.getName()), bp5);
        
        // Agregando tres planos adicionales con dos asociados al mismo autor
        Point[] pts6 = new Point[]{
            new Point(25, 25),
            new Point(75, 25),
            new Point(75, 75),
            new Point(25, 75),
            new Point(25, 25),
            new Point(50, 50)
        };
        Blueprint bp6 = new Blueprint("Ana", "Oficina Central", pts6);
        blueprints.put(new Tuple<>(bp6.getAuthor(), bp6.getName()), bp6);
        
        Point[] pts7 = new Point[]{
            new Point(5, 5),
            new Point(45, 5),
            new Point(45, 35),
            new Point(35, 35),
            new Point(35, 15),
            new Point(15, 15),
            new Point(15, 35),
            new Point(5, 35),
            new Point(5, 5)
        };
        Blueprint bp7 = new Blueprint("Ana", "Centro Comercial", pts7);
        blueprints.put(new Tuple<>(bp7.getAuthor(), bp7.getName()), bp7);
        
        Point[] pts8 = new Point[]{
            new Point(120, 120),
            new Point(180, 120),
            new Point(180, 160),
            new Point(160, 160),
            new Point(160, 140),
            new Point(140, 140),
            new Point(140, 160),
            new Point(120, 160),
            new Point(120, 120)
        };
        Blueprint bp8 = new Blueprint("Pedro", "Hospital Regional", pts8);
        blueprints.put(new Tuple<>(bp8.getAuthor(), bp8.getName()), bp8);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        Tuple<String, String> key = new Tuple<>(bp.getAuthor(), bp.getName());
        // Operación atómica: solo inserta si no existe, evita condición de carrera
        Blueprint existingBlueprint = blueprints.putIfAbsent(key, bp);
        if (existingBlueprint != null) {
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint blueprint = blueprints.get(new Tuple<>(author, bprintname));
        if (blueprint == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + " - " + bprintname);
        }
        return blueprint;
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>();
        // Crea una copia de los valores para evitar ConcurrentModificationException
        // durante la iteración en entorno concurrente
        for (Blueprint bp : new HashSet<>(blueprints.values())) {
            if (bp.getAuthor().equals(author)) {
                authorBlueprints.add(bp);
            }
        }
        if (authorBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }
        return authorBlueprints;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        return new HashSet<>(blueprints.values());
    }
    
    @Override
    public boolean updateBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        Tuple<String, String> key = new Tuple<>(bp.getAuthor(), bp.getName());
        // Intenta actualizar primero (si existe), operación atómica
        Blueprint previousValue = blueprints.replace(key, bp);
        if (previousValue != null) {
            return true; // Blueprint fue actualizado
        } else {
            // Si no existía, intenta crear usando putIfAbsent (operación atómica)
            Blueprint existingValue = blueprints.putIfAbsent(key, bp);
            return existingValue != null; // false = creado, true = ya existía (muy raro)
        }
    }
    
}
