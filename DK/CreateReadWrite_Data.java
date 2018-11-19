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
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;

public class CreateReadWrite_Data {//File dung de doc va tao file notepad weight_data luu trong so va chi so cac thanh pho
private float trong_so;
private String line;
  public CreateReadWrite_Data(){
    trong_so=0f;
  }
  public float readFile(int chisotp1, int chisotp2){//Truyen chi so thanh pho start va end cho ham readFile de doc du lieu tu notepad
    try{
      File file = new File("src/csdl/weight_data.txt");//Doc file luu trong so
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      line=null;
      int i=0;
      while( (line = br.readLine())!=null){//Doc den khi het file 
        String [] tokens = line.split("\\s+"); //Lay cac du lieu sai khi da loai bo khoang trang ky hieu \\s+
        int var_1 = Integer.parseInt(tokens[0])-1;  //Doi stt thanh pho thu 1 sang kieu int
        int var_2 = Integer.parseInt(tokens[1])-1; //Doi stt thanh pho thu 2 sang kieu int
        float var_3 = Float.parseFloat(tokens[2]);  //Luu trongso giua 2 thanh pho vao bien var_3
        
        if(var_1==chisotp1 && var_2==chisotp2){//Gan bien trong_so khi tim thay 2 chi so da luu trong var_1 va var_2
          trong_so=var_3;
          break;//Ngat ra vong lap while khi tim thay trong so
        }
      }
    }
    catch(IOException e){
      System.out.print("Error IO File ");
    }
    return trong_so;
  }
  public void writeFile(int chisotp1,int chisotp2,float trongsocanghi){//Tao file weight_data luu trong so khi file weight_data khong ton tai
    Path p = Paths.get("src/csdl/weight_data.txt"); //Tao noi ket den file

    String var_1 = Integer.toString(chisotp1);   //Ep kieu cac chi so sang kieu String
    String var_2 = Integer.toString(chisotp2);
    String var_3 = Float.toString(trongsocanghi);
    String congchuoi = var_1+" "+var_2+" "+var_3;  //Cong gia tri lai thanh 1 hang
    byte data[]=congchuoi.getBytes();   //Ep sang kieu byte de ghi vao file
    
    try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, CREATE, APPEND))) {
      
      out.write(data, 0, data.length);   //Ghi gia tri chisotp1  chisotp2 va trong_so vao file
      out.write(System.getProperty("line.separator").getBytes());   //xuong hang sau khi ghi xong 1 hang

    }catch (IOException x) {
      
      System.err.println(x);

    }
  }
}
