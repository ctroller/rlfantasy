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

Then simply add users (important: **usernames** from RLFantasy, **NOT** team names!) to the 
`participants.txt` file (one per line), and optionally modify
the `output.txt` file to your likings (use `{DATA}` as placeholder for the data).

**How to run**

The jar file is executable, so simply double-click it. Alternatively, run `java -jar rlfantasy.jar` 
in your command line.

The app will output the result to a file named `rlfantasy.txt`.

**Customizing Output**

With the `rlfantasy.properties` (don't forget to rename from `template_`) you can customize the format of the output. The following variables are available to use:

`player_format`:

| **Variable** | **Description** | **Example** |
| :--- | :--- | :--- |
| `{name}` | Name of the player | Tylacto |
| `{points}` | Points of the player | 123 |
| `{position}` | Chosen player position | ATT |

`output_format`:

| **Variable** | **Description** | **Example** |
| :--- | :--- | :--- |
| `{rank}` | Current Weekly Rank | 6 |
| `{align}` | Blank Space align to pretty print it a bit more |   |
| `{rankLossGain}` | Rank Loss/Gain compared to last week | +3 |
| `{teamName}` | RLFantasy Team Name | What A Cool Team! |
| `{user}` | Username that belongs to the team | troxito |
| `{totalPoints}` | Total Points of the team over all weeks | 12345 |
| `{totalPoints}` | String representing the overall Team MVP | Tylacto, 5555pts |
| `{weeklyMVPs}` | String representing an enumeration of all MVPs per week | (1) Tylacto, 555pts [ATT] \| (2) Tylacto, 130pts [DEF] |


**Advanced: Building + Dependencies**

Simply build it with maven. 

Dependencies are OkHttp3, Jsoup and JetBrains Annotations (see `pom.xml`)
