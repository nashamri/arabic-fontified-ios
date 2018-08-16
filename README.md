![GPL v2](https://img.shields.io/badge/License-GPL%20v2-blue.svg)

# arabic-fontified-ios

A little web utility to decorate Arabic text in iOS apps.

### Live version

A live copy of this repo is available on my site. You can access it
[Here](https://nasser.space/arabic-fontified-ios).

### Development mode

To start the Figwheel compiler, navigate to the project folder and run the following command in the terminal:

```
lein figwheel
```

Figwheel will automatically push cljs changes to the browser.
Once Figwheel starts up, you should be able to open the `public/index.html` page in the browser.


### Building for production

```
lein clean
lein package
```
