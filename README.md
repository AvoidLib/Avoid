# 🦊 Avoid(Lib)

AvoidLib is a wrapper around Minecraft's API (+ additional features), that resolves:
- 🔃 Environmental classloading issues
- ↪️ Version incompatibilities
- 🔀 Copyright disputes
- ◀️ Modloader dependence

**🏳️‍🌈 AvoidLib stands for the LGBTQIA+ community.**

## 🏺 This is a library mod

This mod doesn't add any content on its own.<br/>
It's created to assist developers in making their mods easier.

## 📄 How to develop for AvoidLib

Of course, you can do the standard mod with library way,
but that approach isn't recommended.<br/>
What you should use instead is the custom AvoidLib mod format
(which is cross-compatible).

*<b>(Recommended IDE: <u>IntelliJ IDEA</u>)</b>*

1. **Project Setup**
    <hr/>
    First, make a new Java project.<br/>
    Second, create a `libs` folder and put an AvoidLib jar there.<br/>
    Third, go to your <i><u>build.gradle(.kts)</u></i> and add this onto your <u>dependencies</u> section:
    
    ```groovy
    dependencies {
        compileOnly(fileTree(rootProject.file("libs")))  // <- This!
    }
    ```
    
    Fourth, right-click on the AvoidLib jar within that libs folder in your IDE, and select `Add As Library...`.<br/>
    In that pop-up, set the level as `Project Library`, and click `OK`.
    
    Finally, sync your project if you haven't already *(shift 3 times, tab `Actions`, type `sync all`, click `Sync All Gradle Projects`)*.<br/>
    Now you should be able to use AvoidLib classes in your code and compile your addon as a jar.
    <br/><br/>
2. **Avoid Setup**
   <hr/>

   Although your project seems fine for now, it still can't be added to the game.<br/>
   To fix this, you need to create the `avoid.mod.json` file in your `src/main/resources` project folder; here's how it should look like:

   ```json
   {
    "__schema": 1,
    
    "id": "testmod",
    "version": "1.0",
    "version-system": "semver",
    
    "name": "TestMod",
    "author": "Olafcio",
    "description": "An example mod made with AvoidLib.",
    
    "main-class": "com.example.avoidtmpl.Main"
   }
   ```

   After doing that, your mod .jar should work in the game's mods folder, if it also has Avoid installed.

## 💥 Avoid mods crash with my modloader

Try moving them onto a new directory called `avoidmods` in your game folder.<br/>
This skips non-Avoid-caused loading.

If it still doesn't work after, it's probably some of these mods' issue.

Make sure to read the game and crash log.
