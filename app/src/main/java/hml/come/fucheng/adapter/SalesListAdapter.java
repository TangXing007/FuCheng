package hml.come.fucheng.adapter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import hml.come.fucheng.R;
import hml.come.fucheng.moudle.SalesManagementListData;
import hml.come.fucheng.net_work.NetUrl;

/**
 * Created by TX on 2017/8/10.
 */

public class SalesListAdapter extends BaseAdapter {
    private List<SalesManagementListData> list;
    private Context context;
    public SalesListAdapter(Context context, List<SalesManagementListData> list){
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.sales_list_item, parent, false);
            viewHold = new ViewHold();
            viewHold.imageView = (ImageView)convertView.findViewById(R.id.sales_list_image);
            viewHold.car_name = (TextView)convertView.findViewById(R.id.sales_car_name);
            viewHold.new_car_price = (TextView)convertView.findViewById(R.id.sales_new_car_guided);
            viewHold.recycl_price = (TextView)convertView.findViewById(R.id.recycl_price);
            viewHold.time = (TextView)convertView.findViewById(R.id.sales_time);
            convertView.setTag(viewHold);
        }else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context).load(NetUrl.HEAD + list.get(position).list.thumbnail).into(viewHold.imageView);
        viewHold.car_name.setText(list.get(position).list.car_name);
        viewHold.new_car_price.setText(list.get(position).list.manufacturers_price);
        viewHold.recycl_price.setText(list.get(position).list.dealer_pricing);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long times = Long.parseLong(list.get(position).list.creatime);
        String str = dateFormat.format(new Date(times * 1000));
        viewHold.time.setText(str);
        return convertView;
    }

    public class ViewHold{
        private ImageView imageView;
        private TextView car_name;
        private TextView new_car_price;
        private TextView recycl_price;
        private TextView time;
    }
}
