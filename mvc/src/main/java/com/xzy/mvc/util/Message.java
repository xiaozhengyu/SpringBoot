package com.xzy.mvc.util;

/**
 * @author xzy
 * @date 2020-10-26 09:59
 * 说明：{ "status":xxx,"message":"xxx","ok":xxx}
 */
public class Message {

    public static final int STATUS_OK = 1;
    public static final int STATUS_FAIL = 0;

    public static final String MESSAGE_OK = "成功";
    public static final String MESSAGE_FAIL = "失败";

    public static final boolean BOOLEAN_OK = true;
    public static final boolean BOOLEAN_FAIL = false;

    protected int status;
    protected String message;
    protected boolean ok;

    /*Constructor*/

    public Message() {
        this.status = STATUS_OK;
        this.message = MESSAGE_OK;
        this.ok = BOOLEAN_OK;
    }

    public Message(int status, String message, boolean ok) {
        this.status = status;
        this.message = message;
        this.ok = ok;
    }

    public Message(boolean ok) {
        this.ok = ok;
        this.status = ok ? STATUS_OK : STATUS_FAIL;
        this.message = ok ? MESSAGE_OK : MESSAGE_FAIL;
    }

    /*Setter And Getter*/

    public Message setStatus(int status) {
        this.status = status;
        return this;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public Message setOk(boolean ok) {
        this.ok = ok;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean getOk() {
        return ok;
    }

    /*Analyzer*/

    public boolean isOk() {
        return ok;
    }

    public boolean isFail() {
        return !ok;
    }

    /*Converter*/

    public void toOk() {
        this.status = STATUS_OK;
        this.message = MESSAGE_OK;
        this.ok = BOOLEAN_OK;
    }

    public void toFail() {
        this.status = STATUS_FAIL;
        this.message = MESSAGE_FAIL;
        this.ok = BOOLEAN_FAIL;
    }

    /*Static method*/

    /**
     * @param message - 成功提示信息
     * @return { "status":1,"message":“成功提示信息”,"ok":true}
     */
    public static Message ok(String message) {
        return new Message(STATUS_OK, message, BOOLEAN_OK);
    }

    /**
     * @return - { "status":1,"message":"成功","ok":true}
     */
    public static Message ok() {
        return new Message(STATUS_OK, MESSAGE_OK, BOOLEAN_OK);
    }

    /**
     * @param message - 失败提示信息
     * @return - { "status":0,"message":"失败提示信息","ok":false}
     */
    public static Message fail(String message) {
        return new Message(STATUS_FAIL, message, BOOLEAN_FAIL);
    }

    /**
     * @return - { "status":0,"message":"失败","ok":false}
     */
    public static Message fail() {
        return new Message(STATUS_FAIL, MESSAGE_FAIL, BOOLEAN_FAIL);
    }

    @Override
    public String toString() {
        return "\n{" +
                "    \"status\": " + status + ",\n" +
                "    \"ok\": " + ok + ",\n" +
                "    \"message\": " + message + "\n" +
                "}\n";
    }
}
