package yanglong.frameutils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yanglong.frameutils.XMLReader;

/**
 * Author: Dream.YnagLong
 * Date: 13-12-27
 * Time: 下午3:46
 * E-mail:410357434@163.com
 * 功能：从XML文件中读取数据库连接信息，获取连接并读取数据库中所有表名称
 */
public class DButil {
	/**
	 * 获取数据库连接
	 * 
	 * @return 连接池
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(XMLReader.getConfigBean().getDriver()).newInstance();
			conn = DriverManager.getConnection(XMLReader.getConfigBean().getUrl(),
					XMLReader.getConfigBean().getUsername(), XMLReader
							.getConfigBean().getPassword());
			return conn;
		} catch (Exception e) {
			System.out.println("数据库连接失败！");
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获取数据库表名
	 * 
	 * @param conn 连接池
	 * @return 数据库表名
	 */
	public static List<String> getTableNames(Connection conn) {
		List<String> tableName = new ArrayList<String>();
		DatabaseMetaData metaData;
		try {
			//获取当前会话下数据库的信息
			metaData = conn.getMetaData();
            //获取当前会话下数据库的所有表
			ResultSet rs = metaData.getTables(conn.getCatalog(), "ROOT", null,
					new String[] { "TABLE" });
			//循环ResultSet取出表名
			while (rs.next()) {
				//输出表名
				//System.out.println(rs.getString("TABLE_NAME"));
				tableName.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			System.out.println("获取数据库表名出错！");
			e.printStackTrace();
		}
		return tableName;
	}

	/**
	 * 获取数据库表名
	 * 
	 * @return 数据库表名列表
	 */
	public static List<String> outTableNames() {
		return DButil.getTableNames(DButil.getConnection());
	}
}