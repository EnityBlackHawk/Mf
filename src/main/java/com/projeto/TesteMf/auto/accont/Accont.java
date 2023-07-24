package com.projeto.TesteMf.auto.accont;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.projeto.TesteMf.auto.exchange.Exchange;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "Accont")
public class Accont{
	private java.lang.Integer id;
	private java.lang.Integer id_client;
	private java.lang.Double value;
	private Client client;
	@Id
	private String _id;
	@ReadOnlyProperty
	@DocumentReference(lookup = "{ 'id_conta_dest' : ?#{#self.id} }")
	private java.util.List<Exchange> exchange;
	public java.lang.Integer getId(){
		return id;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	public java.lang.Integer getId_client(){
		return id_client;
	}
	public void setId_client(java.lang.Integer value) {
		this.id_client = value;
	}
	public java.lang.Double getValue(){
		return value;
	}
	public void setValue(java.lang.Double value) {
		this.value = value;
	}
	public Client getClient(){
		return client;
	}
	public void setClient(Client value) {
		this.client = value;
	}
	public String get_id(){
		return _id;
	}
	public void set_id(String value) {
		this._id = value;
	}
	public java.util.List<Exchange> getExchange(){
		return exchange;
	}
	public void setExchange(java.util.List<Exchange> value) {
		this.exchange = value;
	}
}
