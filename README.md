# Blue Zenith Client

## Why is this public?
This project has been abandoned since July of 2023 as neither me (angles) or lev have time or motivation to continue working on the client and with its awful codebase.
We decided to un-private this repository in March 2024 as a way to mark the end of life of the client and maybe get closure.

## You've been warned
If you decide to throughly inspect the source, don't be surprised when you see some extremely messy code (or awful code if you look at the old clickgui), some skidded parts (font renderer, one of the pathfinders, hypixel tower and probably something else I cannot remember anymore), three different code styles and modules that do not make sense (e.g. watchdog velocity disabler module, which was the last thing I played around with mid-2023).

## A bit about the project
This repository has no past commits as it's a reupload in order to hide past commits (there were about 760). As much as I wanted to preserve them, there's some stuff that we still use (hypixel ban checker in particular) and that we'd like to keep private, and doing that is impossible if we keep commit history.

This project has started mid-2021 and fully ended in late 2023, with the last update being released in July of 2022.

The client was free to use, as we never saw a reason to put a price tag on it because we primarily worked on it only to sharpen our own programming skills (which kind of worked).


**If you decide to compile/launch the client - there will probably be a bunch of errors and missing files (esp. font files)**

## Some good stuff
We are particularly proud of a few things that we made in the client (even if they don't follow any coding conventions and all that soyjak shit, making/using them was satisfying)

by angles:
- Account Manager and its functionality (design ripped off of novoline with some of my own additions)
- HUD (NewRenderer)
- Draggables system (making various hud elements draggable with barely any additions to their code needed)
- Some of the targethuds (the system for them is a shitshow but they look goood)
- Probably the event system, it's nothing new but I found it cool when I first made it
- "Ancient Novoline" clickgui mode. The modular structure is nice.
- Chained calls for creating settings, much better than using stupid constructors


by levzzz:
- TP Aura
- Verus boat disabler
- Scaffold ("as messy as it is")
- S08 Watchdog disabler
- C08 Hypixel movement disabler (the 1.17 viaversion thing, we found it ourselves a little later than moon did and still got to use it)


by orangecat:
- Intellij ClickGUI (code is a mess but it was a cool thing by orangecat)

## What's next?
It might be time for us to quit minecraft client development and we'll never publish any more projects like that, but as of writing this (March 8th, 2024) we are working on a cross-version and possibly cross-edition client (using none of this codebase) just as a fun project, this time trying to maintain the quality of our code across the whole project.

## In case you're interested, here's a (not so) quick rundown on the history of the client
OrangeCat joined my discord (called prayhouse, created after liquidsense discord got terminated to keep all of the friends I made) in 2021 and eventually he told me that he's decompiling latest 1.8.9 optifine.

Somehow, that last bit led to me starting a client with him (at that moment, as a joke).

I was eager to learn java (as I barely knew any of it) and thought that making a client in it was a good way to learn. It was.

First server we targeted was omegacraft (you can see a video of it here: https://www.youtube.com/watch?v=5l19kLEOBCQ), and that turned out pretty well.

In the meantime, my good friend levzzz joined the project with no java knowledge and quickly learned it, working on primarily hypixel bypasses and killaura/scaffold.

After omegacraft died off, I started working on bypasses for old verus (the version they used on blocksmc in 2021), which eventually led to me finding a movement disabler, posting a video on it and getting loads of people interested in the client.

With the acquired "popularity" of blue zenith, we began accepting users by applications, me foolishly believing that I could get the client up & running with a basic auth system (since it was semi-private).

Long story short, I figured that auth would take me too long to make properly and we released a build for some users that was basically built from the latest publicly available source code, which was good enough for the first release. The disabler that everyone wanted was released as an obfuscated script (the client had a very basic JS scripting system). That way we released the client without letting the hype die off & still managed to keep the bypass private (the JS file was obfuscated).

The thing I enjoyed most was seeing word about the client spread to other discords, with some people even calling it a paste (which it was not).

After a couple days one of the users leaked the jar file and the script to a guy named ZeroCracks that later would upload a youtube video claiming he 'cracked' our client. Too bad you cannot crack something that has no protection, I guess.

That leak did not really affect us as no private code was truly leaked, but the unprotected release bought me some time to make a very.... interesting auth system. The next release would contain better BlocksMC bypasses, protection, and some stuff for hypixel.

We've managed to build an update schedule until one day in November 2021 OrangeCat disappeared off discord for a couple days, and after some time I received a DM from someone sending me a video posted by OrangeCat that contained the source code of the client.

We had no idea why he did that, but as I later found out he had done it because of his hatred towards levzzz.

We had to act quickly and in just 30 minutes DMCA takedown requests were sent to MediaFire and YouTube, effectively taking down the only source of the leaked code. A few people downloaded it, but it did not spread.

Now with only me and levzzz left working on the client, we decided to up our obfuscation game and rewrite the protection. I was highly unmotivated, so that last part took me about a month until I sprinted it in two days, and another update released in January of 2022.

That update was primarily focused on hypixel bypasses & unpatching of the BlocksMC disabler (which effectively was proof that the disabler was never skidded in the first place and I knew what I was doing back then).

The hypixel bypasses got patched in a few days, levzzz (who was making them) became unmotivated and I became preoccupied with my upcoming exams.

Fast forward to June 2022, school was over for both of us & we finally had some free time. This was a period of time where the client developed rapidly, with me making various internal improvements, new visuals (clickgui, draggable hud elements, alt manager) and lev making... you guessed it, more hypixel bypasses.

This went on for about a month until we were finally ready to release another update, six months after the last one.

We've acquired some new users and the bypasses had worked for a week or two, then some of them got patched and last of our few users stopped using the client.

This would be the last update the client would see.

From then on we both occasionally tinkered with it, but no new notable features or bypasses were made.

In october of 2022 the VPS that the authentication server was running on went down and that marked the end of Blue Zenith for its (probably three) users.
