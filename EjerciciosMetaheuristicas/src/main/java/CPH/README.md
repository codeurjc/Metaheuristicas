# Capacited p-hub Problem
## Descripción
En el problema del capacited p-hub (CPH) se tiene un determinado número de centros que pueden
actuar de clientes o de servidores (también denominados hubs). El número de centros que pueden
actuar como servidores está limitado a p, siendo el resto clientes. Cada cliente sólo se conecta a un
servidor. El objetivo es determinar los p centros que actúan como servidores y conectar todos los
clientes a uno de los servidores de forma que se minimice la suma de las distancias de los clientes a
los servidores. Además, existe la restricción de que cada servidor sólo puede servir una determinada
cantidad de recursos y cada cliente tiene una demanda que debe ser satisfecha. Las soluciones
factibles al problema deben asegurarse de que todos los servidores pueden proporcionar esos
recursos a sus clientes. 

El modelo matemático para este problema es el siguiente:

![cph](/resources/images/cph.png)

Donde:
x ij es 1 si hay una asociación entre el cliente i y el servidor j
y j es 1 si el nodo j es servidor, 0 si es cliente
c es la capacidad del servidor

## Instancias
Se utilizarán dos conjuntos de instancias:

* phub_50_5 son instancias pequeñas, con 50 nodos de los cuales hay que escoger 5
servidores

* phub_100_10 son instancias más grandes, con 100 nodos de los cuales hay que escoger 10
servidores

El formato de los ficheros es el mismo para ambos conjuntos:

* Una primera línea donde se indica el número total de nodos, el número de nodos servidores
(p) y la capacidad de cada servidor (todos los servidores tienen la misma capacidad)
•
* El resto de líneas describen los nodos:
    * El primer valor es el número de nodo
    * El segundo y tercer valor son las coordenadas geométricas del nodo (en un espacio◦
euclídeo)
* El último valor es la demanda del nodo si actúa como cliente

Ejemplo:

<pre>
18 1043
10 2 120
1 3 60 4
2 68 49 8
3 81 53 9
4 62 94 12
5 1 57 10
6 7 98 14
7 24 94 14
8 59 95 12
9 19 13 1
10 70 86 3
</pre> 

# Resolución

A continuación se describe la función de las clases implementadas:

* **CPH:** Esta clase representa el programa principal. En ella se ejecuta las diferentes formas de resolución. Tiene 4 metodos de resolución:
    * *calculateSolutionRandom:* Genera n soluciones aleatorias y se queda con la mejor.
    * *calculateSolutionFirstImprovementRandom:* Genera n soluciones aleatorias e intenta mejorarlas con el metodo (First Improvement con ordenación aleatoria)
    * *calculateSolutionFirstImprovementLexicographical:* Genera n soluciones aleatorias e intenta mejorarlas con el metodo (First Improvement con ordenación lexico grafico)
    * *calculateSolutionBestImprovement:* Genera n soluciones aleatorias e intenta mejorarlas con el metodo (Best Improvement)
* **CPHInstance:** Esta clase contiene el fichero con el problema, la instancia en memoria del problema y los nodos.
* **CPHNode:** Esta clase representa cada nodo del problema.
* **CPHSolution:** Esta clase se encarga de gestionar las posibles soluciones del problema. Esta clase contiene la instancia del problema, los nodos de la resolución del problema y el metodo que calcula la función objetivo *calculateWeight*
* **CPHSolutionsList:** Esta clase contiene todas asl soluciones que se van calculando, despues nos quedaremos con la primera (La mejor)
* **CPHRandomConstructive:** Esta clase se encarga de generar una posible solución de forma aleatoria
* **CPHFirstImprovement:** Esta clase se encarga de mejorar una solución dada. Para ello va cambiando los nodos de la solución por otros que no estan en ella y va calculando si esto mejora o empeora el problema, si lo mejora se queda con el primer cambio que lo mejore.
* **CPHBestImprovement:** Esta clase hace la misma función que *CPHFirstImprovement* pero en vez de quedarse el primer cambio que mejore el problema, se queda con el mejor de todos.