# GXF Service template
This is a template repository for GXF services.

## Per-template configuration
1. Add `gxf-developers` as admin to the contributors
2. Setup branch protection with the following flags
   - Require a pull request before merging
     - Require approvals (1 or 2 depending on the type of repository)
     - Dismiss stale pull request approvals when new commits are pushed
   - Do not allow bypassing the above settings
2. Add Backstage meta-data
3. Change the name `gxf-service-template` to your component name in
   - `build.gradle.kts`: `imageName.set("ghcr.io/osgp/gxf-service-template:${version}")`
   - `settings.gradle.kts`: `rootProject.name = "gxf-service-template"`

## Developer environment configuration (only once)
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
 
