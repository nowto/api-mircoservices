package com.xunlu.api.user.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 腾讯云 云通信IM 服务类, 用于与腾讯云云通信IM集成.
 * 云通信IM的介绍请看 <a href="https://cloud.tencent.com/product/im">腾讯云云通信IM官网</a>
 *
 * @author liweibo
 */
public interface TencentIMService {
	//region 登录鉴权相关 <a hre="https://cloud.tencent.com/document/product/269/31999">腾讯云官网对登录鉴权的介绍</a>

	/**
	 * 根据identifier生成userSig, 生成的userSig有效期为180天, 生成userSig不需要连网.
	 *
	 * 要想使用云通信IM,前提是必须先登录才能使用。
	 * 比如登录QQ用的是QQ号和密码，登录云通信 IM SDK 需要使用您指定的用户名（Identifier）和密码（UserSig）。
	 *
	 * identifier：用户登录云通信 IM 时使用的用户名，即您 App 里的用户 ID。比如 App 里有一个用户，该用户的 ID 是 27149 ，那么可以用 27149 作为登录云通信的 Identifier。
	 * userSig：用户登录云通信 IM 时使用的密码， UserSig 本质是App Server(即本服务该方法)用私钥对 Identifier 等信息非对称加密后的数据。
	 *
	 * <br/><a href="https://cloud.tencent.com/document/product/269/31999">腾讯云官网对登录鉴权的介绍</a>
	 *
	 * 参见<a href="https://cloud.tencent.com/document/product/269/32688">这里</a>服务器生成 UserSig 章节
	 *
	 * @param identifier 用户标识,用于腾讯云通信IM登录
	 * @return userSig 用于腾讯云通信IM登录校验
	 */
	String makeUserSig(String identifier);

	/**
	 * 校验用户标识为identifier的密码userSig是否符合要求.
	 * @param userSig 密码
	 * @param identifier 用户标识
	 * @return true 校验通过,否则false
	 */
	boolean checkUserSig(String userSig, String identifier);

	/**
	 * 根据前缀prefix和用户id生成云通信的Identifier
	 * @param prefix 前缀
	 * @param id id
	 * @return Identifer
	 */
	String makeIdentifier(String prefix, int id);

	//region 对云通信 帐号管理 REST API 的调用

	/**
	 * 将开发者自有帐号导入云通信, 单个帐号的导入.
	 * <a href="https://cloud.tencent.com/document/product/269/1608">单个帐号导入接口-云通信官方文档</a>
	 * @param identifier 用户标识，长度不超过 32 字节
	 * @param nick 用户昵称
	 * @param faceUrl  	用户头像URL
	 * @return ture 代表成功, false代表失败
	 */
	boolean accountImport(@NonNull String identifier, @Nullable String nick, @Nullable String faceUrl);

	/**
	 * 将开发者自有帐号导入云通信, 多个帐号的导入.
	 * <a href="https://cloud.tencent.com/document/product/269/4919">多个帐号导入接口-云通信官方文档</a>
     * @param accounts 需要导入云通信的用户标识列表, 最多支持100个帐号的导入
	 * @return ture 代表成功, false代表失败
	 */
	boolean multiAccountImport(List<String> accounts);
	//endregion
}