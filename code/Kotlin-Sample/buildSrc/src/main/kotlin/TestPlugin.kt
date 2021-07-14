import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by benny.
 */
class TestPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        println("Test Plugin applied!")
    }
}