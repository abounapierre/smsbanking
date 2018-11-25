/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.views.tools;

import com.abouna.lacussms.entities.Command;
import com.abouna.lacussms.entities.ServiceOffert;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SATELLITE
 */
public class BankFilePrinting {

    private final String agence = "001";

    public BankFilePrinting(String path, List<Command> commands) {
        try (FileWriter fileWriter = new FileWriter(path); 
                PrintWriter printWriter = new PrintWriter(fileWriter)) {
            commands.stream().map((command) -> agence + "|" + command.getOpe()
                    + "|" + command.getCompte() + "|" + command.getMontant() + "|" + "D").forEach((s) -> {
                            printWriter.println(s);
            });
        } catch (IOException ex) {
            Logger.getLogger(BankFilePrinting.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
