# Servidor Java - Cliente PHP

Comunicación simple entre un servidor Java y un cliente PHP.

## Archivos incluidos:
- `Servidor.java`: Servidor Java que responde a peticiones GET y POST
- `cliente.php`: Cliente PHP que envía peticiones al servidor

## Cómo ejecutar:

### 1. Iniciar el servidor Java:
```bash
javac Servidor.java
java Servidor
```

### 2. Compilar y ejecutar el cliente PHP:
En otra terminal:
```bash
php cliente.php
```

## Qué hace:

**Servidor Java:**
- Responde a peticiones GET con un mensaje de saludo
- Acepta peticiones POST con datos SOAP y los devuelve
- Retorna respuestas en formato SOAP

**Cliente PHP:**
- Hace una petición GET al servidor
- Hace una petición POST enviando datos SOAP
- Muestra las respuestas del servidor

## Ejemplo de salida:

El cliente mostrará algo como:
```
=== Cliente PHP - Comunicación con Servidor JAVA ===

Respuesta SOAP del servidor: 
<?xml version="1.0"?><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><output>Servidor recibió: [5, 25, 35, 30]</output></soap:Body></soap:Envelope

Respuesta del servidor:
<?xml version="1.0"?><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><output>Servidor recibió: [3, 15]</output></soap:Body></soap:Envelope

==================================================
```