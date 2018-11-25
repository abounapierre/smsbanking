/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views.tools;

import com.abouna.lacussms.entities.BkCli;
import com.abouna.lacussms.entities.BkCompCli;
import com.abouna.lacussms.entities.BkEve;
import com.abouna.lacussms.entities.BkMad;
import com.abouna.lacussms.entities.BkOpe;
import com.abouna.lacussms.entities.MessageFormat;
import com.abouna.lacussms.entities.SentMail;
import com.abouna.lacussms.entities.Variable;
import com.abouna.lacussms.service.LacusSmsService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrateur
 */
public class Utils {
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Utils.class);

    public static int anneeDate(Date d) {
        Date maDate;
        SimpleDateFormat maDateLongue;
        maDate = new Date();
        maDateLongue = new SimpleDateFormat("yyyy");
        return new Integer(maDateLongue.format(maDate));
    }

    public static Date getTimeFromInternet() {
        logger.info("Test connexion internet");
        try {
            Date date = null;
            String TIME_SERVER = "time-a.nist.gov";
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
            date = new Date(returnTime);
            return date;
        } catch (UnknownHostException ex) {
            logger.info("probleme de connexion internet");
            return new Date();
        } catch (IOException ex) {
            logger.info("probleme de connexion internet");
            return new Date();
        }

    }

    /*public List<String> extractValeur(String url){
     List<String> urlList = new ArrayList<String>();
     String r = "";
     for(int i = 0;i<url.length();i++){
     char c = url.charAt(i);
     if(c=='='){
     while(c!='&'){
                     
     }
                 
     }  
     }
     }*/
    public static String moveZero(Double d) {
        String res = "";
        int i = 0;
        while (i != Double.toString(d).length()) {
            if (Double.toString(d).charAt(i) != '.') {
                res += Double.toString(d).charAt(i);
            } else {
                break;
            }
            i++;
        }
        return res;
    }

    public static List<String> getNumFromExcel(String path) {
        List<String> list = new ArrayList<String>();
        FileInputStream fichier = null;
        try {
            fichier = new FileInputStream(new File(path));
            XSSFWorkbook wb = new XSSFWorkbook(fichier);
            XSSFSheet sheet = wb.getSheetAt(0);
            for (Row ligne : sheet) {
                String data = "";

                if (ligne.getCell(0) != null) {
                    if (ligne.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        long c = (long) ligne.getCell(0).getNumericCellValue();
                        data = Long.toString(c);
                    } else if (ligne.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                        data = ligne.getCell(0).getStringCellValue();
                    }
                }

                if (data.length() == 8 || data.length() == 11) {
                    if (!data.startsWith("241")) {
                        data = "241" + data;
                    }
                    list.add(data);
                } else if (data.length() == 9 || data.length() == 12) {
                    if (!data.startsWith("237")) {
                        data = "237" + data;
                    }
                    list.add(data);
                }

            }
            return list;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }

    }

    public static String importExcel(String path, String ext, LacusSmsService service) {
        FileInputStream fichier = null;
        boolean end = false;
        try {
            fichier = new FileInputStream(new File(path));
            //créer une instance workbook qui fait référence au fichier xlsx

            Workbook wb = WorkbookFactory.create(fichier);
            DataFormatter objDefaultFormat = new DataFormatter();
            FormulaEvaluator objFormulaEvaluator = null;
            if (ext.equals(".xls")) {
                objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
            } else if (ext.equals(".xlsx")) {
                objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
            }
            int i=1;
            Sheet sheet = wb.getSheetAt(0);
            for (Row ligne : sheet) {//parcourir les lignes
                try {
                    if (ligne.getRowNum() != 0) {
                        if (objFormulaEvaluator != null) {
                            Cell cellValue = ligne.getCell(0);
                            objFormulaEvaluator.evaluate(cellValue);
                            String cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
                            BkCli bkCli = new BkCli();
                            BkCompCli bkCompCli = new BkCompCli();
                            bkCli.setNom(cellValueStr);
                            cellValue = ligne.getCell(1);
                            objFormulaEvaluator.evaluate(cellValue);
                            cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
                            bkCli.setPrenom(cellValueStr.toUpperCase());
                            cellValue = ligne.getCell(2);
                            objFormulaEvaluator.evaluate(cellValue);
                            cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
                            String id = "", compte = cellValueStr;
                            compte = compte.replace(" ", "").replace(".", "").replace(";", "").replace(",", "");
                            id = compte.length()
                                    >= 9 ? compte.substring(3, 9) : compte;
                            bkCli.setCode(id);
                            cellValue = ligne.getCell(3);
                            objFormulaEvaluator.evaluate(cellValue);
                            cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
                            long num = 0;
                            if (cellValueStr.length() == 9) {
                                num = Long.parseLong("237" + cellValueStr);
                            } else if (Long.toString(num).length() == 8) {
                                num = Long.parseLong("241" + Long.toString(num));
                            }
                            bkCli.setPhone(num);

                            cellValue = ligne.getCell(4);
                            objFormulaEvaluator.evaluate(cellValue);
                            cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
                            bkCli.setEmail(cellValueStr);
                            cellValue = ligne.getCell(5);
                            objFormulaEvaluator.evaluate(cellValue);
                            cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
                            bkCli.setLangue(cellValueStr);
                            bkCli.setEnabled(true);
                            cellValue = ligne.getCell(6);
                            objFormulaEvaluator.evaluate(cellValue);
                            cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
                            bkCli.setLibelle(cellValueStr);

                            if (service.getBkCliById(id) == null) {
                                service.enregistrer(bkCli);
                            }
                            bkCompCli.setCli(bkCli);
                            bkCompCli.setNumc(compte.toUpperCase());
                            bkCompCli.setEnabled(true);
                            if (service.getBkCompCliById(compte) == null) {
                                service.enregistrer(bkCompCli);
                            }
                            //System.out.println(bkCli.toString());
                        }
                        
                    }
                   
                   /* if (ligne.getRowNum() != 0) {
                        BkCli bkCli = new BkCli();
                        BkCompCli bkCompCli = new BkCompCli();
                        bkCli.setNom(ligne.getCell(0) == null ? "" : ligne.getCell(0).getStringCellValue().toUpperCase());
                        bkCli.setPrenom(ligne.getCell(1) == null ? "" : ligne.getCell(1).getStringCellValue().toUpperCase());
                        String compte = "";
                        String id = "";
                        if (ligne.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            String vall = Double.toString(ligne.getCell(2).getNumericCellValue());
                             vall = vall.replace(" ", "");
                            long c = Long.parseLong(vall);//(long) ligne.getCell(2).getNumericCellValue();
                            compte = Long.toString(c);
                            id = Long.toString(c).length()
                                    >= 9 ? Long.toString(c).substring(3, 9) : Long.toString(c);
                            bkCli.setCode(id);
                        } else if (ligne.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                            compte = ligne.getCell(2).getStringCellValue();
                        } else if (ligne.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                            bkCli.setCode(id);
                            String c = ligne.getCell(2).getStringCellValue();
                            compte = c;
                            id = c.length()
                                    >= 9 ? c.substring(3, 9) : c;
                            bkCli.setCode(id);
                        } else {
                            String c = ligne.getCell(2).getStringCellValue();
                            compte = c;
                            id = c.length()
                                    >= 9 ? c.substring(3, 9) : c;
                            bkCli.setCode(id);
                        }
                        long num = (long) (ligne.getCell(3) == null ? 0 : ligne.getCell(3).getNumericCellValue());
                        if (Long.toString(num).length() == 9) {
                            num = Long.parseLong("237" + Long.toString(num));
                        } else if (Long.toString(num).length() == 8) {
                            num = Long.parseLong("241" + Long.toString(num));
                        }
                        bkCli.setPhone(num);
                        bkCli.setEmail(ligne.getCell(4) == null ? "" : ligne.getCell(4).getStringCellValue());
                        bkCli.setLangue(ligne.getCell(5).getStringCellValue());
                        bkCli.setEnabled(true);
                        bkCli.setLibelle(ligne.getCell(6).getStringCellValue());
                        if (service.getBkCliById(id) == null) {
                            service.enregistrer(bkCli);
                        }
                        bkCompCli.setCli(bkCli);
                        bkCompCli.setNumc(compte.toUpperCase());
                        bkCompCli.setEnabled(true);
                        if (service.getBkCompCliById(compte) == null) {
                            service.enregistrer(bkCompCli);
                        }
                    }*/
                    i++;
                } catch (NumberFormatException ex) {
                    System.out.println("Format incorrect nouvelle version " + ex.getMessage());
                    end = true;
                }
            }
            if(end)
                return "KO";
            else
                return "OK";
        } catch (FileNotFoundException ex) {
            logger.info("Fichier non trouvé");
            return ex.getMessage();
        } catch (IOException ex) {
            logger.info("Erreur lors de l'ouverture du fichier");
            return ex.getMessage();
        } catch (InvalidFormatException ex) {
            logger.info("probleme de format de la cellule");
            return ex.getMessage();
        } finally {
            try {
                fichier.close();
            } catch (IOException ex) {
                logger.info("Erreur lors de la fermeture du fichier");
                return ex.getMessage();
            }
        }
        
    }

    public static Object getCellValue(Cell cell) {
        Object o = null;
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            o = cell.getNumericCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            o = cell.getStringCellValue();
        }
        return o;
    }

    public static boolean isCorrect(String text) {
        int a = 0, b = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '<') {
                a++;
            } else if (text.charAt(i) == '>') {
                b++;
            }
        }
        return a == b;
    }

    public static boolean iscorrect(List<String> variables) {
        int a = 0, b = variables.size();
        for (String var : variables) {
            for (Variable s : Arrays.asList(Variable.values())) {
                if (s.toString().equals(var)) {
                    a++;
                }
            }
        }
        return a == b;
    }

    public static List<String> extract(String text) {
        char c = 0;
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < text.length(); i++) {
            String result = "";
            if (text.charAt(i) == '<') {
                int j = i + 1;
                while (text.charAt(j) != '>' & j < text.length()) {
                    result += text.charAt(j);
                    j++;
                }
                list.add(result);
            }
        }
        return list;
    }

    public static String remplacerVariable(BkCli bkCli, BkOpe bkOpe, BkEve bkEve, MessageFormat mf) {
        List<String> list = extract(mf.getContent());
        String text = mf.getContent();
        for (String s : list) {
            if (s.equals("nom")) {
                text = text.replace("<" + s + ">", bkCli.getNom());
            } else if (s.equals("pre")) {
                text = text.replace("<" + s + ">", bkCli.getPrenom());
            } else if (s.equals("numc")) {
                text = text.replace("<" + s + ">", bkEve.getCompte());
            } else if (s.equals("lib")) {
                text = text.replace("<" + s + ">", bkCli.getLibelle());
            } else if (s.equals("numt")) {
                text = text.replace("<" + s + ">", bkCli.getPhone() + "");
            } else if (s.equals("date")) {
                text = text.replace("<" + s + ">", bkEve.getDVAB().length() > 10 ? bkEve.getDVAB().substring(0, 10) : bkEve.getDVAB());
            } else if (s.equals("mont")) {
                text = text.replace("<" + s + ">", bkEve.getMontant() == null ? moveZero(bkEve.getMont()) : bkEve.getMontant());
            } else if (s.equals("agence")) {
                text = text.replace("<" + s + ">", bkEve.getBkAgence() == null ? "" : bkEve.getBkAgence().getNoma());
            } else if (s.equals("heure")) {
                text = text.replace("<" + s + ">", getHSAI(bkEve.getHsai()));
            }
        }
        return text;
    }

    public static String remplacerVariable(BkMad eve, MessageFormat mf) {
        List<String> list = extract(mf.getContent());
        String text = mf.getContent();
        for (String s : list) {
            if (s.equals("numt1")) {
                text = text.replace("<" + s + ">", eve.getAd1p());
            } else if (s.equals("numt2")) {
                text = text.replace("<" + s + ">", eve.getAd2p());
            } else if (s.equals("code")) {
                text = text.replace("<" + s + ">", eve.getClesec());
            } else if (s.equals("date1")) {
                text = text.replace("<" + s + ">", eve.getDco().length() > 10 ? eve.getDco().substring(0, 10) : eve.getDco());
            } else if (s.equals("date2")) {
                text = text.replace("<" + s + ">", eve.getDbd().length() > 10 ? eve.getDbd().substring(0, 10) : eve.getDbd());
            } else if (s.equals("mont")) {
                text = text.replace("<" + s + ">", eve.getMnt());
            }
        }
        return text;
    }

    public static String getHSAI(String heure) {
        String result = null;
        if (heure.length() > 8) {
            result = heure.substring(0, 5);
        } else {
            if (heure.length() == 4) {
                result = heure.substring(0, 2) + ":" + heure.substring(2, 4) + "";
            }
        }
        return result;
    }

    public static boolean estUnEntier(String chaine) {
        try {
            Integer.parseInt(chaine);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean sendMail(String msg) {
        final String username = "lacussms.app@gmail.com";
        final String password = "@dmin123!";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lacussms.appil@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("eabouna@gmail.com"));
            message.setSubject("Application en cours d'exécution");
            Date date = new Date();
            message.setText(msg);
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            return false;
        }
    }

    public static String testConnexionInternet() {
        String code = "KO";
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
            if (conn1.getResponseCode() == HttpURLConnection.HTTP_OK) {
                code = "OK";
            }
        } catch (UnknownHostException ex) {
            return "KO";
        } catch (MalformedURLException ex) {
            return "KO";
        } catch (IOException ex) {
            return "KO";
        }
        return code;
    }

    public static void enregistrerMail(LacusSmsService service) {
        Date date = new Date();
        String message;
        try {
            message = "Application tourne correctement,"
                    + "\n\n Lacus SMS, please CopyRight 2018 SunSoftware Fundation!"
                    + "\n\n " + "en date du " + date + " depuis " + InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            message = "Application tourne correctement,"
                    + "\n\n Lacus SMS, please CopyRight 2018 SunSoftware Fundation!"
                    + "\n\n " + "en date du " + date + " depuis " + " unknow host";
        }
        SentMail sentMail = new SentMail();
        sentMail.setContent(message);
        sentMail.setHeure(date);
        sentMail.setSentDate(date);
        String res = testConnexionInternet();
        if (res.equals("OK")) {
            sendMail(message);
            service.saveMail(sentMail);
        }
    }
    
    public static void ecrireFichier(File f,String chaine) {
        //String path = System.getProperty("user.home") + File.separator + ".smile" + File.separator + "logs" + File.separator + "import_log.txt";
        //File f = new File(path);
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(chaine);
            fw.write("\r\n");
            fw.close();
        } catch (IOException exception) {
            System.out.println("Erreur lors de la lecture : " + exception.getMessage());
        }
    }

    /*RemoteDB remoteDB = serviceManager.getDefaultRemoteDB(true);
     conn = DriverManager.
     getConnection(remoteDB.getUrl(),remoteDB.getName(),remoteDB.getPassword());
     int compteur = serviceManager.getMaxIndexBkEve();
     if(compteur == 0){
     PreparedStatement ps = conn.prepareStatement("SELECT max(b.EVE) AS EVE FROM bkeve b");
     ResultSet rs = ps.executeQuery();
     while(rs.next()){
     compteur = Integer.parseInt(rs.getString("EVE"));
     }
     }
     String query = "SELECT b.CLI1,b.ETA,b.NCP1,b.OPE,b.EVE,b.DVAB,b.MBOR,b.AGE,b.HSAI FROM bkeve b WHERE b.EVE > " + compteur + " AND (b.ETA='VA' OR b.ETA='VF')";
     PreparedStatement ps = conn.prepareStatement(query);
     ResultSet rs = ps.executeQuery();
     System.out.println("Exécution ......" + query);
     while(rs.next()){
     BkEve eve = new BkEve();
     BkAgence bkAgence = serviceManager.getBkAgenceById(rs.getString("AGE"));
     eve.setBkAgence(bkAgence);
     System.out.println(rs.getString("AGE"));
     BkCli bkCli = serviceManager.getBkCliById(rs.getString("CLI1"));
     eve.setCli(bkCli);
     eve.setCompte(rs.getString("NCP1"));
     eve.setEtat(rs.getString("ETA"));
     eve.setHsai(rs.getString("HSAI"));
     eve.setMont(Double.parseDouble(rs.getString("MBOR")));
     BkOpe bkOpe = serviceManager.getBkOpeById(rs.getString("OPE"));
     eve.setOpe(bkOpe);
     eve.setDVAB(rs.getString("DVAB"));
     eve.setSent(false);
     eve.setId(Integer.parseInt(rs.getString("EVE")));
     serviceManager.enregistrer(eve);
     System.out.println(eve.toString());
     }
     conn.close();*/
    /*BkTelCli bkTelCli = new BkTelCli();
     int nbr1 = serviceManager.count(bkTelCli),nbr2=0;
                                
     ps = conn.prepareStatement("SELECT COUNT(*) AS NBR FROM bktelcli b ");
     rs = ps.executeQuery();
     while(rs.next()){
     nbr2 = Integer.parseInt(rs.getString("NBR"));
     }
     ps = conn.prepareStatement("SELECT b.NUM, b.CLI, b.TYPE FROM bktelcli b WHERE b.CLI='" + rs.getString("CLI1") + "' AND b.TYPE='F'");
     rs = ps.executeQuery(); 
                                
     while(rs.next()){
     bkTelCli = new BkTelCli();
     if(bkCli.getPhone() == 0){
     if(rs.getString("TYPE").contentEquals("F")){
     bkCli.setPhone(Long.parseLong(rs.getString("NUM")));
     serviceManager.modifier(bkCli);
     bkTelCli.setPardefault(true);
     } 
     }
     bkTelCli.setBkCli(bkCli);
     bkTelCli.setNumTel(Long.parseLong(rs.getString("NUM")));
     bkTelCli.setTypeNum(rs.getString("TYPE"));
     serviceManager.enregistrer(bkTelCli);
     }*/
    /* DeleteBkEveDialog nouveau1 = null;
     nouveau1 = new DeleteBkEveDialog(parentPanel, serviceManager);
     nouveau1.setSize(450, 200);
     nouveau1.setLocationRelativeTo(null);
     nouveau1.setModal(true);
     nouveau1.setResizable(false);
     nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     nouveau1.setVisible(true);*/
    
    public static Date add(Date d, long a) {
        Date d2 = new Date(d.getTime() + a * (1000 * 60 * 60 * 24));
        return d2;
    }
    
     public static  Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String day(int i) {
        if (i == 2) {
            return "Lundi";
        } else if (i == 3) {
            return "Mardi";
        } else if (i == 4) {
            return "Mercredi";
        } else if (i == 5) {
            return "Jeudi";
        } else if (i == 6) {
            return "Vendredi";
        } else if (i == 7) {
            return "Samedi";
        } else if (i == 1) {
            return "Dimanche";
        }
        return "";
    }
    
   public static boolean exist(String lic,Connection conn) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("select * from licence where code=? and used=?");
        pstmt.setString(1, lic);
        pstmt.setBoolean(2, false);
        ResultSet rs = pstmt.executeQuery();
        return rs.first();
    }
   
   public static boolean existAndUsed(String lic,Connection conn) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("select * from licence where code=? and used=?");
        pstmt.setString(1, lic);
        pstmt.setBoolean(2, true);
        ResultSet rs = pstmt.executeQuery();
        return rs.first();
    }
   
    public static boolean updateLic(String lic, Connection conn) throws SQLException {
        System.out.println("Modification distante");
        PreparedStatement pstmt = conn.prepareStatement("update licence set used=? where code=?");
        pstmt.setString(2, lic);
        pstmt.setBoolean(1, true);
        int r = pstmt.executeUpdate();
        return r > 0;
    }
   
    public static Connection getConnection() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        try {
            conn = DriverManager.
                    getConnection("jdbc:mysql://mysql-eabouna.alwaysdata.net:3306/eabouna_lacus",
                            "eabouna", "Lebomo@1989!");
        } catch (SQLException ex) {
            return null;
        }
        return conn;
    }
    
    public static boolean createAppDirectory() throws IOException{
        String path = System.getenv("APPDATA") + File.separator + "lacus";
        File file = new File(path);
        boolean create = false;
        if(file.exists() == false){
            file.mkdir();
            String p = file.getAbsolutePath()+ File.separator + "app.txt";
            file = new File(p);
            file.createNewFile();
            ecrire("pid -1");
            create = true;
        }
        return create;
    }
    
    public static boolean verify() throws FileNotFoundException, IOException{
        String path = System.getenv("APPDATA") + File.separator + "lacus" + File.separator + "app.txt";
         File file = new File(path);
        boolean test = false;
        if(file.exists()){
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String text = reader.readLine();
            if(text.split(" ")[1].equals("1"))
                test = true;
        }
        return test;
    }
    
    public static void ecrire(String ch) throws IOException {
        String fileName = System.getenv("APPDATA") + File.separator + "lacus" + File.separator + "app.txt";
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(ch);
        printWriter.close();
        fileWriter.close();
    }
}
