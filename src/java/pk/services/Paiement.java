/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.services;

import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.hibernate.Session;
import pk.dao.NewHibernateUtil;
import pk.models.Carte;
import pk.models.Transaction;

/**
 *
 * @author IRADA
 */
@WebService(serviceName = "Paiement")
public class Paiement {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "payerCommande")
    public String payerCommande(@WebParam(name = "nocart") long nocart, @WebParam(name = "datevalid") String datevalid, @WebParam(name = "crypto") int crypto, @WebParam(name = "mnt") float mnt) {
        
        Session s=NewHibernateUtil.getSession();
        Carte c=(Carte) s.get(Carte.class,nocart);
        if(c!=null&&c.getDatevalid().equals(datevalid)&&crypto==c.getCrypto()){
        Transaction tx =new Transaction(0,c,new Date(),mnt);
        s.beginTransaction();
        s.save(tx);
        s.getTransaction().commit();
        return "Ok. "+tx.getIdtx();
        
        }else{
        return "invalid payment information";
        }
        
        
        
    }
}
