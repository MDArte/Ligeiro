package br.ufrj.cos.pinel.ligeiro;

public class GenericTest
{
	protected static String getFilepath(String relativePath)
	{
		String basedir = System.getProperty("basedir");
		if (basedir != null)
			basedir += System.getProperty("file.separator");
		else
			basedir = "";
		return basedir + relativePath;
	}
}
