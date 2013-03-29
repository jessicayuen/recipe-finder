import static org.junit.Assert.assertEquals;
import org.junit.Test;
import team13.cmput301.recipefinder.model.Ingredient;


public class IngredientsTest {
	
	@Test
	public void testIngredientsConstructor() {

		Float quantity = null;
		@SuppressWarnings("null")
		Ingredient ingredient = new Ingredient("Name", quantity);
		
		assertEquals("Name", ingredient.getIngredient());
		assertEquals(quantity, ingredient.getIngredient());
		assertEquals(quantity, null);
		assertEquals("", ingredient.getIngredient());
		assertEquals("", ingredient.getQuantity());
		assertEquals("apples", "apple");
	}

}
