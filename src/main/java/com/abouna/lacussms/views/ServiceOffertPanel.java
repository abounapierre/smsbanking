/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.ServiceOffert;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.main.MainMenuPanel;
import com.abouna.lacussms.views.tools.MethodUtils;
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
public class ServiceOffertPanel extends JPanel {
    @Autowired
    private LacusSmsService serviceManager;
    private DefaultTableModel tableModel;
    private JTable table;
    private final JButton nouveau, modifier, supprimer;
    private final JButton filtre;
    @Autowired
    private  MainMenuPanel parentPanel;

    public ServiceOffertPanel() throws IOException {
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        parentPanel = ApplicationConfig.getApplicationContext().getBean(MainMenuPanel.class);
        setLayout(new BorderLayout());
        JPanel haut = new JPanel();
        haut.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl;
        lbl = new JLabel("CONFIGURATION DES SERVICES");
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
        nouveau.setToolTipText("Ajouter un nouveau service");
        supprimer = new JButton(new ImageIcon(supprImg));
        supprimer.setToolTipText("Suprimer un service");
        modifier = new JButton(new ImageIcon(modifImg));
        modifier.setToolTipText("Modifier un service");
        filtre = new JButton("Filtrer");
        nouveau.addActionListener((ActionEvent ae) -> {
            Nouveau nouveau1 = new Nouveau(null);
            nouveau1.setSize(400, 300);
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
                    nouveau1 = new Nouveau(serviceManager.getServiceById(id));
                    nouveau1.setSize(400, 300);
                    nouveau1.setLocationRelativeTo(null);
                    nouveau1.setModal(true);
                    nouveau1.setResizable(false);
                    nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    nouveau1.setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Aucun élément n'est selectionné");
            }
        });
        supprimer.addActionListener((ActionEvent ae) -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                Integer id = (Integer) tableModel.getValueAt(selected, 0);
                int res = JOptionPane.showConfirmDialog(null, "Etes vous sûr de suppimer le service courant?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    try {
                        serviceManager.supprimerService(id);
                    } catch (Exception ex) {
                        Logger.getLogger(BkCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tableModel.removeRow(selected);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Aucun élément selectionné");
            }
        });

        bas.add(nouveau);
        bas.add(modifier);
        bas.add(supprimer);
        //bas.add(exportBtn);
        JPanel filtrePanel = new JPanel();
        filtrePanel.setLayout(new FlowLayout());
        final JXSearchField searchField = new JXSearchField("Rechercher");
        searchField.setPreferredSize(new Dimension(500, 50));
        filtrePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Zone de recherche"));
        filtrePanel.add(searchField);
        filtrePanel.setBackground(new Color(166, 202, 240));
        searchField.addActionListener((ActionEvent e) -> {
        });
        contenu.add(BorderLayout.AFTER_LAST_LINE, bas);
        contenu.add(BorderLayout.BEFORE_FIRST_LINE, filtrePanel);
        tableModel = new DefaultTableModel(new Object[]{"ID","Code", "Libellé", "Montant", "Actif?"}, 0);

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        //table.getColumnModel().getColumn(2).setPreferredWidth(280);
        table.removeColumn(table.getColumnModel().getColumn(0));
        contenu.add(BorderLayout.CENTER, new JScrollPane(table));
        add(BorderLayout.CENTER, contenu);
        serviceManager.getServiceOfferts().stream().forEach((a) -> {
            tableModel.addRow(new Object[]{
                a.getId(),
                a.getCode(),
                a.getLibelle(),
                a.getMontant(),
                a.isActif()
            });
        });
    }

    private class Nouveau extends JDialog {

        private final JButton okBtn, annulerBtn;
        private final JTextField libelleText;
        private final JTextField montantText;
        private final JCheckBox chkBox;
        private final JComboBox<String> typeBox;

        public Nouveau(final ServiceOffert serviceOffert) {
            setTitle("NOUVEAU SERVICE");
            setModal(true);
            setLayout(new BorderLayout(10, 10));
            JPanel haut = new JPanel();
            JLabel lbl;
            haut.add(lbl = new JLabel("<html><font color = #012345 > AJOUT D'UN SERVICE </font></html>"));
            lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
            add(BorderLayout.BEFORE_FIRST_LINE, haut);
            DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 110dlu:", ""));
            builder.setDefaultDialogBorder();
            builder.append("Type", typeBox = new JComboBox<>());
            builder.append("Libellé", libelleText = new JTextField(50));
            builder.append("Montant", montantText = new JTextField(50));
            builder.append("Actif?", chkBox = new JCheckBox());
            typeBox.addItem(MethodUtils.TYPE_LABEL_SOLDE);
            typeBox.addItem(MethodUtils.TYPE_LABEL_HIST);

            JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Enrégistrer"), annulerBtn = new JButton("Annuler"));
            builder.append(buttonBar, builder.getColumnCount());
            add(BorderLayout.CENTER, builder.getPanel());

            if (serviceOffert != null) {
                libelleText.setText(serviceOffert.getLibelle());
                montantText.setText(serviceOffert.getMontant().toString());
                switch (serviceOffert.getCode()) {
                    case MethodUtils.TYPE_LABEL_SOLDE:
                        typeBox.setSelectedIndex(0);
                        break;
                    case MethodUtils.TYPE_LABEL_HIST:
                        typeBox.setSelectedIndex(1);
                        break;
                }

                if (serviceOffert.isActif()) {
                    chkBox.setSelected(true);
                } else {
                    chkBox.setSelected(false);
                }
            }

            okBtn.addActionListener((ActionEvent ae) -> {
                try {
                    ServiceOffert a = new ServiceOffert();
                    if (!montantText.getText().equals("")) {
                        a.setMontant(Double.parseDouble(montantText.getText()));
                    } else {
                        JOptionPane.showMessageDialog(null, "Le montant est obligatoire");
                    }
                    
                    if (!libelleText.getText().equals("")) {
                        a.setLibelle(libelleText.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Le libellé du service est obligatoire");
                    }

                    a.setActif(false);
                    if (chkBox.isSelected()) {
                        a.setActif(true);
                    }

                    if (typeBox.getSelectedIndex() == 0) {
                        a.setCode(MethodUtils.SOLDE);
                    } else {
                        a.setCode(MethodUtils.HIST);
                    }

                    if (serviceOffert != null) {
                        a.setId(serviceOffert.getId());
                    }
                    if(a.getId() != null){
                        serviceManager.modifierService(a);
                    }else{
                        if(serviceManager.getServiceByCode(a.getCode())!=null){
                            JOptionPane.showMessageDialog(null, "Ce service est déjà configuré");
                        }else{
                            serviceManager.enregistrerService(a);
                        }
                    }
                    
                    dispose();
                    parentPanel.setContenu(new ServiceOffertPanel());
                } catch (IOException ex) {
                    Logger.getLogger(ServiceOffertPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            annulerBtn.addActionListener((ActionEvent ae) -> {
                dispose();
                try {
                    parentPanel.setContenu(new ServiceOffertPanel());
                } catch (IOException ex) {
                    Logger.getLogger(BkCompCliPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
}
