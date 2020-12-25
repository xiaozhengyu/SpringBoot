package com.xzy.mvc.util;

/**
 * @param <T>
 * @author xzy
 * @date 2020-10-26 10:23
 * 说明：{ "status":xxx,"message":"xxx","ok":xxx,"data":xxx}
 */

public class MessageBox<T> extends Message {
    protected T data;

    /*Constructor*/

    public MessageBox() {
        super();
        this.data = null;
    }

    public MessageBox(T data) {
        this(BOOLEAN_OK, data);
    }

    public MessageBox(boolean ok, T data) {
        super(BOOLEAN_OK);
        this.data = data;
    }

    public MessageBox(int status, String message, boolean ok, T data) {
        super(status, message, ok);
        this.data = data;
    }

    /*Setter And Getter*/

    public T getData() {
        return data;
    }

    public MessageBox<T> setData(T data) {
        this.data = data;
        return this;
    }

    /*Static method*/

    /**
     * @return - { "status":1,"message":"成功","ok":true,"data":xxx}
     */
    public static <E> MessageBox<E> ok(E data) {
        return new MessageBox<>(BOOLEAN_OK, data);
    }

    /**
     * @return - { "status":0,"message":"失败","ok":false,"data":xxx}
     */
    public static <E> MessageBox<E> fail(E data) {
        return new MessageBox<>(BOOLEAN_FAIL, data);
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"status\": " + status + ",\n" +
                "    \"ok\": " + ok + ",\n" +
                "    \"message\": " + message + ",\n" +
                "    \"data\": " + data.toString() + "\n" +
                "}\n";
    }
}
