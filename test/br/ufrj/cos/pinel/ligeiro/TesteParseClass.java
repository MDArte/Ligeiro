package br.ufrj.cos.pinel.ligeiro;

import br.ufrj.cos.pinel.ligeiro.common.Util;

public class TesteParseClass
{
	private static String classSignature = "br.ufrj.coppe.system.cs.student.pbi.StudentHandlerPBI";
	//private static String classSignature = "pbi.S";
	//private static String classSignature = "StudentHandlerPBI";

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("ClassSignature: " + classSignature);

		System.out.println("ClassName: " + Util.getClassName(classSignature));
	}
}
