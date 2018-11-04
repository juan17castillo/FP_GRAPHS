package graphs.util.Exceptions;

public class EdgeDoesnotExistException extends Exception{
	
	private Object source;

	private Object end;
	
	public EdgeDoesnotExistException( String message, Object idSource, Object idEnd )
	{
		super( message );
		source = idSource;
		end = idEnd;
	}
	
	public Object getSource( )
	{
		return source;
	}

	public Object getEnd( )
	{
		return end;
	}
}
