package graphs.util.Exceptions;

public class VertexDoesnotExistException extends Exception{
	
	private Object source;
	
	public VertexDoesnotExistException( String message, Object idSource )
	{
		super( message );
		source = idSource;
	}
	
	public Object getSource( )
	{
		return source;
	}

}
