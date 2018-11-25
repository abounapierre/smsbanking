/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views.main;


import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.BkCompCli;
import com.abouna.lacussms.entities.Config;
import com.abouna.lacussms.main.App;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.tools.Utils;
import com.abouna.lacussms.views.tools.XlsGenerator;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrateur
 */
public class HeaderMenu extends JMenuBar {

    private JMenu fichier, configuration, profil, rapports,employe, contact,service;
    private JMenuItem quitter,restart, langue, copier, couper, coller, typeSMS, formatSMS, paramBD, paramTbl,initialiser;
    private JMenuItem  licence, user, typeRapport, rapport,voirContact,importFichierClient;
    private final LacusSmsService serviceManager;
    private JMenuItem exportFichierClient;
    private JCheckBoxMenuItem bkmac,bkmad,bkmpai,event;
    private JFileChooser fc = new JFileChooser();
    private Config config ;
             
    public HeaderMenu() {
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        initComponent();
    }

    protected final void initComponent() {
        fichier = new JMenu("Fichier");
        configuration = new JMenu("Configuration");
        profil = new JMenu("Profil");
        service = new JMenu("Services");
        rapports = new JMenu("Rapports");
        contact = new JMenu("Contacts");
        copier = new JMenuItem("Copier");
        couper = new JMenuItem("Couper");
        coller = new JMenuItem("Coller");
        quitter = new JMenuItem("Quitter");
        restart = new JMenuItem("Redémarrer");
        typeSMS = new JMenuItem("Type SMS");
        formatSMS = new JMenuItem("Format SMS");
        paramBD = new JMenuItem("BD Distante");
        paramTbl = new JMenuItem("Tables Distance");
        employe = new JMenu("Clients");
        licence = new JMenuItem("Licence");
        user = new JMenuItem("utilisateur");
        typeRapport = new JMenuItem("Type de Rapports");
        rapport = new JMenuItem("Fichier de Banque");
        voirContact = new JMenuItem("Afficher");
        importFichierClient = new JMenuItem("Import excel");
        exportFichierClient = new JMenuItem("Export excel");
        event = new JCheckBoxMenuItem("Evenement");
        bkmac = new JCheckBoxMenuItem("Crédit");
        bkmad = new JCheckBoxMenuItem("Mandat");
        bkmpai = new JCheckBoxMenuItem("Salaire");
        
        initialiser = new JMenuItem("Initialiser les données");
        fichier.add(copier);
        fichier.add(couper);
        fichier.add(coller);
        fichier.add(quitter);
        fichier.add(restart);
        configuration.add(initialiser);
        configuration.add(typeSMS);
        configuration.add(formatSMS);
        configuration.add(paramBD);
        configuration.add(paramTbl);
        configuration.add(service);
        config = !serviceManager.getAllConfig().isEmpty()?serviceManager.getAllConfig().get(0):null;
        event.setSelected(config==null?false:config.isEvent());
        bkmac.setSelected(config==null?false:config.isBkmac());
        bkmad.setSelected(config==null?false:config.isMandat());
        bkmpai.setSelected(config==null?false:config.isBkmpai());
        System.out.println(config.toString());
        event.addActionListener((ActionEvent e) -> {
            if(event.isSelected()){
                config.setEvent(true);
                serviceManager.modifierConfig(config);
            }else{
                config.setEvent(false);
                serviceManager.modifierConfig(config);
            }
        });
        bkmac.addActionListener((ActionEvent e) -> {
            if(bkmac.isSelected()){
                config.setBkmac(true);
                serviceManager.modifierConfig(config);
            }else{
                config.setBkmac(false);
                serviceManager.modifierConfig(config);
            }
        });
        bkmad.addActionListener((ActionEvent e) -> {
            if(bkmad.isSelected()){
                config.setMandat(true);
                serviceManager.modifierConfig(config);
            }else{
                config.setMandat(false);
                serviceManager.modifierConfig(config);
            }
        });
        bkmpai.addActionListener((ActionEvent e) -> {
            if(bkmpai.isSelected()){
                config.setBkmpai(true);
                serviceManager.modifierConfig(config);
            }else{
                config.setBkmpai(false);
                serviceManager.modifierConfig(config);
            }
        });
        service.add(event);
        service.add(bkmac);
        service.add(bkmad);
        service.add(bkmpai);
        employe.add(importFichierClient);
        employe.add(exportFichierClient);        
        configuration.add(employe);
        profil.add(licence);
        profil.add(user);
        //rapports.add(typeRapport);
        rapports.add(rapport);
        contact.add(voirContact);
        
        
        
        initialiser.addActionListener((ActionEvent e) -> {
        });
        
        quitter.addActionListener((ActionEvent e) -> {
            int response = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter cette application?", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                Window window = SwingUtilities.windowForComponent(HeaderMenu.this);
                if (window instanceof JFrame) {
                    JFrame frame = (JFrame) window;
                    frame.setVisible(false);
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
        
         restart.addActionListener((ActionEvent e) -> {
             int response = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment redémarrer cette application?", "Confirmation",
                     JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
             if (response == JOptionPane.YES_OPTION) {
                 Window window = SwingUtilities.windowForComponent(HeaderMenu.this);
                 if (window instanceof JFrame) {
                     JFrame frame = (JFrame) window;
                     frame.setVisible(false);
                     frame.dispose();
                 }
                 try {
                     App app = new App();
                     App.initApp();
                 } catch (IOException ex) {
                     Logger.getLogger(HeaderMenu.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (ClassNotFoundException ex) {
                     Logger.getLogger(HeaderMenu.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
        });
        
        voirContact.addActionListener((ActionEvent e) -> {
            String msg = "";/*"ABOUNA PIERRE EMMANUEL Ingénieur de conception en Iformatique\n"
            + " Option Génie Logiciel Tel: 698984176\n" +
            "Email: abouna.emmanuel@yahoo.fr";*/
            JOptionPane.showMessageDialog(null,msg);
        });
        
        importFichierClient.addActionListener((ActionEvent e) -> {
            try {
                File repertoireCourant = null;
                repertoireCourant = new File(".").getCanonicalFile();
                System.out.println("Répertoire courant : " + repertoireCourant);
                JFileChooser dialogue = new JFileChooser(repertoireCourant);
                // affichage
                dialogue.showOpenDialog(null);
                // récupération du fichier sélectionné
                if (dialogue.getSelectedFile() != null) {
                    System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
                    final String chemein = dialogue.getSelectedFile().getPath();
                    final String extension = chemein.substring(chemein.lastIndexOf("."));
                    System.out.println("L'extension est: " + extension);
                    if (extension.equals(".xls") || extension.equals(".xlsx")) {
                        Thread t = new Thread(() -> {
                            String result = Utils.importExcel(chemein, extension, serviceManager);
                            if (result.contentEquals("OK")) {
                                JOptionPane.showMessageDialog(null, "Importation réussie avec success!!", "OK", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Importation du fichier terminée avec les erreurs!!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        t.start();
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Fichier non valide");
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(HeaderMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        exportFichierClient.addActionListener((ActionEvent e) -> {
            int val_retour = fc.showSaveDialog(employe);
            if (val_retour == JFileChooser.APPROVE_OPTION) {
                File fichier = fc.getSelectedFile();
                final String path = fichier.getAbsolutePath() + ".xls";
                Thread t = new Thread(() -> {
                    List<BkCompCli> bkCompClis = serviceManager.getAllBkCompClis();
                    XlsGenerator xlsGenerator = new XlsGenerator(bkCompClis, path);
                });
                t.start();
                int response = JOptionPane.showConfirmDialog(null, "<html>Rapport généré avec success!!<br>Voulez vous l'ouvrir?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    try {
                        File pdfFile = new File(path);
                        if (pdfFile.exists()) {
                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().open(pdfFile);
                            } else {
                                JOptionPane.showMessageDialog(null,"Ce type de fichier n'est pas pris en charge");
                                System.out.println("Awt Desktop is not supported!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Ce fichier n'existe pas");
                            System.out.println("File is not exists!");
                        }
                    } catch (IOException ex) {
                    } catch (HeadlessException ex) {
                    }
                }
            }
        });
        
        rapport.addActionListener((ActionEvent e) -> {
            CbsFileDialog nouveau1 = null;
            try {
                nouveau1 = new CbsFileDialog();
                nouveau1.setSize(375, 200);
                nouveau1.setLocationRelativeTo(null);
                nouveau1.setModal(true);
                nouveau1.setResizable(false);
                nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nouveau1.setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(HeaderMenu.class.getName()).log(Level.SEVERE, null, ex);
            }    
        });
        
        add(fichier);
        add(configuration);
        add(profil);
        add(rapports);
        add(contact);
    }

}
