package com.quensty.ucenter.mapper;

import com.quensty.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author quensty
 * @since 2021-11-28
 */
public interface MemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisters(@Param("date") String date);
}
