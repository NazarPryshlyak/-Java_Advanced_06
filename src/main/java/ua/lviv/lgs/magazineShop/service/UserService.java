package ua.lviv.lgs.magazineShop.service;

import ua.lviv.lgs.magazineShop.daos.DAOAbstractCRUD;
import ua.lviv.lgs.magazineShop.daos.DAOException;
import ua.lviv.lgs.magazineShop.domain.User;

public interface UserService extends DAOAbstractCRUD<User> {

    User readByEmail(String email) throws DAOException;

    boolean updateByEmail(User t) throws DAOException;

}