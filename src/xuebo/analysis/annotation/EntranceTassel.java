/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xuebo.analysis.annotation;

import utils.IOFileFormat;

/**
 *
 * @author xuebozhao
 */
public class EntranceTassel {
    public static void main (String[] args) { 
//        GeneFeature a = new GeneFeature (args[0]);
//        a.writeFile("lala.txt");
////          RangeAttribute b = new RangeAttribute(arg[0]);
////          b.writeTextFile("lalala");
            String infileS = "D:\\FeiLub\\UniquenessScore\\maizeV3\\AP.bfthresh1.1.MNaseHS.Ranges.dat";
            String outfileS = "D:\\FeiLub\\UniquenessScore\\maizeV3\\lalala.bed";
            new DatToBed (infileS,outfileS);
//            c.readFile("lalalabed");
    }
} 