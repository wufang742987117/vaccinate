/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.procategory.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.procategory.dao.ProductTypeDao;
import com.thinkgem.jeesite.modules.procategory.entity.ProductType;

/**
 * 产品类别Service
 * @author 1
 * @version 2016-08-03
 */
@Service
@Transactional(readOnly = true)
public class ProductTypeService extends CrudService<ProductTypeDao, ProductType> {
	@Autowired
	private ProductTypeDao protype;
	
	public List<ProductType>   findtree(){
		List<ProductType> list =protype.findtree();
		Set<String> parentIdSet = Sets.newHashSet();
		for (ProductType e : list) {
			if (e.getPId() != null  ) {
				boolean isExistParent = false;
				for (ProductType e2 : list) {
					if (e.getPId()==e2.getPId()) {
						isExistParent = true;
						break;
					}
				}
				if (!isExistParent) {
					parentIdSet.add(e.getParent().getId());
				}
			}
		}
			return list;
	}
			
	public ProductType get(String id) {
		return super.get(id);
	}
	
	public List<ProductType> findList(ProductType productType) {
		return super.findList(productType);
	}
	
	public Page<ProductType> findPage(Page<ProductType> page, ProductType productType) {
		return super.findPage(page, productType);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductType productType) {
		super.save(productType);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductType productType) {
		super.delete(productType);
	}
	
}