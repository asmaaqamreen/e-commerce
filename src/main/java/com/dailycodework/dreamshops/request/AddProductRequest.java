package com.dailycodework.dreamshops.request;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class AddProductRequest {
    public AddProductRequest(String name, String brand, BigDecimal price, int inventory, String description,
			Category category) {
    	this.name=name;
    	this.brand=brand;
    	this.price=price;
    	this.inventory=inventory;
    	this.description=description;
    	this.category=category;
    	
		// TODO Auto-generated constructor stub
	}
	private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
