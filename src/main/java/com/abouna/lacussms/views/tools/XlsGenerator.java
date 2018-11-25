/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views.tools;

import com.abouna.lacussms.entities.BkCompCli;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author SATELLITE
 */
public class XlsGenerator {
    
    public XlsGenerator(List<BkCompCli> comptes,String path){
        InputStream xlsRefStream = null;
        WritableWorkbook outWorkbook = null;
        try {
            File excel = new File(path);
            outWorkbook = Workbook.createWorkbook(excel);
            //outWorkbook.createSheet("Comptes",0);
            WritableSheet workSheet = outWorkbook.createSheet("Comptes",0);
            int i = 1,j=0;
            for(BkCompCli compte : comptes){
                workSheet.addCell(new Label(0, 0, "NOMS"));
                workSheet.addCell(new Label(1, 0, "Prenoms"));
                workSheet.addCell(new Label(2, 0, "Comptes"));
                Label label = new Label(0, i, compte.getCli().getNom());
                workSheet.addCell(label);
                Label label1 = new Label(1, i, compte.getCli().getPrenom());
                workSheet.addCell(label1);
                Label label2 = new Label(2, i, compte.getNumc());
                workSheet.addCell(label2);
                i++;
            }
                outWorkbook.write();
                outWorkbook.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XlsGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XlsGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(XlsGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }
}
