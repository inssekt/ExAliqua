package com.inssekt.exAliqua

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object CustomItems {


    val ROCK: ItemStack by lazy {
        val item = ItemStack(Material.FIREWORK_STAR)
        val meta = item.itemMeta

        if (meta != null)
        {
            meta.setDisplayName("§7Rock")
            meta.lore = listOf(
                "§7Rocks from the ground.", // Custom description
                "§7Craft 4 into cobblestone."
            )
            item.itemMeta = meta
        }

        item
    }


    val HAMMER: ItemStack by lazy {
        val item = ItemStack(Material.WOODEN_PICKAXE)
        val meta = item.itemMeta

        if (meta != null)
        {
            meta.setDisplayName("§6Hammer")
            meta.lore = listOf(
                "§7Breaks blocks into smaller pieces.", // Custom description
                "§7Cobblestone → Gravel → Sand → Dirt"
            )
            item.itemMeta = meta
        }

        item
    }

    // Stone Hammer (using Stone Pickaxe)
    val STONE_HAMMER: ItemStack by lazy {
        val item = ItemStack(Material.STONE_PICKAXE)
        val meta = item.itemMeta

        if (meta != null) {
            meta.setDisplayName("§7Stone Hammer") // Custom name for Stone Hammer
            meta.lore = listOf(
                "§7Breaks blocks into smaller pieces.", // Custom description
                "§7Cobblestone → Gravel → Sand → Dirt"
            )
            item.itemMeta = meta
        }

        item
    }

    // Iron Hammer (using Iron Pickaxe)
    val IRON_HAMMER: ItemStack by lazy {
        val item = ItemStack(Material.IRON_PICKAXE)
        val meta = item.itemMeta

        if (meta != null) {
            meta.setDisplayName("§fIron Hammer") // Custom name for Iron Hammer
            meta.lore = listOf(
                "§7Breaks blocks into smaller pieces.", // Custom description
                "§7Cobblestone → Gravel → Sand → Dirt"
            )
            item.itemMeta = meta
        }

        item
    }

    // Diamond Hammer (using Diamond Pickaxe)
    val DIAMOND_HAMMER: ItemStack by lazy {
        val item = ItemStack(Material.DIAMOND_PICKAXE)
        val meta = item.itemMeta

        if (meta != null) {
            meta.setDisplayName("§bDiamond Hammer") // Custom name for Diamond Hammer
            meta.lore = listOf(
                "§7Breaks blocks into smaller pieces.", // Custom description
                "§7Cobblestone → Gravel → Sand → Dirt"
            )
            item.itemMeta = meta
        }

        item
    }

    val ITEM_MAP: Map<String, ItemStack> by lazy {
        mapOf(
            "hammer" to HAMMER,
            "stone_hammer" to STONE_HAMMER,
            "iron_hammer" to IRON_HAMMER,
            "diamond_hammer" to DIAMOND_HAMMER,
        )
    }

    val HAMMER_MAP: Map<String, ItemStack> = mapOf(
        "stone" to STONE_HAMMER,
        "iron" to IRON_HAMMER,
        "diamond" to DIAMOND_HAMMER,
        "wooden" to HAMMER
    )

}