package ule.edi.tree;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;


/**
 * Árbol binario de búsqueda (binary search tree, BST).
 * 
 * El código fuente está en UTF-8, y la constante 
 * EMPTY_TREE_MARK definida en AbstractTreeADT del
 * proyecto API debería ser el símbolo de conjunto vacío: ∅
 * 
 * Si aparecen caracteres "raros", es porque
 * el proyecto no está bien configurado en Eclipse para
 * usar esa codificación de caracteres.
 *
 * En el toString() que está ya implementado en AbstractTreeADT
 * se usa el formato:
 * 
 * 		Un árbol vacío se representa como "∅". Un árbol no vacío
 * 		como "{(información raíz), sub-árbol 1, sub-árbol 2, ...}".
 * 
 * 		Por ejemplo, {A, {B, ∅, ∅}, ∅} es un árbol binario con 
 * 		raíz "A" y un único sub-árbol, a su izquierda, con raíz "B".
 * 
 * El método render() también representa un árbol, pero con otro
 * formato; por ejemplo, un árbol {M, {E, ∅, ∅}, {S, ∅, ∅}} se
 * muestra como:
 * 
 * M
 * |  E
 * |  |  ∅
 * |  |  ∅
 * |  S
 * |  |  ∅
 * |  |  ∅
 * 
 * Cualquier nodo puede llevar asociados pares (clave,valor) para
 * adjuntar información extra. Si es el caso, tanto toString() como
 * render() mostrarán los pares asociados a cada nodo.
 * 
 * Con {@link #setTag(String, Object)} se inserta un par (clave,valor)
 * y con {@link #getTag(String)} se consulta.
 * 
 * 
 * Con <T extends Comparable<? super T>> se pide que exista un orden en
 * los elementos. Se necesita para poder comparar elementos al insertar.
 * 
 * Si se usara <T extends Comparable<T>> sería muy restrictivo; en
 * su lugar se permiten tipos que sean comparables no sólo con exactamente
 * T sino también con tipos por encima de T en la herencia.
 * 
 * @param <T>
 *            tipo de la información en cada nodo, comparable.
 */
