package yanglong.frameutils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import yanglong.frameutils.DButil;

/**
 * Author: Dream.YnagLong
 * Date: 13-12-27
 * Time: 下午3:46
 * E-mail:410357434@163.com
 * 功能：生成XML
 */
public class GeneratorUtil {
	private static String BeanPackage;
	private static String MapperPackage;
	private static String DaoPackage;
	private static String TargetProject;
	private static String beanPrefix;
	private static String daoPrefix;
	private static String exaSelect;
	private static String exaDelet;
	private static String exaUpdate;
	private static String exaCount;
	private static String beanScope;
	private static String daoScope;

	/**
	 * 初始化默认使用example类的接口属性配置都是false，如果要使用，请运行初始化后设置静态属性
	 * bean和dao的spring文件配置中托管的范围均默认为"prototype"模式
	 * @param BeanPackage
	 *            实体类存放包位置，eg:"com.yanglong.model"
	 * @param MapperPackage
	 *            映射文件存放包位置
	 * @param DaoPackage
	 *            DAO接口文件存放包位置
	 * @param TargetProject
	 *            目标项目，eg:"framworkutils"
	 * @param connectionXmlName
	 *            数据库连接参数文件名，此文件须位于src下
	 * @param databaseType
	 *            在数据库连接参数文件中配置的数据库父节点名称，eg:"mysql"/"orcl"
	 * @param beanPrefix
	 *            在spring配置文件中实体类想要用的前缀，eg:当使用"bean"作为前缀时会生成"bean_UserInfo"
	 * @param daoPrefix
	 *            dao接口在spring配置文件中想要的前缀，类似实体类前缀
	 */
	public void init(String BeanPackage, String MapperPackage,
			String DaoPackage, String TargetProject, String connectionXmlName,
			String databaseType, String beanPrefix, String daoPrefix) {
		GeneratorUtil.BeanPackage = BeanPackage;
		GeneratorUtil.MapperPackage = MapperPackage;
		GeneratorUtil.DaoPackage = DaoPackage;
		GeneratorUtil.TargetProject = TargetProject;
		GeneratorUtil.beanPrefix = beanPrefix;
		GeneratorUtil.daoPrefix = daoPrefix;
		new XMLReader(connectionXmlName, databaseType);
		GeneratorUtil.exaSelect = "false";
		GeneratorUtil.exaDelet = "false";
		GeneratorUtil.exaCount = "false";
		GeneratorUtil.exaUpdate = "false";
		GeneratorUtil.beanScope="prototype";
		GeneratorUtil.daoScope="prototype";
		
	}

	public static String getBeanPackage() {
		return BeanPackage;
	}

	public static void setBeanPackage(String beanPackage) {
		BeanPackage = beanPackage;
	}

	public static String getMapperPackage() {
		return MapperPackage;
	}

	public static void setMapperPackage(String mapperPackage) {
		MapperPackage = mapperPackage;
	}

	public static String getDaoPackage() {
		return DaoPackage;
	}

	public static void setDaoPackage(String daoPackage) {
		DaoPackage = daoPackage;
	}

	public static String getTargetProject() {
		return TargetProject;
	}

	public static void setTargetProject(String targetProject) {
		TargetProject = targetProject;
	}

