package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    public static final long RECIPE_ID_1 = 1L;
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController(recipeService);
    }

    @Test
    public void testGetRecipe() throws Exception {

        //Given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID_1);

        //when
        when(recipeService.findById(eq(RECIPE_ID_1))).thenReturn(recipe);

        //then
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))                       .andExpect(model().attributeExists("recipe"));


    }

}