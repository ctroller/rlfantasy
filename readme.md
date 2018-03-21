**RLFantasy**

Forgot to create your league for RLFantasy, or didn't add people to it? Sucks.

But there's a way to run your own "private" league with the help of this app.

**How it works**

The app will fetch the weekly stat pages off of 
[RLFantasy](https://fantasy.rocket-league.com) and parse the contents into Java Objects,
then pretty print them into a file.

**How to set it up**

Simple: Download the latest release zip (containing 3 files: `rlfantasy.jar`, 
`template_output.txt` and `template_participants.txt`), then rename the `template_output.txt` 
file to `output.txt`, and do the same with the `template_participants.txt` 
(into `participants.txt`) file.

Then simply add users to the `participants.txt` file (one per line), and optionally modify
the `output.txt` file to your likings (use `{DATA}` as placeholder for the data).

**How to run**

The jar file is executable, so simply double-click it. Alternatively, run `java -jar rlfantasy.jar` 
in your command line.

The app will output the result to a file named `rlfantasy.txt`.

**Advanced: Building + Dependencies**

Simply build it with maven. 

Dependencies are OkHttp3, Jsoup and JetBrains Annotations (see `pom.xml`)