package zhanghegang.com.bawei.date0928jingdong.bean;

import java.util.List;

/**
 * Created by asus on 2017/10/21.
 */

public class OrderBean {

    /**
     * msg : 请求成功
     * code : 0
     * data : [{"createtime":"2017-10-20T15:21:07","orderid":160,"price":70800,"status":0,"uid":88},{"createtime":"2017-10-20T15:22:24","orderid":162,"price":82020.9,"status":0,"uid":88},{"createtime":"2017-10-20T17:50:17","orderid":228,"price":0,"status":0,"uid":88},{"createtime":"2017-10-20T20:22:27","orderid":353,"price":70800,"status":0,"uid":88},{"createtime":"2017-10-20T20:22:28","orderid":354,"price":70800,"status":0,"uid":88},{"createtime":"2017-10-21T14:22:50","orderid":436,"price":11868.7,"status":0,"uid":88},{"createtime":"2017-10-21T15:40:49","orderid":473,"price":35560.3,"status":0,"uid":88},{"createtime":"2017-10-21T15:40:49","orderid":474,"price":35560.3,"status":0,"uid":88},{"createtime":"2017-10-21T15:42:11","orderid":479,"price":35560.3,"status":0,"uid":88}]
     * page : 1
     */

    private String msg;
    private String code;
    private String page;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createtime : 2017-10-20T15:21:07
         * orderid : 160
         * price : 70800
         * status : 0
         * uid : 88
         */

        private String createtime;
        private int orderid;
        private String price;
        private int status;
        private int uid;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
