/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views.main;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.Command;
import com.abouna.lacussms.entities.CutOff;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.tools.BankFilePrinting;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author SATELLITE
 */
public class CbsFileDialog extends JDialog {

    private final JButton okBtn, annulerBtn;
    private final JComboBox<CutOff> cutOffBox;
    private final JFileChooser fc = new JFileChooser();
    @Autowired
    private final LacusSmsService serviceManager;

    public CbsFileDialog() throws ParseException {
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        setTitle("GENERER FICHIER POUR LA BANQUE");
        setModal(true);
        setLayout(new BorderLayout(10, 10));
        JPanel haut = new JPanel();
        JLabel lbl;
        haut.add(lbl = new JLabel("<html><font color = #012345 > GENERER FICHIER POUR LA BANQUE </font></html>"));
        lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        add(BorderLayout.BEFORE_FIRST_LINE, haut);
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 110dlu:", ""));
        builder.setDefaultDialogBorder();
        builder.append("Choisir date",cutOffBox = new JComboBox<>());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date d = format.parse(format.format(new Date()));
        CutOff cutOff = serviceManager.getLastCutOff(d);
        if(cutOff != null){
            cutOffBox.addItem(cutOff);
        }else{
            cutOff = new CutOff();
            cutOff.setCodeDate("");
            cutOff.setCutDate(new Date());
            cutOffBox.addItem(cutOff);
        }
        JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Imprimer"), annulerBtn = new JButton("Annuler"));
        builder.append(buttonBar, builder.getColumnCount());
        add(BorderLayout.CENTER, builder.getPanel());

        okBtn.addActionListener((ActionEvent ae) -> {
            Date d1 = null, d2 = ((CutOff)cutOffBox.getSelectedItem()).getCutDate();
            
            int val_retour = fc.showSaveDialog(CbsFileDialog.this);
                if (val_retour == JFileChooser.APPROVE_OPTION) {
                    File fichier = fc.getSelectedFile();
                    String path = fichier.getAbsolutePath() + "125455" + ".txt";
                    System.out.println(path);
                    List<Command> commands = serviceManager.getAllCommands(d2);
                    BankFilePrinting report = new BankFilePrinting(path, commands);
                    int response = JOptionPane.showConfirmDialog(null, "<html>Rapport généré avec success!!<br>Voulez vous l'ouvrir?", "Confirmation",
                            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                    }
                }
            dispose();
        });

        annulerBtn.addActionListener((ActionEvent ae) -> {
            dispose();
        });
    }

}
