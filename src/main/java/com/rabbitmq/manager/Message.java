package com.rabbitmq.manager;




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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getBeforeType() {
		return beforeType;
	}

	public void setBeforeType(String beforeType) {
		this.beforeType = beforeType;
	}

	public String getAfterType() {
		return afterType;
	}

	public void setAfterType(String afterType) {
		this.afterType = afterType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
