/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.service.impl;

import com.abouna.generic.dao.DataAccessException;
import com.abouna.lacussms.dao.HolidayDao;
import com.abouna.lacussms.dao.IBkAgenceDao;
import com.abouna.lacussms.dao.IBkCliDao;
import com.abouna.lacussms.dao.IBkCompCliDao;
import com.abouna.lacussms.dao.IBkEtatOpDao;
import com.abouna.lacussms.dao.IBkEveDao;
import com.abouna.lacussms.dao.IBkMadDao;
import com.abouna.lacussms.dao.IBkOpeDao;
import com.abouna.lacussms.dao.IBkTelCliDao;
import com.abouna.lacussms.dao.ICommandDao;
import com.abouna.lacussms.dao.IConfigDao;
import com.abouna.lacussms.dao.ICutOffDao;
import com.abouna.lacussms.dao.ILicenceDao;
import com.abouna.lacussms.dao.IMessageDao;
import com.abouna.lacussms.dao.IMessageFormatDao;
import com.abouna.lacussms.dao.IMessageMandatDao;
import com.abouna.lacussms.dao.IRemoteDBDao;
import com.abouna.lacussms.dao.ISentMailDao;
import com.abouna.lacussms.dao.ITypeMessageDao;
import com.abouna.lacussms.dao.IUrlMessageDao;
import com.abouna.lacussms.dao.IUserDao;
import com.abouna.lacussms.dao.ServiceOffertDao;
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
import com.abouna.lacussms.service.LacusSmsService;
import com.abouna.lacussms.views.tools.AES;
import com.abouna.lacussms.views.tools.ConstantUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author SATELLITE
 */
@Service
@Transactional
public class LacusSmsServiceImpl implements LacusSmsService {

    @Autowired
    private IMessageFormatDao messageFormat;
    @Autowired
    private IBkCliDao bkCliDao;
    @Autowired
    private IBkEveDao bkEveDao;
    @Autowired
    private IBkOpeDao bkOpeDao;
    @Autowired
    private ITypeMessageDao typeMessageDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IMessageDao messageDao;
    @Autowired
    private IBkAgenceDao bkAgenceDao;
    @Autowired
    private IRemoteDBDao remoteDBDao;
    @Autowired
    private ILicenceDao licenceDao;
    @Autowired
    private IBkTelCliDao bkTelCliDao;
    @Autowired
    private IUrlMessageDao urlMessageDao;
    @Autowired
    private IBkEtatOpDao bkEtatOpDao;
    @Autowired
    private IBkCompCliDao bkCompCliDao;
    @Autowired
    private IBkMadDao bkMadDao;
    @Autowired
    private IMessageMandatDao messageMandatDao;
    @Autowired
    private ISentMailDao sentMailDao;
    @Autowired
    private IConfigDao configDao;
    @Autowired
    private HolidayDao holidayDao;
    @Autowired
    private ICommandDao commandDao;
    @Autowired
    private ServiceOffertDao serviceOffertDao;
    @Autowired
    private ICutOffDao cutOffDao;

    public IMessageMandatDao getMessageMandatDao() {
        return messageMandatDao;
    }

    public void setMessageMandatDao(IMessageMandatDao messageMandatDao) {
        this.messageMandatDao = messageMandatDao;
    }

    public ISentMailDao getSentMailDao() {
        return sentMailDao;
    }

    public void setSentMailDao(ISentMailDao sentMailDao) {
        this.sentMailDao = sentMailDao;
    }

    public IBkMadDao getBkMadDao() {
        return bkMadDao;
    }

    public void setBkMadDao(IBkMadDao bkMadDao) {
        this.bkMadDao = bkMadDao;
    }

    public IBkCompCliDao getBkCompCliDao() {
        return bkCompCliDao;
    }

    public void setBkCompCliDao(IBkCompCliDao bkCompCliDao) {
        this.bkCompCliDao = bkCompCliDao;
    }

