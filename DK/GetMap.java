/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DK;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author Administrator
 */
public class GetMap extends javax.swing.JFrame {
private String buffer; //Du lieu chua cac thanh pho va ky hieu | o truoc
private JFrame map= new JFrame("Google Map");//Tao khung JFrame ;//Tao khung JFrame

    public GetMap(){}

    public void get(String taphoptp[]){    
        File filetravetugoogle =new File("hinhanh.jpg") ;  //Dat ten file anh google se tra ve
        if(filetravetugoogle.exists()){//Xoa file hinhanh.jpg neu hinh anh gui ve tu google map da ton tai trong he thong
            filetravetugoogle.deleteOnExit();
        }

        try{
            buffer =new String("");
            for(int i=0;i<taphoptp.length;i++){
                buffer = buffer.concat("|"+taphoptp[i]+",VN");   //Noi cac chuoi lai voi nhau dat ky hieu | o phia truoc
            }
            String urlgoogle =new String("https://maps.googleapis.com/maps/api/staticmap?size=640x640&markers=size:mid|color:red"
                + buffer
                +"&path=color:0xff0000ff|weight:5"
                + buffer
            );
            
            //Ghi lai file da tra ve vao hinhanh.jpg
            URL url = new URL(urlgoogle);
            InputStream is = url.openStream();
            OutputStream os =new FileOutputStream(filetravetugoogle);
            byte[] b =new byte[2048];
            int dodai;
            while((dodai = is.read(b)) != -1){
                os.write(b, 0, dodai);
            }
            
            is.close();
            os.close();
            
            ImageIcon icon =new ImageIcon(new ImageIcon("hinhanh.jpg").getImage().getScaledInstance(630, 600, java.awt.Image.SCALE_SMOOTH));
            JPanel jp =new JPanel();//Tao jpanel 
            jp.add(new JLabel(icon)); //Them hinh anh vao jpanel
            map.add(jp); //Them jpanel vao jframe
            map.setLocation(650,30); //Data lai vi tri map lay tu google map api
            map.setVisible(true);
            map.pack();
            jp.repaint();
            
        }
        catch(IOException e){
            System.out.println("No Internet connection !!!");
        }
        filetravetugoogle.delete();//Xoa file hinh anh   
    }
}
