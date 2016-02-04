package com;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class MTable extends JTable
{

	public MTable(Vector<Vector<String>> rowData, Vector<String> columnNames)
	{
		super(rowData, columnNames);
	}

	public MTable(DefaultTableModel tableModel)
	{
		super(tableModel);
	}

	@Override
	public JTableHeader getTableHeader() // 定义表格头
	{
		JTableHeader tableHeader = super.getTableHeader();
		tableHeader.setReorderingAllowed(true);// 设置表格列可重排
		DefaultTableCellRenderer hr = (DefaultTableCellRenderer) tableHeader
				.getDefaultRenderer();// 获得表格头单元格对象
		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);// 设置列名居中显示
		return tableHeader;
	}

	@Override
	public TableCellRenderer getDefaultRenderer(Class<?> columnClass)// 获得表格单元格对象
	{
		DefaultTableCellRenderer cr = (DefaultTableCellRenderer) super
				.getDefaultRenderer(columnClass);// 获得表格的单元格对象
		cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);// 设置单元格内容居中显示

		return cr;
	}

	@Override
	public boolean isCellEditable(int row, int column)// 设置表格不可编辑
	{
		return false;
	}


}
