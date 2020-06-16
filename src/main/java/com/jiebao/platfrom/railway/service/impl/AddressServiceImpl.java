package com.jiebao.platfrom.railway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiebao.platfrom.common.authentication.JWTUtil;
import com.jiebao.platfrom.common.domain.JiebaoConstant;
import com.jiebao.platfrom.common.domain.QueryRequest;
import com.jiebao.platfrom.common.domain.Tree;
import com.jiebao.platfrom.common.utils.SortUtil;
import com.jiebao.platfrom.common.utils.TreeUtil;
import com.jiebao.platfrom.railway.dao.AddressMapper;
import com.jiebao.platfrom.railway.domain.Address;
import com.jiebao.platfrom.railway.domain.Inform;
import com.jiebao.platfrom.railway.service.AddressService;
import com.jiebao.platfrom.system.domain.Dept;
import com.jiebao.platfrom.system.service.DeptService;
import com.jiebao.platfrom.system.service.impl.DeptServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service("AddressService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    DeptService deptService;

    @Autowired
    AddressService addressService;

    @Override
    public IPage<Address> getAddressList(QueryRequest request) {
        LambdaQueryWrapper<Address> lambdaQueryWrapper = new LambdaQueryWrapper();
        Page<Address> page = new Page<>(request.getPageNum(), request.getPageSize());
        lambdaQueryWrapper.orderByDesc(Address::getId);
        return this.baseMapper.selectPage(page, lambdaQueryWrapper);
    }

    @Override
    public Map<String, Object> getAddressLists(QueryRequest request, Dept dept) {

        Map<String, Object> result = new HashMap<>();
        try {
            List<Dept> depts = deptService.findDept(dept, request);
         //   List<Address> addresses = addressService.findAddresses(address, request);
            List<Tree<Dept>> trees = new ArrayList<>();
            buildTrees(trees, depts);
            Tree<Dept> deptTree = TreeUtil.builds(trees);
            result.put("rows", deptTree);
            result.put("total", depts.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }


    @Override
    public List<Address> findAddresses(QueryRequest request, Address address) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(address.getTelPhone() + "")) {
            queryWrapper.lambda().eq(Address::getTelPhone, address.getTelPhone());
        }

        SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", JiebaoConstant.ORDER_ASC, true);
        return this.baseMapper.selectList(queryWrapper);
    }


    @Override
    @Transactional
    public void updateByKey(Address address) {
        this.addressMapper.updateById(address);
    }


    private void buildTrees(List<Tree<Dept>> trees, List<Dept> depts ) {


        depts.forEach(dept -> {
            List<Address> address = addressMapper.getAddressList(dept.getDeptId());
            Tree<Dept> tree = new Tree<>();
            tree.setId(dept.getDeptId());
            tree.setKey(tree.getId());
            tree.setParentId(dept.getParentId());
            tree.setText(dept.getDeptName());
            tree.setCreateTime(dept.getCreateTime());
            tree.setModifyTime(dept.getModifyTime());
            tree.setOrder(dept.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());


            List<Tree<Dept>> childList = new ArrayList<>();
            for (int i = 0; i < address.size(); i++) {
                Tree<Dept> data = new Tree<>();
                Address info = address.get(i);
                data.setId(info.getId());
                data.setKey(info.getId());
                data.setWeiXin(info.getWeiXin());
                data.setUserName(info.getUserName());
                data.setOrder(info.getNumbers());
                data.setTitle(info.getDeptName());
                data.setCreateTime(info.getCreatTime());
                data.setPhone(info.getPhone());
                data.setTelPhone(info.getTelPhone());
                data.setEmail(info.getEmail());
                data.setParentId(info.getParentsId());
                childList.add(data);
            }
            tree.setChildren(childList);
            trees.add(tree);
        });
    }
}
