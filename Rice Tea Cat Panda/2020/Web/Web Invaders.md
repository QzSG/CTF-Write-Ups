# Web Invaders

It seems to be some web invaders game using web assembly in an iframe leading to `https://jef1056.github.io`

I am a lazy ass so I did not play the game, its hard as hell.

We see that the engine can be loaded with some extra parameters 

```js
var extra_params = {
		archive_location_filter: function( path ) {
			return ("archive" + path + "");
		},
		engine_arguments: [],
		custom_heap_size: 268435456,
		disable_context_menu: false
	}
```

Since all domains served using github.io has their source code public we went to `https://github.com/JEF1056/jef1056.github.io` and dug around.
Looks like there is an archive folder with some weird file extensions inside
We open up the json file and looks like an index of the other files used

```json
{
  "content": [
    {
      "name": "game.projectc",
      "size": 2699,
      "pieces": [
        {
          "name": "game.projectc0",
          "offset": 0
        }
      ]
    },
    {
      "name": "game.arci",
      "size": 4128,
      "pieces": [
        {
          "name": "game.arci0",
          "offset": 0
        }
      ]
    },
    {
      "name": "game.arcd",
      "size": 21047,
      "pieces": [
        {
          "name": "game.arcd0",
          "offset": 0
        }
      ]
    },
    {
      "name": "game.dmanifest",
      "size": 7964,
      "pieces": [
        {
          "name": "game.dmanifest0",
          "offset": 0
        }
      ]
    },
    {
      "name": "game.public.der",
      "size": 162,
      "pieces": [
        {
          "name": "game.public.der0",
          "offset": 0
        }
      ]
    }
  ]
}
```

I have no idea what the files are honestly so I downloaded them all.
`game.arcd0` in particular looks likes its part of the textures or sprites used in the game, perhaps the flag gets dumped once u win the game.

As dumb luck would have it, running
```bash
strings game.arcd0 | grep "rtcp{" -A 2
```
gives us our flag, we would have been stumped if it wasnt readable or encoded.

![](webinvadersoops.png)

This is probably not the right way for this challenge but a flag is a flag

Flag: ```rtcp{web_h^ck3r_0004212}```