    public IBkEtatOpDao getBkEtatOpDao() {
        return bkEtatOpDao;
    }

    public void setBkEtatOpDao(IBkEtatOpDao bkEtatOpDao) {
        this.bkEtatOpDao = bkEtatOpDao;
    }

    public IUrlMessageDao getUrlMessageDao() {
        return urlMessageDao;
    }

    public void setUrlMessageDao(IUrlMessageDao urlMessageDao) {
        this.urlMessageDao = urlMessageDao;
    }

    public IBkTelCliDao getBkTelCliDao() {
        return bkTelCliDao;
    }

    public void setBkTelCliDao(IBkTelCliDao bkTelCliDao) {
        this.bkTelCliDao = bkTelCliDao;
    }

    public ILicenceDao getLicenceDao() {
        return licenceDao;
    }

    public void setLicenceDao(ILicenceDao licenceDao) {
        this.licenceDao = licenceDao;
    }

    public IBkAgenceDao getBkAgenceDao() {
        return bkAgenceDao;
    }

    public void setBkAgenceDao(IBkAgenceDao bkAgenceDao) {
        this.bkAgenceDao = bkAgenceDao;
    }

    public IRemoteDBDao getRemoteDBDao() {
        return remoteDBDao;
    }

    public void setRemoteDBDao(IRemoteDBDao remoteDBDao) {
        this.remoteDBDao = remoteDBDao;
    }

    public IMessageDao getMessageDao() {
        return messageDao;
    }

