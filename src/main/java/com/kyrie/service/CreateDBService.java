package com.kyrie.service;

import com.kyrie.pojo.CreateDbDTO;

import java.io.IOException;
import java.sql.SQLException;

public interface CreateDBService {
    String initDB(CreateDbDTO createDBDTO) throws ClassNotFoundException, SQLException, IOException;
}
