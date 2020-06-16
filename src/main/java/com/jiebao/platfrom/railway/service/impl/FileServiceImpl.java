package com.jiebao.platfrom.railway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiebao.platfrom.common.domain.QueryRequest;
import com.jiebao.platfrom.railway.dao.FileMapper;
import com.jiebao.platfrom.railway.domain.Files;
import com.jiebao.platfrom.railway.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service("FileService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements FileService {

    @Override
    public IPage<Files> getFileList(QueryRequest request) {
        LambdaQueryWrapper<Files> lambdaQueryWrapper = new LambdaQueryWrapper();
        Page<Files> page = new Page<>(request.getPageNum(), request.getPageSize());
        lambdaQueryWrapper.orderByDesc(Files::getId);
        return this.baseMapper.selectPage(page,lambdaQueryWrapper);
    }
}
