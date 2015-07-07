package yanglong.frameutils;
import java.util.List;
/**
 * Author: Dream.YnagLong
 * Date: 13-12-27
 * Time: 下午3:46
 * E-mail:410357434@163.com
 * 功能：格式化辅助类，将表名格式化，生成符合要求的字符串数组
 */
public class FormatUtil {
	// 首字母转小写
	public static String toLowerCaseFirst(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	// 首字母转大写
	public static String toUpperCaseFirst(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	// 将string数组转换为string
	public static String arrayToString(String[] args) {
		String str = "";
		for (String string : args) {
			str += string;
		}
		return str;
	}

	/**
	 * 获取单词首字母大写的表名对应的类名
	 * 
	 * @param tableNames
	 *            从数据库获取到的表名
	 * @return 类名数组
	 */
	public static String[] caseTableNames(List<String> tableNames) {
		int length = tableNames.size();
		String[] strs = new String[length];
		while (--length >= 0) {
			String s = tableNames.get(length);
			// 如果包含下划线
			if (s.indexOf("_") != -1) {
				// 按下划线分成字符串数组
				String[] ss = s.split("_");
				for (int i = 0; i < ss.length; i++) {
					ss[i] = toUpperCaseFirst(ss[i]);
				}
				// 转换字符数组到字符串并放入结果字符数组中
				strs[length] = arrayToString(ss);
			} else {// 如果不包含下划线
				strs[length] = toUpperCaseFirst(s);
			}
		}
		return strs;
	}

	/**
	 * 获取带包名实体类名
	 * 
	 * @param args
	 *            实体类名数组
	 * @param packageName
	 *            包名
	 * @return 带包名的实体类名数组
	 */
	public static String[] getBeanNames(String[] args, String packageName) {
		int length = args.length;
		String[] strs = new String[length];
		while (--length >= 0) {
			strs[length] = packageName + "." + args[length];
		}
		return strs;
	}

	/**
	 * 获取带前缀的实体类名称
	 * 
	 * @param args
	 *            实体类名数组
	 * @param prefix
	 *            前缀名
	 * @return 带前缀的实体类名称
	 */
	public static String[] getBeanNamesWithPrefix(String[] args, String prefix) {
		int length = args.length;
		String[] strs = new String[length];
		while (--length >= 0) {
			strs[length] = prefix + "_" + args[length];
		}
		return strs;
	}

	/**
	 * 获取实体类名对应的DAO类名
	 * 
	 * @param args
	 *            实体类名数组
	 * @return DAO类名
	 */
	public static String[] getDaoNames(String[] args) {
		int length = args.length;
		String[] strs = new String[length];
		while (--length >= 0) {
			strs[length] = args[length] + "Mapper";
		}
		return strs;
	}

	/**
	 * 获取带包名的DAO类名
	 * 
	 * @param args
	 *            实体类名数组
	 * @param packgename
	 *            包名
	 * @return 带包名的dao类名数组
	 */
	public static String[] getDaoNames(String[] args, String packgename) {
		int length = args.length;
		String[] strs = new String[length];
		while (--length >= 0) {
			strs[length] = packgename + "." + args[length] + "Mapper";
		}
		return strs;
	}

	/**
	 * 获取带前缀的DAO类名
	 * 
	 * @param args
	 *            dao类名数组
	 * @param prefix
	 *            前缀
	 * @return 带前缀的dao数组
	 */
	public static String[] getDaoNamesWithPrefix(String[] args, String prefix) {
		int length = args.length;
		String[] strs = new String[length];
		while (--length >= 0) {
			strs[length] = prefix + "_" + args[length];
		}
		return strs;
	}
}
