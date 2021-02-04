package resources;
import db.UserDAO;

import javax.ws.rs.GET;

public class UserResource {
    UserDAO userDAO;
    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    public String getEmail(String table, String id){
        return userDAO.getEmail(table, id);
    }

}
