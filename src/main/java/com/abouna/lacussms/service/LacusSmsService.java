/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.service;

import com.abouna.lacussms.entities.BkAgence;
import com.abouna.lacussms.entities.BkCli;
import com.abouna.lacussms.entities.BkCompCli;
import com.abouna.lacussms.entities.BkEtatOp;
import com.abouna.lacussms.entities.BkEve;
import com.abouna.lacussms.entities.BkMad;
import com.abouna.lacussms.entities.BkOpe;
import com.abouna.lacussms.entities.BkTelCli;
import com.abouna.lacussms.entities.Command;
import com.abouna.lacussms.entities.Config;
import com.abouna.lacussms.entities.CutOff;
import com.abouna.lacussms.entities.Holiday;
import com.abouna.lacussms.entities.Licence;
import com.abouna.lacussms.entities.Message;
import com.abouna.lacussms.entities.MessageFormat;
import com.abouna.lacussms.entities.MessageMandat;
import com.abouna.lacussms.entities.RemoteDB;
import com.abouna.lacussms.entities.SentMail;
import com.abouna.lacussms.entities.ServiceOffert;
import com.abouna.lacussms.entities.Status;
import com.abouna.lacussms.entities.TypeEvent;
import com.abouna.lacussms.entities.TypeMessage;
import com.abouna.lacussms.entities.UrlMessage;
import com.abouna.lacussms.entities.User;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author SATELLITE
 */
public interface LacusSmsService {

    public MessageFormat enregistrer(MessageFormat mf);

    public List<MessageFormat> getAll();

    public MessageFormat modifier(MessageFormat mf);

    public void supprimerFormat(Integer id);

    public MessageFormat getFormatById(Integer id);

    public List<MessageFormat> getFormatByBkOpe(BkOpe bkOpe);

    public RemoteDB getDefaultRemoteDB(boolean b);

    public Integer getMaxIndexBkEve();

    public RemoteDB enregistrer(RemoteDB mf);

    public List<RemoteDB> getAllRemoteDB();

    public RemoteDB modifier(RemoteDB mf);

    public void supprimerRemoteDB(Integer id);

    public RemoteDB getRemoteDBById(Integer id);

    public Message enregistrer(Message msg);

    public List<Message> getAllMessages();

    public Message modifier(Message msg);

    public void supprimerMessage(Integer id);

    public Message getMessageById(Integer id);

    public int supprimerToutMessage();

    public UrlMessage enregistrer(UrlMessage url);

    public List<UrlMessage> getAllUrlMessages();

    public UrlMessage modifier(UrlMessage url);

    public void supprimerUrlMessage(Integer id);

    public UrlMessage getUrlMessageById(Integer id);

    public UrlMessage getDefaultUrlMessage();

    public List<BkEtatOp> getListBkEtatOp(boolean actif);

    public BkEtatOp enregistrer(BkEtatOp etat);

    public List<BkEtatOp> getAllBkEtatOps();

    public BkEtatOp modifier(BkEtatOp etat);

    public void supprimerBkEtatOp(Integer id);

    public BkEtatOp getBkEtatOpById(Integer id);

    public List<MessageFormat> getFormatByName(String val);

    public BkCli enregistrer(BkCli cli);

    public List<BkCli> getAllCli();

    public BkCli modifier(BkCli cli);

    public void supprimerBkCli(String id);

    public BkCli getBkCliById(String id);

    public BkCli getBkCliByNumCompte(String compte);

    public BkEve enregistrer(BkEve eve);

    public List<BkEve> getAllBkEves();

    public BkEve modifier(BkEve eve);

    public void supprimerBkEve(Integer id);

    public BkEve getBkEveById(Integer id);

    public List<BkEve> getBkEveByEtat(String etat, Date date);

    public List<BkEve> getBkEveBySendParam(boolean send);

    public int supprimerToutBkEve();

    public BkOpe enregistrer(BkOpe ope);

    public List<BkOpe> getAllBkOpes();

    public BkOpe modifier(BkOpe ope);

    public void supprimerBkOpe(String id);

    public BkOpe getBkOpeById(String id);

    public BkCompCli enregistrer(BkCompCli com);

    public List<BkCompCli> getAllBkCompClis();

    public BkCompCli modifier(BkCompCli com);

    public void supprimerBkCompCli(String id);

    public BkCompCli getBkCompCliById(String id);

    public List<BkCompCli> getBkCompCliByCli(BkCli cli);

    public List<BkCompCli> getBkCompCliByCli(BkCli cli, boolean actif);

    public List<BkCompCli> getBkCompCliByCriteria(String cli);

    public TypeMessage enregistrer(TypeMessage tm);

    public List<TypeMessage> getAllTypeMessages();

    public TypeMessage modifier(TypeMessage tm);

    public void supprimerTypeMessage(Integer id);

    public TypeMessage getTypeMessageById(Integer id);

    public User enregistrer(User u);

    public List<User> getAllUsers();

    public User modifier(User u);

    public void supprimerUser(String id);

    public User getUserById(String id);

    public BkAgence enregistrer(BkAgence a);

    public List<BkAgence> getAllBkAgences();

    public BkAgence modifier(BkAgence a);

    public void supprimerBkAgence(String id);

    public BkAgence getBkAgenceById(String id);

