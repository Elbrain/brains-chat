package com.geekbrains.server;

import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {

    private final DBconnection dBconnection = DBconnection.getInstance();

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return dBconnection.findByLoginAndPass(login, password);
    }

    @Override
    public boolean updateNickname(String oldNickname, String newNickname){
        int result = dBconnection.updateNickname(oldNickname, newNickname);
        return result != 0;
    }
}
