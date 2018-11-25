package com.abouna.lacussms.views.main;

import com.abouna.lacussms.views.BkAgencePanel;
import com.abouna.lacussms.views.BkCliPanel;
import com.abouna.lacussms.views.BkCompCliPanel;
import com.abouna.lacussms.views.BkEvePanel;
import com.abouna.lacussms.views.BkMadPanel;
import com.abouna.lacussms.views.BkOpePanel;
import com.abouna.lacussms.views.CommandPanel;
import com.abouna.lacussms.views.EnvoieSMSDialog;
import com.abouna.lacussms.views.EtatOPParamPanel;
import com.abouna.lacussms.views.HolidayDialog;
import com.abouna.lacussms.views.LicencePanel;
import com.abouna.lacussms.views.MessageFormatPanel;
import com.abouna.lacussms.views.MessageMandatPanel;
import com.abouna.lacussms.views.RapportPanel;
import com.abouna.lacussms.views.RemoteDBPanel;
import com.abouna.lacussms.views.ServiceOffertPanel;
import com.abouna.lacussms.views.UrlParamPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.springframework.stereotype.Component;

/**
 * @author Emmanuel ABOUNA <abouna.emmanuel@yahoo.fr> copy right 2017
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Component
public class MainMenuPanel extends JPanel {

    private JButton accueilBtn = new JButton("Accueil");
    private JButton typeMsgBtn = new JButton("Types SMS");
    private JButton formatMsgBtn = new JButton("Format SMS");
    private JButton cliBtn = new JButton("Client");
    private JButton opeBtn = new JButton("Opération");
    private JButton eveBtn = new JButton("Evenement");
    private JButton remoteBdBtn = new JButton("Param BD");
    private JButton employeeBtn = new JButton("Employés");
    private JButton paramTblBtn = new JButton("Param Table");
    private JButton licenceBtn = new JButton("Licence");
    private JButton userBtn = new JButton("Utilisateur");
    private JButton rapportTypeBtn = new JButton("Types Rapports");
    private JButton rapportBtn = new JButton("Rapports");
    private JButton agenceBtn = new JButton("Agence");
    private JButton urlParam = new JButton("Param Url");
    private JButton etatParam = new JButton("Param Etat OP");
    private JButton gescom = new JButton("Gest. Compte");
    private JButton mandat = new JButton("Mandats");
    private JButton rapportMandatBtn = new JButton("Rapports Mandats");
    private JButton commandBtn = new JButton("Requêtes");
    private JButton serviceBtn = new JButton("Services");

    private JPanel container;
    private Border RaisedBevelBorder = BorderFactory.createRaisedSoftBevelBorder();
    private Border defautBorder = BorderFactory.createCompoundBorder(RaisedBevelBorder, RaisedBevelBorder);
    private JButton envoieMsgBtn = new JButton("SMS PUSH");
    private JButton holidayBtn = new JButton("Jour ferié");

    public MainMenuPanel() {
        setLayout(new BorderLayout());

        accueilBtn.setBackground(new Color(166, 202, 240));
        accueilBtn.setBorder(RaisedBevelBorder);
        accueilBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new EmptyPanel());
            } catch (Exception ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        typeMsgBtn.setBorder(RaisedBevelBorder);
        typeMsgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    setContenu(new MessageFormatPanel());
                } catch (Exception ex) {
                    Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        typeMsgBtn.setBackground(new Color(166, 202, 240));

        serviceBtn.setBorder(RaisedBevelBorder);
        serviceBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new ServiceOffertPanel());
            } catch (Exception ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        serviceBtn.setBackground(new Color(166, 202, 240));

        formatMsgBtn.setBorder(RaisedBevelBorder);
        formatMsgBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new MessageFormatPanel());
            } catch (IOException ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        formatMsgBtn.setBackground(new Color(166, 202, 240));

        remoteBdBtn.setBorder(RaisedBevelBorder);
        remoteBdBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new RemoteDBPanel());
            } catch (Exception ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        remoteBdBtn.setBackground(new Color(166, 202, 240));

        urlParam.setBorder(RaisedBevelBorder);
        urlParam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    setContenu(new UrlParamPanel());
                } catch (Exception ex) {
                    Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        urlParam.setBackground(new Color(166, 202, 240));

        employeeBtn.setBorder(RaisedBevelBorder);
        employeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    setContenu(new MessageFormatPanel());
                } catch (Exception ex) {
                    Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        employeeBtn.setBackground(new Color(166, 202, 240));

        paramTblBtn.setBorder(RaisedBevelBorder);
        paramTblBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    setContenu(new MessageFormatPanel());
                } catch (Exception ex) {
                    Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        paramTblBtn.setBackground(new Color(166, 202, 240));

        mandat.setBorder(RaisedBevelBorder);
        mandat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    setContenu(new BkMadPanel());
                } catch (IOException ex) {
                    Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mandat.setBackground(new Color(166, 202, 240));

        gescom.setBorder(RaisedBevelBorder);
        gescom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    setContenu(new BkCompCliPanel());
                } catch (Exception ex) {
                    Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        gescom.setBackground(new Color(166, 202, 240));

        licenceBtn.setBorder(RaisedBevelBorder);
        licenceBtn.addActionListener((ActionEvent ae) -> {
            LicencePanel nouveau1 = null;
            nouveau1 = new LicencePanel(null);
            nouveau1.setSize(450, 150);
            nouveau1.setLocationRelativeTo(null);
            nouveau1.setModal(true);
            nouveau1.setResizable(false);
            nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nouveau1.setVisible(true);
        });
        licenceBtn.setBackground(new Color(166, 202, 240));

        userBtn.setBorder(RaisedBevelBorder);
        userBtn.addActionListener((ActionEvent ae) -> {
        });
        userBtn.setBackground(new Color(166, 202, 240));

        rapportTypeBtn.setBorder(RaisedBevelBorder);
        rapportTypeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    setContenu(new MessageFormatPanel());
                } catch (Exception ex) {
                    Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        rapportTypeBtn.setBackground(new Color(166, 202, 240));

        rapportMandatBtn.setBorder(RaisedBevelBorder);
        rapportMandatBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new MessageMandatPanel());
            } catch (Exception ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        rapportMandatBtn.setBackground(new Color(166, 202, 240));

        rapportBtn.setBorder(RaisedBevelBorder);
        rapportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    setContenu(new RapportPanel());
                } catch (Exception ex) {
                    Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        rapportBtn.setBackground(new Color(166, 202, 240));

        commandBtn.setBorder(RaisedBevelBorder);
        commandBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new CommandPanel());
            } catch (Exception ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        commandBtn.setBackground(new Color(166, 202, 240));

        cliBtn.setBorder(RaisedBevelBorder);
        cliBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new BkCliPanel());
            } catch (IOException ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cliBtn.setBackground(new Color(166, 202, 240));

        opeBtn.setBorder(RaisedBevelBorder);
        opeBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new BkOpePanel());
            } catch (IOException ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        opeBtn.setBackground(new Color(166, 202, 240));

        eveBtn.setBorder(RaisedBevelBorder);
        eveBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new BkEvePanel());
            } catch (IOException ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        eveBtn.setBackground(new Color(166, 202, 240));

        envoieMsgBtn.setBorder(RaisedBevelBorder);
        envoieMsgBtn.addActionListener((ActionEvent ae) -> {
            EnvoieSMSDialog nouveau1 = new EnvoieSMSDialog();
            nouveau1.setSize(500, 300);
            nouveau1.setLocationRelativeTo(null);
            nouveau1.setModal(true);
            nouveau1.setResizable(false);
            nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nouveau1.setVisible(true);
        });
        envoieMsgBtn.setBackground(new Color(166, 202, 240));

        holidayBtn.setBorder(RaisedBevelBorder);
        holidayBtn.addActionListener((ActionEvent ae) -> {
            HolidayDialog nouveau1 = new HolidayDialog();
            nouveau1.setSize(600, 400);
            nouveau1.setLocationRelativeTo(null);
            nouveau1.setModal(true);
            nouveau1.setResizable(false);
            nouveau1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nouveau1.setVisible(true);
        });
        holidayBtn.setBackground(new Color(166, 202, 240));

        agenceBtn.setBorder(RaisedBevelBorder);
        agenceBtn.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new BkAgencePanel());
            } catch (IOException ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        agenceBtn.setBackground(new Color(166, 202, 240));

        etatParam.setBorder(RaisedBevelBorder);
        etatParam.addActionListener((ActionEvent ae) -> {
            try {
                setContenu(new EtatOPParamPanel());
            } catch (IOException ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        etatParam.setBackground(new Color(166, 202, 240));

        JXTaskPaneContainer menu = new JXTaskPaneContainer();
        JXTaskPane donneesPane = new JXTaskPane() {
            @Override
            public boolean isVisible() {
                return true;
            }
        };

        JXTaskPane comptePane = new JXTaskPane() {
            @Override
            public boolean isVisible() {
                return true;
            }
        };

        JXTaskPane rapportPane = new JXTaskPane() {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
        menu.setEnabled(false);

        donneesPane.setTitle("Configurations");
        donneesPane.add(accueilBtn);
        donneesPane.add(agenceBtn);
        donneesPane.add(cliBtn);
        donneesPane.add(gescom);
        donneesPane.add(etatParam);
        donneesPane.add(opeBtn);
        donneesPane.add(serviceBtn);
        donneesPane.add(formatMsgBtn);
        donneesPane.add(eveBtn);
        //donneesPane.add(holidayBtn);
        donneesPane.add(envoieMsgBtn);
        donneesPane.add(remoteBdBtn);
        donneesPane.add(urlParam);

        //donneesPane.add(typeMsgBtn);
        comptePane.setTitle("Profil");
        comptePane.add(licenceBtn);
        comptePane.add(userBtn);

        rapportPane.setTitle("Rapports");
        //rapportPane.add(rapportTypeBtn);
        rapportPane.add(rapportBtn);
        rapportPane.add(commandBtn);
        //rapportPane.add(rapportMandatBtn);

        menu.add(donneesPane);
        menu.add(comptePane);
        menu.add(rapportPane);

        menu.setBackground(new Color(166, 202, 240));
        menu.setBorder(defautBorder);
        add(new JScrollPane(menu), BorderLayout.BEFORE_LINE_BEGINS);
        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBorder(/*new EmptyBorder(2, 20, 20, 20)*/defautBorder);
        setBackground(Color.red);
        add(container, BorderLayout.CENTER);
    }

    public void setContenu(JPanel pan) {
        container.removeAll();
        container.add(BorderLayout.CENTER, pan);
        container.validate();
    }

}
