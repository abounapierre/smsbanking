/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.BkCli;
import com.abouna.lacussms.entities.BkCompCli;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.main.MainMenuPanel;
import com.abouna.lacussms.views.tools.XlsGenerator;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXSearchField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author SATELLITE
 */

public class BkCompCliPanel extends JPanel{
    private DefaultTableModel tableModel;
    private JTable table;
    private final JButton nouveau, modifier, supprimer,exportBtn;
    private final JButton filtre;
    @Autowired
    private  MainMenuPanel parentPanel;
    @Autowired
    private  LacusSmsService serviceManager;
    private final JFileChooser fc = new JFileChooser();
    
    public BkCompCliPanel() throws IOException{
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        setLayout(new BorderLayout());
        JPanel haut = new JPanel();
        haut.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        lbl = new JLabel("GESTION DES COMPTES CLIENTS");
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
        Image excelImg = ImageIO.read(getClass().getResource("/images/excel.PNG"));
        exportBtn = new JButton(new ImageIcon(excelImg));
        nouveau = new JButton(new ImageIcon(ajouImg));
        exportBtn.setToolTipText("Exporter la liste des comptes Excel");
        nouveau.setToolTipText("Ajouter un nouveau compte client");
        supprimer = new JButton(new ImageIcon(supprImg));
        supprimer.setToolTipText("Suprimer un compte client");
        modifier = new JButton(new ImageIcon(modifImg));
        modifier.setToolTipText("Modifier un compte client");
        filtre = new JButton("Filtrer");
        nouveau.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Nouveau nouveau1 = new Nouveau(null);
                nouveau1.setSize(400, 400);
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
                    String id = (String) tableModel.getValueAt(selected, 0);
                    Nouveau nouveau1 = null;
                    try {
                        nouveau1 = new Nouveau(serviceManager.getBkCompCliById(id));
                    } catch (Exception ex) {
                        Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    nouveau1.setSize(400, 400);
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
                    String id = (String) tableModel.getValueAt(selected, 0);
                    int res = JOptionPane.showConfirmDialog(null, "Etes vous sûr de suppimer le compte client courant?", "Confirmation",
                            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (res == JOptionPane.YES_OPTION) {
                        try {
                            serviceManager.supprimerBkCompCli(id);
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
        
        exportBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                int val_retour = fc.showSaveDialog(BkCompCliPanel.this);
                if (val_retour == JFileChooser.APPROVE_OPTION) {
                    File fichier = fc.getSelectedFile();
                    final String path = fichier.getAbsolutePath() + ".xls";
                    Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            List<BkCompCli> bkCompClis = serviceManager.getAllBkCompClis();
                            XlsGenerator xlsGenerator = new XlsGenerator(bkCompClis, path);
                        }
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
                                    JOptionPane.showMessageDialog(BkCompCliPanel.this, "Ce type de fichier n'est pas pris en charge");
                                    System.out.println("Awt Desktop is not supported!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(BkCompCliPanel.this, "Ce fichier n'existe pas");
                                System.out.println("File is not exists!");
                            }
                        } catch (IOException ex) {
                        } catch (HeadlessException ex) {
                        }
                    }
                }
            }
        });
        bas.add(nouveau);
        bas.add(modifier);
        bas.add(supprimer);
        bas.add(exportBtn);
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
                        List<BkCompCli> bkCompCliList = serviceManager.getBkCompCliByCriteria(val);
                        for (BkCompCli a : bkCompCliList) {
                            tableModel.addRow(new Object[]{
                                a.getNumc(),
                                a.getCli()==null?"":a.getCli().getNom() + " " + a.getCli().getPrenom(),
                                a.isEnabled()
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
        tableModel = new DefaultTableModel(new Object[]{"Num Compte","Client","Actif?"}, 0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        //table.getColumnModel().getColumn(2).setPreferredWidth(280);
        //table.removeColumn(table.getColumnModel().getColumn(0));
        contenu.add(BorderLayout.CENTER, new JScrollPane(table));
        add(BorderLayout.CENTER, contenu);
        try {
             for (BkCompCli a : serviceManager.getBkCompCliLimit(100)) {
                            tableModel.addRow(new Object[]{
                                a.getNumc(),
                                a.getCli()==null?"":a.getCli().getNom() + " " + a.getCli().getPrenom(),
                                a.isEnabled()
                            });

                        }
        } catch (Exception ex) {
            Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class Nouveau extends JDialog {

        private final JButton okBtn, annulerBtn;
        private final JTextField codeText;
        private final JComboBox<BkCli> clientBox; 
        private final JCheckBox chkBox;
        private int c = 0, rang =0;

        public Nouveau(final BkCompCli bkCompCli) {
            setTitle("NOUVEAU COMPTE CLIENT");
            setModal(true);
            setLayout(new BorderLayout(10, 10));
            JPanel haut = new JPanel();
            JLabel lbl;
            haut.add(lbl = new JLabel("<html><font color = #012345 > AJOUT D'UN NOUVEAU COMPTE CLIENT </font></html>"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 110dlu:", ""));
            builder.setDefaultDialogBorder();
            builder.append("Num de compte", codeText = new JTextField(50));
            builder.append("Client", clientBox = new JComboBox<BkCli>());
            builder.append("Actif?", chkBox = new JCheckBox());
            
            for(BkCli bkCli : serviceManager.getAllCli()){
                clientBox.addItem(bkCli);
                if(bkCompCli != null){
                   if(bkCompCli.getCli().getCode().equals(bkCli.getCode())){
                    rang = c;
                } 
                }
                c++;
            }
           
            JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Enrégistrer"), annulerBtn = new JButton("Annuler"));
            builder.append(buttonBar, builder.getColumnCount());
            add(BorderLayout.CENTER, builder.getPanel());
            
            if (bkCompCli != null) {
               codeText.setText(bkCompCli.getNumc());
               codeText.setEditable(false);
               clientBox.setSelectedIndex(0);
               
               if(bkCompCli.isEnabled())
                   chkBox.setSelected(true);
               else
                   chkBox.setSelected(false);
            }

            okBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    BkCompCli a = new BkCompCli();
                     if (!codeText.getText().equals("")) {
                        a.setNumc(codeText.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Le num compte est obligatoire");
                    }
                    
                    a.setEnabled(false);
                    if(chkBox.isSelected())
                        a.setEnabled(true);
                    
                    a.setCli((BkCli) clientBox.getSelectedItem());
                    
                    if (bkCompCli == null) {
                        try {
                            serviceManager.enregistrer(a);
                        } catch (Exception ex) {
                            Logger.getLogger(BkCompCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        a.setNumc(bkCompCli.getNumc());
                        try {
                            serviceManager.modifier(a);
                        } catch (Exception ex) {
                            Logger.getLogger(BkCompCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    dispose();
                    try {
                        parentPanel.setContenu(new BkCompCliPanel());
                    } catch (IOException ex) {
                        Logger.getLogger(BkCompCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            annulerBtn.addActionListener((ActionEvent ae) -> {
                dispose();
                try {
                    parentPanel.setContenu(new BkCompCliPanel());
                } catch (IOException ex) {
                    Logger.getLogger(BkCompCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
}
