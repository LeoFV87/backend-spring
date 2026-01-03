package ec.edu.ups.icc.fundamentos01.products.dtos;

public class ProductsResponseDto {
    
  private int id;
    private String name;
    private Double price;
    private Integer stock;
    private String createdAt; 

 
    public ProductsResponseDto() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

  

}