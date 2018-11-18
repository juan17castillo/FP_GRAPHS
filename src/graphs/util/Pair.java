package graphs.util;

public class Pair<K extends Comparable <? super K>, E> implements Comparable<Pair<K, E>>{

	private K key;
	private E value;
	public Pair(K k, E e) {
		key = k;
		value = e;
	}
	public K getKey() {
		return key;
	}
	public E getValue() {
		return value;
	}
	public void setValue(E value) {
		this.value = value;
	}
	@Override
	public int compareTo(Pair<K, E> o) {
		if(key.compareTo(o.key)>0)
		{
			return 1;
		}
		else if(key.compareTo(o.key)<0)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

	
}
