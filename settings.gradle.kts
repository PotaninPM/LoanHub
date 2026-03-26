pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "ProdFinalTour2026"
include(":app")
include(":sources")
include(":sources:common")
include(":sources:common:common-database")
include(":sources:common:common-network")
include(":sources:common:uikit")
include(":sources:common:common-di")
include(":sources:features")
include(":sources:features:feature-my-requests")
include(":sources:features:feature-application-preview")
include(":sources:features:feature-loans-list")
include(":sources:features:feature-loan-application")
include(":sources:features:feature-application-details")
include(":sources:features:feature-loan-details")
include(":sources:features:feature-profile")
include(":sources:features:feature-edit-profile")
include(":sources:features:feature-auth")
include(":sources:core")
include(":sources:core:core-navigation")
include(":sources:core:core-utils")
include(":sources:core:core-prefs")
include(":sources:core:core-auth")
