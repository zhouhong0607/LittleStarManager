package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Dao
{
	protected static String dbClassName = "com.mysql.jdbc.Driver";
	protected static String dbUrl = "jdbc:mysql://127.0.0.1:3306/db_xiaoxingxing";
	protected static String dbUser = "root";
	protected static String dbPwd = "123";

	public static Connection conn = null;

	static
	{
		try
		{
			if (conn == null)
			{
				Class.forName(dbClassName).newInstance();
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);// connect资源申请，记得释放
			}
		} catch (Exception ee)
		{
			ee.printStackTrace();
		}

	}

	// 禁止生成dao实例
	private Dao()
	{
	}

	// 读取用户
	public static TbUserlist getUser(String loginname, String password)
	{
		TbUserlist user = new TbUserlist();
		ResultSet rs = findForResultSet("select * from tb_user where username='"
				+ loginname + "'");
		try
		{
			if (rs.next())
			{
				user.setUsername(loginname);
				user.setPass(rs.getString("password"));
				if (user.getPass().equals(password))
				{
					user.setName(rs.getString("name"));
					user.setQuan(rs.getString("quan"));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return user;
	}

	// 读取kidmanager信息赋值给模型,将所有kid信息赋值给向量
	public static Vector<TbKidmanager> getTbKidmanager()
	{
		Vector<TbKidmanager> kidlista = new Vector<>();
		ResultSet rs = findForResultSet("select * from tb_kidmanager");

		try
		{
			while (rs.next())
			{
				TbKidmanager kidlist = new TbKidmanager();
				kidlist.setid(rs.getInt("id"));
				kidlist.setkidname(rs.getString("kidname"));
				kidlist.setindate(rs.getString("indate"));
				kidlist.setbirdate(rs.getString("birdate"));
				kidlist.setphone(rs.getString("phone"));
				kidlist.setJan(rs.getInt("Jan"));
				kidlist.setFeb(rs.getInt("Feb"));
				kidlist.setMar(rs.getInt("Mar"));
				kidlist.setApr(rs.getInt("Apr"));
				kidlist.setMay(rs.getInt("May"));
				kidlist.setJun(rs.getInt("Jun"));
				kidlist.setJul(rs.getInt("Jul"));
				kidlist.setAug(rs.getInt("Aug"));
				kidlist.setSep(rs.getInt("Sep"));
				kidlist.setOct(rs.getInt("Oct"));
				kidlist.setNov(rs.getInt("Nov"));
				kidlist.setDec(rs.getInt("Dece"));
				kidlist.setpaydate(rs.getString("paydate"));

				kidlista.add(kidlist);
				// System.out.println("+1");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return kidlista;

	}

	public static ResultSet findForResultSet(String sql)
	{
		if (conn == null)
			return null;
		ResultSet rs = null;
		try
		{
			Statement stmt = null;
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);// 只读模式，不可更改结果集
			rs = stmt.executeQuery(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return rs;
	}

	// 通过模型TbKidmanager插入一条新记录,成功返回true
	public static boolean inserttodb(TbKidmanager newkid)
	{
		boolean result = false;
		try
		{
			// 插入 SQL语句
			String sql = "insert tb_kidmanager values('" + newkid.getid()
					+ "','" + newkid.getkidname() + "','" + newkid.getindate()
					+ "','" + newkid.getbirdate() + "','" + newkid.getphone()
					+ "','" + newkid.getJan() + "','" + newkid.getFeb() + "','"
					+ newkid.getMar() + "','" + newkid.getApr() + "','"
					+ newkid.getMay() + "','" + newkid.getJun() + "','"
					+ newkid.getJul() + "','" + newkid.getAug() + "','"
					+ newkid.getSep() + "','" + newkid.getOct() + "','"
					+ newkid.getNov() + "','" + newkid.getDec() + "','"
					+ newkid.getpaydate() + "')";// 将newkid中数据插入到表tb_kidmanager,新增一项！！！

			Statement stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			if (i > 0)// 判断插入是否成功
			{
				result = true;
				System.out.println("数据库插入新记录成功成功");
			}

		} catch (SQLException e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// 删除指定id条目,成功返回true
	public static boolean deleteid(int deleid)
	{
		boolean result = false;
		try
		{
			PreparedStatement prestat = conn
					.prepareStatement("delete  from tb_kidmanager where id = ?");
			prestat.setInt(1, deleid);
			int de = prestat.executeUpdate();

			if (de > 0) // 判断删除是否成功
			{
				result = true;
				System.out.println("数据库删除" + deleid + "成功");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	// 更新大于id的条目的id自减1,成功返回true
	public static boolean updateid(int upid)
	{
		boolean result = false;
		try
		{
			PreparedStatement prestat = conn
					.prepareStatement("update tb_kidmanager set id = id -1 where id>?");
			prestat.setInt(1, upid);
			int up = prestat.executeUpdate();

			if (up >= 0)// 判断更新操作是否成功
			{
				System.out.println("更新数据库id成功");
				result = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	// 更新指定id的paydate字段,成功返回true
	public static boolean updateinfostr(int id, String value)
	{
		boolean result = false;
		try
		{
			PreparedStatement prestat = conn
					.prepareStatement("update tb_kidmanager set  paydate  = ? where id=?");
			prestat.setString(1, value);
			prestat.setInt(2, id);

			int ups = prestat.executeUpdate();
			if (ups > 0)
			{
				result = true;
				System.out.println("更新数据库交费日期成功");
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;

	}

	// 更新id的 某个月份为1,更新成功返回ture
	public static boolean updateinfomon(int id, int month , int value)
	{
		boolean result = false;
		try
		{
			String sql = "";
			System.out.println("交费的月份："+month);
			switch (month)
			{
			case 1:
				sql = "update tb_kidmanager set  Jan  = ? where id=?";
				break;
			case 2:
				sql = "update tb_kidmanager set  Feb  = ? where id=?";
				break;
			case 3:
				sql = "update tb_kidmanager set  Mar  = ? where id=?";
				break;
			case 4:
				sql = "update tb_kidmanager set  Apr  = ? where id=?";
				break;
			case 5:
				sql = "update tb_kidmanager set  May  = ? where id=?";
				break;
			case 6:
				sql = "update tb_kidmanager set  Jun  = ? where id=?";
				break;
			case 7:
				sql = "update tb_kidmanager set  Jul  = ? where id=?";
				break;
			case 8:
				sql = "update tb_kidmanager set  Aug  = ? where id=?";
				break;
			case 9:
				sql = "update tb_kidmanager set  Sep  = ? where id=?";
				break;
			case 10:
				sql = "update tb_kidmanager set  Oct  = ? where id=?";
				break;
			case 11:
				sql = "update tb_kidmanager set  Nov  = ? where id=?";
				System.out.println(sql);
				break;
			case 12:
				sql = "update tb_kidmanager set  Dece  = ? where id=?";
				System.out.println(sql);
				break;
			default:
				break;
			}
			PreparedStatement prestat = conn.prepareStatement(sql);// 写法有问题
			prestat.setInt(1, value);
			prestat.setInt(2, id);

			int ups = prestat.executeUpdate();
			if (ups > 0)
			{
				result = true;
				System.out.println("更新数据库指定月份设置为交费成功");
			}else {
				JOptionPane.showMessageDialog(null, "数据库更新失败",
						"警告提示窗", JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;

	}

	// public static boolean insert(String sql)
	// {
	// boolean result = false;
	// try
	// {
	// Statement stmt = conn.createStatement();
	// result = stmt.execute(sql);
	// } catch (SQLException e)
	// {
	// e.printStackTrace();
	// }
	// return result;
	// }
	//
	// public static int update(String sql)
	// {
	// int result = 0;
	// try
	// {
	// Statement stmt = conn.createStatement();
	// result = stmt.executeUpdate(sql);
	// } catch (SQLException e)
	// {
	// e.printStackTrace();
	// }
	// return result;
	// }
	//
	//
	//
	//
	// // 添加用户
	// public static int addUser(TbUserlist ul)
	// {
	// return update("insert tb_userlist values('" + ul.getUsername() + "','" +
	// ul.getName() + "','" + ul.getPass()
	// + "','" + ul.getQuan() + "')");
	// }
	//
	//
	//
	// // 修改用户方法
	// public static int updateUser(TbUserlist user)
	// {
	// return update("update tb_userlist set username='" + user.getUsername() +
	// "',name='" + user.getName()
	// + "',pass='" + user.getPass() + "',quan='" + user.getQuan() + "' where
	// name='" + user.getName() + "'");
	// }

}