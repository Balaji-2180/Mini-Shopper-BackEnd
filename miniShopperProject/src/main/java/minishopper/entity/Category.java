package minishopper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name="categories", indexes= {@Index(name="idx_category_id", columnList = "category_id")})
public class Category {
	
	@Id
	@Column(name="categoryId")
	private String categoryId;
	
	@Column(name= "categoryTitle", length=100, nullable=false)
	private String categoryTitle;
	
	@Column(name="description")
	private String description;

}
