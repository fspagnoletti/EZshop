package it.polito.ezshop.model;
import it.polito.ezshop.data.ProductType;

public class ProductTypeClass implements ProductType{
	int id;
	String barCode;
	String productDescription;
	double pricePerUnit; 
	String note;
	String location;
	int quantity;
	
	public ProductTypeClass (String description, String productCode, double pricePerUnit, String note) {
		this.productDescription=description;
		this.barCode=productCode;
		this.pricePerUnit=pricePerUnit;
		this.note=note;
	}
	
	@Override
	public
	Integer getQuantity() {
		return this.quantity;
	}

	@Override
	public
    void setQuantity(Integer quantity) {
		this.quantity=quantity;
	}

	@Override
	public
    String getLocation() {
		return this.location;
	}

	@Override
	public
    void setLocation(String location) {
		this.location=location;
	}

	@Override
	public
    String getNote() {
		return this.note;
	}

	@Override
	public
    void setNote(String note) {
		this.note=note;
	}

	@Override
	public
    String getProductDescription() {
		return this.productDescription;
	}

	@Override
	public
    void setProductDescription(String productDescription) {
		this.productDescription=productDescription;
	}

	@Override
	public
    String getBarCode() {
		return this.barCode;
	}

	@Override
	public
    void setBarCode(String barCode) {
		this.barCode=barCode;
	}

	@Override
	public
    Double getPricePerUnit() {
		return this.pricePerUnit;
	}

	@Override
	public
    void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit=pricePerUnit;
	}

	@Override
	public
    Integer getId() {
		return this.id;
	}

	@Override
	public
    void setId(Integer id) {
		this.id=id;
	}

	@Override
	public String toString() {
		return "ProductTypeClass [id=" + id + ", barCode=" + barCode + ", productDescription=" + productDescription
				+ ", pricePerUnit=" + pricePerUnit + ", note=" + note + ", location=" + location + ", quantity="
				+ quantity + "]";
	}
	
	
}
