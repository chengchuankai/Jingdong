package zhanghegang.com.bawei.date0928jingdong.bean;

import java.util.List;

/**
 * Created by asus on 2017/9/29.
 */

public class BannerBean {

    /**
     * msg :
     * code : 0
     * data : [{"aid":1,"createtime":"2017-09-28T21:14:34","icon":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1649060787,253637340&fm=27&gp=0.jpg","productId":null,"title":"花生油","type":0,"url":"https://sale.jd.com/m/act/zHY8PefFj1.html?_ts=1506578599845&utm_source=androidapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=QQfriends"},{"aid":2,"createtime":"2017-09-28T16:48:26","icon":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507193296&di=8b6d3b1618e6b8f0e825d6e025f7e3e1&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.zhaoxi.net%2Fimages%2Fmymake%2F2014%2F4%2F201404242353235302.jpg","productId":null,"title":"京东女人节","type":0,"url":"https://pro.m.jd.com/mall/active/hMuGahrSriEuvYBEcQS4gXrHQC5/index.html?utm_source=pdappwakeupup_20170001"},{"aid":3,"createtime":"2017-09-28T21:14:34","icon":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506598616492&di=ffc9053d88fa5378b3a81d3380f57309&imgtype=0&src=http%3A%2F%2Fwww.th7.cn%2Fd%2Ffile%2Fp%2F2014%2F04%2F26%2F42bb698e1310d66e112b60c39b69093c.jpg","productId":null,"title":"国庆大惠战","type":0,"url":"https://3.cn/iRi02f?utm_source=androidapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=QQfriends"},{"aid":4,"createtime":"2017-09-28T21:20:50","icon":null,"productId":"1","title":"商品1","type":1,"url":"https://3.cn/iRi02f?utm_source=androidapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=QQfriends"}]
     */

    private String msg;
    private String code;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * aid : 1
         * createtime : 2017-09-28T21:14:34
         * icon : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1649060787,253637340&fm=27&gp=0.jpg
         * productId : null
         * title : 花生油
         * type : 0
         * url : https://sale.jd.com/m/act/zHY8PefFj1.html?_ts=1506578599845&utm_source=androidapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=QQfriends
         */

        private int aid;
        private String createtime;
        private String icon;
        private Object productId;
        private String title;
        private int type;
        private String url;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Object getProductId() {
            return productId;
        }

        public void setProductId(Object productId) {
            this.productId = productId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
