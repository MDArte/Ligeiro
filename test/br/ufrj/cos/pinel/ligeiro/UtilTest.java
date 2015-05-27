package br.ufrj.cos.pinel.ligeiro;

import org.junit.Test;

import br.ufrj.cos.pinel.ligeiro.common.Util;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * 
 * @author Roque Pinel
 * 
 */
public class UtilTest
{
	private static String classSignature = "br.ufrj.coppe.system.cs.student.pbi.StudentHandlerPBI";
	private static String methodSignature = classSignature + ".listStudents(br.ufrj.coppe.system.vo.StudentVO, java.lang.Integer)";

	@Test
	public void parseClassName()
	{
		assertEquals("StudentHandlerPBI", Util.getClassName(classSignature));
	}
	
	@Test
	public void parseMethodClassName()
	{
		assertEquals("br.ufrj.coppe.system.cs.student.pbi.StudentHandlerPBI", Util.getMethodClassName(methodSignature));	
	}
	
	@Test
	public void parseMethodName()
	{
		assertEquals("listStudents", Util.getMethodName(methodSignature));
	}
	
	@Test
	public void parseMethodParameters()
	{
		assertArrayEquals(new String[]{"br.ufrj.coppe.system.vo.StudentVO", "java.lang.Integer"}, Util.getMethodParameters(methodSignature));
	}
}
