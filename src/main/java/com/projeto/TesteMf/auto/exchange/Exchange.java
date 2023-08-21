package com.projeto.TesteMf.auto.exchange;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Exchange")
public class Exchange{
	private java.lang.Integer id;
	private java.lang.Integer id_conta_source;
	private java.lang.Integer id_conta_dest;
	private java.lang.Double value;
	@Id
	private String _id;
	public java.lang.Integer getId(){
		return id;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	public java.lang.Integer getId_conta_source(){
		return id_conta_source;
	}
	public void setId_conta_source(java.lang.Integer value) {
		this.id_conta_source = value;
	}
	public java.lang.Integer getId_conta_dest(){
		return id_conta_dest;
	}
	public void setId_conta_dest(java.lang.Integer value) {
		this.id_conta_dest = value;
	}
	public java.lang.Double getValue(){
		return value;
	}
	public void setValue(java.lang.Double value) {
		this.value = value;
	}
	public String get_id(){
		return _id;
	}
	public void set_id(String value) {
		this._id = value;
	}
}
