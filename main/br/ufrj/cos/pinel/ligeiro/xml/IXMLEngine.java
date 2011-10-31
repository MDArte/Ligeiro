package br.ufrj.cos.pinel.ligeiro.xml;

import java.util.Collection;

import br.ufrj.cos.pinel.ligeiro.common.FPAConfig;
import br.ufrj.cos.pinel.ligeiro.data.BaseClass;
import br.ufrj.cos.pinel.ligeiro.data.Entity;
import br.ufrj.cos.pinel.ligeiro.data.Service;
import br.ufrj.cos.pinel.ligeiro.data.UseCase;
import br.ufrj.cos.pinel.ligeiro.xml.exception.ReadXMLException;

/**
 * Interface of the XML Engine
 * 
 * @author Roque Pinel
 *
 */
public interface IXMLEngine
{
	/**
	 * Reads the type of a statistic XML.
	 * 
	 * @param fileName the file name
	 * @return the type of the statistic XML
	 * @throws ReadXMLException
	 */
	public String readStatisticType (String fileName) throws ReadXMLException;

	/**
	 * Read a <code>Collection</code> of entities.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of entities
	 * @throws ReadXMLException
	 */
	public Collection<Entity> readEntities(String fileName) throws ReadXMLException;

	/**
	 * Reads a <code>Collection</code> of classes.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of classes
	 * @throws ReadXMLException
	 */
	public Collection<BaseClass> readClasses(String fileName) throws ReadXMLException;

	/**
	 * Reads a <code>Collection</code> of services.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of services
	 * @throws ReadXMLException
	 */
	public Collection<Service> readServices(String fileName) throws ReadXMLException;

	/**
	 * Reads a <code>Collection</code> of use cases.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of use cases
	 * @throws ReadXMLException
	 */
	public Collection<UseCase> readUseCases(String fileName) throws ReadXMLException;

	/**
	 * Reads the dependencies from a XML and return as a <code>Collection</code>
	 * of <code>BaseClass</code>.
	 * 
	 * @param fileName the file name
	 * @return the <code>Collection</code> of classes and dependencies
	 * @throws ReadXMLException
	 */
	public Collection<BaseClass> readDependencies(String fileName) throws ReadXMLException;

	/**
	 * Reads the FPA Configuration from a XML and return it.
	 * 
	 * @param fileName the file name
	 * @return the configuration
	 * @throws ReadXMLException
	 */
	public FPAConfig readFPAConfig(String fileName) throws ReadXMLException;
}
