
package com.abouna.lacussms.views;

import com.abouna.lacussms.config.ApplicationConfig;
import com.abouna.lacussms.entities.Holiday;
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.main.MainMenuPanel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author SATELLITE
 */
public class HolidayDialog extends JDialog {

    private final JButton okBtn, annulerBtn;
    private JXDatePicker dateTxt;
    private final JTextField contentText;
    private int c = 0, rang = 0, c1 = 0, rang1 = 0;
    @Autowired
    private LacusSmsService serviceManager;
    @Autowired
    private  MainMenuPanel parentPanel;
    private String date, description;

    public HolidayDialog() {
        serviceManager = ApplicationConfig.getApplicationContext().getBean(LacusSmsService.class);
        parentPanel = ApplicationConfig.getApplicationContext().getBean(MainMenuPanel.class);
        setTitle("Gestion des jours fériés");
        setModal(true);
        setLayout(new BorderLayout(10, 10));
        JPanel haut = new JPanel();
        JLabel lbl;
        haut.add(lbl = new JLabel("<html><font color = #012345 > Enregistrer un jour férié </font></html>"));
        lbl.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        add(BorderLayout.BEFORE_FIRST_LINE, haut);
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("right:max(40dlu;p), 12dlu, 180dlu:", ""));
        builder.setDefaultDialogBorder();
        builder.append("Date", dateTxt = new JXDatePicker());
        builder.append("Description", contentText = new JTextField(50));
        JPanel buttonBar = ButtonBarFactory.buildOKCancelBar(okBtn = new JButton("Enregistrer"), annulerBtn = new JButton("Annuler"));
        builder.append(buttonBar, builder.getColumnCount());
        add(BorderLayout.CENTER, builder.getPanel());
        

        okBtn.addActionListener((ActionEvent ae) -> {
            try {
                if (!contentText.getText().equals("")) {
                    description = contentText.getText();
                } else {
                    JOptionPane.showMessageDialog(null, "Le contenu est obligatoire");
                    return;
                }
                if (dateTxt.getDate() != null) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    date = format.format(dateTxt.getDate());
                } else {
                    JOptionPane.showMessageDialog(null, "Le titre est obligatoire");
                    return;
                }
                Holiday holiday = new Holiday();
                holiday.setDescription(description);
                holiday.sethDate(date);
                Calendar calendar = dateToCalendar(dateTxt.getDate());
                holiday.setJour(day(calendar.get(Calendar.DAY_OF_WEEK)));
                if(serviceManager.saveHoliday(holiday)==null){
                    JOptionPane.showMessageDialog(null, "Cette date a déjà été enregistrée!!");
                }
                dispose();
            } catch (ExecutionException ex) {
                JOptionPane.showMessageDialog(null, "Cette date a déjà été enregistrée!!");
            }
        });

        annulerBtn.addActionListener((ActionEvent ae) -> {
            dispose();
        });
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private String day(int i) {
        if (i == 2) {
            return "Lundi";
        } else if (i == 3) {
            return "Mardi";
        } else if (i == 4) {
            return "Mercredi";
        } else if (i == 5) {
            return "Jeudi";
        } else if (i == 6) {
            return "Vendredi";
        } else if (i == 7) {
            return "Samedi";
        } else if (i == 1) {
            return "Dimanche";
        }
        return "";
    }
}
