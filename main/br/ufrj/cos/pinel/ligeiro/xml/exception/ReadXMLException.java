package br.ufrj.cos.pinel.ligeiro.xml.exception;

public class ReadXMLException extends Exception
{
	public ReadXMLException(Throwable exception)
	{
		super(exception);
	}

	public ReadXMLException(String message)
	{
		super(message);
	}

	public ReadXMLException(String message, Throwable exception)
	{
		super(message, exception);
	}
}
