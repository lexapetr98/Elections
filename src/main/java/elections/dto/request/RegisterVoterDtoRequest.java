package elections.dto.request;

import java.io.Serializable;


public class RegisterVoterDtoRequest implements Serializable {
    private String name;
    private String surname;
    private String patronymic;
    private String street;
    private String house;
    private String flat;
    private String login;
    private String password;


    public RegisterVoterDtoRequest(String name, String surname, String patronymic, String street, String house, String flat, String login, String password) {
        setName(name);
        setSurname(surname);
        setPatronymic(patronymic);
        setStreet(street);
        setHouse(house);
        setFlat(flat);
        setLogin(login);
        setPassword(password);
    }

    public RegisterVoterDtoRequest(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
