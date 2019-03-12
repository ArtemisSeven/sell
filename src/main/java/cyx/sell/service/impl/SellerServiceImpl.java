package cyx.sell.service.impl;

import cyx.sell.dao.SellerDao;
import cyx.sell.entity.Seller;
import cyx.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerDao sellerDao;

    @Override
    public Seller findSellerByOpenid(String openid) {
        Seller seller= sellerDao.findByOpenid(openid);
        return seller;
    }
}
