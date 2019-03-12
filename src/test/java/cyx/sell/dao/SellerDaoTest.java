package cyx.sell.dao;

import cyx.sell.entity.Seller;
import cyx.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerDaoTest {

    @Autowired
    private SellerDao repository;

    @Test
    public void save() {
        Seller sellerInfo = new Seller();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");

        Seller result = repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid() throws Exception {
        Seller result = repository.findByOpenid("abc");
        Assert.assertEquals("abc", result.getOpenid());
    }

}