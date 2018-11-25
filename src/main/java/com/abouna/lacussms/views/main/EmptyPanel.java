package com.abouna.lacussms.views.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author ${user}
 */
public class EmptyPanel extends JPanel{
    private BufferedImage m_image;
    
    public EmptyPanel(){
        try {
            this.m_image = ImageIO.read(getClass().getResource("/images/logo-reg.png"));
        
            JXLabel label = new JXLabel("");
            label.setFont(new Font("Bernard MT Condensed",Font.BOLD,65));
             label.setForeground(Color.WHITE);
            add(label);
            
        } catch (IOException ex) {
            Logger.getLogger(EmptyPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        super.paintComponent(g);
        g.drawImage(m_image, 0, 0,getWidth(),getHeight(),null);
    }
}