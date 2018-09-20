package id.co.wassaph.aladhine.wassaph.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.wassaph.aladhine.wassaph.R;
import id.co.wassaph.aladhine.wassaph.manager.AppData;
import id.co.wassaph.aladhine.wassaph.model.MessageModel;

/**
 * Created by aladhine on 01/11/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MessageModel> messages;
    //private List<Message> messagesO;
    private Context mContext;
    //private Filter filter;
    private String myName;
    public final int isYou = 1, isOther = 0, isImage = 2;

    public static class MyViewHolderOther extends RecyclerView.ViewHolder {
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtMessage)
        TextView txtMessage;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;

        public MyViewHolderOther(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class MyViewHolderYou extends RecyclerView.ViewHolder {
        @BindView(R.id.txtMessage)
        TextView txtPesan;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;

        public MyViewHolderYou(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class MyViewHolderImage extends RecyclerView.ViewHolder {
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.imgMessage)
        ImageView imgMessage;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;

        public MyViewHolderImage(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public MessageAdapter(Context context, List<MessageModel> messageList, String name){
        this.messages = messageList;
        mContext = context;
        this.myName = name;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case isYou:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message_layout_you, parent, false);
                return new MyViewHolderYou(view);
            case isImage:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message_layout_image, parent, false);
                return new MyViewHolderImage(view);
            case isOther:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message_layout, parent, false);
                return new MyViewHolderOther(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Layouting default
        float d = mContext.getResources().getDisplayMetrics().density;
        int top = (int)(5 * d), left = (int)(100 * d), bottom = (int)(5 * d), right = (int)(5 * d); // margin in pixels

        MessageModel messageBefore = new MessageModel();
        MessageModel message = this.messages.get(position);
        MessageModel messageNext = new MessageModel();
        try{
            messageNext = this.messages.get(position + 1);
            messageBefore = this.messages.get(position - 1);
        }catch (Exception e){

        }

        String decryptedName = message.getFromMessage();
        String decryptedPesan = message.getTextMessage();

        if (decryptedName.equals(this.myName)) {
            final MyViewHolderYou myViewHolder = (MyViewHolderYou) holder;
            myViewHolder.txtPesan.setText(decryptedPesan);
            myViewHolder.txtPesan.requestLayout();
            myViewHolder.linearLayout.requestLayout();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)myViewHolder.linearLayout.getLayoutParams();
            int bottomNew = (int)(1 * d);
            params.setMargins(left, top, right, bottomNew);
            myViewHolder.linearLayout.setLayoutParams(params);

            if(isOnlyEmoji(decryptedPesan)){
                if(decryptedPesan.equals("❤")){
                    myViewHolder.txtPesan.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                    myViewHolder.txtPesan.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                }else{
                    myViewHolder.txtPesan.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
                    myViewHolder.txtPesan.setTextColor(Color.WHITE);
                }
                myViewHolder.linearLayout.setBackgroundColor(Color.TRANSPARENT);
            }else{
                myViewHolder.txtPesan.setTextColor(Color.WHITE);
                myViewHolder.txtPesan.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                myViewHolder.linearLayout.setBackgroundColor(Color.parseColor("#E91E63"));
                if(messageNext.getFromMessage().equals(this.myName) && messageBefore.getFromMessage().equals(this.myName)){
                    int topThis = (int)(1 * d), bottomThis = topThis;
                    params.setMargins(left, topThis, right, bottomThis);
                    myViewHolder.linearLayout.setLayoutParams(params);
                }else if(messageNext.getFromMessage().equals(this.myName)){
                    int bottomThis = (int)(1 * d);
                    params.setMargins(left, top, right, bottomThis);
                    myViewHolder.linearLayout.setLayoutParams(params);
                }else if(messageBefore.getFromMessage().equals(this.myName)){
                    int topThis = (int)(1 * d);
                    params.setMargins(left, topThis, right, bottom);
                    myViewHolder.linearLayout.setLayoutParams(params);
                }
                myViewHolder.linearLayout.setBackgroundResource(R.drawable.message_bubble_you);
            }
        } else if(message.getTypeMessage() == AppData.imageMessage) {
            final MyViewHolderImage myViewHolder = (MyViewHolderImage) holder;
            myViewHolder.txtName.setText(decryptedName);
            Glide.with(mContext).load(decryptedPesan).into(myViewHolder.imgMessage);
            myViewHolder.imgMessage.requestLayout();
            myViewHolder.linearLayout.requestLayout();
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)myViewHolder.linearLayout.getLayoutParams();
            int bottomNew = (int)(1 * d);
            params.setMargins(right, top, left, bottomNew);
            myViewHolder.linearLayout.setLayoutParams(params);
        } else {
            final MyViewHolderOther myViewHolder = (MyViewHolderOther) holder;
            myViewHolder.txtName.setText(decryptedName);
            myViewHolder.txtMessage.setText(decryptedPesan);
            myViewHolder.txtMessage.requestLayout();
            myViewHolder.linearLayout.requestLayout();
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)myViewHolder.linearLayout.getLayoutParams();
            int bottomNew = (int)(1 * d);
            params.setMargins(right, top, left, bottomNew);
            myViewHolder.linearLayout.setLayoutParams(params);

            if(isOnlyEmoji(decryptedPesan)){
                if(decryptedPesan.equals("❤")){
                    myViewHolder.txtMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                    myViewHolder.txtMessage.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                }else{
                    myViewHolder.txtMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
                    myViewHolder.txtMessage.setTextColor(Color.BLACK);
                }
                myViewHolder.txtName.setVisibility(View.GONE);
                myViewHolder.linearLayout.setBackgroundColor(Color.TRANSPARENT);
            }else{
                myViewHolder.txtMessage.setTextColor(Color.BLACK);
                myViewHolder.txtMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                myViewHolder.linearLayout.setBackgroundColor(Color.WHITE);
                myViewHolder.txtName.setVisibility(View.VISIBLE);
                if(messageNext.getFromMessage().equals(message.getFromMessage()) && messageBefore.getFromMessage().equals(message.getFromMessage())){ //Midle of list
                    int topThis = (int)(1 * d), bottomThis = topThis;
                    params.setMargins(right, topThis, left, bottomThis);
                    myViewHolder.linearLayout.setLayoutParams(params);
                    myViewHolder.txtName.setVisibility(View.GONE);
                }else if(messageNext.getFromMessage().equals(message.getFromMessage())){ //Top of list
                    int bottomThis = (int)(1 * d);
                    params.setMargins(right, top, left, bottomThis);
                    myViewHolder.linearLayout.setLayoutParams(params);
                    myViewHolder.txtName.setVisibility(View.VISIBLE);
                }else if(messageBefore.getFromMessage().equals(message.getFromMessage())){ //bottom of list
                    int topThis = (int)(1 * d);
                    params.setMargins(right, topThis, left, bottom);
                    myViewHolder.linearLayout.setLayoutParams(params);
                    myViewHolder.txtName.setVisibility(View.GONE);
                }
                myViewHolder.linearLayout.setBackgroundResource(R.drawable.message_bubble);
            }
        }
    }

    @Override
    public int getItemCount() { return messages == null ? 0 : messages.size(); }

    @Override
    public int getItemViewType(int position) {
        MessageModel message = messages.get(position);
        String nama = message.getFromMessage();
        int type;
        if (nama.equals(this.myName)) {
            type = isYou;
        } else if(message.getTypeMessage() == AppData.imageMessage){
            type = isImage;
        } else {
            type = isOther;
        }
        return type;
    }

    public boolean isOnlyEmoji(String pesan){
        //String regex = "([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])";
        pesan = pesan.replaceAll("([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])", "");

        if(pesan.equals(""))
            return true;
        else
            return false;
    }
}
