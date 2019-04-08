package com.xunlu.api.user.domain;

import com.xunlu.api.user.infrastructure.BaseCodeEnum;
import com.xunlu.api.user.util.UserUtil;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户model类
 *
 * @author liweibo
 */
public class User {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     *
     */
    private String personSign;
    /**
     * 头像
     */
    private String photo;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户偏好
     */
    private Prefer prefer;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 区域码  如:中国 86
     */
    private String areaCode;
    /**
     * TODO 待定
     */
    private Integer liked = 0;
    /**
     * 第三方用户id
     */
    private String timIdentifier;

    /**
     * TODO 待定
     */
    private Map<String, Object> extraInfo;

    public User() { }

    public void setExtraInfo(String key, Object value) {
        if (extraInfo == null) {
            extraInfo = new HashMap<>(16);
        }
        extraInfo.put(key, value);
    }

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }

    /**
     * 创建一个第一次手机号登录时默认注册时的一个用户
     * @param phone 手机号
     * @param areaCode 区号 例如中国:86
     * @param password
     * @return
     */
    public static final User newPhoneRegisterUser(String phone, String areaCode, String password) {
        User user = new User();
        user.setPhone(phone);
        user.setAreaCode(areaCode);
        user.setNickName(UserUtil.generateDefaultNickName());
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setCreateTime(LocalDateTime.now());
        return user;
    }

    @Override
    public String toString() {
        return getUserName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPersonSign() {
        return personSign;
    }

    public void setPersonSign(String personSign) {
        this.personSign = personSign;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Prefer getPrefer() {
        return prefer;
    }

    public void setPrefer(Prefer prefer) {
        this.prefer = prefer;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public String getTimIdentifier() {
        return timIdentifier;
    }

    public void setTimIdentifier(String timIdentifier) {
        this.timIdentifier = timIdentifier;
    }

    public static class Prefer {
        /**
         * 哪个用户的偏好
         */
        private Integer userId;
        /**
         * 自然景观偏好，10.一般，20.亲近，30.大爱
         */
        private Natural preferNatural;
        /**
         * 人文景观偏好，10.一般，20.亲近，30.大爱
         */
        private Natural preferHuman;
        /**
         * 奔波系数偏好，10.城区为主，20.覆盖近郊，30.不畏远郊
         */
        private Running preferRunning;
        /**
         * 日间游玩时长，10.小半天，20.大半天，30.全天
         */
        private PlayTime preferPlayTime;
        /**
         * 夜间游玩偏好，10.酒店休息，20.
         */
        private NightPlay preferNightPlay;
        /**
         * 10-最优   20-综合
         */
        private PubTransFirst preferPubTransFirst;
        /**
         * 酒店类型偏好，10.民居客栈，20.2-3星，30.4-5星
         */
        private HotelLevel preferHotelLevel;
        /**
         * 旅行人数:10-独行侠  20-伴侣  30-一家三口  40-很多朋友
         */
        private TripNumber preferTripNumber;
        /**
         * 航班偏好:10-高效直飞  20-低价中转
         */
        private Flight preferFlight;

        /**
         * 用户是否具有某项偏好
         * @return true 有, false 没有
         */
        public boolean hasPrefer() {
            return preferNatural != null
                    || preferHuman != null
                    || preferRunning != null
                    || preferPlayTime != null
                    || preferNightPlay != null
                    || preferPubTransFirst != null
                    || preferHotelLevel != null
                    || preferTripNumber != null
                    || preferFlight != null;
        }

        /**
         * 自然景观枚举，10.一般，20.亲近，30.大爱
         */
        public enum Natural implements BaseCodeEnum<Natural> {
            /**
             * 一般
             */
            PLAIN(10, "一般"),
            /**
             * 亲近
             */
            CLOSE(20, "亲近"),
            /**
             * 大爱
             */
            ULTIMATE_PURSUIT(30, "大爱");

            private int code;
            private String meaning;

            Natural(int code, String meaning) {
                this.code = code;
                this.meaning = meaning;
            }

            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMeaning() {
                return meaning;
            }
        }

        /**
         * 奔波系数枚举，10.城区为主，20.覆盖近郊，30.不畏远郊
         */
        public enum Running implements BaseCodeEnum<Running> {
            /**
             * 城区为主
             */
            URBAN_DOMINATED(10, "城区为主"),
            /**
             * 覆盖近郊
             */
            COVER_SUBURBS(20, "覆盖近郊"),
            /**
             * 不畏远郊
             */
            FEARLESS_OUTER_SUBURBS(30, "不畏远郊");
            private int code;
            private String meaning;

            Running(int code, String meaning) {
                this.code = code;
                this.meaning = meaning;
            }

            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMeaning() {
                return meaning;
            }
        }

        /**
         * 日间游玩时长枚举，10.小半天，20.大半天，30.全天
         */
        public enum PlayTime implements BaseCodeEnum<PlayTime> {
            /**
             * 小半天
             */
            SMALL_HALF_DAY(10, "小半天"),
            /**
             * 大半天
             */
            BIG_HALF_DAY(20, "大半天"),
            /**
             * 全天
             */
            WHOLE_DAY(30, "全天")
            ;
            private int code;
            private String meaning;

            PlayTime(int code, String meaning) {
                this.code = code;
                this.meaning = meaning;
            }

            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMeaning() {
                return meaning;
            }
        }

        /**
         * 夜间游玩
         */
        public enum NightPlay implements BaseCodeEnum<NightPlay> {
            /**
             * 酒店休息
             */
            REST_IN_HOTEL(10, "酒店休息"),
            /**
             * 为止
             */
            UNKNOW(20, "为止")
            ;
            private int code;
            private String meaning;

            NightPlay(int code, String meaning) {
                this.code = code;
                this.meaning = meaning;
            }

            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMeaning() {
                return meaning;
            }
        }

        /**
         * 10-最优   20-综合
         */
        public enum PubTransFirst implements BaseCodeEnum<PubTransFirst> {
            /**
             * 最优
             */
            OPTIMAL(10, "最优"),
            /**
             * 综合
             */
            SYNTHETICAL(20, "综合")
            ;
            private int code;
            private String meaning;

            PubTransFirst(int code, String meaning) {
                this.code = code;
                this.meaning = meaning;
            }

            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMeaning() {
                return meaning;
            }
        }

        /**
         * 酒店级别枚举，10.民居客栈，20.2-3星，30.4-5星
         */
        public enum HotelLevel implements BaseCodeEnum<HotelLevel> {
            /**
             * 民居客栈
             */
            RESIDENCE_INN(10, "民居客栈"),
            /**
             * 2-3星
             */
            TWO_THREE_STAR(20, "2-3星"),
            /**
             * 4-5星
             */
            FOUR_FIVE_STAR(30, "4-5星")
            ;
            private int code;
            private String meaning;

            HotelLevel(int code, String meaning) {
                this.code = code;
                this.meaning = meaning;
            }

            @Override
            public String getMeaning() {
                return meaning;
            }

            @Override
            public int getCode() {
                return code;
            }
        }

        /**
         * 旅行人数枚举:10-独行侠  20-伴侣  30-一家三口  40-很多朋友
         */
        public enum TripNumber implements BaseCodeEnum<TripNumber> {
            /**
             * 独行侠
             */
            LONE_RANGER(10, "独行侠"),
            /**
             * 伴侣
             */
            MATE(20, "伴侣"),
            /**
             * 一家三口
             */
            A_FAMILY_OF_THREE(30, "一家三口"),
            /**
             * 很多朋友
             */
            MANY_FRIENDS(40, "很多朋友")
            ;
            private int code;
            private String meaning;

            TripNumber(int code, String meaning) {
                this.code = code;
                this.meaning = meaning;
            }

            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMeaning() {
                return meaning;
            }
        }

        /**
         * 航班偏好枚举:10-高效直飞  20-低价中转
         */
        public enum Flight implements BaseCodeEnum<Flight> {
            /**
             * 高效直飞
             */
            EFFICIENT_DIRECT(10, "高效直飞"),
            /**
             * 低价中转
             */
            CHEAP_TRANSFER(20, "低价中转")
            ;
            private int code;
            private String meaning;

            Flight(int code, String meaning) {
                this.code = code;
                this.meaning = meaning;
            }

            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMeaning() {
                return meaning;
            }
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Natural getPreferNatural() {
            return preferNatural;
        }

        public void setPreferNatural(Natural preferNatural) {
            this.preferNatural = preferNatural;
        }

        public Natural getPreferHuman() {
            return preferHuman;
        }

        public void setPreferHuman(Natural preferHuman) {
            this.preferHuman = preferHuman;
        }

        public Running getPreferRunning() {
            return preferRunning;
        }

        public void setPreferRunning(Running preferRunning) {
            this.preferRunning = preferRunning;
        }

        public PlayTime getPreferPlayTime() {
            return preferPlayTime;
        }

        public void setPreferPlayTime(PlayTime preferPlayTime) {
            this.preferPlayTime = preferPlayTime;
        }

        public NightPlay getPreferNightPlay() {
            return preferNightPlay;
        }

        public void setPreferNightPlay(NightPlay preferNightPlay) {
            this.preferNightPlay = preferNightPlay;
        }

        public PubTransFirst getPreferPubTransFirst() {
            return preferPubTransFirst;
        }

        public void setPreferPubTransFirst(PubTransFirst preferPubTransFirst) {
            this.preferPubTransFirst = preferPubTransFirst;
        }

        public HotelLevel getPreferHotelLevel() {
            return preferHotelLevel;
        }

        public void setPreferHotelLevel(HotelLevel preferHotelLevel) {
            this.preferHotelLevel = preferHotelLevel;
        }

        public TripNumber getPreferTripNumber() {
            return preferTripNumber;
        }

        public void setPreferTripNumber(TripNumber preferTripNumber) {
            this.preferTripNumber = preferTripNumber;
        }

        public Flight getPreferFlight() {
            return preferFlight;
        }

        public void setPreferFlight(Flight preferFlight) {
            this.preferFlight = preferFlight;
        }
    }

}
