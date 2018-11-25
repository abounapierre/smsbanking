/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.BkCli;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXSearchField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author SATELLITE
 */
public class UserPanel extends JPanel{
    private DefaultTableModel tableModel;
    private JTable table;
    private final JButton nouveau, modifier, supprimer;
    private final JButton filtre;
    @Autowired
    private  MainMenuPanel parentPanel;
    @Autowired
    private  LacusSmsService serviceManager;
    
    public UserPanel(MainMenuPanel parentFrame,LacusSmsService service) throws IOException{
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        parentPanel = ApplicationConfig.getApplicationContext().getBean(MainMenuPanel.class);
        setLayout(new BorderLayout());
        this.parentPanel = parentFrame;
        this.serviceManager = service;
        JPanel haut = new JPanel();
        haut.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        lbl = new JLabel("GESTION DES CLIENTS");
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
        nouveau.setToolTipText("Ajouter un nouveau client");
        supprimer = new JButton(new ImageIcon(supprImg));
        supprimer.setToolTipText("Suprimer un client");
        modifier = new JButton(new ImageIcon(modifImg));
        modifier.setToolTipText("Modifier un client");
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
                        nouveau1 = new Nouveau(serviceManager.getBkCliById(id));
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
                    int res = JOptionPane.showConfirmDialog(null, "Etes vous sûr de suppimer le client courant?", "Confirmation",
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
        bas.add(supprimer);
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
                        List<BkCli> bkcliList = serviceManager.getAllCli();
                        for (BkCli a : bkcliList) {
                            tableModel.addRow(new Object[]{
                                a.getCode(),
                                a.getLibelle(),
                                a.getNom(),
                                a.getPrenom(),
                                a.getPhone(),
                                a.getEmail(),
                                a.getLangue(),
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
        tableModel = new DefaultTableModel(new Object[]{"Code","Libellé", "Nom","Prénom","Téléphone","Email","Langue","Actif?"}, 0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        //table.getColumnModel().getColumn(2).setPreferredWidth(280);
        //table.removeColumn(table.getColumnModel().getColumn(0));
        contenu.add(BorderLayout.CENTER, new JScrollPane(table));
        add(BorderLayout.CENTER, contenu);
        try {
             for (BkCli a : service.getAllCli()) {
                            tableModel.addRow(new Object[]{
                                a.getCode(),
                                a.getLibelle(),
                                a.getNom(),
                                a.getPrenom(),
                                a.getPhone(),
                                a.getEmail(),
                                a.getLangue(),
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
        private final JTextField nameText;
        private final JTextField prenomText;
        private final JComboBox<String> libelleBox;
        private final JComboBox<String> langueBox;
        private final JTextField emailText;
        private final JTextField phoneText;
        private final JCheckBox chkBox;
        private int c = 0, rang =0;

        public Nouveau(final BkCli bkcli) {
            setTitle("NOUVEAU CLIENT");
            setModal(true);
            setLayout(new BorderLayout(10, 10));
            JPanel haut = new JPanel();
            JLabel lbl;
            haut.add(lbl = new JLabel("<html><font color = #012345 > AJOUT D'UN NOUVEAU CLIENT </font></html>"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 110dlu:", ""));
            builder.setDefaultDialogBorder();
            builder.append("Code", codeText = new JTextField(50));
            builder.append("Nom", nameText = new JTextField(50));
            builder.append("Prénom", prenomText = new JTextField(10));
            builder.append("Libellé", libelleBox = new JComboBox<String>());
            builder.append("Téléphone", phoneText = new JTextField(50));
            builder.append("Email", emailText = new JTextField(50));
            builder.append("Langue", langueBox = new JComboBox<String>());
            builder.append("Actif?", chkBox = new JCheckBox());
            libelleBox.addItem("Mr");
            libelleBox.addItem("Mme");
            libelleBox.addItem("Mlle");
            langueBox.addItem("Français");
            langueBox.addItem("Anglais");
           
            JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Enrégistrer"), annulerBtn = new JButton("Annuler"));
            builder.append(buttonBar, builder.getColumnCount());
            add(BorderLayout.CENTER, builder.getPanel());
            
            if (bkcli != null) {
               codeText.setText(bkcli.getCode());
               nameText.setText(bkcli.getNom());
               prenomText.setText(bkcli.getPrenom());
               emailText.setText(bkcli.getEmail());
               phoneText.setText(Long.toString(bkcli.getPhone()));
               codeText.setEditable(false);
               if(bkcli.getLangue().equals("FR"))
                   langueBox.setSelectedIndex(0);
               else if(bkcli.getLangue().equals("EN"))
                   langueBox.setSelectedIndex(1);
               if(bkcli.getLibelle().equals("Mr"))
                   libelleBox.setSelectedIndex(0);
               else if(bkcli.getLibelle().equals("Mme"))
                   libelleBox.setSelectedIndex(1);
               else if(bkcli.getLibelle().equals("Mlle"))
                   libelleBox.setSelectedIndex(2);
               if(bkcli.isEnabled())
                   chkBox.setSelected(true);
               else
                   chkBox.setSelected(false);
            }

            okBtn.addActionListener((ActionEvent ae) -> {
                BkCli a = new BkCli();
                if (!codeText.getText().equals("")) {
                    a.setCode(codeText.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
                }
                if (!nameText.getText().equals("")) {
                    a.setNom(nameText.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
                }
                if (!phoneText.getText().equals("")) {
                    a.setPhone(Long.parseLong(phoneText.getText()));
                } else {
                    JOptionPane.showMessageDialog(null, "Le téléphone est obligatoire");
                }
                a.setEnabled(false);
                if(chkBox.isSelected())
                    a.setEnabled(true);
                
                if(langueBox.getSelectedIndex() == 0)
                    a.setLangue("FR");
                else if(langueBox.getSelectedIndex() == 1)
                    a.setLangue("EN");
                
                a.setPrenom(prenomText.getText());
                a.setLibelle((String) libelleBox.getSelectedItem());
                a.setEmail(emailText.getText());
                
                if (bkcli == null) {
                    try {
                        serviceManager.enregistrer(a);
                    } catch (Exception ex) {
                        Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    a.setCode(bkcli.getCode());
                    try {
                        serviceManager.modifier(a);
                    } catch (Exception ex) {
                        Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                dispose();
                try {
                    parentPanel.setContenu(new BkCliPanel());
                } catch (IOException ex) {
                    Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            annulerBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    dispose();
                    try {
                        parentPanel.setContenu(new BkCliPanel());
                    } catch (IOException ex) {
                        Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
        }
    }
    
}
