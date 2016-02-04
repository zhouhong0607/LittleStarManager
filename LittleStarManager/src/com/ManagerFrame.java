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


public class ManagerFrame extends JFrame
{
	private JTextField nameTF;
	private JTextField indateTF;
	private JTextField birdateTF;
	private JTextField phoneTF;
	private JTextField paydateTF;
	private JTextField dayoffTF;
	Vector<TbKidmanager> kidlist;// ���ڳ��ε������ݹ���tablemodel,֮����tableModel�������ݸ��£������ݿ����ӽ���
	DefaultTableModel tableModel = null;// ���ģ��
	MTable table = null;

	/**
	 * Create the frame.
	 */
	public ManagerFrame()
	{
		super();
		setTitle("��Ϣ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, screenSize.width, screenSize.height - 60);// ����ȫ����ʾ
		// �������
		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		// ��ť���
		final JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		// ���һ��
		JButton inserbutton = new JButton("���");
		inserbutton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)// ִ����Ӳ�����������һ����kidlist��ӣ����ݿ����һ��
			{
				// ������
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
					// ��ȡ�������� +һ����
					String paydates = AddOneMonth(paydateTF.getText());
					newkid.setpaydate(paydates);

					int month = getmonth(paydateTF.getText());// ��ȡ�ѽ����·�
					newkid.setMonth(month);// ���ø��·ݶ�ӦֵΪ1
					if (Dao.inserttodb(newkid))// �������ݿ�,�ɹ��Ļ����뵽table��kidlist
					{
						JOptionPane.showMessageDialog(null, "�¼�¼�ѳɹ���ӵ����ݿ�",
								"������ʾ��", JOptionPane.WARNING_MESSAGE);
						Vector<String> rowvector = new Vector<>();
						rowvector.add(newkid.getid().toString());
						rowvector.add(newkid.getkidname());
						rowvector.add(newkid.getindate());
						rowvector.add(newkid.getbirdate());
						rowvector.add(newkid.getphone());
						rowvector.add(newkid.getJan() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getFeb() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getMar() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getApr() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getMay() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getJun() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getJul() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getAug() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getSep() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getOct() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getNov() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getDec() == 0 ? "δ��" : "�ѽ�");
						rowvector.add(newkid.getpaydate());
						// tablemodel���
						tableModel.addRow(rowvector);
					}
				} else
				{
					JOptionPane.showMessageDialog(null,
							"���������Ƿ�����,���ڸ�ʽ�Ƿ���ȷ,���ѵ������Ƿ�����", "������ʾ��",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		// ɾ��һ��,�ж��Ƿ�Ϊһ������,���ȸ���ѡ���������kidlist�е�ָ��Ԫ��deletekid,���ݿ�ɾ��deletekid,kidlist��ɾ������,�����ɾ��deletekid,�������޸�id,�������ݿ��и�λ��֮�����е�id-1,kidlist��id-1,���id-1.
		JButton deletebutton = new JButton("ɾ��");
		deletebutton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				int deleteid = Integer.parseInt(tableModel.getValueAt(
						selectedRow, 0).toString());// ����ɾ�����id,int�ͱ�ʾ
				if (selectedRow == -1)
				{
					JOptionPane.showMessageDialog(null, "δѡ���κ�һ��", "������ʾ��",
							JOptionPane.WARNING_MESSAGE);
					// û��ѡ���κ�һ��
				} else
				{
					if (Dao.deleteid(deleteid) && Dao.updateid(deleteid))
					{
						// ������ݿ�ɾ��������id�ɹ�����ִ�и���tablemodel��>deletedid��id,����ɾ����һ��
						JOptionPane.showMessageDialog(null, "ɾ���ɹ���", "������ʾ��",
								JOptionPane.WARNING_MESSAGE);

						for (int i = 0; i < table.getRowCount(); i++)
						{
							if (Integer.parseInt(tableModel.getValueAt(i, 0)
									.toString()) > deleteid)
								tableModel.setValueAt(
										Integer.parseInt(tableModel.getValueAt(
												i, 0).toString()) - 1, i, 0);// ����id-1
						}
						tableModel.removeRow(selectedRow);// ɾ������
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
		JLabel paydateLB = new JLabel("�ɷ�����");
		buttonPanel.add(paydateLB);
		paydateTF = new JTextField();
		buttonPanel.add(paydateTF);
		paydateTF.setColumns(10);
		buttonPanel.add(inserbutton);
		buttonPanel.add(deletebutton);
		// �ɷѰ�ť,
		JButton setpaid = new JButton("����");
		setpaid.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1)
				{
					JOptionPane.showMessageDialog(null, "δѡ���κ�һ��", "������ʾ��",
							JOptionPane.WARNING_MESSAGE);
					// û��ѡ���κ�һ��
				} else
				// ���½�������+1��
				{
					int selectedid = Integer.parseInt(tableModel.getValueAt(
							selectedRow, 0).toString());// ���ѡ���е�id
					String update = tableModel.getValueAt(selectedRow, 17)
							.toString();
					int changemonth = getmonth(update);// ��ȡ���ѵ��·�
					update = AddOneMonth(update);// ����һ����
					// ����paydate������Ϣ���·���Ϣ
					if (Dao.updateinfostr(selectedid, update)
							&& Dao.updateinfomon(selectedid, changemonth,1))// ���µ����ݿ�   
					{
						JOptionPane.showMessageDialog(null, "���ѳɹ�,���ݿ�id�Ѹ���",
								"������ʾ��", JOptionPane.WARNING_MESSAGE);
						tableModel.setValueAt(update, selectedRow, 17);// ���±��Ľɷ�����
						paydateTF.setText(tableModel
								.getValueAt(selectedRow, 17)// ���±༭��Ľɷ�����
								.toString());// ����paydate�༭��
						tableModel.setValueAt("�ѽ�", selectedRow,
								changemonth + 4);// ���ö�Ӧ�·�Ϊ�ѽ�
					}
					if(changemonth==12)//�µ�һ����¶�Ӧ�·�Ϊδ��
					{
						JOptionPane.showMessageDialog(null, "�µ�һ�꣬��������ing",
								"������ʾ��", JOptionPane.WARNING_MESSAGE);
						for(int k=1;k<=12;k++)
						{
							boolean ok=Dao.updateinfomon(selectedid, k,0);//������ݿ���³ɹ������±��
							if(ok)
							{
								tableModel.setValueAt("δ��", selectedRow,
										k + 4);// ���������·�Ϊδ��
							}
						}
					}
					
				}
			}
		});
		JLabel dayoffLB = new JLabel("�������:");
		buttonPanel.add(dayoffLB);
		dayoffTF = new JTextField();
		buttonPanel.add(dayoffTF);
		dayoffTF.setColumns(5);
		JButton dayoffBT = new JButton("���");
		dayoffBT.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1)// ʹ��������ʽ�ж��Ƿ�Ϊ������
				{
					JOptionPane.showMessageDialog(null, "����,����ѡ���ˣ���", "������ʾ��",
							JOptionPane.WARNING_MESSAGE);
					// û��ѡ���κ�һ��
				} else if (!isNumeric(dayoffTF.getText()))
				{
					JOptionPane.showMessageDialog(null, "����,������������", "������ʾ��",
							JOptionPane.WARNING_MESSAGE);
				} else if (Integer.parseInt(dayoffTF.getText()) == 0)
				{
					JOptionPane.showMessageDialog(null, "����,���0��������ô - -��",
							"������ʾ��", JOptionPane.WARNING_MESSAGE);
				} else
				{
					int daynum = Integer.parseInt(dayoffTF.getText());// ���������ת��Ϊint��
					int selectedid = Integer.parseInt(tableModel.getValueAt(
							selectedRow, 0).toString());// ���ѡ���е�id
					String update = tableModel.getValueAt(selectedRow, 17)// ���ԭ�����ѵ�����
							.toString();
					update = AddDay(update, daynum);// ���½��ѵ�����,������ٵ�����
					// ����paydate������Ϣ
					if (Dao.updateinfostr(selectedid, update))
						;// ���µ����ݿ�
					{
						JOptionPane.showMessageDialog(null, "��ٳɹ�,���ݿ��Ѹ���",
								"������ʾ��", JOptionPane.WARNING_MESSAGE);
						// kidlist.elementAt(selectedRow).setpaydate(update);
						tableModel.setValueAt(update, selectedRow, 17);
						paydateTF.setText(tableModel
								.getValueAt(selectedRow, 17).toString());// ����paydate�༭��
					}
				}
			}
		});
		buttonPanel.add(dayoffBT);
		buttonPanel.add(setpaid);
		// ��������
		Vector<String> columnNameV = new Vector<>();
		columnNameV.add("ID");
		columnNameV.add("����");
		columnNameV.add("��԰����");
		columnNameV.add("��������");
		columnNameV.add("��ϵ��ʽ");
		columnNameV.add("һ��");
		columnNameV.add("����");
		columnNameV.add("����");
		columnNameV.add("����");
		columnNameV.add("����");
		columnNameV.add("����");
		columnNameV.add("����");
		columnNameV.add("����");
		columnNameV.add("����");
		columnNameV.add("ʮ��");
		columnNameV.add("ʮһ��");
		columnNameV.add("ʮ����");
		columnNameV.add("�´ν�������");
		// ��Ϣ����,�����ݿ⵼�뵽kidlist��
		kidlist = Dao.getTbKidmanager(); // �޸������޸�kidlist��kidlist���ݲ�ΪString
		// System.out.println("kidlist��С��" + kidlist.size());
		// ����������
		Vector<Vector<String>> tableValueV = new Vector<>();
		for (int row = 0; row < kidlist.size(); row++)// ��kidlist�����ݱ�ΪSting��ֵ����������tableValueV
		{
			Vector<String> rowV = new Vector<>();
			rowV.add(kidlist.elementAt(row).getid().toString());
			rowV.add(kidlist.elementAt(row).getkidname());
			rowV.add(kidlist.elementAt(row).getindate());
			rowV.add(kidlist.elementAt(row).getbirdate());
			rowV.add(kidlist.elementAt(row).getphone());
			rowV.add(kidlist.elementAt(row).getJan() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getFeb() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getMar() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getApr() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getMay() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getJun() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getJul() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getAug() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getSep() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getOct() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getNov() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getDec() == 1 ? "�ѽ�" : "δ��");
			rowV.add(kidlist.elementAt(row).getpaydate());// ��kidlist��������뵽tablemodel��
			tableValueV.add(rowV);
		}
		tableModel = new DefaultTableModel(tableValueV, columnNameV);// ���ģ��
		table = new MTable(tableModel);
		// ��Ԫ����ɫ�ĸı�
		table.setDefaultRenderer(Object.class, new MuRender());
		// ����������������Ϣչʾ������
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
				int selectedRow = table.getSelectedRow(); // ����Ĺ�����table˳��ı�,tablemodel��˳�򲻱�
				Object selectedname = table.getValueAt(selectedRow, 1);// ��ȡѡ�е�Ԫ���ֵ
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
		// ���ñ�������� ��ʱ�����Ŵ˹��ܣ�Ӱ��id,Ӱ�����ݿ�, �°취���������һ��
		// ����Number,ɾ����ʱ��ֻ���±�����ѡ���е�Number, idΪѧ����ѧ��
		// ���ݿ��������ѧ�ű仯,���ݿ�ɾ���������ٸ���id,kidlist�����ݿⱣ��ͬ��
		table.setRowSorter(new TableRowSorter<>(tableModel));

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// ���ñ����Զ�����ģʽ
		// table.setRowHeight(33);// �����и�,Ĭ��16����
		// table.setRowSelectionAllowed(false);// �����Ƿ�����ѡ�б���У�Ĭ������
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// ����ѡ�����е�ѡ��ģʽ,ֻ����ѡ��һ��
		table.setSelectionBackground(Color.YELLOW);// ����ѡ���б�����ɫ
		// table.setSelectionForeground(Color.cyan);// ����ѡ����ǰ����ɫ(����)

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

	public boolean TFDateFormat(String s)// ���������ʽ�ж�,������ ��
	{
		String checkValue = s;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date d = null;
		if (checkValue != null && !checkValue.equals("")) // �ж��Ƿ�Ϊ��
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
			System.out.println("��ʽ����");

		}
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(checkValue);
		boolean b = m.matches();
		if (b)
		{
			System.out.println("��ʽ��ȷ");
			return true;

		} else
		{
			System.out.println("��ʽ����");
			return false;

		}

	}

	// ����һ����
	public String AddOneMonth(String sdate)
	{
		String trsdate = null;
		DateFormat df1 = DateFormat.getDateInstance();// ���ڸ�ʽ����ȷ����
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

	// ������ٵ�����
	public String AddDay(String date, int day)
	{
		String trsdate = null;
		DateFormat df1 = DateFormat.getDateInstance();// ���ڸ�ʽ����ȷ����
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

	public int getmonth(String dates)// ��ȡ�·�
	{
		int getmon = 0;
		DateFormat df1 = DateFormat.getDateInstance();// ���ڸ�ʽ����ȷ����
		Date dt = null;
		try
		{
			dt = df1.parse(dates);
			Calendar cdar = Calendar.getInstance();
			cdar.setTime(dt);
			getmon = cdar.get(Calendar.MONTH) + 1;// ��ȡ�·�
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
