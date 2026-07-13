# 🦊 Avoid(Lib)

AvoidLib is a wrapper around Minecraft's API (+ additional features), that resolves:
- 🔃 Environmental classloading issues
- ↪️ Version incompatibilities
- 🔀 Copyright disputes
- ⬅️ Modloader dependence

**🏳️‍🌈 AvoidLib stands for the LGBTQIA+ community.**

## 🏺 This is a library mod

This mod doesn't add any content on its own.<br/>
It's created to assist developers in making their mods easier.

## 📄 How to develop for AvoidLib

Of course, you can do the standard mod with library way,
but that approach isn't recommended.<br/>
What you should use instead is the custom AvoidLib mod format
(which is cross-compatible).

**Take a look at [the quickstart](https://github.com/AvoidLib/Avoid/wiki/Creating-your-mod).**

## 💥 Avoid mods crash with my modloader

Try moving them onto a new directory called `avoidmods` in your game folder.<br/>
This skips non-Avoid-caused loading.

If it still doesn't work after, it's probably some of these mods' issue.

Make sure to read the game and crash log.
