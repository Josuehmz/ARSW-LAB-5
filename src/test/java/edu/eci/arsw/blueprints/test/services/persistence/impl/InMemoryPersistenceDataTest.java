/*
 * Test para verificar que los datos de inicialización estén correctos
 */
package edu.eci.arsw.blueprints.test.services.persistence.impl;

import edu.eci.arsw.model.Blueprint;
import edu.eci.arsw.persistence.BlueprintNotFoundException;
import edu.eci.arsw.persistence.impl.InMemoryBlueprintPersistence;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author usuario
 */
public class InMemoryPersistenceDataTest {
    
    @Test
    public void testInitialDataLoad() throws BlueprintNotFoundException {
        InMemoryBlueprintPersistence persistence = new InMemoryBlueprintPersistence();
        
        // Verificar que se cargaron todos los blueprints
        Set<Blueprint> allBlueprints = persistence.getAllBlueprints();
        assertEquals(5, allBlueprints.size(), "Debe haber 5 blueprints cargados inicialmente");
        
        // Verificar que Juan tiene dos blueprints
        Set<Blueprint> juanBlueprints = persistence.getBlueprintsByAuthor("Juan");
        assertEquals(2, juanBlueprints.size(), "Juan debe tener exactamente 2 blueprints");
        
        // Verificar que los nombres de los blueprints de Juan son correctos
        boolean hasCasaModerna = false;
        boolean hasEdificioComercial = false;
        
        for (Blueprint bp : juanBlueprints) {
            if ("Casa Moderna".equals(bp.getName())) {
                hasCasaModerna = true;
                // Verificar que tiene puntos redundantes (antes del filtrado)
                assertEquals(7, bp.getPoints().size(), "Casa Moderna debe tener 7 puntos");
            }
            if ("Edificio Comercial".equals(bp.getName())) {
                hasEdificioComercial = true;
                // Verificar que tiene 8 puntos
                assertEquals(8, bp.getPoints().size(), "Edificio Comercial debe tener 8 puntos");
            }
        }
        
        assertTrue(hasCasaModerna, "Juan debe tener el blueprint 'Casa Moderna'");
        assertTrue(hasEdificioComercial, "Juan debe tener el blueprint 'Edificio Comercial'");
        
        // Verificar que Maria tiene un blueprint
        Set<Blueprint> mariaBlueprints = persistence.getBlueprintsByAuthor("Maria");
        assertEquals(1, mariaBlueprints.size(), "Maria debe tener exactamente 1 blueprint");
        assertEquals("Villa Residencial", mariaBlueprints.iterator().next().getName(), 
                    "El blueprint de Maria debe ser 'Villa Residencial'");
        
        // Verificar que Carlos tiene un blueprint
        Set<Blueprint> carlosBlueprints = persistence.getBlueprintsByAuthor("Carlos");
        assertEquals(1, carlosBlueprints.size(), "Carlos debe tener exactamente 1 blueprint");
        Blueprint carlosBlueprint = carlosBlueprints.iterator().next();
        assertEquals("El blueprint de Carlos debe ser 'Diseño Complejo'", 
                    "Diseño Complejo", carlosBlueprint.getName());
        assertEquals(10, carlosBlueprint.getPoints().size(), "Diseño Complejo debe tener 10 puntos");
        
        // Verificar que el autor original también existe
        Blueprint originalBlueprint = persistence.getBlueprint("_authorname_", "_bpname_ ");
        assertNotNull(originalBlueprint, "El blueprint original debe existir");
        assertEquals(2, originalBlueprint.getPoints().size(), "El blueprint original debe tener 2 puntos");
    }
    
    @Test
    public void testAuthorWithMultipleBlueprints() throws BlueprintNotFoundException {
        InMemoryBlueprintPersistence persistence = new InMemoryBlueprintPersistence();
        
        // Obtener blueprints específicos de Juan
        Blueprint casaModerna = persistence.getBlueprint("Juan", "Casa Moderna");
        Blueprint edificioComercial = persistence.getBlueprint("Juan", "Edificio Comercial");
        
        assertNotNull(casaModerna, "Casa Moderna debe existir");
        assertNotNull(edificioComercial, "Edificio Comercial debe existir");
        
        assertEquals(casaModerna.getAuthor(), edificioComercial.getAuthor(), 
                    "Ambos blueprints deben ser del mismo autor");
        assertEquals("Juan", casaModerna.getAuthor(), "El autor debe ser Juan");
        
        // Verificar que son diferentes blueprints
        assertNotEquals(casaModerna.getName(), edificioComercial.getName(), 
                       "Los blueprints deben tener nombres diferentes");
    }
    
    @Test
    public void testNonExistentAuthor() {
        InMemoryBlueprintPersistence persistence = new InMemoryBlueprintPersistence();
        
        // Intentar obtener blueprints de un autor que no existe
        assertThrows(BlueprintNotFoundException.class, () -> {
            persistence.getBlueprintsByAuthor("AutorInexistente");
        });
    }
}
