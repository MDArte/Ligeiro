package br.ufrj.coppe.pinel.express.xml.exception;

public class WriteXMLException extends Exception
{
	public WriteXMLException(Throwable exception)
	{
		super(exception);
	}

	public WriteXMLException(String message)
	{
		super(message);
	}

	public WriteXMLException(String message, Throwable exception)
	{
		super(message, exception);
	}
}