	/**
	 * generatorConfig.xml生成方法，生成后须配置classPathEntry属性
	 */
	public static void createGeneratorConfigXML() {
		// 在内存中创建xml文件对象
		Document document = DocumentHelper.createDocument();
		// 添加dtd验证
		document.addDocType("generatorConfiguration",
				"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN",
				"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd");
		// 添加root节点
		Element rootElement = document.addElement("generatorConfiguration");
		// 添加classPathEntry节点
		Element driverPathElement = rootElement.addElement("classPathEntry");
		driverPathElement.addAttribute("location", "请更换为你的jdbc驱动地址的绝对路径！");// 添加属性
		// 添加context
		Element contextElement = rootElement.addElement("context");
		contextElement.addAttribute("id", "context1");
		// 添加jdbcConnection
		Element connElement = contextElement.addElement("jdbcConnection");
		connElement.addComment("JDBC连接属性！");
		connElement.addAttribute("driverClass", XMLReader.getConfigBean()
				.getDriver());
		connElement.addAttribute("connectionURL", XMLReader.getConfigBean()
				.getUrl());
		connElement.addAttribute("userId", XMLReader.getConfigBean()
				.getUsername());
		connElement.addAttribute("password", XMLReader.getConfigBean()
				.getPassword());
		// 添加javaModelGenerator
		Element modelElement = contextElement.addElement("javaModelGenerator");
		modelElement.addComment("实体类位置！");
		modelElement.addAttribute("targetPackage", GeneratorUtil.BeanPackage);
		modelElement.addAttribute("targetProject", GeneratorUtil.TargetProject);
		// 添加sqlMapGenerator
		Element mapperElement = contextElement.addElement("sqlMapGenerator");
		mapperElement.addComment("映射文件位置！");
		mapperElement
				.addAttribute("targetPackage", GeneratorUtil.MapperPackage);
		mapperElement
				.addAttribute("targetProject", GeneratorUtil.TargetProject);
		// 添加javaClientGenerator
		Element daoElement = contextElement.addElement("javaClientGenerator");
		daoElement.addComment("DAO类位置！");
		daoElement.addAttribute("targetPackage", GeneratorUtil.DaoPackage);
		daoElement.addAttribute("targetProject", GeneratorUtil.TargetProject);
		daoElement.addAttribute("type", "XMLMAPPER");
		// 拿到数据库中表名链表和对应的实体类名字符串数组
		List<String> tableNames = DButil.outTableNames();
		String[] beanNames = FormatUtil.caseTableNames(tableNames);
		// 取长度
		int length = beanNames.length;
		// 循环添table节点
		while (--length >= 0) {
			// 添加第一个table子节点
			Element tableElement = contextElement.addElement("table");
			// 添加属性
			tableElement.addAttribute("tableName", tableNames.get(length));
			tableElement.addAttribute("domainObjectName", beanNames[length]);
			tableElement.addAttribute("enableInsert", "true");
			tableElement.addAttribute("enableSelectByPrimaryKey", "true");
			tableElement.addAttribute("enableUpdateByPrimaryKey", "true");
			tableElement.addAttribute("enableDeleteByPrimaryKey", "true");
			tableElement.addAttribute("enableSelectByExample",
					GeneratorUtil.exaSelect);
			tableElement.addAttribute("enableDeleteByExample",
					GeneratorUtil.exaDelet);
			tableElement.addAttribute("enableCountByExample",
					GeneratorUtil.exaCount);
			tableElement.addAttribute("enableUpdateByExample",
					GeneratorUtil.exaUpdate);
		}
		/*************************   写XML   *******************************************/
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(
					System.getProperty("user.dir") + File.separator + "generatorConfig.xml")));
			xmlWriter.write(document);
			xmlWriter.close();
			System.out.println("生成generatorConfig.xml文档成功!");
			System.out.println("*********请配置classPathEntry节点！将location属性配置为你的JDBC驱动的绝对路径**********");
			System.out.println("*********请配置classPathEntry节点！将location属性配置为你的JDBC驱动的绝对路径**********");
			System.out.println("*********请配置classPathEntry节点！将location属性配置为你的JDBC驱动的绝对路径**********");
		} catch (IOException e) {
			System.out.println("生成generatorConfig.xml文档失败= =!");
			e.printStackTrace();
		}
	}

	 
	/**
	 *applicationContext-bean.xml生成方法
	 */
	public static void createBeanXML() {
		// 获取实体类名数组
		List<String> tableNames = DButil.outTableNames();
		String[] beanNames = FormatUtil.caseTableNames(tableNames);
		//获取带前缀的实体类名数组
		String[] preBeanNames=FormatUtil.getBeanNamesWithPrefix(beanNames, GeneratorUtil.beanPrefix);
		//获取带包名的实体类名数组
		String[] packageBeanNames=FormatUtil.getBeanNames(beanNames,GeneratorUtil.BeanPackage);
		// 在内存中创建xml文件对象
	    Document document = DocumentHelper.createDocument();
		// 添加dtd验证
		// 添加root节点
		Element rootElement = document.addElement("beans");
		rootElement.addAttribute("xmlns","http://www.springframework.org/schema/beans");
		rootElement.addAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
		rootElement.addAttribute("xmlns:p","http://www.springframework.org/schema/p");
		rootElement.addAttribute("xsi:schemaLocation","http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd");
		rootElement.addComment("以下是实体类配置");
		//获取类个数
		int length=beanNames.length;
		//循环添加bean子节点
		while(--length>=0){
		//添加第一个节点
		Element beanElement=rootElement.addElement("bean");
		//添加ID属性
		beanElement.addAttribute("id", preBeanNames[length]);
		//添加calss属性
		beanElement.addAttribute("class", packageBeanNames[length]);
		//添加scope属性
		beanElement.addAttribute("scope", GeneratorUtil.beanScope);
		}
		/*************************   写XML   *******************************************/
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(
					System.getProperty("user.dir") + File.separator + "applicationContext-"+GeneratorUtil.beanPrefix+".xml")));
			xmlWriter.write(document);
			xmlWriter.close();
			System.out.println("生成SpringBeanXML文档成功!");
		} catch (IOException e) {
			System.out.println("生成SpringBeanXML文档失败= =!");
			e.printStackTrace();
		}
	}

	/**
	 * applicationContext-dao.xml生成方法
	 */
	public static void createDAOXML() {
    // 获取实体类名数组
	List<String> tableNames = DButil.outTableNames();
    String[] beanNames = FormatUtil.caseTableNames(tableNames); 
    //获取dao类名数组
    String[] daoNames=FormatUtil.getDaoNames(beanNames);
    //获取带前缀的dao类名数组
    String[] prefixDaoNames=FormatUtil.getDaoNamesWithPrefix(daoNames, GeneratorUtil.daoPrefix);
    //获取带包名的dao类名数组
    String[] packageDaoNames=FormatUtil.getDaoNames(beanNames, GeneratorUtil.DaoPackage);
    // 在内存中创建xml文件对象
    Document document = DocumentHelper.createDocument();
	// 添加dtd验证
	// 添加root节点
	Element rootElement = document.addElement("beans");
	rootElement.addAttribute("xmlns","http://www.springframework.org/schema/beans");
	rootElement.addAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
	rootElement.addAttribute("xmlns:p","http://www.springframework.org/schema/p");
	rootElement.addAttribute("xsi:schemaLocation","http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd");
	rootElement.addComment("以下是DAO类配置");
	//获取类个数
	int length=beanNames.length;
	while(--length>=0){
	//添加第一个daoBean
	Element beanElement=rootElement.addElement("bean");
	beanElement.addAttribute("id",prefixDaoNames[length]);
	beanElement.addAttribute("class", "org.mybatis.spring.mapper.MapperFactoryBean");
	beanElement.addAttribute("scope", GeneratorUtil.daoScope);
	Element propertyElement=beanElement.addElement("property");
	propertyElement.addAttribute("name", "mapperInterface");
	propertyElement.addAttribute("value", packageDaoNames[length]);
	Element property1Element=beanElement.addElement("property");
	property1Element.addAttribute("name", "sqlSessionFactory");
	property1Element.addAttribute("ref", "sqlSessionFactory");
	  }
	/*************************   写XML   *******************************************/
	try {
		XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(
				System.getProperty("user.dir") + File.separator + "applicationContext-"+GeneratorUtil.daoPrefix+".xml")));
		xmlWriter.write(document);
		xmlWriter.close();
		System.out.println("生成SpringDaoXML文档成功!");
	} catch (IOException e) {
		System.out.println("生成SpringDaoXML文档失败= =!");
		e.printStackTrace();
	}
	}
}
