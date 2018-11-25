/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views.main;

import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.LoginPane;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SATELLITE
 */
public class LoginFrame {

    public LoginFrame(LacusSmsService service,MainFrame mainFrame) {
        JDialog frame = new JDialog();
        frame.setTitle("Login");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.GREEN);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        frame.setContentPane(content);
        frame.add(new LoginPane());
        frame.pack();
        frame.setLocationRelativeTo(mainFrame);
        frame.setVisible(true);
    }
}
