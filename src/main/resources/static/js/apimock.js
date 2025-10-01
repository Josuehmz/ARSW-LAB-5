/**
 * Módulo API Mock para simular datos del backend
 * Proporciona datos de prueba para los blueprints
 */
var apimock = (function () {
    'use strict';

    var _blueprints = {
        'maria': [
            {
                name: 'plano1',
                author: 'maria',
                points: [
                    {x: 10, y: 10},
                    {x: 20, y: 20},
                    {x: 30, y: 30},
                    {x: 40, y: 40},
                    {x: 50, y: 50}
                ]
            },
            {
                name: 'plano2',
                author: 'maria',
                points: [
                    {x: 5, y: 5},
                    {x: 15, y: 15},
                    {x: 25, y: 25},
                    {x: 35, y: 35},
                    {x: 45, y: 45},
                    {x: 55, y: 55},
                    {x: 65, y: 65}
                ]
            },
            {
                name: 'plano3',
                author: 'maria',
                points: [
                    {x: 0, y: 0},
                    {x: 10, y: 10},
                    {x: 20, y: 20},
                    {x: 30, y: 30},
                    {x: 40, y: 40},
                    {x: 50, y: 50},
                    {x: 60, y: 60},
                    {x: 70, y: 70},
                    {x: 80, y: 80}
                ]
            }
        ],
        'juan': [
            {
                name: 'blueprint1',
                author: 'juan',
                points: [
                    {x: 1, y: 1},
                    {x: 2, y: 2},
                    {x: 3, y: 3},
                    {x: 4, y: 4},
                    {x: 5, y: 5},
                    {x: 6, y: 6}
                ]
            },
            {
                name: 'blueprint2',
                author: 'juan',
                points: [
                    {x: 10, y: 10},
                    {x: 20, y: 20},
                    {x: 30, y: 30},
                    {x: 40, y: 40},
                    {x: 50, y: 50},
                    {x: 60, y: 60},
                    {x: 70, y: 70},
                    {x: 80, y: 80},
                    {x: 90, y: 90},
                    {x: 100, y: 100}
                ]
            },
            {
                name: 'blueprint3',
                author: 'juan',
                points: [
                    {x: 0, y: 0},
                    {x: 5, y: 5},
                    {x: 10, y: 10},
                    {x: 15, y: 15},
                    {x: 20, y: 20}
                ]
            },
            {
                name: 'blueprint4',
                author: 'juan',
                points: [
                    {x: 1, y: 1},
                    {x: 3, y: 3},
                    {x: 5, y: 5},
                    {x: 7, y: 7},
                    {x: 9, y: 9},
                    {x: 11, y: 11},
                    {x: 13, y: 13},
                    {x: 15, y: 15},
                    {x: 17, y: 17},
                    {x: 19, y: 19},
                    {x: 21, y: 21}
                ]
            }
        ],
        'pedro': [
            {
                name: 'diseño1',
                author: 'pedro',
                points: [
                    {x: 0, y: 0},
                    {x: 10, y: 10},
                    {x: 20, y: 20},
                    {x: 30, y: 30},
                    {x: 40, y: 40},
                    {x: 50, y: 50},
                    {x: 60, y: 60},
                    {x: 70, y: 70},
                    {x: 80, y: 80},
                    {x: 90, y: 90},
                    {x: 100, y: 100},
                    {x: 110, y: 110}
                ]
            },
            {
                name: 'diseño2',
                author: 'pedro',
                points: [
                    {x: 5, y: 5},
                    {x: 15, y: 15},
                    {x: 25, y: 25},
                    {x: 35, y: 35},
                    {x: 45, y: 45}
                ]
            }
        ],
        'ana': [
            {
                name: 'proyecto1',
                author: 'ana',
                points: [
                    {x: 1, y: 1},
                    {x: 2, y: 2},
                    {x: 3, y: 3},
                    {x: 4, y: 4},
                    {x: 5, y: 5},
                    {x: 6, y: 6},
                    {x: 7, y: 7},
                    {x: 8, y: 8},
                    {x: 9, y: 9},
                    {x: 10, y: 10},
                    {x: 11, y: 11},
                    {x: 12, y: 12},
                    {x: 13, y: 13},
                    {x: 14, y: 14},
                    {x: 15, y: 15}
                ]
            },
            {
                name: 'proyecto2',
                author: 'ana',
                points: [
                    {x: 0, y: 0},
                    {x: 20, y: 20},
                    {x: 40, y: 40},
                    {x: 60, y: 60},
                    {x: 80, y: 80}
                ]
            },
            {
                name: 'proyecto3',
                author: 'ana',
                points: [
                    {x: 10, y: 10},
                    {x: 30, y: 30},
                    {x: 50, y: 50},
                    {x: 70, y: 70},
                    {x: 90, y: 90},
                    {x: 110, y: 110},
                    {x: 130, y: 130}
                ]
            }
        ],
        'carlos': [
            {
                name: 'esquema1',
                author: 'carlos',
                points: [
                    {x: 0, y: 0},
                    {x: 5, y: 5},
                    {x: 10, y: 10},
                    {x: 15, y: 15},
                    {x: 20, y: 20},
                    {x: 25, y: 25},
                    {x: 30, y: 30},
                    {x: 35, y: 35},
                    {x: 40, y: 40},
                    {x: 45, y: 45},
                    {x: 50, y: 50},
                    {x: 55, y: 55},
                    {x: 60, y: 60},
                    {x: 65, y: 65},
                    {x: 70, y: 70},
                    {x: 75, y: 75},
                    {x: 80, y: 80}
                ]
            }
        ]
    };

    /**
     * Simula una petición AJAX para obtener blueprints por autor
     * @param {string} author - Nombre del autor
     * @param {function} callback - Función callback para manejar la respuesta
     */
    function _getBlueprintsByAuthor(author, callback) {
        setTimeout(function() {
            var authorBlueprints = _blueprints[author.toLowerCase()];
            
            if (authorBlueprints) {
                callback(null, authorBlueprints);
            } else {
                var error = new Error('Autor no encontrado');
                error.status = 404;
                callback(error, null);
            }
        }, 500);
    }

    /**
     * Obtiene todos los autores disponibles
     * @returns {Array} Lista de nombres de autores
     */
    function _getAuthors() {
        return Object.keys(_blueprints);
    }

    /**
     * Obtiene el total de blueprints de un autor
     * @param {string} author - Nombre del autor
     * @returns {number} Número total de blueprints
     */
    function _getBlueprintCount(author) {
        var authorBlueprints = _blueprints[author.toLowerCase()];
        return authorBlueprints ? authorBlueprints.length : 0;
    }

    /**
     * Obtiene el total de puntos de un autor
     * @param {string} author - Nombre del autor
     * @returns {number} Número total de puntos
     */
    function _getTotalPoints(author) {
        var authorBlueprints = _blueprints[author.toLowerCase()];
        if (!authorBlueprints) return 0;
        
        var total = 0;
        authorBlueprints.forEach(function(blueprint) {
            total += blueprint.points ? blueprint.points.length : 0;
        });
        return total;
    }

    /**
     * Obtiene un blueprint específico por nombre y autor
     * @param {string} author - Nombre del autor
     * @param {string} blueprintName - Nombre del blueprint
     * @param {function} callback - Función callback para manejar la respuesta
     */
    function _getBlueprintsByNameAndAuthor(author, blueprintName, callback) {
        setTimeout(function() {
            var authorBlueprints = _blueprints[author.toLowerCase()];
            
            if (!authorBlueprints) {
                var error = new Error('Autor no encontrado');
                error.status = 404;
                callback(error, null);
                return;
            }
            
            var blueprint = authorBlueprints.find(function(bp) {
                return bp.name === blueprintName;
            });
            
            if (blueprint) {
                callback(null, blueprint);
            } else {
                var error = new Error('Blueprint no encontrado');
                error.status = 404;
                callback(error, null);
            }
        }, 300);
    }

    return {
        getBlueprintsByAuthor: _getBlueprintsByAuthor,
        getBlueprintsByNameAndAuthor: _getBlueprintsByNameAndAuthor,
        getAuthors: _getAuthors,
        getBlueprintCount: _getBlueprintCount,
        getTotalPoints: _getTotalPoints,
        
        getAllData: function() {
            return _blueprints;
        }
    };
})();
