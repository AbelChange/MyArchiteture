# AGENTS.md

## Project Overview

This repository is a multi-module Android project centered around `:app` as the host application.

Key modules:

- `:app`: main shell app, demo pages, navigation graphs, app-level initialization
- `:module_base`: shared infrastructure, service interfaces, routing config, DB, common utilities
- `:module_login`: login/account service implementation
- `:module_compose`: Compose demo/service implementation
- `:module_native`: native capability/service implementation
- `:library`: shared Android/library code
- `:time_cost`: custom Gradle plugin for method instrumentation
- `:learn_note`: notes / learning examples, not core app runtime

## Architecture Notes

- The project mixes Android Navigation and ARouter.
- ARouter service contracts live mainly in `module_base/src/main/java/com/ablec/module_base/config/RouterConfig.kt`.
- Cross-module service lookup is centralized in `module_base/src/main/java/com/ablec/module_base/service/RouterServiceManager.kt`.
- App startup uses `AppApplication` plus AndroidX Startup initializer(s), especially `BaseInitializer`.
- Hilt is enabled in the app and shared modules.

## Important Entry Points

- Application: `app/src/main/java/com/ablec/myarchitecture/AppApplication.kt`
- Main activity: `app/src/main/java/com/ablec/myarchitecture/logic/main/MainActivity.kt`
- Splash activity: `app/src/main/java/com/ablec/myarchitecture/logic/main/SplashActivity.kt`
- Startup initializer: `module_base/src/main/java/com/ablec/module_base/provider/BaseInitializer.kt`
- ARouter scheme bridge: `app/src/main/java/com/ablec/myarchitecture/SchemeFilterActivity.kt`

## Build And Sync

Common commands:

- `./gradlew :app:assembleIntranetAtestDebug`
- `./gradlew :app:installIntranetAtestDebug`
- `./gradlew :app:help`
- `./gradlew build`
- `./gradlew test`

Notes:

- Product flavors include `server` and `market`.
- The `app/channel` file participates in market flavor generation.
- The project uses a local custom plugin module `:time_cost`.
- If IDE navigation is broken, first verify Gradle sync succeeds from the project root.

## Coding Guidelines

- Prefer Kotlin for new Android code unless matching surrounding Java code is the better fit.
- Follow existing module boundaries; do not introduce circular dependencies.
- Put reusable contracts/interfaces in `:module_base`, and concrete implementations in feature modules.
- Keep routing constants centralized instead of scattering raw route strings.
- Preserve existing architecture patterns before introducing new ones.
- Avoid broad refactors unless the task explicitly asks for them.

## Dependency And Tooling Notes

- AGP version is managed in `gradle/libs.versions.toml`.
- Kotlin version is managed in `gradle/libs.versions.toml`.
- Annotation processing currently uses `kapt`.
- Room schemas are exported under `module_base/schemas`.

## When Editing This Repo

- Check whether a change belongs in `:app` or in a feature/shared module before editing.
- Be careful with generated/navigation/annotation-processed code assumptions; confirm source declarations first.
- For routing changes, verify both the route constant and the `@Route` implementation.
- For startup changes, consider both `AppApplication` and `BaseInitializer`.
- For flavor-specific issues, confirm which variant is affected before changing build logic.

## Validation Expectations

After meaningful changes, prefer the smallest relevant verification:

- Gradle sync-style check: `./gradlew :app:help`
- Compile affected module: `./gradlew :app:compileIntranetAtestDebugKotlin`
- Full app build when needed: `./gradlew :app:assembleIntranetAtestDebug`

If a task only touches docs or comments, heavy validation is optional.

## Agent Tips

- Open the repository from the root containing `settings.gradle.kts`.
- If code navigation in Android Studio fails, suspect IDE indexing/sync before suspecting source layout.
- When investigating unresolved symbols across modules, inspect `settings.gradle.kts`, module dependencies, and generated flavor variants first.
