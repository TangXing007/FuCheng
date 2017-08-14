package hml.come.fucheng.moudle;

import java.util.ArrayList;

/**
 * Created by TX on 2017/8/9.
 */

public class CarListData {
    public Msg xinxi;
    public class Msg{
        public String enterprise_name;
    }
    public ArrayList<Data> car_list;
    public class Data{
        public boolean check;
        public String aid;
        public String car_name;
        public String dealer_pricing;
    }
}
