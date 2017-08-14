package hml.come.fucheng.net_work;

import hml.come.fucheng.moudle.SalesManagementData;

/**
 * Created by TX on 2017/7/20.
 */

public class NetUrl {
    public static final String FC = "/fuchengService";
    public static final String HEAD = "http://www.fuchengqiche.org";
    public static final String TEST_TX_HEAD = "http://192.168.1.105:8080";
    //public static final String TEST_ZZG_HEAD = "http://192.168.1.8:8080/fuchengService/";
    public static final String TEST_LF_HEAD = "http://192.168.1.110:8080";
    public static final String LAND = TEST_TX_HEAD +"user/userLogin.action";
    public static final String IMAGE_COMMIT = TEST_TX_HEAD + "/member/addUserMember.action";
    public static final String PERSON = TEST_TX_HEAD + "/user/uploadHead.action";
    public static final String SHOU_YE_CAR_BIAO_ZHI = TEST_TX_HEAD + "/car/getCarBrandList_tuijian.action";
    public static final String SHOU_YE_CAR_IMAGE = TEST_TX_HEAD + "/car/getCarCategoryList_tuijian.action";
    public static final String JXS_JIN_RONG = TEST_TX_HEAD + "/loan/findLoanInfoList.action";
    public static final String RONG_ZI_ZU_LIN = TEST_TX_HEAD + "/financingLease/findFinancingLease.action";
    public static final String BANK = TEST_TX_HEAD + "/bankMortgage/findBankMortgageList.action";
    public static final String PRICE = TEST_TX_HEAD + "/car/getCarCategoryListByPrice.action";
    public static final String CAR_WATCH = TEST_TX_HEAD + "/car/getCar.action";
    public static final String COLOR = TEST_TX_HEAD + "/car/CarColorList.action";
    public static final String PINPAI_CHOSE = TEST_TX_HEAD + "/car/getCarCategoryListByBrand.action";
    public static final String BUY_CHOOSE_CAR = TEST_TX_HEAD + "/car/getCarBrandList.action";
    public static final String USER_LANDING = TEST_TX_HEAD +  "/user/userLogin.action";
    public static final String YAN_ZHENG_MA = HEAD + "/ceshi/sendmobile";
    public static final String USER_ZHU_CE = HEAD + "/user/gr_add";
    public static final String FORGET_PASSWORD = HEAD + "/login/gr_wj";
    public static final String PERSON_DATA = TEST_TX_HEAD + "/user/findUserByid.action";
    public static final String AMEND_NAME = TEST_TX_HEAD + "/user/updateName.action";
    public static final String ADD_CAR = TEST_TX_HEAD + "/car/collectCar.action";
    public static final String INTENTION_CAR = TEST_TX_HEAD + "/car/getCarLikeList.action";
    public static final String ALTER_PASSWORD = TEST_TX_HEAD + "/user/upPassword.action";
    public static final String PERSON_LIKE_CAR = TEST_TX_HEAD + "/car/getLikeCar.action";
    public static final String SALES_MANAGEMENT = HEAD + "/dealers/jxs_xsgl";
    public static final String SALES_MANAGEMENT_CHILD = HEAD + "/jxs_orders";
    public static final String DEALER_MY_MESSAGE = HEAD + "/Dealers/jxs_xx";
    public static final String DEALER_LANDING = TEST_TX_HEAD + "/member/memberLogin.action";
    public static final String GALLERY = HEAD + "/maiche/car_img";
    public static final String MY_BANK_CARD = HEAD + "/user/searchyhk";
    public static final String ADD_BANK_CARD = HEAD + "/user/add_yhk";
    public static final String CAR_LIST = "http://www.fuchengqiche.cn/index.php/Home/resources/zyf_zxdc";
    public static final String SALES_MANAGMENT_LIST = HEAD + "/dealers/jxs_orders";
    public static final String ONLINE_CAR = TEST_TX_HEAD + "/memberCar/orderCarOnline.action";
    public static final String CONTRACT = "http://www.fuchengqiche.org/Dealers/ht";
}
