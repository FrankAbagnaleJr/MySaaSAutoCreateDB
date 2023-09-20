package com.kyrie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyrie.pojo.CreateDbInfo;

public interface CreateDBService extends IService<CreateDbInfo> {
    String initDB(CreateDbInfo createDBDTO);

    String addDataSourceToCache(CreateDbInfo createDbDTO);

}
