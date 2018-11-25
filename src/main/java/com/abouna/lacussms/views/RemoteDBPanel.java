/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.RemoteDB;
import com.abouna.lacussms.main.App;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.main.MainMenuPanel;
import com.abouna.lacussms.views.tools.AES;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXSearchField;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author SATELLITE
 */
public class RemoteDBPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private final JButton nouveau, modifier, supprimer;
    private final JButton filtre;
    private  MainMenuPanel parentPanel;
    private LacusSmsService serviceManager;

    public RemoteDBPanel() throws IOException {
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        parentPanel = ApplicationConfig.getApplicationContext().getBean(MainMenuPanel.class);
        setLayout(new BorderLayout());
        JPanel haut = new JPanel();
        haut.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        lbl = new JLabel("GESTION DES DB DISTANTES");
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
        nouveau.setToolTipText("Ajouter une nouvelle agence");
        supprimer = new JButton(new ImageIcon(supprImg));
        supprimer.setToolTipText("Suprimer une agence");
        modifier = new JButton(new ImageIcon(modifImg));
        modifier.setToolTipText("Modifier une agence");
        filtre = new JButton("Filtrer");
        nouveau.addActionListener((ActionEvent ae) -> {
            Nouveau nouveau1 = new Nouveau(null);
            nouveau1.setSize(500, 300);
            nouveau1.setLocationRelativeTo(null);
            nouveau1.setModal(true);
            nouveau1.setResizable(false);
            nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nouveau1.setVisible(true);
        });
        modifier.addActionListener((ActionEvent ae) -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                Integer id = (Integer) tableModel.getValueAt(selected, 0);
                Nouveau nouveau1 = null;
                try {
                    nouveau1 = new Nouveau(serviceManager.getRemoteDBById(id));
                } catch (Exception ex) {
                    Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        });
        supprimer.addActionListener((ActionEvent ae) -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                Integer id = (Integer) tableModel.getValueAt(selected, 0);
                int res = JOptionPane.showConfirmDialog(null, "Etes vous sûr de suppimer le client courant?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    try {
                        serviceManager.supprimerRemoteDB(id);
                    } catch (Exception ex) {
                        Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tableModel.removeRow(selected);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Aucun élément selectionné");
            }
        });
        JButton testBtn = new JButton("Test Connexion");
        testBtn.addActionListener((ActionEvent e) -> {
            if (App.testConnexion()) {
                JOptionPane.showMessageDialog(parentPanel, "Connexion réussie");
            } else {
                JOptionPane.showMessageDialog(parentPanel, "Erreur lors de l'établissement de la connexion!");
            }
        });
        bas.add(nouveau);
        bas.add(modifier);
        bas.add(supprimer);
        bas.add(testBtn);
        JPanel filtrePanel = new JPanel();
        filtrePanel.setLayout(new FlowLayout());
        final JXSearchField searchField = new JXSearchField("Rechercher");
        searchField.setPreferredSize(new Dimension(500, 50));
        filtrePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Zone de recherche"));
        filtrePanel.add(searchField);
        filtrePanel.setBackground(new Color(166, 202, 240));
        searchField.addActionListener((ActionEvent e) -> {
            String val = null;
            if (searchField.getText() != null) {
                try {
                    val = searchField.getText().toUpperCase();
                    tableModel.setNumRows(0);
                    List<RemoteDB> remoliList = serviceManager.getAllRemoteDB();
                    remoliList.stream().forEach((a) -> {
                        tableModel.addRow(new Object[]{
                            a.getId(),
                            a.getUrl(),
                            a.getHostName(),
                            a.getName()
                        });
                    });
                } catch (Exception ex) {
                    Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        contenu.add(BorderLayout.AFTER_LAST_LINE, bas);
        contenu.add(BorderLayout.BEFORE_FIRST_LINE, filtrePanel);
        tableModel = new DefaultTableModel(new Object[]{"id", "Url", "Hôte", "Nom"}, 0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        //table.getColumnModel().getColumn(2).setPreferredWidth(280);
        //table.removeColumn(table.getColumnModel().getColumn(0));
        contenu.add(BorderLayout.CENTER, new JScrollPane(table));
        add(BorderLayout.CENTER, contenu);
        try {
            serviceManager.getAllRemoteDB().stream().forEach((a) -> {
                tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getUrl(),
                    a.getHostName(),
                    a.getName()
                });
            });
        } catch (Exception ex) {
            Logger.getLogger(MessageFormatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class Nouveau extends JDialog {

        private final JButton okBtn, annulerBtn;
        private final JTextField nomText;
        private final JTextField urlText;
        private final JPasswordField passwordText;
        private final JTextField hostText;
        private final JCheckBox chkBox;
        private final JComboBox<String> driverBox;
        private Map map = new HashMap();

        private int c = 0, rang = 0;

        public Nouveau(final RemoteDB remoteDB) {
            setTitle("NOUVELLE AGENCE");
            setModal(true);
            setLayout(new BorderLayout(10, 10));
            JPanel haut = new JPanel();
            JLabel lbl;
            haut.add(lbl = new JLabel("<html><font color = #012345 > AJOUT D'UNE NOUVELLE BD </font></html>"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:", ""));
            builder.setDefaultDialogBorder();
             builder.append("SGBD", driverBox = new JComboBox<>());
             driverBox.addItem("");
            driverBox.addItem("Mysql");
            driverBox.addItem("Oracle");
            driverBox.addItem("H2DB");
            driverBox.addItem("PostgreSql");
            builder.append("URL", urlText = new JTextField(80));
            builder.append("Nom de l'hôte", hostText = new JTextField(30));
            builder.append("Nom utilisateur", nomText = new JTextField(50));
            builder.append("Mot de passe", passwordText = new JPasswordField(30));
            builder.append("Par défaut?", chkBox = new JCheckBox());
           
            urlText.setToolTipText("jdbc:oracle:thin:@localhost:1521:SID");

            JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Enrégistrer"), annulerBtn = new JButton("Annuler"));
            builder.append(buttonBar, builder.getColumnCount());
            add(BorderLayout.CENTER, builder.getPanel());

            if (remoteDB != null) {
                nomText.setText(remoteDB.getName());
                urlText.setText(remoteDB.getUrl());

                passwordText.setText(remoteDB.getPassword());
                hostText.setText(remoteDB.getHostName());
                if (remoteDB.isParDefault()) {
                    chkBox.setSelected(true);
                } else {
                    chkBox.setSelected(false);
                }
                switch (remoteDB.getDriverClassName()) {
                    case "com.mysql.jdbc.Driver":
                        driverBox.setSelectedIndex(0);
                        break;
                    case "oracle.jdbc.driver.OracleDriver":
                        driverBox.setSelectedIndex(1);
                        break;
                    case "org.h2.Driver":
                        driverBox.setSelectedIndex(2);
                        break;
                    case "org.postgresql.Driver":
                        driverBox.setSelectedIndex(3);
                        break;
                }
            }

            okBtn.addActionListener((ActionEvent ae) -> {
                RemoteDB a = new RemoteDB();
                if (!nomText.getText().equals("")) {
                    a.setName(nomText.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Le nom est obligatoire");
                    return;
                }
                if (!urlText.getText().equals("")) {
                    a.setUrl(urlText.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "L'url est obligatoire");
                    return;
                }
                if (passwordText.getPassword().length != 0) {
                    String passText = new String(passwordText.getPassword());
                    final String secretKey = "LACUS2017";
                    String originalString = passText;
                    String encryptedString = AES.encrypt(originalString, secretKey);
                    String decryptedString = AES.decrypt(encryptedString, secretKey);
                    a.setPassword(encryptedString);
                } else {
                    JOptionPane.showMessageDialog(null, "Le mot de passe est obligatoire");
                    return;
                }

                if (!hostText.getText().equals("")) {
                    a.setHostName(hostText.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Le nom de l'hote est obligatoire");
                    return;
                }

                if (chkBox.isSelected()) {
                    a.setParDefault(true);
                } else {
                    a.setParDefault(false);
                }

                if (a.isParDefault()) {
                    serviceManager.getAllRemoteDB().stream().map((r) -> {
                        r.setParDefault(false);
                        return r;
                    }).forEach((r) -> {
                        serviceManager.modifier(r);
                    });
                }

                String driver = null;
                if (driverBox.getSelectedIndex() == 0) {
                    driver = "com.mysql.jdbc.Driver";
                }

                if (driverBox.getSelectedIndex() == 1) {
                    driver = "oracle.jdbc.driver.OracleDriver";
                }

                if (driverBox.getSelectedIndex() == 2) {
                    driver = "org.h2.Driver";
                }

                if (driverBox.getSelectedIndex() == 3) {
                    driver = "org.postgresql.Driver";
                }

                a.setDriverClassName(driver);

                if (remoteDB == null) {
                    try {
                        serviceManager.enregistrer(a);
                    } catch (Exception ex) {
                        Logger.getLogger(RemoteDBPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    a.setId(remoteDB.getId());
                    try {
                        serviceManager.modifier(a);
                    } catch (Exception ex) {
                        Logger.getLogger(RemoteDBPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                dispose();
                try {
                    parentPanel.setContenu(new RemoteDBPanel());
                } catch (IOException ex) {
                    Logger.getLogger(RemoteDBPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            annulerBtn.addActionListener((ActionEvent ae) -> {
                dispose();
                try {
                    parentPanel.setContenu(new RemoteDBPanel());
                } catch (IOException ex) {
                    Logger.getLogger(RemoteDBPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
}
