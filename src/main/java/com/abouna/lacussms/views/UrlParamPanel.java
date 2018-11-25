/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.UrlMessage;
import com.abouna.lacussms.main.App;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.main.MainMenuPanel;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import org.jdesktop.swingx.JXTextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ABOUNA
 */
public class UrlParamPanel extends JPanel{
    private DefaultTableModel tableModel;
    private JTable table;
    private final JButton nouveau, modifier, supprimer;
    private final JButton filtre;
    private  MainMenuPanel parentPanel;
    private  LacusSmsService serviceManager;
    
    public UrlParamPanel() throws IOException{
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        parentPanel = ApplicationConfig.getApplicationContext().getBean(MainMenuPanel.class);
        setLayout(new BorderLayout());
        JPanel haut = new JPanel();
        haut.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        lbl = new JLabel("GESTION DES URLS DE MESSAGE");
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
        nouveau.setToolTipText("Ajouter une nouvelle url");
        supprimer = new JButton(new ImageIcon(supprImg));
        supprimer.setToolTipText("Suprimer une url");
        modifier = new JButton(new ImageIcon(modifImg));
        modifier.setToolTipText("Modifier une url");
        filtre = new JButton("Filtrer");
        nouveau.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Nouveau nouveau1 = new Nouveau(null);
                nouveau1.setSize(500, 250);
                nouveau1.setLocationRelativeTo(null);
                nouveau1.setModal(true);
                nouveau1.setResizable(false);
                nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nouveau1.setVisible(true);
            }
        });
        modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int selected = table.getSelectedRow();
                if (selected >= 0) {
                    Integer id = (Integer) tableModel.getValueAt(selected, 0);
                    Nouveau nouveau1 = null;
                    try {
                        nouveau1 = new Nouveau(serviceManager.getUrlMessageById(id));
                    } catch (Exception ex) {
                        Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    nouveau1.setSize(500, 250);
                    nouveau1.setLocationRelativeTo(null);
                    nouveau1.setModal(true);
                    nouveau1.setResizable(false);
                    nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    nouveau1.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun élément n'est selectionné");
                }
            }
        });
        supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int selected = table.getSelectedRow();
                if (selected >= 0) {
                    Integer id = (Integer) tableModel.getValueAt(selected, 0);
                    int res = JOptionPane.showConfirmDialog(null, "Etes vous sûr de suppimer l'url de message courante?", "Confirmation",
                            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (res == JOptionPane.YES_OPTION) {
                        try {
                            serviceManager.supprimerUrlMessage(id);
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
        JButton testBtn = new JButton("Test Connexion");
        testBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(App.testConnexion()){
                    JOptionPane.showMessageDialog(parentPanel, "Connexion réussie");
                }else{
                    JOptionPane.showMessageDialog(parentPanel, "Erreur lors de l'établissement de la connexion!");
                }
            }
        });
        bas.add(nouveau);
        bas.add(modifier);
        bas.add(supprimer);
        //bas.add(testBtn);
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
                        List<UrlMessage> urlMessageList = serviceManager.getAllUrlMessages();
                        for (UrlMessage a : urlMessageList) {
                            tableModel.addRow(new Object[]{
                               a.getId(),
                                a.getUrlValue(),
                                a.getMethode().equals("METHO1")?"Méthode 1":"Méthode 2",
                                a.isDefaultUrl()
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
        tableModel = new DefaultTableModel(new Object[]{"id", "Url","Methode","Par défaut?"}, 0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.getColumnModel().getColumn(1).setPreferredWidth(350);
        //table.removeColumn(table.getColumnModel().getColumn(0));
        contenu.add(BorderLayout.CENTER, new JScrollPane(table));
        add(BorderLayout.CENTER, contenu);
        try {
             for (UrlMessage a : serviceManager.getAllUrlMessages()) {
                            tableModel.addRow(new Object[]{
                                 a.getId(),
                                a.getUrlValue(),
                                a.getMethode().equals("METHO1")?"Méthode 1":"Méthode 2",
                                a.isDefaultUrl()
                            });
            }
        } catch (Exception ex) {
            Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class Nouveau extends JDialog {

        private final JButton okBtn, annulerBtn;
        private final JTextArea urlText;
        private final JCheckBox chkBox;
        private final JComboBox<String> methodeBox;
       
        private int c = 0, rang =0;

        public Nouveau(final UrlMessage url) {
            setTitle("NOUVELLE URL");
            setModal(true);
            setLayout(new BorderLayout(10, 10));
            JPanel haut = new JPanel();
            JLabel lbl;
            haut.add(lbl = new JLabel("<html><font color = #012345 > AJOUT D'UNE NOUVELLE URL POUR MESSAGE </font></html>"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:", ""));
            builder.setDefaultDialogBorder();
            builder.append("URL", urlText = new JTextArea(5, 20));
            builder.append("Methode d'envoie", methodeBox = new JComboBox<String>());
            builder.append("Par défaut?", chkBox = new JCheckBox());
            urlText.setSize(5, 20);
            urlText.setLineWrap(true);
            urlText.setWrapStyleWord(true);
            JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Enrégistrer"), annulerBtn = new JButton("Annuler"));
            builder.append(buttonBar, builder.getColumnCount());
            add(BorderLayout.CENTER, builder.getPanel());
            methodeBox.addItem("Méthode 1");
            methodeBox.addItem("Méthode 2");
            if (url != null) {
               String url1 = url.getUrlValue();
                        String[] tab = url1.split("=");
                        for(int i = 1;i<tab.length;i++){
                            String[] t2 = tab[i].split("&"); //URLEncoder.encode(t2[0], "ISO-8859-1")
                            if(!t2[0].contains("<")){
                                try {
                                    if(t2.length == 2)
                                        url1 = url1.replace(t2[0], URLDecoder.decode(t2[0], "UTF-8")).replace(t2[1], URLDecoder.decode(t2[1], "UTF-8"));
                                    else
                                        url1 = url1.replace(t2[0], URLDecoder.decode(t2[0], "UTF-8"));
                                     } catch (UnsupportedEncodingException ex) {
                                    Logger.getLogger(UrlParamPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
               urlText.setText(url1);
               if(url.isDefaultUrl())
                  chkBox.setSelected(true);
               else
                   chkBox.setSelected(false);
               
               if(url.getMethode()!=null){
                   if(url.getMethode().equals("METHO1"))
                   methodeBox.setSelectedIndex(0);
               else if(url.getMethode().equals("METHO2"))
                   methodeBox.setSelectedIndex(1);
               }
               
            }

            okBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    UrlMessage a = new UrlMessage();
                     
                    if (!urlText.getText().equals("")) {
                        String url = urlText.getText();
                        String[] tab = url.split("=");
                        for(int i = 1;i<tab.length;i++){
                            String[] t2 = tab[i].split("&"); //URLEncoder.encode(t2[0], "ISO-8859-1")
                            if(!t2[0].contains("<")){
                                try {
                                    if(t2.length == 2)
                                        url = url.replace(t2[0], URLEncoder.encode(t2[0], "UTF-8")).replace(t2[1], URLEncoder.encode(t2[1], "UTF-8"));
                                    else
                                        url = url.replace(t2[0], URLEncoder.encode(t2[0], "UTF-8"));
                                            } catch (UnsupportedEncodingException ex) {
                                    Logger.getLogger(UrlParamPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        a.setUrlValue(url);
                    } else {
                        JOptionPane.showMessageDialog(null, "L'url est obligatoire");
                        return;
                    }
                    
                    if(methodeBox.getSelectedItem().equals("Méthode 1"))
                        a.setMethode("METHO1");
                    else if(methodeBox.getSelectedItem().equals("Méthode 2"))
                        a.setMethode("METHO2");
                    
                    if(chkBox.isSelected())
                        a.setDefaultUrl(true);
                    else
                        a.setDefaultUrl(false);
                    
                    if(a.isDefaultUrl()){
                            for(UrlMessage r : serviceManager.getAllUrlMessages()){
                                r.setDefaultUrl(false);
                                serviceManager.modifier(r);
                            }
                        }
                    int i = 0;
                    String r = "";
                    String var = urlText.getText();
                    while (urlText.getText().charAt(i) != '?') {
                        r += urlText.getText().charAt(i);
                        var = var.replace(""+urlText.getText().charAt(i), "");
                        i++;
                    }
                    var = var.replace("?", "");
                    a.setRoot(r);
                    a.setParamString(var);
                    if (url == null) {
                        try {
                            serviceManager.enregistrer(a);
                        } catch (Exception ex) {
                            Logger.getLogger(RemoteDBPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        a.setId(url.getId());
                        try {
                            serviceManager.modifier(a);
                        } catch (Exception ex) {
                            Logger.getLogger(UrlParamPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    dispose();
                    try {
                        parentPanel.setContenu(new UrlParamPanel());
                    } catch (IOException ex) {
                        Logger.getLogger(UrlParamPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            annulerBtn.addActionListener((ActionEvent ae) -> {
                dispose();
                try {
                    parentPanel.setContenu(new UrlParamPanel());
                } catch (IOException ex) {
                    Logger.getLogger(UrlParamPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
}
