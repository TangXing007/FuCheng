package hml.come.fucheng.moudle;

import java.util.ArrayList;

import hml.come.fucheng.net_work.BaseResult;

/**
 * Created by TX on 2017/8/11.
 */

public class ContractData extends BaseResult{
    public ArrayList<Data> data;
    public class Data{
        public String pid;
        public String car_name;
    }
}
