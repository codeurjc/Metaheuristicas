# Determinar la calidad de un algoritmo
Para determinar la calidad de un algoritmo de resolución aproximada de problemas de optimización,
el algoritmo se ejecuta sobre un conjunto determinado de instancias. Posteriormente, se comparan
los resultados obtenidos en la ejecución con los mejores valores conocidos para ese conjunto de
instancias. Por otro lado, los algoritmos pueden tener diversos parámetros o pueden emplear
diversas estrategias. Para determinar qué valor es el más adecuado para un parámetro o saber la
estrategia más efectiva los algoritmos también se comparan entre sí.

La comparación de los algoritmos se lleva a cabo fundamentalmente usando dos criterios, la calidad
de las soluciones obtenidas y el tiempo de ejecución empleado para conseguirlas. Además, es
posible que los algoritmos no se comporten de la misma forma si se ejecutan sobre un conjunto de
instancias u otro.

Para facilitar la comparación de algoritmos se ha desarrollado una herramienta, mhAnalyzer, que
analiza los resultados obtenidos al ejecutar varios algoritmos sobre un conjunto de instancias. Esta
herramienta genera una tabla que resume la calidad de las soluciones obtenidas por cada algoritmo
y el tiempo empleado para obtenerlas. En concreto, la herramienta calcula, por cada método, los
estadísticos Dev y Time:

* Dev se calcula como la media de las desviaciones, en porcentaje, del valor obtenido por cada
método en cada instancia respecto al mejor valor conocido para esa instancia. Es decir:

    ![mhanalyzer](/resources/images/mhanalyzer.png)

    De esta forma, no se tiene en cuenta el valor concreto de una solución para una instancia,
sino que se determina lo cerca que se está de obtener la mejor solución conocida.

* Time se calcula como la media del tiempo de ejecución empleado por cada instancia.

Cuanto menor es el valor de Dev para un algoritmo, mejor calidad tiene ese algoritmo, porque en
media obtiene soluciones más cercanas al mejor valor conocido. Por ejemplo, si dos métodos
obtienen soluciones de la misma calidad (tienen valores de Dev similares), uno será mejor que el
otro si emplea menos tiempo en media.

Para la realización del análisis, mhAnalyzer requiere que existan una serie de ficheros en una
carpeta. Deberá existir un fichero (con cualquier nombre) por cada algoritmo que se quiera
comparar con el siguiente formato:

<pre>
Descripción del Algoritmo
nombre_fichero_instancia_1 valor_obtenido [índices_elementos_solución]
nombre_fichero_instancia_2 valor_obtenido [índices_elementos_solución]
nombre_fichero_instancia_3 valor_obtenido [índices_elementos_solución]
nombre_fichero_instancia_4 valor_obtenido [índices_elementos_solución]
...
</pre>

Deberá existir un fichero llamado best_values.txt que contenga los mejores valores conocidos
para cada instancia con el siguiente formato:

<pre>
nombre_fichero_instancia_1 mejor_valor_conocido
nombre_fichero_instancia_2 mejor_valor_conocido
nombre_fichero_instancia_3 mejor_valor_conocido
nombre_fichero_instancia_4 mejor_valor_conocido
...
</pre>

Para ejecutar mhAnalyzer se utilizará el siguiente comando:

<pre>
java -jar mhanalyzer.jar carpeta_resultados modo_optimización
</pre>

Donde el último parámetro (modo_optimización) será “min” si el problema es de minimización y
“max” si es de maximización.