package com.kyrie.controller;

import com.kyrie.pojo.CreateDbDTO;
import com.kyrie.service.CreateDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/create")
public class CreateDBController {

    @Autowired
    private CreateDBService createDBService;

    @PostMapping("/initDB")
    public String initDB(@RequestBody CreateDbDTO createDBDTO) throws SQLException, IOException, ClassNotFoundException {
        return createDBService.initDB(createDBDTO);
    }
}
