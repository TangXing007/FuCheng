package hml.come.fucheng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import hml.come.fucheng.R;
import hml.come.fucheng.moudle.ContractData;

/**
 * Created by TX on 2017/8/11.
 */

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ViewHold>
        implements View.OnClickListener{
    private ArrayList<ContractData.Data> list;
    private OnItemClickListener monItemClickListener = null;
    public ContractAdapter(ArrayList<ContractData.Data> list){
        this.list = list;
    }

    public static interface OnItemClickListener{
        void onItemClick(View view, int postion);
    }

    @Override
    public void onClick(View v) {
        if (monItemClickListener != null){
            monItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.monItemClickListener = listener;
    }

    @Override
    public ContractAdapter.ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contract_list_item,
                parent, false);
        ViewHold vh = new ViewHold(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ContractAdapter.ViewHold holder, int position) {
        holder.textView.setText(list.get(position).car_name);
        holder.view.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHold extends RecyclerView.ViewHolder {
        public TextView textView;
        public View view;
        public ViewHold(View itemView) {
            super(itemView);
            view = itemView;
            textView = (TextView)itemView.findViewById(R.id.contract_text);
        }
    }
}
