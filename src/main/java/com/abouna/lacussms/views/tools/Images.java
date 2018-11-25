/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views.tools;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;




/**
 *
 * @author Administrateur
 */
public class Images {
    private static Image adIcon;
    private static Image remIcon;
    private static Image upIcon;
    private static Image homeIcon;
    private static Image adherentIcon;

    public Images() throws IOException {
        adIcon = ImageIO.read(getClass().getResource("/images/Ajouter.png"));
        remIcon = ImageIO.read(getClass().getResource("/images/Cancel2.png"));
        upIcon = ImageIO.read(getClass().getResource("/images/modifier.png"));
        adherentIcon = ImageIO.read(getClass().getResource("/images/ad.jpg"));
    }

    public static Image getAdIcon() {
        return adIcon;
    }

    public static void setAdIcon(Image adIcon) {
        Images.adIcon = adIcon;
    }

    public static Image getRemIcon() {
        return remIcon;
    }

    public static void setRemIcon(Image remIcon) {
        Images.remIcon = remIcon;
    }

    public static Image getUpIcon() {
        return upIcon;
    }

    public static void setUpIcon(Image upIcon) {
        Images.upIcon = upIcon;
    }

    public static Image getHomeIcon() {
        return homeIcon;
    }

    public static void setHomeIcon(Image homeIcon) {
        Images.homeIcon = homeIcon;
    }

    public static Image getAdherentIcon() {
        return adherentIcon;
    }

    public static void setAdherentIcon(Image adherentIcon) {
        Images.adherentIcon = adherentIcon;
    }

   
    
}
