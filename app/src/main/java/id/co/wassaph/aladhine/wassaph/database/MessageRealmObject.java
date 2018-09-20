package id.co.wassaph.aladhine.wassaph.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aladhine on 01/11/17.
 */

public class MessageRealmObject extends RealmObject {
    @PrimaryKey
    private int idMessage;
    private String textMessage;
    private String fromMessage;
    private int typeMessage;

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
