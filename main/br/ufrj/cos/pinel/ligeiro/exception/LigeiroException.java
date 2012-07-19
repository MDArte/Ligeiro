package br.ufrj.cos.pinel.ligeiro.exception;

public class LigeiroException extends Exception
{
	public LigeiroException(Throwable exception)
	{
		super(exception);
	}

	public LigeiroException(String message)
	{
		super(message);
	}

	public LigeiroException(String message, Throwable exception)
	{
		super(message, exception);
	}
}
