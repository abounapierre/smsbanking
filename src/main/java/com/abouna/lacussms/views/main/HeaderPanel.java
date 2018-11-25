package com.abouna.lacussms.views.main;




import com.abouna.lacussms.main.App;
import com.abouna.lacussms.views.ContactPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jdesktop.swingx.JXButton;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public  class HeaderPanel extends JPanel{
    private JButton adBtn;
    private JButton cotBtn;
    private JButton runBtn;
    private JButton stopBtn;
    private JButton runParaBtn;
    
    public HeaderPanel() throws IOException{
        setLayout(new FlowLayout(FlowLayout.LEFT,10,10));        
        setBackground(new Color(166, 202, 240));
        setBorder(BorderFactory.createRaisedSoftBevelBorder());
        JPanel pan = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        pan.setBorder(new EmptyBorder(0, 0, 0, 10));
        setPreferredSize(new Dimension(400,65));
        init();
    }
    
    protected final void init() throws IOException{
       ImageIcon img = new ImageIcon(ImageIO.read(getClass().getResource("/images/adherent.jpg")));
       ImageIcon img1 = new ImageIcon(ImageIO.read(getClass().getResource("/images/icona.jpg")));
       ImageIcon img2 = new ImageIcon(ImageIO.read(getClass().getResource("/images/run.JPG")));
       ImageIcon img3 = new ImageIcon(ImageIO.read(getClass().getResource("/images/stop.PNG")));
       ImageIcon img4 = new ImageIcon(ImageIO.read(getClass().getResource("/images/play.JPG")));
        adBtn = new JXButton(img);
        adBtn.setPreferredSize(new Dimension(40, 40));
        cotBtn = new JXButton(img1);
        cotBtn.setPreferredSize(new Dimension(40, 40));
        runBtn = new JXButton(img2);
        runBtn.setPreferredSize(new Dimension(40, 40));
        stopBtn = new JXButton(img3);
        stopBtn.setPreferredSize(new Dimension(40, 40));
        stopBtn.setEnabled(false);
        runBtn.setToolTipText("Démarrer le service SMS Séquentiel");
        stopBtn.setToolTipText("Arrêter le service SMS");
        runParaBtn = new JXButton(img4);
        runParaBtn.setPreferredSize(new Dimension(40, 40));
        runParaBtn.setToolTipText("Démarrer le service SMS Parallèle");
        add(adBtn);
        add(cotBtn);
        add(runBtn);
        add(runParaBtn);
        add(stopBtn);
        //runParaBtn.setEnabled(false);
        
        runBtn.addActionListener((ActionEvent e) -> {
            if (App.testConnexion()) {
                App.demarrerServiceSequenciel();
                App.demarrerServiceRequete();
                stopBtn.setEnabled(true);
                runBtn.setEnabled(false);
                runParaBtn.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Le service a démarré avec succès");
            } else {
                JOptionPane.showMessageDialog(null, "Erreur lors du démarrage du service, la connexion à la base de donnees n'est pas prête!!");
            }
       });       
         runParaBtn.addActionListener((ActionEvent e) -> {
             if(App.testConnexion()){
                 App.demarrerServiceData();
                 App.demarrerServiceSms();
                 App.demarrerServiceRequete();
                 stopBtn.setEnabled(true);
                 runBtn.setEnabled(false);
                 runParaBtn.setEnabled(false);
                 JOptionPane.showMessageDialog(null, "Le service a démarré avec succès");
             }else{
                 JOptionPane.showMessageDialog(null, "Erreur lors du démarrage du service, la connexion à la base de donnees n'est pas prête!!");
             }
       });
       
        stopBtn.addActionListener((ActionEvent e) -> {
            App.stopper();
            stopBtn.setEnabled(false);
            runBtn.setEnabled(true);
            runParaBtn.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Le service a été arrêté");
       });      
        cotBtn.addActionListener((ActionEvent e) -> {
            JDialog contact = new ContactPanel();
            contact.setSize(400, 200);
            contact.setLocationRelativeTo(null);
            contact.setModal(true);
            contact.setResizable(false);
            contact.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            contact.setVisible(true);
       });
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("Serif", Font.PLAIN, 32);
        GradientPaint gp = new GradientPaint(1f,1f,Color.DARK_GRAY,0f,30f,Color.CYAN);
        ((Graphics2D)g).setPaint(gp);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("GESTION DES COTISATIONS",310,40);
       
    }*/

}
