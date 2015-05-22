package br.ufrj.cos.pinel.ligeiro;

import br.ufrj.cos.pinel.ligeiro.common.Util;

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
		Util.println("MethodSignature: " + methodSignature);

		Util.println("ClassName: " + Util.getMethodClassName(methodSignature));

		Util.println("MethodName: " + Util.getMethodName(methodSignature));

		String[] params = Util.getMethodParameters(methodSignature);
		for (int i = 0; i < params.length; i++)
		{
			Util.println("\tParam: -" + params[i].trim() + "-");
		}
	}
}
