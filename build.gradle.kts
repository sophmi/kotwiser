import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
	alias(libs.plugins.kotlin)
	alias(libs.plugins.intelliJPlatform)
	alias(libs.plugins.changelog)
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

kotlin {
	jvmToolchain(21)
}

repositories {
	mavenCentral()

	intellijPlatform {
		defaultRepositories()
	}
}

dependencies {
	testImplementation(kotlin("reflect"))
	testImplementation(libs.junit)

	// https://youtrack.jetbrains.com/issue/IJPL-157292/lib-testFramework.jar-is-missing-library-opentest4j
	testImplementation("org.opentest4j:opentest4j:1.3.0")

	intellijPlatform {
		create(providers.gradleProperty("platformType"), providers.gradleProperty("platformVersion"))

		bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })
		plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

		pluginVerifier()
		zipSigner()
		testFramework(TestFrameworkType.Platform)
	}
}

intellijPlatform {
	pluginConfiguration {
		version = providers.gradleProperty("pluginVersion")

		description = providers.fileContents(layout.projectDirectory.file("src/main/resources/META-INF/plugin_description.html")).asText.map {
			val start = it.indexOf('\n') // remove the <html> tag
			val end = it.lastIndexOf("</html>")

			markdownToHTML(it.substring(start, end))
		}

		val changelog = project.changelog // local variable for configuration cache compatibility
		changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
			val item = (changelog.getOrNull(pluginVersion) ?: changelog.getUnreleased()).apply {
				withHeader(false)
				withEmptySections(false)
			}

			changelog.renderItem(item, Changelog.OutputType.HTML)
		}

		ideaVersion {
			sinceBuild = providers.gradleProperty("pluginSinceBuild")
			untilBuild = providers.gradleProperty("pluginUntilBuild")
		}
	}

	signing {
		certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
		privateKey = providers.environmentVariable("PRIVATE_KEY")
		password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
	}

	publishing {
		token = providers.environmentVariable("PUBLISH_TOKEN")
		channels = providers.gradleProperty("pluginVersion")
			.map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
	}

	pluginVerification {
		ides {
			recommended()
		}
	}
}

intellijPlatformTesting {
	testIde
}

// https://github.com/JetBrains/gradle-changelog-plugin
changelog {
	groups.empty()
	repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
}

tasks {
	withType<Jar> {
		exclude("META-INF/plugin_description.html")
	}

	publishPlugin {
		dependsOn(patchChangelog)
	}
}
