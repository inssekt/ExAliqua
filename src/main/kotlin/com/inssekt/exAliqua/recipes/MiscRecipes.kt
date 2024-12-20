package com.inssekt.exAliqua.recipes

import com.inssekt.exAliqua.CustomItems
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class MiscRecipes(private val plugin: JavaPlugin) {

    private val registeredRecipeKeys = mutableSetOf<NamespacedKey>()

    fun registerRecipes()
    {
        registerRockRecipe()
        registerCrookRecipe()
    }

    fun registerRockRecipe()
    {
        val rockItem = CustomItems.ROCK
        val recipeKey = NamespacedKey(plugin, "rock_to_cobblestone")

        val result = ItemStack(Material.COBBLESTONE)

        val recipe=ShapedRecipe(recipeKey, result);

        recipe.shape("RR ", "RR ", "   ")
        recipe.setIngredient('R', RecipeChoice.ExactChoice(rockItem))

        Bukkit.addRecipe(recipe)
        registeredRecipeKeys.add(recipeKey)
    }

    fun registerCrookRecipe()
    {
        val recipeKey = NamespacedKey(plugin, "crook")

        val result = ItemStack(CustomItems.CROOK)

        val recipe=ShapedRecipe(recipeKey, result);

        recipe.shape("SS ", " S ", " S ")
        recipe.setIngredient('S', Material.STICK)

        Bukkit.addRecipe(recipe)
        registeredRecipeKeys.add(recipeKey)
    }

    fun unregisterRecipes() {
        registeredRecipeKeys.forEach { recipeKey ->
            Bukkit.removeRecipe(recipeKey)
        }

        // Clear the set to avoid memory leaks
        registeredRecipeKeys.clear()
    }

}