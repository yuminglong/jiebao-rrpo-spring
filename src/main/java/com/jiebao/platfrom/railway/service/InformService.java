package com.jiebao.platfrom.railway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiebao.platfrom.common.domain.QueryRequest;
import com.jiebao.platfrom.railway.domain.Inform;

/**
 * @author yf
 */
public interface InformService extends IService<Inform> {

    IPage<Inform>   getInformList(QueryRequest request,Inform inform);

    void updateByKey(Inform inform);


}
