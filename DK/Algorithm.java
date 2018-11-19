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
public class Algorithm {
private float pi[];//Tao mang luu khoang cach tu Origin den pi[i]
private int p[];//Tao mang luu thanh pho ke voi thanh pho dang xet
private int mark[];//Tao mang danh dau cac thanh pho chua xet
private final float INF = 9999999;  //Bien vo cung de so sanh
private int path[]; //Tao mang luu chi so cac thanh pho ket qua tu Origin den Destination
private String file_return[];//Tao mang tra ve ten thanh pho thong qua mang (path[] luu chi so)
private int size;//so luong phan tu cua mang path[]
    ReadData rd =new ReadData();//Goi doi tuong de kiem tra 2 thanh pho co ke nhau trong mang_so[][] hay ko va lay tongdiem
    public Algorithm(){
        rd.readFile();//Doc File notepad weight luu trong so
        pi = new float[rd.getNumber()];
        p = new int[rd.getNumber()];
        mark = new int[rd.getNumber()];
        path = new int[rd.getNumber()];
        size=0;
    }
    public void Dijkstra(int start, int end){    
        CreateReadWrite_Data crw = new CreateReadWrite_Data();  //Goi doi tuong de truy xuat ham readFile tra ve trongso 2 thanh pho
        rd.readFile();
        int i,j,it;
        for(i=0;i<rd.getNumber();i++){  //Khoi tao ban dau
          pi[i]=INF;
          mark[i]=0;
        }
        pi[start]=0f;  //Khoang cach tu diem dau tien toi chinh no bang 0
        p[start]=-1;  //Khong co diem lien truoc cua dinh dau tien
        for(it=0;it<rd.getNumber();it++){
            float min_pi=INF;    
            for(j=0;j<rd.getNumber();j++){
                if(mark[j]==0 && pi[j]<min_pi){  //So sanh lua chon diem co khoang cach tu start den no ngan nhat dua vao pi[]
                   min_pi=pi[j];
                   i=j;
                }
            }
            mark[i]=1;  //Danh dau diem vua dc chon
  
            for(j=0;j<rd.getNumber();j++){
                if(mark[j]==0&&(rd.check_ke(i, j)!=0)){
                   if(pi[i]+crw.readFile(i, j)<pi[j]){ //Cap nhat lai gia tri cua cac dinh tu goc den no
                       pi[j]=pi[i]+crw.readFile(i, j);
                       p[j]=i;
                   }  
                }
            }
        }
    }
    public String[] copyArray(String[]x){//Tao phuong thuc den sap xep lai mang luu thanh pho theo thu tu tu Origin den Destination
        String copy[];
        int count=0;
        for(int i=0;i<x.length;i++){
            if(x[i]!=null){//Mang x[] luu nhung phan tu null va ten cua cac thanh pho nen can dem xem co bao nhieu phan tu khac null
                count++;
            }
        }
        copy =new String[count];//Khoi tao mang copy co count phan tu
        count=0;
        for(int i=x.length-1;i>=0;i--){//Sao chep lai cac thanh pho mang (x[] theo thu tu destination den origin) qua mang (count[] theo thu tu origin den destination)
            if(x[i]!=null ){
                copy[count]=new String();
                copy[count]=x[i];
                count++;
            }
        }
        return copy;//Tra ve mang da sap xep
    }
    public String[] tapHopTenThanhPhoTuEndVeStart(int origins ,int destinations){
        ReadData rd=new ReadData();
        rd.readFile();
        int current=destinations;//Gan vi tri destination vao 1 bien
        String path_name[]=new String[rd.getNumber()];
        int j=0;
        
        while(current != -1){
            path_name[j]=new String();//Tao mang ten
            path_name[j]=rd.layTenDiemDi(current);//Luu lai ten cua phan tu
            j++;  
            current = p[current];//Gan ten cua thanh pho ke de qua lai vong lap while
        }
        file_return = new String[copyArray(path_name).length];//Goi lai ham sap xep ten thanh pho o tren
        for(j=0;j<copyArray(path_name).length;j++){
            file_return[j]=copyArray(path_name)[j]; //Sao chep lai mang ten vao file_return
        }
        
        setlength(file_return.length);//Dat lai do dai
        return file_return;   //Tra ve tap hop ten thanh pho
    }
    
    public String getFloat(int e){//Tra ve gia tri ep kieu String cua do dai kich thuoc tu origin den destination
        return String.valueOf(pi[e]);
    }
    public void setlength(int size){
        this.size=size;
    }
    public int length_filereturn(){
        return size;
    }
    public String[] getFilereturn(){
        return file_return;
    }
}
    
