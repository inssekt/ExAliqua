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

    fun registerRecipes() {
        woodPlanks.forEach { plankType ->
            registerWoodenHammerRecipe(plankType)
        }

        materialMap.forEach { (material, item) ->
            registerHammerRecipe(material, item)
        }
    }

    private fun registerWoodenHammerRecipe(plankType: Material) {
        val recipeKey = NamespacedKey(plugin, "wooden_hammer_${plankType.name.lowercase(Locale.getDefault())}")

        val recipe = ShapedRecipe(recipeKey, CustomItems.HAMMER)
        recipe.shape(" WW", " SW", "S  ") // AWW, ASW, ASA pattern
        recipe.setIngredient('W', plankType)   // Specific wood plank type (e.g., OAK_PLANKS)
        recipe.setIngredient('S', Material.STICK) // Stick

        Bukkit.addRecipe(recipe)
        registeredRecipeKeys.add(recipeKey)
    }

    private fun registerHammerRecipe(material: Material, hammerItem: org.bukkit.inventory.ItemStack) {
        val recipeKey = NamespacedKey(plugin, "hammer_${material.name.lowercase(Locale.getDefault())}")

        val recipe = ShapedRecipe(recipeKey, hammerItem)
        recipe.shape(" WW", " SW", "S  ")
        recipe.setIngredient('W', material)   // Specific material type (e.g., IRON_INGOT, COBBLESTONE, DIAMOND)
        recipe.setIngredient('S', Material.STICK) // Stick

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