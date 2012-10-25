package com.snowdream.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.snowdream.bean.Discuss;
import com.snowdream.bean.User;

/**
 * 数据库连接类
 * 
 * @author luowen
 * @date 2012-10-24
 */
public class JDBCConnection {
	// /////////////////////
	// ////配置开始//////////
	// /////////////////////
	private static String url = "jdbc:mysql://localhost:3306/snow?useUnicode=true&characterEncoding=UTF-8";
	private static String user = "root";
	private static String password = "root";
//	private static String url="jdbc:mysql://mysql.jhost.cn:3306/udb_luowen5241?useUnicode=true&characterEncoding=UTF-8";
//	private static String user = "luowen5241";
//	private static String password = "lqyygy5240";
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public static void free(ResultSet rs, PreparedStatement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// /////////////////////
	// ////配置结束//////////
	// /////////////////////
	/**
	 * 添加消息
	 * 
	 * @param dis
	 */
	public void addDiscuss(Discuss dis) {
		// System.out.println("addDisscuss");
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "insert into msg (msg_from, msg_cnt, msg_ip) values (?, ?, ?);";
		try {
			conn = JDBCConnection.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, dis.getFromUser().getName());
			st.setString(2, dis.getContent());
			st.setString(3, dis.getIp());
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.free(rs, st, conn);
		}
	}

	/**
	 * 添加消息
	 * 
	 * @param dis
	 */
	public void addUser(User user) {
		// System.out.println("addDisscuss");
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "insert into user (name, age, create_ip) values (?, ?, ?);";
		try {
			conn = JDBCConnection.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, user.getName());
			st.setShort(2, user.getAge());
			st.setString(3, user.getCreateIp());
			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.free(rs, st, conn);
		}
	}

	/**
	 * 取得消息
	 * 
	 * @return
	 */
	public List<Discuss> getDiscuss() {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Discuss dis = null;
		User user = null;
		List<Discuss> list = new ArrayList<Discuss>();
		String sql = "select * from msg m left join user u on u.name=m.msg_from";
		try {
			conn = JDBCConnection.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				dis = new Discuss();
				dis.setContent(rs.getString("msg_cnt"));
				dis.setDttm(rs.getTimestamp("msg_dttm"));
				dis.setIp(rs.getString("last_logon_ip"));
				user = new User();
				user.setAge(rs.getShort("age"));
				user.setCreateIp(rs.getString("create_ip"));
				user.setLastLogonIp("last_logon_ip");
				user.setName(rs.getString("name"));
				user.setUid(rs.getLong("uid"));
				dis.setFromUser(user);
				list.add(dis);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.free(rs, st, conn);
		}
		return list;
	}

	/**
	 * 查询是否存在某姓名
	 * 
	 * @param name
	 * @return
	 */
	public boolean isUserExsit(String name) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select * from user where user.name=?";
		try {
			conn = JDBCConnection.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, name);
			rs = st.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.free(rs, st, conn);
		}
		return false;
	}

//	public static void main(String[] arg) {
		// List<Discuss> list = new JDBCConnection().getDiscuss();
		// for (Discuss dis : list) {
		// System.out.println("dis: " + dis.getCountent());
		// }
		// -----------
		// System.out.println(new JDBCConnection().isUserExsit("vane"));
		// System.out.println(new JDBCConnection().isUserExsit("vane2"));
		// ------------
//		new JDBCConnection().addUser(null);
//	}
}
