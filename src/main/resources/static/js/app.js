/**
 * Módulo JavaScript para la aplicación Blueprints
 * Implementa el patrón Módulo de JavaScript para manejar la lógica del frontend
 */
var app = (function () {
    'use strict';

    // ===== CONFIGURACIÓN DE API =====
    // Cambiar entre 'apimock' y 'apiclient' con solo modificar esta línea
    var _useApiClient = true; // true = apiclient, false = apimock
    
    // Referencia dinámica al módulo API seleccionado
    var _api = _useApiClient ? apiclient : apimock;
    // ================================

    var _currentAuthor = null;
    var _blueprintsList = [];
    var _totalPoints = 0;

    var _authorInput = null;
    var _getBlueprintsBtn = null;
    var _selectedAuthor = null;
    var _blueprintsTable = null;
    var _blueprintsTbody = null;
    var _totalPointsElement = null;
    var _blueprintCanvas = null;
    var _currentBlueprintName = null;
    var _authorBlueprintsTitle = null;

    /**
     * Inicializa las referencias a los elementos del DOM
     */
    function _initializeElements() {
        _authorInput = document.getElementById('author-input');
        _getBlueprintsBtn = document.getElementById('get-blueprints-btn');
        _selectedAuthor = document.getElementById('selected-author');
        _blueprintsTable = document.getElementById('blueprints-table');
        _blueprintsTbody = document.getElementById('blueprints-tbody');
        _totalPointsElement = document.getElementById('total-points');
        _blueprintCanvas = document.getElementById('blueprint-canvas');
        _currentBlueprintName = document.getElementById('current-blueprint-name');
        _authorBlueprintsTitle = document.getElementById('author-blueprints-title');
    }

    /**
     * Configura los event listeners
     */
    function _setupEventListeners() {
        if (_getBlueprintsBtn) {
            _getBlueprintsBtn.addEventListener('click', function() {
                var author = _authorInput ? _authorInput.value.trim() : '';
                if (author) {
                    updateBlueprintsByAuthor(author);
                } else {
                    _showAlert('Por favor ingrese un nombre de autor');
                }
            });
        }

        if (_authorInput) {
            _authorInput.addEventListener('keypress', function(event) {
                if (event.key === 'Enter') {
                    var author = _authorInput.value.trim();
                    if (author) {
                        updateBlueprintsByAuthor(author);
                    } else {
                        _showAlert('Por favor ingrese un nombre de autor');
                    }
                }
            });
        }
    }

    /**
     * Configura los event listeners para los botones de abrir blueprint
     */
    function _setupBlueprintButtons() {
        $('.open-blueprint-btn').off('click').on('click', function() {
            var author = $(this).data('author');
            var blueprint = $(this).data('blueprint');
            
            if (author && blueprint) {
                drawBlueprint(author, blueprint);
            } else {
                _showAlert('Error: No se pudo obtener la información del plano');
            }
        });
    }


    /**
     * Actualiza el campo de autor seleccionado
     */
    function _updateSelectedAuthor(author) {
        if (_selectedAuthor) {
            _selectedAuthor.textContent = author;
        }
        if (_authorBlueprintsTitle) {
            _authorBlueprintsTitle.textContent = author + "'s blueprints:";
        }
    }


    /**
     * Procesa los datos de blueprints y los convierte a la estructura requerida
     * @param {Array} data - Array de blueprints del backend
     */
    function _processBlueprintsData(data) {
        _blueprintsList = [];
        data.forEach(function(blueprint) {
            var blueprintInfo = {
                name: blueprint.name || '-',
                points: blueprint.points ? blueprint.points.length : 0
            };
            _blueprintsList.push(blueprintInfo);
        });
    }

    /**
     * Actualiza la tabla de blueprints
     */
    function _updateBlueprintsTable() {
        if (!_blueprintsTbody) return;

        _blueprintsTbody.innerHTML = '';

        if (_blueprintsList.length === 0) {
            var row = document.createElement('tr');
            row.innerHTML = '<td colspan="3" class="text-center">No se encontraron blueprints para este autor</td>';
            _blueprintsTbody.appendChild(row);
            return;
        }

        _blueprintsList.forEach(function(blueprintInfo) {
            var row = document.createElement('tr');
            
            row.innerHTML = 
                '<td>' + blueprintInfo.name + '</td>' +
                '<td>' + (_currentAuthor || '-') + '</td>' +
                '<td>' + blueprintInfo.points + '</td>';
            
            _blueprintsTbody.appendChild(row);
        });
    }

    /**
     * Calcula el total de puntos de todos los blueprints
     */
    function _calculateTotalPoints() {
        _totalPoints = 0;
        
        _blueprintsList.forEach(function(blueprintInfo) {
            _totalPoints += blueprintInfo.points;
        });

        _updateTotalPointsDisplay();
    }

    /**
     * Actualiza la visualización del total de puntos
     */
    function _updateTotalPointsDisplay() {
        if (_totalPointsElement) {
            _totalPointsElement.textContent = _totalPoints;
        }
    }

    /**
     * Limpia la tabla y resetea los valores
     */
    function _clearTable() {
        if (_blueprintsTbody) {
            _blueprintsTbody.innerHTML = '';
        }
        if (_totalPointsElement) {
            _totalPointsElement.textContent = '0';
        }
        _blueprintsList = [];
        _totalPoints = 0;
    }

    /**
     * Limpia la tabla usando jQuery
     */
    function _clearTableWithjQuery() {
        $('#blueprints-tbody').empty();
        $('#total-points').text('0');
        _blueprintsList = [];
        _totalPoints = 0;
    }

    /**
     * Muestra una alerta al usuario
     */
    function _showAlert(message) {
        alert(message);
    }

    /**
     * Limpia el canvas
     */
    function _clearCanvas() {
        if (_blueprintCanvas) {
            var ctx = _blueprintCanvas.getContext('2d');
            ctx.clearRect(0, 0, _blueprintCanvas.width, _blueprintCanvas.height);
            ctx.fillStyle = '#f9f9f9';
            ctx.fillRect(0, 0, _blueprintCanvas.width, _blueprintCanvas.height);
        }
    }

    /**
     * Dibuja los puntos del blueprint en el canvas con estilo cartoon sutil
     * @param {Array} points - Array de puntos con coordenadas x, y
     */
    function _drawBlueprintPoints(points) {
        if (!_blueprintCanvas || !points || points.length === 0) {
            return;
        }

        var ctx = _blueprintCanvas.getContext('2d');
        _clearCanvas();

        // Fondo blanco limpio
        ctx.fillStyle = '#fff';
        ctx.fillRect(0, 0, _blueprintCanvas.width, _blueprintCanvas.height);

        if (points.length === 1) {
            // Punto único
            _drawCartoonPoint(ctx, points[0].x, points[0].y, '#4ecdc4');
            return;
        }

        // Dibujar líneas con colores cartoon suaves
        for (var i = 0; i < points.length - 1; i++) {
            _drawCartoonLine(ctx, points[i], points[i + 1], i);
        }

        // Dibujar puntos con estilo cartoon
        for (var j = 0; j < points.length; j++) {
            var color = _getCartoonColor(j);
            _drawCartoonPoint(ctx, points[j].x, points[j].y, color);
        }
    }

    /**
     * Dibuja una línea con estilo blanco y negro
     */
    function _drawCartoonLine(ctx, point1, point2, index) {
        var colors = ['#000', '#333', '#666', '#000', '#333'];
        var color = colors[index % colors.length];
        
        ctx.beginPath();
        ctx.moveTo(point1.x, point1.y);
        ctx.lineTo(point2.x, point2.y);
        
        // Sombra dramática
        ctx.shadowColor = color;
        ctx.shadowBlur = 8;
        ctx.strokeStyle = color;
        ctx.lineWidth = 5;
        ctx.lineCap = 'round';
        ctx.stroke();
        
        // Línea principal
        ctx.shadowBlur = 0;
        ctx.strokeStyle = '#fff';
        ctx.lineWidth = 3;
        ctx.stroke();
        
        // Línea de contorno
        ctx.strokeStyle = color;
        ctx.lineWidth = 1;
        ctx.stroke();
    }

    /**
     * Dibuja un punto con estilo blanco y negro
     */
    function _drawCartoonPoint(ctx, x, y, color) {
        // Sombra exterior
        ctx.shadowColor = 'rgba(0,0,0,0.8)';
        ctx.shadowBlur = 6;
        ctx.shadowOffsetX = 3;
        ctx.shadowOffsetY = 3;
        
        // Círculo exterior
        ctx.beginPath();
        ctx.arc(x, y, 8, 0, 2 * Math.PI);
        ctx.fillStyle = color;
        ctx.fill();
        
        // Resetear sombra
        ctx.shadowBlur = 0;
        ctx.shadowOffsetX = 0;
        ctx.shadowOffsetY = 0;
        
        // Círculo interior blanco
        ctx.beginPath();
        ctx.arc(x, y, 5, 0, 2 * Math.PI);
        ctx.fillStyle = '#fff';
        ctx.fill();
        
        // Punto central
        ctx.beginPath();
        ctx.arc(x, y, 2, 0, 2 * Math.PI);
        ctx.fillStyle = color;
        ctx.fill();
        
        // Contorno
        ctx.beginPath();
        ctx.arc(x, y, 8, 0, 2 * Math.PI);
        ctx.strokeStyle = color;
        ctx.lineWidth = 2;
        ctx.stroke();
    }

    /**
     * Obtiene un color blanco y negro basado en el índice
     */
    function _getCartoonColor(index) {
        var colors = ['#000', '#333', '#666', '#000', '#333'];
        return colors[index % colors.length];
    }

    /**
     * Actualiza el nombre del plano actual en el DOM
     * @param {string} blueprintName - Nombre del plano
     */
    function _updateCurrentBlueprintName(blueprintName) {
        if (_currentBlueprintName) {
            _currentBlueprintName.textContent = blueprintName || '-';
        }
    }

    /**
     * Obtiene el autor actual
     */
    function getCurrentAuthor() {
        return _currentAuthor;
    }

    /**
     * Obtiene la lista de blueprints actuales (nombre y puntos)
     */
    function getCurrentBlueprints() {
        return _blueprintsList.slice();
    }

    /**
     * Cambia el autor actualmente seleccionado
     * @param {string} newAuthor - Nuevo nombre del autor
     */
    function setCurrentAuthor(newAuthor) {
        if (newAuthor && typeof newAuthor === 'string') {
            _currentAuthor = newAuthor.trim();
            _updateSelectedAuthor(_currentAuthor);
            console.log('Autor cambiado a:', _currentAuthor);
        } else {
            console.warn('Nombre de autor inválido:', newAuthor);
        }
    }

    /**
     * Actualiza la lista de blueprints basada en el nombre del autor
     * @param {string} authorName - Nombre del autor
     */
    function updateBlueprintsByAuthor(authorName) {
        if (!authorName || typeof authorName !== 'string') {
            console.warn('Nombre de autor inválido:', authorName);
            return;
        }

        _currentAuthor = authorName.trim();
        _updateSelectedAuthor(_currentAuthor);

        _api.getBlueprintsByAuthor(authorName, function(error, blueprintsList) {
            if (error) {
                console.error('Error al obtener blueprints:', error);
                _showAlert('Error al obtener los blueprints del autor: ' + authorName);
                _clearTableWithjQuery();
                return;
            }

            var mappedBlueprints = blueprintsList.map(function(blueprint) {
                return {
                    name: blueprint.name || '-',
                    points: blueprint.points ? blueprint.points.length : 0
                };
            });

            _blueprintsList = mappedBlueprints;

            $('#blueprints-tbody').empty();

            if (mappedBlueprints.length === 0) {
                $('#blueprints-tbody').append('<tr><td colspan="3" class="text-center">No se encontraron blueprints para este autor</td></tr>');
            } else {
                mappedBlueprints.map(function(blueprintInfo) {
                    var rowHtml = '<tr>' +
                        '<td>' + blueprintInfo.name + '</td>' +
                        '<td>' + blueprintInfo.points + '</td>' +
                        '<td><button type="button" class="btn btn-primary btn-sm open-blueprint-btn" ' +
                        'data-author="' + _currentAuthor + '" data-blueprint="' + blueprintInfo.name + '">Open</button></td>' +
                        '</tr>';
                    $('#blueprints-tbody').append(rowHtml);
                });
                
                _setupBlueprintButtons();
            }

            var totalPoints = mappedBlueprints.reduce(function(total, blueprintInfo) {
                return total + blueprintInfo.points;
            }, 0);

            _totalPoints = totalPoints;
            $('#total-points').text(totalPoints);

            console.log('Blueprints actualizados para', authorName + ':', mappedBlueprints.length, 'blueprints,', totalPoints, 'puntos totales');
        });
    }

    /**
     * Obtiene el total de puntos actual
     */
    function getTotalPoints() {
        return _totalPoints;
    }

    /**
     * Cambia entre apimock y apiclient
     * @param {boolean} useApiClient - true para usar apiclient, false para apimock
     */
    function switchApi(useApiClient) {
        _useApiClient = useApiClient;
        _api = _useApiClient ? apiclient : apimock;
        console.log('API cambiada a:', _useApiClient ? 'apiclient' : 'apimock');
    }

    /**
     * Obtiene el módulo API actualmente en uso
     */
    function getCurrentApi() {
        return _useApiClient ? 'apiclient' : 'apimock';
    }

    /**
     * Dibuja un blueprint específico en el canvas
     * @param {string} authorName - Nombre del autor
     * @param {string} blueprintName - Nombre del blueprint
     */
    function drawBlueprint(authorName, blueprintName) {
        if (!authorName || !blueprintName || typeof authorName !== 'string' || typeof blueprintName !== 'string') {
            _showAlert('Parámetros inválidos para dibujar el plano');
            _clearCanvas();
            _updateCurrentBlueprintName('-');
            return;
        }

        _api.getBlueprintsByNameAndAuthor(authorName.trim(), blueprintName.trim(), function(error, blueprint) {
            if (error) {
                _showAlert('Error al obtener el plano: ' + error.message);
                _clearCanvas();
                _updateCurrentBlueprintName('-');
                return;
            }

            if (!blueprint || !blueprint.points || blueprint.points.length === 0) {
                _showAlert('El plano no tiene puntos para dibujar');
                _clearCanvas();
                _updateCurrentBlueprintName('-');
                return;
            }

            _updateCurrentBlueprintName(blueprint.name);
            _drawBlueprintPoints(blueprint.points);
            
            console.log('Plano dibujado:', blueprint.name, 'con', blueprint.points.length, 'puntos');
        });
    }

    return {
        init: function() {
            console.log('Inicializando módulo Blueprints...');
            console.log('API configurada:', getCurrentApi());
            _initializeElements();
            _setupEventListeners();
            console.log('Módulo Blueprints inicializado correctamente');
        },
        
        getCurrentAuthor: getCurrentAuthor,
        getCurrentBlueprints: getCurrentBlueprints,
        getTotalPoints: getTotalPoints,
        setCurrentAuthor: setCurrentAuthor,
        updateBlueprintsByAuthor: updateBlueprintsByAuthor,
        drawBlueprint: drawBlueprint,
        switchApi: switchApi,
        getCurrentApi: getCurrentApi,
        
        clear: function() {
            _clearTable();
            if (_selectedAuthor) {
                _selectedAuthor.textContent = '-';
            }
            _currentAuthor = null;
            _clearCanvas();
            _updateCurrentBlueprintName('-');
        }
    };
})();

document.addEventListener('DOMContentLoaded', function() {
    app.init();
});
