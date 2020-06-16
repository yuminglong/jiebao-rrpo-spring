package com.jiebao.platfrom.railway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiebao.platfrom.common.domain.QueryRequest;
import com.jiebao.platfrom.railway.domain.Files;

/**
 * @author yf
 */
public interface FileService extends IService<Files> {

    IPage<Files>   getFileList(QueryRequest request);
}
