package com.xunlu.api.user.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户model类
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
    private String photo = "";
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
     * //TODO 使用枚举实现
     * 自然景观偏好，10.一般，20.亲近，30.大爱
     */
    private Byte preferNatural;
    /**
     * TODO 使用枚举实现
     * 人文景观偏好，10.一般，20.亲近，30.大爱
     */
    private Byte preferHuman;
    /**
     * TODO 使用枚举实现
     * 奔波系数偏好，10.城区为主，20.覆盖近郊，30.不畏远郊
     */
    private Byte preferRunning;
    /**
     * TODO 使用枚举实现
     * 日间游玩时长，10.小半天，20.大半天，30.全天
     */
    private Byte preferPlayTime;
    /**
     * TODO 使用枚举实现
     * 夜间游玩偏好，10.酒店休息，20.
     */
    private Byte preferNightPlay;
    /**
     * TODO 使用枚举实现
     * 10-最优   20-综合
     */
    private Byte preferPubTransFirst;
    /**
     * TODO 使用枚举实现
     * 酒店类型偏好，10.民居客栈，20.2-3星，30.4-5星
     */
    private Byte preferHotelLevel;
    /**
     * TODO 枚举
     * 旅行人数:10-独行侠  20-伴侣  30-一家三口  40-很多朋友
     */
    private Byte preferTripNumber;
    /**
     * TODO 枚举
     * 航班偏好:10-高效直飞  20-低价中转
     */
    private Byte preferFlight;
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
    private Integer liked=0;
    /**
     * 第三方用户id
     */
    private String timIdentifier;

    /**
     * TODO 待定
     */
    private Map<String,Object> extraInfo;

    public void setExtraInfo(String key,Object value)
    {
        if(extraInfo==null) {
            extraInfo=new HashMap<>();
        }
        extraInfo.put(key, value);
    }

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }


    @Override
    public String toString() {
        //new Gson().toJson(this);
        return "";
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

    public Byte getPreferNatural() {
        return preferNatural;
    }

    public void setPreferNatural(Byte preferNatural) {
        this.preferNatural = preferNatural;
    }

    public Byte getPreferHuman() {
        return preferHuman;
    }

    public void setPreferHuman(Byte preferHuman) {
        this.preferHuman = preferHuman;
    }

    public Byte getPreferRunning() {
        return preferRunning;
    }

    public void setPreferRunning(Byte preferRunning) {
        this.preferRunning = preferRunning;
    }

    public Byte getPreferPlayTime() {
        return preferPlayTime;
    }

    public void setPreferPlayTime(Byte preferPlayTime) {
        this.preferPlayTime = preferPlayTime;
    }

    public Byte getPreferNightPlay() {
        return preferNightPlay;
    }

    public void setPreferNightPlay(Byte preferNightPlay) {
        this.preferNightPlay = preferNightPlay;
    }

    public Byte getPreferPubTransFirst() {
        return preferPubTransFirst;
    }

    public void setPreferPubTransFirst(Byte preferPubTransFirst) {
        this.preferPubTransFirst = preferPubTransFirst;
    }

    public Byte getPreferHotelLevel() {
        return preferHotelLevel;
    }

    public void setPreferHotelLevel(Byte preferHotelLevel) {
        this.preferHotelLevel = preferHotelLevel;
    }

    public Byte getPreferTripNumber() {
        return preferTripNumber;
    }

    public void setPreferTripNumber(Byte preferTripNumber) {
        this.preferTripNumber = preferTripNumber;
    }

    public Byte getPreferFlight() {
        return preferFlight;
    }

    public void setPreferFlight(Byte preferFlight) {
        this.preferFlight = preferFlight;
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
}
