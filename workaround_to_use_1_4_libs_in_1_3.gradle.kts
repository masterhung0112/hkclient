val pluginAction: Plugin<*>.() -> Unit = {
    val pluginVersion = try {
        this.javaClass.getMethod("getKotlinPluginVersion").invoke(this) as String
    } catch(e: Exception) { null }
    if (pluginVersion != null && pluginVersion.startsWith("1.3")) {
        val jsCompilerAttr = Attribute.of("org.jetbrains.kotlin.js.compiler", String::class.java)
        project.dependencies.attributesSchema.attribute(jsCompilerAttr) {
            this.disambiguationRules.add(KotlinJsCompilerDisambiguationRule::class.java)
        }
    }
}
project.plugins.withId("org.jetbrains.kotlin.multiplatform", pluginAction)
project.plugins.withId("org.jetbrains.kotlin.js", pluginAction)
// project.plugins.withId("kotlin2js", pluginAction) // maybe even `kotlin2js`
private class KotlinJsCompilerDisambiguationRule : AttributeDisambiguationRule<String> {
    override fun execute(details: MultipleCandidatesDetails<String>) {
        details.closestMatch("legacy")
    }
}