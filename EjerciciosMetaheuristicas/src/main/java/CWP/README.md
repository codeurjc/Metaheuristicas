# Cutwidth Problem
## Descripción
Dado un grafo, el problema del minimizado del cutwidth (CutWidth Problem – CWP) consiste en
encontrar una ordenación lineal del grafo de tal forma que el máximo número de aristas cortadas
entre dos vértices consecutivos sea mínima. 

El problema del cutwidth se puede formular de la
siguiente manera:

![cwp](/resources/images/cwp.png)

Donde
x es una solución al problema que consiste en una ordenación de los nodos, es decir, x i es la posición
del nodo i en la ordenación dada por la solución x
ci es, para una determinada ordenación, el corte en el nodo i. Es decir, el número de aristas que
salen de i o de nodos anteriores a i y van a parar a nodos posteriores a i.

## Instancias
Se utilizará un único conjunto de instancias denominado Harwell-Boeing.
El formato de los ficheros es el siguiente:

* Una primera línea donde se indica el número de nodos (por duplicado) y el número de
aristas

* El resto de líneas describen las aristas entre los nodos como dos índices que se refieren al
número de nodo.

Ejemplo:

<pre>
10 10 179
1 2
2 4
2 5
3 7
4 6
5 6
7 8
7 9
8 9
9 3
10 5
</pre> 

# Resolución

A continuación se describe la función de las clases implementadas:

* **CWP:** Esta clase representa el programa principal. En ella se ejecuta las diferentes formas de resolución. Tiene 4 metodos de resolución:
    * *calculateSolutionRandom:* Genera n soluciones aleatorias y se queda con la mejor.
    * *calculateSolutionFirstImprovementRandom:* Genera n soluciones aleatorias e intenta mejorarlas con el metodo (First Improvement con ordenación aleatoria)
    * *calculateSolutionFirstImprovementLexicographical:* Genera n soluciones aleatorias e intenta mejorarlas con el metodo (First Improvement con ordenación lexico grafico)
    * *calculateSolutionBestImprovement:* Genera n soluciones aleatorias e intenta mejorarlas con el metodo (Best Improvement)
* **CWPInstance:** Esta clase contiene el fichero con el problema, la instancia en memoria del problema y los nodos.
* **CWPNode:** Esta clase representa cada nodo del problema.
* **CWPSolution:** Esta clase se encarga de gestionar las posibles soluciones del problema. Esta clase contiene la instancia del problema, los nodos de la resolución del problema y el metodo que calcula la función objetivo *calculateWeight*
* **CWPSolutionsList:** Esta clase contiene todas asl soluciones que se van calculando, despues nos quedaremos con la primera (La mejor)
* **CWPRandomConstructive:** Esta clase se encarga de generar una posible solución de forma aleatoria
* **CWPFirstImprovement:** Esta clase se encarga de mejorar una solución dada. Para ello va cambiando los nodos de la solución por otros que no estan en ella y va calculando si esto mejora o empeora el problema, si lo mejora se queda con el primer cambio que lo mejore.
* **CWPBestImprovement:** Esta clase hace la misma función que *CWPFirstImprovement* pero en vez de quedarse el primer cambio que mejore el problema, se queda con el mejor de todos.