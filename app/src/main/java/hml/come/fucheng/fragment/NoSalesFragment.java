package hml.come.fucheng.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hml.come.fucheng.R;

/**
 * Created by TX on 2017/8/11.
 */

public class NoSalesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstateInstate){
        super.onCreateView(inflater, viewGroup, savedInstateInstate);
        View view = inflater.inflate(R.layout.sales_fragment, viewGroup, false);
        return view;
    }
}
