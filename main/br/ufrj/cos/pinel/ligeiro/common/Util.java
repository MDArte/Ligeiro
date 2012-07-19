package br.ufrj.cos.pinel.ligeiro.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import br.ufrj.cos.pinel.ligeiro.data.Association;
import br.ufrj.cos.pinel.ligeiro.data.Attribute;
import br.ufrj.cos.pinel.ligeiro.data.Entity;
import br.ufrj.cos.pinel.ligeiro.data.Event;
import br.ufrj.cos.pinel.ligeiro.data.IBaseClass;
import br.ufrj.cos.pinel.ligeiro.data.Method;
import br.ufrj.cos.pinel.ligeiro.data.Parameter;
import br.ufrj.cos.pinel.ligeiro.exception.LigeiroException;
import br.ufrj.cos.pinel.ligeiro.report.FPAReport;
import br.ufrj.cos.pinel.ligeiro.report.ReportResult;

/**
 * @author Roque Pinel
 * 
 */
public class Util
{
//	private static Logger logger = Logger.getLogger(Util.class.getName());
//
//	static
//	{
//		BasicConfigurator.configure();
//	}

	public static void print(String s)
	{
		if (Constants.DEBUG)
			System.out.print(s);
			//logger.info(s);
	}

	public static void println(String s)
	{
		if (Constants.DEBUG)
			System.out.println(s);
			//logger.info(s);
	}

	public static void printClasses(Collection<IBaseClass> classes)
	{
		for (IBaseClass clazz : classes)
		{
			printClass(clazz);
		}
	}

	public static void printClass(IBaseClass clazz)
	{
		Util.println("Class Name: " + clazz.getName());
		Util.println("      Impl Name: " + clazz.getImplementationName());
		Util.println("      Module: " + clazz.getModuleName());

		Util.println("\tMethods:");
		for (Method method : clazz.getMethods())
		{
			Util.println("\t\tName: " + method.getName());
			Util.println("\t\tImplementationName: " + method.getImplementationName());
			Util.println("\t\tReturn: " + method.getReturnType());

			if (method.getTarget() != null)
			{
				Util.println("\t\tTarget: " + method.getTarget().getName());
				Util.println("\t\t  FINAL: " + method.getTarget().isFinalState());

				if (method.getTarget().isFinalState())
				{
					Util.println("\t\t  hyperlinkApplicationName: " + method.getTarget().getHyperlinkApplicationName());
					Util.println("\t\t  hyperlinkModulo: " + method.getTarget().getHyperlinkModulo());
					Util.println("\t\t  hyperlink: " + method.getTarget().getHyperlink());
				}

				for (Event event : method.getTarget().getEvents())
				{
					Util.println("\t\t  * " + event.getName());
				}
			}

			for (Parameter parameter : method.getParameters())
			{
				Util.println("\t\t\tName: " + parameter.getName());
				Util.println("\t\t\t\tType: " + parameter.getType());
			}
		}

		Util.println("\tAttributes:");
		for (Attribute attribute : clazz.getAttributes())
		{
			Util.println("\t\tName: " + attribute.getName());
			Util.println("\t\t\tType: " + attribute.getType());
		}

		Util.println("\tAssociations:");
		for (Association association : clazz.getAssociations())
		{
			Util.println("\t\tName: " + association.getName());
		}
	}

	public static boolean isEmptyOrNull(String s)
	{
		if (s == null || s.equals(""))
			return true;
		return false;
	}

	public static int countDET(Entity entity, Map<String, Entity> entities)
	{
		int counter = 0;

		IBaseClass clazz = entity;

		while (clazz != null)
		{
			for (Attribute attribute : clazz.getAttributes())
			{
				if (!attribute.isIdentifier())
					counter++;
			}

			clazz = clazz.getExtendsClass() == null ? null : entities.get(clazz.getExtendsClass());
		}

		// counting associations
		for (Association association : entity.getAssociations())
		{
			if (association.isManyToOne())
				counter++;
		}

		return counter;
	}

	public static String[] getCSVColumns(String s, char delim)
	{
		int counter = 0;

		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == delim)
				counter++;

		String[] columns = new String[counter + 1];

		int begin = 0;
		int end = s.indexOf(delim);

		counter = 0;
		while (end >= 0)
		{
			columns[counter++] = s.substring(begin, end);

			begin = end + 1;
			end = s.indexOf(delim, begin);
		}
		columns[counter] = s.substring(begin, s.length());

		return columns;
	}

	public static String getClassName(String classSignature)
	{
		String className = null;

		int dot = classSignature.lastIndexOf('.');

		if (dot >= 0 && dot < classSignature.length() - 1)
			className = classSignature.substring(dot + 1, classSignature.length());

		return className;
	}

	public static String getMethodClassName(String methodSignature)
	{
		String className = null;

		int parentheses = methodSignature.indexOf('(');

		if (parentheses >= 0)
		{
			int dot = methodSignature.lastIndexOf('.', parentheses);

			if (dot >= 0)
				className = methodSignature.substring(0, dot);
		}

		return className;
	}

	public static String getMethodName(String methodSignature)
	{
		String methodName = null;

		int parentheses = methodSignature.indexOf('(');

		if (parentheses >= 0)
		{
			int dot = methodSignature.lastIndexOf('.', parentheses);

			if (dot >= 0)
			{
				methodName = methodSignature.substring(dot + 1, parentheses);
			}
		}

		return methodName;
	}

	public static String[] getMethodParameters(String methodSignature)
	{
		String[] params = new String[0];

		int firstParentheses = methodSignature.indexOf('(');
		int lastParentheses = methodSignature.lastIndexOf(')');

		// if it is a really a method
		if (firstParentheses >= 0 && lastParentheses >= 0 && firstParentheses < lastParentheses)
		{
			String paramsStr = methodSignature.substring(firstParentheses + 1, lastParentheses);

			if (!Util.isEmptyOrNull(paramsStr))
				params = paramsStr.split(",");
		}

		return params;
	}

	public static void writeCSVReport(FPAReport fpaReport, String filename) throws LigeiroException
	{
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));

			out.write("Namespace;DF / TF;Type;RET / FTR;DET;Complexity;PF\n");

			out.write("Data Functions;;;;;;\n");

			for (ReportResult reportResult : fpaReport.getDFReport())
			{
				out.write(reportResult.getNamespace() + ";");
				out.write(reportResult.getElement() + ";");
				out.write(reportResult.getType() + ";");
				out.write(reportResult.getRet_ftr() + ";");
				out.write(reportResult.getDet() + ";");
				out.write(reportResult.getComplexity() + ";");
				out.write(reportResult.getComplexityValue() + "\n");
			}

			out.write("Transaction Functions;;;;;;\n");

			for (ReportResult reportResult : fpaReport.getTFReport())
			{
				out.write(reportResult.getNamespace() + ";");
				out.write(reportResult.getElement() + ";");
				out.write(reportResult.getType() + ";");
				out.write(reportResult.getRet_ftr() + ";");
				out.write(reportResult.getDet() + ";");
				out.write(reportResult.getComplexity() + ";");
				out.write(reportResult.getComplexityValue() + "\n");
			}

			out.write(";;;;;Total;");
			out.write(Integer.toString(fpaReport.getReportTotal()));
			out.write("\n");

			out.close();
		}
		catch (IOException e)
		{
			throw new LigeiroException(e.getMessage());
		}
	}
}
