/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views.tools;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author Administrateur
 */
public class InfosPanel extends JPanel{
    private JLabel message;

    public InfosPanel(JLabel message, LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        this.message = message;
    }

    public InfosPanel(JLabel message, LayoutManager layout) {
        super(layout);
        this.message = message;
    }

    public InfosPanel(JLabel message, boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        this.message = message;
    }

    public InfosPanel(JLabel message) {
        this.message = message;
    }

    public InfosPanel() {
        setLayout(new BorderLayout());
    }

    public JLabel getMessage() {
        return message;
    }

    public void setMessage(JLabel message) {
        this.message = message;
    }

    public ComponentUI getUi() {
        return ui;
    }

    public void setUi(ComponentUI ui) {
        this.ui = ui;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }
    
    public void setContenu(JLabel label){
        removeAll();
        setLayout(new BorderLayout());
        add(label);
    }
    
}
