package com.rabbitmq.manager.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class Message {
	private String id;
	private String menu, base, core;
	private BeverageType beverageType;
	private Date date;


	public Message() {
		// TODO Auto-generated constructor stub
	}

	public Message(String id,String menu) {
		super();
		this.id = id;
		this.menu = menu;
	}
	public Message(String id){
		super();
		this.id = id;
	}
	public Message(String id,String menu,String base,String core,BeverageType beverageType,Date date) {
		super();
		this.id = id;
		this.menu = menu;
		this.base = base;
		this.core = core;
		this.beverageType = beverageType;
		this.date = date;

	}




}