public class BinarySearchTreeImpl<T extends Comparable<? super T>> extends
		AbstractBinaryTreeADT<T> {

   BinarySearchTreeImpl<T> father;  //referencia a su nodo padre)

	/**
	 * Devuelve el árbol binario de búsqueda izquierdo.
	 */
	protected BinarySearchTreeImpl<T> getLeftBST() {
		//	El atributo leftSubtree es de tipo AbstractBinaryTreeADT<T> pero
		//	aquí se sabe que es además de búsqueda binario
		//
		return (BinarySearchTreeImpl<T>) leftSubtree;
	}

	private void setLeftBST(BinarySearchTreeImpl<T> left) {
		this.leftSubtree = left;
	}
	
	/**
	 * Devuelve el árbol binario de búsqueda derecho.
	 */
	protected BinarySearchTreeImpl<T> getRightBST() {
		return (BinarySearchTreeImpl<T>) rightSubtree;
	}

	private void setRightBST(BinarySearchTreeImpl<T> right) {
		this.rightSubtree = right;
	}
	
	/**
	 * Árbol BST vacío
	 */
	public BinarySearchTreeImpl() {
		this.content = null;
		this.setRightBST(emptyBST(this));
		this.setLeftBST(emptyBST(this));
			
	}
	
	public BinarySearchTreeImpl(BinarySearchTreeImpl<T> father) {
		this.father = father;
		this.content = null;
		this.setRightBST(null);
		this.setLeftBST(null);
	}


	private BinarySearchTreeImpl<T> emptyBST(BinarySearchTreeImpl<T> father) {
		return new BinarySearchTreeImpl<T>(father);
	}
	
	/**
	 * Inserta los elementos de una colección en el árbol.
	 *  si alguno es 'null', NO INSERTA NINGUNO
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements
	 *            valores a insertar.
	 * @return numero de elementos insertados en el arbol (los que ya están no los inserta)
	 */
	public int insert(Collection<T> elements) {
		
		Iterator<T> iterador = elements.iterator();
		
		while(iterador.hasNext()) {
			if(iterador.next()==null)
				throw new IllegalArgumentException();
		}
		
		//Si no tiene elementos nulos
		iterador = elements.iterator();
		int elemsIntroducidos = 0;
		while(iterador.hasNext()) {
			T element = iterador.next();
			if( !this.contains(element) ) {
				this.insert(element);
				elemsIntroducidos++;
			}
		}
		return elemsIntroducidos;
	}

	/**
	 * Inserta los elementos de un array en el árbol.
	 *  si alguno es 'null', NO INSERTA NINGUNO
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements elementos a insertar.
	 * @return numero de elementos insertados en el arbol (los que ya están no los inserta)
	 */
	public int insert(T ... elements) {
		
		for(T element : elements) {
			if(element == null)
				throw new IllegalArgumentException();
		}
		
		int elemsIntroducidos = 0;
		for (T element : elements) {
			if(!this.contains(element)) {
				this.insert(element);
				elemsIntroducidos++;
			}		
		}
		return elemsIntroducidos;
	}
	
	/**
	 * Inserta (como hoja) un nuevo elemento en el árbol de búsqueda.
	 * 
	 * Debe asignarse valor a su atributo father (referencia a su nodo padre o null si es la raíz)
	 * 
	 * No se permiten elementos null. Si element es null dispara excepción: IllegalArgumentException 
	 *  Si el elemento ya existe en el árbol NO lo inserta.
	 * 
	 * @param element
	 *            valor a insertar.
	 * @return true si se pudo insertar (no existia ese elemento en el arbol, false en caso contrario
	 * @throws IllegalArgumentException si element es null           
	 */
	public boolean insert(T element) {
		if(element == null)
			throw new IllegalArgumentException();
		if(this.contains(element))
			return false;
		
		if(this.content == null) {	

			this.content = element;
			this.setLeftBST(emptyBST(this));
			this.setRightBST(emptyBST(this));
			return true;
		}else {
			
			if(element.compareTo(this.content)>0) //mayor
				return this.getRightBST().insert(element);
			else if(element.compareTo(this.content)<0)
				return this.getLeftBST().insert(element);
			else
				return false;
		}	
	}
	

	/**
	 * Busca el elemento en el árbol.
	 * 
	 * No se permiten elementos null. 
	 * 
	 * @param element   valor a buscar.
	 * @return true si el elemento está en el árbol, false en caso contrario          
	 */
	public boolean contains(T element) {
				
		if(element == null)
			throw new IllegalArgumentException();
		
		if(this.content == null)
			return false;
		
		if(element.compareTo(this.content)>0) //mayor
			return this.getRightBST().contains(element);
		else if(element.compareTo(this.content)<0)
			return this.getLeftBST().contains(element);
		else
			return true;
			
	}
	
	/**
	 * Elimina los valores en un array del árbol.
	 * O todos o ninguno; si alguno es 'null'o no lo contiene el árbol, no se eliminará ningún elemento
	 * 
	 * @throws NoSuchElementException si alguno de los elementos a eliminar no está en el árbol           
	 */
	public void remove(T ... elements) {
		
		for(T element : elements) {
			if(element==null)
				throw new IllegalArgumentException();
			if(!this.contains(element))
				throw new NoSuchElementException();
		}	
		for(T element : elements) 
			this.remove(element);
		
	}
	
	/**
	 * Elimina un elemento del árbol.
	 * 
	 * Si el elemento tiene dos hijos, se tomará el criterio de sustituir el elemento por
	 *  el menor de sus mayores y eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no está en el árbol           
	 */
	public void remove(T element) {
		
		
		if(element == null)
			throw new IllegalArgumentException();
		if(!this.contains(element))
			throw new NoSuchElementException();
			
		if(element.compareTo(this.content)>0) 
			this.getRightBST().remove(element);
		else if(element.compareTo(this.content)<0)
			this.getLeftBST().remove(element);
		else {
			
			if(this.getLeftBST().content==null && this.getRightBST().content==null) { //Si es hoja
				this.content = null;
				this.setLeftBST(null);
				this.setRightBST(null);
				
			}else if( (this.getLeftBST().content!=null) ^  (this.getRightBST().content!=null) ) { //Si tiene un hijo
				
				if(this.getLeftBST().content!=null) 
					this.content = getLeftBST().content;	
				else if(this.getRightBST().content!=null) 
					this.content = getRightBST().content;
					
				this.setLeftBST(emptyBST(this));
				this.setRightBST(emptyBST(this));
				
			}else { //Tiene 2 hijos
				
				BinarySearchTreeImpl<T> menorMayor = this.getRightBST();
				
				while(menorMayor.getLeftBST().content !=null)
					menorMayor = menorMayor.getLeftBST();
				
				this.content = menorMayor.content;
				this.getRightBST().remove(menorMayor.content);
			}
		}
	}
	
	/**
	 * Importante: Solamente se puede recorrer el árbol una vez
	 * 
	 * Etiqueta cada nodo con la etiqueta "height" y el valor correspondiente a la altura del nodo.
	 * 
	 * Por ejemplo, sea un árbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}
	 * 
     * 10
     * |  5
     * |  |  2
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  ∅
     * |  20
     * |  |  15
     * |  |  |  ∅
     * |  |  |  ∅ 
     * |  |  30
     * |  |  |  ∅
     * |  |  |  ∅
     * 
	 * 
	 * el árbol quedaría etiquetado:
	 * 
	 *   {10 [(height, 1)], {5 [(height, 2)], {2 [(height, 3)], ∅, ∅}, ∅},
	 *               {20 [(height, 2)], {15 [(height, 3)], {12 [(height, 4)], ∅, ∅}, ∅}, ∅}}
	 * 
	 */
	public void tagHeight() {
		if(this!=null) {
			
			this.setTag("height", 1);
			calculateHeight(this,0);
		}
	}
	
	private void calculateHeight(BinarySearchTreeImpl<T> tree, int prevHeight) {
		
		
		int height = prevHeight+1;
		tree.setTag("height", height);
		
		if(tree.getLeftBST().content!=null)  
			calculateHeight(tree.getLeftBST(), height);
		
		if(tree.getRightBST().content!=null) 
			calculateHeight(tree.getRightBST(), height);
		
		tree.setTag("height", height);
		
	}
	
	/**
	 * Importante: Solamente se puede recorrer el árbol una vez
	 * 
	 * Etiqueta cada nodo con el valor correspondiente al número de descendientes que tiene en este árbol.
	 * 
	 * Por ejemplo, sea un árbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}
	 * 
     * 10
     * |  5
     * |  |  2
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  ∅
     * |  20
     * |  |  15
     * |  |  |  ∅
     * |  |  |  ∅ 
     * |  |  30
     * |  |  |  ∅
     * |  |  |  ∅
     * 
	 * 
	 * el árbol quedaría etiquetado:
	 * 
	 *  {10 [(decendents, 5)], 
	 *       {5 [(decendents, 1)], {2 [(decendents, 0)], ∅, ∅}, ∅}, 
	 *       {20 [(decendents, 2)], {15 [(decendents, 0)], ∅, ∅}, {30 [(decendents, 0)], ∅, ∅}}}
	 * 
	 * 
	 */
	public void tagDecendents() {
		
		if(this!=null)
			calculateDecendents(this);
	}
		
	private int calculateDecendents(BinarySearchTreeImpl<T> tree) {
		
			int decendents = 0;
			if(tree.getLeftBST().content!=null)  
				decendents += calculateDecendents(tree.getLeftBST())+1;
			
			if(tree.getRightBST().content!=null) 
				decendents += calculateDecendents(tree.getRightBST())+1;
			
			tree.setTag("decendents", decendents);
			return decendents;
	}
	
	/**	
	 * Devuelve un iterador que recorre los elementos del arbol por niveles según 
         * el recorrido en anchura
	 * 
	 * Por ejemplo, con el árbol
	 * 
	 * 		{50, {30, {10, ∅, ∅}, {40, ∅, ∅}}, {80, {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvería el iterador que recorrería los nodos en el orden: 50, 30, 80, 10, 40, 60
	 * 
	 * 		
	 * 
	 * @return iterador para el recorrido en anchura
	 */

	public Iterator<T> iteratorWidth() {
	
		LinkedList<BinarySearchTreeImpl<T>> recorridoAnchura = new LinkedList<BinarySearchTreeImpl<T>>();
		LinkedList<T> elementos = new LinkedList<T>();
		
		recorridoAnchura.add(this);
		
		while(recorridoAnchura.size()!=0) {
			
			BinarySearchTreeImpl<T> actual = recorridoAnchura.poll();
			elementos.add(actual.content);
			
			if(actual.getLeftBST().content!=null)
				recorridoAnchura.add(actual.getLeftBST());
			if(actual.getRightBST().content!=null)
				recorridoAnchura.add(actual.getRightBST());
		}
		return elementos.iterator();
	}	

	/**
	 * Importante: Solamente se puede recorrer el árbol una vez
	 * 
	 * Calcula y devuelve el número de nodos que son hijos únicos 
	 *  y etiqueta cada nodo que sea hijo único (no tenga hermano hijo del mismo padre) 
	 *   con la etiqueta "onlySon" y el valor correspondiente a su posición según el 
	 *   recorrido inorden en este árbol. 
	 *   
	 *   La raíz no se considera hijo único.
	 * 
	 * Por ejemplo, sea un árbol "A", que tiene 3 hijos únicos, los va etiquetando según 
	 * su recorrido en inorden. 
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}
	 * 
     *
	 * el árbol quedaría etiquetado:
	 * 
	 * {10, {5, {2 [(onlySon, 1)], ∅, ∅}, ∅}, 
	 *      {20, {15 [(onlySon, 3)], {12 [(onlySon, 2)], ∅, ∅}, ∅}, ∅}}
	 * 
	 */
	public int tagOnlySonInorder() {
	
		
		int onlyChilds = 0;
		
		
		LinkedList<BinarySearchTreeImpl<T>> recorridoAnchuraIzq = new LinkedList<BinarySearchTreeImpl<T>>();
		LinkedList<BinarySearchTreeImpl<T>> elementosIzq = new LinkedList<BinarySearchTreeImpl<T>>();
		
		LinkedList<BinarySearchTreeImpl<T>> recorridoAnchuraDcha = new LinkedList<BinarySearchTreeImpl<T>>();
		LinkedList<BinarySearchTreeImpl<T>> elementosDcha = new LinkedList<BinarySearchTreeImpl<T>>();
		
		if(getLeftBST() != null)
		recorridoAnchuraIzq.add(getLeftBST());
		
		
		
		while(recorridoAnchuraIzq.size()!=0) {
			
			BinarySearchTreeImpl<T> actual = recorridoAnchuraIzq.poll();
			elementosIzq.add(actual);
			
			if(actual.getLeftBST().content!=null)
				recorridoAnchuraIzq.add(actual.getLeftBST());
			if(actual.getRightBST().content!=null)
				recorridoAnchuraIzq.add(actual.getRightBST());
		}
		
		if(getRightBST() != null)
		recorridoAnchuraDcha.add(getRightBST());
		

		
		while(recorridoAnchuraDcha.size()!=0) {
			
			BinarySearchTreeImpl<T> actual = recorridoAnchuraDcha.poll();
			elementosDcha.add(actual);
			
			if(actual.getLeftBST().content!=null)
				recorridoAnchuraDcha.add(actual.getLeftBST());
			if(actual.getRightBST().content!=null)
				recorridoAnchuraDcha.add(actual.getRightBST());
		}
		
		//Invertir las listas y a�adir tags
		
		while(!elementosIzq.isEmpty()) {
			
			BinarySearchTreeImpl<T> actual = elementosIzq.pollLast();
			BinarySearchTreeImpl<T> padreActual = actual.father;
			if(padreActual !=null && (padreActual.getLeftBST().content!=null) ^  (padreActual.getRightBST().content!=null)) {
				onlyChilds++;
				actual.setTag("onlySon", onlyChilds);
				
			}
		}
		
		while(!elementosDcha.isEmpty()) {
			
			BinarySearchTreeImpl<T> actual = elementosDcha.pollLast();
			BinarySearchTreeImpl<T> padreActual = actual.father;
			if(padreActual !=null && (padreActual.getLeftBST().content!=null) ^  (padreActual.getRightBST().content!=null)) {
				onlyChilds++;
				actual.setTag("onlySon", onlyChilds);
			}
		
		}

		return onlyChilds;		
	}
	
	
}

