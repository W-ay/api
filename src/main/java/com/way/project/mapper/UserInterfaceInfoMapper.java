package com.way.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.way.dubbointerface.model.entity.UserInterfaceInfo;
import com.way.project.model.vo.InterfaceInfoVO;

import java.util.List;

/**
 * @author Way
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<InterfaceInfoVO> listTopInvokeInterfaceInfo(int limit);
}
