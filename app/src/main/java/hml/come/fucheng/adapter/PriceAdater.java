package hml.come.fucheng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hml.come.fucheng.R;
import hml.come.fucheng.net_work.NetUrl;
import hml.come.fucheng.moudle.PriceData;

/**
 * Created by TX on 2017/7/21.
 */

public class PriceAdater extends BaseAdapter {
    private List<PriceData.Data> list;
    private Context context;
    public PriceAdater(Context context, List<PriceData.Data> list){
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
        ViewHold viewHold;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.price_list_item, parent, false);
            viewHold = new ViewHold();
            viewHold.car_image = (ImageView)convertView.findViewById(R.id.car_image);
            viewHold.car_name = (TextView)convertView.findViewById(R.id.car_name);
            viewHold.manufacturers_price = (TextView)convertView.findViewById(R.id.manufacturers_price);
            viewHold.dealer_pricing = (TextView)convertView.findViewById(R.id.dealer_pricing);
            convertView.setTag(viewHold);
        }else {
            viewHold = (ViewHold) convertView.getTag();
        }
        Picasso.with(context).load(NetUrl.HEAD + list.get(position).thumbnail).into(viewHold.car_image);
        viewHold.car_name.setText(list.get(position).car_name);
        viewHold.manufacturers_price.setText("新车市场价：" + list.get(position).manufacturers_price + ".00");
        viewHold.dealer_pricing.setText(list.get(position).dealer_pricing);
        return convertView;
    }
    public class ViewHold{
        private ImageView car_image;
        private TextView car_name;
        private TextView manufacturers_price;
        private TextView dealer_pricing;
    }
}
