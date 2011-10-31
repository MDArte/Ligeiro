package br.ufrj.cos.pinel.ligeiro.exception;

public class ExpressFPAException extends Exception
{
	public ExpressFPAException(Throwable exception)
	{
		super(exception);
	}

	public ExpressFPAException(String message)
	{
		super(message);
	}

	public ExpressFPAException(String message, Throwable exception)
	{
		super(message, exception);
	}
}
