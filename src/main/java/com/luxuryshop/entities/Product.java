package com.luxuryshop.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table (name = "tbl_products")
public class Product extends ParentEntity implements Serializable, Comparable<Product>{
	
	@Column(name = "title", length = 500, nullable = false)
	private String title;

	@Column(name = "price", nullable = false)
	private float price;

	@Column(name = "price_old", nullable = false)
	private float priceOld;
	
	@Column(name = "short_description", nullable = true, columnDefinition = "TEXT")
	private String shortDescription;
	
	@JsonIgnore
	@Lob
	@Column(name = "detail_description", nullable = true, columnDefinition = "LONGTEXT")
	private String detailDescription;
	
	@Column(name = "seo",length = 100, nullable = true)
	private String seo;

	@Column(name = "ishot", nullable = true)
	private Boolean isHot;

	@Column(name = "isnew", nullable = true)
	private Boolean isNew;

	@Column(name = "issale", nullable = true)
	private Boolean isSale;
	
	@Column(name = "rate", columnDefinition = "integer default 5")
	private Integer rate;
	
	// get only specific property in relation: many-to-one or one-to-many ,...
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne
	@JoinColumn (name = "category_id" , nullable = true )
	private Category category;
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "path")
	@JsonIdentityReference(alwaysAsId = true)
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductImages> productImages = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "productCart" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
	private List<Cart> carts = new ArrayList<>(); // focus
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne
	@JoinColumn(name = "collection_id" , nullable = true)
	private Collection collection;
	
	@OneToOne(mappedBy = "product",  cascade = CascadeType.ALL)
	private ProductDetail productDetail;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "fProducts" , fetch = FetchType.LAZY)
	List<User> likedUsers;
	
	@JsonIgnore
	@OneToMany (mappedBy = "product" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	List<RateProduct> rates;
	@Override
	public int compareTo(Product product) {
		return (int) (this.price - product.getPrice());
	}
	
	public boolean userLiked(String username) {
		if (username == null) return false;
		List<User> users = this.getLikedUsers();
		for (User user : users) {
			if (user.getUsername().equals(username)) return true;
		}
		return false;
	}
	
	public void caculateRate() {
		double newRate = 0;
		for (RateProduct rate : this.getRates()) {
			newRate += rate.getRate();
		}
		newRate = newRate / this.getRates().size();
		this.rate = (int) Math.round(newRate);
	}
}
