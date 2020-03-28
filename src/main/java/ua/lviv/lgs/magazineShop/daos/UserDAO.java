package ua.lviv.lgs.magazineShop.daos;

import ua.lviv.lgs.magazineShop.domain.User;

public interface UserDAO extends DAOAbstractCRUD<User>{

    User readByEmail(String email) throws DAOException;

    boolean updateByEmail(User t) throws DAOException;

}