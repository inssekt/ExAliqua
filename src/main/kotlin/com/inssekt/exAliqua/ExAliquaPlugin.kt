package com.inssekt.exAliqua

import com.inssekt.exAliqua.commands.ExGiveCommand
import com.inssekt.exAliqua.commands.ExItemsCommand
import com.inssekt.exAliqua.events.*
import com.inssekt.exAliqua.gui.ExItemsGUIListener
import com.inssekt.exAliqua.recipes.HammerRecipes
import com.inssekt.exAliqua.recipes.MiscRecipes
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class ExAliquaPlugin : JavaPlugin() {

    private lateinit var hammerRecipes: HammerRecipes
    private lateinit var miscRecipes: MiscRecipes

    override fun onEnable() {



        val pluginManager: PluginManager = server.pluginManager


        //Listeners
        pluginManager.registerEvents(HammerEventListener(), this)
        pluginManager.registerEvents(SiftingListener(), this)
        pluginManager.registerEvents(ExItemsGUIListener(), this)
        pluginManager.registerEvents(RockGatheringListener(), this)
        pluginManager.registerEvents(CrookListener(), this)
        pluginManager.registerEvents(SilkwormListener(this), this)



        //Commands
        getCommand("exgive")?.setExecutor(ExGiveCommand())
        getCommand("exitems")?.setExecutor(ExItemsCommand())

        //Recipes
        hammerRecipes = HammerRecipes(this)
        hammerRecipes.registerRecipes()
        miscRecipes = MiscRecipes(this)
        miscRecipes.registerRecipes()
    }

    override fun onDisable() {
        hammerRecipes.unregisterRecipes() // Unregister recipes when plugin is disabled
        miscRecipes.unregisterRecipes() // Unregister recipes when plugin is disabled
    }

}
