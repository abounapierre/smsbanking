/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

/**
 *
 * @author SATELLITE
 */
public class SplashFrame {
    final SplashScreen splash = SplashScreen.getSplashScreen();
    
    public SplashFrame(){
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        for(int i=0; i<100; i++) {
            renderSplashFrame(g, i);
            splash.update();
            try {
                Thread.sleep(90);
            }
            catch(InterruptedException e) {
            }
        }
        
    }
    
    public void close(){
        splash.close();
    }
    
    static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {"services", "parametres", "licence","données","initialisation"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(88,89,146,92);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 14));
        //g.drawString("Loading "+comps[(frame/5)%5]+"...", 75, 100);
        g.drawString("démrrage en cours ...", 50, 100);
    }
}
