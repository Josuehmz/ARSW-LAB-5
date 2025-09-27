/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.filters;

import edu.eci.arsw.model.Blueprint;

/**
 *
 * @author hcadavid
 */
public interface BlueprintFilter {
    
    /**
     * Aplica el filtro al blueprint dado
     * @param blueprint el blueprint a filtrar
     * @return el blueprint filtrado
     */
    public Blueprint filterBlueprint(Blueprint blueprint);
    
}
