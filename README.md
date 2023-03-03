[![kotlin_version](https://img.shields.io/badge/kotlin-1.8.0-blueviolet?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![minecraft_version](https://img.shields.io/badge/minecraft-1.19-green?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![platforms](https://img.shields.io/badge/platform-spigot-blue?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
# Kapitalystik [WIP]
### Minecraft plugin to create organizations
## Commands-Core
| Command  |  Description  |  Permission |
|----------|:-------------:|------:|
| `/kpt create \<tag> \<name>` |  Create organization named \<name> with tag \<tag> | kapitalystic.create |
| `/kpt setspawn` |  Set spawn of your organization | kapitalystic.spawn.set |
| `/kpt spawn` |  Teleport to spawn of your organization | kapitalystic.spawn.tp |
| `/kpt spawnpublic \<bool>` |  Sep spanw of your organization public/private | kapitalystic.spawn.visibility |
| `/kpt disband` |  Disband your organization if yo're the owner | kapitalystic.disband
| `/kpt rename \<name>` |  Rename your organization | kapitalystic.rename |
| `/kpt invite \<user>` |  Invite online user to your organization | kapitalystic.invite |
| `/kpt accept \<tag>` |  Accept invite from organization with tag \<tag> | kapitalystic.accept |
| `/kpt kick \<user>` |  Kick user from your ogranization \<tag> | kapitalystic.kick |
| `/kpt transfer \<user>` |  Give organization leadership to other player \<tag> | kapitalystic.transfer |
| `/kpt bio \<message>` |  Change town BIO | kapitalystic.bio |
| `/kpt rules` |  List town rules | kapitalystic.rules.list |
| `/kpt rules add \<index> \<rule>` |  Add new rule to town | kapitalystic.rules.add |
| `/kpt rules remove \<index>` |  Remove rule at index \<index> | kapitalystic.rules.remove |
| `/kpt list` |  List of current organizations | kapitalystic.orgs.list |
| `/kpt org \<tag> info` |  Organization info | kapitalystic.orgs.info |
## Configuration-Core
```yaml
general:
  economy:
    # Is Economy enabled
    enabled: true
    # Organization creation costs
    create: 5000
    # Organization rename costs
    rename: 5000
    # Invite player to organization costs
    invite: 100
    # Set organization bio costs
    bio: 100
    # Setspawn cost
    spawn:
      set: 100
      visibility: 200
    # Add or remove rule costs
    rules:
      add: 100
      remove: 100

```

Also, checkout [AstraLearner](https://play.google.com/store/apps/details?id=com.makeevrserg.astralearner) - it will help you to learn foreign words easily!
