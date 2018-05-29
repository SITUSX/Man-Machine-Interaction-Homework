package config;

/**
 * Created by huangxiao on 2017/6/1.
 */
public enum ResultMessage {
    SUCCESS("success"),
    USER_EXISTS("user_exists"),
    USER_NOT_EXISTS("user_not_exists"),
    PASSWORD_ERROR("password_error"),;

    private String message;

    ResultMessage(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
