package br.ufrj.cos.pinel.ligeiro.xml.handler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import br.ufrj.cos.pinel.ligeiro.common.Util;

/**
 * The generic listener used to read the XML files.
 * 
 * @author Roque Pinel
 *
 */
public class GenericHandler extends DefaultHandler
{
	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	public void startDocument() throws SAXException
	{
		Util.println("> Start document.");
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	public void endDocument() throws SAXException
	{
		Util.println("> End document.");
	}
}
