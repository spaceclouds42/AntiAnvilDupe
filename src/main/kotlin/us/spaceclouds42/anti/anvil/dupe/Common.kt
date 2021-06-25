package us.spaceclouds42.anti.anvil.dupe

import net.fabricmc.api.ModInitializer
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import org.spaceserve.ekho.ekho

object Common : ModInitializer {
    val CONFIG = AntiAnvilDupeConfig()
    val replacements = mutableMapOf(
        "playername" to "playername not found",
        "coords" to "coords not found",
        "dimension" to "dimension not found",
        "dimensionId" to "dimensionId not found"
    )

    override fun onInitialize() {
        println("Anvil duping is no more")
        CONFIG.load()
    }

    fun getStaffMsg(): Text = ekho("[AntiAnvilDupe] ") {
        style { darkRed
            clickEvent {
                runCommand = "/execute in ${replacements["dimensionId"]} run tp @s ${replacements["coords"]}"
            }
        }
        replacements["playername"]!! { style { yellow } }
        " potentially attempted to anvil dupe" { style { red } }
        newLine
        "[Click to teleport to where ${replacements["playername"]} tried to dupe (${replacements["coords"]})]" { style { darkGreen} }
        newLine
        "Please note that it is possible to legitimately break an anvil in this way" { style { gray } }
    }

    fun getAllMsg(): Text = ekho("[") {
        style { darkGray }
        "Mod" { style { darkAqua } }
        "] ["()
        "BM" { style { darkRed; bold } }
        "] "()
        "Wamens " { style { darkAqua; bold } }
        "> " { style { gray; bold } }
        "uh oh, i think ${replacements["playername"]} just tried to dupe at ${replacements["coords"]} (${replacements["dimension"]})" { style { white } }
    }
}
