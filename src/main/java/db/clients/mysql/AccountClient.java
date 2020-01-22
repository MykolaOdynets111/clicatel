package db.clients.mysql;

import db.entities.mysql.Account;
import db.enums.Sessions;

import static db.clients.HibernateBaseClient.addEntity;
import static db.clients.HibernateBaseClient.getEntityById;
import static db.repository.mysql.AccountRepo.getAccount;

/**
 * Class DB client, based client wrapper, needs to be used in case of wrap big amount of DB queries
 * The class is a DSL layer at ones
 * <p>
 * Such methods can be used directly or via client or control wrappers
 * <p>
 *
 * @author ksichenko
 */

public class AccountClient {

    public static Account getAccountById(final Sessions sqlSessionsName,
                                         final String accountId) {

        return getEntityById(sqlSessionsName, accountId, Account.class);
    }

    public static String getAccountNameById(final Sessions sqlSessionsName,
                                            final String accountId) {

        return getEntityById(sqlSessionsName, accountId, Account.class).getName();
    }

    public static Integer getCreatedAccountId(final Sessions sqlSessionsName) {

        return (Integer) addEntity(sqlSessionsName, getAccount());
    }
}
