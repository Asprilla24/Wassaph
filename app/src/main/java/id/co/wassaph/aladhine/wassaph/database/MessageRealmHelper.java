package id.co.wassaph.aladhine.wassaph.database;

import android.util.Log;

import java.util.ArrayList;

import id.co.wassaph.aladhine.wassaph.model.MessageModel;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by aladhine on 01/11/17.
 */

public class MessageRealmHelper {
    private static final String TAG = "MessageRealmHelper";

    private Realm realm;

    public MessageRealmHelper(Realm mRealm) {
        realm = mRealm;
    }

    public void addMessage(MessageModel message) {
        MessageRealmObject listObj = new MessageRealmObject();
        listObj.setIdMessage(getMessageId());
        listObj.setFromMessage(message.getFromMessage());
        listObj.setTextMessage(message.getTextMessage());
        listObj.setTypeMessage(message.getTypeMessage());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(listObj);
        realm.commitTransaction();
    }

    public ArrayList<MessageModel> getListMessage() {
        ArrayList<MessageModel> data = new ArrayList<>();

        RealmResults<MessageRealmObject> rCall = realm.where(MessageRealmObject.class).findAll();
        if(rCall.size() > 0)
            showLog("Size : " + rCall.size());

        for (int i = 0; i < rCall.size(); i++) {
            data.add(new MessageModel(rCall.get(i)));
        }

        return data;
    }

    public int getMessageId(){
        RealmResults<MessageRealmObject> rCall = realm.where(MessageRealmObject.class).findAll();

        return rCall.size() + 1;
    }

    public void clearMessage(){
        RealmResults<MessageRealmObject> data = realm.where(MessageRealmObject.class).findAll();
        realm.beginTransaction();
        data.clear();
        realm.commitTransaction();
    }

    private void showLog(String s) {
        Log.d(TAG, s);
    }
}
