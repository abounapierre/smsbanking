/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.service.LacusSmsService;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author SATELLITE
 */
public class LoginPanel extends JPanel{
    private final JTextField loginText;
    private final JPasswordField passwdText;
    private final JButton btnLogin;
    @Autowired
    private  LacusSmsService serviceManager;
    
    public LoginPanel(){
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        setLayout(new BorderLayout(10,10));
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(new EmptyBorder(180, 350, 50, 300));
        loginPanel.setBackground(new Color(166, 202, 240));
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("l:p, 12dlu, 100dlu", ""));
        builder.setBackground(new Color(166, 202, 240));
        builder.append("Nom d'utilisateur",loginText = new JTextField());
        builder.append("Mot de passe", passwdText = new JPasswordField());
        JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(btnLogin = new JButton("Connexion"),new JButton("Annuler"));
        builder.append(buttonBar,builder.getColumnCount());
        loginPanel.add(BorderLayout.CENTER, builder.getPanel());
        add(BorderLayout.CENTER,loginPanel);
    }
    
     @Override
    protected void paintComponent(Graphics g) {
        try {
            Image img = ImageIO.read(getClass().getResource("/images/smile.png"));
            Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        super.paintComponent(g);
        //g.drawImage(img, 0, 0,getWidth(),getHeight(),null);
        g.setColor(new Color(166, 202, 240));
       //g.drawString("SUIVI DES INDICATEURS",310,80);
        } catch (IOException ex) {
            Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
