# dariojolo-wenance
Para resolver el challenge se optó por realizar una API Rest que exponga dos métodos.
Uno que devuelve la cotización del bitcoin en un momento dado (timestamp pasado como parametro) y el segundo que devuelve la cotización promedio y el calculo de la diferencia porcentual entre este valor y la cotización máxima.

&nbsp;
**Instrucciones de ejecución**
1. Iniciar la aplicacion y la misma comenzará a recolectar datos
2. Si se quiere hacer la consulta del precio pasando un timestamp se debe realizar la siguiente llamada a la API por **método GET**
  
  ```
  http://localhost:8080/api/prices/
  ```
  pasando como parámetro un objeto JSON con el siguiente formato
  
 ```
  {
    "date": "10:26:50 18/01/2021"
  }
 ```
 3. Si se requiere hacer una consulta del promedio del precio en un tiempo determinado, se debe realizar la siguiente llamada a la API por **metodo GET** 
  ```
  http://localhost:8080/api/prices/avg
  ```
 pasando por parámetro un objeto **JSON** que contenga el horario de inicio y fin del periodo que se quiere consultar
 
 &nbsp; 
 Ejemplo del objeto JSON a enviar, el formato de fecha y hora se debe respetar 
  ```
  {
    "start": "12:49:10 17/01/2021",
    "end":"12:49:30 17/01/2021"
  }
  ```
