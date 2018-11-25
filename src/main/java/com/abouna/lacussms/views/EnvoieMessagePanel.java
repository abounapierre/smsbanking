/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.BkEve;
import com.abouna.lacussms.entities.Message;
import com.abouna.lacussms.entities.MessageFormat;
import com.abouna.lacussms.main.App;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.sms.SerialParameters;
import com.abouna.lacussms.sms.SmsFinal;
import com.abouna.lacussms.views.main.MainMenuPanel;
import com.abouna.lacussms.views.tools.Utils;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.SerialPort;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXSearchField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author SATELLITE
 */
public class EnvoieMessagePanel extends JPanel{
    private DefaultTableModel tableModel;
    private JTable table;
    private final JButton nouveau, modifier, supprimer;
    private final JButton filtre;
    @Autowired
    private  MainMenuPanel parentPanel;
    @Autowired
    private  LacusSmsService serviceManager;
    
    public EnvoieMessagePanel() throws IOException{
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        parentPanel = ApplicationConfig.getApplicationContext().getBean(MainMenuPanel.class);
        setLayout(new BorderLayout());
        JPanel haut = new JPanel();
        haut.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        lbl = new JLabel("GESTION DES EVENEMENTS");
        haut.add(lbl);
        lbl.setFont(new Font("Broadway", Font.BOLD, 30));
        add(BorderLayout.BEFORE_FIRST_LINE, haut);
        JPanel contenu = new JPanel();
        contenu.setLayout(new BorderLayout());
        JPanel bas = new JPanel();
        bas.setLayout(new FlowLayout());
        Image ajouImg = ImageIO.read(getClass().getResource("/images/Ajouter.png"));
        Image supprImg = ImageIO.read(getClass().getResource("/images/Cancel2.png"));
        Image modifImg = ImageIO.read(getClass().getResource("/images/OK.png"));
        nouveau = new JButton(new ImageIcon(ajouImg));
        nouveau.setText("Envoyer SMS");
        nouveau.setToolTipText("Ajouter un nouveau message");
        supprimer = new JButton(new ImageIcon(supprImg));
        supprimer.setToolTipText("Suprimer un evenement");
        modifier = new JButton("Push");
        modifier.setToolTipText("Modifier un evenement");
        filtre = new JButton("Filtrer");
        modifier.addActionListener((ActionEvent ae) -> {
            EnvoieDialog nouveau1 = new EnvoieDialog();
            nouveau1.setSize(500, 400);
            nouveau1.setLocationRelativeTo(null);
            nouveau1.setModal(true);
            nouveau1.setResizable(false);
            nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nouveau1.setVisible(true);
        });
        nouveau.addActionListener((ActionEvent ae) -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                Integer id = (Integer) tableModel.getValueAt(selected, 0);
                Nouveau nouveau1 = null;
                try {
                    nouveau1 = new Nouveau(serviceManager.getBkEveById(id),null);
                } catch (Exception ex) {
                    Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                nouveau1.setSize(400, 300);
                nouveau1.setLocationRelativeTo(null);
                nouveau1.setModal(true);
                nouveau1.setResizable(false);
                nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nouveau1.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Aucun élément n'est selectionné");
            }
        });
        supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int selected = table.getSelectedRow();
                if (selected >= 0) {
                    String id = (String) tableModel.getValueAt(selected, 0);
                    int res = JOptionPane.showConfirmDialog(null, "Etes vous sûr de suppimer l'évenement courant?", "Confirmation",
                            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (res == JOptionPane.YES_OPTION) {
                        try {
                            serviceManager.supprimerBkCli(id);
                        } catch (Exception ex) {
                            Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tableModel.removeRow(selected);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun élément selectionné");
                }
            }
        });
        bas.add(nouveau);
        bas.add(modifier);
        //bas.add(supprimer);
        JPanel filtrePanel = new JPanel();
        filtrePanel.setLayout(new FlowLayout());
        final JXSearchField searchField = new JXSearchField("Rechercher");
        searchField.setPreferredSize(new Dimension(500, 50));
        filtrePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Zone de recherche"));
        filtrePanel.add(searchField);
         filtrePanel.setBackground(new Color(166, 202, 240));
        searchField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String val = null;
                if (searchField.getText() != null) {
                    try {
                        val = searchField.getText().toUpperCase();
                        tableModel.setNumRows(0);
                        List<BkEve> bkeveList = serviceManager.getBkEveByName(val);
                        for (BkEve a : bkeveList) {
                            tableModel.addRow(new Object[]{
                               a.getId(),
                                a.getCli().getNom() + " " + a.getCli().getPrenom(),
                                a.getOpe().getLib(),
                                a.getCompte(),
                                a.getEtat()
                            });

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        contenu.add(BorderLayout.AFTER_LAST_LINE, bas);
        contenu.add(BorderLayout.BEFORE_FIRST_LINE, filtrePanel);
        tableModel = new DefaultTableModel(new Object[]{"Code","Client", "Opération","Compte","Etat"}, 0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        //table.getColumnModel().getColumn(2).setPreferredWidth(280);
        //table.removeColumn(table.getColumnModel().getColumn(0));
        contenu.add(BorderLayout.CENTER, new JScrollPane(table));
        add(BorderLayout.CENTER, contenu);
        try {
             for (BkEve a : serviceManager.getAllBkEves()) {
                            tableModel.addRow(new Object[]{
                                a.getId(),
                                a.getCli().getNom() + " " + a.getCli().getPrenom(),
                                a.getOpe().getLib(),
                                a.getCompte(),
                                a.getEtat()
                            });
            }
        } catch (Exception ex) {
            Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class Nouveau extends JDialog {

        private final JButton okBtn, annulerBtn;
        private final JTextField nameText;
        private final JTextArea contentText;
        private int c = 0, rang =0,c1 = 0, rang1 =0;

        public Nouveau(final BkEve bkeve,final Message message) {
            setTitle("ENVOIE DE MESSAGE");
            setModal(true);
            setLayout(new BorderLayout(10, 10));
            JPanel haut = new JPanel();
            JLabel lbl;
            haut.add(lbl = new JLabel("<html><font color = #012345 > ENVOIE DE MESSAGE </font></html>"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 110dlu:", ""));
            builder.setDefaultDialogBorder();
            builder.append("Nom", nameText = new JTextField(30));
            builder.append("Contenu de message", contentText = new JTextArea(5, 20));
            contentText.setLineWrap(true);
            contentText.setWrapStyleWord(true);
            JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Envoyer"), annulerBtn = new JButton("Annuler"));
            builder.append(buttonBar, builder.getColumnCount());
            add(BorderLayout.CENTER, builder.getPanel());
            
            if(bkeve != null){
                MessageFormat mf = new MessageFormat();
                mf = serviceManager.getFormatByBkOpe(bkeve.getOpe(),bkeve.getCli().getLangue());
                String text = Utils.remplacerVariable(bkeve.getCli(), bkeve.getOpe(), bkeve, mf);
                nameText.setText(bkeve.getOpe().getLib());
                contentText.setText(text);
            }
            
            okBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    Message a = new Message();
                     if (!contentText.getText().equals("")) {
                        a.setContent(contentText.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Le contenu est obligatoire");
                        return;
                    }
                    if (!nameText.getText().equals("")) {
                        a.setTitle(nameText.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Le titre est obligatoire");
                        return;
                    }
                    a.setBkEve(bkeve);
                    a.setSendDate(new Date());
                    
                    
                    if (message == null) {
                        try {
                            SerialParameters params = new SerialParameters();
                            params.setPortName("COM6"); // default COM1
                            params.setBaudRate(115200); // default 115200
                            params.setFlowControlIn(SerialPort.FLOWCONTROL_NONE); // default none flowcontrol
                            params.setFlowControlOut(SerialPort.FLOWCONTROL_NONE); // default none flowcontrol
                            params.setDatabits(SerialPort.DATABITS_8); // default data bits 8
                            params.setStopbits(SerialPort.STOPBITS_1); // default stop bits 1
                            params.setParity(SerialPort.PARITY_NONE); // default none parity bits 1

                            // object sms client
                            SmsFinal sms = new SmsFinal(params);
                            serviceManager.enregistrer(a);
                        } catch (Exception ex) {
                            Logger.getLogger(EnvoieMessagePanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } 
                    dispose();
                    try {
                        parentPanel.setContenu(new BkEvePanel());
                    } catch (IOException ex) {
                        Logger.getLogger(EnvoieMessagePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            annulerBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    dispose();
                    try {
                        parentPanel.setContenu(new EnvoieMessagePanel());
                    } catch (IOException ex) {
                        Logger.getLogger(EnvoieMessagePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
        }
    }
    
    
    private class EnvoieDialog extends JDialog {

        private final JButton okBtn, annulerBtn;
        private final JTextField nameText;
        private final JTextArea contentText,numerosText;
        private int c = 0, rang =0,c1 = 0, rang1 =0;

        public EnvoieDialog() {
            setTitle("ENVOIE DE MESSAGE PUSH");
            setModal(true);
            setLayout(new BorderLayout(10, 10));
            JPanel haut = new JPanel();
            JLabel lbl;
            haut.add(lbl = new JLabel("<html><font color = #012345 > ENVOIE DE MESSAGE PUSH </font></html>"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:", ""));
            builder.setDefaultDialogBorder();
            builder.append("Nom", nameText = new JTextField(30));
            builder.append("Contenu de message", contentText = new JTextArea(5, 50));
            builder.append("Liste des numéros", numerosText = new JTextArea(5, 50));
            contentText.setLineWrap(true);
            contentText.setWrapStyleWord(true);
            JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Envoyer"), annulerBtn = new JButton("Annuler"));
            builder.append(buttonBar, builder.getColumnCount());
            add(BorderLayout.CENTER, builder.getPanel());
            
            
            okBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    Message a = new Message();
                     if (!contentText.getText().equals("")) {
                        a.setContent(contentText.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Le contenu est obligatoire");
                        return;
                    }
                    if (!nameText.getText().equals("")) {
                        a.setTitle(nameText.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Le titre est obligatoire");
                        return;
                    }
                    String text = "";
                    if (!numerosText.getText().equals("")) {
                        text = numerosText.getText();
                    } else {
                        JOptionPane.showMessageDialog(null, "Le numero est obligatoire");
                        return;
                    }
                    
                    a.setSendDate(new Date());
                    
                    String[] tab = numerosText.getText().split(",");
                    String urlParam = serviceManager.getDefaultUrlMessage().getUrlValue();
                    for (String tab1 : tab) {
                        Message mes = new Message();
                        mes.setTitle(nameText.getText());
                        mes.setContent(contentText.getText());
                        mes.setSendDate(new Date());
                        if (tab1.length() == 9) {
                            App.send(urlParam, "237" + tab1, contentText.getText());
                        } else if (tab1.length() == 9) {
                            App.send(urlParam, "241" + tab1, contentText.getText());
                        } else {
                            App.send(urlParam, tab1, contentText.getText());
                        }
                    }
                    dispose();
                    try {
                        parentPanel.setContenu(new EnvoieMessagePanel());
                    } catch (IOException ex) {
                        Logger.getLogger(EnvoieMessagePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            annulerBtn.addActionListener((ActionEvent ae) -> {
                dispose();
                try {
                    parentPanel.setContenu(new EnvoieMessagePanel());
                } catch (IOException ex) {
                    Logger.getLogger(EnvoieMessagePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
    
    public boolean envoieSMS(String numero,String numc,String message){
                
                
            return false;
        }
}
