package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String show(@PathVariable Long id, Model model){
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeform";
    }


    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return String.format("redirect:/recipe/%s/show", savedCommand.getId());
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id){
        log.debug("Deleting id: " + id);
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

}
