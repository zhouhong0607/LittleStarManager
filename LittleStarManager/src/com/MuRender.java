package com;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

//���õ�Ԫ����ɫ
public class MuRender extends DefaultTableCellRenderer
{

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component renderer = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		// �Ȱ����е�Ԫ������Ϊ��ɫ
		renderer.setForeground(Color.BLACK);
		// ������Ⱦ
		if (column > 4 && column < 17) // 6~17�е�Ԫ��ı���ɫ
		{
			// ȡ�õ�Ԫ���ֵ
			String strValue = (String) value;

			switch (strValue)
			{
			case "�ѽ�":
				renderer.setForeground(Color.green); // 1����Ϊ��ɫ
				break;
			case "δ��":
				renderer.setForeground(Color.red); // 0����Ϊ��ɫ
				break;
			default:
				break;
			}

		} else if (column == 17)
		{
			String strValue = (String) value;
			if (strValue.equals(getToday()))
				renderer.setForeground(Color.PINK);
		}
		return renderer;
	}

	// ���õ�Ԫ����ɫ����*****
	private String getToday()
	{
		String today;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new java.util.Date();
		today = sdf.format(date);
		return today;
	}
}