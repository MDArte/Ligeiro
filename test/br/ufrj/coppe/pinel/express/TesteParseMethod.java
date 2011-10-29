package br.ufrj.coppe.pinel.express;

import br.ufrj.coppe.pinel.express.common.Util;

public class TesteParseMethod
{
	private static String methodSignature = "br.ufrj.coppe.system.cs.student.pbi.StudentHandlerPBI.listStudents(br.ufrj.coppe.system.vo.StudentVO, java.lang.Integer)";
	//private static String methodSignature = "br.ufrj.coppe.system.uc.system.student.search.SearchStudentForm";
	//private static String methodSignature = "br.ufrj.coppe.system.uc.system.student.search.SearchStudentForm.getName()";

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("MethodSignature: " + methodSignature);

		System.out.println("ClassName: " + Util.getClassName(methodSignature));

		System.out.println("MethodName: " + Util.getMethodName(methodSignature));

		String[] params = Util.getParameters(methodSignature);
		for (int i = 0; i < params.length; i++)
		{
			System.out.println("\tParam: -" + params[i].trim() + "-");
		}
	}
}
