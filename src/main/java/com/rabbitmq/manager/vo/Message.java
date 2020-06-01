package com.rabbitmq.manager.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {
	private String path;
	private long size;
	private String beforeType;
	private String afterType;
	private String id;

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public Message(String path, long size, String beforeType, String afterType, String id) {
		super();
		this.path = path;
		this.size = size;
		this.beforeType = beforeType;
		this.afterType = afterType;
		this.id = id;
	}


	
}
