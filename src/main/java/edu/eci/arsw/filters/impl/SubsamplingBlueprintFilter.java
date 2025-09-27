/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.filters.impl;

import edu.eci.arsw.filters.BlueprintFilter;
import edu.eci.arsw.model.Blueprint;
import edu.eci.arsw.model.Point;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */
@Component

public class SubsamplingBlueprintFilter implements BlueprintFilter {
    
    @Override
    public Blueprint filterBlueprint(Blueprint blueprint) {
        if (blueprint == null || blueprint.getPoints().isEmpty()) {
            return blueprint;
        }
        
        List<Point> originalPoints = blueprint.getPoints();
        List<Point> filteredPoints = new ArrayList<>();
        
        // Tomar puntos intercalados (1 de cada 2)
        for (int i = 0; i < originalPoints.size(); i += 2) {
            filteredPoints.add(originalPoints.get(i));
        }
        
        // Crear un nuevo blueprint con los puntos filtrados
        Point[] pointArray = filteredPoints.toArray(new Point[0]);
        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), pointArray);
    }
    
}
