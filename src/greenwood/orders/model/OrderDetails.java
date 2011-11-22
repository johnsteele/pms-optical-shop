package greenwood.orders.model;

/**
 * A container for the finer-grained details of an Order.
 * 
 * @author John Steele
 */
public class OrderDetails {

	/**
	 * Job type.
	 */
	private boolean doFinishLenses = false;
	
	/**
	 * Which lens.
	 */
	private String lens = "";
	
	/**
	 * The brand.
	 */
	private String brand = "";
	
	/**
	 * The design.
	 */
	private String design = "";
	
	/**
	 * Lens options.
	 */
	private String options = "";
	
	/** 
	 * AR Coating.
	 */
	private boolean doARcoating = false;
	
	/**
	 * Mirror coated.
	 */
	private boolean doMirrorCoating = false;
	
	
	/**
	 * Creates a OrderDetails with default values.
	 */
	public OrderDetails() {}


	public boolean isDoFinishLenses() {
		return doFinishLenses;
	}


	public void setDoFinishLenses(boolean doFinishLenses) {
		this.doFinishLenses = doFinishLenses;
	}


	public String getLens() {
		return lens;
	}


	public void setLens(String lens) {
		this.lens = lens;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getDesign() {
		return design;
	}


	public void setDesign(String design) {
		this.design = design;
	}


	public String getOptions() {
		return options;
	}


	public void setOptions(String options) {
		this.options = options;
	}


	public boolean isDoARcoating() {
		return doARcoating;
	}


	public void setDoARcoating(boolean doARcoating) {
		this.doARcoating = doARcoating;
	}


	public boolean isDoMirrorCoating() {
		return doMirrorCoating;
	}


	public void setDoMirrorCoating(boolean doMirrorCoating) {
		this.doMirrorCoating = doMirrorCoating;
	}
}
