package com;

public class TbUserlist
{
	private String name;// 使用者的名字
	private String username;// 登录名
	private String pass;
	private String quan;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPass()
	{
		return this.pass;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}

	public String getQuan()
	{
		return this.quan;
	}

	public void setQuan(String quan)
	{
		this.quan = quan;
	}
}