Para generar el ejecutable pararse dentro de la carpeta del proyecto y correr "mvn clean install",
luego pararse sobre la carpeta target y ahí se encontrara el ejecutable Reversi-0.0.1-SNAPSHOT.jar.
Para ejecutarlo ejecutar java -jar Reversi-0.0.1-SNAPSHOT.jar [parametros]

los parametros pueden ser los siguientes: 

-size [n]: determina el tamaño del tablero. “n” debe ser un número entero.

-ai [m]: determina el rol de la AI. “m” es un número que significa:

0: no hay AI. Juegan dos jugadores humanos

1: AI mueve primero

2: AI mueve segundo

-mode [time|depth]: determina si el algoritmo minimax se corre por tiempo o por
profundidad.
-param [k]: acompaña al parámetro anterior. En el caso de “time”, k deben ser los
segundos. En el caso de “depth”, debe ser la profundidad del árbol.

-prune [on|off]: activa o desactiva la poda.

-load [file]: opcional. Este parámetro debe cargar la partida previamente guardada.
“file” es una referencia al archivo donde se guardó el tablero. En el caso de utilizar
esta opción, no es necesario especificar el tamaño del tablero, pues este dato
se debe interpretar directamente sobre el tablero cargado.

Tienen que estar si o si en orden y si se quiere usar un archivo guardado no hay que poner el parametro
size.