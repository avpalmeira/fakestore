package com.honeywell.rtcmobile.model;

public class User {

    private String name,
                   Matricula,
                   Senha,
                   token;

    public User(String Matricula, String Senha) {

        this.Matricula = Matricula;
        this.Senha = Senha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "user:"+getName()+"\n"+
                "matricula:"+getMatricula()+"\n"+
                "senha:"+getSenha();
    }
}
