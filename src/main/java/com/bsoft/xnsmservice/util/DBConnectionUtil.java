package com.bsoft.xnsmservice.util;

import com.bsoft.xnsmservice.config.DBInfoConfig;
import com.bsoft.xnsmservice.config.SMServiceType;
import com.bsoft.xnsmservice.entity.SmsSendHistory;

import java.net.SocketException;
import java.sql.*;

/**
 * @ClassName DBConnectionUtil
 * @Description TODO
 * Created by blackchen on 2020/9/24 10:12
 */
public class DBConnectionUtil {
	private static Connection conn;//创建连接器，用完即关

	/**
	 * 连接池工具类，返回唯一的一个数据库连接池对象,单例模式
	 */
	private static DBConnectionPool poolInstance = null;
	private DBConnectionUtil() {//私有静态方法
	}

	public static DBConnectionPool GetPoolInstance() {
		if(poolInstance == null) {
			poolInstance = new DBConnectionPool( DBInfoConfig.getDriver(), DBInfoConfig.getUrl(),//+"?useUnicode=true&characterEncoding=utf-8",
					 DBInfoConfig.getUsername(), DBInfoConfig.getPassword()
			);

			try {
				poolInstance.createPool();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return poolInstance;
	}
	/**
	 * 获取数据库连接
	 */
	public static void getDBConnection(SMServiceType type) throws ClassNotFoundException,SQLException  {
		DBInfoConfig.ConfigDBInfo(type);
		try {//相当于 new com.mysql.jdbc.Driver()，在环境中创建此对象，如果没有该类，则抛出ClassNotFoundException。
			//如果catch的类别中中没有设置ClassNotFoundException，则会报错
			Class.forName(DBInfoConfig.getDriver());//注册jdbc驱动

		//使用url、用户名、密码进行连接
			conn = DriverManager.getConnection(DBInfoConfig.getUrl(), DBInfoConfig.getUsername(), DBInfoConfig.getPassword());
			if (!conn.isClosed()){
				String tableName = DBInfoConfig.getTablename();
				ResultSet tabs = conn.getMetaData().getTables(null, null, tableName, null);

				if (!tabs.next()) {//表不存在
					Statement stmt = conn.createStatement();

					String sql = "create table " + tableName + "(" +
							"    ecName     tinytext not null comment '企业名称'," +
							"    apId       char(16) not null comment '接口账号用户名'," +
							"    mobiles    text     not null comment '收信手机号码'," +
							"    content    text     not null comment '短信内容'," +
							"    params     tinytext null comment '模板变量'," +
							"    templateId tinytext null comment '模板id'," +
							"    sign       char(50) null comment '签名编码'," +
							"    addSerial  char(50) null comment '扩展码'," +
							"    rspcod     char(16) null comment '返回码:" +
							"IllegalMac,//\tmac校验不通过。\n" +
							"IllegalSignId,//\t无效的签名编码。\n" +
							"InvalidMessage,//\t非法消息，请求数据解析失败。\n" +
							"InvalidUsrOrPwd,//\t非法用户名/密码。\n" +
							"NoSignId,//\t未匹配到对应的签名信息。\n" +
							"success,//\t数据验证通过。\n" +
							"TooManyMobiles,//\t手机号数量超限（>5000），应≤5000。',\n" +
							"    ipv4       char(60) not null comment 'ipv4地址'," +
							"    macId      char(64) null comment 'MAC地址'," +
							"    sendTime   char(20) null comment '发送时间'," +
							"    s_id       int auto_increment comment 'id'," +
							"    serid      char(20) null comment '服务代码'," +
							"    constraint " + tableName + "_s_id_uindex" +
							"        unique (s_id)" +
							")" +
							"    comment '短信发送记录' charset = utf8mb4;" +

							"alter table " + tableName +
							"    add primary key (s_id);";

					if(stmt.executeUpdate(sql)==0)
						System.out.println("create table success!");
				} else {
					System.out.println("table exist!");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("数据库打开成功");
		}
	}

	/**
	 * 增加发送记录 通过连接池
	 * @param his
	 */
	public static void addSmsRecordPool(SmsSendHistory rec) throws ClassNotFoundException,SocketException,SQLException{
		try {
			DBConnectionPool connPool = DBConnectionUtil.GetPoolInstance();//单例模式创建连接池对象
			// SQL语句
			String sql = "insert into " +DBInfoConfig.getTablename()+
					"(ecName,apId,mobiles,content,params,templateId,sign,addSerial,rspcod,ipv4,macId,sendTime,serid) " +
					"values(" +
					rec.toSQLString() +
					")";

			Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接
			Statement stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			stmt.close();
			connPool.returnConnection(conn);// 连接使用完后释放连接到连接池

			// connPool.refreshConnections();//刷新数据库连接池中所有连接，即不管连接是否正在运行，都把所有连接都释放并放回到连接池。注意：这个耗时比较大。
			connPool.closeConnectionPool();// 关闭数据库连接池。注意：这个耗时比较大。
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 增加发送记录
	 * @param rec
	 */
	public static void addSmsRecord(SmsSendHistory rec) throws ClassNotFoundException,SocketException,SQLException {
		// 连接数据库
		getDBConnection(rec.getServiceType());

		System.out.println("DBInfoConfig.getTablename()" +DBInfoConfig.getTablename());
		System.out.println(rec.toString());

		String sql = "insert into " +DBInfoConfig.getTablename()+
				"(ecName,apId,mobiles,content,params,templateId,sign,addSerial,rspcod,ipv4,macId,sendTime,serid) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		// 绑定
		PreparedStatement pst = conn.prepareStatement(sql);
		// 通过对象往数据库中add数据
		// 几个问号几句 (从1开始)
		pst.setString(1, rec.getEcname());
		pst.setString(2, rec.getApid());
		pst.setString(3, rec.getMobiles());
		pst.setString(4, rec.getContent());
		pst.setString(5, rec.getParams());
		pst.setString(6, rec.getTemplateid());
		pst.setString(7, rec.getSign());
		pst.setString(8, rec.getAddserial());
		pst.setString(9, rec.getRspcod());
		pst.setString(10, rec.getIpv4());
		pst.setString(11, rec.getMacid());
		pst.setString(12, rec.getSendtime());
		pst.setString(13, rec.getSerid());

		pst.executeUpdate();

		// 关闭数据库
		pst.close();
		conn.close();
	}
}
