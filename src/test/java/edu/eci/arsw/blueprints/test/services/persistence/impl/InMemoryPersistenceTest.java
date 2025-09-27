/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.services.persistence.impl;

import edu.eci.arsw.model.Blueprint;
import edu.eci.arsw.model.Point;
import edu.eci.arsw.persistence.BlueprintNotFoundException;
import edu.eci.arsw.persistence.BlueprintPersistenceException;
import edu.eci.arsw.persistence.impl.InMemoryBlueprintPersistence;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull(ibpp.getBlueprint(bp.getAuthor(), bp.getName()), "Loading a previously stored blueprint returned null.");
        
        assertEquals(ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp, "Loading a previously stored blueprint returned a different blueprint.");
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }

    @Test
    public void getBlueprintsByAuthorTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        
        // Agregar varios blueprints del mismo autor
        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "blueprint1", pts1);
        
        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30)};
        Blueprint bp2 = new Blueprint("john", "blueprint2", pts2);
        
        Point[] pts3 = new Point[]{new Point(40, 40), new Point(50, 50)};
        Blueprint bp3 = new Blueprint("alice", "blueprint3", pts3);
        
        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);
        ibpp.saveBlueprint(bp3);
        
        // Obtener blueprints de john
        Set<Blueprint> johnBlueprints = ibpp.getBlueprintsByAuthor("john");
        assertEquals(2, johnBlueprints.size(), "Should return 2 blueprints for john");
        assertTrue(johnBlueprints.contains(bp1), "Should contain blueprint1");
        assertTrue(johnBlueprints.contains(bp2), "Should contain blueprint2");
        assertFalse(johnBlueprints.contains(bp3), "Should not contain alice's blueprint");
        
        // Obtener blueprints de alice
        Set<Blueprint> aliceBlueprints = ibpp.getBlueprintsByAuthor("alice");
        assertEquals(1, aliceBlueprints.size(), "Should return 1 blueprint for alice");
        assertTrue(aliceBlueprints.contains(bp3), "Should contain blueprint3");
    }

    @Test
    public void getBlueprintsByNonExistentAuthorTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        
        try {
            ibpp.getBlueprintsByAuthor("nonexistent");
            fail("Should throw BlueprintNotFoundException for non-existent author");
        } catch (BlueprintNotFoundException ex) {
            // Expected behavior
        }
    }

    @Test
    public void getAllBlueprintsTest() throws BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        
        // Verificar que ya existe un blueprint por defecto
        Set<Blueprint> allBlueprints = ibpp.getAllBlueprints();
        int initialSize = allBlueprints.size();
        assertTrue(initialSize >= 1, "Should have at least 1 blueprint initially");
        
        // Agregar más blueprints
        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "blueprint1", pts1);
        
        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30)};
        Blueprint bp2 = new Blueprint("alice", "blueprint2", pts2);
        
        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);
        
        allBlueprints = ibpp.getAllBlueprints();
        assertEquals(initialSize + 2, allBlueprints.size(), "Should have initial + 2 blueprints");
    }
    
}
