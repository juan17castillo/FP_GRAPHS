package graphs.util.Exceptions;

public class VertexExistException extends Exception{
	
	private Object source;
	
	public VertexExistException( String message, Object idSource )
	{
		super( message );
		source = idSource;
	}
	
	public Object getSource( )
	{
		return source;
	}

}
