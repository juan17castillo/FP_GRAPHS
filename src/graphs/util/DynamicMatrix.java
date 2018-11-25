package graphs.util;

import java.util.ArrayList;

public class DynamicMatrix<K, V extends IVertex<K>, A extends IEdge> {

	private ArrayList<ArrayList<A>> matrix;
	private int dimension;
	
	public DynamicMatrix()
	{
		matrix = new ArrayList<>();
		dimension = 0;
	}
	
	public int addElement()
	{
		matrix.add(new ArrayList<>());
		for (int i = 0; i < matrix.size(); i++) {
			matrix.get(matrix.size()-1).add(null);
		}
		for (int i = 0; i < matrix.size()-1; i++) {
			matrix.get(i).add(null);
		}
		dimension++;
		return matrix.size()-1;
		
	}
	public void add(A k, int i, int j) throws IndexOutOfBoundsException
	{
		if(i>dimension-1||j>dimension-1)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			matrix.get(i).remove(j);
			matrix.get(i).add(j, k);
		}
	}
	
	public A get(int i, int j) throws IndexOutOfBoundsException
	{
		if(i>matrix.size()-1||j>matrix.get(0).size()-1)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			return matrix.get(i).get(j);
		}
	}
	
	public int getDimension()
	{
		return dimension;
	}
	
	public void removeElement(int k)
	{
		dimension--;
		matrix.remove(k);
		for (int i = 0; i < matrix.size(); i++) {
			matrix.get(i).remove(k);
		}
	}
	
	public void print()
	{
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if(get(i, j)!=null)
				System.out.print(get(i, j).getWeight()+ " ");
				else System.out.print("0"+" ");
			}
			System.out.println();
		}
	}
}
