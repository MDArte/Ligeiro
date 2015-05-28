package br.ufrj.cos.pinel.ligeiro.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.ufrj.cos.pinel.ligeiro.GenericTest;
import br.ufrj.cos.pinel.ligeiro.common.FPAConfig;
import br.ufrj.cos.pinel.ligeiro.data.Attribute;
import br.ufrj.cos.pinel.ligeiro.data.BaseClass;
import br.ufrj.cos.pinel.ligeiro.data.Entity;
import br.ufrj.cos.pinel.ligeiro.data.Event;
import br.ufrj.cos.pinel.ligeiro.data.Method;
import br.ufrj.cos.pinel.ligeiro.data.Parameter;
import br.ufrj.cos.pinel.ligeiro.data.Service;
import br.ufrj.cos.pinel.ligeiro.data.State;
import br.ufrj.cos.pinel.ligeiro.data.UseCase;
import br.ufrj.cos.pinel.ligeiro.data.View;

/**
 * 
 * @author Roque Pinel
 * 
 */
public class XMLUtilTest extends GenericTest
{
	private static String classesFilename = getFilepath("data/AcademicSystem/statistics/statistics_controleacesso.xml");
	private static String configFilename = getFilepath("conf/LigeiroConfig.xml");
	private static String dependencyFilename = getFilepath("data/AcademicSystem/dependency/as-core-student.xml");
	private static String entitiesFilename = getFilepath("data/AcademicSystem/statistics/statistics_entities.xml");
	private static String servicesFilename = getFilepath("data/AcademicSystem/statistics/statistics_services.xml");
	private static String useCasesFilename = getFilepath("data/AcademicSystem/statistics/statistics_usecases.xml");
	
	@Test
	public void readServices() throws Exception
	{
		Object[] services = XMLUtil.readServices(servicesFilename).toArray();
		assertEquals(3, services.length);

		// Service 1

		Service service = (Service) services[0];
		assertEquals("br.ufrj.coppe.system.cs.student.StudentHandler", service.getName());
		assertEquals("br.ufrj.coppe.system.cs.student.StudentHandlerBeanImpl", service.getImplementationName());
		assertEquals("student", service.getModuleName());
		assertFalse(service.isWebService());
		assertEquals(0, service.getAttributes().size());
		assertEquals(0, service.getAssociations().size());

		Object[] methods = service.getMethods().toArray();
		assertEquals(4, methods.length);

		Method method = (Method) methods[0];
		assertEquals("listStudents", method.getName());
		assertEquals("handleListStudents", method.getImplementationName());
		assertEquals("java.util.Collection", method.getReturnType());

		Object[] parameters = method.getParameters().toArray();
		assertEquals(2, parameters.length);

		Parameter parameter = (Parameter) parameters[0];
		assertEquals("studentVO", parameter.getName());
		assertEquals("br.ufrj.coppe.system.vo.StudentVO", parameter.getType());

		parameter = (Parameter) parameters[1];
		assertEquals("paginacao", parameter.getName());
		assertEquals("java.lang.Integer", parameter.getType());

		// Service 2

		service = (Service) services[1];
		assertEquals("br.ufrj.coppe.system.cs.ws.InterfaceHandler", service.getName());
		assertEquals("br.ufrj.coppe.system.cs.ws.InterfaceHandlerBeanImpl", service.getImplementationName());
		assertEquals("ws", service.getModuleName());
		assertTrue(service.isWebService());
		assertEquals(0, service.getAttributes().size());
		assertEquals(0, service.getAssociations().size());

		// Service 3

		service = (Service) services[2];
		assertEquals("br2.gov.controleacesso.cs.CriaOperador", service.getName());
		assertEquals("br2.gov.controleacesso.cs.CriaOperadorBeanImpl", service.getImplementationName());
		assertEquals("controleacesso", service.getModuleName());
		assertFalse(service.isWebService());
		assertEquals(0, service.getAttributes().size());
		assertEquals(0, service.getAssociations().size());
	}

	@Test
	public void readEntities() throws Exception
	{
		Object[] entities = XMLUtil.readEntities(entitiesFilename).toArray();
		assertEquals(18, entities.length);
		
		// Entity 1
		
		Entity entity = (Entity) entities[0];
		assertEquals("br.gov.controleacesso.cd.ContextoCa", entity.getName());
		assertEquals("br.gov.controleacesso.cd.ContextoCaImpl", entity.getImplementationName());
		assertNull(entity.getModuleName());
		assertEquals(0, entity.getAssociations().size());

		Object[] methods = entity.getMethods().toArray();
		assertEquals(4, methods.length);

		Method method = (Method) methods[0];
		assertEquals("setContexto", method.getName());
		assertNull(method.getImplementationName());
		assertEquals("void", method.getReturnType());

		Object[] parameters = method.getParameters().toArray();
		assertEquals(1, parameters.length);

		Parameter parameter = (Parameter) parameters[0];
		assertEquals("contexto", parameter.getName());
		assertEquals("java.lang.String", parameter.getType());
		
		Object[] attributes = entity.getAttributes().toArray();
		assertEquals(2, attributes.length);

		Attribute attribute = (Attribute) attributes[0];
		assertEquals("contexto", attribute.getName());
		assertEquals("java.lang.String", attribute.getType());
		
		attribute = (Attribute) attributes[1];
		assertEquals("id", attribute.getName());
		assertEquals("java.lang.Long", attribute.getType());
	}
	
