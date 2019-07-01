package data.model.Message;

import org.json.JSONObject;

public class LoginUserMessage {
    private String name;
    private String surname;
    private String email;
    private String uid;

    public LoginUserMessage(String name, String surname, String email, String uid) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public static LoginUserMessage parse(Object payload) {
        JSONObject jsonObject = (JSONObject) payload;
        return new LoginUserMessage(
                jsonObject.has("name") ? jsonObject.getString("name") : "",
                jsonObject.has("surname") ? jsonObject.getString("surname") : "",
                jsonObject.has("email") ? jsonObject.getString("email") : "",
                jsonObject.has("uid") ? jsonObject.getString("uid") : ""
        );
    }

    @Override
    public String toString() {
        return "LoginUserMessage{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
