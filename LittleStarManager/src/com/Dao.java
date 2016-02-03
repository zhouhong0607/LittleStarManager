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
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);// connect��Դ���룬�ǵ��ͷ�
			}
		} catch (Exception ee)
		{
			ee.printStackTrace();
		}

	}

	// ��ֹ����daoʵ��
	private Dao()
	{
	}

	// ��ȡ�û�
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

	// ��ȡkidmanager��Ϣ��ֵ��ģ��,������kid��Ϣ��ֵ������
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
					ResultSet.CONCUR_READ_ONLY);// ֻ��ģʽ�����ɸ��Ľ����
			rs = stmt.executeQuery(sql);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return rs;
	}

	// ͨ��ģ��TbKidmanager����һ���¼�¼,�ɹ�����true
	public static boolean inserttodb(TbKidmanager newkid)
	{
		boolean result = false;
		try
		{
			// ���� SQL���
			String sql = "insert tb_kidmanager values('" + newkid.getid()
					+ "','" + newkid.getkidname() + "','" + newkid.getindate()
					+ "','" + newkid.getbirdate() + "','" + newkid.getphone()
					+ "','" + newkid.getJan() + "','" + newkid.getFeb() + "','"
					+ newkid.getMar() + "','" + newkid.getApr() + "','"
					+ newkid.getMay() + "','" + newkid.getJun() + "','"
					+ newkid.getJul() + "','" + newkid.getAug() + "','"
					+ newkid.getSep() + "','" + newkid.getOct() + "','"
					+ newkid.getNov() + "','" + newkid.getDec() + "','"
					+ newkid.getpaydate() + "')";// ��newkid�����ݲ��뵽��tb_kidmanager,����һ�����

			Statement stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			if (i > 0)// �жϲ����Ƿ�ɹ�
			{
				result = true;
				System.out.println("���ݿ�����¼�¼�ɹ��ɹ�");
			}

		} catch (SQLException e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// ɾ��ָ��id��Ŀ,�ɹ�����true
	public static boolean deleteid(int deleid)
	{
		boolean result = false;
		try
		{
			PreparedStatement prestat = conn
					.prepareStatement("delete  from tb_kidmanager where id = ?");
			prestat.setInt(1, deleid);
			int de = prestat.executeUpdate();

			if (de > 0) // �ж�ɾ���Ƿ�ɹ�
			{
				result = true;
				System.out.println("���ݿ�ɾ��" + deleid + "�ɹ�");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	// ���´���id����Ŀ��id�Լ�1,�ɹ�����true
	public static boolean updateid(int upid)
	{
		boolean result = false;
		try
		{
			PreparedStatement prestat = conn
					.prepareStatement("update tb_kidmanager set id = id -1 where id>?");
			prestat.setInt(1, upid);
			int up = prestat.executeUpdate();

			if (up >= 0)// �жϸ��²����Ƿ�ɹ�
			{
				System.out.println("�������ݿ�id�ɹ�");
				result = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	// ����ָ��id��paydate�ֶ�,�ɹ�����true
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
				System.out.println("�������ݿ⽻�����ڳɹ�");
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;

	}

	// ����id�� ĳ���·�Ϊ1,���³ɹ�����ture
	public static boolean updateinfomon(int id, int month , int value)
	{
		boolean result = false;
		try
		{
			String sql = "";
			System.out.println("���ѵ��·ݣ�"+month);
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
			PreparedStatement prestat = conn.prepareStatement(sql);// д��������
			prestat.setInt(1, value);
			prestat.setInt(2, id);

			int ups = prestat.executeUpdate();
			if (ups > 0)
			{
				result = true;
				System.out.println("�������ݿ�ָ���·�����Ϊ���ѳɹ�");
			}else {
				JOptionPane.showMessageDialog(null, "���ݿ����ʧ��",
						"������ʾ��", JOptionPane.WARNING_MESSAGE);
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
	// // ����û�
	// public static int addUser(TbUserlist ul)
	// {
	// return update("insert tb_userlist values('" + ul.getUsername() + "','" +
	// ul.getName() + "','" + ul.getPass()
	// + "','" + ul.getQuan() + "')");
	// }
	//
	//
	//
	// // �޸��û�����
	// public static int updateUser(TbUserlist user)
	// {
	// return update("update tb_userlist set username='" + user.getUsername() +
	// "',name='" + user.getName()
	// + "',pass='" + user.getPass() + "',quan='" + user.getQuan() + "' where
	// name='" + user.getName() + "'");
	// }

}