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
	public JTableHeader getTableHeader() // ������ͷ
	{
		JTableHeader tableHeader = super.getTableHeader();
		tableHeader.setReorderingAllowed(true);// ���ñ���п�����
		DefaultTableCellRenderer hr = (DefaultTableCellRenderer) tableHeader
				.getDefaultRenderer();// ��ñ��ͷ��Ԫ�����
		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);// ��������������ʾ
		return tableHeader;
	}

	@Override
	public TableCellRenderer getDefaultRenderer(Class<?> columnClass)// ��ñ��Ԫ�����
	{
		DefaultTableCellRenderer cr = (DefaultTableCellRenderer) super
				.getDefaultRenderer(columnClass);// ��ñ��ĵ�Ԫ�����
		cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);// ���õ�Ԫ�����ݾ�����ʾ

		return cr;
	}

	@Override
	public boolean isCellEditable(int row, int column)// ���ñ�񲻿ɱ༭
	{
		return false;
	}


}
