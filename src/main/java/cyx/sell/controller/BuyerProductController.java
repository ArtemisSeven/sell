package cyx.sell.controller;

import cyx.sell.VO.ProductInfoVO;
import cyx.sell.VO.ProductVO;
import cyx.sell.VO.ResultVO;
import cyx.sell.entity.Product;
import cyx.sell.entity.ProductCategory;
import cyx.sell.service.ProductCategoryService;
import cyx.sell.service.ProductService;
import cyx.sell.utils.ResultVOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //1.查询所有在架商品
        List<Product> productList=productService.findUpAll();
        //2.取得所有在架商品的商品类目
        List<Integer> categoryTypes=productList.stream()//首先把商品列表转为流
                .map(e->e.getCategoryType())//然后只取出每条数据的categoryType的值
                .collect(Collectors.toList());//收集categoryType的值，并保存为list
        //3.获取商品类目的详情
        List<ProductCategory> productCategoryList=productCategoryService.findByCategoryTypeIn(categoryTypes);//可以直接用这个list啦
        //4.封装数据
        List<ProductInfoVO> productInfoVOList=new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList){
            ProductInfoVO productInfoVO=new ProductInfoVO();
            productInfoVO.setCategoryName(productCategory.getCategoryName());
            productInfoVO.setCategoryType(productCategory.getCategoryType());
            List<ProductVO> productVOList=new ArrayList<>();
            for (Product product:productList){
                if (product.getCategoryType()==productCategory.getCategoryType()){
                    ProductVO productVO=new ProductVO();
                    BeanUtils.copyProperties(product,productVO);
                    productVOList.add(productVO);
                }
            }
            productInfoVO.setProductVOList(productVOList);
            productInfoVOList.add(productInfoVO);
        }
        return ResultVOUtils.success(productInfoVOList);
    }
}
