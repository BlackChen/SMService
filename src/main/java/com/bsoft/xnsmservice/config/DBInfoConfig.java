package com.bsoft.xnsmservice.config;

/**
 * @ClassName DBInfoConfig
 * @Description 数据库信息配置
 * Created by blackchen on 2020/9/24 11:00
 */
public class DBInfoConfig {
	/**
	 * MySQL 8.0 以上版本的数据库连接有所不同：
	 * 1、MySQL 8.0 以上版本驱动包版本 mysql-connector-java-8.0.16.jar。
	 * 2、com.mysql.jdbc.Driver 更换为 com.mysql.cj.jdbc.Driver。
	 * MySQL 8.0 以上版本不需要建立 SSL 连接的，需要显示关闭。
	 * allowPublicKeyRetrieval=true 允许客户端从服务器获取公钥。
	 * 最后还需要设置 CST。
	 * 加载驱动与连接数据库方式如下：
	 * Class.forName("com.mysql.cj.jdbc.Driver");
	 * conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","root","password");
	 */
	private static String driver;//数据库驱动
	//本地数据库的地址 wl_test_database是我的数据库名称
	private static String url;//(mysql://localhost:3306/mysql)
	private static String username;//用户名
	private static String password;//用户root的密码，这里是我设置的密码
	private static String tablename;//用户表名

	public static void ConfigDBInfo(SMServiceType type){
		switch (type){
			case CMCC_TZPH_Normal:{
				driver = "com.mysql.cj.jdbc.Driver";
				url = "jdbc:mysql://192.168.0.104:3306/wx_db";
				username = "root";
				password = "Tzxyy@1234";
				tablename = "sms_send_history";
			}break;
			case CMCC_TZPH_Model: {
				driver = "com.mysql.cj.jdbc.Driver";
				url = "jdbc:mysql://192.168.0.104:3306/wx_db";
				username = "root";
				password = "Tzxyy@1234";
				tablename = "sms_send_history";
			}break;

			default:
				throw new IllegalStateException("Unexpected value: " + type);
		}
	}

	public static String getTablename() {
		return tablename;
	}

	public static void setTablename(String tablename) {
		DBInfoConfig.tablename = tablename;
	}

	public static String getDriver() {
		return driver;
	}

	public static void setDriver(String driver) {
		DBInfoConfig.driver = driver;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		DBInfoConfig.url = url;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		DBInfoConfig.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		DBInfoConfig.password = password;
	}
}
