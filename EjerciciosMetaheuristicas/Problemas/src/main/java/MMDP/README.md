# MaxMin Diversity Problem
## Descripción
El problema de la diversidad MaxMin (MaxMin Diversity Problem - MMDP), consiste en
seleccionar un determinado número de elementos (m) de un conjunto de n elementos de tal forma
que la menor de las distancias entre los elementos seleccionados sea máxima. La definición de
distancia entre los elementos depende de las aplicaciones específicas. 

El problema MMDP se puede
formular como un problema cuadrático binario:

![mmdp](/resources/images/mmdp.png)

## Instancias
Se utilizarán dos conjuntos de instancias. GKD-Ia son instancias más pequeñas (con n entre 10 y 30,
y diferentes valores de m) y por tanto más sencillas de resolver. El conjunto GKD-Ic contiene
instancias más grandes (n=500, m=50) que requieren un mayor esfuerzo computacional. Estos dos
conjuntos de instancias junto con los mejores valores conocidos para ambos pueden descargarse
desde la página web.

El formato de los ficheros es el mismo para ambos conjuntos:
* Una primera línea donde se indica el valor de n y de m

* A continuación, en cada línea se indica el índice de dos elementos y la distancias entre ellos

* Para estas instancias, existe una distancia para cada par de elementos

Ejemplo:

<pre>
5 2
0 1 166.47234
0 2 170.18475
0 3 174.55453
0 4 153.28670
1 2 145.12186
1 3 144.42723
1 4 170.98466
2 3 176.58110
2 4 162.99632
3 4 168.48360
</pre> 

# Resolución

A continuación se describe la función de las clases implementadas:

* **MMDP:** Esta clase representa el programa principal. En ella se ejecuta las diferentes formas de resolución. Tiene 4 metodos de resolución:
    * *calculateSolutionRandom:* Genera n soluciones aleatorias y se queda con la mejor.
    * *calculateSolutionFirstImprovementRandom:* Genera n soluciones aleatorias e intenta mejorarlas con el metodo (First Improvement con ordenación aleatoria)
    * *calculateSolutionFirstImprovementLexicographical:* Genera n soluciones aleatorias e intenta mejorarlas con el metodo (First Improvement con ordenación lexico grafico)
    * *calculateSolutionBestImprovement:* Genera n soluciones aleatorias e intenta mejorarlas con el metodo (Best Improvement)
* **MMDPInstance:** Esta clase contiene el fichero con el problema, la instancia en memoria del problema y los nodos.
* **MMDPNode:** Esta clase representa cada nodo del problema.
* **MMDPSolution:** Esta clase se encarga de gestionar las posibles soluciones del problema. Esta clase contiene la instancia del problema, los nodos de la resolución del problema y el metodo que calcula la función objetivo *calculateWeight*
* **MMDPSolutionsList:** Esta clase contiene todas asl soluciones que se van calculando, despues nos quedaremos con la primera (La mejor)
* **MMDPRandomConstructive:** Esta clase se encarga de generar una posible solución de forma aleatoria
* **MMDPFirstImprovement:** Esta clase se encarga de mejorar una solución dada. Para ello va cambiando los nodos de la solución por otros que no estan en ella y va calculando si esto mejora o empeora el problema, si lo mejora se queda con el primer cambio que lo mejore.
* **MMDPBestImprovement:** Esta clase hace la misma función que *MMDPFirstImprovement* pero en vez de quedarse el primer cambio que mejore el problema, se queda con el mejor de todos.