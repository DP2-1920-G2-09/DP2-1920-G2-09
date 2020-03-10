
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;


	@Autowired
	public ProductController(final ProductService productService) {
		this.productService = productService;
	}

	@InitBinder("product")
	public void initPetBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new ProductValidator());
	}

	@ModelAttribute("categories")
	public List<String> populatePetTypes() {
		List<String> res = new ArrayList<>();
		for (Category c : Category.values()) {
			res.add(c.toString());
		}
		return res;
	}

	@GetMapping(value = "/all")
	public String findAllProducts(final ModelMap model) {
		Product p = new Product();
		model.put("product", p);
		Iterable<Product> products = this.productService.findAllProducts();
		model.addAttribute("products", products);
		return "products/listProducts";
	}

	@GetMapping(value = "/filter")
	public String findProductsFiltered(final ModelMap model, final Product product) {
		model.put("product", product);
		Iterable<Product> products = this.productService.findFilteredProducts(product.getName());
		model.addAttribute("products", products);
		return "products/listProducts";
	}

	@GetMapping(value = "/{productId}")
	public ModelAndView showProduct(@PathVariable("productId") final int productId) {
		ModelAndView mav = new ModelAndView("products/productDetails");
		mav.addObject(this.productService.findProductById(productId));
		return mav;
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final Product owner, final ModelMap model) {
		Product product = new Product();
		model.put("product", product);
		return "products/createOrUpdateProductForm";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Product product, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("product", product);
			return "products/createOrUpdateProductForm";
		} else {
			this.productService.saveProduct(product);
			return this.findAllProducts(model);
		}
	}

	@GetMapping(value = "/{productId}/edit")
	public String initUpdateProductForm(@PathVariable("productId") final int productId, final Model model) {
		Product product = this.productService.findProductById(productId);
		model.addAttribute(product);
		return "products/createOrUpdateProductForm";
	}

	@PostMapping(value = "/{productId}/edit")
	public String processUpdateProductForm(@Valid final Product product, final BindingResult result, final Model model, @PathVariable("productId") final int productId) {
		if (result.hasErrors()) {
			model.addAttribute("product", product);
			return "products/createOrUpdateProductForm";
		} else {
			product.setId(productId);
			this.productService.saveProduct(product);
			return "redirect:/products/{productId}";
		}
	}

}