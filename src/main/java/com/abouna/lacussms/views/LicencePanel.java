/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.Licence;
import com.abouna.lacussms.main.App;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.main.BottomPanel;
import com.abouna.lacussms.views.main.MainFrame;
import com.abouna.lacussms.views.tools.Utils;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author SATELLITE
 */
public class LicencePanel extends JDialog {

    private  JButton okBtn, annulerBtn;
    private  JTextField nameText;
    @Autowired
    private LacusSmsService serviceManager;

    public LicencePanel() {
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
    }

    public LicencePanel(final Licence licence) {
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        setTitle("GESTION DE LA LICENCE");
        setModal(true);
        setLayout(new BorderLayout(10, 10));
        JPanel haut = new JPanel();
        JLabel lbl;
        haut.add(lbl = new JLabel("<html><font color = #012345 > METTRE A JOUR LA LICENCE </font></html>"));
        lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        add(BorderLayout.BEFORE_FIRST_LINE, haut);
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:", ""));
        builder.setDefaultDialogBorder();
        builder.append("Insérer la licence", nameText = new JTextField(50));
        JPopupMenu menu = new JPopupMenu();
        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.NAME, "Couper");
        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        menu.add(cut);

        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copier");
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        menu.add(copy);

        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, "Coller");
        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        menu.add(paste);

        Action selectAll = new SelectAll();
        menu.add(selectAll);

        nameText.setComponentPopupMenu(menu);

        JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Enrégistrer"), annulerBtn = new JButton("Annuler"));
        builder.append(buttonBar, builder.getColumnCount());
        add(BorderLayout.CENTER, builder.getPanel());

        okBtn.addActionListener((ActionEvent ae) -> {
            try {
                Licence a = new Licence();
                BottomPanel.settextLabel("Etablissement de la connexion sur le serveur...", java.awt.Color.BLACK);
                Connection conn = Utils.getConnection();
                if (!nameText.getText().equals("")) {
                    a.setValeur(nameText.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "La licence est obligatoire");
                    return;
                }
                if (licence == null) {
                    try {
                        if (conn != null) {
                            BottomPanel.settextLabel("Connexion établie...", java.awt.Color.BLACK);
                            if (Utils.exist(a.getValeur(), conn)) {
                                BottomPanel.settextLabel("Enregistrement de la licence...", java.awt.Color.BLACK);
                                if (serviceManager.getLicences().isEmpty()) {
                                    System.out.println("Enregistrement");
                                    serviceManager.enregistrer(a);
                                } else {
                                    for (Licence lic : serviceManager.getLicences()) {
                                        lic.setValeur(nameText.getText());
                                        System.out.println("Modification");
                                        serviceManager.modifier(lic);
                                    }
                                }
                                Utils.ecrire("pid 1");
                                Utils.updateLic(a.getValeur(), conn);
                                JOptionPane.showMessageDialog(null, "Success!");
                                dispose();
                                if (!App.isAppliRun()) {
                                    App.initApp();
                                }
                                BottomPanel.settextLabel("", java.awt.Color.BLACK);
                            } else {
                                JOptionPane.showMessageDialog(null, "Licence inconnue ou déjà utilisée!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Problème de connexion assurez vous que votre poste\r\n "
                                    + "est connecté à internet ou verifiez votre parfeu\r\n "
                                    + "s'il ne bloque pas le connexion vers le serveur (alwaysdata.com)!");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Problème de connexion à la base de données");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Problème de connexion à la base de données");
                    } catch (ClassNotFoundException ex) {
                        
                    } catch (HeadlessException ex) {
                        
                    }
                } else {
                    a.setId(licence.getId());
                    try {
                        if (conn != null) {
                            if (Utils.exist(a.getValeur(), conn)) {
                                System.out.println("Modification");
                                serviceManager.modifier(a);
                                JOptionPane.showMessageDialog(null, "Licence enregistrée avec success!");
                                dispose();
                                if (!App.isAppliRun()) {
                                    App.initApp();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Licence inconnue ou déjà utilisée!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Problème de connexion assurez vous que votre poste\r\n "
                                    + "est connecté à internet ou verifiez votre parfeu\r\n "
                                    + "s'il ne bloque pas le connexion vers le serveur (alwaysdata.com)!");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Problème de connexion à la base de données");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Problème de connexion à la base de données");
                    }
                }
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Problème de connexion de chargement du pilote");
            }
        });

        annulerBtn.addActionListener((ActionEvent ae) -> {
            BottomPanel.settextLabel("", java.awt.Color.BLACK);
            dispose();
            if (serviceManager.getLicences().isEmpty()) {
                System.exit(0);
            } else if (serviceManager.getLicences().get(0).getJour() == 0) {
                System.exit(0);
            }else if(!App.isAppliRun()){
                System.exit(0);
            }
        });
    }

    static class SelectAll extends TextAction {

        public SelectAll() {
            super("Select All");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextComponent component = getFocusedComponent();
            component.selectAll();
            component.requestFocusInWindow();
        }
    }

}
