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
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */
@Component
@Primary
public class RedundancyBlueprintFilter implements BlueprintFilter {
    
    @Override
    public Blueprint filterBlueprint(Blueprint blueprint) {
        if (blueprint == null || blueprint.getPoints().isEmpty()) {
            return blueprint;
        }
        
        List<Point> originalPoints = blueprint.getPoints();
        List<Point> filteredPoints = new ArrayList<>();
        
        // Agregar el primer punto
        filteredPoints.add(originalPoints.get(0));
        
        // Agregar solo puntos que no sean iguales al anterior
        for (int i = 1; i < originalPoints.size(); i++) {
            Point currentPoint = originalPoints.get(i);
            Point previousPoint = originalPoints.get(i - 1);
            
            if (!currentPoint.equals(previousPoint)) {
                filteredPoints.add(currentPoint);
            }
        }
        
        // Crear un nuevo blueprint con los puntos filtrados
        Point[] pointArray = filteredPoints.toArray(new Point[0]);
        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), pointArray);
    }
    
}
