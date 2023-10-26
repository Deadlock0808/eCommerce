package com.ecommerce.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.dto.ProductDto;
import com.ecommerce.ecommerce.model.Category;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.repository.ProductRepo;

@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;
	
	public void createProduct(ProductDto productDto, Category category) {

		Product product= new Product();
		product.setDescription(productDto.getDescription());
		product.setImageURL(productDto.getImageURL());
		product.setName(productDto.getName());
		product.setCategory(category);
		product.setPrice(productDto.getPrice());
		productRepo.save(product);
	}
	
	/*
	 * public ProductService(@Qualifier("products") ProductRepo productRepo) { //
	 * TODO Auto-generated constructor stub this.productRepo=productRepo; }
	 */
	
	public List<ProductDto>getproducts(){
		List<ProductDto>  productDTOList= new ArrayList<ProductDto>();
		List<Product> allProducts = productRepo.findAll();
		
		for(Product product : allProducts) {
			productDTOList.add(convertoDto(product));
		}
		
		
		return productDTOList;
		
	}
	
	public ProductDto convertoDto(Product product){
		
			ProductDto productDto = new ProductDto();
			productDto.setCategoryId(product.getCategory().getId());
			productDto.setProduct_id(product.getId());
			productDto.setDescription(product.getDescription());
			productDto.setImageURL(product.getImageURL());
			productDto.setName(product.getName());
			productDto.setPrice(product.getPrice());
			
			
			return productDto;
	}
	
	public void updateProduct(int id , ProductDto productDto) throws Exception {
		Optional< Product>optionalProduct=productRepo.findById(id);
		if(!optionalProduct.isPresent()) {
			throw new Exception("Product Not Found");
		}
		else {
		Product product = optionalProduct.get();
		//product.setId(productDto.getProduct_id());
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setImageURL(productDto.getImageURL());
		product.setPrice(productDto.getPrice());
		productRepo.save(product);
			}
	}

}
