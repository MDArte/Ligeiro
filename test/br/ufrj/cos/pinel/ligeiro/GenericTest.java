package br.ufrj.cos.pinel.ligeiro;

public class GenericTest
{
	protected static String classesFilename = getFilepath("data/AcademicSystem/statistics/statistics_controleacesso.xml");
	protected static String configFilename = getFilepath("conf/LigeiroConfig.xml");
	protected static String sampleDependencyFilename = getFilepath("data/AcademicSystem/dependency/amostra.xml");
	protected static String entitiesFilename = getFilepath("data/AcademicSystem/statistics/statistics_entities.xml");
	protected static String servicesFilename = getFilepath("data/AcademicSystem/statistics/statistics_services.xml");
	protected static String useCasesFilename = getFilepath("data/AcademicSystem/statistics/statistics_usecases.xml");

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
