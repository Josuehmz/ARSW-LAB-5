/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.model.Blueprint;
import edu.eci.arsw.persistence.BlueprintNotFoundException;
import edu.eci.arsw.persistence.BlueprintPersistenceException;
import edu.eci.arsw.services.BlueprintsServices;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    
    @Autowired
    private BlueprintsServices blueprintsServices;
    
    @GetMapping
    public ResponseEntity<?> getAllBlueprints() {
        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Obteniendo todos los blueprints");
        try {
            Set<Blueprint> blueprints = blueprintsServices.getAllBlueprints();
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Se encontraron " + blueprints.size() + " blueprints en total");
            return new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al obtener los blueprints", HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Buscando blueprints para el autor: " + author);
        try {
            Set<Blueprint> blueprints = blueprintsServices.getBlueprintsByAuthor(author);
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Encontrados " + blueprints.size() + " blueprints para el autor: " + author);
            return new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.WARNING, "No se encontraron blueprints para el autor: " + author, ex);
            return new ResponseEntity<>("No se encontraron blueprints para el autor: " + author, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{author}/{bpname}")
    public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String bpname) {
        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Buscando blueprint '" + bpname + "' del autor: " + author);
        try {
            Blueprint blueprint = blueprintsServices.getBlueprint(author, bpname);
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Blueprint encontrado: '" + bpname + "' del autor: " + author);
            return new ResponseEntity<>(blueprint, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.WARNING, "No se encontró el blueprint '" + bpname + "' del autor: " + author, ex);
            return new ResponseEntity<>("No se encontró el blueprint '" + bpname + "' del autor: " + author, HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> manejadorPostRecursoBlueprint(@RequestBody Blueprint blueprint) {
        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Creando nuevo blueprint: '" + blueprint.getName() + "' del autor: " + blueprint.getAuthor());
        try {
            blueprintsServices.addNewBlueprint(blueprint);
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Blueprint creado exitosamente: '" + blueprint.getName() + "' del autor: " + blueprint.getAuthor());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, "Error al crear el blueprint: " + ex.getMessage(), ex);
            return new ResponseEntity<>("Error al crear el blueprint: " + ex.getMessage(), HttpStatus.FORBIDDEN);            
        }        
    }
    
    @PutMapping("/{author}/{bpname}")
    public ResponseEntity<?> updateBlueprint(@PathVariable String author, @PathVariable String bpname, @RequestBody Blueprint blueprint) {
        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Actualizando/creando blueprint '" + bpname + "' del autor: " + author);
        
        // Verificar que los parámetros de la URL coincidan con el contenido del blueprint
        if (!author.equals(blueprint.getAuthor()) || !bpname.equals(blueprint.getName())) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.WARNING, "Parámetros de URL no coinciden con datos del blueprint");
            return new ResponseEntity<>("El autor y nombre en la URL deben coincidir con los datos del blueprint", HttpStatus.BAD_REQUEST);
        }
        
        try {
            boolean wasUpdated = blueprintsServices.updateBlueprint(blueprint);
            if (wasUpdated) {
                Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Blueprint actualizado: '" + bpname + "' del autor: " + author);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Blueprint creado: '" + bpname + "' del autor: " + author);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, "Error al actualizar/crear el blueprint: " + ex.getMessage(), ex);
            return new ResponseEntity<>("Error al actualizar/crear el blueprint: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);            
        }
    }
    
    // Endpoint temporal de depuración para listar autores disponibles
    @GetMapping("/authors")
    public ResponseEntity<?> getAllAuthors() {
        try {
            Set<Blueprint> allBlueprints = blueprintsServices.getAllBlueprints();
            Set<String> authors = new java.util.HashSet<>();
            for (Blueprint bp : allBlueprints) {
                authors.add(bp.getAuthor());
            }
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.INFO, "Autores disponibles: " + authors);
            return new ResponseEntity<>(authors, HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al obtener los autores", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

