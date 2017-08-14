package hml.come.fucheng.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hml.come.fucheng.R;
import hml.come.fucheng.moudle.BuyChooseCarData;
import hml.come.fucheng.net_work.NetUrl;

/**
 * Created by TX on 2017/7/18.
 */

public class ParentListAdapter extends BaseAdapter {
    private ArrayList<BuyChooseCarData.Content> list;
    private Context context;
    public ParentListAdapter(Context context, ArrayList<BuyChooseCarData.Content> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BuyChooseCarData.Content item = list.get(position);
        ViewHold viewHold;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.parent_item_view, parent, false);
            viewHold = new ViewHold();
            viewHold.textView = (TextView)convertView.findViewById(R.id.list_parent_text);
            viewHold.imageIcon = (ImageView) convertView.findViewById(R.id.image_icon);
            viewHold.line = (View)convertView.findViewById(R.id.line);
            viewHold.list_view = (LinearLayout)convertView.findViewById(R.id.list_item);
            convertView.setTag(viewHold);
        }else {
            viewHold = (ViewHold)convertView.getTag();
        }
        if(item.flag){
            viewHold.imageIcon.setVisibility(View.GONE);
            viewHold.line.setVisibility(View.GONE);
            viewHold.textView.setText(item.name);
            viewHold.textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHold.textView.setTextSize(18);
            viewHold.list_view.setClickable(true);
        }else{
            viewHold.line.setVisibility(View.VISIBLE);
            viewHold.imageIcon.setVisibility(View.VISIBLE);
            Picasso.with(context).load(NetUrl.TEST_TX_HEAD + item.thumbnail).into(viewHold.imageIcon);
            viewHold.textView.setText(item.name);
            viewHold.textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            viewHold.list_view.setClickable(false);
        }
        return convertView;
    }
    public class ViewHold{
        private TextView textView;
        private ImageView imageIcon;
        private View line;
        private LinearLayout list_view;
    }
}
