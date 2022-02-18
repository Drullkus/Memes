# Memes
A website where you can authenticate yourself using Github then post memes. Memes

To start, you will need to configure an [Oauth App on Github](https://docs.github.com/en/developers/apps/building-oauth-apps/creating-an-oauth-app) to get your client ID and client secret. Once you have these; write an `.env` file in this root directory (ignored by VCS) and write the file contents as follows:

```.env
SPRING_GITHUB_ID=<Your Github ID>
SPRING_GITHUB_SECRET=<Your Github Seceret>
```

Replace these placeholders `<Your Github ID>` and `<Your Github Secret>` with their real values respectively, once you have obtained your Oauth ID and Secret from the hyperlink above.