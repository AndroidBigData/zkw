package com.zjwam.zkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjwam.zkw.R;
import com.zjwam.zkw.entity.PersonalNoteBookBean;

public class PersonalMineNoteBookAdapter extends ListBaseAdapter<PersonalNoteBookBean.getNoteBookItem> {

    private LayoutInflater mLayoutInflater;
    private deleteNoteBook deleteNoteBook;

    public PersonalMineNoteBookAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.mine_notebook_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final PersonalNoteBookBean.getNoteBookItem  items = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mine_notebook_name.setText(items.getName());
        viewHolder.mine_notebook_vname.setText(items.getVname());
        viewHolder.mine_notebook_note.setText(items.getNote());
        viewHolder.mine_notebook_vtime.setText(items.getVtime());
        viewHolder.mine_notebook_addtime.setText(items.getAddtime());
        viewHolder.mine_notebook_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNoteBook.OnDeleteNoteBook(items.getId(),position);
            }
        });
    }
    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mine_notebook_name,mine_notebook_vname,mine_notebook_note,mine_notebook_vtime,mine_notebook_addtime,mine_notebook_del;

        public ViewHolder(View itemView) {
            super(itemView);
            mine_notebook_name = itemView.findViewById(R.id.mine_notebook_name);
            mine_notebook_vname = itemView.findViewById(R.id.mine_notebook_vname);
            mine_notebook_note = itemView.findViewById(R.id.mine_notebook_note);
            mine_notebook_vtime = itemView.findViewById(R.id.mine_notebook_vtime);
            mine_notebook_addtime = itemView.findViewById(R.id.mine_notebook_addtime);
            mine_notebook_del = itemView.findViewById(R.id.mine_notebook_del);
        }
    }
    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if(position != (getDataList().size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,this.mDataList.size()-position);
        }
    }
    public void onDeleteNoteBook(deleteNoteBook deleteNoteBook){
        this.deleteNoteBook = deleteNoteBook;
    }
    public interface deleteNoteBook{
        void OnDeleteNoteBook(int noteId,int position);
    }
}