    public List<BkCli> getBkCliByName(String val);

    public List<BkOpe> getBkOpeByName(String val);

    public List<BkEve> getBkEveByName(String val);

    public List<BkEve> getBkEveByDate(Date date);

    public List<BkEve> getBkEveMaxDate();

    public MessageFormat getFormatByBkOpe(BkOpe ope, String langue);

    public List<BkEve> getBkEveByPeriode(Date d1, Date d2);

    public List<Message> getMessageFromPeriode(Date d1, Date d2);

    public List<Licence> getLicences();

    public Licence enregistrer(Licence licence);

    public Licence modifier(Licence licence);

    public List<BkTelCli> getListBkTelByCli(BkCli b);

    public BkTelCli getListBkTelByCliDefault(BkCli b, boolean d);

    public BkTelCli enregistrer(BkTelCli bkTelCli);

    public List<BkTelCli> getBkTelByClis();

    public BkTelCli modifier(BkTelCli b);

    public void supprimer(Integer id);

    public BkTelCli getBkTelCliById(Integer i);

    public Integer count(BkTelCli bkTelCli);

    public List<BkEve> getBkEveBySendParam(boolean b, List<String> listString);

    public List<BkCli> getBkCliByCriteria(String criteria);

    public List<BkEve> getBkEveByCriteria(String code, String date, String compte);

    public List<BkEve> getBkEveByCriteria(String code);

    public int supprimerParPeriode(String date1, String date2);

    public int supprimerParPeriode(Date date1, Date date2);

    public List<BkCli> getBkCliLimit(int limit);

    public List<BkCompCli> getBkCompCliLimit(int limit);

    public Integer getMaxIndexBkEve(TypeEvent type);

    public List<BkEve> getBkEveBySendParam(boolean send, List<String> list, TypeEvent type);

    public List<BkCompCli> getBkCompCliByCli(BkCli cli, String compte, boolean actif);

    public BkCompCli getBkCompCliByCriteria(BkCli cli, String compte, boolean actif);

    public List<BkEve> getBkEveByCriteria(String code, Date date, String compte);
    
    public List<BkEve> getBkEveByPeriode(String code, String compte, Date date1, Date date2);

    public List<BkMad> getBkMadsByPeriode(Date d1, Date d2);

    public int supprimerBkMad(Date d1, Date d2);

    public List<BkMad> getbkMadsByCriteria(String val);

    public BkMad getBkMadByCriteria(String num, String ad1p, Date date);

    public Integer getMaxBkMad();

    public BkMad enregistrer(BkMad bkMad);

    public List<BkMad> getAllBkMads();

    public BkMad modifier(BkMad bkMad);

    public void supprimerBkMad(Integer id);

    public BkMad getBkMadById(Integer id);

    public void supprimerToutBkMad();

    public BkMad getBkMadByClesec(String num);

    public List<BkMad> getBkMadByTraite();

    public MessageMandat enregistrer(MessageMandat mes);

    public List<MessageMandat> getAllMessageMandats();

    public MessageMandat modifier(MessageMandat mes);

    public void supprimerMessageMandat(Integer id);

    public MessageMandat getMessageMandatById(Integer id);

    public List<MessageMandat> getMessageMandatFromPeriode(Date d1, Date d2);

    public int supprimerTout();

    public List<BkMad> getBkMadByLimit(int limit);

    public List<BkEve> getBkEveByLimit(int limit);

    public List<BkEve> getBkEveByCriteria2(String code, Date date, String compte);

    public List<BkEve> getBkEveByCriteria(String code, String compte, String heure, String montant);
    
     public List<BkEve> getBkEveByCriteriaMontant(String code, String compte, String montant);
    
    public List<BkEve> getBkEveByCriteria(String code, String compte);

    public List<SentMail> getMailByDate(Date d);

    public SentMail saveMail(SentMail mail);

    public List<SentMail> getAllEmails();

    public Config enregistrerConfig(Config c);

    public Config modifierConfig(Config c);

    public List<Config> getAllConfig();

    public boolean viderLicence();
    
    public Holiday saveHoliday(Holiday holiday) throws ExecutionException;
    
    public void supprimerHoliday(Integer id);
    
    public List<Holiday> getHolidays();
    
    public Holiday getHolidayById(Integer id);
    
    public List<Holiday> getHolidaysByDate(String date);
    
    //service des operations 
    public Command enregistrer(Command cmd);

    public List<Command> getAllCommands();
    
     public List<Command> getAllCommands(Date date);
     
     public List<Command> getCommandByStatus(Status status);

    public Command modifier(Command cmd);

    public void supprimerCommand(Integer id);

    public Command getCommandById(Integer id);
    
    public void serviceRequete();
    
    public List<ServiceOffert> getServiceOfferts();
    
    public ServiceOffert findServiceByCode(String code);
    
    public ServiceOffert enregistrerService(ServiceOffert s);
    
    public ServiceOffert modifierService(ServiceOffert s);
    
    public void supprimerService(Integer id);

    public ServiceOffert getServiceById(Integer id);
    
    public ServiceOffert getServiceByCode(String id);
    
    public List<CutOff> getAllCutOffs();
    
    public List<CutOff> getCutOffs();
    
    public CutOff getLastCutOff(Date dateDebut);
    
    public CutOff getCutOffById(Integer id);
}
