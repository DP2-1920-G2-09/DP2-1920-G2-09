
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

	private ProductRepository productRepository;


	@Autowired
	public ProductService(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional
	public Iterable<Product> findAllProducts() {
		Iterable<Product> products = this.productRepository.findAll();
		return products;
	}

	@Transactional
	public Iterable<Product> findFilteredProducts(final String name) {
		Iterable<Product> products = this.productRepository.findProductsFiltered(name);
		return products;
	}

	@Transactional
	public Iterable<Product> findFilteredProductsByCategory(final Category category) {
		Iterable<Product> products = this.productRepository.findProductsFilteredByCategory(category);
		return products;
	}

	@Transactional(readOnly = true)
	public Product findProductById(final int id) throws DataAccessException {
		return this.productRepository.findById(id);
	}

	@Transactional
	public void saveProduct(final Product product) {
		if (product.getStock() == 0) {
			product.setAvailable(false);
		}
		this.productRepository.save(product);
	}

}
