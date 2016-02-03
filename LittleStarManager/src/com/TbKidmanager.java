package com;

import java.util.Date;

public class TbKidmanager
{
	private Integer id;// 使用者的名字
	private String kidname;// 登录名
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
	private String paydate;// 新增交费日期

	// id号
	public Integer getid()
	{
		return id;
	}

	public void setid(Integer idd)
	{
		this.id = idd;
	}

	// 姓名
	public String getkidname()
	{
		return kidname;
	}

	public void setkidname(String kidnamee)
	{
		this.kidname = kidnamee;
	}

	// 入园日期
	public String getindate()
	{
		return indate;
	}

	public void setindate(String indatee)
	{
		this.indate = indatee;
	}

	// 出生日期
	public String getbirdate()
	{
		return birdate;
	}

	public void setbirdate(String birdatee)
	{
		this.birdate = birdatee;
	}

	// 电话
	public String getphone()
	{
		return phone;
	}

	public void setphone(String phonee)
	{
		this.phone = phonee;
	}

	// 一月
	public Integer getJan()
	{
		return Jan;
	}

	// 设置某月份已交
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

	// 二月
	public Integer getFeb()
	{
		return Feb;
	}

	public void setFeb(Integer Febb)
	{
		this.Feb = Febb;
	}

	// 三月
	public Integer getMar()
	{
		return Mar;
	}

	public void setMar(Integer Marr)
	{
		this.Mar = Marr;
	}

	// 四月
	public Integer getApr()
	{
		return Apr;
	}

	public void setApr(Integer Aprr)
	{
		this.Apr = Aprr;
	}

	// 五月
	public Integer getMay()
	{
		return May;
	}

	public void setMay(Integer Mayy)
	{
		this.May = Mayy;
	}

	// 六月
	public Integer getJun()
	{
		return Jun;
	}

	public void setJun(Integer Junn)
	{
		this.Jun = Junn;
	}

	// 七月
	public Integer getJul()
	{
		return Jul;
	}

	public void setJul(Integer Jull)
	{
		this.Jul = Jull;
	}

	// 八月
	public Integer getAug()
	{
		return Aug;
	}

	public void setAug(Integer Augg)
	{
		this.Aug = Augg;
	}

	// 九月
	public Integer getSep()
	{
		return Sep;
	}

	public void setSep(Integer Sepp)
	{
		this.Sep = Sepp;
	}

	// 十月
	public Integer getOct()
	{
		return Oct;
	}

	public void setOct(Integer Octt)
	{
		this.Oct = Octt;
	}

	// 十一月
	public Integer getNov()
	{
		return Nov;
	}

	public void setNov(Integer Novv)
	{
		this.Nov = Novv;
	}

	// 十二月
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
