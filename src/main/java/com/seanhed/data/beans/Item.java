package com.seanhed.data.beans;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private long itemNo;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "amount", nullable = false)
	private int amount;
	@Column(name = "inventoryCode", nullable = false)
	private String inventoryCode;

}
