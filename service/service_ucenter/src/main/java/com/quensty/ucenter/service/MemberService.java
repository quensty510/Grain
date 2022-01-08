package com.quensty.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quensty.ucenter.entity.UcenterMember;
import com.quensty.ucenter.entity.vo.LoginVo;
import com.quensty.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author quensty
 * @since 2021-11-28
 */
public interface MemberService extends IService<UcenterMember> {

    /**
     * 登录
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    UcenterMember getMemberByOpenId(String openid);

    /**
     * 统计注册人数
     * @param date
     * @return
     */
    Integer countRegisters(String date);
}
