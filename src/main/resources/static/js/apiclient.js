/**
 * Módulo API Client para obtener datos reales del backend REST
 * Proporciona las mismas operaciones que apimock pero usando peticiones HTTP reales
 */
var apiclient = (function () {
    'use strict';

    var _baseUrl = '/blueprints';

    /**
     * Obtiene todos los blueprints del servidor
     * @param {function} callback - Función callback para manejar la respuesta
     */
    function _getAllBlueprints(callback) {
        $.ajax({
            url: _baseUrl,
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                callback(null, data);
            },
            error: function(xhr, status, error) {
                var errorObj = new Error('Error al obtener todos los blueprints: ' + error);
                errorObj.status = xhr.status;
                callback(errorObj, null);
            }
        });
    }

    /**
     * Obtiene blueprints por autor del servidor
     * @param {string} author - Nombre del autor
     * @param {function} callback - Función callback para manejar la respuesta
     */
    function _getBlueprintsByAuthor(author, callback) {
        $.ajax({
            url: _baseUrl + '/' + encodeURIComponent(author),
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                callback(null, data);
            },
            error: function(xhr, status, error) {
                var errorObj = new Error('Autor no encontrado: ' + author);
                errorObj.status = xhr.status;
                callback(errorObj, null);
            }
        });
    }

    /**
     * Obtiene un blueprint específico por nombre y autor del servidor
     * @param {string} author - Nombre del autor
     * @param {string} blueprintName - Nombre del blueprint
     * @param {function} callback - Función callback para manejar la respuesta
     */
    function _getBlueprintsByNameAndAuthor(author, blueprintName, callback) {
        $.ajax({
            url: _baseUrl + '/' + encodeURIComponent(author) + '/' + encodeURIComponent(blueprintName),
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                callback(null, data);
            },
            error: function(xhr, status, error) {
                var errorObj = new Error('Blueprint no encontrado: ' + blueprintName + ' del autor ' + author);
                errorObj.status = xhr.status;
                callback(errorObj, null);
            }
        });
    }

    /**
     * Obtiene todos los autores disponibles del servidor
     * @param {function} callback - Función callback para manejar la respuesta
     */
    function _getAuthors(callback) {
        $.ajax({
            url: _baseUrl + '/authors',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                callback(null, data);
            },
            error: function(xhr, status, error) {
                var errorObj = new Error('Error al obtener los autores: ' + error);
                errorObj.status = xhr.status;
                callback(errorObj, null);
            }
        });
    }

    /**
     * Obtiene el total de blueprints de un autor
     * @param {string} author - Nombre del autor
     * @param {function} callback - Función callback para manejar la respuesta
     */
    function _getBlueprintCount(author, callback) {
        _getBlueprintsByAuthor(author, function(error, blueprints) {
            if (error) {
                callback(error, null);
            } else {
                callback(null, blueprints ? blueprints.length : 0);
            }
        });
    }

    /**
     * Obtiene el total de puntos de un autor
     * @param {string} author - Nombre del autor
     * @param {function} callback - Función callback para manejar la respuesta
     */
    function _getTotalPoints(author, callback) {
        _getBlueprintsByAuthor(author, function(error, blueprints) {
            if (error) {
                callback(error, null);
            } else {
                var total = 0;
                if (blueprints && Array.isArray(blueprints)) {
                    blueprints.forEach(function(blueprint) {
                        total += blueprint.points ? blueprint.points.length : 0;
                    });
                }
                callback(null, total);
            }
        });
    }

    return {
        getBlueprintsByAuthor: _getBlueprintsByAuthor,
        getBlueprintsByNameAndAuthor: _getBlueprintsByNameAndAuthor,
        getAuthors: _getAuthors,
        getBlueprintCount: _getBlueprintCount,
        getTotalPoints: _getTotalPoints,
        getAllBlueprints: _getAllBlueprints
    };
})();
