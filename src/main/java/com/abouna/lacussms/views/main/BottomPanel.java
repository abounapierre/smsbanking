/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Administrateur
 */
public class BottomPanel extends JPanel{
    private final JLabel signatureLabel;
    private JLabel label;
    private static JLabel info = new JLabel();
    
    public BottomPanel(){
        signatureLabel = new JLabel("Design by SMILE SOFTWARE FONDATION");//Ing. ABOUNA P.E
        signatureLabel.setFont(new Font("",Font.ITALIC,8));
        signatureLabel.setForeground(Color.red);
        setBackground(new Color(166,202,240));
        setLayout(new BorderLayout(10,10));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
        rightPanel.add(signatureLabel);
        rightPanel.setBackground(new Color(166,202,240));
        leftPanel.setBackground(new Color(166,202,240));
        leftPanel.add(info);
        add(leftPanel,BorderLayout.BEFORE_LINE_BEGINS);
        add(rightPanel,BorderLayout.AFTER_LINE_ENDS);
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public static JLabel getInfo() {
        return info;
    }

    public static void setInfo(JLabel info) {
        BottomPanel.info = info;
    }
    
    public static void settextLabel(String s){
        info.setText(s);
        info.setFont(new Font("",Font.BOLD,14));
    }
    
    public static void settextLabel(String s,Color c){
        info.setText(s);
        info.setForeground(c);
        info.setFont(new Font("",Font.BOLD,14));
    }
}
