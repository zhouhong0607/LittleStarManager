package com;

import java.util.Date;

public class TbKidmanager
{
	private Integer id;// ʹ���ߵ�����
	private String kidname;// ��¼��
	private String indate;
	private String birdate;
	private String phone;
	private Integer Jan;
	private Integer Feb;
	private Integer Mar;
	private Integer Apr;
	private Integer May;
	private Integer Jun;
	private Integer Jul;
	private Integer Aug;
	private Integer Sep;
	private Integer Oct;
	private Integer Nov;
	private Integer Dec;
	private String paydate;// ������������

	// id��
	public Integer getid()
	{
		return id;
	}

	public void setid(Integer idd)
	{
		this.id = idd;
	}

	// ����
	public String getkidname()
	{
		return kidname;
	}

	public void setkidname(String kidnamee)
	{
		this.kidname = kidnamee;
	}

	// ��԰����
	public String getindate()
	{
		return indate;
	}

	public void setindate(String indatee)
	{
		this.indate = indatee;
	}

	// ��������
	public String getbirdate()
	{
		return birdate;
	}

	public void setbirdate(String birdatee)
	{
		this.birdate = birdatee;
	}

	// �绰
	public String getphone()
	{
		return phone;
	}

	public void setphone(String phonee)
	{
		this.phone = phonee;
	}

	// һ��
	public Integer getJan()
	{
		return Jan;
	}

	// ����ĳ�·��ѽ�
	public void setMonth(Integer mon)
	{
		switch (mon)
		{
		case 1:
			this.Jan = 1;
			break;
		case 2:
			this.Feb = 1;
			break;
		case 3:
			this.Mar = 1;
			break;
		case 4:
			this.Apr = 1;
			break;
		case 5:
			this.May = 1;
			break;
		case 6:
			this.Jun = 1;
			break;
		case 7:
			this.Jul = 1;
			break;
		case 8:
			this.Aug = 1;
			break;
		case 9:
			this.Sep = 1;
			break;
		case 10:
			this.Oct = 1;
			break;
		case 11:
			this.Nov = 1;
			break;
		case 12:
			this.Dec = 1;
			break;
		default:
			break;
		}

	}

	public void setJan(Integer Jann)
	{
		this.Jan = Jann;
	}

	// ����
	public Integer getFeb()
	{
		return Feb;
	}

	public void setFeb(Integer Febb)
	{
		this.Feb = Febb;
	}

	// ����
	public Integer getMar()
	{
		return Mar;
	}

	public void setMar(Integer Marr)
	{
		this.Mar = Marr;
	}

	// ����
	public Integer getApr()
	{
		return Apr;
	}

	public void setApr(Integer Aprr)
	{
		this.Apr = Aprr;
	}

	// ����
	public Integer getMay()
	{
		return May;
	}

	public void setMay(Integer Mayy)
	{
		this.May = Mayy;
	}

	// ����
	public Integer getJun()
	{
		return Jun;
	}

	public void setJun(Integer Junn)
	{
		this.Jun = Junn;
	}

	// ����
	public Integer getJul()
	{
		return Jul;
	}

	public void setJul(Integer Jull)
	{
		this.Jul = Jull;
	}

	// ����
	public Integer getAug()
	{
		return Aug;
	}

	public void setAug(Integer Augg)
	{
		this.Aug = Augg;
	}

	// ����
	public Integer getSep()
	{
		return Sep;
	}

	public void setSep(Integer Sepp)
	{
		this.Sep = Sepp;
	}

	// ʮ��
	public Integer getOct()
	{
		return Oct;
	}

	public void setOct(Integer Octt)
	{
		this.Oct = Octt;
	}

	// ʮһ��
	public Integer getNov()
	{
		return Nov;
	}

	public void setNov(Integer Novv)
	{
		this.Nov = Novv;
	}

	// ʮ����
	public Integer getDec()
	{
		return Dec;
	}

	public void setDec(Integer Decc)
	{
		this.Dec = Decc;
	}

	public String getpaydate()
	{
		return paydate;
	}

	public void setpaydate(String paydatee)
	{
		paydate = paydatee;
	}
}
