/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.utils;

import edu.esprit.models.User;

/**
 *
 * @author yjaballi
 */
public class UserManager {
     private static User user;
    private UserManager(){   
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserManager.user = user;
    }
    
}
