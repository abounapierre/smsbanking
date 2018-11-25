/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.views.tools;

import com.abouna.lacussms.entities.Message;
import com.abouna.lacussms.service.LacusSmsService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author SATELLITE
 */
public class PrintReportPDF {

    private Document document = null;
    private final Font headerFont;
    private boolean status = false;
    private final LacusSmsService serviceManager;

    public PrintReportPDF(String path, Date dateDeb, Date dateFin, LacusSmsService service) throws FileNotFoundException {
        this.serviceManager = service;
        headerFont = new Font();
        headerFont.setStyle(Font.BOLD);
        headerFont.setSize(7.5f);
        try {
            document = new Document(PageSize.A4, 10, 10, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            Paragraph p = new Paragraph("LA REGIONALE");
            document.add(p);
            document.add(new Paragraph(" "));
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            p = new Paragraph("RAPPORT DES MESSAGES ENVOYES DE LA PERIODE ALLANT DU " + format.format(dateDeb) + " AU " + format.format(dateFin));
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(9);
            table.setWidths(new float[]{2f, 3f, 10f, 4f, 4f, 4f, 6f, 4f, 4f});
            table.setSpacingBefore(10);
            table.setWidthPercentage(100);
            PdfPCell cell;
            cell = new PdfPCell(new Phrase("Code", headerFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Titre", headerFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Message", headerFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Téléphone", headerFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Date", headerFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Destinataire", headerFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Client", headerFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Compte", headerFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Agence", headerFont));
            table.addCell(cell);

            int i = 1;
            for (Message msg : serviceManager.getMessageFromPeriode(dateDeb, dateFin)) {
                cell = new PdfPCell(new Phrase("" + msg.getId(), headerFont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(msg.getTitle(), headerFont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(msg.getContent(), headerFont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(msg.getBkEve()==null ? "" : msg.getBkEve().getCli() == null ? "" : Long.toString(msg.getBkEve().getCli().getPhone()), headerFont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(msg.getSendDate().toString(), headerFont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(msg.getNumero(), headerFont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(msg.getBkEve() == null ? "" : msg.getBkEve().getCli() == null ? "" : msg.getBkEve().getCli().toString(), headerFont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(msg.getBkEve() == null ? "" : msg.getBkEve().getCompte() == null ? "" : msg.getBkEve().getCompte().toString(), headerFont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(msg.getBkEve() == null ? "" : msg.getBkEve().getBkAgence() == null ? "" : msg.getBkEve().getBkAgence().getNoma(), headerFont));
                table.addCell(cell);
                i++;
            }
            document.add(table);
            document.add(new Paragraph(" "));
            p = new Paragraph("TOTAL ENVOYE(S) : " + i, headerFont);
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
            document.add(new Paragraph(" "));
            Date d = new Date();
            p = new Paragraph(d.toString(), headerFont);
            p.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
        } catch (DocumentException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la génération du rapport");
        } finally {
            document.close();
            status = true;
        }
    }
}
