package com.zhx.service;

import com.zhx.dao.AccountFileDao;
import com.zhx.vo.AccountFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/9/13.
 */
@Service
public class AccountFileService {

    @Autowired
    private AccountFileDao accountFileDao;

    public List<AccountFile> listPayAccount() {

        List<AccountFile> accountFiles = accountFileDao.queryPayList();

        return accountFiles;
    }


    public List<AccountFile> listRefundAccount(){

        List<AccountFile> accountFiles = accountFileDao.queryRefundList();

        return accountFiles;
    }
}
