/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.services.filters.impl;

import edu.eci.arsw.filters.impl.RedundancyBlueprintFilter;
import edu.eci.arsw.model.Blueprint;
import edu.eci.arsw.model.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hcadavid
 */
public class RedundancyBlueprintFilterTest {
    
    @Test
    public void testRedundancyFilter() {
        RedundancyBlueprintFilter filter = new RedundancyBlueprintFilter();
        
        // Crear blueprint con puntos redundantes
        Point[] points = new Point[]{
            new Point(10, 10),
            new Point(10, 10), // Duplicado consecutivo
            new Point(20, 20),
            new Point(20, 20), // Duplicado consecutivo
            new Point(20, 20), // Otro duplicado consecutivo
            new Point(30, 30)
        };
        
        Blueprint originalBlueprint = new Blueprint("author", "test", points);
        Blueprint filteredBlueprint = filter.filterBlueprint(originalBlueprint);
        
        // Verificar que los duplicados consecutivos fueron removidos
        assertEquals(3, filteredBlueprint.getPoints().size(), "Should have 3 unique points");
        assertEquals(new Point(10, 10), filteredBlueprint.getPoints().get(0));
        assertEquals(new Point(20, 20), filteredBlueprint.getPoints().get(1));
        assertEquals(new Point(30, 30), filteredBlueprint.getPoints().get(2));
    }
    
    @Test
    public void testEmptyBlueprint() {
        RedundancyBlueprintFilter filter = new RedundancyBlueprintFilter();
        Blueprint emptyBlueprint = new Blueprint("author", "empty");
        Blueprint filteredBlueprint = filter.filterBlueprint(emptyBlueprint);
        
        assertEquals(0, filteredBlueprint.getPoints().size(), "Empty blueprint should remain empty");
    }
    
    @Test
    public void testNoRedundantPoints() {
        RedundancyBlueprintFilter filter = new RedundancyBlueprintFilter();
        
        Point[] points = new Point[]{
            new Point(10, 10),
            new Point(20, 20),
            new Point(30, 30),
            new Point(40, 40)
        };
        
        Blueprint originalBlueprint = new Blueprint("author", "test", points);
        Blueprint filteredBlueprint = filter.filterBlueprint(originalBlueprint);
        
        assertEquals(4, filteredBlueprint.getPoints().size(), "Should keep all non-redundant points");
    }
}
