package com.inssekt.exAliqua.recipes

import com.inssekt.exAliqua.CustomItems
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.NamespacedKey;
import java.util.*

class HammerRecipes(private val plugin: JavaPlugin) {

    private val registeredRecipeKeys = mutableSetOf<NamespacedKey>()

    // List of all the wood plank types that should work for the wooden hammer recipe
    private val woodPlanks = listOf(
        Material.OAK_PLANKS,
        Material.SPRUCE_PLANKS,
        Material.BIRCH_PLANKS,
        Material.JUNGLE_PLANKS,
        Material.ACACIA_PLANKS,
        Material.DARK_OAK_PLANKS,
        Material.MANGROVE_PLANKS,
        Material.CHERRY_PLANKS
    )

    private val materialMap = mapOf(
        Material.IRON_INGOT to CustomItems.IRON_HAMMER,
        Material.COBBLESTONE to CustomItems.STONE_HAMMER,
        Material.DIAMOND to CustomItems.DIAMOND_HAMMER
    )

    // Method to register all hammer recipes
    fun registerRecipes() {
        // Register the wooden hammer recipe for each type of wood plank
        woodPlanks.forEach { plankType ->
            registerWoodenHammerRecipe(plankType)
        }

        materialMap.forEach { (material, item) ->
            registerHammerRecipe(material, item)
        }
    }

    // Register the recipe for the Wooden Hammer with a specific type of plank
    private fun registerWoodenHammerRecipe(plankType: Material) {
        // Create the NamespacedKey for the recipe (namespace:recipe_name)
        val recipeKey = NamespacedKey(plugin, "wooden_hammer_${plankType.name.lowercase(Locale.getDefault())}")

        val recipe = ShapedRecipe(recipeKey, CustomItems.HAMMER)
        recipe.shape(" WW", " SW", "S  ") // AWW, ASW, ASA pattern
        recipe.setIngredient('W', plankType)   // Specific wood plank type (e.g., OAK_PLANKS)
        recipe.setIngredient('S', Material.STICK) // Stick

        // Ensure the recipe is registered
        Bukkit.addRecipe(recipe)
        registeredRecipeKeys.add(recipeKey)
    }

    // Register stone, iron, and diamond hammer recipes
    private fun registerHammerRecipe(material: Material, hammerItem: org.bukkit.inventory.ItemStack) {
        // Create the NamespacedKey for the recipe (namespace:recipe_name)
        val recipeKey = NamespacedKey(plugin, "hammer_${material.name.lowercase(Locale.getDefault())}")

        val recipe = ShapedRecipe(recipeKey, hammerItem)
        recipe.shape(" WW", " SW", "S  ")
        recipe.setIngredient('W', material)   // Specific material type (e.g., IRON_INGOT, GOLD_INGOT, DIAMOND)
        recipe.setIngredient('S', Material.STICK) // Stick

        // Ensure the recipe is registered
        Bukkit.addRecipe(recipe)
        registeredRecipeKeys.add(recipeKey)
    }

    fun unregisterRecipes() {
        // Iterate over all the registered recipe keys
        registeredRecipeKeys.forEach { recipeKey ->
            Bukkit.removeRecipe(recipeKey) // Remove the recipe using its NamespacedKey
        }

        // Clear the set to avoid memory leaks
        registeredRecipeKeys.clear()
    }
}