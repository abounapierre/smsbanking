/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author SATELLITE
 */
@Entity
@Table(name = "bkeve")
public class BkEve implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private String etat;
    private String compte;
    @Temporal(javax.persistence.TemporalType.DATE)
    @JoinColumn(name = "edate")
    private Date eventDate;
    @ManyToOne
    @JoinColumn(name = "ope")
    private BkOpe ope;
    @ManyToOne
    @JoinColumn(name = "cli")
    private BkCli cli;
    private String hsai;
    private boolean sent;
    @ManyToOne
    private BkAgence bkAgence;
    private double mont;
    private String DVAB;
    private String numEve;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Message> messages;
    private TypeEvent type;
    private String montant;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date DCO;

    public BkEve() {
    }

    public BkEve(String etat, String compte, BkOpe ope, BkCli cli) {
        this.etat = etat;
        this.compte = compte;
        this.ope = ope;
        this.cli = cli;
    }

    public Date getDCO() {
        return DCO;
    }

    public void setDCO(Date DCO) {
        this.DCO = DCO;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public TypeEvent getType() {
        return type;
    }

    public void setType(TypeEvent type) {
        this.type = type;
    }
    
    public void addMessage(Message message){
        messages.add(message);
        message.setBkEve(this);
    }
    
     public void removeMessage(Message message){
        messages.remove(message);
        message.setBkEve(null);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getNumEve() {
        return numEve;
    }

    public void setNumEve(String numEve) {
        this.numEve = numEve;
    }

    public double getMont() {
        return mont;
    }

    public String getDVAB() {
        return DVAB;
    }

    public void setDVAB(String DVAB) {
        this.DVAB = DVAB;
    }

    public void setMont(double mont) {
        this.mont = mont;
    }

    public BkAgence getBkAgence() {
        return bkAgence;
    }

    public void setBkAgence(BkAgence bkAgence) {
        this.bkAgence = bkAgence;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    
    public String getHsai() {
        return hsai;
    }

    public void setHsai(String hsai) {
        this.hsai = hsai;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public BkOpe getOpe() {
        return ope;
    }

    public void setOpe(BkOpe ope) {
        this.ope = ope;
    }

    public BkCli getCli() {
        return cli;
    }

    public void setCli(BkCli cli) {
        this.cli = cli;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "BkEve{" + "id=" + id + ", etat=" + etat + ", compte=" + compte + ", eventDate=" + eventDate + ", ope=" + ope + ", cli=" + cli + ", hsai=" + hsai + ", sent=" + sent + ", bkAgence=" + bkAgence + ", mont=" + mont + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.etat != null ? this.etat.hashCode() : 0);
        hash = 61 * hash + (this.compte != null ? this.compte.hashCode() : 0);
        hash = 61 * hash + (this.ope != null ? this.ope.hashCode() : 0);
        hash = 61 * hash + (this.cli != null ? this.cli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BkEve other = (BkEve) obj;
        if ((this.etat == null) ? (other.etat != null) : !this.etat.equals(other.etat)) {
            return false;
        }
        if ((this.compte == null) ? (other.compte != null) : !this.compte.equals(other.compte)) {
            return false;
        }
        if (this.ope != other.ope && (this.ope == null || !this.ope.equals(other.ope))) {
            return false;
        }
        return this.cli == other.cli || (this.cli != null && this.cli.equals(other.cli));
    }
    
}
