package graphs.util.Exceptions;

public class EdgeExistException extends Exception{
	
	private Object source;

	private Object end;
	
	public EdgeExistException( String message, Object idSource, Object idEnd )
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
