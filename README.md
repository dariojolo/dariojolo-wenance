# dariojolo-wenance
Para resolver el challenge se optó por realizar una API Rest que exponga dos métodos.
Uno que devuelve la cotización del bitcoin al momento de realizar la llamada y el segundo que devuelve la cotización promedio y el calculo de la diferencia porcentual entre este valor y la cotización máxima.
Si bien son dos métodos GET se optó por usar el verbo POST para el segundo método para que las direcciones URL donde escucha nuestra aplicación sean similares y aprovechando que en la solución actual no había necesidad de insertar nuevos valores de forma manual que requiera la utilización de un metodo POST

&nbsp;
**Instrucciones de ejecución**
1. Iniciar la aplicacion y la misma comenzará a recolectar datos
2. Si se quiere hacer una consulta del precio vigente se debe realizar la siguiente llamada a la API por **método GET**
  ```http://localhost:8080/api/prices/```
3. Si se requiere hacer una consulta del promedio del precio en un tiempo determinado, se debe realizar la siguiente llamada a la API por **metodo POST** pasando por parámetro 
 un objeto **JSON** que contenga el horario de inicio y fin del periodo que se quiere consultar
  ```http://localhost:8080/api/prices/```
 Ejemplo del objeto JSON a enviar, el formato de fecha y hora se debe respetar 
  ```
  {
    "start": "12:49:10 17/01/2021",
    "end":"12:49:30 17/01/2021"
  }
  ```
