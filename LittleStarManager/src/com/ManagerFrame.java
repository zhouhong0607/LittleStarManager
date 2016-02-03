package com;

//
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.TextField;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.mysql.jdbc.StringUtils;

public class ManagerFrame extends JFrame
{
	private JTextField nameTF;
	private JTextField indateTF;
	private JTextField birdateTF;
	private JTextField phoneTF;
	private JTextField paydateTF;
	private JTextField dayoffTF;
	Vector<TbKidmanager> kidlist;// 用于初次导入数据构造tablemodel,之后由tableModel进行数据更新，与数据库连接交互
	DefaultTableModel tableModel = null;// 表格模型
	MTable table = null;

	/**
	 * Create the frame.
	 */
	public ManagerFrame()
	{
		super();
		setTitle("信息管理");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, screenSize.width, screenSize.height - 60);// 设置全屏显示
		// 滚动面板
		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		// 按钮面板
		final JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		// 添加一行
		JButton inserbutton = new JButton("添加");
		inserbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)// 执行添加操作，表格添加一条，kidlist添加，数据库添加一条
			{
				// 表格添加
				// TODO Auto-generated method stub
				if (nameTF.getText() != "" && TFDateFormat(indateTF.getText())
						&& TFDateFormat(birdateTF.getText())
						&& TFDateFormat(paydateTF.getText()))
				{
					TbKidmanager newkid = new TbKidmanager();
					newkid.setid(table.getRowCount() + 1);
					newkid.setkidname(nameTF.getText());
					newkid.setindate(indateTF.getText());
					newkid.setbirdate(birdateTF.getText());
					newkid.setphone(phoneTF.getText());
					newkid.setJan(0);
					newkid.setFeb(0);
					newkid.setMar(0);
					newkid.setApr(0);
					newkid.setMay(0);
					newkid.setJun(0);
					newkid.setJul(0);
					newkid.setAug(0);
					newkid.setSep(0);
					newkid.setOct(0);
					newkid.setNov(0);
					newkid.setDec(0);
					// 获取交费日期 +一个月
					String paydates = AddOneMonth(paydateTF.getText());
					newkid.setpaydate(paydates);

					int month = getmonth(paydateTF.getText());// 获取已交费月份
					newkid.setMonth(month);// 设置该月份对应值为1
					if (Dao.inserttodb(newkid))// 插入数据库,成功的话插入到table和kidlist
					{
						JOptionPane.showMessageDialog(null, "新记录已成功添加到数据库",
								"警告提示窗", JOptionPane.WARNING_MESSAGE);
						Vector<String> rowvector = new Vector<>();
						rowvector.add(newkid.getid().toString());
						rowvector.add(newkid.getkidname());
						rowvector.add(newkid.getindate());
						rowvector.add(newkid.getbirdate());
						rowvector.add(newkid.getphone());
						rowvector.add(newkid.getJan() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getFeb() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getMar() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getApr() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getMay() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getJun() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getJul() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getAug() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getSep() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getOct() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getNov() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getDec() == 0 ? "未交" : "已交");
						rowvector.add(newkid.getpaydate());
						// tablemodel添加
						tableModel.addRow(rowvector);
					}
				} else
				{
					JOptionPane.showMessageDialog(null,
							"请检查名字是否输入,日期格式是否正确,交费的日期是否输入", "警告提示窗",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		// 删除一行,判断是否为一个数据,首先根据选择索引获得kidlist中的指定元素deletekid,数据库删除deletekid,kidlist中删除该项,表格中删除deletekid,接下来修改id,首先数据库中该位置之后所有的id-1,kidlist中id-1,表格id-1.
		JButton deletebutton = new JButton("删除");
		deletebutton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				int deleteid = Integer.parseInt(tableModel.getValueAt(
						selectedRow, 0).toString());// 返回删除项的id,int型表示
				if (selectedRow == -1)
				{
					JOptionPane.showMessageDialog(null, "未选择任何一行", "警告提示窗",
							JOptionPane.WARNING_MESSAGE);
					// 没有选择任何一行
				} else
				{
					if (Dao.deleteid(deleteid) && Dao.updateid(deleteid))
					{
						// 如果数据库删除，更新id成功，则执行更新tablemodel的>deletedid的id,并且删除这一条
						JOptionPane.showMessageDialog(null, "删除成功！", "警告提示窗",
								JOptionPane.WARNING_MESSAGE);

						for (int i = 0; i < table.getRowCount(); i++)
						{
							if (Integer.parseInt(tableModel.getValueAt(i, 0)
									.toString()) > deleteid)
								tableModel.setValueAt(
										Integer.parseInt(tableModel.getValueAt(
												i, 0).toString()) - 1, i, 0);// 更新id-1
						}
						tableModel.removeRow(selectedRow);// 删除该行
					}
				}
			}
		});
		JLabel nameLB = new JLabel("\u59D3\u540D\uFF1A");
		buttonPanel.add(nameLB);
		nameTF = new JTextField();
		buttonPanel.add(nameTF);
		nameTF.setColumns(10);
		JLabel indateLB = new JLabel("\u5165\u56ED\u65E5\u671F\uFF1A");
		buttonPanel.add(indateLB);
		indateTF = new JTextField();
		buttonPanel.add(indateTF);
		indateTF.setColumns(10);
		JLabel birdateLB = new JLabel("\u51FA\u751F\u65E5\u671F\uFF1A");
		buttonPanel.add(birdateLB);
		birdateTF = new JTextField();
		buttonPanel.add(birdateTF);
		birdateTF.setColumns(10);
		JLabel phoneLB = new JLabel("\u8054\u7CFB\u65B9\u5F0F\uFF1A");
		buttonPanel.add(phoneLB);
		phoneTF = new JTextField();
		buttonPanel.add(phoneTF);
		phoneTF.setColumns(10);
		JLabel paydateLB = new JLabel("缴费日期");
		buttonPanel.add(paydateLB);
		paydateTF = new JTextField();
		buttonPanel.add(paydateTF);
		paydateTF.setColumns(10);
		buttonPanel.add(inserbutton);
		buttonPanel.add(deletebutton);
		// 缴费按钮,
		JButton setpaid = new JButton("交费");
		setpaid.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1)
				{
					JOptionPane.showMessageDialog(null, "未选择任何一行", "警告提示窗",
							JOptionPane.WARNING_MESSAGE);
					// 没有选择任何一行
				} else
				// 更新交费日期+1月
				{
					int selectedid = Integer.parseInt(tableModel.getValueAt(
							selectedRow, 0).toString());// 获得选中行的id
					String update = tableModel.getValueAt(selectedRow, 17)
							.toString();
					int changemonth = getmonth(update);// 获取交费的月份
					update = AddOneMonth(update);// 增加一个月
					// 更新paydate部分信息与月份信息
					if (Dao.updateinfostr(selectedid, update)
							&& Dao.updateinfomon(selectedid, changemonth,1))// 更新到数据库   
					{
						JOptionPane.showMessageDialog(null, "交费成功,数据库id已更新",
								"警告提示窗", JOptionPane.WARNING_MESSAGE);
						tableModel.setValueAt(update, selectedRow, 17);// 更新表格的缴费日期
						paydateTF.setText(tableModel
								.getValueAt(selectedRow, 17)// 更新编辑框的缴费日期
								.toString());// 更新paydate编辑框
						tableModel.setValueAt("已交", selectedRow,
								changemonth + 4);// 设置对应月份为已交
					}
					if(changemonth==12)//新的一年更新对应月份为未交
					{
						JOptionPane.showMessageDialog(null, "新的一年，更新数据ing",
								"警告提示窗", JOptionPane.WARNING_MESSAGE);
						for(int k=1;k<=12;k++)
						{
							boolean ok=Dao.updateinfomon(selectedid, k,0);//如果数据库更新成功，更新表格
							if(ok)
							{
								tableModel.setValueAt("未交", selectedRow,
										k + 4);// 设置所有月份为未交
							}
						}
					}
					
				}
			}
		});
		JLabel dayoffLB = new JLabel("请假天数:");
		buttonPanel.add(dayoffLB);
		dayoffTF = new JTextField();
		buttonPanel.add(dayoffTF);
		dayoffTF.setColumns(5);
		JButton dayoffBT = new JButton("请假");
		dayoffBT.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1)// 使用正则表达式判断是否为正整数
				{
					JOptionPane.showMessageDialog(null, "拜托,请先选个人！！", "警告提示窗",
							JOptionPane.WARNING_MESSAGE);
					// 没有选择任何一行
				} else if (!isNumeric(dayoffTF.getText()))
				{
					JOptionPane.showMessageDialog(null, "拜托,请输入正整数", "警告提示窗",
							JOptionPane.WARNING_MESSAGE);
				} else if (Integer.parseInt(dayoffTF.getText()) == 0)
				{
					JOptionPane.showMessageDialog(null, "拜托,请假0天有意义么 - -！",
							"警告提示窗", JOptionPane.WARNING_MESSAGE);
				} else
				{
					int daynum = Integer.parseInt(dayoffTF.getText());// 将请假天数转化为int型
					int selectedid = Integer.parseInt(tableModel.getValueAt(
							selectedRow, 0).toString());// 获得选中行的id
					String update = tableModel.getValueAt(selectedRow, 17)// 获得原来交费的日期
							.toString();
					update = AddDay(update, daynum);// 更新交费的日期,加上请假的天数
					// 更新paydate部分信息
					if (Dao.updateinfostr(selectedid, update))
						;// 更新到数据库
					{
						JOptionPane.showMessageDialog(null, "请假成功,数据库已更新",
								"警告提示窗", JOptionPane.WARNING_MESSAGE);
						// kidlist.elementAt(selectedRow).setpaydate(update);
						tableModel.setValueAt(update, selectedRow, 17);
						paydateTF.setText(tableModel
								.getValueAt(selectedRow, 17).toString());// 更新paydate编辑框
					}
				}
			}
		});
		buttonPanel.add(dayoffBT);
		buttonPanel.add(setpaid);
		// 列名向量
		Vector<String> columnNameV = new Vector<>();
		columnNameV.add("ID");
		columnNameV.add("姓名");
		columnNameV.add("入园日期");
		columnNameV.add("出生日期");
		columnNameV.add("联系方式");
		columnNameV.add("一月");
		columnNameV.add("二月");
		columnNameV.add("三月");
		columnNameV.add("四月");
		columnNameV.add("五月");
		columnNameV.add("六月");
		columnNameV.add("七月");
		columnNameV.add("八月");
		columnNameV.add("九月");
		columnNameV.add("十月");
		columnNameV.add("十一月");
		columnNameV.add("十二月");
		columnNameV.add("下次交费日期");
		// 信息向量,由数据库导入到kidlist中
		kidlist = Dao.getTbKidmanager(); // 修改数据修改kidlist，kidlist数据不为String
		// System.out.println("kidlist大小：" + kidlist.size());
		// 表数据向量
		Vector<Vector<String>> tableValueV = new Vector<>();
		for (int row = 0; row < kidlist.size(); row++)// 将kidlist中数据变为Sting赋值给数据向量tableValueV
		{
			Vector<String> rowV = new Vector<>();
			rowV.add(kidlist.elementAt(row).getid().toString());
			rowV.add(kidlist.elementAt(row).getkidname());
			rowV.add(kidlist.elementAt(row).getindate());
			rowV.add(kidlist.elementAt(row).getbirdate());
			rowV.add(kidlist.elementAt(row).getphone());
			rowV.add(kidlist.elementAt(row).getJan() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getFeb() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getMar() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getApr() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getMay() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getJun() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getJul() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getAug() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getSep() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getOct() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getNov() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getDec() == 1 ? "已交" : "未交");
			rowV.add(kidlist.elementAt(row).getpaydate());// 将kidlist新增项加入到tablemodel中
			tableValueV.add(rowV);
		}
		tableModel = new DefaultTableModel(tableValueV, columnNameV);// 表哥模型
		table = new MTable(tableModel);
		// 单元格颜色的改变
		table.setDefaultRenderer(Object.class, new MuRender());
		// 设置鼠标监听，将信息展示到下面
		table.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow(); // 排序的过程中table顺序改变,tablemodel的顺序不变
				Object selectedname = table.getValueAt(selectedRow, 1);// 获取选中单元格的值
				Object indate = table.getValueAt(selectedRow, 2);
				Object birdate = table.getValueAt(selectedRow, 3);
				Object phone = table.getValueAt(selectedRow, 4);
				Object paydate = table.getValueAt(selectedRow, 17);
				nameTF.setText(selectedname.toString());
				indateTF.setText(indate.toString());
				birdateTF.setText(birdate.toString());
				phoneTF.setText(phone.toString());
				paydateTF.setText(paydate.toString());

			}
		});
		// 设置表格排序器 暂时不开放此功能，影响id,影响数据库, 新办法：表格新增一条
		// 计数Number,删除的时候只更新表格大于选择行的Number, id为学生的学号
		// 数据库操作根据学号变化,数据库删除操作不再更新id,kidlist与数据库保持同步
		table.setRowSorter(new TableRowSorter<>(tableModel));

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 设置表格的自动调整模式
		// table.setRowHeight(33);// 设置行高,默认16像素
		// table.setRowSelectionAllowed(false);// 设置是否允许选中表格行，默认允许
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 设置选择表格行的选择模式,只允许选择一行
		table.setSelectionBackground(Color.YELLOW);// 设置选择行背景颜色
		// table.setSelectionForeground(Color.cyan);// 设置选择行前景颜色(文字)

		scrollPane.setViewportView(table);

	}

	public Date StringtoDate(String s)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		try
		{
			dt = sdf.parse(s);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return dt;
	}

	public boolean TFDateFormat(String s)// 日期输入格式判断,不包括 空
	{
		String checkValue = s;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date d = null;
		if (checkValue != null && !checkValue.equals("")) // 判断是否为空
		{
			if (checkValue.split("/").length > 1)
			{
				dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			}
			if (checkValue.split("-").length > 1)
			{
				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
		} else
		{
			return false;
		}
		try
		{
			d = dateFormat.parse(checkValue);
			System.out.println(d);
		} catch (Exception e)
		{
			System.out.println("格式错误");

		}
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(checkValue);
		boolean b = m.matches();
		if (b)
		{
			System.out.println("格式正确");
			return true;

		} else
		{
			System.out.println("格式错误");
			return false;

		}

	}

	// 增加一个月
	public String AddOneMonth(String sdate)
	{
		String trsdate = null;
		DateFormat df1 = DateFormat.getDateInstance();// 日期格式，精确到日
		Date dt = null;

		try
		{
			dt = df1.parse(sdate);
			Calendar cdar = Calendar.getInstance();
			cdar.setTime(dt);
			cdar.add(Calendar.MONTH, 1);
			dt = cdar.getTime();
			trsdate = df1.format(dt);

		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		return trsdate;
	}

	// 增加请假的天数
	public String AddDay(String date, int day)
	{
		String trsdate = null;
		DateFormat df1 = DateFormat.getDateInstance();// 日期格式，精确到日
		Date dt = null;

		try
		{
			dt = df1.parse(date);
			Calendar cdar = Calendar.getInstance();
			cdar.setTime(dt);
			cdar.add(Calendar.DATE, day);
			dt = cdar.getTime();
			trsdate = df1.format(dt);

		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		return trsdate;
	}

	public int getmonth(String dates)// 获取月份
	{
		int getmon = 0;
		DateFormat df1 = DateFormat.getDateInstance();// 日期格式，精确到日
		Date dt = null;
		try
		{
			dt = df1.parse(dates);
			Calendar cdar = Calendar.getInstance();
			cdar.setTime(dt);
			getmon = cdar.get(Calendar.MONTH) + 1;// 获取月份
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return getmon;
	}

	public boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}
}
