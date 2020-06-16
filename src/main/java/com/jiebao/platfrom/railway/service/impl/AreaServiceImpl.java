package com.jiebao.platfrom.railway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiebao.platfrom.common.domain.JiebaoConstant;
import com.jiebao.platfrom.common.domain.QueryRequest;
import com.jiebao.platfrom.common.utils.SortUtil;
import com.jiebao.platfrom.railway.dao.AreaMapper;
import com.jiebao.platfrom.railway.domain.Area;
import com.jiebao.platfrom.railway.domain.AreaTree;
import com.jiebao.platfrom.railway.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service("AreaService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {



    private final static String TOP_NODE_ID = "0";
    @Autowired
    AreaService areaService;

    @Override
    public IPage<Area> getAreaList(QueryRequest request) {
        LambdaQueryWrapper<Area> lambdaQueryWrapper = new LambdaQueryWrapper();
        Page<Area> page = new Page<>(request.getPageNum(), request.getPageSize());
        lambdaQueryWrapper.orderByDesc(Area::getId);
        return this.baseMapper.selectPage(page,lambdaQueryWrapper);
    }

    @Override
    public Map<String, Object> getAreaListByService(QueryRequest request, Area area) {
        Map<String, Object> map = new HashMap<>();
        try {
            QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
            if (StringUtils.isNotBlank(area.getAreaName())){
                queryWrapper.lambda().eq(Area::getAreaName,area.getAreaName());
            }
            SortUtil.handleWrapperSort(request,queryWrapper,"id", JiebaoConstant.ORDER_ASC,true);
            List<Area> areas = baseMapper.selectList(queryWrapper);
            ArrayList<AreaTree<Area>> trees = new ArrayList<>();
            buildTrees(trees,areas);
            AreaTree<Area> build = build(trees);
            map.put("rows",build);
            map.put("total",areas.size());
        }
        catch (Exception e){
            log.error("获取部门列表失败", e);
            map.put("rows", null);
            map.put("total", 0);
        }
        return map;
    }





    private void buildTrees(List<AreaTree<Area>> trees, List<Area> areas) {
        areas.forEach(area -> {
            AreaTree<Area> areaTree = new AreaTree<>();
            areaTree.setId(area.getId());
            areaTree.setKey(areaTree.getId());
            areaTree.setParentId(area.getParentId());
            areaTree.setRank(area.getRank());
            areaTree.setAreaCode(area.getAreaCode());
            areaTree.setAreaName(area.getAreaName());
            areaTree.setCreatTime(area.getCreatTime());
            trees.add(areaTree);
        });
    }


    public static <T> AreaTree<T> build(List<AreaTree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<AreaTree<T>> topNodes = new ArrayList<>();
        nodes.forEach(node -> {
            String pid = node.getParentId();
            if (pid == null || TOP_NODE_ID.equals(pid)) {
                topNodes.add(node);
                return;
            }
            for (AreaTree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null)
                        n.initChildren();
                    n.getChildren().add(node);
                    node.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
            }
            if (topNodes.isEmpty()) {
                topNodes.add(node);
            }
        });


        AreaTree<T> root = new AreaTree<>();
        root.setId("0");
        root.setParentId("");
        root.setHasParent(false);
        root.setHasChildren(true);
        root.setChildren(topNodes);
        root.setText("root");
        return root;
    }




}
