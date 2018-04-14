/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Email;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ushiho
 */
@Stateless
public class EmailFacade extends AbstractFacade<Email> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmailFacade() {
        super(Email.class);
    }

    public Email generatePassword(String login, String pass, int type) {
        Email email = clone(findByType(type));
        email.setContenu(email.getContenu()
                + " Votre login :'" + login + "' et mot de passe : '" + pass + "'");
        return email;
    }

    public Email verifyEmail(String pass, int type) {
        Email email = clone(findByType(type));
        email.setContenu(email.getContenu() + "'" + pass + "'");
        return email;
    }

    public int save(Email email) {
        if (email != null) {
            create(email);
            return 1;
        }
        return -1;
    }

    public void modify(Email anEmail, Email nvEmail) {
    }

    public void delete(Email Email) {
    }

    public Email findByType(int type) {
        return getUniqueResult("SELECT e FROM Email e WHERE e.type = '" + type + "'");
    }

    public Email clone(Email email) {
        Email clone = new Email(email.getSubject(), email.getContenu(), email.getType());
        clone.setDgi(email.getDgi());
        return clone;
    }
}
