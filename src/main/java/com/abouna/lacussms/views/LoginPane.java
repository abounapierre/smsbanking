/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author SATELLITE
 */

public class LoginPane extends JPanel {

    public LoginPane() {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Login"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        add(new JTextField(10), gbc);
        gbc.gridy++;
        add(new JTextField(10), gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        JButton cancelBtn = new JButton("Cancel");
        add(loginBtn, gbc);
        gbc.gridx++;
        add(cancelBtn, gbc);
    }

    public JPanel getLoginPanel() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.GREEN);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        return content;
    }

}
