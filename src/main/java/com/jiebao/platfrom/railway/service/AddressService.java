package com.jiebao.platfrom.railway.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiebao.platfrom.common.domain.QueryRequest;
import com.jiebao.platfrom.railway.domain.Address;
import com.jiebao.platfrom.system.domain.Dept;

import java.util.List;
import java.util.Map;

/**
 * @author yf
 */
public interface AddressService extends IService<Address> {

    IPage<Address> getAddressList(QueryRequest request);

    Map<String, Object> getAddressLists(QueryRequest request, Dept dept);

    void updateByKey(Address address);

    List<Address> findAddresses( QueryRequest request,Address address);

}
