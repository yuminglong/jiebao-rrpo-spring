package com.jiebao.platfrom.railway.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiebao.platfrom.common.annotation.Log;
import com.jiebao.platfrom.common.controller.BaseController;
import com.jiebao.platfrom.common.domain.JiebaoResponse;
import com.jiebao.platfrom.common.domain.QueryRequest;
import com.jiebao.platfrom.common.exception.JiebaoException;
import com.jiebao.platfrom.railway.dao.FileMapper;
import com.jiebao.platfrom.railway.domain.Files;
import com.jiebao.platfrom.railway.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author yf
 */
@RestController
@RequestMapping(value = "/file")
@Api(tags = "文件共享")   //swagger2 api文档说明示例
public class FileController extends BaseController {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileService fileService;

    /**
     * 使用Mapper操作数据库
     *
     * @return JiebaoResponse 标准返回数据类型
     */
    @PostMapping(value = "/getFileListByMapper")
    @ApiOperation(value = "查询数据List", notes = "查询数据List列表", response = JiebaoResponse.class, httpMethod = "POST")
    public JiebaoResponse getFileListByMapper() {
        List<Files> list = fileMapper.getFileList();
        return new JiebaoResponse().data(list).message("查询成功").put("remake", "其他数据返回");
    }

    /**
     * 分页查询
     *
     * @param request - 分页参数
     * @return
     * @Parameters sortField  according to order by Field
     * @Parameters sortOrder  JiebaoConstant.ORDER_ASC or JiebaoConstant.ORDER_DESC
     */
    @PostMapping("/getFileList")
    @ApiOperation(value = "分页查询", notes = "查询分页数据", response = JiebaoResponse.class, httpMethod = "POST")
    public JiebaoResponse getFileList(QueryRequest request) {
        IPage<Files> fileList = fileService.getFileList(request);
        return new JiebaoResponse().data(this.getDataTable(fileList));
    }

    @PostMapping("/delete/{ids}")
    @Log("删除文件")
    @Transactional(rollbackFor = Exception.class)
    public JiebaoResponse delete(@PathVariable Integer[] ids) throws JiebaoException {

        try {
            Arrays.stream(ids).forEach(id -> {
                Files byId = fileService.getById(id);
                File file = new File(byId.getUrl());
                if (file.exists()) {
                    file.delete();
                }
                fileService.removeById(id);
            });
        } catch (Exception e) {
            throw new JiebaoException("删除失败");
        }
        return new JiebaoResponse().message("删除成功");
    }

    @PostMapping("/update/{file}")
    @Log("修改")
    @Transactional(rollbackFor = Exception.class)
    public JiebaoResponse addFile(@PathVariable Files file) {
        fileService.saveOrUpdate(file);
        return new JiebaoResponse().message("成功");
    }


    @PostMapping("/upload")
    @ApiOperation(value = "文件上传", notes = "文件上传", response = JiebaoResponse.class, httpMethod = "POST")
    public JiebaoResponse upload(@RequestParam("file")MultipartFile file,Files files){
        String path = null;
        if(file!=null){
            path = "D:/rrpo/download/"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+file.getOriginalFilename();
        }

        return new JiebaoResponse().message("上传成功");
    }

}
