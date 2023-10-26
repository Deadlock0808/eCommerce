package com.ecommerce.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.config.ApiResponse;
import com.ecommerce.ecommerce.dto.ProductDto;
import com.ecommerce.ecommerce.model.Category;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.repository.CategoryRepo;
import com.ecommerce.ecommerce.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@PostMapping("/addProduct")
	public ResponseEntity<ApiResponse> create(@RequestBody ProductDto productDto) {
		Optional<Category> optionalCategory= categoryRepo.findById(productDto.getCategoryId());
		if(!optionalCategory.isPresent()) {
			return	new ResponseEntity<ApiResponse>(new ApiResponse(false, "Created New Cateogry"), HttpStatus.BAD_REQUEST);

			//return	new ResponseEntity<ApiResponse>(new ApiResponse(false, "Created New Cateogry"));
		}
		productService.createProduct(productDto ,optionalCategory.get());
		return	new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
	}
	
	
	@PostMapping("/getProducts")
	public ResponseEntity<List<ProductDto>>getAllProducts(){
		
		List<ProductDto> productdto =productService.getproducts();
		if(!productdto.isEmpty()) {
			return	new ResponseEntity<List<ProductDto>>(productdto,HttpStatus.OK);
			
		}
		
		return	new ResponseEntity<List<ProductDto>>(productdto,HttpStatus.NO_CONTENT);	

	}
	
	@PostMapping("update/{product_id}")
	public ResponseEntity<ApiResponse>update(@PathVariable Integer product_id , @RequestBody ProductDto productDto) throws Exception{
		Optional<Category> optionalCategory =	categoryRepo.findById(productDto.getCategoryId());
		if(!optionalCategory.isPresent()) {
			return	new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category not found"), HttpStatus.NOT_FOUND);

		}
		
		productService.updateProduct(product_id , productDto);
		return	new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.OK);

	}
}
