package com.example.member_registration.Model;
public class Emp {
    public String Id,Name,E_mail,Address,Contact,DOB,DOA,State,City;
    private byte[] image;

    public Emp(String Id,String Name,String E_mail,String Address,String Contact,String DOB,String DOA,String State,String City,byte[] image)
    {
        this.Id=Id;
        this.Name=Name;
        this.E_mail=E_mail;
        this.Address=Address;
        this.Contact=Contact;
        this.DOB=DOB;
        this.DOA=DOA;
        this.State=State;
        this.City=City;
        this.image=image;
    }
    public Emp()
    {}
    public String getId(){
        return Id;
    }
    public void setId(String Id){
        this.Id=Id;
    }
    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name=Name;
    }
    public String getE_mail(){
        return E_mail;
    }
    public void setE_mail(String E_mail){
        this.E_mail=E_mail;
    }
    public String getAddress(){
        return Address;
    }
    public void setAddress(String Address){
        this.Address=Address;
    }
    public String getContact(){
        return Contact;
    }
    public void setContact(String Contact){
        this.Contact=Contact;
    }
    public String getDOB(){
        return DOB;
    }
    public void setDOB(String DOB){
        this.DOB=DOB;
    }
    public String getDOA(){
        return DOA;
    }
    public void setDOA(String DOA){
        this.DOA=DOA;
    }
    public String getState()
    {
        return State;
    }
    public void setState(String State)
    {
        this.State=State;
    }
    public String getCity(){
        return City;
    }
    public void setCity(String City){
        this.City=City;
    }


}
