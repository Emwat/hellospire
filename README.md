# Basic Mod

Brings Sonic the Hedgehog as a character to Slay the Spire.

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

# BaseMod things
- <kbd>~</kbd> Dev Console - This key is at the upper left corner of the keyboard, next to <kbd>1</kbd> and underneath <kbd>Esc</kbd>. The dev console gives me easy access to events. I've created a few dev console commands: Resetting tutorials.
- <kbd>Shift</kbd><kbd>E</kbd> imGUI - Easy discard, easy upgrade multiple times, easy infinite energy

# Good wiki links
- [How to Start Modding] (https://discord.com/channels/309399445785673728/1145803879821361293/1145803879821361293)
- [BasicMod (getting started)] (https://github.com/Alchyr/BasicMod/wiki) - this is the base for this mod.
- [Base Mod wiki (40 Pages)] (https://github.com/daviscook477/BaseMod/wiki)
- [StSLib wiki (14 Pages)] (https://github.com/kiooeht/StSLib/wiki)
- [Spreadsheet of Vanilla Characters] (https://docs.google.com/spreadsheets/d/1c5j-O1zIckvEPtI4sSI7YeG6v1EptKxRwmPV-J73tCA/edit?pli=1#gid=0 )
- [Character Design Pitfalls] (https://discord.com/channels/309399445785673728/1146430771528220792/1146431508119306340)
- [Packmaster Github] (https://github.com/erasels/PackmasterCharacter) - this has been code reviewed. My code has NOT been code reviewed sadface.
- [Adding additional authors and details] (https://github.com/kiooeht/ModTheSpire/wiki/ModInfo)

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
- [Loadout Mod] (https://steamcommunity.com/sharedfiles/filedetails/?id=2814267979) - there's a tab on the left side of the screen which lets you spawn any card, any relic, and any enemy. I ended up preferring the dev console to test cards.
- [Run Resumer] (https://steamcommunity.com/sharedfiles/filedetails/?id=3127212809) - to test against the Heart after balancing cards. Also easy campfire access.
- [Better Debug] (https://steamcommunity.com/sharedfiles/filedetails/?id=2351301825) - type "debug" in the dev console to use. It helps when I need to look at which addToBot/addToTop Action is playing or if I need to look at draw coordinates.
- [Modded Spire Exporter] (https://steamcommunity.com/sharedfiles/filedetails/?id=2069872611&searchtext=exporter) - Export every card of a mod to an html file! Now I can Ctrl + F for specific descriptions in Packmaster
- [Googly Eyes steam] (https://steamcommunity.com/sharedfiles/filedetails/?id=1615430126) - Adds Replay value 
- [Googly Eyes github] (https://github.com/twanvl/sts-googly-mod/) - Be warned: Every time you open the game, the googly-eye-locations.json DOES NOT append new entries to the existing file. It overwrites the file! I learned that lesson the hard way.
- [Spriter] (https://www.youtube.com/watch?v=hNyEVGeMf-o) - this is the tool used for animating in this mod.
- [Mega Transparent The Spire 2.0] https://steamcommunity.com/sharedfiles/filedetails/?id=3309442660 - you can see the debug log while you play.
- [Packmaster Wiz] (https://github.com/erasels/PackmasterCharacter/blob/main/src/main/java/thePackmaster/util/Wiz.java) - Utility Library with short function abbreviations for things like atb for addToBottom.
- Github.com - You need to log in to search the search bar. Sometimes I'll search the whole website with very specific code to find what I'm looking for.
- Steam > Settings > Game Recording > Record in Background. Play game. Steam > Library > Slay the Spire > video is underneath POST-GAME SUMMARY. Amazing debugging tool.
- YouTube - You can double right-click a video and take snapshot.
- YouTube - You can press <kbd>.</kbd> or <kbd>,</kbd> to move the video by one frame.
- You can edit `AppData\Local\ModTheSpire\mod_lists.json` to re-organize MTS profiles

# General Coding Tips for beginners
- Learn some basics. You don't need a full course. What is a method? What is method overloading?
- Test your code as soon as you code it. People have short-term memory and it's a lot harder to find bugs when you're recalling what you coded 2 hours ago.
- When you test your code, use the bare minimum amount of mods for faster loading. I keep it strictly to BaseMod, StSLib, Run Resumer, Loadout, and my mod. This also isolates your code so you don't end up chasing a mod conflict bug. 
- There's a buttload of testing. I hate testing. I abhor it. But the definition of quality assurance is testing.
- I use [Java Playground] https://www.onlineide.pro/playground/java for testing very isolated code, just to double check my math sometimes.
- I use `SonicMod.logger.info("here's some info: " + myVariableName")` in the why-isnt-this-working?? scenarios a lot.

# StS Modding Gotchas
- If an attack targets ALL enemies, you need this `this.isMultiDamage = true;`.
- `this.retain = true;` only retains for one turn. See `setSelfRetain(true);` for retaining a card every turn.
- `modifyCostForCombat(amt)` is a counter-intuitive name for a function. Amt is not the new cost. Amt increments it.
- Overriding the `upgrade` function in BasicMod can lose functionality. If you're going to do so, you can include the `super()` at the end of the upgrade function.
- PowerStrings.json requires `[E]` to have spaces around it. For example: `[E] [E] .`
- TogetherInSpire v6.4.0 has a thing. If your card is a skill that applies Vulnerable, the card.target will end up being SELF and m is null.
- TogetherInSpire v6.4.0 potions require additional code. See below or see my ChaosSodaPotion.
```
// TogetherInSpire needs these textures
    private static final Texture containerImg = ImageMaster.POTION_BOTTLE_CONTAINER;
    private static final Texture liquidImg = ImageMaster.POTION_BOTTLE_LIQUID;
    private static final Texture hybridImg = ImageMaster.POTION_BOTTLE_HYBRID;
    private static final Texture spotsImg = ImageMaster.POTION_BOTTLE_SPOTS;
    private static final Texture outlineImg = ImageMaster.POTION_BOTTLE_OUTLINE;
```
- TogetherInSpire v6.4.20 mod phasedEvents w/ combats have reports of not working.
- `isIndeedWithoutADoubtInCombat()` is apparently necessary b/c some cards crash the game in the compendium.
- Custom events need a patch to see its name in the run history. [GetEventNamePatch] (https://github.com/erasels/PackmasterCharacter/blob/main/src/main/java/thePackmaster/patches/GetEventNamePatch.java
- Unlocking cards needs the AutoAdd to setDefaultSeen(false)
```Java
@Override
  public void receiveEditCards() {
    new AutoAdd(modID) // Loads files from this mod
            .packageFilter(BaseCard.class) // In the same package as this class
            .setDefaultSeen(false) // And marks them as seen in the compendium
            .cards(); // Adds the cards
  } 
```
- Unlocking cards is a pain, b/c I can't accurately reflect the player's environment. I ended up taking a bit of Downfall's code. [Downfall Unlock Code] (https://github.com/mikemayhemdev/DownfallSTS/blob/f744942e2a63b4f2cb73f9f8c4d9175141a09ce3/src/main/java/downfall/downfallMod.java#L2010)
- If you choose to Retain an Ethereal card, it typically does Retain, which is counter-intuitive to base game logic.
- I'm sure there's more! But idr rn.

# IntelliJ shortcuts
- Double tapping <kbd>Shift</kbd> : brings up a Universal Code Search Tool
- <kbd>Ctrl</kbd><kbd>Shift</kbd><kbd>F</kbd> : Find All
- <kbd>Alt</kbd><kbd>Enter</kbd> : Context Actions
- <kbd>Ctrl</kbd><kbd>B</kbd> : Decompile/Direct to source

# Testing cards
The Loadout mod is great. I've used it a ton in the beginning, but now I mostly use console commands.
- See [BaseMod/wiki #Registering your command] (https://github.com/daviscook477/BaseMod/wiki/Console#registering-your-command)
- Take a look at my src/main/java/theHedgehog/console/SonicConsoleDevCustom.java and feel free to copy any code there.

# Every mod character should do this
- Before you publish, you should make sure your modID is all lowercase for Chinese compatibility. If you change the modid after publish, you might end up deleting everyone's save file of your character.
- People like to know more about a mod before they subscribe. Add a small preview compendium. Add info about the mechanics. Add "English" as a tag.
- Create a win screen. I'm tired of the Ironclad ending on modded characters. It's not that hard. You don't need 3 pictures. You can just use one picture. See Sonic.java.
```java
   @Override
    public List<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(endingPath("ending.png")));
        return panels;
    }
```
- Change out Bash for the Gremlin match and keep in the `getStartCardForEvent()`
- Update the version number

# Base Game things
- [Red for attack, blue for block] (https://www.reddit.com/r/slaythespire/comments/1rtdpxv/blocks_cards_look_like_attack_cards_card_visual/)
- Silent has two extra starter cards so that you need exactly two turns for a complete shuffle
- Defect has Lightning in its starter relic and its starter deck. So if you have a secondary resource, do consider having it in your starter relic and starter deck.
- Every character has their three character unique potions and relics.
- Card Texts split up NLs appropriately. I was a bit hesitant on having NLs on cards w/ too much text but it does make a lot of things easier to read.

# Steam Workshop Link
- https://steamcommunity.com/sharedfiles/filedetails/?id=3489847473
- Less than 25 ratings = 0 stars
- More than 25 ratings = 3 stars
- More than 30 ratings = 4 stars
- More than 200 ratings = 5 stars

- The first chart with the yellow line has two numbers when you hover over said yellow line: Days and Total Subscriptions.
- The second chart with all of the lines demonstrate that people are typically more active on the weekend.

# How to farm downvotes
- ai art
- character mod that adds global content
- enemies that are too hard
- not having zhs translation (Chinese Simplified)
- low quality
- gay flags and gay
- a very minor basegame mechanic change

# Known mod conflicts
- Lights Out & Bundle of Energy
- ModTheSpire++ & Texture Replacer
- ModTheSpire++ & Loadout
- Run Resumer & DownFall
- Run Resumer & Act Like It


