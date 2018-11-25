/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.config.OracleConnect;
import com.abouna.lacussms.entities.MessageFormat;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TriggerEventCreationPanel extends JPanel{
    private DefaultTableModel tableModel;
    private JTable table;
    private final JButton nouveau, modifier, supprimer;
    private final JButton filtre;
    @Autowired
    private  MainMenuPanel parentPanel;
    @Autowired
    private  LacusSmsService serviceManager;
    
    public TriggerEventCreationPanel() throws IOException{
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        parentPanel = ApplicationConfig.getApplicationContext().getBean(MainMenuPanel.class);
     setLayout(new BorderLayout());
        JPanel haut = new JPanel();
        haut.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        lbl = new JLabel("GESTION DES FORMATS DE MASSAGE");
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
        nouveau.setToolTipText("Ajouter un nouveau format de massage");
        supprimer = new JButton(new ImageIcon(supprImg));
        supprimer.setToolTipText("Suprimer un format de message");
        modifier = new JButton(new ImageIcon(modifImg));
        modifier.setToolTipText("Modifier un format de message");
        filtre = new JButton("Filtrer");
        nouveau.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Nouveau nouveau1 = new Nouveau(null);
                nouveau1.setSize(400, 250);
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
                        nouveau1 = new Nouveau(serviceManager.getFormatById(id));
                    } catch (Exception ex) {
                        Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    nouveau1.setSize(500, 300);
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
                    int res = JOptionPane.showConfirmDialog(null, "Etes vous sûr de suppimer l'épargne courante?", "Confirmation",
                            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (res == JOptionPane.YES_OPTION) {
                        try {
                            serviceManager.supprimerFormat(id);
                        } catch (Exception ex) {
                            Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
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
                        List<MessageFormat> messages = serviceManager.getFormatByName(val);
                        for (MessageFormat a : messages) {
                            tableModel.addRow(new Object[]{
                                a.getId(),
                                a.getName(),
                                a.getContent()
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
        tableModel = new DefaultTableModel(new Object[]{"N° Ordre", "Nom de table"}, 0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.getColumnModel().getColumn(1).setPreferredWidth(280);
        //table.removeColumn(table.getColumnModel().getColumn(0));
        contenu.add(BorderLayout.CENTER, new JScrollPane(table));
        add(BorderLayout.CENTER, contenu);
        try {
            for (MessageFormat a : serviceManager.getAll()) {
                tableModel.addRow(new Object[]{
                     a.getId(),
                     a.getName()
                });

            }
        } catch (Exception ex) {
            Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class Nouveau extends JDialog {

        private final JButton okBtn, annulerBtn;
        private final JTextField nameText;

        public Nouveau(final MessageFormat messageFormat) {
            setTitle("NOUVEAU FORMAT DE MESSAGE");
            setModal(true);
            setLayout(new BorderLayout(10, 10));
            JPanel haut = new JPanel();
            JLabel lbl;
            haut.add(lbl = new JLabel("<html><font color = #012345 > AJOUT D'UN NOUVEAU FORMAT DE MESSAGE </font></html>"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 110dlu:", ""));
            builder.setDefaultDialogBorder();
            builder.append("Nom de la table", nameText = new JTextField(20));
            JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Enrégistrer"), annulerBtn = new JButton("Annuler"));
            builder.append(buttonBar, builder.getColumnCount());
            add(BorderLayout.CENTER, builder.getPanel());
            
            if (messageFormat != null) {
                nameText.setText(messageFormat.getName());
            }

            okBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    MessageFormat a = new MessageFormat();

                    if (!nameText.getText().equals("")) {
                        a.setName(nameText.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
                    }

                    if (messageFormat == null) {
                        try {
                            OracleConnect oracleConnect = new OracleConnect();
                            String query = "CREATE TRIGGER bt BEFORE UPDATE OR DELETE OR INSERT ON " + nameText.getText() + " BEGIN stat.rowcnt := 0; END;";
                            PreparedStatement ps = oracleConnect.getConnection().prepareStatement(query);
                            ps.execute();
                            oracleConnect.getConnection().close();
                            //serviceManager.enregistrer(a);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(TriggerEventCreationPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(TriggerEventCreationPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        a.setId(messageFormat.getId());
                        try {
                            serviceManager.modifier(a);
                        } catch (Exception ex) {
                            Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    dispose();
                    try {
                        parentPanel.setContenu(new MessageFormatPanel());
                    } catch (IOException ex) {
                        Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            annulerBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    dispose();
                    try {
                        parentPanel.setContenu(new MessageFormatPanel());
                    } catch (IOException ex) {
                        Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
        }
    }
}