	@Test
	public void readUseCases() throws Exception
	{
		Object[] useCases = XMLUtil.readUseCases(useCasesFilename).toArray();
		assertEquals(4, useCases.length);

		// Use Case 1

		UseCase useCase = (UseCase) useCases[0];
		assertEquals("br.ufrj.coppe.system.uc.system.student.insert.InsertStudent", useCase.getName());
		assertEquals("system", useCase.getModuleName());
		
		Object[] views = useCase.getViews().toArray();
		assertEquals(1, views.length);
		
		View view = (View) views[0];
		assertEquals("Insert Student", view.getName());
		assertNull(view.getImplementationName());
		assertNull(view.getModuleName());
		assertEquals(0, view.getAttributes().size());
		assertEquals(0, view.getAssociations().size());
		
		Object[] methods = view.getMethods().toArray();
		assertEquals(1, methods.length);

		Method method = (Method) methods[0];
		assertEquals("Insert", method.getName());
		assertNull(method.getImplementationName());
		assertNull(method.getReturnType());
		
		State target = method.getTarget();
		assertEquals("insert student", target.getName());
		assertFalse(target.isFinalState());
		
		Object[] events = target.getEvents().toArray();
		assertEquals(1, events.length);
		
		Event event = (Event) events[0];
		assertEquals("insertStudent", event.getName());
		
		Object[] parameters = method.getParameters().toArray();
		assertEquals(3, parameters.length);

		Parameter parameter = (Parameter) parameters[0];
		assertEquals("id", parameter.getName());
		assertEquals("java.lang.Long", parameter.getType());
		
		parameter = (Parameter) parameters[1];
		assertEquals("name", parameter.getName());
		assertEquals("java.lang.String", parameter.getType());
		
		parameter = (Parameter) parameters[2];
		assertEquals("registration", parameter.getName());
		assertEquals("java.lang.String", parameter.getType());
		
		Object[] states = useCase.getStates().toArray();
		assertEquals(1, states.length);
		
		State state = (State) states[0];
		assertEquals("insert student", state.getName());
		assertFalse(state.isFinalState());
		
		target = state.getTarget();
		assertEquals("UpdateStudent", target.getName());
		assertTrue(target.isFinalState());
		
		assertNull(target.getHyperlinkApplicationName());
		assertNull(target.getHyperlinkModulo());
		assertNull(target.getHyperlink());
	}

	@Test
	public void readClasses() throws Exception
	{
		Object[] classes = XMLUtil.readClasses(classesFilename).toArray();
		assertEquals(1, classes.length);

		// Class 1

		BaseClass clazz = (BaseClass) classes[0];
		assertEquals("accessControl.ControleAcessoImpl", clazz.getName());
		assertNull(clazz.getImplementationName());
		assertNull(clazz.getModuleName());
		assertEquals(0, clazz.getAttributes().size());
		assertEquals(0, clazz.getAssociations().size());

		Object[] methods = clazz.getMethods().toArray();
		assertEquals(11, methods.length);

		Method method = (Method) methods[0];
		assertEquals("precisaTrocarSenha", method.getName());
		assertNull(method.getImplementationName());
		assertEquals("boolean", method.getReturnType());

		Object[] parameters = method.getParameters().toArray();
		assertEquals(1, parameters.length);

		Parameter parameter = (Parameter) parameters[0];
		assertEquals("operador", parameter.getName());
		assertEquals("accessControl.Operador", parameter.getType());
	}
	
	@Test
	public void readStatisticType() throws Exception
	{
		assertEquals("services", XMLUtil.readStatisticType(servicesFilename));
		assertEquals("entities", XMLUtil.readStatisticType(entitiesFilename));
		assertEquals("usecases", XMLUtil.readStatisticType(useCasesFilename));
		assertEquals("classes", XMLUtil.readStatisticType(classesFilename));
	}
	
	@Test
	public void readDependencies() throws Exception
	{
		Object[] classes = XMLUtil.readDependencies(dependencyFilename).toArray();
		assertEquals(2, classes.length);

		// Class 1

		BaseClass clazz = (BaseClass) classes[0];
		assertEquals("br.ufrj.coppe.system.cs.student.StudentHandlerBean", clazz.getName());
		assertNull(clazz.getImplementationName());
		assertNull(clazz.getModuleName());
		assertEquals(0, clazz.getAttributes().size());
		assertEquals(0, clazz.getAssociations().size());

		Object[] methods = clazz.getMethods().toArray();
		assertEquals(18, methods.length);

		Method method = (Method) methods[0];
		assertEquals("br.ufrj.coppe.system.cs.student.StudentHandlerBean.StudentHandlerBean()", method.getName());
		assertNull(method.getImplementationName());
		assertNull(method.getReturnType());
		assertEquals(0, method.getParameters().size());
		
		method = (Method) methods[1];
		assertEquals("br.ufrj.coppe.system.cs.student.StudentHandlerBean.ctx", method.getName());
		assertNull(method.getImplementationName());
		assertNull(method.getReturnType());
		assertEquals(0, method.getParameters().size());
	}
	
	@Test
	public void readDependencies2() throws Exception
	{
		FPAConfig fpaConfig = XMLUtil.readFPAConfiguration(configFilename);
		assertEquals(10, fpaConfig.getIFLComplexityValue(7, 0));
	}
}
