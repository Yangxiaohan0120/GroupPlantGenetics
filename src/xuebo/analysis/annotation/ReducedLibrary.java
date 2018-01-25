/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xuebo.analysis.annotation;

import static htsjdk.samtools.util.SequenceUtil.a;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author xuebozhao
 */
public class ReducedLibrary {
    
    ReducedLibrary(String infileS, String outfileS) {

        this.SecondFiltering(infileS, outfileS);
//        this.outputFiles(outfileS1, outfileS2);
    }

    public void SecondFiltering(String infileS, String outfileS) {
        try{
            
           BufferedReader br;            
           br = IOUtils.getTextReader(infileS);
           
            String temp = null;
            String temp1 = null;
            String temp2 = "";
            String temp3 = null;
           
            int i = 0;
           
            BufferedWriter bw = IOUtils.getTextWriter(outfileS);
           
           
            while (( temp = br.readLine()) != null) {
//               System.out.println(tem[0]);
               
                if(temp.startsWith(">")){
                    if(i > 0){
                       
                       bw.write(temp2+"\n");
//                       i = 0;
                    }
                    String[] tem = temp.split(" ");
                    temp1 = tem[0];                   
                    temp2 = "";
                    bw.write(tem[0] + "\n");
                }
                else {
//                   while (temp.startsWith("A") || temp.startsWith("T")|| temp.startsWith("C")|| temp.startsWith("G")) {
//                   temp3 = temp2 + temp;
//                   temp2 = temp; 
//                   } 
//                   bw.write(temp3 + "\n");   
//                   temp2 = temp;
//                   temp3 = temp2;
                    temp2 = temp2+temp;
                    i = 1;
                }
            }
            bw.write(temp2+"\n");
            bw.flush();  
            bw.close(); 
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
       
    public List<Integer> binaryCutter(String inChr,String cutter){
        String query = null;
        List<Integer> pos = new ArrayList<>();
        for(int i = 0 ; i < inChr.length() ; i++){
            query = inChr.substring(i, i+cutter.length());
            if(query.equals(cutter)){
                pos.add(i);
            }       
        }
        return pos;
    }
    
//    public void binarySearching(String inChr,String cutter1,String cutter2){
//        List<Integer> pos1 = new ArrayList<>(this.binaryCutter(inChr,cutter1));
//        List<Integer> pos2= new ArrayList<>(this.binaryCutter(inChr, cutter2));
//        Integer[] a = (Integer[])pos1.toArray();
//        Integer[] b = pos1.toArray(new Integer[pos1.size()]);
//        int index = Arrays.binarySearch(b ,pos2);
//        index = - index -1;
//        System.out.println(index);
//    }
    
    public List<Integer> posSearching(String inChr,String cutter1,String cutter2){
        
        List<Integer> aa = new ArrayList<>(this.binaryCutter(inChr,cutter1));
        
        List<Integer> bb = new ArrayList<>(this.binaryCutter(inChr, cutter2));
        
        List<Integer> cc = new ArrayList<>();
        
        List<Integer> getpos = new ArrayList<>();
        
//        String[] both = concact(pos1, pos2);    
//        int c[] = this.contact(pos1, pos2);

        cc.addAll(aa);
        cc.addAll(bb);
        
//        Integer[] aa = pos1.toArray(new Integer[pos1.size()]);
//        Integer[] bb = pos2.toArray(new Integer[pos2.size()]);
//        Integer[] cc = pos3.toArray(new Integer[pos3.size()]);
        Collections.sort(cc);
        
        boolean ispos1 = false;
        boolean ispos2 = false;
        int pos1V = 0;
        int pos2V = 0;
        int j = 0;
        
        for(int i = 0; i<cc.size();i++){
            if(j >= aa.size()){
                cc.add(-1);
            }
            else if(j<aa.size()){
                
                if(cc.get(i) == aa.get(i)){
                    ispos1 = true;  
                    pos1V = cc.get(i);
                    j++;
                    if(ispos1 && ispos2){                       
                        if(pos1V<=pos2V){
                            getpos.add(pos1V);
                            getpos.add(pos2V);
                        }
                        else{
                            getpos.add(pos2V);
                            getpos.add(pos1V);
                        }
                        ispos2 = false;
                    }
                }
                
                
                else{
                    ispos2 = true;
                    pos2V = cc.get(i);
                    if(ispos1 && ispos2){                       
                        if(pos1V<=pos2V){
                            getpos.add(pos1V);
                            getpos.add(pos2V);
                        }
                        else{
                            getpos.add(pos2V);
                            getpos.add(pos1V);
                        }
                        ispos1 = false;
                    }
                } 
            }
        }
        return getpos;     
    }
     

    public void getReducedLibrary(String Chr,List<Integer> getpos,String inChr,
            String outfileS){
        try{
        String cutterinChr = null;
        String headcutterinchr =null;
//        BufferedWriter bw = IOUtils.getTextWriter(outfileS);
            for(int i = 0;i<getpos.size();i = i+2){

                cutterinChr = inChr.substring(getpos.get(i),getpos.get(i+2));
                headcutterinchr = ">" + Chr + "\t" + getpos.get(i) + "\t" + getpos.get(i+1) + "\n";
                getWriteStreamAppend(outfileS,headcutterinchr);
                getWriteStreamAppend(outfileS,cutterinChr+"\n");
//                bw.write(cutterinChr);
//                bw.write(headcutterinchr);
            }
//            bw.flush();  
//            bw.close(); 
        }
        catch (Exception e){
            e.printStackTrace();
        }       
    }
    
   
    public static void getWriteStreamAppend(String file, String conent) {                         
        BufferedWriter out = null;                                                   
        try {                                                                        
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));                              
            out.write(conent);                                                      
        } 
        catch (Exception e) {                                                     
            e.printStackTrace();                                                    
        } 
        finally {                                                                 
            try {                                                                    
                out.close();                                                        
            } catch (Exception e) {                                               
                e.printStackTrace();                                                
            }                                                                       
        }                                                                           
    }   
    
    
    
}