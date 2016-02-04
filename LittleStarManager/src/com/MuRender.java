package com;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

//设置单元格颜色
public class MuRender extends DefaultTableCellRenderer
{

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component renderer = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		// 先把所有单元格设置为白色
		renderer.setForeground(Color.BLACK);
		// 进行渲染
		if (column > 4 && column < 17) // 6~17列单元格改变颜色
		{
			// 取得单元格的值
			String strValue = (String) value;

			switch (strValue)
			{
			case "已交":
				renderer.setForeground(Color.green); // 1设置为绿色
				break;
			case "未交":
				renderer.setForeground(Color.red); // 0设置为红色
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

	// 设置单元格颜色结束*****
	private String getToday()
	{
		String today;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new java.util.Date();
		today = sdf.format(date);
		return today;
	}
}