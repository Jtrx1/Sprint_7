package org.example.model;

public class DeleteCourierResponse {
    private Boolean ok;
    private String message;

    public DeleteCourierResponse(Boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
