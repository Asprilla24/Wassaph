package id.co.wassaph.aladhine.wassaph.model;

import id.co.wassaph.aladhine.wassaph.database.MessageRealmObject;

/**
 * Created by aladhine on 01/11/17.
 */

public class MessageModel {
    private int idMessage;
    private String textMessage;
    private String fromMessage;
    private int typeMessage;

    public MessageModel() {
        this.textMessage = "";
        this.fromMessage = "";
    }

    public MessageModel(String mTextMessage, String mFromMessage, int mTypeMessage){
        this.textMessage = mTextMessage;
        this.fromMessage = mFromMessage;
        this.typeMessage = mTypeMessage;
    }

    public MessageModel(MessageRealmObject obj){
        this.idMessage = obj.getIdMessage();
        this.textMessage = obj.getTextMessage();
        this.fromMessage = obj.getFromMessage();
        this.typeMessage = obj.getTypeMessage();
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getFromMessage() {
        return fromMessage;
    }

    public void setFromMessage(String fromMessage) {
        this.fromMessage = fromMessage;
    }

    public int getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(int typeMessage) {
        this.typeMessage = typeMessage;
    }
}
