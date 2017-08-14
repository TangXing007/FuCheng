package hml.come.fucheng.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hml.come.fucheng.R;

/**
 * Created by TX on 2017/7/18.
 */

public class OrderCarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInsatanceState){
        View view = inflater.inflate(R.layout.fragment_order_car, viewGroup, false);
        return view;
    }
}
