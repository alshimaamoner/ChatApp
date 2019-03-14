package com.route.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.route.DataHolder;
import com.route.Model.ChatRoom;
import com.route.Model.Message;
import com.route.chatappg1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 2/22/2019.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class ChatThreadAdapter extends RecyclerView.Adapter<ChatThreadAdapter.ViewHolder> {

    List<Message> messages;


    public static final int INCOMING=20;
    public static final int OUGOING=30;

    public ChatThreadAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        Message message=messages.get(position);
        if(message.getSenderId().equals(DataHolder.currentUser.getId())){
            return OUGOING;
        }
        return INCOMING;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view=null;
        if(viewType==INCOMING)
            view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_item_incoming_message,viewGroup,false);
        else if(viewType==OUGOING)
            view=LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_item_outgoing_message,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {

        Message message=messages.get(pos);

        int ViewType=getItemViewType(pos);
            viewHolder.content.setText(message.getContent());
            viewHolder.time.setText(message.getTimeStamp());
         if(ViewType==INCOMING){
            viewHolder.senderName.setText(message.getSenderName());
        }


    }



    public void changeData(List<Message> messages){
        this.messages=messages;
        notifyDataSetChanged();
    }

    public void AddMessage(Message message){
        if(messages==null)messages=new ArrayList<>();
        messages.add(message);
        notifyItemChanged(messages.size()-1);
    }

    @Override
    public int getItemCount() {
        if(messages==null)return 0;
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView senderName;
        TextView content;
        TextView time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName=itemView.findViewById(R.id.sender_name);
            content=itemView.findViewById(R.id.content);
            time=itemView.findViewById(R.id.time);
        }
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int pos, ChatRoom room);
    }
}
