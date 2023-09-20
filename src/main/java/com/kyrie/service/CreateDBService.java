package com.kyrie.service;

import com.kyrie.pojo.CreateDbInfo;

public interface CreateDBService {
    String initDB(CreateDbInfo createDBDTO);

    String addDataSource(CreateDbInfo createDbDTO);

}
