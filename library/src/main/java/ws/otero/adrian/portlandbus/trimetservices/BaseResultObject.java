package ws.otero.adrian.portlandbus.trimetservices;

public class BaseResultObject {

    private String errorMessage;

    public BaseResultObject() {

    }

    public BaseResultObject(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
