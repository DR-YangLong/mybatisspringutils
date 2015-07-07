package yanglong.frameutils;

import java.util.List;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	public void testDBUtil() {
		new XMLReader("DBConnection.xml", "mysql");
		List<String> tableNames = DButil.outTableNames();
		System.out
				.println("******************************************DButil测试打印表名**************************************************************");
		for (String string : tableNames) {
			System.out.print(string + ":");
		}
		System.out
		.println();
		System.out
				.print("******************************************DButil测试打印表名**************************************************************");
	}
	
	public void testFormatUtil() {
		new XMLReader("DBConnection.xml", "mysql");
		List<String> tableNames = DButil.outTableNames();
		System.out
				.println("*****************************实体类名**************************************");
		String[] strs = FormatUtil.caseTableNames(tableNames);
		System.out.print("#");
		for (String string : strs) {
			System.out.print(string+"#");
		}
		System.out
		.println("*****************************实体类名**************************************");
	}
	
	public void testGeneratorUtil(){
		GeneratorUtil g = new GeneratorUtil();
		//参数请看原方法注释
		g.init("com.transfar.greentech.model", "com.transfar.greentech.mapper", "com.transfar.greentech.dao", "shiro",
				"DBConnection.xml", "mysql", "bean", "dao");
		GeneratorUtil.createBeanXML();
		GeneratorUtil.createGeneratorConfigXML();
		GeneratorUtil.createDAOXML();
	}
}
