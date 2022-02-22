# Memes
A website where you can authenticate yourself using Github then post memes. Memes

To even run this project, you will need to create an `.env` file at the project root before you can run `sudo docker compose -f docker_compose.yml up`.

## The `.env` file

Start your `.env` file with this blank template and start quantifying each variable with its appropriate value:

```
GITHUB_OAUTH_ID=
GITHUB_OAUTH_SECRET=

AUTH0_ID=
AUTH0_SECRET=

POSTGRES_USER=
POSTGRES_PASSWORD=
```

- You will need to configure an [Oauth App on Github](https://docs.github.com/en/developers/apps/building-oauth-apps/creating-an-oauth-app) to get your client ID and client secret.
- You will need the same from https://auth0.com/
  - You will also need the URI, formatted to as HTTPS address. Ensure you have a trailing slash at the end of the URI, it is apparently critical to have.
- Pick a username and password for locking the Postgres database.
