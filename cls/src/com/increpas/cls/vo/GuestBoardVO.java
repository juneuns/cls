package com.increpas.cls.vo;

import java.sql.*;
import java.text.*;

public class GuestBoardVO {
	private int rno, gno, mno, ano;
	private String id, name, body, avatar, sdate;
	private Date wdate;
	private Time wtime;
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getGno() {
		return gno;
	}
	public void setGno(int gno) {
		this.gno = gno;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate() {
		SimpleDateFormat form1 = new SimpleDateFormat("yyyy년 MM월 dd일");
		SimpleDateFormat form2 = new SimpleDateFormat("HH:mm");
		
		sdate = form1.format(wdate) + " " + form2.format(wtime);
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public Date getWdate() {
		return wdate;
	}
	public void setWdate(Date wdate) {
		this.wdate = wdate;
	}
	public Time getWtime() {
		return wtime;
	}
	public void setWtime(Time wtime) {
		this.wtime = wtime;
		setSdate();
	}
}
