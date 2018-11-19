/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DK;


import java.io.*;
public class ReadData  {
private String line; //Dong trong file
private String number_str; //so luong thanh pho tai dong dau tien trong file dang String
private int number;  //so luong thanh pho tai dong dau tien trong file dang int(da Parse())
private float mang_so[][]; //Mang 2 chieu danh dau so luong thanh pho ke nhau
private int i;
private int var_1,var_2;  //Bien doc stt cac thanh pho
private String var_3,var_4;  //Bien doc ten cac thanh pho
private String mang_ten[];  //Mang 1 chieu luu ten thanh pho
private int mang_csten[]; //Mang 1 chieu luu cs ten thanh_pho

    public ReadData(){
        line = null;
        number_str = null;
        number=0;
        mang_so = new float[number][number];
        var_1=0;
        var_2=0;
        var_3=null;
        var_4=null;
    }
    public void makeNull_mang(float mang_so[][],int n){ //Tao mang 2 chieu rong
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                mang_so[i][j]=0;
            }
        }
    }

    public void readFile()  { //Doc File du lieu txt tu csdl/main_data
        
        LoadDataJSon ldj = new LoadDataJSon ();
        CreateReadWrite_Data crwd= new CreateReadWrite_Data();
        
        try{
            File file = new File("src/csdl/main_data.txt");//Doc file notepad luu chi so va ten thanh pho 
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            number_str = br.readLine(); //Doc dong dau tien
            number = Integer.parseInt(number_str); //Doi chuoi sang int
            setNumber(number);
            
            mang_so=new float[number][number]; 
            mang_ten= new String[number];
            mang_csten=new int[number];
            i=0;
            makeNull_mang(mang_so,number);//Tao mang rong pi luu toan bo gia tri 0
            while((line = br.readLine()).compareTo("null")!=0){//Load den khi doc duoc hang co luu chuoi ky tu null
               String [] tokens = line.split("\\s+"); //Lay cac du lieu sai khi da loai bo khoang trang ky hieu \\s+
               var_1 = Integer.parseInt(tokens[0]);  //Doi stt thanh pho thu 1 sang kieu int
               var_2 = Integer.parseInt(tokens[1]); //Doi stt thanh pho thu 2 sang kieu int
               var_3 = tokens[2];  //Luu ten thanh pho thu 1 vao bien var_3
               var_4 = tokens[3];  //Luu ten thanh pho thu 2 vao bien var_4
               //LoadDatJSOn gi trong so truc tiep va so dau duoi
               
               mang_so[var_1-1][var_2-1] = 1;//13 citys run from  0 to 12  Danh dau cac thanh pho ke nhau
               mang_ten[var_1-1]=new String(var_3);  // Luu ten thanh pho tu bien var_3 sang mang_ten tai stt la var_1-1
               i++;
            }
            
            //Chay lai thuat toan lan 2 de luu gia tri trong so vao file weight_data
            File f = new File("src/csdl/weight_data.txt");
            BufferedReader br_ghitrongso = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            br_ghitrongso.readLine();
            if(!f.exists()){//Chi ghi lai File neu nhu file khong ton tai
                //System.out.print("Gia tri da ton tai");
                while( (line = br_ghitrongso.readLine()).compareTo("null")!=0){
                    String [] tokens = line.split("\\s+"); //Lay cac du lieu sai khi da loai bo khoang trang ky hieu \\s+
                    if(tokens[0].equals("null")){
                	   break;
                    }else{
                       var_1 = Integer.parseInt(tokens[0]);  //Doi stt thanh pho thu 1 sang kieu int
                       var_2 = Integer.parseInt(tokens[1]); //Doi stt thanh pho thu 2 sang kieu int
                       var_3 = tokens[2];  //Luu ten thanh pho thu 1 vao bien var_3
                       var_4 = tokens[3];  //Luu ten thanh pho thu 2 vao bien var_4
                       //LoadDatJSOn ghi trong so truc tiep va so dau duoi
                       System.out.print(var_3+var_4);
                       crwd.writeFile(var_1, var_2, ldj.loadData(var_3, var_4));
                    }
                }
            }
        }
        catch(IOException e){
            System.out.println("Error IO File !!!!");
        }
    }

    public String layTenDiemDi(int chisodiemdi){
        return mang_ten[chisodiemdi];
    }
    public int demSoDiemKe(String ten){
        int sluongptketrongmang=0;  //So luong phan tu ke trong mang cua diem dang xet
        for(int i=0;i<number;i++){
            if(mang_so[timChiSoDuaVaoTen(ten)][i]!=0){  //Dem co bao nhieu phan tu ke voi diem dang xet luu vao bien sluongptketrongmang
                sluongptketrongmang++;
            }
        }
        return sluongptketrongmang;
    }
    
    public String[] layTenDiemKe(String tendiemdi){
        String tendiemke[]; //Tao mang chua ten diem ke vs diem dang xet    
        int mang_ke[]=new int[demSoDiemKe(tendiemdi)];  //Tao mang co kieu int de luu chi so cac phan tu ke voi diem dang xet
        int chisomang_ke=0;
        for(int i=0;i<number;i++){
            if(mang_so[timChiSoDuaVaoTen(tendiemdi)][i]!=0){   //Do tim cac phan tu ke voi diem dang xet tra ve chi so diem ke luu vao mang_ke[]
                mang_ke[chisomang_ke]=i;
                chisomang_ke++;
            }
        }

        tendiemke = new String[chisomang_ke];
        for(chisomang_ke=0;chisomang_ke<mang_ke.length;chisomang_ke++){  //Dung chi co diem ke luu vao mang_ke[] de tim ra ten tp
            tendiemke[chisomang_ke]=new String(mang_ten[mang_ke[chisomang_ke]]);//Luu ten thanh pho vao mang tendiemke[]
        }
        return tendiemke;//Tra ve ten diem ke vs diem dang xet
    }

    public String[] layTenTatCaTP(){
        return mang_ten;
    }

    public int timChiSoDuaVaoTen(String ten){ 
        int i;
        for(i=0;i<number;i++){  //Do tim chi so ten diem trong mang_ten
            if(mang_ten[i].equals(ten)){
              break;  //Tra ve chi so 
            }
        }
        return i;
    }
    public void setTrongSoTrongMang(float trongso,String tentpdi,String tentpden){
        mang_so[timChiSoDuaVaoTen(tentpdi)][timChiSoDuaVaoTen(tentpden)]=trongso;
    }
    public int getNumber(){
        return number;
    }
    public void setNumber(int number){
        this.number=number;
    }
    public int check_ke(int start, int end){
        if(mang_so[start][end] != 0){
            return 1;
        }
        else{
            return 0;
        }
    }
    public int traVeTrongSoTrongMang(int start, int end){
        return (int) mang_so[start][end];
    }
}

    
