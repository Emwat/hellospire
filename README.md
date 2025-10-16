# Basic Mod

Brings Sonic the Hedgehog as a character to Slay the Spire. He plays like a Prismatic Shard.

# Requirements
Requires subscription to
[BaseMod](https://github.com/daviscook477/BaseMod/releases)
[ModTheSpire](https://github.com/kiooeht/ModTheSpire/releases)

# Gameplay Features
- Creating cards
- Manipulating cards' energy costs
- Voice clips, Music Patches
- 10+ patches
 - Chao Evolve at rest site
 - Dialogue against the Champ
- Relics
- 1 Event w/ combat reward
- Easy Config
- Ending Pictures

# BaseMod things
- <kbd>~</kbd> Dev Console - This key is at the upper left corner of the keyboard, next to <kbd>1</kbd> and underneath <kbd>Esc</kbd>. The dev console gives me easy access to events. I've created a few dev console commands: Resetting tutorials.
- <kbd>Shift</kbd><kbd>E</kbd> imGUI - Easy discard, easy upgrade multiple times, easy infinite energy

# Good wiki links
- [How to Start Modding] (https://discord.com/channels/309399445785673728/1145803879821361293/1145803879821361293)
- [BasicMod (getting started)] (https://github.com/Alchyr/BasicMod/wiki) - this is the base for this mod.
- [Base Mod wiki (40 Pages)] (https://github.com/daviscook477/BaseMod/wiki)
- [StSLib wiki] (https://github.com/kiooeht/StSLib/wiki)
- [Spreadsheet of Vanilla Characters] (https://docs.google.com/spreadsheets/d/1c5j-O1zIckvEPtI4sSI7YeG6v1EptKxRwmPV-J73tCA/edit?pli=1#gid=0 )
- [Character Design Pitfalls] (https://discord.com/channels/309399445785673728/1146430771528220792/1146431508119306340)
- [Packmaster Github] (https://github.com/erasels/PackmasterCharacter) - this has been code reviewed. My code has NOT been code reviewed sadface.

```
- Common mod pitfalls comment by BigGucciSosa
- too many random card ideas that don't synergize well with each other, leading to a disjointed-feeling drafting experience
- too much text on the cards, often indicating ideas that are too complex for the actual amount of value and fun they bring
- too many scattered mechanics preventing the mod from FEELING cohesive from a game design standpoint.
- ideas that are too similar to base game mechanics, causing the character to not feel differentiated enough.
```

# Good modding tools - HUGE THANKS TO ALL OF YOU.
- Slay the Spire Discord - #modding-technical - Thank you guys so much again for helping me out.
- [StSModdingToolCardImagesCreator] (https://github.com/JohnnyBazooka89/StSModdingToolCardImagesCreator) - crops and masks card images.
- [Loadout Mod] (https://steamcommunity.com/sharedfiles/filedetails/?id=2814267979) - there's a tab on the left side of the screen which lets you spawn any card, any relic, and any enemy.
- [Run Resumer] (https://steamcommunity.com/sharedfiles/filedetails/?id=3127212809) - to test against the Heart after balancing cards. Also easy campfire access.
- [Better Debug] (https://steamcommunity.com/sharedfiles/filedetails/?id=2351301825) - type "debug" in the dev console to use. It helps when I need to look at which addToBot/addToTop Action is playing.
- [Modded Spire Exporter] (https://steamcommunity.com/sharedfiles/filedetails/?id=2069872611&searchtext=exporter) - Export every card of a mod to an html file! Now I can Ctrl + F for specific descriptions in Packmaster
- [Googly Eyes steam] (https://steamcommunity.com/sharedfiles/filedetails/?id=1615430126) - Be warned: Every time you open the game, the googly-eye-locations.json DOES NOT append new entries to the existing file. It overwrites the file! I learned that lesson the hard way.
- [Googly Eyes github] (https://github.com/twanvl/sts-googly-mod/)
- [Spriter] (https://www.youtube.com/watch?v=hNyEVGeMf-o) - this is the tool used for animating in this mod.
- [Mega Transparent The Spire 2.0] https://steamcommunity.com/sharedfiles/filedetails/?id=3309442660 - you can see the debug log while you play.
- Steam > Settings > Game Recording > Record in Background. Play game. Steam > Library > Slay the Spire > video is underneath POST-GAME SUMMARY. Amazing debugging tool.
- YouTube - You can double right-click a video and take snapshot.
- YouTube - You can also press <kbd>.</kbd> or <kbd>,</kbd> to move the video by one frame.

# IntelliJ shortcuts
- Double tapping <kbd>Shift</kbd> : brings up a Universal Code Search Tool
- <kbd>Ctrl</kbd><kbd>Shift</kbd><kbd>F</kbd> : Find All
- <kbd>Alt</kbd><kbd>Enter</kbd> : Context Actions
- <kbd>Ctrl</kbd><kbd>B</kbd> : Decompile/Direct to source

# Every mod character should do this
- Before you publish, you should make sure your modID is all lowercase for Chinese compatibility.
- Create a win screen. I'm tired of the Ironclad ending on modded characters. It's not that hard. You don't need 3 pictures. You can just use one picture. See Sonic.java.
```java
   @Override
    public List<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(endingPath("ending.png")));
        return panels;
    }
```

# Steam Workshop Link
- https://steamcommunity.com/sharedfiles/filedetails/?id=3489847473
- Less than 25 ratings = 0 stars
- More than 25 ratings = 3 stars
- More than 30 ratings = 4 stars
- More than 200+ ratings = 5 stars

# My Design Process
1) Think of an incredibly awesome attack
2) let’s find the closest vanilla card’s description 
3) figure out the synergy later

# My review of my mod
I think people are leaning towards okay/meh with my mod, because I very heavily leaned towards a vanilla character. There's no crazy new mechanic. There's no vfx. It doesn't add anything new to Slay the Spire.

I designed Sonic to be that way, b/c 1) it's my first mod and 2) It's loyal to his character. In the Sonic Advance series, ALL of his friends have an extra ability, but Sonic is very vanilla except for the fact that he can go Super Sonic for the ending.

People don't like single-use keywords and at the time "If most Left/Right" was only for Quick Air/Quick Step. Well, I decided to apply it to the rare cards. The problem with it is I never remember which cards are lefties and righties now. The idea is that LEFT symbolized for being far away from your enemy and RIGHT symbolized up close and personal. I don't think that flavor carried well enough and I didn't add cards that help activate their effects.