    public void setMessageDao(IMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public IMessageFormatDao getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(IMessageFormatDao messageFormat) {
        this.messageFormat = messageFormat;
    }

    public IBkCliDao getBkCliDao() {
        return bkCliDao;
    }

    public void setBkCliDao(IBkCliDao bkCliDao) {
        this.bkCliDao = bkCliDao;
    }

    public IBkEveDao getBkEveDao() {
        return bkEveDao;
    }

    public void setBkEveDao(IBkEveDao bkEveDao) {
        this.bkEveDao = bkEveDao;
    }

    public IBkOpeDao getBkOpeDao() {
        return bkOpeDao;
    }

    public void setBkOpeDao(IBkOpeDao bkOpeDao) {
        this.bkOpeDao = bkOpeDao;
    }

    public ITypeMessageDao getTypeMessageDao() {
        return typeMessageDao;
    }

    public void setTypeMessageDao(ITypeMessageDao typeMessageDao) {
        this.typeMessageDao = typeMessageDao;
    }

    public LacusSmsServiceImpl() {
    }

    @Override
    public MessageFormat enregistrer(MessageFormat mf) {
        try {
            return messageFormat.create(mf); //To change body of generated methods, choose Tools | Templates.
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<MessageFormat> getAll() {
        try {
            return messageFormat.findAll(); //To change body of generated methods, choose Tools | Templates.
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public MessageFormat modifier(MessageFormat mf) {
        try {
            return messageFormat.update(mf);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerFormat(Integer id) {
        try {
            messageFormat.delete(messageFormat.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public MessageFormat getFormatById(Integer id) {
        try {
            return messageFormat.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<MessageFormat> getFormatByName(String val) {
        return null;
    }

    @Override
    public BkCli enregistrer(BkCli cli) {
        try {
            return bkCliDao.create(cli);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkCli> getAllCli() {
        try {
            return bkCliDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkCli modifier(BkCli cli) {
        try {
            return bkCliDao.update(cli);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerBkCli(String id) {
        try {
            bkCliDao.delete(bkCliDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BkCli getBkCliById(String id) {
        try {
            return bkCliDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkEve enregistrer(BkEve eve) {
        try {
            return bkEveDao.create(eve);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkEve> getAllBkEves() {
        try {
            return bkEveDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkEve modifier(BkEve eve) {
        try {
            return bkEveDao.update(eve);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerBkEve(Integer id) {
        try {
            bkEveDao.delete(bkEveDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BkEve getBkEveById(Integer id) {
        try {
            return bkEveDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkOpe enregistrer(BkOpe ope) {
        try {
            return bkOpeDao.create(ope);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkOpe> getAllBkOpes() {
        try {
            return bkOpeDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkOpe modifier(BkOpe ope) {
        try {
            return bkOpeDao.update(ope);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerBkOpe(String id) {
        try {
            bkOpeDao.delete(bkOpeDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BkOpe getBkOpeById(String id) {
        try {
            return bkOpeDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public TypeMessage enregistrer(TypeMessage tm) {
        try {
            return typeMessageDao.create(tm);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<TypeMessage> getAllTypeMessages() {
        try {
            return typeMessageDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public TypeMessage modifier(TypeMessage tm) {
        try {
            return typeMessageDao.update(tm);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerTypeMessage(Integer id) {
        try {
            typeMessageDao.delete(typeMessageDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public TypeMessage getTypeMessageById(Integer id) {
        try {
            return typeMessageDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User enregistrer(User u) {
        try {
            return userDao.create(u);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User modifier(User u) {
        try {
            return userDao.update(u);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerUser(String id) {
        try {
            userDao.delete(userDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User getUserById(String id) {
        try {
            return userDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkCli> getBkCliByName(String val) {
        return null;
    }

    @Override
    public List<BkOpe> getBkOpeByName(String val) {
        return null;
    }

    @Override
    public List<BkEve> getBkEveByName(String val) {
        return null;
    }

    @Override
    public Message enregistrer(Message msg) {
        try {
            return messageDao.create(msg);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Message> getAllMessages() {
        try {
            return messageDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Message modifier(Message msg) {
        try {
            return messageDao.update(msg);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerMessage(Integer id) {
        try {
            messageDao.delete(messageDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Message getMessageById(Integer id) {
        try {
            return messageDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<MessageFormat> getFormatByBkOpe(BkOpe bkOpe) {
        return messageFormat
                .getMessageFormatByOpe(bkOpe);
    }

    @Override
    public List<BkEve> getBkEveByEtat(String etat, Date date) {
        return bkEveDao.getBkEvesByEtat(etat, date);
    }

    @Override
    public List<BkEve> getBkEveByDate(Date date) {
        return bkEveDao.getBkEveByDate(date);
    }

    @Override
    public List<BkEve> getBkEveMaxDate() {
        return bkEveDao.getBkEveMaxDate();
    }

    @Override
    public List<BkEve> getBkEveBySendParam(boolean send) {
        return bkEveDao.getBkEveBySendParam(send);
    }

    @Override
    public BkAgence enregistrer(BkAgence a) {
        try {
            return bkAgenceDao.create(a);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkAgence> getAllBkAgences() {
        try {
            return bkAgenceDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkAgence modifier(BkAgence a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimerBkAgence(String id) {
        try {
            bkAgenceDao.delete(bkAgenceDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BkAgence getBkAgenceById(String id) {
        try {
            return bkAgenceDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public MessageFormat getFormatByBkOpe(BkOpe ope, String langue) {
        return messageFormat.getMessageFormatByOpe(ope, langue);
    }

    @Override
    public RemoteDB enregistrer(RemoteDB mf) {
        try {
            return remoteDBDao.create(mf);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<RemoteDB> getAllRemoteDB() {
        try {
            return remoteDBDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public RemoteDB modifier(RemoteDB mf) {
        try {
            return remoteDBDao.update(mf);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerRemoteDB(Integer id) {
        try {
            remoteDBDao.delete(remoteDBDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public RemoteDB getRemoteDBById(Integer id) {
        try {
            return remoteDBDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public RemoteDB getDefaultRemoteDB(boolean b) {
        return remoteDBDao.getDefaultRemoteDB(b);
    }

    @Override
    public Integer getMaxIndexBkEve() {
        Integer a = bkEveDao.getMaxIndexBkEve();
        return a == null ? 0 : a;
    }

    @Override
    public List<BkEve> getBkEveByPeriode(Date d1, Date d2) {
        return bkEveDao.getBkEveByPeriode(d1, d2);
    }

    @Override
    public List<Message> getMessageFromPeriode(Date d1, Date d2) {
        return messageDao.getMessageFromPeriode(d1, d2);
    }

    @Override
    public List<Licence> getLicences() {
        try {
            return licenceDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Licence enregistrer(Licence licence) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
            Calendar cal = Calendar.getInstance();
            licence.setStartDate(sdf.format(cal.getTime()));
            String secretKey = ConstantUtils.SECRET_KEY;
            String value = AES.decrypt(licence.getValeur(), secretKey);
            long jour = (sdf.parse(value.substring(0, 6)).getTime() - cal.getTime().getTime()) / (1000 * 60 * 60 * 24);
            licence.setJour(jour);
            return licenceDao.create(licence);
        } catch (DataAccessException | ParseException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Licence modifier(Licence licence) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
            Calendar cal = Calendar.getInstance();
            licence.setStartDate(sdf.format(cal.getTime()));
            String secretKey = ConstantUtils.SECRET_KEY;
            String value = AES.decrypt(licence.getValeur(), secretKey);
            long jour = (sdf.parse(value.substring(0, 6)).getTime() - cal.getTime().getTime()) / (1000 * 60 * 60 * 24);
            System.out.println("jour=" + jour);
            licence.setJour(jour);
            return licenceDao.update(licence);
        } catch (DataAccessException | ParseException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkTelCli> getListBkTelByCli(BkCli b) {
        return bkTelCliDao.getListBkTelByCli(b);
    }

    @Override
    public BkTelCli getListBkTelByCliDefault(BkCli b, boolean d) {
        return bkTelCliDao.getBkTelCliDefault(b, d);
    }

    @Override
    public BkTelCli enregistrer(BkTelCli bkTelCli) {
        try {
            return bkTelCliDao.create(bkTelCli);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkTelCli> getBkTelByClis() {
        try {
            return bkTelCliDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkTelCli modifier(BkTelCli b) {
        try {
            return bkTelCliDao.update(b);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimer(Integer id) {
        try {
            bkTelCliDao.delete(bkTelCliDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BkTelCli getBkTelCliById(Integer i) {
        try {
            return bkTelCliDao.findById(i);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Integer count(BkTelCli bkTelCli) {
        try {
            bkTelCliDao.count(bkTelCli);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public UrlMessage enregistrer(UrlMessage url) {
        try {
            return urlMessageDao.create(url);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<UrlMessage> getAllUrlMessages() {
        try {
            return urlMessageDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public UrlMessage modifier(UrlMessage url) {
        try {
            return urlMessageDao.update(url);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerUrlMessage(Integer id) {
        try {
            urlMessageDao.delete(urlMessageDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public UrlMessage getUrlMessageById(Integer id) {
        try {
            return urlMessageDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public UrlMessage getDefaultUrlMessage() {
        return urlMessageDao.getDefaultUrlMessage();
    }

    @Override
    public List<BkEtatOp> getListBkEtatOp(boolean actif) {
        return bkEtatOpDao.getListBkEtatOp(actif);
    }

    @Override
    public BkEtatOp enregistrer(BkEtatOp etat) {
        try {
            return bkEtatOpDao.create(etat);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkEtatOp> getAllBkEtatOps() {
        try {
            return bkEtatOpDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkEtatOp modifier(BkEtatOp etat) {
        try {
            return bkEtatOpDao.update(etat);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerBkEtatOp(Integer id) {
        try {
            bkEtatOpDao.delete(bkEtatOpDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BkEtatOp getBkEtatOpById(Integer id) {
        try {
            return bkEtatOpDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkEve> getBkEveBySendParam(boolean b, List<String> listString) {
        return bkEveDao.getBkEveBySendParam(b, listString);
    }

    @Override
    public int supprimerToutMessage() {
        try {
            messageDao.purge();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int supprimerToutBkEve() {
        try {
            bkEveDao.purge();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public BkCompCli enregistrer(BkCompCli com) {
        try {
            return bkCompCliDao.create(com);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkCompCli> getAllBkCompClis() {
        try {
            return bkCompCliDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkCompCli modifier(BkCompCli com) {
        try {
            return bkCompCliDao.update(com);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerBkCompCli(String id) {
        try {
            bkCompCliDao.delete(bkCompCliDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BkCompCli getBkCompCliById(String id) {
        try {
            return bkCompCliDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkCompCli> getBkCompCliByCli(BkCli cli) {
        try {
            return bkCompCliDao.getBkCompCliByCli(cli);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkCompCli> getBkCompCliByCli(BkCli cli, boolean actif) {
        try {
            return bkCompCliDao.getBkCompCliByCli(cli, actif);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkCli> getBkCliByCriteria(String criteria) {
        return bkCliDao.getBkCliByCriteria(criteria);
    }

    @Override
    public List<BkCompCli> getBkCompCliByCriteria(String cli) {
        try {
            return bkCompCliDao.getBkCompCliByCli(cli);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String code, String date, String compte) {
        return bkEveDao.getBkEveByCriteria(code, date, compte);
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String code) {
        return bkEveDao.getBkEveByCriteria(code);
    }

    @Override
    public int supprimerParPeriode(String date1, String date2) {
        return bkEveDao.supprimerParPeriode(date1, date2);
    }

    @Override
    public int supprimerParPeriode(Date date1, Date date2) {
        return bkEveDao.supprimerParPeriode(date1, date2);
    }

    @Override
    public List<BkCli> getBkCliLimit(int limit) {
        return bkCliDao.getBkCliLimit(limit);
    }

    @Override
    public List<BkCompCli> getBkCompCliLimit(int limit) {
        try {
            return bkCompCliDao.getBkCompCliLimit(limit);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Integer getMaxIndexBkEve(TypeEvent type) {
        Integer a = bkEveDao.getMaxIndexBkEve(type);
        return a == null ? 0 : a;
    }

    @Override
    public List<BkEve> getBkEveBySendParam(boolean send, List<String> list, TypeEvent type) {
        return bkEveDao.getBkEveBySendParam(send, list, type);
    }

    @Override
    public List<BkCompCli> getBkCompCliByCli(BkCli cli, String compte, boolean actif) {
        try {
            return bkCompCliDao.getBkCompCliByCli(cli, compte, actif);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BkCompCli getBkCompCliByCriteria(BkCli cli, String compte, boolean actif) {
        return bkCompCliDao.getBkCompCliByCriteria(cli, compte, actif);
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String code, Date date, String compte) {
        return bkEveDao.getBkEveByCriteria(code, date, compte);
    }

    @Override
    public List<BkMad> getBkMadsByPeriode(Date d1, Date d2) {
        return bkMadDao.getBkMadsByPeriode(d1, d2);
    }

    @Override
    public int supprimerBkMad(Date d1, Date d2) {
        return bkMadDao.supprimerBkMad(d1, d2);
    }

    @Override
    public List<BkMad> getbkMadsByCriteria(String val) {
        return bkMadDao.getbkMadsByCriteria(val);
    }

    @Override
    public BkMad getBkMadByCriteria(String num, String ad1p, Date date) {
        return bkMadDao.getBkMadByCriteria(num, ad1p, date);
    }

    @Override
    public Integer getMaxBkMad() {
        Integer a = bkMadDao.getMaxBkMad();
        return a == null ? 0 : a;
    }

    @Override
    public BkMad enregistrer(BkMad bkMad) {
        try {
            return bkMadDao.create(bkMad);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<BkMad> getAllBkMads() {
        try {
            return bkMadDao.findAll();
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public BkMad modifier(BkMad bkMad) {
        try {
            return bkMadDao.update(bkMad);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void supprimerBkMad(Integer id) {
        try {
            bkMadDao.delete(bkMadDao.findById(id));
        } catch (DataAccessException ex) {
        }
    }

    @Override
    public BkMad getBkMadById(Integer id) {
        try {
            return bkMadDao.findById(id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void supprimerToutBkMad() {
        try {
            bkMadDao.purge();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BkMad getBkMadByClesec(String num) {
        return bkMadDao.getBkMadByClesec(num);
    }

    @Override
    public List<BkMad> getBkMadByTraite() {
        return bkMadDao.getBkMadByTraite();
    }

    @Override
    public MessageMandat enregistrer(MessageMandat mes) {
        try {
            return messageMandatDao.create(mes);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<MessageMandat> getAllMessageMandats() {
        try {
            return messageMandatDao.findAll();
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public MessageMandat modifier(MessageMandat mes) {
        try {
            return messageMandatDao.update(mes);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerMessageMandat(Integer id) {
        try {
            messageMandatDao.delete(messageMandatDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public MessageMandat getMessageMandatById(Integer id) {
        try {
            return messageMandatDao.findById(id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<MessageMandat> getMessageMandatFromPeriode(Date d1, Date d2) {
        return messageMandatDao.getMessageFromPeriode(d1, d2);
    }

    @Override
    public int supprimerTout() {
        try {
            messageMandatDao.purge();
            return 0;
        } catch (DataAccessException ex) {
            return 0;
        }
    }

    @Override
    public List<BkMad> getBkMadByLimit(int limit) {
        return bkMadDao.getBkMadByLimit(limit);
    }

    @Override
    public List<BkEve> getBkEveByLimit(int limit) {
        return bkEveDao.getBkEveByLimit(limit);
    }

    @Override
    public List<BkEve> getBkEveByCriteria2(String code, Date date, String compte) {
        return bkEveDao.getBkEveByCriteria2(code, date, compte);
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String code, String compte, String heure, String montant) {
        return bkEveDao.getBkEveByCriteria(code, compte, heure, montant);
    }

    @Override
    public BkCli getBkCliByNumCompte(String compte) {
        try {
            return bkCompCliDao.getBkCli(compte);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<SentMail> getMailByDate(Date d) {
        return sentMailDao.getMailByDate(d);
    }

    @Override
    public SentMail saveMail(SentMail mail) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            mail.setDate2(format.format(mail.getSentDate()));
            return sentMailDao.create(mail);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<SentMail> getAllEmails() {
        try {
            return sentMailDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Config enregistrerConfig(Config c) {
        try {
            return configDao.create(c);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Config modifierConfig(Config c) {
        try {
            return configDao.update(c);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Config> getAllConfig() {
        try {
            return configDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public boolean viderLicence() {
        return licenceDao.vider();
    }

    @Override
    public Holiday saveHoliday(Holiday holiday) {
        try {
            return holidayDao.create(holiday);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void supprimerHoliday(Integer id) {
        try {
            holidayDao.delete(holidayDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Holiday> getHolidays() {
        try {
            return holidayDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Holiday getHolidayById(Integer id) {
        try {
            return holidayDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Holiday> getHolidaysByDate(String date) {
        return holidayDao.getHolidaysByDate(date);
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String code, String compte) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BkEve> getBkEveByCriteriaMontant(String code, String compte, String montant) {
        return bkEveDao.getBkEveByCriteriaMontant(code, compte, montant);
    }

    @Override
    public List<BkEve> getBkEveByPeriode(String code, String compte, Date date1, Date date2) {
        return bkEveDao.getBkEveByPeriode(code, compte, date1, date2);
    }

    @Override
    public Command enregistrer(Command cmd) {
        try {
            return commandDao.create(cmd);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Command> getAllCommands() {
        try {
            return commandDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Command> getAllCommands(Date date) {
        return commandDao.getCommandByDate(date, null, Status.ERROR);
    }

    @Override
    public Command modifier(Command cmd) {
        try {
            return commandDao.update(cmd);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerCommand(Integer id) {
        try {
            commandDao.delete(getCommandById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Command getCommandById(Integer id) {
        try {
            return commandDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void serviceRequete() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                List<Command> commands = commandDao.getCommandByStatus(Status.PENDING);
                for (Command command : commands) {
                    long numero = Long.parseLong(command.getPhone().replace(" ", ""));
                    String content = command.getContent();
                    String[] token = content.split(" ");
                    for (int i = 0; i < token.length; i++) {
                        if(token[i].toLowerCase().contains("solde")){
                            
                        }
                    }
                }
            }
        });
        t.start();
    }
    
    /*public void envoieSMSRequete(String methode, BkOpe ope, String compte) {
        BkCli bkCli = eve.getCli();
        if (bkCli != null && ope != null && !"".equals(methode)) {
            if (bkCli.isEnabled() && getBkCompCliByCriteria(bkCli, compte, true) != null && bkCli.getPhone() != 0) {
                MessageFormat mf = getFormatByBkOpe(ope, bkCli.getLangue());
                if (mf != null) {
                    String text = Utils.remplacerVariable(bkCli, eve.getOpe(), eve, mf);
                    String res = testConnexionInternet();
                    BottomPanel.settextLabel("Test connexion ...." + res, java.awt.Color.BLACK);
                    if (res.equals("OK")) {
                        BottomPanel.settextLabel("Envoie du Message à.... " + eve.getCompte(), java.awt.Color.BLACK);
                        if (methode.equals("METHO1")) {
                            App.send(urlParam, "" + bkCli.getPhone(), text);
                        } else if (methode.equals("METHO2")) {
                            App.send2(urlParam, "" + bkCli.getPhone(), text);
                        }
                    } else {
                        BottomPanel.settextLabel("Message non envoyé à.... " + eve.getCompte() + " Problème de connexion internet!!", java.awt.Color.RED);
                    }
                    Message message = new Message();
                    message.setTitle(eve.getOpe().getLib());
                    message.setContent(text);
                    message.setBkEve(eve);
                    message.setSendDate(new Date());
                    message.setNumero(Long.toString(bkCli.getPhone()));
                    if (res.equals("OK")) {
                        serviceManager.enregistrer(message);
                        eve.setSent(true);
                        serviceManager.modifier(eve);
                        BottomPanel.settextLabel("OK Message envoyé ", java.awt.Color.BLACK);
                    }
                }
            }
        }
    }*/

    @Override
    public List<Command> getCommandByStatus(Status status) {
        return commandDao.getCommandByStatus(status);
    }

    @Override
    public List<ServiceOffert> getServiceOfferts() {
        return serviceOffertDao.getServiceOfferts();
    }

    @Override
    public ServiceOffert findServiceByCode(String code) {
        return serviceOffertDao.findServiceByCode(code);
    }

    @Override
    public ServiceOffert enregistrerService(ServiceOffert s) {
        try {
            return serviceOffertDao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ServiceOffert modifierService(ServiceOffert s) {
        try {
            return serviceOffertDao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void supprimerService(Integer id) {
        try {
            serviceOffertDao.delete(serviceOffertDao.findById(id));
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ServiceOffert getServiceById(Integer id) {
        try {
            return serviceOffertDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ServiceOffert getServiceByCode(String id) {
        return serviceOffertDao.findServiceByCode(id);
    }

    @Override
    public List<CutOff> getCutOffs() {
        return cutOffDao.getCutOffs();
    }

    @Override
    public CutOff getLastCutOff(Date dateDebut) {
        return cutOffDao.getLastCutOff(dateDebut);
    }

    @Override
    public List<CutOff> getAllCutOffs() {
        try {
            return cutOffDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public CutOff getCutOffById(Integer id) {
        try {
            return cutOffDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LacusSmsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
