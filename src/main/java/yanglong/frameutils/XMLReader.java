package yanglong.frameutils;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Author: Dream.YnagLong
 * Date: 13-12-27
 * Time: 下午3:46
 * E-mail:410357434@163.com
 * 功能：读取XML指定节点信息封装到内部类
 */
public class XMLReader {
	private static String xmlName;// 配置文件名称
	private static String nodeName;// 数据库类型
	private static Element root;// XML文件根节点
	private static Element subNode;// 数据库类型对应节点
	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		XMLReader.xmlName = xmlName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		XMLReader.nodeName = nodeName;
	}

	public Element getSubNode() {
		return subNode;
	}

	public void setSubNode(Element subNode) {
		XMLReader.subNode = subNode;
	}

	public void setRoot(Element root) {
		XMLReader.root = root;
	}

	public XMLReader(String xmlName, String nodeName) {
		XMLReader.xmlName = xmlName;
		XMLReader.nodeName = nodeName;
	}

	/**
	 * 内部类，数据库连接配置属性
	 * 
	 * @author DR.YangLong
	 * 
	 */
	public static class ConfigBean {
		//用戶名
		private String username;
		//密码
		private String password;
		//驱动
		private String driver;
		//链接
		private String url;

		public String getDriver() {
			return driver;
		}

		public void setDriver(String driver) {
			this.driver = driver;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}

	/**
	 * 获取根节点
	 * 
	 * @throws Exception
	 */
	public static void getRoot() throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(System.getProperty("user.dir"), XMLReader.xmlName));
		Element root = document.getRootElement();
		XMLReader.root = root;
	}

	/**
	 * 获取数据库类型对应节点
	 * 
	 * @param element
	 *            根节点
	 * @param nodeName
	 *            数据库类型名称
	 */
	@SuppressWarnings("rawtypes")
	public static void getNodeByName(Element element, String nodeName) {
		// 遍历根节点的所有子节点找出nodeName名称一样的子节点
		for (Iterator iter = element.elementIterator(); iter.hasNext();) {
			Element et = (Element) iter.next();
			if (et.getName().equals(nodeName)) {
				XMLReader.subNode = et;
				break;
			}
			if (et instanceof Element) {
				getNodeByName(et, nodeName);
			}
		}
	}

	/**
	 * 获取数据库连接配置属性对象
	 * 
	 * @return
	 */
	public static ConfigBean getConfigBean(){
		try{
		XMLReader.getRoot();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("获取根节点出错，请检查xml文件位置和数据库类型名称参数nodeName！");
		}
		XMLReader.getNodeByName(XMLReader.root, XMLReader.nodeName);
		ConfigBean bean = new ConfigBean();
		bean.setUsername(((Element) XMLReader.subNode).element("username").getText());
		bean.setPassword(((Element) XMLReader.subNode).element("password").getText());
		bean.setDriver(((Element) XMLReader.subNode).element("driver").getText());
		bean.setUrl(((Element) XMLReader.subNode).element("url").getText());
		return bean;
	}
    //test
	public static void main(String[] args) throws Exception {
		new XMLReader("DBConnection.xml", "orcl");
		System.out.println(XMLReader.getConfigBean().getUsername());
		System.out.println(XMLReader.getConfigBean().getPassword());
		System.out.println(XMLReader.getConfigBean().getDriver());
		System.out.println(XMLReader.getConfigBean().getUrl());
	}
}
