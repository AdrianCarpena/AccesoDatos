/**
 * 
 */
/**
 * 
 */
module P1_2 {
	
	requires xstream;
	requires com.google.gson;
	requires java.xml;
	

	opens PruebasXStream to xstream,com.google.gson;

	exports PruebasXStream;
}