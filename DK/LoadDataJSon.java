/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DK;

/**
 *
 * @author Administrator
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.omg.CORBA.portable.InputStream;

import net.sf.json.JSONException;
public class LoadDataJSon {
private float trongso;
    public LoadDataJSon(){
        trongso=0f;
    }

    public float loadData(String origins,String destinations){
        JSONParser jp = new JSONParser();//Tao doi tuong JSONParser
        try{
            URL link = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="
                                + origins
                                + ",VN&destinations="
                                + destinations
                                +",VN"
            );//Tao URL ket noi den google map api luu trong so trong file Json
            
            URLConnection cn = link.openConnection();//Tao ket noi google map bang URL
            Object ob = jp.parse(new BufferedReader(new InputStreamReader(cn.getInputStream()))); //Parse file JSon bang BufferedReader vao ob
            JSONObject jo = (JSONObject)ob;  //Ep kieu thanh doi tuong JSon
            JSONArray ja =(JSONArray)jo.get("rows"); // Lay thuoc tinh rows trong file JSon gan cho mang JSON ja
            for(int i=0;i<ja.size();i++){
                JSONObject items = (JSONObject) ja.get(i);  //Xet 1 du lieu trong thuoc tinh rows o tren
                JSONArray items1=(JSONArray)items.get("elements");  //Lay thuoc tinh elements(co dang mang) cua thuoc tinh rows ra 
                System.out.print("size: "+items1.size());
                for(int j=0;j<items1.size();j++){
                    JSONObject items2 = (JSONObject) items1.get(j);  //Xet du lieu ke tiep trong thuoc tinh elements
                    JSONObject items3=(JSONObject)items2.get("distance");  //Lay thuoc tinh distance mang 2 gia tri "text" va "value"
                    String items4=new String();
                    try{
                    items4=new String(items3.toString()); //Ep toan bo gia tri cua distance sang kieu chuoi
                	System.out.println(": "+items4);
                    }
                    catch(Exception e){
                    	//Bat loi cac truong hop Google Map API khong luu ton tai du lieu trong so 
                    	if((origins.equals("BinhDinh")&&destinations.equals("QuangNgai"))||(origins.equals("QuangNgai")&&destinations.equals("BinhDinh"))){
                    		items4=new String("{'text':'11.1 mi','value':18727}");
                    	}
                    	if((origins.equals("GiaLai")&&destinations.equals("BinhDinh"))||(origins.equals("BinhDinh")&&destinations.equals("GiaLai"))){
                    		items4=new String("{'text':'105.1 mi','value':170030}");
                    	}
                    	if((origins.equals("PhuYen")&&destinations.equals("BinhDinh"))||(origins.equals("BinhDinh")&&destinations.equals("PhuYen"))){
                    		items4=new String("{'text':'61.2 mi','value':98330}");
                    	}
                    	if((origins.equals("KonTum")&&destinations.equals("BinhDinh"))||(origins.equals("BinhDinh")&&destinations.equals("KonTum"))){
                    		items4=new String("{'text':'146.4 mi','value':235000}");
                    	}
                    	System.out.println(": "+items4+" Google khong co du lieu");
                    }
                    char items5[]=new char[items4.length()]; //Tao mang ky tu moi luu chuoi distance vua moi ep 
                    items5 = items4.toCharArray(); 
                    int x;
                    for( x=0;x<items5.length;x++){  //Xet toan bo mang ky tu cua distance va tim vi tri cua chu cai "v" dung truoc "value"
                        if(items5[x]=='v'){
                                break;    //Ngat khi da tim ra
                        }
                    }
                    int size=0;
                    for(int y=x+7;y<items5.length-1;y++){  //Dem toan bo ky tu con lai tu vi tri sau "value: " den vi tri truoc dau ";" 
                        size++;
                    }
                    char giatridodai[] = new char[size];  
                    i=0;
                    for(int y=x+7;y<items5.length-1;y++){  //Sao chep mang items5 cho giatri do dai theo thu tu tu 0 den length 
                        giatridodai[i]=items5[y];
                        i++;
                    }
                    String w = String.valueOf(giatridodai);  //Ghep mang ky tu thanh chuoi 
                    trongso = Float.parseFloat(w);  //Ep ve kieu float
                }
            }
        }
        catch(Exception e){
                System.out.println("Loi load du lieu file JSON tu google");

        }
        return trongso/1000; //Tra ve gia tri trong so sau khi da chuyen doi tu m sang km
    }
}

