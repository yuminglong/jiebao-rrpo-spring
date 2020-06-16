package com.jiebao.platfrom.railway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiebao.platfrom.common.domain.QueryRequest;
import com.jiebao.platfrom.railway.domain.Area;

import java.util.Map;

/**
 * @author yf
 */
public interface AreaService extends IService<Area> {

    IPage<Area>   getAreaList(QueryRequest request);

    Map<String, Object> getAreaListByService(QueryRequest request, Area area);
}
