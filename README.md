# GXF Service template
This is a template repository for GXF services.

## Developer configuration
To retrieve GXF dependencies you need to configure a local GitHub token.

1. Go to GitHub to create a classic token: [Create Classic Token](https://github.com/settings/tokens/new)
2. Give the token a name
3. Choose an appropriate expiration date 
4. Add the scope `read:packages`
5. Press the `Generate token` button
6. Copy the newly generated token

Now place the generated token in `~/.gradle/gradle.properties` 
```properties
github.username=<github username>
github.token=<github token>
```
 
