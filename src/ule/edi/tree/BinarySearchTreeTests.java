package ule.edi.tree;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;





public class BinarySearchTreeTests {

   
	/*
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
    */	
	private BinarySearchTreeImpl<Integer> ejemplo = null;
	
	
	/*
	* 10
	* |  5
	* |  |  2
	* |  |  |  ∅
	* |  |  |  ∅
	* |  |  ∅
	* |  20
	* |  |  15
	* |  |  |  12
	* |  |  |  |  ∅
	* |  |  |  |  ∅
	* |  |  ∅
  */
	private BinarySearchTreeImpl<Integer> other=null;
	
	@Before
	public void setupBSTs() {
		
			
		ejemplo = new BinarySearchTreeImpl<Integer>();
		ejemplo.insert(10, 20, 5, 2, 15, 30);
		
		Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}");
		
		
		other =new BinarySearchTreeImpl<Integer>();
		other.insert(10, 20, 5, 2, 15, 12);
		Assert.assertEquals(other.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}");
		
	    	}

	@Test
	public void testRemoveHoja() {
		ejemplo.remove(30);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, ∅}}",ejemplo.toString());
	}
	
	@Test
	public void testRemove1Hijo() {
		ejemplo.remove(5);
		Assert.assertEquals("{10, {2, ∅, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}",ejemplo.toString());
	}
	
	@Test
	public void testRemove2Hijos() {
		ejemplo.remove(10);
		Assert.assertEquals("{15, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}",ejemplo.toString());
	}
	
		@Test
		public void testTagDecendentsEjemplo() {
			ejemplo.tagDecendents();
			ejemplo.filterTags("decendents");
			Assert.assertEquals("{10 [(decendents, 5)], {5 [(decendents, 1)], {2 [(decendents, 0)], ∅, ∅}, ∅}, {20 [(decendents, 2)], {15 [(decendents, 0)], ∅, ∅}, {30 [(decendents, 0)], ∅, ∅}}}",ejemplo.toString());
		}
		
		@Test
		public void testTagHeightEjemplo() {
			other.tagHeight();
			other.filterTags("height");
			Assert.assertEquals("{10 [(height, 1)], {5 [(height, 2)], {2 [(height, 3)], ∅, ∅}, ∅}, {20 [(height, 2)], {15 [(height, 3)], {12 [(height, 4)], ∅, ∅}, ∅}, ∅}}",other.toString());
		}
		
		
		@Test
		public void testTagOnlySonEjemplo() {
		
		Assert.assertEquals(other.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}");
		Assert.assertEquals(3,other.tagOnlySonInorder());
		other.filterTags("onlySon");
		Assert.assertEquals("{10, {5, {2 [(onlySon, 1)], ∅, ∅}, ∅}, {20, {15 [(onlySon, 3)], {12 [(onlySon, 2)], ∅, ∅}, ∅}, ∅}}",other.toString());

		}
		
		
		@Test
		public void testInsertCollection() {
			
			BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<Integer>();
			LinkedList<Integer> coleccion = new LinkedList<Integer>();
			LinkedList<Integer> coleccion2 = new LinkedList<Integer>();
			coleccion.add(10);
			coleccion.add(20);
			coleccion.add(5);
			coleccion.add(2);
			coleccion.add(15);
			coleccion.add(30);
			coleccion2.add(10);
			arbol.insert(coleccion);
			arbol.insert(coleccion);
			arbol.insert(10,2);
			Assert.assertFalse(arbol.insert(10));
			Assert.assertEquals(arbol.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}");
			
		}
		
		@Test
		public void testRemove() {
			
			
			Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}");
			ejemplo.remove(15);
			Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}");
			ejemplo.remove(2,10);
			Assert.assertEquals(ejemplo.toString(), "{20, {5, ∅, ∅}, {30, ∅, ∅}}");	
			
		}
		@Test
		public void testIterator() {
			
			Iterator<Integer> iterador = ejemplo.iteratorWidth();
			
			Assert.assertTrue(iterador.next()==10);
			Assert.assertTrue(iterador.next()==5);
			Assert.assertTrue(iterador.next()==20);
			Assert.assertTrue(iterador.next()==2);
			Assert.assertTrue(iterador.next()==15);
			Assert.assertTrue(iterador.next()==30);
		}
		
		@Test
		public void testOnlySons() {
			BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<Integer>();
			arbol.insert(1,24,2,8,55,32,12,94,23,33,75,12,3,88,19);
			arbol.tagOnlySonInorder();
			//assert
			Assert.assertEquals("{1, ∅, {24 [(onlySon, 7)], {2, ∅, {8 [(onlySon, 6)], {3, ∅, ∅}, {12, ∅, {23 [(onlySon, 3)], {19 [(onlySon, 1)], ∅, ∅}, ∅}}}}, {55, {32, ∅, {33 [(onlySon, 5)], ∅, ∅}}, {94, {75 [(onlySon, 4)], ∅, {88 [(onlySon, 2)], ∅, ∅}}, ∅}}}}",arbol.toString());
			BinarySearchTreeImpl<Integer> arbol2 = new BinarySearchTreeImpl<Integer>();
			arbol2.insert(10, 9);
			arbol2.tagOnlySonInorder();
			//assert
			Assert.assertEquals("{10, {9 [(onlySon, 1)], ∅, ∅}, ∅}",arbol2.toString());
			arbol2.insert(11);
			arbol2.tagOnlySonInorder();
			//assert
			Assert.assertEquals("{10, {9 [(onlySon, 1)], ∅, ∅}, {11, ∅, ∅}}",arbol2.toString());
			BinarySearchTreeImpl<Integer> arbol3 = new BinarySearchTreeImpl<Integer>();
			arbol3.insert(10);
			arbol3.insert(5);
			arbol3.insert(6);
			arbol3.insert(4);
			arbol3.tagOnlySonInorder();
			Assert.assertEquals("{10, {5 [(onlySon, 1)], {4, ∅, ∅}, {6, ∅, ∅}}, ∅}",arbol3.toString());
		}
		
		//Tests de excepciones
		
		@Test(expected=IllegalArgumentException.class)
		public void testInsertCollExcp() {
			BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<Integer>();
			LinkedList<Integer> coleccion = new LinkedList<Integer>();
			coleccion.add(1);
			coleccion.add(null);
			arbol.insert(coleccion);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void testInsertArrayExcp() {
			ejemplo.insert(null,5,1,3);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void testInsertExcp() {
			Integer i = null;
			ejemplo.insert(i);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void testContainsExcp() {
			ejemplo.contains(null);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void testRemoveArrayExcp1() {
			ejemplo.remove(null,1,2);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void testRemoveExcp1() {
			Integer i = null;
			ejemplo.remove(i);
		}
		
		@Test(expected=NoSuchElementException.class)
		public void testRemoveArrayExcp2() {
			ejemplo.remove(5, 98);
		}
		
		@Test(expected=NoSuchElementException.class)
		public void testRemoveExcp2() {
			ejemplo.remove(98);
		}
	}


