package test;

import graphs.util.IVertex;

public class Vert implements IVertex<Character>{

	char id;
	public Vert(char c)
	{
		id = c;
	}
	@Override
	public Character getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
