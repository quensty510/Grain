package com.quensty.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quensty.commonutils.JwtUtils;
import com.quensty.commonutils.MD5;
import com.quensty.serviceBase.handler.GuLiException;
import com.quensty.ucenter.entity.UcenterMember;
import com.quensty.ucenter.entity.vo.LoginVo;
import com.quensty.ucenter.entity.vo.RegisterVo;
import com.quensty.ucenter.mapper.MemberMapper;
import com.quensty.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 会员表 服务实现类
 *
 * @author quensty
 * @since 2021-11-28
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, UcenterMember> implements MemberService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String phone = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            throw new GuLiException(20001,"登录失败");
        }

        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",phone);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        //判断是否存在用户
        if (ucenterMember == null){
            throw new GuLiException(20001,"当前手机号未注册");
        }
        //判断密码是否正确
        //数据库中的密码进行过加密
        //将输入的密码进行加密再进行比对
        //加密方式 MD5
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw new GuLiException(20001,"用户名或密码错误");
        }
        //判断用户是否禁用
        if (ucenterMember.getIsDisabled()){
            throw new GuLiException(20001,"登录失败");
        }
        //登录成功
        //生成token
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册数据
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuLiException(20001,"注册失败");
        }

        //获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get("vfCode::" + mobile);
        if (!code.equals(redisCode)){
            throw new GuLiException(20001,"注册失败");
        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer selectCount = baseMapper.selectCount(queryWrapper);
        if (selectCount > 0){
            redisTemplate.delete("vfCode::" + mobile);
            throw new GuLiException(20001,"手机号重复");
        }

        //数据添加到数据库中
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://edu-demo-oss.oss-cn-beijing.aliyuncs.com/2021/11/08/defaultAvatar.jpg");
        baseMapper.insert(ucenterMember);
        redisTemplate.delete("vfCode::" + mobile);
    }

    @Override
    public UcenterMember getMemberByOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer countRegisters(String date) {
       return baseMapper.countRegisters(date);
    }
}
