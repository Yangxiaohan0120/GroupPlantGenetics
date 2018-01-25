/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhouyao.analysis.wheatHapMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 *
 * @author yaozhou
 */
public class ExtractionOfFastq {
    public ExtractionOfFastq(String inFile,String outFile,int ExSize){
        this.getFastq(inFile,outFile,ExSize);
    }
    public void getFastq(String inFile,String outFile,int ExSize){
        try{
            BufferedReader var;
            if(inFile.endsWith("gz"))  var = IOUtils.getTextGzipReader(inFile);
            else  var = IOUtils.getTextReader(inFile);
            BufferedWriter bw = IOUtils.getTextGzipWriter(outFile + ExSize/1000 + "_k.fq.gz");
            String content = null;
            int size = 0;
            while((content = var.readLine()) != null){
                size++;
                if(size < 4*ExSize + 1){
                    bw.write(content+"\n");
                    bw.flush();
                }else{
                    break;
                }
            }
            bw.close();
        }       
        catch (Exception e){
            e.printStackTrace();
        }  
    }
     
}
