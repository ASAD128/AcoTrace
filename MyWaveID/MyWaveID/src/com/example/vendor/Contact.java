package com.example.vendor;

public class Contact {
	
	//private variables
	int _id;
	String _name;
	String _lname;
	String _fname;
	String _email;
	String _address;
	String _phone_number;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String name, String _phone_number){
		this._id = id;
		this._name = name;
		this._phone_number = _phone_number;
	}
	
	// constructor
	public Contact(String name, String _phone_number){
		this._name = name;
		this._phone_number = _phone_number;
	}
	public Contact(String fname, String lname, String email,String address,String _phone_number){
		this._fname = fname;
		this._lname = lname;
		this._email = email;
		this._address = address;
		this._phone_number = _phone_number;
	}
	public String get_lname() {
		return _lname;
	}
	public void set_lname(String _lname) {
		this._lname = _lname;
	}
	public String get_fname() {
		return _fname;
	}
	public void set_fname(String _fname) {
		this._fname = _fname;
	}
	public String get_email() {
		return _email;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	public String get_address() {
		return _address;
	}
	public void set_address(String _address) {
		this._address = _address;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting phone number
	public String getPhoneNumber(){
		return this._phone_number;
	}
	
	// setting phone number
	public void setPhoneNumber(String phone_number){
		this._phone_number = phone_number;
	}
}