package com.jiebao.platfrom.railway.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiebao.platfrom.common.annotation.Log;
import com.jiebao.platfrom.common.controller.BaseController;
import com.jiebao.platfrom.common.domain.JiebaoResponse;
import com.jiebao.platfrom.common.domain.QueryRequest;
import com.jiebao.platfrom.common.exception.JiebaoException;
import com.jiebao.platfrom.railway.dao.AreaMapper;
import com.jiebao.platfrom.railway.domain.Area;
import com.jiebao.platfrom.railway.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author yf
 */
@RestController
@RequestMapping(value = "/area")
@Api(tags = "地区")   //swagger2 api文档说明示例
public class AreaController extends BaseController {

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private AreaService areaService;

    /**
     * 使用Mapper操作数据库
     *
     * @return JiebaoResponse 标准返回数据类型
     */
    @GetMapping
    @ApiOperation(value = "查询数据List", notes = "查询数据List列表", response = JiebaoResponse.class, httpMethod = "GET")
    public Map<String, Object> getAreaListByService(QueryRequest request, Area area) {
        return this.areaService.getAreaListByService(request,area);
    }

    /**
     * 分页查询
     *
     * @param request - 分页参数
     * @return
     * @Parameters sortField  according to order by Field
     * @Parameters sortOrder  JiebaoConstant.ORDER_ASC or JiebaoConstant.ORDER_DESC
     */
    @PostMapping("/getAreaList")
    @ApiOperation(value = "分页查询", notes = "查询分页数据", response = JiebaoResponse.class, httpMethod = "POST")
    public JiebaoResponse getAreaList(QueryRequest request) {
        IPage<Area> areaList = areaService.getAreaList(request);
        return new JiebaoResponse().data(this.getDataTable(areaList));
    }

    @DeleteMapping("/{ids}")
    @Log("删除")
    @Transactional(rollbackFor = Exception.class)
    public JiebaoResponse delete(@PathVariable Integer[] ids) throws JiebaoException {
        try {
            Arrays.stream(ids).forEach(id -> {
                areaService.removeById(id);
            });
        } catch (Exception e) {
            throw new JiebaoException("删除失败");
        }
        return new JiebaoResponse().message("删除成功");
    }

    @PostMapping
    @Log("新增或修改")
    @Transactional(rollbackFor = Exception.class)
    public JiebaoResponse addArea(@PathVariable Area area){
        areaService.saveOrUpdate(area);
        return new JiebaoResponse().message("成功");
    }


}
